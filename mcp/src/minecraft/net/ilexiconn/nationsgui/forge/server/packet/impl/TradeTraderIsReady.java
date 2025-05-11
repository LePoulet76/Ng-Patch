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

public class TradeTraderIsReady
implements IPacket,
IClientPacket {
    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!(TradeTraderIsReady.mc.field_71462_r instanceof GuiTrade)) {
            return;
        }
        GuiTrade gui = (GuiTrade)TradeTraderIsReady.mc.field_71462_r;
        gui.traderIsReady = true;
        gui.lastInteraction = System.currentTimeMillis();
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

