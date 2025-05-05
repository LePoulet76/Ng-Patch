package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SetRedstonePacket implements IPacket, IServerPacket {

   private int x;
   private int y;
   private int z;
   private boolean needsRedstone;


   public SetRedstonePacket(RadioBlockEntity blockEntity) {
      this.x = blockEntity.field_70329_l;
      this.y = blockEntity.field_70330_m;
      this.z = blockEntity.field_70327_n;
      this.needsRedstone = blockEntity.needsRedstone;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.x = data.readInt();
      this.y = data.readInt();
      this.z = data.readInt();
      this.needsRedstone = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.x);
      data.writeInt(this.y);
      data.writeInt(this.z);
      data.writeBoolean(this.needsRedstone);
   }

   public void handleServerPacket(EntityPlayer player) {
      TileEntity tileEntity = player.field_70170_p.func_72796_p(this.x, this.y, this.z);
      if(tileEntity != null) {
         ((RadioBlockEntity)tileEntity).needsRedstone = this.needsRedstone;
      }

   }
}
