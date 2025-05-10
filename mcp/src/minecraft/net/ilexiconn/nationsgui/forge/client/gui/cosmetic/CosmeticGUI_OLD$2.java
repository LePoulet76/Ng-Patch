package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class CosmeticGUI_OLD$2 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return CosmeticGUI_OLD.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new CosmeticGUI_OLD());
    }
}
