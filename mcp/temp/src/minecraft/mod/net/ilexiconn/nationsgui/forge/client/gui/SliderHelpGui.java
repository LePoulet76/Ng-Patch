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
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SliderHelpGui extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static int imageWidth = 1570;
   public static int imageHeight = 2160;
   public static float widthHeightRatio = 0.7268519F;
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
   public static List<String> validatedSlides = new ArrayList();


   public SliderHelpGui(String identifier, GuiScreen guiFrom) {
      this.identifier = identifier.split("##")[0];
      this.guiFrom = guiFrom;
      images = new ArrayList();
      wikiURL = "";
      this.timeOpenGUI = System.currentTimeMillis();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SliderHelpDataPacket(this.identifier)));
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.hoveredAction = "";
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if(!images.isEmpty()) {
         this.func_73873_v_();
         float translationXProgress = Math.min(1.0F, (float)(System.currentTimeMillis() - this.timeOpenGUI) / (float)translationXDuration);
         String url = (String)images.get(this.imageIndex);
         DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
         if(downloadableTexture != null && downloadableTexture.getBufferedImage() != null) {
            ModernGui.bindRemoteTexture(url);
            ModernGui.drawScaledCustomSizeModalRect(0.0F - (1.0F - translationXProgress) * (float)this.field_73881_g * widthHeightRatio, 0.0F, 0.0F, 0.0F, imageWidth, imageHeight, (int)((float)this.field_73881_g * widthHeightRatio), this.field_73881_g, (float)imageWidth, (float)imageHeight, true);
            if(translationXProgress == 1.0F) {
               float dotWidth = 8.0F * widthHeightRatio;
               float dotSpace = 2.0F * widthHeightRatio;
               float totalDotsWidth = (float)images.size() * (dotWidth + dotSpace) - dotSpace;
               float offsetX = (float)this.field_73881_g * widthHeightRatio / 2.0F - totalDotsWidth / 2.0F;

               float btnLeftWidth;
               float diffBtnWidth;
               for(int btnRightWidth = 0; btnRightWidth < images.size(); ++btnRightWidth) {
                  btnLeftWidth = offsetX + (float)btnRightWidth * (dotWidth + dotSpace);
                  diffBtnWidth = (float)this.field_73881_g * 0.925F;
                  ClientEventHandler.STYLE.bindTexture("slider_help");
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  boolean btnHeight = (float)mouseX >= btnLeftWidth && (float)mouseX <= btnLeftWidth + dotWidth && (float)mouseY >= diffBtnWidth && (float)mouseY <= diffBtnWidth + dotWidth;
                  byte btnSpace = 48;
                  if(btnRightWidth == this.imageIndex) {
                     btnSpace = 24;
                  } else if(btnRightWidth < this.imageIndex && btnHeight) {
                     btnSpace = 72;
                     this.hoveredAction = "dot#" + btnRightWidth;
                  } else if(btnRightWidth > this.imageIndex) {
                     btnSpace = 96;
                  }

                  ModernGui.drawScaledCustomSizeModalRect((float)((int)btnLeftWidth), (float)((int)diffBtnWidth), (float)(827 * GUI_SCALE), (float)(btnSpace * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, (int)dotWidth, (int)dotWidth, (float)(1920 * GUI_SCALE), (float)(1080 * GUI_SCALE), true);
               }

               float var21 = 112.0F * widthHeightRatio;
               btnLeftWidth = 90.0F * widthHeightRatio;
               diffBtnWidth = Math.abs(var21 - btnLeftWidth);
               float var22 = 24.0F * widthHeightRatio;
               float var23 = 4.0F * widthHeightRatio;
               float centerX = (float)this.field_73881_g * widthHeightRatio / 2.0F;
               if(this.lastNextTimer == 0L && !validatedSlides.contains(this.identifier + "##" + this.imageIndex)) {
                  this.lastNextTimer = System.currentTimeMillis();
               }

               int waitingSeconds = Math.max(0, (int)((nextButtonWaitingTime - (System.currentTimeMillis() - this.lastNextTimer)) / 1000L));
               String nextButtonSuffix = waitingSeconds > 0?" (" + waitingSeconds + ")":"";
               boolean hoveringBtnLeft;
               if(this.imageIndex == 0 && (wikiURL.isEmpty() || images.size() > 1)) {
                  ClientEventHandler.STYLE.bindTexture("slider_help");
                  hoveringBtnLeft = (float)mouseX >= centerX - var21 / 2.0F && (float)mouseX <= centerX + var21 / 2.0F && (float)mouseY >= (float)this.field_73881_g * 0.85F && (float)mouseY <= (float)this.field_73881_g * 0.85F + var22;
                  ModernGui.drawScaledCustomSizeModalRect(centerX - var21 / 2.0F, (float)this.field_73881_g * 0.85F, (float)(885 * GUI_SCALE), (float)((images.size() > 1 && waitingSeconds > 0?92:(hoveringBtnLeft?160:24)) * GUI_SCALE), 225 * GUI_SCALE, 48 * GUI_SCALE, (int)var21, (int)var22, (float)(1920 * GUI_SCALE), (float)(1080 * GUI_SCALE), true);
                  ModernGui.drawScaledStringCustomFont(images.size() > 1?I18n.func_135053_a("gui.slider_help.next") + nextButtonSuffix:I18n.func_135053_a("gui.slider_help.end"), centerX, (float)this.field_73881_g * 0.85F + var22 * 0.3F, 1315867, 0.5F, "center", false, "georamaSemiBold", 25);
                  if(hoveringBtnLeft && waitingSeconds == 0) {
                     this.hoveredAction = "next";
                  }
               } else {
                  ClientEventHandler.STYLE.bindTexture("slider_help");
                  hoveringBtnLeft = (float)mouseX >= centerX - diffBtnWidth / 2.0F - var23 / 2.0F - btnLeftWidth && (float)mouseX <= centerX - diffBtnWidth / 2.0F - var23 / 2.0F && (float)mouseY >= (float)this.field_73881_g * 0.85F && (float)mouseY <= (float)this.field_73881_g * 0.85F + var22;
                  ModernGui.drawScaledCustomSizeModalRect(centerX - diffBtnWidth / 2.0F - var23 / 2.0F - btnLeftWidth, (float)this.field_73881_g * 0.85F, (float)(1127 * GUI_SCALE), (float)((hoveringBtnLeft?160:24) * GUI_SCALE), 181 * GUI_SCALE, 48 * GUI_SCALE, (int)btnLeftWidth, (int)var22, (float)(1920 * GUI_SCALE), (float)(1080 * GUI_SCALE), true);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a(this.imageIndex == images.size() - 1 && !wikiURL.isEmpty()?"gui.slider_help.wiki":"gui.slider_help.back"), centerX - diffBtnWidth / 2.0F - var23 / 2.0F - btnLeftWidth / 2.0F, (float)this.field_73881_g * 0.85F + var22 * 0.3F, hoveringBtnLeft?1315867:16777215, 0.5F, "center", false, "georamaSemiBold", 25);
                  if(hoveringBtnLeft) {
                     this.hoveredAction = this.imageIndex == images.size() - 1 && !wikiURL.isEmpty()?"wiki":"back";
                  }

                  ClientEventHandler.STYLE.bindTexture("slider_help");
                  boolean hoveringBtnRight = (float)mouseX >= centerX + diffBtnWidth / 2.0F + var23 / 2.0F && (float)mouseX <= centerX + diffBtnWidth / 2.0F + var23 / 2.0F + var21 && (float)mouseY >= (float)this.field_73881_g * 0.85F && (float)mouseY <= (float)this.field_73881_g * 0.85F + var22;
                  ModernGui.drawScaledCustomSizeModalRect(centerX - diffBtnWidth / 2.0F + var23 / 2.0F, (float)this.field_73881_g * 0.85F, (float)(885 * GUI_SCALE), (float)((waitingSeconds > 0?92:(hoveringBtnRight?160:24)) * GUI_SCALE), 225 * GUI_SCALE, 48 * GUI_SCALE, (int)var21, (int)var22, (float)(1920 * GUI_SCALE), (float)(1080 * GUI_SCALE), true);
                  ModernGui.drawScaledStringCustomFont(this.imageIndex == images.size() - 1?I18n.func_135053_a("gui.slider_help.end") + nextButtonSuffix:I18n.func_135053_a("gui.slider_help.next") + nextButtonSuffix, centerX - diffBtnWidth / 2.0F + var23 / 2.0F + var21 / 2.0F, (float)this.field_73881_g * 0.85F + var22 * 0.3F, 1315867, 0.5F, "center", false, "georamaSemiBold", 25);
                  if(hoveringBtnRight && waitingSeconds == 0) {
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
      if(keyCode != 1) {
         super.func_73869_a(typedChar, keyCode);
      }
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.startsWith("dot")) {
            this.imageIndex = Integer.parseInt(this.hoveredAction.split("#")[1]);
         } else if(this.hoveredAction.equals("next")) {
            validatedSlides.add(this.identifier + "##" + this.imageIndex);
            if(this.imageIndex < images.size() - 1) {
               ++this.imageIndex;
               ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G4456.mp3", 1.0F);
               if(!validatedSlides.contains(this.identifier + "##" + this.imageIndex)) {
                  this.lastNextTimer = System.currentTimeMillis();
               }
            } else {
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
               ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G445N.mp3", 1.0F);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket(this.identifier)));
               if(this.guiFrom != null) {
                  Minecraft.func_71410_x().func_71373_a(this.guiFrom);
               }
            }
         } else if(this.hoveredAction.equals("back")) {
            this.lastNextTimer = 0L;
            this.imageIndex = Math.max(0, this.imageIndex - 1);
         } else if(this.hoveredAction.equals("wiki")) {
            try {
               Class t = Class.forName("java.awt.Desktop");
               Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
               t.getMethod("browse", new Class[]{URI.class}).invoke(theDesktop, new Object[]{URI.create(wikiURL)});
            } catch (Throwable var6) {
               var6.printStackTrace();
            }
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
