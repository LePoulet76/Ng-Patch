package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionBuyActionPacket implements IPacket, IClientPacket, IServerPacket {

   public String factionTargetId;
   public String factionWhoBuyId;
   public String ownerFactionId;
   public int index;
   public String price;


   public FactionBuyActionPacket(String factionTargetId, String factionWhoBuyId, String ownerFactionId, int index, String price) {
      this.factionTargetId = factionTargetId;
      this.factionWhoBuyId = factionWhoBuyId;
      this.ownerFactionId = ownerFactionId;
      this.index = index;
      this.price = price;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionTargetId = data.readUTF();
      this.factionWhoBuyId = data.readUTF();
      this.ownerFactionId = data.readUTF();
      this.index = data.readInt();
      this.price = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionTargetId);
      data.writeUTF(this.factionWhoBuyId);
      data.writeUTF(this.ownerFactionId);
      data.writeInt(this.index);
      data.writeUTF(this.price);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public void handleServerPacket(EntityPlayer player) {}
}
