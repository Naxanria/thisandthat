package nl.naxanria.thisandthat.tile.generators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.fluid.TileEntityFluidTankBase;
import nl.naxanria.nlib.util.CompoundHelper;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.nlib.util.RandomHelper;
import nl.naxanria.nlib.util.WorldUtil;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;
import nl.naxanria.thisandthat.fluid.FluidInit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TileEntitySteamProducer extends TileEntityFluidTankBase
{
  private static class HeatInfo
  {
    final Block block;
    float heating;
    float maxHeat;
    float produceModifier;
  
    public HeatInfo(Block block, float heating, float maxHeat, float produceModifier)
    {
      this.block = block;
      this.heating = heating;
      this.maxHeat = maxHeat;
      this.produceModifier = produceModifier;
    }
  }
  
  private static HashMap<Block, HeatInfo> heatInfoMap = new HashMap<>();
  
  public static void registerHeatingBlock(Block block, float heating, float maxHeating, float produceModifier)
  {
    HeatInfo info;
    
    if (heatInfoMap.containsKey(block))
    {
      info = heatInfoMap.get(block);
      info.heating = heating;
      info.maxHeat = maxHeating;
      info.produceModifier = produceModifier;
    }
    else
    {
      info = new HeatInfo(block, heating, maxHeating, produceModifier);
    }
    
    heatInfoMap.put(block, info);
  }
  
  static
  {
    registerHeatingBlock(Blocks.FIRE, 3, 90, 0.9f);
    registerHeatingBlock(Blocks.LAVA, 5, 150, 1.2f);
  }
  
  private float consumeWaterChance = 10;
  private float consumeWaterTempThreshold = 85;
  
  private float generateRateMax = 100;
  private float temperatureMax = 250;
  private float temperature = 0;
  private float temperatureDecay = 3f;

  private CompoundHelper helper;
  
  public TileEntitySteamProducer()
  {
    super(Fluid.BUCKET_VOLUME * 2);
    
    tank.setCanFill(false);
    
    helper = CompoundHelper.Float()
      .create
      (
        "Temperature",
        this::getTemperature,
        this::setTemperature
      );
  }
  
  @Override
  public EnumFacing[] getFluidProvidingSides()
  {
    return new EnumFacing[] { EnumFacing.UP };
  }
  
  @Override
  public void entityUpdate()
  {
    generateRateMax = 7.5f;
    temperatureMax = 100;
    temperatureDecay = 1.3f;
  
    consumeWaterChance = 4;
    consumeWaterTempThreshold = 85;
    
    if (!world.isRemote && ticksPassed % 3 == 0)
    {
      int lavaCount = 0;
      int waterCount = 0;
  
      IBlockState[] states = WorldUtil.getBlockStatesAround(world, pos);
      List<BlockPos> waterPositions = new ArrayList<>();
      
      // start at 2 as we skip Down and Up for water.
      for (int i = 2; i < 6; i++)
      {
        IBlockState state = states[i];

        if (state.getBlock() == Blocks.WATER && state.getBlock().getMetaFromState(state) == 0)
        {
          waterCount++;
          waterPositions.add(pos.offset(EnumFacing.values()[i]));
        }
      }
      
      HeatInfo info = heatInfoMap.get(states[EnumFacing.DOWN.getIndex()].getBlock());
      
      float change = (info == null) ? -temperatureDecay : info.heating;
      float max = (info == null) ? temperatureMax : info.maxHeat;
      
      float newTemp = MathUtil.clamp(temperature + change, 0, max);
      float diff = newTemp - temperature;
      
      temperature = newTemp;
      float waterCountMultiplier = waterCount / 2f;
      float produce = (temperature / temperatureMax) * (generateRateMax * ((info == null) ? 1 : info.produceModifier)) * waterCountMultiplier;
      
      
//      Log.info(LogColor.PURPLE, "t=" + temperature + ",td=" + diff + ",w=" + waterCount + ",l=" + lavaCount + ",p=" + produce + ",wc=" + waterCountMultiplier);
      
      
//      if (waterCount > lavaCount)
//      {
//        temperature *= 0.998f;
//      }
      
      if (waterCount > 0 && temperature > 50)
      {
        produce *= 1 + 50.0f / temperature;
        
        FluidStack fluidStack = new FluidStack(FluidInit.STEAM, (int) produce);
        
        tank.setCanFill(true);
        int f = tank.fill(fluidStack, true);
  
//        Log.info("filled: " + f);
        
        tank.setCanFill(false);
      }
      
      // todo: the hotter, the higher chance of consuming water
      if (temperature >= consumeWaterTempThreshold)
      {
        if (RandomHelper.chance(world.rand, consumeWaterChance))
        {
          BlockPos wpos = RandomHelper.choose(world.rand, waterPositions);
          if (wpos != null)
          {
            world.setBlockToAir(wpos);
            
            // todo: PARTICLES
          }
        }
      }
    }
    
    super.entityUpdate();
  }
  
  public void setTemperature(float temperature)
  {
    this.temperature = temperature;
  }
  
  public float getTemperature()
  {
    return temperature;
  }
  
  @Override
  public boolean doesShareFluid()
  {
    return true;
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    helper.writeToNBT(compound);
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    helper.readFromNBT(compound);
    
    super.readSyncableNBT(compound, type);
  }
}
