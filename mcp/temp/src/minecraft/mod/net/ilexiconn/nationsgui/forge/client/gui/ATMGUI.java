package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ATMConvertPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ATMDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ATMGUI extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static int COLOR_LIGHT_GRAY = 10395075;
   public static int COLOR_DARK_BLUE = 2499659;
   public static int COLOR_LIGHT_BLUE = 6249630;
   public static int COLOR_WHITE = 14342893;
   public static int COLOR_PINK = 11431662;
   public static HashMap<String, Object> data = new HashMap();
   public static CFontRenderer semiBold40 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(40));
   public static Gson gson = new Gson();
   public static boolean loaded = false;
   public String hoveredAction = "";
   public int xSize = 221;
   public int ySize = 187;
   public RenderItem itemRenderer = new RenderItem();
   public int guiLeft;
   public int guiTop;
   public List<String> tooltipToDraw = new ArrayList();
   public static int lastBalanceDollars = -1;
   public static int lastBalanceOrbs = -1;
   public static int lastATMDollars = -1;
   public static int lastATMPlaytime = -1;
   public static long lastAmountAnimation = -1L;


   public ATMGUI() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ATMDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(this.hoveredAction.equals("convert_dollars")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ATMConvertPacket("dollars")));
         } else if(this.hoveredAction.equals("convert_orbs")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ATMConvertPacket("orbs_playtime")));
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.tooltipToDraw.clear();
      this.hoveredAction = "";
      ClientEventHandler.STYLE.bindTexture("atm");
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      ClientEventHandler.STYLE.bindTexture("atm");
      if(mouseX >= this.guiLeft + 225 && mouseX <= this.guiLeft + 225 + 10 && mouseY >= this.guiTop + 0 && mouseY <= this.guiTop + 0 + 10) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 225), (float)(this.guiTop + 0), (float)(245 * GUI_SCALE), (float)(0 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         this.hoveredAction = "close";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 225), (float)(this.guiTop + 0), (float)(225 * GUI_SCALE), (float)(0 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      }

      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.atm.title"), (float)(this.guiLeft + 9), (float)(this.guiTop + 3), COLOR_WHITE, 1.0F, "left", false, "georamaExtraBold", 28);
      if(loaded) {
         int balanceDollars = ((Double)data.get("player_dollars")).intValue();
         double hours;
         if(lastBalanceDollars != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
            double balanceOrbs = ((Double)data.get("player_dollars")).doubleValue() - (double)lastBalanceDollars;
            hours = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0D * balanceOrbs;
            balanceDollars = lastBalanceDollars + (int)hours;
         } else {
            lastBalanceDollars = balanceDollars;
         }

         ModernGui.drawScaledStringCustomFont(balanceDollars + "", (float)(this.guiLeft + 153), (float)this.guiTop + 9.5F, COLOR_LIGHT_GRAY, 0.5F, "right", false, "georamaSemiBold", 27);
         int balanceOrbs1 = ((Double)data.get("player_orbs")).intValue();
         if(lastBalanceOrbs != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
            double playtime = ((Double)data.get("player_orbs")).doubleValue() - (double)lastBalanceOrbs;
            double minutes = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0D * playtime;
            balanceOrbs1 = lastBalanceOrbs + (int)minutes;
         } else {
            lastBalanceOrbs = balanceOrbs1;
         }

         ModernGui.drawScaledStringCustomFont(balanceOrbs1 + "", (float)(this.guiLeft + 200), (float)this.guiTop + 9.5F, COLOR_LIGHT_GRAY, 0.5F, "right", false, "georamaSemiBold", 27);
         int playtime1 = ((Double)data.get("atm_minutes")).intValue();
         if(lastATMPlaytime != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
            hours = ((Double)data.get("atm_minutes")).doubleValue() - (double)lastATMPlaytime;
            double hoursStr = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0D * hours;
            playtime1 = lastATMPlaytime + (int)hoursStr;
         } else {
            lastATMPlaytime = playtime1;
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.atm.label.playtime").toUpperCase(), (float)(this.guiLeft + 20), (float)(this.guiTop + 33), COLOR_LIGHT_BLUE, 0.5F, "left", false, "georamaSemiBold", 27);
         int hours1 = playtime1 / 60;
         int minutes1 = playtime1 % 60;
         String hoursStr1 = hours1 < 10?"0" + hours1:"" + hours1;
         String minutesStr = minutes1 < 10?"0" + minutes1:"" + minutes1;
         ModernGui.drawScaledStringCustomFont(hoursStr1 + "h " + minutesStr + "min", (float)(this.guiLeft + 110), (float)(this.guiTop + 58), COLOR_WHITE, 1.0F, "center", false, "georamaBold", 30);
         boolean isMouseOver = mouseX >= this.guiLeft + 20 && mouseX <= this.guiLeft + 20 + 180 && mouseY >= this.guiTop + 94 && mouseY <= this.guiTop + 94 + 35;
         ClientEventHandler.STYLE.bindTexture("atm");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 20), (float)(this.guiTop + 94), (float)(96 * GUI_SCALE), (float)((isMouseOver?295:207) * GUI_SCALE), 180 * GUI_SCALE, 35 * GUI_SCALE, 180, 35, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.atm.label.convert"), (float)(this.guiLeft + 110), (float)(this.guiTop + 102), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
         int atmDollars = ((Double)data.get("atm_dollars")).intValue();
         if(lastATMDollars != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
            double gap = ((Double)data.get("atm_dollars")).doubleValue() - (double)lastATMDollars;
            double progress = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0D * gap;
            atmDollars = lastATMDollars + (int)progress;
         } else {
            lastATMDollars = playtime1;
         }

         ModernGui.drawScaledStringCustomFont(atmDollars + "", (float)(this.guiLeft + 112 - 4), (float)this.guiTop + 110.5F, isMouseOver?COLOR_DARK_BLUE:COLOR_WHITE, 0.5F, "center", false, "georamaSemiBold", 40);
         ClientEventHandler.STYLE.bindTexture("atm");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 112 - 4) + semiBold40.getStringWidth(atmDollars + "") / 2.0F / 2.0F + 2.0F, (float)this.guiTop + 111.5F, (float)(325 * GUI_SCALE), (float)((isMouseOver?194:233) * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ClientEventHandler.STYLE.bindTexture("atm");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 190), (float)(this.guiTop + 120), (float)(313 * GUI_SCALE), (float)(246 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)data.get("player_dollars_per_minute")}) + "$/min", (float)(this.guiLeft + 188), (float)(this.guiTop + 120), COLOR_DARK_BLUE, 0.5F, "right", false, "georamaMedium", 24);
         if(mouseX >= this.guiLeft + 190 && mouseX <= this.guiLeft + 190 + 6 && mouseY >= this.guiTop + 120 && mouseY <= this.guiTop + 120 + 6) {
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("gui.atm.tooltip.dollars_per_minute").split("#")));
         }

         if(isMouseOver) {
            this.hoveredAction = "convert_dollars";
         }

         isMouseOver = mouseX >= this.guiLeft + 21 && mouseX <= this.guiLeft + 21 + 86 && mouseY >= this.guiTop + 136 && mouseY <= this.guiTop + 136 + 35;
         ClientEventHandler.STYLE.bindTexture("atm");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 21), (float)(this.guiTop + 136), (float)(0 * GUI_SCALE), (float)((((Boolean)data.get("ngprime")).booleanValue()?383:(isMouseOver?251:207)) * GUI_SCALE), 86 * GUI_SCALE, 35 * GUI_SCALE, 86, 35, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("gui.atm.label.convert_orb_to_playtime").replaceAll("<orbs>", balanceOrbs1 + ""), (float)(this.guiLeft + 64), (float)(this.guiTop + 144), ((Boolean)data.get("ngprime")).booleanValue()?COLOR_LIGHT_BLUE:COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 30, 7, 120);
         if(isMouseOver) {
            this.hoveredAction = "convert_orbs";
         }
      }

      super.func_73863_a(mouseX, mouseY, par3);
      if(!this.tooltipToDraw.isEmpty()) {
         this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
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

   public boolean isNumeric(String str) {
      try {
         Double.parseDouble(str);
         return true;
      } catch (NumberFormatException var3) {
         return false;
      }
   }

}
