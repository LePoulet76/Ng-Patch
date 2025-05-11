/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;

public class TradeStatePacket
implements IPacket,
IClientPacket {
    private String trader;
    private int state;

    public TradeStatePacket(String trader, int state) {
        this.trader = trader;
        this.state = state;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.trader = data.readUTF();
        this.state = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.trader);
        data.writeInt(this.state);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EntityPlayer trader = player.field_70170_p.func_72924_a(this.trader);
        if (trader == null || !(TradeStatePacket.mc.field_71462_r instanceof GuiTrade)) {
            return;
        }
        GuiTrade gui = (GuiTrade)TradeStatePacket.mc.field_71462_r;
        if (gui.trader == null) {
            gui.trader = trader;
        }
        gui.updateState(EnumTradeState.values()[this.state]);
    }
}

