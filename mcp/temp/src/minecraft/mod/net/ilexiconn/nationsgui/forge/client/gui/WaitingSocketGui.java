package net.ilexiconn.nationsgui.forge.client.gui;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
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

public class WaitingSocketGui extends GuiScreen {

   public static int position = 0;
   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/screen-queue_bg.png");
   private static final ResourceLocation LOGO_GLOBE = new ResourceLocation("nationsgui", "textures/gui/ic_ng_white.png");
   private static final ResourceLocation LOGO_TEXT = new ResourceLocation("nationsgui", "textures/gui/ic_text_white.png");
   private CFontRenderer cFontRenderer;
   private CFontRenderer cFontRendererLarge;
   public static SoundStreamer player;
   public static boolean askForConfirmation = false;
   public Long timeOpenGUI = Long.valueOf(System.currentTimeMillis());
   public String hoveredAction = "";
   private RenderItem itemRenderer = new RenderItem();


   public WaitingSocketGui() {
      try {
         this.cFontRenderer = FontManager.createFont("nationsgui", "SourceSansPro-Regular.ttf");
         this.cFontRenderer.setFontSize(14.0F);
         this.cFontRendererLarge = FontManager.createFont("nationsgui", "SourceSansPro-Regular.ttf");
         this.cFontRendererLarge.setFontSize(16.0F);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      ClientProxy.multiRespawn = false;
      PlayerListGUI.topText = "";
      PlayerListGUI.bottomText = "";
      askForConfirmation = false;
      generateSteamer();
      (new Thread(player)).start();
      this.timeOpenGUI = Long.valueOf(System.currentTimeMillis());
   }

   public void func_73874_b() {
      super.func_73874_b();
      if(player.isPlaying()) {
         player.forceClose();
      }

   }

   public static SoundStreamer generateSteamer() {
      player = new SoundStreamer("https://static.nationsglory.fr/N4__N3N63N.mp3");
      player.setLooping(true);
      player.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15F);
      return player;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.hoveredAction = "";
      ArrayList tooltipToDraw = new ArrayList();
      ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_waiting");
      ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0F, 2160.0F, false);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
      boolean hoveringCross = mouseX >= this.field_73880_f - 30 && mouseX < this.field_73880_f - 30 + 17 && mouseY >= 10 && mouseY < 27;
      ModernGui.drawScaledCustomSizeModalRect((float)(this.field_73880_f - 30), 10.0F, (float)((hoveringCross?758:698) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(141 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 17, 17, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
      if(hoveringCross) {
         this.hoveredAction = "close";
         tooltipToDraw.add(I18n.func_135053_a("waitqueue.close"));
      }

      boolean hoveringMusicBtn = mouseX >= this.field_73880_f - 30 - 20 && mouseX < this.field_73880_f - 30 - 20 + 17 && mouseY >= 10 && mouseY < 27;
      ModernGui.drawScaledCustomSizeModalRect((float)(this.field_73880_f - 30 - 20), 10.0F, (float)((hoveringMusicBtn?(player.isPlaying()?758:882):(player.isPlaying()?822:698)) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(75 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 52 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 17, 17, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
      if(hoveringMusicBtn) {
         this.hoveredAction = "toggle_music";
      }

      if(ClientData.waitingPosition > 0 && System.currentTimeMillis() - this.timeOpenGUI.longValue() > 1000L) {
         byte offetX = 35;
         byte offsetY = 0;
         ModernGui.drawScaledStringCustomFont(ClientData.waitingPriority?I18n.func_135053_a("waitqueue.waitingqueue.priority"):I18n.func_135053_a("waitqueue.waitingqueue"), (float)offetX, (float)(this.field_73881_g / 3 + offsetY), 16777215, 0.5F, "left", false, "georamaRegular", 30);
         ModernGui.drawScaledStringCustomFont(askForConfirmation?I18n.func_135053_a("waitqueue.switch_queue"):I18n.func_135053_a("waitqueue.server") + " " + ClientData.waitingServerName + " ...", (float)offetX, (float)(this.field_73881_g / 3 + offsetY + 8), 16777215, 0.5F, "left", false, "minecraftDungeons", 30);
         ModernGui.drawSectionStringCustomFont(askForConfirmation?I18n.func_135053_a("waitqueue.text_switch").replaceAll("<target>", ClientData.waitingServerNeedConfirmation.toUpperCase()).replaceAll("<server>", ClientData.waitingServerName.toUpperCase()):(ClientData.waitingPriority?I18n.func_135053_a("waitqueue.full.priority"):I18n.func_135053_a("waitqueue.full")), (float)offetX, (float)(this.field_73881_g / 3 + offsetY + 30), 13619152, 0.5F, "left", false, "georamaRegular", 24, 7, 350);
         if(askForConfirmation) {
            boolean progress = mouseX >= offetX && mouseX < offetX + 109 && mouseY >= this.field_73881_g / 3 + offsetY + 75 && mouseY < this.field_73881_g / 3 + offsetY + 75 + 16;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
            ModernGui.drawScaledCustomSizeModalRect((float)offetX, (float)(this.field_73881_g / 3 + offsetY + 75), (float)((progress?1464:1113) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)((progress?110:169) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 327 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 109, 16, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waitqueue.btn_yes").replaceAll("<target>", ClientData.waitingServerNeedConfirmation.toUpperCase()), (float)(offetX + 54), (float)(this.field_73881_g / 3 + offsetY + 75 + 5), 0, 0.5F, "center", false, "georamaSemiBold", 23);
            if(progress) {
               this.hoveredAction = "switch_yes";
            }

            boolean hoveringBtnNo = mouseX >= offetX + 109 + 10 && mouseX < offetX + 109 + 10 + 109 && mouseY >= this.field_73881_g / 3 + offsetY + 75 && mouseY < this.field_73881_g / 3 + offsetY + 75 + 16;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
            ModernGui.drawScaledCustomSizeModalRect((float)(offetX + 109 + 10), (float)(this.field_73881_g / 3 + offsetY + 75), (float)(1464 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)((hoveringBtnNo?110:169) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 327 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 109, 16, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waitqueue.btn_no").replaceAll("<server>", ClientData.waitingServerName.toUpperCase()), (float)(offetX + 109 + 10 + 54), (float)(this.field_73881_g / 3 + offsetY + 75 + 5), hoveringBtnNo?0:16777215, 0.5F, "center", false, "georamaSemiBold", 23);
            if(hoveringBtnNo) {
               this.hoveredAction = "switch_no";
            }
         } else {
            ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / " + ClientData.waitingTotal + " " + I18n.func_135053_a("waitqueue.players"), (float)offetX, (float)(this.field_73881_g / 3 + offsetY + 75), 16777215, 0.5F, "left", false, "georamaSemiBold", 24);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
            double progress1 = ClientData.waitingTotal == 1?1.0D:1.0D - (double)ClientData.waitingPosition / (double)ClientData.waitingTotal;
            ModernGui.drawScaledCustomSizeModalRect((float)offetX, (float)(this.field_73881_g / 3 + offsetY + 85), 0.0F, 0.0F, 679 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 16 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 226, 5, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)offetX, (float)(this.field_73881_g / 3 + offsetY + 85), 0.0F, (float)(32 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (int)(progress1 * 679.0D * (double)NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 16 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, (int)(progress1 * 679.0D / 3.0D), 5, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            boolean hoveringBtnHub = mouseX >= offetX && mouseX < offetX + 131 && mouseY >= this.field_73881_g / 3 + offsetY + 112 && mouseY < this.field_73881_g / 3 + offsetY + 112 + 16;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
            ModernGui.drawScaledCustomSizeModalRect((float)offetX, (float)(this.field_73881_g / 3 + offsetY + 112), 0.0F, (float)((hoveringBtnHub?141:77) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 395 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 131, 16, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waitqueue.btn_hub"), (float)(offetX + 65), (float)(this.field_73881_g / 3 + offsetY + 112 + 5), 0, 0.5F, "center", false, "georamaSemiBold", 23);
            if(hoveringBtnHub) {
               this.hoveredAction = "hub";
            }

            boolean hoveringBtnStore = mouseX >= offetX + 131 + 10 && mouseX < offetX + 131 + 10 + 86 && mouseY >= this.field_73881_g / 3 + offsetY + 112 && mouseY < this.field_73881_g / 3 + offsetY + 112 + 16;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
            ModernGui.drawScaledCustomSizeModalRect((float)(offetX + 131 + 10), (float)(this.field_73881_g / 3 + offsetY + 112), (float)(419 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)((hoveringBtnStore?141:77) * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 260 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 48 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 86, 16, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(ClientData.waitingPriority?I18n.func_135053_a("waitqueue.btn_store"):I18n.func_135053_a("waitqueue.btn_ranks"), (float)(offetX + 131 + 10 + 43), (float)(this.field_73881_g / 3 + offsetY + 112 + 5), hoveringBtnStore?0:16777215, 0.5F, "center", false, "georamaSemiBold", 23);
            if(hoveringBtnStore) {
               this.hoveredAction = ClientData.waitingPriority?"store":"ranks";
            }
         }
      }

      if(!tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

   }

   protected void func_73864_a(int par1, int par2, int par3) {
      super.func_73864_a(par1, par2, par3);
      if(this.hoveredAction.equalsIgnoreCase("close")) {
         Minecraft.func_71410_x().func_71373_a(new MainGUI());
         ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
         ClientData.waitingServerName = null;
      } else if(this.hoveredAction.equalsIgnoreCase("hub")) {
         ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
      } else if(this.hoveredAction.equalsIgnoreCase("store")) {
         try {
            Desktop.getDesktop().browse(new URI("https://nationsglory.fr/store"));
         } catch (URISyntaxException var6) {
            var6.printStackTrace();
         }
      } else if(this.hoveredAction.equalsIgnoreCase("ranks")) {
         try {
            Desktop.getDesktop().browse(new URI("https://nationsglory.fr/store/category/13"));
         } catch (URISyntaxException var5) {
            var5.printStackTrace();
         }
      } else if(this.hoveredAction.equalsIgnoreCase("toggle_music")) {
         if(player.isPlaying()) {
            player.forceClose();
         } else {
            (new Thread(generateSteamer())).start();
         }
      } else if(this.hoveredAction.equalsIgnoreCase("switch_yes")) {
         if(ClientSocket.out != null) {
            ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
            ClientData.waitingServerName = null;
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerNeedConfirmation);
            ClientData.waitingServerName = ClientData.waitingServerNeedConfirmation;
            ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
            ClientData.waitingServerNeedConfirmation = null;
            askForConfirmation = false;
         }
      } else if(this.hoveredAction.equalsIgnoreCase("switch_no")) {
         askForConfirmation = false;
         ClientData.waitingServerNeedConfirmation = null;
      }

   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
