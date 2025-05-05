package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamsDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandTeamCreatePacket implements IPacket, IClientPacket {

   private int teamNum;
   private int islandId;


   public IslandTeamCreatePacket(int teamNum, int islandId) {
      this.teamNum = teamNum;
      this.islandId = islandId;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.teamNum);
      data.writeInt(this.islandId);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamsDataPacket(true)));
   }
}
