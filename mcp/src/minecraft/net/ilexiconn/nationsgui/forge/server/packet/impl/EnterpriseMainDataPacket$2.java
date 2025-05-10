package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseParcelleGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class EnterpriseMainDataPacket$2 implements GuiScreenTab
{
    final EnterpriseMainDataPacket this$0;

    EnterpriseMainDataPacket$2(EnterpriseMainDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public Class <? extends GuiScreen > getClassReferent()
    {
        return EnterpriseParcelleGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new EnterpriseParcelleGUI());
    }
}
