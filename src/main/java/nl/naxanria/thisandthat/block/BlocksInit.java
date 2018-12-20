package nl.naxanria.thisandthat.block;

import net.minecraft.block.material.Material;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.registry.BlockRegistry;
import nl.naxanria.thisandthat.block.machines.BlockPrimitiveFurnace;

public class BlocksInit
{
  public static class Metals
  {
    public static final BlockBase BLOCK_PRIMITIVE = new BlockBase(Material.IRON, "block_primitive");
    public static final BlockBase BLOCK_BASIC = new BlockBase(Material.IRON, "block_basic");
  }
  
  public static class Furnace
  {
    public static final BlockPrimitiveFurnace FURNACE_PRIMITIVE = new BlockPrimitiveFurnace();
  }
  
  public static class Misc
  {
    public static final BlockCushion BLOCK_CUSHION = new BlockCushion();
  }
  
  public static void init(BlockRegistry registry)
  {
    registry.addAll
    (
      Furnace.FURNACE_PRIMITIVE,
      
      Metals.BLOCK_PRIMITIVE,
      Metals.BLOCK_BASIC,
      
      Misc.BLOCK_CUSHION
    );
  }
}
