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
import net.ilexiconn.nationsgui.forge.client.ClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SetViewPacket
implements IClientPacket,
IPacket {
    private EntityPlayer target;

    public SetViewPacket(EntityPlayer viewer, EntityPlayer target) {
        this.target = target;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        String targetUsername = data.readUTF();
        this.target = ClientHooks.getNearPlayerFromName(targetUsername);
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target.field_71092_bJ);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.target != null) {
            ClientHooks.setEntityViewRenderer(this.target);
        }
    }
}

