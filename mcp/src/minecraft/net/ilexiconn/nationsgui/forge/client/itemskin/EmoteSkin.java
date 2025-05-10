package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class EmoteSkin extends AbstractSkin
{
    public EmoteSkin(JSONObject object)
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
        ResourceLocation rl = new ResourceLocation("nationsgui", "emotes/icons/" + this.getId() + ".png");

        if (EmoteSkin.class.getResourceAsStream("/assets/" + rl.getResourceDomain() + "/" + rl.getResourcePath()) == null)
        {
            rl = new ResourceLocation("nationsgui", "emotes/icons/custom.png");
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
        ModernGui.drawModalRectWithCustomSizedTexture(5.0F, 4.0F, 0, 0, 16, 16, 16.0F, 16.0F, false);
    }

    private boolean iconExist(ResourceLocation rl)
    {
        return EmoteSkin.class.getResourceAsStream("/assets/" + rl.getResourceDomain() + "/" + rl.getResourcePath()) != null;
    }
}
