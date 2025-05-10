package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class EnterpriseCreateGui$2 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return EnterpriseListGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new EnterpriseListGui());
    }
}
