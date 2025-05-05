package fr.nationsglory.remoteitem.common.item;

import com.google.common.collect.HashMultimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.renderer.texture.CustomAtlas;
import fr.nationsglory.remoteitem.common.data.EnchantData;
import fr.nationsglory.remoteitem.common.data.ItemData;
import fr.nationsglory.remoteitem.common.data.PotionEffectData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class CustomItem extends Item {

   private ItemData data;


   public CustomItem(String name, ItemData data) {
      super(data.getId());
      this.data = data;
      this.func_77655_b("remoteitem." + name);
      this.func_111206_d(name);
      this.func_77656_e(data.getDurability());
      this.func_77637_a(CreativeTabs.field_78027_g);
      this.func_77625_d(data.getStackSize());
      RemoteItem.proxy.setCreativeTabItem(this, data);
      this.field_77789_bW = data.isFull3D();
   }

   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      super.func_77624_a(par1ItemStack, par2EntityPlayer, par3List, par4);
      generateDescription(par3List, this, this.data);
   }

   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
      return par2ItemStack.field_77993_c == this.data.getRepairItemID();
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

   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
      return this.data.isGfxEffet();
   }

   public static void generateDescription(List list, Item item, ItemData data) {
      if(!data.getDescription().isEmpty()) {
         list.add(StatCollector.func_74838_a(item.func_77658_a() + ".description").trim());
      }

      ArrayList list1 = new ArrayList();
      Iterator hashmultimap = data.getEffects().iterator();

      while(hashmultimap.hasNext()) {
         PotionEffectData iterator = (PotionEffectData)hashmultimap.next();
         list1.add(new PotionEffect(iterator.getId(), iterator.getDuration(), iterator.getAmplifier()));
      }

      HashMultimap hashmultimap1 = HashMultimap.create();
      Iterator iterator1;
      if(list1 != null && !list1.isEmpty()) {
         iterator1 = list1.iterator();

         while(iterator1.hasNext()) {
            PotionEffect entry1 = (PotionEffect)iterator1.next();
            String attributemodifier2 = StatCollector.func_74838_a(entry1.func_76453_d()).trim();
            Potion d0 = Potion.field_76425_a[entry1.func_76456_a()];
            Map map = d0.func_111186_k();
            if(map != null && map.size() > 0) {
               Iterator d1 = map.entrySet().iterator();

               while(d1.hasNext()) {
                  Entry entry = (Entry)d1.next();
                  AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.func_111166_b(), d0.func_111183_a(entry1.func_76458_c(), attributemodifier), attributemodifier.func_111169_c());
                  hashmultimap1.put(((Attribute)entry.getKey()).func_111108_a(), attributemodifier1);
               }
            }

            if(entry1.func_76458_c() > 0) {
               attributemodifier2 = attributemodifier2 + " " + StatCollector.func_74838_a("potion.potency." + entry1.func_76458_c()).trim();
            }

            if(entry1.func_76459_b() > 20) {
               attributemodifier2 = attributemodifier2 + " (" + Potion.func_76389_a(entry1) + ")";
            }

            if(d0.func_76398_f()) {
               list.add(EnumChatFormatting.RED + attributemodifier2);
            } else {
               list.add(EnumChatFormatting.GRAY + attributemodifier2);
            }
         }
      }

      if(!hashmultimap1.isEmpty()) {
         list.add("");
         list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a("potion.effects.whenDrank"));
         iterator1 = hashmultimap1.entries().iterator();

         while(iterator1.hasNext()) {
            Entry entry11 = (Entry)iterator1.next();
            AttributeModifier attributemodifier21 = (AttributeModifier)entry11.getValue();
            double d01 = attributemodifier21.func_111164_d();
            double d11;
            if(attributemodifier21.func_111169_c() != 1 && attributemodifier21.func_111169_c() != 2) {
               d11 = attributemodifier21.func_111164_d();
            } else {
               d11 = attributemodifier21.func_111164_d() * 100.0D;
            }

            if(d01 > 0.0D) {
               list.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("attribute.modifier.plus." + attributemodifier21.func_111169_c(), new Object[]{ItemStack.field_111284_a.format(d11), StatCollector.func_74838_a("attribute.name." + (String)entry11.getKey())}));
            } else if(d01 < 0.0D) {
               d11 *= -1.0D;
               list.add(EnumChatFormatting.RED + StatCollector.func_74837_a("attribute.modifier.take." + attributemodifier21.func_111169_c(), new Object[]{ItemStack.field_111284_a.format(d11), StatCollector.func_74838_a("attribute.name." + (String)entry11.getKey())}));
            }
         }
      }

   }

   public int getTooltipColor() {
      return this.data.getTooltipColor();
   }

   public int getTooltipBackgroundColor() {
      return this.data.getTooltipBackgroundColor();
   }
}
