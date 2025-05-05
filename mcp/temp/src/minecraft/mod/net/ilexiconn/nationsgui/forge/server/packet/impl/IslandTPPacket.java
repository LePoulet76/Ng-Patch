package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class IslandTPPacket implements IPacket {

   public String id;
   public String serverNumber;


   public IslandTPPacket(String id, String serverNumber) {
      this.id = id;
      this.serverNumber = serverNumber;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.id);
      data.writeUTF(this.serverNumber);
   }
}
