package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class ChatTabSendPMPacket implements IPacket {

   public String target;
   public String content;


   public ChatTabSendPMPacket(String target, String content) {
      this.target = target;
      this.content = content;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.target = data.readUTF();
      this.content = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
      data.writeUTF(this.content);
   }
}
