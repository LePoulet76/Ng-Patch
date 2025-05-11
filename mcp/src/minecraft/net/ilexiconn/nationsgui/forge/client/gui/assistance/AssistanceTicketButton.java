/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class AssistanceTicketButton
extends AbstractAssistanceComponent
implements GuiScrollerElement {
    private final int id;
    private final String category;
    private final String dateString;
    private final boolean closed;
    private final String owner;
    public static boolean locked = false;
    private int width;

    public AssistanceTicketButton(int id, String category, Date creationDate, boolean closed) {
        this(id, category, creationDate, closed, "");
    }

    public AssistanceTicketButton(int id, String category, Date creationDate, boolean closed, String owner) {
        this.id = id;
        this.category = category;
        this.closed = closed;
        this.owner = owner;
        SimpleDateFormat dateFormat = new SimpleDateFormat(I18n.func_135053_a((String)"nationsgui.assistance.ticketDate"));
        this.dateString = dateFormat.format(creationDate);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        String title = I18n.func_135052_a((String)(this.closed ? "nationsgui.assistance.closedticket" : "nationsgui.assistance.openticket"), (Object[])new Object[]{"#" + this.id + " " + this.category});
        float scale = Math.min(1.0f, ((float)this.width - 18.0f) / (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(title));
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, title, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)10.0f, (float)0.0f);
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, "\u00a7e" + this.owner + " \u00a78- \u00a77" + this.dateString, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        this.func_73729_b(this.width - 15, 0, 13, 362, 15, 16);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        if (mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= this.getHeight() && !locked) {
            this.container.actionPerformed(this);
        }
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

    public int getId() {
        return this.id;
    }

    @Override
    public int getHeight() {
        return 17;
    }
}

