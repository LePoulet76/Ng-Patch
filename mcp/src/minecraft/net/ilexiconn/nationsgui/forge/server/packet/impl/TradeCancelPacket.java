package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.trade.ITrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class TradeCancelPacket implements IPacket, IClientPacket, IServerPacket
{
    private String trader;

    public TradeCancelPacket()
    {
        this.trader = "";
    }

    public TradeCancelPacket(String trader)
    {
        this.trader = trader;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.trader = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.trader);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.currentScreen instanceof ITrade)
        {
            ITrade gui = (ITrade)mc.currentScreen;

            if (gui.getTrader() == null || this.trader.equals(gui.getTrader().getCommandSenderName()))
            {
                mc.displayGuiScreen((GuiScreen)null);
                player.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.cancel", new Object[] {this.trader}));
                player.closeScreen();
            }
        }
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TradeData data = TradeData.get(player);

        if (data.isTradingWith(data.tradePlayer) && data.state != EnumTradeState.DONE)
        {
            TradeManager.sendCancel(data.tradePlayer, player.getCommandSenderName());
        }

        data.closeTrade();
        TradeManager.sendCancel(player, player.getCommandSenderName());
    }
}
