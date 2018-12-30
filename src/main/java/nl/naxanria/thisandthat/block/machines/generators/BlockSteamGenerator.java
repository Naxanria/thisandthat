package nl.naxanria.thisandthat.block.machines.generators;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.IEnergyDisplay;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.thisandthat.tile.generators.TileEntitySteamGenerator;

import javax.annotation.Nullable;

public class BlockSteamGenerator extends BlockTileBase<TileEntitySteamGenerator> implements IEnergyDisplay
{
  public BlockSteamGenerator()
  {
    super(Material.IRON, "block_steam_generator");
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      TileEntitySteamGenerator tile = getTileEntity(world, pos);
      
      player.sendStatusMessage(
        new TextComponentString(
          String.format("Power: %d, Tank: %d",
            tile.getEnergyStorage(EnumFacing.NORTH).getEnergyStored(),
            ((FluidTank) tile.getFluidHandler(EnumFacing.NORTH)).getFluidAmount()
          )
        ), true
      );
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  @Nullable
  @Override
  public TileEntitySteamGenerator createTileEntity(World world, IBlockState state)
  {
    return new TileEntitySteamGenerator();
  }
  
  @Override
  public Class<TileEntitySteamGenerator> getTileEntityClass()
  {
    return TileEntitySteamGenerator.class;
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public EnergyStorage getStorageToDisplay(World world, IBlockState state, BlockPos pos)
  {
//    EnergyStorage storage = getTileEntity(world, pos).getEnergyStorage();
//
//    Log.info(String.format("Storage: %d / %d", storage.getEnergyStored(), storage.getMaxEnergyStored()));
//
//    return storage;
    
    return null;
  }
}
