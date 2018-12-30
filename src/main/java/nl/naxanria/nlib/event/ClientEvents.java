package nl.naxanria.nlib.event;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.naxanria.nlib.block.IEnergyDisplay;
import nl.naxanria.nlib.block.IHudDisplay;
import nl.naxanria.nlib.gui.EnergyDisplay;
import nl.naxanria.nlib.util.logging.Log;

public class ClientEvents
{
  private static EnergyDisplay energyDisplay;
  
  public ClientEvents()
  {
    MinecraftForge.EVENT_BUS.register(this);
  }
  
  @SubscribeEvent
  public void onGameOverlay(RenderGameOverlayEvent.Post event)
  {
    Minecraft minecraft = Minecraft.getMinecraft();
    
    if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && minecraft.currentScreen == null)
    {
      EntityPlayer player = minecraft.player;
      RayTraceResult ray = minecraft.objectMouseOver;
      
      // display hud for held item
      displayHudItem(player.getHeldItemMainhand(), player, minecraft, ray, event.getResolution());
      displayHudItem(player.getHeldItemOffhand(), player, minecraft, ray, event.getResolution());
      
      //Log.info(ray + "");
      
      // display hud for mouseover block/entity
      if (ray != null)
      {
        World world = minecraft.world;
        BlockPos pos = ray.getBlockPos();
  
        Block blockHit = world.getBlockState(pos).getBlock();
        TileEntity tileHit = world.getTileEntity(pos);
        
        //Log.info(String.format("%s%s%s", world, blockHit, tileHit));
  
        if (blockHit instanceof IHudDisplay)
        {
          ((IHudDisplay) blockHit).displayHud(minecraft, player, ray, event.getResolution());
        }
  
        if (blockHit instanceof IEnergyDisplay)
        {
          displayEnergy(((IEnergyDisplay) blockHit).getStorageToDisplay(world, world.getBlockState(pos), pos), player, minecraft, event.getResolution());
        }
        
        if (tileHit != null)
        {
          if (tileHit instanceof IHudDisplay)
          {
            ((IHudDisplay) tileHit).displayHud(minecraft, player, ray, event.getResolution());
          }
          if (tileHit instanceof IEnergyDisplay)
          {
            EnergyStorage storage = ((IEnergyDisplay) tileHit).getStorageToDisplay(world, world.getBlockState(pos), pos);
            if (storage != null)
            {
              displayEnergy(storage, player, minecraft, event.getResolution());
              Log.info(" Displaying energy contents?");
            }
          }
        }
        
      }
    }
  }
  
  private void displayEnergy(EnergyStorage storage, EntityPlayer player, Minecraft minecraft, ScaledResolution resolution)
  {
    if (energyDisplay == null)
    {
      energyDisplay = new EnergyDisplay(3, resolution.getScaledHeight() - 120, storage);
    }
    else
    {
      energyDisplay.setData(3, resolution.getScaledHeight() - 120, storage);
    }
  
  
    GlStateManager.pushMatrix();
    GlStateManager.color(1, 1, 1,1 );
    energyDisplay.draw();
    GlStateManager.popMatrix();
  }
  
  private void displayHudItem(ItemStack stack, EntityPlayer player, Minecraft minecraft, RayTraceResult ray, ScaledResolution resolution)
  {
    if (stack != ItemStack.EMPTY)
    {
      if (stack.getItem() instanceof IHudDisplay)
      {
        ((IHudDisplay) stack.getItem()).displayHud(minecraft, player, ray, resolution);
      }
    }
  }
}
