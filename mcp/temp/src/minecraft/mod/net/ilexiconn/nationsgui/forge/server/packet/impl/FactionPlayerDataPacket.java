package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlayerDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPlayerDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> playerInfos = new HashMap();
   public String playerName;


   public FactionPlayerDataPacket(String targetName) {
      this.playerName = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.playerInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionPlayerDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerName);
   }

   public void handleClientPacket(EntityPlayer player) {
      FactionGUI.playerTooltip.put((String)this.playerInfos.get("name"), this.playerInfos);
   }
}
