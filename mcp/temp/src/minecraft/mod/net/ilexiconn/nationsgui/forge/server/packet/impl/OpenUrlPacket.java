package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class OpenUrlPacket implements IPacket, IClientPacket, IServerPacket {

   public String url;


   public OpenUrlPacket(String url) {
      this.url = url;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.url = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.url);
   }

   public void handleClientPacket(EntityPlayer player) {
      try {
         Class t = Class.forName("java.awt.Desktop");
         Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         t.getMethod("browse", new Class[]{URI.class}).invoke(theDesktop, new Object[]{new URI(this.url)});
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public void handleServerPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
   }
}
