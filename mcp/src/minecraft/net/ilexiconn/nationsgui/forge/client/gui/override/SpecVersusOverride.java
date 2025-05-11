/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import com.google.gson.internal.LinkedTreeMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.HotbarOverride;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class SpecVersusOverride
extends Gui
implements ElementOverride {
    public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", 25);
    public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", 25);

    public static String secondsToTimer(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

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
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB < 50L) {
            return;
        }
        if (ClientProxy.currentServerName.equals("mmr")) {
            if (!ClientData.versusOverlayData.isEmpty()) {
                ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 118.6f, 5.0f, 0.0f, 331.0f, 593, 171, 237, 68, 1920.0f, 1033.0f, true);
                ModernGui.drawScaledStringCustomFont(SpecVersusOverride.secondsToTimer(((Double)ClientData.versusOverlayData.get("timer")).intValue()), resolution.func_78326_a() / 2, 5.5f, 15463162, 0.6f, "center", false, "minecraftDungeons", 21);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("overlay.versus.stage." + ClientData.versusOverlayData.get("stage"))), resolution.func_78326_a() / 2, 63.0f, 855319, 0.5f, "center", false, "minecraftDungeons", 21);
                String teamName = ((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get((Object)"name")).replaceAll("Empire", "E.");
                ModernGui.drawScaledStringCustomFont(teamName.length() < 11 ? teamName.toUpperCase() : teamName.substring(0, 9).toUpperCase() + "..", (float)(resolution.func_78326_a() / 2) - 118.6f + 8.0f, 25.0f, 855319, 0.7f, "left", false, "minecraftDungeons", 21);
                ModernGui.drawScaledStringCustomFont(((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get((Object)"color")).toUpperCase(), (float)(resolution.func_78326_a() / 2) - 118.6f + 8.0f, 37.0f, 855319, 0.5f, "left", false, "georamaBold", 21);
                int team1Score = ((Double)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get((Object)"score")).intValue();
                for (int i = 0; i <= 1; ++i) {
                    ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 118.6f + 8.0f + (float)(i * 10), 45.0f, team1Score > i ? 612.0f : 641.0f, 370.0f, 21, 21, 8, 8, 1920.0f, 1033.0f, true);
                }
                teamName = ((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get((Object)"name")).replaceAll("Empire", "E.");
                ModernGui.drawScaledStringCustomFont(teamName.length() < 11 ? teamName.toUpperCase() : teamName.substring(0, 9).toUpperCase(), (float)(resolution.func_78326_a() / 2) + 118.6f - 8.0f, 25.0f, 855319, 0.7f, "right", false, "minecraftDungeons", 21);
                ModernGui.drawScaledStringCustomFont(((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get((Object)"color")).toUpperCase(), (float)(resolution.func_78326_a() / 2) + 118.6f - 8.0f, 37.0f, 855319, 0.5f, "right", false, "georamaBold", 21);
                int team2Score = ((Double)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get((Object)"score")).intValue();
                for (int i = 0; i <= 1; ++i) {
                    ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) + 118.6f - 8.0f - 8.4f - (float)(i * 10), 45.0f, team2Score > i ? 612.0f : 641.0f, 370.0f, 21, 21, 8, 8, 1920.0f, 1033.0f, true);
                }
                if (!((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                    ArrayList playersTeam1 = (ArrayList)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get((Object)"players");
                    float offsetY = (float)(resolution.func_78328_b() / 2) - (float)playersTeam1.size() * 40.0f / 2.0f;
                    for (LinkedTreeMap player : playersTeam1) {
                        ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                        ModernGui.drawScaledCustomSizeModalRect(5.0f, offsetY, 0.0f, (Boolean)player.get((Object)"isDead") != false ? 207.0f : 102.0f, 249, 96, 99, 38, 1920.0f, 1033.0f, true);
                        if (!ClientProxy.cacheHeadPlayer.containsKey((String)player.get((Object)"name"))) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)player.get((Object)"name")));
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)player.get((Object)"name")));
                                ClientProxy.cacheHeadPlayer.put((String)player.get((Object)"name"), resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get((String)player.get((Object)"name")));
                            Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get((String)player.get((Object)"name")));
                            GUIUtils.drawScaledCustomSizeModalRect(30, (int)offsetY + 27, 8.0f, 16.0f, 8, -8, -16, -16, 64.0f, 64.0f);
                        }
                        ModernGui.drawScaledStringCustomFont((String)player.get((Object)"name"), 41.0f, offsetY + 9.0f, 15463162, 0.5f, "left", false, "minecraftDungeons", 21);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)player.get((Object)"damage")) + " d\u00e9g\u00e2ts", 41.0f, offsetY + 18.0f, 15463162, 0.5f, "left", false, "georamaSemiBold", 21);
                        Double playerHealth = (Double)player.get((Object)"health");
                        for (int i = 0; i < 10; ++i) {
                            ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                            ModernGui.drawScaledCustomSizeModalRect(41 + i * 6, offsetY + 26.0f, playerHealth >= (double)(i * 2) ? 329.0f : 313.0f, 11.0f, 13, 11, 5, 4, 1920.0f, 1033.0f, true);
                        }
                        ArrayList playerEffects = (ArrayList)player.get((Object)"effects");
                        for (int i = 0; i < playerEffects.size(); ++i) {
                            Potion potion = Potion.field_76425_a[((Double)playerEffects.get(i)).intValue()];
                            if (potion == null) continue;
                            ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                            ModernGui.drawScaledCustomSizeModalRect(105 + i / 2 * 14, offsetY + 5.5f + (float)(i % 2 * 14), 252.0f, 114.0f, 33, 34, 13, 13, 1920.0f, 1033.0f, true);
                            client.func_110434_K().func_110577_a(HotbarOverride.icons);
                            int iconIndex = potion.func_76392_e();
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            ModernGui.drawScaledCustomSizeModalRect(107.0f + (float)(i / 2 * 14), offsetY + 8.0f + (float)(i % 2 * 14), iconIndex % 8 * 18, 198 + iconIndex / 8 * 18, 18, 18, 8, 8, 256.0f, 256.0f, true);
                        }
                        offsetY += 40.0f;
                    }
                    ArrayList playersTeam2 = (ArrayList)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get((Object)"players");
                    offsetY = (float)(resolution.func_78328_b() / 2) - (float)playersTeam2.size() * 40.0f / 2.0f;
                    for (LinkedTreeMap player : playersTeam2) {
                        ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() - 5) - 99.6f, offsetY, 0.0f, (Boolean)player.get((Object)"isDead") != false ? 750.0f : 645.0f, 249, 96, 99, 38, 1920.0f, 1033.0f, true);
                        if (!ClientProxy.cacheHeadPlayer.containsKey((String)player.get((Object)"name"))) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)player.get((Object)"name")));
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)player.get((Object)"name")));
                                ClientProxy.cacheHeadPlayer.put((String)player.get((Object)"name"), resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get((String)player.get((Object)"name")));
                            Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get((String)player.get((Object)"name")));
                            GUIUtils.drawScaledCustomSizeModalRect(resolution.func_78326_a() - 15, (int)offsetY + 27, 8.0f, 16.0f, 8, -8, -16, -16, 64.0f, 64.0f);
                        }
                        ModernGui.drawScaledStringCustomFont((String)player.get((Object)"name"), resolution.func_78326_a() - 41, offsetY + 9.0f, 15463162, 0.5f, "right", false, "minecraftDungeons", 21);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)player.get((Object)"damage")) + " d\u00e9g\u00e2ts", resolution.func_78326_a() - 41, offsetY + 18.0f, 15463162, 0.5f, "right", false, "georamaSemiBold", 21);
                        Double playerHealth = (Double)player.get((Object)"health");
                        for (int i = 0; i < 10; ++i) {
                            ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                            ModernGui.drawScaledCustomSizeModalRect(resolution.func_78326_a() - 46 - i * 6, offsetY + 26.0f, playerHealth >= (double)(i * 2) ? 329.0f : 313.0f, 11.0f, 13, 11, 5, 4, 1920.0f, 1033.0f, true);
                        }
                        ArrayList playerEffects = (ArrayList)player.get((Object)"effects");
                        for (int i = 0; i < playerEffects.size(); ++i) {
                            Potion potion = Potion.field_76425_a[((Double)playerEffects.get(i)).intValue()];
                            if (potion == null) continue;
                            ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                            ModernGui.drawScaledCustomSizeModalRect(resolution.func_78326_a() - 118 - i / 2 * 14, offsetY + 5.5f + (float)(i % 2 * 14), 252.0f, 114.0f, 33, 34, 13, 13, 1920.0f, 1033.0f, true);
                            client.func_110434_K().func_110577_a(HotbarOverride.icons);
                            int iconIndex = potion.func_76392_e();
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            ModernGui.drawScaledCustomSizeModalRect((float)resolution.func_78326_a() - 116.0f - (float)(i / 2 * 14), offsetY + 8.0f + (float)(i % 2 * 14), iconIndex % 8 * 18, 198 + iconIndex / 8 * 18, 18, 18, 8, 8, 256.0f, 256.0f, true);
                        }
                        offsetY += 40.0f;
                    }
                }
            } else {
                ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 48.4f, 15.0f, 0.0f, 903.0f, 242, 119, 96, 47, 1920.0f, 1033.0f, true);
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
}

