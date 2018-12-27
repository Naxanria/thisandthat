package nl.naxanria.thisandthat.block.machines.generators;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.thisandthat.tile.generators.TileEntitySteamProducer;

import javax.annotation.Nullable;

public class BlockSteamProducer extends BlockTileBase<TileEntitySteamProducer>
{
  public BlockSteamProducer()
  {
    super(Material.IRON, "block_steam_producer");
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      TileEntitySteamProducer tile = getTileEntity(world, pos);
      FluidTank tank = tile.tank;
      
      if (!player.isSneaking())
      {
        useItemOnTank(player, hand, tank);
        
        int amount = tank.getFluidAmount();
        
        player.sendStatusMessage(new TextComponentString(amount + " mb " + tile.getTemperature() + " C " + tile.getComparatorStrength() ), true);
      }
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  @Nullable
  @Override
  public TileEntitySteamProducer createTileEntity(World world, IBlockState state)
  {
    return new TileEntitySteamProducer();
  }
  
  @Override
  public Class<TileEntitySteamProducer> getTileEntityClass()
  {
    return TileEntitySteamProducer.class;
  }
  
  @Override
  public boolean hasComparatorInputOverride(IBlockState state)
  {
    return true;
  }
  
//  @Override
//  public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
//  {
//    TileEntitySteamProducer tile = (TileEntitySteamProducer) world.getTileEntity(pos);
//
//    return tile.getComparatorStrength();
//  }
  
  
}
