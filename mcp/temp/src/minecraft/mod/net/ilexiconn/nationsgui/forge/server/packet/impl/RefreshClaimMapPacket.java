package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.nationsmap.overlay.OverlayClaim;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshClaimMapPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshClaimMapPacket implements IPacket, IClientPacket {

   public HashMap<String, String> claimedChunks;
   public boolean remove;


   public void fromBytes(ByteArrayDataInput data) {
      this.claimedChunks = (HashMap)(new Gson()).fromJson(data.readUTF(), (new RefreshClaimMapPacket$1(this)).getType());
      this.remove = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.claimedChunks));
      data.writeBoolean(this.remove);
   }

   public void handleClientPacket(EntityPlayer player) {
      if(!this.remove) {
         OverlayClaim.claimedChunks.putAll(this.claimedChunks);
      } else {
         Iterator it = this.claimedChunks.entrySet().iterator();

         while(it.hasNext()) {
            Entry pair = (Entry)it.next();
            OverlayClaim.claimedChunks.remove(pair.getKey());
            it.remove();
         }
      }

   }
}
