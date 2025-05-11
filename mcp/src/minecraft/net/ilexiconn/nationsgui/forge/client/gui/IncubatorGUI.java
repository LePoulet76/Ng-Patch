/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.block.entity.IncubatorBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.IncubatorContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class IncubatorGUI
extends GuiContainer {
    private static final ResourceLocation INCUBATOR_GUI = new ResourceLocation("nationsgui", "textures/gui/incubator.png");
    private IncubatorBlockEntity blockEntity;

    public IncubatorGUI(IncubatorContainer container) {
        super((Container)container);
        this.blockEntity = container.getBlockEntity();
    }

    protected void func_74189_g(int par1, int par2) {
        String name = this.blockEntity.func_94042_c() ? this.blockEntity.func_70303_b() : I18n.func_135053_a((String)this.blockEntity.func_70303_b());
        this.field_73886_k.func_78276_b(name, this.field_74194_b / 2 - this.field_73886_k.func_78256_a(name) / 2, 6, 0x404040);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"container.inventory"), 8, this.field_74195_c - 96 + 2, 0x404040);
    }

    protected void func_74185_a(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.field_73882_e.func_110434_K().func_110577_a(INCUBATOR_GUI);
        int x = (this.field_73880_f - this.field_74194_b) / 2;
        int y = (this.field_73881_g - this.field_74195_c) / 2;
        this.func_73729_b(x, y, 0, 0, this.field_74194_b, this.field_74195_c);
        if (this.blockEntity.isActive()) {
            this.func_73729_b(x + 135, y + 22, 176, 0, 14, 14);
            this.func_73729_b(x + 52, y + 64, 176, 14, this.blockEntity.getProgress(), 5);
        }
    }
}

