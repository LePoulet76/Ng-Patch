package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class BuddySkin extends AbstractSkin
{
    private RenderItem itemRenderer = new RenderItem();

    public BuddySkin(JSONObject object)
    {
        super(object);
    }

    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform)
    {
        forcedRenderSkin = this;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef(scale, scale, scale);
        this.render(partialTick);
        GL11.glPopMatrix();
        forcedRenderSkin = null;
    }

    protected void render(float partialTick)
    {
        String alias = this.getId().split("_")[1];
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("SkullOwner", alias);
        ItemStack stack = new ItemStack(397, 1, 3);
        stack.stackTagCompound = compound;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, 5, 3);
        this.itemRenderer.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, 5, 3, "");
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
    }
}
