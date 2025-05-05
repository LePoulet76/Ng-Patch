package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class PreSellStatsPacket implements IPacket {

   private int slotID;


   public PreSellStatsPacket(int slotID) {
      this.slotID = slotID;
   }

   public PreSellStatsPacket() {}

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.slotID);
   }
}
