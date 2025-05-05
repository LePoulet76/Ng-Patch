package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BadgesGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class FactionBadgeDataPacket implements IPacket, IServerPacket, IClientPacket
{
    private NBTTagCompound badges;
    private String playerName;

    public FactionBadgeDataPacket(String playerName)
    {
        this.playerName = playerName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.playerName = data.readUTF();
        boolean client = data.readByte() == 1;

        if (client)
        {
            try
            {
                this.badges = CompressedStreamTools.read(data);
            }
            catch (IOException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.playerName);
        data.writeByte(this.badges == null ? 0 : 1);

        if (this.badges != null)
        {
            try
            {
                CompressedStreamTools.write(this.badges, data);
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }
        }
    }

    public void handleServerPacket(EntityPlayer player)
    {
        this.badges = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Badges")).getCompoundTag(this.playerName);
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        BadgesGUI.CLIENT_BADGES = this.badges;
        BadgesGUI.loaded = true;
    }
}
