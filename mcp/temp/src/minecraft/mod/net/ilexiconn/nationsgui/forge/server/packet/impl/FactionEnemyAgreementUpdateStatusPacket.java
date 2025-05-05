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

public class FactionEnemyAgreementUpdateStatusPacket implements IPacket, IClientPacket {

   private Integer agreementId;
   private String status;


   public FactionEnemyAgreementUpdateStatusPacket(Integer agreementId, String status) {
      this.agreementId = agreementId;
      this.status = status;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.agreementId.intValue());
      data.writeUTF(this.status);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }
}
