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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EventOverride extends Gui implements ElementOverride
{
    public static HashMap<String, String> cachedChronos = new HashMap();
    public static int cachedSCBHeight = 0;
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }

    public static String chronoTimeToStr(Long chrono, boolean millis)
    {
        if (cachedChronos.containsKey(chrono + "#" + millis))
        {
            return (String)cachedChronos.get(chrono + "#" + millis);
        }
        else
        {
            long originalChrono = chrono.longValue();
            long hours = TimeUnit.MILLISECONDS.toHours(chrono.longValue());
            chrono = Long.valueOf(chrono.longValue() - TimeUnit.HOURS.toMillis(hours));
            long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono.longValue());
            chrono = Long.valueOf(chrono.longValue() - TimeUnit.MINUTES.toMillis(minutes));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono.longValue());
            chrono = Long.valueOf(chrono.longValue() - TimeUnit.SECONDS.toMillis(seconds));
            long milliseconds = chrono.longValue();
            String chronoStr = String.format("%02dh %02dm %02ds", new Object[] {Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});

            if (millis)
            {
                chronoStr = chronoStr + String.format(" %03dms", new Object[] {Long.valueOf(milliseconds)});
            }

            cachedChronos.put(originalChrono + "#" + millis, chronoStr);
            return chronoStr;
        }
    }

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        boolean display = false;
        String title = "";
        int x;
        int chronoStr;

        if (ClientProxy.serverType.equals("ng"))
        {
            if (ClientData.eventsInfos.size() > 0)
            {
                HashMap doubleY = (HashMap)((HashMap)ClientData.eventsInfos.get(0)).clone();

                if (doubleY.containsKey("isPlayerInRegion") && !Boolean.parseBoolean((String)doubleY.get("isPlayerInRegion")))
                {
                    return;
                }

                if (doubleY.containsKey("hasPlayerPermissionScoreboard") && !Boolean.parseBoolean((String)doubleY.get("hasPlayerPermissionScoreboard")))
                {
                    return;
                }

                if (doubleY.containsKey("showScoreboard") && !Boolean.parseBoolean((String)doubleY.get("showScoreboard")))
                {
                    return;
                }

                title = !doubleY.get("displayName").equals("") ? (String)doubleY.get("displayName") : (String)doubleY.get("name");
                Double y = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
                x = y.intValue();
                chronoStr = resolution.getScaledWidth() - 140;
                ArrayList recordName = (ArrayList)doubleY.get("scoreboard");

                if (recordName.size() > 10)
                {
                    x -= (recordName.size() - 10) * 13;
                }

                drawRect(chronoStr + 30, x, chronoStr + 140, x + 16, -1157627904);
                ModernGui.drawScaledStringCustomFont(title, (float)(chronoStr + 85), (float)(x + 4), 14803951, 0.5F, "center", false, "georamaSemiBold", 32);
                Long recordTime = Long.valueOf(((Double)doubleY.get("startTime")).longValue());
                Long duration = Long.valueOf(((Double)doubleY.get("duration")).longValue() * 1000L);
                Long remainingTimeOnPause = Long.valueOf(((Double)doubleY.get("remainingTimeOnPause")).longValue() * 1000L);
                Long playerData = Long.valueOf(Math.max(0L, recordTime.longValue() + duration.longValue() - System.currentTimeMillis()));

                if (doubleY.get("state").equals("pause"))
                {
                    playerData = remainingTimeOnPause;
                }
                else if (doubleY.get("state").equals("end"))
                {
                    playerData = duration;
                }

                if (!recordName.isEmpty())
                {
                    for (int playerName = 0; playerName < recordName.size(); ++playerName)
                    {
                        int playerKills = x + 16 + playerName * 13;
                        drawRect(chronoStr + 30, playerKills, chronoStr + 140, playerKills + 13, 1996488704);
                        String matcherKills = ((String)recordName.get(playerName)).replace("&", "\u00a7");
                        matcherKills = matcherKills.replace("{TIME_LEFT}", chronoTimeToStr(playerData, false));
                        matcherKills = matcherKills.replace("{TIME_LEFT_MS}", chronoTimeToStr(playerData, true));
                        ModernGui.drawScaledStringCustomFont(matcherKills, (float)(chronoStr + 35), (float)(playerKills + 4), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                    }
                }
            }
        }
        else
        {
            Double var20;
            int var21;
            String var22;

            if (ClientProxy.currentServerName.equals("wonder"))
            {
                title = "\u00a76WONDER XI";
                var20 = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
                var21 = var20.intValue();
                x = resolution.getScaledWidth() - 140;
                drawRect(x + 30, var21, x + 140, var21 + 16, -1157627904);
                ModernGui.drawScaledStringCustomFont(title, (float)(x + 85), (float)(var21 + 4), 14803951, 0.5F, "center", false, "georamaSemiBold", 30);
                drawRect(x + 30, var21 + 16, x + 140, var21 + 16 + 50, 1996488704);
                ModernGui.drawScaledStringCustomFont("\u00a77Equipe: \u00a7f" + (ClientData.eventsInfos.size() > 0 && ((HashMap)ClientData.eventsInfos.get(0)).containsKey("teamName") ? ((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamName")).substring(0, 1).toUpperCase() + ((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamName")).substring(1) : "Aucune"), (float)(x + 35), (float)(var21 + 25), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 = chronoTimeToStr(Long.valueOf(1667415600000L - System.currentTimeMillis()), false);
                ModernGui.drawScaledStringCustomFont("\u00a7eFin de l\'event", (float)(x + 35), (float)(var21 + 45), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont("\u00a77> " + var22, (float)(x + 35), (float)(var21 + 55), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
            }
            else if (ClientProxy.currentServerName.equals("aff") && ClientData.eventsInfos.size() > 0)
            {
                title = "\u00a7cAffrontement";
                var20 = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
                var21 = var20.intValue();
                x = resolution.getScaledWidth() - 140;
                drawRect(x + 30, var21, x + 140, var21 + 16, -1157627904);
                ModernGui.drawScaledStringCustomFont(title, (float)(x + 85), (float)(var21 + 4), 14803951, 0.5F, "center", false, "georamaSemiBold", 30);
                drawRect(x + 30, var21 + 16, x + 140, cachedSCBHeight, 1996488704);
                chronoStr = var21 + 20;
                ModernGui.drawScaledStringCustomFont("\u00a7cTemps: \u00a77" + ((HashMap)ClientData.eventsInfos.get(0)).get("timer"), (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                chronoStr += 15;
                ModernGui.drawScaledStringCustomFont("\u00a7eClass. Equipes", (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                int var24;
                String[] var26;
                int var27;
                int var28;
                String var29;
                String var30;
                String var31;

                if (((HashMap)ClientData.eventsInfos.get(0)).containsKey("teamsInScb") && ((HashMap)ClientData.eventsInfos.get(0)).containsKey("teamsPoints"))
                {
                    chronoStr += 3;
                    var24 = 1;
                    var26 = ((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamsPoints")).split(",");
                    var27 = var26.length;

                    for (var28 = 0; var28 < var27; ++var28)
                    {
                        var29 = var26[var28];
                        var30 = var29.split("#")[0];
                        var31 = var29.split("#")[1];

                        if (((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamsInScb")).contains(var30))
                        {
                            chronoStr += 8;
                            ModernGui.drawScaledStringCustomFont(var24 + ". " + var30.substring(0, 1).toUpperCase() + var30.substring(1), (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
                            Matcher var32 = Pattern.compile(var30 + "#(\\d+)").matcher((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamsKills"));
                            Matcher matcherSurvivors = Pattern.compile(var30 + "#(\\d+)").matcher((String)((HashMap)ClientData.eventsInfos.get(0)).get("teamsSurvivors"));
                            String scoreDetails = "";

                            if (var32.find() && matcherSurvivors.find())
                            {
                                scoreDetails = "\u00a7a" + var32.group(1) + "\u00a7r/\u00a7c" + matcherSurvivors.group(1);
                            }

                            ModernGui.drawScaledStringCustomFont(scoreDetails, (float)(x + 90), (float)chronoStr, 14803951, 0.5F, "center", false, "georamaSemiBold", 28);
                            ModernGui.drawScaledStringCustomFont(var31, (float)(x + 135), (float)chronoStr, 14803951, 0.5F, "right", false, "georamaBold", 30);
                            ++var24;
                        }
                    }
                }

                chronoStr += 15;
                ModernGui.drawScaledStringCustomFont("\u00a7eClass. Joueurs", (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                chronoStr += 3;
                var24 = 1;

                if (((HashMap)ClientData.eventsInfos.get(0)).containsKey("playersKills") && !((String)((HashMap)ClientData.eventsInfos.get(0)).get("playersKills")).isEmpty())
                {
                    var26 = ((String)((HashMap)ClientData.eventsInfos.get(0)).get("playersKills")).split(",");
                    var27 = var26.length;

                    for (var28 = 0; var28 < var27; ++var28)
                    {
                        var29 = var26[var28];

                        if (var24 <= 10)
                        {
                            chronoStr += 8;
                            var30 = var29.split("#")[0];
                            var31 = var29.split("#")[1];
                            ModernGui.drawScaledStringCustomFont("\u00a77" + var30, (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
                            ModernGui.drawScaledStringCustomFont(var31, (float)(x + 135), (float)chronoStr, 14803951, 0.5F, "right", false, "georamaSemiBold", 28);
                            ++var24;
                        }
                    }
                }
                else
                {
                    chronoStr += 8;
                    ModernGui.drawScaledStringCustomFont("\u00a77- aucun kill -", (float)(x + 35), (float)chronoStr, 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
                }

                cachedSCBHeight = chronoStr + 10;
            }
            else if (!ClientProxy.serverType.equals("build"))
            {
                if (ClientData.currentJumpStartTime.longValue() != -1L)
                {
                    display = true;
                    title = "Jump";
                }

                if (display)
                {
                    var20 = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
                    var21 = var20.intValue();
                    x = resolution.getScaledWidth() - 140;
                    drawRect(x, var21, x + 140, var21 + 16, -1157627904);
                    this.drawSmallString(client.fontRenderer, title, x + 75 - client.fontRenderer.getStringWidth(title) / 2, var21 + 4, 16777215);
                    ClientEventHandler.STYLE.bindTexture("hud2");
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    drawRect(x, var21 + 16, x + 140, var21 + 16 + 80, 1996488704);

                    if (ClientData.currentJumpStartTime.longValue() != -1L)
                    {
                        this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a7a> Jump en cours", x + 5, var21 + 28, 16777215, 0.9F, false, false);
                        var22 = chronoTimeToStr(Long.valueOf(System.currentTimeMillis() - ClientData.currentJumpStartTime.longValue()), true);
                        this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a7cTemps: \u00a77" + var22, x + 5, var21 + 43, 16777215, 0.9F, false, false);
                        String var23 = "-";
                        String var25 = "Aucun temps";

                        if (!ClientData.currentJumpRecord.isEmpty())
                        {
                            var23 = ClientData.currentJumpRecord.split(",")[0].split("#")[0];
                            var25 = ClientData.currentJumpRecord.split(",")[0].split("#")[1];
                        }

                        this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a76Record: \u00a7e" + var23, x + 5, var21 + 58, 16777215, 0.9F, false, false);
                        this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a7d" + (this.isNumeric(var25) ? chronoTimeToStr(Long.valueOf(Long.parseLong(var25)), true) : var25), x + 5, var21 + 68, 16777215, 0.9F, false, false);
                    }

                    ClientEventHandler.STYLE.bindTexture("hud2");
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    drawRect(x, var21 + 16 + 80 - 16, x + 140, var21 + 16 + 80, 1996488704);

                    if (ClientData.currentJumpStartTime.longValue() != -1L)
                    {
                        this.drawSmallString(client.fontRenderer, I18n.getString("island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_WARZONE_LEAVE.keyCode)), x + 75 - client.fontRenderer.getStringWidth(I18n.getString("island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_WARZONE_LEAVE.keyCode))) / 2, var21 + 85, 16777215);
                    }
                }
            }
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.95F, 0.95F, 0.95F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isNumeric(String str)
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

            if (Integer.parseInt(str) < 0)
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
}
