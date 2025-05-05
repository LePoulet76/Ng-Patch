package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CommonPacketHandler implements IPacketHandler {

   private void handleVoiceData(Packet250CustomPayload packet, Player player, boolean end) {
      byte[] data = null;
      if(!end) {
         DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.field_73629_c));

         try {
            int var8 = dis.readInt();
            data = new byte[var8];

            for(int i = 0; i < data.length; ++i) {
               data[i] = dis.readByte();
            }

            if(data.length > 61) {
               throw new Exception("Security: Received to much data! LIMIT 61, current: " + data.length);
            }
         } catch (Exception var81) {
            var81.printStackTrace();
         }
      }

      VoiceChat.getServerInstance().getVoiceServer().handleVoiceData(player, data, ((EntityPlayerMP)player).field_70157_k, end);
   }

   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
      if(packet.field_73630_a.equals("GVC-SMPL") || packet.field_73630_a.equals("GVC-SMPLE")) {
         this.handleVoiceData(packet, player, packet.field_73630_a.equals("GVC-SMPLE"));
      }

   }
}
