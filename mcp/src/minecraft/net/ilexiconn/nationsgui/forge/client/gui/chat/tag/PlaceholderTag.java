package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class PlaceholderTag extends AbstractActionTag
{
    private String text;
    private String tooltip;
    private int width;

    public PlaceholderTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.text = (String)parameters.get("text");
        this.tooltip = (String)parameters.get("tooltip");

        if (this.text == null)
        {
            throw new Exception("[ChatEngine] Invalid placeholder text");
        }
        else
        {
            this.text = "\u00a7n" + this.text;
            this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(this.text);
        }
    }

    public void render(int mouseX, int mouseY)
    {
        Minecraft.getMinecraft().fontRenderer.drawString(this.text, 0, 0, 16777215);

        if (this.tooltip != null && mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= 8)
        {
            TabbyChat.gnc.addTooltipToDisplay(Arrays.asList(this.tooltip.split("\n")), mouseX, mouseY);
        }
    }

    public void onClick(int mouseX, int mouseY)
    {
        if (mouseX >= 0 && mouseX <= this.getWidth() && mouseY >= 0 && mouseY <= 8)
        {
            System.out.println("CLICK ON SWAG !");
            this.doAction();
        }
    }

    public int getWidth()
    {
        return this.width;
    }
}
