package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.LotoAdminGui;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenLotoPacket implements IPacket, IClientPacket {

   private boolean admin;


   public void handleClientPacket(EntityPlayer player) {
      if(this.admin) {
         Minecraft.func_71410_x().func_71373_a(new LotoAdminGui());
      } else {
         Minecraft.func_71410_x().func_71373_a(new LotoGui());
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      this.admin = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {}
}
