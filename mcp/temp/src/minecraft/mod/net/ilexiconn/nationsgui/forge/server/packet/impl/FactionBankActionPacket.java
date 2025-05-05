package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionBankActionPacket implements IPacket, IClientPacket {

   public String bankName;
   public String amount;
   public String action;


   public FactionBankActionPacket(String bankName, String amount, String action) {
      this.bankName = bankName;
      this.amount = amount;
      this.action = action;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.bankName = data.readUTF();
      this.amount = data.readUTF();
      this.action = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.bankName);
      data.writeUTF(this.amount);
      data.writeUTF(this.action);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankDataPacket(this.bankName)));
   }
}
