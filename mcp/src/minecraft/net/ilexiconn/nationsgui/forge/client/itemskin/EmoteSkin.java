/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class EmoteSkin
extends AbstractSkin {
    public EmoteSkin(JSONObject object) {
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
        ResourceLocation rl = new ResourceLocation("nationsgui", "emotes/icons/" + this.getId() + ".png");
        if (EmoteSkin.class.getResourceAsStream("/assets/" + rl.func_110624_b() + "/" + rl.func_110623_a()) == null) {
            rl = new ResourceLocation("nationsgui", "emotes/icons/custom.png");
        }
        Minecraft.func_71410_x().func_110434_K().func_110577_a(rl);
        ModernGui.drawModalRectWithCustomSizedTexture(5.0f, 4.0f, 0, 0, 16, 16, 16.0f, 16.0f, false);
    }

    private boolean iconExist(ResourceLocation rl) {
        return EmoteSkin.class.getResourceAsStream("/assets/" + rl.func_110624_b() + "/" + rl.func_110623_a()) != null;
    }
}

