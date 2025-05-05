package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class IncrementObjectivePacket implements IPacket, IClientPacket, IServerPacket {

   private String objective_name;
   private int value;


   public IncrementObjectivePacket(String objective_name, int value) {
      this.objective_name = objective_name;
      this.value = value;
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket(this.objective_name, this.value)));
   }

   public void handleServerPacket(EntityPlayer player) {}

   public void fromBytes(ByteArrayDataInput data) {
      this.objective_name = data.readUTF();
      this.value = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.objective_name);
      data.writeInt(this.value);
   }
}
