package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class ClickLimiterPacket implements IPacket, IClientPacket {

   public int limit;


   public void fromBytes(ByteArrayDataInput data) {
      this.limit = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.limit);
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientData.clickLimit = this.limit;
   }
}
