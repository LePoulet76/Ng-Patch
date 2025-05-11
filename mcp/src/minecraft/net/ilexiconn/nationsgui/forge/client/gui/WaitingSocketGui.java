/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.gui.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WaitingSocketGui
extends GuiScreen {
    public static int position = 0;
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/screen-queue_bg.png");
    private static final ResourceLocation LOGO_GLOBE = new ResourceLocation("nationsgui", "textures/gui/ic_ng_white.png");
    private static final ResourceLocation LOGO_TEXT = new ResourceLocation("nationsgui", "textures/gui/ic_text_white.png");
    private CFontRenderer cFontRenderer;
    private CFontRenderer cFontRendererLarge;
    public static SoundStreamer player;
    public static boolean askForConfirmation;
    public Long timeOpenGUI = System.currentTimeMillis();
    public String hoveredAction = "";
    private RenderItem itemRenderer = new RenderItem();

    public WaitingSocketGui() {
        try {
            this.cFontRenderer = FontManager.createFont("nationsgui", "SourceSansPro-Regular.ttf");
            this.cFontRenderer.setFontSize(14.0f);
            this.cFontRendererLarge = FontManager.createFont("nationsgui", "SourceSansPro-Regular.ttf");
            this.cFontRendererLarge.setFontSize(16.0f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ClientProxy.multiRespawn = false;
        PlayerListGUI.topText = "";
        PlayerListGUI.bottomText = "";
        askForConfirmation = false;
        WaitingSocketGui.generateSteamer();
        new Thread(player).start();
        this.timeOpenGUI = System.currentTimeMillis();
    }

    public void func_73874_b() {
        super.func_73874_b();
        if (player.isPlaying()) {
            player.forceClose();
        }
    }

    public static SoundStreamer generateSteamer() {
        player = new SoundStreamer("https://static.nationsglory.fr/N4__N3N63N.mp3");
        player.setLooping(true);
        player.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
        return player;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAction = "";
        ArrayList<String> tooltipToDraw = new ArrayList<String>();
        ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_waiting");
        ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0f, 2160.0f, false);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
        boolean hoveringCross = mouseX >= this.field_73880_f - 30 && mouseX < this.field_73880_f - 30 + 17 && mouseY >= 10 && mouseY < 27;
        ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 30, 10.0f, (hoveringCross ? 758 : 698) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 141 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 17, 17, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
        if (hoveringCross) {
            this.hoveredAction = "close";
            tooltipToDraw.add(I18n.func_135053_a((String)"waitqueue.close"));
        }
        boolean hoveringMusicBtn = mouseX >= this.field_73880_f - 30 - 20 && mouseX < this.field_73880_f - 30 - 20 + 17 && mouseY >= 10 && mouseY < 27;
        ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 30 - 20, 10.0f, (hoveringMusicBtn ? (player.isPlaying() ? 758 : 882) : (player.isPlaying() ? 822 : 698)) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 75 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 17, 17, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
        if (hoveringMusicBtn) {
            this.hoveredAction = "toggle_music";
        }
        if (ClientData.waitingPosition > 0 && System.currentTimeMillis() - this.timeOpenGUI > 1000L) {
            int offetX = 35;
            int offsetY = 0;
            ModernGui.drawScaledStringCustomFont(ClientData.waitingPriority ? I18n.func_135053_a((String)"waitqueue.waitingqueue.priority") : I18n.func_135053_a((String)"waitqueue.waitingqueue"), offetX, this.field_73881_g / 3 + offsetY, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 30);
            ModernGui.drawScaledStringCustomFont(askForConfirmation ? I18n.func_135053_a((String)"waitqueue.switch_queue") : I18n.func_135053_a((String)"waitqueue.server") + " " + ClientData.waitingServerName + " ...", offetX, this.field_73881_g / 3 + offsetY + 8, 0xFFFFFF, 0.5f, "left", false, "minecraftDungeons", 30);
            ModernGui.drawSectionStringCustomFont(askForConfirmation ? I18n.func_135053_a((String)"waitqueue.text_switch").replaceAll("<target>", ClientData.waitingServerNeedConfirmation.toUpperCase()).replaceAll("<server>", ClientData.waitingServerName.toUpperCase()) : (ClientData.waitingPriority ? I18n.func_135053_a((String)"waitqueue.full.priority") : I18n.func_135053_a((String)"waitqueue.full")), offetX, this.field_73881_g / 3 + offsetY + 30, 13619152, 0.5f, "left", false, "georamaRegular", 24, 7, 350);
            if (askForConfirmation) {
                boolean hoveringBtnYes = mouseX >= offetX && mouseX < offetX + 109 && mouseY >= this.field_73881_g / 3 + offsetY + 75 && mouseY < this.field_73881_g / 3 + offsetY + 75 + 16;
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
                ModernGui.drawScaledCustomSizeModalRect(offetX, this.field_73881_g / 3 + offsetY + 75, (hoveringBtnYes ? 1464 : 1113) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (hoveringBtnYes ? 110 : 169) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 327 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 109, 16, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waitqueue.btn_yes").replaceAll("<target>", ClientData.waitingServerNeedConfirmation.toUpperCase()), offetX + 54, this.field_73881_g / 3 + offsetY + 75 + 5, 0, 0.5f, "center", false, "georamaSemiBold", 23);
                if (hoveringBtnYes) {
                    this.hoveredAction = "switch_yes";
                }
                boolean hoveringBtnNo = mouseX >= offetX + 109 + 10 && mouseX < offetX + 109 + 10 + 109 && mouseY >= this.field_73881_g / 3 + offsetY + 75 && mouseY < this.field_73881_g / 3 + offsetY + 75 + 16;
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
                ModernGui.drawScaledCustomSizeModalRect(offetX + 109 + 10, this.field_73881_g / 3 + offsetY + 75, 1464 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (hoveringBtnNo ? 110 : 169) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 327 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 109, 16, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waitqueue.btn_no").replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), offetX + 109 + 10 + 54, this.field_73881_g / 3 + offsetY + 75 + 5, hoveringBtnNo ? 0 : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 23);
                if (hoveringBtnNo) {
                    this.hoveredAction = "switch_no";
                }
            } else {
                ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / " + ClientData.waitingTotal + " " + I18n.func_135053_a((String)"waitqueue.players"), offetX, this.field_73881_g / 3 + offsetY + 75, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
                double progress = ClientData.waitingTotal == 1 ? 1.0 : 1.0 - (double)ClientData.waitingPosition / (double)ClientData.waitingTotal;
                ModernGui.drawScaledCustomSizeModalRect(offetX, this.field_73881_g / 3 + offsetY + 85, 0.0f, 0.0f, 679 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 16 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 226, 5, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(offetX, this.field_73881_g / 3 + offsetY + 85, 0.0f, 32 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (int)(progress * 679.0 * (double)NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 16 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (int)(progress * 679.0 / 3.0), 5, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                boolean hoveringBtnHub = mouseX >= offetX && mouseX < offetX + 131 && mouseY >= this.field_73881_g / 3 + offsetY + 112 && mouseY < this.field_73881_g / 3 + offsetY + 112 + 16;
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
                ModernGui.drawScaledCustomSizeModalRect(offetX, this.field_73881_g / 3 + offsetY + 112, 0.0f, (hoveringBtnHub ? 141 : 77) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 395 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 131, 16, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waitqueue.btn_hub"), offetX + 65, this.field_73881_g / 3 + offsetY + 112 + 5, 0, 0.5f, "center", false, "georamaSemiBold", 23);
                if (hoveringBtnHub) {
                    this.hoveredAction = "hub";
                }
                boolean hoveringBtnStore = mouseX >= offetX + 131 + 10 && mouseX < offetX + 131 + 10 + 86 && mouseY >= this.field_73881_g / 3 + offsetY + 112 && mouseY < this.field_73881_g / 3 + offsetY + 112 + 16;
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
                ModernGui.drawScaledCustomSizeModalRect(offetX + 131 + 10, this.field_73881_g / 3 + offsetY + 112, 419 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (hoveringBtnStore ? 141 : 77) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 260 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 86, 16, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(ClientData.waitingPriority ? I18n.func_135053_a((String)"waitqueue.btn_store") : I18n.func_135053_a((String)"waitqueue.btn_ranks"), offetX + 131 + 10 + 43, this.field_73881_g / 3 + offsetY + 112 + 5, hoveringBtnStore ? 0 : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 23);
                if (hoveringBtnStore) {
                    String string = this.hoveredAction = ClientData.waitingPriority ? "store" : "ranks";
                }
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        super.func_73864_a(par1, par2, par3);
        if (this.hoveredAction.equalsIgnoreCase("close")) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new MainGUI());
            ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
            ClientData.waitingServerName = null;
        } else if (this.hoveredAction.equalsIgnoreCase("hub")) {
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
        } else if (this.hoveredAction.equalsIgnoreCase("store")) {
            try {
                Desktop.getDesktop().browse(new URI("https://nationsglory.fr/store"));
            }
            catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (this.hoveredAction.equalsIgnoreCase("ranks")) {
            try {
                Desktop.getDesktop().browse(new URI("https://nationsglory.fr/store/category/13"));
            }
            catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (this.hoveredAction.equalsIgnoreCase("toggle_music")) {
            if (player.isPlaying()) {
                player.forceClose();
            } else {
                new Thread(WaitingSocketGui.generateSteamer()).start();
            }
        } else if (this.hoveredAction.equalsIgnoreCase("switch_yes")) {
            if (ClientSocket.out != null) {
                ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
                ClientData.waitingServerName = null;
                ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerNeedConfirmation);
                ClientData.waitingServerName = ClientData.waitingServerNeedConfirmation;
                ClientData.waitingJoinTime = System.currentTimeMillis();
                ClientData.waitingServerNeedConfirmation = null;
                askForConfirmation = false;
            }
        } else if (this.hoveredAction.equalsIgnoreCase("switch_no")) {
            askForConfirmation = false;
            ClientData.waitingServerNeedConfirmation = null;
        }
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    static {
        askForConfirmation = false;
    }
}

