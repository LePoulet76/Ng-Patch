package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.DropdownComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTicketPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

public class AssistanceStaffGUI extends AbstractAssistanceGUI
{
    private final GuiScroller ranksScroller = new GuiScroller(157, 93);
    private final GuiScroller ticketsScroller = new GuiScroller(157, 156);
    private DropdownComponent dropdownComponent;
    private final int ticketOpened;
    private final int ticketClosed;

    public AssistanceStaffGUI(NBTTagCompound tagCompound)
    {
        NBTTagList ranks = tagCompound.getTagList("ranks");

        for (int i = 0; i < ranks.tagCount(); ++i)
        {
            NBTTagCompound compound = (NBTTagCompound)ranks.tagAt(i);
            this.ranksScroller.addElement(new AssistanceStaffRankGUI(compound.getString("pseudo"), compound.getInteger("score")));

            if (i < ranks.tagCount() - 1)
            {
                this.ranksScroller.addElement(new ScrollerSeparator());
            }
        }

        this.ticketOpened = tagCompound.getInteger("openedTickets");
        this.ticketClosed = tagCompound.getInteger("closedTickets");
    }

    protected void drawGui(int mouseX, int mouseY, float partialTick)
    {
        this.mc.fontRenderer.drawString(I18n.getString("nationsgui.assistance.statsTitle"), this.guiLeft + 202, this.guiTop + 60, 0);
        ModernGui.drawNGBlackSquare(this.guiLeft + 202, this.guiTop + 70, 157, 34);
        this.drawString(this.fontRenderer, I18n.getStringParams("nationsgui.assistance.openTicketStat", new Object[] {Integer.valueOf(this.ticketOpened)}), this.guiLeft + 207, this.guiTop + 76, 16777215);
        this.drawString(this.fontRenderer, I18n.getStringParams("nationsgui.assistance.closedTicketStat", new Object[] {Integer.valueOf(this.ticketClosed)}), this.guiLeft + 207, this.guiTop + 90, 16777215);
        this.mc.fontRenderer.drawString(I18n.getString("nationsgui.assistance.staffTopTitle"), this.guiLeft + 215, this.guiTop + 112, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
        this.drawTexturedModalRect(this.guiLeft + 202, this.guiTop + 110, 230, 275, 10, 12);
    }

    private void populate()
    {
        this.ticketsScroller.clearElement();

        for (int i = 0; i < AssistanceListingPacket.ticketList.tagCount(); ++i)
        {
            NBTTagCompound ticketData = (NBTTagCompound)AssistanceListingPacket.ticketList.tagAt(i);

            if (ticketData.getBoolean("closed") == (this.dropdownComponent != null && this.dropdownComponent.getSelectionIndex() == 1))
            {
                this.ticketsScroller.addElement(new AssistanceTicketButton(ticketData.getInteger("id"), I18n.getString(ticketData.getString("category")), new Date(ticketData.getLong("date")), ticketData.getBoolean("closed"), ticketData.getString("player")));

                if (i < AssistanceListingPacket.ticketList.tagCount() - 1)
                {
                    this.ticketsScroller.addElement(new ScrollerSeparator());
                }
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.populate();
        this.ranksScroller.init(this.guiLeft + 202, this.guiTop + 124);
        this.ticketsScroller.init(this.guiLeft + 13, this.guiTop + 61);
        this.dropdownComponent = new DropdownComponent(this.guiLeft + 90, this.guiTop + 40, 80);
        this.dropdownComponent.getChoices().add(I18n.getString("nationsgui.assistance.open"));
        this.dropdownComponent.getChoices().add(I18n.getString("nationsgui.assistance.closed"));
        this.addComponent(this.ranksScroller);
        this.addComponent(this.ticketsScroller);
        this.addComponent(this.dropdownComponent);
    }

    public void actionPerformed(GuiComponent guiComponent)
    {
        if (guiComponent instanceof DropdownComponent)
        {
            this.populate();
            this.ticketsScroller.init(this.guiLeft + 13, this.guiTop + 61);
            this.addComponent(this.ticketsScroller);
        }
        else if (guiComponent instanceof AssistanceTicketButton)
        {
            AssistanceTicketButton ticketButton = (AssistanceTicketButton)guiComponent;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTicketPacket(ticketButton.getId())));
            AssistanceTicketButton.locked = true;
        }
    }
}
