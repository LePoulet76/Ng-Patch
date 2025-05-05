package fr.nationsglory.remoteitem.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.renderer.texture.CustomAtlas;
import fr.nationsglory.remoteitem.common.data.EnchantData;
import fr.nationsglory.remoteitem.common.data.ItemData;
import fr.nationsglory.remoteitem.common.data.PotionEffectData;
import fr.nationsglory.remoteitem.common.item.CustomItem;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class CustomFood extends ItemFood {

   private ItemData data;


   public CustomFood(String name, ItemData data) {
      super(data.getId(), data.getFeedAmount(), data.getSaturationAmplifier(), data.isWolfsFavoriteMeat());
      this.func_77655_b("remoteitem." + name);
      this.func_111206_d(name);
      this.func_77625_d(data.getStackSize());
      this.data = data;
      RemoteItem.proxy.setCreativeTabItem(this, data);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = CustomAtlas.registerIcon(par1IconRegister, this.field_111218_cA);
   }

   public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
      Iterator var4 = this.data.getEffects().iterator();

      while(var4.hasNext()) {
         PotionEffectData potionEffectData = (PotionEffectData)var4.next();
         par2EntityLivingBase.func_70690_d(new PotionEffect(potionEffectData.getId(), potionEffectData.getDuration(), potionEffectData.getAmplifier()));
      }

      return super.func_77644_a(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
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

   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      super.func_77624_a(par1ItemStack, par2EntityPlayer, par3List, par4);
      CustomItem.generateDescription(par3List, this, this.data);
   }

   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
      return this.data.isGfxEffet();
   }

   public int getTooltipColor() {
      return this.data.getTooltipColor();
   }

   public int getTooltipBackgroundColor() {
      return this.data.getTooltipBackgroundColor();
   }
}
