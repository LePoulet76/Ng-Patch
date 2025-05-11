/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class GuiScroller
extends Gui
implements GuiComponent,
ComponentContainer {
    private int posX;
    private int posY;
    private final int width;
    private final int height;
    private GuiScrollBar scrollBar = null;
    private final List<GuiScrollerElement> elementList = new ArrayList<GuiScrollerElement>();
    private ComponentContainer container;

    public GuiScroller(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.scrollBar = new Scrollbar(posX + this.width - 5, posY + 3, this.height - 6);
        for (GuiScrollerElement element : this.elementList) {
            element.init(this);
        }
    }

    public void clearElement() {
        this.elementList.clear();
    }

    public void addElement(GuiScrollerElement element) {
        this.elementList.add(element);
    }

    private int getTotalHeight() {
        int height = 0;
        for (GuiScrollerElement element : this.elementList) {
            height += element.getHeight();
        }
        return height;
    }

    private int getRelativeMouseY(int mouseY) {
        return this.getRelativeMouseY(mouseY, this.getTotalHeight());
    }

    private int getRelativeMouseY(int mouseY, int totalHeight) {
        return (int)((float)(mouseY - this.posY) + this.scrollBar.getSliderValue() * (float)Math.max(totalHeight - this.height + 3, 0));
    }

    private int getRelativeMouseX(int mouseX) {
        return mouseX - this.posX;
    }

    protected void drawBackground(int mouseX, int mouseY, float partialTick) {
        ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, this.height);
        GuiScroller.func_73734_a((int)(this.posX + this.width - 5), (int)(this.posY + 3), (int)(this.posX + this.width - 3), (int)(this.posY + this.height - 3), (int)-16777216);
    }

    @Override
    public void init(ComponentContainer container) {
        this.container = container;
        for (GuiScrollerElement element : this.elementList) {
            element.init(container);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTick) {
        int totalHeight = this.getTotalHeight();
        int relativeX = this.getRelativeMouseX(mouseX);
        int relativeY = this.getRelativeMouseY(mouseY, totalHeight);
        this.drawBackground(mouseX, mouseY, partialTick);
        GUIUtils.startGLScissor(this.posX, this.posY, this.width, this.height);
        int offset = (int)(-(this.scrollBar.getSliderValue() * (float)Math.max(totalHeight - this.height + 6, 0)));
        for (GuiScrollerElement element : this.elementList) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.posX + 4), (float)(this.posY + offset + 3), (float)0.0f);
            element.draw(relativeX, relativeY, partialTick);
            offset += element.getHeight();
            GL11.glPopMatrix();
        }
        GUIUtils.endGLScissor();
        boolean hovered = mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
        this.scrollBar.draw(mouseX, mouseY, hovered);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        int relativeX = this.getRelativeMouseX(mouseX);
        int relativeY = this.getRelativeMouseY(mouseY);
        int totalHeight = 0;
        for (GuiScrollerElement element : this.elementList) {
            element.onClick(relativeX, relativeY - totalHeight, clickType);
            totalHeight += element.getHeight();
        }
    }

    @Override
    public void update() {
        for (GuiScrollerElement element : this.elementList) {
            element.update();
        }
    }

    @Override
    public void keyTyped(char c, int key) {
        for (GuiScrollerElement element : this.elementList) {
            element.keyTyped(c, key);
        }
    }

    @Override
    public boolean isPriorityClick() {
        return false;
    }

    public int getWorkWidth() {
        return this.width - 12;
    }

    @Override
    public void actionPerformed(GuiComponent guiComponent) {
        this.container.actionPerformed(guiComponent);
    }

    public float getValue() {
        return this.scrollBar.sliderValue;
    }

    public float setValue(float value) {
        this.scrollBar.sliderValue = Math.max(0.0f, Math.min(value, 1.0f));
        return this.scrollBar.sliderValue;
    }

    private static class Scrollbar
    extends GuiScrollBar {
        public Scrollbar(float x, float y, int height) {
            super(x, y, height);
        }

        @Override
        protected void drawScroller() {
            Scrollbar.func_73734_a((int)((int)this.x), (int)((int)(this.y + (float)(this.height - 6) * this.sliderValue)), (int)((int)this.x + 2), (int)((int)(this.y + (float)(this.height - 6) * this.sliderValue) + 6), (int)-10592674);
        }
    }
}

