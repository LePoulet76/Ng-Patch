/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticBuyPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticTurnWheelPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CommonCosmeticGUI
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static int COLOR_LIGHT_GRAY = 10395075;
    public static int COLOR_DARK_BLUE = 2499659;
    public static int COLOR_LIGHT_BLUE = 6249630;
    public static int COLOR_WHITE = 0xDADAED;
    public static int COLOR_PINK = 0xAE6EEE;
    public static HashMap<String, Object> data = new HashMap();
    public static List<String> CATEGORIES_ORDER = Arrays.asList("hats", "chestplates", "capes", "hands", "armors", "items", "vehicles", "badges", "emotes", "buddies");
    public static List<String> SERVERS_ORDER = Arrays.asList("red", "coral", "orange", "yellow", "lime", "green", "blue", "cyan", "pink", "purple", "white", "black", "mocha", "dev");
    public static LinkedHashMap<String, Integer> SERVERS_COLOR = new LinkedHashMap<String, Integer>(){
        {
            this.put("red", -1760196);
            this.put("coral", -168352);
            this.put("orange", -28116);
            this.put("yellow", -606204);
            this.put("lime", -8730273);
            this.put("green", -11232173);
            this.put("blue", -9537810);
            this.put("cyan", -9518866);
            this.put("pink", -35401);
            this.put("purple", -5345554);
            this.put("white", -1314054);
            this.put("black", -8224108);
            this.put("dev", -1314054);
        }
    };
    public static CFontRenderer semiBold25 = ModernGui.getCustomFont("georamaSemiBold", 25);
    public static CFontRenderer semiBold27 = ModernGui.getCustomFont("georamaSemiBold", 27);
    public static CFontRenderer semiBold30 = ModernGui.getCustomFont("georamaSemiBold", 30);
    public static CFontRenderer semiBold32 = ModernGui.getCustomFont("georamaSemiBold", 32);
    public static CFontRenderer semiBold24 = ModernGui.getCustomFont("georamaSemiBold", 24);
    public static CFontRenderer bold28 = ModernGui.getCustomFont("georamaBold", 28);
    public static CFontRenderer medium26 = ModernGui.getCustomFont("georamaMedium", 26);
    public static LinkedHashMap<String, Integer> rarityColors = new LinkedHashMap<String, Integer>(){
        {
            this.put("common", -8730273);
            this.put("rare", -9518866);
            this.put("epic", -5345554);
            this.put("limited", -606204);
        }
    };
    public static LinkedHashMap<String, Integer> marketBackgroundByRarityY = new LinkedHashMap<String, Integer>(){
        {
            this.put("common", 569);
            this.put("rare", 624);
            this.put("epic", 679);
            this.put("limited", 736);
        }
    };
    public static LinkedHashMap<String, Integer> modalColorByRarityY = new LinkedHashMap<String, Integer>(){
        {
            this.put("common", 563);
            this.put("rare", 679);
            this.put("epic", 798);
            this.put("limited", 913);
        }
    };
    public static LinkedHashMap<String, Integer> categoryIcons49Y = new LinkedHashMap<String, Integer>(){
        {
            this.put("hats", 0);
            this.put("chestplates", 49);
            this.put("capes", 98);
            this.put("hands", 147);
            this.put("armors", 196);
            this.put("items", 245);
            this.put("vehicles", 294);
            this.put("badges", 343);
            this.put("emotes", 392);
            this.put("buddies", 441);
        }
    };
    public static LinkedHashMap<String, Integer> eventIcons = new LinkedHashMap<String, Integer>(){
        {
            this.put("patrick", 154);
            this.put("valentine", 168);
            this.put("birthday", 182);
            this.put("halloween", 196);
            this.put("easter", 210);
            this.put("christmas", 224);
            this.put("fidelity", 252);
            this.put("music", 266);
            this.put("summer", 280);
        }
    };
    public static Gson gson = new Gson();
    public String hoveredAction = "";
    public int xSize = 463;
    public int ySize = 235;
    public int guiLeft;
    public int guiTop;
    public RenderItem itemRenderer = new RenderItem();
    public List<String> tooltipToDraw = new ArrayList<String>();
    public boolean displayModal = false;
    public boolean displayNGWheel = false;
    public HashMap<String, String> itemToBuy = new HashMap();
    public HashMap<String, String> itemToBuyHover = new HashMap();
    public String categoryTarget = "";
    public String playerTarget;
    public static int lastPoints = -1;
    public static long lastPointsAnimation = -1L;
    private int targetWheelRotation = 0;
    private int currentWheelRotation = 0;
    private Long startWheelTime = 0L;

    public static String formatTime(Long time) {
        long now = Long.parseLong((String)CosmeticGUI.data.get("serverTime")) + (System.currentTimeMillis() - CosmeticGUI.timeOpenGUI);
        long diff = time - now;
        String date = "";
        if (diff > 0L) {
            long days = diff / 86400000L;
            long hours = 0L;
            long minutes = 0L;
            long seconds = 0L;
            if (days > 0L) {
                date = date + " " + days + I18n.func_135053_a((String)"faction.common.days.short");
            }
            hours = (diff -= days * 86400000L) / 3600000L;
            date = date + " " + hours + I18n.func_135053_a((String)"faction.common.hours.short");
            minutes = (diff -= hours * 3600000L) / 60000L;
            date = date + " " + minutes + I18n.func_135053_a((String)"faction.common.minutes.short");
            if (days == 0L) {
                seconds = (diff -= minutes * 60000L) / 1000L;
                date = date + " " + seconds + I18n.func_135053_a((String)"faction.common.seconds.short");
            }
            return date.trim();
        }
        return I18n.func_135053_a((String)"cosmetic.label.expired");
    }

    public CommonCosmeticGUI() {
        this.itemToBuy.clear();
        if (System.currentTimeMillis() - lastPointsAnimation > 10000L) {
            lastPointsAnimation = System.currentTimeMillis();
            lastPoints = 0;
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.contains("store")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                MainGUI.openURL(System.getProperty("java.lang").equals("fr") ? "https://nationsglory.fr/store" : "https://nationsglory.com/store");
            } else if (this.hoveredAction.contains("open_url")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                MainGUI.openURL(this.hoveredAction.replaceAll("open_url#", ""));
            } else if (this.hoveredAction.contains("buy_prime")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                MainGUI.openURL(System.getProperty("java.lang").equals("fr") ? "https://nationsglory.fr/store/package/96" : "https://nationsglory.com/store/package/96");
            } else if (this.hoveredAction.contains("open_modal")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.displayModal = true;
                this.itemToBuy = (HashMap)this.itemToBuyHover.clone();
            } else if (this.hoveredAction.equals("open_ngprime")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.displayModal = true;
                this.displayNGWheel = true;
            } else if (this.hoveredAction.equals("close_modal")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.displayModal = false;
                this.itemToBuy.clear();
            } else if (this.hoveredAction.equals("buy")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (!this.itemToBuy.isEmpty()) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticBuyPacket(this.playerTarget, this.itemToBuy.get("skin_name"), this.categoryTarget)));
                }
                this.displayModal = false;
                this.itemToBuy.clear();
            } else if (this.hoveredAction.contains("spin_wheel")) {
                this.hoveredAction = "";
                ClientProxy.playClientMusic("https://static.nationsglory.fr/N34322663N.mp3", 2.5f);
                this.startWheelTime = System.currentTimeMillis();
                this.targetWheelRotation = 1800 - (int)((Double)CosmeticGUI.data.get("win") / 12.0 * 360.0);
                this.currentWheelRotation = 0;
                CosmeticGUI.data.put("lastWheel", System.currentTimeMillis() + "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticTurnWheelPacket()));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        if (mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 13, 593 * GUI_SCALE, 132 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 13, 593 * GUI_SCALE, 119 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        if (mouseX >= this.guiLeft + 381 && mouseX <= this.guiLeft + 381 + 57 && mouseY >= this.guiTop + 11 && mouseY <= this.guiTop + 11 + 14) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 381, this.guiTop + 11, 619 * GUI_SCALE, 136 * GUI_SCALE, 56 * GUI_SCALE, 14 * GUI_SCALE, 56, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.store"), this.guiLeft + 381 + 29, this.guiTop + 15, COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
            this.hoveredAction = "store";
        } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.store"), this.guiLeft + 381 + 29, this.guiTop + 15, COLOR_WHITE, 0.5f, "center", false, "georamaSemiBold", 27);
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        if (CosmeticGUI.loaded) {
            int balance = ((Double)CosmeticGUI.data.get("player_points")).intValue();
            if (lastPoints != -1 && System.currentTimeMillis() - lastPointsAnimation < 1000L) {
                double gap = balance - lastPoints;
                Double progress = (double)(System.currentTimeMillis() - lastPointsAnimation) / 1000.0 * gap;
                balance = lastPoints + progress.intValue();
            } else {
                lastPoints = balance;
            }
            ModernGui.drawScaledStringCustomFont(balance + "", this.guiLeft + 281, (float)this.guiTop + 14.5f, COLOR_LIGHT_GRAY, 0.5f, "right", false, "georamaSemiBold", 30);
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        if (mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 65 && mouseY >= this.guiTop + 11 && mouseY <= this.guiTop + 11 + 14) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 300, this.guiTop + 11, 709 * GUI_SCALE, 154 * GUI_SCALE, 75 * GUI_SCALE, 14 * GUI_SCALE, 75, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.prime_wheel"), this.guiLeft + 301 + 32, this.guiTop + 15, COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
            this.hoveredAction = "open_ngprime";
        } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.prime_wheel"), this.guiLeft + 301 + 32, this.guiTop + 15, COLOR_WHITE, 0.5f, "center", false, "georamaSemiBold", 27);
        }
        if (this.displayModal) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)250.0f);
            Gui.func_73734_a((int)this.guiLeft, (int)this.guiTop, (int)(this.guiLeft + this.xSize), (int)(this.guiTop + this.ySize), (int)-1090519040);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (!this.itemToBuy.isEmpty()) {
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 94, this.guiTop + 61, 0 * GUI_SCALE, 563 * GUI_SCALE, 276 * GUI_SCALE, 111 * GUI_SCALE, 276, 111, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 94, this.guiTop + 61, 0 * GUI_SCALE, modalColorByRarityY.get(this.itemToBuy.get("rarity")) * GUI_SCALE, 276 * GUI_SCALE, 111 * GUI_SCALE, 276, 111, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                if (mouseX >= this.guiLeft + 374 && mouseX <= this.guiLeft + 374 + 10 && mouseY >= this.guiTop + 63 && mouseY <= this.guiTop + 63 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 374, this.guiTop + 63, 593 * GUI_SCALE, 132 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "close_modal";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 374, this.guiTop + 63, 593 * GUI_SCALE, 119 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                }
                if (mouseX < this.guiLeft + 94 || mouseX > this.guiLeft + 94 + 276 || mouseY < this.guiTop + 61 || mouseY > this.guiTop + 61 + 111) {
                    this.hoveredAction = "close_modal";
                }
                if (ClientProxy.SKIN_MANAGER.getSkinFromID(this.itemToBuy.get("skin_name")) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID(this.itemToBuy.get("skin_name")).renderInGUI(this.guiLeft + 94 + 3, this.guiTop + 61 + 10, 4.0f, par3);
                }
                String skinTitle = this.itemToBuy.containsKey("name_" + System.getProperty("java.lang")) ? this.itemToBuy.get("name_" + System.getProperty("java.lang")).toUpperCase() : this.itemToBuy.get("skin_name").toUpperCase();
                ModernGui.drawScaledStringCustomFont(skinTitle, this.guiLeft + 200, this.guiTop + 85, CosmeticGUI.COLOR_LIGHT_GRAY, 0.75f, "left", false, "georamaSemiBold", 30);
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.glColorHex((Integer)CosmeticGUI.rarityColors.get(this.itemToBuy.get("rarity")), 1.0f);
                ModernGui.drawRoundedRectangle(this.guiLeft + 200, this.guiTop + 73, 0.0f, 35.0f, 9.0f);
                String label = I18n.func_135053_a((String)("cosmetic.rarity." + this.itemToBuy.get("rarity")));
                ModernGui.drawScaledStringCustomFont(label, this.guiLeft + 200 + 17, (float)this.guiTop + 74.5f, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                ModernGui.drawSectionStringCustomFont(this.itemToBuy.containsKey("description_" + System.getProperty("java.lang")) ? this.itemToBuy.get("description_" + System.getProperty("java.lang")) : "no description set", this.guiLeft + 200, this.guiTop + 102, CosmeticGUI.COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaMedium", 25, 9, 320);
                ModernGui.drawScaledStringCustomFont(this.itemToBuy.get("price"), this.guiLeft + 200, this.guiTop + 130, CosmeticGUI.COLOR_WHITE, 0.75f, "left", false, "georamaSemiBold", 25);
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 200) + 2.5f + (float)((int)((double)semiBold25.getStringWidth(this.itemToBuy.get("price")) * 0.75)), (float)this.guiTop + 130.3f, 594 * GUI_SCALE, 179 * GUI_SCALE, 9 * GUI_SCALE, 8 * GUI_SCALE, 9, 8, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                if (mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 39 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 200, this.guiTop + 145, 283 * GUI_SCALE, 579 * GUI_SCALE, 39 * GUI_SCALE, 14 * GUI_SCALE, 39, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.return"), this.guiLeft + 200 + 19, (float)(this.guiTop + 145) + 4.0f, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 25);
                    this.hoveredAction = "close_modal";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 200, this.guiTop + 145, 283 * GUI_SCALE, 563 * GUI_SCALE, 39 * GUI_SCALE, 14 * GUI_SCALE, 39, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.return"), this.guiLeft + 200 + 19, (float)(this.guiTop + 145) + 4.0f, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 25);
                }
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                if ((double)Integer.parseInt(this.itemToBuy.get("price")) <= (Double)CosmeticGUI.data.get("player_points")) {
                    if (mouseX >= this.guiLeft + 245 && mouseX <= this.guiLeft + 245 + 46 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 145, 283 * GUI_SCALE, 611 * GUI_SCALE, 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.buy"), this.guiLeft + 245 + 23, (float)(this.guiTop + 145) + 4.0f, CosmeticGUI.COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 25);
                        this.hoveredAction = "buy";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 145, 283 * GUI_SCALE, 595 * GUI_SCALE, 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.buy"), this.guiLeft + 245 + 23, (float)(this.guiTop + 145) + 4.0f, CosmeticGUI.COLOR_WHITE, 0.5f, "center", false, "georamaSemiBold", 25);
                    }
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 145, 283 * GUI_SCALE, 627 * GUI_SCALE, 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.buy"), this.guiLeft + 245 + 23, (float)(this.guiTop + 145) + 4.0f, CosmeticGUI.COLOR_LIGHT_GRAY, 0.5f, "center", false, "georamaSemiBold", 25);
                    if (mouseX >= this.guiLeft + 245 && mouseX <= this.guiLeft + 245 + 46 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 14) {
                        this.tooltipToDraw.add(I18n.func_135053_a((String)"cosmetic.label.not_enough_points"));
                    }
                }
            } else if (this.displayNGWheel) {
                ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 150, this.guiTop + 54, 0 * GUI_SCALE, 0 * GUI_SCALE, 224 * GUI_SCALE, 115 * GUI_SCALE, 224, 115, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 77 + 14 - 90, this.guiTop + 37 + 14 - 90, 61 * GUI_SCALE, 270 * GUI_SCALE, 298 * GUI_SCALE, 298 * GUI_SCALE, 298, 298, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.guiLeft + 77 + 73), (float)(this.guiTop + 37 + 73), (float)0.0f);
                this.currentWheelRotation = this.targetWheelRotation;
                if (System.currentTimeMillis() - this.startWheelTime < 3500L) {
                    this.currentWheelRotation = (int)(0.0 + (double)(this.targetWheelRotation - 0) * (1.0 - Math.cos(Math.PI * (double)(System.currentTimeMillis() - this.startWheelTime) / 3500.0)) / 2.0);
                }
                GL11.glRotatef((float)this.currentWheelRotation, (float)0.0f, (float)0.0f, (float)1.0f);
                ModernGui.drawScaledCustomSizeModalRect(-73.0f, -73.0f, 17 * GUI_SCALE, 124 * GUI_SCALE, 146 * GUI_SCALE, 146 * GUI_SCALE, 146, 146, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                for (int i = 0; i < 12; ++i) {
                    double angle = 0.5235987755982988 * (double)i;
                    int x = (int)(0.0 + 45.0 * Math.cos(angle));
                    int y = (int)(0.0 + 45.0 * Math.sin(angle));
                    if (this.targetWheelRotation != 0) {
                        String price = (String)((List)CosmeticGUI.data.get("prizes")).get(i);
                        if (this.isNumeric(price)) {
                            ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                            ModernGui.drawScaledCustomSizeModalRect(x - 6, y - 6, 213 * GUI_SCALE, 173 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                            continue;
                        }
                        if (ClientProxy.SKIN_MANAGER.getSkinFromID(price) != null) {
                            ClientProxy.SKIN_MANAGER.getSkinFromID(price).renderInGUI(x - 10, y - 10, 0.75f, par3);
                        }
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        continue;
                    }
                    ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                    ModernGui.drawScaledCustomSizeModalRect(x - 4, y - 6, 215 * GUI_SCALE, (i % 2 == 0 ? 216 : 199) * GUI_SCALE, 8 * GUI_SCALE, 13 * GUI_SCALE, 8, 13, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                }
                ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                GL11.glPopMatrix();
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 202, this.guiTop + 106, 195 * GUI_SCALE, 125 * GUI_SCALE, 36 * GUI_SCALE, 17 * GUI_SCALE, 36, 17, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                if (mouseX >= this.guiLeft + 379 && mouseX <= this.guiLeft + 379 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 379, this.guiTop + 50, 593 * GUI_SCALE, 132 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "close_modal";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 379, this.guiTop + 50, 593 * GUI_SCALE, 119 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                }
                if (mouseX < this.guiLeft + 77 || mouseX > this.guiLeft + 77 + 297 || mouseY < this.guiTop + 37 || mouseY > this.guiTop + 37 + 146) {
                    this.hoveredAction = "close_modal";
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.wheel.title"), this.guiLeft + 243, this.guiTop + 69, COLOR_WHITE, 1.0f, "left", false, "georamaExtraBold", 28);
                if (this.targetWheelRotation != 0 && this.targetWheelRotation == this.currentWheelRotation) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.prime_wheel.win"), this.guiLeft + 243, this.guiTop + 90, COLOR_PINK, 0.5f, "left", false, "georamaBold", 30);
                    if (((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).containsKey((Object)"skin_name")) {
                        String name = ((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).containsKey((Object)("name_" + System.getProperty("java.lang"))) ? (String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)("name_" + System.getProperty("java.lang"))) : (String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)"skin_name");
                        ModernGui.drawScaledStringCustomFont(name, this.guiLeft + 243, this.guiTop + 100, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 30);
                        ModernGui.glColorHex((Integer)CosmeticGUI.rarityColors.get(((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)"rarity")), 1.0f);
                        ModernGui.drawRoundedRectangle((float)(this.guiLeft + 246) + semiBold30.getStringWidth(name) * 0.5f, this.guiTop + 100, 0.0f, 35.0f, 9.0f);
                        String label = I18n.func_135053_a((String)("cosmetic.rarity." + (String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)"rarity")));
                        ModernGui.drawScaledStringCustomFont(label, (float)(this.guiLeft + 246 + 17) + semiBold30.getStringWidth(name) * 0.5f, this.guiTop + 101, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                    } else {
                        ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)"points"), this.guiLeft + 243, this.guiTop + 100, COLOR_LIGHT_GRAY, 0.75f, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 246) + semiBold30.getStringWidth((String)((LinkedTreeMap)CosmeticGUI.data.get("prizeInfos")).get((Object)"points")) * 0.75f, this.guiTop + 99, 213 * GUI_SCALE, 173 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    }
                } else {
                    ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"cosmetic.wheel.description"), this.guiLeft + 243, this.guiTop + 90, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaMedium", 26, 8, 240);
                }
                ClientEventHandler.STYLE.bindTexture("cosmetic_prime");
                if (CosmeticGUI.loaded) {
                    boolean isPrime = (Boolean)CosmeticGUI.data.get("ngprime");
                    boolean canSpin = CosmeticGUI.data.containsKey("prizes") && Long.parseLong((String)CosmeticGUI.data.get("serverTime")) + (System.currentTimeMillis() - CosmeticGUI.timeOpenGUI) - 86400000L > Long.parseLong((String)CosmeticGUI.data.get("lastWheel"));
                    boolean isMouseOver = mouseX >= this.guiLeft + 243 && mouseX <= this.guiLeft + 243 + 92 && mouseY >= this.guiTop + 143 && mouseY <= this.guiTop + 143 + 14;
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 243, this.guiTop + 143, 259 * GUI_SCALE, (isMouseOver && (!isPrime || canSpin) ? 60 : (!isPrime ? 20 : (!canSpin ? 40 : 0))) * GUI_SCALE, 92 * GUI_SCALE, 14 * GUI_SCALE, 92, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    String label = I18n.func_135053_a((String)"cosmetic.wheel.spin");
                    if (!isPrime) {
                        label = I18n.func_135053_a((String)"cosmetic.wheel.not_prime");
                    } else if (!canSpin) {
                        label = I18n.func_135053_a((String)"cosmetic.wheel.play_cooldown") + " " + CommonCosmeticGUI.formatTime(Long.parseLong((String)CosmeticGUI.data.get("lastWheel")) + 86400000L);
                    }
                    ModernGui.drawScaledStringCustomFont(label, this.guiLeft + 243 + 46, (float)(this.guiTop + 143) + 4.0f, isMouseOver && (!isPrime || canSpin) ? COLOR_LIGHT_BLUE : (!isPrime ? COLOR_WHITE : (!canSpin ? COLOR_PINK : COLOR_WHITE)), 0.5f, "center", false, "georamaSemiBold", 25);
                    if (isMouseOver && canSpin) {
                        this.hoveredAction = "spin_wheel";
                    } else if (isMouseOver && !isPrime) {
                        this.hoveredAction = "buy_prime";
                    }
                }
            }
            GL11.glPopMatrix();
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean isBehindModal(int mouseX, int mouseY) {
        return this.displayModal && mouseX >= this.guiLeft + 94 && mouseX <= this.guiLeft + 94 + 276 && mouseY >= this.guiTop + 61 && mouseY <= this.guiTop + 61 + 111;
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

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}

