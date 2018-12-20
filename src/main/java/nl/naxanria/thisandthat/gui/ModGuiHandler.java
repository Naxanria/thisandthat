package nl.naxanria.thisandthat.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import nl.naxanria.thisandthat.container.ContainerFurnacePrimitive;
import nl.naxanria.thisandthat.tile.TileEntityPrimitiveFurnace;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
  public static final int FURNACE_PRIMITIVE = 10;
  
  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
    
    switch (ID)
    {
      case FURNACE_PRIMITIVE:
        return new ContainerFurnacePrimitive((TileEntityPrimitiveFurnace) tile, player);
        
      default:
        return null;
    }
  }
  
  @Nullable
  @Override
  public GuiContainer getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    
    switch (ID)
    {
      case FURNACE_PRIMITIVE:
        return new GuiFurnacePrimitive((ContainerFurnacePrimitive) container, player);
      default:
        return null;
    }
  }
}
