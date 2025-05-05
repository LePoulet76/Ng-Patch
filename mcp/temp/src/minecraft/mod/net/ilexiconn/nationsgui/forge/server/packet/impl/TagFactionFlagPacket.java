package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TagFactionFlagPacket implements IPacket, IClientPacket {

   private String factionName;
   private String flag;


   public TagFactionFlagPacket(String factionName) {
      this.factionName = factionName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionName = data.readUTF();
      this.flag = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionName);
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientEventHandler.factionsFlag.put(this.factionName.replaceFirst("\u00a7.{1}", ""), this.flag);
   }
}
