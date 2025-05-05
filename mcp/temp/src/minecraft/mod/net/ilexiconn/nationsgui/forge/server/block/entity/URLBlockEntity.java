package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.minecraft.nbt.NBTTagCompound;

public class URLBlockEntity extends BlockEntity {

   public String url = "";


   public void saveNBTData(NBTTagCompound compound) {
      compound.func_74778_a("url", this.url);
   }

   public void loadNBTData(NBTTagCompound compound) {
      this.url = compound.func_74779_i("url");
   }

   public String getURL() {
      return this.url;
   }
}
