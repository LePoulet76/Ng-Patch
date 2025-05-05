package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSaveFlagDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSaveFlagDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> flagData = new HashMap();

    public FactionSaveFlagDataPacket(HashMap<String, Object> flagData)
    {
        this.flagData = flagData;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.flagData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSaveFlagDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.flagData));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.clearCacheCountryFLag((String)this.flagData.get("factionName"));
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)this.flagData.get("factionName"), true)));
    }
}
