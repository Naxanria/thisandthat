package nl.naxanria.nlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.NMod;

public class BlockFluidBase extends BlockFluidClassic // implements IBlockBase
{
  public BlockFluidBase(String name, Fluid fluid, Material material)
  {
    super(fluid, material);
    
    setUnlocalizedName(name);
    setRegistryName(name);
    
    displacements.put(this, false);
  }
  
  @SideOnly(Side.CLIENT)
  public void render()
  {
    ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
  }
}
