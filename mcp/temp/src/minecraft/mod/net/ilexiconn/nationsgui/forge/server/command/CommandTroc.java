package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayersInTradePacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class CommandTroc extends CommandBase {

   public String func_71517_b() {
      return "troc";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/troc <player>";
   }

   public List func_71514_a() {
      return Arrays.asList(new String[]{"barter", "troc"});
   }

   public void func_71515_b(ICommandSender icommandsender, String[] args) {
      if(icommandsender instanceof EntityPlayer) {
         EntityPlayer sender = (EntityPlayer)icommandsender;
         if(args.length > 0 && sender.func_130014_f_().func_72924_a(args[0]) != null) {
            EntityPlayer trader = sender.func_130014_f_().func_72924_a(args[0]);
            if(!sender.equals(trader)) {
               if(TradeData.playersClose(sender, trader)) {
                  TradeData dataSender = TradeData.get(sender);
                  TradeData dataTrader = TradeData.get(trader);
                  if(dataTrader.tradePlayer == sender) {
                     if(!dataTrader.isTrading() && !dataSender.isTrading()) {
                        if(System.currentTimeMillis() - dataSender.getStartTradeTime() <= 60000L) {
                           dataSender.tradePlayer = trader;
                           sender.openGui(NationsGUI.INSTANCE, 777, sender.field_70170_p, 0, 0, 0);
                           trader.openGui(NationsGUI.INSTANCE, 777, trader.field_70170_p, 0, 0, 0);
                           dataSender.setState(EnumTradeState.STARTED);
                           dataTrader.setState(EnumTradeState.STARTED);
                           PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PlayersInTradePacket(sender.field_71092_bJ, trader.field_71092_bJ, true)), (Player)sender);
                        } else {
                           dataSender.closeTrade();
                           dataTrader.closeTrade();
                           icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.timeout", new Object[]{trader.func_70005_c_()}));
                        }
                     } else {
                        icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.busy", new Object[]{trader.func_70005_c_()}));
                     }
                  } else {
                     dataSender.setStartTradeTime(System.currentTimeMillis());
                     dataSender.tradePlayer = trader;
                     dataSender.setState(EnumTradeState.WAITING);
                     TradeManager.sendRequest(trader, sender.func_70005_c_());
                     icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.request_sent", new Object[]{trader.getDisplayName()}));
                  }
               } else {
                  icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.toofar", new Object[]{trader.func_70005_c_()}));
               }
            } else {
               icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.self", new Object[]{trader.func_70005_c_()}));
            }
         } else {
            icommandsender.func_70006_a(ChatMessageComponent.func_111082_b("trade.notfound", new Object[]{sender.func_70005_c_()}));
         }
      }

   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return par2ArrayOfStr.length == 1?func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z()):null;
   }

   public int compareTo(Object o) {
      return 0;
   }
}
