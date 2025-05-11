/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssistanceStaffRankGUI
extends AbstractAssistanceComponent
implements GuiScrollerElement {
    private final String pseudo;
    private final String score;
    private int width;

    public AssistanceStaffRankGUI(String pseudo, int score) {
        this.pseudo = pseudo;
        this.score = "(" + score + ")";
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        float scoreWidth = Minecraft.func_71410_x().field_71466_p.func_78256_a(this.score);
        float maxPseudoWidth = (float)this.width - scoreWidth - 16.0f - 2.0f;
        float pseudoRatio = Math.min(1.0f, maxPseudoWidth / (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(this.pseudo));
        ResourceLocation resourceLocation = AbstractClientPlayer.func_110311_f((String)this.pseudo);
        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)this.pseudo);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GUIUtils.drawScaledCustomSizeModalRect(0, 0, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        GUIUtils.drawScaledCustomSizeModalRect(0, 0, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)16.0f, (float)(7.0f - 9.0f * pseudoRatio / 2.0f), (float)0.0f);
        GL11.glScalef((float)pseudoRatio, (float)pseudoRatio, (float)pseudoRatio);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.pseudo, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.score, (int)((float)this.width - scoreWidth), 4, 0xFFFFFF);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
    }

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(char c, int key) {
    }

    @Override
    public void init(GuiScroller scroller) {
        this.width = scroller.getWorkWidth();
    }

    @Override
    public int getHeight() {
        return 15;
    }
}

