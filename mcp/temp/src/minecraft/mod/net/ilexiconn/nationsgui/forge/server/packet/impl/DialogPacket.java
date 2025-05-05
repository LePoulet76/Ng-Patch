package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class DialogPacket implements IPacket, IClientPacket {

   private HashMap<String, Object> dialogData;


   public DialogPacket(HashMap<String, Object> dialogData) {
      this.dialogData = dialogData;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.dialogData = (HashMap)(new Gson()).fromJson(data.readUTF(), HashMap.class);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.dialogData));
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientData.dialogs.add(this.dialogData);
   }
}
