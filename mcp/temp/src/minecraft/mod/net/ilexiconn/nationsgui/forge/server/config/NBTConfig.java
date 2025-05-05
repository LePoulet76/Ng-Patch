package net.ilexiconn.nationsgui.forge.server.config;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;

public class NBTConfig {

   public static final File FILE = new File(".", "nationsgui.dat");
   public static final NBTConfig CONFIG = get();
   private NBTTagCompound compound;


   public static NBTConfig get() {
      NBTConfig config = new NBTConfig();

      try {
         config.compound = CompressedStreamTools.func_74797_a(FILE);
      } catch (IOException var2) {
         System.err.println(var2.getLocalizedMessage());
      }

      if(config.compound == null) {
         config.compound = new NBTTagCompound();
      }

      config.setIfNull("RadioVolume", new NBTTagFloat("RadioVolume", 100.0F));
      config.setIfNull("RadioVolume", new NBTTagFloat("BrowserVolume", 100.0F));
      config.setIfNull("DatedScreenshot", new NBTTagByte("DatedScreenshot", (byte)0));
      config.setIfNull("Buddies", new NBTTagCompound("Buddies"));
      config.setIfNull("Aliases", new NBTTagCompound("Aliases"));
      config.setIfNull("Badges", new NBTTagCompound("Badges"));
      config.setIfNull("FactionChest", new NBTTagCompound("FactionChest"));
      return config;
   }

   private void setIfNull(String key, NBTBase value) {
      if(!this.compound.func_74764_b(key)) {
         this.compound.func_74782_a(key, value);
      }

   }

   public NBTTagCompound getCompound() {
      return this.compound;
   }

   public void save() {
      try {
         if(!FILE.exists()) {
            FILE.createNewFile();
         }

         CompressedStreamTools.func_74795_b(this.compound, FILE);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

}
