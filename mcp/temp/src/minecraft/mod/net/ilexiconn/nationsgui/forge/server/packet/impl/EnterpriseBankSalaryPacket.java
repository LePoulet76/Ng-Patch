package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankSalaryPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBankSalaryPacket implements IPacket, IClientPacket {

   public String enterpriseName;
   public HashMap<String, Integer> salaries;


   public EnterpriseBankSalaryPacket(String enterpriseName, HashMap<String, Integer> salaries) {
      this.enterpriseName = enterpriseName;
      this.salaries = salaries;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.enterpriseName = data.readUTF();
      this.salaries = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseBankSalaryPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF((new Gson()).toJson(this.salaries));
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankDataPacket(this.enterpriseName)));
   }
}
