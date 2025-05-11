/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class FactionEnemyAgreementCreatePacket
implements IPacket,
IClientPacket {
    private Integer warId;
    private String type;
    private int duration;
    private String conditions;

    public FactionEnemyAgreementCreatePacket(Integer warId, String type, int duration, String conditions) {
        this.warId = warId;
        this.type = type;
        this.duration = duration;
        this.conditions = conditions;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.warId.intValue());
        data.writeUTF(this.type);
        data.writeInt(this.duration);
        data.writeUTF(this.conditions);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }
}

