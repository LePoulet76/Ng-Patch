/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.util;

import java.awt.Font;
import net.halalaboos.cfont.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {
    public static CFontRenderer createFont(String modID, String name) {
        try {
            CFontRenderer fontRenderer = new CFontRenderer(Font.createFont(0, Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation(modID, "fonts/" + name)).func_110527_b()), true, false);
            return fontRenderer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CFontRenderer createClientHookFontDungeons() {
        try {
            CFontRenderer fontRenderer = new CFontRenderer(Font.createFont(0, FontManager.class.getResourceAsStream("/assets/nationsgui/fonts/minecraftDungeons.ttf")), true, false);
            for (char c = ' '; c < '\u007f'; c = (char)(c + '\u0001')) {
                fontRenderer.getStringWidth(String.valueOf(c));
            }
            return fontRenderer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CFontRenderer createClientHookFontGeorama() {
        try {
            CFontRenderer fontRenderer = new CFontRenderer(Font.createFont(0, FontManager.class.getResourceAsStream("/assets/nationsgui/fonts/georamaSemiBold.ttf")), true, false);
            for (char c = ' '; c < '\u007f'; c = (char)(c + '\u0001')) {
                fontRenderer.getStringWidth(String.valueOf(c));
            }
            return fontRenderer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

