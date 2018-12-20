package nl.naxanria.thisandthat.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.naxanria.nlib.registry.RecipeRegistry;
import nl.naxanria.thisandthat.block.BlocksInit;
import nl.naxanria.thisandthat.item.ItemsInit;

public class RecipesInit
{
  public static void init(RecipeRegistry registry)
  {
    /////////////////////////////
    // blobs, ingots, etc
    /////////////////////////////
    
    registry.addShaped
    (
      new ItemStack(ItemsInit.Blobs.BLOB_PRIMITIVE),
      "XXX",
      "XOX",
      "XXX",
      'X', new ItemStack(Blocks.DIRT),
      'O', new ItemStack(Items.BRICK)
    );
    
    registry.addShaped
    (
      new ItemStack(ItemsInit.Blobs.BLOB_BASIC),
      "XXX",
      "XOX",
      "XXX",
      'X', new ItemStack(Blocks.STONE),
      'O', new ItemStack(Items.IRON_INGOT)
    );
    
    //smelting
    GameRegistry.addSmelting(ItemsInit.Blobs.BLOB_PRIMITIVE, new ItemStack(ItemsInit.Ingots.INGOT_PRIMITIVE), 0.5f);
    GameRegistry.addSmelting(ItemsInit.Blobs.BLOB_BASIC, new ItemStack(ItemsInit.Ingots.INGOT_BASIC), 0.5f);
    
    // blocks
    registry.add3x3Block(BlocksInit.Metals.BLOCK_PRIMITIVE, new ItemStack(ItemsInit.Ingots.INGOT_PRIMITIVE));
    registry.add3x3Block(BlocksInit.Metals.BLOCK_BASIC, new ItemStack(ItemsInit.Ingots.INGOT_BASIC));
//    registry.addShaped
//    (
//      new ItemStack(BlocksInit.Metals.BLOCK_PRIMITIVE),
//      "XXX",
//      "XXX",
//      "XXX",
//      'X', new ItemStack(ItemsInit.Ingots.INGOT_PRIMITIVE)
//    );
//
//    registry.addShapeless
//    (
//      new ItemStack(ItemsInit.Ingots.INGOT_PRIMITIVE, 9),
//      Ingredient.fromItem(BlocksInit.Metals.BLOCK_PRIMITIVE.getAsItem())
//    );
//
//    registry.addShaped
//    (
//      new ItemStack(BlocksInit.Metals.BLOCK_BASIC),
//      "XXX",
//      "XXX",
//      "XXX",
//      'X', new ItemStack(ItemsInit.Ingots.INGOT_BASIC)
//    );
//
//    registry.addShapeless
//    (
//      new ItemStack(ItemsInit.Ingots.INGOT_BASIC, 9),
//      Ingredient.fromItem(BlocksInit.Metals.BLOCK_BASIC.getAsItem())
//    );
  
    ////////////////////////
    // furnaces
    ////////////////////////
    
    registry.addShaped
    (
      new ItemStack(BlocksInit.Furnace.FURNACE_PRIMITIVE),
      "XXX",
      "XFX",
      "XXX",
      'X', new ItemStack(ItemsInit.Ingots.INGOT_PRIMITIVE),
      'F', new ItemStack(Blocks.FURNACE)
    );
  }
}
