/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBadgeDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class BadgesGUI
extends GuiScreen {
    protected int xSize = 237;
    protected int ySize = 222;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    public static NBTTagCompound CLIENT_BADGES = null;
    public static List<String> CLIENT_ANIMS = new ArrayList<String>();
    public static String CLICKED_BADGE = null;
    private String targetName;
    private String[] availableBadges;
    private GuiScrollBar scrollBar;
    private String hoveredBadge = "";

    public BadgesGUI(String targetName) {
        this.targetName = targetName;
        loaded = false;
        CLICKED_BADGE = null;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionBadgeDataPacket(this.targetName)));
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBar(this.guiLeft + 214, this.guiTop + 47, 106);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_badge");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 223 && mouseX <= this.guiLeft + 223 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 223, this.guiTop - 6, 0, 236, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 223, this.guiTop - 6, 0, 226, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.badges.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 0x191919, 1.4f, false, false);
        if (loaded) {
            this.availableBadges = CLIENT_BADGES.func_74779_i("AvailableBadges").split(",");
            this.hoveredBadge = "";
            if (CLICKED_BADGE != null && NationsGUI.BADGES_RESOURCES.containsKey(CLICKED_BADGE)) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(CLICKED_BADGE));
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 60, this.guiTop + 176, 0, 0, 18, 18, 18.0f, 18.0f, false);
                if (NationsGUI.BADGES_NAMES.containsKey(CLICKED_BADGE)) {
                    this.drawScaledString(NationsGUI.BADGES_NAMES.get(CLICKED_BADGE), this.guiLeft + 60 + 25, this.guiTop + 182, 0xFFFFFF, 1.2f, false, true);
                } else {
                    this.drawScaledString(CLICKED_BADGE, this.guiLeft + 60 + 25, this.guiTop + 182, 0xFFFFFF, 1.2f, false, true);
                }
            }
            if (CLIENT_BADGES != null && CLIENT_BADGES.func_74764_b("AvailableBadges")) {
                GUIUtils.startGLScissor(this.guiLeft + 46, this.guiTop + 45, 164, 110);
                int i = 0;
                for (String availableBadge : this.availableBadges) {
                    int j = i % 9;
                    int k = i / 9;
                    if (this.availableBadges == null || availableBadge.isEmpty() || !NationsGUI.BADGES_RESOURCES.containsKey(availableBadge)) continue;
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(availableBadge));
                    int offsetX = this.guiLeft + 47 + j * 18;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 46 + k * 18) + this.getSlide());
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 2, 0.0f, 0.0f, 18, 18, 14, 14, 18.0f, 18.0f, false);
                    if (mouseX >= offsetX && mouseX <= offsetX + 18 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 18.0f) {
                        if (NationsGUI.BADGES_TOOLTIPS.containsKey(availableBadge)) {
                            this.hoveredBadge = availableBadge;
                        }
                        if (Mouse.isButtonDown((int)0) && (CLICKED_BADGE == null || CLICKED_BADGE != null && !CLICKED_BADGE.equals(availableBadge))) {
                            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                            CLICKED_BADGE = availableBadge;
                        }
                    }
                    if (CLICKED_BADGE != null && CLICKED_BADGE.equals(availableBadge)) {
                        ClientEventHandler.STYLE.bindTexture("faction_badge");
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        this.field_73735_i = 300.0f;
                        int offsetXSelect = this.guiLeft + 38 + j * 18;
                        Double offsetYSelect = (double)this.guiTop + 43.5 + (double)(k * 18);
                        this.func_73729_b(offsetXSelect, offsetYSelect.intValue(), 18, 226, 24, 24);
                        this.field_73735_i = 0.0f;
                        GL11.glDisable((int)3042);
                    }
                    ++i;
                }
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
                if (!this.hoveredBadge.isEmpty()) {
                    List<String> tooltipLines = NationsGUI.BADGES_TOOLTIPS.get(this.hoveredBadge);
                    this.drawHoveringText(tooltipLines, mouseX, mouseY, this.field_73886_k);
                    this.hoveredBadge = "";
                }
            }
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > this.guiLeft + 223 && mouseX < this.guiLeft + 223 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.targetName, ""));
        }
    }

    private float getSlide() {
        return this.availableBadges.length > 54 ? (float)(-(this.availableBadges.length - 54) * 18) * this.scrollBar.getSliderValue() : 0.0f;
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

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

