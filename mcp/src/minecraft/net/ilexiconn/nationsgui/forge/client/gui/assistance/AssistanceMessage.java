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

import java.text.SimpleDateFormat;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiTextMultiLines;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssistanceMessage
extends AbstractAssistanceComponent
implements GuiScrollerElement {
    private String pseudo;
    private String message;
    private String date;
    private int width;
    private GuiTextMultiLines multiLines;

    public AssistanceMessage(String pseudo, String message, Date date) {
        this.pseudo = pseudo;
        this.message = message;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.date = simpleDateFormat.format(date);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        float dateWidth = (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(this.date) * 0.75f;
        float maxPseudoWidth = (float)this.width - dateWidth - 16.0f - 2.0f;
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
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)this.width - dateWidth), (float)4.0f, (float)0.0f);
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.date, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        this.multiLines.draw(mouseX, mouseY, partialTicks);
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
        this.multiLines = new GuiTextMultiLines(this.message, this.width - 6, false, 1.0f);
        this.multiLines.setPosition(0, 20);
    }

    @Override
    public int getHeight() {
        return 20 + this.multiLines.getHeight() + 4;
    }
}

