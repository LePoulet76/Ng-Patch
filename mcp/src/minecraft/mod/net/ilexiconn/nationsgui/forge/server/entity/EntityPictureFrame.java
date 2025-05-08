package net.ilexiconn.nationsgui.forge.server.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Stub serveur pour EntityPictureFrame, sans dépendances client.
 */
public class EntityPictureFrame extends Entity {
    public EntityPictureFrame(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {}
}
