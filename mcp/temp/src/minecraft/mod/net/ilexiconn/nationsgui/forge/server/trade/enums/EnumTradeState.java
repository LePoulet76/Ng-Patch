package net.ilexiconn.nationsgui.forge.server.trade.enums;


public enum EnumTradeState {

   NONE("NONE", 0),
   WAITING("WAITING", 1),
   STARTED("STARTED", 2),
   TRADER_ACCEPTED("TRADER_ACCEPTED", 3),
   YOU_ACCEPTED("YOU_ACCEPTED", 4),
   DONE("DONE", 5);
   // $FF: synthetic field
   private static final EnumTradeState[] $VALUES = new EnumTradeState[]{NONE, WAITING, STARTED, TRADER_ACCEPTED, YOU_ACCEPTED, DONE};


   private EnumTradeState(String var1, int var2) {}

}
