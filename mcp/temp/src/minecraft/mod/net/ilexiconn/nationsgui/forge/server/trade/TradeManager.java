package net.ilexiconn.nationsgui.forge.server.trade;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayersInTradePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeAcceptPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeAddCooldownPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeCancelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeClosePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeCompletePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeIgnorePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeItemsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeRejectedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeStatePacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager$1;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TradeManager {

   public static void sendData(EnumPacketServer enu, int finalMoney) {
      Object packet = null;
      switch(TradeManager$1.$SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[enu.ordinal()]) {
      case 1:
         packet = new TradeAcceptPacket();
         break;
      case 2:
         packet = new TradeCancelPacket();
         break;
      case 3:
         packet = new TradeCompletePacket(finalMoney);
         break;
      case 4:
         packet = new TradeIgnorePacket();
         break;
      case 5:
         packet = new TradeRejectedPacket();
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket((IPacket)packet));
   }

   public static void sendRequest(EntityPlayer player, String name) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeRequestPacket(name)), (Player)player);
   }

   public static void sendItems(EntityPlayer player, NBTTagCompound items, String username) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeItemsPacket(items, username)), (Player)player);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)player);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)TradeData.get(player).tradePlayer);
   }

   public static void sendCancel(EntityPlayer player, String trader) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeCancelPacket(trader)), (Player)player);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PlayersInTradePacket(player.field_71092_bJ, trader, false)), (Player)player);
   }

   public static void sendClose(EntityPlayer player) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeClosePacket(player.field_71092_bJ)), (Player)player);
   }

   public static void sendState(EntityPlayer player, String trader, EnumTradeState state) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeStatePacket(trader, state.ordinal())), (Player)player);
   }
}
