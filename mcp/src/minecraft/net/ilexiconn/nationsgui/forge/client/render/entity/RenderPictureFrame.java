/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderEntity
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.entity;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.entity.EntityPictureFrame;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPictureFrame
extends RenderEntity {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/entity/pictureframeoff.png");

    private void renderEntityPicture(EntityPictureFrame par1EntityPainting, int par2, int par3) {
        Tessellator tessellator = Tessellator.field_78398_a;
        if (par1EntityPainting.getDownloadImageSkin() != null && par1EntityPainting.getDownloadImageSkin().isTextureUploaded() || !ClientProxy.clientConfig.displayPictureFrame) {
            this.func_110776_a(ClientProxy.clientConfig.displayPictureFrame ? par1EntityPainting.getLocationSkin() : TEXTURE);
            tessellator.func_78382_b();
            tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
            tessellator.func_78374_a((double)(par2 / 2), (double)(par3 / 2), 0.0, 0.0, 0.0);
            tessellator.func_78374_a((double)(par2 / 2), (double)(-par3 / 2), 0.0, 0.0, 1.0);
            tessellator.func_78374_a((double)(-par2 / 2), (double)(-par3 / 2), 0.0, 1.0, 1.0);
            tessellator.func_78374_a((double)(-par2 / 2), (double)(par3 / 2), 0.0, 1.0, 0.0);
            tessellator.func_78381_a();
        } else {
            GL11.glDisable((int)3553);
            tessellator.func_78382_b();
            tessellator.func_78369_a(1.0f, 1.0f, 1.0f, 1.0f);
            tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
            tessellator.func_78377_a((double)(par2 / 2), (double)(par3 / 2), 0.0);
            tessellator.func_78377_a((double)(par2 / 2), (double)(-par3 / 2), 0.0);
            tessellator.func_78377_a((double)(-par2 / 2), (double)(-par3 / 2), 0.0);
            tessellator.func_78377_a((double)(-par2 / 2), (double)(par3 / 2), 0.0);
            tessellator.func_78381_a();
            GL11.glEnable((int)3553);
        }
    }

    public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)par2, (double)par4, (double)par6);
        GL11.glRotatef((float)par8, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glEnable((int)32826);
        float f2 = 0.0625f;
        GL11.glScalef((float)f2, (float)f2, (float)f2);
        EntityPictureFrame entityPictureFrame = (EntityPictureFrame)par1Entity;
        this.renderEntityPicture(entityPictureFrame, entityPictureFrame.getWidthPixels(), entityPictureFrame.getHeightPixels());
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE;
    }
}

