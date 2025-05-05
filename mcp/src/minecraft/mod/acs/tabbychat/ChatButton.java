package acs.tabbychat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ChatButton extends GuiButton
{
    public ChatChannel channel;
    public static RenderItem itemRenderer = new RenderItem();

    public ChatButton()
    {
        super(9999, 0, 0, 1, 1, "");
    }

    public ChatButton(int _id, int _x, int _y, int _w, int _h, String _title)
    {
        super(_id, _x, _y, _w, _h, _title);
    }

    protected int width()
    {
        return this.width;
    }

    protected void width(int _w)
    {
        this.width = _w;
    }

    protected int height()
    {
        return this.height;
    }

    protected void height(int _h)
    {
        this.height = _h;
    }

    public void clear()
    {
        this.channel = null;
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int par2, int par3)
    {
        float scaleSetting = TabbyChat.gnc.getScaleSetting();
        int adjY = (int)((float)(mc.currentScreen.height - this.yPosition - 28) * (1.0F - scaleSetting)) + this.yPosition;
        int adjX = (int)((float)(this.xPosition - 5) * scaleSetting) + 5;
        int adjW = (int)((float)this.width * scaleSetting);
        int adjH = (int)((float)this.height * scaleSetting);
        return this.enabled && this.drawButton && par2 >= adjX && par3 >= adjY && par2 < adjX + adjW && par3 < adjY + adjH;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int cursorX, int cursorY)
    {
        if (this.drawButton)
        {
            FontRenderer fr = mc.fontRenderer;
            float _mult = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            int _opacity = (int)(255.0F * _mult);
            float scaleSetting = TabbyChat.gnc.getScaleSetting();
            int adjY = (int)((float)(mc.currentScreen.height - this.yPosition - 28) * (1.0F - scaleSetting)) + this.yPosition;
            int adjX = (int)((float)(this.xPosition - 5) * scaleSetting) + 5;
            int adjW = (int)((float)this.width * scaleSetting);
            int adjH = (int)((float)this.height * scaleSetting);
            boolean hovered = cursorX >= adjX && cursorY >= adjY && cursorX < adjX + adjW && cursorY < adjY + adjH;
            int var7 = 10526880;
            int var8 = 0;

            if (!this.enabled)
            {
                var7 = -6250336;
            }
            else if (hovered)
            {
                var7 = 16777120;
                var8 = 8355922;
            }
            else if (this.channel.active)
            {
                var7 = 10872804;
                var8 = 5995643;
            }
            else if (this.channel.unread)
            {
                var7 = 16711680;
                var8 = 7471104;
            }

            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, var8 + (_opacity / 2 << 24));
            GL11.glEnable(GL11.GL_BLEND);
            String label = this.displayString;
            boolean hasBracket = this.displayString.contains("[");
            boolean hasChevron = this.displayString.contains("<");
            String customLabel = I18n.getString("chat.tab." + this.displayString.toLowerCase().replace(" ", "_").replace("[", "").replace("]", "").replace("<", "").replace(">", ""));

            if (hasBracket)
            {
                customLabel = "[" + customLabel + "]";
            }

            if (hasChevron)
            {
                customLabel = "<" + customLabel + ">";
            }

            if (this.displayString.contains("ALL"))
            {
                label = "\u00a75" + customLabel;
            }
            else if (this.displayString.contains("ENE"))
            {
                label = "\u00a7c" + customLabel;
            }
            else if (this.displayString.contains("Mon pays"))
            {
                label = "\u00a7a" + customLabel;
            }
            else if (this.displayString.contains("ADMIN"))
            {
                label = "\u00a74" + customLabel;
            }
            else if (this.displayString.contains("MODO"))
            {
                label = "\u00a72" + customLabel;
            }
            else if (this.displayString.contains("Police"))
            {
                label = "\u00a71" + customLabel;
            }
            else if (this.displayString.contains("Mafia"))
            {
                label = "\u00a75" + customLabel;
            }
            else if (this.displayString.contains("Guide"))
            {
                label = "\u00a7d" + customLabel;
            }
            else if (this.displayString.contains("Journal"))
            {
                label = "\u00a7e" + customLabel;
            }
            else if (this.displayString.contains("Avocat"))
            {
                label = "\u00a73" + customLabel;
            }
            else if (this.displayString.contains("Logs"))
            {
                label = "\u00a74" + customLabel;
            }
            else if (this.displayString.contains("RP"))
            {
                label = "\u00a76" + customLabel;
            }

            this.drawCenteredString(fr, label, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var7 + (_opacity << 24));

            if (hovered && !this.displayString.contains("Global"))
            {
                this.drawHoveringText(Arrays.asList(new String[] {"\u00a77Shift + Click", I18n.getString("chat.label.to_close")}), this.xPosition + 3, this.yPosition - 25, fr);
            }
        }
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2;
            j1 = par3;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (par2 + k > Minecraft.getMinecraft().currentScreen.width)
            {
                var15 = par2 - (28 + k);
            }

            if (par3 + k1 + 6 > Minecraft.getMinecraft().currentScreen.height)
            {
                j1 = Minecraft.getMinecraft().currentScreen.height - k1 - 6;
            }

            this.zLevel = 500.0F;
            itemRenderer.zLevel = 500.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
