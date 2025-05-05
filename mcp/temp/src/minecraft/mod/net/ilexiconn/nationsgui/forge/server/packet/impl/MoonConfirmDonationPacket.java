package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class MoonConfirmDonationPacket implements IPacket {

   private double value;


   public MoonConfirmDonationPacket(double d) {
      this.value = d;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.value = data.readDouble();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeDouble(this.value);
   }
}
