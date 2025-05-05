package net.ilexiconn.nationsgui.forge.server.trade;

import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTrade$SlotTrader extends Slot {

   // $FF: synthetic field
   final ContainerTrade this$0;


   public ContainerTrade$SlotTrader(ContainerTrade this$0, IInventory par1iInventory, int par2, int par3, int par4) {
      super(par1iInventory, par2, par3, par4);
      this.this$0 = this$0;
   }

   public boolean func_75214_a(ItemStack par1ItemStack) {
      EnumTradeState state = this.this$0.state;
      if(!ContainerTrade.access$000(this.this$0).field_70170_p.field_72995_K) {
         TradeData data = TradeData.get(ContainerTrade.access$000(this.this$0));
         state = data.state;
      }

      return state == EnumTradeState.STARTED || state == EnumTradeState.TRADER_ACCEPTED || state == EnumTradeState.YOU_ACCEPTED;
   }

   public void func_75218_e() {
      super.func_75218_e();
      if(!ContainerTrade.access$000(this.this$0).field_70170_p.field_72995_K) {
         TradeData data = TradeData.get(ContainerTrade.access$000(this.this$0));
         if(data.state != EnumTradeState.DONE && data.state != EnumTradeState.WAITING) {
            data.setState(EnumTradeState.STARTED);
            TradeData data2 = TradeData.get(data.tradePlayer);
            data2.setState(EnumTradeState.STARTED);
         }

         TradeManager.sendItems(data.tradePlayer, this.this$0.itemsToComp(), data.player.field_71092_bJ);
      } else {
         PacketCallbacks.MONEY.send(new String[0]);
      }

   }
}
