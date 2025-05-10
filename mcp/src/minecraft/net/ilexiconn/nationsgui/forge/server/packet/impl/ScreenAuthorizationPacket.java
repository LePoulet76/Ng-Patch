package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceNewTicketGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ScreenAuthorizationPacket implements IPacket, IClientPacket
{
    private String token;
    private String location;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.token = data.readUTF();
        this.location = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (Minecraft.getMinecraft().currentScreen instanceof AssistanceNewTicketGUI)
        {
            AssistanceNewTicketGUI gui = (AssistanceNewTicketGUI)Minecraft.getMinecraft().currentScreen;
            gui.screenUrl = this.location;
            gui.screenToken = this.token;
        }
    }
}
