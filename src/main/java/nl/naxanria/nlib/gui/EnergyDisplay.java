package nl.naxanria.nlib.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.nlib.util.logging.Log;

@SideOnly(Side.CLIENT)
public class EnergyDisplay extends Display
{
  private EnergyStorage storage;
  private int x, y;
  
  public EnergyDisplay(int x, int y, EnergyStorage storage)
  {
    this.storage = storage;
    this.x = x;
    this.y = y;
  }
  
  public void setData(int x, int y, EnergyStorage storage)
  {
    this.x = x;
    this.y = y;
    this.storage = storage;
  }
  
  @Override
  public void draw()
  {
    //Minecraft minecraft = Minecraft.getMinecraft();
    
    if (storage == null)
    {
      return;
    }
    
    drawRect(x, y, x + 20, y + 100, Color.GRAY.color);
    
    int h = (int) ((storage.getEnergyStored() / (float) storage.getMaxEnergyStored()) * 100f);
    Log.info(String.format("%d,%d,%d", h, storage.getEnergyStored(), storage.getMaxEnergyStored()));
    
    if (h != 0)
    {
      drawRect(x + 1, y + 1, x + 20 - 2, y + h - 2, Color.LIME.color);
    }
    
  }
}
