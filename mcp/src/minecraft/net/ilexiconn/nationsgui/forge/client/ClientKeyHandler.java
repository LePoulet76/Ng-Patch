/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.stats.StatList
 *  org.lwjgl.input.Mouse
 */
package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.data.ClientConfig;
import net.ilexiconn.nationsgui.forge.client.gui.BonusesGui;
import net.ilexiconn.nationsgui.forge.client.gui.GuiDebugSkin;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.NoelMegaGiftGui;
import net.ilexiconn.nationsgui.forge.client.gui.NotificationActionsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.override.DialogOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.EmoteGui;
import net.ilexiconn.nationsgui.forge.client.gui.trade.ITrade;
import net.ilexiconn.nationsgui.forge.server.block.PortalBlock;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DialogExecPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandPortalTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenCatalogPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SellItemCheckPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.stats.StatList;
import org.lwjgl.input.Mouse;

@SideOnly(value=Side.CLIENT)
public class ClientKeyHandler
extends KeyBindingRegistry.KeyHandler {
    public static final KeyBinding KEY_MULTIPLAYER = new KeyBinding("Multiplayer", 64);
    public static final KeyBinding KEY_CATALOG = new KeyBinding("Catalogue", 46);
    public static final KeyBinding KEY_SPRINT = new KeyBinding("Sprint", 29);
    public static final KeyBinding KEY_SPECIAL_INFO = new KeyBinding("InfoBox", 22);
    public static final KeyBinding KEY_WARZONE_LEAVE = new KeyBinding("Quitter Warzone", 24);
    public static final KeyBinding KEY_BONUS = new KeyBinding("Afficher les bonus", 48);
    public static final KeyBinding KEY_WAITING = new KeyBinding("G\u00e9rer la file d'attente", 36);
    public static final KeyBinding KEY_PORTAL_TP = new KeyBinding("T\u00e9l\u00e9portation portail", 25);
    public static final KeyBinding KEY_SELL = new KeyBinding("nationsgui.shop.key", 37);
    public static final KeyBinding KEY_TOGGLE_SPRINT = new KeyBinding("Toggle Sprint", 35);
    public static final KeyBinding KEY_BLOCKINFO = new KeyBinding("Block Info", 82);
    public static final KeyBinding KEY_CHUNCK = new KeyBinding("Display Chunck Limit", 67);
    public static final KeyBinding KEY_MOB_SPAWN = new KeyBinding("Display Mob Spawning Zones", 65);
    public static final KeyBinding KEY_REFRESH_HATS = new KeyBinding("Refresh Hats Rendering", 209);
    public static final KeyBinding KEY_EMOTES = new KeyBinding("Emotes Menu", 48);
    public static final KeyBinding KEY_HIDE_GUI = new KeyBinding("Hide GUI", 59);
    public static final KeyBinding KEY_SCREENSHOT = new KeyBinding("Screenshot", 60);
    public static final KeyBinding KEY_DEBUG = new KeyBinding("Debug", 61);
    public static final KeyBinding KEY_THIRD_PERSON = new KeyBinding("Third Person Switch", 63);
    public static final KeyBinding KEY_SMOOTH_CAMERA = new KeyBinding("Smooth Camera", 66);
    public static final KeyBinding KEY_FULLSCREEN = new KeyBinding("Full Screen", 87);
    public static final KeyBinding KEY_OPEN_NOTIF = new KeyBinding("Open Notification / Next Dialog", 28);
    public static final KeyBinding KEY_TOGGLE_3D_SKINS = new KeyBinding("Toggle Comestics", 17);
    public static final KeyBinding KEY_TEST = new KeyBinding("Test", 207);
    public static boolean toggleSprintEnabled = false;
    public static boolean displayChunck = false;
    public static boolean displayMobSpawning = false;
    public static Long lastHotbarKeyPress = 0L;
    private static LinkedHashMap<KeyBinding, Boolean> keyBindingBooleanHashMap = new LinkedHashMap();

    public ClientKeyHandler() {
        super(keyBindingBooleanHashMap.keySet().toArray(new KeyBinding[0]), ClientKeyHandler.toPrimitiveArray(keyBindingBooleanHashMap.values().toArray(new Boolean[0])));
    }

    private static boolean[] toPrimitiveArray(Boolean[] booleanList) {
        boolean[] primitives = new boolean[booleanList.length];
        int index = 0;
        for (Boolean object : booleanList) {
            primitives[index++] = object;
        }
        return primitives;
    }

    public void keyDown(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd, boolean isRepeat) {
        if (!tickEnd) {
            if (!(keyBinding != KEY_MULTIPLAYER || keyBinding.field_74512_d == -100 || keyBinding.field_74512_d == -99 || Minecraft.func_71410_x().field_71462_r != null && Minecraft.func_71410_x().field_71462_r instanceof ITrade)) {
                ClientData.lastPlayerWantDisconnect = System.currentTimeMillis();
                if (Minecraft.func_71410_x().field_71441_e != null) {
                    Minecraft.func_71410_x().func_71373_a(null);
                    TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);
                    if (Minecraft.func_71410_x().field_71441_e != null) {
                        Minecraft.func_71410_x().field_71413_E.func_77450_a(StatList.field_75947_j, 1);
                        Minecraft.func_71410_x().field_71441_e.func_72882_A();
                        Minecraft.func_71410_x().func_71403_a(null);
                        for (SoundStreamer player : new ArrayList<SoundStreamer>(ClientProxy.STREAMER_LIST)) {
                            player.forceClose();
                        }
                        ClientProxy.STREAMER_LIST.clear();
                    }
                    ClientEventHandler.modsChecked = false;
                    if (!ClientProxy.currentServerName.contains("hub")) {
                        if (ClientSocket.out != null) {
                            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
                            ClientData.waitingJoinTime = System.currentTimeMillis();
                            Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingSocketGui());
                        }
                    } else {
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new MainGUI());
                    }
                } else {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new MainGUI());
                }
            } else if (keyBinding == KEY_CATALOG && Minecraft.func_71410_x().field_71462_r == null) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoteOpenCatalogPacket()));
            } else if (keyBinding == KEY_SPECIAL_INFO && Minecraft.func_71410_x().field_71462_r == null) {
                ClientConfig clientConfig = ClientProxy.clientConfig;
                clientConfig.specialEnabled = !clientConfig.specialEnabled;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyBinding == KEY_PORTAL_TP && Minecraft.func_71410_x().field_71462_r == null) {
                if (!(System.currentTimeMillis() - ClientProxy.lastCollidedWithPortalTime >= 100L || PortalBlock.lastTP.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && System.currentTimeMillis() - PortalBlock.lastTP.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) <= 5000L)) {
                    PortalBlock.lastTP.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, System.currentTimeMillis());
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandPortalTPPacket(ClientProxy.lastCollidedWithPortalX, ClientProxy.lastCollidedWithPortalY, ClientProxy.lastCollidedWithPortalZ, Minecraft.func_71410_x().field_71439_g.field_71092_bJ, Minecraft.func_71410_x().field_71439_g.field_70177_z)));
                }
            } else if (keyBinding == KEY_WARZONE_LEAVE && Minecraft.func_71410_x().field_71462_r == null && !this.isAnyMouseButtonDown()) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("spawn", 0)));
                if (ClientData.currentJumpStartTime != -1L) {
                    NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", -1L);
                    ClientData.currentJumpRecord = "";
                    ClientData.currentJumpStartTime = -1L;
                }
            } else if (keyBinding == KEY_BONUS && Minecraft.func_71410_x().field_71462_r == null && !this.isAnyMouseButtonDown()) {
                if (System.currentTimeMillis() > 1734166800000L && System.currentTimeMillis() < 1736722800000L) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new NoelMegaGiftGui());
                } else if (ClientData.bonusStartTime != 0L && System.currentTimeMillis() >= ClientData.bonusStartTime && System.currentTimeMillis() <= ClientData.bonusEndTime) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new BonusesGui());
                }
            } else if (keyBinding == KEY_WAITING && Minecraft.func_71410_x().field_71462_r == null && !this.isAnyMouseButtonDown() && ClientData.waitingServerName != null) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingModalGui("quit"));
            } else if (keyBinding == KEY_TOGGLE_SPRINT && Minecraft.func_71410_x().field_71462_r == null) {
                boolean bl = toggleSprintEnabled = !toggleSprintEnabled;
                if (!toggleSprintEnabled) {
                    Minecraft.func_71410_x().field_71439_g.func_70031_b(false);
                }
            } else if (keyBinding == KEY_BLOCKINFO) {
                ClientConfig clientConfig = ClientProxy.clientConfig;
                clientConfig.blockInfoEnabled = !clientConfig.blockInfoEnabled;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyBinding == KEY_CHUNCK) {
                boolean bl = displayChunck = !displayChunck;
                if (Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals("iBalix") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals("MisterSand") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals("Tymothi")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new GuiDebugSkin());
                }
            } else if (keyBinding == KEY_MOB_SPAWN) {
                displayMobSpawning = !displayMobSpawning;
            } else if (keyBinding == KEY_SELL) {
                if (Minecraft.func_71410_x().field_71441_e != null && Minecraft.func_71410_x().field_71462_r == null) {
                    InventoryPlayer inventoryPlayer = Minecraft.func_71410_x().field_71439_g.field_71071_by;
                    ItemStack itemStack = inventoryPlayer.field_70462_a[inventoryPlayer.field_70461_c];
                    if (itemStack != null) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SellItemCheckPacket()));
                    }
                }
            } else if (keyBinding == KEY_EMOTES && Minecraft.func_71410_x().field_71441_e != null) {
                if (Minecraft.func_71410_x().field_71462_r == null) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EmoteGui());
                } else if (Minecraft.func_71410_x().field_71462_r instanceof EmoteGui) {
                    Minecraft.func_71410_x().func_71373_a(null);
                }
            } else if (keyBinding.field_74515_c.contains("Hot Bar Slot")) {
                Minecraft minecraft = Minecraft.func_71410_x();
                if (minecraft.field_71441_e != null && minecraft.field_71462_r == null) {
                    Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70461_c = Integer.parseInt(keyBinding.field_74515_c.substring(keyBinding.field_74515_c.length() - 1)) - 1;
                }
                if (minecraft.field_71441_e != null && minecraft.field_71462_r != null && minecraft.field_71462_r instanceof GuiContainer && !(minecraft.field_71462_r instanceof InventoryGUI)) {
                    if (System.currentTimeMillis() - lastHotbarKeyPress < 250L) {
                        Minecraft.func_71410_x().func_71373_a(null);
                    }
                    lastHotbarKeyPress = System.currentTimeMillis();
                }
            } else if (keyBinding == KEY_REFRESH_HATS) {
                String pseudo = Minecraft.func_71410_x().func_110432_I().func_111285_a();
                List<String> pseudoList = Arrays.asList("iBalix", "GuiGeeK60", "GuiGeeK60_ulti", "Wascar");
                if (pseudoList.contains(pseudo)) {
                    Minecraft.func_71410_x().field_71439_g.func_71035_c("\u00a74[\u00a7cHat\u00a74]\u00a7r \u00a77Refreshing Skins...");
                }
            } else if (keyBinding == KEY_TOGGLE_3D_SKINS && Minecraft.func_71410_x().field_71462_r == null) {
                ClientProxy.clientConfig.render3DSkins = !ClientProxy.clientConfig.render3DSkins;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135052_a((String)"nationsgui.toggle3d", (Object[])new Object[]{ClientProxy.clientConfig.render3DSkins ? "ON" : "OFF"}));
            } else if (keyBinding == KEY_OPEN_NOTIF && Minecraft.func_71410_x().field_71462_r == null) {
                Notification notification;
                Iterator<Notification> iterator;
                if (!ClientData.dialogs.isEmpty()) {
                    if (DialogOverride.currentDialogLetterIndex == ((String)ClientData.dialogs.get(0).get("content")).length()) {
                        if (ClientData.dialogs.get(0).get("command") != null) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)ClientData.dialogs.get(0).get("identifier"))));
                        }
                        ClientData.dialogs.remove(0);
                        DialogOverride.resetDisplay();
                    } else {
                        DialogOverride.currentDialogLetterIndex = ((String)ClientData.dialogs.get(0).get("content")).length();
                    }
                }
                if ((iterator = ClientData.notifications.iterator()).hasNext() && !(notification = iterator.next()).isExpired() && ClientProxy.clientConfig.displayNotifications && notification.getCompound().func_74764_b("actions")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new NotificationActionsGUI(notification));
                }
            }
        }
    }

    public void keyUp(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd) {
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    public String getLabel() {
        return "nationsgui";
    }

    private boolean isAnyMouseButtonDown() {
        for (int i = 0; i < Mouse.getButtonCount(); ++i) {
            if (!Mouse.isButtonDown((int)i)) continue;
            return true;
        }
        return false;
    }

    static {
        keyBindingBooleanHashMap.put(KEY_MULTIPLAYER, false);
        keyBindingBooleanHashMap.put(KEY_CATALOG, false);
        keyBindingBooleanHashMap.put(KEY_SPRINT, false);
        keyBindingBooleanHashMap.put(KEY_SPECIAL_INFO, false);
        keyBindingBooleanHashMap.put(KEY_PORTAL_TP, false);
        keyBindingBooleanHashMap.put(KEY_WARZONE_LEAVE, false);
        keyBindingBooleanHashMap.put(KEY_BONUS, false);
        keyBindingBooleanHashMap.put(KEY_WAITING, false);
        keyBindingBooleanHashMap.put(KEY_TOGGLE_SPRINT, false);
        keyBindingBooleanHashMap.put(KEY_BLOCKINFO, false);
        keyBindingBooleanHashMap.put(KEY_CHUNCK, false);
        keyBindingBooleanHashMap.put(KEY_MOB_SPAWN, false);
        keyBindingBooleanHashMap.put(KEY_SELL, false);
        keyBindingBooleanHashMap.put(KEY_EMOTES, false);
        keyBindingBooleanHashMap.put(KEY_SCREENSHOT, false);
        keyBindingBooleanHashMap.put(KEY_DEBUG, false);
        keyBindingBooleanHashMap.put(KEY_HIDE_GUI, false);
        keyBindingBooleanHashMap.put(KEY_THIRD_PERSON, false);
        keyBindingBooleanHashMap.put(KEY_SMOOTH_CAMERA, false);
        keyBindingBooleanHashMap.put(KEY_FULLSCREEN, false);
        keyBindingBooleanHashMap.put(KEY_TOGGLE_3D_SKINS, false);
        keyBindingBooleanHashMap.put(KEY_OPEN_NOTIF, false);
        keyBindingBooleanHashMap.put(KEY_TEST, false);
        String pseudo = Minecraft.func_71410_x().func_110432_I().func_111285_a();
        if (pseudo.equalsIgnoreCase("iBalix") || pseudo.equalsIgnoreCase("GuiGeeK60") || pseudo.equalsIgnoreCase("GuiGeeK60_ulti")) {
            keyBindingBooleanHashMap.put(KEY_REFRESH_HATS, false);
        }
        for (int i = 0; i < 9; ++i) {
            keyBindingBooleanHashMap.put(new KeyBinding("Hot Bar Slot " + (i + 1), 2 + i), false);
        }
    }
}

