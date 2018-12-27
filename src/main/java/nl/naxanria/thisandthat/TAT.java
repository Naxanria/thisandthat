package nl.naxanria.thisandthat;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.block.BlockFluidBase;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.thisandthat.block.BlocksInit;
import nl.naxanria.thisandthat.fluid.FluidInit;
import nl.naxanria.thisandthat.gui.ModGuiHandler;
import nl.naxanria.thisandthat.item.ItemsInit;
import nl.naxanria.thisandthat.recipe.RecipesInit;

public class TAT extends NMod
{
  static
  {
    FluidRegistry.enableUniversalBucket();
  }
  
  public static final String MODID = "tat";
  public static final String NAME = "This and That";
  public static final String VERSION = "@Version@";

  public static final TATTab tab = new TATTab();
  
  @SidedProxy(clientSide = "nl.naxanria.nlib.proxy.ProxyClient", serverSide = "nl.naxanria.nlib.proxy.ProxyServer")
  public static Proxy proxy;

  public static TAT getInstance()
  {
    return (TAT) NMod.getInstance();
  }
  
  public TAT(Object mod)
  {
    super(mod);
  }
  
  @Override
  protected Class getBlockClass()
  {
    return BlocksInit.class;
  }
  
  @Override
  protected Class getItemClass()
  {
    return ItemsInit.class;
  }
  
  @Override
  protected Class getRecipeClass()
  {
    return RecipesInit.class;
  }
  
  @Override
  protected Class getFluidClass()
  {
    return FluidInit.class;
  }
  
  @Override
  public String modId()
  {
    return MODID;
  }
  
  @Override
  public String modName()
  {
    return NAME;
  }
  
  @Override
  public String modVersion()
  {
    return VERSION;
  }
  
  @Override
  public void onServerStarting(FMLServerStartingEvent event)
  {
  
  }
  
  @Override
  protected void onPreInit(FMLPreInitializationEvent event)
  {
    NetworkRegistry.INSTANCE.registerGuiHandler(mod, new ModGuiHandler());
    
    ((BlockFluidBase) FluidInit.STEAM.getBlock()).render();
  }
  
  @Override
  protected void onInit(FMLInitializationEvent event)
  {
    super.onInit(event);
  }
  
  @Override
  protected void onPostInit(FMLPostInitializationEvent event)
  {
    super.onPostInit(event);
  }
  
  @Override
  public CreativeTabs defaultTab()
  {
    return tab;
  }
  
  public static class TATTab extends CreativeTabs
  {
    public TATTab()
    {
      super(MODID);
    }
    
    @Override
    public ItemStack getTabIconItem()
    {
      return new ItemStack(Items.IRON_AXE);
    }
    
    private NonNullList<ItemStack> list;
  
    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
      this.list = list;
      
      super.displayAllRelevantItems(list);
      
      add(FluidInit.STEAM.getAsBucket());
    }
    
    private void add(ItemStack stack)
    {
      list.add(stack);
    }
    
    private void add(Item item)
    {
      list.add(new ItemStack(item));
    }
    
    private void add(Block block)
    {
      block.getSubBlocks(this, list);
    }
  }
}
