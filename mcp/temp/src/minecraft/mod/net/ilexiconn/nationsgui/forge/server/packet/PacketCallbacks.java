package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks$1;
import net.minecraft.entity.player.EntityPlayer;

public enum PacketCallbacks {

   PERMISSION("PERMISSION", 0),
   MONEY("MONEY", 1),
   TICKETS("TICKETS", 2),
   TELEPORT("TELEPORT", 3),
   REMOVE_TICKET("REMOVE_TICKET", 4),
   TICKET_RESOLVED("TICKET_RESOLVED", 5),
   NEW_TICKET("NEW_TICKET", 6),
   REQUEST_SONG("REQUEST_SONG", 7);
   // $FF: synthetic field
   private static final PacketCallbacks[] $VALUES = new PacketCallbacks[]{PERMISSION, MONEY, TICKETS, TELEPORT, REMOVE_TICKET, TICKET_RESOLVED, NEW_TICKET, REQUEST_SONG};


   private PacketCallbacks(String var1, int var2) {}

   public abstract void handleCallback(EntityPlayer var1, ByteArrayDataInput var2);

   public void send(String ... extra) {
      ByteArrayDataOutput data = ByteStreams.newDataOutput();
      data.writeInt(this.ordinal() - 50);
      data.writeInt(extra.length);
      String[] var3 = extra;
      int var4 = extra.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         data.writeUTF(s);
      }

      PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket("nationsgui", data.toByteArray()));
   }

   // $FF: synthetic method
   PacketCallbacks(String x0, int x1, PacketCallbacks$1 x2) {
      this(x0, x1);
   }

}
