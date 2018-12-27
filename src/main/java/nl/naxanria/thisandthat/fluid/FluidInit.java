package nl.naxanria.thisandthat.fluid;

import nl.naxanria.nlib.block.BlockFluidBase;
import nl.naxanria.nlib.fluid.FluidBase;
import nl.naxanria.nlib.registry.FluidRegistry;
import nl.naxanria.thisandthat.util.ModRegistry;


public class FluidInit
{
  public static final FluidBase STEAM = new FluidSteam();
  public static final BlockFluidBase BLOCK_STEAM = new BlockFluidBase("block_steam", STEAM, ModRegistry.Materials.STEAM);
  
  public static void init(FluidRegistry registry)
  {
    registry.addAll
    (
      BLOCK_STEAM
    );
  }
}
