package net.ilexiconn.nationsgui.forge.client.gui.advanced;

public interface GuiComponent
{
    void init(ComponentContainer var1);

    void draw(int var1, int var2, float var3);

    void onClick(int var1, int var2, int var3);

    void update();

    void keyTyped(char var1, int var2);

    boolean isPriorityClick();
}
