package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerListGUI extends Gui
{
    private Minecraft mc;
    public static final ResourceLocation PHONE_ICON = new ResourceLocation("nationsgui", "textures/gui/phone.png");
    public final CFontRenderer dg22;
    public static String topText = "";
    public static String bottomText = "";
    private int currentOffsetCountry = 0;
    public static Long startAnimationCountry = Long.valueOf(0L);
    public static Long startAnimationStaff = Long.valueOf(0L);
    public static String sideAnimationStaff = "down";
    public static String sideAnimationCountry = "down";

    public PlayerListGUI(Minecraft mc)
    {
        this.mc = mc;
        this.dg22 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(22));
        startAnimationCountry = Long.valueOf(System.currentTimeMillis());
        startAnimationStaff = Long.valueOf(System.currentTimeMillis());
        sideAnimationStaff = "down";
        sideAnimationCountry = "down";
    }

    public void renderPlayerList(ScaledResolution resolution)
    {
        NetClientHandler clientHandler = this.mc.thePlayer.sendQueue;
        List allInfoList = clientHandler.playerInfoList;
        ArrayList orderedPlayerInfoList = new ArrayList();
        ArrayList orderedStaffInfoList = new ArrayList();
        ArrayList assaultAttackersList = new ArrayList();
        ArrayList assaultDefendersList = new ArrayList();
        ArrayList fondaInfoList = new ArrayList();
        ArrayList coFondaInfoList = new ArrayList();
        ArrayList respAdminInfoList = new ArrayList();
        ArrayList respCommInfoList = new ArrayList();
        ArrayList respGameInfoList = new ArrayList();
        ArrayList adminInfoList = new ArrayList();
        ArrayList supermodoInfoList = new ArrayList();
        ArrayList modoPlusInfoList = new ArrayList();
        ArrayList modoInfoList = new ArrayList();
        ArrayList modoTestInfoList = new ArrayList();
        ArrayList guideInfoList = new ArrayList();
        ArrayList ytbInfoList = new ArrayList();
        ArrayList premiumInfoList = new ArrayList();
        ArrayList legendeInfoList = new ArrayList();
        ArrayList herosInfoList = new ArrayList();
        ArrayList othersInfoList = new ArrayList();
        ArrayList joueurInfoList = new ArrayList();
        ArrayList countryInfoList = new ArrayList();
        int width = 0;
        Iterator offsetYStaff = allInfoList.iterator();
        int xIndex;
        int yIndex;
        int xPos;
        String var64;

        while (offsetYStaff.hasNext())
        {
            GuiPlayerInfo totalHeight = (GuiPlayerInfo)offsetYStaff.next();
            ScorePlayerTeam animDuration = this.mc.theWorld.getScoreboard().getPlayersTeam(totalHeight.name);
            String offsetYCountry = ScorePlayerTeam.formatPlayerName(animDuration, totalHeight.name);
            Tuple totalHeightCountry = ChatTagManager.parseChatLine(offsetYCountry);
            String animDurationCountry = (String)totalHeightCountry.a;
            StringBuilder i = new StringBuilder();
            xIndex = 0;
            yIndex = 0;

            for (xPos = 0; xPos < animDurationCountry.length(); ++xPos)
            {
                char yPos = animDurationCountry.charAt(xPos);

                if (yPos == ChatTagManager.REF_CHAR)
                {
                    if (i.length() > 0)
                    {
                        xIndex += this.mc.fontRenderer.getStringWidth(i.toString());
                        i = new StringBuilder();
                    }

                    AbstractChatTag playerInfo = (AbstractChatTag)((ArrayList)totalHeightCountry.b).get(yIndex);
                    xIndex += playerInfo.getWidth();
                    ++yIndex;
                }
                else
                {
                    i.append(yPos);
                }
            }

            if (i.length() > 0)
            {
                xIndex += this.mc.fontRenderer.getStringWidth(i.toString());
            }

            width = Math.max(width, xIndex);
            var64 = "";
            Pattern var65 = Pattern.compile(".*country=\"(\\w+)\".*");
            Matcher var69 = var65.matcher(offsetYCountry);

            if (var69.find())
            {
                var64 = var69.group(1);
            }

            if (!var64.isEmpty() && var64.equals(ClientData.currentFaction))
            {
                countryInfoList.add(totalHeight);
            }

            if (!ClientData.currentAssault.isEmpty() && !var64.isEmpty() && (var64.equals(ClientData.currentAssault.get("attackerFactionName")) || ((String)ClientData.currentAssault.get("attackerHelpersName")).contains(var64)))
            {
                assaultAttackersList.add(totalHeight);
            }
            else if (!ClientData.currentAssault.isEmpty() && !var64.isEmpty() && (var64.equals(ClientData.currentAssault.get("defenderFactionName")) || ((String)ClientData.currentAssault.get("defenderHelpersName")).contains(var64)))
            {
                assaultDefendersList.add(totalHeight);
            }
            else if (!offsetYCountry.toLowerCase().matches(".*\"fondateur(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"founder(\"|_prime).*"))
            {
                if (!offsetYCountry.toLowerCase().matches(".*\"co-fonda(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"co-founder(\"|_prime).*"))
                {
                    if (!offsetYCountry.toLowerCase().matches(".*\"respadmin(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"adminmanager(\"|_prime).*"))
                    {
                        if (!offsetYCountry.toLowerCase().matches(".*\"respcomm(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"commmanager(\"|_prime).*"))
                        {
                            if (!offsetYCountry.toLowerCase().matches(".*\"respgameplay(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"gameplaymanager(\"|_prime).*"))
                            {
                                if (offsetYCountry.toLowerCase().matches(".*\"admin(\"|_prime).*"))
                                {
                                    adminInfoList.add(totalHeight);
                                }
                                else if (!offsetYCountry.toLowerCase().matches(".*\"supermodo(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"supermod(\"|_prime).*"))
                                {
                                    if (!offsetYCountry.toLowerCase().matches(".*\"moderateur_plus(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"mod_plus(\"|_prime).*"))
                                    {
                                        if (!offsetYCountry.toLowerCase().matches(".*\"moderateur(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"mod(\"|_prime).*"))
                                        {
                                            if (!offsetYCountry.toLowerCase().matches(".*\"moderateur_test(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"mod_test(\"|_prime).*"))
                                            {
                                                if (offsetYCountry.toLowerCase().matches(".*\"guide(\"|_prime).*"))
                                                {
                                                    guideInfoList.add(totalHeight);
                                                }
                                                else if (offsetYCountry.toLowerCase().matches(".*\"affiliate(\"|_prime).*"))
                                                {
                                                    ytbInfoList.add(totalHeight);
                                                }
                                                else if (offsetYCountry.toLowerCase().matches(".*\"premium(\"|_prime).*"))
                                                {
                                                    premiumInfoList.add(totalHeight);
                                                }
                                                else if (!offsetYCountry.toLowerCase().matches(".*\"legende(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"legend(\"|_prime).*"))
                                                {
                                                    if (!offsetYCountry.toLowerCase().matches(".*\"heros(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"hero(\"|_prime).*"))
                                                    {
                                                        if (!offsetYCountry.toLowerCase().matches(".*\"joueur(\"|_prime).*") && !offsetYCountry.toLowerCase().matches(".*\"player(\"|_prime).*"))
                                                        {
                                                            othersInfoList.add(totalHeight);
                                                        }
                                                        else
                                                        {
                                                            joueurInfoList.add(totalHeight);
                                                        }
                                                    }
                                                    else
                                                    {
                                                        herosInfoList.add(totalHeight);
                                                    }
                                                }
                                                else
                                                {
                                                    legendeInfoList.add(totalHeight);
                                                }
                                            }
                                            else
                                            {
                                                modoTestInfoList.add(totalHeight);
                                            }
                                        }
                                        else
                                        {
                                            modoInfoList.add(totalHeight);
                                        }
                                    }
                                    else
                                    {
                                        modoPlusInfoList.add(totalHeight);
                                    }
                                }
                                else
                                {
                                    supermodoInfoList.add(totalHeight);
                                }
                            }
                            else
                            {
                                respGameInfoList.add(totalHeight);
                            }
                        }
                        else
                        {
                            respCommInfoList.add(totalHeight);
                        }
                    }
                    else
                    {
                        respAdminInfoList.add(totalHeight);
                    }
                }
                else
                {
                    coFondaInfoList.add(totalHeight);
                }
            }
            else
            {
                fondaInfoList.add(totalHeight);
            }
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
        ArrayList var50 = orderedPlayerInfoList;
        ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.tab.staff"), (float)(resolution.getScaledWidth() / 2 - 240), 6.0F, 16777215, 0.5F, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(orderedStaffInfoList.size() + "", (float)(resolution.getScaledWidth() / 2 - 240 + 4) + this.dg22.getStringWidth(I18n.getString("overlay.tab.staff")) * 0.5F, 7.0F, 12237530, 0.5F, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0F);
        ModernGui.drawRoundedRectangle((float)(resolution.getScaledWidth() / 2 - 240), 17.0F, this.zLevel, 119.0F, 107.0F);
        float var51 = 0.0F;
        int var52 = orderedStaffInfoList.size() * 9 - 90 - 10;
        float var53 = (float)Math.abs(var52 * 250);

        if (var52 > 0)
        {
            if (sideAnimationStaff.equals("down"))
            {
                var51 = (float)(System.currentTimeMillis() - startAnimationStaff.longValue()) / var53 * (float)var52;

                if (var51 >= (float)var52)
                {
                    startAnimationStaff = Long.valueOf(System.currentTimeMillis());
                    sideAnimationStaff = "up";
                }
            }
            else
            {
                var51 = (float)var52 - (float)(System.currentTimeMillis() - startAnimationStaff.longValue()) / var53 * (float)var52;

                if (var51 <= 0.0F)
                {
                    startAnimationStaff = Long.valueOf(System.currentTimeMillis());
                    sideAnimationStaff = "down";
                }
            }
        }

        GUIUtils.startGLScissor(resolution.getScaledWidth() / 2 - 240, 17, 119, 104);
        float var58;

        for (int var54 = 0; var54 < Math.min(60, orderedStaffInfoList.size()); ++var54)
        {
            int var56 = resolution.getScaledWidth() / 2 - 240 + 5;
            var58 = (float)(22 + var54 * 9) - var51;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GuiPlayerInfo var59 = (GuiPlayerInfo)orderedStaffInfoList.get(var54);
            String var62 = var59.name.split(" ")[var59.name.split(" ").length - 1];
            ScorePlayerTeam var61 = this.mc.theWorld.getScoreboard().getPlayersTeam(var59.name);
            var64 = ScorePlayerTeam.formatPlayerName(var61, var59.name);
            Tuple var66 = ChatTagManager.parseChatLine(var64);
            String var71 = (String)var66.a;
            StringBuilder playerName = new StringBuilder();
            int team = 0;
            int displayName = 0;

            for (int tuple = 0; tuple < var71.length(); ++tuple)
            {
                char nameToDisplay = var71.charAt(tuple);

                if (nameToDisplay == ChatTagManager.REF_CHAR)
                {
                    if (playerName.length() > 0)
                    {
                        String stringBuilder = playerName.toString();
                        ModernGui.drawScaledStringCustomFont(stringBuilder, (float)(var56 + team), var58, 16777215, 0.5F, "left", true, "georamaMedium", 22);
                        team += this.mc.fontRenderer.getStringWidth(stringBuilder);
                        playerName = new StringBuilder();
                    }

                    AbstractChatTag var81 = (AbstractChatTag)((ArrayList)var66.b).get(displayName);
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(var56 + team), (double)(var58 + 0.6F), 0.0D);
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    GL11.glScalef(0.75F, 0.75F, 0.75F);
                    var81.render(-1, -1);
                    GL11.glPopMatrix();
                    team = (int)((float)team + (float)var81.getWidth() * 0.75F);
                    ++displayName;
                }
                else
                {
                    playerName.append(nameToDisplay);
                }
            }

            if (playerName.length() > 0)
            {
                ModernGui.drawScaledStringCustomFont(playerName.toString(), (float)(var56 + team + 1), var58 - 0.1F, 16777215, 0.5F, "left", true, "georamaMedium", 32);
            }
        }

        GUIUtils.endGLScissor();
        ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.tab.my_country"), (float)(resolution.getScaledWidth() / 2 - 240), 128.0F, 16777215, 0.5F, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(countryInfoList.size() + "", (float)(resolution.getScaledWidth() / 2 - 240 + 4) + this.dg22.getStringWidth(I18n.getString("overlay.tab.my_country")) * 0.5F, 129.0F, 12237530, 0.5F, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0F);
        ModernGui.drawRoundedRectangle((float)(resolution.getScaledWidth() / 2 - 240), 139.0F, this.zLevel, 119.0F, 67.0F);
        GUIUtils.startGLScissor(resolution.getScaledWidth() / 2 - 240, 140, 119, 66);
        float var55 = 0.0F;
        float var57 = (float)(countryInfoList.size() * 9 - 54 - 10);
        var58 = Math.abs(var57 * 250.0F);

        if (var57 > 0.0F)
        {
            if (sideAnimationCountry.equals("down"))
            {
                var55 = (float)(System.currentTimeMillis() - startAnimationCountry.longValue()) / var58 * var57;

                if (var55 >= var57)
                {
                    startAnimationCountry = Long.valueOf(System.currentTimeMillis());
                    sideAnimationCountry = "up";
                }
            }
            else
            {
                var55 = var57 - (float)(System.currentTimeMillis() - startAnimationCountry.longValue()) / var58 * var57;

                if (var55 <= 0.0F)
                {
                    startAnimationCountry = Long.valueOf(System.currentTimeMillis());
                    sideAnimationCountry = "down";
                }
            }
        }

        int xOffset;
        int var60;
        String var74;
        String var76;

        for (var60 = 0; var60 < Math.min(60, countryInfoList.size()); ++var60)
        {
            xIndex = resolution.getScaledWidth() / 2 - 240 + 5;
            float var63 = (float)(143 + var60 * 9) - var55;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GuiPlayerInfo var67 = (GuiPlayerInfo)countryInfoList.get(var60);
            String var68 = var67.name.split(" ")[var67.name.split(" ").length - 1];

            if (!ClientEventHandler.playersFaction.containsKey(var68))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(var68)));
                ClientEventHandler.playersFaction.put(var68, "");
            }

            if (ClientEventHandler.playersFaction.containsKey(var68) && !((String)ClientEventHandler.playersFaction.get(var68)).equals(""))
            {
                ClientProxy.loadCountryFlag((String)ClientEventHandler.playersFaction.get(var68));

                if (ClientProxy.flagsTexture.containsKey(ClientEventHandler.playersFaction.get(var68)))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(ClientEventHandler.playersFaction.get(var68))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)xIndex, var63, 0.0F, 0.0F, 156, 78, 11, 7, 156.0F, 78.0F, false);
                    xIndex += 13;
                }
            }

            ScorePlayerTeam var72 = this.mc.theWorld.getScoreboard().getPlayersTeam(var67.name);
            var74 = ScorePlayerTeam.formatPlayerName(var72, var67.name);
            Tuple var75 = ChatTagManager.parseChatLine(var74);
            var76 = (String)var75.a;
            StringBuilder var78 = new StringBuilder();
            int var79 = 0;
            int var83 = 0;

            for (xOffset = 0; xOffset < var76.length(); ++xOffset)
            {
                char tagIndex = var76.charAt(xOffset);

                if (tagIndex == ChatTagManager.REF_CHAR)
                {
                    if (var78.length() > 0)
                    {
                        String j = var78.toString();
                        ModernGui.drawScaledStringCustomFont(j, (float)(xIndex + var79), var63, 16777215, 0.5F, "left", true, "georamaMedium", 22);
                        var79 += this.mc.fontRenderer.getStringWidth(j);
                        var78 = new StringBuilder();
                    }

                    AbstractChatTag var91 = (AbstractChatTag)((ArrayList)var75.b).get(var83);
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xIndex + var79), (double)(var63 + 0.6F), 0.0D);
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    GL11.glScalef(0.75F, 0.75F, 0.75F);
                    var91.render(-1, -1);
                    GL11.glPopMatrix();
                    var79 = (int)((float)var79 + (float)var91.getWidth() * 0.75F);
                    ++var83;
                }
                else
                {
                    var78.append(tagIndex);
                }
            }

            if (var78.length() > 0)
            {
                ModernGui.drawScaledStringCustomFont(var78.toString().length() < 14 ? var78.toString() : var78.toString().substring(0, 14) + "..", (float)(xIndex + var79 + 1), var63 - 0.1F, 16777215, 0.5F, "left", true, "georamaMedium", 32);
            }
        }

        GUIUtils.endGLScissor();
        ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.tab.players"), (float)(resolution.getScaledWidth() / 2 - 118), 6.0F, 16777215, 0.5F, "left", true, "minecraftDungeons", 22);
        ModernGui.drawScaledStringCustomFont(orderedPlayerInfoList.size() + "", (float)(resolution.getScaledWidth() / 2 - 118 + 4) + this.dg22.getStringWidth(I18n.getString("overlay.tab.players")) * 0.5F, 6.7F, 12237530, 0.5F, "left", true, "georamaSemiBold", 28);
        ModernGui.glColorHex(-1289411259, 1.0F);
        ModernGui.drawRoundedRectangle((float)(resolution.getScaledWidth() / 2 - 118), 17.0F, this.zLevel, 360.0F, 189.0F);

        for (var60 = 0; var60 < Math.min(60, var50.size()); ++var60)
        {
            xIndex = var60 % 3;
            yIndex = var60 / 3;
            xPos = resolution.getScaledWidth() / 2 - 118 + 5 + xIndex * 115;
            int var70 = 22 + yIndex * 9;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GuiPlayerInfo var73 = (GuiPlayerInfo)var50.get(var60);
            var74 = var73.name.split(" ")[var73.name.split(" ").length - 1];

            if (!ClientEventHandler.playersFaction.containsKey(var74))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(var74)));
                ClientEventHandler.playersFaction.put(var74, "");
            }

            if (ClientEventHandler.playersFaction.containsKey(var74) && !((String)ClientEventHandler.playersFaction.get(var74)).equals(""))
            {
                ClientProxy.loadCountryFlag((String)ClientEventHandler.playersFaction.get(var74));

                if (ClientProxy.flagsTexture.containsKey(ClientEventHandler.playersFaction.get(var74)))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(ClientEventHandler.playersFaction.get(var74))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)xPos, (float)var70, 0.0F, 0.0F, 156, 78, 11, 7, 156.0F, 78.0F, false);
                    xPos += 13;
                }
            }

            ScorePlayerTeam var77 = this.mc.theWorld.getScoreboard().getPlayersTeam(var73.name);
            var76 = ScorePlayerTeam.formatPlayerName(var77, var73.name);

            if (assaultAttackersList.contains(var73))
            {
                var76 = "\u00a7c\u00a7l[ATT]\u00a7r\u00a7c " + var74;
            }
            else if (assaultDefendersList.contains(var73))
            {
                var76 = "\u00a79\u00a7l[DEF] \u00a7r\u00a79" + var74;
            }

            if (ClientProxy.serverType.equals("ng") && ClientData.eventsInfos.size() > 0)
            {
                boolean var80 = false;
                Iterator var84 = ClientData.eventsInfos.iterator();

                while (var84.hasNext())
                {
                    HashMap var86 = (HashMap)var84.next();
                    List var88 = (List)var86.get("teams");

                    if (var88 != null)
                    {
                        Iterator var89 = var88.iterator();

                        while (var89.hasNext())
                        {
                            LinkedTreeMap var92 = (LinkedTreeMap)var89.next();
                            List c = (List)var92.get("players");

                            if (c.contains(var74))
                            {
                                var76 = var76.replaceAll(var74, var92.get("color") + "[" + (!var92.get("displayName").equals("") ? var92.get("displayName") : var92.get("name")) + "] " + var74);
                                var80 = true;
                                break;
                            }
                        }
                    }

                    if (!var80)
                    {
                        var76 = var76.replaceAll(var74, "\u00a77[" + (!var86.get("displayName").equals("") ? var86.get("displayName") : var86.get("name")) + "] " + var74);
                    }
                }
            }

            Tuple var82 = ChatTagManager.parseChatLine(var76);
            String var85 = (String)var82.a;
            StringBuilder var87 = new StringBuilder();
            xOffset = 0;
            int var90 = 0;

            for (int var93 = 0; var93 < var85.length(); ++var93)
            {
                char var94 = var85.charAt(var93);

                if (var94 == ChatTagManager.REF_CHAR)
                {
                    if (var87.length() > 0)
                    {
                        String chatTag = var87.toString();
                        ModernGui.drawScaledStringCustomFont(chatTag, (float)(xPos + xOffset), (float)var70, 16777215, 0.5F, "left", true, "georamaMedium", 22);
                        xOffset += this.mc.fontRenderer.getStringWidth(chatTag);
                        var87 = new StringBuilder();
                    }

                    AbstractChatTag var95 = (AbstractChatTag)((ArrayList)var82.b).get(var90);
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xPos + xOffset), (double)((float)var70 + 0.6F), 0.0D);
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    GL11.glScalef(0.75F, 0.75F, 0.75F);
                    var95.render(-1, -1);
                    GL11.glPopMatrix();
                    xOffset = (int)((float)xOffset + (float)var95.getWidth() * 0.75F);
                    ++var90;
                }
                else
                {
                    var87.append(var94);
                }
            }

            if (var87.length() > 0)
            {
                ModernGui.drawScaledStringCustomFont(var87.toString(), (float)(xPos + xOffset + 1), (float)var70 - 0.1F, 16777215, 0.5F, "left", false, "georamaMedium", 32);
            }
        }

        ModernGui.drawScaledStringCustomFont("NATIONSGLORY " + ClientProxy.currentServerName.toUpperCase(), (float)(resolution.getScaledWidth() / 2 - 118 + 360), 6.0F, 16777215, 0.4F, "right", true, "minecraftDungeons", 30);
    }

    private void drawPing(int x, int width, int y, GuiPlayerInfo playerInfo)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (playerInfo.responseTime == -42)
        {
            this.mc.getTextureManager().bindTexture(PHONE_ICON);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(x + width - 11), (float)y, 0, 0, 10, 8, 10.0F, 8.0F, true);
        }
        else
        {
            this.mc.getTextureManager().bindTexture(Gui.icons);
            byte ping;

            if (playerInfo.responseTime < 0)
            {
                ping = 5;
            }
            else if (playerInfo.responseTime < 150)
            {
                ping = 0;
            }
            else if (playerInfo.responseTime < 300)
            {
                ping = 1;
            }
            else if (playerInfo.responseTime < 600)
            {
                ping = 2;
            }
            else if (playerInfo.responseTime < 1000)
            {
                ping = 3;
            }
            else
            {
                ping = 4;
            }

            this.zLevel += 100.0F;
            this.drawTexturedModalRect(x + width - 11, y, 0, 176 + ping * 8, 10, 8);
            this.zLevel -= 100.0F;
        }
    }
}
