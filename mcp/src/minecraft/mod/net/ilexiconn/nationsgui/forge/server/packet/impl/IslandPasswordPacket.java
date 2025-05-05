package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPasswordGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class IslandPasswordPacket implements IPacket, IClientPacket
{
    public String id;
    public String password;
    public String passwordValue;
    public String serverNumber;
    public boolean response;

    public IslandPasswordPacket(String id, String password, String passwordValue, String serverNumber)
    {
        this.id = id;
        this.password = password;
        this.passwordValue = passwordValue;
        this.serverNumber = serverNumber;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.id = data.readUTF();
        this.response = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.id);
        data.writeUTF(this.password);
        data.writeUTF(this.passwordValue);
        data.writeUTF(this.serverNumber);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.response)
        {
            IslandPasswordGui.hasError = true;
        }
        else
        {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTPPacket(this.id, this.serverNumber)));
        }
    }
}
