package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FlagRequestPacket implements IPacket, IClientPacket {

   private String countryName;


   public void handleClientPacket(EntityPlayer player) {
      fr.nationsglory.ngcontent.server.packet.impls.FlagRequestPacket flagRequestPacket = new fr.nationsglory.ngcontent.server.packet.impls.FlagRequestPacket(this.countryName);
      flagRequestPacket.handleClientPacket(player);
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.countryName = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.countryName);
   }
}
