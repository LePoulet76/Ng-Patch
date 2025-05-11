/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@SideOnly(value=Side.CLIENT)
public enum GUIStyle {
    DEFAULT("default");

    private Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
    private String id;

    private GUIStyle(String id) {
        this.id = id;
    }

    public void bindTexture(String type) {
        ResourceLocation texture = this.textures.get(type);
        if (texture == null) {
            texture = new ResourceLocation("nationsglory", "textures/gui/" + this.id + "/" + type + ".png");
            this.textures.put(type, texture);
        }
        Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
    }

    public static GUIStyle getTypeByID(String id) {
        for (GUIStyle style : GUIStyle.values()) {
            if (!style.id.equals(id)) continue;
            return style;
        }
        return DEFAULT;
    }
}

