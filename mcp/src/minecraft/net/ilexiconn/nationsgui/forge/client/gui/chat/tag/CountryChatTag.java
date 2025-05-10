package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class CountryChatTag extends AbstractChatTag
{
    String countryName;
    String reformatedCountryName;
    String countryColor;

    public CountryChatTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.countryName = (String)parameters.get("name");
        this.reformatedCountryName = this.countryName.replaceAll("Empire", "Emp");
        this.countryColor = ((String)parameters.get("color")).replaceAll("\u00a7f", "\u00a77");
    }

    public void render(int mouseX, int mouseY)
    {
        if (!this.reformatedCountryName.isEmpty())
        {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.countryColor + this.reformatedCountryName, 1, 0, 16777215);
        }
        else
        {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("\u00a77" + I18n.getString("main.no_country"), 1, 0, 16777215);
        }
    }

    public void onClick(int mouseX, int mouseY)
    {
        if (!this.countryName.isEmpty() && mouseX >= 2 && mouseX <= Minecraft.getMinecraft().fontRenderer.getStringWidth(this.reformatedCountryName) && mouseY >= 0 && mouseY <= 8)
        {
            Minecraft.getMinecraft().displayGuiScreen(new FactionGUI(this.countryName));
        }
    }

    public int getWidth()
    {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(!this.countryName.isEmpty() ? this.reformatedCountryName : I18n.getString("main.no_country"));
    }
}
