/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.SuffixedNumberField;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class NumberSelector
extends Gui {
    private SuffixedNumberField textField;
    private Minecraft mc;
    private int posX;
    private int posY;
    private int width;
    private int max = -1;

    public NumberSelector(Minecraft mc, int x, int y, int width) {
        this(mc, x, y, width, "");
    }

    public NumberSelector(Minecraft mc, int x, int y, int width, String suffix) {
        this(mc, x, y, width, 18, suffix);
    }

    public NumberSelector(Minecraft mc, int x, int y, int width, int height, String suffix) {
        this.mc = mc;
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.textField = new SuffixedNumberField(mc.field_71466_p, x, y + 1, width - 24, height, suffix);
    }

    public void setText(String text) {
        this.textField.setText(text);
    }

    public String getText() {
        return this.textField.getText();
    }

    public void draw(int mouseX, int mouseY) {
        this.textField.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("auction_sell");
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.drawButton(this.posX + this.width - 23, this.posY, false, this.upActivated(), mouseX, mouseY);
        this.drawButton(this.posX + this.width - 23, this.posY + 10, true, this.downActivated(), mouseX, mouseY);
    }

    public void update() {
        this.textField.updateCursorCounter();
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        this.clickButton(this.posX + this.width - 23, this.posY, mouseX, mouseY, 1);
        this.clickButton(this.posX + this.width - 23, this.posY + 10, mouseX, mouseY, -1);
        this.textField.mouseClicked(mouseX, mouseY, button);
    }

    public void keyTyped(char character, int key) {
        this.textField.textboxKeyTyped(character, key);
    }

    private boolean upActivated() {
        try {
            if (this.max != -1) {
                return Integer.parseInt(this.textField.getText()) < this.max;
            }
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean downActivated() {
        try {
            return Integer.parseInt(this.textField.getText()) > 0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void clickButton(int x, int y, int mouseX, int mouseY, int inc) {
        try {
            if (mouseX >= x && mouseX <= x + 22 && mouseY >= y && mouseY <= y + 10) {
                if (Keyboard.isKeyDown((int)42)) {
                    if (inc > 0 && this.max != -1) {
                        this.textField.setText(Integer.toString(this.max));
                    } else if (inc < 0) {
                        this.textField.setText("0");
                    }
                    return;
                }
                int i = Integer.parseInt(this.textField.getText()) + inc;
                if (i < 0) {
                    i = 0;
                } else if (this.max != -1 && i > this.max) {
                    i = this.max;
                }
                this.textField.setText(i + "");
            }
        }
        catch (NumberFormatException e) {
            this.textField.setText("0");
        }
    }

    private void drawButton(int x, int y, boolean flipped, boolean activated, int mouseX, int mouseY) {
        int pos;
        int n = pos = activated ? 128 : 118;
        if (activated && mouseX >= x && mouseX <= x + 22 && mouseY >= y && mouseY <= y + 10) {
            pos = 138;
        }
        ModernGui.drawModalRectWithCustomSizedTexture(x, y, flipped ? 24 : 3, pos, 21, 10, 275.0f, 256.0f, false);
    }

    public void setMax(int max) {
        this.max = max;
        this.textField.setMax(max);
    }

    public int getMax() {
        return this.max;
    }

    public int getNumber() {
        try {
            return Integer.parseInt(this.textField.getText());
        }
        catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }
}

