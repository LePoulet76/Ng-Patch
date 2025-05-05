package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseSavePermDataPacket implements IPacket, IClientPacket {

   public String enterpriseName;
   public ArrayList<String> enterpriseUpdatedPerms = new ArrayList();


   public EnterpriseSavePermDataPacket(String enterpriseName, ArrayList<String> enterpriseUpdatedPerms) {
      this.enterpriseName = enterpriseName;
      this.enterpriseUpdatedPerms = enterpriseUpdatedPerms;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF((new Gson()).toJson(this.enterpriseUpdatedPerms));
   }

   public void handleClientPacket(EntityPlayer player) {}
}
