package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionLockActionPacket implements IPacket, IClientPacket, IServerPacket {

   public String factionTargetId;
   public int index;


   public FactionLockActionPacket(String factionTargetId, int index) {
      this.factionTargetId = factionTargetId;
      this.index = index;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionTargetId = data.readUTF();
      this.index = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionTargetId);
      data.writeInt(this.index);
   }

   public void handleClientPacket(EntityPlayer player) {}

   public void handleServerPacket(EntityPlayer player) {}
}
