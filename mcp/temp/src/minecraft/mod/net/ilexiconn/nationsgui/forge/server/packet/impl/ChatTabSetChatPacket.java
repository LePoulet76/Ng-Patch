package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class ChatTabSetChatPacket implements IPacket {

   public String channel;


   public ChatTabSetChatPacket(String targetName) {
      this.channel = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.channel = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.channel);
   }
}
