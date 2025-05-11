/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class CheckboxComponent
extends AbstractAssistanceComponent {
    private final int posX;
    private final int posY;
    private final String string;
    private final int textColor;
    private boolean checked = false;

    public CheckboxComponent(int posX, int posY, String string) {
        this(posX, posY, string, 0xFFFFFF);
    }

    public CheckboxComponent(int posX, int posY, String string, int textColor) {
        this.posX = posX;
        this.posY = posY;
        this.string = string;
        this.textColor = textColor;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.func_73729_b(this.posX, this.posY, this.checked ? 0 : 10, 237, 10, 10);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.string, this.posX + 12, this.posY, this.textColor);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        if (clickType == 0 && mouseX >= this.posX && mouseX <= this.posX + 12 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.string) && mouseY >= this.posY && mouseY <= this.posY + 10) {
            this.checked = !this.checked;
            this.container.actionPerformed(this);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(char c, int key) {
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

