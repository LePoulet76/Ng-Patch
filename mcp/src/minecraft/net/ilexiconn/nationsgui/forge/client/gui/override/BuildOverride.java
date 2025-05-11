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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

public class BuildOverride
extends Gui
implements ElementOverride {
    private DynamicTexture imageTexture;
    private String loadImageIslandId;
    public static HashMap<Long, String> cachedChronos = new HashMap();
    public static long lastSwitchNameAndDesc = 0L;
    public static boolean displayName = true;

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
        if (ClientProxy.serverType.equals("build") && !ClientData.currentIsland.isEmpty()) {
            Double doubleY = (double)resolution.func_78328_b() * 0.4;
            int y = doubleY.intValue();
            int x = resolution.func_78326_a() - 120;
            BuildOverride.func_73734_a((int)x, (int)y, (int)(x + 120), (int)(y + 16), (int)-1157627904);
            BuildOverride.func_73734_a((int)x, (int)(y + 16), (int)(x + 120), (int)(y + 16 + 59), (int)0x77000000);
            BuildOverride.func_73734_a((int)x, (int)(y + 16 + 59), (int)(x + 120), (int)(y + 16 + 59 + 20 + 20), (int)-1157627904);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(x + 5, y + -8, 0, 67, 32, 32);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if ((this.imageTexture == null || this.loadImageIslandId != null && !this.loadImageIslandId.equals(ClientData.currentIsland.get("id"))) && ClientData.currentIsland.containsKey("image")) {
                BufferedImage image = BuildOverride.decodeToImage(ClientData.currentIsland.get("image"));
                this.imageTexture = new DynamicTexture(image);
                this.loadImageIslandId = ClientData.currentIsland.get("id");
            }
            if (this.imageTexture != null) {
                GL11.glBindTexture((int)3553, (int)this.imageTexture.func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(x + 6, y - 7, 0.0f, 0.0f, 102, 102, 30, 30, 102.0f, 102.0f, false);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"island.overlay.island") + " n\u00b0" + ClientData.currentIsland.get("id"), x + 40, y + 4, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (ClientData.currentJumpStartTime != -1L && System.currentTimeMillis() - ClientData.currentJumpStartTime < 5400000L) {
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.jump_inprogress"), x + 5, y + 28, 0xFFFFFF, 0.9f, false, false);
                String chronoStr = BuildOverride.chronoTimeToStr(System.currentTimeMillis() - ClientData.currentJumpStartTime, true);
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.time") + " \u00a77" + chronoStr, x + 5, y + 43, 0xFFFFFF, 0.9f, false, false);
                String recordName = "-";
                String recordTime = "Aucun temps";
                if (!ClientData.currentJumpRecord.isEmpty()) {
                    recordName = ClientData.currentJumpRecord.split(",")[0].split("#")[0];
                    recordTime = ClientData.currentJumpRecord.split(",")[0].split("#")[1];
                }
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a76Record: \u00a7e" + recordName, x + 5, y + 58, 0xFFFFFF, 0.9f, false, false);
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a7d" + (this.isNumeric(recordTime) ? BuildOverride.chronoTimeToStr(Long.parseLong(recordTime), true) : recordTime), x + 5, y + 68, 0xFFFFFF, 0.9f, false, false);
            } else if (ClientData.currentIsland.containsKey("team")) {
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.team") + " " + ClientData.currentIsland.get("team") + " \u00a7e(" + ClientData.currentIsland.get("score") + ")", x + 5, y + 28, 0xFFFFFF, 0.9f, false, false);
                List<String> scores = Arrays.asList(ClientData.currentIsland.get("scores").split(","));
                for (int i = 0; i < scores.size() && i < 3; ++i) {
                    int pos = i + 1;
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, "\u00a7e" + pos + " \u00a77> \u00a7r" + scores.get(i).split("##")[0] + " \u00a76(" + scores.get(i).split("##")[1] + ")", x + 5, y + 43 + i * 10, 0xFFFFFF, 0.9f, false, false);
                }
            } else {
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (System.currentTimeMillis() - lastSwitchNameAndDesc > 10000L) {
                    lastSwitchNameAndDesc = System.currentTimeMillis();
                    displayName = !displayName;
                }
                String[] words = ClientData.currentIsland.get("description").replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                if (displayName) {
                    words = ClientData.currentIsland.get("name").replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                }
                String line = "";
                int lineNumber = 0;
                for (String descWord : words) {
                    if (Minecraft.func_71410_x().field_71466_p.func_78256_a(line + descWord) <= 120) {
                        if (!line.equals("")) {
                            line = line + " ";
                        }
                        line = line + descWord;
                        continue;
                    }
                    if (lineNumber == 0 && !displayName) {
                        line = "\u00a7o\"" + line;
                    }
                    this.drawScaledString(Minecraft.func_71410_x().field_71466_p, displayName ? "\u00a76" + line : line, x + 60, y + 28 + lineNumber * 10, 0xFFFFFF, 0.9f, true, false);
                    ++lineNumber;
                    line = descWord;
                }
                if (lineNumber == 0 && !displayName) {
                    line = "\u00a7o\"" + line;
                }
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, displayName ? "\u00a76" + line : line + "\"", x + 60, y + 28 + lineNumber * 10, 0xFFFFFF, 0.9f, true, false);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"island.overlay.by") + " " + ClientData.currentIsland.get("creator"), x + 5, y + 65, 0xFFFFFF);
            }
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(x + 13, y + 78, 33, 67, 94, 15);
            if (ClientData.currentJumpStartTime == -1L) {
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.infos"), x + 60, y + 83, 0, 0.8f, true, false);
            } else {
                this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName((int)ClientKeyHandler.KEY_WARZONE_LEAVE.field_74512_d)), x + 60, y + 83, 0, 0.8f, true, false);
            }
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(x + 13, y + 98, 33, 67, 94, 15);
            this.drawScaledString(Minecraft.func_71410_x().field_71466_p, I18n.func_135053_a((String)"island.overlay.list"), x + 60, y + 103, 0, 0.8f, true, false);
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
        if (cachedChronos.containsKey(chrono)) {
            return cachedChronos.get(chrono);
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono);
        chrono = chrono - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono);
        chrono = chrono - TimeUnit.SECONDS.toMillis(seconds);
        long left = chrono / 10L;
        String chronoStr = (minutes < 10L ? "0" + minutes : Long.valueOf(minutes)) + ":" + (seconds < 10L ? "0" + seconds : Long.valueOf(seconds)) + (millis ? ":" + (left < 10L ? "0" + left : Long.valueOf(left)) : "");
        cachedChronos.put(chrono, chronoStr);
        return chronoStr;
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

