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
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class UpdateClientRenderCustomColorPacket
implements IPacket,
IClientPacket {
    private int red;
    private int green;
    private int blue;

    public UpdateClientRenderCustomColorPacket(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.red = data.readInt();
        this.green = data.readInt();
        this.blue = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientData.customRenderColorRed = (float)this.red / 255.0f;
        ClientData.customRenderColorGreen = (float)this.green / 255.0f;
        ClientData.customRenderColorBlue = (float)this.blue / 255.0f;
    }
}

