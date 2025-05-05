package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseListDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseListDataPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseListDataPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, String>> enterprises = new ArrayList();
   public List<String> availableTypes = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.enterprises = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new EnterpriseListDataPacket$1(this)).getType());
      this.availableTypes = (List)(new Gson()).fromJson(data.readUTF(), (new EnterpriseListDataPacket$2(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseListGui.enterprises.addAll(this.enterprises);
      EnterpriseListGui.loaded = true;
      EnterpriseGui.availableTypes.clear();
      EnterpriseGui.availableTypes.add("all");
      EnterpriseGui.availableTypes.addAll(this.availableTypes);
   }
}
