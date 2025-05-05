package net.ilexiconn.nationsgui.forge.client.voices.networking;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.client.voices.networking.MinecraftVoiceClient;
import net.ilexiconn.nationsgui.forge.client.voices.networking.VoiceClient;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ClientNetwork {

   VoiceChatClient voiceChat;
   VoiceClient voiceClient;
   Thread voiceClientThread;


   public ClientNetwork(VoiceChatClient voiceChatClient) {
      this.voiceChat = voiceChatClient;
   }

   public VoiceClient startClientNetwork(EnumVoiceNetworkType type, String hash, String serverAddress, int udpPort, int soundDist) {
      this.voiceChat.getSettings().resetQuality();
      VoiceChatClient.getSoundManager().reset();
      this.voiceClient = new MinecraftVoiceClient(type);
      this.sendClientInfo();
      this.voiceChat.getSettings().setSoundDistance(soundDist);
      this.voiceClientThread = new Thread(this.voiceClient, "Voice Client");
      this.voiceClientThread.start();
      VoiceChatClient.getLogger().info("Connected to [" + type.name + "] Server.");
      return this.voiceClient;
   }

   public void stopClientNetwork() {
      VoiceChatClient.getLogger().info("Stopped Voice Client.");
      this.getVoiceClient().stop();
      this.voiceClient = null;
      this.voiceClientThread.stop();
      this.voiceClientThread = null;
   }

   private void sendClientInfo() {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
      DataOutputStream outputStream = new DataOutputStream(bos);

      try {
         outputStream.writeUTF(this.voiceChat.getShortVersion());
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.field_73630_a = "GVC-VS";
      packet.field_73629_c = bos.toByteArray();
      packet.field_73628_b = bos.size();
      PacketDispatcher.sendPacketToServer(packet);
   }

   public synchronized VoiceClient getVoiceClient() {
      return this.voiceClient;
   }

   public boolean voiceClientExists() {
      return this.voiceClient != null;
   }

   public void sendSamples(byte[] samples, boolean end) {
      if(this.voiceClient != null && !KeyManager.getInstance().isKeyMuted()) {
         this.voiceClient.sendVoiceData(samples, end);
      }

   }

   public void setQuality(int x, int x1) {
      this.voiceChat.getSettings().setBandwidthLimit(x, x1);
   }
}
