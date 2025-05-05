package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSavePermDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> factionUpdatedPermsInfos = new HashMap();


   public FactionSavePermDataPacket(HashMap<String, Object> factionUpdatedPermsInfos) {
      this.factionUpdatedPermsInfos = factionUpdatedPermsInfos;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.factionUpdatedPermsInfos));
   }

   public void handleClientPacket(EntityPlayer player) {}
}
