package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;


public enum EnumVoiceNetworkType {

   MINECRAFT("MINECRAFT", 0, "MINECRAFT", 0, "Minecraft", false);
   public boolean authRequired;
   public String name;
   // $FF: synthetic field
   private static final EnumVoiceNetworkType[] $VALUES = new EnumVoiceNetworkType[]{MINECRAFT};


   private EnumVoiceNetworkType(String var1, int var2, String var11, int var21, String name, boolean authRequired) {
      this.name = name;
      this.authRequired = authRequired;
   }

}
