package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class SearchGui$5 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return SearchSellCountryGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new SearchSellCountryGui());
    }
}
