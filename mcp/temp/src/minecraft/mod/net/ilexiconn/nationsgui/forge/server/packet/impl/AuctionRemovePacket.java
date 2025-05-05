package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class AuctionRemovePacket implements IPacket {

   private String uuid;


   public AuctionRemovePacket(String uuid) {
      this.uuid = uuid;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.uuid);
   }
}
