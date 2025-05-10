package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiTextMultiLines extends Gui implements GuiScrollerElement
{
    private int lineMargin = 1;
    private final List<String> lines = new ArrayList();
    private int posX;
    private int posY;
    private int color = 16777215;
    private boolean centered = false;
    private float textSize = 1.0F;
    private int width = 0;

    public GuiTextMultiLines(String text, int width, boolean centered, float textSize)
    {
        this.centered = centered;
        this.textSize = textSize;
        this.width = width;
        StringBuilder sub = new StringBuilder();
        String[] words = text.split(" ");
        String[] var7 = words;
        int var8 = words.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            String word = var7[var9];
            String temp = (!Objects.equals(words[0], word) ? " " : "") + word;

            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(sub.toString()) + Minecraft.getMinecraft().fontRenderer.getStringWidth(temp) <= width)
            {
                sub.append(temp);
            }
            else
            {
                this.lines.add(sub.toString());
                sub = new StringBuilder(word);
            }
        }

        if (!sub.toString().equals(""))
        {
            this.lines.add(sub.toString());
        }
    }

    public void setPosition(int x, int y)
    {
        this.posX = x;
        this.posY = y;
    }

    public void init(GuiScroller scroller) {}

    public int getHeight()
    {
        return this.lines.size() * this.getLineHeight();
    }

    public void init(ComponentContainer container) {}

    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        for (int i = 0; i < this.lines.size(); ++i)
        {
            String line = (String)this.lines.get(i);
            ModernGui.drawScaledString(line, this.centered ? this.posX + this.width / 2 : this.posX, this.posY + this.getLineHeight() * i, this.color, this.textSize, this.centered, false);
        }
    }

    public void onClick(int mouseX, int mouseY, int clickType) {}

    public void update() {}

    public void keyTyped(char c, int key) {}

    public boolean isPriorityClick()
    {
        return false;
    }

    private int getLineHeight()
    {
        return 10 + this.lineMargin;
    }

    public void setLineMargin(int lineMargin)
    {
        this.lineMargin = lineMargin;
    }

    public void setColor(int color)
    {
        this.color = color;
    }
}
