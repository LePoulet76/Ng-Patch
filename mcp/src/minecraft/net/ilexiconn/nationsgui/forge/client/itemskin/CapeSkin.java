/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class CapeSkin
extends AbstractSkin {
    public CapeSkin(JSONObject object) {
        super(object);
    }

    @Override
    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform) {
        forcedRenderSkin = this;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)50.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        this.render(partialTick);
        GL11.glPopMatrix();
        forcedRenderSkin = null;
    }

    @Override
    protected void render(float partialTick) {
        try {
            ResourceLocation resourceLocation = ClientProxy.getCachedCape(this.getId());
            if (resourceLocation != null) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.getCachedCape(this.getId()));
                ModernGui.drawScaledCustomSizeModalRect(5.5f, 3.0f, 0.0f, 0.0f, 12, 17, 14, 20, 64.0f, 32.0f, false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTextureURL() {
        return "https://apiv2.nationsglory.fr/json/capes/capes/" + this.getId() + ".png";
    }
}

