package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.gui.ChallengeGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class ChallengeDataPacket implements IPacket, IClientPacket {

   public ArrayList<String> kits = new ArrayList();
   private String playerAtt;
   private String playerDef;
   private String playerAttStats;
   private String playerDefStats;


   public ChallengeDataPacket(String playerAtt, String playerDef) {
      this.playerAtt = playerAtt;
      this.playerDef = playerDef;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.kits = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new ChallengeDataPacket$1(this)).getType());
      this.playerAttStats = data.readUTF();
      this.playerDefStats = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerAtt);
      data.writeUTF(this.playerDef);
   }

   public void handleClientPacket(EntityPlayer player) {
      ChallengeGui.kits = this.kits != null?this.kits:new ArrayList();
      ChallengeGui.playerAttStats = this.playerAttStats;
      ChallengeGui.playerDefStats = this.playerDefStats;
      ChallengeGui.loaded = true;
   }
}
