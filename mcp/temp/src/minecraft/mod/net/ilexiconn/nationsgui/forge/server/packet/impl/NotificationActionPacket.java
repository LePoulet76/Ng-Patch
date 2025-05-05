package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.awt.image.BufferedImage;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class NotificationActionPacket implements IPacket, IClientPacket {

   private String notificationAction;
   private String args;


   public NotificationActionPacket(String notificationAction, String args) {
      this.notificationAction = notificationAction;
      this.args = args;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.notificationAction = data.readUTF();
      this.args = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.notificationAction);
      data.writeUTF(this.args);
   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.notificationAction.equals("screen.uploader.allow") && this.args != null && !this.args.isEmpty()) {
         NationsGUIClientHooks.uploadToForum((BufferedImage)NationsGUIClientHooks.screenMap.remove(this.args));
      }

   }
}
