/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.particle.DamageParticle;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDamagePacket
implements IPacket,
IClientPacket {
    private int entityID;
    private float ammount;

    public EntityDamagePacket(int entityID, float ammount) {
        this.entityID = entityID;
        this.ammount = ammount;
    }

    public EntityDamagePacket() {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        Entity entity = player.field_70170_p.func_73045_a(this.entityID);
        if (entity != null && ClientProxy.clientConfig.damageIndicator) {
            World world = entity.field_70170_p;
            double motionX = world.field_73012_v.nextGaussian() * 0.02;
            double motionY = 0.5;
            double motionZ = world.field_73012_v.nextGaussian() * 0.02;
            DamageParticle damageIndicator = new DamageParticle(world, entity.field_70165_t, entity.field_70163_u + (double)entity.field_70131_O, entity.field_70161_v, motionX, motionY, motionZ, this.ammount);
            Minecraft.func_71410_x().field_71452_i.func_78873_a((EntityFX)damageIndicator);
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.entityID = data.readInt();
        this.ammount = data.readFloat();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.entityID);
        data.writeFloat(this.ammount);
    }
}

