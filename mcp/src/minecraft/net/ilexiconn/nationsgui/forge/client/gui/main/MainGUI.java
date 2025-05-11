/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.internal.LinkedTreeMap
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSelectWorld
 *  net.minecraft.client.multiplayer.GuiConnecting
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Session
 *  org.apache.commons.io.FileUtils
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.main;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.gui.GuiBrowser;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
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
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class MainGUI
extends GuiMainMenu {
    private static Gson gson = new Gson();
    public static List<String> serverList = Arrays.asList("blue", "orange", "yellow", "white", "black", "cyan", "lime", "coral", "pink", "purple", "green", "red", "ruby", "mocha");
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
    public static float ANIMATION_NEWS_DURATION = 400.0f;
    public static long ANIMATION_NEWS_STARTED = 0L;
    public static String ANIMATION_NEWS_WAY = "down";
    public static String hoveredServer = "";
    public static String hoveredUrl = "";
    public static String hoveredAction = "";
    private Map<Map.Entry<String, ServersData.ServerGroup>, Float> serverGroups = new HashMap<Map.Entry<String, ServersData.ServerGroup>, Float>();
    private ModernScrollBar modernScrollBar;
    List<ModernServerButton> tempServList = new ArrayList<ModernServerButton>();
    private float decalBase;
    public static List<GuiButton> serversButtonList = new ArrayList<GuiButton>();
    public static String serverLang = "fr";
    public static HashMap<String, Object> cachedDataMainMenu = new HashMap();
    public static long lastRefreshDataMainMenu = 0L;
    public static int activeCarouselSlide = 0;
    public static ResourceLocation cacheHeadPlayer = null;
    public static HashMap<String, DynamicTexture> cachedFlagsTexture = new HashMap();

    public MainGUI() {
        for (String server : serverList) {
            SERVERS.put(server, new ResourceLocation("nationsgui", "textures/gui/main/servers/" + server + ".png"));
            SERVERS_HOVER.put(server, new ResourceLocation("nationsgui", "textures/gui/main/servers_hover/" + server + ".png"));
        }
        try {
            FileUtils.write((File)new File("snoopers.json"), (CharSequence)new Gson().toJson((Object)Minecraft.func_71410_x().func_71378_E().func_76465_c()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ClientEventHandler.modsChecked = false;
        NationsGUIClientHooks.setDone(true);
        Thread thread = new Thread(){

            @Override
            public void run() {
                Method method = null;
                try {
                    method = GuiMultiplayer.class.getDeclaredMethod("func_74017_b", ServerData.class);
                    method.setAccessible(true);
                }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                HashMap<String, ServersData.Server> serverMap = new HashMap<String, ServersData.Server>();
                for (Map.Entry<String, ServersData.ServerGroup> entry : ClientProxy.serversData.getPermanentServers().entrySet()) {
                    serverMap.putAll(entry.getValue().getServerMap());
                }
                serverMap.putAll(ClientProxy.serversData.getTemporaryServers());
                for (ServersData.Server server : serverMap.values()) {
                    if (!server.isVisible(Minecraft.func_71410_x()) || server.getIp().contains("event.nationsglory")) continue;
                    ServerData serverData = new ServerData("Server", server.getIp());
                    try {
                        method.invoke(null, serverData);
                        server.setSlots(Integer.parseInt(MainGUI.this.clearPopulationString(serverData.field_78846_c)));
                    }
                    catch (IllegalAccessException | InvocationTargetException e) {
                        server.setSlots(0);
                    }
                }
            }
        };
        thread.start();
        if (System.currentTimeMillis() - lastRefreshDataMainMenu > 600000L) {
            try {
                HashMap data;
                Session sess = Minecraft.func_71410_x().func_110432_I();
                cachedDataMainMenu = data = (HashMap)gson.fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/mods/menu?accessToken=" + sess.func_111286_b() + "&cuid=" + System.getProperty("java.tweaker") + "&lang=" + serverLang).openStream(), StandardCharsets.UTF_8), new TypeToken<HashMap<String, Object>>(){}.getType());
                lastRefreshDataMainMenu = System.currentTimeMillis();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String clearPopulationString(String string) {
        string = string.split("/")[0];
        StringBuilder stringBuilder = new StringBuilder();
        boolean colorType = false;
        for (char c : string.toCharArray()) {
            if (c == '\u00a7') {
                colorType = true;
                continue;
            }
            if (colorType) {
                colorType = false;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void func_73866_w_() {
        this.field_73887_h.clear();
        int baseY = 88;
        int serverPerLine = (int)(((float)this.field_73880_f - 139.0f - 12.0f - 16.0f) / 86.0f);
        this.serverGroups.clear();
        for (Map.Entry<String, ServersData.ServerGroup> entry : ClientProxy.serversData.getPermanentServers().entrySet()) {
            boolean flag = false;
            Iterator<ServersData.Server> iterator = entry.getValue().getServerMap().values().iterator();
            if (iterator.hasNext()) {
                ServersData.Server server = iterator.next();
                this.serverGroups.put(entry, Float.valueOf((float)baseY - 4.5f - 8.0f));
                flag = true;
            }
            int i = 0;
            for (Map.Entry<String, ServersData.Server> entry2 : entry.getValue().getServerMap().entrySet()) {
                if (i == serverPerLine) {
                    i = 0;
                    baseY += 18;
                }
                ServersData.Server server = entry2.getValue();
                this.field_73887_h.add(new ModernServerButton(6, 147 + i * 88, baseY, 84, 14, server, entry2.getKey(), "permanent"));
                ++i;
            }
            if (!flag) continue;
            baseY += 36;
        }
        serversButtonList = this.field_73887_h;
        baseY = 136;
        this.tempServList.clear();
        for (Map.Entry<String, Object> entry : ClientProxy.serversData.getTemporaryServers().entrySet()) {
            ServersData.Server server = (ServersData.Server)entry.getValue();
            if (!server.isVisible(this.field_73882_e)) continue;
            this.tempServList.add(new ModernServerButton(6, 20, baseY, 107, 14, server, entry.getKey(), "temporary"));
            baseY += 18;
        }
        float size = this.field_73881_g - 112 - 58 - 24 - 8;
        this.decalBase = (float)(baseY - 136) - size;
        if ((float)(baseY - 136) > size) {
            if (this.modernScrollBar != null) {
                this.modernScrollBar.setHeight((int)size);
            } else {
                this.modernScrollBar = new ModernScrollBar(129.0f, 136.0f, 3, this.field_73881_g - 112 - 58 - 24 - 8, 3);
            }
        } else if (this.modernScrollBar != null) {
            this.modernScrollBar = null;
        }
    }

    protected void func_73875_a(GuiButton button) {
    }

    public static void drawWaitingQueueOverlay(int mouseX, int mouseY) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int positionX = 50;
        int positionY = 50;
        if (ClientData.waitingServerName == null || ClientData.waitingServerName.isEmpty() || ClientData.waitingServerName.equalsIgnoreCase("null")) {
            return;
        }
        ClientEventHandler.STYLE.bindTexture("overlay_hud");
        ModernGui.drawScaledCustomSizeModalRect(positionX, positionY, 813.0f, 0.0f, 216, 114, 432, 228, 1920.0f, 1033.0f, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waiting.server") + " " + ClientData.waitingServerName.toUpperCase(), positionX + 30, positionY + 25, 0x6E76EE, 2.5f, "left", false, "minecraftDungeons", 24);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + (ClientData.waitingPriority ? I18n.func_135053_a((String)"waiting.list.priority") : I18n.func_135053_a((String)"waiting.list.classic")), positionX + 30, positionY + 80, 0xD9D9D9, 2.0f, "left", false, "georamaSemiBold", 24);
        long diffTime = System.currentTimeMillis() - ClientData.waitingJoinTime;
        diffTime = diffTime / 1000L / 60L;
        ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / \u00a77" + ClientData.waitingTotal, positionX + 30, positionY + 120, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 30);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + diffTime + " minutes", positionX + 30, positionY + 160, 0xD9D9D9, 2.0f, "left", false, "georamaRegular", 20);
        boolean hoveringQuitBtn = mouseX >= positionX && mouseX <= positionX + 198 && mouseY >= positionY + 240 && mouseY <= positionY + 240 + 48;
        ClientEventHandler.STYLE.bindTexture("overlay_hud");
        ModernGui.drawScaledCustomSizeModalRect(positionX, positionY + 240, hoveringQuitBtn ? 1345 : 1241, 88.0f, 99, 24, 198, 48, 1920.0f, 1033.0f, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waiting.quit"), positionX + 105, positionY + 253, 0xFFFFFF, 2.0f, "center", false, "georamaSemiBold", 24);
        if (hoveringQuitBtn) {
            hoveredAction = "quit_waiting";
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        String countryName;
        ArrayList slides;
        hoveredServer = "";
        hoveredUrl = "";
        hoveredAction = "";
        GL11.glDisable((int)2884);
        GL11.glPushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int windowWidth = this.field_73880_f;
        int windowHeight = this.field_73880_f * 9 / 16;
        int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = screenWidth * 9 / 16;
        int mouseXScaled = (int)((float)mouseX * (3840.0f / (float)this.field_73880_f));
        int mouseYScaled = (int)((float)mouseY * (2160.0f / (float)windowHeight));
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0f, 2160.0f, false);
        GL11.glScaled((double)((float)this.field_73880_f / 3840.0f), (double)((float)windowHeight / 2160.0f), (double)1.0);
        this.field_73882_e.func_110434_K().func_110577_a(LOGO_NG);
        ModernGui.drawModalRectWithCustomSizedTexture(24.0f, 34.0f, 0, 0, 148, 148, 148.0f, 148.0f, true);
        this.field_73882_e.func_110434_K().func_110577_a(MultiGUI.LOGO_HOME_HOVER);
        ModernGui.drawModalRectWithCustomSizedTexture(72.0f, 600.0f, 0, 0, 60, 52, 60.0f, 52.0f, true);
        if (mouseXScaled >= 60 && mouseXScaled <= 140 && mouseYScaled >= 720 && mouseYScaled <= 776) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_MULTI_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_MULTI);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(60.0f, 720.0f, 0, 0, 80, 56, 80.0f, 56.0f, true);
        if (Minecraft.func_71410_x().field_71439_g != null && (Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("blakonne") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("ibalix"))) {
            if (mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
                this.field_73882_e.func_110434_K().func_110577_a(LOGO_SOLO_HOVER);
            } else {
                this.field_73882_e.func_110434_K().func_110577_a(LOGO_SOLO);
            }
            ModernGui.drawModalRectWithCustomSizedTexture(74.0f, 840.0f, 0, 0, 52, 52, 52.0f, 52.0f, true);
        }
        if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1280 && mouseYScaled <= 1344) {
            ClientProxy.loadResource("textures/gui/main/logo_wiki_hover.png");
        } else {
            ClientProxy.loadResource("textures/gui/main/logo_wiki.png");
        }
        ModernGui.drawModalRectWithCustomSizedTexture(66.0f, 1280.0f, 0, 0, 64, 64, 64.0f, 64.0f, true);
        if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1400 && mouseYScaled <= 1464) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SITE_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SITE);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(66.0f, 1400.0f, 0, 0, 64, 64, 64.0f, 64.0f, true);
        if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1520 && mouseYScaled <= 1586) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_DISCORD_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_DISCORD);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(70.0f, 1520.0f, 0, 0, 58, 66, 58.0f, 66.0f, false);
        if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1640 && mouseYScaled <= 1698) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_TEAMSPEAK_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_TEAMSPEAK);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(70.0f, 1640.0f, 0, 0, 58, 58, 58.0f, 58.0f, false);
        if (mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1760 && mouseYScaled <= 1820) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SETTINGS_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_SETTINGS);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(70.0f, 1760.0f, 0, 0, 60, 60, 60.0f, 60.0f, false);
        String offlineClient = System.getProperty("ng.offline");
        if (offlineClient != null && offlineClient.equals("true")) {
            ModernGui.drawScaledStringCustomFont("DEV", 100.0f, 1880.0f, 1316402, 3.0f, "center", false, "georamaSemiBold", 28);
        }
        if (cachedDataMainMenu != null) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.news"), 347.0f, 180.0f, 12369613, 2.0f, "left", false, "georamaMedium", 37);
            GUIUtils.startGLScissor((int)(347.0f * ((float)this.field_73880_f / 3840.0f)), (int)(52.0f * ((float)windowHeight / 2160.0f)), (int)(2298.0f * ((float)this.field_73880_f / 3840.0f)), (int)(728.0f * ((float)windowHeight / 2160.0f)));
            if (cachedDataMainMenu.get("news") != null) {
                ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347, 52, 0, 0, 2298, 728, 2298, 728, 2298.0f, 728.0f, false, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"background"));
                if (!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"hover")).isEmpty()) {
                    if (mouseXScaled >= 347 && mouseXScaled <= 2645 && mouseYScaled >= 52 && mouseYScaled <= 780) {
                        if (!ANIMATION_NEWS_WAY.equals("up")) {
                            ANIMATION_NEWS_WAY = "up";
                            ANIMATION_NEWS_STARTED = System.currentTimeMillis();
                        }
                        if ((float)(System.currentTimeMillis() - ANIMATION_NEWS_STARTED) >= ANIMATION_NEWS_DURATION) {
                            ANIMATION_NEWS_STARTED = -1L;
                        }
                        if (ANIMATION_NEWS_STARTED != -1L) {
                            this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347 + (int)((float)(40L * (System.currentTimeMillis() - ANIMATION_NEWS_STARTED)) / ANIMATION_NEWS_DURATION), 52, 0, 0, 2298, 728, 2298, 728, 2298.0f, 728.0f, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"hover"));
                        } else {
                            this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(387, 52, 0, 0, 2298, 728, 2298, 728, 2298.0f, 728.0f, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"hover"));
                        }
                    } else {
                        if (!ANIMATION_NEWS_WAY.equals("down")) {
                            ANIMATION_NEWS_WAY = "down";
                            ANIMATION_NEWS_STARTED = System.currentTimeMillis();
                        }
                        if ((float)(System.currentTimeMillis() - ANIMATION_NEWS_STARTED) >= ANIMATION_NEWS_DURATION) {
                            ANIMATION_NEWS_STARTED = -1L;
                        }
                        if (ANIMATION_NEWS_STARTED != -1L) {
                            this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(387 - (int)((float)(40L * (System.currentTimeMillis() - ANIMATION_NEWS_STARTED)) / ANIMATION_NEWS_DURATION), 52, 0, 0, 2298, 728, 2298, 728, 2298.0f, 728.0f, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"hover"));
                        } else {
                            this.field_73882_e.func_110434_K().func_110577_a(BOX_NEWS_OVER);
                            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(347, 52, 0, 0, 2298, 728, 2298, 728, 2298.0f, 728.0f, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"hover"));
                        }
                    }
                }
                if (!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"text_button")).isEmpty()) {
                    if (mouseXScaled >= 398 && mouseXScaled <= 720 && mouseYScaled >= 628 && mouseYScaled <= 712) {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_NEWS_HOVER);
                        ModernGui.drawModalRectWithCustomSizedTexture(398.0f, 628.0f, 0, 0, 322, 84, 322.0f, 84.0f, true);
                        ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"text_button"), 560.0f, 648.0f, 0xFFFFFF, 3.0f, "center", false, "georamaSemiBold", 28);
                        String buttonTarget = (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"link_button");
                        if (buttonTarget.contains("#")) {
                            hoveredServer = buttonTarget;
                        } else {
                            hoveredUrl = buttonTarget;
                        }
                    } else {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_NEWS);
                        ModernGui.drawModalRectWithCustomSizedTexture(398.0f, 628.0f, 0, 0, 322, 84, 322.0f, 84.0f, true);
                        ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"text_button"), 560.0f, 648.0f, 1316402, 3.0f, "center", false, "georamaSemiBold", 28);
                    }
                }
                int textIndex = 0;
                for (String line : ((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"text")).split("\\\\n")) {
                    ModernGui.drawScaledStringCustomFont(line, 402.0f, 351 + 40 * textIndex + (textIndex != 0 ? 50 : 0), 0xEEEFFC, textIndex == 0 ? 4.0f : 2.0f, "left", false, textIndex == 0 ? "georamaBold" : "georamaRegular", 33);
                    ++textIndex;
                }
            }
            GUIUtils.endGLScissor();
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.shortcuts"), 346.0f, 860.0f, 12369613, 2.0f, "left", false, "georamaMedium", 37);
        int offsetX = 346;
        if (mouseXScaled >= offsetX && mouseXScaled <= offsetX + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
            this.field_73882_e.func_110434_K().func_110577_a(BOX_HUB);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
            if (serverLang.equals("fr")) {
                hoveredServer = "annexe#hub#hub.nationsglory.fr#25578";
            }
        } else {
            ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
        }
        if (serverLang.equals("fr")) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.hub.title"), offsetX + 60, 1020.0f, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 50);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.hub.subtitle1"), offsetX + 60, 1100.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.hub.subtitle2"), offsetX + 60, 1130.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
        }
        ClientProxy.loadResource("textures/gui/main/arrow_right.png");
        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 544, 1039.0f, 0, 0, 84, 84, 84.0f, 84.0f, true);
        offsetX = 1138;
        if (mouseXScaled >= offsetX && mouseXScaled <= offsetX + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
            this.field_73882_e.func_110434_K().func_110577_a(BOX_MINE);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
            if (serverLang.equals("fr")) {
                hoveredServer = "annexe#accueil#event.nationsglory.fr#25579";
            }
        } else {
            ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.tutorial.title"), offsetX + 60, 1020.0f, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 50);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.tutorial.subtitle1"), offsetX + 60, 1100.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.tutorial.subtitle2"), offsetX + 60, 1130.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
        ClientProxy.loadResource("textures/gui/main/arrow_right.png");
        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 544, 1039.0f, 0, 0, 84, 84, 84.0f, 84.0f, true);
        offsetX = 1930;
        if (mouseXScaled >= offsetX && mouseXScaled <= offsetX + 714 && mouseYScaled >= 911 && mouseYScaled <= 1251) {
            this.field_73882_e.func_110434_K().func_110577_a(BOX_ISLAND);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
            if (serverLang.equals("fr")) {
                hoveredServer = "annexe#freebuild1#event.nationsglory.fr#25580";
            }
        } else {
            ClientProxy.loadResource("textures/gui/main/box_shortcut.png");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, 911.0f, 0, 0, 714, 340, 714.0f, 340.0f, true);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.island.title"), offsetX + 60, 1020.0f, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 50);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.island.subtitle1"), offsetX + 60, 1100.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.island.subtitle2"), offsetX + 60, 1130.0f, 0xFFFFFF, 2.0f, "left", false, "georamaRegular", 30);
        ClientProxy.loadResource("textures/gui/main/arrow_right.png");
        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 544, 1039.0f, 0, 0, 84, 84, 84.0f, 84.0f, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.to_discover"), 347.0f, 1324.0f, 12369613, 2.0f, "left", false, "georamaMedium", 37);
        if (cachedDataMainMenu != null && (slides = (ArrayList)cachedDataMainMenu.get("store")) != null && slides.size() > 0) {
            if (slides.size() <= activeCarouselSlide) {
                activeCarouselSlide = 0;
            }
            ClientProxy.loadResource("textures/gui/main/carousel_background.png");
            ModernGui.drawModalRectWithCustomSizedTexture(345.0f, 1374.0f, 0, 0, 1508, 622, 1508.0f, 622.0f, false);
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(487, 1459, 0, 0, 500, 450, 500, 450, 500.0f, 450.0f, true, (String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get((Object)"image"));
            int textIndex = 0;
            for (String line : ((String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get((Object)"text")).split("\\\\n")) {
                ModernGui.drawScaledStringCustomFont(line, 1167.0f, 1563 + 40 * textIndex + (textIndex > 0 ? 30 : 0), 0xFFFFFF, textIndex == 0 ? 3.0f : 2.0f, "left", false, textIndex == 0 ? "georamaBold" : "georamaRegular", 33);
                ++textIndex;
            }
            textIndex += 2;
            if (mouseXScaled >= 500 && mouseXScaled <= 1720 && mouseYScaled >= 1412 && mouseYScaled <= 1912) {
                ClientProxy.loadResource("textures/gui/main/button_shop_hover.png");
                ModernGui.drawModalRectWithCustomSizedTexture(1158.0f, 1563 + textIndex * 40, 0, 0, 242, 78, 242.0f, 78.0f, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.discover"), 1279.0f, 1563 + textIndex * 40 + 22, 0, 2.0f, "center", false, "georamaBold", 33);
                hoveredUrl = (String)((LinkedTreeMap)((ArrayList)cachedDataMainMenu.get("store")).get(activeCarouselSlide)).get((Object)"link");
            } else {
                ClientProxy.loadResource("textures/gui/main/button_shop.png");
                ModernGui.drawModalRectWithCustomSizedTexture(1158.0f, 1563 + textIndex * 40, 0, 0, 242, 78, 242.0f, 78.0f, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.discover"), 1282.0f, 1563 + textIndex * 40 + 23, 0xFFFFFF, 2.0f, "center", false, "georamaBold", 33);
            }
            if (slides.size() > 1) {
                if (mouseXScaled >= 356 && mouseXScaled <= 486 && mouseYScaled >= 1534 && mouseYScaled <= 1854) {
                    ClientProxy.loadResource("textures/gui/main/carousel_left_hover.png");
                    hoveredAction = "carousel_scroll#down";
                } else {
                    ClientProxy.loadResource("textures/gui/main/carousel_left.png");
                }
                ModernGui.drawModalRectWithCustomSizedTexture(400.0f, 1649.0f, 0, 0, 41, 74, 41.0f, 74.0f, true);
                if (mouseXScaled >= 1724 && mouseXScaled <= 1854 && mouseYScaled >= 1534 && mouseYScaled <= 1854) {
                    ClientProxy.loadResource("textures/gui/main/carousel_right_hover.png");
                    hoveredAction = "carousel_scroll#up";
                } else {
                    ClientProxy.loadResource("textures/gui/main/carousel_right.png");
                }
                ModernGui.drawModalRectWithCustomSizedTexture(1753.0f, 1649.0f, 0, 0, 41, 74, 41.0f, 74.0f, true);
                for (int i = 0; i < slides.size(); ++i) {
                    if (activeCarouselSlide == i) {
                        ClientProxy.loadResource("textures/gui/main/carousel_dot_active.png");
                    } else {
                        ClientProxy.loadResource("textures/gui/main/carousel_dot_inactive.png");
                    }
                    int posX = 1096 - (slides.size() > 1 ? 25 * (slides.size() - 1) : 0);
                    ModernGui.drawModalRectWithCustomSizedTexture(posX + i * 50 - 14, 1934.0f, 0, 0, 28, 28, 28.0f, 28.0f, true);
                    if (mouseXScaled < posX + i * 50 - 14 || mouseXScaled > posX + i * 50 - 14 + 28 || mouseYScaled < 1934 || mouseYScaled > 1962) continue;
                    hoveredAction = "carousel_change#" + i;
                }
            }
        }
        String server = "";
        if (cachedDataMainMenu != null) {
            server = cachedDataMainMenu.get("userbox") != null && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"last_server") != null ? (String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"last_server") : "none";
            ClientProxy.loadResource("textures/gui/main/servers_card/" + server + ".png");
            ModernGui.drawModalRectWithCustomSizedTexture(1930.0f, 1374.0f, 0, 0, 714, 400, 714.0f, 400.0f, true);
            if (cacheHeadPlayer == null) {
                try {
                    ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                    resourceLocation = AbstractClientPlayer.func_110311_f((String)Minecraft.func_71410_x().func_110432_I().func_111285_a());
                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)Minecraft.func_71410_x().func_110432_I().func_111285_a());
                    cacheHeadPlayer = resourceLocation;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer);
                this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer);
                GUIUtils.drawScaledCustomSizeModalRect(2070, 1516, 8.0f, 16.0f, 8, -8, -100, -100, 64.0f, 64.0f);
            }
            ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().func_110432_I().func_111285_a().toUpperCase(), 2100.0f, 1423.0f, !server.equals("white") ? 0xFFFFFF : 0x242447, 3.0f, "left", false, "georamaBold", 33);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.server").replace("#server#", !server.equals("none") ? server.substring(0, 1).toUpperCase() + server.substring(1) : "-"), 2100.0f, 1478.0f, 0x242447, 2.0f, "left", false, "georamaMedium", 33);
        }
        if (cachedDataMainMenu != null && cachedDataMainMenu.get("userbox") != null && !cachedFlagsTexture.containsKey(server) && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"country") != null && ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"flag") != null) {
            BufferedImage image = ModernGui.decodeToImage((String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"flag"));
            cachedFlagsTexture.put(server, new DynamicTexture(image));
        }
        if (cachedFlagsTexture.containsKey(server)) {
            GL11.glBindTexture((int)3553, (int)cachedFlagsTexture.get(server).func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect(1998.0f, 1582.0f, 0.0f, 0.0f, 156, 78, 221, 130, 156.0f, 78.0f, false);
        } else {
            Gui.func_73734_a((int)1998, (int)1582, (int)2219, (int)1712, (int)1209604377);
        }
        String string = countryName = ((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"country") != null && !((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"country").equals("") ? (String)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"country") : I18n.func_135053_a((String)"main.no_country");
        if (countryName.length() > 13) {
            countryName = countryName.substring(0, 12) + "..";
        }
        ModernGui.drawScaledStringCustomFont(countryName, 2250.0f, 1600.0f, !server.equals("white") ? 0xFFFFFF : 0x242447, 3.0f, "left", false, "georamaBold", 33);
        if (((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"money") != null) {
            ModernGui.drawScaledStringCustomFont(((Double)((LinkedTreeMap)cachedDataMainMenu.get("userbox")).get((Object)"money")).intValue() + "$", 2250.0f, 1657.0f, !server.equals("white") ? 0xFFFFFF : 0x242447, 2.0f, "left", false, "georamaSemiBold", 33);
        }
        if (mouseXScaled >= 1930 && mouseXScaled <= 2644 && mouseYScaled >= 1820 && mouseYScaled <= 1996) {
            ClientProxy.loadResource("textures/gui/main/box_patchnote_hover.png");
            hoveredUrl = "https://nationsglory.fr/changelogs";
        } else {
            ClientProxy.loadResource("textures/gui/main/box_patchnote.png");
        }
        ModernGui.drawModalRectWithCustomSizedTexture(1930.0f, 1820.0f, 0, 0, 714, 176, 714.0f, 176.0f, true);
        ClientProxy.loadResource("textures/gui/main/logo_patchnote.png");
        ModernGui.drawModalRectWithCustomSizedTexture(1972.0f, 1879.0f, 0, 0, 94, 63, 94.0f, 63.0f, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.patchnotes"), 2100.0f, 1890.0f, 0xFFFFFF, 3.0f, "left", false, "georamaBold", 33);
        ClientProxy.loadResource("textures/gui/main/arrow_right.png");
        ModernGui.drawModalRectWithCustomSizedTexture(2500.0f, 1866.0f, 0, 0, 84, 84, 84.0f, 84.0f, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.servers") + " - " + serverLang.toUpperCase(), 2900.0f, 120.0f, 0xFFFFFF, 2.0f, "left", false, "georamaMedium", 37);
        int index = 0;
        for (GuiButton button : this.field_73887_h) {
            int offset = 170 * (index % 7);
            offsetX = index / 7 * 450;
            String serverName = ((ModernServerButton)button).getServerName();
            String serverType = ((ModernServerButton)button).getServerType();
            String serverIp = ((ModernServerButton)button).getIp();
            String serverPort = ((ModernServerButton)button).getPort() + "";
            if (!((ModernServerButton)button).server.getZones().contains(serverLang) && !serverLang.contains("fr")) continue;
            if (SERVERS.containsKey(serverName)) {
                if (mouseXScaled >= 2900 + offsetX && mouseXScaled <= 2900 + offsetX + 116 && mouseYScaled >= 230 + offset && mouseYScaled <= 230 + offset + 118) {
                    this.field_73882_e.func_110434_K().func_110577_a(SERVERS_HOVER.get(serverName));
                    hoveredServer = serverType + "#" + serverName + "#" + serverIp + "#" + serverPort;
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(SERVERS.get(serverName));
                }
                ModernGui.drawModalRectWithCustomSizedTexture(2900 + offsetX, 230 + offset, 0, 0, 116, 118, 116.0f, 118.0f, false);
                ModernGui.drawScaledStringCustomFont(serverName.substring(0, 1).toUpperCase() + serverName.substring(1), 3034 + offsetX, 260 + offset, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 45);
            }
            ++index;
        }
        MainGUI.drawWaitingQueueOverlay(mouseXScaled, mouseYScaled);
        GL11.glPopMatrix();
    }

    public static void openURL(String url) {
        Desktop desktop;
        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        super.func_73864_a(par1, par2, par3);
        int windowWidth = this.field_73880_f;
        int windowHeight = this.field_73880_f * 9 / 16;
        int mouseXScaled = (int)((float)par1 * (3840.0f / (float)this.field_73880_f));
        int mouseYScaled = (int)((float)par2 * (2160.0f / (float)windowHeight));
        if (mouseXScaled >= 60 && mouseXScaled <= 140 && mouseYScaled >= 720 && mouseYScaled <= 776) {
            boolean isShiftDown = Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54);
            this.field_73882_e.func_71373_a((GuiScreen)(!isShiftDown ? new GuiMultiplayer((GuiScreen)this) : new GuiSelectWorld((GuiScreen)this)));
        } else if (mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
            if (Minecraft.func_71410_x().field_71439_g != null && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar")) {
                this.field_73882_e.func_71373_a((GuiScreen)new GuiSelectWorld((GuiScreen)this));
            }
        } else if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1280 && mouseYScaled <= 1344) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new GuiBrowser("https://wiki.nationsglory.fr/fr/"));
        } else if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1400 && mouseYScaled <= 1464) {
            MainGUI.openURL(serverLang.equals("fr") ? "https://nationsglory.fr" : "https://nationsglory.com");
        } else if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1520 && mouseYScaled <= 1586) {
            MainGUI.openURL(serverLang.equals("fr") ? "https://discord.gg/nationsglory" : "https://discord.gg/eSwrBrzAkD");
        } else if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1640 && mouseYScaled <= 1698) {
            MainGUI.openURL(serverLang.equals("fr") ? "ts3server://ts.nationsglory.fr" : "ts3server://ts.nationsglory.com");
        } else if (mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1760 && mouseYScaled <= 1820) {
            this.field_73882_e.func_71373_a((GuiScreen)new GuiOptions((GuiScreen)this, this.field_73882_e.field_71474_y));
        } else if (mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1880 && mouseYScaled <= 1940) {
            this.field_73882_e.func_71373_a((GuiScreen)new GuiConnecting((GuiScreen)this, this.field_73882_e, "127.0.0.1", 25565));
        } else if (hoveredAction.contains("carousel_scroll")) {
            activeCarouselSlide = hoveredAction.split("#")[1].equals("up") ? activeCarouselSlide + 1 : (activeCarouselSlide - 1 >= 0 ? activeCarouselSlide - 1 : Math.max(0, ((ArrayList)cachedDataMainMenu.get("store")).size() - 1));
        } else if (hoveredAction.contains("carousel_change")) {
            activeCarouselSlide = Integer.parseInt(hoveredAction.split("#")[1]);
        } else if (!hoveredServer.isEmpty()) {
            String serverType = hoveredServer.split("#")[0];
            String serverName = hoveredServer.split("#")[1];
            String serverIp = hoveredServer.split("#")[2];
            String serverPort = hoveredServer.split("#")[3];
            if (serverName != null && ClientProxy.getServerIpAndPort(serverName) != null) {
                if (ClientSocket.out != null) {
                    ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverName.toLowerCase());
                    ClientData.waitingJoinTime = System.currentTimeMillis();
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingSocketGui());
                } else {
                    this.field_73882_e.func_71373_a((GuiScreen)new GuiMultiplayer((GuiScreen)this));
                }
            } else {
                int port = Integer.parseInt(serverPort);
                this.field_73882_e.func_71373_a((GuiScreen)new GuiConnecting((GuiScreen)this, this.field_73882_e, serverIp, port));
            }
        } else if (!hoveredUrl.isEmpty()) {
            MainGUI.openURL(hoveredUrl);
        } else if (hoveredAction.equalsIgnoreCase("quit_waiting")) {
            ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
            ClientData.waitingServerName = null;
        }
    }
}

