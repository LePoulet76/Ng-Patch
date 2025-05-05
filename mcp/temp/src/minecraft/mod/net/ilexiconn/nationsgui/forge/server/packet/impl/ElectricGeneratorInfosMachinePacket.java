package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.client.gui.ElectricGeneratorGUI;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ElectricGeneratorInfosMachinePacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class ElectricGeneratorInfosMachinePacket implements IPacket, IClientPacket {

   private ArrayList<String> enterprises;


   public void fromBytes(ByteArrayDataInput data) {
      this.enterprises = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new ElectricGeneratorInfosMachinePacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      ElectricGeneratorGUI.enterprises = this.enterprises;
      ElectricGeneratorGUI.loaded = true;
   }
}
