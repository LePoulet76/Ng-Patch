/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.main.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class MenuButtonGUI
extends GuiButton {
    public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("nationsgui", "textures/gui/menu.png");
    public float positionX;
    public float positionY;
    public float positionXOrig;
    public float positionYOrog;
    private int textureX;
    private int textureY;
    private int color;
    private boolean continuous;
    private ButtonPositionHandler positionHandler;

    public MenuButtonGUI(int id, int x, int y, int textureX, int textureY) {
        this(id, x, y, 200, 20, textureX, textureY);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY) {
        this(id, x, y, width, height, textureX, textureY, "");
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text) {
        this(id, x, y, width, height, textureX, textureY, text, -1);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color) {
        this(id, x, y, width, height, textureX, textureY, text, color, false);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color, boolean continuous) {
        this(id, x, y, width, height, textureX, textureY, text, color, continuous, ButtonPositionHandler.NONE);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color, boolean continuous, ButtonPositionHandler positionHandler) {
        super(id, 0, 0, width, height, text);
        this.positionX = this.positionXOrig = (float)x;
        this.positionY = this.positionYOrog = (float)y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.color = color;
        this.continuous = continuous;
        this.positionHandler = positionHandler;
    }

    public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_73748_h) {
            boolean hovered;
            mc.func_110434_K().func_110577_a(BUTTON_TEXTURE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = hovered = (float)mouseX >= this.positionX && (float)mouseY >= this.positionY && (float)mouseX < this.positionX + (float)this.field_73747_a && (float)mouseY < this.positionY + (float)this.field_73745_b;
            if (hovered) {
                this.positionHandler.handleTransform(this, ButtonPositionHandler.State.HOVER);
            } else {
                this.positionHandler.handleTransform(this, ButtonPositionHandler.State.NORMAL);
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            if (!this.continuous) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.positionX, this.positionY, this.textureX, this.textureY + (hovered ? this.field_73745_b : 0), this.field_73747_a, this.field_73745_b, 256.0f, 256.0f, false);
            } else {
                GUIUtils.drawContinuousTexturedBox(BUTTON_TEXTURE, this.positionX, this.positionY, this.textureX, this.textureY + (hovered ? this.field_73745_b : 0), this.field_73747_a, this.field_73745_b, 200, 20, 2, 3, 2, 2, this.field_73735_i);
            }
            GL11.glTranslatef((float)this.positionX, (float)this.positionY, (float)0.0f);
            switch (this.positionHandler) {
                case LEFT: {
                    this.func_73731_b(mc.field_71466_p, this.field_73744_e, this.field_73747_a - mc.field_71466_p.func_78256_a(this.field_73744_e) - 6, (this.field_73745_b - 8) / 2, this.color);
                    break;
                }
                case RIGHT: {
                    this.func_73731_b(mc.field_71466_p, this.field_73744_e, 6, (this.field_73745_b - 8) / 2, this.color);
                    break;
                }
                case NONE: {
                    this.func_73732_a(mc.field_71466_p, this.field_73744_e, this.field_73747_a / 2, (this.field_73745_b - 8) / 2, this.color);
                }
            }
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
    }

    public boolean func_73736_c(Minecraft mc, int mouseX, int mouseY) {
        return this.field_73742_g && this.field_73748_h && (float)mouseX >= this.positionX && (float)mouseY >= this.positionY && (float)mouseX < this.positionX + (float)this.field_73747_a && (float)mouseY < this.positionY + (float)this.field_73745_b;
    }
}

