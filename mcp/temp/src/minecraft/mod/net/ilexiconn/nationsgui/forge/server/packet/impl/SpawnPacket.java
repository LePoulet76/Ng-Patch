package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class SpawnPacket implements IPacket {

   public boolean factionSpawn = true;


   public void fromBytes(ByteArrayDataInput data) {
      this.factionSpawn = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeBoolean(this.factionSpawn);
   }
}
