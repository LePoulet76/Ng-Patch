/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class WaitingModalGui
extends GuiScreen {
    public static int GUI_SCALE = 1;
    public String hoveredAction = "";
    public String displayMode = "quit";
    public Long timeGuiOpened = System.currentTimeMillis();
    protected int xSize = 428;
    protected int ySize = 174;
    protected int guiLeft;
    protected int guiTop;

    public WaitingModalGui(String displayMode) {
        this.displayMode = displayMode;
        this.timeGuiOpened = System.currentTimeMillis();
    }

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        boolean hoveringStayBtn;
        boolean hoveringQuitBtn;
        if (this.displayMode.equalsIgnoreCase("join") && System.currentTimeMillis() - this.timeGuiOpened > 30000L) {
            ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
            ClientData.waitingServerName = null;
            this.field_73882_e.func_71373_a(null);
            return;
        }
        if (ClientData.waitingServerName == null) {
            this.field_73882_e.func_71373_a(null);
            return;
        }
        this.hoveredAction = "";
        Gui.func_73734_a((int)0, (int)0, (int)this.field_73880_f, (int)this.field_73881_g, (int)-2145049275);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("overlay_hud");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 813 * GUI_SCALE, 122 * GUI_SCALE, 857 * GUI_SCALE, 348 * GUI_SCALE, this.xSize, this.ySize, 1920 * GUI_SCALE, 1033 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waiting.title"), this.guiLeft + this.xSize / 2, this.guiTop + 27, 0xFFFFFF, 1.0f, "center", false, "minecraftDungeons", 30);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("waiting.modal." + this.displayMode + ".1")).replaceAll("<position>", ClientData.waitingPosition + "").replaceAll("<total>", ClientData.waitingTotal + "").replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), this.guiLeft + this.xSize / 2, this.guiTop + 65, 0xD9D9D9, 0.5f, "center", false, "georamaRegular", 30);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("waiting.modal." + this.displayMode + ".2")).replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), this.guiLeft + this.xSize / 2, this.guiTop + 85, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
        boolean bl = hoveringQuitBtn = mouseX >= this.guiLeft + 116 && mouseX < this.guiLeft + 116 + 92 && mouseY >= this.guiTop + 118 && mouseY < this.guiTop + 118 + 24;
        if (hoveringQuitBtn) {
            this.hoveredAction = "yes";
            ClientEventHandler.STYLE.bindTexture("overlay_hud");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 116, this.guiTop + 118, 816 * GUI_SCALE, 478 * GUI_SCALE, 185 * GUI_SCALE, 48 * GUI_SCALE, 92, 24, 1920 * GUI_SCALE, 1033 * GUI_SCALE, false);
        }
        String labelBtnYes = I18n.func_135053_a((String)"waiting.btn.yes");
        if (this.displayMode.equalsIgnoreCase("join")) {
            int leftTime = (int)(30000L - (System.currentTimeMillis() - this.timeGuiOpened)) / 1000;
            labelBtnYes = I18n.func_135053_a((String)"waiting.btn.yes") + " (" + leftTime + ")";
        }
        ModernGui.drawScaledStringCustomFont(labelBtnYes, this.guiLeft + 116 + 47, this.guiTop + 118 + 8, hoveringQuitBtn ? 0x14141B : 0x14141B, 0.5f, "center", false, "georamaBold", 35);
        boolean bl2 = hoveringStayBtn = mouseX >= this.guiLeft + 220 && mouseX < this.guiLeft + 220 + 92 && mouseY >= this.guiTop + 118 && mouseY < this.guiTop + 118 + 24;
        if (hoveringStayBtn) {
            this.hoveredAction = "no";
            ClientEventHandler.STYLE.bindTexture("overlay_hud");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 220, this.guiTop + 118, 1024 * GUI_SCALE, 478 * GUI_SCALE, 185 * GUI_SCALE, 48 * GUI_SCALE, 92, 24, 1920 * GUI_SCALE, 1033 * GUI_SCALE, false);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waiting.btn.no"), this.guiLeft + 220 + 47, this.guiTop + 118 + 8, hoveringStayBtn ? 0xFFFFFF : 0xFFFFFF, 0.5f, "center", false, "georamaBold", 35);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equalsIgnoreCase("yes")) {
                if (this.displayMode.equalsIgnoreCase("join")) {
                    ClientSocket.connectPlayerToServer(ClientData.waitingServerIpPort);
                }
                ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
                ClientData.waitingServerName = null;
                if (this.field_73882_e.field_71462_r instanceof WaitingModalGui) {
                    this.field_73882_e.func_71373_a(null);
                }
                if (this.displayMode.equalsIgnoreCase("switch") && ClientSocket.out != null) {
                    ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerNeedConfirmation);
                    ClientData.waitingServerName = ClientData.waitingServerNeedConfirmation;
                    ClientData.waitingJoinTime = System.currentTimeMillis();
                    ClientData.waitingServerNeedConfirmation = null;
                    WaitingSocketGui.askForConfirmation = false;
                }
            } else if (this.hoveredAction.equalsIgnoreCase("no")) {
                if (this.displayMode.equalsIgnoreCase("join")) {
                    ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
                    ClientData.waitingServerName = null;
                }
                this.field_73882_e.func_71373_a(null);
            }
        }
    }
}

