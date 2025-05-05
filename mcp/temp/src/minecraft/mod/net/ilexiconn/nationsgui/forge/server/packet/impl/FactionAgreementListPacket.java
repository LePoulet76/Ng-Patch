package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAgreementListPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionAgreementListPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, Object>> agreements = new ArrayList();
   public int warId;
   public boolean canCreateAgreement;
   public String playerFactionId;


   public FactionAgreementListPacket(int warId) {
      this.warId = warId;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.agreements = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionAgreementListPacket$1(this)).getType());
      this.canCreateAgreement = data.readBoolean();
      this.playerFactionId = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.warId);
   }

   public void handleClientPacket(EntityPlayer player) {
      WarAgreementListGui.agreements = this.agreements;
      WarAgreementListGui.canCreateAgreement = this.canCreateAgreement;
      WarAgreementListGui.playerFactionId = this.playerFactionId;
      WarAgreementListGui.loaded = true;
   }
}
