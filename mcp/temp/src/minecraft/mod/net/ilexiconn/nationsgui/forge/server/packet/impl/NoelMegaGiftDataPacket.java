package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.gui.NoelMegaGiftGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelMegaGiftDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class NoelMegaGiftDataPacket implements IPacket, IClientPacket {

   public ArrayList<String> history = new ArrayList();
   public boolean megaGiftAround = false;


   public void fromBytes(ByteArrayDataInput data) {
      this.history = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new NoelMegaGiftDataPacket$1(this)).getType());
      this.megaGiftAround = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      NoelMegaGiftGui.history.addAll(this.history);
      NoelMegaGiftGui.megaGiftAround = this.megaGiftAround;
   }
}
