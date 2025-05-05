package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyRequestUpdateStatusPacket implements IPacket, IClientPacket {

   private String status;
   private String oldStatus;
   private Integer requestID;
   private String factionATT;
   private String factionDEF;


   public FactionEnemyRequestUpdateStatusPacket(Integer requestID, String status, String oldStatus, String factionATT, String factionDEF) {
      this.requestID = requestID;
      this.status = status;
      this.oldStatus = oldStatus;
      this.factionATT = factionATT;
      this.factionDEF = factionDEF;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.requestID.intValue());
      data.writeUTF(this.status);
      data.writeUTF(this.oldStatus);
      data.writeUTF(this.factionATT);
      data.writeUTF(this.factionDEF);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }
}
