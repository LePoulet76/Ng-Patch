/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraftforge.client.model.IModelCustom
 *  net.minecraftforge.client.model.obj.ObjModelLoader
 *  org.lwjgl.opengl.GL11
 */
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

public class NGAdvancedModelRenderer {
    private static Minecraft mc = Minecraft.func_71410_x();
    private static Map<CachedResource, DynamicTexture> resourceCache = Maps.newHashMap();

    public static IModelCustom loadModelFromURL(String name, URL url) {
        return new ObjModelLoader().loadInstance(name, url);
    }

    public static Dimension bindCachedResource(CachedTextureResource c) {
        DynamicTexture dt = null;
        try {
            dt = new DynamicTexture(ImageIO.read(c.getFile()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        dt = !resourceCache.containsKey(c) ? resourceCache.put(c, dt) : resourceCache.get(c);
        dt.func_110564_a();
        System.out.println("Texture : " + dt == null);
        GL11.glBindTexture((int)3553, (int)dt.func_110552_b());
        return new Dimension(c.getWidth(), c.getHeight());
    }
}

