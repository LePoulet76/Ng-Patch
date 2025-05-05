package net.ilexiconn.nationsgui.forge.server.trade.enums;


public enum EnumPacketClient {

   TRADE_REQUEST("TRADE_REQUEST", 0),
   TRADE_CANCEL("TRADE_CANCEL", 1),
   TRADE_STATE("TRADE_STATE", 2),
   TRADE_CLOSE("TRADE_CLOSE", 3),
   TRADE_ITEMS("TRADE_ITEMS", 4);
   // $FF: synthetic field
   private static final EnumPacketClient[] $VALUES = new EnumPacketClient[]{TRADE_REQUEST, TRADE_CANCEL, TRADE_STATE, TRADE_CLOSE, TRADE_ITEMS};


   private EnumPacketClient(String var1, int var2) {}

}
