package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket$3;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMainDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> factionInfos = new HashMap();
   public String target;
   private boolean force;


   public FactionMainDataPacket(String targetName, boolean force) {
      this.target = targetName;
      this.force = force;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionMainDataPacket$1(this)).getType());
      this.force = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
      data.writeBoolean(this.force);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      FactionGUI.initTabs();
      FactionGUI.factionInfos = this.factionInfos;
      FactionGUI.loaded = true;
      if(((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() || ((Boolean)FactionGUI.factionInfos.get("isAdmin")).booleanValue()) {
         FactionGUI.TABS.add(new FactionMainDataPacket$2(this));
      }

      if(FactionGUI.hasPermissions("settings")) {
         FactionGUI.TABS.add(new FactionMainDataPacket$3(this));
      }

   }
}
