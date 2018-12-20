package nl.naxanria.thisandthat.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.nlib.inventory.ItemStackHandlerBase;
import nl.naxanria.thisandthat.tile.TileEntityPrimitiveFurnace;

public class ContainerFurnacePrimitive extends ContainerBase<TileEntityPrimitiveFurnace>
{
  public ContainerFurnacePrimitive(TileEntityPrimitiveFurnace tile, EntityPlayer player)
  {
    super(tile, player);
  
    syncHelper.create
      (
        "furnaceBurnTime",
        tile::setFurnaceBurnTime,
        tile::getFurnaceBurnTime
      );
  
    syncHelper.create
      (
        "cookTime",
        tile::setCookTime,
        tile::getCookTime
      );
  
    syncHelper.create
      (
        "totalCookTime",
        tile::setTotalCookTime,
        tile::getTotalCookTime
      );
  
    ItemStackHandlerBase inventory = tile.inventory;
    
    int x = 45;
    int y = 20;
    int step = 18;
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityPrimitiveFurnace.SLOT_SMELT0, x, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityPrimitiveFurnace.SLOT_SMELT1, x + step, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityPrimitiveFurnace.SLOT_FUEL, x + step * 2, y + step * 2)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityPrimitiveFurnace.SLOT_OUTPUT0, x + step * 3, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityPrimitiveFurnace.SLOT_OUTPUT1, x + step * 4, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
    
    INVENTORY_START = inventory.getSlots();
    
    createPlayerInventorySlots(player.inventory);
  }
}
