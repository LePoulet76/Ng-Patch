package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDiplomatieWishActionPacket implements IPacket, IClientPacket {

   public String factionFrom;
   public String targetName;
   public String action;
   public String relationType;


   public FactionDiplomatieWishActionPacket(String factionFrom, String targetName, String action, String relationType) {
      this.factionFrom = factionFrom;
      this.targetName = targetName;
      this.action = action;
      this.relationType = relationType;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionFrom = data.readUTF();
      this.targetName = data.readUTF();
      this.action = data.readUTF();
      this.relationType = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionFrom);
      data.writeUTF(this.targetName);
      data.writeUTF(this.action);
      data.writeUTF(this.relationType);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }
}
