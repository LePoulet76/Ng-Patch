package fr.nationsglory.remoteitem.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.renderer.texture.CustomAtlas;
import fr.nationsglory.remoteitem.common.data.EnchantData;
import fr.nationsglory.remoteitem.common.data.ItemData;
import fr.nationsglory.remoteitem.common.data.PotionEffectData;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.EnumHelper;

public class CustomPickaxe extends ItemPickaxe
{
    private ItemData data;

    public CustomPickaxe(String name, ItemData data)
    {
        super(data.getId(), EnumHelper.addToolMaterial(name, data.getHarvestLevel(), data.getDurability(), data.getMiningSpeed(), data.getDamageVsEntities() - 2.0F, data.getEnchantability()));
        this.setUnlocalizedName("remoteitem." + name);
        this.setTextureName(name);
        this.data = data;
        RemoteItem.proxy.setCreativeTabItem(this, data);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = CustomAtlas.registerIcon(par1IconRegister, this.iconString);
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.itemID == this.data.getRepairItemID();
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        CustomItem.generateDescription(par3List, this, this.data);
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        Iterator var4 = this.data.getEffects().iterator();

        while (var4.hasNext())
        {
            PotionEffectData potionEffectData = (PotionEffectData)var4.next();
            par2EntityLivingBase.addPotionEffect(new PotionEffect(potionEffectData.getId(), potionEffectData.getDuration(), potionEffectData.getAmplifier()));
        }

        return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        ItemStack itemStack = new ItemStack(par1, 1, 0);

        if (!this.data.getEnchants().isEmpty())
        {
            Iterator var5 = this.data.getEnchants().iterator();

            while (var5.hasNext())
            {
                EnchantData enchantData = (EnchantData)var5.next();
                itemStack.addEnchantment(Enchantment.enchantmentsList[enchantData.getId()], enchantData.getLevel());
            }
        }

        par3List.add(itemStack);
    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return this.data.isGfxEffet();
    }

    public int getTooltipColor()
    {
        return this.data.getTooltipColor();
    }

    public int getTooltipBackgroundColor()
    {
        return this.data.getTooltipBackgroundColor();
    }
}
