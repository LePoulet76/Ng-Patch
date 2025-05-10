package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.minecraft.client.Minecraft;

public class PlayerChatTag extends AbstractChatTag
{
    String playerName;
    int customColor = 0;

    public PlayerChatTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.playerName = (String)parameters.get("name");

        if (!this.playerName.startsWith("\u00a7"))
        {
            this.customColor = 16113331;
        }
    }

    public void render(int mouseX, int mouseY)
    {
        if (this.customColor != 0)
        {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.playerName, 1, 0, this.customColor);
        }
        else
        {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.playerName, 1, 0, 16777215);
        }
    }

    public void onClick(int mouseX, int mouseY)
    {
        if (!this.playerName.isEmpty() && mouseX >= 2 && mouseX <= Minecraft.getMinecraft().fontRenderer.getStringWidth(this.playerName) && mouseY >= 0 && mouseY <= 8)
        {
            Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.playerName, ""));
        }
    }

    public int getWidth()
    {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(this.playerName);
    }
}
