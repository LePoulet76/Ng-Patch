/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Desktop;
import java.net.URI;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceMessage;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceSimpleButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketReply;
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
import net.minecraft.network.packet.Packet;

public class AssistanceTicketGUI
extends AbstractAssistanceGUI {
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

    public AssistanceTicketGUI(NBTTagCompound compound) {
        if (ClientProxy.playersInAdminMode.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
            this.admin = ClientProxy.playersInAdminMode.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
        }
        this.scroller = new GuiScroller(157, 132);
        NBTTagList list = compound.func_74761_m("replies");
        this.id = compound.func_74762_e("id");
        this.closed = compound.func_74767_n("closed");
        this.responsesNumber = list.func_74745_c() - 1;
        for (int i = 1; i < list.func_74745_c(); ++i) {
            NBTTagCompound reply = (NBTTagCompound)list.func_74743_b(i);
            this.scroller.addElement(new AssistanceMessage(reply.func_74779_i("pseudo"), reply.func_74779_i("message"), new Date(reply.func_74763_f("date"))));
            if (i >= list.func_74745_c() - 1) continue;
            this.scroller.addElement(new ScrollerSeparator());
        }
        NBTTagCompound firstReply = (NBTTagCompound)list.func_74743_b(0);
        this.message = new AssistanceMessage(firstReply.func_74779_i("pseudo"), firstReply.func_74779_i("message"), new Date(firstReply.func_74763_f("date")));
        this.scrollerLeft = new GuiScroller(157, this.admin ? 132 : 156);
        this.scrollerLeft.addElement(this.message);
        this.title = I18n.func_135052_a((String)(compound.func_74767_n("closed") ? "nationsgui.assistance.closedticket" : "nationsgui.assistance.openticket"), (Object[])new Object[]{"#" + this.id + " " + I18n.func_135053_a((String)compound.func_74779_i("category"))});
        if (compound.func_74764_b("screenshot")) {
            this.screenUrl = compound.func_74779_i("screenshot");
        }
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scroller.init(this.guiLeft + 202, this.guiTop + 61);
        this.scroller.setValue(1.0f);
        this.scrollerLeft.init(this.guiLeft + 13, this.guiTop + 61);
        this.addComponent(this.scroller);
        this.addComponent(this.scrollerLeft);
        this.field_73887_h.add(new AssistanceSimpleButton(0, this.guiLeft + 13, this.guiTop + 42, 181, 256));
        AssistanceButton replyButton = new AssistanceButton(1, this.guiLeft + 202, this.guiTop + 197, 157, 20, I18n.func_135053_a((String)"nationsgui.assistance.reply"));
        replyButton.field_73742_g = !this.closed;
        this.field_73887_h.add(replyButton);
        if (this.screenUrl != null && !this.screenUrl.isEmpty()) {
            this.field_73887_h.add(new AssistanceSimpleButton(2, this.guiLeft + 142, this.guiTop + (this.admin ? 174 : 198), 181, 272));
        }
        if (this.admin) {
            GuiButton teleport = new GuiButton(3, this.guiLeft + 13, this.guiTop + 197, 77, 20, I18n.func_135053_a((String)"nationsgui.assistance.teleport"));
            teleport.field_73742_g = !this.closed;
            this.field_73887_h.add(teleport);
            AssistanceButton closeButton = new AssistanceButton(4, this.guiLeft + 94, this.guiTop + 197, 77, 20, I18n.func_135053_a((String)"nationsgui.assistance.close"));
            closeButton.setUVMap(249, 406, 256);
            closeButton.field_73742_g = !this.closed;
            this.field_73887_h.add(closeButton);
        }
    }

    @Override
    protected void drawGui(int mouseX, int mouseY, float partialTick) {
        Minecraft.func_71410_x().field_71466_p.func_78276_b(this.title, this.guiLeft + 13 + 25, this.guiTop + 47, 0);
        Minecraft.func_71410_x().field_71466_p.func_78276_b(I18n.func_135052_a((String)"nationsgui.assistance.responses", (Object[])new Object[]{this.responsesNumber}), this.guiLeft + 202, this.guiTop + 51, 0);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (this.locked) {
            return;
        }
        switch (par1GuiButton.field_73741_f) {
            case 2: {
                Desktop desktop = Desktop.getDesktop();
                if (!desktop.isSupported(Desktop.Action.BROWSE)) break;
                try {
                    desktop.browse(new URI(this.screenUrl));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new AssistanceTicketReply(this, this.id, this.message, this.title, this.screenUrl));
                break;
            }
            case 0: {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
                this.locked = true;
                break;
            }
            case 3: {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceTeleportPacket(this.id)));
                this.field_73882_e.func_71373_a(null);
                break;
            }
            case 4: {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceClosePacket(this.id)));
                this.locked = true;
            }
        }
    }

    @Override
    public void actionPerformed(GuiComponent guiComponent) {
    }
}

