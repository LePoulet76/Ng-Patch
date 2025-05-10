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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDamagePacket implements IPacket, IClientPacket
{
    private int entityID;
    private float ammount;

    public EntityDamagePacket(int entityID, float ammount)
    {
        this.entityID = entityID;
        this.ammount = ammount;
    }

    public EntityDamagePacket() {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(this.entityID);

        if (entity != null && ClientProxy.clientConfig.damageIndicator)
        {
            World world = entity.worldObj;
            double motionX = world.rand.nextGaussian() * 0.02D;
            double motionY = 0.5D;
            double motionZ = world.rand.nextGaussian() * 0.02D;
            DamageParticle damageIndicator = new DamageParticle(world, entity.posX, entity.posY + (double)entity.height, entity.posZ, motionX, motionY, motionZ, this.ammount);
            Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.entityID = data.readInt();
        this.ammount = data.readFloat();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.entityID);
        data.writeFloat(this.ammount);
    }
}
