package net.ilexiconn.nationsgui.forge.server.packet.impl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.ParrotGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ParrotDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class ParrotDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> data = new HashMap();
    public boolean hasNGPrime = false;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new ParrotDataPacket$1(this)).getType());
        this.hasNGPrime = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        ParrotGui.loaded = true;
        ParrotGui.hasNGPrime = this.hasNGPrime;
        ParrotGui.data = this.data;
    }
}
