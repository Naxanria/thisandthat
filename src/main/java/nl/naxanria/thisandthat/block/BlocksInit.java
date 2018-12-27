package nl.naxanria.thisandthat.block;

import net.minecraft.block.material.Material;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.registry.BlockRegistry;
import nl.naxanria.thisandthat.block.machines.BlockPrimitiveFurnace;
import nl.naxanria.thisandthat.block.machines.generators.BlockSteamGenerator;
import nl.naxanria.thisandthat.block.machines.generators.BlockSteamProducer;

public class BlocksInit
{
  public static class Metals
  {
    public static final BlockBase BLOCK_PRIMITIVE = (BlockBase) new BlockBase(Material.IRON, "block_primitive").setHardness(6);
    public static final BlockBase BLOCK_BASIC = (BlockBase) new BlockBase(Material.IRON, "block_basic").setHardness(8);
  }
  
  public static class Furnace
  {
    public static final BlockPrimitiveFurnace FURNACE_PRIMITIVE = new BlockPrimitiveFurnace();
  }
  
  public static class Machines
  {
    public static final BlockSteamProducer STEAM_PRODUCER = new BlockSteamProducer();
    public static final BlockSteamGenerator STEAM_GENERATOR = new BlockSteamGenerator();
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
      
      Machines.STEAM_PRODUCER,
      Machines.STEAM_GENERATOR,
      
      Misc.BLOCK_CUSHION
    );
  }
}
