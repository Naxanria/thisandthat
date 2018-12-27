package nl.naxanria.thisandthat.util;


import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import nl.naxanria.thisandthat.block.BlocksInit;

import java.util.HashMap;

public class ModRegistry
{
  
  public static class Furnace
  {
    private static HashMap<Block, Integer> furnaceModifiers = new HashMap<Block, Integer>()
    {{
      put(Blocks.FIRE, 2);
      put(Blocks.LAVA, 3);
    }};
  
    public static void registerFurnaceModifier(Block block, int amount)
    {
      furnaceModifiers.put(block, amount);
    }
  
    public static void removeFurnaceModifier(Block block)
    {
      furnaceModifiers.remove(block);
    }
  
    public static int getFurnaceModifier(Block block)
    {
      Integer i = furnaceModifiers.get(block);
    
      return (i == null) ? 0 : i;
    }
  }
  
  public static class Materials
  {
    public static final Material STEAM = new MaterialLiquid(MapColor.GRAY);
  }
  
}
