/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.boss.BossStatus
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.server.item.MobSpawnerItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class SpawnerItemRenderer
implements IItemRenderer {
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case EQUIPPED: 
            case EQUIPPED_FIRST_PERSON: {
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                this.renderInventoryItem((RenderBlocks)data[0], item);
                break;
            }
            case INVENTORY: {
                this.renderInventoryItem((RenderBlocks)data[0], item);
                break;
            }
            case ENTITY: {
                GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                this.renderInventoryItem((RenderBlocks)data[0], item);
            }
        }
    }

    public void renderInventoryItem(RenderBlocks render, ItemStack item) {
        int bossTimeout;
        String bossName;
        block5: {
            int meta = item.func_77960_j();
            if (meta == 0) {
                meta = 90;
            }
            bossName = BossStatus.field_82827_c;
            bossTimeout = BossStatus.field_82826_b;
            try {
                WorldClient world = Minecraft.func_71410_x().field_71441_e;
                render.func_78600_a(Block.field_72065_as, 0, 1.0f);
                GL11.glPushMatrix();
                EntityLiving entity = MobSpawnerItem.getEntity(meta, (World)world);
                if (entity != null) {
                    entity.func_70029_a((World)world);
                    float f1 = 0.4375f;
                    if ((double)entity.field_82151_R > 1.5) {
                        f1 = 0.1f;
                    }
                    GL11.glRotatef((float)-20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.4f, (float)0.0f);
                    GL11.glScalef((float)f1, (float)f1, (float)f1);
                    entity.func_70012_b(0.0, 0.0, 0.0, 0.0f, 0.0f);
                    RenderManager.field_78727_a.func_78719_a((Entity)entity, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                }
                GL11.glPopMatrix();
                GL11.glEnable((int)32826);
                OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77476_b);
                GL11.glDisable((int)3553);
                OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77478_a);
            }
            catch (Exception e) {
                if (!Tessellator.field_78398_a.field_78415_z) break block5;
                Tessellator.field_78398_a.func_78381_a();
            }
        }
        BossStatus.field_82827_c = bossName;
        BossStatus.field_82826_b = bossTimeout;
    }
}

