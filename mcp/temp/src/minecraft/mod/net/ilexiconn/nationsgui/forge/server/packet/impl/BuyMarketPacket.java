package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class BuyMarketPacket implements IPacket {

   private String uuid;
   private int quantity;


   public BuyMarketPacket(String uuid, int quantity) {
      this.uuid = uuid;
      this.quantity = quantity;
   }

   public BuyMarketPacket() {}

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.uuid);
      data.writeInt(this.quantity);
   }
}
