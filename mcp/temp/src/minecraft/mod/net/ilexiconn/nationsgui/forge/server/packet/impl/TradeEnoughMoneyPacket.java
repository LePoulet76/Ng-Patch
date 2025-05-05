package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TradeEnoughMoneyPacket implements IPacket, IClientPacket {

   private boolean enough;


   public TradeEnoughMoneyPacket(boolean enough) {
      this.enough = enough;
   }

   public void handleClientPacket(EntityPlayer player) {
      GuiTrade gui = (GuiTrade)mc.field_71462_r;
      if(gui != null) {
         gui.hasEnoughMoney = this.enough;
      }
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.enough = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeBoolean(this.enough);
   }
}
