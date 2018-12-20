package nl.naxanria.thisandthat;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod
(
  modid = TAT.MODID,
  name = TAT.NAME,
  version = TAT.VERSION
)
public class ModInit
{
  private final nl.naxanria.nlib.NMod NModInstance;
  
  public ModInit()
  {
    this.NModInstance = new TAT(this);
  }
  
  @Mod.EventHandler
  public final void preInit(FMLPreInitializationEvent event)
  {
    NModInstance.preInit(event);
  }
  
  @Mod.EventHandler
  public final void init(FMLInitializationEvent event)
  {
    NModInstance.init(event);
  }
  
  @Mod.EventHandler
  public final void postInit(FMLPostInitializationEvent event)
  {
    NModInstance.postInit(event);
  }
  
  @Mod.EventHandler
  public final void serverLoad(FMLServerStartingEvent event)
  {
    NModInstance.onServerStarting(event);
  }
}
