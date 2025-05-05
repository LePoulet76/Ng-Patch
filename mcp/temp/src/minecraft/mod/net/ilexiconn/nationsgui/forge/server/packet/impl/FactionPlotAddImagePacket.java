package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class FactionPlotAddImagePacket implements IPacket {

   public String imageLink;
   private int plotId;


   public FactionPlotAddImagePacket(int plotId, String imageLink) {
      this.plotId = plotId;
      this.imageLink = imageLink;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.plotId = data.readInt();
      this.imageLink = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.plotId);
      data.writeUTF(this.imageLink);
   }
}
