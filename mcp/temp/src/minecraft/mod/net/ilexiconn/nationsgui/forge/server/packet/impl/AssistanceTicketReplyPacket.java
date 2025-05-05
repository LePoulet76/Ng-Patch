package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class AssistanceTicketReplyPacket implements IPacket {

   private int id;
   private String string;


   public AssistanceTicketReplyPacket(int id, String string) {
      this.id = id;
      this.string = string;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.id);
      data.writeUTF(this.string);
   }
}
