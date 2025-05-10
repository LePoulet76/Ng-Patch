package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CountryFlagChatTag extends AbstractChatTag
{
    String countryName;
    String countryColor;

    public CountryFlagChatTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.countryName = (String)parameters.get("name");
        this.countryColor = (String)parameters.get("color");
    }

    public void render(int mouseX, int mouseY)
    {
        if (this.countryName != null && !this.countryName.contains("Wilderness") && !this.countryName.isEmpty())
        {
            ClientProxy.loadCountryFlag(this.countryName);

            if (ClientProxy.flagsTexture.containsKey(this.countryName))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(this.countryName)).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 156, 78, 12, 7, 156.0F, 78.0F, false);
                GL11.glEnable(GL11.GL_BLEND);
            }
        }
    }

    public void onClick(int mouseX, int mouseY)
    {
        if (mouseX >= 2 && mouseX <= 14 && mouseY >= 0 && mouseY <= 8)
        {
            Minecraft.getMinecraft().displayGuiScreen(new FactionGUI(this.countryName));
        }
    }

    public int getWidth()
    {
        return this.countryName != null && !this.countryName.contains("Wilderness") && !this.countryName.isEmpty() ? 15 : 0;
    }
}
