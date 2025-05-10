package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class BadgeSkin extends AbstractSkin
{
    public BadgeSkin(JSONObject object)
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
        if (NationsGUI.BADGES_RESOURCES.containsKey(this.getId()))
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(this.getId()));
            ModernGui.drawModalRectWithCustomSizedTexture(3.0F, 3.0F, 0, 0, 18, 18, 18.0F, 18.0F, false);
        }
    }
}
