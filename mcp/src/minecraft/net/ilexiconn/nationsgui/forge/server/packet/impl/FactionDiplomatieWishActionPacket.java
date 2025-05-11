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
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class FactionDiplomatieWishActionPacket
implements IPacket,
IClientPacket {
    public String factionFrom;
    public String targetName;
    public String action;
    public String relationType;

    public FactionDiplomatieWishActionPacket(String factionFrom, String targetName, String action, String relationType) {
        this.factionFrom = factionFrom;
        this.targetName = targetName;
        this.action = action;
        this.relationType = relationType;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.factionFrom = data.readUTF();
        this.targetName = data.readUTF();
        this.action = data.readUTF();
        this.relationType = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.factionFrom);
        data.writeUTF(this.targetName);
        data.writeUTF(this.action);
        data.writeUTF(this.relationType);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }
}

