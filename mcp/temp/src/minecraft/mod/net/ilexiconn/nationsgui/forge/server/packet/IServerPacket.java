package net.ilexiconn.nationsgui.forge.server.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public interface IServerPacket {

   MinecraftServer sv = MinecraftServer.func_71276_C();


   void handleServerPacket(EntityPlayer var1);

}
