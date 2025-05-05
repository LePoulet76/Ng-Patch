package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class DialogExecPacket implements IPacket {

   private String dialogName;


   public DialogExecPacket(String dialogName) {
      this.dialogName = dialogName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.dialogName = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.dialogName);
   }
}
