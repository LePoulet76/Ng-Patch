package acs.tabbychat;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

class GuiChatTC$EmoteButton extends GuiButton
{
    private String emoteName;

    final GuiChatTC this$0;

    public GuiChatTC$EmoteButton(GuiChatTC var1, int id, int posX, int posY, int width, int height, String emoteName)
    {
        super(id, posX, posY, width, height, "");
        this.this$0 = var1;
        this.emoteName = emoteName;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (GuiChatTC.access$100(this.this$0))
        {
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            float _mult = GuiChatTC.access$200(this.this$0).gameSettings.chatOpacity * 0.9F + 0.1F;

            if (this.field_82253_i)
            {
                _mult *= 0.5F;
            }

            int _opacity = (int)(255.0F * _mult);
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, _opacity / 2 << 24);
            Double scale = Double.valueOf(0.6D);
            GL11.glPushMatrix();
            GL11.glScaled(scale.doubleValue(), scale.doubleValue(), scale.doubleValue());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            if (NationsGUI.EMOTES_RESOURCES.containsKey(this.emoteName))
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.EMOTES_RESOURCES.get(this.emoteName));
                ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition * (1.0F / scale.floatValue()) + 8.0F, (float)this.yPosition * (1.0F / scale.floatValue()) + 7.0F, 0, 0, 18, 18, 18.0F, 18.0F, true);
            }

            GL11.glPopMatrix();
        }
    }
}
