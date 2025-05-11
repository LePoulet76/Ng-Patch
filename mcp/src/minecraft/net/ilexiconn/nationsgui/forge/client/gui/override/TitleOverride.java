/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.MathHelper
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class TitleOverride
implements ElementOverride {
    Minecraft mc = Minecraft.func_71410_x();
    FontRenderer font;
    private String displayedTitle;
    private String displayedSubTitle;
    private int titlesTimer;
    private int titleFadeIn;
    private int titleFadeOut;
    private int titleDisplayTime;

    public TitleOverride() {
        this.font = this.mc.field_71466_p;
    }

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution res, float partialTicks) {
        if (this.titlesTimer > 0) {
            float age = (float)this.titlesTimer - partialTicks;
            int opacity = 255;
            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
                float f3 = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - age;
                opacity = (int)(f3 * 255.0f / (float)this.titleFadeIn);
            }
            if (this.titlesTimer <= this.titleFadeOut) {
                opacity = (int)(age * 255.0f / (float)this.titleFadeOut);
            }
            if ((opacity = MathHelper.func_76125_a((int)opacity, (int)0, (int)255)) > 8) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(res.func_78326_a() / 2), (float)(res.func_78328_b() / 2), (float)0.0f);
                GL11.glPushMatrix();
                GL11.glScalef((float)4.0f, (float)4.0f, (float)4.0f);
                int l = opacity << 24 & 0xFF000000;
                this.font.func_85187_a(this.displayedTitle, -this.font.func_78256_a(this.displayedTitle) / 2, -10, 0xFFFFFF | l, true);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                this.font.func_85187_a(this.displayedSubTitle, -this.font.func_78256_a(this.displayedSubTitle) / 2, 5, 0xFFFFFF | l, true);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            } else {
                this.displayedTitle = "";
                this.displayedSubTitle = "";
            }
        } else {
            this.displayedTitle = "";
            this.displayedSubTitle = "";
        }
    }

    public void updateTimer() {
        if (this.titlesTimer > 0) {
            --this.titlesTimer;
            if (this.titlesTimer <= 0) {
                this.displayedTitle = "";
                this.displayedSubTitle = "";
            }
        }
    }

    public void displayTitle(String title, String subTitle, int displayTime, int timeFadeIn, int timeFadeOut) {
        this.displayedTitle = title;
        this.displayedSubTitle = subTitle;
        this.titleFadeIn = timeFadeIn;
        this.titleFadeOut = timeFadeOut;
        this.titleDisplayTime = displayTime;
        this.titlesTimer = timeFadeIn + displayTime + timeFadeOut;
    }
}

