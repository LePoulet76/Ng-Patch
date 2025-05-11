/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.achievements;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AchievementsDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class AchievementsGUI
extends GuiScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsglory", "textures/gui/achievements.png");
    private RenderItem itemRenderer = new RenderItem();
    private ArrayList<HashMap<String, String>> achievements = new ArrayList();
    private int guiLeft = 0;
    private int guiTop = 0;
    private int xSize = 182;
    private int ySize = 195;
    private GuiButton refreshButton;
    private GuiScrollBar scrollBar;
    public static boolean loaded = false;
    public static boolean badgesChecked = false;
    public static boolean achievementDone = false;

    public AchievementsGUI() {
        loaded = false;
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_achievements", 1)));
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.guiLeft = this.field_73880_f / 2 - this.xSize / 2;
        this.guiTop = this.field_73881_g / 2 - this.ySize / 2;
        this.field_73887_h.add(new CloseButtonGUI(0, this.guiLeft + 161, this.guiTop + 13));
        this.scrollBar = new GuiScrollBar(this.guiLeft + 162, this.guiTop + 46, 140);
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AchievementsDataPacket()));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.guiLeft = this.field_73880_f / 2 - this.xSize / 2;
        this.guiTop = this.field_73881_g / 2 - this.ySize / 2;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("achievements");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"gui.achievements.title"), this.guiLeft + 30, this.guiTop + 11, 0xFFFFFF, 1.8f, false);
        if (loaded && ClientData.getAchievements() != null) {
            this.achievements = ClientData.getAchievements();
            ClientEventHandler.STYLE.bindTexture("achievements");
            GUIUtils.startGLScissor(this.guiLeft + 10, this.guiTop + 45, 147, 142);
            for (int i = 0; i < this.achievements.size(); ++i) {
                int offsetX = this.guiLeft + 10;
                Float offsetY = Float.valueOf((float)(this.guiTop + 45 + i * 61) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("achievements");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 0, 201, 147, 58, 512.0f, 512.0f, false);
                if (NationsGUI.BADGES_RESOURCES.containsKey(this.achievements.get(i).get("badge"))) {
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(this.achievements.get(i).get("badge")));
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 9, offsetY.intValue() + 9, 0, 0, 18, 18, 18.0f, 18.0f, false);
                }
                this.drawScaledString(this.achievements.get(i).get("name"), offsetX + 33, offsetY.intValue() + 10, 0xFFFFFF, 1.1f, false);
                int descLineNumber = 0;
                for (String descLine : this.achievements.get(i).get("description").split("\n")) {
                    this.drawScaledString(descLine, offsetX + 33, offsetY.intValue() + 23 + 10 * descLineNumber, 0xB4B4B4, 1.0f, false);
                    ++descLineNumber;
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int progress = Integer.parseInt(this.achievements.get(i).get("progress"));
                int maxBarSize = 147;
                Double barSize = (double)maxBarSize * ((double)progress * 1.0 / 100.0);
                ClientEventHandler.STYLE.bindTexture("achievements");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue() + 52, 0, 195, barSize.intValue(), 6, 512.0f, 512.0f, false);
                if (mouseX <= offsetX || mouseX >= offsetX + 147 || mouseY <= offsetY.intValue() + 52 || mouseY >= offsetY.intValue() + 52 + 6) continue;
                this.drawHoveringText(Collections.singletonList("\u00a7o" + progress + "%"), mouseX, mouseY, this.field_73886_k);
            }
            GUIUtils.endGLScissor();
        }
        this.scrollBar.draw(mouseX, mouseY);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    private float getSlide() {
        return this.achievements.size() > 2 ? (float)(-(this.achievements.size() - 2) * 58) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
    }

    public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer) {
        if (!text.isEmpty()) {
            GL11.glDisable((int)32826);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int width = 0;
            for (String line : text) {
                int lineWidth = fontRenderer.func_78256_a(line);
                width = Math.max(width, lineWidth);
            }
            int posX = mouseX + 12;
            int posY = mouseY - 12;
            int offsetY = 8;
            if (text.size() > 1) {
                offsetY += 2 + (text.size() - 1) * 10;
            }
            if (posX + width > this.field_73880_f) {
                posX -= 28 + width;
            }
            if (posY + offsetY + 6 > this.field_73881_g) {
                posY = this.field_73881_g - offsetY - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int color1 = -267386864;
            this.func_73733_a(posX - 3, posY - 4, posX + width + 3, posY - 3, color1, color1);
            this.func_73733_a(posX - 3, posY + offsetY + 3, posX + width + 3, posY + offsetY + 4, color1, color1);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX - 4, posY - 3, posX - 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX + width + 3, posY - 3, posX + width + 4, posY + offsetY + 3, color1, color1);
            int color2 = 0x505000FF;
            int color3 = (color2 & 0xFEFEFE) >> 1 | color2 & 0xFF000000;
            this.func_73733_a(posX - 3, posY - 3 + 1, posX - 3 + 1, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX + width + 2, posY - 3 + 1, posX + width + 3, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY - 3 + 1, color2, color2);
            this.func_73733_a(posX - 3, posY + offsetY + 2, posX + width + 3, posY + offsetY + 3, color3, color3);
            for (int i = 0; i < text.size(); ++i) {
                String line = text.get(i);
                if (i == 0) {
                    fontRenderer.func_78261_a(line, posX, posY, -1);
                    posY += 2;
                } else {
                    fontRenderer.func_78261_a(EnumChatFormatting.GOLD + line, posX + width - fontRenderer.func_78256_a(line), posY, 0xFFFFFF);
                }
                posY += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(null);
            this.field_73882_e.func_71381_h();
            this.field_73882_e.field_71416_A.func_82461_f();
        }
    }

    public boolean func_73868_f() {
        return false;
    }
}

