package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class WildernessGui$1 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return CreateGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new CreateGui());
    }
}
