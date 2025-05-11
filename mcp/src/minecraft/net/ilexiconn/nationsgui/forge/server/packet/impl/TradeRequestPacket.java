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
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class TradeRequestPacket
implements IPacket,
IClientPacket {
    private String trader;

    public TradeRequestPacket(String trader) {
        this.trader = trader;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.trader = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.trader);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EntityPlayer trader = player.field_70170_p.func_72924_a(this.trader);
        if (trader != null) {
            trader.func_70006_a(ChatMessageComponent.func_111082_b((String)"trade.request", (Object[])new Object[]{trader.getDisplayName(), trader.getDisplayName()}));
        }
    }
}

