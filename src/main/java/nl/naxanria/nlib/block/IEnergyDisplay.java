package nl.naxanria.nlib.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;

public interface IEnergyDisplay
{
  EnergyStorage getStorageToDisplay(World world, IBlockState state, BlockPos pos);
}
