package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class NotificationPacket implements IPacket, IClientPacket {

   private NBTTagCompound notification;


   public NotificationPacket(NBTTagCompound notification) {
      this.notification = notification;
   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         this.notification = CompressedStreamTools.func_74794_a(data);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      try {
         CompressedStreamTools.func_74800_a(this.notification, data);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      ClientData.notifications.add(new Notification(this.notification));
   }
}
