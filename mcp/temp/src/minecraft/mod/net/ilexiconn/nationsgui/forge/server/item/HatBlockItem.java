package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.ilexiconn.nationsgui.forge.server.item.ICustomTooltip;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HatBlockItem extends ItemBlock implements ICustomTooltip {

   public HatBlockItem(int par1) {
      super(par1);
   }

   public String func_77667_c(ItemStack par1ItemStack) {
      NBTTagCompound tagCompound = par1ItemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("HatID")) {
         String hatID = tagCompound.func_74779_i("HatID");
         return "hat." + hatID;
      } else {
         return "hat.name.unknow";
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      NBTTagCompound tagCompound = par1ItemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("HatID")) {
         String hatID = tagCompound.func_74779_i("HatID");
         HatSkin var7 = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);
      }

      par3List.add(I18n.func_135053_a("item.hat.help"));
   }

   @SideOnly(Side.CLIENT)
   public int getTooltipColor(ItemStack itemStack) {
      NBTTagCompound tagCompound = itemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("HatID")) {
         String hatID = tagCompound.func_74779_i("HatID");
         HatSkin hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);
         if(hatSkin != null) {
            return -4671304;
         }
      }

      return 255;
   }

   public int getTooltipBackgroundColor(ItemStack itemStack) {
      NBTTagCompound tagCompound = itemStack.func_77978_p();
      if(tagCompound != null && tagCompound.func_74764_b("HatID")) {
         String hatID = tagCompound.func_74779_i("HatID");
         HatSkin var4 = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);
      }

      return 255;
   }
}
