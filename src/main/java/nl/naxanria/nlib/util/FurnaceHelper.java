package nl.naxanria.nlib.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class FurnaceHelper
{
  public static boolean isFuel(ItemStack stack)
  {
    return getBurnTime(stack) != 0;
  }
  
  public static int getBurnTime(ItemStack stack)
  {
    return TileEntityFurnace.getItemBurnTime(stack);
  }
  
  public static boolean isSmeltable(ItemStack stack)
  {
    return getBurningResult(stack) != ItemStack.EMPTY;
  }
  
  public static ItemStack getBurningResult(ItemStack stack)
  {
    return FurnaceRecipes.instance().getSmeltingResult(stack);
  }
}
