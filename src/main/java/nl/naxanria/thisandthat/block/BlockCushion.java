package nl.naxanria.thisandthat.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockBase;

import javax.annotation.Nullable;

public class BlockCushion extends BlockBase
{
  public BlockCushion()
  {
    super(
    new Material(MapColor.SNOW)
    {
      @Override
      public boolean blocksLight()
      {
        return true;
      }
  
      @Override
      public boolean getCanBurn()
      {
        return true;
      }
  
      @Override
      public EnumPushReaction getMobilityFlag()
      {
        return EnumPushReaction.NORMAL;
      }
  
      @Override
      public boolean blocksMovement()
      {
        return false;
      }
    },
      "block_cushion");
  }
  
  @Override
  public boolean canEntitySpawn(IBlockState state, Entity entityIn)
  {
    return false;
  }
  
  
  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
  {
    return NULL_AABB;
  }
  
  @Override
  public boolean isFullBlock(IBlockState state)
  {
    return false;
  }
  
  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
  {
    return BlockFaceShape.UNDEFINED;
  }
  
  @Override
  public boolean causesSuffocation(IBlockState state)
  {
    return false;
  }
  
  @Override
  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
  {
    entityIn.fallDistance = 0;
  }
}
