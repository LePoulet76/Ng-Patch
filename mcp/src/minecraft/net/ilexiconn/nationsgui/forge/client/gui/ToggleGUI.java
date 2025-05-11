/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ToggleGUI
extends GuiButton {
    private ISliderCallback callback;
    private float sliderOffset = 10.0f;
    private boolean active;

    public ToggleGUI(int x, int y, ISliderCallback callback, boolean active) {
        super(-1, x, y, 27, 14, "");
        this.active = active;
        this.sliderOffset = active ? 0.0f : 10.0f;
        this.callback = callback;
    }

    public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
        mc.func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 384, 21, this.field_73747_a, this.field_73745_b, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.sliderOffset, (float)0.0f, (float)0.0f);
        ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c + 1, this.field_73743_d - 1, 381, 0, 15, 17, 512.0f, 512.0f, false);
        GL11.glPopMatrix();
        GL11.glDisable((int)3042);
        this.sliderOffset = GUIUtils.interpolate(this.sliderOffset, this.active ? 0.0f : 10.0f, 0.15f);
    }

    public boolean func_73736_c(Minecraft mc, int mouseX, int mouseY) {
        if (super.func_73736_c(mc, mouseX, mouseY)) {
            boolean bl = this.active = !this.active;
            if (this.callback != null) {
                this.callback.call(this.active);
            }
            return true;
        }
        return false;
    }

    public static interface ISliderCallback {
        public void call(boolean var1);
    }
}

