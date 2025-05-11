/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSelectWorld
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.multiplayer.GuiConnecting
 *  net.minecraft.client.multiplayer.ServerAddress
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.multiplayer.ServerList
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet254ServerPing
 *  net.minecraft.util.ChatAllowedCharacters
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.multi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.gui.GuiBrowser;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernServerButton;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class MultiGUI
extends GuiMultiplayer {
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
    public static final ResourceLocation LOGO_HOME = new ResourceLocation("nationsgui", "textures/gui/main/logo_home.png");
    public static final ResourceLocation LOGO_HOME_HOVER = new ResourceLocation("nationsgui", "textures/gui/main/logo_home_hover.png");
    public static final ResourceLocation BUTTON_ADD = new ResourceLocation("nationsgui", "textures/gui/multi/button_add.png");
    public static final ResourceLocation BUTTON_ADD_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/button_add_hover.png");
    public static final ResourceLocation BUTTON_RELOAD = new ResourceLocation("nationsgui", "textures/gui/multi/button_reload.png");
    public static final ResourceLocation BUTTON_RELOAD_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/button_reload_hover.png");
    public static final ResourceLocation BUTTON_REMOVE = new ResourceLocation("nationsgui", "textures/gui/multi/button_remove.png");
    public static final ResourceLocation BUTTON_REMOVE_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/button_remove_hover.png");
    public static final ResourceLocation BUTTON_EDIT = new ResourceLocation("nationsgui", "textures/gui/multi/button_edit.png");
    public static final ResourceLocation BUTTON_EDIT_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/button_edit_hover.png");
    public static final ResourceLocation BUTTON_CONNECTION = new ResourceLocation("nationsgui", "textures/gui/multi/button_connection.png");
    public static final ResourceLocation BUTTON_CONNECTION_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/button_connection_hover.png");
    public static final ResourceLocation BUTTON_WHITE = new ResourceLocation("nationsgui", "textures/gui/generic/button_white.png");
    public static final ResourceLocation BUTTON_BLUE = new ResourceLocation("nationsgui", "textures/gui/generic/button_blue.png");
    public static final ResourceLocation BUTTON_HOVER = new ResourceLocation("nationsgui", "textures/gui/generic/button_hover.png");
    public static final ResourceLocation BOX_MULTI = new ResourceLocation("nationsgui", "textures/gui/multi/box_multi.png");
    public static final ResourceLocation BOX_MULTI_SHADOW_BOTTOM = new ResourceLocation("nationsgui", "textures/gui/multi/box_multi_shadow_bottom.png");
    public static final ResourceLocation BOX_MULTI_SHADOW_TOP = new ResourceLocation("nationsgui", "textures/gui/multi/box_multi_shadow_top.png");
    public static final ResourceLocation BOX_SERVER = new ResourceLocation("nationsgui", "textures/gui/multi/box_server.png");
    public static final ResourceLocation BOX_SERVER_HOVER = new ResourceLocation("nationsgui", "textures/gui/multi/box_server_hover.png");
    public static final ResourceLocation PING_BAD = new ResourceLocation("nationsgui", "textures/gui/multi/ping_bad.png");
    public static final ResourceLocation PING_MEDIUM = new ResourceLocation("nationsgui", "textures/gui/multi/ping_medium.png");
    public static final ResourceLocation PING_GOOD = new ResourceLocation("nationsgui", "textures/gui/multi/ping_good.png");
    public static final ResourceLocation SCROLLBAR = new ResourceLocation("nationsgui", "textures/gui/multi/scrollbar.png");
    public static final ResourceLocation SCROLLBAR_CURSOR = new ResourceLocation("nationsgui", "textures/gui/multi/scrollbar_cursor.png");
    public static final ResourceLocation TOOLTIP_OVERLAY = new ResourceLocation("nationsgui", "textures/gui/generic/tooltip_overlay.png");
    public static final ResourceLocation INPUT = new ResourceLocation("nationsgui", "textures/gui/generic/input.png");
    public static final ResourceLocation TOOLTIP_SMALL = new ResourceLocation("nationsgui", "textures/gui/generic/tooltip_small.png");
    public static final ResourceLocation CHECKBOX_DOT = new ResourceLocation("nationsgui", "textures/gui/generic/checkbox_dot.png");
    public static final ResourceLocation CHECKBOX_ON = new ResourceLocation("nationsgui", "textures/gui/generic/checkbox_on.png");
    public static final ResourceLocation CHECKBOX_OFF = new ResourceLocation("nationsgui", "textures/gui/generic/checkbox_off.png");
    public static final ResourceLocation CROSS_BLUE = new ResourceLocation("nationsgui", "textures/gui/generic/cross_blue.png");
    public static final ResourceLocation CROSS_WHITE = new ResourceLocation("nationsgui", "textures/gui/generic/cross_white.png");
    public static final ResourceLocation CHECKBOX_CHECKED = new ResourceLocation("nationsgui", "textures/gui/generic/checkbox_checked.png");
    public static final ResourceLocation CHECKBOX_NOT_CHECKED = new ResourceLocation("nationsgui", "textures/gui/generic/checkbox_not_checked.png");
    public static final HashMap<String, ResourceLocation> SERVERS = new HashMap();
    public static final HashMap<String, ResourceLocation> SERVERS_HOVER = new HashMap();
    public static String hoveredServer = "";
    public static String hoveredUrl = "";
    private float decalBase;
    public MainGUI mainGui;
    public static MultiGUI multiGui;
    private RenderItem itemRenderer = new RenderItem();
    public ServerList multiServers;
    public long lastServerRefresh = 0L;
    public int hoveredServerF6 = -1;
    public String hoveredActionF6 = "";
    public String openedTooltip = "";
    private GuiTextField inputAdd_serverName;
    private GuiTextField inputAdd_serverAdress;
    private GuiTextField inputConnect_serverAdress;
    private boolean checkboxWarning_dontAsk = false;
    private GuiScrollBarGeneric scrollBar;
    private ServerData editedServerData = null;
    private ServerData warnedServerData = null;

    public MultiGUI(GuiScreen guiScreen) {
        super(guiScreen);
        this.mainGui = (MainGUI)guiScreen;
        multiGui = this;
        for (String server : MainGUI.serverList) {
            SERVERS.put(server, new ResourceLocation("nationsgui", "textures/gui/main/servers/" + server + ".png"));
            SERVERS_HOVER.put(server, new ResourceLocation("nationsgui", "textures/gui/main/servers_hover/" + server + ".png"));
        }
    }

    public void func_74016_g() {
    }

    public void func_73876_c() {
        super.func_73876_c();
        this.inputAdd_serverName.func_73780_a();
        this.inputAdd_serverAdress.func_73780_a();
        this.inputConnect_serverAdress.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.inputAdd_serverName.func_73802_a(typedChar, keyCode);
        this.inputAdd_serverAdress.func_73802_a(typedChar, keyCode);
        this.inputConnect_serverAdress.func_73802_a(typedChar, keyCode);
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBar = new GuiScrollBarGeneric(2550.0f, 366.0f, 1478, SCROLLBAR_CURSOR, 24, 179);
        int windowHeight = this.field_73880_f * 9 / 16;
        this.inputAdd_serverName = new GuiTextField(this.field_73886_k, (int)(1650.0f / (3840.0f / (float)this.field_73880_f)), (int)(880.0f / (2160.0f / (float)windowHeight)), (int)(530.0f / (3840.0f / (float)this.field_73880_f)), 20);
        this.inputAdd_serverName.func_73786_a(false);
        this.inputAdd_serverName.func_73804_f(25);
        this.inputAdd_serverAdress = new GuiTextField(this.field_73886_k, (int)(1650.0f / (3840.0f / (float)this.field_73880_f)), (int)(1050.0f / (2160.0f / (float)windowHeight)), (int)(530.0f / (3840.0f / (float)this.field_73880_f)), 20);
        this.inputAdd_serverAdress.func_73786_a(false);
        this.inputAdd_serverAdress.func_73804_f(35);
        this.inputConnect_serverAdress = new GuiTextField(this.field_73886_k, (int)(1650.0f / (3840.0f / (float)this.field_73880_f)), (int)(950.0f / (2160.0f / (float)windowHeight)), (int)(530.0f / (3840.0f / (float)this.field_73880_f)), 20);
        this.inputConnect_serverAdress.func_73786_a(false);
        this.inputConnect_serverAdress.func_73804_f(35);
        this.multiServers = new ServerList(this.field_73882_e);
        this.multiServers.func_78853_a();
        if (MainGUI.serverLang.equals("fr")) {
            for (Map.Entry<String, ServersData.ServerGroup> entry : ClientProxy.serversData.getPermanentServers().entrySet()) {
                for (Map.Entry<String, ServersData.Server> server : entry.getValue().getServerMap().entrySet()) {
                    this.multiServers.func_78849_a(new ServerData("- NATIONSGLORY " + server.getKey().toUpperCase(), server.getValue().getIp()));
                }
            }
        }
        int i = 0;
        while (i < this.multiServers.func_78856_c()) {
            final int finalI = i++;
            new Thread(new Runnable(){

                @Override
                public void run() {
                    ServerData serverData = MultiGUI.this.multiServers.func_78850_a(finalI);
                    try {
                        long var1 = System.nanoTime();
                        MultiGUI.getData(serverData);
                        long var2 = System.nanoTime();
                        serverData.field_78844_e = (var2 - var1) / 1000000L;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        this.lastServerRefresh = System.currentTimeMillis();
    }

    protected void func_73875_a(GuiButton button) {
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        ArrayList toolTipLines = new ArrayList();
        hoveredServer = "";
        hoveredUrl = "";
        this.hoveredServerF6 = -1;
        this.hoveredActionF6 = "";
        MainGUI.hoveredAction = "";
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
        if (mouseXScaled >= 72 && mouseXScaled <= 132 && mouseYScaled >= 600 && mouseYScaled <= 652) {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_HOME_HOVER);
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(LOGO_HOME);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(72.0f, 600.0f, 0, 0, 60, 52, 60.0f, 52.0f, true);
        if (Minecraft.func_71410_x().field_71439_g != null && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar")) {
            if (mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
                this.field_73882_e.func_110434_K().func_110577_a(MainGUI.LOGO_SOLO_HOVER);
            } else {
                this.field_73882_e.func_110434_K().func_110577_a(MainGUI.LOGO_SOLO);
            }
            ModernGui.drawModalRectWithCustomSizedTexture(74.0f, 840.0f, 0, 0, 52, 52, 52.0f, 52.0f, true);
        }
        this.field_73882_e.func_110434_K().func_110577_a(LOGO_MULTI_HOVER);
        ModernGui.drawModalRectWithCustomSizedTexture(60.0f, 720.0f, 0, 0, 80, 56, 80.0f, 56.0f, true);
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
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.servers") + " - " + MainGUI.serverLang.toUpperCase(), 2900.0f, 120.0f, 0xFFFFFF, 2.0f, "left", false, "georamaMedium", 37);
        int index = 0;
        for (GuiButton button : MainGUI.serversButtonList) {
            int offset = 148 * (index % 7);
            int offsetX = index / 7 * 350;
            String serverName = ((ModernServerButton)button).getServerName();
            String serverType = ((ModernServerButton)button).getServerType();
            String serverIp = ((ModernServerButton)button).getIp();
            String serverPort = ((ModernServerButton)button).getPort() + "";
            if (!((ModernServerButton)button).server.getZones().contains(MainGUI.serverLang) && !MainGUI.serverLang.contains("fr")) continue;
            if (SERVERS.containsKey(serverName)) {
                if (mouseXScaled >= 2900 + offsetX && mouseXScaled <= 2900 + offsetX + 116 && mouseYScaled >= 180 + offset && mouseYScaled <= 180 + offset + 118) {
                    this.field_73882_e.func_110434_K().func_110577_a(MainGUI.SERVERS_HOVER.get(serverName));
                    hoveredServer = serverType + "#" + serverName + "#" + serverIp + "#" + serverPort;
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(MainGUI.SERVERS.get(serverName));
                }
                ModernGui.drawModalRectWithCustomSizedTexture(2900 + offsetX, 180 + offset, 0, 0, 116, 118, 116.0f, 118.0f, false);
                ModernGui.drawScaledStringCustomFont(serverName.substring(0, 1).toUpperCase() + serverName.substring(1), 3034 + offsetX, 210 + offset, 0xFFFFFF, 2.0f, "left", false, "georamaSemiBold", 45);
            }
            ++index;
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.my_servers") + " (" + this.multiServers.func_78856_c() + ")", 352.0f, 120.0f, 12369613, 2.0f, "left", false, "georamaMedium", 37);
        this.field_73882_e.func_110434_K().func_110577_a(BOX_MULTI);
        ModernGui.drawModalRectWithCustomSizedTexture(352.0f, 180.0f, 0, 0, 2284, 1756, 2284.0f, 1756.0f, false);
        if (this.multiServers.func_78856_c() > 0) {
            GUIUtils.startGLScissor((int)(414.0f * ((float)this.field_73880_f / 3840.0f)), (int)(336.0f * ((float)windowHeight / 2160.0f)), (int)(2072.0f * ((float)this.field_73880_f / 3840.0f)), (int)(1600.0f * ((float)windowHeight / 2160.0f)));
            for (int i = 0; i < this.multiServers.func_78856_c(); ++i) {
                ServerData serverData = this.multiServers.func_78850_a(i);
                Float offsetY = Float.valueOf((float)(366 + 278 * i) + this.getSlide());
                if (this.openedTooltip.isEmpty() && mouseXScaled >= 414 && mouseXScaled <= 2468 && (float)mouseYScaled >= offsetY.floatValue() && (float)mouseYScaled <= offsetY.floatValue() + 232.0f && mouseXScaled >= 414 && mouseXScaled <= 2468 && mouseYScaled >= 336 && mouseYScaled <= 1936) {
                    this.hoveredServerF6 = i;
                    this.field_73882_e.func_110434_K().func_110577_a(BOX_SERVER_HOVER);
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(BOX_SERVER);
                }
                ModernGui.drawModalRectWithCustomSizedTexture(414.0f, offsetY.intValue(), 0, 0, 2054, 232, 2054.0f, 232.0f, false);
                ModernGui.drawScaledStringCustomFont(serverData.field_78847_a.toUpperCase(), 469.0f, offsetY.intValue() + 40, this.hoveredServerF6 == i ? 0xFFFFFF : 0xC4C4C4, 2.0f, "left", false, "georamaSemiBold", 55);
                ModernGui.drawScaledStringCustomFont(serverData.field_78846_c != null ? serverData.field_78846_c.replaceAll("\u00a77", "").replaceAll("\u00a78", "") : "-/100", 2418.0f, offsetY.intValue() + 40, this.hoveredServerF6 == i ? 0xFFFFFF : 0xC4C4C4, 2.0f, "right", false, "georamaSemiBold", 50);
                if (!serverData.field_78845_b.contains("nationsglory.fr")) {
                    ModernGui.drawScaledStringCustomFont(serverData.field_78843_d != null ? serverData.field_78843_d.toUpperCase() : (this.lastServerRefresh != 0L && System.currentTimeMillis() - this.lastServerRefresh > 3000L ? I18n.func_135053_a((String)"multi.unreachable") : I18n.func_135053_a((String)"multi.loading")), 469.0f, offsetY.intValue() + 110, 0xC4C4C4, 2.0f, "left", false, "georamaRegular", 40);
                }
                if (!serverData.field_78845_b.contains("nationsglory.fr")) {
                    if (serverData.field_78844_e > 0L && serverData.field_78844_e <= 150L) {
                        this.field_73882_e.func_110434_K().func_110577_a(PING_GOOD);
                    } else if (serverData.field_78844_e > 150L && serverData.field_78844_e <= 300L) {
                        this.field_73882_e.func_110434_K().func_110577_a(PING_MEDIUM);
                    } else {
                        this.field_73882_e.func_110434_K().func_110577_a(PING_BAD);
                    }
                    ModernGui.drawModalRectWithCustomSizedTexture(2434.0f, offsetY.intValue() - 20, 0, 0, 52, 52, 52.0f, 52.0f, true);
                }
                this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
                if (this.hoveredServerF6 != i) continue;
                if (!serverData.field_78845_b.contains("nationsglory.fr") || serverData.field_78845_b.contains("event.nationsglory.fr") || serverData.field_78845_b.contains("beta.nationsglory.fr") || serverData.field_78845_b.contains("dev.nationsglory.fr")) {
                    if (mouseXScaled >= 2310 && mouseXScaled <= 2352 && mouseYScaled >= offsetY.intValue() + 160 && mouseYScaled <= offsetY.intValue() + 160 + 42) {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_EDIT_HOVER);
                        this.hoveredActionF6 = "edit";
                    } else {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_EDIT);
                    }
                    ModernGui.drawModalRectWithCustomSizedTexture(2310.0f, offsetY.intValue() + 160, 0, 0, 42, 42, 42.0f, 42.0f, true);
                    if (mouseXScaled >= 2380 && mouseXScaled <= 2422 && mouseYScaled >= offsetY.intValue() + 160 && mouseYScaled <= offsetY.intValue() + 160 + 42) {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_REMOVE_HOVER);
                        this.hoveredActionF6 = "remove";
                    } else {
                        this.field_73882_e.func_110434_K().func_110577_a(BUTTON_REMOVE);
                    }
                    ModernGui.drawModalRectWithCustomSizedTexture(2380.0f, offsetY.intValue() + 160, 0, 0, 42, 42, 42.0f, 42.0f, true);
                }
                if (mouseXScaled >= 1900 && mouseXScaled <= 2142 && mouseYScaled >= offsetY.intValue() + 80 && mouseYScaled <= offsetY.intValue() + 80 + 78) {
                    this.field_73882_e.func_110434_K().func_110577_a(MainGUI.BUTTON_BOX_HOVER);
                    ModernGui.drawModalRectWithCustomSizedTexture(1856.0f, offsetY.intValue() + 80 - 44, 0, 0, 330, 166, 330.0f, 166.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.play"), 2020.0f, offsetY.intValue() + 100, 0, 2.0f, "center", false, "georamaSemiBold", 35);
                    this.hoveredActionF6 = "play";
                    continue;
                }
                this.field_73882_e.func_110434_K().func_110577_a(MainGUI.BUTTON_BOX);
                ModernGui.drawModalRectWithCustomSizedTexture(1900.0f, offsetY.intValue() + 80, 0, 0, 242, 78, 242.0f, 78.0f, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"main.play"), 2021.0f, offsetY.intValue() + 100, 0xFFFFFF, 2.0f, "center", false, "georamaSemiBold", 35);
            }
            GUIUtils.endGLScissor();
        } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.empty_servers"), 1492.0f, 400.0f, 0xC4C4C4, 2.0f, "center", false, "georamaSemiBold", 39);
        }
        this.field_73882_e.func_110434_K().func_110577_a(BOX_MULTI_SHADOW_BOTTOM);
        ModernGui.drawModalRectWithCustomSizedTexture(352.0f, 180.0f, 0, 0, 2284, 1756, 2284.0f, 1756.0f, false);
        if (this.openedTooltip.isEmpty() && mouseXScaled >= 414 && mouseXScaled <= 852 && mouseYScaled >= 230 && mouseYScaled <= 310) {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_CONNECTION_HOVER);
            ModernGui.drawModalRectWithCustomSizedTexture(370.0f, 186.0f, 0, 0, 526, 168, 526.0f, 168.0f, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.quick_connect"), 633.0f, 252.0f, 1513527, 2.0f, "center", false, "georamaMedium", 37);
            this.hoveredActionF6 = "directConnect";
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_CONNECTION);
            ModernGui.drawModalRectWithCustomSizedTexture(414.0f, 230.0f, 0, 0, 438, 80, 438.0f, 80.0f, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.quick_connect"), 633.0f, 252.0f, 0xFFFFFF, 2.0f, "center", false, "georamaMedium", 37);
        }
        if (this.openedTooltip.isEmpty() && mouseXScaled >= 882 && mouseXScaled <= 962 && mouseYScaled >= 230 && mouseYScaled <= 310) {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_ADD_HOVER);
            this.hoveredActionF6 = "add";
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_ADD);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(882.0f, 230.0f, 0, 0, 80, 80, 80.0f, 80.0f, true);
        if (this.openedTooltip.isEmpty() && mouseXScaled >= 992 && mouseXScaled <= 1072 && mouseYScaled >= 230 && mouseYScaled <= 310) {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_RELOAD_HOVER);
            this.hoveredActionF6 = "reload";
        } else {
            this.field_73882_e.func_110434_K().func_110577_a(BUTTON_RELOAD);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(992.0f, 230.0f, 0, 0, 80, 80, 80.0f, 80.0f, true);
        this.field_73882_e.func_110434_K().func_110577_a(SCROLLBAR);
        ModernGui.drawModalRectWithCustomSizedTexture(2550.0f, 366.0f, 0, 0, 24, 1478, 24.0f, 1478.0f, true);
        this.scrollBar.draw(mouseXScaled, mouseYScaled);
        if (!this.openedTooltip.isEmpty()) {
            this.field_73882_e.func_110434_K().func_110577_a(TOOLTIP_OVERLAY);
            ModernGui.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 0, 0, 3840, 2160, 3840.0f, 2160.0f, false);
            if (this.openedTooltip.equals("add") || this.openedTooltip.equals("edit")) {
                this.field_73882_e.func_110434_K().func_110577_a(TOOLTIP_SMALL);
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1424.0f, 542.0f, 0, 0, 992, 836, 992.0f, 836.0f, false);
                ModernGui.drawScaledStringCustomFont(this.openedTooltip.equals("add") ? I18n.func_135053_a((String)"multi.add_server") : I18n.func_135053_a((String)"multi.edit_server"), 1920.0f, 690.0f, 0xFFFFFF, 2.0f, "center", false, "georamaBold", 50);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.serverName"), 1634.0f, 800.0f, 0xFFFFFF, 2.0f, "left", false, "georamaMedium", 35);
                this.field_73882_e.func_110434_K().func_110577_a(INPUT);
                ModernGui.drawModalRectWithCustomSizedTexture(1634.0f, 845.0f, 0, 0, 572, 84, 572.0f, 84.0f, false);
                GL11.glScaled((double)(3840.0f / (float)this.field_73880_f), (double)(2160.0f / (float)windowHeight), (double)1.0);
                this.inputAdd_serverName.func_73795_f();
                GL11.glScaled((double)((float)this.field_73880_f / 3840.0f), (double)((float)windowHeight / 2160.0f), (double)1.0);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.serverAddress"), 1634.0f, 970.0f, 0xFFFFFF, 2.0f, "left", false, "georamaMedium", 35);
                this.field_73882_e.func_110434_K().func_110577_a(INPUT);
                ModernGui.drawModalRectWithCustomSizedTexture(1634.0f, 1015.0f, 0, 0, 572, 84, 572.0f, 84.0f, false);
                GL11.glScaled((double)(3840.0f / (float)this.field_73880_f), (double)(2160.0f / (float)windowHeight), (double)1.0);
                this.inputAdd_serverAdress.func_73795_f();
                GL11.glScaled((double)((float)this.field_73880_f / 3840.0f), (double)((float)windowHeight / 2160.0f), (double)1.0);
                if (mouseXScaled >= 1785 && mouseXScaled <= 2055 && mouseYScaled >= 1160 && mouseYScaled <= 1240) {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_HOVER);
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1741.0f, 1116.0f, 0, 0, 358, 168, 358.0f, 168.0f, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.valid"), 1920.0f, 1183.0f, 0xFFFFFF, 2.0f, "center", false, "georamaSemiBold", 35);
                    this.hoveredActionF6 = this.openedTooltip.equals("add") ? "add_new_server" : "edit_server";
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_WHITE);
                    ModernGui.drawModalRectWithCustomSizedTexture(1785.0f, 1160.0f, 0, 0, 270, 80, 270.0f, 80.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.valid"), 1920.0f, 1183.0f, 1579579, 2.0f, "center", false, "georamaSemiBold", 35);
                }
            } else if (this.openedTooltip.equals("warning")) {
                this.field_73882_e.func_110434_K().func_110577_a(TOOLTIP_SMALL);
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1424.0f, 542.0f, 0, 0, 992, 836, 992.0f, 836.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.title"), 1920.0f, 720.0f, 0xEE6E6E, 2.0f, "center", false, "georamaBold", 50);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.text1"), 1920.0f, 840.0f, 0xFFFFFF, 2.0f, "center", false, "georamaSemiBold", 45);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.text2"), 1920.0f, 890.0f, 0xEE6E6E, 2.0f, "center", false, "georamaSemiBold", 45);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.text3"), 1920.0f, 940.0f, 0xEE6E6E, 2.0f, "center", false, "georamaSemiBold", 45);
                if (this.checkboxWarning_dontAsk) {
                    this.field_73882_e.func_110434_K().func_110577_a(CHECKBOX_CHECKED);
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(CHECKBOX_NOT_CHECKED);
                }
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1620.0f, 1042.0f, 0, 0, 58, 48, 58.0f, 48.0f, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.checkbox"), 1695.0f, 1050.0f, 0x656886, 2.0f, "left", false, "georamaMedium", 35);
                if (mouseXScaled >= 1620 && mouseXScaled <= 1668 && mouseYScaled >= 1042 && mouseYScaled <= 1087) {
                    this.hoveredActionF6 = "checkbox_dontAsk";
                }
                if (mouseXScaled >= 1650 && mouseXScaled <= 1920 && mouseYScaled >= 1158 && mouseYScaled <= 1238) {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_HOVER);
                    ModernGui.drawModalRectWithCustomSizedTexture(1606.0f, 1114.0f, 0, 0, 358, 168, 358.0f, 168.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.continue"), 1785.0f, 1180.0f, 1711166, 2.0f, "center", false, "georamaSemiBold", 35);
                    this.hoveredActionF6 = "play_valid_warning";
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_WHITE);
                    ModernGui.drawModalRectWithCustomSizedTexture(1650.0f, 1158.0f, 0, 0, 270, 80, 270.0f, 80.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.continue"), 1785.0f, 1180.0f, 1711166, 2.0f, "center", false, "georamaSemiBold", 35);
                }
                if (mouseXScaled >= 1936 && mouseXScaled <= 2206 && mouseYScaled >= 1158 && mouseYScaled <= 1238) {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_HOVER);
                    ModernGui.drawModalRectWithCustomSizedTexture(1892.0f, 1114.0f, 0, 0, 358, 168, 358.0f, 168.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.cancel"), 2071.0f, 1180.0f, 1711166, 2.0f, "center", false, "georamaSemiBold", 35);
                    this.hoveredActionF6 = "close_tooltip";
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_BLUE);
                    ModernGui.drawModalRectWithCustomSizedTexture(1936.0f, 1158.0f, 0, 0, 270, 80, 270.0f, 80.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.warning.cancel"), 2071.0f, 1180.0f, 0xFFFFFF, 2.0f, "center", false, "georamaSemiBold", 35);
                }
            } else if (this.openedTooltip.equals("directConnect")) {
                this.field_73882_e.func_110434_K().func_110577_a(TOOLTIP_SMALL);
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1424.0f, 542.0f, 0, 0, 992, 836, 992.0f, 836.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.quick_connect"), 1920.0f, 690.0f, 0xFFFFFF, 2.0f, "center", false, "georamaBold", 50);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.serverAddress"), 1634.0f, 870.0f, 0xFFFFFF, 2.0f, "left", false, "georamaMedium", 35);
                this.field_73882_e.func_110434_K().func_110577_a(INPUT);
                ModernGui.drawModalRectWithCustomSizedTexture(1634.0f, 915.0f, 0, 0, 572, 84, 572.0f, 84.0f, false);
                GL11.glScaled((double)(3840.0f / (float)this.field_73880_f), (double)(2160.0f / (float)windowHeight), (double)1.0);
                this.inputConnect_serverAdress.func_73795_f();
                GL11.glScaled((double)((float)this.field_73880_f / 3840.0f), (double)((float)windowHeight / 2160.0f), (double)1.0);
                if (mouseXScaled >= 1785 && mouseXScaled <= 2055 && mouseYScaled >= 1160 && mouseYScaled <= 1240) {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_HOVER);
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(1741.0f, 1116.0f, 0, 0, 358, 168, 358.0f, 168.0f, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.valid"), 1920.0f, 1183.0f, 1579579, 2.0f, "center", false, "georamaBold", 35);
                    this.hoveredActionF6 = "play_direct";
                } else {
                    this.field_73882_e.func_110434_K().func_110577_a(BUTTON_WHITE);
                    ModernGui.drawModalRectWithCustomSizedTexture(1785.0f, 1160.0f, 0, 0, 270, 80, 270.0f, 80.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"multi.valid"), 1920.0f, 1183.0f, 1579579, 2.0f, "center", false, "georamaSemiBold", 35);
                }
            }
            if (mouseXScaled >= 2310 && mouseXScaled <= 2342 && mouseYScaled >= 620 && mouseYScaled <= 652) {
                this.field_73882_e.func_110434_K().func_110577_a(CROSS_BLUE);
                this.hoveredActionF6 = "close_tooltip";
            } else {
                this.field_73882_e.func_110434_K().func_110577_a(CROSS_WHITE);
            }
            ModernGui.drawModalRectWithCustomSizedTexture(2310.0f, 620.0f, 0, 0, 32, 32, 32.0f, 32.0f, true);
        }
        if (!toolTipLines.isEmpty()) {
            this.drawHoveringText(toolTipLines, mouseXScaled, mouseYScaled, this.field_73886_k);
        }
        MainGUI.drawWaitingQueueOverlay(mouseXScaled, mouseYScaled);
        GL11.glPopMatrix();
    }

    public void openURL(String url) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private float getSlide() {
        return this.multiServers.func_78856_c() > 5 ? (float)(-(this.multiServers.func_78856_c() - 5) * 278) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        int windowWidth = this.field_73880_f;
        int windowHeight = this.field_73880_f * 9 / 16;
        int mouseXScaled = (int)((float)par1 * (3840.0f / (float)this.field_73880_f));
        int mouseYScaled = (int)((float)par2 * (2160.0f / (float)windowHeight));
        if (this.openedTooltip.isEmpty()) {
            if (mouseXScaled >= 72 && mouseXScaled <= 124 && mouseYScaled >= 600 && mouseYScaled <= 652) {
                this.field_73882_e.func_71373_a((GuiScreen)new MainGUI());
            }
            if (mouseXScaled >= 74 && mouseXScaled <= 126 && mouseYScaled >= 840 && mouseYScaled <= 892) {
                if (Minecraft.func_71410_x().field_71439_g != null && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("wascar")) {
                    this.field_73882_e.func_71373_a((GuiScreen)new GuiSelectWorld((GuiScreen)this));
                }
            } else if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1280 && mouseYScaled <= 1344) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new GuiBrowser("https://wiki.nationsglory.fr/fr/"));
            } else if (mouseXScaled >= 66 && mouseXScaled <= 130 && mouseYScaled >= 1400 && mouseYScaled <= 1464) {
                this.openURL(MainGUI.serverLang.equals("fr") ? "https://nationsglory.fr" : "https://nationsglory.com");
            } else if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1520 && mouseYScaled <= 1586) {
                this.openURL(MainGUI.serverLang.equals("fr") ? "https://discord.gg/nationsglory" : "https://discord.gg/yEZs99KZ");
            } else if (mouseXScaled >= 70 && mouseXScaled <= 128 && mouseYScaled >= 1640 && mouseYScaled <= 1698) {
                this.openURL(MainGUI.serverLang.equals("fr") ? "ts3server://ts.nationsglory.fr" : "ts3server://ts.nationsglory.com");
            } else if (mouseXScaled >= 70 && mouseXScaled <= 130 && mouseYScaled >= 1760 && mouseYScaled <= 1820) {
                this.field_73882_e.func_71373_a((GuiScreen)new GuiOptions((GuiScreen)this, this.field_73882_e.field_71474_y));
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
            } else if (this.hoveredActionF6.equalsIgnoreCase("remove") && this.hoveredServerF6 != -1) {
                this.multiServers.func_78851_b(this.hoveredServerF6);
                this.saveServerList();
            } else if (this.hoveredActionF6.equalsIgnoreCase("add")) {
                this.openedTooltip = "add";
            } else if (this.hoveredActionF6.equals("directConnect")) {
                if (ClientProxy.clientConfig.directConnectMultiAdress != null && !ClientProxy.clientConfig.directConnectMultiAdress.isEmpty()) {
                    this.inputConnect_serverAdress.func_73782_a(ClientProxy.clientConfig.directConnectMultiAdress);
                }
                this.openedTooltip = "directConnect";
            } else if (this.hoveredActionF6.equalsIgnoreCase("edit") && this.hoveredServerF6 != -1) {
                this.openedTooltip = "edit";
                this.editedServerData = this.multiServers.func_78850_a(this.hoveredServerF6);
                this.inputAdd_serverName.func_73782_a(this.editedServerData.field_78847_a);
                this.inputAdd_serverAdress.func_73782_a(this.editedServerData.field_78845_b);
            } else if (this.hoveredActionF6.equalsIgnoreCase("play") && this.hoveredServerF6 != -1) {
                if (this.multiServers.func_78850_a((int)this.hoveredServerF6).field_78845_b.contains("localhost") || ClientProxy.getServerNameByIpAndPort(this.multiServers.func_78850_a((int)this.hoveredServerF6).field_78845_b) != null) {
                    this.connectToServer(this.multiServers.func_78850_a(this.hoveredServerF6));
                } else {
                    List<String> trustedServers;
                    this.warnedServerData = this.multiServers.func_78850_a(this.hoveredServerF6);
                    if (ClientProxy.clientConfig.trustedMultiServers != null && !ClientProxy.clientConfig.trustedMultiServers.isEmpty() && (trustedServers = Arrays.asList(ClientProxy.clientConfig.trustedMultiServers.split("#"))).contains(this.warnedServerData.field_78845_b)) {
                        this.connectToServer(this.multiServers.func_78850_a(this.hoveredServerF6));
                        return;
                    }
                    this.checkboxWarning_dontAsk = false;
                    this.openedTooltip = "warning";
                }
            } else if (this.hoveredActionF6.equalsIgnoreCase("reload")) {
                for (int i = 0; i < this.multiServers.func_78856_c(); ++i) {
                    final int finalI = i;
                    this.multiServers.func_78850_a((int)i).field_78843_d = null;
                    this.multiServers.func_78850_a((int)i).field_78846_c = null;
                    new Thread(new Runnable(){

                        @Override
                        public void run() {
                            ServerData serverData = MultiGUI.this.multiServers.func_78850_a(finalI);
                            try {
                                long var1 = System.nanoTime();
                                MultiGUI.getData(serverData);
                                long var2 = System.nanoTime();
                                serverData.field_78844_e = (var2 - var1) / 1000000L;
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                this.lastServerRefresh = System.currentTimeMillis();
            } else if (!hoveredUrl.isEmpty()) {
                this.openURL(hoveredUrl);
            } else if (MainGUI.hoveredAction.equalsIgnoreCase("quit_waiting")) {
                ClientSocket.out.println("MESSAGE socket REMOVE_WAITINGLIST");
                ClientData.waitingServerName = null;
            }
        } else if (this.hoveredActionF6.equals("close_tooltip")) {
            this.openedTooltip = "";
            this.inputAdd_serverName.func_73782_a("");
            this.inputAdd_serverAdress.func_73782_a("");
        } else if (this.hoveredActionF6.equals("checkbox_dontAsk")) {
            this.checkboxWarning_dontAsk = !this.checkboxWarning_dontAsk;
        } else if (this.hoveredActionF6.equalsIgnoreCase("play_direct") && !this.inputConnect_serverAdress.func_73781_b().isEmpty()) {
            ServerData serverData = new ServerData("DirectConnect", this.inputConnect_serverAdress.func_73781_b());
            this.openedTooltip = "";
            ClientProxy.clientConfig.directConnectMultiAdress = this.inputConnect_serverAdress.func_73781_b();
            this.connectToServer(serverData);
        } else if (this.hoveredActionF6.equalsIgnoreCase("play_valid_warning") && this.warnedServerData != null) {
            this.openedTooltip = "";
            this.connectToServer(this.warnedServerData);
            if (this.checkboxWarning_dontAsk) {
                ArrayList<String> trustedServers = new ArrayList<String>();
                if (ClientProxy.clientConfig.trustedMultiServers != null && !ClientProxy.clientConfig.trustedMultiServers.isEmpty()) {
                    trustedServers = new ArrayList<String>(Arrays.asList(ClientProxy.clientConfig.trustedMultiServers.split("#")));
                }
                if (!trustedServers.contains(this.warnedServerData.field_78845_b)) {
                    trustedServers.add(this.warnedServerData.field_78845_b);
                }
                String newTrustedServers = "";
                for (String trustedServer : trustedServers) {
                    newTrustedServers = newTrustedServers + trustedServer + "#";
                }
                ClientProxy.clientConfig.trustedMultiServers = newTrustedServers = newTrustedServers.replaceAll("#$", "");
            }
        } else if (this.hoveredActionF6.equals("add_new_server")) {
            if (!this.inputAdd_serverName.func_73781_b().isEmpty() && !this.inputAdd_serverAdress.func_73781_b().isEmpty()) {
                final ServerData serverData = new ServerData(this.inputAdd_serverName.func_73781_b(), this.inputAdd_serverAdress.func_73781_b());
                this.multiServers.func_78849_a(serverData);
                this.saveServerList();
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            long var1 = System.nanoTime();
                            MultiGUI.getData(serverData);
                            long var2 = System.nanoTime();
                            serverData.field_78844_e = (var2 - var1) / 1000000L;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                this.openedTooltip = "";
                this.inputAdd_serverName.func_73782_a("");
                this.inputAdd_serverAdress.func_73782_a("");
            }
        } else if (this.hoveredActionF6.equals("edit_server") && !this.inputAdd_serverName.func_73781_b().isEmpty() && !this.inputAdd_serverAdress.func_73781_b().isEmpty()) {
            final ServerData serverDataFinal = this.editedServerData;
            serverDataFinal.field_78847_a = this.inputAdd_serverName.func_73781_b();
            serverDataFinal.field_78845_b = this.inputAdd_serverAdress.func_73781_b();
            serverDataFinal.field_78846_c = null;
            serverDataFinal.field_78843_d = null;
            this.saveServerList();
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        long var1 = System.nanoTime();
                        MultiGUI.getData(serverDataFinal);
                        long var2 = System.nanoTime();
                        serverDataFinal.field_78844_e = (var2 - var1) / 1000000L;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            this.openedTooltip = "";
            this.inputAdd_serverName.func_73782_a("");
            this.inputAdd_serverAdress.func_73782_a("");
        }
        this.inputAdd_serverName.func_73793_a(par1, par2, par3);
        this.inputAdd_serverAdress.func_73793_a(par1, par2, par3);
        this.inputConnect_serverAdress.func_73793_a(par1, par2, par3);
        super.func_73864_a(par1, par2, par3);
    }

    private void connectToServer(ServerData var1) {
        if (var1.field_78845_b.contains("uniscube") || var1.field_78845_b.contains("146.19.162.161")) {
            Minecraft.func_71410_x().func_71404_a(new CrashReport("", (Throwable)new Exception("")));
            return;
        }
        String serverNameFromIpAndPort = ClientProxy.getServerNameByIpAndPort(var1.field_78845_b);
        if (serverNameFromIpAndPort != null && ClientSocket.out != null) {
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverNameFromIpAndPort.toLowerCase());
            ClientData.waitingJoinTime = System.currentTimeMillis();
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingSocketGui());
            return;
        }
        this.field_73882_e.func_71373_a((GuiScreen)new GuiConnecting((GuiScreen)this, this.field_73882_e, var1));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void getData(ServerData p_74017_0_) throws IOException {
        block25: {
            ServerAddress var1 = ServerAddress.func_78860_a((String)p_74017_0_.field_78845_b);
            Socket var2 = null;
            FilterInputStream var3 = null;
            FilterOutputStream var4 = null;
            try {
                int var9;
                var2 = new Socket();
                var2.setSoTimeout(3000);
                var2.setTcpNoDelay(true);
                var2.setTrafficClass(18);
                var2.connect(new InetSocketAddress(var1.func_78861_a(), var1.func_78864_b()), 3000);
                var3 = new DataInputStream(var2.getInputStream());
                var4 = new DataOutputStream(var2.getOutputStream());
                Packet254ServerPing var5 = new Packet254ServerPing(78, var1.func_78861_a(), var1.func_78864_b());
                ((DataOutputStream)var4).writeByte(var5.func_73281_k());
                var5.func_73273_a((DataOutput)((Object)var4));
                if (var3.read() != 255) {
                    throw new IOException("Bad message");
                }
                String var6 = Packet.func_73282_a((DataInput)((Object)var3), (int)256);
                char[] var7 = var6.toCharArray();
                for (int var8 = 0; var8 < var7.length; ++var8) {
                    if (var7[var8] == '\u00a7' || var7[var8] == '\u0000' || ChatAllowedCharacters.field_71568_a.indexOf(var7[var8]) >= 0) continue;
                    var7[var8] = 63;
                }
                var6 = new String(var7);
                if (var6.startsWith("\u00a7") && var6.length() > 1) {
                    String[] var27 = var6.substring(1).split("\u0000");
                    if (MathHelper.func_82715_a((String)var27[0], (int)0) == 1) {
                        p_74017_0_.field_78843_d = var27[3];
                        p_74017_0_.field_82821_f = MathHelper.func_82715_a((String)var27[1], (int)p_74017_0_.field_82821_f);
                        p_74017_0_.field_82822_g = var27[2];
                        var9 = MathHelper.func_82715_a((String)var27[4], (int)0);
                        int var10 = MathHelper.func_82715_a((String)var27[5], (int)0);
                        p_74017_0_.field_78846_c = var9 >= 0 && var10 >= 0 ? EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10 : "" + EnumChatFormatting.DARK_GRAY + "???";
                    } else {
                        p_74017_0_.field_82822_g = "???";
                        p_74017_0_.field_78843_d = "" + EnumChatFormatting.DARK_GRAY + "???";
                        p_74017_0_.field_82821_f = 79;
                        p_74017_0_.field_78846_c = "" + EnumChatFormatting.DARK_GRAY + "???";
                    }
                    break block25;
                }
                String[] var27 = var6.split("\u00a7");
                var6 = var27[0];
                var9 = -1;
                int var10 = -1;
                try {
                    var9 = Integer.parseInt(var27[1]);
                    var10 = Integer.parseInt(var27[2]);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                p_74017_0_.field_78843_d = EnumChatFormatting.GRAY + var6;
                p_74017_0_.field_78846_c = var9 >= 0 && var10 > 0 ? EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10 : "" + EnumChatFormatting.DARK_GRAY + "???";
                p_74017_0_.field_82822_g = "1.3";
                p_74017_0_.field_82821_f = 77;
            }
            finally {
                try {
                    if (var3 != null) {
                        var3.close();
                    }
                }
                catch (Throwable throwable) {}
                try {
                    if (var4 != null) {
                        var4.close();
                    }
                }
                catch (Throwable throwable) {}
                try {
                    if (var2 != null) {
                        var2.close();
                    }
                }
                catch (Throwable throwable) {}
            }
        }
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void saveServerList() {
        try {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.multiServers.func_78856_c(); ++i) {
                ServerData serverdata = this.multiServers.func_78850_a(i);
                if (serverdata.field_78847_a.startsWith("- NATIONSGLORY")) continue;
                nbttaglist.func_74742_a((NBTBase)serverdata.func_78836_a());
            }
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74782_a("servers", (NBTBase)nbttaglist);
            CompressedStreamTools.func_74793_a((NBTTagCompound)nbttagcompound, (File)new File(this.field_73882_e.field_71412_D, "servers.dat"));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

