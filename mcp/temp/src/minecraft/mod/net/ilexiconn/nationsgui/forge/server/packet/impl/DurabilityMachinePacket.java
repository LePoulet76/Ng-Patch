package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class DurabilityMachinePacket implements IPacket, IServerPacket, IClientPacket {

   public int posX;
   public int posY;
   public int posZ;
   public int durability;
   public boolean addition;


   public DurabilityMachinePacket(int posX, int posY, int posZ, int durability, boolean addition) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.durability = durability;
      this.addition = addition;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.durability = data.readInt();
      this.addition = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeInt(this.durability);
      data.writeBoolean(this.addition);
   }

   public void handleServerPacket(EntityPlayer player) {
      TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ);
      if(tileEntity instanceof GCCoreTileEntityUniversalElectrical) {
         int dura = ((GCCoreTileEntityUniversalElectrical)tileEntity).durability;
         if(this.addition) {
            dura += this.durability;
            dura = Math.max(0, dura);
            dura = Math.min(100, dura);
         } else {
            dura = this.durability;
         }

         ((GCCoreTileEntityUniversalElectrical)tileEntity).durability = dura;
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }
}
