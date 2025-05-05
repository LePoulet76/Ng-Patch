package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMainJoinPacket implements IPacket, IClientPacket {

   public String target;


   public FactionMainJoinPacket(String targetName) {
      this.target = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.target = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.target, false)));
   }
}
