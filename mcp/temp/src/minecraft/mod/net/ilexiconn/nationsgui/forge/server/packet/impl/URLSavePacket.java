package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class URLSavePacket implements IPacket, IServerPacket {

   private int x;
   private int y;
   private int z;
   private String url;


   public URLSavePacket(TileEntity blockEntity) {
      this.x = blockEntity.field_70329_l;
      this.y = blockEntity.field_70330_m;
      this.z = blockEntity.field_70327_n;
      if(blockEntity instanceof URLBlockEntity) {
         this.url = ((URLBlockEntity)blockEntity).url;
      }

      if(blockEntity instanceof GenericGeckoTileEntity) {
         this.url = ((GenericGeckoTileEntity)blockEntity).url;
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      this.x = data.readInt();
      this.y = data.readInt();
      this.z = data.readInt();
      this.url = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.x);
      data.writeInt(this.y);
      data.writeInt(this.z);
      data.writeUTF(this.url);
   }

   public void handleServerPacket(EntityPlayer player) {
      if(MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(player.field_71092_bJ)) {
         TileEntity tileEntity = player.field_70170_p.func_72796_p(this.x, this.y, this.z);
         if(tileEntity != null) {
            if(tileEntity instanceof URLBlockEntity) {
               ((URLBlockEntity)tileEntity).url = this.url;
            } else if(tileEntity instanceof GenericGeckoTileEntity) {
               ((GenericGeckoTileEntity)tileEntity).url = this.url;
            }
         }
      }

   }
}
