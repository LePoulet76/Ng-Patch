package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandSaveTeamPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> teamInfos;


   public IslandSaveTeamPacket(HashMap<String, Object> teamInfos) {
      this.teamInfos = teamInfos;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.teamInfos));
   }

   public void handleClientPacket(EntityPlayer player) {}
}
