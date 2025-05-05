package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyRequestPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> warInfos = new HashMap();
   public int warRequestId;


   public FactionEnemyRequestPacket(int warRequestId) {
      this.warRequestId = warRequestId;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.warInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionEnemyRequestPacket$1(this)).getType());
      this.warRequestId = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.warRequestId);
   }

   public void handleClientPacket(EntityPlayer player) {
      WarRequestGUI.warInfos = this.warInfos;
      WarRequestGUI.warRequestId = this.warRequestId;
      WarRequestGUI.loaded = true;
   }
}
