package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class CheckDurabilityMachinePacket implements IPacket, IServerPacket, IClientPacket {

   private String playerName;
   public int posX;
   public int posY;
   public int posZ;
   public String target;


   public CheckDurabilityMachinePacket(String playerName, int posX, int posY, int posZ, String target) {
      this.playerName = playerName;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.target = target;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.playerName = data.readUTF();
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.target = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerName);
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeUTF(this.target);
   }

   public void handleServerPacket(EntityPlayer player) {
      if(this.target.equalsIgnoreCase("forge")) {
         TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ);
         if(tileEntity instanceof GCCoreTileEntityUniversalElectrical) {
            int dura = ((GCCoreTileEntityUniversalElectrical)tileEntity).durability;
            if(dura < 100) {
               this.target = "bukkit";
               PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
            }
         }
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }
}
