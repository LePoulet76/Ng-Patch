/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.render.item.SpeakerItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SpeakerBlockRenderer
extends TileEntitySpecialRenderer {
    public void func_76894_a(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        float rotation = 0.0f;
        switch (tileEntity.func_70322_n()) {
            case 2: {
                GL11.glTranslated((double)(x + 0.5), (double)(y - 1.5), (double)(z + 1.5));
                rotation = 0.0f;
                break;
            }
            case 3: {
                GL11.glTranslated((double)(x + 0.5), (double)(y - 1.5), (double)(z - 0.5));
                rotation = 180.0f;
                break;
            }
            case 4: {
                GL11.glTranslated((double)(x + 1.5), (double)(y - 1.5), (double)(z + 0.5));
                rotation = 90.0f;
                break;
            }
            case 5: {
                GL11.glTranslated((double)(x - 0.5), (double)(y - 1.5), (double)(z + 0.5));
                rotation = 270.0f;
                break;
            }
            default: {
                GL11.glPopMatrix();
                return;
            }
        }
        GL11.glRotatef((float)rotation, (float)0.0f, (float)1.0f, (float)0.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(SpeakerItemRenderer.TEXTURE);
        SpeakerItemRenderer.MODEL.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}

