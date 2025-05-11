/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ChunkCoordinates
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.block;

import net.ilexiconn.nationsgui.forge.client.render.item.RadioItemRenderer;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.lwjgl.opengl.GL11;

public class RadioBlockRenderer
extends TileEntitySpecialRenderer {
    public void func_76894_a(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        float rotation = (float)(tileEntity.func_70322_n() * 360) / 16.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)(x + 0.5), (double)(y + 1.5), (double)(z + 0.5));
        GL11.glRotatef((float)(-rotation), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(RadioItemRenderer.TEXTURE);
        RadioItemRenderer.MODEL.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        if (RenderManager.field_85095_o) {
            for (ChunkCoordinates coordinates : ((RadioBlockEntity)tileEntity).speakers) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)x, (double)y, (double)z);
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2884);
                GL11.glDisable((int)3042);
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78371_b(1);
                tessellator.func_78377_a(0.5, 0.5, 0.5);
                tessellator.func_78377_a((double)((float)(coordinates.field_71574_a - tileEntity.field_70329_l) + 0.5f), (double)((float)(coordinates.field_71572_b - tileEntity.field_70330_m) + 0.5f), (double)((float)(coordinates.field_71573_c - tileEntity.field_70327_n) + 0.5f));
                tessellator.func_78381_a();
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2884);
                GL11.glEnable((int)3042);
                GL11.glPopMatrix();
            }
        }
    }
}

