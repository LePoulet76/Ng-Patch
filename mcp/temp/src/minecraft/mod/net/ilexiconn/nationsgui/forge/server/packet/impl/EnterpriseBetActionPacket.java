package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBetActionPacket implements IPacket, IClientPacket {

   public String enterpriseName;
   public String betId;
   public String action;
   private final int amount;
   private final int option;


   public EnterpriseBetActionPacket(String enterpriseName, String betId, String action, int amount, int option) {
      this.enterpriseName = enterpriseName;
      this.betId = betId;
      this.action = action;
      this.amount = amount;
      this.option = option;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
      data.writeUTF(this.betId);
      data.writeUTF(this.action);
      data.writeInt(this.amount);
      data.writeInt(this.option);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBetDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
   }
}
