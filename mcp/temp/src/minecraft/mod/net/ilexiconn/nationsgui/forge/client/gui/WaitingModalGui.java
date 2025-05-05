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

public class WaitingModalGui extends GuiScreen {

   public static int GUI_SCALE = 1;
   public String hoveredAction = "";
   public String displayMode = "quit";
   public Long timeGuiOpened = Long.valueOf(System.currentTimeMillis());
   protected int xSize = 428;
   protected int ySize = 174;
   protected int guiLeft;
   protected int guiTop;


   public WaitingModalGui(String displayMode) {
      this.displayMode = displayMode;
      this.timeGuiOpened = Long.valueOf(System.currentTimeMillis());
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      if(this.displayMode.equalsIgnoreCase("join") && System.currentTimeMillis() - this.timeGuiOpened.longValue() > 30000L) {
         ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
         ClientData.waitingServerName = null;
         this.field_73882_e.func_71373_a((GuiScreen)null);
      } else if(ClientData.waitingServerName == null) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
      } else {
         this.hoveredAction = "";
         Gui.func_73734_a(0, 0, this.field_73880_f, this.field_73881_g, -2145049275);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ClientEventHandler.STYLE.bindTexture("overlay_hud");
         ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(813 * GUI_SCALE), (float)(122 * GUI_SCALE), 857 * GUI_SCALE, 348 * GUI_SCALE, this.xSize, this.ySize, (float)(1920 * GUI_SCALE), (float)(1033 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.title"), (float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + 27), 16777215, 1.0F, "center", false, "minecraftDungeons", 30);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.modal." + this.displayMode + ".1").replaceAll("<position>", ClientData.waitingPosition + "").replaceAll("<total>", ClientData.waitingTotal + "").replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), (float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + 65), 14277081, 0.5F, "center", false, "georamaRegular", 30);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.modal." + this.displayMode + ".2").replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), (float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + 85), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
         boolean hoveringQuitBtn = mouseX >= this.guiLeft + 116 && mouseX < this.guiLeft + 116 + 92 && mouseY >= this.guiTop + 118 && mouseY < this.guiTop + 118 + 24;
         if(hoveringQuitBtn) {
            this.hoveredAction = "yes";
            ClientEventHandler.STYLE.bindTexture("overlay_hud");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 116), (float)(this.guiTop + 118), (float)(816 * GUI_SCALE), (float)(478 * GUI_SCALE), 185 * GUI_SCALE, 48 * GUI_SCALE, 92, 24, (float)(1920 * GUI_SCALE), (float)(1033 * GUI_SCALE), false);
         }

         String labelBtnYes = I18n.func_135053_a("waiting.btn.yes");
         if(this.displayMode.equalsIgnoreCase("join")) {
            int hoveringStayBtn = (int)(30000L - (System.currentTimeMillis() - this.timeGuiOpened.longValue())) / 1000;
            labelBtnYes = I18n.func_135053_a("waiting.btn.yes") + " (" + hoveringStayBtn + ")";
         }

         ModernGui.drawScaledStringCustomFont(labelBtnYes, (float)(this.guiLeft + 116 + 47), (float)(this.guiTop + 118 + 8), hoveringQuitBtn?1315867:1315867, 0.5F, "center", false, "georamaBold", 35);
         boolean hoveringStayBtn1 = mouseX >= this.guiLeft + 220 && mouseX < this.guiLeft + 220 + 92 && mouseY >= this.guiTop + 118 && mouseY < this.guiTop + 118 + 24;
         if(hoveringStayBtn1) {
            this.hoveredAction = "no";
            ClientEventHandler.STYLE.bindTexture("overlay_hud");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 220), (float)(this.guiTop + 118), (float)(1024 * GUI_SCALE), (float)(478 * GUI_SCALE), 185 * GUI_SCALE, 48 * GUI_SCALE, 92, 24, (float)(1920 * GUI_SCALE), (float)(1033 * GUI_SCALE), false);
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.btn.no"), (float)(this.guiLeft + 220 + 47), (float)(this.guiTop + 118 + 8), hoveringStayBtn1?16777215:16777215, 0.5F, "center", false, "georamaBold", 35);
      }
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if(this.hoveredAction.equalsIgnoreCase("yes")) {
            if(this.displayMode.equalsIgnoreCase("join")) {
               ClientSocket.connectPlayerToServer(ClientData.waitingServerIpPort);
            }

            ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
            ClientData.waitingServerName = null;
            if(this.field_73882_e.field_71462_r instanceof WaitingModalGui) {
               this.field_73882_e.func_71373_a((GuiScreen)null);
            }

            if(this.displayMode.equalsIgnoreCase("switch") && ClientSocket.out != null) {
               ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerNeedConfirmation);
               ClientData.waitingServerName = ClientData.waitingServerNeedConfirmation;
               ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
               ClientData.waitingServerNeedConfirmation = null;
               WaitingSocketGui.askForConfirmation = false;
            }
         } else if(this.hoveredAction.equalsIgnoreCase("no")) {
            if(this.displayMode.equalsIgnoreCase("join")) {
               ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
               ClientData.waitingServerName = null;
            }

            this.field_73882_e.func_71373_a((GuiScreen)null);
         }
      }

   }

}
