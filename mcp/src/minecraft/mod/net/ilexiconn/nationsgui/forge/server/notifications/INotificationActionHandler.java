package net.ilexiconn.nationsgui.forge.server.notifications;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface INotificationActionHandler
{
    void handleAction(EntityPlayer var1, NBTTagCompound var2);
}
