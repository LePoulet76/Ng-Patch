/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  fr.nationsglory.ngcontent.server.packet.impls.FlagRequestPacket
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FlagRequestPacket
implements IPacket,
IClientPacket {
    private String countryName;

    @Override
    public void handleClientPacket(EntityPlayer player) {
        fr.nationsglory.ngcontent.server.packet.impls.FlagRequestPacket flagRequestPacket = new fr.nationsglory.ngcontent.server.packet.impls.FlagRequestPacket(this.countryName);
        flagRequestPacket.handleClientPacket(player);
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.countryName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.countryName);
    }
}

