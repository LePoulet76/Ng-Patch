package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandCreateGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandCreateCooldownPacket implements IPacket, IClientPacket {

   public long lastPlayerCreation;


   public void fromBytes(ByteArrayDataInput data) {
      this.lastPlayerCreation = data.readLong();
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      IslandCreateGui.lastPlayerCreation = this.lastPlayerCreation;
      IslandCreateGui.loaded = true;
   }
}
