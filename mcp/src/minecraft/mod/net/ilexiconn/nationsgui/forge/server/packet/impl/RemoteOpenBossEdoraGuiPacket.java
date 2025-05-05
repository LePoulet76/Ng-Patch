package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.BossEdoraGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenBossEdoraGuiPacket implements IClientPacket, IPacket
{
    private boolean forceBoss;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.forceBoss = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new BossEdoraGui(this.forceBoss));
    }
}
