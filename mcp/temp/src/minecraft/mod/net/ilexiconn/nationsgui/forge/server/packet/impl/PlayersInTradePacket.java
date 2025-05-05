package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class PlayersInTradePacket implements IPacket, IClientPacket {

   private String player1;
   private String player2;
   private boolean start;


   public PlayersInTradePacket(String player1, String player2, boolean start) {
      this.player1 = player1;
      this.player2 = player2;
      this.start = start;
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.player1 = data.readUTF();
      this.player2 = data.readUTF();
      this.start = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.player1);
      data.writeUTF(this.player2);
      data.writeBoolean(this.start);
   }
}
