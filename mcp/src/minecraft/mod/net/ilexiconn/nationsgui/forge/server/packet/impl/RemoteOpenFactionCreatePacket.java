package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenFactionCreatePacket implements IPacket, IClientPacket
{
    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new FactionCreateGUI());
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}
}
