package nl.naxanria.thisandthat.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.IEnergyDisplay;
import nl.naxanria.thisandthat.tile.TileEntityBattery;

import javax.annotation.Nullable;

public class BlockBattery extends BlockTileBase<TileEntityBattery> implements IEnergyDisplay
{
  public BlockBattery()
  {
    super(Material.IRON, "block_battery");
  }
  
  @Nullable
  @Override
  public TileEntityBattery createTileEntity(World world, IBlockState state)
  {
    return new TileEntityBattery();
  }
  
  @Override
  public Class<TileEntityBattery> getTileEntityClass()
  {
    return TileEntityBattery.class;
  }
  
  @Override
  public EnergyStorage getStorageToDisplay(World world, IBlockState state, BlockPos pos)
  {
    return getTileEntity(world, pos).storage;
  }
}
