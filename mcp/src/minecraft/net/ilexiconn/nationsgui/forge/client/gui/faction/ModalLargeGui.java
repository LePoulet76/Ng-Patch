/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ModalLargeGui
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 217;
    public int guiLeft;
    public int guiTop;
    public GuiScreen guiFrom;

    public ModalLargeGui(GuiScreen guiFrom) {
        this.guiFrom = guiFrom;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_modal_large");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        super.func_73863_a(mouseX, mouseY, par3);
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 227, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 217, 9, 10, 512.0f, 512.0f, false);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(this.guiFrom);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean func_73868_f() {
        return false;
    }
}

