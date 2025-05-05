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

public class CommandTroc extends CommandBase
{
    public String getCommandName()
    {
        return "troc";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/troc <player>";
    }

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"barter", "troc"});
    }

    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            EntityPlayer sender = (EntityPlayer)icommandsender;

            if (args.length > 0 && sender.getEntityWorld().getPlayerEntityByName(args[0]) != null)
            {
                EntityPlayer trader = sender.getEntityWorld().getPlayerEntityByName(args[0]);

                if (!sender.equals(trader))
                {
                    if (TradeData.playersClose(sender, trader))
                    {
                        TradeData dataSender = TradeData.get(sender);
                        TradeData dataTrader = TradeData.get(trader);

                        if (dataTrader.tradePlayer == sender)
                        {
                            if (!dataTrader.isTrading() && !dataSender.isTrading())
                            {
                                if (System.currentTimeMillis() - dataSender.getStartTradeTime() <= 60000L)
                                {
                                    dataSender.tradePlayer = trader;
                                    sender.openGui(NationsGUI.INSTANCE, 777, sender.worldObj, 0, 0, 0);
                                    trader.openGui(NationsGUI.INSTANCE, 777, trader.worldObj, 0, 0, 0);
                                    dataSender.setState(EnumTradeState.STARTED);
                                    dataTrader.setState(EnumTradeState.STARTED);
                                    PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PlayersInTradePacket(sender.username, trader.username, true)), (Player)sender);
                                }
                                else
                                {
                                    dataSender.closeTrade();
                                    dataTrader.closeTrade();
                                    icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.timeout", new Object[] {trader.getCommandSenderName()}));
                                }
                            }
                            else
                            {
                                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.busy", new Object[] {trader.getCommandSenderName()}));
                            }
                        }
                        else
                        {
                            dataSender.setStartTradeTime(System.currentTimeMillis());
                            dataSender.tradePlayer = trader;
                            dataSender.setState(EnumTradeState.WAITING);
                            TradeManager.sendRequest(trader, sender.getCommandSenderName());
                            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.request_sent", new Object[] {trader.getDisplayName()}));
                        }
                    }
                    else
                    {
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.toofar", new Object[] {trader.getCommandSenderName()}));
                    }
                }
                else
                {
                    icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.self", new Object[] {trader.getCommandSenderName()}));
                }
            }
            else
            {
                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("trade.notfound", new Object[] {sender.getCommandSenderName()}));
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
