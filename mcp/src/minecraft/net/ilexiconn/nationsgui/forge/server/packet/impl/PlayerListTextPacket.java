package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListTextPacket implements IPacket, IClientPacket
{
    public String up;
    public String bottom;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.up = data.readUTF();
        this.bottom = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.up);
        data.writeUTF(this.bottom);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PlayerListGUI.topText = this.up.replace("%player%", Minecraft.getMinecraft().getSession().getUsername());
        PlayerListGUI.bottomText = this.bottom.replace("%player%", Minecraft.getMinecraft().getSession().getUsername());
    }
}
