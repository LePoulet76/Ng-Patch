package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionPlotsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPlotsDataPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, Object>> plots = new ArrayList();
   public String targetFaction;
   public int countSell;
   public int countRent;
   public int countAvailable;
   public boolean canCreateNewPlot;


   public FactionPlotsDataPacket(String targetFaction) {
      this.targetFaction = targetFaction;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.targetFaction = data.readUTF();
      this.plots = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionPlotsDataPacket$1(this)).getType());
      this.countSell = data.readInt();
      this.countRent = data.readInt();
      this.countAvailable = data.readInt();
      this.canCreateNewPlot = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.targetFaction);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      FactionPlotsGUI.plots.addAll(this.plots);
      FactionPlotsGUI.loaded = true;
      FactionPlotsGUI.selectedPlot = new HashMap();
      FactionPlotsGUI.countSell = this.countSell;
      FactionPlotsGUI.countRent = this.countRent;
      FactionPlotsGUI.countAvailable = this.countAvailable;
      FactionPlotsGUI.canCreateNewPlot = this.canCreateNewPlot;
   }
}
