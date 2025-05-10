package net.ilexiconn.nationsgui.forge.client.render.item;

import fr.nationsglory.ngupgrades.client.renderer.item.GenericItemSwordGeckoRenderer;
import fr.nationsglory.ngupgrades.common.item.GenericGeckoItemSword;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Halloween2024HammerRenderer extends GenericItemSwordGeckoRenderer
{
    public Halloween2024HammerRenderer(String name, String modId)
    {
        super(name, modId);
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GL11.glRotated(50.0D, 0.0D, 1.0D, 0.0D);

        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON))
        {
            GL11.glScalef(0.8F, 0.8F, 0.8F);
            GL11.glRotated(-160.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
            GL11.glTranslated(0.0D, -0.4D, -1.0D);
        }
        else if (type.equals(ItemRenderType.INVENTORY))
        {
            GL11.glScalef(0.6F, 0.6F, 0.6F);
            GL11.glRotated(-60.0D, 0.0D, 0.0D, 1.0D);
            GL11.glTranslated(-0.7D, -1.0D, 0.0D);
        }
        else if (type.equals(ItemRenderType.EQUIPPED))
        {
            GL11.glTranslated(-0.55D, 0.75D, 1.1D);
            GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(-90.0D, 0.0D, 0.0D, 1.0D);
        }
        else
        {
            GL11.glTranslated(-0.5D, -0.3D, 0.0D);
        }

        GL11.glPushMatrix();
        this.render((GenericGeckoItemSword)item.getItem(), item, Minecraft.getMinecraft().timer.renderPartialTicks);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
