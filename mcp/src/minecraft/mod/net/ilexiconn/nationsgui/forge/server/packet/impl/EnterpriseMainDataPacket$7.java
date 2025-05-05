package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterprisePetrolGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class EnterpriseMainDataPacket$7 implements GuiScreenTab
{
    final EnterpriseMainDataPacket this$0;

    EnterpriseMainDataPacket$7(EnterpriseMainDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public Class <? extends GuiScreen > getClassReferent()
    {
        return EnterprisePetrolGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new EnterprisePetrolGUI());
    }
}
