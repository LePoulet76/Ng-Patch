/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseLeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.StatsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class ProfilGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    protected int xSize = 319;
    protected int ySize = 249;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private String targetName;
    private String enterpriseName;
    private EntityOtherPlayerMP playerEntity = null;
    private GuiScrollBarFaction scrollBar;
    public static HashMap<String, Object> playerInfos;
    public String hoveredAction = "";
    private DynamicTexture flagTexture;
    public static Long lastPromotePlayer;

    public ProfilGui(String targetName, String enterpriseName) {
        this.targetName = targetName;
        this.enterpriseName = enterpriseName;
        loaded = false;
        playerInfos = null;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionProfilDataPacket(this.targetName, this.enterpriseName)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 168, this.guiTop + 143, 80);
        this.playerEntity = null;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (this.playerEntity == null && loaded && !this.targetName.isEmpty() || loaded && !this.targetName.isEmpty() && this.playerEntity != null && !this.playerEntity.getDisplayName().equals(this.targetName)) {
            this.playerEntity = new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, this.targetName);
        }
        if (loaded && this.flagTexture == null && playerInfos.get("factionFlag") != null && !((String)playerInfos.get("factionFlag")).isEmpty()) {
            BufferedImage image = ProfilGui.decodeToImage((String)playerInfos.get("factionFlag"));
            this.flagTexture = new DynamicTexture(image);
        }
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_profil");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        super.func_73863_a(mouseX, mouseY, par3);
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 259, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 249, 9, 10, 512.0f, 512.0f, false);
        }
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 0x191919, 1.4f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_profil");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 30, 0, 322, 251, 68, 512.0f, 512.0f, false);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 30, 0, 391, 251, 68, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3008);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 145, this.guiTop + 40, 323, 5, 150, 50, 512.0f, 512.0f, false);
            if (this.playerEntity != null) {
                GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 31, 93, 68);
                GuiInventory.func_110423_a((int)(this.guiLeft + 97), (int)(this.guiTop + 190), (int)80, (float)0.0f, (float)0.0f, (EntityLivingBase)this.playerEntity);
                GUIUtils.endGLScissor();
            }
            if (this.flagTexture != null) {
                GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 267, this.guiTop + 35, 0.0f, 0.0f, 156, 78, 30, 16, 156.0f, 78.0f, false);
                ClientEventHandler.STYLE.bindTexture("faction_profil");
                GL11.glEnable((int)3042);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3008);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 267, this.guiTop + 35, 197, 249, 30, 16, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3008);
            }
            if (NationsGUI.BADGES_RESOURCES.containsKey("badges_" + ((String)playerInfos.get("group")).toLowerCase())) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get("badges_" + ((String)playerInfos.get("group")).toLowerCase()));
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 152, this.guiTop + 45, 0.0f, 0.0f, 18, 18, 10, 10, 18.0f, 18.0f, false);
            }
            this.drawScaledString(this.getRankColor((String)playerInfos.get("group")) + playerInfos.get("group") + "", this.guiLeft + 166, this.guiTop + 46, 0xFFFFFF, 1.1f, false, false);
            String date = "";
            if (!((String)playerInfos.get("login")).isEmpty()) {
                long diff = System.currentTimeMillis() - Long.parseLong(playerInfos.get("login") + "");
                long days = diff / 86400000L;
                long hours = 0L;
                long minutes = 0L;
                long seconds = 0L;
                if (days == 0L) {
                    hours = diff / 3600000L;
                    if (hours == 0L) {
                        minutes = diff / 60000L;
                        if (minutes == 0L) {
                            seconds = diff / 1000L;
                            date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds");
                        } else {
                            date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes");
                        }
                    } else {
                        date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
                    }
                } else {
                    date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
                }
            } else {
                date = "-";
            }
            ClientEventHandler.STYLE.bindTexture("faction_profil");
            if (((String)playerInfos.get("is_unique_account")).equals("true")) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 285, this.guiTop + 82, 228, 249, 14, 14, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 285 && mouseX <= this.guiLeft + 285 + 14 && mouseY >= this.guiTop + 82 && mouseY <= this.guiTop + 82 + 14) {
                    ArrayList<String> texts = new ArrayList<String>();
                    texts.add(I18n.func_135053_a((String)"faction.profil.unique.account_1"));
                    texts.add(I18n.func_135053_a((String)"faction.profil.unique.account_2"));
                    if (((String)playerInfos.get("bourse_already_given")).equals("false")) {
                        texts.add(I18n.func_135053_a((String)"faction.profil.unique.account_3"));
                        texts.add(I18n.func_135053_a((String)"faction.profil.unique.account_4"));
                        texts.add(I18n.func_135053_a((String)"faction.profil.unique.account_5"));
                    }
                    this.drawHoveringText(texts, mouseX, mouseY, this.field_73886_k);
                }
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.date_1"), this.guiLeft + 150, this.guiTop + 67, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.date_2") + date + " " + I18n.func_135053_a((String)"faction.profil.date_3"), this.guiLeft + 150, this.guiTop + 77, 0xFFFFFF, 1.0f, false, false);
            String powerString = "Power : " + playerInfos.get("power") + "/" + playerInfos.get("power_max");
            Float powerStringLength = Float.valueOf((float)this.field_73886_k.func_78256_a(powerString) * 0.8f);
            this.drawScaledString(powerString, this.guiLeft + 91, this.guiTop + 111, 0xB4B4B4, 0.8f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.salary") + " : " + playerInfos.get("salary") + "$", this.guiLeft + 174, this.guiTop + 111, 0xB4B4B4, 0.8f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.tax") + " : " + playerInfos.get("tax") + "$", this.guiLeft + 267, this.guiTop + 111, 0xB4B4B4, 0.8f, true, false);
            if ((float)mouseX >= (float)(this.guiLeft + 91) - powerStringLength.floatValue() / 2.0f && (float)mouseX <= (float)(this.guiLeft + 91) - powerStringLength.floatValue() / 2.0f + powerStringLength.floatValue() && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 109 + 8) {
                this.drawHoveringText(Arrays.asList("\u00a7a+" + playerInfos.get("power_bonus") + " powerboost", "\u00a76+" + playerInfos.get("power_hour") + "/" + I18n.func_135053_a((String)"faction.common.hour") + ", " + playerInfos.get("power_death") + "/" + I18n.func_135053_a((String)"faction.common.death")), mouseX, mouseY, this.field_73886_k);
            }
            ArrayList actions = (ArrayList)playerInfos.get("actions");
            this.hoveredAction = "";
            if (actions.size() > 0) {
                GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 139, 117, 88);
                for (int j = 0; j < actions.size(); ++j) {
                    int offsetX = this.guiLeft + 51;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 139 + j * 20) + this.getSlide());
                    ClientEventHandler.STYLE.bindTexture("faction_profil");
                    if (mouseX >= offsetX && mouseX <= offsetX + 117 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 20.0f) {
                        this.hoveredAction = (String)actions.get(j);
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 323, 56, 117, 20, 512.0f, 512.0f, false);
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 51, 139, 117, 20, 512.0f, 512.0f, false);
                    }
                    String actionHumanName = "";
                    actionHumanName = ((String)actions.get(j)).contains("promote_") ? I18n.func_135053_a((String)"faction.profil.actions.promote") + " " + I18n.func_135053_a((String)("faction.profil.actions.rank." + ((String)actions.get(j)).split("_")[1])) : (((String)actions.get(j)).contains("demote_") ? I18n.func_135053_a((String)"faction.profil.actions.demote") + " " + I18n.func_135053_a((String)("faction.profil.actions.rank." + ((String)actions.get(j)).split("_")[1])) : I18n.func_135053_a((String)("faction.profil.actions." + (String)actions.get(j))));
                    this.drawScaledString(actionHumanName, offsetX + 6, offsetY.intValue() + 6, 0xB4B4B4, 0.85f, false, false);
                }
                GUIUtils.endGLScissor();
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.boutons.skills"), this.guiLeft + 184, this.guiTop + 145, 0xB4B4B4, 0.85f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.profil.boutons.stats"), this.guiLeft + 184, this.guiTop + 168, 0xB4B4B4, 0.85f, false, false);
            if (mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 125 && mouseY > this.guiTop + 138 && mouseY < this.guiTop + 138 + 90) {
                this.scrollBar.draw(mouseX, mouseY);
            }
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
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
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.enterpriseName != null && !this.enterpriseName.isEmpty()) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseGui(this.enterpriseName));
                } else if (FactionGui_OLD.factionInfos != null && FactionGui_OLD.factionInfos.get("name") != null) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGui_OLD((String)FactionGui_OLD.factionInfos.get("name")));
                } else {
                    Minecraft.func_71410_x().func_71373_a(null);
                }
            } else if (mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 117 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 139 + 88) {
                if (!this.hoveredAction.isEmpty()) {
                    if (this.hoveredAction.contains("promote")) {
                        if (System.currentTimeMillis() - lastPromotePlayer < 2000L) {
                            return;
                        }
                        lastPromotePlayer = System.currentTimeMillis();
                    }
                    if (this.hoveredAction.equals("promote_leader")) {
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new LeaderConfirmGui(this, this.targetName));
                    } else if (this.hoveredAction.equals("enterprise_leader")) {
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseLeaderConfirmGui(this, this.targetName, this.enterpriseName));
                    } else {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(this.targetName, this.enterpriseName, this.hoveredAction)));
                    }
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    this.hoveredAction = "";
                }
            } else if (mouseX > this.guiLeft + 179 && mouseX < this.guiLeft + 179 + 124 && mouseY > this.guiTop + 138 && mouseY < this.guiTop + 138 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SkillsGui(this.targetName));
            } else if (mouseX > this.guiLeft + 179 && mouseX < this.guiLeft + 179 + 124 && mouseY > this.guiTop + 161 && mouseY < this.guiTop + 161 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new StatsGUI(this.targetName));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return ((ArrayList)playerInfos.get("actions")).size() > 4 ? (float)(-(((ArrayList)playerInfos.get("actions")).size() - 4) * 20) * this.scrollBar.getSliderValue() : 0.0f;
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

    public boolean func_73868_f() {
        return false;
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

    public String getRankColor(String rank) {
        String res = "\u00a7f";
        switch (rank.toLowerCase()) {
            case "heros": {
                res = "\u00a77";
                break;
            }
            case "legende": {
                res = "\u00a73";
                break;
            }
            case "premium": {
                res = "\u00a76";
                break;
            }
            case "moderateur": 
            case "moderateur_plus": 
            case "moderateur_test": {
                res = "\u00a7a";
                break;
            }
            case "supermodo": {
                res = "\u00a79";
                break;
            }
            case "admin": {
                res = "\u00a7c";
                break;
            }
            case "respadmin": {
                res = "\u00a74";
                break;
            }
            case "fondateur": 
            case "co-fonda": {
                res = "\u00a7b";
            }
        }
        return res;
    }

    static {
        lastPromotePlayer = 0L;
    }
}

