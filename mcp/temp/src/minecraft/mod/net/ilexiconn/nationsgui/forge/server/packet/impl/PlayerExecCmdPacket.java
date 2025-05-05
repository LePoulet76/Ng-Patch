package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class PlayerExecCmdPacket implements IPacket {

   public String cmdToExecute = "";
   public int price = 0;


   public PlayerExecCmdPacket(String cmdToExecute, int price) {
      this.cmdToExecute = cmdToExecute;
      this.price = price;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.cmdToExecute);
      data.writeInt(this.price);
   }
}
