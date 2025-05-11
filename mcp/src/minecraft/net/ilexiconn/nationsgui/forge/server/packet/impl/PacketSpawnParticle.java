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
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSpawnParticle
implements IPacket,
IClientPacket {
    private String particleName;
    private double x;
    private double y;
    private double z;
    private double xd;
    private double yd;
    private double zd;

    public PacketSpawnParticle(String particleName, double x, double y, double z, double xd, double yd, double zd) {
        this.particleName = particleName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.particleName = data.readUTF();
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.xd = data.readDouble();
        this.yd = data.readDouble();
        this.zd = data.readDouble();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.particleName);
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeDouble(this.xd);
        data.writeDouble(this.yd);
        data.writeDouble(this.zd);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (PacketSpawnParticle.mc.field_71441_e != null) {
            PacketSpawnParticle.mc.field_71441_e.func_72869_a(this.particleName, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        }
    }
}

