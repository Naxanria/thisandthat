package nl.naxanria.nlib.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class WorldUtil
{
  public static final int FLAG_STATE_UPDATE_BLOCK = 1;
  public static final int FLAG_STATE_SEND_TO_ALL_CLIENTS = 2;
  public static final int FLAG_STATE_PREVENT_RERENDER = 4;
  public static final int FLAG_STATE_RERENDER_MAINTHREAD = 8;
  public static final int FLAG_STATE_PREVENT_OBSERVERS = 16;
  
  
  public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer)
  {
    if (maxTransfer > 0)
    {
  
      if (tileFrom == null || tileTo == null)
      {
        return;
      }
      
      EnumFacing opp = sideTo == null ? null : sideTo.getOpposite();

      if (tileFrom.hasCapability(CapabilityEnergy.ENERGY, sideTo) && tileTo.hasCapability(CapabilityEnergy.ENERGY, opp))
      {
        IEnergyStorage handlerFrom = tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
        IEnergyStorage handlerTo = tileTo.getCapability(CapabilityEnergy.ENERGY, opp);
  
        if (handlerFrom != null && handlerTo != null)
        {
          int drain = handlerFrom.extractEnergy(maxTransfer, true);
          if (drain > 0)
          {
            int filled = handlerTo.receiveEnergy(drain, false);
            handlerFrom.extractEnergy(filled, false);
            //return;
          }
        }
      }
  
    }
  }
  
  public static void doFluidInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer)
  {
    if(maxTransfer > 0)
    {
      if (tileFrom == null || tileTo == null)
      {
        return;
      }
      if(tileFrom.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo)
        && tileTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite()))
      {
        IFluidHandler handlerFrom = tileFrom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo);
        IFluidHandler handlerTo = tileTo.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite());
        FluidStack drain = handlerFrom.drain(maxTransfer, false);
        if(drain != null)
        {
          int filled = handlerTo.fill(drain.copy(), true);
          handlerFrom.drain(filled, true);
        }
      }
    }
  }
  
  public static void dropItemInWorld(World world, BlockPos pos, Item item)
  {
    dropItemInWorld(world, pos, new ItemStack(item));
  }
  
  public static void dropItemInWorld(World world, BlockPos pos, Item item, int amount)
  {
    dropItemInWorld(world, pos, new ItemStack(item, amount));
  }
  
  public static void dropItemInWorld(World world, BlockPos pos, Block block)
  {
    dropItemInWorld(world, pos, new ItemStack(block));
  }
  
  public static void dropItemInWorld(World world, BlockPos pos, Block block, int amount)
  {
    dropItemInWorld(world, pos, new ItemStack(block, amount));
  }
  
  public static void dropItemInWorld(World world, BlockPos pos, ItemStack stack)
  {
    EntityItem toDrop = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    world.spawnEntity(toDrop);
  }
  
  public static IBlockState[] getBlockStatesAround(World world, BlockPos pos)
  {
    IBlockState[] states = new IBlockState[6];
    
    states[EnumFacing.DOWN.getIndex()] = world.getBlockState(pos.down());
    states[EnumFacing.UP.getIndex()] = world.getBlockState(pos.up());
    states[EnumFacing.NORTH.getIndex()] = world.getBlockState(pos.north());
    states[EnumFacing.SOUTH.getIndex()] = world.getBlockState(pos.south());
    states[EnumFacing.WEST.getIndex()] = world.getBlockState(pos.west());
    states[EnumFacing.EAST.getIndex()] = world.getBlockState(pos.east());
    
    return states;
  }
}