package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.block.entity.PortalBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class IslandPortalBreakPacket implements IPacket, IServerPacket, IClientPacket {

   public int posX;
   public int posY;
   public int posZ;
   public String code;


   public IslandPortalBreakPacket(int posX, int posY, int posZ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.code = "";
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.code = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeUTF(this.code);
   }

   public void handleServerPacket(EntityPlayer player) {
      if(this.code.isEmpty()) {
         TileEntity tile = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         if(tile != null && tile instanceof PortalBlockEntity) {
            this.code = ((PortalBlockEntity)tile).code;
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
         }
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }
}
