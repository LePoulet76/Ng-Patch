/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  fr.nationsglory.ngcontent.client.render.block.IslandsPlateEntityRenderer
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngcontent.client.render.block.IslandsPlateEntityRenderer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandsTogglePlatesPacket
implements IPacket,
IClientPacket {
    public boolean can;

    public IslandsTogglePlatesPacket(boolean can) {
        this.can = can;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.can = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeBoolean(this.can);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        IslandsPlateEntityRenderer.renderHolo = this.can;
    }
}

