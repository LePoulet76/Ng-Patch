package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Pvp_Gui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormPvpPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseContractFormPvpPacket implements IPacket, IClientPacket {

   public HashMap<String, String> infos = new HashMap();
   public String enterpriseName;


   public EnterpriseContractFormPvpPacket(String enterpriseName) {
      this.enterpriseName = enterpriseName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.infos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseContractFormPvpPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
   }

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseContractForm_Pvp_Gui.data = this.infos;
      EnterpriseContractForm_Pvp_Gui.loaded = true;
   }
}
