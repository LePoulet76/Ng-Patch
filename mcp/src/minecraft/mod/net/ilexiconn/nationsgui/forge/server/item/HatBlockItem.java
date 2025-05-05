package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HatBlockItem extends ItemBlock implements ICustomTooltip
{
    public HatBlockItem(int par1)
    {
        super(par1);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        NBTTagCompound tagCompound = par1ItemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("HatID"))
        {
            String hatID = tagCompound.getString("HatID");
            return "hat." + hatID;
        }
        else
        {
            return "hat.name.unknow";
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        NBTTagCompound tagCompound = par1ItemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("HatID"))
        {
            String hatID = tagCompound.getString("HatID");
            HatSkin var7 = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);
        }

        par3List.add(I18n.getString("item.hat.help"));
    }

    @SideOnly(Side.CLIENT)
    public int getTooltipColor(ItemStack itemStack)
    {
        NBTTagCompound tagCompound = itemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("HatID"))
        {
            String hatID = tagCompound.getString("HatID");
            HatSkin hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);

            if (hatSkin != null)
            {
                return -4671304;
            }
        }

        return 255;
    }

    public int getTooltipBackgroundColor(ItemStack itemStack)
    {
        NBTTagCompound tagCompound = itemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("HatID"))
        {
            String hatID = tagCompound.getString("HatID");
            HatSkin var4 = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(hatID);
        }

        return 255;
    }
}
