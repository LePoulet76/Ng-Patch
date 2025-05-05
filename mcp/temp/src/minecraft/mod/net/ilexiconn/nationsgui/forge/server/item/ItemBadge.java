package net.ilexiconn.nationsgui.forge.server.item;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBadge extends Item {

   public ItemBadge(int i) {
      super(i - 256);
   }

   public String func_77653_i(ItemStack par1ItemStack) {
      NBTTagCompound tagCompound = par1ItemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("BadgeID")) {
         String badgeID = tagCompound.func_74779_i("BadgeID");
         return I18n.func_135052_a("item.badge", new Object[]{NationsGUI.BADGES_NAMES.get(badgeID)});
      } else {
         return super.func_77653_i(par1ItemStack);
      }
   }

   public String func_77628_j(ItemStack par1ItemStack) {
      NBTTagCompound tagCompound = par1ItemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("BadgeID")) {
         String badgeID = tagCompound.func_74779_i("BadgeID");
         return I18n.func_135052_a("item.badge", new Object[]{NationsGUI.BADGES_NAMES.get(badgeID)});
      } else {
         return super.func_77628_j(par1ItemStack);
      }
   }
}
