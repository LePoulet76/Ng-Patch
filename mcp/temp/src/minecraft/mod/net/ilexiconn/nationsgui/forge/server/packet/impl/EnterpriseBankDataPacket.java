package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBankGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBankDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> bankInfos = new HashMap();
   public String enterpriseName;


   public EnterpriseBankDataPacket(String targetName) {
      this.enterpriseName = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.bankInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseBankDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
   }

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseBankGUI.enterpriseBankInfos = this.bankInfos;
      EnterpriseBankGUI.loaded = true;
   }
}
