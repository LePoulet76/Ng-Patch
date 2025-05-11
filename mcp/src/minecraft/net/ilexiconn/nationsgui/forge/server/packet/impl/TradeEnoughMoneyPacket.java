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
import net.minecraft.entity.player.EntityPlayer;

public class TradeEnoughMoneyPacket
implements IPacket,
IClientPacket {
    private boolean enough;

    public TradeEnoughMoneyPacket(boolean enough) {
        this.enough = enough;
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        GuiTrade gui = (GuiTrade)TradeEnoughMoneyPacket.mc.field_71462_r;
        if (gui == null) {
            return;
        }
        gui.hasEnoughMoney = this.enough;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enough = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeBoolean(this.enough);
    }
}

