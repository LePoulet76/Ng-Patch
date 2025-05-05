package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class TradeRequestPacket implements IPacket, IClientPacket
{
    private String trader;

    public TradeRequestPacket(String trader)
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

    public void handleClientPacket(EntityPlayer player)
    {
        EntityPlayer trader = player.worldObj.getPlayerEntityByName(this.trader);

        if (trader != null)
        {
            trader.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.request", new Object[] {trader.getDisplayName(), trader.getDisplayName()}));
        }
    }
}
