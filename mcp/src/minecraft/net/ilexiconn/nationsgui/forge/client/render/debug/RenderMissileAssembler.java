/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  icbm.explosion.ex.Ex
 *  icbm.explosion.explosive.ExplosiveRegistry
 *  icbm.explosion.machines.TileMissileAssembler
 *  icbm.explosion.model.tiles.ModelMissileAssemblerClaw
 *  icbm.explosion.model.tiles.ModelMissileAssemblerPanel
 *  icbm.explosion.render.entity.RenderMissile
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.IModelCustom
 *  net.minecraftforge.common.ForgeDirection
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.debug;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.explosion.ex.Ex;
import icbm.explosion.explosive.ExplosiveRegistry;
import icbm.explosion.machines.TileMissileAssembler;
import icbm.explosion.model.tiles.ModelMissileAssemblerClaw;
import icbm.explosion.model.tiles.ModelMissileAssemblerPanel;
import icbm.explosion.render.entity.RenderMissile;
import java.util.HashMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderMissileAssembler
extends TileEntitySpecialRenderer {
    public static final ResourceLocation TEXTURE_FILE = new ResourceLocation("icbm", "textures/models/missileAssembler.png");
    public static final ModelMissileAssemblerPanel MODEL_PANEL = new ModelMissileAssemblerPanel();
    public static final ModelMissileAssemblerClaw MODEL_CLAW1 = new ModelMissileAssemblerClaw(-2);
    public static final ModelMissileAssemblerClaw MODEL_CLAW2 = new ModelMissileAssemblerClaw(12);
    public static final ModelMissileAssemblerClaw MODEL_CLAW3 = new ModelMissileAssemblerClaw(-16);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renderAModelAt(TileMissileAssembler tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        this.func_110628_a(TEXTURE_FILE);
        GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 1.5f), (float)((float)z + 0.5f));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        if (tileEntity.placedSide != ForgeDirection.UP && tileEntity.placedSide != ForgeDirection.DOWN) {
            if (tileEntity.placedSide == ForgeDirection.EAST) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)1.0f, (float)-1.0f, (float)0.0f);
            } else if (tileEntity.placedSide == ForgeDirection.WEST) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glTranslatef((float)1.0f, (float)-1.0f, (float)0.0f);
                if (tileEntity.rotationSide == 0) {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                } else if (tileEntity.rotationSide == 1) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                } else if (tileEntity.rotationSide == 2) {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
            } else if (tileEntity.placedSide == ForgeDirection.NORTH) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glTranslatef((float)1.0f, (float)-1.0f, (float)0.0f);
            } else if (tileEntity.placedSide == ForgeDirection.SOUTH) {
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)-1.0f, (float)1.0f);
                if (tileEntity.rotationSide == 1) {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                } else if (tileEntity.rotationSide == 2) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                } else if (tileEntity.rotationSide == 3) {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
            }
        } else {
            if (tileEntity.rotationSide == 1) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (tileEntity.rotationSide == 2) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (tileEntity.rotationSide == 3) {
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (tileEntity.placedSide == ForgeDirection.DOWN) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glTranslatef((float)0.0f, (float)-2.0f, (float)0.0f);
            }
        }
        MODEL_PANEL.render(0.0625f);
        MODEL_CLAW1.render(0.0625f);
        MODEL_CLAW2.render(0.0625f);
        MODEL_CLAW3.render(0.0625f);
        if (tileEntity.missileID >= 0) {
            Ex missile = (Ex)ExplosiveRegistry.get((int)tileEntity.missileID);
            float scale = 0.8f;
            float right = 1.0f;
            if (missile.getTier() == 2 || !missile.hasBlockForm() || missile.getTier() == 3 || missile.getTier() == 4) {
                // empty if block
            }
            GL11.glTranslatef((float)right, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glScalef((float)scale, (float)scale, (float)scale);
            GL11.glTranslatef((float)1.0f, (float)0.0f, (float)0.0f);
            FMLClientHandler.instance().getClient().field_71446_o.func_110577_a(missile.getMissileResource());
            HashMap var12 = RenderMissile.cache;
            HashMap hashMap = RenderMissile.cache;
            synchronized (hashMap) {
                if (!RenderMissile.cache.containsKey(missile)) {
                    RenderMissile.cache.put(missile, missile.getMissileModel());
                }
                ((IModelCustom)RenderMissile.cache.get(missile)).renderAll();
            }
        }
        GL11.glPopMatrix();
    }

    public void func_76894_a(TileEntity tileentity, double d, double d1, double d2, float f) {
        this.renderAModelAt((TileMissileAssembler)tileentity, d, d1, d2, f);
    }
}

