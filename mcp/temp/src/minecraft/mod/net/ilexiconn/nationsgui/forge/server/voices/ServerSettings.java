package net.ilexiconn.nationsgui.forge.server.voices;

import java.io.File;
import net.ilexiconn.nationsgui.forge.server.voices.ServerConfiguration;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;

public class ServerSettings {

   ServerConfiguration configuartion;
   VoiceChatServer voiceChat;
   private int soundDist = 64;
   private int udpPort = 5447;
   private boolean advancedNetwork;
   private boolean enableVoice;


   public ServerSettings(VoiceChatServer voiceChatServer) {
      this.voiceChat = voiceChatServer;
   }

   public void init(File file) {
      this.soundDist = 64;
      this.udpPort = 5427;
      this.configuartion = new ServerConfiguration(this, file);
      this.configuartion.init();
   }

   public int getSoundDistance() {
      return this.soundDist;
   }

   public void setSoundDistance(int dist) {
      this.soundDist = dist;
   }

   public void setUDP(int udp) {
      this.udpPort = udp;
   }

   public int getUDPort() {
      return this.udpPort;
   }

   public void setAdvancedNetworking(boolean bol) {
      this.advancedNetwork = bol;
   }

   public boolean isAdvancedNetworkAllowed() {
      return this.advancedNetwork;
   }

   public boolean isVoiceEnable() {
      return this.enableVoice;
   }

   public void setVoiceEnable(boolean bl) {
      this.enableVoice = bl;
   }
}
