package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHatBlock extends TileEntity {

   private String hatID = "";


   public void func_70307_a(NBTTagCompound par1nbtTagCompound) {
      if(!this.field_70331_k.field_72995_K && this.field_70331_k.func_72798_a(this.field_70329_l, this.field_70330_m, this.field_70327_n) != 3590) {
         this.field_70331_k.func_72932_q(this.field_70329_l, this.field_70330_m, this.field_70327_n);
      }

      super.func_70307_a(par1nbtTagCompound);
      NBTTagCompound data = par1nbtTagCompound.func_74775_l("data");
      if(data != null) {
         this.hatID = data.func_74779_i("HatID");
      }

   }

   public void func_70310_b(NBTTagCompound par1nbtTagCompound) {
      super.func_70310_b(par1nbtTagCompound);
      NBTTagCompound data = new NBTTagCompound();
      data.func_74778_a("HatID", this.hatID);
      par1nbtTagCompound.func_74766_a("data", data);
   }

   public Packet func_70319_e() {
      NBTTagCompound data = new NBTTagCompound();
      this.func_70310_b(data);
      return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 3, data);
   }

   public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
      this.func_70307_a(pkt.field_73331_e);
   }

   public String getHatID() {
      return this.hatID;
   }

   public void setHatID(String hatID) {
      this.hatID = hatID;
   }
}
