package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandRestoreBackupPacket implements IPacket, IClientPacket {

   public String id;
   public String backupId;


   public IslandRestoreBackupPacket(String id, String backupId) {
      this.id = id;
      this.backupId = backupId;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.id);
      data.writeUTF(this.backupId);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandMainDataPacket()));
   }
}
