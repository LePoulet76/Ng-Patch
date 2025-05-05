package net.ilexiconn.nationsgui.forge.server.trade.enums;


public enum EnumPacketServer {

   TRADE_REJECTED("TRADE_REJECTED", 0),
   TRADE_IGNORE("TRADE_IGNORE", 1),
   TRADE_ACCEPT("TRADE_ACCEPT", 2),
   TRADE_CANCEL("TRADE_CANCEL", 3),
   TRADE_COMPLETE("TRADE_COMPLETE", 4);
   // $FF: synthetic field
   private static final EnumPacketServer[] $VALUES = new EnumPacketServer[]{TRADE_REJECTED, TRADE_IGNORE, TRADE_ACCEPT, TRADE_CANCEL, TRADE_COMPLETE};


   private EnumPacketServer(String var1, int var2) {}

}
