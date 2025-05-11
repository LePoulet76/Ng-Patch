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
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CloseButtonGUI
extends GuiButton {
    public CloseButtonGUI(int id, int x, int y) {
        super(id, x, y, 9, 10, "");
    }

    public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
        this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
        mc.func_110434_K().func_110577_a(ShopGUI.TEXTURE);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        this.func_73729_b(this.field_73746_c, this.field_73743_d, 204, 18 + (this.field_82253_i ? this.field_73745_b : 0), this.field_73747_a, this.field_73745_b);
        GL11.glDisable((int)3042);
    }
}

