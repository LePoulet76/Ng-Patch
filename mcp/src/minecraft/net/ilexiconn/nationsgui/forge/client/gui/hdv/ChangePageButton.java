/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ChangePageButton
extends GuiButton {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
    private boolean direction;

    public ChangePageButton(int id, int posX, int posY, boolean direction) {
        super(id, posX, posY, 12, 12, "");
        this.direction = direction;
    }

    public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        if (this.field_73748_h) {
            par1Minecraft.func_110434_K().func_110577_a(BACKGROUND);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            int textureOffset = this.field_73742_g ? (this.field_82253_i ? 24 : 12) : 0;
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 348 + (this.direction ? 12 : 0), textureOffset, 12, 12, 372.0f, 400.0f, false);
            this.func_73739_b(par1Minecraft, par2, par3);
        }
    }
}

