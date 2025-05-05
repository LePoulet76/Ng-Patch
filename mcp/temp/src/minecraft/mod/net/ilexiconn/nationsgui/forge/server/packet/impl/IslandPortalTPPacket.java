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

public class IslandPortalTPPacket implements IPacket, IClientPacket, IServerPacket {

   public int posX;
   public int posY;
   public int posZ;
   public String playerName;
   public float yaw;
   public boolean dataSentToFirebase;
   public String codeFetchedFromServer;
   public boolean isSync;


   public IslandPortalTPPacket(int posX, int posY, int posZ, String playerName, float yaw) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.playerName = playerName;
      this.yaw = yaw;
      this.dataSentToFirebase = false;
      this.codeFetchedFromServer = "";
      this.isSync = false;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.playerName = data.readUTF();
      this.yaw = data.readFloat();
      this.dataSentToFirebase = data.readBoolean();
      this.codeFetchedFromServer = data.readUTF();
      this.isSync = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeUTF(this.playerName);
      data.writeFloat(this.yaw);
      data.writeBoolean(this.dataSentToFirebase);
      data.writeUTF(this.codeFetchedFromServer);
      data.writeBoolean(this.isSync);
   }

   public void handleServerPacket(EntityPlayer player) {
      TileEntity tile1;
      TileEntity tile2;
      if(this.codeFetchedFromServer.isEmpty()) {
         tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
         if(tile1 instanceof PortalBlockEntity || tile2 instanceof PortalBlockEntity) {
            if(tile1 instanceof PortalBlockEntity) {
               this.codeFetchedFromServer = ((PortalBlockEntity)tile1).code;
            } else {
               this.codeFetchedFromServer = ((PortalBlockEntity)tile2).code;
            }

            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
         }
      }

      if(this.dataSentToFirebase) {
         tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
         TileEntity tile3 = player.field_70170_p.func_72796_p(this.posX, this.posY - 1, this.posZ);
         if(tile1 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile1).active = this.isSync;
         }

         if(tile2 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile2).active = this.isSync;
         }

         if(tile3 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile3).active = this.isSync;
         }

         player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY - 1, this.posZ);
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.dataSentToFirebase) {
         TileEntity tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
         TileEntity tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
         TileEntity tile3 = player.field_70170_p.func_72796_p(this.posX, this.posY - 1, this.posZ);
         if(tile1 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile1).active = this.isSync;
         }

         if(tile2 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile2).active = this.isSync;
         }

         if(tile3 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile3).active = this.isSync;
         }

         player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
         player.field_70170_p.func_72902_n(this.posX, this.posY - 1, this.posZ);
      }

      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
   }
}
