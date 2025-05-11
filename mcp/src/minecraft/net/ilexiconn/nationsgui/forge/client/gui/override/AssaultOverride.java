/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngvehicles.client.render.utils.GlStateManagerHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import fr.nationsglory.ngvehicles.client.render.utils.GlStateManagerHelper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class AssaultOverride
extends Gui
implements ElementOverride {
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();
    public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", 25);
    public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", 25);
    public List<String> tooltipToDraw = new ArrayList<String>();

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
        this.tooltipToDraw = new ArrayList<String>();
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB < 50L) {
            return;
        }
        if (GenericOverride.displaysCountryEntry()) {
            return;
        }
        if (NotificationOverride.displaysNotification()) {
            return;
        }
        if (!ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty()) {
            String defenderName;
            boolean hasDefenderHandicap;
            String attackerName;
            boolean hasAttackerHandicap;
            ModernGui.bindTextureOverlayMain();
            float progress = Float.parseFloat(ClientData.currentAssault.get("progressTime"));
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 42.0f, 46.0f, 1218.0f, 837.0f, (int)(195.0f * progress), 5, (int)(78.0f * progress), 2, 1920.0f, 1033.0f, false);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 228.4f, 0.0f, 770.0f, 536.0f, 1142, 167, 456, 66, 1920.0f, 1033.0f, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("overlay.assault.state." + ClientData.currentAssault.get("currentState"))), resolution.func_78326_a() / 2, 15.0f, 16000586, 0.5f, "center", true, "minecraftDungeons", 20);
            ModernGui.drawScaledStringCustomFont(ClientData.currentAssault.get("remainingTimeInCurrentState"), resolution.func_78326_a() / 2, 22.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 27);
            int currentStateNumber = Integer.parseInt(ClientData.currentAssault.get("currentStateNumber"));
            if (currentStateNumber >= 1) {
                ModernGui.bindTextureOverlayMain();
                for (int i = 0; i < currentStateNumber; ++i) {
                    boolean isCapture = i == 1 || i == 3 || i == 4;
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 46.0f + (float)(i * 18), 36.5f, isCapture ? 1268.0f : 1218.0f, 779.0f, 50, 50, 20, 20, 1920.0f, 1033.0f, false);
                }
            }
            ModernGui.bindTextureOverlayMain();
            boolean bl = hasAttackerHandicap = Float.parseFloat(ClientData.currentAssault.get("multiRatioAttackersByDefenders")) > 1.0f;
            if (hasAttackerHandicap) {
                ModernGui.bindTextureOverlayMain();
                int iconX = resolution.func_78326_a() / 2 - 135;
                int iconY = 11;
                int iconWidth = 7;
                int iconHeight = 8;
                ModernGui.drawScaledCustomSizeModalRect(iconX, iconY, 1318.0f, 729.0f, 18, 21, iconWidth, iconHeight, 1920.0f, 1033.0f, false);
                int mouseX = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                int mouseY = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                if (mouseX >= iconX && mouseX <= iconX + iconWidth && mouseY >= iconY && mouseY <= iconY + iconHeight) {
                    this.generateHandicapTooltip();
                }
            }
            ModernGui.drawScaledStringCustomFont((attackerName = ClientData.currentAssault.get("attackerFactionName").replaceAll("Empire", "Emp")).length() < 14 ? attackerName.toUpperCase() : attackerName.substring(0, 13).toUpperCase() + "..", resolution.func_78326_a() / 2 - 135 + (hasAttackerHandicap ? 10 : 0), 10.0f, 16000586, 0.4f, "left", true, "minecraftDungeons", 30);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.assault.attacker"), resolution.func_78326_a() / 2 - 135, 22.0f, 0xFFFFFF, 0.5f, "left", true, "georamaExtraBold", 23);
            ModernGui.drawScaledStringCustomFont(ClientData.currentAssault.get("attackerScore") + " Points", resolution.func_78326_a() / 2 - 135, 29.0f, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 23);
            ModernGui.drawScaledStringCustomFont(ClientData.currentAssault.get("attackerPlayersCount") + " " + I18n.func_135053_a((String)"main.players"), resolution.func_78326_a() / 2 - 135, 36.0f, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 23);
            ModernGui.bindTextureOverlayMain();
            boolean bl2 = hasDefenderHandicap = Float.parseFloat(ClientData.currentAssault.get("multiRatioAttackersByDefenders")) < 1.0f;
            if (hasDefenderHandicap) {
                ModernGui.bindTextureOverlayMain();
                int iconX = resolution.func_78326_a() / 2 + 135 - 7;
                int iconY = 11;
                int iconWidth = 7;
                int iconHeight = 8;
                ModernGui.drawScaledCustomSizeModalRect(iconX, iconY, 1318.0f, 729.0f, 18, 21, iconWidth, iconHeight, 1920.0f, 1033.0f, false);
                int mouseX = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                int mouseY = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                if (mouseX >= iconX && mouseX <= iconX + iconWidth && mouseY >= iconY && mouseY <= iconY + iconHeight) {
                    this.generateHandicapTooltip();
                }
            }
            ModernGui.drawScaledStringCustomFont((defenderName = ClientData.currentAssault.get("defenderFactionName").replaceAll("Empire", "Emp")).length() < 14 ? defenderName.toUpperCase() : defenderName.substring(0, 13).toUpperCase() + "..", resolution.func_78326_a() / 2 + 135 - (hasDefenderHandicap ? 9 : 0), 10.0f, 16000586, 0.4f, "right", true, "minecraftDungeons", 30);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.assault.defender"), resolution.func_78326_a() / 2 + 135, 22.0f, 0xFFFFFF, 0.5f, "right", true, "georamaExtraBold", 23);
            ModernGui.drawScaledStringCustomFont(ClientData.currentAssault.get("defenderScore") + " Points", resolution.func_78326_a() / 2 + 135, 29.0f, 0xFFFFFF, 0.5f, "right", true, "georamaSemiBold", 23);
            ModernGui.drawScaledStringCustomFont(ClientData.currentAssault.get("defenderPlayersCount") + " " + I18n.func_135053_a((String)"main.players"), resolution.func_78326_a() / 2 + 135, 36.0f, 0xFFFFFF, 0.5f, "right", true, "georamaSemiBold", 23);
            if (!this.tooltipToDraw.isEmpty()) {
                int mouseX = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                int mouseY = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                this.drawCustomHoveringText(this.tooltipToDraw, mouseX, mouseY, client.field_71466_p, resolution);
            }
        }
    }

    public void generateHandicapTooltip() {
        float ratio = Float.parseFloat(ClientData.currentAssault.get("multiRatioAttackersByDefenders"));
        int totalPlayers = Integer.parseInt(ClientData.currentAssault.get("attackerPlayersCount")) + Integer.parseInt(ClientData.currentAssault.get("defenderPlayersCount"));
        this.tooltipToDraw.add("\u00a74Ratio ATT/DEF: \u00a7c" + ratio);
        this.tooltipToDraw.add("");
        if (ratio != 1.0f) {
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.potions").replaceAll("<ratio>", String.format("%.2f", Float.valueOf(ratio))).split("##")));
        }
        if ((ratio > 2.0f || (double)ratio < 0.5) && totalPlayers >= 8) {
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.conditions_divided").split("##")));
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.no_allies").split("##")));
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.no_missiles").split("##")));
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.damage_reduction").split("##")));
            if (ratio > 2.0f) {
                this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.no_kill_package").split("##")));
            }
            if ((double)ratio < 0.5) {
                this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.no_point_package").split("##")));
                this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"overlay.assault.handicap.no_protection_r&d").split("##")));
            }
        }
    }

    private void drawCustomHoveringText(List<String> textLines, int x, int y, FontRenderer font, ScaledResolution resolution) {
        if (!textLines.isEmpty()) {
            GlStateManagerHelper.disableRescaleNormal();
            RenderHelper.func_74518_a();
            GlStateManagerHelper.disableLighting();
            GlStateManagerHelper.disableDepth();
            int tooltipWidth = 0;
            for (String textLine : textLines) {
                int textLineWidth = font.func_78256_a(textLine);
                if (textLineWidth <= tooltipWidth) continue;
                tooltipWidth = textLineWidth;
            }
            int tooltipX = x + 12;
            int tooltipY = y - 12;
            int tooltipHeight = 8;
            if (textLines.size() > 1) {
                tooltipHeight += 2 + (textLines.size() - 1) * 10;
            }
            if (tooltipX + tooltipWidth > resolution.func_78326_a()) {
                tooltipX -= 28 + tooltipWidth;
            }
            if (tooltipY + tooltipHeight + 6 > resolution.func_78328_b()) {
                tooltipY = resolution.func_78328_b() - tooltipHeight - 6;
            }
            int backgroundColor = -267386864;
            AssaultOverride.func_73734_a((int)(tooltipX - 3), (int)(tooltipY - 4), (int)(tooltipX + tooltipWidth + 3), (int)(tooltipY - 3), (int)-267386864);
            AssaultOverride.func_73734_a((int)(tooltipX - 3), (int)(tooltipY + tooltipHeight + 3), (int)(tooltipX + tooltipWidth + 3), (int)(tooltipY + tooltipHeight + 4), (int)-267386864);
            AssaultOverride.func_73734_a((int)(tooltipX - 3), (int)(tooltipY - 3), (int)(tooltipX + tooltipWidth + 3), (int)(tooltipY + tooltipHeight + 3), (int)-267386864);
            AssaultOverride.func_73734_a((int)(tooltipX - 4), (int)(tooltipY - 3), (int)(tooltipX - 3), (int)(tooltipY + tooltipHeight + 3), (int)-267386864);
            AssaultOverride.func_73734_a((int)(tooltipX + tooltipWidth + 3), (int)(tooltipY - 3), (int)(tooltipX + tooltipWidth + 4), (int)(tooltipY + tooltipHeight + 3), (int)-267386864);
            int borderColorStart = 0x505000FF;
            int borderColorEnd = 1344798847;
            this.func_73733_a(tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, 0x505000FF, 1344798847);
            this.func_73733_a(tooltipX + tooltipWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipWidth + 3, tooltipY + tooltipHeight + 3 - 1, 0x505000FF, 1344798847);
            this.func_73733_a(tooltipX - 3, tooltipY - 3, tooltipX + tooltipWidth + 3, tooltipY - 3 + 1, 0x505000FF, 0x505000FF);
            this.func_73733_a(tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipWidth + 3, tooltipY + tooltipHeight + 3, 1344798847, 1344798847);
            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
                String line = textLines.get(lineNumber);
                font.func_78261_a(line, tooltipX, tooltipY, -1);
                if (lineNumber == 0) {
                    tooltipY += 2;
                }
                tooltipY += 10;
            }
            GlStateManagerHelper.enableLighting();
            GlStateManagerHelper.enableDepth();
            RenderHelper.func_74519_b();
            GlStateManagerHelper.enableRescaleNormal();
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
}

