package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyCreatePacket implements IPacket, IClientPacket {

   private String factionATT;
   private String factionDEF;
   private String reason;
   private Integer requestID;


   public FactionEnemyCreatePacket(String factionATT, String factionDEF, String reason) {
      this.factionATT = factionATT;
      this.factionDEF = factionDEF;
      this.reason = reason;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.requestID = Integer.valueOf(data.readInt());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionATT);
      data.writeUTF(this.factionDEF);
      data.writeUTF(this.reason);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.requestID.intValue() != -1) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestPacket(this.requestID.intValue())));
      } else {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }
}
