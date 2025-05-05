package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class TradeClosePacket implements IPacket, IClientPacket {

   private String playerName;


   public TradeClosePacket(String playerName) {
      this.playerName = playerName;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerName);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(mc.field_71462_r instanceof GuiTrade) {
         mc.func_71373_a((GuiScreen)null);
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeClosePacket(player.field_71092_bJ)));
   }
}
