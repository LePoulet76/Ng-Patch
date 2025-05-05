package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterprisePlayerDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterprisePlayerDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> playerInfos = new HashMap();
   public String playerName;
   public String enterpriseName;


   public EnterprisePlayerDataPacket(String enterpriseName, String playerName) {
      this.enterpriseName = enterpriseName;
      this.playerName = playerName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.playerName = data.readUTF();
      this.enterpriseName = data.readUTF();
      this.playerInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterprisePlayerDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerName);
      data.writeUTF(this.enterpriseName);
   }

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseGui.playerTooltip.put(this.enterpriseName + "##" + this.playerName, this.playerInfos);
   }
}
