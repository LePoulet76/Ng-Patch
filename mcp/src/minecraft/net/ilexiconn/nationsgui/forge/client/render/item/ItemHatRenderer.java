package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.ilexiconn.nationsgui.forge.client.render.item.ItemHatRenderer$1;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class ItemHatRenderer implements IItemRenderer
{
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        switch (ItemHatRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
        {
            case 1:
                GL11.glTranslatef(0.0F, 0.5F, 0.2F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                this.renderHat(item);
                break;

            case 2:
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.8F, 0.0F, -0.3F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                this.renderHat(item);
                break;

            case 3:
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.4F, 0.2F, -0.3F);
                this.renderHat(item);
                break;

            case 4:
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                this.renderHat(item);
        }
    }

    private void renderHat(ItemStack item)
    {
        if (item.hasTagCompound() && item.stackTagCompound.hasKey("HatID") && ClientProxy.SKIN_MANAGER.getSkinFromID(item.stackTagCompound.getString("HatID")) != null)
        {
            HatSkin hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(item.stackTagCompound.getString("HatID"));
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            if (hatSkin != null)
            {
                hatSkin.getModel().updateModel(hatSkin.getTransform("entity"));
                hatSkin.getModel().render(0.0F);
            }
        }
    }
}
