package nl.naxanria.thisandthat.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.thisandthat.container.ContainerFurnacePrimitive;
import nl.naxanria.thisandthat.tile.TileEntityPrimitiveFurnace;
import nl.naxanria.thisandthat.util.Constants;

public class GuiFurnacePrimitive extends GuiContainerBase<ContainerFurnacePrimitive>
{
  public GuiFurnacePrimitive(ContainerFurnacePrimitive inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int xPos = (width - xSize) >> 1;
    int yPos = (height - ySize) >> 1;
    
    
    
    drawNineSlice(xPos, yPos, xSize, ySize, Constants.BACKGROUND_PRIMITIVE);
    
    //drawDefault();
    
    drawSlots(xPos, yPos);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    TileEntityPrimitiveFurnace tile = container.tile;
    
    int smeltW = (int) ((float) tile.getCookTime() / (float) tile.getTotalCookTime() * 16);
    if (smeltW < 0)
    {
      smeltW = 0;
    }
    int burnTime = tile.getFurnaceBurnTime();
    
    Slot slot = container.getSlot(TileEntityPrimitiveFurnace.SLOT_SMELT1);
    int xp = slot.xPos + 18;
    int yp = slot.yPos + 2;
    
    drawRectWidthHeight(xp, yp, 16, 6, Color.DARK_GRAY.color);
    drawRectWidthHeight(xp, yp, smeltW, 6, Color.ORANGE.color);
    
    yp = container.getSlot(TileEntityPrimitiveFurnace.SLOT_FUEL).yPos - 2 - fontRenderer.FONT_HEIGHT;
    
    drawString(fontRenderer, String.valueOf(burnTime), xp, yp, Color.WHITE.color);
    
    Slot hover = getSlotUnderMouse();
    if (hover != null)
    {
      ItemStack stack = hover.getStack();

      xp = hover.xPos;
      yp = hover.yPos;
      
      if (!stack.isEmpty())
      {
        itemRender.renderItemAndEffectIntoGUI(mc.player, hover.getStack(), xp, yp);
        itemRender.renderItemOverlayIntoGUI(fontRenderer, hover.getStack(), xp, yp, null);

        drawHoveringText(getItemToolTip(stack), xp, yp);
      }
    }
    
  }
}
