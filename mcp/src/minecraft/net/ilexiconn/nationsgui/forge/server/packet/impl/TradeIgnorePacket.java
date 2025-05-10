package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class TradeIgnorePacket implements IPacket, IServerPacket
{
    public void handleServerPacket(EntityPlayer player)
    {
        TradeData data = TradeData.get(player);
        data.ignored.put(data.tradePlayer.getCommandSenderName().toLowerCase(), Long.valueOf(System.currentTimeMillis()));
        player.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.ignored", new Object[] {data.tradePlayer.getCommandSenderName()}));
        data.tradePlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.rejected", new Object[] {player.getCommandSenderName()}));
        data.closeTrade();
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}
}
