package net.ilexiconn.nationsgui.forge.client.gui.main;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Desktop.Action;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$ServerGroup;
import net.ilexiconn.nationsgui.forge.client.gui.GuiBrowser;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernServerButton;
import net.ilexiconn.nationsgui.forge.client.gui.multi.MultiGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MainGUI extends GuiMainMenu {

   private static Gson gson = new Gson();
   public static List<String> serverList = Arrays.asList(new String[]{"blue", "orange", "yellow", "white", "black", "cyan", "lime", "coral", "pink", "purple", "green", "red", "ruby", "mocha"});
   public static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/main/background.png");
   public static final ResourceLocation LOGO_NG = new ResourceLocation("nationsgui", "textures/gui/main/logo_ng.png");
   public static final ResourceLocation LOGO_SOLO = new ResourceLocation("nationsgui", "textures/gui/main/logo_solo.png");
   public static final ResourceLocation LOGO_MULTI = new ResourceLocation("nationsgui", "textures/gui/main/logo_multi.png");
   public static final ResourceLocation LOGO_SITE = new ResourceLocation("nationsgui", "textures/gui/main/logo_site.png");
   public static final ResourceLocation LOGO_DISCORD = new ResourceLocation("nationsgui", "textures/gui/main/logo_discord.png");
   public static final ResourceLocation LOGO_TEAMSPEAK = new ResourceLocation("nationsgui", "textures/gui/main/logo_teamspeak.png");
   public static final ResourceLocation LOGO_SETTINGS = new ResourceLocation("nationsgui", "textures/gui/main/logo_settings.png");
   public static final ResourceLocation LOGO_SOLO_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_solo_hover.png");
   public static final ResourceLocation LOGO_MULTI_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_multi_hover.png");
   public static final ResourceLocation LOGO_SITE_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_site_hover.png");
   public static final ResourceLocation LOGO_DISCORD_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_discord_hover.png");
   public static final ResourceLocation LOGO_TEAMSPEAK_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_teamspeak_hover.png");
   public static final ResourceLocation LOGO_SETTINGS_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_settings_hover.png");
   public static final ResourceLocation LOGO_NGPRIME = new ResourceLocation("nationsgui", "textures/gui/main/logo_ngprime.png");
   public static final ResourceLocation BOX_NEWS = new ResourceLocation("nationsgui", "textures/gui/main/box_news_bg.png");
   public static final ResourceLocation BOX_NEWS_OVER = new ResourceLocation("nationsgui", "textures/gui/main/box_news_over.png");
   public static final ResourceLocation BOX_HUB = new ResourceLocation("nationsgui", "textures/gui/main/box_hub.png");
   public static final ResourceLocation BOX_ISLAND = new ResourceLocation("nationsgui", "textures/gui/main/box_island.png");
   public static final ResourceLocation BOX_MINE = new ResourceLocation("nationsgui", "textures/gui/main/box_mine.png");
   public static final ResourceLocation BOX_ARCADE = new ResourceLocation("nationsgui", "textures/gui/main/box_arcade.png");
   public static final ResourceLocation BOX_STORE = new ResourceLocation("nationsgui", "textures/gui/main/box_store.png");
   public static final ResourceLocation BOX_WHITE = new ResourceLocation("nationsgui", "textures/gui/main/box_white.png");
   public static final ResourceLocation BUTTON_NEWS = new ResourceLocation("nationsgui", "textures/gui/main/button_news.png");
   public static final ResourceLocation BUTTON_BOX = new ResourceLocation("nationsgui", "textures/gui/main/button_box.png");
   public static final ResourceLocation BUTTON_NEWS_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/button_news_hover.png");
   public static final ResourceLocation BUTTON_BOX_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/button_box_hover.png");
   public static final ResourceLocation LANG_SWITCH = new ResourceLocation("nationsgui", "textures/gui/main/lang_switch.png");
   public static final ResourceLocation LANG_SWITCH_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/lang_switch_hover.png");
   public static final HashMap<String, ResourceLocation> SERVERS = new HashMap();
   public static final HashMap<String, ResourceLocation> SERVERS_HOVER = new HashMap();
   public static long ANIMATION_BOX_HUB_DURATION = 100L;
   public static long ANIMATION_BOX_HUB_STARTED = 0L;
   public static String ANIMATION_BOX_HUB_WAY = "down";
   public static long ANIMATION_BOX_MINE_DURATION = 100L;
   public static long ANIMATION_BOX_MINE_STARTED = 0L;
   public static String ANIMATION_BOX_MINE_WAY = "down";
   public static long ANIMATION_BOX_ISLAND_DURATION = 100L;
   public static long ANIMATION_BOX_ISLAND_STARTED = 0L;
   public static String ANIMATION_BOX_ISLAND_WAY = "down";
   public static long ANIMATION_BOX_ARCADE_DURATION = 100L;
   public static long ANIMATION_BOX_ARCADE_STARTED = 0L;
   public static String ANIMATION_BOX_ARCADE_WAY = "down";
   public static float ANIMATION_NEWS_DURATION = 400.0F;
   public static long ANIMATION_NEWS_STARTED = 0L;
   public static String ANIMATION_NEWS_WAY = "down";
   public static String hoveredServer = "";
   public static String hoveredUrl = "";
   public static String hoveredAction = "";
   private Map<Entry<String, ServersData$ServerGroup>, Float> serverGroups = new HashMap();
   private ModernScrollBar modernScrollBar;
   List<ModernServerButton> tempServList = new ArrayList();
   private float decalBase;
   public static List<GuiButton> serversButtonList = new ArrayList();
   public static String serverLang = "fr";
   public static HashMap<String, Object> cachedDataMainMenu = new HashMap();
   public static long lastRefreshDataMainMenu = 0L;
   public static int activeCarouselSlide = 0;
   public static ResourceLocation cacheHeadPlayer = null;
   public static HashMap<String, DynamicTexture> cachedFlagsTexture = new HashMap();


   public MainGUI() {
      Iterator thread = serverList.iterator();

      while(thread.hasNext()) {
         String e = (String)thread.next();
         SERVERS.put(e, new ResourceLocation("nationsgui", "textures/gui/main/servers/" + e + ".png"));
         SERVERS_HOVER.put(e, new ResourceLocation("nationsgui", "textures/gui/main/servers_hover/" + e + ".png"));
      }

      try {
         FileUtils.write(new File("snoopers.json"), (new Gson()).toJson(Minecraft.func_71410_x().func_71378_E().func_76465_c()));
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      ClientEventHandler.modsChecked = false;
      NationsGUIClientHooks.setDone(true);
      MainGUI$1 thread1 = new MainGUI$1(this);
      thread1.start();
      if(System.currentTimeMillis() - lastRefreshDataMainMenu > 600000L) {
         try {
            Session e1 = Minecraft.func_71410_x().func_110432_I();
            HashMap data = (HashMap)gson.fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/mods/menu?accessToken=" + e1.func_111286_b() + "&cuid=" + System.getProperty("java.tweaker") + "&lang=" + serverLang)).openStream(), StandardCharsets.UTF_8), (new MainGUI$2(this)).getType());
            cachedDataMainMenu = data;
            lastRefreshDataMainMenu = System.currentTimeMillis();
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }

   }

   private String clearPopulationString(String string) {
      string = string.split("/")[0];
      StringBuilder stringBuilder = new StringBuilder();
      boolean colorType = false;
      char[] var4 = string.toCharArray();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char c = var4[var6];
         if(c == 167) {
            colorType = true;
         } else if(colorType) {
            colorType = false;
         } else {
            stringBuilder.append(c);
         }
      }

      return stringBuilder.toString();
   }

   public void func_73866_w_() {
      this.field_73887_h.clear();
      int baseY = 88;
      int serverPerLine = (int)(((float)this.field_73880_f - 139.0F - 12.0F - 16.0F) / 86.0F);
      this.serverGroups.clear();
      Iterator size = ClientProxy.serversData.getPermanentServers().entrySet().iterator();

      Entry entry;
      while(size.hasNext()) {
         entry = (Entry)size.next();
         boolean server = false;
         Iterator i = ((ServersData$ServerGroup)entry.getValue()).getServerMap().values().iterator();
         if(i.hasNext()) {
            ServersData$Server server1 = (ServersData$Server)i.next();
            this.serverGroups.put(entry, Float.valueOf((float)baseY - 4.5F - 8.0F));
            server = true;
         }

         int var12 = 0;

         for(Iterator var13 = ((ServersData$ServerGroup)entry.getValue()).getServerMap().entrySet().iterator(); var13.hasNext(); ++var12) {
            Entry entry1 = (Entry)var13.next();
            if(var12 == serverPerLine) {
               var12 = 0;
               baseY += 18;
            }

            ServersData$Server server2 = (ServersData$Server)entry1.getValue();
            this.field_73887_h.add(new ModernServerButton(6, 147 + var12 * 88, baseY, 84, 14, server2, (String)entry1.getKey(), "permanent"));
         }

         if(server) {
            baseY += 36;
         }
      }

      serversButtonList = this.field_73887_h;
      baseY = 136;
      this.tempServList.clear();
      size = ClientProxy.serversData.getTemporaryServers().entrySet().iterator();

      while(size.hasNext()) {
         entry = (Entry)size.next();
         ServersData$Server var11 = (ServersData$Server)entry.getValue();
         if(var11.isVisible(this.field_73882_e)) {
            this.tempServList.add(new ModernServerButton(6, 20, baseY, 107, 14, var11, (String)entry.getKey(), "temporary"));
            baseY += 18;
         }
      }

      float var10 = (float)(this.field_73881_g - 112 - 58 - 24 - 8);
      this.decalBase = (float)(baseY - 136) - var10;
      if((float)(baseY - 136) > var10) {
         if(this.modernScrollBar != null) {
            this.modernScrollBar.setHeight((int)var10);
         } else {
            this.modernScrollBar = new ModernScrollBar(129.0F, 136.0F, 3, this.field_73881_g - 112 - 58 - 24 - 8, 3);
         }
      } else if(this.modernScrollBar != null) {
         this.modernScrollBar = null;
      }

   }

   protected void func_73875_a(GuiButton button) {}

   public static void drawWaitingQueueOverlay(int mouseX, int mouseY) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      byte positionX = 50;
      byte positionY = 50;
      if(ClientData.waitingServerName != null && !ClientData.waitingServerName.isEmpty() && !ClientData.waitingServerName.equalsIgnoreCase("null")) {
         ClientEventHandler.STYLE.bindTexture("overlay_hud");
         ModernGui.drawScaledCustomSizeModalRect((float)positionX, (float)positionY, 813.0F, 0.0F, 216, 114, 432, 228, 1920.0F, 1033.0F, false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.server") + " " + ClientData.waitingServerName.toUpperCase(), (float)(positionX + 30), (float)(positionY + 25), 7239406, 2.5F, "left", false, "minecraftDungeons", 24);
         ModernGui.drawScaledStringCustomFont("\u00a7o" + (ClientData.waitingPriority?I18n.func_135053_a("waiting.list.priority"):I18n.func_135053_a("waiting.list.classic")), (float)(positionX + 30), (float)(positionY + 80), 14277081, 2.0F, "left", false, "georamaSemiBold", 24);
         long diffTime = System.currentTimeMillis() - ClientData.waitingJoinTime.longValue();
         diffTime = diffTime / 1000L / 60L;
         ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / \u00a77" + ClientData.waitingTotal, (float)(positionX + 30), (float)(positionY + 120), 16777215, 2.0F, "left", false, "georamaSemiBold", 30);
         ModernGui.drawScaledStringCustomFont("\u00a7o" + diffTime + " minutes", (float)(positionX + 30), (float)(positionY + 160), 14277081, 2.0F, "left", false, "georamaRegular", 20);
         boolean hoveringQuitBtn = mouseX >= positionX && mouseX <= positionX + 198 && mouseY >= positionY + 240 && mouseY <= positionY + 240 + 48;
         ClientEventHandler.STYLE.bindTexture("overlay_hud");
         ModernGui.drawScaledCustomSizeModalRect((float)positionX, (float)(positionY + 240), (float)(hoveringQuitBtn?1345:1241), 88.0F, 99, 24, 198, 48, 1920.0F, 1033.0F, false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("waiting.quit"), (float)(positionX + 105), (float)(positionY + 253), 16777215, 2.0F, "center", false, "georamaSemiBold", 24);
         if(hoveringQuitBtn) {
            hoveredAction = "quit_waiting";
         }

      }
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      hoveredServer = "";
      hoveredUrl = "";
      hoveredAction = "";
      GL11.glDisable(2884);
      GL11.glPushMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int windowWidth = this.field_73880_f;
      int windowHeight = this.field_73880_f * 9 / 16;
      int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      int screenHeight = screenWidth * 9 / 16;
      int mouseXScaled = (int)((float)mouseX * (3840.0F / (float)this.field_73880_f));
      int mouseYScaled = (int)((float)mouseY * (2160.0F / (float)windowHeight));
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0F, 2160.0F, false);
      GL11.glScaled((double)((float)this.field_73880_f / 3840.0F), (double)((float)windowHeight / 2160.0F), 1.0D);
      this.field_73882_e.func_110434_K().func_110577_a(LOGO_NG);
      ModernGui.drawModalRectWithCustomSizedTexture(24.0F, 34.0F, 0, 0, 148, 148, 148.0F, 148.0F, true);
      this.field_73882_e.func_110434_K().func_110577_a(MultiGUI.LOGO_HOME_HOVER);
      ModernGui.drawModalRectWithCustomSizedTexture(72.0F, 600.0F, 0, 0, 60, 52, 60.0F, 52.0F, true);
      if(mouseXScaled >= 60 && mouseXScaled <= 140 && mouseYScaled >= 720 && mouseYScaled <= 776) {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_MULTI_HOVER);
      } else {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_MULTI);
      }

      ModernGui.drawModalRectWithCustomSizedTexture(60.0F, 720.0F, 0, 0, 80, 56, 80.0F, 56.0F, true);
      if(Minecraft.func_71410_x().field_71439_g != null && (Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("blakonne") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("ibalix"))) {
         if(mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SOLO_HOVER);
         } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SOLO);
         }

         ModernGui.drawModalRectWithCustomSizedTexture(74.0F, 840.0F, 0, 0, 52, 52, 52.0F, 52.0F, true);
      }

      if(mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1280 && mouseYScaled <= 1344) {
         ClientProxy.loadResource("textures/gui/main/logo_wiki_hover.png");
      } else {
         ClientProxy.loadResource("textures/gui/main/logo_wiki.png");
      }

      ModernGui.drawModalRectWithCustomSizedTexture(66.0F, 1280.0F, 0, 0, 64, 64, 64.0F, 64.0F, true);
      if(mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1400 && mouseYScaled <= 1464) {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_SITE_HOVER);
      } else {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_SITE);
      }

      ModernGui.drawModalRectWithCustomSizedTexture(66.0F, 1400.0F, 0, 0, 64, 64, 64.0F, 64.0F, true);
      if(mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1520 && mouseYScaled <= 1586) {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_DISCORD_HOVER);
      } else {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_DISCORD);
      }

      ModernGui.drawModalRectWithCustomSizedTexture(70.0F, 1520.0F, 0, 0, 58, 66, 58.0F, 66.0F, false);
      if(mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1640 && mouseYScaled <= 1698) {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_TEAMSPEAK_HOVER);
      } else {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_TEAMSPEAK);
      }

      ModernGui.drawModalRectWithCustomSizedTexture(70.0F, 1640.0F, 0, 0, 58, 58, 58.0F, 58.0F, false);
      if(mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1760 && mouseYScaled <= 1820) {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_SETTINGS_HOVER);
      } else {
         this.field_73882_e.func_110434_K().func_110577_a(LOGO_SETTINGS);
      }

      ModernGui.drawModalRectWithCustomSizedTexture(70.0F, 1760.0F, 0, 0, 60, 60, 60.0F, 60.0F, false);
      String offlineClient = System.getProperty("ng.offline");
      if(offlineClient != null && offlineClient.equals("true")) {
         ModernGui.drawScaledStringCustomFont("DEV", 100.0F, 1880.0F, 1316402, 3.0F, "center", false, "georamaSemiBold", 28);
      }

      int countryName;
      int index;
      int var23;
      if(cachedDataMainMenu != null) {
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.news"), 347.0F, 180.0F, 12369613, 2.0F, "left", false, "georamaMedium", 37);
         GUIUtils.startGLScissor((int)(347.0F * ((float)this.field_73880_f / 3840.0F)), (int)(52.0F * ((float)windowHeight / 2160.0F)), (int)(2298.0F * ((float)this.field_73880_f / 3840.0F)), (int)(728.0F * ((float)windowHeight / 2160.0F)));
         if(cachedDataMainMenu.get("news") != null) {
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347, 52, 0, 0, 2298, 728, 2298, 728, 2298.0F, 728.0F, false, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("background"));
            if(!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("hover")).isEmpty()) {
               if(mouseXScaled >= 347 && mouseXScaled <= 2645 && mouseYScaled >= 52 && mouseYScaled <= 780) {
                  if(!ANIMATION_NEWS_WAY.equals("up")) {
                     ANIMATION_NEWS_WAY = "up";
                     ANIMATION_NEWS_STARTED = System.currentTimeMillis();
                  }

                  if((float)(System.currentTimeMillis() - ANIMATION_NEWS_STARTED) >= ANIMATION_NEWS_DURATION) {
                     ANIMATION_NEWS_STARTED = -1L;
                  }

                  if(ANIMATION_NEWS_STARTED != -1L) {
                     this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                     ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347 + (int)((float)(40L * (System.currentTimeMillis() - ANIMATION_NEWS_STARTED)) / ANIMATION_NEWS_DURATION), 52, 0, 0, 2298, 728, 2298, 728, 2298.0F, 728.0F, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("hover"));
                  } else {
                     this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                     ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(387, 52, 0, 0, 2298, 728, 2298, 728, 2298.0F, 728.0F, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("hover"));
                  }
               } else {
                  if(!ANIMATION_NEWS_WAY.equals("down")) {
                     ANIMATION_NEWS_WAY = "down";
                     ANIMATION_NEWS_STARTED = System.currentTimeMillis();
                  }

                  if((float)(System.currentTimeMillis() - ANIMATION_NEWS_STARTED) >= ANIMATION_NEWS_DURATION) {
                     ANIMATION_NEWS_STARTED = -1L;
                  }

                  if(ANIMATION_NEWS_STARTED != -1L) {
                     this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                     ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(387 - (int)((float)(40L * (System.currentTimeMillis() - ANIMATION_NEWS_STARTED)) / ANIMATION_NEWS_DURATION), 52, 0, 0, 2298, 728, 2298, 728, 2298.0F, 728.0F, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("hover"));
                  } else {
                     this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                     ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347, 52, 0, 0, 2298, 728, 2298, 728, 2298.0F, 728.0F, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("hover"));
                  }
               }
            }

            if(!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("text_button")).isEmpty()) {
               if(mouseXScaled >= 398 && mouseXScaled <= 720 && mouseYScaled >= 628 && mouseYScaled <= 712) {
                  this.field_73882_e.func_110434_K().func_110577_a(BUTTON_NEWS_HOVER);
                  ModernGui.drawModalRectWithCustomSizedTexture(398.0F, 628.0F, 0, 0, 322, 84, 322.0F, 84.0F, true);
                  ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("text_button"), 560.0F, 648.0F, 16777215, 3.0F, "center", false, "georamaSemiBold", 28);
                  String offsetX = (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("link_button");
                  if(offsetX.contains("#")) {
                     hoveredServer = offsetX;
                  } else {
                     hoveredUrl = offsetX;
                  }
               } else {
                  this.field_73882_e.func_110434_K().func_110577_a(BUTTON_NEWS);
                  ModernGui.drawModalRectWithCustomSizedTexture(398.0F, 628.0F, 0, 0, 322, 84, 322.0F, 84.0F, true);
                  ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("text_button"), 560.0F, 648.0F, 1316402, 3.0F, "center", false, "georamaSemiBold", 28);
               }
            }

            var23 = 0;
            String[] server = ((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("text")).split("\\\\n");
            countryName = server.length;

            for(index = 0; index < countryName; ++index) {
               String posX = server[index];
               ModernGui.drawScaledStringCustomFont(posX, 402.0F, (float)(351 + 40 * var23 + (var23 != 0?50:0)), 15659004, var23 == 0?4.0F:2.0F, "left", false, var23 == 0?"georamaBold":"georamaRegular", 33);
               ++var23;
            }
         }

         GUIUtils.endGLScissor();
      }

      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.shortcuts"), 346.0F, 860.0F, 12369613, 2.0F, "left", false, "georamaMedium", 37);
      short var24 = 346;
      if(mouseXScaled >= var24 && mouseXScaled <= var24 + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
         this.field_73882_e.func_110434_K().func_110577_a(BOX_HUB);
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
         if(serverLang.equals("fr")) {
            hoveredServer = "annexe#hub#hub.nationsglory.fr#25578";
         }
      } else {
         ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
      }

      if(serverLang.equals("fr")) {
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.hub.title"), (float)(var24 + 60), 1020.0F, 16777215, 2.0F, "left", false, "georamaSemiBold", 50);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.hub.subtitle1"), (float)(var24 + 60), 1100.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.hub.subtitle2"), (float)(var24 + 60), 1130.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
      }

      ClientProxy.loadResource("textures/gui/main/arrow_right.png");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(var24 + 544), 1039.0F, 0, 0, 84, 84, 84.0F, 84.0F, true);
      var24 = 1138;
      if(mouseXScaled >= var24 && mouseXScaled <= var24 + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
         this.field_73882_e.func_110434_K().func_110577_a(BOX_MINE);
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
         if(serverLang.equals("fr")) {
            hoveredServer = "annexe#accueil#event.nationsglory.fr#25579";
         }
      } else {
         ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
      }

      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.tutorial.title"), (float)(var24 + 60), 1020.0F, 16777215, 2.0F, "left", false, "georamaSemiBold", 50);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.tutorial.subtitle1"), (float)(var24 + 60), 1100.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.tutorial.subtitle2"), (float)(var24 + 60), 1130.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
      ClientProxy.loadResource("textures/gui/main/arrow_right.png");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(var24 + 544), 1039.0F, 0, 0, 84, 84, 84.0F, 84.0F, true);
      var24 = 1930;
      if(mouseXScaled >= var24 && mouseXScaled <= var24 + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
         this.field_73882_e.func_110434_K().func_110577_a(BOX_ISLAND);
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
         if(serverLang.equals("fr")) {
            hoveredServer = "annexe#freebuild1#event.nationsglory.fr#25580";
         }
      } else {
         ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
         ModernGui.drawModalRectWithCustomSizedTexture((float)var24, 911.0F, 0, 0, 714, 340, 714.0F, 340.0F, true);
      }

      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.island.title"), (float)(var24 + 60), 1020.0F, 16777215, 2.0F, "left", false, "georamaSemiBold", 50);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.island.subtitle1"), (float)(var24 + 60), 1100.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.island.subtitle2"), (float)(var24 + 60), 1130.0F, 16777215, 2.0F, "left", false, "georamaRegular", 30);
      ClientProxy.loadResource("textures/gui/main/arrow_right.png");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(var24 + 544), 1039.0F, 0, 0, 84, 84, 84.0F, 84.0F, true);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.to_discover"), 347.0F, 1324.0F, 12369613, 2.0F, "left", false, "georamaMedium", 37);
      if(cachedDataMainMenu != null) {
         ArrayList var25 = (ArrayList)cachedDataMainMenu.get("store");
         if(var25 != null && var25.size() > 0) {
            if(var25.size() <= activeCarouselSlide) {
               activeCarouselSlide = 0;
            }

            ClientProxy.loadResource("textures/gui/main/carousel_background.png");
            ModernGui.drawModalRectWithCustomSizedTexture(345.0F, 1374.0F, 0, 0, 1508, 622, 1508.0F, 622.0F, false);
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(487, 1459, 0, 0, 500, 450, 500, 450, 500.0F, 450.0F, true, (String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get("image"));
            countryName = 0;
            String[] var28 = ((String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get("text")).split("\\\\n");
            int var30 = var28.length;

            for(int button = 0; button < var30; ++button) {
               String offset = var28[button];
               ModernGui.drawScaledStringCustomFont(offset, 1167.0F, (float)(1563 + 40 * countryName + (countryName > 0?30:0)), 16777215, countryName == 0?3.0F:2.0F, "left", false, countryName == 0?"georamaBold":"georamaRegular", 33);
               ++countryName;
            }

            countryName += 2;
            if(mouseXScaled >= 500 && mouseXScaled <= 1720 && mouseYScaled >= 1412 && mouseYScaled <= 1912) {
               ClientProxy.loadResource("textures/gui/main/button_shop_hover.png");
               ModernGui.drawModalRectWithCustomSizedTexture(1158.0F, (float)(1563 + countryName * 40), 0, 0, 242, 78, 242.0F, 78.0F, true);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.discover"), 1279.0F, (float)(1563 + countryName * 40 + 22), 0, 2.0F, "center", false, "georamaBold", 33);
               hoveredUrl = (String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get("link");
            } else {
               ClientProxy.loadResource("textures/gui/main/button_shop.png");
               ModernGui.drawModalRectWithCustomSizedTexture(1158.0F, (float)(1563 + countryName * 40), 0, 0, 242, 78, 242.0F, 78.0F, true);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.discover"), 1282.0F, (float)(1563 + countryName * 40 + 23), 16777215, 2.0F, "center", false, "georamaBold", 33);
            }

            if(var25.size() > 1) {
               if(mouseXScaled >= 356 && mouseXScaled <= 486 && mouseYScaled >= 1534 && mouseYScaled <= 1854) {
                  ClientProxy.loadResource("textures/gui/main/carousel_left_hover.png");
                  hoveredAction = "carousel_scroll#down";
               } else {
                  ClientProxy.loadResource("textures/gui/main/carousel_left.png");
               }

               ModernGui.drawModalRectWithCustomSizedTexture(400.0F, 1649.0F, 0, 0, 41, 74, 41.0F, 74.0F, true);
               if(mouseXScaled >= 1724 && mouseXScaled <= 1854 && mouseYScaled >= 1534 && mouseYScaled <= 1854) {
                  ClientProxy.loadResource("textures/gui/main/carousel_right_hover.png");
                  hoveredAction = "carousel_scroll#up";
               } else {
                  ClientProxy.loadResource("textures/gui/main/carousel_right.png");
               }

               ModernGui.drawModalRectWithCustomSizedTexture(1753.0F, 1649.0F, 0, 0, 41, 74, 41.0F, 74.0F, true);

               for(index = 0; index < var25.size(); ++index) {
                  if(activeCarouselSlide == index) {
                     ClientProxy.loadResource("textures/gui/main/carousel_dot_active.png");
                  } else {
                     ClientProxy.loadResource("textures/gui/main/carousel_dot_inactive.png");
                  }

                  var30 = 1096 - (var25.size() > 1?25 * (var25.size() - 1):0);
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(var30 + index * 50 - 14), 1934.0F, 0, 0, 28, 28, 28.0F, 28.0F, true);
                  if(mouseXScaled >= var30 + index * 50 - 14 && mouseXScaled <= var30 + index * 50 - 14 + 28 && mouseYScaled >= 1934 && mouseYScaled <= 1962) {
                     hoveredAction = "carousel_change#" + index;
                  }
               }
            }
         }
      }

      String var26 = "";
      if(cachedDataMainMenu != null) {
         var26 = cachedDataMainMenu.get("userbox") != null && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("last_server") != null?(String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("last_server"):"none";
         ClientProxy.loadResource("textures/gui/main/servers_card/" + var26 + ".png");
         ModernGui.drawModalRectWithCustomSizedTexture(1930.0F, 1374.0F, 0, 0, 714, 400, 714.0F, 400.0F, true);
         if(cacheHeadPlayer == null) {
            try {
               ResourceLocation var27 = AbstractClientPlayer.field_110314_b;
               var27 = AbstractClientPlayer.func_110311_f(Minecraft.func_71410_x().func_110432_I().func_111285_a());
               AbstractClientPlayer.func_110304_a(var27, Minecraft.func_71410_x().func_110432_I().func_111285_a());
               cacheHeadPlayer = var27;
            } catch (Exception var22) {
               System.out.println(var22.getMessage());
            }
         } else {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer);
            this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer);
            GUIUtils.drawScaledCustomSizeModalRect(2070, 1516, 8.0F, 16.0F, 8, -8, -100, -100, 64.0F, 64.0F);
         }

         ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().func_110432_I().func_111285_a().toUpperCase(), 2100.0F, 1423.0F, !var26.equals("white")?16777215:2368583, 3.0F, "left", false, "georamaBold", 33);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.server").replace("#server#", !var26.equals("none")?var26.substring(0, 1).toUpperCase() + var26.substring(1):"-"), 2100.0F, 1478.0F, 2368583, 2.0F, "left", false, "georamaMedium", 33);
      }

      if(cachedDataMainMenu != null && cachedDataMainMenu.get("userbox") != null && !cachedFlagsTexture.containsKey(var26) && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("country") != null && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("flag") != null) {
         BufferedImage var29 = ModernGui.decodeToImage((String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("flag"));
         cachedFlagsTexture.put(var26, new DynamicTexture(var29));
      }

      if(cachedFlagsTexture.containsKey(var26)) {
         GL11.glBindTexture(3553, ((DynamicTexture)cachedFlagsTexture.get(var26)).func_110552_b());
         ModernGui.drawScaledCustomSizeModalRect(1998.0F, 1582.0F, 0.0F, 0.0F, 156, 78, 221, 130, 156.0F, 78.0F, false);
      } else {
         Gui.func_73734_a(1998, 1582, 2219, 1712, 1209604377);
      }

      String var31 = ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("country") != null && !((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("country").equals("")?(String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("country"):I18n.func_135053_a("main.no_country");
      if(var31.length() > 13) {
         var31 = var31.substring(0, 12) + "..";
      }

      ModernGui.drawScaledStringCustomFont(var31, 2250.0F, 1600.0F, !var26.equals("white")?16777215:2368583, 3.0F, "left", false, "georamaBold", 33);
      if(((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("money") != null) {
         ModernGui.drawScaledStringCustomFont(((Double)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get("money")).intValue() + "$", 2250.0F, 1657.0F, !var26.equals("white")?16777215:2368583, 2.0F, "left", false, "georamaSemiBold", 33);
      }

      if(mouseXScaled >= 1930 && mouseXScaled <= 2644 && mouseYScaled >= 1820 && mouseYScaled <= 1996) {
         ClientProxy.loadResource("textures/gui/main/box_patchnote_hover.png");
         hoveredUrl = "https://nationsglory.fr/changelogs";
      } else {
         ClientProxy.loadResource("textures/gui/main/box_patchnote.png");
      }

      ModernGui.drawModalRectWithCustomSizedTexture(1930.0F, 1820.0F, 0, 0, 714, 176, 714.0F, 176.0F, true);
      ClientProxy.loadResource("textures/gui/main/logo_patchnote.png");
      ModernGui.drawModalRectWithCustomSizedTexture(1972.0F, 1879.0F, 0, 0, 94, 63, 94.0F, 63.0F, true);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.patchnotes"), 2100.0F, 1890.0F, 16777215, 3.0F, "left", false, "georamaBold", 33);
      ClientProxy.loadResource("textures/gui/main/arrow_right.png");
      ModernGui.drawModalRectWithCustomSizedTexture(2500.0F, 1866.0F, 0, 0, 84, 84, 84.0F, 84.0F, true);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("main.servers") + " - " + serverLang.toUpperCase(), 2900.0F, 120.0F, 16777215, 2.0F, "left", false, "georamaMedium", 37);
      index = 0;
      Iterator var32 = this.field_73887_h.iterator();

      while(var32.hasNext()) {
         GuiButton var33 = (GuiButton)var32.next();
         int var34 = 170 * (index % 7);
         var23 = index / 7 * 450;
         String serverName = ((ModernServerButton)var33).getServerName();
         String serverType = ((ModernServerButton)var33).getServerType();
         String serverIp = ((ModernServerButton)var33).getIp();
         String serverPort = ((ModernServerButton)var33).getPort() + "";
         if(((ModernServerButton)var33).server.getZones().contains(serverLang) || serverLang.contains("fr")) {
            if(SERVERS.containsKey(serverName)) {
               if(mouseXScaled >= 2900 + var23 && mouseXScaled <= 2900 + var23 + 116 && mouseYScaled >= 230 + var34 && mouseYScaled <= 230 + var34 + 118) {
                  this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)SERVERS_HOVER.get(serverName));
                  hoveredServer = serverType + "#" + serverName + "#" + serverIp + "#" + serverPort;
               } else {
                  this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)SERVERS.get(serverName));
               }

               ModernGui.drawModalRectWithCustomSizedTexture((float)(2900 + var23), (float)(230 + var34), 0, 0, 116, 118, 116.0F, 118.0F, false);
               ModernGui.drawScaledStringCustomFont(serverName.substring(0, 1).toUpperCase() + serverName.substring(1), (float)(3034 + var23), (float)(260 + var34), 16777215, 2.0F, "left", false, "georamaSemiBold", 45);
            }

            ++index;
         }
      }

      drawWaitingQueueOverlay(mouseXScaled, mouseYScaled);
      GL11.glPopMatrix();
   }

   public static void openURL(String url) {
      if(Desktop.isDesktopSupported()) {
         Desktop desktop = Desktop.getDesktop();
         if(desktop.isSupported(Action.BROWSE)) {
            try {
               desktop.browse(new URI(url));
            } catch (Exception var3) {
               var3.printStackTrace();
            }
         }
      }

   }

   protected void func_73864_a(int par1, int par2, int par3) {
      super.func_73864_a(par1, par2, par3);
      int windowWidth = this.field_73880_f;
      int windowHeight = this.field_73880_f * 9 / 16;
      int mouseXScaled = (int)((float)par1 * (3840.0F / (float)this.field_73880_f));
      int mouseYScaled = (int)((float)par2 * (2160.0F / (float)windowHeight));
      if(mouseXScaled >= 60 && mouseXScaled <= 140 && mouseYScaled >= 720 && mouseYScaled <= 776) {
         boolean serverType1 = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
         this.field_73882_e.func_71373_a((GuiScreen)(!serverType1?new GuiMultiplayer(this):new GuiSelectWorld(this)));
      } else if(mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
         if(Minecraft.func_71410_x().field_71439_g != null && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar")) {
            this.field_73882_e.func_71373_a(new GuiSelectWorld(this));
         }
      } else if(mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1280 && mouseYScaled <= 1344) {
         Minecraft.func_71410_x().func_71373_a(new GuiBrowser("https://wiki.nationsglory.fr/fr/"));
      } else if(mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1400 && mouseYScaled <= 1464) {
         openURL(serverLang.equals("fr")?"https://nationsglory.fr":"https://nationsglory.com");
      } else if(mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1520 && mouseYScaled <= 1586) {
         openURL(serverLang.equals("fr")?"https://discord.gg/nationsglory":"https://discord.gg/eSwrBrzAkD");
      } else if(mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1640 && mouseYScaled <= 1698) {
         openURL(serverLang.equals("fr")?"ts3server://ts.nationsglory.fr":"ts3server://ts.nationsglory.com");
      } else if(mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1760 && mouseYScaled <= 1820) {
         this.field_73882_e.func_71373_a(new GuiOptions(this, this.field_73882_e.field_71474_y));
      } else if(mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1880 && mouseYScaled <= 1940) {
         this.field_73882_e.func_71373_a(new GuiConnecting(this, this.field_73882_e, "127.0.0.1", 25565));
      } else if(hoveredAction.contains("carousel_scroll")) {
         activeCarouselSlide = hoveredAction.split("#")[1].equals("up")?activeCarouselSlide + 1:(activeCarouselSlide - 1 >= 0?activeCarouselSlide - 1:Math.max(0, ((ArrayList)cachedDataMainMenu.get("store")).size() - 1));
      } else if(hoveredAction.contains("carousel_change")) {
         activeCarouselSlide = Integer.parseInt(hoveredAction.split("#")[1]);
      } else if(!hoveredServer.isEmpty()) {
         String serverType = hoveredServer.split("#")[0];
         String serverName = hoveredServer.split("#")[1];
         String serverIp = hoveredServer.split("#")[2];
         String serverPort = hoveredServer.split("#")[3];
         if(serverName != null && ClientProxy.getServerIpAndPort(serverName) != null) {
            if(ClientSocket.out != null) {
               ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverName.toLowerCase());
               ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
               Minecraft.func_71410_x().func_71373_a(new WaitingSocketGui());
            } else {
               this.field_73882_e.func_71373_a(new GuiMultiplayer(this));
            }
         } else {
            int port = Integer.parseInt(serverPort);
            this.field_73882_e.func_71373_a(new GuiConnecting(this, this.field_73882_e, serverIp, port));
         }
      } else if(!hoveredUrl.isEmpty()) {
         openURL(hoveredUrl);
      } else if(hoveredAction.equalsIgnoreCase("quit_waiting")) {
         ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
         ClientData.waitingServerName = null;
      }

   }

   // $FF: synthetic method
   static String access$000(MainGUI x0, String x1) {
      return x0.clearPopulationString(x1);
   }

}
