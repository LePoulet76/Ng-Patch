/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DialogExecPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SliderHelpDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SliderHelpGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static int imageWidth = 1570;
    public static int imageHeight = 2160;
    public static float widthHeightRatio = 0.7268519f;
    public static long translationXDuration = 150L;
    public static long nextButtonWaitingTime = 5000L;
    public static ArrayList<String> images = new ArrayList();
    public static String wikiURL = "";
    public static String command = "";
    public String hoveredAction = "";
    private String identifier;
    private GuiScreen guiFrom;
    private int imageIndex = 0;
    private long timeOpenGUI = 0L;
    private long lastNextTimer = 0L;
    public static List<String> validatedSlides = new ArrayList<String>();

    public SliderHelpGui(String identifier, GuiScreen guiFrom) {
        this.identifier = identifier.split("##")[0];
        this.guiFrom = guiFrom;
        images = new ArrayList();
        wikiURL = "";
        this.timeOpenGUI = System.currentTimeMillis();
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SliderHelpDataPacket(this.identifier)));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAction = "";
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (!images.isEmpty()) {
            this.func_73873_v_();
            float translationXProgress = Math.min(1.0f, (float)(System.currentTimeMillis() - this.timeOpenGUI) / (float)translationXDuration);
            String url = images.get(this.imageIndex);
            DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
            if (downloadableTexture != null && downloadableTexture.getBufferedImage() != null) {
                ModernGui.bindRemoteTexture(url);
                ModernGui.drawScaledCustomSizeModalRect(0.0f - (1.0f - translationXProgress) * (float)this.field_73881_g * widthHeightRatio, 0.0f, 0.0f, 0.0f, imageWidth, imageHeight, (int)((float)this.field_73881_g * widthHeightRatio), this.field_73881_g, imageWidth, imageHeight, true);
                if (translationXProgress == 1.0f) {
                    int waitingSeconds;
                    String nextButtonSuffix;
                    float dotWidth = 8.0f * widthHeightRatio;
                    float dotSpace = 2.0f * widthHeightRatio;
                    float totalDotsWidth = (float)images.size() * (dotWidth + dotSpace) - dotSpace;
                    float offsetX = (float)this.field_73881_g * widthHeightRatio / 2.0f - totalDotsWidth / 2.0f;
                    for (int i = 0; i < images.size(); ++i) {
                        float x = offsetX + (float)i * (dotWidth + dotSpace);
                        float y = (float)this.field_73881_g * 0.925f;
                        ClientEventHandler.STYLE.bindTexture("slider_help");
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        boolean hoveringDot = (float)mouseX >= x && (float)mouseX <= x + dotWidth && (float)mouseY >= y && (float)mouseY <= y + dotWidth;
                        int dotTextureY = 48;
                        if (i == this.imageIndex) {
                            dotTextureY = 24;
                        } else if (i < this.imageIndex && hoveringDot) {
                            dotTextureY = 72;
                            this.hoveredAction = "dot#" + i;
                        } else if (i > this.imageIndex) {
                            dotTextureY = 96;
                        }
                        ModernGui.drawScaledCustomSizeModalRect((int)x, (int)y, 827 * GUI_SCALE, dotTextureY * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, (int)dotWidth, (int)dotWidth, 1920 * GUI_SCALE, 1080 * GUI_SCALE, true);
                    }
                    float btnRightWidth = 112.0f * widthHeightRatio;
                    float btnLeftWidth = 90.0f * widthHeightRatio;
                    float diffBtnWidth = Math.abs(btnRightWidth - btnLeftWidth);
                    float btnHeight = 24.0f * widthHeightRatio;
                    float btnSpace = 4.0f * widthHeightRatio;
                    float centerX = (float)this.field_73881_g * widthHeightRatio / 2.0f;
                    if (this.lastNextTimer == 0L && !validatedSlides.contains(this.identifier + "##" + this.imageIndex)) {
                        this.lastNextTimer = System.currentTimeMillis();
                    }
                    String string = nextButtonSuffix = (waitingSeconds = Math.max(0, (int)((nextButtonWaitingTime - (System.currentTimeMillis() - this.lastNextTimer)) / 1000L))) > 0 ? " (" + waitingSeconds + ")" : "";
                    if (this.imageIndex == 0 && (wikiURL.isEmpty() || images.size() > 1)) {
                        ClientEventHandler.STYLE.bindTexture("slider_help");
                        boolean hoveringBtn = (float)mouseX >= centerX - btnRightWidth / 2.0f && (float)mouseX <= centerX + btnRightWidth / 2.0f && (float)mouseY >= (float)this.field_73881_g * 0.85f && (float)mouseY <= (float)this.field_73881_g * 0.85f + btnHeight;
                        ModernGui.drawScaledCustomSizeModalRect(centerX - btnRightWidth / 2.0f, (float)this.field_73881_g * 0.85f, 885 * GUI_SCALE, (images.size() > 1 && waitingSeconds > 0 ? 92 : (hoveringBtn ? 160 : 24)) * GUI_SCALE, 225 * GUI_SCALE, 48 * GUI_SCALE, (int)btnRightWidth, (int)btnHeight, 1920 * GUI_SCALE, 1080 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(images.size() > 1 ? I18n.func_135053_a((String)"gui.slider_help.next") + nextButtonSuffix : I18n.func_135053_a((String)"gui.slider_help.end"), centerX, (float)this.field_73881_g * 0.85f + btnHeight * 0.3f, 0x14141B, 0.5f, "center", false, "georamaSemiBold", 25);
                        if (hoveringBtn && waitingSeconds == 0) {
                            this.hoveredAction = "next";
                        }
                    } else {
                        ClientEventHandler.STYLE.bindTexture("slider_help");
                        boolean hoveringBtnLeft = (float)mouseX >= centerX - diffBtnWidth / 2.0f - btnSpace / 2.0f - btnLeftWidth && (float)mouseX <= centerX - diffBtnWidth / 2.0f - btnSpace / 2.0f && (float)mouseY >= (float)this.field_73881_g * 0.85f && (float)mouseY <= (float)this.field_73881_g * 0.85f + btnHeight;
                        ModernGui.drawScaledCustomSizeModalRect(centerX - diffBtnWidth / 2.0f - btnSpace / 2.0f - btnLeftWidth, (float)this.field_73881_g * 0.85f, 1127 * GUI_SCALE, (hoveringBtnLeft ? 160 : 24) * GUI_SCALE, 181 * GUI_SCALE, 48 * GUI_SCALE, (int)btnLeftWidth, (int)btnHeight, 1920 * GUI_SCALE, 1080 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(this.imageIndex == images.size() - 1 && !wikiURL.isEmpty() ? "gui.slider_help.wiki" : "gui.slider_help.back")), centerX - diffBtnWidth / 2.0f - btnSpace / 2.0f - btnLeftWidth / 2.0f, (float)this.field_73881_g * 0.85f + btnHeight * 0.3f, hoveringBtnLeft ? 0x14141B : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 25);
                        if (hoveringBtnLeft) {
                            this.hoveredAction = this.imageIndex == images.size() - 1 && !wikiURL.isEmpty() ? "wiki" : "back";
                        }
                        ClientEventHandler.STYLE.bindTexture("slider_help");
                        boolean hoveringBtnRight = (float)mouseX >= centerX + diffBtnWidth / 2.0f + btnSpace / 2.0f && (float)mouseX <= centerX + diffBtnWidth / 2.0f + btnSpace / 2.0f + btnRightWidth && (float)mouseY >= (float)this.field_73881_g * 0.85f && (float)mouseY <= (float)this.field_73881_g * 0.85f + btnHeight;
                        ModernGui.drawScaledCustomSizeModalRect(centerX - diffBtnWidth / 2.0f + btnSpace / 2.0f, (float)this.field_73881_g * 0.85f, 885 * GUI_SCALE, (waitingSeconds > 0 ? 92 : (hoveringBtnRight ? 160 : 24)) * GUI_SCALE, 225 * GUI_SCALE, 48 * GUI_SCALE, (int)btnRightWidth, (int)btnHeight, 1920 * GUI_SCALE, 1080 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(this.imageIndex == images.size() - 1 ? I18n.func_135053_a((String)"gui.slider_help.end") + nextButtonSuffix : I18n.func_135053_a((String)"gui.slider_help.next") + nextButtonSuffix, centerX - diffBtnWidth / 2.0f + btnSpace / 2.0f + btnRightWidth / 2.0f, (float)this.field_73881_g * 0.85f + btnHeight * 0.3f, 0x14141B, 0.5f, "center", false, "georamaSemiBold", 25);
                        if (hoveringBtnRight && waitingSeconds == 0) {
                            this.hoveredAction = "next";
                        }
                    }
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (keyCode == 1) {
            return;
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.startsWith("dot")) {
                this.imageIndex = Integer.parseInt(this.hoveredAction.split("#")[1]);
            } else if (this.hoveredAction.equals("next")) {
                validatedSlides.add(this.identifier + "##" + this.imageIndex);
                if (this.imageIndex < images.size() - 1) {
                    ++this.imageIndex;
                    ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G4456.mp3", 1.0f);
                    if (!validatedSlides.contains(this.identifier + "##" + this.imageIndex)) {
                        this.lastNextTimer = System.currentTimeMillis();
                    }
                } else {
                    Minecraft.func_71410_x().func_71373_a(null);
                    ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G445N.mp3", 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket(this.identifier)));
                    if (this.guiFrom != null) {
                        Minecraft.func_71410_x().func_71373_a(this.guiFrom);
                    }
                }
            } else if (this.hoveredAction.equals("back")) {
                this.lastNextTimer = 0L;
                this.imageIndex = Math.max(0, this.imageIndex - 1);
            } else if (this.hoveredAction.equals("wiki")) {
                try {
                    Class<?> desktop = Class.forName("java.awt.Desktop");
                    Object theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    desktop.getMethod("browse", URI.class).invoke(theDesktop, URI.create(wikiURL));
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
}

