package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMainTPPacket implements IPacket, IClientPacket {

   private String factionName;


   public FactionMainTPPacket(String factionName) {
      this.factionName = factionName;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionName);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
