/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.EmotesGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CosmeticGUI_OLD
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/cosmetic.png");
    public static NBTTagCompound CLIENT_BADGES = null;
    public static String CLICKED_BADGE = null;
    public static String MAIN_BADGE = null;
    public static boolean loaded = false;
    private String[] availableBadges;
    private String[] activeBadges;
    private RenderItem itemRenderer = new RenderItem();
    private int guiLeft = 0;
    private int guiTop = 0;
    private int xSize = 257;
    private int ySize = 209;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiScrollBar scrollBar;

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.guiLeft = this.field_73880_f / 2 - this.xSize / 2;
        this.guiTop = this.field_73881_g / 2 - this.ySize / 2;
        this.field_73887_h.clear();
        CLIENT_BADGES = null;
        CLICKED_BADGE = null;
        MAIN_BADGE = null;
        this.activeBadges = null;
        this.availableBadges = null;
        this.addButton = new TexturedButtonGUI(0, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 0, 0, I18n.func_135053_a((String)"gui.cosmetic.add"));
        this.removeButton = new TexturedButtonGUI(1, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 74, 0, I18n.func_135053_a((String)"gui.cosmetic.remove"));
        this.field_73887_h.add(new CloseButtonGUI(2, this.guiLeft + 236, this.guiTop + 17));
        this.scrollBar = new GuiScrollBar(this.guiLeft + 177, this.guiTop + 53, 106);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int i;
        int y;
        int x;
        GuiScreenTab type;
        int i2;
        this.guiLeft = this.field_73880_f / 2 - 128;
        this.guiTop = this.field_73881_g / 2 - 104;
        this.func_73873_v_();
        if (MAIN_BADGE == null && GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ))) {
            MAIN_BADGE = GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        for (i2 = 0; i2 <= 3; ++i2) {
            type = TABS.get(i2);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i2 % 4;
            y = i2 / 4;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft - 23, this.guiTop + 50 + i2 * 31, 125, 210, 29, 30);
                this.func_73729_b(this.guiLeft - 23 + 3, this.guiTop + 50 + i2 * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft - 20, this.guiTop + 50 + i2 * 31, 102, 210, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft - 20 + 3, this.guiTop + 50 + i2 * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        for (i2 = 4; i2 < TABS.size(); ++i2) {
            type = TABS.get(i2);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i2 % 4;
            y = i2 / 4;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft + 250, this.guiTop + 50 + (i2 - 4) * 31, 51, 210, 29, 30);
                this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (i2 - 4) * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft + 252, this.guiTop + 50 + (i2 - 4) * 31, 80, 210, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (i2 - 4) * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        this.drawScaledString(I18n.func_135053_a((String)"gui.cosmetic.title"), this.guiLeft + 20, this.guiTop + 15, 0xFFFFFF, 2.0f, false);
        if (loaded) {
            if (this.activeBadges == null && CLIENT_BADGES.func_74764_b("Active_Badge") && CLIENT_BADGES.func_74764_b("Active_Badge") && !CLIENT_BADGES.func_74779_i("Active_Badge").equals("null")) {
                this.activeBadges = CLIENT_BADGES.func_74779_i("Active_Badge").split(",");
            }
            this.availableBadges = CLIENT_BADGES.func_74779_i("AvailableBadges").split(",");
            this.availableBadges = this.removeAvailableBadgesIfActive(this.availableBadges);
            if (CLICKED_BADGE != null && NationsGUI.BADGES_RESOURCES.containsKey(CLICKED_BADGE)) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(CLICKED_BADGE));
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 23, this.guiTop + 175, 0, 0, 18, 18, 18.0f, 18.0f, false);
                if (NationsGUI.BADGES_NAMES.containsKey(CLICKED_BADGE)) {
                    this.drawScaledString(NationsGUI.BADGES_NAMES.get(CLICKED_BADGE), this.guiLeft + 23 + 30, this.guiTop + 180, 0xFFFFFF, 1.2f, false);
                } else {
                    this.drawScaledString(CLICKED_BADGE, this.guiLeft + 23 + 30, this.guiTop + 180, 0xFFFFFF, 1.2f, false);
                }
            }
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if (MAIN_BADGE != null) {
                GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(MAIN_BADGE));
                Double offsetX = ((double)this.guiLeft + 193.5) * 1.0 / 0.75;
                Double offsetY = (double)((this.guiTop + 54) * 1) / 0.75;
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX.intValue(), offsetY.intValue(), 0, 0, 18, 18, 18.0f, 18.0f, false);
                GL11.glScaled((double)1.3333333333333333, (double)1.3333333333333333, (double)1.3333333333333333);
            } else {
                this.func_73729_b(this.guiLeft + 191, this.guiTop + 52, 33, 210, 18, 18);
                if (mouseX > this.guiLeft + 191 && mouseX < this.guiLeft + 191 + 18 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 18) {
                    this.drawHoveringText(Collections.singletonList("\u00a7cEmplacement r\u00e9serv\u00e9"), mouseX, mouseY, this.field_73886_k);
                }
            }
            if (this.activeBadges != null) {
                int i3;
                GL11.glPushMatrix();
                GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                for (i3 = 0; i3 < this.activeBadges.length; ++i3) {
                    int j = i3 % 9;
                    int k = i3 / 9;
                    if (NationsGUI.BADGES_RESOURCES.containsKey(this.activeBadges[i3])) {
                        Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(this.activeBadges[i3]));
                        Double offsetX = ((double)this.guiLeft + 211.5 + (double)(j * 18)) * 1.0 / 0.75;
                        Double offsetY = ((double)this.guiTop + 54.5 + (double)(k * 18)) * 1.0 / 0.75;
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX.intValue(), offsetY.intValue(), 0, 0, 18, 18, 18.0f, 18.0f, false);
                    }
                    if (CLICKED_BADGE == null || !CLICKED_BADGE.equals(this.activeBadges[i3])) continue;
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    GL11.glScaled((double)1.3333333333333333, (double)1.3333333333333333, (double)1.3333333333333333);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    this.field_73735_i = 300.0f;
                    Double offsetXSelect = (double)this.guiLeft + 206.5 + (double)(j * 18);
                    Double offsetYSelect = (double)this.guiTop + 49.5 + (double)(k * 18);
                    this.func_73729_b(offsetXSelect.intValue(), offsetYSelect.intValue(), 9, 210, 24, 24);
                    this.field_73735_i = 0.0f;
                    GL11.glDisable((int)3042);
                    GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                }
                GL11.glScaled((double)1.3333333333333333, (double)1.3333333333333333, (double)1.3333333333333333);
                for (i3 = 0; i3 < this.activeBadges.length; ++i3) {
                    Double j = (double)(this.guiLeft + i3 % 9 * 18) + 211.0;
                    Double k = (double)(this.guiTop + i3 / 9 * 18) + 52.0;
                    if (!((double)mouseX > j) || !((double)mouseX < j + 18.0) || !((double)mouseY > k) || !((double)mouseY < k + 18.0)) continue;
                    if (NationsGUI.BADGES_TOOLTIPS.containsKey(this.activeBadges[i3])) {
                        List<String> tooltipLines = NationsGUI.BADGES_TOOLTIPS.get(this.activeBadges[i3]);
                        this.drawHoveringText(tooltipLines, mouseX, mouseY, this.field_73886_k);
                    }
                    if (!Mouse.isButtonDown((int)0) || CLICKED_BADGE != null && (CLICKED_BADGE == null || CLICKED_BADGE.equals(this.activeBadges[i3]))) continue;
                    Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    CLICKED_BADGE = this.activeBadges[i3];
                    this.clickOnBadge(this.activeBadges[i3]);
                }
                if (MAIN_BADGE != null) {
                    if (NationsGUI.BADGES_TOOLTIPS.containsKey(MAIN_BADGE) && mouseX > this.guiLeft + 192 && mouseX < this.guiLeft + 192 + 18 && mouseY > this.guiTop + 53 && mouseY < this.guiTop + 53 + 18) {
                        this.drawHoveringText(NationsGUI.BADGES_TOOLTIPS.get(MAIN_BADGE), mouseX, mouseY, this.field_73886_k);
                    }
                } else if (mouseX > this.guiLeft + 191 && mouseX < this.guiLeft + 191 + 18 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 18) {
                    this.drawHoveringText(Collections.singletonList("\u00a7cEmplacement r\u00e9serv\u00e9"), mouseX, mouseY, this.field_73886_k);
                }
                GL11.glPopMatrix();
                ClientEventHandler.STYLE.bindTexture("cosmetic");
            }
            if (CLIENT_BADGES != null && CLIENT_BADGES.func_74764_b("AvailableBadges")) {
                GL11.glPushMatrix();
                GUIUtils.startGLScissor(this.guiLeft + 9, this.guiTop + 51, 164, 110);
                GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                int i4 = 0;
                for (String availableBadge : this.availableBadges) {
                    int j = i4 % 9;
                    int k = i4 / 9;
                    if (!NationsGUI.BADGES_RESOURCES.containsKey(availableBadge)) continue;
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(availableBadge));
                    Double offsetX = ((double)this.guiLeft + 12.5 + (double)(j * 18)) * 1.0 / 0.75;
                    Double offsetY = ((double)this.guiTop + 54.5 + (double)(k * 18) + (double)this.getSlide()) * 1.0 / 0.75;
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX.intValue(), offsetY.intValue(), 0, 0, 18, 18, 18.0f, 18.0f, false);
                    if (CLICKED_BADGE != null && CLICKED_BADGE.equals(availableBadge)) {
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glScaled((double)1.3333333333333333, (double)1.3333333333333333, (double)1.3333333333333333);
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        this.field_73735_i = 300.0f;
                        Double offsetXSelect = (double)this.guiLeft + 7.5 + (double)(j * 18);
                        Double offsetYSelect = (double)this.guiTop + 49.0 + (double)(k * 18);
                        this.func_73729_b(offsetXSelect.intValue(), offsetYSelect.intValue(), 9, 210, 24, 24);
                        this.field_73735_i = 0.0f;
                        GL11.glDisable((int)3042);
                        GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                    }
                    ++i4;
                }
                GL11.glScaled((double)1.3333333333333333, (double)1.3333333333333333, (double)1.3333333333333333);
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
                i4 = 0;
                for (String availableBadge : this.availableBadges) {
                    if (!NationsGUI.BADGES_RESOURCES.containsKey(availableBadge)) continue;
                    Double j = (double)(this.guiLeft + i4 % 9 * 18) + 9.375;
                    Double k = (double)(this.guiTop + i4 / 9 * 18) + 52.0;
                    if ((double)mouseX > j && (double)mouseX < j + 18.0 && (double)mouseY > k && (double)mouseY < k + 18.0) {
                        if (NationsGUI.BADGES_TOOLTIPS.containsKey(availableBadge)) {
                            List<String> tooltipLines = NationsGUI.BADGES_TOOLTIPS.get(availableBadge);
                            this.drawHoveringText(tooltipLines, mouseX, mouseY, this.field_73886_k);
                        }
                        if (Mouse.isButtonDown((int)0) && (CLICKED_BADGE == null || CLICKED_BADGE != null && !CLICKED_BADGE.equals(availableBadge))) {
                            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                            CLICKED_BADGE = availableBadge;
                            this.clickOnBadge(availableBadge);
                        }
                    }
                    ++i4;
                }
                GL11.glPopMatrix();
                ClientEventHandler.STYLE.bindTexture("cosmetic");
            }
        }
        for (i = 0; i <= 3; ++i) {
            if (mouseX < this.guiLeft - (((Object)((Object)this)).getClass() == InventoryGUI.TABS.get(i).getClassReferent() ? 23 : 20) || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 50 + i * 31 || mouseY > this.guiTop + 85 + i * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.cosmetic.tab." + i))), mouseX, mouseY, this.field_73886_k);
        }
        for (i = 4; i < TABS.size(); ++i) {
            if (mouseX < this.guiLeft + 252 || mouseX > this.guiLeft + 278 || mouseY < this.guiTop + 50 + (i - 4) * 31 || mouseY > this.guiTop + 85 + (i - 4) * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.cosmetic.tab." + i))), mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    private float getSlide() {
        return this.availableBadges.length > 54 ? (float)(-(this.availableBadges.length - 54) * 18) * this.scrollBar.getSliderValue() : 0.0f;
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

    public String[] removeAvailableBadgesIfActive(String[] availableBadges) {
        ArrayList<String> availableBadgesWhichAreNotActive = new ArrayList<String>();
        for (String availableBadge : availableBadges) {
            boolean add = true;
            if (this.activeBadges != null) {
                for (String activeBadge : this.activeBadges) {
                    if (!activeBadge.equals(availableBadge)) continue;
                    add = false;
                }
            }
            if (MAIN_BADGE != null && MAIN_BADGE.equals(availableBadge)) {
                add = false;
            }
            if (!add) continue;
            availableBadgesWhichAreNotActive.add(availableBadge);
        }
        return availableBadgesWhichAreNotActive.toArray(new String[0]);
    }

    private void clickOnBadge(String badge) {
        ArrayList<String> activeBadgesTab = null;
        if (this.activeBadges != null) {
            activeBadgesTab = new ArrayList<String>(Arrays.asList(this.activeBadges));
        }
        if (activeBadgesTab != null && activeBadgesTab.contains(badge)) {
            this.field_73887_h.add(this.removeButton);
        } else {
            this.field_73887_h.add(this.addButton);
        }
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
        if (mouseButton == 0) {
            GuiScreenTab type;
            int i;
            for (i = 0; i <= 3; ++i) {
                type = TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 50 + i * 31 || mouseY > this.guiTop + 85 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (i = 4; i < TABS.size(); ++i) {
                type = TABS.get(i);
                if (mouseX < this.guiLeft + 252 || mouseX > this.guiLeft + 278 || mouseY < this.guiTop + 50 + (i - 4) * 31 || mouseY > this.guiTop + 85 + (i - 4) * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 2) {
            this.field_73882_e.func_71373_a(null);
            this.field_73882_e.func_71381_h();
            this.field_73882_e.field_71416_A.func_82461_f();
        }
        if (this.availableBadges != null) {
            if (button.field_73741_f == 0) {
                if (this.activeBadges == null || this.activeBadges.length < 2) {
                    ArrayList<String> newActiveBadges = new ArrayList<String>();
                    if (this.activeBadges != null) {
                        newActiveBadges = new ArrayList<String>(Arrays.asList(this.activeBadges));
                    }
                    newActiveBadges.add(CLICKED_BADGE);
                    this.activeBadges = newActiveBadges.toArray(new String[0]);
                    CLICKED_BADGE = null;
                    this.field_73887_h.clear();
                }
            } else if (button.field_73741_f == 1 && this.activeBadges.length > 0) {
                ArrayList<String> newActiveBadges = new ArrayList<String>(Arrays.asList(this.activeBadges));
                newActiveBadges.remove(CLICKED_BADGE);
                this.activeBadges = newActiveBadges.toArray(new String[0]);
                CLICKED_BADGE = null;
                this.field_73887_h.clear();
            }
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    static {
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new InventoryGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return CosmeticGUI_OLD.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CosmeticGUI_OLD());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return EmotesGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EmotesGUI());
            }
        });
    }
}

