/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiPlayerInfo
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.NetClientHandler
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ChatTagManager;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TagPlayerFactionPacket;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class PlayerListGUI
extends Gui {
    private Minecraft mc;
    public static final ResourceLocation PHONE_ICON = new ResourceLocation("nationsgui", "textures/gui/phone.png");
    public final CFontRenderer dg22;
    public static String topText = "";
    public static String bottomText = "";
    private int currentOffsetCountry = 0;
    public static Long startAnimationCountry = 0L;
    public static Long startAnimationStaff = 0L;
    public static String sideAnimationStaff = "down";
    public static String sideAnimationCountry = "down";

    public PlayerListGUI(Minecraft mc) {
        this.mc = mc;
        this.dg22 = ModernGui.getCustomFont("minecraftDungeons", 22);
        startAnimationCountry = System.currentTimeMillis();
        startAnimationStaff = System.currentTimeMillis();
        sideAnimationStaff = "down";
        sideAnimationCountry = "down";
    }

    public void renderPlayerList(ScaledResolution resolution) {
        int i;
        NetClientHandler clientHandler = this.mc.field_71439_g.field_71174_a;
        ArrayList allInfoList = clientHandler.field_72559_c;
        ArrayList orderedPlayerInfoList = new ArrayList();
        ArrayList orderedStaffInfoList = new ArrayList();
        ArrayList<GuiPlayerInfo> assaultAttackersList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> assaultDefendersList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> fondaInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> coFondaInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> respAdminInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> respCommInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> respGameInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> adminInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> supermodoInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> modoPlusInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> modoInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> modoTestInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> guideInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> ytbInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> premiumInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> legendeInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> herosInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> othersInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> joueurInfoList = new ArrayList<GuiPlayerInfo>();
        ArrayList<GuiPlayerInfo> countryInfoList = new ArrayList<GuiPlayerInfo>();
        int width = 0;
        for (GuiPlayerInfo playerInfo : allInfoList) {
            ScorePlayerTeam team = this.mc.field_71441_e.func_96441_U().func_96509_i(playerInfo.field_78831_a);
            String displayName = ScorePlayerTeam.func_96667_a((Team)team, (String)playerInfo.field_78831_a);
            Tuple<String, ArrayList<AbstractChatTag>> tuple = ChatTagManager.parseChatLine(displayName);
            String nameToDisplay = (String)tuple.a;
            StringBuilder stringBuilder = new StringBuilder();
            int nameWidth = 0;
            int tagIndex = 0;
            for (int j = 0; j < nameToDisplay.length(); ++j) {
                char c = nameToDisplay.charAt(j);
                if (c == ChatTagManager.REF_CHAR) {
                    if (stringBuilder.length() > 0) {
                        nameWidth += this.mc.field_71466_p.func_78256_a(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    }
                    AbstractChatTag chatTag = (AbstractChatTag)((Object)((ArrayList)tuple.b).get(tagIndex));
                    nameWidth += chatTag.getWidth();
                    ++tagIndex;
                    continue;
                }
                stringBuilder.append(c);
            }
            if (stringBuilder.length() > 0) {
                nameWidth += this.mc.field_71466_p.func_78256_a(stringBuilder.toString());
            }
            width = Math.max(width, nameWidth);
            String countryName = "";
            Pattern pattern = Pattern.compile(".*country=\"(\\w+)\".*");
            Matcher matcher = pattern.matcher(displayName);
            if (matcher.find()) {
                countryName = matcher.group(1);
            }
            if (!countryName.isEmpty() && countryName.equals(ClientData.currentFaction)) {
                countryInfoList.add(playerInfo);
            }
            if (!ClientData.currentAssault.isEmpty() && !countryName.isEmpty() && (countryName.equals(ClientData.currentAssault.get("attackerFactionName")) || ClientData.currentAssault.get("attackerHelpersName").contains(countryName))) {
                assaultAttackersList.add(playerInfo);
                continue;
            }
            if (!ClientData.currentAssault.isEmpty() && !countryName.isEmpty() && (countryName.equals(ClientData.currentAssault.get("defenderFactionName")) || ClientData.currentAssault.get("defenderHelpersName").contains(countryName))) {
                assaultDefendersList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"fondateur(\"|_prime).*") || displayName.toLowerCase().matches(".*\"founder(\"|_prime).*")) {
                fondaInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"co-fonda(\"|_prime).*") || displayName.toLowerCase().matches(".*\"co-founder(\"|_prime).*")) {
                coFondaInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"respadmin(\"|_prime).*") || displayName.toLowerCase().matches(".*\"adminmanager(\"|_prime).*")) {
                respAdminInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"respcomm(\"|_prime).*") || displayName.toLowerCase().matches(".*\"commmanager(\"|_prime).*")) {
                respCommInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"respgameplay(\"|_prime).*") || displayName.toLowerCase().matches(".*\"gameplaymanager(\"|_prime).*")) {
                respGameInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"admin(\"|_prime).*")) {
                adminInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"supermodo(\"|_prime).*") || displayName.toLowerCase().matches(".*\"supermod(\"|_prime).*")) {
                supermodoInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"moderateur_plus(\"|_prime).*") || displayName.toLowerCase().matches(".*\"mod_plus(\"|_prime).*")) {
                modoPlusInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"moderateur(\"|_prime).*") || displayName.toLowerCase().matches(".*\"mod(\"|_prime).*")) {
                modoInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"moderateur_test(\"|_prime).*") || displayName.toLowerCase().matches(".*\"mod_test(\"|_prime).*")) {
                modoTestInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"guide(\"|_prime).*")) {
                guideInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"affiliate(\"|_prime).*")) {
                ytbInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"premium(\"|_prime).*")) {
                premiumInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"legende(\"|_prime).*") || displayName.toLowerCase().matches(".*\"legend(\"|_prime).*")) {
                legendeInfoList.add(playerInfo);
                continue;
            }
            if (displayName.toLowerCase().matches(".*\"heros(\"|_prime).*") || displayName.toLowerCase().matches(".*\"hero(\"|_prime).*")) {
                herosInfoList.add(playerInfo);
                continue;
            }
            if (!displayName.toLowerCase().matches(".*\"joueur(\"|_prime).*") && !displayName.toLowerCase().matches(".*\"player(\"|_prime).*")) {
                othersInfoList.add(playerInfo);
                continue;
            }
            joueurInfoList.add(playerInfo);
        }
        orderedPlayerInfoList.addAll(assaultAttackersList);
        orderedPlayerInfoList.addAll(assaultDefendersList);
        orderedPlayerInfoList.addAll(ytbInfoList);
        orderedPlayerInfoList.addAll(premiumInfoList);
        orderedPlayerInfoList.addAll(legendeInfoList);
        orderedPlayerInfoList.addAll(herosInfoList);
        orderedPlayerInfoList.addAll(othersInfoList);
        orderedPlayerInfoList.addAll(joueurInfoList);
        orderedStaffInfoList.addAll(fondaInfoList);
        orderedStaffInfoList.addAll(coFondaInfoList);
        orderedStaffInfoList.addAll(respAdminInfoList);
        orderedStaffInfoList.addAll(respGameInfoList);
        orderedStaffInfoList.addAll(respCommInfoList);
        orderedStaffInfoList.addAll(adminInfoList);
        orderedStaffInfoList.addAll(supermodoInfoList);
        orderedStaffInfoList.addAll(modoPlusInfoList);
        orderedStaffInfoList.addAll(modoInfoList);
        orderedStaffInfoList.addAll(modoTestInfoList);
        orderedStaffInfoList.addAll(guideInfoList);
        allInfoList = orderedPlayerInfoList;
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.tab.staff"), resolution.func_78326_a() / 2 - 240, 6.0f, 0xFFFFFF, 0.5f, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(orderedStaffInfoList.size() + "", (float)(resolution.func_78326_a() / 2 - 240 + 4) + this.dg22.getStringWidth(I18n.func_135053_a((String)"overlay.tab.staff")) * 0.5f, 7.0f, 0xBABADA, 0.5f, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0f);
        ModernGui.drawRoundedRectangle(resolution.func_78326_a() / 2 - 240, 17.0f, this.field_73735_i, 119.0f, 107.0f);
        float offsetYStaff = 0.0f;
        int totalHeight = orderedStaffInfoList.size() * 9 - 90 - 10;
        float animDuration = Math.abs(totalHeight * 250);
        if (totalHeight > 0) {
            if (sideAnimationStaff.equals("down")) {
                offsetYStaff = (float)(System.currentTimeMillis() - startAnimationStaff) / animDuration * (float)totalHeight;
                if (offsetYStaff >= (float)totalHeight) {
                    startAnimationStaff = System.currentTimeMillis();
                    sideAnimationStaff = "up";
                }
            } else {
                offsetYStaff = (float)totalHeight - (float)(System.currentTimeMillis() - startAnimationStaff) / animDuration * (float)totalHeight;
                if (offsetYStaff <= 0.0f) {
                    startAnimationStaff = System.currentTimeMillis();
                    sideAnimationStaff = "down";
                }
            }
        }
        GUIUtils.startGLScissor(resolution.func_78326_a() / 2 - 240, 17, 119, 104);
        for (int i2 = 0; i2 < Math.min(60, orderedStaffInfoList.size()); ++i2) {
            int xPos = resolution.func_78326_a() / 2 - 240 + 5;
            float yPos = (float)(22 + i2 * 9) - offsetYStaff;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3042);
            GuiPlayerInfo playerInfo = (GuiPlayerInfo)orderedStaffInfoList.get(i2);
            String playerName = playerInfo.field_78831_a.split(" ")[playerInfo.field_78831_a.split(" ").length - 1];
            ScorePlayerTeam team = this.mc.field_71441_e.func_96441_U().func_96509_i(playerInfo.field_78831_a);
            String displayName = ScorePlayerTeam.func_96667_a((Team)team, (String)playerInfo.field_78831_a);
            Tuple<String, ArrayList<AbstractChatTag>> tuple = ChatTagManager.parseChatLine(displayName);
            String nameToDisplay = (String)tuple.a;
            StringBuilder stringBuilder = new StringBuilder();
            int xOffset = 0;
            int tagIndex = 0;
            for (int j = 0; j < nameToDisplay.length(); ++j) {
                char c = nameToDisplay.charAt(j);
                if (c == ChatTagManager.REF_CHAR) {
                    if (stringBuilder.length() > 0) {
                        String txt = stringBuilder.toString();
                        ModernGui.drawScaledStringCustomFont(txt, xPos + xOffset, yPos, 0xFFFFFF, 0.5f, "left", true, "georamaMedium", 22);
                        xOffset += this.mc.field_71466_p.func_78256_a(txt);
                        stringBuilder = new StringBuilder();
                    }
                    AbstractChatTag chatTag = (AbstractChatTag)((Object)((ArrayList)tuple.b).get(tagIndex));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xPos + xOffset), (double)(yPos + 0.6f), (double)0.0);
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
                    chatTag.render(-1, -1);
                    GL11.glPopMatrix();
                    xOffset = (int)((float)xOffset + (float)chatTag.getWidth() * 0.75f);
                    ++tagIndex;
                    continue;
                }
                stringBuilder.append(c);
            }
            if (stringBuilder.length() <= 0) continue;
            ModernGui.drawScaledStringCustomFont(stringBuilder.toString(), xPos + xOffset + 1, yPos - 0.1f, 0xFFFFFF, 0.5f, "left", true, "georamaMedium", 32);
        }
        GUIUtils.endGLScissor();
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.tab.my_country"), resolution.func_78326_a() / 2 - 240, 128.0f, 0xFFFFFF, 0.5f, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(countryInfoList.size() + "", (float)(resolution.func_78326_a() / 2 - 240 + 4) + this.dg22.getStringWidth(I18n.func_135053_a((String)"overlay.tab.my_country")) * 0.5f, 129.0f, 0xBABADA, 0.5f, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0f);
        ModernGui.drawRoundedRectangle(resolution.func_78326_a() / 2 - 240, 139.0f, this.field_73735_i, 119.0f, 67.0f);
        GUIUtils.startGLScissor(resolution.func_78326_a() / 2 - 240, 140, 119, 66);
        float offsetYCountry = 0.0f;
        float totalHeightCountry = countryInfoList.size() * 9 - 54 - 10;
        float animDurationCountry = Math.abs(totalHeightCountry * 250.0f);
        if (totalHeightCountry > 0.0f) {
            if (sideAnimationCountry.equals("down")) {
                offsetYCountry = (float)(System.currentTimeMillis() - startAnimationCountry) / animDurationCountry * totalHeightCountry;
                if (offsetYCountry >= totalHeightCountry) {
                    startAnimationCountry = System.currentTimeMillis();
                    sideAnimationCountry = "up";
                }
            } else {
                offsetYCountry = totalHeightCountry - (float)(System.currentTimeMillis() - startAnimationCountry) / animDurationCountry * totalHeightCountry;
                if (offsetYCountry <= 0.0f) {
                    startAnimationCountry = System.currentTimeMillis();
                    sideAnimationCountry = "down";
                }
            }
        }
        for (i = 0; i < Math.min(60, countryInfoList.size()); ++i) {
            int xPos = resolution.func_78326_a() / 2 - 240 + 5;
            float yPos = (float)(143 + i * 9) - offsetYCountry;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3042);
            GuiPlayerInfo playerInfo = (GuiPlayerInfo)countryInfoList.get(i);
            String playerName = playerInfo.field_78831_a.split(" ")[playerInfo.field_78831_a.split(" ").length - 1];
            if (!ClientEventHandler.playersFaction.containsKey(playerName)) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(playerName)));
                ClientEventHandler.playersFaction.put(playerName, "");
            }
            if (ClientEventHandler.playersFaction.containsKey(playerName) && !ClientEventHandler.playersFaction.get(playerName).equals("")) {
                ClientProxy.loadCountryFlag(ClientEventHandler.playersFaction.get(playerName));
                if (ClientProxy.flagsTexture.containsKey(ClientEventHandler.playersFaction.get(playerName))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ClientEventHandler.playersFaction.get(playerName)).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(xPos, yPos, 0.0f, 0.0f, 156, 78, 11, 7, 156.0f, 78.0f, false);
                    xPos += 13;
                }
            }
            ScorePlayerTeam team = this.mc.field_71441_e.func_96441_U().func_96509_i(playerInfo.field_78831_a);
            String displayName = ScorePlayerTeam.func_96667_a((Team)team, (String)playerInfo.field_78831_a);
            Tuple<String, ArrayList<AbstractChatTag>> tuple = ChatTagManager.parseChatLine(displayName);
            String nameToDisplay = (String)tuple.a;
            StringBuilder stringBuilder = new StringBuilder();
            int xOffset = 0;
            int tagIndex = 0;
            for (int j = 0; j < nameToDisplay.length(); ++j) {
                char c = nameToDisplay.charAt(j);
                if (c == ChatTagManager.REF_CHAR) {
                    if (stringBuilder.length() > 0) {
                        String txt = stringBuilder.toString();
                        ModernGui.drawScaledStringCustomFont(txt, xPos + xOffset, yPos, 0xFFFFFF, 0.5f, "left", true, "georamaMedium", 22);
                        xOffset += this.mc.field_71466_p.func_78256_a(txt);
                        stringBuilder = new StringBuilder();
                    }
                    AbstractChatTag chatTag = (AbstractChatTag)((Object)((ArrayList)tuple.b).get(tagIndex));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xPos + xOffset), (double)(yPos + 0.6f), (double)0.0);
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
                    chatTag.render(-1, -1);
                    GL11.glPopMatrix();
                    xOffset = (int)((float)xOffset + (float)chatTag.getWidth() * 0.75f);
                    ++tagIndex;
                    continue;
                }
                stringBuilder.append(c);
            }
            if (stringBuilder.length() <= 0) continue;
            ModernGui.drawScaledStringCustomFont(stringBuilder.toString().length() < 14 ? stringBuilder.toString() : stringBuilder.toString().substring(0, 14) + "..", xPos + xOffset + 1, yPos - 0.1f, 0xFFFFFF, 0.5f, "left", true, "georamaMedium", 32);
        }
        GUIUtils.endGLScissor();
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.tab.players"), resolution.func_78326_a() / 2 - 118, 6.0f, 0xFFFFFF, 0.5f, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(allInfoList.size() + "", (float)(resolution.func_78326_a() / 2 - 118 + 4) + this.dg22.getStringWidth(I18n.func_135053_a((String)"overlay.tab.players")) * 0.5f, 6.7f, 0xBABADA, 0.5f, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0f);
        ModernGui.drawRoundedRectangle(resolution.func_78326_a() / 2 - 118, 17.0f, this.field_73735_i, 360.0f, 189.0f);
        for (i = 0; i < Math.min(60, allInfoList.size()); ++i) {
            int xIndex = i % 3;
            int yIndex = i / 3;
            int xPos = resolution.func_78326_a() / 2 - 118 + 5 + xIndex * 115;
            int yPos = 22 + yIndex * 9;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3042);
            GuiPlayerInfo playerInfo = (GuiPlayerInfo)allInfoList.get(i);
            String playerName = playerInfo.field_78831_a.split(" ")[playerInfo.field_78831_a.split(" ").length - 1];
            if (!ClientEventHandler.playersFaction.containsKey(playerName)) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(playerName)));
                ClientEventHandler.playersFaction.put(playerName, "");
            }
            if (ClientEventHandler.playersFaction.containsKey(playerName) && !ClientEventHandler.playersFaction.get(playerName).equals("")) {
                ClientProxy.loadCountryFlag(ClientEventHandler.playersFaction.get(playerName));
                if (ClientProxy.flagsTexture.containsKey(ClientEventHandler.playersFaction.get(playerName))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ClientEventHandler.playersFaction.get(playerName)).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(xPos, yPos, 0.0f, 0.0f, 156, 78, 11, 7, 156.0f, 78.0f, false);
                    xPos += 13;
                }
            }
            ScorePlayerTeam team = this.mc.field_71441_e.func_96441_U().func_96509_i(playerInfo.field_78831_a);
            String displayName = ScorePlayerTeam.func_96667_a((Team)team, (String)playerInfo.field_78831_a);
            if (assaultAttackersList.contains(playerInfo)) {
                displayName = "\u00a7c\u00a7l[ATT]\u00a7r\u00a7c " + playerName;
            } else if (assaultDefendersList.contains(playerInfo)) {
                displayName = "\u00a79\u00a7l[DEF] \u00a7r\u00a79" + playerName;
            }
            if (ClientProxy.serverType.equals("ng") && ClientData.eventsInfos.size() > 0) {
                boolean foundTeam = false;
                for (HashMap<String, Object> eventData : ClientData.eventsInfos) {
                    List eventTeams = (List)eventData.get("teams");
                    if (eventTeams != null) {
                        for (LinkedTreeMap eventTeam : eventTeams) {
                            List players = (List)eventTeam.get((Object)"players");
                            if (!players.contains(playerName)) continue;
                            displayName = displayName.replaceAll(playerName, eventTeam.get((Object)"color") + "[" + (!eventTeam.get((Object)"displayName").equals("") ? eventTeam.get((Object)"displayName") : eventTeam.get((Object)"name")) + "] " + playerName);
                            foundTeam = true;
                            break;
                        }
                    }
                    if (foundTeam) continue;
                    displayName = displayName.replaceAll(playerName, "\u00a77[" + (!eventData.get("displayName").equals("") ? eventData.get("displayName") : eventData.get("name")) + "] " + playerName);
                }
            }
            Tuple<String, ArrayList<AbstractChatTag>> tuple = ChatTagManager.parseChatLine(displayName);
            String nameToDisplay = (String)tuple.a;
            StringBuilder stringBuilder = new StringBuilder();
            int xOffset = 0;
            int tagIndex = 0;
            for (int j = 0; j < nameToDisplay.length(); ++j) {
                char c = nameToDisplay.charAt(j);
                if (c == ChatTagManager.REF_CHAR) {
                    if (stringBuilder.length() > 0) {
                        String txt = stringBuilder.toString();
                        ModernGui.drawScaledStringCustomFont(txt, xPos + xOffset, yPos, 0xFFFFFF, 0.5f, "left", true, "georamaMedium", 22);
                        xOffset += this.mc.field_71466_p.func_78256_a(txt);
                        stringBuilder = new StringBuilder();
                    }
                    AbstractChatTag chatTag = (AbstractChatTag)((Object)((ArrayList)tuple.b).get(tagIndex));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xPos + xOffset), (double)((float)yPos + 0.6f), (double)0.0);
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
                    chatTag.render(-1, -1);
                    GL11.glPopMatrix();
                    xOffset = (int)((float)xOffset + (float)chatTag.getWidth() * 0.75f);
                    ++tagIndex;
                    continue;
                }
                stringBuilder.append(c);
            }
            if (stringBuilder.length() <= 0) continue;
            ModernGui.drawScaledStringCustomFont(stringBuilder.toString(), xPos + xOffset + 1, (float)yPos - 0.1f, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 32);
        }
        ModernGui.drawScaledStringCustomFont("NATIONSGLORY " + ClientProxy.currentServerName.toUpperCase(), resolution.func_78326_a() / 2 - 118 + 360, 6.0f, 0xFFFFFF, 0.4f, "right", true, "minecraftDungeons", 30);
    }

    private void drawPing(int x, int width, int y, GuiPlayerInfo playerInfo) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (playerInfo.field_78829_b == -42) {
            this.mc.func_110434_K().func_110577_a(PHONE_ICON);
            ModernGui.drawModalRectWithCustomSizedTexture(x + width - 11, y, 0, 0, 10, 8, 10.0f, 8.0f, true);
        } else {
            this.mc.func_110434_K().func_110577_a(Gui.field_110324_m);
            int ping = playerInfo.field_78829_b < 0 ? 5 : (playerInfo.field_78829_b < 150 ? 0 : (playerInfo.field_78829_b < 300 ? 1 : (playerInfo.field_78829_b < 600 ? 2 : (playerInfo.field_78829_b < 1000 ? 3 : 4))));
            this.field_73735_i += 100.0f;
            this.func_73729_b(x + width - 11, y, 0, 176 + ping * 8, 10, 8);
            this.field_73735_i -= 100.0f;
        }
    }
}

