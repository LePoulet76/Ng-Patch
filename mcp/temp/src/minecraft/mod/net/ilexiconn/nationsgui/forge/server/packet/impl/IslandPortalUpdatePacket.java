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

public class IslandPortalUpdatePacket implements IPacket, IServerPacket, IClientPacket {

   public int posX;
   public int posY;
   public int posZ;
   public String code;
   public int sync;


   public IslandPortalUpdatePacket(int posX, int posY, int posZ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.code = "";
      this.sync = -1;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.code = data.readUTF();
      this.sync = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeUTF(this.code);
      data.writeInt(this.sync);
   }

   public void handleServerPacket(EntityPlayer player) {
      if(this.code.isEmpty() || this.sync != -1) {
         TileEntity tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         if(tile1 != null && tile1 instanceof PortalBlockEntity) {
            if(this.code.isEmpty()) {
               this.code = ((PortalBlockEntity)tile1).code;
               PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
            } else if(this.sync != -1) {
               TileEntity tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
               TileEntity tile3 = player.field_70170_p.func_72796_p(this.posX, this.posY - 1, this.posZ);
               if(this.sync == 1) {
                  ((PortalBlockEntity)tile1).active = true;
                  if(tile2 instanceof PortalBlockEntity) {
                     ((PortalBlockEntity)tile2).active = true;
                  }

                  if(tile3 instanceof PortalBlockEntity) {
                     ((PortalBlockEntity)tile3).active = true;
                  }
               } else {
                  ((PortalBlockEntity)tile1).active = false;
                  if(tile2 instanceof PortalBlockEntity) {
                     ((PortalBlockEntity)tile2).active = false;
                  }

                  if(tile3 instanceof PortalBlockEntity) {
                     ((PortalBlockEntity)tile3).active = false;
                  }
               }

               player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
               player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
               player.field_70170_p.func_72902_n(this.posX, this.posY - 1, this.posZ);
            }
         }
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.sync != -1) {
         TileEntity tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         TileEntity tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
         TileEntity tile3 = player.field_70170_p.func_72796_p(this.posX, this.posY - 1, this.posZ);
         if(this.sync == 1) {
            if(tile1 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile1).active = true;
            }

            if(tile2 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile2).active = true;
            }

            if(tile3 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile3).active = true;
            }
         } else {
            if(tile1 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile1).active = false;
            }

            if(tile2 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile2).active = false;
            }

            if(tile3 instanceof PortalBlockEntity) {
               ((PortalBlockEntity)tile3).active = false;
            }
         }

         player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY - 1, this.posZ);
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }
}
