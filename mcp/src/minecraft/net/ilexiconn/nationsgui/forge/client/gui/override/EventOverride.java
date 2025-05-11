/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EventOverride
extends Gui
implements ElementOverride {
    public static HashMap<String, String> cachedChronos = new HashMap();
    public static int cachedSCBHeight = 0;
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String chronoTimeToStr(Long chrono, boolean millis) {
        if (cachedChronos.containsKey(chrono + "#" + millis)) {
            return cachedChronos.get(chrono + "#" + millis);
        }
        long originalChrono = chrono;
        long hours = TimeUnit.MILLISECONDS.toHours(chrono);
        chrono = chrono - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono);
        chrono = chrono - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono);
        chrono = chrono - TimeUnit.SECONDS.toMillis(seconds);
        long milliseconds = chrono;
        String chronoStr = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
        if (millis) {
            chronoStr = chronoStr + String.format(" %03dms", milliseconds);
        }
        cachedChronos.put(originalChrono + "#" + millis, chronoStr);
        return chronoStr;
    }

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        boolean display = false;
        String title = "";
        if (ClientProxy.serverType.equals("ng")) {
            if (ClientData.eventsInfos.size() > 0) {
                HashMap eventData = (HashMap)ClientData.eventsInfos.get(0).clone();
                if (eventData.containsKey("isPlayerInRegion") && !Boolean.parseBoolean((String)eventData.get("isPlayerInRegion"))) {
                    return;
                }
                if (eventData.containsKey("hasPlayerPermissionScoreboard") && !Boolean.parseBoolean((String)eventData.get("hasPlayerPermissionScoreboard"))) {
                    return;
                }
                if (eventData.containsKey("showScoreboard") && !Boolean.parseBoolean((String)eventData.get("showScoreboard"))) {
                    return;
                }
                title = !eventData.get("displayName").equals("") ? (String)eventData.get("displayName") : (String)eventData.get("name");
                Double doubleY = (double)resolution.func_78328_b() * 0.4;
                int y = doubleY.intValue();
                int x = resolution.func_78326_a() - 140;
                ArrayList scoreboardLines = (ArrayList)eventData.get("scoreboard");
                if (scoreboardLines.size() > 10) {
                    y -= (scoreboardLines.size() - 10) * 13;
                }
                EventOverride.func_73734_a((int)(x + 30), (int)y, (int)(x + 140), (int)(y + 16), (int)-1157627904);
                ModernGui.drawScaledStringCustomFont(title, x + 85, y + 4, 14803951, 0.5f, "center", false, "georamaSemiBold", 32);
                Long startTime = ((Double)eventData.get("startTime")).longValue();
                Long duration = ((Double)eventData.get("duration")).longValue() * 1000L;
                Long remainingTimeOnPause = ((Double)eventData.get("remainingTimeOnPause")).longValue() * 1000L;
                Long timeLeft = Math.max(0L, startTime + duration - System.currentTimeMillis());
                if (eventData.get("state").equals("pause")) {
                    timeLeft = remainingTimeOnPause;
                } else if (eventData.get("state").equals("end")) {
                    timeLeft = duration;
                }
                if (!scoreboardLines.isEmpty()) {
                    for (int i = 0; i < scoreboardLines.size(); ++i) {
                        int offsetY = y + 16 + i * 13;
                        EventOverride.func_73734_a((int)(x + 30), (int)offsetY, (int)(x + 140), (int)(offsetY + 13), (int)0x77000000);
                        String line = ((String)scoreboardLines.get(i)).replace("&", "\u00a7");
                        line = line.replace("{TIME_LEFT}", EventOverride.chronoTimeToStr(timeLeft, false));
                        line = line.replace("{TIME_LEFT_MS}", EventOverride.chronoTimeToStr(timeLeft, true));
                        ModernGui.drawScaledStringCustomFont(line, x + 35, offsetY + 4, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
                    }
                }
            }
        } else if (ClientProxy.currentServerName.equals("wonder")) {
            title = "\u00a76WONDER XI";
            Double doubleY = (double)resolution.func_78328_b() * 0.4;
            int y = doubleY.intValue();
            int x = resolution.func_78326_a() - 140;
            EventOverride.func_73734_a((int)(x + 30), (int)y, (int)(x + 140), (int)(y + 16), (int)-1157627904);
            ModernGui.drawScaledStringCustomFont(title, x + 85, y + 4, 14803951, 0.5f, "center", false, "georamaSemiBold", 30);
            EventOverride.func_73734_a((int)(x + 30), (int)(y + 16), (int)(x + 140), (int)(y + 16 + 50), (int)0x77000000);
            ModernGui.drawScaledStringCustomFont("\u00a77Equipe: \u00a7f" + (ClientData.eventsInfos.size() > 0 && ClientData.eventsInfos.get(0).containsKey("teamName") ? ((String)ClientData.eventsInfos.get(0).get("teamName")).substring(0, 1).toUpperCase() + ((String)ClientData.eventsInfos.get(0).get("teamName")).substring(1) : "Aucune"), x + 35, y + 25, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
            String chronoStr = EventOverride.chronoTimeToStr(1667415600000L - System.currentTimeMillis(), false);
            ModernGui.drawScaledStringCustomFont("\u00a7eFin de l'event", x + 35, y + 45, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont("\u00a77> " + chronoStr, x + 35, y + 55, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
        } else if (ClientProxy.currentServerName.equals("aff") && ClientData.eventsInfos.size() > 0) {
            int pos;
            title = "\u00a7cAffrontement";
            Double doubleY = (double)resolution.func_78328_b() * 0.4;
            int y = doubleY.intValue();
            int x = resolution.func_78326_a() - 140;
            EventOverride.func_73734_a((int)(x + 30), (int)y, (int)(x + 140), (int)(y + 16), (int)-1157627904);
            ModernGui.drawScaledStringCustomFont(title, x + 85, y + 4, 14803951, 0.5f, "center", false, "georamaSemiBold", 30);
            EventOverride.func_73734_a((int)(x + 30), (int)(y + 16), (int)(x + 140), (int)cachedSCBHeight, (int)0x77000000);
            int offsetY = y + 20;
            ModernGui.drawScaledStringCustomFont("\u00a7cTemps: \u00a77" + ClientData.eventsInfos.get(0).get("timer"), x + 35, offsetY, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont("\u00a7eClass. Equipes", x + 35, offsetY += 15, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
            if (ClientData.eventsInfos.get(0).containsKey("teamsInScb") && ClientData.eventsInfos.get(0).containsKey("teamsPoints")) {
                offsetY += 3;
                pos = 1;
                for (String teamData : ((String)ClientData.eventsInfos.get(0).get("teamsPoints")).split(",")) {
                    String teamName = teamData.split("#")[0];
                    String teamPoints = teamData.split("#")[1];
                    if (!((String)ClientData.eventsInfos.get(0).get("teamsInScb")).contains(teamName)) continue;
                    ModernGui.drawScaledStringCustomFont(pos + ". " + teamName.substring(0, 1).toUpperCase() + teamName.substring(1), x + 35, offsetY += 8, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                    Matcher matcherKills = Pattern.compile(teamName + "#(\\d+)").matcher((String)ClientData.eventsInfos.get(0).get("teamsKills"));
                    Matcher matcherSurvivors = Pattern.compile(teamName + "#(\\d+)").matcher((String)ClientData.eventsInfos.get(0).get("teamsSurvivors"));
                    String scoreDetails = "";
                    if (matcherKills.find() && matcherSurvivors.find()) {
                        scoreDetails = "\u00a7a" + matcherKills.group(1) + "\u00a7r/\u00a7c" + matcherSurvivors.group(1);
                    }
                    ModernGui.drawScaledStringCustomFont(scoreDetails, x + 90, offsetY, 14803951, 0.5f, "center", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(teamPoints, x + 135, offsetY, 14803951, 0.5f, "right", false, "georamaBold", 30);
                    ++pos;
                }
            }
            ModernGui.drawScaledStringCustomFont("\u00a7eClass. Joueurs", x + 35, offsetY += 15, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
            offsetY += 3;
            pos = 1;
            if (ClientData.eventsInfos.get(0).containsKey("playersKills") && !((String)ClientData.eventsInfos.get(0).get("playersKills")).isEmpty()) {
                for (String playerData : ((String)ClientData.eventsInfos.get(0).get("playersKills")).split(",")) {
                    if (pos > 10) continue;
                    String playerName = playerData.split("#")[0];
                    String playerKills = playerData.split("#")[1];
                    ModernGui.drawScaledStringCustomFont("\u00a77" + playerName, x + 35, offsetY += 8, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(playerKills, x + 135, offsetY, 14803951, 0.5f, "right", false, "georamaSemiBold", 28);
                    ++pos;
                }
            } else {
                ModernGui.drawScaledStringCustomFont("\u00a77- aucun kill -", x + 35, offsetY += 8, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
            }
            cachedSCBHeight = offsetY + 10;
        } else if (!ClientProxy.serverType.equals("build")) {
            if (ClientData.currentJumpStartTime != -1L) {
                display = true;
                title = "Jump";
            }
            if (display) {
                Double doubleY = (double)resolution.func_78328_b() * 0.4;
                int y = doubleY.intValue();
                int x = resolution.func_78326_a() - 140;
                EventOverride.func_73734_a((int)x, (int)y, (int)(x + 140), (int)(y + 16), (int)-1157627904);
                this.drawSmallString(client.field_71466_p, title, x + 75 - client.field_71466_p.func_78256_a(title) / 2, y + 4, 0xFFFFFF);
                ClientEventHandler.STYLE.bindTexture("hud2");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                EventOverride.func_73734_a((int)x, (int)(y + 16), (int)(x + 140), (int)(y + 16 + 80), (int)0x77000000);
                if (ClientData.currentJumpStartTime != -1L) {
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a7a> Jump en cours", x + 5, y + 28, 0xFFFFFF, 0.9f, false, false);
                    String chronoStr = EventOverride.chronoTimeToStr(System.currentTimeMillis() - ClientData.currentJumpStartTime, true);
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a7cTemps: \u00a77" + chronoStr, x + 5, y + 43, 0xFFFFFF, 0.9f, false, false);
                    String recordName = "-";
                    String recordTime = "Aucun temps";
                    if (!ClientData.currentJumpRecord.isEmpty()) {
                        recordName = ClientData.currentJumpRecord.split(",")[0].split("#")[0];
                        recordTime = ClientData.currentJumpRecord.split(",")[0].split("#")[1];
                    }
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a76Record: \u00a7e" + recordName, x + 5, y + 58, 0xFFFFFF, 0.9f, false, false);
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a7d" + (this.isNumeric(recordTime) ? EventOverride.chronoTimeToStr(Long.parseLong(recordTime), true) : recordTime), x + 5, y + 68, 0xFFFFFF, 0.9f, false, false);
                }
                ClientEventHandler.STYLE.bindTexture("hud2");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                EventOverride.func_73734_a((int)x, (int)(y + 16 + 80 - 16), (int)(x + 140), (int)(y + 16 + 80), (int)0x77000000);
                if (ClientData.currentJumpStartTime != -1L) {
                    this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName((int)ClientKeyHandler.KEY_WARZONE_LEAVE.field_74512_d)), x + 75 - client.field_71466_p.func_78256_a(I18n.func_135053_a((String)"island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName((int)ClientKeyHandler.KEY_WARZONE_LEAVE.field_74512_d))) / 2, y + 85, 0xFFFFFF);
                }
            }
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)fontRenderer.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) >= 0;
    }
}

