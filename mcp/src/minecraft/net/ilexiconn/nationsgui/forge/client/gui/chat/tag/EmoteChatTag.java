package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.GuiNewChatTC;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmoteChatTag extends AbstractChatTag
{
    private String id;
    private ResourceLocation resourceLocation;

    public EmoteChatTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.id = (String)parameters.get("id");

        if (this.id == null)
        {
            throw new Exception("[ChatEngine] Emote/badge id is missing");
        }
        else
        {
            this.resourceLocation = (ResourceLocation)this.getResourceList().get(this.id);

            if (this.resourceLocation == null)
            {
                throw new Exception("[ChatEngine] Invalid emote/badge id");
            }
        }
    }

    protected HashMap<String, ResourceLocation> getResourceList()
    {
        return NationsGUI.EMOTES_RESOURCES;
    }

    public void render(int mouseX, int mouseY)
    {
        if (this.resourceLocation != null)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
            GL11.glPushMatrix();
            GL11.glScalef(0.25F, 0.25F, 0.25F);
            ModernGui.drawModalRectWithCustomSizedTexture(0.0F, 0.0F, 0, 0, 32, 32, 32.0F, 32.0F, true);
            GL11.glPopMatrix();

            if (mouseX >= 0 && mouseX <= this.getWidth() && mouseY >= 0 && mouseY <= 9)
            {
                GuiNewChatTC.me.addTooltipToDisplay(Collections.singletonList(this.id), mouseX, mouseY);
            }
        }
    }

    public void onClick(int mouseX, int mouseY) {}

    public int getWidth()
    {
        return 8;
    }
}
