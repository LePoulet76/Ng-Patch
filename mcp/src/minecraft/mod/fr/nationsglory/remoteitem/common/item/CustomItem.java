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

public class CustomItem extends Item
{
    private ItemData data;

    public CustomItem(String name, ItemData data)
    {
        super(data.getId());
        this.data = data;
        this.setUnlocalizedName("remoteitem." + name);
        this.setTextureName(name);
        this.setMaxDamage(data.getDurability());
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setMaxStackSize(data.getStackSize());
        RemoteItem.proxy.setCreativeTabItem(this, data);
        this.bFull3D = data.isFull3D();
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        generateDescription(par3List, this, this.data);
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.itemID == this.data.getRepairItemID();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = CustomAtlas.registerIcon(par1IconRegister, this.iconString);
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

    public static void generateDescription(List list, Item item, ItemData data)
    {
        if (!data.getDescription().isEmpty())
        {
            list.add(StatCollector.translateToLocal(item.getUnlocalizedName() + ".description").trim());
        }

        ArrayList list1 = new ArrayList();
        Iterator hashmultimap = data.getEffects().iterator();

        while (hashmultimap.hasNext())
        {
            PotionEffectData iterator = (PotionEffectData)hashmultimap.next();
            list1.add(new PotionEffect(iterator.getId(), iterator.getDuration(), iterator.getAmplifier()));
        }

        HashMultimap hashmultimap1 = HashMultimap.create();
        Iterator iterator1;

        if (list1 != null && !list1.isEmpty())
        {
            iterator1 = list1.iterator();

            while (iterator1.hasNext())
            {
                PotionEffect entry1 = (PotionEffect)iterator1.next();
                String attributemodifier2 = StatCollector.translateToLocal(entry1.getEffectName()).trim();
                Potion d0 = Potion.potionTypes[entry1.getPotionID()];
                Map map = d0.func_111186_k();

                if (map != null && map.size() > 0)
                {
                    Iterator d1 = map.entrySet().iterator();

                    while (d1.hasNext())
                    {
                        Entry entry = (Entry)d1.next();
                        AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), d0.func_111183_a(entry1.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        hashmultimap1.put(((Attribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
                    }
                }

                if (entry1.getAmplifier() > 0)
                {
                    attributemodifier2 = attributemodifier2 + " " + StatCollector.translateToLocal("potion.potency." + entry1.getAmplifier()).trim();
                }

                if (entry1.getDuration() > 20)
                {
                    attributemodifier2 = attributemodifier2 + " (" + Potion.getDurationString(entry1) + ")";
                }

                if (d0.isBadEffect())
                {
                    list.add(EnumChatFormatting.RED + attributemodifier2);
                }
                else
                {
                    list.add(EnumChatFormatting.GRAY + attributemodifier2);
                }
            }
        }

        if (!hashmultimap1.isEmpty())
        {
            list.add("");
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
            iterator1 = hashmultimap1.entries().iterator();

            while (iterator1.hasNext())
            {
                Entry entry11 = (Entry)iterator1.next();
                AttributeModifier attributemodifier21 = (AttributeModifier)entry11.getValue();
                double d01 = attributemodifier21.getAmount();
                double d11;

                if (attributemodifier21.getOperation() != 1 && attributemodifier21.getOperation() != 2)
                {
                    d11 = attributemodifier21.getAmount();
                }
                else
                {
                    d11 = attributemodifier21.getAmount() * 100.0D;
                }

                if (d01 > 0.0D)
                {
                    list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier21.getOperation(), new Object[] {ItemStack.field_111284_a.format(d11), StatCollector.translateToLocal("attribute.name." + (String)entry11.getKey())}));
                }
                else if (d01 < 0.0D)
                {
                    d11 *= -1.0D;
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier21.getOperation(), new Object[] {ItemStack.field_111284_a.format(d11), StatCollector.translateToLocal("attribute.name." + (String)entry11.getKey())}));
                }
            }
        }
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
