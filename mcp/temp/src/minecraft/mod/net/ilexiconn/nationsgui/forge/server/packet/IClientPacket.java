package net.ilexiconn.nationsgui.forge.server.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public interface IClientPacket {

   @SideOnly(Side.CLIENT)
   Minecraft mc = Minecraft.func_71410_x();


   @SideOnly(Side.CLIENT)
   void handleClientPacket(EntityPlayer var1);

}
