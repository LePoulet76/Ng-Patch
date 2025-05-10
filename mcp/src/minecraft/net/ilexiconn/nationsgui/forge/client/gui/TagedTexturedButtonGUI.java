package net.ilexiconn.nationsgui.forge.client.gui;

public class TagedTexturedButtonGUI<T extends Object> extends TexturedButtonGUI
{
    private T tag;

    public TagedTexturedButtonGUI(int id, int x, int y, int width, int height, String texture, int u, int v, String text, T tag)
    {
        super(id, x, y, width, height, texture, u, v, text);
        this.tag = tag;
    }

    public T getTag()
    {
        return this.tag;
    }
}
