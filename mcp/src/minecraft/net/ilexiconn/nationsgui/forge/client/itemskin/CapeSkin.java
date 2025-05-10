package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class CapeSkin extends AbstractSkin
{
    public CapeSkin(JSONObject object)
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
        try
        {
            ResourceLocation e = ClientProxy.getCachedCape(this.getId());

            if (e != null)
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(ClientProxy.getCachedCape(this.getId()));
                ModernGui.drawScaledCustomSizeModalRect(5.5F, 3.0F, 0.0F, 0.0F, 12, 17, 14, 20, 64.0F, 32.0F, false);
            }
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
        }
    }

    public String getTextureURL()
    {
        return "https://apiv2.nationsglory.fr/json/capes/capes/" + this.getId() + ".png";
    }
}
