/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.AxisAlignedBB
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketEmote;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;

public class StartAnimationPacket
implements IPacket,
IServerPacket {
    private String emote;
    private boolean playerOnly = false;

    public StartAnimationPacket(String emote, boolean playerOnly) {
        this.emote = emote;
        this.playerOnly = playerOnly;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.emote = data.readUTF();
        this.playerOnly = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.emote);
        data.writeBoolean(this.playerOnly);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        if (this.emote == null || this.emote.isEmpty()) {
            return;
        }
        if (this.playerOnly) {
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(this.emote, player.field_71092_bJ)), (Player)((Player)player));
            return;
        }
        for (Object e : player.field_70170_p.func_72872_a(player.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player.field_70165_t - 50.0, player.field_70163_u - 50.0, player.field_70161_v - 50.0, player.field_70165_t + 50.0, player.field_70163_u + 50.0, player.field_70161_v + 50.0))) {
            if (!(e instanceof Player)) continue;
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(this.emote, player.field_71092_bJ)), (Player)((Player)e));
        }
    }
}

