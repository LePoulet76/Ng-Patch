package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$1 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return FactionGui_OLD.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new FactionGui_OLD((String)FactionGui_OLD.factionInfos.get("name")));
    }
}
