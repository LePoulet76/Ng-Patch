/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.Mail;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarkMailDeletedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NewMailPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RequestMailPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MailGUI
extends TabbedGUI {
    private Mail openMail;
    private boolean writingMail;
    private GuiButton backButton;
    private GuiButton deleteButton;
    private GuiButton replyButton;
    private GuiButton writeButton;
    private GuiButton sendButton;
    private GuiButton joinButton;
    private GuiTextField receiverText;
    private GuiTextField titleText;
    private GuiTextField contentText;
    private GuiScrollBar scrollBar;
    private String receiverName;
    public static boolean loaded = false;

    public MailGUI(EntityPlayer player) {
        super(player);
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.backButton = new TexturedButtonGUI(0, this.guiLeft + 10, this.guiTop + 196, 19, 19, "mail_open", 201, 72, "");
        this.deleteButton = new TexturedButtonGUI(1, this.guiLeft + 132 + 21, this.guiTop + 52, 19, 19, "mail_open", 182, 72, "");
        this.replyButton = new TexturedButtonGUI(2, this.guiLeft + 83 + 15, this.guiTop + 196, 74, 19, "mail_open", 182, 15, I18n.func_135053_a((String)"gui.mail.reply"));
        this.writeButton = new TexturedButtonGUI(3, this.guiLeft + 98 + 21, this.guiTop + 52, 31, 19, "mail", 182, 15, "");
        this.sendButton = new TexturedButtonGUI(4, this.guiLeft + 98, this.guiTop + 197, 74, 19, "mail_send", 182, 15, I18n.func_135053_a((String)"gui.mail.send"));
        this.joinButton = new TexturedButtonGUI(5, this.guiLeft + 83 + 15, this.guiTop + 196, 74, 19, "mail_open", 182, 15, I18n.func_135053_a((String)"gui.mail.join"));
        this.receiverText = new GuiTextField(this.field_73886_k, this.guiLeft + 34, this.guiTop + 56, 133, 9);
        this.receiverText.func_73786_a(false);
        this.receiverText.func_73804_f(16);
        this.titleText = new GuiTextField(this.field_73886_k, this.guiLeft + 15, this.guiTop + 80, 152, 9);
        this.titleText.func_73786_a(false);
        this.titleText.func_73804_f(32);
        this.contentText = new TextAreaGUI(this.guiLeft + 11, this.guiTop + 100, 152);
        this.contentText.func_73804_f(225);
        this.scrollBar = new GuiScrollBar(this.guiLeft + 162, this.guiTop + 75, 140);
        if (this.openMail != null) {
            this.openMail(this.openMail);
        } else {
            this.closeMail();
        }
        this.setWritingMail(this.writingMail);
        loaded = false;
        ClientData.getMail().clear();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RequestMailPacket()));
    }

    public void func_73876_c() {
        this.receiverText.func_73780_a();
        this.titleText.func_73780_a();
        this.contentText.func_73780_a();
        this.sendButton.field_73742_g = this.receiverName != null && this.titleText.func_73781_b().length() > 2 && this.contentText.func_73781_b().length() > 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (this.openMail != null) {
            ClientEventHandler.STYLE.bindTexture("mail_open");
            this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.field_73882_e.func_110434_K().func_110577_a(AbstractClientPlayer.func_110311_f((String)this.openMail.sender));
            MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 8.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
            MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 40.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
            this.field_73886_k.func_85187_a(this.openMail.sender, this.guiLeft + 34, this.guiTop + 56, 0xFFFFFF, true);
            this.field_73886_k.func_85187_a(this.openMail.title, this.guiLeft + 16, this.guiTop + 80, 0xFFFFFF, true);
            Gui.func_73734_a((int)(this.guiLeft + 15), (int)(this.guiTop + 93), (int)(this.guiLeft + 152 + 15), (int)(this.guiTop + 94), (int)-12303292);
            this.field_73886_k.func_78279_b(this.openMail.content, this.guiLeft + 16, this.guiTop + 99, 152, 0xB4B4B4);
        } else if (this.writingMail) {
            ClientEventHandler.STYLE.bindTexture("mail_send");
            this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.receiverText.func_73795_f();
            this.titleText.func_73795_f();
            this.contentText.func_73795_f();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ResourceLocation skin = AbstractClientPlayer.field_110314_b;
            if (this.receiverName != null) {
                skin = AbstractClientPlayer.func_110305_h((String)this.receiverName);
                AbstractClientPlayer.func_110304_a((ResourceLocation)skin, (String)this.receiverName);
            }
            this.field_73882_e.func_110434_K().func_110577_a(skin);
            MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 8.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
            MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 40.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
        } else {
            ClientEventHandler.STYLE.bindTexture("mail");
            this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"gui.mail.inbox"), this.guiLeft + 52 + 11, this.guiTop + 59, 0xFFFFFF);
            if (loaded) {
                GUIUtils.startGLScissor(this.guiLeft + 10, this.guiTop + 75, 145, 140);
                for (int i = 0; i < ClientData.getMail().size(); ++i) {
                    Mail mail = ClientData.getMail().get(i);
                    int x = 17;
                    int y = (int)((float)(84 + i * 35) + this.getSlide());
                    Gui.func_73734_a((int)(this.guiLeft + x - 1), (int)(this.guiTop + y - 1), (int)(this.guiLeft + x + 16 + 1), (int)(this.guiTop + y + 16 + 1), (int)-4605511);
                    Gui.func_73734_a((int)(this.guiLeft + x - 1), (int)(this.guiTop + y + 25), (int)(this.guiLeft + x + 134), (int)(this.guiTop + y + 26), (int)-12303292);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    ResourceLocation skin = AbstractClientPlayer.func_110305_h((String)mail.sender);
                    AbstractClientPlayer.func_110304_a((ResourceLocation)skin, (String)mail.sender);
                    this.field_73882_e.func_110434_K().func_110577_a(skin);
                    MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + x, this.guiTop + y, 8.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
                    MailGUI.drawScaledCustomSizeModalRect(this.guiLeft + x, this.guiTop + y, 40.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
                    this.field_73886_k.func_85187_a(mail.title, this.guiLeft + x + 20, this.guiTop + y - 1, 0xFFFFFF, true);
                    this.field_73886_k.func_85187_a(mail.sender, this.guiLeft + x + 20, this.guiTop + y + 10, 0x7F7F7F, true);
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    if (mouseX < this.guiLeft + x - 6 || mouseX > this.guiLeft + x + 139 || mouseY < this.guiTop + y - 9 || mouseY > this.guiTop + y + 24) continue;
                    Gui.func_73734_a((int)(this.guiLeft + x - 6), (int)(this.guiTop + y - 9), (int)(this.guiLeft + x + 139), (int)(this.guiTop + y + 25), (int)0x20FFFFFF);
                }
                GUIUtils.endGLScissor();
            } else {
                this.field_73886_k.func_85187_a(I18n.func_135053_a((String)"gui.mail.loading"), this.guiLeft + 12 + 71 - this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"gui.mail.loading")) / 2, this.guiTop + 85, 6645612, false);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            }
            this.scrollBar.draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.writingMail) {
            if (this.receiverText.func_73802_a(typedChar, keyCode)) {
                this.receiverName = this.receiverText.func_73781_b();
            } else if (!this.titleText.func_73802_a(typedChar, keyCode) && !this.contentText.func_73802_a(typedChar, keyCode)) {
                super.func_73869_a(typedChar, keyCode);
            }
        } else {
            super.func_73869_a(typedChar, keyCode);
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (this.openMail == null && !this.writingMail) {
            if (mouseButton == 0) {
                for (int i = 0; i < ClientData.getMail().size(); ++i) {
                    Mail mail = ClientData.getMail().get(i);
                    int x = 17;
                    int y = (int)((float)(84 + i * 35) + this.getSlide());
                    if (mouseX < this.guiLeft + x - 6 || mouseX > this.guiLeft + x + 139 || mouseY < this.guiTop + y - 9 || mouseY > this.guiTop + y + 24) continue;
                    this.openMail(mail);
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                }
            }
        } else if (this.writingMail) {
            this.receiverText.func_73793_a(mouseX, mouseY, mouseButton);
            this.titleText.func_73793_a(mouseX, mouseY, mouseButton);
            this.contentText.func_73793_a(mouseX, mouseY, mouseButton);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private void openMail(Mail mail) {
        this.openMail = mail;
        this.field_73887_h.clear();
        this.field_73887_h.add(this.backButton);
        if (mail.title.contains("[Invitation]")) {
            this.field_73887_h.add(this.joinButton);
        } else {
            this.field_73887_h.add(this.replyButton);
        }
    }

    private void closeMail() {
        this.openMail = null;
        this.field_73887_h.clear();
        this.field_73887_h.add(this.deleteButton);
        this.field_73887_h.add(this.writeButton);
    }

    private void setWritingMail(boolean writingMail) {
        this.writingMail = writingMail;
        this.receiverName = null;
        this.receiverText.func_73782_a("");
        this.titleText.func_73782_a("");
        this.contentText.func_73782_a("");
        this.sendButton.field_73742_g = false;
        this.field_73887_h.clear();
        if (this.writingMail) {
            this.field_73887_h.add(this.backButton);
            this.field_73887_h.add(this.sendButton);
            this.receiverText.func_73796_b(true);
        } else {
            this.field_73887_h.add(this.writeButton);
            this.field_73887_h.add(this.deleteButton);
            this.receiverText.func_73796_b(false);
        }
    }

    protected void func_73875_a(GuiButton button) {
        Mail mail;
        if (button == this.backButton) {
            if (this.writingMail) {
                this.setWritingMail(false);
            } else {
                this.closeMail();
            }
        } else if (button == this.deleteButton) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarkMailDeletedPacket()));
            ClientData.getMail().clear();
            Minecraft.func_71410_x().func_71373_a((GuiScreen)this);
        } else if (button == this.writeButton) {
            this.setWritingMail(true);
        } else if (button == this.sendButton) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NewMailPacket(this.receiverName, this.titleText.func_73781_b(), this.contentText.func_73781_b())));
            this.setWritingMail(false);
        } else if (button == this.replyButton) {
            Mail mail2 = this.openMail;
            this.closeMail();
            this.setWritingMail(true);
            this.receiverText.func_73782_a(mail2.sender);
            this.receiverName = mail2.sender;
        } else if (button == this.joinButton && (mail = this.openMail) != null) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket(mail.title.split(" ")[1])));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGui_OLD(mail.title.split(" ")[1]));
        }
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(7);
        tessellator.func_78374_a((double)x, (double)(y + height), 0.0, (double)(u * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + width), (double)(y + height), 0.0, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + width), (double)y, 0.0, (double)((u + (float)uWidth) * f), (double)(v * f1));
        tessellator.func_78374_a((double)x, (double)y, 0.0, (double)(u * f), (double)(v * f1));
        tessellator.func_78381_a();
    }

    private float getSlide() {
        return ClientData.getMail().size() > 4 ? (float)(-(ClientData.getMail().size() - 4) * 35) * this.scrollBar.getSliderValue() : 0.0f;
    }
}

