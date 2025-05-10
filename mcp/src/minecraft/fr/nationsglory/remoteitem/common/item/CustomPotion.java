package fr.nationsglory.remoteitem.common.item;

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

public class CustomPotion extends ItemPotion
{
    ItemData data;

    public CustomPotion(String name, ItemData data)
    {
        super(data.getId());
        this.setUnlocalizedName("remoteitem." + name);
        this.setTextureName(name);
        this.data = data;
        this.setHasSubtypes(false);
        RemoteItem.proxy.setCreativeTabItem(this, data);
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List getEffects(int par1)
    {
        ArrayList potionEffectList = new ArrayList();
        Iterator var3 = this.data.getEffects().iterator();

        while (var3.hasNext())
        {
            PotionEffectData effectData = (PotionEffectData)var3.next();
            potionEffectList.add(new PotionEffect(effectData.getId(), effectData.getDuration(), effectData.getAmplifier()));
        }

        return potionEffectList;
    }

    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    public List getEffects(ItemStack par1ItemStack)
    {
        return this.getEffects(0);
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        if (!par2World.isRemote)
        {
            List list = this.getEffects(par1ItemStack);

            if (list != null)
            {
                Iterator iterator = list.iterator();

                while (iterator.hasNext())
                {
                    PotionEffect potioneffect = (PotionEffect)iterator.next();
                    par3EntityPlayer.addPotionEffect(new PotionEffect(potioneffect));
                }
            }
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode && this.data.getPotionCusumedID() != 0)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.itemsList[this.data.getPotionCusumedID()]);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.itemsList[this.data.getPotionCusumedID()]));
        }

        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = CustomAtlas.registerIcon(par1IconRegister, this.iconString);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return this.data.isGfxEffet();
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

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.itemIcon;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        CustomItem.generateDescription(par3List, this, this.data);
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
