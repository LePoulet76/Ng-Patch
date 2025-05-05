package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.util.StringCompression;
import net.minecraft.entity.player.EntityPlayer;

public class FactionActionsDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> actionsInfos = new HashMap();
   public String target;


   public FactionActionsDataPacket(String targetName) {
      this.target = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      int size = data.readInt();
      byte[] bytes = new byte[size];
      data.readFully(bytes);
      JsonParser jsonParser = new JsonParser();
      JsonObject jsonObject = null;

      try {
         jsonObject = jsonParser.parse(StringCompression.decompress(bytes)).getAsJsonObject();
         this.actionsInfos = (HashMap)(new Gson()).fromJson(jsonObject.getAsJsonObject("result"), (new FactionActionsDataPacket$1(this)).getType());
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      System.out.println(this.actionsInfos);
      BankGUI.factionActionsInfos = this.actionsInfos;
   }
}
