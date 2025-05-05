package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerAbsenceRequestUpdateStatusPacket implements IPacket, IClientPacket {

   private String status;
   private String playerName;


   public PlayerAbsenceRequestUpdateStatusPacket(String playerName, String status) {
      this.playerName = playerName;
      this.status = status;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.status);
      data.writeUTF(this.playerName);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
