package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.PingThread;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class PingPacket implements IPacket, IClientPacket, IServerPacket {

   private int ping = 0;
   private String emitter = "";


   public PingPacket() {}

   public PingPacket(String emitter) {
      this.emitter = emitter;
   }

   public void handleClientPacket(EntityPlayer player) {
      this.ping = PingThread.ping;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.ping = data.readInt();
      this.emitter = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.ping);
      data.writeUTF(this.emitter);
   }

   public void handleServerPacket(EntityPlayer player) {
      EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(this.emitter);
      entityPlayerMP.func_70006_a(ChatMessageComponent.func_111066_d(player.field_71092_bJ + " : " + this.ping + " ms"));
   }
}
