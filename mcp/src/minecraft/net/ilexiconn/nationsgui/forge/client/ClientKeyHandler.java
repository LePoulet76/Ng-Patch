package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
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
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class ClientKeyHandler extends KeyHandler
{
    public static final KeyBinding KEY_MULTIPLAYER = new KeyBinding("Multiplayer", 64);
    public static final KeyBinding KEY_CATALOG = new KeyBinding("Catalogue", 46);
    public static final KeyBinding KEY_SPRINT = new KeyBinding("Sprint", 29);
    public static final KeyBinding KEY_SPECIAL_INFO = new KeyBinding("InfoBox", 22);
    public static final KeyBinding KEY_WARZONE_LEAVE = new KeyBinding("Quitter Warzone", 24);
    public static final KeyBinding KEY_BONUS = new KeyBinding("Afficher les bonus", 48);
    public static final KeyBinding KEY_WAITING = new KeyBinding("G\u00e9rer la file d\'attente", 36);
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
    public static Long lastHotbarKeyPress = Long.valueOf(0L);
    private static LinkedHashMap<KeyBinding, Boolean> keyBindingBooleanHashMap = new LinkedHashMap();

    public ClientKeyHandler()
    {
        super((KeyBinding[])keyBindingBooleanHashMap.keySet().toArray(new KeyBinding[0]), toPrimitiveArray((Boolean[])keyBindingBooleanHashMap.values().toArray(new Boolean[0])));
    }

    private static boolean[] toPrimitiveArray(Boolean[] booleanList)
    {
        boolean[] primitives = new boolean[booleanList.length];
        int index = 0;
        Boolean[] var3 = booleanList;
        int var4 = booleanList.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            Boolean object = var3[var5];
            primitives[index++] = object.booleanValue();
        }

        return primitives;
    }

    public void keyDown(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd, boolean isRepeat)
    {
        if (!tickEnd)
        {
            Iterator iterator4;

            if (keyBinding == KEY_MULTIPLAYER && keyBinding.keyCode != -100 && keyBinding.keyCode != -99 && (Minecraft.getMinecraft().currentScreen == null || !(Minecraft.getMinecraft().currentScreen instanceof ITrade)))
            {
                ClientData.lastPlayerWantDisconnect = Long.valueOf(System.currentTimeMillis());

                if (Minecraft.getMinecraft().theWorld != null)
                {
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                    TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);

                    if (Minecraft.getMinecraft().theWorld != null)
                    {
                        Minecraft.getMinecraft().statFileWriter.readStat(StatList.leaveGameStat, 1);
                        Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                        Minecraft.getMinecraft().loadWorld((WorldClient)null);
                        iterator4 = (new ArrayList(ClientProxy.STREAMER_LIST)).iterator();

                        while (iterator4.hasNext())
                        {
                            SoundStreamer notification3 = (SoundStreamer)iterator4.next();
                            notification3.forceClose();
                        }

                        ClientProxy.STREAMER_LIST.clear();
                    }

                    ClientEventHandler.modsChecked = false;

                    if (!ClientProxy.currentServerName.contains("hub"))
                    {
                        if (ClientSocket.out != null)
                        {
                            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
                            ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
                            Minecraft.getMinecraft().displayGuiScreen(new WaitingSocketGui());
                        }
                    }
                    else
                    {
                        Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
                    }
                }
                else
                {
                    Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
                }
            }
            else if (keyBinding == KEY_CATALOG && Minecraft.getMinecraft().currentScreen == null)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenCatalogPacket()));
            }
            else
            {
                ClientConfig iterator;

                if (keyBinding == KEY_SPECIAL_INFO && Minecraft.getMinecraft().currentScreen == null)
                {
                    iterator = ClientProxy.clientConfig;
                    iterator.specialEnabled = !iterator.specialEnabled;

                    try
                    {
                        ClientProxy.saveConfig();
                    }
                    catch (IOException var9)
                    {
                        var9.printStackTrace();
                    }
                }
                else if (keyBinding == KEY_PORTAL_TP && Minecraft.getMinecraft().currentScreen == null)
                {
                    if (System.currentTimeMillis() - ClientProxy.lastCollidedWithPortalTime.longValue() < 100L && (!PortalBlock.lastTP.containsKey(Minecraft.getMinecraft().thePlayer.username) || System.currentTimeMillis() - ((Long)PortalBlock.lastTP.get(Minecraft.getMinecraft().thePlayer.username)).longValue() > 5000L))
                    {
                        PortalBlock.lastTP.put(Minecraft.getMinecraft().thePlayer.username, Long.valueOf(System.currentTimeMillis()));
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandPortalTPPacket(ClientProxy.lastCollidedWithPortalX, ClientProxy.lastCollidedWithPortalY, ClientProxy.lastCollidedWithPortalZ, Minecraft.getMinecraft().thePlayer.username, Minecraft.getMinecraft().thePlayer.rotationYaw)));
                    }
                }
                else if (keyBinding == KEY_WARZONE_LEAVE && Minecraft.getMinecraft().currentScreen == null && !this.isAnyMouseButtonDown())
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("spawn", 0)));

                    if (ClientData.currentJumpStartTime.longValue() != -1L)
                    {
                        NationsGUI.islandsSaveJumpPosition(ClientData.currentJumpLocation, "stop", Long.valueOf(-1L));
                        ClientData.currentJumpRecord = "";
                        ClientData.currentJumpStartTime = Long.valueOf(-1L);
                    }
                }
                else if (keyBinding == KEY_BONUS && Minecraft.getMinecraft().currentScreen == null && !this.isAnyMouseButtonDown())
                {
                    if (System.currentTimeMillis() > 1734166800000L && System.currentTimeMillis() < 1736722800000L)
                    {
                        Minecraft.getMinecraft().displayGuiScreen(new NoelMegaGiftGui());
                    }
                    else if (ClientData.bonusStartTime.longValue() != 0L && System.currentTimeMillis() >= ClientData.bonusStartTime.longValue() && System.currentTimeMillis() <= ClientData.bonusEndTime.longValue())
                    {
                        Minecraft.getMinecraft().displayGuiScreen(new BonusesGui());
                    }
                }
                else if (keyBinding == KEY_WAITING && Minecraft.getMinecraft().currentScreen == null && !this.isAnyMouseButtonDown() && ClientData.waitingServerName != null)
                {
                    Minecraft.getMinecraft().displayGuiScreen(new WaitingModalGui("quit"));
                }
                else if (keyBinding == KEY_TOGGLE_SPRINT && Minecraft.getMinecraft().currentScreen == null)
                {
                    toggleSprintEnabled = !toggleSprintEnabled;

                    if (!toggleSprintEnabled)
                    {
                        Minecraft.getMinecraft().thePlayer.setSprinting(false);
                    }
                }
                else if (keyBinding == KEY_BLOCKINFO)
                {
                    iterator = ClientProxy.clientConfig;
                    iterator.blockInfoEnabled = !iterator.blockInfoEnabled;

                    try
                    {
                        ClientProxy.saveConfig();
                    }
                    catch (IOException var8)
                    {
                        var8.printStackTrace();
                    }
                }
                else if (keyBinding == KEY_CHUNCK)
                {
                    displayChunck = !displayChunck;

                    if (Minecraft.getMinecraft().thePlayer.username.equals("iBalix") || Minecraft.getMinecraft().thePlayer.username.equals("MisterSand") || Minecraft.getMinecraft().thePlayer.username.equals("Tymothi"))
                    {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiDebugSkin());
                    }
                }
                else if (keyBinding == KEY_MOB_SPAWN)
                {
                    displayMobSpawning = !displayMobSpawning;
                }
                else if (keyBinding == KEY_SELL)
                {
                    if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().currentScreen == null)
                    {
                        InventoryPlayer iterator1 = Minecraft.getMinecraft().thePlayer.inventory;
                        ItemStack notification = iterator1.mainInventory[iterator1.currentItem];

                        if (notification != null)
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SellItemCheckPacket()));
                        }
                    }
                }
                else if (keyBinding == KEY_EMOTES && Minecraft.getMinecraft().theWorld != null)
                {
                    if (Minecraft.getMinecraft().currentScreen == null)
                    {
                        Minecraft.getMinecraft().displayGuiScreen(new EmoteGui());
                    }
                    else if (Minecraft.getMinecraft().currentScreen instanceof EmoteGui)
                    {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                    }
                }
                else if (keyBinding.keyDescription.contains("Hot Bar Slot"))
                {
                    Minecraft iterator2 = Minecraft.getMinecraft();

                    if (iterator2.theWorld != null && iterator2.currentScreen == null)
                    {
                        Minecraft.getMinecraft().thePlayer.inventory.currentItem = Integer.parseInt(keyBinding.keyDescription.substring(keyBinding.keyDescription.length() - 1)) - 1;
                    }

                    if (iterator2.theWorld != null && iterator2.currentScreen != null && iterator2.currentScreen instanceof GuiContainer && !(iterator2.currentScreen instanceof InventoryGUI))
                    {
                        if (System.currentTimeMillis() - lastHotbarKeyPress.longValue() < 250L)
                        {
                            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                        }

                        lastHotbarKeyPress = Long.valueOf(System.currentTimeMillis());
                    }
                }
                else if (keyBinding == KEY_REFRESH_HATS)
                {
                    String iterator3 = Minecraft.getMinecraft().getSession().getUsername();
                    List notification1 = Arrays.asList(new String[] {"iBalix", "GuiGeeK60", "GuiGeeK60_ulti", "Wascar"});

                    if (notification1.contains(iterator3))
                    {
                        Minecraft.getMinecraft().thePlayer.addChatMessage("\u00a74[\u00a7cHat\u00a74]\u00a7r \u00a77Refreshing Skins...");
                    }
                }
                else if (keyBinding == KEY_TOGGLE_3D_SKINS && Minecraft.getMinecraft().currentScreen == null)
                {
                    ClientProxy.clientConfig.render3DSkins = !ClientProxy.clientConfig.render3DSkins;

                    try
                    {
                        ClientProxy.saveConfig();
                    }
                    catch (IOException var7)
                    {
                        var7.printStackTrace();
                    }

                    Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getStringParams("nationsgui.toggle3d", new Object[] {ClientProxy.clientConfig.render3DSkins ? "ON" : "OFF"}));
                }
                else if (keyBinding == KEY_OPEN_NOTIF && Minecraft.getMinecraft().currentScreen == null)
                {
                    if (!ClientData.dialogs.isEmpty())
                    {
                        if (DialogOverride.currentDialogLetterIndex == ((String)((HashMap)ClientData.dialogs.get(0)).get("content")).length())
                        {
                            if (((HashMap)ClientData.dialogs.get(0)).get("command") != null)
                            {
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)((HashMap)ClientData.dialogs.get(0)).get("identifier"))));
                            }

                            ClientData.dialogs.remove(0);
                            DialogOverride.resetDisplay();
                        }
                        else
                        {
                            DialogOverride.currentDialogLetterIndex = ((String)((HashMap)ClientData.dialogs.get(0)).get("content")).length();
                        }
                    }

                    iterator4 = ClientData.notifications.iterator();

                    if (iterator4.hasNext())
                    {
                        Notification notification2 = (Notification)iterator4.next();

                        if (!notification2.isExpired() && ClientProxy.clientConfig.displayNotifications && notification2.getCompound().hasKey("actions"))
                        {
                            Minecraft.getMinecraft().displayGuiScreen(new NotificationActionsGUI(notification2));
                        }
                    }
                }
            }
        }
    }

    public void keyUp(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd) {}

    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

    public String getLabel()
    {
        return "nationsgui";
    }

    private boolean isAnyMouseButtonDown()
    {
        for (int i = 0; i < Mouse.getButtonCount(); ++i)
        {
            if (Mouse.isButtonDown(i))
            {
                return true;
            }
        }

        return false;
    }

    static
    {
        keyBindingBooleanHashMap.put(KEY_MULTIPLAYER, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_CATALOG, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_SPRINT, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_SPECIAL_INFO, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_PORTAL_TP, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_WARZONE_LEAVE, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_BONUS, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_WAITING, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_TOGGLE_SPRINT, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_BLOCKINFO, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_CHUNCK, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_MOB_SPAWN, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_SELL, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_EMOTES, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_SCREENSHOT, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_DEBUG, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_HIDE_GUI, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_THIRD_PERSON, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_SMOOTH_CAMERA, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_FULLSCREEN, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_TOGGLE_3D_SKINS, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_OPEN_NOTIF, Boolean.valueOf(false));
        keyBindingBooleanHashMap.put(KEY_TEST, Boolean.valueOf(false));
        String pseudo = Minecraft.getMinecraft().getSession().getUsername();

        if (pseudo.equalsIgnoreCase("iBalix") || pseudo.equalsIgnoreCase("GuiGeeK60") || pseudo.equalsIgnoreCase("GuiGeeK60_ulti"))
        {
            keyBindingBooleanHashMap.put(KEY_REFRESH_HATS, Boolean.valueOf(false));
        }

        for (int i = 0; i < 9; ++i)
        {
            keyBindingBooleanHashMap.put(new KeyBinding("Hot Bar Slot " + (i + 1), 2 + i), Boolean.valueOf(false));
        }
    }
}
