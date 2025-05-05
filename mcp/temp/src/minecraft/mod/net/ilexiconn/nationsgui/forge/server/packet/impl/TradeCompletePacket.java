package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeTraderIsReady;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeUpdateMoneyPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TradeCompletePacket implements IPacket, IServerPacket {

   private int money;


   public TradeCompletePacket(int money) {
      this.money = money;
   }

   public void handleServerPacket(EntityPlayer player) {
      TradeData data = TradeData.get(player);
      if(!data.isTrading()) {
         data.closeTrade();
      } else if(data.state != EnumTradeState.DONE) {
         if(Bukkit.getServer() != null) {
            Plugin trader = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if(this.hasMoney(trader, player.field_71092_bJ, this.money)) {
               data.setMoney(this.money);
               EntityPlayer var9 = data.tradePlayer;
               TradeData traderdata = TradeData.get(var9);
               if(data.state == EnumTradeState.TRADER_ACCEPTED) {
                  traderdata.setState(EnumTradeState.DONE);
                  data.setState(EnumTradeState.DONE);
                  ContainerTrade container1 = (ContainerTrade)player.field_71070_bA;
                  ContainerTrade container2 = (ContainerTrade)var9.field_71070_bA;
                  if(Bukkit.getServer() != null) {
                     Plugin i = Bukkit.getPluginManager().getPlugin("NationsGUI");
                     if(!this.hasMoney(i, player.field_71092_bJ, data.getMoney()) || !this.hasMoney(i, var9.field_71092_bJ, traderdata.getMoney())) {
                        data.closeTrade();
                        TradeManager.sendClose(player);
                        TradeManager.sendClose(var9);
                        player.func_71035_c(Translation.get("Action impossible, pas assez d\'argent."));
                        var9.func_71035_c(Translation.get("Action impossible, pas assez d\'argent."));
                        return;
                     }

                     if(this.hasMoney(i, player.field_71092_bJ, data.getMoney())) {
                        this.addMoney(i, player.field_71092_bJ, -data.getMoney());
                        this.addMoney(i, var9.field_71092_bJ, data.getMoney());
                     }

                     if(this.hasMoney(i, var9.field_71092_bJ, traderdata.getMoney())) {
                        this.addMoney(i, var9.field_71092_bJ, -traderdata.getMoney());
                        this.addMoney(i, player.field_71092_bJ, traderdata.getMoney());
                     }
                  }

                  for(int var10 = 0; var10 < 14; ++var10) {
                     ItemStack item = container1.craftMatrix.func_70301_a(var10);
                     if(container1.craftMatrix.func_70301_a(var10) != null) {
                        System.out.println("[TRADE] " + player.getDisplayName() + " GIVE " + container1.craftMatrix.func_70301_a(var10).field_77993_c + ":" + container1.craftMatrix.func_70301_a(var10).func_77960_j() + " x" + container1.craftMatrix.func_70301_a(var10).field_77994_a + " TO " + var9.getDisplayName());
                     }

                     if(container2.craftMatrix.func_70301_a(var10) != null) {
                        System.out.println("[TRADE] " + var9.getDisplayName() + " GIVE " + container2.craftMatrix.func_70301_a(var10).field_77993_c + ":" + container2.craftMatrix.func_70301_a(var10).func_77960_j() + " x" + container2.craftMatrix.func_70301_a(var10).field_77994_a + " TO " + player.getDisplayName());
                     }

                     container1.craftMatrix.func_70299_a(var10, container2.craftMatrix.func_70301_a(var10));
                     container2.craftMatrix.func_70299_a(var10, item);
                  }

                  traderdata.setMoney(0);
                  data.setMoney(0);
                  TradeManager.sendClose(player);
                  TradeManager.sendClose(var9);
               } else {
                  traderdata.setState(EnumTradeState.TRADER_ACCEPTED);
                  data.setState(EnumTradeState.YOU_ACCEPTED);
                  PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeTraderIsReady()), (Player)data.tradePlayer);
                  PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(this.money, "", false)), (Player)data.tradePlayer);
               }

            }
         }
      }
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.money = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.money);
   }

   public void addMoney(Plugin pl, String player, int money) {
      try {
         Method e = pl.getClass().getDeclaredMethod("addMoney", new Class[]{String.class, Integer.TYPE});
         e.invoke(pl, new Object[]{player, Integer.valueOf(money)});
         if(money != 0) {
            System.out.println("[TRADE] " + player + " " + money);
         }
      } catch (InvocationTargetException var5) {
         var5.printStackTrace();
      }

   }

   public boolean hasMoney(Plugin pl, String player, int money) {
      try {
         Method e = pl.getClass().getDeclaredMethod("hasMoney", new Class[]{String.class, Integer.TYPE});
         return ((Boolean)e.invoke(pl, new Object[]{player, Integer.valueOf(money)})).booleanValue();
      } catch (InvocationTargetException var5) {
         return false;
      }
   }
}
