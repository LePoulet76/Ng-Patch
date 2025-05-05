package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class CosmeticDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> data = new HashMap();
   private String playerTarget;


   public CosmeticDataPacket(String playerTarget) {
      this.playerTarget = playerTarget;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new CosmeticDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerTarget);
   }

   public void handleClientPacket(EntityPlayer player) {
      CosmeticGUI.data = this.data;
      CosmeticGUI.loaded = true;
      CosmeticGUI.timeOpenGUI = Long.valueOf(System.currentTimeMillis());
   }
}
