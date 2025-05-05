package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.minecraft.entity.player.EntityPlayer;

enum PacketCallbacks$1
{
    public void handleCallback(EntityPlayer player, ByteArrayDataInput data)
    {
        PermissionCache.INSTANCE.addPermission(data.readUTF(), Boolean.valueOf(data.readBoolean()));
    }
}
