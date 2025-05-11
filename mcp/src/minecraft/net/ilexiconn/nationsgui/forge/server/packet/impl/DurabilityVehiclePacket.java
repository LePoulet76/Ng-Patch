/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  fr.nationsglory.ngvehicles.common.entity.vehicles.EntityPoweredVehicle
 *  micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityPoweredVehicle;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class DurabilityVehiclePacket
implements IPacket,
IServerPacket,
IClientPacket {
    private int entityID;
    private int durability;
    private boolean addition;

    public DurabilityVehiclePacket(int entityID, int durability, boolean addition) {
        this.entityID = entityID;
        this.durability = durability;
        this.addition = addition;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.entityID = data.readInt();
        this.durability = data.readInt();
        this.addition = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.entityID);
        data.writeInt(this.durability);
        data.writeBoolean(this.addition);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        Entity entity = player.field_70170_p.func_73045_a(this.entityID);
        if (entity instanceof EntityPoweredVehicle) {
            ((EntityPoweredVehicle)entity).setDurability(((EntityPoweredVehicle)entity).getDurability() + this.durability);
        } else if (entity instanceof EntityAutoRocket) {
            ((EntityAutoRocket)entity).setDurability(((EntityAutoRocket)entity).getDurability() + this.durability);
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
    }
}

