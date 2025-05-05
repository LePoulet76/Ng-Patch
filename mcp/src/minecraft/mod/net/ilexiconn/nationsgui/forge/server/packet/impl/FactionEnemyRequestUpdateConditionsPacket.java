package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyRequestUpdateConditionsPacket implements IPacket, IClientPacket
{
    private Integer requestID;
    private String side;
    private String conditionsType;
    private String conditions;
    private String rewards;

    public FactionEnemyRequestUpdateConditionsPacket(Integer requestID, String side, String conditionsType, String conditions, String rewards)
    {
        this.requestID = requestID;
        this.side = side;
        this.conditionsType = conditionsType;
        this.conditions = conditions;
        this.rewards = rewards;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.requestID.intValue());
        data.writeUTF(this.side);
        data.writeUTF(this.conditionsType);
        data.writeUTF(this.conditions);
        data.writeUTF(this.rewards);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }
}
