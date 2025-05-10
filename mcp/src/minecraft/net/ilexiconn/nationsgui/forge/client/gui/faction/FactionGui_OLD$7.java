package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$7 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return CountrySkillsGUI_OLD.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new CountrySkillsGUI_OLD(Minecraft.getMinecraft().thePlayer));
    }
}
