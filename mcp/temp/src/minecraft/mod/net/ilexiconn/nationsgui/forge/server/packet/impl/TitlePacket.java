package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.util.Title;
import net.minecraft.entity.player.EntityPlayer;

public class TitlePacket implements IPacket, IClientPacket {

   private Title title;


   public TitlePacket(Title title) {
      this.title = title;
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientEventHandler.getInstance().getTitleOverlay().displayTitle(this.title.getTitle(), this.title.getSubtitle(), (int)this.title.getTimer(), 0, 0);
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.title = Title.fromPacket(data.readUTF(), data.readUTF(), data.readFloat());
   }

   public void toBytes(ByteArrayDataOutput data) {
      this.title.writePacket(data);
   }
}
