package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$5 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return SkillsGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new SkillsGui(Minecraft.getMinecraft().thePlayer.username));
    }
}
