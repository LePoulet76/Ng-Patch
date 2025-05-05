package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.network.packet.Packet;

public enum PacketRegistry {

   INSTANCE("INSTANCE", 0);
   public static final String CHANNEL = "nationsgui";
   public List<Class<? extends IPacket>> packetList = new ArrayList();
   // $FF: synthetic field
   private static final PacketRegistry[] $VALUES = new PacketRegistry[]{INSTANCE};


   private PacketRegistry(String var1, int var2) {}

   public void registerPacket(Class<? extends IPacket> packetClass) {
      this.packetList.add(packetClass);
   }

   public Packet generatePacket(IPacket packet) {
      if(!this.packetList.contains(packet.getClass())) {
         throw new RuntimeException("Attempted to send an unregistered packet!");
      } else {
         ByteArrayDataOutput data = ByteStreams.newDataOutput();
         data.writeInt(this.packetList.indexOf(packet.getClass()));
         packet.toBytes(data);
         return PacketDispatcher.getPacket("nationsgui", data.toByteArray());
      }
   }

}
