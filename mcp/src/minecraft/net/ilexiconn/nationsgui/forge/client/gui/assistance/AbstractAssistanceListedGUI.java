/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ScrollerSeparator;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketButton;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceTicketPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class AbstractAssistanceListedGUI
extends AbstractAssistanceGUI {
    private final GuiScroller scroller = new GuiScroller(157, 156);

    public AbstractAssistanceListedGUI() {
        AbstractAssistanceListedGUI.populateScroller(this.scroller);
    }

    public static void populateScroller(GuiScroller scroller) {
        for (int i = 0; i < AssistanceListingPacket.ticketList.func_74745_c(); ++i) {
            NBTTagCompound ticketData = (NBTTagCompound)AssistanceListingPacket.ticketList.func_74743_b(i);
            scroller.addElement(new AssistanceTicketButton(ticketData.func_74762_e("id"), I18n.func_135053_a((String)ticketData.func_74779_i("category")), new Date(ticketData.func_74763_f("date")), ticketData.func_74767_n("closed"), ticketData.func_74779_i("player")));
            if (i >= AssistanceListingPacket.ticketList.func_74745_c() - 1) continue;
            scroller.addElement(new ScrollerSeparator());
        }
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scroller.init(this.guiLeft + 13, this.guiTop + 61);
        this.addComponent(this.scroller);
    }

    @Override
    protected void drawGui(int mouseX, int mouseY, float partialTick) {
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"nationsgui.assistance.mytickets"), this.guiLeft + 13, this.guiTop + 50, 0);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)25.0f);
        if (AssistanceListingPacket.ticketList.func_74745_c() == 0) {
            this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"nationsgui.assistance.notickets"), this.guiLeft + 13 + 78, this.guiTop + 70, 0xFFFFFF);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void actionPerformed(GuiComponent guiComponent) {
        if (guiComponent instanceof AssistanceTicketButton) {
            AssistanceTicketButton ticketButton = (AssistanceTicketButton)guiComponent;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceTicketPacket(ticketButton.getId())));
            AssistanceTicketButton.locked = true;
        }
    }
}

