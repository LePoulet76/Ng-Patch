package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class ItemSkinRenderer implements IItemRenderer
{
    private final ItemSkinModel itemSkinModel;

    public ItemSkinRenderer(ItemSkinModel itemSkinModel)
    {
        this.itemSkinModel = itemSkinModel;
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        if (this.itemSkinModel.getModel() != null)
        {
            Transform transform = type == ItemRenderType.EQUIPPED_FIRST_PERSON ? this.itemSkinModel.getTransformFirstPerson() : this.itemSkinModel.getTransformEntity();
            GL11.glPushMatrix();
            transform.applyGL();
            this.itemSkinModel.getModel().render();
            GL11.glPopMatrix();
        }
    }
}
