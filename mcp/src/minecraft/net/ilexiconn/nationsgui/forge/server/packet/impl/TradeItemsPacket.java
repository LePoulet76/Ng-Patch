/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class TradeItemsPacket
implements IPacket,
IClientPacket {
    private NBTTagCompound comp;

    public TradeItemsPacket(NBTTagCompound comp, String username) {
        this.comp = comp;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.comp = CompressedStreamTools.func_74794_a((DataInput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        try {
            CompressedStreamTools.func_74800_a((NBTTagCompound)this.comp, (DataOutput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!(TradeItemsPacket.mc.field_71462_r instanceof GuiTrade)) {
            return;
        }
        GuiTrade gui = (GuiTrade)TradeItemsPacket.mc.field_71462_r;
        gui.items = ContainerTrade.CompToItem(this.comp);
    }
}

