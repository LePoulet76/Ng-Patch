package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionStartAssaultPacket implements IPacket, IClientPacket {

   String factionId;
   String factionTargetId;


   public FactionStartAssaultPacket(String factionId, String factionTargetId) {
      this.factionId = factionId;
      this.factionTargetId = factionTargetId;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionId);
      data.writeUTF(this.factionTargetId);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
