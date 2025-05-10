package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTicketPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class AbstractAssistanceListedGUI extends AbstractAssistanceGUI
{
    private final GuiScroller scroller = new GuiScroller(157, 156);

    public AbstractAssistanceListedGUI()
    {
        populateScroller(this.scroller);
    }

    public static void populateScroller(GuiScroller scroller)
    {
        for (int i = 0; i < AssistanceListingPacket.ticketList.tagCount(); ++i)
        {
            NBTTagCompound ticketData = (NBTTagCompound)AssistanceListingPacket.ticketList.tagAt(i);
            scroller.addElement(new AssistanceTicketButton(ticketData.getInteger("id"), I18n.getString(ticketData.getString("category")), new Date(ticketData.getLong("date")), ticketData.getBoolean("closed"), ticketData.getString("player")));

            if (i < AssistanceListingPacket.ticketList.tagCount() - 1)
            {
                scroller.addElement(new ScrollerSeparator());
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scroller.init(this.guiLeft + 13, this.guiTop + 61);
        this.addComponent(this.scroller);
    }

    protected void drawGui(int mouseX, int mouseY, float partialTick)
    {
        this.fontRenderer.drawString(I18n.getString("nationsgui.assistance.mytickets"), this.guiLeft + 13, this.guiTop + 50, 0);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 25.0F);

        if (AssistanceListingPacket.ticketList.tagCount() == 0)
        {
            this.drawCenteredString(this.fontRenderer, I18n.getString("nationsgui.assistance.notickets"), this.guiLeft + 13 + 78, this.guiTop + 70, 16777215);
        }

        GL11.glPopMatrix();
    }

    public void actionPerformed(GuiComponent guiComponent)
    {
        if (guiComponent instanceof AssistanceTicketButton)
        {
            AssistanceTicketButton ticketButton = (AssistanceTicketButton)guiComponent;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTicketPacket(ticketButton.getId())));
            AssistanceTicketButton.locked = true;
        }
    }
}
