package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.override.InfoWidgetOverride;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPacket implements IPacket, IClientPacket {

   public String factionName;


   public void handleClientPacket(EntityPlayer player) {
      InfoWidgetOverride.faction = this.factionName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionName = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionName);
   }
}
