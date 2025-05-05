package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;

public abstract class VoiceServer implements Runnable {

   protected EnumVoiceNetworkType type;


   public VoiceServer(EnumVoiceNetworkType enumVoiceServer) {
      this.type = enumVoiceServer;
   }

   public final void run() {
      this.start();
   }

   public abstract boolean start();

   public abstract void handleVoiceData(Player var1, byte[] var2, int var3, boolean var4);

   public abstract void sendEntityData(Player var1, int var2, String var3, double var4, double var6, double var8, double var10, double var12, double var14);

   public abstract void sendEntityPosition(Player var1, int var2, double var3, double var5, double var7, double var9, double var11, double var13);

   public abstract void sendVoiceData(Player var1, int var2, int var3, byte[] var4);

   public abstract void sendChunkVoiceData(Player var1, int var2, int var3, byte[] var4, int var5);

   public abstract void sendVoiceEnd(Player var1, int var2);

   public final EnumVoiceNetworkType getType() {
      return this.type;
   }
}
