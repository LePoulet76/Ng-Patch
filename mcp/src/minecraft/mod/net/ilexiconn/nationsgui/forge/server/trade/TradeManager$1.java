package net.ilexiconn.nationsgui.forge.server.trade;

import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;

class TradeManager$1
{
    static final int[] $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer = new int[EnumPacketServer.values().length];

    static
    {
        try
        {
            $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[EnumPacketServer.TRADE_ACCEPT.ordinal()] = 1;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }

        try
        {
            $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[EnumPacketServer.TRADE_CANCEL.ordinal()] = 2;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[EnumPacketServer.TRADE_COMPLETE.ordinal()] = 3;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[EnumPacketServer.TRADE_IGNORE.ordinal()] = 4;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            $SwitchMap$net$ilexiconn$nationsgui$forge$server$trade$enums$EnumPacketServer[EnumPacketServer.TRADE_REJECTED.ordinal()] = 5;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
