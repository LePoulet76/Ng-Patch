package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticBuyPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticTurnWheelPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CommonCosmeticGUI extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static int COLOR_LIGHT_GRAY = 10395075;
   public static int COLOR_DARK_BLUE = 2499659;
   public static int COLOR_LIGHT_BLUE = 6249630;
   public static int COLOR_WHITE = 14342893;
   public static int COLOR_PINK = 11431662;
   public static HashMap<String, Object> data = new HashMap();
   public static List<String> CATEGORIES_ORDER = Arrays.asList(new String[]{"hats", "chestplates", "capes", "hands", "armors", "items", "vehicles", "badges", "emotes", "buddies"});
   public static List<String> SERVERS_ORDER = Arrays.asList(new String[]{"red", "coral", "orange", "yellow", "lime", "green", "blue", "cyan", "pink", "purple", "white", "black", "mocha", "dev"});
   public static LinkedHashMap<String, Integer> SERVERS_COLOR = new CommonCosmeticGUI$1();
   public static CFontRenderer semiBold25 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(25));
   public static CFontRenderer semiBold27 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(27));
   public static CFontRenderer semiBold30 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(30));
   public static CFontRenderer semiBold32 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(32));
   public static CFontRenderer semiBold24 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(24));
   public static CFontRenderer bold28 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(28));
   public static CFontRenderer medium26 = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(26));
   public static LinkedHashMap<String, Integer> rarityColors = new CommonCosmeticGUI$2();
   public static LinkedHashMap<String, Integer> marketBackgroundByRarityY = new CommonCosmeticGUI$3();
   public static LinkedHashMap<String, Integer> modalColorByRarityY = new CommonCosmeticGUI$4();
   public static LinkedHashMap<String, Integer> categoryIcons49Y = new CommonCosmeticGUI$5();
   public static LinkedHashMap<String, Integer> eventIcons = new CommonCosmeticGUI$6();
   public static Gson gson = new Gson();
   public String hoveredAction = "";
   public int xSize = 463;
   public int ySize = 235;
   public int guiLeft;
   public int guiTop;
   public RenderItem itemRenderer = new RenderItem();
   public List<String> tooltipToDraw = new ArrayList();
   public boolean displayModal = false;
   public boolean displayNGWheel = false;
   public HashMap<String, String> itemToBuy = new HashMap();
   public HashMap<String, String> itemToBuyHover = new HashMap();
   public String categoryTarget = "";
   public String playerTarget;
   public static int lastPoints = -1;
   public static long lastPointsAnimation = -1L;
   private int targetWheelRotation = 0;
   private int currentWheelRotation = 0;
   private Long startWheelTime = Long.valueOf(0L);


   public static String formatTime(Long time) {
      long now = Long.parseLong((String)CosmeticGUI.data.get("serverTime")) + (System.currentTimeMillis() - CosmeticGUI.timeOpenGUI.longValue());
      long diff = time.longValue() - now;
      String date = "";
      if(diff > 0L) {
         long days = diff / 86400000L;
         long hours = 0L;
         long minutes = 0L;
         long seconds = 0L;
         if(days > 0L) {
            date = date + " " + days + I18n.func_135053_a("faction.common.days.short");
         }

         diff -= days * 86400000L;
         hours = diff / 3600000L;
         date = date + " " + hours + I18n.func_135053_a("faction.common.hours.short");
         diff -= hours * 3600000L;
         minutes = diff / 60000L;
         date = date + " " + minutes + I18n.func_135053_a("faction.common.minutes.short");
         if(days == 0L) {
            diff -= minutes * 60000L;
            seconds = diff / 1000L;
            date = date + " " + seconds + I18n.func_135053_a("faction.common.seconds.short");
         }

         return date.trim();
      } else {
         return I18n.func_135053_a("cosmetic.label.expired");
      }
   }

   public CommonCosmeticGUI() {
      this.displayModal = false;
      this.itemToBuy.clear();
      if(System.currentTimeMillis() - lastPointsAnimation > 10000L) {
         lastPointsAnimation = System.currentTimeMillis();
         lastPoints = 0;
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(this.hoveredAction.contains("store")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            MainGUI.openURL(System.getProperty("java.lang").equals("fr")?"https://nationsglory.fr/store":"https://nationsglory.com/store");
         } else if(this.hoveredAction.contains("open_url")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            MainGUI.openURL(this.hoveredAction.replaceAll("open_url#", ""));
         } else if(this.hoveredAction.contains("buy_prime")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            MainGUI.openURL(System.getProperty("java.lang").equals("fr")?"https://nationsglory.fr/store/package/96":"https://nationsglory.com/store/package/96");
         } else if(this.hoveredAction.contains("open_modal")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.displayModal = true;
            this.itemToBuy = (HashMap)this.itemToBuyHover.clone();
         } else if(this.hoveredAction.equals("open_ngprime")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.displayModal = true;
            this.displayNGWheel = true;
         } else if(this.hoveredAction.equals("close_modal")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.displayModal = false;
            this.itemToBuy.clear();
         } else if(this.hoveredAction.equals("buy")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            if(!this.itemToBuy.isEmpty()) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticBuyPacket(this.playerTarget, (String)this.itemToBuy.get("skin_name"), this.categoryTarget)));
            }

            this.displayModal = false;
            this.itemToBuy.clear();
         } else if(this.hoveredAction.contains("spin_wheel")) {
            this.hoveredAction = "";
            ClientProxy.playClientMusic("https://static.nationsglory.fr/N34322663N.mp3", 2.5F);
            this.startWheelTime = Long.valueOf(System.currentTimeMillis());
            this.targetWheelRotation = 1800 - (int)(((Double)CosmeticGUI.data.get("win")).doubleValue() / 12.0D * 360.0D);
            this.currentWheelRotation = 0;
            CosmeticGUI.data.put("lastWheel", System.currentTimeMillis() + "");
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticTurnWheelPacket()));
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("cosmetic");
      if(mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 13), (float)(593 * GUI_SCALE), (float)(132 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         this.hoveredAction = "close";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 13), (float)(593 * GUI_SCALE), (float)(119 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      }

      ClientEventHandler.STYLE.bindTexture("cosmetic");
      if(mouseX >= this.guiLeft + 381 && mouseX <= this.guiLeft + 381 + 57 && mouseY >= this.guiTop + 11 && mouseY <= this.guiTop + 11 + 14) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 381), (float)(this.guiTop + 11), (float)(619 * GUI_SCALE), (float)(136 * GUI_SCALE), 56 * GUI_SCALE, 14 * GUI_SCALE, 56, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.store"), (float)(this.guiLeft + 381 + 29), (float)(this.guiTop + 15), COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
         this.hoveredAction = "store";
      } else {
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.store"), (float)(this.guiLeft + 381 + 29), (float)(this.guiTop + 15), COLOR_WHITE, 0.5F, "center", false, "georamaSemiBold", 27);
      }

      ClientEventHandler.STYLE.bindTexture("cosmetic");
      int isPrime;
      double canSpin;
      if(CosmeticGUI.loaded) {
         isPrime = ((Double)CosmeticGUI.data.get("player_points")).intValue();
         if(lastPoints != -1 && System.currentTimeMillis() - lastPointsAnimation < 1000L) {
            canSpin = (double)(isPrime - lastPoints);
            Double label = Double.valueOf((double)(System.currentTimeMillis() - lastPointsAnimation) / 1000.0D * canSpin);
            isPrime = lastPoints + label.intValue();
         } else {
            lastPoints = isPrime;
         }

         ModernGui.drawScaledStringCustomFont(isPrime + "", (float)(this.guiLeft + 281), (float)this.guiTop + 14.5F, COLOR_LIGHT_GRAY, 0.5F, "right", false, "georamaSemiBold", 30);
      }

      ClientEventHandler.STYLE.bindTexture("cosmetic");
      if(mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 65 && mouseY >= this.guiTop + 11 && mouseY <= this.guiTop + 11 + 14) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 300), (float)(this.guiTop + 11), (float)(709 * GUI_SCALE), (float)(154 * GUI_SCALE), 75 * GUI_SCALE, 14 * GUI_SCALE, 75, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.prime_wheel"), (float)(this.guiLeft + 301 + 32), (float)(this.guiTop + 15), COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
         this.hoveredAction = "open_ngprime";
      } else {
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.prime_wheel"), (float)(this.guiLeft + 301 + 32), (float)(this.guiTop + 15), COLOR_WHITE, 0.5F, "center", false, "georamaSemiBold", 27);
      }

      if(this.displayModal) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.0F, 250.0F);
         Gui.func_73734_a(this.guiLeft, this.guiTop, this.guiLeft + this.xSize, this.guiTop + this.ySize, -1090519040);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         String var10;
         String var11;
         if(!this.itemToBuy.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 94), (float)(this.guiTop + 61), (float)(0 * GUI_SCALE), (float)(563 * GUI_SCALE), 276 * GUI_SCALE, 111 * GUI_SCALE, 276, 111, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 94), (float)(this.guiTop + 61), (float)(0 * GUI_SCALE), (float)(((Integer)modalColorByRarityY.get(this.itemToBuy.get("rarity"))).intValue() * GUI_SCALE), 276 * GUI_SCALE, 111 * GUI_SCALE, 276, 111, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if(mouseX >= this.guiLeft + 374 && mouseX <= this.guiLeft + 374 + 10 && mouseY >= this.guiTop + 63 && mouseY <= this.guiTop + 63 + 10) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 374), (float)(this.guiTop + 63), (float)(593 * GUI_SCALE), (float)(132 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               this.hoveredAction = "close_modal";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 374), (float)(this.guiTop + 63), (float)(593 * GUI_SCALE), (float)(119 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            if(mouseX < this.guiLeft + 94 || mouseX > this.guiLeft + 94 + 276 || mouseY < this.guiTop + 61 || mouseY > this.guiTop + 61 + 111) {
               this.hoveredAction = "close_modal";
            }

            if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)this.itemToBuy.get("skin_name")) != null) {
               ClientProxy.SKIN_MANAGER.getSkinFromID((String)this.itemToBuy.get("skin_name")).renderInGUI(this.guiLeft + 94 + 3, this.guiTop + 61 + 10, 4.0F, par3);
            }

            var10 = this.itemToBuy.containsKey("name_" + System.getProperty("java.lang"))?((String)this.itemToBuy.get("name_" + System.getProperty("java.lang"))).toUpperCase():((String)this.itemToBuy.get("skin_name")).toUpperCase();
            ModernGui.drawScaledStringCustomFont(var10, (float)(this.guiLeft + 200), (float)(this.guiTop + 85), CosmeticGUI.COLOR_LIGHT_GRAY, 0.75F, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            ModernGui.glColorHex(((Integer)CosmeticGUI.rarityColors.get(this.itemToBuy.get("rarity"))).intValue(), 1.0F);
            ModernGui.drawRoundedRectangle((float)(this.guiLeft + 200), (float)(this.guiTop + 73), 0.0F, 35.0F, 9.0F);
            var11 = I18n.func_135053_a("cosmetic.rarity." + (String)this.itemToBuy.get("rarity"));
            ModernGui.drawScaledStringCustomFont(var11, (float)(this.guiLeft + 200 + 17), (float)this.guiTop + 74.5F, COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
            ModernGui.drawSectionStringCustomFont(this.itemToBuy.containsKey("description_" + System.getProperty("java.lang"))?(String)this.itemToBuy.get("description_" + System.getProperty("java.lang")):"no description set", (float)(this.guiLeft + 200), (float)(this.guiTop + 102), CosmeticGUI.COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaMedium", 25, 9, 320);
            ModernGui.drawScaledStringCustomFont((String)this.itemToBuy.get("price"), (float)(this.guiLeft + 200), (float)(this.guiTop + 130), CosmeticGUI.COLOR_WHITE, 0.75F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 200) + 2.5F + (float)((int)((double)semiBold25.getStringWidth((String)this.itemToBuy.get("price")) * 0.75D)), (float)this.guiTop + 130.3F, (float)(594 * GUI_SCALE), (float)(179 * GUI_SCALE), 9 * GUI_SCALE, 8 * GUI_SCALE, 9, 8, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if(mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 39 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 200), (float)(this.guiTop + 145), (float)(283 * GUI_SCALE), (float)(579 * GUI_SCALE), 39 * GUI_SCALE, 14 * GUI_SCALE, 39, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.return"), (float)(this.guiLeft + 200 + 19), (float)(this.guiTop + 145) + 4.0F, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 25);
               this.hoveredAction = "close_modal";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 200), (float)(this.guiTop + 145), (float)(283 * GUI_SCALE), (float)(563 * GUI_SCALE), 39 * GUI_SCALE, 14 * GUI_SCALE, 39, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.return"), (float)(this.guiLeft + 200 + 19), (float)(this.guiTop + 145) + 4.0F, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 25);
            }

            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if((double)Integer.parseInt((String)this.itemToBuy.get("price")) <= ((Double)CosmeticGUI.data.get("player_points")).doubleValue()) {
               if(mouseX >= this.guiLeft + 245 && mouseX <= this.guiLeft + 245 + 46 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 245), (float)(this.guiTop + 145), (float)(283 * GUI_SCALE), (float)(611 * GUI_SCALE), 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.buy"), (float)(this.guiLeft + 245 + 23), (float)(this.guiTop + 145) + 4.0F, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 25);
                  this.hoveredAction = "buy";
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 245), (float)(this.guiTop + 145), (float)(283 * GUI_SCALE), (float)(595 * GUI_SCALE), 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.buy"), (float)(this.guiLeft + 245 + 23), (float)(this.guiTop + 145) + 4.0F, CosmeticGUI.COLOR_WHITE, 0.5F, "center", false, "georamaSemiBold", 25);
               }
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 245), (float)(this.guiTop + 145), (float)(283 * GUI_SCALE), (float)(627 * GUI_SCALE), 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.buy"), (float)(this.guiLeft + 245 + 23), (float)(this.guiTop + 145) + 4.0F, CosmeticGUI.COLOR_LIGHT_GRAY, 0.5F, "center", false, "georamaSemiBold", 25);
               if(mouseX >= this.guiLeft + 245 && mouseX <= this.guiLeft + 245 + 46 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
                  this.tooltipToDraw.add(I18n.func_135053_a("cosmetic.label.not_enough_points"));
               }
            }
         } else if(this.displayNGWheel) {
            ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 150), (float)(this.guiTop + 54), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 224 * GUI_SCALE, 115 * GUI_SCALE, 224, 115, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 77 + 14 - 90), (float)(this.guiTop + 37 + 14 - 90), (float)(61 * GUI_SCALE), (float)(270 * GUI_SCALE), 298 * GUI_SCALE, 298 * GUI_SCALE, 298, 298, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.guiLeft + 77 + 73), (float)(this.guiTop + 37 + 73), 0.0F);
            this.currentWheelRotation = this.targetWheelRotation;
            if(System.currentTimeMillis() - this.startWheelTime.longValue() < 3500L) {
               this.currentWheelRotation = (int)(0.0D + (double)(this.targetWheelRotation - 0) * (1.0D - Math.cos(3.141592653589793D * (double)(System.currentTimeMillis() - this.startWheelTime.longValue()) / 3500.0D)) / 2.0D);
            }

            GL11.glRotatef((float)this.currentWheelRotation, 0.0F, 0.0F, 1.0F);
            ModernGui.drawScaledCustomSizeModalRect(-73.0F, -73.0F, (float)(17 * GUI_SCALE), (float)(124 * GUI_SCALE), 146 * GUI_SCALE, 146 * GUI_SCALE, 146, 146, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);

            for(isPrime = 0; isPrime < 12; ++isPrime) {
               canSpin = 0.5235987755982988D * (double)isPrime;
               int var14 = (int)(0.0D + 45.0D * Math.cos(canSpin));
               int y = (int)(0.0D + 45.0D * Math.sin(canSpin));
               if(this.targetWheelRotation != 0) {
                  String price = (String)((List)CosmeticGUI.data.get("prizes")).get(isPrime);
                  if(this.isNumeric(price)) {
                     ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                     ModernGui.drawScaledCustomSizeModalRect((float)(var14 - 6), (float)(y - 6), (float)(213 * GUI_SCALE), (float)(173 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                  } else {
                     if(ClientProxy.SKIN_MANAGER.getSkinFromID(price) != null) {
                        ClientProxy.SKIN_MANAGER.getSkinFromID(price).renderInGUI(var14 - 10, y - 10, 0.75F, par3);
                     }

                     GL11.glColor3f(1.0F, 1.0F, 1.0F);
                  }
               } else {
                  ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                  ModernGui.drawScaledCustomSizeModalRect((float)(var14 - 4), (float)(y - 6), (float)(215 * GUI_SCALE), (float)((isPrime % 2 == 0?216:199) * GUI_SCALE), 8 * GUI_SCALE, 13 * GUI_SCALE, 8, 13, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               }
            }

            ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
            GL11.glPopMatrix();
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 202), (float)(this.guiTop + 106), (float)(195 * GUI_SCALE), (float)(125 * GUI_SCALE), 36 * GUI_SCALE, 17 * GUI_SCALE, 36, 17, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if(mouseX >= this.guiLeft + 379 && mouseX <= this.guiLeft + 379 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 379), (float)(this.guiTop + 50), (float)(593 * GUI_SCALE), (float)(132 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               this.hoveredAction = "close_modal";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 379), (float)(this.guiTop + 50), (float)(593 * GUI_SCALE), (float)(119 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            if(mouseX < this.guiLeft + 77 || mouseX > this.guiLeft + 77 + 297 || mouseY < this.guiTop + 37 || mouseY > this.guiTop + 37 + 146) {
               this.hoveredAction = "close_modal";
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.wheel.title"), (float)(this.guiLeft + 243), (float)(this.guiTop + 69), COLOR_WHITE, 1.0F, "left", false, "georamaExtraBold", 28);
            if(this.targetWheelRotation != 0 && this.targetWheelRotation == this.currentWheelRotation) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.prime_wheel.win"), (float)(this.guiLeft + 243), (float)(this.guiTop + 90), COLOR_PINK, 0.5F, "left", false, "georamaBold", 30);
               if(((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).containsKey("skin_name")) {
                  var10 = ((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).containsKey("name_" + System.getProperty("java.lang"))?(String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("name_" + System.getProperty("java.lang")):(String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("skin_name");
                  ModernGui.drawScaledStringCustomFont(var10, (float)(this.guiLeft + 243), (float)(this.guiTop + 100), COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 30);
                  ModernGui.glColorHex(((Integer)CosmeticGUI.rarityColors.get(((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("rarity"))).intValue(), 1.0F);
                  ModernGui.drawRoundedRectangle((float)(this.guiLeft + 246) + semiBold30.getStringWidth(var10) * 0.5F, (float)(this.guiTop + 100), 0.0F, 35.0F, 9.0F);
                  var11 = I18n.func_135053_a("cosmetic.rarity." + (String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("rarity"));
                  ModernGui.drawScaledStringCustomFont(var11, (float)(this.guiLeft + 246 + 17) + semiBold30.getStringWidth(var10) * 0.5F, (float)(this.guiTop + 101), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
               } else {
                  ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("points"), (float)(this.guiLeft + 243), (float)(this.guiTop + 100), COLOR_LIGHT_GRAY, 0.75F, "left", false, "georamaSemiBold", 30);
                  ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 246) + semiBold30.getStringWidth((String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get("points")) * 0.75F, (float)(this.guiTop + 99), (float)(213 * GUI_SCALE), (float)(173 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               }
            } else {
               ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("cosmetic.wheel.description"), (float)(this.guiLeft + 243), (float)(this.guiTop + 90), COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaMedium", 26, 8, 240);
            }

            ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
            if(CosmeticGUI.loaded) {
               boolean var12 = ((Boolean)CosmeticGUI.data.get("ngprime")).booleanValue();
               boolean var13 = CosmeticGUI.data.containsKey("prizes") && Long.parseLong((String)CosmeticGUI.data.get("serverTime")) + (System.currentTimeMillis() - CosmeticGUI.timeOpenGUI.longValue()) - 86400000L > Long.parseLong((String)CosmeticGUI.data.get("lastWheel"));
               boolean isMouseOver = mouseX >= this.guiLeft + 243 && mouseX <= this.guiLeft + 243 + 92 && mouseY >= this.guiTop + 143 && mouseY <= this.guiTop + 143 + 14;
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 243), (float)(this.guiTop + 143), (float)(259 * GUI_SCALE), (float)((isMouseOver && (!var12 || var13)?60:(!var12?20:(!var13?40:0))) * GUI_SCALE), 92 * GUI_SCALE, 14 * GUI_SCALE, 92, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               String var15 = I18n.func_135053_a("cosmetic.wheel.spin");
               if(!var12) {
                  var15 = I18n.func_135053_a("cosmetic.wheel.not_prime");
               } else if(!var13) {
                  var15 = I18n.func_135053_a("cosmetic.wheel.play_cooldown") + " " + formatTime(Long.valueOf(Long.parseLong((String)CosmeticGUI.data.get("lastWheel")) + 86400000L));
               }

               ModernGui.drawScaledStringCustomFont(var15, (float)(this.guiLeft + 243 + 46), (float)(this.guiTop + 143) + 4.0F, isMouseOver && (!var12 || var13)?COLOR_LIGHT_BLUE:(!var12?COLOR_WHITE:(!var13?COLOR_PINK:COLOR_WHITE)), 0.5F, "center", false, "georamaSemiBold", 25);
               if(isMouseOver && var13) {
                  this.hoveredAction = "spin_wheel";
               } else if(isMouseOver && !var12) {
                  this.hoveredAction = "buy_prime";
               }
            }
         }

         GL11.glPopMatrix();
      }

      super.func_73863_a(mouseX, mouseY, par3);
   }

   public boolean isBehindModal(int mouseX, int mouseY) {
      return this.displayModal && mouseX >= this.guiLeft + 94 && mouseX <= this.guiLeft + 94 + 276 && mouseY >= this.guiTop + 61 && mouseY <= this.guiTop + 61 + 111;
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
