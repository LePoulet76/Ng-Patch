package net.ilexiconn.nationsgui.forge.client.gui.override;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class TitleOverride implements ElementOverride
{
    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer font;
    private String displayedTitle;
    private String displayedSubTitle;
    private int titlesTimer;
    private int titleFadeIn;
    private int titleFadeOut;
    private int titleDisplayTime;

    public TitleOverride()
    {
        this.font = this.mc.fontRenderer;
    }

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution res, float partialTicks)
    {
        if (this.titlesTimer > 0)
        {
            float age = (float)this.titlesTimer - partialTicks;
            int opacity = 255;

            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime)
            {
                float l = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - age;
                opacity = (int)(l * 255.0F / (float)this.titleFadeIn);
            }

            if (this.titlesTimer <= this.titleFadeOut)
            {
                opacity = (int)(age * 255.0F / (float)this.titleFadeOut);
            }

            opacity = MathHelper.clamp_int(opacity, 0, 255);

            if (opacity > 8)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(res.getScaledWidth() / 2), (float)(res.getScaledHeight() / 2), 0.0F);
                GL11.glPushMatrix();
                GL11.glScalef(4.0F, 4.0F, 4.0F);
                int l1 = opacity << 24 & -16777216;
                this.font.drawString(this.displayedTitle, -this.font.getStringWidth(this.displayedTitle) / 2, -10, 16777215 | l1, true);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(2.0F, 2.0F, 2.0F);
                this.font.drawString(this.displayedSubTitle, -this.font.getStringWidth(this.displayedSubTitle) / 2, 5, 16777215 | l1, true);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
            else
            {
                this.displayedTitle = "";
                this.displayedSubTitle = "";
            }
        }
        else
        {
            this.displayedTitle = "";
            this.displayedSubTitle = "";
        }
    }

    public void updateTimer()
    {
        if (this.titlesTimer > 0)
        {
            --this.titlesTimer;

            if (this.titlesTimer <= 0)
            {
                this.displayedTitle = "";
                this.displayedSubTitle = "";
            }
        }
    }

    public void displayTitle(String title, String subTitle, int displayTime, int timeFadeIn, int timeFadeOut)
    {
        this.displayedTitle = title;
        this.displayedSubTitle = subTitle;
        this.titleFadeIn = timeFadeIn;
        this.titleFadeOut = timeFadeOut;
        this.titleDisplayTime = displayTime;
        this.titlesTimer = timeFadeIn + displayTime + timeFadeOut;
    }
}
