package nl.naxanria.thisandthat.tile.generators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.block.IEnergyDisplay;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.fluid.TileEntityFluidTankBase;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.tile.power.IEnergySharingProvider;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.thisandthat.fluid.FluidInit;

public class TileEntitySteamGenerator extends TileEntityFluidTankBase implements IEnergySharingProvider, IEnergyDisplay
{
  private float producePer10 = 25;
  private float maxConsume = 100;
  
  private EnergyStorageBase storage;
  
  public TileEntitySteamGenerator()
  {
    super(5000, FluidInit.STEAM);
    storage = new EnergyStorageBase(100000, 500, 1500, true);
    
    flags.enableFlag(TileFlags.SaveOnWorldChange);
  }
  
  public EnergyStorage getEnergyStorage()
  {
    return storage;
  }
  
  @Override
  public boolean doesShareFluid()
  {
    return false;
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return (CollectionUtil.contains(getEnergyProvidingSides(), facing)) ? storage : null;
  }
  
  @Override
  public int getEnergyToShare()
  {
    return Math.min(storage.getMaxExtract(), storage.getEnergyStored());
  }
  
  @Override
  public boolean doesShareEnergy()
  {
    return true;
  }
  
  @Override
  public EnumFacing[] getEnergyProvidingSides()
  {
    return EnumHelper.Facing.SIDES;
  }
  
  @Override
  public boolean canShareEnergyTo(TileEntity tile)
  {
    return true;
  }
  
  @Override
  protected void entityUpdate()
  {
    if (!world.isRemote)
    {
      float toConsume = Math.min(tank.getFluidAmount(), maxConsume);
      
      if (toConsume > 10)
      {
        float produce = toConsume / 10 * producePer10;
        Log.info("produced " + produce);
        tank.drain((int) toConsume, true);
        storage.receiveEnergy((int) produce, false);
        
        markDirty();
      }
    }
    
    
    
    super.entityUpdate();
  }
  
  @Override
  public EnergyStorage getStorageToDisplay(World world, IBlockState state, BlockPos pos)
  {
    return storage;
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
//    storage.writeToNBT(compound);
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
//    storage.readFromNbt(compound);
    super.readSyncableNBT(compound, type);
  }
}
