package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionCreatePacket implements IPacket, IClientPacket {

   public String factionName;
   public boolean announce;
   public boolean regen;


   public FactionCreatePacket(String factionName, boolean announce, boolean regen) {
      this.factionName = factionName;
      this.announce = announce;
      this.regen = regen;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionName = data.readUTF();
      this.announce = data.readBoolean();
      this.regen = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionName);
      data.writeBoolean(this.announce);
      data.writeBoolean(this.regen);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
