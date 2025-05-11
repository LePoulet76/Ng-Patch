/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class TradeIgnorePacket
implements IPacket,
IServerPacket {
    @Override
    public void handleServerPacket(EntityPlayer player) {
        TradeData data = TradeData.get(player);
        data.ignored.put(data.tradePlayer.func_70005_c_().toLowerCase(), System.currentTimeMillis());
        player.func_70006_a(ChatMessageComponent.func_111082_b((String)"trade.ignored", (Object[])new Object[]{data.tradePlayer.func_70005_c_()}));
        data.tradePlayer.func_70006_a(ChatMessageComponent.func_111082_b((String)"trade.rejected", (Object[])new Object[]{player.func_70005_c_()}));
        data.closeTrade();
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

