package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$10;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$11;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$12;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$13;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$14;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$15;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$16;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$17;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$18;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$19;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$20;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$21;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$22;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$23;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI$9;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionRemoveDiscordPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class FactionGUI extends TabbedFactionGUI
{
    public static HashMap<String, Object> factionInfos;
    public static boolean loaded = false;
    public static Long durationLevelAnimation = Long.valueOf(4000L);
    public static Long startLevelAnimation = Long.valueOf(0L);
    public static boolean displayLevels = false;
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static boolean achievementDone = false;
    public static HashMap<String, Integer> textColor = new FactionGUI$1();
    public static HashMap<String, Integer> blockLevel = new FactionGUI$2();
    private String targetName;
    private RenderItem itemRenderer = new RenderItem();
    protected String hoveredPlayer = "";
    private EntityOtherPlayerMP leaderEntity = null;
    private DynamicTexture flagTexture;
    private DynamicTexture bannerTexture;
    private GuiScrollBarGeneric scrollBar;
    public static HashMap<String, Integer> blockMainPalierX = new FactionGUI$3();
    public static HashMap<String, Integer> blockMainPalierY = new FactionGUI$4();
    public static HashMap<String, Integer> blockSecondaryPalierX = new FactionGUI$5();
    public static HashMap<String, Integer> blockRelation = new FactionGUI$6();
    public static HashMap<String, Integer> iconRelations = new FactionGUI$7();
    public static HashMap<String, Integer> buttonRelations = new FactionGUI$8();
    public static HashMap<String, Integer> badgesPositionTextureX = new FactionGUI$9();
    public static HashMap<String, Integer> iconsRewardsMainY = new FactionGUI$10();
    public static HashMap<String, Integer> iconsRewardsSecondaryY = new FactionGUI$11();
    public static HashMap<String, Integer> progressBarX = new FactionGUI$12();
    public static HashMap<String, Integer> progressBarY = new FactionGUI$13();
    public static HashMap<String, Integer> numbersY = new FactionGUI$14();
    public int currentDisplayPalier = -1;
    private HashMap<Integer, LinkedHashMap<String, String>> rewards = new HashMap();
    private Long levelGUIOpen = Long.valueOf(0L);

    public FactionGUI(String targetName)
    {
        initTabs();
        this.targetName = targetName;
        displayLevels = false;
        loaded = false;
        factionInfos = null;

        if (!achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_country", 1)));
        }

        playerTooltip = new HashMap();
        this.rewards.put(Integer.valueOf(0), new FactionGUI$15(this));
        this.rewards.put(Integer.valueOf(10), new FactionGUI$16(this));
        this.rewards.put(Integer.valueOf(20), new FactionGUI$17(this));
        this.rewards.put(Integer.valueOf(30), new FactionGUI$18(this));
        this.rewards.put(Integer.valueOf(40), new FactionGUI$19(this));
        this.rewards.put(Integer.valueOf(50), new FactionGUI$20(this));
        this.rewards.put(Integer.valueOf(60), new FactionGUI$21(this));
        this.rewards.put(Integer.valueOf(70), new FactionGUI$22(this));
        this.rewards.put(Integer.valueOf(80), new FactionGUI$23(this));
    }

    public static String getRoleName(String roleName)
    {
        return factionInfos != null && ((LinkedTreeMap)factionInfos.get("rolesName")).containsKey(roleName) && !((String)((LinkedTreeMap)factionInfos.get("rolesName")).get(roleName)).isEmpty() ? (String)((LinkedTreeMap)factionInfos.get("rolesName")).get(roleName) : roleName;
    }

    public static int getResearchLevel(String domain)
    {
        LinkedTreeMap researchesLevel = (LinkedTreeMap)factionInfos.get("researchesLevel");
        return researchesLevel.containsKey(domain) ? ((Double)researchesLevel.get(domain)).intValue() : 0;
    }

    public static ArrayList<String> getPlayerTooltip(String playerName)
    {
        HashMap playerInfo = (HashMap)playerTooltip.get(playerName);
        ArrayList tooltipLines = new ArrayList();

        if (playerInfo != null)
        {
            if (!((String)playerInfo.get("title")).isEmpty() && !((String)playerInfo.get("title")).contains("no title set"))
            {
                tooltipLines.add((String)playerInfo.get("title"));
            }

            tooltipLines.add("\u00a7f" + I18n.getString("faction.home.tooltip.member_since_1") + " " + playerInfo.get("member_days") + " " + I18n.getString("faction.home.tooltip.member_since_2"));
            tooltipLines.add("\u00a77Power : " + playerInfo.get("power") + "/" + playerInfo.get("maxpower"));
            tooltipLines.add("\u00a78" + I18n.getString("faction.home.tooltip.salary") + " : " + playerInfo.get("salary") + "$ | " + I18n.getString("faction.home.tooltip.tax") + " : " + playerInfo.get("tax") + "$");

            if (playerInfo.containsKey("valid_playtime") && Long.parseLong((String)playerInfo.get("valid_playtime")) < 172800L)
            {
                tooltipLines.add("\u00a7b\u2605 " + I18n.getString("faction.home.tooltip.playtime"));
            }

            tooltipLines.add("\u00a78----------------------");

            if (playerInfo.get("offline_days").equals("online"))
            {
                tooltipLines.add("\u00a7a" + I18n.getString("faction.home.tooltip.online"));
            }
            else
            {
                tooltipLines.add("\u00a77" + I18n.getString("faction.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.getString("faction.home.tooltip.offline_since_2"));
            }
        }

        return tooltipLines;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();

        if (factionInfos != null && factionInfos.size() != 0 && factionInfos.get("name").equals(this.targetName))
        {
            loaded = true;
        }
        else
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.targetName, false)));
        }

        this.leaderEntity = null;
        this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 444), (float)(this.guiTop + 147), 64, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    public void drawScreen(int mouseX, int mouseY) {}

    private float getSlideOnline()
    {
        return ((ArrayList)factionInfos.get("players_main_list")).size() > 6 ? (float)(-(((ArrayList)factionInfos.get("players_main_list")).size() - 6) * 13) * this.scrollBar.getSliderValue() : 0.0F;
    }

    public static boolean isNumeric(String str, boolean allowZero)
    {
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) == 0 && !allowZero)
            {
                return false;
            }
            else if (Integer.parseInt(str) < 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean hasPermissions(String permName)
    {
        return factionInfos.containsKey("permissions") && ((ArrayList)((ArrayList)factionInfos.get("permissions"))).contains(permName + "##true");
    }

    public void drawFactionMainButton(int mouseX, int mouseY, int buttonIndex, String buttonName, boolean enabled)
    {
        ClientEventHandler.STYLE.bindTexture("faction_main");

        if (!enabled)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * buttonIndex), (float)(this.guiTop + 79), (float)(190 * GUI_SCALE), (float)(138 * GUI_SCALE), 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.button." + buttonName), (float)(this.guiLeft + 41 + 62 * buttonIndex + 27), (float)(this.guiTop + 82), 3682124, 0.5F, "center", false, "georamaSemiBold", 28);
        }
        else if (mouseX >= this.guiLeft + 41 + 62 * buttonIndex && mouseX <= this.guiLeft + 41 + 62 * buttonIndex + 55 && mouseY >= this.guiTop + 79 && mouseY <= this.guiTop + 79 + 13)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * buttonIndex), (float)(this.guiTop + 79), (float)(190 * GUI_SCALE), (float)(106 * GUI_SCALE), 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.button." + buttonName), (float)(this.guiLeft + 41 + 62 * buttonIndex + 27), (float)(this.guiTop + 82), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
            this.hoveredAction = buttonName;
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * buttonIndex), (float)(this.guiTop + 79), (float)(190 * GUI_SCALE), (float)(((Integer)buttonRelations.get((String)factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.button." + buttonName), (float)(this.guiLeft + 41 + 62 * buttonIndex + 27), (float)(this.guiTop + 82), 16777215, 0.5F, "center", false, "georamaSemiBold", 28);
        }
    }

    public void drawFactionMainBadge(int mouseX, int mouseY, int badgeIndex, String badgeName)
    {
        CFontRenderer cFontRenderer = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(32));
        ClientEventHandler.STYLE.bindTexture("faction_global");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex), (float)(this.guiTop + 15), (float)(((Integer)badgesPositionTextureX.get(badgeName)).intValue() * GUI_SCALE), (float)(34 * GUI_SCALE), 12 * GUI_SCALE, 14 * GUI_SCALE, 12, 14, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

        if (mouseX >= this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex && mouseX <= this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex + 12 && mouseY >= this.guiTop + 15 && mouseY <= this.guiTop + 15 + 14)
        {
            tooltipToDraw.addAll(Arrays.asList(I18n.getString("faction.common.badge." + badgeName).replaceAll("#empire#", (String)factionInfos.get("isColony")).split("##")));

            if (badgeName.equals("colony"))
            {
                tooltipToDraw.add(I18n.getString("faction.common.badge.colony_tax") + " " + factionInfos.get("colonyTax") + "%");
            }
            else if (badgeName.equals("surclaims"))
            {
                Iterator infos = ((LinkedTreeMap)factionInfos.get("availableSurclaims")).entrySet().iterator();

                while (infos.hasNext())
                {
                    Entry duration = (Entry)infos.next();
                    Date date = new Date(Long.parseLong((String)duration.getKey()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
                    tooltipToDraw.add("\u00a7c" + simpleDateFormat.format(date) + ": \u00a77" + ((Double)duration.getValue()).intValue());
                }
            }
            else if (badgeName.equals("absence"))
            {
                String[] infos1 = ((String)factionInfos.get("absenceTime")).split("#");
                String duration1 = ModernGui.formatDuration(Long.valueOf((Long.parseLong(infos1[1]) - System.currentTimeMillis()) / 1000L));
                tooltipToDraw.add(I18n.getString("faction.main.absence").replaceAll("<duration>", "" + duration1).replaceAll("<sender>", ((String)factionInfos.get("absenceRequesterInfos")).split("#")[0]));
            }
            else if (badgeName.equals("restrictDisband"))
            {
                tooltipToDraw.add(I18n.getString("faction.main.warning." + factionInfos.get("disband_message")));
            }
            else if (badgeName.equals("restrictNotation"))
            {
                tooltipToDraw.add(I18n.getString("faction.main.warning_notations." + factionInfos.get("isEligibleForNotations")));
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty())
        {
            if (this.hoveredAction.equals("edit_photo"))
            {
                ClientData.lastCaptureScreenshot.put("main", Long.valueOf(System.currentTimeMillis()));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText(I18n.getString("faction.take_picture")));
            }
            else if (this.hoveredAction.equals("tphome"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainTPPacket((String)factionInfos.get("name"))));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (this.hoveredAction.equals("join"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            }
            else if (this.hoveredAction.equals("leave"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new LeaveConfirmGui(this));
            }
            else if (this.hoveredAction.contains("empire"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EmpireConfirmGui(this));
            }
            else if (this.hoveredAction.equals("buy"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new BuyCountryConfirmGui(this));
            }
            else if (this.hoveredAction.equals("discord"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new OpenDiscordConfirmGui(this));
            }
            else if (this.hoveredAction.equals("discord_remove"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionRemoveDiscordPacket((String)factionInfos.get("name"))));
                factionInfos.put("discord", "");
            }
            else if (this.hoveredAction.equals("back_levels"))
            {
                displayLevels = false;
            }
            else if (this.hoveredAction.equals("level_prev"))
            {
                this.currentDisplayPalier = Math.max(0, this.currentDisplayPalier - 10);
                startLevelAnimation = Long.valueOf(0L);
            }
            else if (this.hoveredAction.equals("level_next"))
            {
                this.currentDisplayPalier = Math.min(80, this.currentDisplayPalier + 10);
                startLevelAnimation = Long.valueOf(0L);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        this.drawDefaultBackground();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ClientEventHandler.STYLE.bindTexture("faction_main");

        if (factionInfos != null)
        {
            int currentLevel;
            int prevPalier;
            int scoreActuelPalier;
            int var23;

            if (!displayLevels)
            {
                if (factionInfos.get("banners") != null && ((Map)factionInfos.get("banners")).containsKey("main"))
                {
                    ModernGui.bindRemoteTexture((String)((Map)factionInfos.get("banners")).get("main"));
                }
                else
                {
                    ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
                }

                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 110, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
                ClientEventHandler.STYLE.bindTexture("faction_global");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(375 * GUI_SCALE), 433 * GUI_SCALE, 110 * GUI_SCALE, 433, 110, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("name"), (float)(this.guiLeft + 39), (float)(this.guiTop + 13), 16777215, 1.0F, "left", false, "georamaSemiBold", 32);
                currentLevel = 0;

                if (factionInfos.get("age") != null && Integer.parseInt((String)factionInfos.get("age")) < 7)
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "young");
                    ++currentLevel;
                }

                if (((Boolean)factionInfos.get("isEmpire")).booleanValue())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "empire");
                    ++currentLevel;
                }
                else if (!((String)factionInfos.get("isColony")).isEmpty())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "colony");
                    ++currentLevel;
                }

                if (((Boolean)factionInfos.get("isTopWarzone")).booleanValue())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "warzone");
                    ++currentLevel;
                }

                if (((Boolean)factionInfos.get("isReferent")).booleanValue())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "referent");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("availableSurclaims") && factionInfos.get("availableSurclaims") != null && ((LinkedTreeMap)factionInfos.get("availableSurclaims")).size() > 0)
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "surclaims");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("absenceTime") && !((String)factionInfos.get("absenceTime")).equals(""))
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "absence");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("isRestrictAssault") && ((Boolean)factionInfos.get("isRestrictAssault")).booleanValue())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "restrictAssault");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("isRestrictMissile") && ((Boolean)factionInfos.get("isRestrictMissile")).booleanValue())
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "restrictMissile");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("isEligibleForNotations") && factionInfos.get("isEligibleForNotations") != null)
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "restrictNotation");
                    ++currentLevel;
                }

                if (factionInfos.containsKey("disband_message") && factionInfos.get("disband_message") != null)
                {
                    this.drawFactionMainBadge(mouseX, mouseY, currentLevel, "restrictDisband");
                    ++currentLevel;
                }

                String currentScore = "";
                String nextPallier;

                for (Iterator currentPalier = ((ArrayList)factionInfos.get("tags")).iterator(); currentPalier.hasNext(); currentScore = currentScore + "#" + I18n.getString("faction.settings.tags." + nextPallier) + " ")
                {
                    nextPallier = (String)currentPalier.next();
                }

                ModernGui.drawScaledStringCustomFont("\u00a7o" + currentScore, (float)(this.guiLeft + 41), (float)(this.guiTop + 30), 14342893, 0.5F, "left", false, "georamaMedium", 25);
                ModernGui.drawSectionStringCustomFont(((String)factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), (float)(this.guiLeft + 41), (float)(this.guiTop + 40), 10395075, 0.5F, "left", false, "georamaMedium", 25, 8, 350);

                if (this.leaderEntity == null && loaded && factionInfos.size() > 0 || loaded && factionInfos.size() > 0 && this.leaderEntity != null && !this.leaderEntity.getDisplayName().equals(factionInfos.get("leader")))
                {
                    try
                    {
                        this.leaderEntity = new EntityOtherPlayerMP(this.mc.theWorld, (String)factionInfos.get("leader"));
                    }
                    catch (Exception var20)
                    {
                        this.leaderEntity = null;
                    }
                }

                if (loaded && factionInfos != null && this.flagTexture == null && factionInfos != null && factionInfos.get("flagImage") != null && !((String)factionInfos.get("flagImage")).isEmpty())
                {
                    BufferedImage var22 = ModernGui.decodeToImage((String)factionInfos.get("flagImage"));
                    this.flagTexture = new DynamicTexture(var22);
                }

                if (this.leaderEntity != null)
                {
                    GUIUtils.startGLScissor(this.guiLeft + 295, this.guiTop + 10, 100, 100);
                    GuiInventory.func_110423_a(this.guiLeft + 345, this.guiTop + 155, 75, 0.0F, 0.0F, this.leaderEntity);
                    GUIUtils.endGLScissor();
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(false);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 375), (float)(this.guiTop + 18), 792.0F, 129.0F, 4 * GUI_SCALE, 112 * GUI_SCALE, 4, 92, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(true);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);

                    if (this.flagTexture != null)
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture.getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 379), (float)(this.guiTop + 20), 0.0F, 0.0F, 156, 78, 50, 30, 156.0F, 78.0F, false);
                    }
                }

                var23 = 0;

                if (((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue())
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "join", true);
                    ++var23;
                }
                else if (((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue())
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "join", true);
                    ++var23;
                }
                else if (((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue())
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "leave", true);
                    ++var23;
                }

                if (!((Boolean)factionInfos.get("isLeader")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && ((Boolean)factionInfos.get("isForSale")).booleanValue())
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "buy", true);
                    ++var23;
                }

                if ((((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isAdmin") && ((Boolean)factionInfos.get("isAdmin")).booleanValue()) && ((Boolean)factionInfos.get("hasHome")).booleanValue())
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "tphome", true);
                    ++var23;
                }

                if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue())
                {
                    if (((String)factionInfos.get("name")).contains("Empire"))
                    {
                        this.drawFactionMainButton(mouseX, mouseY, var23, "empire_down", true);
                        ++var23;
                    }
                    else
                    {
                        this.drawFactionMainButton(mouseX, mouseY, var23, "empire_up", getResearchLevel("general") >= 11);
                        ++var23;
                    }
                }

                if (factionInfos != null && !((String)factionInfos.get("discord")).isEmpty() && factionInfos.get("canRemoveDiscord").equals("true"))
                {
                    this.drawFactionMainButton(mouseX, mouseY, var23, "discord_remove", true);
                    ++var23;
                }

                ClientEventHandler.STYLE.bindTexture("faction_global");

                if (Integer.parseInt((String)factionInfos.get("level")) >= 30)
                {
                    if (mouseX >= this.guiLeft + 41 + 62 * var23 && mouseX <= this.guiLeft + 41 + 62 * var23 + 10 && mouseY >= this.guiTop + 81 && mouseY <= this.guiTop + 81 + 11)
                    {
                        if (!((String)factionInfos.get("discord")).isEmpty())
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * var23), (float)(this.guiTop + 81), (float)(16 * GUI_SCALE), (float)(19 * GUI_SCALE), 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                            this.hoveredAction = "discord";
                        }
                        else
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * var23), (float)(this.guiTop + 81), (float)(16 * GUI_SCALE), (float)(3 * GUI_SCALE), 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                            tooltipToDraw.add(I18n.getString("faction.home.no_discord"));
                        }
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + 62 * var23), (float)(this.guiTop + 81), (float)(16 * GUI_SCALE), (float)(3 * GUI_SCALE), 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    }
                }

                if (factionInfos.containsKey("disband_message") && factionInfos.get("disband_message") != null && (!((String)factionInfos.get("disband_message")).toLowerCase().contains("warning") || ((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isAdmin")))
                {
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.main.warning." + factionInfos.get("disband_message")), (float)(this.guiLeft + 41), (float)(this.guiTop + 95), 12002109, 0.5F, "left", false, "georamaMedium", 26);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.informations"), (float)(this.guiLeft + 41), (float)(this.guiTop + 117), 16777215, 0.5F, "left", false, "georamaMedium", 32);

                if (((Boolean)factionInfos.get("isInCountry")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 136), 0.0F, (float)(0 * GUI_SCALE), 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 50), (float)(this.guiTop + 147), (float)(392 * GUI_SCALE), (float)(46 * GUI_SCALE), 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.level") + " (" + String.format("%.0f", new Object[] {(Double)factionInfos.get("progress")}) + "%)", (float)(this.guiLeft + 80), (float)(this.guiTop + 147), 16777215, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), (float)(this.guiLeft + 80), (float)(this.guiTop + 155), 16777215, 0.75F, "left", false, "georamaSemiBold", 30);

                    if (mouseX >= this.guiLeft + 41 && mouseX <= this.guiLeft + 41 + 91 && mouseY >= this.guiTop + 136 && mouseY <= this.guiTop + 136 + 39)
                    {
                        this.hoveredAction = "display_levels";
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 181), (float)(190 * GUI_SCALE), 0.0F, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 53), (float)(this.guiTop + 192), (float)(416 * GUI_SCALE), (float)(0 * GUI_SCALE), 13 * GUI_SCALE, 18 * GUI_SCALE, 13, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.notations"), (float)(this.guiLeft + 80), (float)(this.guiTop + 192), 16777215, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", new Object[] {(Double)factionInfos.get("notationsPosition")}): factionInfos.get("notationsPosition")) + "/" + factionInfos.get("totalCountries"), (float)(this.guiLeft + 80), (float)(this.guiTop + 200), 16777215, 0.75F, "left", false, "georamaSemiBold", 30);
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 136), 0.0F, (float)(((Integer)blockRelation.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ClientEventHandler.STYLE.bindTexture("faction_global");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 45), (float)(this.guiTop + 138), (float)(((Integer)iconRelations.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (float)(212 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.relation"), (float)(this.guiLeft + 73), (float)(this.guiTop + 139), 16777215, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common." + factionInfos.get("actualRelation") + ".nocolor"), (float)(this.guiLeft + 73), (float)(this.guiTop + 147), 16777215, 0.75F, "left", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_main");

                    if (mouseX >= this.guiLeft + 41 && mouseX <= this.guiLeft + 41 + 91 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 39)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 165), 0.0F, (float)(401 * GUI_SCALE), 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 47), (float)(this.guiTop + 170), (float)(392 * GUI_SCALE), (float)(23 * GUI_SCALE), 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.level") + " (" + String.format("%.0f", new Object[] {(Double)factionInfos.get("progress")}) + "%)", (float)(this.guiLeft + 73), (float)(this.guiTop + 168), 2234425, 0.5F, "left", false, "georamaMedium", 25);
                        ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), (float)(this.guiLeft + 73), (float)(this.guiTop + 176), 2234425, 0.75F, "left", false, "georamaSemiBold", 30);
                        this.hoveredAction = "display_levels";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 165), 0.0F, (float)(191 * GUI_SCALE), 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 47), (float)(this.guiTop + 170), (float)(392 * GUI_SCALE), (float)(0 * GUI_SCALE), 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.level") + " (" + String.format("%.0f", new Object[] {(Double)factionInfos.get("progress")}) + "%)", (float)(this.guiLeft + 73), (float)(this.guiTop + 168), 16777215, 0.5F, "left", false, "georamaMedium", 25);
                        ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), (float)(this.guiLeft + 73), (float)(this.guiTop + 176), 16777215, 0.75F, "left", false, "georamaSemiBold", 30);
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41), (float)(this.guiTop + 195), 0.0F, (float)(191 * GUI_SCALE), 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 50), (float)(this.guiTop + 199), (float)(416 * GUI_SCALE), (float)(0 * GUI_SCALE), 13 * GUI_SCALE, 18 * GUI_SCALE, 13, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.notations"), (float)(this.guiLeft + 73), (float)(this.guiTop + 198), 16777215, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", new Object[] {(Double)factionInfos.get("notationsPosition")}): factionInfos.get("notationsPosition")) + "/" + factionInfos.get("totalCountries"), (float)(this.guiLeft + 73), (float)(this.guiTop + 206), 16777215, 0.75F, "left", false, "georamaSemiBold", 30);
                }

                ClientEventHandler.STYLE.bindTexture("faction_empty_map");
                ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/mods/get_map?server=" + ClientProxy.currentServerName + "&country=" + factionInfos.get("name"));
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 147), (float)(this.guiTop + 135), 0.0F, 0.0F, 91 * GUI_SCALE, 85 * GUI_SCALE, 91, 85, (float)(91 * GUI_SCALE), (float)(85 * GUI_SCALE), false);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 253), (float)(this.guiTop + 135), (float)(190 * GUI_SCALE), 0.0F, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.claim"), (float)(this.guiLeft + 259), (float)(this.guiTop + 140), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("claims"), (float)(this.guiLeft + 302), (float)(this.guiTop + 140), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.mmr"), (float)(this.guiLeft + 259), (float)(this.guiTop + 151), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("mmr"), (float)(this.guiLeft + 302), (float)(this.guiTop + 151), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.power"), (float)(this.guiLeft + 259), (float)(this.guiTop + 162), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(factionInfos.get("power") + "/" + factionInfos.get("maxpower"), (float)(this.guiLeft + 302), (float)(this.guiTop + 162), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
                String scoreNextPalier;

                if (mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 259 + 80 && mouseY >= this.guiTop + 162 && mouseY <= this.guiTop + 162 + 10)
                {
                    tooltipToDraw.add("\u00a76Powerboost:");
                    tooltipToDraw.add("\u00a7eFixe: \u00a77" + factionInfos.get("powerboost_fixed"));
                    tooltipToDraw.add("\u00a7eWarzone: \u00a77" + factionInfos.get("powerboost_real"));
                    tooltipToDraw.add("\u00a7eUNESCO: \u00a77" + factionInfos.get("powerboost_unesco"));
                    tooltipToDraw.add("\u00a7eRecrutement: \u00a77" + factionInfos.get("newMembers"));
                    tooltipToDraw.add("\u00a7eMalus Assaut: \u00a77-" + factionInfos.get("powerboost_malus_assault"));

                    if (!((String)factionInfos.get("powerboost_others")).isEmpty())
                    {
                        String[] var24 = ((String)factionInfos.get("powerboost_others")).split(",");
                        prevPalier = var24.length;

                        for (scoreActuelPalier = 0; scoreActuelPalier < prevPalier; ++scoreActuelPalier)
                        {
                            scoreNextPalier = var24[scoreActuelPalier];

                            if (scoreNextPalier.split("#")[1].contains("-"))
                            {
                                tooltipToDraw.add("\u00a78" + I18n.getString("faction.home.tooltip.war_against") + " " + scoreNextPalier.split("#")[0] + ": \u00a7c" + scoreNextPalier.split("#")[1]);
                            }
                            else
                            {
                                tooltipToDraw.add("\u00a78" + I18n.getString("faction.home.tooltip.war_against") + " " + scoreNextPalier.split("#")[0] + ": \u00a7a" + scoreNextPalier.split("#")[1]);
                            }
                        }
                    }
                }

                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 253), (float)(this.guiTop + 180), (float)(190 * GUI_SCALE), 0.0F, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.ratings"), (float)(this.guiLeft + 259), (float)(this.guiTop + 185), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", new Object[] {(Double)factionInfos.get("notationsPosition")}): (String)factionInfos.get("notationsPosition"), (float)(this.guiLeft + 302), (float)(this.guiTop + 185), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.creation"), (float)(this.guiLeft + 259), (float)(this.guiTop + 196), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("age") + I18n.getString("faction.common.days.short"), (float)(this.guiLeft + 302), (float)(this.guiTop + 196), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.home.players"), (float)(this.guiLeft + 259), (float)(this.guiTop + 207), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                nextPallier = ((List)factionInfos.get("players_online")).size() + "\u00a7f/" + String.format("%.0f", new Object[] {(Double)factionInfos.get("count_players_offline")});
                ModernGui.drawScaledStringCustomFont(nextPallier, (float)(this.guiLeft + 302), (float)(this.guiTop + 207), ((Integer)textColor.get(factionInfos.get("actualRelation"))).intValue(), 0.5F, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 359), (float)(this.guiTop + 135), (float)(95 * GUI_SCALE), 0.0F, 91 * GUI_SCALE, 85 * GUI_SCALE, 91, 85, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 444), (float)(this.guiTop + 147), (float)(272 * GUI_SCALE), (float)(43 * GUI_SCALE), 2 * GUI_SCALE, 64 * GUI_SCALE, 2, 64, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                if (((ArrayList)factionInfos.get("players_main_list")).size() > 0)
                {
                    GUIUtils.startGLScissor(this.guiLeft + 359, this.guiTop + 135, 84, 85);

                    for (prevPalier = 0; prevPalier < ((ArrayList)factionInfos.get("players_main_list")).size(); ++prevPalier)
                    {
                        String var26 = ((String)((ArrayList)factionInfos.get("players_main_list")).get(prevPalier)).split("#")[1];
                        scoreNextPalier = "";

                        if (var26.split(" ").length > 1)
                        {
                            scoreNextPalier = var26.split(" ")[0];
                            var26 = var26.split(" ")[1];
                        }

                        int offsetYAnimation = this.guiLeft + 359;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 140 + prevPalier * 13) + this.getSlideOnline());

                        if (mouseX > offsetYAnimation && mouseX < offsetYAnimation + 84 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 13.0F)
                        {
                            this.hoveredPlayer = var26;
                        }

                        if (!ClientProxy.cacheHeadPlayer.containsKey(var26))
                        {
                            try
                            {
                                ResourceLocation maxOffsetY = AbstractClientPlayer.locationStevePng;
                                maxOffsetY = AbstractClientPlayer.getLocationSkin(var26);
                                AbstractClientPlayer.getDownloadImageSkin(maxOffsetY, var26);
                                ClientProxy.cacheHeadPlayer.put(var26, maxOffsetY);
                            }
                            catch (Exception var19)
                            {
                                System.out.println(var19.getMessage());
                            }
                        }
                        else
                        {
                            Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var26));
                            this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var26));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetYAnimation + 8 + 10, offsetY.intValue() + 0 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        }

                        if (prevPalier < ((ArrayList)factionInfos.get("players_online")).size())
                        {
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect((float)(offsetYAnimation + 15), (float)(offsetY.intValue() - 2), (float)(224 * GUI_SCALE), (float)(52 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        }

                        ClientEventHandler.STYLE.bindTexture("faction_global");

                        if (scoreNextPalier.equals("**"))
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(offsetYAnimation + 19), (float)(offsetY.intValue() + 2), (float)(345 * GUI_SCALE), (float)((prevPalier >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        }
                        else if (scoreNextPalier.equals("*"))
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(offsetYAnimation + 19), (float)(offsetY.intValue() + 2), (float)(305 * GUI_SCALE), (float)((prevPalier >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        }
                        else if (scoreNextPalier.equals("-"))
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(offsetYAnimation + 19), (float)(offsetY.intValue() + 2), (float)(265 * GUI_SCALE), (float)((prevPalier >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        }
                        else
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(offsetYAnimation + 19), (float)(offsetY.intValue() + 2), (float)(225 * GUI_SCALE), (float)((prevPalier >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        }

                        ModernGui.drawScaledStringCustomFont((prevPalier >= ((ArrayList)factionInfos.get("players_online")).size() ? "\u00a77" : "") + var26, (float)(offsetYAnimation + 27), (float)(offsetY.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                    }

                    GUIUtils.endGLScissor();
                }

                this.scrollBar.draw(mouseX, mouseY);
            }
            else if (displayLevels)
            {
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("faction_levels");

                if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(496 * GUI_SCALE), (float)(40 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                    this.hoveredAction = "back_levels";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(505 * GUI_SCALE), (float)(40 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 10395075, 0.5F, "left", false, "georamaSemiBold", 30);
                }

                currentLevel = Integer.parseInt((String)factionInfos.get("level"));
                int var21 = Integer.parseInt((String)factionInfos.get("score"));
                var23 = Math.min(80, currentLevel - currentLevel % 10);
                int var25 = Math.min(80, var23 + 10);
                prevPalier = var23 - 10;
                scoreActuelPalier = var23 * 151;
                int var27 = var25 * 151;

                if (this.currentDisplayPalier == -1)
                {
                    this.currentDisplayPalier = var23;
                }

                double var28 = 0.0D;
                double var29 = (double)Math.max(0, (((LinkedHashMap)this.rewards.get(Integer.valueOf(0))).size() - 7) * 21);

                if (var29 > 0.0D)
                {
                    if (startLevelAnimation.longValue() == 0L)
                    {
                        startLevelAnimation = Long.valueOf(System.currentTimeMillis());
                    }

                    double index = (double)(System.currentTimeMillis() - startLevelAnimation.longValue()) * 1.0D / (double)durationLevelAnimation.longValue();

                    if (index <= 1.0D)
                    {
                        var28 = index * var29;
                    }
                    else if (index <= 2.0D)
                    {
                        var28 = (1.0D - (index - 1.0D)) * var29;
                    }
                    else
                    {
                        startLevelAnimation = Long.valueOf(System.currentTimeMillis());
                    }
                }

                ClientEventHandler.STYLE.bindTexture("faction_levels");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 181), (float)(this.guiTop + 17), (float)((var23 >= this.currentDisplayPalier ? ((Integer)blockMainPalierX.get(factionInfos.get("actualRelation"))).intValue() : 0) * GUI_SCALE), (float)((var23 >= this.currentDisplayPalier ? ((Integer)blockMainPalierY.get(factionInfos.get("actualRelation"))).intValue() : 132) * GUI_SCALE), 126 * GUI_SCALE, 174 * GUI_SCALE, 126, 174, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier").toUpperCase() + " " + this.currentDisplayPalier, (float)(this.guiLeft + 190), (float)(this.guiTop + 24), 16777215, 0.5F, "left", false, "georamaBold", 32);
                int var30 = 0;
                GUIUtils.startGLScissor(this.guiLeft + 190, this.guiTop + 38, 115, 153);

                for (Iterator it = ((LinkedHashMap)this.rewards.get(Integer.valueOf(this.currentDisplayPalier))).entrySet().iterator(); it.hasNext(); ++var30)
                {
                    Entry progress = (Entry)it.next();
                    ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 190), (float)(this.guiTop + 38 + 21 * var30 - (int)var28), (float)((var23 >= this.currentDisplayPalier ? (!progress.getValue().equals("0") && !progress.getValue().equals("false") ? 87 : 43) : 65) * GUI_SCALE), (float)(((Integer)iconsRewardsMainY.get(progress.getKey())).intValue() * GUI_SCALE), 22 * GUI_SCALE, 22 * GUI_SCALE, 22, 22, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.label." + progress.getKey()), (float)(this.guiLeft + 230), (float)(this.guiTop + 42 + 21 * var30 - (int)var28), 16777215, 0.5F, "left", false, "georamaRegular", 24);
                    ModernGui.drawScaledStringCustomFont(isNumeric((String)progress.getValue(), true) ? (String)progress.getValue() : I18n.getString("faction.level.label." + progress.getValue()), (float)(this.guiLeft + 230), (float)(this.guiTop + 50 + 21 * var30 - (int)var28), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                }

                GUIUtils.endGLScissor();
                Entry firstNumber;
                Iterator var31;

                if (this.currentDisplayPalier - 10 >= 0)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 88), (float)(this.guiTop + 50), (float)((var23 >= this.currentDisplayPalier - 10 ? ((Integer)blockSecondaryPalierX.get(factionInfos.get("actualRelation"))).intValue() : 0) * GUI_SCALE), (float)(0 * GUI_SCALE), 86 * GUI_SCALE, 130 * GUI_SCALE, 86, 130, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier").toUpperCase() + " " + (this.currentDisplayPalier - 10), (float)(this.guiLeft + 95), (float)(this.guiTop + 55), 16777215, 0.5F, "left", false, "georamaBold", 28);
                    var30 = 0;

                    for (var31 = ((LinkedHashMap)this.rewards.get(Integer.valueOf(this.currentDisplayPalier - 10))).entrySet().iterator(); var31.hasNext() && var30 < 7; ++var30)
                    {
                        firstNumber = (Entry)var31.next();
                        ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 94), (float)(this.guiTop + 65 + 16 * var30), (float)((var23 >= this.currentDisplayPalier - 10 ? (!firstNumber.getValue().equals("0") && !firstNumber.getValue().equals("false") ? 109 : 0) : 17) * GUI_SCALE), (float)(((Integer)iconsRewardsSecondaryY.get(firstNumber.getKey())).intValue() * GUI_SCALE), 17 * GUI_SCALE, 17 * GUI_SCALE, 17, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(isNumeric((String)firstNumber.getValue(), true) ? (String)firstNumber.getValue() : I18n.getString("faction.level.label." + firstNumber.getValue()), (float)(this.guiLeft + 123), (float)(this.guiTop + 70 + 16 * var30), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 174 - 58), (float)(this.guiTop + 50), (float)(128 * GUI_SCALE), (float)(308 * GUI_SCALE), 58 * GUI_SCALE, 130 * GUI_SCALE, 58, 130, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                if (this.currentDisplayPalier + 10 <= 80)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 315), (float)(this.guiTop + 50), (float)((var23 >= this.currentDisplayPalier + 10 ? ((Integer)blockSecondaryPalierX.get(factionInfos.get("actualRelation"))).intValue() : 0) * GUI_SCALE), (float)(0 * GUI_SCALE), 86 * GUI_SCALE, 130 * GUI_SCALE, 86, 130, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier").toUpperCase() + " " + (this.currentDisplayPalier + 10), (float)(this.guiLeft + 322), (float)(this.guiTop + 55), 16777215, 0.5F, "left", false, "georamaBold", 28);
                    var30 = 0;

                    for (var31 = ((LinkedHashMap)this.rewards.get(Integer.valueOf(this.currentDisplayPalier + 10))).entrySet().iterator(); var31.hasNext() && var30 < 7; ++var30)
                    {
                        firstNumber = (Entry)var31.next();
                        ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 321), (float)(this.guiTop + 65 + 16 * var30), (float)((var23 >= this.currentDisplayPalier + 10 ? (!firstNumber.getValue().equals("0") && !firstNumber.getValue().equals("false") ? 109 : 0) : 17) * GUI_SCALE), (float)(((Integer)iconsRewardsSecondaryY.get(firstNumber.getKey())).intValue() * GUI_SCALE), 17 * GUI_SCALE, 17 * GUI_SCALE, 17, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(isNumeric((String)firstNumber.getValue(), true) ? (String)firstNumber.getValue() : I18n.getString("faction.level.label." + firstNumber.getValue()), (float)(this.guiLeft + 350), (float)(this.guiTop + 70 + 16 * var30), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 315), (float)(this.guiTop + 50), (float)(188 * GUI_SCALE), (float)(308 * GUI_SCALE), 58 * GUI_SCALE, 130 * GUI_SCALE, 58, 130, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                ClientEventHandler.STYLE.bindTexture("faction_levels");

                if (mouseX >= this.guiLeft + 67 && mouseX <= this.guiLeft + 67 + 19 && mouseY >= this.guiTop + 103 && mouseY <= this.guiTop + 103 + 18)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 67), (float)(this.guiTop + 103), (float)(487 * GUI_SCALE), (float)(19 * GUI_SCALE), 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    this.hoveredAction = "level_prev";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 67), (float)(this.guiTop + 103), (float)(501 * GUI_SCALE), (float)(19 * GUI_SCALE), 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                ClientEventHandler.STYLE.bindTexture("faction_levels");

                if (mouseX >= this.guiLeft + 412 && mouseX <= this.guiLeft + 412 + 19 && mouseY >= this.guiTop + 103 && mouseY <= this.guiTop + 103 + 18)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 412), (float)(this.guiTop + 103), (float)(487 * GUI_SCALE), (float)(0 * GUI_SCALE), 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    this.hoveredAction = "level_next";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 412), (float)(this.guiTop + 103), (float)(501 * GUI_SCALE), (float)(0 * GUI_SCALE), 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                ClientEventHandler.STYLE.bindTexture("faction_levels");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 137), (float)(this.guiTop + 201), (float)(0 * GUI_SCALE), (float)(484 * GUI_SCALE), 215 * GUI_SCALE, 8 * GUI_SCALE, 215, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                float var32 = Math.min((float)(var21 - scoreActuelPalier) / ((float)(var27 - scoreActuelPalier) * 1.0F), 1.0F);

                if (System.currentTimeMillis() - this.levelGUIOpen.longValue() <= 1000L)
                {
                    var32 *= (float)(System.currentTimeMillis() - this.levelGUIOpen.longValue()) / 1000.0F;
                }

                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 137), (float)(this.guiTop + 201), (float)(((Integer)progressBarX.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (float)(((Integer)progressBarY.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (int)(215.0F * var32) * GUI_SCALE, 8 * GUI_SCALE, (int)(215.0F * var32), 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                int var33 = var23 / 10;
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 70), (float)(this.guiTop + 196), (float)((383 + 14 * (var33 - 1)) * GUI_SCALE), (float)(((Integer)numbersY.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 84), (float)(this.guiTop + 196), (float)(367 * GUI_SCALE), (float)(((Integer)numbersY.get(factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier_actual").split(" ")[0], (float)(this.guiLeft + 102), (float)(this.guiTop + 197), ((Integer)textColor.get(factionInfos.get("actualRelation"))).intValue(), 0.5F, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier_actual").split(" ")[1], (float)(this.guiLeft + 102), (float)(this.guiTop + 206), ((Integer)textColor.get(factionInfos.get("actualRelation"))).intValue(), 0.5F, "left", false, "georamaSemiBold", 30);

                if (var23 < 80)
                {
                    var33 = (var23 + 10) / 10;
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 392), (float)(this.guiTop + 196), (float)((382 + 14 * (var33 - 1)) * GUI_SCALE), (float)(456 * GUI_SCALE), 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 406), (float)(this.guiTop + 196), (float)(366 * GUI_SCALE), (float)(456 * GUI_SCALE), 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier_next").split(" ")[0], (float)(this.guiLeft + 387), (float)(this.guiTop + 197), 14342893, 0.5F, "right", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.palier_next").split(" ")[1], (float)(this.guiLeft + 387), (float)(this.guiTop + 206), 14342893, 0.5F, "right", false, "georamaSemiBold", 30);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.level.label.level") + " " + currentLevel, (float)(this.guiLeft + 244), (float)(this.guiTop + 214), 10395075, 0.5F, "center", false, "georamaBold", 32);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }
}
