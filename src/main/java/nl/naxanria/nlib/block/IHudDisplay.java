package nl.naxanria.nlib.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

public interface IHudDisplay
{
  void displayHud(Minecraft minecraft, EntityPlayer player, RayTraceResult ray, ScaledResolution resolution);
}
