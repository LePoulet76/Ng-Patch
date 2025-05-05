package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class AssaultResultGUI extends GuiScreen {

   public static int GUI_SCALE = 5;
   public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(25));
   protected int xSize = 244;
   private int guiLeft;
   private int guiTop;
   public HashMap<String, String> infos = new HashMap();
   protected int ySize = 205;
   private String hoveredAction = "";


   public AssaultResultGUI(HashMap<String, String> infos) {
      this.infos = infos;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.hoveredAction = "";
      if(this.infos != null) {
         ClientEventHandler.STYLE.bindTexture("assault_resume");
         ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, (float)(0 * GUI_SCALE), (float)((((String)this.infos.get("result")).equals("victory")?221:597) * GUI_SCALE), 640 * GUI_SCALE, 360 * GUI_SCALE, this.field_73880_f, this.field_73881_g, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)((((String)this.infos.get("result")).equals("victory")?0:260) * GUI_SCALE), (float)(0 * GUI_SCALE), 244 * GUI_SCALE, 205 * GUI_SCALE, this.xSize, this.ySize, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.field_73880_f - 20), 6.0F, (float)(519 * GUI_SCALE), (float)(0 * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         boolean isHoveringClose = mouseX >= this.field_73880_f - 20 && mouseX <= this.field_73880_f - 20 + 14 && mouseY >= 6 && mouseY <= 20;
         if(isHoveringClose) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.field_73880_f - 20), 6.0F, (float)((((String)this.infos.get("result")).equals("victory")?537:555) * GUI_SCALE), (float)(0 * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            this.hoveredAction = "close";
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.title." + (String)this.infos.get("result")), (float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + 0), ((String)this.infos.get("result")).equals("victory")?16171012:16711772, 2.5F, "center", true, "minecraftDungeons", 20);
         ModernGui.drawScaledStringCustomFont("Score :", (float)(this.guiLeft + this.xSize / 2 - 10) - dg25.getStringWidth((String)this.infos.get("score_total")) / 2.0F - 3.0F, (float)(this.guiTop + 45), 12434877, 0.5F, "right", true, "minecraftDungeons", 25);
         ModernGui.drawScaledStringCustomFont((String)this.infos.get("score_total"), (float)(this.guiLeft + this.xSize / 2 - 10), (float)(this.guiTop + 45), ((String)this.infos.get("result")).equals("victory")?16171012:16711772, 0.5F, "right", true, "minecraftDungeons", 25);
         ModernGui.drawScaledStringCustomFont(!((String)this.infos.get("result")).equals("defeat")?"+ " + (String)this.infos.get("mmr") + " MMR":"- " + (String)this.infos.get("mmr") + " MMR", (float)(this.guiLeft + this.xSize / 2 + 10), (float)(this.guiTop + 45), ((String)this.infos.get("result")).equals("victory")?16171012:16711772, 0.5F, "left", false, "minecraftDungeons", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.title.score"), (float)(this.guiLeft + 65), (float)(this.guiTop + 80), 12434877, 0.5F, "center", false, "georamaSemiBold", 28);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.score_mode"), (float)(this.guiLeft + 20), (float)(this.guiTop + 104), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + (String)this.infos.get("score_mode"), (float)(this.guiLeft + 110), (float)(this.guiTop + 104), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.score_missiles"), (float)(this.guiLeft + 20), (float)(this.guiTop + 115), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + (String)this.infos.get("score_missile"), (float)(this.guiLeft + 110), (float)(this.guiTop + 115), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.score_kills"), (float)(this.guiLeft + 20), (float)(this.guiTop + 126), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + (String)this.infos.get("score_kill"), (float)(this.guiLeft + 110), (float)(this.guiTop + 126), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont("TOTAL", (float)(this.guiLeft + 20), (float)(this.guiTop + 148), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont((String)this.infos.get("score_total"), (float)(this.guiLeft + 110), (float)(this.guiTop + 148), ((String)this.infos.get("result")).equals("victory")?16171012:16711772, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.title.assault"), (float)(this.guiLeft + this.xSize - 65), (float)(this.guiTop + 80), 12434877, 0.5F, "center", false, "georamaSemiBold", 28);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.duration"), (float)(this.guiLeft + 134), (float)(this.guiTop + 104), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(this.formatDurationMilliSeconds(Long.valueOf(Long.parseLong((String)this.infos.get("duration")))), (float)(this.guiLeft + 224), (float)(this.guiTop + 104), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.kills"), (float)(this.guiLeft + 134), (float)(this.guiTop + 115), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont((String)this.infos.get("kills"), (float)(this.guiLeft + 224), (float)(this.guiTop + 115), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.missile_blocks_destroyed"), (float)(this.guiLeft + 134), (float)(this.guiTop + 126), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont((String)this.infos.get("missile_blocks_destroyed"), (float)(this.guiLeft + 224), (float)(this.guiTop + 126), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.missile_blocks_destroyed_own"), (float)(this.guiLeft + 134), (float)(this.guiTop + 137), 12434877, 0.5F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont((String)this.infos.get("missile_blocks_destroyed_own"), (float)(this.guiLeft + 224), (float)(this.guiTop + 137), 12434877, 0.5F, "right", false, "georamaSemiBold", 25);
         boolean isBtnHovered = mouseX >= this.guiLeft + 0 && mouseX <= this.guiLeft + 0 + this.xSize && mouseY >= this.guiTop + 183 && mouseY <= this.guiTop + 183 + 23;
         if(isBtnHovered) {
            ClientEventHandler.STYLE.bindTexture("assault_resume");
            ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 183), (float)(519 * GUI_SCALE), (float)((((String)this.infos.get("result")).equals("victory")?18:44) * GUI_SCALE), this.xSize * GUI_SCALE, 22 * GUI_SCALE, this.xSize, 22, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            this.hoveredAction = "close";
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("assault.label.continue"), (float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + 190), 16777215, 0.5F, "center", false, "georamaSemiBold", 28);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && this.hoveredAction.equalsIgnoreCase("close")) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }

   public String formatDurationMilliSeconds(Long time) {
      int seconds = time.intValue() / 1000;
      return seconds < 60?this.twoDigitString(seconds % 60) + "s":(seconds < 3600?seconds % 3600 / 60 + "m" + this.twoDigitString(seconds % 60) + "s":seconds / 3600 + "h" + this.twoDigitString(seconds % 3600 / 60) + "m" + this.twoDigitString(seconds %= 60) + "s");
   }

   public String twoDigitString(int number) {
      return number == 0?"00":(number / 10 == 0?"0" + number:String.valueOf(number));
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

}
