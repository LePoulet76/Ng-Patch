package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandSaveSettingsPacket implements IPacket, IClientPacket {

   public String id;
   public HashMap<String, String> settings;


   public IslandSaveSettingsPacket(String id, HashMap<String, String> settings) {
      this.id = id;
      this.settings = settings;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.id);
      data.writeUTF((new Gson()).toJson(this.settings));
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandMainDataPacket()));
   }
}
