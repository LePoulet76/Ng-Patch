package net.ilexiconn.nationsgui.forge.client.gui.faction;

import acs.tabbychat.GuiChatTC;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI$TAB;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDetailsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerListGUI extends TabbedFactionListGUI
{
    public static HashMap<String, HashMap<String, String>> playersExtraData = new HashMap();
    public static ArrayList<HashMap<String, String>> playersData = new ArrayList();
    public static LinkedHashMap<String, ArrayList<String>> staffPlayers = new LinkedHashMap();
    public static LinkedHashMap<String, ArrayList<String>> staffInterServerPlayers = new LinkedHashMap();
    public static HashMap<String, String> selectedPlayer = new HashMap();
    public static HashMap<String, String> hoveredPlayer = new HashMap();
    public static String hoveredStaff = "";
    private HashMap<String, EntityOtherPlayerMP> playersEntity = new HashMap();
    public static boolean loaded = false;
    private GuiScrollBarGeneric scrollBarPlayer;
    private GuiScrollBarGeneric scrollBarStaff;
    public static int staffContentHeight = 0;
    private GuiTextField searchInput;
    public int countPlayersBySearch = -1;
    public static Long lastExtraDataPacket = Long.valueOf(0L);
    private CFontRenderer cFontSemiBold28;
    private PlayerListGUI$TAB activeTab;

    public PlayerListGUI()
    {
        this.activeTab = PlayerListGUI$TAB.LIST_PLAYERS;
        playersData.clear();
        loaded = false;
        initTabs();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();

        if (playersData.isEmpty())
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerListDataPacket()));
        }

        this.scrollBarPlayer = new GuiScrollBarGeneric((float)(this.guiLeft + 297), (float)(this.guiTop + 70), 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarPlayer.setScrollIncrement(5.0E-4F);
        this.scrollBarStaff = new GuiScrollBarGeneric((float)(this.guiLeft + 297), (float)(this.guiTop + 70), 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.searchInput = new CustomInputFieldGUI(this.guiLeft + 343, this.guiTop + 54, 94, 12, "georamaSemiBold", 28);
        this.searchInput.setEnableBackgroundDrawing(false);
        this.searchInput.setMaxStringLength(15);
        this.countPlayersBySearch = -1;
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(28));
    }

    public void drawScreen(int mouseX, int mouseY) {}

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        ModernGui.drawDefaultBackground(this, this.width, this.height, mouseX, mouseY);
        tooltipToDraw.clear();
        this.hoveredAction = "";
        hoveredStaff = "";
        hoveredPlayer = new HashMap();
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.manage_countries"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.list_players"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 324), (float)(this.guiTop + 48), (float)(276 * GUI_SCALE), (float)(157 * GUI_SCALE), 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        this.searchInput.drawTextBox();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int index = 0;
        PlayerListGUI$TAB[] playerExtraData = PlayerListGUI$TAB.values();
        int action = playerExtraData.length;
        int pair;

        for (pair = 0; pair < action; ++pair)
        {
            PlayerListGUI$TAB groupName = playerExtraData[pair];
            ClientEventHandler.STYLE.bindTexture("faction_list");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42 + 55 * index), (float)(this.guiTop + 36), (float)(409 * GUI_SCALE), (float)((this.activeTab.equals(groupName) ? 103 : 89) * GUI_SCALE), 52 * GUI_SCALE, 12 * GUI_SCALE, 52, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.tab." + groupName.name().toLowerCase()), (float)(this.guiLeft + 42 + 55 * index + 26), (float)(this.guiTop + 39), this.activeTab.equals(groupName) ? 7239406 : 14342893, 0.5F, "center", false, "georamaSemiBold", 26);

            if (mouseX >= this.guiLeft + 42 + 55 * index && mouseX <= this.guiLeft + 42 + 55 * index + 52 && mouseY >= this.guiTop + 36 && mouseY <= this.guiTop + 36 + 12)
            {
                this.hoveredAction = "switch_tab#" + groupName.name();
            }

            ++index;
        }

        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 48), (float)(0 * GUI_SCALE), (float)(191 * GUI_SCALE), 266 * GUI_SCALE, 173 * GUI_SCALE, 266, 173, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

        if (!this.activeTab.equals(PlayerListGUI$TAB.LIST_PLAYERS) && !this.activeTab.equals(PlayerListGUI$TAB.AVAILABLE))
        {
            Float offsetY;
            int playerIndex;
            boolean x;
            int y;
            Iterator var14;
            String playerName;
            String online;
            ResourceLocation resourceLocation;
            int var23;
            Iterator var25;
            Entry var28;
            String var30;
            int var31;
            int var32;

            if (this.activeTab.equals(PlayerListGUI$TAB.STAFF))
            {
                GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 55, 247, 154);
                var23 = 0;
                var25 = staffPlayers.entrySet().iterator();

                while (var25.hasNext())
                {
                    var28 = (Entry)var25.next();

                    if (((ArrayList)var28.getValue()).size() > 0)
                    {
                        var30 = (String)var28.getKey();
                        var31 = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 58 + var23) + this.getSlideStaff());
                        ModernGui.drawScaledStringCustomFont(I18n.getString("badge.title." + var30), (float)var31, (float)offsetY.intValue(), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                        var23 += 13;
                        offsetY = Float.valueOf((float)(this.guiTop + 58 + var23) + this.getSlideStaff());
                        playerIndex = 0;
                        x = false;
                        y = 0;
                        var14 = ((ArrayList)var28.getValue()).iterator();

                        while (var14.hasNext())
                        {
                            playerName = (String)var14.next();

                            if (this.searchInput.getText().isEmpty() || playerName.toLowerCase().contains(this.searchInput.getText().toLowerCase()))
                            {
                                var32 = playerIndex % 8;
                                y = playerIndex / 8;
                                online = playerName.split("##")[1];
                                playerName = playerName.split("##")[0];

                                if (!ClientProxy.cacheHeadPlayer.containsKey(playerName))
                                {
                                    try
                                    {
                                        resourceLocation = AbstractClientPlayer.locationStevePng;
                                        resourceLocation = AbstractClientPlayer.getLocationSkin(playerName);
                                        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, playerName);
                                        ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                                    }
                                    catch (Exception var20)
                                    {
                                        ;
                                    }
                                }
                                else
                                {
                                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                                    this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                                    GUIUtils.drawScaledCustomSizeModalRect(var31 + 21 + 30 * var32, offsetY.intValue() + 21 + 25 * y, 8.0F, 16.0F, 8, -8, -21, -21, 64.0F, 64.0F);
                                    ClientEventHandler.STYLE.bindTexture("faction_list");

                                    if (online.equals("online"))
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var31 + 30 * var32 + 18), (float)(offsetY.intValue() + 25 * y - 3), (float)(481 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                    }
                                    else
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var31 + 30 * var32 + 18), (float)(offsetY.intValue() + 25 * y - 3), (float)(491 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                    }
                                }

                                if (mouseX >= var31 + 30 * var32 && mouseX <= var31 + 30 * var32 + 21 && mouseY >= offsetY.intValue() + 25 * y && mouseY <= offsetY.intValue() + 25 * y + 21)
                                {
                                    tooltipToDraw.add(playerName);
                                    hoveredStaff = playerName;
                                }

                                ++playerIndex;
                            }
                        }

                        var23 += (y + 1) * 25 + 3;
                    }
                }

                staffContentHeight = var23;
                GUIUtils.endGLScissor();

                if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173)
                {
                    this.scrollBarStaff.draw(mouseX, mouseY);
                }
            }
            else if (this.activeTab.equals(PlayerListGUI$TAB.STAFF_INTER_SERVER))
            {
                GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 55, 247, 154);
                var23 = 0;
                var25 = staffInterServerPlayers.entrySet().iterator();

                while (var25.hasNext())
                {
                    var28 = (Entry)var25.next();

                    if (((ArrayList)var28.getValue()).size() > 0)
                    {
                        var30 = (String)var28.getKey();
                        var31 = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 58 + var23) + this.getSlideStaff());
                        ModernGui.drawScaledStringCustomFont(I18n.getString("staff.interserver." + var30), (float)var31, (float)offsetY.intValue(), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                        var23 += 13;
                        offsetY = Float.valueOf((float)(this.guiTop + 58 + var23) + this.getSlideStaff());
                        playerIndex = 0;
                        x = false;
                        y = 0;
                        var14 = ((ArrayList)var28.getValue()).iterator();

                        while (var14.hasNext())
                        {
                            playerName = (String)var14.next();

                            if (this.searchInput.getText().isEmpty() || playerName.toLowerCase().contains(this.searchInput.getText().toLowerCase()))
                            {
                                var32 = playerIndex % 8;
                                y = playerIndex / 8;
                                online = playerName.split("##")[1];
                                playerName = playerName.split("##")[0];

                                if (!ClientProxy.cacheHeadPlayer.containsKey(playerName))
                                {
                                    try
                                    {
                                        resourceLocation = AbstractClientPlayer.locationStevePng;
                                        resourceLocation = AbstractClientPlayer.getLocationSkin(playerName);
                                        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, playerName);
                                        ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                                    }
                                    catch (Exception var19)
                                    {
                                        ;
                                    }
                                }
                                else
                                {
                                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                                    this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                                    GUIUtils.drawScaledCustomSizeModalRect(var31 + 21 + 30 * var32, offsetY.intValue() + 21 + 25 * y, 8.0F, 16.0F, 8, -8, -21, -21, 64.0F, 64.0F);
                                    ClientEventHandler.STYLE.bindTexture("faction_list");

                                    if (online.equals("online"))
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var31 + 30 * var32 + 18), (float)(offsetY.intValue() + 25 * y - 3), (float)(481 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                    }
                                    else
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var31 + 30 * var32 + 18), (float)(offsetY.intValue() + 25 * y - 3), (float)(491 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                    }
                                }

                                if (mouseX >= var31 + 30 * var32 && mouseX <= var31 + 30 * var32 + 21 && mouseY >= offsetY.intValue() + 25 * y && mouseY <= offsetY.intValue() + 25 * y + 21)
                                {
                                    tooltipToDraw.add(playerName);
                                    hoveredStaff = playerName;
                                }

                                ++playerIndex;
                            }
                        }

                        var23 += (y + 1) * 25 + 3;
                    }
                }

                staffContentHeight = var23;
                GUIUtils.endGLScissor();

                if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173)
                {
                    this.scrollBarStaff.draw(mouseX, mouseY);
                }
            }
        }
        else
        {
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.getString("faction.list.label.skin"), (float)(this.guiLeft + 50), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.getString("faction.list.label.pseudo"), (float)(this.guiLeft + 93), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.getString("faction.list.label.countries"), (float)(this.guiLeft + 169), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.getString("faction.list.label.power"), (float)(this.guiLeft + 243), (float)(this.guiTop + 57), 10395075, 0.5F, "left", false, "georamaMedium", 28);
            GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 67, 247, 154);

            if (playersData.size() > 0)
            {
                index = 0;
                Iterator var22 = playersData.iterator();

                while (var22.hasNext())
                {
                    HashMap var24 = (HashMap)var22.next();
                    pair = this.guiLeft + 50;
                    Float var29 = Float.valueOf((float)(this.guiTop + 70 + index * 16) + this.getSlidePlayers());

                    if ((this.searchInput.getText().isEmpty() || ((String)var24.get("name")).toLowerCase().contains(this.searchInput.getText().toLowerCase())) && (!this.activeTab.equals(PlayerListGUI$TAB.AVAILABLE) || ((String)var24.get("country")).equals("") && ((String)var24.get("isOnline")).equals("true")))
                    {
                        if (var29.intValue() >= this.guiTop + 60 && var29.intValue() <= this.guiTop + 70 + 170)
                        {
                            if (!ClientProxy.cacheHeadPlayer.containsKey(var24.get("name")))
                            {
                                try
                                {
                                    ResourceLocation offsetX = AbstractClientPlayer.locationStevePng;
                                    offsetX = AbstractClientPlayer.getLocationSkin((String)var24.get("name"));
                                    AbstractClientPlayer.getDownloadImageSkin(offsetX, (String)var24.get("name"));
                                    ClientProxy.cacheHeadPlayer.put(var24.get("name"), offsetX);
                                }
                                catch (Exception var21)
                                {
                                    ;
                                }
                            }
                            else
                            {
                                Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var24.get("name")));
                                this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var24.get("name")));
                                GUIUtils.drawScaledCustomSizeModalRect(pair + 10, var29.intValue() + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                                ClientEventHandler.STYLE.bindTexture("faction_list");

                                if (((String)var24.get("isOnline")).equals("true"))
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(pair + 7), (float)(var29.intValue() - 3), (float)(481 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                }
                                else
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(pair + 7), (float)(var29.intValue() - 3), (float)(491 * GUI_SCALE), (float)(2 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                                }
                            }

                            if (!((String)var24.get("country")).isEmpty())
                            {
                                ClientProxy.loadCountryFlag((String)var24.get("country"));

                                if (ClientProxy.flagsTexture.containsKey(var24.get("country")))
                                {
                                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(var24.get("country"))).getGlTextureId());
                                    ModernGui.drawScaledCustomSizeModalRect((float)(pair + 15), (float)(var29.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                                }
                            }
                        }

                        ModernGui.drawScaledStringCustomFont((String)var24.get("name"), (float)(pair + 43), (float)(var29.intValue() + 2), ((String)var24.get("name")).equals(selectedPlayer.get("name")) ? 7239406 : (((String)var24.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5F, "left", false, "georamaSemiBold", 28);
                        ModernGui.drawScaledStringCustomFont((String)var24.get("country"), (float)(pair + 120), (float)(var29.intValue() + 2), ((String)var24.get("name")).equals(selectedPlayer.get("name")) ? 7239406 : (((String)var24.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5F, "left", false, "georamaSemiBold", 28);
                        ModernGui.drawScaledStringCustomFont((String)var24.get("power"), (float)(pair + 194), (float)(var29.intValue() + 2), ((String)var24.get("name")).equals(selectedPlayer.get("name")) ? 7239406 : (((String)var24.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5F, "left", false, "georamaSemiBold", 28);

                        if (mouseX >= pair && mouseX <= pair + 247 && (float)mouseY >= var29.floatValue() && (float)mouseY <= var29.floatValue() + 16.0F)
                        {
                            hoveredPlayer = var24;
                        }

                        ++index;
                    }
                }

                if (this.countPlayersBySearch == -1)
                {
                    this.countPlayersBySearch = index + 1;

                    if (this.countPlayersBySearch > 2500)
                    {
                        this.scrollBarPlayer.setScrollIncrement(5.0E-4F);
                    }
                    else if (this.countPlayersBySearch > 1000)
                    {
                        this.scrollBarPlayer.setScrollIncrement(0.005F);
                    }
                    else if (this.countPlayersBySearch > 200)
                    {
                        this.scrollBarPlayer.setScrollIncrement(0.01F);
                    }
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173)
            {
                this.scrollBarPlayer.draw(mouseX, mouseY);
            }
        }

        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 324), (float)(this.guiTop + 81), (float)(0 * GUI_SCALE), (float)(372 * GUI_SCALE), 129 * GUI_SCALE, 140 * GUI_SCALE, 129, 140, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

        if (selectedPlayer != null && !selectedPlayer.isEmpty())
        {
            if (!playersExtraData.containsKey(selectedPlayer.get("name")))
            {
                if (System.currentTimeMillis() - lastExtraDataPacket.longValue() > 500L)
                {
                    lastExtraDataPacket = Long.valueOf(System.currentTimeMillis());
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerListDetailsPacket((String)selectedPlayer.get("name"))));
                }
            }
            else
            {
                HashMap var27 = (HashMap)playersExtraData.get(selectedPlayer.get("name"));
                ClientEventHandler.STYLE.bindTexture("faction_list");

                if (((String)selectedPlayer.get("isOnline")).equals("true"))
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 448), (float)(this.guiTop + 77), (float)(480 * GUI_SCALE), (float)(11 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 448), (float)(this.guiTop + 77), (float)(490 * GUI_SCALE), (float)(11 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                ModernGui.drawScaledStringCustomFont((String)selectedPlayer.get("name"), (float)(this.guiLeft + 378), (float)(this.guiTop + 88), 16514302, 0.75F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.label." + (((String)selectedPlayer.get("isOnline")).equals("true") ? "online" : "offline")).replaceAll("#time#", !((String)selectedPlayer.get("lastLogin")).isEmpty() ? ModernGui.formatDelayTime(Long.valueOf(Long.parseLong((String)selectedPlayer.get("lastLogin")))) : "-"), (float)(this.guiLeft + 378), (float)(this.guiTop + 100), 10395075, 0.5F, "left", false, "georamaMedium", 26);

                if (NationsGUI.BADGES_RESOURCES.containsKey(((String)var27.get("group")).toLowerCase()))
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(((String)var27.get("group")).toLowerCase()));
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 378), (float)(this.guiTop + 113), 0.0F, 0.0F, 18, 18, 8, 8, 18.0F, 18.0F, false);
                }

                ModernGui.drawScaledStringCustomFont(ModernGui.getRankColor(((String)var27.get("group")).toLowerCase()) + (String)var27.get("group"), (float)(this.guiLeft + 388), (float)(this.guiTop + 113), 10395075, 0.5F, "left", false, "georamaMedium", 26);

                if (!this.playersEntity.containsKey(selectedPlayer.get("name")))
                {
                    try
                    {
                        this.playersEntity.put(selectedPlayer.get("name"), new EntityOtherPlayerMP(this.mc.theWorld, (String)selectedPlayer.get("name")));
                    }
                    catch (Exception var18)
                    {
                        ;
                    }
                }

                if (this.playersEntity.containsKey(selectedPlayer.get("name")) && this.playersEntity.get(selectedPlayer.get("name")) != null)
                {
                    GUIUtils.startGLScissor(this.guiLeft + 324, this.guiTop + 67, 50, 57);
                    GuiInventory.func_110423_a(this.guiLeft + 350, this.guiTop + 145, 35, 0.0F, 0.0F, (EntityLivingBase)this.playersEntity.get(selectedPlayer.get("name")));
                    GUIUtils.endGLScissor();
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.label.playtime_interserv"), (float)(this.guiLeft + 353), (float)(this.guiTop + 131), ((Integer)FactionGUI.textColor.get("neutral")).intValue(), 0.5F, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(ModernGui.formatDuration(Long.valueOf(Long.parseLong((String)var27.get("playtimeInterServ")))), (float)(this.guiLeft + 353), (float)(this.guiTop + 137), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 124), (float)(((Integer)FactionListGUI.iconsXPerRelation.get("neutral")).intValue() * GUI_SCALE), (float)(((Integer)FactionListGUI.iconsYByName.get("stats")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.label.playtime").replaceAll("#server#", ClientProxy.currentServerName), (float)(this.guiLeft + 353), (float)(this.guiTop + 156), ((Integer)FactionGUI.textColor.get("neutral")).intValue(), 0.5F, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(!((String)selectedPlayer.get("playtime")).isEmpty() ? ModernGui.formatDuration(Long.valueOf(Long.parseLong((String)selectedPlayer.get("playtime")))) : "0", (float)(this.guiLeft + 353), (float)(this.guiTop + 162), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 149), (float)(((Integer)FactionListGUI.iconsXPerRelation.get("neutral")).intValue() * GUI_SCALE), (float)(((Integer)FactionListGUI.iconsYByName.get("timer")).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.label.top_skill"), (float)(this.guiLeft + 353), (float)(this.guiTop + 181), ((Integer)FactionGUI.textColor.get("neutral")).intValue(), 0.5F, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(!((String)var27.get("topSkill")).isEmpty() ? I18n.getString("skills.skill." + (String)var27.get("topSkill")) : "-", (float)(this.guiLeft + 353), (float)(this.guiTop + 187), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 325), (float)(this.guiTop + 174), (float)(((Integer)FactionListGUI.iconsXPerRelation.get("neutral")).intValue() * GUI_SCALE), (float)(((Integer)FactionListGUI.iconsYByName.get(var27.get("topSkill"))).intValue() * GUI_SCALE), 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                String var26 = "send_message";

                if (!((String)selectedPlayer.get("name")).equals(Minecraft.getMinecraft().thePlayer.username) && !((String)selectedPlayer.get("isOnline")).equals("false"))
                {
                    if (this.activeTab.equals(PlayerListGUI$TAB.LIST_PLAYERS))
                    {
                        var26 = "send_message";
                    }
                    else if (this.activeTab.equals(PlayerListGUI$TAB.AVAILABLE) && var27.containsKey("viewerCanInvite") && ((String)var27.get("viewerCanInvite")).equals("true"))
                    {
                        var26 = "invite_country";
                    }
                }
                else
                {
                    var26 = "see_player";
                }

                if (mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 103 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 204), (float)(276 * GUI_SCALE), (float)(222 * GUI_SCALE), 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = var26;
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 204), (float)(276 * GUI_SCALE), (float)(204 * GUI_SCALE), 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.list.label." + var26), (float)(this.guiLeft + 337 + 52), (float)(this.guiTop + 207), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    private float getSlidePlayers()
    {
        return this.countPlayersBySearch > 10 ? (float)(-(this.countPlayersBySearch - 10) * 12) * this.scrollBarPlayer.getSliderValue() : 0.0F;
    }

    private float getSlideStaff()
    {
        return (float)(-(staffContentHeight - 154)) * this.scrollBarStaff.getSliderValue();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.searchInput.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.searchInput.textboxKeyTyped(typedChar, keyCode);
        this.countPlayersBySearch = -1;
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.hoveredAction.isEmpty())
            {
                if (this.hoveredAction.equals("send_message"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiChatTC("/msg " + (String)selectedPlayer.get("name") + ""));
                }
                else if (this.hoveredAction.equals("invite_country"))
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket((String)selectedPlayer.get("name"), "", "invite_start")));
                }
                else if (this.hoveredAction.contains("switch_tab"))
                {
                    this.activeTab = PlayerListGUI$TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
                    this.countPlayersBySearch = -1;
                }
                else if (this.hoveredAction.contains("see_player"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new ProfilGui((String)selectedPlayer.get("name"), ""));
                }
            }
            else if (hoveredPlayer != null && !hoveredPlayer.isEmpty())
            {
                selectedPlayer = hoveredPlayer;
            }
            else if (!hoveredStaff.isEmpty())
            {
                Iterator var4 = playersData.iterator();

                while (var4.hasNext())
                {
                    HashMap player = (HashMap)var4.next();

                    if (((String)player.get("name")).equalsIgnoreCase(hoveredStaff))
                    {
                        selectedPlayer = player;
                        break;
                    }
                }
            }
        }

        this.searchInput.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
