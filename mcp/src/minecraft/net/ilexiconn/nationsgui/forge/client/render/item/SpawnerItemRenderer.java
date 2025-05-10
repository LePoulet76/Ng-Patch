package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.render.item.SpawnerItemRenderer$1;
import net.ilexiconn.nationsgui.forge.server.item.MobSpawnerItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SpawnerItemRenderer implements IItemRenderer
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
        switch (SpawnerItemRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
        {
            case 1:
            case 2:
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                this.renderInventoryItem((RenderBlocks)data[0], item);
                break;

            case 3:
                this.renderInventoryItem((RenderBlocks)data[0], item);
                break;

            case 4:
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                this.renderInventoryItem((RenderBlocks)data[0], item);
        }
    }

    public void renderInventoryItem(RenderBlocks render, ItemStack item)
    {
        int meta = item.getItemDamage();

        if (meta == 0)
        {
            meta = 90;
        }

        String bossName = BossStatus.bossName;
        int bossTimeout = BossStatus.statusBarLength;

        try
        {
            WorldClient e = Minecraft.getMinecraft().theWorld;
            render.renderBlockAsItem(Block.mobSpawner, 0, 1.0F);
            GL11.glPushMatrix();
            EntityLiving entity = MobSpawnerItem.getEntity(meta, e);

            if (entity != null)
            {
                entity.setWorld(e);
                float f1 = 0.4375F;

                if ((double)entity.distanceWalkedOnStepModified > 1.5D)
                {
                    f1 = 0.1F;
                }

                GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                entity.setLocationAndAngles(0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            }

            GL11.glPopMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
        catch (Exception var9)
        {
            if (Tessellator.instance.isDrawing)
            {
                Tessellator.instance.draw();
            }
        }

        BossStatus.bossName = bossName;
        BossStatus.statusBarLength = bossTimeout;
    }
}
