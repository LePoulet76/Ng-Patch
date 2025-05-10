package net.ilexiconn.nationsgui.forge.client.render;

import com.google.common.collect.Maps;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.cache.CachedResource;
import net.ilexiconn.nationsgui.forge.client.cache.CachedTextureResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;

public class NGAdvancedModelRenderer
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private static Map<CachedResource, DynamicTexture> resourceCache = Maps.newHashMap();

    public static IModelCustom loadModelFromURL(String name, URL url)
    {
        return (new ObjModelLoader()).loadInstance(name, url);
    }

    public static Dimension bindCachedResource(CachedTextureResource c)
    {
        DynamicTexture dt = null;

        try
        {
            dt = new DynamicTexture(ImageIO.read(c.getFile()));
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }

        if (!resourceCache.containsKey(c))
        {
            dt = (DynamicTexture)resourceCache.put(c, dt);
        }
        else
        {
            dt = (DynamicTexture)resourceCache.get(c);
        }

        dt.updateDynamicTexture();
        System.out.println("Texture : " + dt == null);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, dt.getGlTextureId());
        return new Dimension(c.getWidth(), c.getHeight());
    }
}
