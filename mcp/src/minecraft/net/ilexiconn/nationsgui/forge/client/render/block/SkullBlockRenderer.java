/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntitySkull
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.model.block.SkullModel;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SkullBlockRenderer
extends TileEntitySkullRenderer {
    public static final SkullBlockRenderer INSTANCE = new SkullBlockRenderer();

    public void func_76894_a(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        TileEntitySkull skull = (TileEntitySkull)tileEntity;
        this.renderSkull((float)x, (float)y, (float)z, skull.func_70322_n() & 7, (float)(skull.func_82119_b() * 360) / 16.0f, skull.func_82117_a(), skull.func_82120_c(), false);
    }

    public void renderSkull(float x, float y, float z, int placement, float rotation, int type, String extraType, boolean wear) {
        SkullModel model = SkullItemRenderer.MODEL_SKULL;
        switch (type) {
            default: {
                this.func_110628_a(SkullItemRenderer.SKELETON_TEXTURE);
                break;
            }
            case 1: {
                this.func_110628_a(SkullItemRenderer.WITHER_SKELETON_TEXTURE);
                break;
            }
            case 2: {
                this.func_110628_a(SkullItemRenderer.ZOMBIE_TEXTURE);
                model = SkullItemRenderer.MODEL_SKULL_LARGE;
                break;
            }
            case 3: {
                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                if (extraType != null && extraType.length() > 0) {
                    resourceLocation = AbstractClientPlayer.func_110305_h((String)extraType);
                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)extraType);
                }
                this.func_110628_a(resourceLocation);
                model = SkullItemRenderer.MODEL_SKULL_LARGE;
                break;
            }
            case 4: {
                this.func_110628_a(SkullItemRenderer.CREEPER_TEXTURE);
            }
        }
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        if (placement != 1) {
            switch (placement) {
                case 2: {
                    GL11.glTranslated((double)(x + 0.5f), (double)(y + 0.25f), (double)(z + 0.74f));
                    break;
                }
                case 3: {
                    GL11.glTranslated((double)(x + 0.5f), (double)(y + 0.25f), (double)(z + 0.26f));
                    rotation = 180.0f;
                    break;
                }
                case 4: {
                    GL11.glTranslated((double)(x + 0.74f), (double)(y + 0.25f), (double)(z + 0.5f));
                    rotation = 270.0f;
                    break;
                }
                default: {
                    GL11.glTranslated((double)(x + 0.26f), (double)(y + 0.25f), (double)(z + 0.5f));
                    rotation = 90.0f;
                    break;
                }
            }
        } else {
            GL11.glTranslated((double)(x + 0.5f), (double)y, (double)(z + 0.5f));
        }
        if (wear) {
            GL11.glTranslated((double)0.0, (double)-0.03f, (double)0.0);
        }
        GL11.glEnable((int)32826);
        if (wear) {
            GL11.glScalef((float)-1.1f, (float)-1.1f, (float)1.1f);
        } else {
            GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        }
        GL11.glEnable((int)3008);
        model.func_78088_a(null, 0.0f, 0.0f, 0.0f, rotation, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}

