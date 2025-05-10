package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemBadgeRenderer implements IItemRenderer
{
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        if (item.getTagCompound() != null)
        {
            String badgeType = item.getTagCompound().getString("BadgeID");
            Tessellator tessellator = Tessellator.instance;
            byte par5 = 16;
            byte par4 = 16;
            Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(badgeType));
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D, (double)par5, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV((double)par4, (double)par5, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV((double)par4, 0.0D, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
        }
    }
}
