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
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelAventDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelAventGivePacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class NoelAventGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static int COLOR_GOLD = 13279592;
    public static HashMap<Integer, String> gifts = new HashMap();
    public static long serverTime = 0L;
    public long lastMusicCheck = 0L;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    public static List<Integer> daysOrder = Arrays.asList(22, 16, 20, 9, 23, 2, 6, 13, 3, 8, 1, 11, 15, 14, 12, 18, 17, 4, 21, 7, 10, 5, 19, 24);
    public String hoveredAction = "";
    protected int xSize = 463;
    protected int ySize = 235;
    public ArrayList<String> stars = new ArrayList();
    public static String currentDay = "";
    public static String currentMonth = "";

    public NoelAventGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NoelAventDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N336_56N2y.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        this.func_73873_v_();
        this.hoveredAction = "";
        ArrayList<String> tooltipToDraw = new ArrayList<String>();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("noel_avent");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 31, this.guiTop + 49, 19 * GUI_SCALE, 345 * GUI_SCALE, 109 * GUI_SCALE, 4 * GUI_SCALE, 109, 4, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        long december1 = 1701385200000L;
        long december23 = 1703329200000L;
        float progress = (float)Math.max(0L, december23 - System.currentTimeMillis()) / ((float)december23 - (float)december1 * 1.0f);
        progress = 1.0f - Math.min(1.0f, progress);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 31, this.guiTop + 49, 19 * GUI_SCALE, 336 * GUI_SCALE, (int)(109.0f * progress) * GUI_SCALE, 4 * GUI_SCALE, (int)(109.0f * progress), 4, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        if (this.stars.size() < 30) {
            Random random = new Random();
            String star = random.nextInt(450) + "#" + random.nextInt(75) + "#" + System.currentTimeMillis() + "#" + (System.currentTimeMillis() + (long)(random.nextInt(2000) + 1000)) + "#" + (random.nextInt(3) + 1) + "#" + (random.nextInt(80) + 20);
            this.stars.add(star);
        }
        ArrayList<String> newStars = new ArrayList<String>();
        for (String star : this.stars) {
            GL11.glPushMatrix();
            String[] startData = star.split("#");
            float percent = (float)(System.currentTimeMillis() - Long.parseLong(startData[2])) * 1.0f / (float)(Long.parseLong(startData[3]) - Long.parseLong(startData[2]));
            GL11.glScalef((float)(percent *= (float)Integer.parseInt(startData[5]) / 100.0f), (float)percent, (float)percent);
            GL11.glTranslatef((float)((float)(this.guiLeft + 5 + Integer.parseInt(startData[0])) * (1.0f / percent) - 7.0f * percent), (float)((float)(this.guiTop + 5 + Integer.parseInt(startData[1])) * (1.0f / percent) - 7.0f * percent), (float)0.0f);
            ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 488 * GUI_SCALE, (104 + (14 * Integer.parseInt(startData[4]) - 14)) * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            GL11.glPopMatrix();
            if (System.currentTimeMillis() >= Long.parseLong(startData[3])) continue;
            newStars.add(star);
        }
        this.stars = newStars;
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 163, this.guiTop + 0, (this.field_73882_e.field_71474_y.field_74363_ab.startsWith("fr_") ? 43 : 195) * GUI_SCALE, 247 * GUI_SCALE, 128 * GUI_SCALE, 68 * GUI_SCALE, 128, 68, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"noel_avent.subtitle.left").toUpperCase(), this.guiLeft + 32, this.guiTop + 34, COLOR_GOLD, 0.5f, "left", false, "georamaSemiBold", 22, 7, 250);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"noel_avent.subtitle.right").toUpperCase(), this.guiLeft + this.xSize - 32, this.guiTop + 34, COLOR_GOLD, 0.5f, "right", false, "georamaSemiBold", 22, 7, 200);
        ClientEventHandler.STYLE.bindTexture("noel_avent");
        if (mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 13, 490 * GUI_SCALE, 147 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 13, 490 * GUI_SCALE, 159 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        if (loaded) {
            ClientEventHandler.STYLE.bindTexture("noel_avent");
            int foundMissedDay = 0;
            for (int i = 0; i < Math.min(24, daysOrder.size()); ++i) {
                int offsetX = i % 8;
                int offsetY = i / 8;
                String giftDataStr = gifts.get(daysOrder.get(i));
                String[] giftData = giftDataStr.split("#");
                ClientEventHandler.STYLE.bindTexture("noel_avent");
                if (giftData[3].equals("false") && Integer.parseInt(currentDay) > daysOrder.get(i) && currentMonth.equals("12")) {
                    ++foundMissedDay;
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24 + offsetX * 47, this.guiTop + 77 + offsetY * 47, 538 * GUI_SCALE, 1 * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    continue;
                }
                if (giftData[3].equals("true") && Integer.parseInt(currentDay) >= daysOrder.get(i)) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24 + offsetX * 47, this.guiTop + 77 + offsetY * 47, 488 * GUI_SCALE, 1 * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 28 + offsetX * 47, this.guiTop + 84 + offsetY * 47, 675 * GUI_SCALE, 13 * GUI_SCALE, 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    if (giftData[0].equals("item")) {
                        int itemID = Integer.parseInt(giftData[1].split(":")[0]);
                        int meta = giftData[1].split(":").length > 2 ? Integer.parseInt(giftData[1].split(":")[1]) : 0;
                        GL11.glPushMatrix();
                        this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), new ItemStack(itemID, meta, 0), this.guiLeft + 36 + offsetX * 47, this.guiTop + 83 + offsetY * 47);
                        GL11.glPopMatrix();
                        GL11.glDisable((int)2896);
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    } else if (giftData[0].equals("hat")) {
                        GL11.glPushMatrix();
                        GL11.glScalef((float)1.5f, (float)1.5f, (float)1.5f);
                        GL11.glPopMatrix();
                        GL11.glDisable((int)2896);
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    } else if (!giftData[0].equals("cape") && giftData[0].equals("badge") && NationsGUI.BADGES_RESOURCES.containsKey(giftData[1])) {
                        Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(giftData[1]));
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 37 + offsetX * 47, this.guiTop + 81 + offsetY * 47, 0.0f, 0.0f, 18, 18, 14, 14, 18.0f, 18.0f, false);
                    }
                    ClientEventHandler.STYLE.bindTexture("noel_avent");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 28 + offsetX * 47, this.guiTop + 84 + offsetY * 47, 633 * GUI_SCALE, 13 * GUI_SCALE, 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    if (mouseX < this.guiLeft + 24 + offsetX * 47 || mouseX > this.guiLeft + 24 + offsetX * 47 + 43 || mouseY < this.guiTop + 86 + offsetY * 47 || mouseY > this.guiTop + 86 + offsetY * 47 + 43) continue;
                    tooltipToDraw.add("\u00a74Jour " + daysOrder.get(i));
                    tooltipToDraw.add("\u00a7c" + giftData[2]);
                    continue;
                }
                if (mouseX >= this.guiLeft + 24 + offsetX * 47 && mouseX <= this.guiLeft + 24 + offsetX * 47 + 43 && mouseY >= this.guiTop + 86 + offsetY * 47 && mouseY <= this.guiTop + 86 + offsetY * 47 + 43) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 18 + offsetX * 47, this.guiTop + 71 + offsetY * 47, (daysOrder.get(i) != 24 ? 533 : 585) * GUI_SCALE, 47 * GUI_SCALE, 50 * GUI_SCALE, 50 * GUI_SCALE, 50, 50, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(daysOrder.get(i) + "", this.guiLeft + 44 + offsetX * 47, this.guiTop + 86 + offsetY * 47, i != daysOrder.size() - 1 ? COLOR_GOLD : 15788512, 1.0f, "center", false, "georamaExtraBold", 46);
                    this.hoveredAction = "give#" + daysOrder.get(i);
                    continue;
                }
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24 + offsetX * 47, this.guiTop + 77 + offsetY * 47, (daysOrder.get(i) != 24 ? 487 : 642) * GUI_SCALE, 52 * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 40, 40, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(daysOrder.get(i) + "", this.guiLeft + 45 + offsetX * 47, this.guiTop + 86 + offsetY * 47, i != daysOrder.size() - 1 ? COLOR_GOLD : 15788512, 1.0f, "center", false, "georamaExtraBold", 44);
            }
            ClientEventHandler.STYLE.bindTexture("noel_avent");
            if (foundMissedDay > 5) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 77, 538 * GUI_SCALE, 108 * GUI_SCALE, 40 * GUI_SCALE, 134 * GUI_SCALE, 40, 134, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            } else if (gifts.get(25).endsWith("true")) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 77, 637 * GUI_SCALE, 108 * GUI_SCALE, 40 * GUI_SCALE, 134 * GUI_SCALE, 40, 134, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 404, this.guiTop + 131, 675 * GUI_SCALE, 13 * GUI_SCALE, 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                GL11.glPushMatrix();
                GL11.glScalef((float)1.5f, (float)1.5f, (float)1.5f);
                GL11.glPopMatrix();
                GL11.glDisable((int)2896);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("noel_avent");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 404, this.guiTop + 131, 633 * GUI_SCALE, 13 * GUI_SCALE, 32 * GUI_SCALE, 32 * GUI_SCALE, 32, 32, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 40 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 134) {
                    tooltipToDraw.add("\u00a74Jour 25");
                    tooltipToDraw.add("\u00a7c" + gifts.get(25).split("#")[2]);
                }
            } else {
                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 40 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 134) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 395, this.guiTop + 72, 582 * GUI_SCALE, 103 * GUI_SCALE, 50 * GUI_SCALE, 154 * GUI_SCALE, 50, 154, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    this.hoveredAction = "give#25";
                }
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.guiLeft + 417), (float)(this.guiTop + 106), (float)0.0f);
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glTranslatef((float)(-(this.guiLeft + 417)), (float)(-(this.guiTop + 106)), (float)0.0f);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"noel_avent.mega_cadeau").toUpperCase(), this.guiLeft + 380, this.guiTop + 106, foundMissedDay > 5 ? 9045025 : 15788512, 0.5f, "center", false, "georamaSemiBold", 30);
                GL11.glPopMatrix();
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
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

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (loaded && this.hoveredAction.contains("give#")) {
                int day = Integer.parseInt(this.hoveredAction.replaceAll("give#", ""));
                if (Integer.parseInt(currentDay) == day && currentMonth.equals("12")) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NoelAventGivePacket()));
                    this.field_73882_e.field_71416_A.func_77366_a("random.levelup", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a(null);
                } else {
                    this.field_73882_e.field_71416_A.func_77366_a("mob.villager.no", 1.0f, 1.0f);
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }
}

