package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import fr.nationsglory.ngcontent.server.entity.EntityCarePackage;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class KillCarePackagePacket implements IPacket, IServerPacket, IClientPacket {

   public static HashMap<String, EntityCarePackage> carePakagesAlive = new HashMap();
   public String defenserName;


   public KillCarePackagePacket(String defenserName) {
      this.defenserName = defenserName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.defenserName = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.defenserName);
   }

   public void handleServerPacket(EntityPlayer player) {}

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new KillCarePackagePacket(this.defenserName)));
   }

}
