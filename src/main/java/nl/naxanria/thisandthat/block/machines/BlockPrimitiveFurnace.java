package nl.naxanria.thisandthat.block.machines;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.block.BlockTileGuiBase;
import nl.naxanria.nlib.util.WorldUtil;
import nl.naxanria.thisandthat.block.BlocksInit;
import nl.naxanria.thisandthat.gui.ModGuiHandler;
import nl.naxanria.thisandthat.tile.TileEntityPrimitiveFurnace;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockPrimitiveFurnace extends BlockTileGuiBase<TileEntityPrimitiveFurnace>
{
  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  
  public static boolean keepInventory;
  
  public BlockPrimitiveFurnace()
  {
    super(Material.IRON, "furnace_primitive", ModGuiHandler.FURNACE_PRIMITIVE);
    
    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    
    setHardness(6);
    
    droppedItem = Item.getItemFromBlock(this);
  }
  
  @Override
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
  {
    return new ItemStack(BlocksInit.Furnace.FURNACE_PRIMITIVE, 1, 0);
  }
  
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune)
  {
    return Item.getItemFromBlock(BlocksInit.Furnace.FURNACE_PRIMITIVE);
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
  {
    tooltip.add(TextFormatting.YELLOW + "A slightly better furnace");
  }
//
//  public static void setState(boolean active, World world, BlockPos pos)
//  {
//    IBlockState iblockstate = world.getBlockState(pos);
//    TileEntity te = world.getTileEntity(pos);
//    keepInventory = true;
//
//    world.setBlockState(pos, BlocksInit.Furnace.FURNACE_PRIMITIVE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)));
//
//    keepInventory = true;
//
//    if (te != null)
//    {
//      te.validate();
//      world.setTileEntity(pos, te);
//    }
//  }
  
  @Override
  public int damageDropped(IBlockState state)
  {
    return 0;
  }
  
  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
  {
    return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)) / EnumFacing.values().length);
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta)
  {
    EnumFacing enumfacing = EnumFacing.getFront(meta);
    if (enumfacing.getAxis() == EnumFacing.Axis.Y)
    {
      enumfacing = EnumFacing.NORTH;
    }
    
    return this.getDefaultState().withProperty(FACING, enumfacing);
  }
  
  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
  {
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }
  
  @Override
  protected BlockStateContainer createBlockState()
  {
    return new BlockStateContainer(this, FACING);
  }
  
  @Override
  public int getMetaFromState(IBlockState state)
  {
    return state.getValue(FACING).getIndex();
  }
  
  private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
    if (!world.isRemote)
    {
      IBlockState northState = world.getBlockState(pos.north());
      IBlockState southState = world.getBlockState(pos.south());
      IBlockState westState = world.getBlockState(pos.west());
      IBlockState eastState = world.getBlockState(pos.east());
      EnumFacing enumfacing = state.getValue(FACING);
      
      if (enumfacing == EnumFacing.NORTH && northState.isFullBlock() && !southState.isFullBlock())
      {
        enumfacing = EnumFacing.SOUTH;
      }
      else if (enumfacing == EnumFacing.SOUTH && southState.isFullBlock() && !northState.isFullBlock())
      {
        enumfacing = EnumFacing.NORTH;
      }
      else if (enumfacing == EnumFacing.WEST && westState.isFullBlock() && !eastState.isFullBlock())
      {
        enumfacing = EnumFacing.EAST;
      }
      else if (enumfacing == EnumFacing.EAST && eastState.isFullBlock() && !westState.isFullBlock())
      {
        enumfacing = EnumFacing.WEST;
      }
      
      world.setBlockState(pos, state.withProperty(FACING, enumfacing), WorldUtil.FLAG_STATE_SEND_TO_ALL_CLIENTS);
    }
  }
  
  public IBlockState withRotation(IBlockState state, Rotation rot)
  {
    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
  }
  
  public IBlockState withMirror(IBlockState state, Mirror mirror)
  {
    return state.withRotation(mirror.toRotation(state.getValue(FACING)));
  }
  
  public void onBlockAdded(World world, BlockPos pos, IBlockState state)
  {
    this.setDefaultFacing(world, pos, state);
  }
  
  @Nullable
  @Override
  public TileEntityPrimitiveFurnace createTileEntity(World world, IBlockState state)
  {
    return new TileEntityPrimitiveFurnace();
  }
  
  @Override
  public Class<TileEntityPrimitiveFurnace> getTileEntityClass()
  {
    return TileEntityPrimitiveFurnace.class;
  }
}
