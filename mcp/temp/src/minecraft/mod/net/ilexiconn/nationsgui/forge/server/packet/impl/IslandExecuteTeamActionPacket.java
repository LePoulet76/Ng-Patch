package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandExecuteTeamActionPacket implements IPacket, IClientPacket {

   public String islandId;
   public String teamId;
   public String action;


   public IslandExecuteTeamActionPacket(String islandId, String teamId, String action) {
      this.islandId = islandId;
      this.teamId = teamId;
      this.action = action;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.islandId);
      data.writeUTF(this.teamId);
      data.writeUTF(this.action);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
