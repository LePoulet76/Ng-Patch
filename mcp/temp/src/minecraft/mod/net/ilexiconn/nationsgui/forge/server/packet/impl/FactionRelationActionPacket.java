package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionRelationActionPacket implements IPacket, IClientPacket {

   public String target;
   public String relation;


   public FactionRelationActionPacket(String targetName, String relation) {
      this.target = targetName;
      this.relation = relation;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.target = data.readUTF();
      this.relation = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
      data.writeUTF(this.relation);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.target, true)));
   }
}
