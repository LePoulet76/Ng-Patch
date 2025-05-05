package fr.nationsglory.remoteitem.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.renderer.texture.CustomAtlas;
import fr.nationsglory.remoteitem.common.data.EnchantData;
import fr.nationsglory.remoteitem.common.data.ItemData;
import fr.nationsglory.remoteitem.common.data.PotionEffectData;
import fr.nationsglory.remoteitem.common.item.CustomItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CustomPotion extends ItemPotion {

   ItemData data;


   public CustomPotion(String name, ItemData data) {
      super(data.getId());
      this.func_77655_b("remoteitem." + name);
      this.func_111206_d(name);
      this.data = data;
      this.func_77627_a(false);
      RemoteItem.proxy.setCreativeTabItem(this, data);
   }

   public String func_77628_j(ItemStack par1ItemStack) {
      return ("" + StatCollector.func_74838_a(this.func_77657_g(par1ItemStack) + ".name")).trim();
   }

   public List func_77834_f(int par1) {
      ArrayList potionEffectList = new ArrayList();
      Iterator var3 = this.data.getEffects().iterator();

      while(var3.hasNext()) {
         PotionEffectData effectData = (PotionEffectData)var3.next();
         potionEffectList.add(new PotionEffect(effectData.getId(), effectData.getDuration(), effectData.getAmplifier()));
      }

      return potionEffectList;
   }

   public List func_77832_l(ItemStack par1ItemStack) {
      return this.func_77834_f(0);
   }

   public ItemStack func_77654_b(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par3EntityPlayer.field_71075_bZ.field_75098_d) {
         --par1ItemStack.field_77994_a;
      }

      if(!par2World.field_72995_K) {
         List list = this.func_77832_l(par1ItemStack);
         if(list != null) {
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
               PotionEffect potioneffect = (PotionEffect)iterator.next();
               par3EntityPlayer.func_70690_d(new PotionEffect(potioneffect));
            }
         }
      }

      if(!par3EntityPlayer.field_71075_bZ.field_75098_d && this.data.getPotionCusumedID() != 0) {
         if(par1ItemStack.field_77994_a <= 0) {
            return new ItemStack(Item.field_77698_e[this.data.getPotionCusumedID()]);
         }

         par3EntityPlayer.field_71071_by.func_70441_a(new ItemStack(Item.field_77698_e[this.data.getPotionCusumedID()]));
      }

      return par1ItemStack;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = CustomAtlas.registerIcon(par1IconRegister, this.field_111218_cA);
   }

   @SideOnly(Side.CLIENT)
   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
      return this.data.isGfxEffet();
   }

   public void func_77633_a(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      ItemStack itemStack = new ItemStack(par1, 1, 0);
      if(!this.data.getEnchants().isEmpty()) {
         Iterator var5 = this.data.getEnchants().iterator();

         while(var5.hasNext()) {
            EnchantData enchantData = (EnchantData)var5.next();
            itemStack.func_77966_a(Enchantment.field_77331_b[enchantData.getId()], enchantData.getLevel());
         }
      }

      par3List.add(itemStack);
   }

   @SideOnly(Side.CLIENT)
   public Icon func_77617_a(int par1) {
      return this.field_77791_bV;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_77618_c(int par1, int par2) {
      return this.field_77791_bV;
   }

   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      super.func_77624_a(par1ItemStack, par2EntityPlayer, par3List, par4);
      CustomItem.generateDescription(par3List, this, this.data);
   }

   public int getTooltipColor() {
      return this.data.getTooltipColor();
   }

   public int getTooltipBackgroundColor() {
      return this.data.getTooltipBackgroundColor();
   }
}
