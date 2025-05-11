/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.block;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class HatBlockRenderer
extends TileEntitySpecialRenderer {
    public void func_76894_a(TileEntity teParam, double posX, double posY, double posZ, float timeSinceLastTick) {
        HatSkin hatSkin;
        TileEntityHatBlock te = (TileEntityHatBlock)teParam;
        if (te != null && !te.getHatID().equals("") && (hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(te.getHatID())) != null) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)(posX + 0.5), (double)(posY + 0.1), (double)(posZ + 0.5));
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)1.0f);
            hatSkin.getModel().updateModel(hatSkin.getTransform("entity"));
            hatSkin.getModel().render(timeSinceLastTick);
            GL11.glPopMatrix();
        }
    }
}

