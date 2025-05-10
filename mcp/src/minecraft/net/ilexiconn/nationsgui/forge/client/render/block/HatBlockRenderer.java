package net.ilexiconn.nationsgui.forge.client.render.block;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class HatBlockRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(TileEntity teParam, double posX, double posY, double posZ, float timeSinceLastTick)
    {
        TileEntityHatBlock te = (TileEntityHatBlock)teParam;

        if (te != null && !te.getHatID().equals(""))
        {
            HatSkin hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(te.getHatID());

            if (hatSkin != null)
            {
                GL11.glPushMatrix();
                GL11.glTranslated(posX + 0.5D, posY + 0.1D, posZ + 0.5D);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
                hatSkin.getModel().updateModel(hatSkin.getTransform("entity"));
                hatSkin.getModel().render(timeSinceLastTick);
                GL11.glPopMatrix();
            }
        }
    }
}
