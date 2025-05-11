/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.attributes.Attribute
 *  net.minecraft.entity.ai.attributes.AttributeModifier
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.StatCollector
 */
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
import java.util.List;
import java.util.Map;
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

public class CustomItem
extends Item {
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
        CustomItem.generateDescription(par3List, this, this.data);
    }

    public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.field_77993_c == this.data.getRepairItemID();
    }

    @SideOnly(value=Side.CLIENT)
    public void func_94581_a(IconRegister par1IconRegister) {
        this.field_77791_bV = CustomAtlas.registerIcon(par1IconRegister, this.field_111218_cA);
    }

    public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        for (PotionEffectData potionEffectData : this.data.getEffects()) {
            par2EntityLivingBase.func_70690_d(new PotionEffect(potionEffectData.getId(), potionEffectData.getDuration(), potionEffectData.getAmplifier()));
        }
        return super.func_77644_a(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }

    public void func_77633_a(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        ItemStack itemStack = new ItemStack(par1, 1, 0);
        if (!this.data.getEnchants().isEmpty()) {
            for (EnchantData enchantData : this.data.getEnchants()) {
                itemStack.func_77966_a(Enchantment.field_77331_b[enchantData.getId()], enchantData.getLevel());
            }
        }
        par3List.add(itemStack);
    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return this.data.isGfxEffet();
    }

    public static void generateDescription(List list, Item item, ItemData data) {
        if (!data.getDescription().isEmpty()) {
            list.add(StatCollector.func_74838_a((String)(item.func_77658_a() + ".description")).trim());
        }
        ArrayList<PotionEffect> list1 = new ArrayList<PotionEffect>();
        for (PotionEffectData potionEffectData : data.getEffects()) {
            list1.add(new PotionEffect(potionEffectData.getId(), potionEffectData.getDuration(), potionEffectData.getAmplifier()));
        }
        HashMultimap hashmultimap = HashMultimap.create();
        if (list1 != null && !list1.isEmpty()) {
            for (PotionEffect potioneffect : list1) {
                String s = StatCollector.func_74838_a((String)potioneffect.func_76453_d()).trim();
                Potion potion = Potion.field_76425_a[potioneffect.func_76456_a()];
                Map map = potion.func_111186_k();
                if (map != null && map.size() > 0) {
                    for (Map.Entry entry : map.entrySet()) {
                        AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.func_111166_b(), potion.func_111183_a(potioneffect.func_76458_c(), attributemodifier), attributemodifier.func_111169_c());
                        hashmultimap.put((Object)((Attribute)entry.getKey()).func_111108_a(), (Object)attributemodifier1);
                    }
                }
                if (potioneffect.func_76458_c() > 0) {
                    s = s + " " + StatCollector.func_74838_a((String)("potion.potency." + potioneffect.func_76458_c())).trim();
                }
                if (potioneffect.func_76459_b() > 20) {
                    s = s + " (" + Potion.func_76389_a((PotionEffect)potioneffect) + ")";
                }
                if (potion.func_76398_f()) {
                    list.add(EnumChatFormatting.RED + s);
                    continue;
                }
                list.add(EnumChatFormatting.GRAY + s);
            }
        }
        if (!hashmultimap.isEmpty()) {
            list.add("");
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a((String)"potion.effects.whenDrank"));
            for (Map.Entry entry1 : hashmultimap.entries()) {
                AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
                double d0 = attributemodifier2.func_111164_d();
                double d1 = attributemodifier2.func_111169_c() != 1 && attributemodifier2.func_111169_c() != 2 ? attributemodifier2.func_111164_d() : attributemodifier2.func_111164_d() * 100.0;
                if (d0 > 0.0) {
                    list.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a((String)("attribute.modifier.plus." + attributemodifier2.func_111169_c()), (Object[])new Object[]{ItemStack.field_111284_a.format(d1), StatCollector.func_74838_a((String)("attribute.name." + (String)entry1.getKey()))}));
                    continue;
                }
                if (!(d0 < 0.0)) continue;
                list.add(EnumChatFormatting.RED + StatCollector.func_74837_a((String)("attribute.modifier.take." + attributemodifier2.func_111169_c()), (Object[])new Object[]{ItemStack.field_111284_a.format(d1 *= -1.0), StatCollector.func_74838_a((String)("attribute.name." + (String)entry1.getKey()))}));
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

