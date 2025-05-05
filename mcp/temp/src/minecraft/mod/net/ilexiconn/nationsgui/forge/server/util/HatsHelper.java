package net.ilexiconn.nationsgui.forge.server.util;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HatsHelper {

   public static ItemStack generateHatItem(String hatID) {
      ItemStack itemHat = new ItemStack(NationsGUI.HATBLOCK.field_71990_ca, 1, 0);
      if(!itemHat.func_77942_o()) {
         itemHat.field_77990_d = new NBTTagCompound();
      }

      itemHat.field_77990_d.func_74778_a("HatID", hatID);
      return itemHat;
   }
}
