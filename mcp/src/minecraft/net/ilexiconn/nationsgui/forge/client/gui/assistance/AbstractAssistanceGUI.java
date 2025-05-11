/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.AdvancedGui;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.StaffButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractAssistanceGUI
extends AdvancedGui {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("nationsgui", "textures/gui/assistance.png");
    protected static final int GUI_WIDTH = 373;
    protected static final int GUI_HEIGHT = 227;
    public static boolean canBeAdmin = false;
    protected int guiTop;
    protected int guiLeft;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = this.field_73880_f / 2 - 186;
        this.guiTop = this.field_73881_g / 2 - 113;
        if (canBeAdmin) {
            StaffButton staffButton = new StaffButton(this.guiLeft + 336, this.guiTop + 12);
            if (ClientProxy.playersInAdminMode.containsKey(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                staffButton.setActive(ClientProxy.playersInAdminMode.get(Minecraft.func_71410_x().field_71439_g.func_70005_c_()));
            }
            this.addComponent(staffButton);
        }
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.field_73882_e.func_110434_K().func_110577_a(GUI_TEXTURE);
        this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, 373, 227);
        ModernGui.drawScaledString(I18n.func_135053_a((String)"nationsgui.assistance.title"), this.guiLeft + 40, this.guiTop + 13, 0xFFFFFF, 1.5f, false, true);
        this.drawGui(mouseX, mouseY, partialTick);
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    protected abstract void drawGui(int var1, int var2, float var3);

    public void func_73729_b(int posX, int posY, int u, int v, int width, int height) {
        float f = 0.001953125f;
        float f1 = 0.001953125f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)posX, (double)(posY + height), (double)this.field_73735_i, (double)((float)u * f), (double)((float)(v + height) * f1));
        tessellator.func_78374_a((double)(posX + width), (double)(posY + height), (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.func_78374_a((double)(posX + width), (double)posY, (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)v * f1));
        tessellator.func_78374_a((double)posX, (double)posY, (double)this.field_73735_i, (double)((float)u * f), (double)((float)v * f1));
        tessellator.func_78381_a();
    }
}

