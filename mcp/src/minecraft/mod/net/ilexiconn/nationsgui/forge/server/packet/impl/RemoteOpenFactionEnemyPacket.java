package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.faction.EnemyGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenFactionEnemyPacket implements IPacket, IClientPacket
{
    private String factionATT;
    private String factionDEF;

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new EnemyGui(this.factionATT, this.factionDEF));
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.factionATT = data.readUTF();
        this.factionDEF = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}
}
