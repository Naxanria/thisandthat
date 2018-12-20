package nl.naxanria.nlib.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.logging.Log;

public class ProxyClient extends Proxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(NMod.getModId() + ":" + id, "inventory"));
    }

    public void registerItemRendererWithVariant(Item item, int meta, String id, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(NMod.getModId() + ":" + id, variant));
    }

    @Override
    public String getLocalization(String unlocalized, Object... args)
    {
        return I18n.format(unlocalized, args);
    }

    public <TE extends TileEntityBase> void registerTileEntityRender(Class<TE> tileEntityClass, String tileEntityRendererClass)
    {
        try
        {
            Class clazz = Class.forName(tileEntityRendererClass);
            TileEntitySpecialRenderer renderer = (TileEntitySpecialRenderer) clazz.newInstance();
            ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            Log.error("Unable to register renderer for " + tileEntityClass.toString() + e);
        }
    }
}
