/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.internal.LinkedTreeMap
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CosmeticGUI
extends CommonCosmeticGUI {
    public static HashMap<String, Object> data = new HashMap();
    public static boolean loaded = false;
    private EntityOtherPlayerMP playerEntity = null;
    public static HashMap<String, Object> cachedDataMainMenu = new HashMap();
    private static Gson gson = new Gson();
    public static Long timeOpenGUI = System.currentTimeMillis();

    public CosmeticGUI(String playerTarget) {
        this.playerTarget = playerTarget;
        data.clear();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticDataPacket(playerTarget)));
        this.playerEntity = null;
        try {
            HashMap data;
            cachedDataMainMenu = data = (HashMap)gson.fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/json/cosmetics_home.json").openStream(), StandardCharsets.UTF_8), new TypeToken<HashMap<String, Object>>(){}.getType());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (this.displayModal) {
            super.func_73864_a(mouseX, mouseY, mouseButton);
        }
        if (mouseButton == 0 && this.hoveredAction.contains("tab#")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new CosmeticCategoryGUI(this.hoveredAction.replaceAll("tab#", ""), this.playerTarget));
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean func_73868_f() {
        return false;
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        this.hoveredAction = "";
        this.tooltipToDraw.clear();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase(this.playerTarget) ? I18n.func_135053_a((String)"cosmetic.title") : I18n.func_135053_a((String)"cosmetic.title_of").replace("#player#", this.playerTarget.toUpperCase()), this.guiLeft + 12, this.guiTop + 11, 0xC4C4C4, 1.0f, "left", false, "georamaExtraBold", 28);
        LinkedTreeMap categoryCounters = (LinkedTreeMap)data.get("categoryCounters");
        LinkedTreeMap rarityCounters = (LinkedTreeMap)data.get("rarityCounters");
        LinkedTreeMap maxCounters = (LinkedTreeMap)data.get("maxCounters");
        if (!cachedDataMainMenu.isEmpty()) {
            ModernGui.glColorHex((Integer)rarityColors.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"rarity")), 1.0f);
            ModernGui.drawRoundedRectangle((float)this.guiLeft + 14.0f, (float)this.guiTop + 39.0f, this.field_73735_i, 268.0f, 105.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 14, this.guiTop + 39, 483 * GUI_SCALE, 0 * GUI_SCALE, 310 * GUI_SCALE, 105 * GUI_SCALE, 310, 105, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            GUIUtils.startGLScissor(this.guiLeft + 14, this.guiTop + 0, 185, 144);
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(this.guiLeft + 14, this.guiTop + 15, 0, 0, 500, 500, 500 / GUI_SCALE, 500 / GUI_SCALE, 500.0f, 500.0f, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"image"));
            GUIUtils.endGLScissor();
            if (!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"category")).isEmpty()) {
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.glColorHex((Integer)rarityColors.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"rarity")), 1.0f);
                ModernGui.drawRoundedRectangle(this.guiLeft + 292, this.guiTop + 35, 0.0f, 35.0f, 9.0f);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 310 - 6 - 2 - (int)(semiBold25.getStringWidth(I18n.func_135053_a((String)("cosmetic.rarity." + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"rarity")))) / 2.0f / 2.0f), this.guiTop + 34, 851 * GUI_SCALE, (Integer)categoryIcons49Y.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"category")) * GUI_SCALE, 49 * GUI_SCALE, 49 * GUI_SCALE, 12, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("cosmetic.rarity." + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"rarity"))), this.guiLeft + 310 + 5, (float)this.guiTop + 36.5f, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 25);
            }
            ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)("title_" + (System.getProperty("java.lang").equals("fr") ? "fr" : "en"))), this.guiLeft + 205, this.guiTop + 57, COLOR_WHITE, 0.5f, "left", false, "georamaSemiBold", 30);
            ModernGui.drawSectionStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)("description_" + (System.getProperty("java.lang").equals("fr") ? "fr" : "en"))), this.guiLeft + 205, this.guiTop + 73, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 25, 10, 220);
            if (!((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"link_button").equals("")) {
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 46 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 14) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 123, 283 * GUI_SCALE, 611 * GUI_SCALE, 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.get").toUpperCase(), this.guiLeft + 205 + 23, (float)this.guiTop + 126.5f, COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                    this.hoveredAction = "open_url#" + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get((Object)"link_button");
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 123, 283 * GUI_SCALE, 595 * GUI_SCALE, 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.get").toUpperCase(), this.guiLeft + 205 + 23, (float)this.guiTop + 126.5f, COLOR_WHITE, 0.5f, "center", false, "georamaSemiBold", 27);
                }
            }
        }
        if (loaded) {
            int index = 0;
            for (LinkedTreeMap marketData : (ArrayList)data.get("market")) {
                boolean mouseOver;
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 334, this.guiTop + 39 + index * 56, 345 * GUI_SCALE, (Integer)marketBackgroundByRarityY.get((String)marketData.get((Object)"rarity")) * GUI_SCALE, 118 * GUI_SCALE, 49 * GUI_SCALE, 118, 49, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                boolean bl = mouseOver = mouseX >= this.guiLeft + 334 && mouseX <= this.guiLeft + 334 + 118 && mouseY >= this.guiTop + 39 + index * 56 && mouseY <= this.guiTop + 39 + index * 56 + 49;
                if (ClientProxy.SKIN_MANAGER.getSkinFromID((String)marketData.get((Object)"skin_name")) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID((String)marketData.get((Object)"skin_name")).renderInGUI(this.guiLeft + 335 - (int)(mouseOver ? (float)(120 / GUI_SCALE) * 0.05f / 2.0f : 0.0f), this.guiTop + 40 + index * 56 - (int)(mouseOver ? (float)(120 / GUI_SCALE) * 0.05f / 2.0f : 0.0f), mouseOver ? 2.1f : 2.0f, par3);
                }
                String name = marketData.containsKey((Object)("name_" + System.getProperty("java.lang"))) ? (String)marketData.get((Object)("name_" + System.getProperty("java.lang"))) : (String)marketData.get((Object)"skin_name");
                ModernGui.drawSectionStringCustomFont(name.toUpperCase(), this.guiLeft + 389, this.guiTop + 55 + index * 56, COLOR_WHITE, 0.5f, "left", false, "georamaSemiBold", 30, 8, 90);
                if (!((String)marketData.get((Object)"market_end")).isEmpty()) {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 418, this.guiTop + 33 + index * 56, 483 * GUI_SCALE, 238 * GUI_SCALE, 39 * GUI_SCALE, 13 * GUI_SCALE, 39, 13, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(CosmeticGUI.formatTime(Long.parseLong((String)marketData.get((Object)"market_end"))), this.guiLeft + 438, this.guiTop + 37 + index * 56, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 22);
                }
                if (mouseOver && (((String)marketData.get((Object)"market_end")).isEmpty() || System.currentTimeMillis() < Long.parseLong((String)marketData.get((Object)"market_end")))) {
                    for (Map.Entry entry : marketData.entrySet()) {
                        this.itemToBuyHover.put(entry.getKey(), (String)entry.getValue());
                    }
                    this.hoveredAction = "open_modal";
                }
                ++index;
            }
        }
        int offsetX = this.guiLeft + 27;
        int textY = this.guiTop + 211;
        String text = "";
        if (loaded) {
            text = String.format("%.0f", data.get("ownedCount")) + "/" + String.format("%.0f", maxCounters.get((Object)"total")) + " " + I18n.func_135053_a((String)"cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(text, offsetX, textY, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + (semiBold25.getStringWidth(text) / 2.0f + 12.0f));
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex((Integer)rarityColors.get("limited"), 1.0f);
        ModernGui.drawRoundedRectangle(offsetX, textY - 2, 0.0f, 30.0f, 9.0f);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.rarity.limited"), offsetX + 15, textY - 1, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
        offsetX += 34;
        if (loaded) {
            text = (rarityCounters.containsKey((Object)"limited") ? String.format("%.0f", rarityCounters.get((Object)"limited")) : "0") + "/" + (maxCounters.containsKey((Object)"limited") ? String.format("%.0f", maxCounters.get((Object)"limited")) : "?") + " " + I18n.func_135053_a((String)"cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(text, offsetX, textY, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + (semiBold25.getStringWidth(text) / 2.0f + 12.0f));
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex((Integer)rarityColors.get("epic"), 1.0f);
        ModernGui.drawRoundedRectangle(offsetX, textY - 2, 0.0f, 30.0f, 9.0f);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.rarity.epic"), offsetX + 15, textY - 1, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
        offsetX += 34;
        if (loaded) {
            text = (rarityCounters.containsKey((Object)"epic") ? String.format("%.0f", rarityCounters.get((Object)"epic")) : "0") + "/" + (maxCounters.containsKey((Object)"epic") ? String.format("%.0f", maxCounters.get((Object)"epic")) : "?") + " " + I18n.func_135053_a((String)"cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(text, offsetX, textY, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + (semiBold25.getStringWidth(text) / 2.0f + 12.0f));
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex((Integer)rarityColors.get("rare"), 1.0f);
        ModernGui.drawRoundedRectangle(offsetX, textY - 2, 0.0f, 30.0f, 9.0f);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.rarity.rare"), offsetX + 15, textY - 1, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
        offsetX += 34;
        if (loaded) {
            text = (rarityCounters.containsKey((Object)"rare") ? String.format("%.0f", rarityCounters.get((Object)"rare")) : "0") + "/" + (maxCounters.containsKey((Object)"rare") ? String.format("%.0f", maxCounters.get((Object)"rare")) : "?") + " " + I18n.func_135053_a((String)"cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(text, offsetX, textY, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + (semiBold25.getStringWidth(text) / 2.0f + 12.0f));
        }
        int index = 0;
        for (String category : CATEGORIES_ORDER) {
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            if (mouseX >= this.guiLeft + 13 + 45 * index && mouseX <= this.guiLeft + 13 + 45 * index + 36 && mouseY >= this.guiTop + 161 && mouseY <= this.guiTop + 161 + 36) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 15 + 45 * index, this.guiTop + 161, 951 * GUI_SCALE, index * 30 * GUI_SCALE, 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                this.hoveredAction = "tab#" + category;
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 15 + 45 * index, this.guiTop + 161, 920 * GUI_SCALE, index * 30 * GUI_SCALE, 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            }
            if (loaded) {
                ModernGui.drawScaledStringCustomFont((categoryCounters.containsKey((Object)category) ? String.format("%.0f", categoryCounters.get((Object)category)) : "0") + "/" + (maxCounters.containsKey((Object)category) ? String.format("%.0f", maxCounters.get((Object)category)) : "?"), this.guiLeft + 37 + 45 * index, this.guiTop + 190, COLOR_LIGHT_GRAY, 0.5f, "center", false, "georamaSemiBold", 20);
            }
            ++index;
        }
        super.func_73863_a(mouseX, mouseY, par3);
        if (!this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }
}

