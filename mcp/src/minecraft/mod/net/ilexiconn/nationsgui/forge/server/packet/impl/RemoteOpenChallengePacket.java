package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.ChallengeGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenChallengePacket implements IPacket, IClientPacket
{
    private String playerAtt;
    private String playerDef;
    private boolean isSetup;
    private String duelInfos;

    public RemoteOpenChallengePacket(String playerAtt, String playerDef, boolean isSetup, String duelInfos)
    {
        this.playerAtt = playerAtt;
        this.playerDef = playerDef;
        this.isSetup = isSetup;
        this.duelInfos = duelInfos;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new ChallengeGui(this.playerAtt, this.playerDef, this.isSetup, this.duelInfos));
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.playerAtt = data.readUTF();
        this.playerDef = data.readUTF();
        this.isSetup = data.readBoolean();
        this.duelInfos = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}
}
