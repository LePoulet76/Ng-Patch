package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SpecVersusPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class SpecVersusPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> data;


   public void fromBytes(ByteArrayDataInput data) {
      this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new SpecVersusPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      if(!this.data.isEmpty()) {
         ClientData.versusOverlayData = this.data;
      } else {
         ClientData.versusOverlayData.clear();
      }

   }
}
