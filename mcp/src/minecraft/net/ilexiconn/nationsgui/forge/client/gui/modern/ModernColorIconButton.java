/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModernColorIconButton
extends ModernButton {
    private int backgroundColor;

    public ModernColorIconButton(int id, int posX, int posY, int width, int height, String text, int color, ResourceLocation icon, int backgroundColor) {
        super(id, posX, posY, width, height, text, color, icon);
        this.backgroundColor = backgroundColor;
    }

    public ModernColorIconButton(int id, int posX, int posY, String text, int color, ResourceLocation icon, int backgroundColor) {
        super(id, posX, posY, text, color, icon);
        this.backgroundColor = backgroundColor;
    }

    public ModernColorIconButton(int id, int posX, int posY, int color, ResourceLocation location, int backgroundColor) {
        super(id, posX, posY, color, location);
        this.backgroundColor = backgroundColor;
    }

    @Override
    protected void drawText(Minecraft minecraft, float w, float h) {
        cFontRenderer.drawString(this.field_73744_e, (float)this.field_73746_c + 4.0f + 7.0f + 3.0f, (float)this.field_73743_d + (float)this.field_73745_b / 2.0f - h / 2.0f, 0xFFFFFF);
    }

    @Override
    protected void drawIcon(Minecraft minecraft, float w, float h) {
        float r = (float)(this.backgroundColor >> 24 & 0xFF) / 255.0f;
        float g = (float)(this.backgroundColor >> 16 & 0xFF) / 255.0f;
        float b = (float)(this.backgroundColor >> 8 & 0xFF) / 255.0f;
        GL11.glColor3f((float)r, (float)g, (float)b);
        ModernGui.drawRoundedRectangle((float)this.field_73746_c + 3.0f, (float)this.field_73743_d + (float)this.field_73745_b / 2.0f - 4.5f, this.field_73735_i, 9.0f, 9.0f);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.drawIconInSquare(minecraft, w, g);
    }

    protected void drawIconInSquare(Minecraft minecraft, float w, float h) {
        minecraft.func_110434_K().func_110577_a(this.resourceIcon);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c + 4.0f, (float)this.field_73743_d + (float)this.field_73745_b / 2.0f - 3.5f, 0, 0, 7, 7, 7.0f, 7.0f, false);
    }
}

