package nl.naxanria.nlib.registry;


import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.block.BlockFluidBase;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FluidRegistry
{
  private List<BlockFluidBase> toAdd = new ArrayList<>();
  
  public void addAll(BlockFluidBase... fluidBlocks)
  {
    Collections.addAll(toAdd, fluidBlocks);
  }
  
  public void register(IForgeRegistry<Block> registry, BlockFluidBase block)
  {
    registry.register(block);
  
    Log.info(LogColor.CYAN, "Added fluid " + block.getFluid().getUnlocalizedName() + " with block " + block.getUnlocalizedName());
  }
  
  public void registerAllFluidBlocks(IForgeRegistry<Block> registry)
  {
    toAdd.forEach((b) -> register(registry, b));
  }
}
