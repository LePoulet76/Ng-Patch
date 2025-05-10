package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceClosePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTeleportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AssistanceTicketGUI extends AbstractAssistanceGUI
{
    private final GuiScroller scroller;
    private final GuiScroller scrollerLeft;
    private final String title;
    private final int responsesNumber;
    private String screenUrl = null;
    private final int id;
    private final AssistanceMessage message;
    private boolean locked = false;
    private boolean admin = false;
    private final boolean closed;

    public AssistanceTicketGUI(NBTTagCompound compound)
    {
        if (ClientProxy.playersInAdminMode.containsKey(Minecraft.getMinecraft().thePlayer.username))
        {
            this.admin = ((Boolean)ClientProxy.playersInAdminMode.get(Minecraft.getMinecraft().thePlayer.username)).booleanValue();
        }

        this.scroller = new GuiScroller(157, 132);
        NBTTagList list = compound.getTagList("replies");
        this.id = compound.getInteger("id");
        this.closed = compound.getBoolean("closed");
        this.responsesNumber = list.tagCount() - 1;

        for (int firstReply = 1; firstReply < list.tagCount(); ++firstReply)
        {
            NBTTagCompound reply = (NBTTagCompound)list.tagAt(firstReply);
            this.scroller.addElement(new AssistanceMessage(reply.getString("pseudo"), reply.getString("message"), new Date(reply.getLong("date"))));

            if (firstReply < list.tagCount() - 1)
            {
                this.scroller.addElement(new ScrollerSeparator());
            }
        }

        NBTTagCompound var5 = (NBTTagCompound)list.tagAt(0);
        this.message = new AssistanceMessage(var5.getString("pseudo"), var5.getString("message"), new Date(var5.getLong("date")));
        this.scrollerLeft = new GuiScroller(157, this.admin ? 132 : 156);
        this.scrollerLeft.addElement(this.message);
        this.title = I18n.getStringParams(compound.getBoolean("closed") ? "nationsgui.assistance.closedticket" : "nationsgui.assistance.openticket", new Object[] {"#" + this.id + " " + I18n.getString(compound.getString("category"))});

        if (compound.hasKey("screenshot"))
        {
            this.screenUrl = compound.getString("screenshot");
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scroller.init(this.guiLeft + 202, this.guiTop + 61);
        this.scroller.setValue(1.0F);
        this.scrollerLeft.init(this.guiLeft + 13, this.guiTop + 61);
        this.addComponent(this.scroller);
        this.addComponent(this.scrollerLeft);
        this.buttonList.add(new AssistanceSimpleButton(0, this.guiLeft + 13, this.guiTop + 42, 181, 256));
        AssistanceButton replyButton = new AssistanceButton(1, this.guiLeft + 202, this.guiTop + 197, 157, 20, I18n.getString("nationsgui.assistance.reply"));
        replyButton.enabled = !this.closed;
        this.buttonList.add(replyButton);

        if (this.screenUrl != null && !this.screenUrl.isEmpty())
        {
            this.buttonList.add(new AssistanceSimpleButton(2, this.guiLeft + 142, this.guiTop + (this.admin ? 174 : 198), 181, 272));
        }

        if (this.admin)
        {
            GuiButton teleport = new GuiButton(3, this.guiLeft + 13, this.guiTop + 197, 77, 20, I18n.getString("nationsgui.assistance.teleport"));
            teleport.enabled = !this.closed;
            this.buttonList.add(teleport);
            AssistanceButton closeButton = new AssistanceButton(4, this.guiLeft + 94, this.guiTop + 197, 77, 20, I18n.getString("nationsgui.assistance.close"));
            closeButton.setUVMap(249, 406, 256);
            closeButton.enabled = !this.closed;
            this.buttonList.add(closeButton);
        }
    }

    protected void drawGui(int mouseX, int mouseY, float partialTick)
    {
        int var10002 = this.guiLeft + 13 + 25;
        int var10003 = this.guiTop + 47;
        Minecraft.getMinecraft().fontRenderer.drawString(this.title, var10002, var10003, 0);
        Minecraft.getMinecraft().fontRenderer.drawString(I18n.getStringParams("nationsgui.assistance.responses", new Object[] {Integer.valueOf(this.responsesNumber)}), this.guiLeft + 202, this.guiTop + 51, 0);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!this.locked)
        {
            switch (par1GuiButton.id)
            {
                case 0:
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
                    this.locked = true;
                    break;

                case 1:
                    Minecraft.getMinecraft().displayGuiScreen(new AssistanceTicketReply(this, this.id, this.message, this.title, this.screenUrl));
                    break;

                case 2:
                    Desktop desktop = Desktop.getDesktop();

                    if (desktop.isSupported(Action.BROWSE))
                    {
                        try
                        {
                            desktop.browse(new URI(this.screenUrl));
                        }
                        catch (Exception var4)
                        {
                            var4.printStackTrace();
                        }
                    }

                    break;

                case 3:
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceTeleportPacket(this.id)));
                    this.mc.displayGuiScreen((GuiScreen)null);
                    break;

                case 4:
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceClosePacket(this.id)));
                    this.locked = true;
            }
        }
    }

    public void actionPerformed(GuiComponent guiComponent) {}
}
