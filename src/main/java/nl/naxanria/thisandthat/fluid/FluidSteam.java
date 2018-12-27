package nl.naxanria.thisandthat.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import nl.naxanria.nlib.fluid.FluidBase;

public class FluidSteam extends FluidBase
{
  public FluidSteam()
  {
    super("fluid_steam",
      new ResourceLocation("minecraft:blocks/water_still"),
      new ResourceLocation("minecraft:blocks/water_flow")
    );
    
    
  }
}
