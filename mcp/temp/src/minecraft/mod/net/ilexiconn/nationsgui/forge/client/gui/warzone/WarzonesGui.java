package net.ilexiconn.nationsgui.forge.client.gui.warzone;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui$4;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzoneTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WarzonesGui extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static HashMap<String, Object> data = new HashMap();
   public static boolean loaded = false;
   public static boolean loadedRanking = false;
   private RenderItem itemRenderer = new RenderItem();
   public static String displayMode = "bateau";
   public static String rankingMode = "daily";
   public static int dollarsDailyLimit;
   public static int maxPowerboost;
   public static int maxSkillboost;
   private GuiScrollBarGeneric scrollBar;
   public static HashMap<String, String> bateauInfos = new HashMap();
   public static HashMap<String, String> petrolInfos = new HashMap();
   public static HashMap<String, String> mineInfos = new HashMap();
   public static HashMap<String, String> scoreInfos = new HashMap();
   public static HashMap<String, Object> rankingAllInfos = new HashMap();
   public static LinkedHashMap<String, Integer> bgOffsetY = new WarzonesGui$1();
   public static LinkedHashMap<String, Integer> renderOffsetY = new WarzonesGui$2();
   public static HashMap<String, String> warzoneInfos = new HashMap();
   public static LinkedHashMap<String, Integer> rankingPanelOffsetX = new WarzonesGui$3();
   public List<String> WARZONES = Arrays.asList(new String[]{"bateau", "petrol", "mine"});
   public String hoveredAction = "";
   protected int xSize = 463;
   protected int ySize = 235;
   private int guiLeft;
   private int guiTop;


   public WarzonesGui() {
      loaded = false;
      loadedRanking = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzonesDataPacket()));
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzonesLeaderboardDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 448), (float)(this.guiTop + 104), 113, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      ArrayList warzones = new ArrayList(Arrays.asList(new String[]{"bateau", "petrol", "mine"}));
      ArrayList rankingModes = new ArrayList(Arrays.asList(new String[]{"daily", "weekly"}));
      WarzonesGui$4 rewardPanelOffsetX = new WarzonesGui$4(this);
      warzones.remove(displayMode);
      rankingModes.remove(rankingMode);
      ArrayList tooltipToDraw = new ArrayList();
      this.hoveredAction = "";
      String factionName = ClientData.currentFaction;
      String unknownWarzone = displayMode;
      byte ownerFactionName = -1;
      switch(unknownWarzone.hashCode()) {
      case -1396173020:
         if(unknownWarzone.equals("bateau")) {
            ownerFactionName = 0;
         }
         break;
      case -991657904:
         if(unknownWarzone.equals("petrol")) {
            ownerFactionName = 1;
         }
         break;
      case 3351635:
         if(unknownWarzone.equals("mine")) {
            ownerFactionName = 2;
         }
      }

      switch(ownerFactionName) {
      case 0:
         warzoneInfos = bateauInfos;
         break;
      case 1:
         warzoneInfos = petrolInfos;
         break;
      case 2:
         warzoneInfos = mineInfos == null?petrolInfos:mineInfos;
      }

      boolean var25 = warzoneInfos.size() == 0;
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("warzone");
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, 0.0F, (float)(((Integer)bgOffsetY.get(displayMode)).intValue() * GUI_SCALE), 465 * GUI_SCALE, 235 * GUI_SCALE, 465, 235, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      GUIUtils.startGLScissor(this.guiLeft + 14, this.guiTop, 179, 179);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop - 35), (float)(483 * GUI_SCALE), (float)(((Integer)renderOffsetY.get(displayMode)).intValue() * GUI_SCALE), 179 * GUI_SCALE, 179 * GUI_SCALE, 179, 179, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      GUIUtils.endGLScissor();
      if(mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 13), (float)(169 * GUI_SCALE), (float)(747 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         this.hoveredAction = "close";
      }

      ModernGui.drawScaledStringCustomFont("WARZONE", (float)(this.guiLeft + 12), (float)(this.guiTop + 14), 16777215, 1.0F, "left", false, "georamaSemiBold", 30);
      ClientEventHandler.STYLE.bindTexture("warzone");
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.name." + displayMode), (float)(this.guiLeft + 205), (float)(this.guiTop + 49), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
      ClientEventHandler.STYLE.bindTexture("warzone");
      if(loaded) {
         String var26 = !var25 && !((String)warzoneInfos.get("factionName")).equals("Neutre")?((String)warzoneInfos.get("factionName")).replace('&', '\u00a7'):I18n.func_135053_a("warzone.zone.unowned");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 66), 0.0F, (float)(940 * GUI_SCALE), 101 * GUI_SCALE, 20 * GUI_SCALE, 101, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ClientProxy.loadCountryFlag(var26);
         if(mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 67 && mouseY >= this.guiTop + 121 && mouseY <= this.guiTop + 121 + 12) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 121), 0.0F, (float)(765 * GUI_SCALE), 67 * GUI_SCALE, 12 * GUI_SCALE, 67, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.button.tp"), (float)(this.guiLeft + 239), (float)(this.guiTop + 124), 0, 0.5F, "center", false, "georamaSemiBold", 25);
            this.hoveredAction = "teleport";
         } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.button.tp"), (float)(this.guiLeft + 239), (float)(this.guiTop + 124), 16777215, 0.5F, "center", false, "georamaSemiBold", 25);
         }

         ClientEventHandler.STYLE.bindTexture("warzone");
         if(var26.equals(I18n.func_135053_a("warzone.zone.unowned"))) {
            Gui.func_73734_a(this.guiLeft + 210, this.guiTop + 70, this.guiLeft + 210 + 18, this.guiTop + 70 + 12, -1);
         } else if(ClientProxy.flagsTexture.containsKey(var26)) {
            GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(var26)).func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 210), (float)(this.guiTop + 70), 0.0F, 0.0F, 156, 78, 18, 12, 156.0F, 78.0F, false);
         }

         ClientEventHandler.STYLE.bindTexture("warzone");
         ModernGui.drawScaledStringCustomFont(var26.equals(I18n.func_135053_a("warzone.zone.unowned"))?var26:this.factionNameShortener(var26), (float)(this.guiLeft + 233), (float)(this.guiTop + 69), 16777215, 0.5F, "left", false, "georamaMedium", 30);
         long lastTimeOwned = var25?0L:Long.parseLong((String)warzoneInfos.get("lastTimeOwned"));
         ModernGui.drawScaledStringCustomFont((warzoneInfos.containsKey("percent")?(String)warzoneInfos.get("percent"):"0") + "% - " + I18n.func_135053_a("warzone.age.msg") + " " + ModernGui.formatDelayTime(Long.valueOf(lastTimeOwned)), (float)(this.guiLeft + 233), (float)(this.guiTop + 77), 10395075, 0.5F, "left", false, "georamaMedium", 24);
         ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("warzone.desc." + displayMode), (float)(this.guiLeft + 205), (float)(this.guiTop + 88), 10395075, 0.5F, "left", false, "georamaMedium", 25, 7, 200);

         for(int daily = 0; daily < warzones.size(); ++daily) {
            int weekly = daily * 160;
            HashMap rankingInfos = ((String)warzones.get(daily)).equals("bateau")?bateauInfos:(((String)warzones.get(daily)).equals("petrol")?petrolInfos:mineInfos);
            String position = rankingInfos.containsKey("percent")?(String)rankingInfos.get("percent"):"0";
            String rank = I18n.func_135053_a("warzone.zone.unowned");
            long it = rankingInfos.get("lastTimeOwned") == null?System.currentTimeMillis():Long.parseLong((String)rankingInfos.get("lastTimeOwned"));
            if(rankingInfos.get("factionName") != null) {
               rank = ((String)rankingInfos.get("factionName")).equals("Neutre")?I18n.func_135053_a("warzone.zone.unowned"):((String)rankingInfos.get("factionName")).replace('&', '\u00a7');
            }

            ClientEventHandler.STYLE.bindTexture("warzone");
            if(mouseX >= this.guiLeft + 14 + weekly && mouseX <= this.guiLeft + 14 + weekly + 158 && mouseY >= this.guiTop + 153 && mouseY <= this.guiTop + 153 + 68) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 14 + weekly), (float)(this.guiTop + 153), 0.0F, (float)(786 * GUI_SCALE), 158 * GUI_SCALE, 68 * GUI_SCALE, 158, 68, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(rank.equals(I18n.func_135053_a("warzone.zone.unowned"))?I18n.func_135053_a("warzone.zone.unowned"):this.factionNameShortener(rank), (float)(this.guiLeft + 50 + weekly), (float)(this.guiTop + 199), 0, 0.5F, "left", false, "georamaMedium", 27);
               ModernGui.drawScaledStringCustomFont(position + "% - " + I18n.func_135053_a("warzone.age.msg") + " " + ModernGui.formatDelayTime(Long.valueOf(it)), (float)(this.guiLeft + 50 + weekly), (float)(this.guiTop + 207), 0, 0.5F, "left", false, "georamaMedium", 23);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.name." + (String)warzones.get(daily)), (float)(this.guiLeft + 19 + weekly), (float)(this.guiTop + 160), 0, 0.5F, "left", false, "georamaSemiBold", 30);
               ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("warzone.desc." + (String)warzones.get(daily)), (float)(this.guiLeft + 19 + weekly), (float)(this.guiTop + 171), 0, 0.5F, "left", false, "georamaSemiBold", 20, 6, 200);
               this.hoveredAction = (String)warzones.get(daily);
            } else {
               ModernGui.drawScaledStringCustomFont(rank.equals(I18n.func_135053_a("warzone.zone.unowned"))?I18n.func_135053_a("warzone.zone.unowned"):this.factionNameShortener(rank), (float)(this.guiLeft + 50 + weekly), (float)(this.guiTop + 199), 16777215, 0.5F, "left", false, "georamaMedium", 27);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.name." + (String)warzones.get(daily)), (float)(this.guiLeft + 19 + weekly), (float)(this.guiTop + 160), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
               ModernGui.drawScaledStringCustomFont(position + "% - " + I18n.func_135053_a("warzone.age.msg") + " " + ModernGui.formatDelayTime(Long.valueOf(it)), (float)(this.guiLeft + 50 + weekly), (float)(this.guiTop + 207), 10395075, 0.5F, "left", false, "georamaMedium", 23);
               ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("warzone.desc." + (String)warzones.get(daily)), (float)(this.guiLeft + 19 + weekly), (float)(this.guiTop + 171), 10395075, 0.5F, "left", false, "georamaSemiBold", 20, 6, 200);
            }

            if(rank.equals(I18n.func_135053_a("warzone.zone.unowned"))) {
               Gui.func_73734_a(this.guiLeft + 23 + weekly, this.guiTop + 199, this.guiLeft + 23 + weekly + 23, this.guiTop + 199 + 14, -1);
            } else {
               ClientProxy.loadCountryFlag(rank);
               if(ClientProxy.flagsTexture.containsKey(rank)) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(rank)).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 23 + weekly), (float)(this.guiTop + 199), 0.0F, 0.0F, 156, 78, 23, 14, 156.0F, 78.0F, false);
               }

               ClientEventHandler.STYLE.bindTexture("warzone");
            }
         }

         if(loadedRanking) {
            GUIUtils.startGLScissor(this.guiLeft + 340, this.guiTop + 106, 112, 112);
            ArrayList var27 = (ArrayList)rankingAllInfos.get("daily");
            ArrayList var28 = (ArrayList)rankingAllInfos.get("weekly");
            ArrayList var29 = rankingMode.equals("daily")?var27:var28;
            int var30 = 1;

            String tooltip;
            for(int var32 = 0; var32 < var29.size(); ++var32) {
               int var31 = this.guiLeft + 340;
               Float pair = Float.valueOf((float)(this.guiTop + 108 + var32 * 11) + this.getSlide(var29));
               ArrayList wzName = new ArrayList(Arrays.asList(((String)var29.get(var32)).split("##")));
               String offsetX = (String)wzName.get(0);
               tooltip = (String)wzName.get(1);
               ModernGui.drawScaledStringCustomFont(String.valueOf(var30), (float)(var31 + 5), (float)pair.intValue(), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
               ClientProxy.loadCountryFlag(offsetX);
               if(ClientProxy.flagsTexture.containsKey(offsetX)) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(offsetX)).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(var31 + 12), (float)pair.intValue(), 0.0F, 0.0F, 156, 78, 13, 8, 156.0F, 78.0F, false);
               }

               ModernGui.drawScaledStringCustomFont(this.factionNameShortener(offsetX), (float)(var31 + 28), (float)pair.intValue(), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
               ModernGui.drawScaledStringCustomFont(tooltip, (float)(var31 + 103), (float)pair.intValue(), 10395075, 0.5F, "right", false, "georamaMedium", 30);
               double scoreWidth = (double)ModernGui.getCustomFont("georamaMedium", Integer.valueOf(30)).getStringWidth(tooltip) * 0.5D;
               if((double)mouseX >= (double)(var31 + 103) - scoreWidth && mouseX <= var31 + 103 && mouseY >= pair.intValue() && mouseY <= pair.intValue() + 10) {
                  tooltipToDraw.add("\u00a77" + I18n.func_135053_a("warzone.bateau") + ": \u00a7r" + (String)wzName.get(2));
                  tooltipToDraw.add("\u00a77" + I18n.func_135053_a("warzone.petrol") + ": \u00a7r" + (String)wzName.get(3));
                  tooltipToDraw.add("\u00a77" + I18n.func_135053_a("warzone.mine") + ": \u00a7r" + (String)wzName.get(4));
               }

               ++var30;
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
            ClientProxy.loadCountryFlag(factionName);
            if(ClientProxy.flagsTexture.containsKey(factionName)) {
               GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(factionName)).func_110552_b());
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 340), (float)(this.guiTop + 45), 0.0F, 0.0F, 156, 78, 30, 19, 156.0F, 78.0F, false);
            }

            ModernGui.drawScaledStringCustomFont(this.factionNameShortener(factionName), (float)(this.guiLeft + 375), (float)(this.guiTop + 45), 16777215, 0.75F, "left", false, "georamaSemiBold", 22);
            Double var34 = rankingMode.equals("daily")?(Double)rankingAllInfos.get("dailyCountryPosition"):(Double)rankingAllInfos.get("weeklyCountryPosition");
            if(var34.doubleValue() != 0.0D) {
               ModernGui.drawScaledStringCustomFont(var34.intValue() + (var34.doubleValue() == 1.0D?I18n.func_135053_a("warzone.ranking.msg_first"):I18n.func_135053_a("warzone.ranking.msg_general")), (float)(this.guiLeft + 375), (float)(this.guiTop + 55), 10395075, 0.5F, "left", false, "georamaSemiBold", 26);
            } else {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.msg_unranked"), (float)(this.guiLeft + 375), (float)(this.guiTop + 55), 10395075, 0.5F, "left", false, "georamaSemiBold", 26);
            }

            ClientEventHandler.STYLE.bindTexture("warzone");
            Iterator var33 = rewardPanelOffsetX.entrySet().iterator();

            while(var33.hasNext()) {
               Entry var35 = (Entry)var33.next();
               String var36 = (String)var35.getKey();
               int var37 = ((Integer)var35.getValue()).intValue();
               if(var36.equals(displayMode)) {
                  ClientEventHandler.STYLE.bindTexture("warzone");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var37), (float)(this.guiTop + 67), (float)(126 * GUI_SCALE), (float)(748 * GUI_SCALE), 35 * GUI_SCALE, 8 * GUI_SCALE, 35, 8, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }

               ModernGui.drawScaledStringCustomFont(scoreInfos == null?(var36.equals("bateau")?"0$":(var36.equals("petrol")?"0 POWER":"0% SKILL")):(var36.equals("bateau")?(String)scoreInfos.get("bateau") + " $$$":(var36.equals("petrol")?(String)scoreInfos.get("petrol") + " POWER":(scoreInfos.get("mine") == null?Integer.valueOf(0):(Serializable)scoreInfos.get("mine")) + "% SKILL")), (float)(this.guiLeft + var37 + 18), (float)(this.guiTop + 69), 16777215, 0.5F, "center", false, "georamaSemiBold", 20);
               if(mouseX >= this.guiLeft + var37 && mouseX <= this.guiLeft + var37 + 35 && mouseY >= this.guiTop + 67 && mouseY <= this.guiTop + 67 + 8) {
                  tooltip = "\u00a760/0";
                  if(scoreInfos.get(var36) != null) {
                     tooltip = "\u00a76" + (String)scoreInfos.get(var36) + "/" + (var36.equals("bateau")?dollarsDailyLimit:(var36.equals("petrol")?maxPowerboost:maxSkillboost));
                  }

                  tooltipToDraw.add(tooltip);
               }
            }

            ClientEventHandler.STYLE.bindTexture("warzone");
            if(mouseX >= this.guiLeft + 334 && mouseX <= this.guiLeft + 334 + 59 && mouseY >= this.guiTop + 88 && mouseY <= this.guiTop + 88 + 10) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 334), (float)(this.guiTop + 88), (float)(0 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.daily"), (float)(this.guiLeft + 363), (float)(this.guiTop + 88 + 3), 16777215, 0.5F, "center", false, "georamaSemiBold", 24);
               ClientEventHandler.STYLE.bindTexture("warzone");
               this.hoveredAction = "daily";
            } else if(rankingMode == "daily") {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 334), (float)(this.guiTop + 88), (float)(0 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.daily"), (float)(this.guiLeft + 363), (float)(this.guiTop + 88 + 3), 16777215, 0.5F, "center", false, "georamaSemiBold", 24);
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 334), (float)(this.guiTop + 88), (float)(63 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.daily"), (float)(this.guiLeft + 363), (float)(this.guiTop + 88 + 3), 2499659, 0.5F, "center", false, "georamaSemiBold", 24);
            }

            ClientEventHandler.STYLE.bindTexture("warzone");
            if(mouseX >= this.guiLeft + 393 && mouseX <= this.guiLeft + 393 + 59 && mouseY >= this.guiTop + 88 && mouseY <= this.guiTop + 88 + 10) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 393), (float)(this.guiTop + 88), (float)(0 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.weekly"), (float)(this.guiLeft + 363 + 59), (float)(this.guiTop + 88 + 3), 16777215, 0.5F, "center", false, "georamaSemiBold", 24);
               this.hoveredAction = "weekly";
            } else if(rankingMode == "weekly") {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 393), (float)(this.guiTop + 88), (float)(0 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.weekly"), (float)(this.guiLeft + 363 + 59), (float)(this.guiTop + 88 + 3), 16777215, 0.5F, "center", false, "georamaSemiBold", 24);
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + ((Integer)rankingPanelOffsetX.get(rankingModes.get(0))).intValue()), (float)(this.guiTop + 88), (float)(63 * GUI_SCALE), (float)(746 * GUI_SCALE), 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("warzone.ranking.weekly"), (float)(this.guiLeft + 363 + 59), (float)(this.guiTop + 88 + 3), 2499659, 0.5F, "center", false, "georamaSemiBold", 24);
            }
         }

         if(!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
         }
      }

      super.func_73863_a(mouseX, mouseY, par3);
   }

   public boolean func_73868_f() {
      return false;
   }

   private String factionNameShortener(String factionName) {
      String res = factionName;
      if(factionName.length() > 12) {
         if(factionName.contains("Empire")) {
            res = factionName.replace("Empire", "Emp");
         }

         if(res.length() > 12) {
            res = res.substring(0, 9) + "..";
         }
      }

      return res;
   }

   private float getSlide(ArrayList<String> rankingInfos) {
      return rankingInfos.size() > 10?(float)(-(rankingInfos.size() - 10) * 11) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(this.hoveredAction.equals("petrol")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            displayMode = "petrol";
         } else if(this.hoveredAction.equals("daily")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            rankingMode = "daily";
         } else if(this.hoveredAction.equals("weekly")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            rankingMode = "weekly";
         } else if(this.hoveredAction.equals("mine")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            displayMode = "mine";
         } else if(this.hoveredAction.equals("bateau")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            displayMode = "bateau";
         } else if(this.hoveredAction.equals("teleport") && warzoneInfos != null && warzoneInfos.containsKey("tpLeft") && !((String)warzoneInfos.get("tpLeft")).equals("0")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket(displayMode)));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
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
