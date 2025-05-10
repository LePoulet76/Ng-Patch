package net.ilexiconn.nationsgui.forge.client.gui.shop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.util.JsonToNBT;
import net.ilexiconn.nationsgui.forge.server.json.CategoryItemJSON;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class CategoryItem
{
    private CategoryItemJSON container;
    private int index;
    private ResourceLocation resourceLocation;
    private ThreadDownloadImageData imageData;
    private ResourceLocation resourceLocationPreview;
    private ThreadDownloadImageData imageDataPreview;
    private ItemStack item;

    public CategoryItem(ShopGUI gui, Category category, CategoryItemJSON container, int index)
    {
        this.container = container;
        this.index = index;

        if (container.image != null)
        {
            this.resourceLocation = new ResourceLocation("item_icon/" + category.getName() + index);
            this.imageData = gui.getDownloadImage(this.resourceLocation, container.image);
        }

        if (container.imagePreview != null)
        {
            this.resourceLocationPreview = new ResourceLocation("item_icon_preview/" + category.getName() + index);
            this.imageDataPreview = gui.getDownloadImage(this.resourceLocationPreview, container.imagePreview);
        }

        if (container.id != 0)
        {
            this.item = new ItemStack(container.id, 1, container.metadata);

            if (container.nbt != null)
            {
                try
                {
                    this.item.setTagCompound(JsonToNBT.getTagFromJson(container.nbt.replace("${username}", Minecraft.getMinecraft().thePlayer.username)));
                }
                catch (Exception var7)
                {
                    var7.printStackTrace();
                }
            }

            if (container.nbtItem != null)
            {
                NBTTagCompound tag = this.item.getTagCompound() == null ? new NBTTagCompound("tag") : this.item.getTagCompound();
                String[] kv = container.nbtItem.split(":");
                tag.setString(kv[0], kv[1]);
                this.item.setTagCompound(tag);

                if (container.name != null)
                {
                    this.item.setItemName("\u00a7c" + container.name);
                }
            }
        }
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public int getPage()
    {
        return this.index / 36;
    }

    public int getX()
    {
        return this.index % 9;
    }

    public int getY()
    {
        return this.index > 35 ? this.index % 36 / 9 : this.index / 9;
    }

    public boolean isIconLoaded()
    {
        return this.imageData.isTextureUploaded();
    }

    public boolean isPreviewIconLoaded()
    {
        return this.imageDataPreview.isTextureUploaded();
    }

    public ResourceLocation getIcon()
    {
        return this.resourceLocation;
    }

    public ResourceLocation getPreviewIcon()
    {
        return this.resourceLocationPreview;
    }

    public int getID()
    {
        return this.container.id;
    }

    public String getName()
    {
        return this.container.name;
    }

    public int getMetadata()
    {
        return this.container.metadata;
    }

    public double getPrice()
    {
        if (ClientProxy.serverType.equals("build"))
        {
            return 0.0D;
        }
        else
        {
            double price = this.container.price;

            if (ClientData.bonuses.containsKey("catalog") && !((Float)ClientData.bonuses.get("catalog")).equals(Float.valueOf(0.0F)))
            {
                price *= (double)(1.0F - (((Float)ClientData.bonuses.get("catalog")).floatValue() - 1.0F));
            }

            return price;
        }
    }

    public String getDescription()
    {
        return this.container.description;
    }

    public int getMaxAmount()
    {
        return this.container.maxAmount;
    }

    public int getIndex()
    {
        return this.index;
    }
}
