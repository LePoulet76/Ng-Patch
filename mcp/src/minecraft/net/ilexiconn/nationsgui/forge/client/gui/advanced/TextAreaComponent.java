/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.TextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

public class TextAreaComponent
extends AbstractAssistanceComponent {
    private final TextAreaGUI textAreaGUI;
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;

    public TextAreaComponent(int x, int y, int width, int lines) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = 10 * lines + 2;
        this.textAreaGUI = new TextAreaGUI(x + 2, y + 2, width - 10);
        this.textAreaGUI.func_73804_f((width - 4) / 6 * lines);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, this.height);
        this.textAreaGUI.func_73795_f();
    }

    public String getText() {
        return this.textAreaGUI.func_73781_b();
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        this.textAreaGUI.func_73793_a(mouseX, mouseY, clickType);
    }

    @Override
    public void update() {
        this.textAreaGUI.func_73780_a();
    }

    @Override
    public void keyTyped(char c, int key) {
        this.textAreaGUI.func_73802_a(c, key);
        this.container.actionPerformed(this);
    }
}

