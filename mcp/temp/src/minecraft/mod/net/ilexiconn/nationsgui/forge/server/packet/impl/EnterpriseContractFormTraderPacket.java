package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Trader_Gui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormTraderPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseContractFormTraderPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> infos = new HashMap();
   public String enterpriseName;


   public EnterpriseContractFormTraderPacket(String enterpriseName) {
      this.enterpriseName = enterpriseName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.infos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseContractFormTraderPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
   }

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseContractForm_Trader_Gui.data = this.infos;
      EnterpriseContractForm_Trader_Gui.loaded = true;
   }
}
