/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.PotionEffect
 *  net.minecraftforge.common.EnumHelper
 */
package fr.nationsglory.remoteitem.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.renderer.texture.CustomAtlas;
import fr.nationsglory.remoteitem.common.data.EnchantData;
import fr.nationsglory.remoteitem.common.data.ItemData;
import fr.nationsglory.remoteitem.common.data.PotionEffectData;
import fr.nationsglory.remoteitem.common.item.CustomItem;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.EnumHelper;

public class CustomAxe
extends ItemAxe {
    private ItemData data;

    public CustomAxe(String name, ItemData data) {
        super(data.getId(), EnumHelper.addToolMaterial((String)name, (int)data.getHarvestLevel(), (int)data.getDurability(), (float)data.getMiningSpeed(), (float)(data.getDamageVsEntities() - 3.0f), (int)data.getEnchantability()));
        this.func_77655_b("remoteitem." + name);
        this.func_111206_d(name);
        this.data = data;
        RemoteItem.proxy.setCreativeTabItem((Item)this, data);
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

    @SideOnly(value=Side.CLIENT)
    public void func_94581_a(IconRegister par1IconRegister) {
        this.field_77791_bV = CustomAtlas.registerIcon(par1IconRegister, this.field_111218_cA);
    }

    public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.field_77993_c == this.data.getRepairItemID();
    }

    public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.func_77624_a(par1ItemStack, par2EntityPlayer, par3List, par4);
        CustomItem.generateDescription(par3List, (Item)this, this.data);
    }

    public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        for (PotionEffectData potionEffectData : this.data.getEffects()) {
            par2EntityLivingBase.func_70690_d(new PotionEffect(potionEffectData.getId(), potionEffectData.getDuration(), potionEffectData.getAmplifier()));
        }
        return super.func_77644_a(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return this.data.isGfxEffet() || super.hasEffect(par1ItemStack, pass);
    }

    public int getTooltipColor() {
        return this.data.getTooltipColor();
    }

    public int getTooltipBackgroundColor() {
        return this.data.getTooltipBackgroundColor();
    }
}

