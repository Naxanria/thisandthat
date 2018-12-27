package nl.naxanria.nlib.fluid;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import nl.naxanria.nlib.NMod;
import nl.naxanria.thisandthat.TAT;
import nl.naxanria.thisandthat.fluid.FluidInit;

public class FluidBase extends Fluid
{
  public final String name;
  
  public FluidBase(String fluidName, String texture)
  {
    this
    (
      fluidName,
      new ResourceLocation(NMod.getModId(), "blocks/" + texture + "_still"),
      new ResourceLocation(NMod.getModId(), "blocks/" + texture + "_flowing")
    );
  }
  
  public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing)
  {
    super(fluidName, still, flowing);
    
    name = fluidName;
  
    FluidRegistry.registerFluid(this);
    FluidRegistry.addBucketForFluid(this);
  
    //FluidUtil.getFilledBucket(FluidRegistry.getFluidStack(name, Fluid.BUCKET_VOLUME))
    //  .getItem().setCreativeTab(TAT.tab);
  }
  
  public ItemStack getAsBucket()
  {
    return FluidUtil.getFilledBucket(new FluidStack(this, BUCKET_VOLUME));
  }
  
  public static ItemStack getAsBucket(FluidBase fluidBase)
  {
    return fluidBase.getAsBucket();
  }
}
