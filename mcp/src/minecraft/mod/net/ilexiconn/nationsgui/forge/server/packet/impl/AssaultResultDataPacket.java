package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.faction.AssaultResultGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssaultResultDataPacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AssaultResultDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, String> assaultResultInfos = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.assaultResultInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new AssaultResultDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.setCurrentAssault(new HashMap());
        Minecraft.getMinecraft().displayGuiScreen(new AssaultResultGUI(this.assaultResultInfos));
    }
}
