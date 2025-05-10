package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class AssistanceTicketButton extends AbstractAssistanceComponent implements GuiScrollerElement
{
    private final int id;
    private final String category;
    private final String dateString;
    private final boolean closed;
    private final String owner;
    public static boolean locked = false;
    private int width;

    public AssistanceTicketButton(int id, String category, Date creationDate, boolean closed)
    {
        this(id, category, creationDate, closed, "");
    }

    public AssistanceTicketButton(int id, String category, Date creationDate, boolean closed, String owner)
    {
        this.id = id;
        this.category = category;
        this.closed = closed;
        this.owner = owner;
        SimpleDateFormat dateFormat = new SimpleDateFormat(I18n.getString("nationsgui.assistance.ticketDate"));
        this.dateString = dateFormat.format(creationDate);
    }

    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        String title = I18n.getStringParams(this.closed ? "nationsgui.assistance.closedticket" : "nationsgui.assistance.openticket", new Object[] {"#" + this.id + " " + this.category});
        float scale = Math.min(1.0F, ((float)this.width - 18.0F) / (float)Minecraft.getMinecraft().fontRenderer.getStringWidth(title));
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        this.drawString(Minecraft.getMinecraft().fontRenderer, title, 0, 0, 16777215);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 10.0F, 0.0F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        this.drawString(Minecraft.getMinecraft().fontRenderer, "\u00a7e" + this.owner + " \u00a78- \u00a77" + this.dateString, 0, 0, 16777215);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(this.width - 15, 0, 13, 362, 15, 16);
    }

    public void onClick(int mouseX, int mouseY, int clickType)
    {
        if (mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= this.getHeight() && !locked)
        {
            this.container.actionPerformed(this);
        }
    }

    public void update() {}

    public void keyTyped(char c, int key) {}

    public void init(GuiScroller scroller)
    {
        this.width = scroller.getWorkWidth();
    }

    public int getId()
    {
        return this.id;
    }

    public int getHeight()
    {
        return 17;
    }
}
