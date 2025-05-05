package net.ilexiconn.nationsgui.forge.server.trade;

import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTrade$SlotTrader extends Slot
{
    final ContainerTrade this$0;

    public ContainerTrade$SlotTrader(ContainerTrade this$0, IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
        this.this$0 = this$0;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        EnumTradeState state = this.this$0.state;

        if (!ContainerTrade.access$000(this.this$0).worldObj.isRemote)
        {
            TradeData data = TradeData.get(ContainerTrade.access$000(this.this$0));
            state = data.state;
        }

        return state == EnumTradeState.STARTED || state == EnumTradeState.TRADER_ACCEPTED || state == EnumTradeState.YOU_ACCEPTED;
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        super.onSlotChanged();

        if (!ContainerTrade.access$000(this.this$0).worldObj.isRemote)
        {
            TradeData data = TradeData.get(ContainerTrade.access$000(this.this$0));

            if (data.state != EnumTradeState.DONE && data.state != EnumTradeState.WAITING)
            {
                data.setState(EnumTradeState.STARTED);
                TradeData data2 = TradeData.get(data.tradePlayer);
                data2.setState(EnumTradeState.STARTED);
            }

            TradeManager.sendItems(data.tradePlayer, this.this$0.itemsToComp(), data.player.username);
        }
        else
        {
            PacketCallbacks.MONEY.send(new String[0]);
        }
    }
}
