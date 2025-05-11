/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticCategoryDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticResetGroupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticSetActivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.HatExportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CosmeticCategoryGUI
extends CommonCosmeticGUI {
    public static String selectedSkinId;
    public int entityMoovingCallCount = 0;
    public static HashMap<String, LinkedHashMap<String, ArrayList<HashMap<String, String>>>> categoriesData;
    public static boolean loaded;
    public static LinkedHashMap<String, Integer> groupIDSlotByRarityX;
    public static LinkedHashMap<String, Integer> skinIDSlotByRarityX;
    private String selectedGroupId;
    public long ticks = 0L;
    public static HashMap<String, String> cachedSelectedSkin;
    private EntityOtherPlayerMP playerEntity = null;
    private GuiScrollBarGeneric scrollBarLeftNavCategories;
    private GuiScrollBarGeneric scrollBarGroupIds;
    public static List<String> activeEmotes;
    public static List<String> activeBadges;
    public String overridePreviewSkin;
    public static float targetPlayerRotation;
    public static float manualOffsetPlayerRotation;
    public static float lastPlayerRotation;
    public static long lastPlayerAnimation;
    public static long nextRandomAnimationTime;
    public static String nextRandomAnimation;
    public static List<String> randomAnimations;

    public CosmeticCategoryGUI(String categoryTarget, String playerTarget) {
        this.categoryTarget = categoryTarget;
        this.playerTarget = playerTarget;
        categoriesData.clear();
        selectedSkinId = null;
        loaded = false;
        manualOffsetPlayerRotation = 0.0f;
        nextRandomAnimationTime = System.currentTimeMillis() + (long)new Random().nextInt(20000) + 10000L;
        nextRandomAnimation = randomAnimations.get(new Random().nextInt(randomAnimations.size()));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryTarget, this.playerTarget)));
        this.playerEntity = null;
        this.scrollBarLeftNavCategories = new GuiScrollBarGeneric(0.0f, 0.0f, 0, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 0, 0);
        this.scrollBarGroupIds = new GuiScrollBarGeneric(this.guiLeft + 448, this.guiTop + 158, 57, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    private float getSlideCategories() {
        return CosmeticGUI.CATEGORIES_ORDER.size() > 4 ? (float)(-(CosmeticGUI.CATEGORIES_ORDER.size() - 4) * 39) * this.scrollBarLeftNavCategories.getSliderValue() : 0.0f;
    }

    private float getSlideGroupIds() {
        return categoriesData.get(this.categoryTarget).size() > 16 ? -((float)Math.ceil((float)(categoriesData.get(this.categoryTarget).size() - 16) / 8.0f)) * 32.0f * this.scrollBarGroupIds.getSliderValue() : 0.0f;
    }

    public void renderPlayer(int par0, int par1, int par2, float mouseX, float mouseY, EntityLivingBase par5EntityLivingBase) {
        float rotation;
        GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 10, 160, 210);
        switch (this.categoryTarget) {
            case "hats": 
            case "buddies": {
                targetPlayerRotation = -20.0f;
                break;
            }
            case "chestplates": 
            case "emotes": {
                targetPlayerRotation = 20.0f;
                break;
            }
            case "capes": {
                targetPlayerRotation = 135.0f;
                break;
            }
            default: {
                targetPlayerRotation = -20.0f;
            }
        }
        float f = rotation = lastPlayerRotation != -1.0f ? lastPlayerRotation : (targetPlayerRotation += manualOffsetPlayerRotation);
        if (rotation != targetPlayerRotation) {
            rotation = rotation > targetPlayerRotation ? Math.max(targetPlayerRotation, rotation - (float)(System.currentTimeMillis() - lastPlayerAnimation) * 0.3f) : Math.min(targetPlayerRotation, rotation + (float)(System.currentTimeMillis() - lastPlayerAnimation) * 0.3f);
        }
        lastPlayerAnimation = System.currentTimeMillis();
        lastPlayerRotation = rotation;
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par0, (float)par1, (float)50.0f);
        GL11.glScalef((float)(-par2), (float)par2, (float)par2);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f2 = par5EntityLivingBase.field_70761_aq;
        float f3 = par5EntityLivingBase.field_70177_z;
        float f4 = par5EntityLivingBase.field_70125_A;
        float f5 = par5EntityLivingBase.field_70758_at;
        float f6 = par5EntityLivingBase.field_70759_as;
        GL11.glRotatef((float)rotation, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.func_74519_b();
        GL11.glRotatef((float)(-rotation), (float)0.0f, (float)1.0f, (float)0.0f);
        par5EntityLivingBase.field_70761_aq = rotation;
        par5EntityLivingBase.field_70177_z = rotation;
        par5EntityLivingBase.field_70125_A = 0.0f;
        par5EntityLivingBase.field_70759_as = par5EntityLivingBase.field_70177_z;
        par5EntityLivingBase.field_70758_at = par5EntityLivingBase.field_70177_z;
        GL11.glTranslatef((float)0.0f, (float)par5EntityLivingBase.field_70129_M, (float)0.0f);
        RenderManager.field_78727_a.field_78735_i = 180.0f;
        RenderManager.field_78727_a.func_78719_a((Entity)par5EntityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        par5EntityLivingBase.field_70761_aq = f2;
        par5EntityLivingBase.field_70177_z = f3;
        par5EntityLivingBase.field_70125_A = f4;
        par5EntityLivingBase.field_70758_at = f5;
        par5EntityLivingBase.field_70759_as = f6;
        GL11.glPopMatrix();
        RenderHelper.func_74518_a();
        GL11.glDisable((int)32826);
        OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77476_b);
        GL11.glDisable((int)3553);
        OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77478_a);
        if (nextRandomAnimationTime < System.currentTimeMillis()) {
            nextRandomAnimationTime = System.currentTimeMillis() + (long)new Random().nextInt(20000) + 10000L;
            if (!this.categoryTarget.equals("capes")) {
                ClientEmotesHandler.playEmote(nextRandomAnimation, true);
            }
            nextRandomAnimation = randomAnimations.get(new Random().nextInt(randomAnimations.size()));
        }
        GUIUtils.endGLScissor();
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        boolean isHovered = mouseX >= (float)(this.guiLeft + 65) && mouseX <= (float)(this.guiLeft + 65 + 32) && mouseY >= (float)(this.guiTop + 151) && mouseY <= (float)(this.guiTop + 151 + 20);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 65, this.guiTop + 151, 534 * GUI_SCALE, (isHovered ? 209 : 184) * GUI_SCALE, 32 * GUI_SCALE, 23 * GUI_SCALE, 32, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        if (isHovered) {
            this.hoveredAction = "rotatePlus";
        }
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        isHovered = mouseX >= (float)(this.guiLeft + 151) && mouseX <= (float)(this.guiLeft + 151 + 32) && mouseY >= (float)(this.guiTop + 151) && mouseY <= (float)(this.guiTop + 151 + 20);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 151, this.guiTop + 151, 534 * GUI_SCALE, (isHovered ? 159 : 134) * GUI_SCALE, 32 * GUI_SCALE, 23 * GUI_SCALE, 32, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        if (isHovered) {
            this.hoveredAction = "rotateMinus";
        }
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        this.hoveredAction = "";
        this.tooltipToDraw.clear();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ++this.ticks;
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 321 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        GUIUtils.startGLScissor(this.guiLeft + 12, this.guiTop + 0, 30, 235);
        int index = 0;
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        for (String string : CosmeticGUI.CATEGORIES_ORDER) {
            int offsetX = this.guiLeft + 12;
            Float offsetY = Float.valueOf((float)(this.guiTop + 69 + index * 39) + this.getSlideCategories());
            ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue(), 991 * GUI_SCALE, (this.categoryTarget.equals(string) ? 7 : 45) * GUI_SCALE, 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            if (mouseX >= offsetX && mouseX <= offsetX + 30 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 30.0f) {
                ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue(), 951 * GUI_SCALE, index * 30 * GUI_SCALE, 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                this.hoveredAction = "tab#" + string;
            } else {
                ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue(), (this.categoryTarget.equals(string) ? 951 : 920) * GUI_SCALE, index * 30 * GUI_SCALE, 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            }
            ++index;
        }
        GUIUtils.endGLScissor();
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 245 * GUI_SCALE, 55 * GUI_SCALE, 66 * GUI_SCALE, 55, 66, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        if (mouseX >= this.guiLeft + 13 && (float)mouseX <= (float)(this.guiLeft + 21) + bold28.getStringWidth(I18n.func_135053_a((String)"cosmetic.label.return")) && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 9) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 30, 597 * GUI_SCALE, 222 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.return"), this.guiLeft + 21, this.guiTop + 31, COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaBold", 28);
            this.hoveredAction = "return";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 30, 597 * GUI_SCALE, 211 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.return"), this.guiLeft + 21, this.guiTop + 31, COLOR_LIGHT_BLUE, 0.5f, "left", false, "georamaBold", 28);
        }
        if (mouseX >= this.guiLeft && mouseX <= this.guiLeft + 55 && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize) {
            this.scrollBarLeftNavCategories.draw(mouseX, mouseY);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("cosmetic.category." + this.categoryTarget)), this.guiLeft + 12, this.guiTop + 11, 0xC4C4C4, 1.0f, "left", false, "georamaExtraBold", 28);
        if (!this.displayModal) {
            if (this.categoryTarget.equals("hats") || this.categoryTarget.equals("hands") || this.categoryTarget.equals("capes") || this.categoryTarget.equals("chestplates") || this.categoryTarget.equals("emotes") || this.categoryTarget.equals("buddies")) {
                this.renderPlayer(this.guiLeft + 125, this.guiTop + 185, 65, mouseX, mouseY, (EntityLivingBase)Minecraft.func_71410_x().field_71439_g);
            } else if (this.categoryTarget.equals("vehicles")) {
                if (selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 50, this.guiTop + 60, 6.0f, par3);
                }
            } else if (this.categoryTarget.equals("items")) {
                if (selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 50, this.guiTop + 60, 6.0f, par3);
                }
            } else if (this.categoryTarget.equals("armors") || this.categoryTarget.equals("items")) {
                if (selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 35, this.guiTop + 35, 7.0f, par3);
                }
            } else if (this.categoryTarget.equals("hands")) {
                if (selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                    ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 45, this.guiTop + 40, 6.0f, par3);
                }
            } else if (this.categoryTarget.equals("badges") && selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 75, this.guiTop + 75, 4.0f, par3);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        if (loaded) {
            if (this.selectedGroupId == null && categoriesData.containsKey(this.categoryTarget) && categoriesData.get(this.categoryTarget).keySet().size() > 0) {
                for (Map.Entry entry : categoriesData.get(this.categoryTarget).entrySet()) {
                    String groupID = (String)entry.getKey();
                    for (HashMap skinData : (ArrayList)entry.getValue()) {
                        if (!((String)skinData.get("active")).equals("1")) continue;
                        this.selectedGroupId = groupID;
                    }
                }
                if (this.selectedGroupId == null) {
                    this.selectedGroupId = (String)categoriesData.get(this.categoryTarget).keySet().toArray()[0];
                }
            }
            if (categoriesData.containsKey(this.categoryTarget) && categoriesData.get(this.categoryTarget).size() > 0 && this.selectedGroupId != null && categoriesData.get(this.categoryTarget).get(this.selectedGroupId) != null) {
                int offsetX;
                HashMap<String, String> hashMap;
                ArrayList<HashMap<String, String>> skinsOfSelectedGroupID = categoriesData.get(this.categoryTarget).get(this.selectedGroupId);
                HashMap<String, String> hashMap2 = hashMap = cachedSelectedSkin != null ? cachedSelectedSkin : skinsOfSelectedGroupID.get(0);
                if (hashMap.get("skin_name").equals(selectedSkinId)) {
                    if (hashMap.get("owned").equals("1")) {
                        if (!hashMap.get("by_default").equals("1")) {
                            ClientEventHandler.STYLE.bindTexture("cosmetic");
                            int exportBtnOffsetX = this.categoryTarget.equals("hats") && !hashMap.get("rarity").equals("limited") ? 79 : 94;
                            boolean mouseOver = mouseX >= this.guiLeft + exportBtnOffsetX && mouseX <= this.guiLeft + exportBtnOffsetX + 59 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 11;
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + exportBtnOffsetX, this.guiTop + 197, 283 * GUI_SCALE, (hashMap.get("active").equals("1") ? (mouseOver ? 680 : 695) : (mouseOver ? 680 : 710)) * GUI_SCALE, 59 * GUI_SCALE, 11 * GUI_SCALE, 59, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                            String label = I18n.func_135053_a((String)("cosmetic.label." + (hashMap.get("active").equals("1") ? "unequip" : "equip"))).toUpperCase();
                            ModernGui.drawScaledStringCustomFont(label, this.guiLeft + exportBtnOffsetX + 30 - 4, (float)this.guiTop + 200.0f, mouseOver || !hashMap.get("active").equals("1") ? COLOR_DARK_BLUE : COLOR_LIGHT_GRAY, 0.5f, "center", false, "georamaSemiBold", 24);
                            ClientEventHandler.STYLE.bindTexture("cosmetic");
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + exportBtnOffsetX + 30 + 1) + semiBold24.getStringWidth(label) * 0.5f / 2.0f, (float)this.guiTop + 199.0f, 595 * GUI_SCALE, (hashMap.get("active").equals("1") ? (mouseOver ? 278 : 269) : 287) * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                            if (mouseOver) {
                                this.hoveredAction = "skinID#" + hashMap.get("skin_name") + "#" + (hashMap.get("active").equals("1") ? "unequip" : "equip");
                            }
                            if (this.categoryTarget.equals("hats") && !hashMap.get("rarity").equals("limited")) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 142, this.guiTop + 197, 709 * GUI_SCALE, 174 * GUI_SCALE, 18 * GUI_SCALE, 11 * GUI_SCALE, 18, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                                if (mouseX >= this.guiLeft + 142 && mouseX <= this.guiLeft + 142 + 59 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 11) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 142, this.guiTop + 197, 729 * GUI_SCALE, 174 * GUI_SCALE, 18 * GUI_SCALE, 11 * GUI_SCALE, 18, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                                    this.hoveredAction = "exportHat";
                                }
                            }
                        } else if (hashMap.get("by_default").equals("1")) {
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.by_default").toUpperCase(), this.guiLeft + 124, this.guiTop + 199, COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                        }
                    } else if (hashMap.get("price").equals("0") || !hashMap.get("unlock_type").equals("null") && (!hashMap.get("unlock_type").equals("ngprime") || !((Boolean)CosmeticGUI.data.get("ngprime")).booleanValue()) || hashMap.get("availability_start") != null && Long.parseLong(hashMap.get("availability_start")) > System.currentTimeMillis() || hashMap.get("availability_end") != null && Long.parseLong(hashMap.get("availability_end")) < System.currentTimeMillis()) {
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.not_buyable").toUpperCase(), this.guiLeft + 124 - 5, this.guiTop + 199, COLOR_LIGHT_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a((String)"cosmetic.label.not_buyable").toUpperCase()) * 0.5f / 2.0f, (float)this.guiTop + 198.2f, 594 * GUI_SCALE, 236 * GUI_SCALE, 9 * GUI_SCALE, 8 * GUI_SCALE, 9, 8, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        if ((float)mouseX >= (float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a((String)"cosmetic.label.not_buyable").toUpperCase()) * 0.5f / 2.0f && (float)mouseX <= (float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a((String)"cosmetic.label.not_buyable").toUpperCase()) * 0.5f / 2.0f + 9.0f && (float)mouseY >= (float)this.guiTop + 198.2f && (float)mouseY <= (float)this.guiTop + 198.2f + 8.0f) {
                            this.tooltipToDraw.add(I18n.func_135053_a((String)("cosmetic.unlock_type." + (hashMap.get("unlock_type") != null && (!hashMap.get("unlock_type").equals("ngprime") || (Boolean)CosmeticGUI.data.get("ngprime") == false) ? hashMap.get("unlock_type") : "date"))));
                        }
                    } else if (!hashMap.get("price").equals("0")) {
                        int price = Integer.parseInt(hashMap.get("price"));
                        boolean canPlayerBuy = CosmeticGUI.data.get("player_points") != null && (Double)CosmeticGUI.data.get("player_points") >= (double)price;
                        boolean isMouseOver = mouseX >= this.guiLeft + 121 && mouseX <= this.guiLeft + 121 + 46 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 12;
                        ModernGui.drawScaledStringCustomFont(hashMap.get("price"), this.guiLeft + 104, this.guiTop + 199, canPlayerBuy ? COLOR_WHITE : COLOR_LIGHT_BLUE, 0.75f, "right", false, "georamaSemiBold", 24);
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 107, this.guiTop + 199, 594 * GUI_SCALE, (canPlayerBuy ? 179 : 199) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 121, this.guiTop + 197, 483 * GUI_SCALE, (canPlayerBuy && !isMouseOver ? 134 : 117) * GUI_SCALE, 46 * GUI_SCALE, 12 * GUI_SCALE, 46, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"cosmetic.label.get").toUpperCase(), this.guiLeft + 144, this.guiTop + 200, canPlayerBuy && !isMouseOver ? COLOR_WHITE : COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 24);
                        if (canPlayerBuy && isMouseOver) {
                            this.itemToBuyHover.putAll(hashMap);
                            this.hoveredAction = "open_modal";
                        } else if (isMouseOver) {
                            this.tooltipToDraw.add(I18n.func_135053_a((String)"cosmetic.label.not_enough_points"));
                        }
                    }
                }
                String skinTitle = hashMap.containsKey("name_" + System.getProperty("java.lang")) ? hashMap.get("name_" + System.getProperty("java.lang")).toUpperCase() : hashMap.get("skin_name").toUpperCase();
                ModernGui.drawScaledStringCustomFont(skinTitle, this.guiLeft + 206, this.guiTop + 43, CosmeticGUI.COLOR_LIGHT_GRAY, 0.75f, "left", false, "georamaSemiBold", 30);
                int offsetXLabels = 0;
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                String rarityToUse = hashMap.get("rarity");
                if (cachedSelectedSkin != null) {
                    rarityToUse = cachedSelectedSkin.get("rarity");
                }
                ModernGui.glColorHex((Integer)CosmeticGUI.rarityColors.get(rarityToUse), 1.0f);
                ModernGui.drawRoundedRectangle(this.guiLeft + 206 + 5 + (int)((double)CosmeticGUI.semiBold30.getStringWidth(skinTitle) * 0.75), this.guiTop + 43, 0.0f, (rarityToUse.equals("limited") && hashMap.get("owned").equals("1") ? 70 : 35) + (hashMap.get("unlock_type").equals("ngprime") ? 5 : 0), 9.0f);
                offsetXLabels += (int)((double)CosmeticGUI.semiBold30.getStringWidth(skinTitle) * 0.75) + (rarityToUse.equals("limited") && hashMap.get("owned").equals("1") ? 70 : 35) + 3;
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                String label = I18n.func_135053_a((String)("cosmetic.rarity." + rarityToUse));
                if (rarityToUse.equals("limited") && hashMap.get("owned").equals("1")) {
                    label = !hashMap.get("limited_copies").equals("0") ? I18n.func_135053_a((String)"cosmetic.rarity.limited") + " N\u00b0" + hashMap.get("limited_copy_number") + "/" + hashMap.get("limited_copies") : I18n.func_135053_a((String)"cosmetic.rarity.limited") + " N\u00b0" + hashMap.get("limited_copy_number");
                }
                if (hashMap.get("unlock_type").equals("ngprime")) {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206 + 5 + (rarityToUse.equals("limited") && hashMap.get("owned").equals("1") ? 35 : 17) + (int)((double)CosmeticGUI.semiBold30.getStringWidth(skinTitle) * 0.75)) - CosmeticGUI.semiBold27.getStringWidth(label) / 2.0f / 2.0f - 2.0f, this.guiTop + 45, 655 * GUI_SCALE, 187 * GUI_SCALE, 7 * GUI_SCALE, 5 * GUI_SCALE, 7, 5, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(label, this.guiLeft + 206 + 6 + (rarityToUse.equals("limited") && hashMap.get("owned").equals("1") ? 35 : 17) + (int)((double)CosmeticGUI.semiBold30.getStringWidth(skinTitle) * 0.75) + (hashMap.get("unlock_type").equals("ngprime") ? 7 : 0), (float)this.guiTop + 44.0f, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                if (hashMap.containsKey("is_new") && hashMap.get("is_new").equals("1")) {
                    ModernGui.glColorHex(-1, 1.0f);
                    ModernGui.drawRoundedRectangle(this.guiLeft + 206 + 5 + offsetXLabels, this.guiTop + 43, 0.0f, 26.0f, 9.0f);
                    ModernGui.drawScaledStringCustomFont("NEW !", this.guiLeft + 206 + 5 + offsetXLabels + 13, this.guiTop + 44, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
                    offsetXLabels += 29;
                }
                if (hashMap.containsKey("only_servers") && hashMap.get("only_servers") != null) {
                    for (String server : hashMap.get("only_servers").split(",")) {
                        if (!CommonCosmeticGUI.SERVERS_COLOR.containsKey(server)) continue;
                        ModernGui.glColorHex(CommonCosmeticGUI.SERVERS_COLOR.get(server), 1.0f);
                        ModernGui.drawRoundedRectangle(this.guiLeft + 206 + 5 + offsetXLabels, this.guiTop + 43, 0.0f, 40.0f, 9.0f);
                        ModernGui.glColorHex(-14277557, 1.0f);
                        ModernGui.drawRoundedRectangle((float)(this.guiLeft + 206) + 5.6f + (float)offsetXLabels, (float)this.guiTop + 43.5f, 0.0f, 38.9f, 7.9f);
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206) + 7.5f + (float)offsetXLabels, this.guiTop + 44, 685 * GUI_SCALE, (156 + CommonCosmeticGUI.SERVERS_ORDER.indexOf(server) * 8) * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(server.toUpperCase(), this.guiLeft + 206 + 5 + offsetXLabels + 24, (float)this.guiTop + 44.4f, CommonCosmeticGUI.SERVERS_COLOR.get(server), 0.5f, "center", false, "georamaSemiBold", 27);
                        offsetXLabels += 43;
                    }
                }
                if (hashMap.get("event_icon") != null && !hashMap.get("event_icon").isEmpty() && eventIcons.containsKey(hashMap.get("event_icon"))) {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    boolean isMouseHover = mouseX >= this.guiLeft + 206 + 5 + offsetXLabels && mouseX <= this.guiLeft + 206 + 5 + offsetXLabels + 14 && (float)mouseY >= (float)this.guiTop + 40.5f && (float)mouseY <= (float)this.guiTop + 40.5f + 14.0f;
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 206 + 5 + offsetXLabels, (float)this.guiTop + 40.3f, (isMouseHover ? 631 : 617) * GUI_SCALE, (Integer)eventIcons.get(hashMap.get("event_icon")) * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    if (isMouseHover) {
                        this.tooltipToDraw.add(I18n.func_135053_a((String)("cosmetic.event." + hashMap.get("event_icon"))));
                    }
                }
                ModernGui.drawSectionStringCustomFont(hashMap.containsKey("description_" + System.getProperty("java.lang")) ? hashMap.get("description_" + System.getProperty("java.lang")).replaceAll("<player>", Minecraft.func_71410_x().field_71439_g.getDisplayName()) : "no description set", this.guiLeft + 206, this.guiTop + 60, CosmeticGUI.COLOR_LIGHT_GRAY, 0.5f, "left", false, "georamaMedium", 25, 9, 450);
                if (!(this.categoryTarget.equals("capes") || this.categoryTarget.equals("emotes") || this.categoryTarget.equals("badges") || this.categoryTarget.equals("buddies"))) {
                    index = 0;
                    for (HashMap<String, String> skinData : skinsOfSelectedGroupID) {
                        int offsetX2 = this.guiLeft + 207 + index % 8 * 30;
                        int offsetY = this.guiTop + 90 + index / 8 * 29;
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        if (selectedSkinId == null && skinData.get("active").equals("1")) {
                            selectedSkinId = skinData.get("skin_name");
                        }
                        if (selectedSkinId != null && selectedSkinId.equals(skinData.get("skin_name")) && (cachedSelectedSkin == null || !cachedSelectedSkin.get("skin_name").equals(skinData.get("skin_name")))) {
                            cachedSelectedSkin = skinData;
                            if (cachedSelectedSkin.get("preview_sound") != null) {
                                ClientProxy.playClientMusic(cachedSelectedSkin.get("preview_sound"), 1.5f);
                            } else {
                                ClientProxy.stopClientMusic();
                            }
                        }
                        if (!this.isBehindModal(offsetX2, offsetY)) {
                            if (skinData.get("owned").equals("1")) {
                                ModernGui.drawScaledCustomSizeModalRect(offsetX2, offsetY, skinIDSlotByRarityX.get(skinData.get("rarity")) * GUI_SCALE, 322 * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                                if (ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")) != null) {
                                    ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")).renderInGUI(offsetX2, offsetY, 1.0f, par3);
                                }
                                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                                if (mouseX >= offsetX2 && mouseX <= offsetX2 + 25 && mouseY >= offsetY && mouseY <= offsetY + 25) {
                                    this.hoveredAction = "skinID#" + skinData.get("skin_name") + "#select";
                                }
                            } else {
                                ModernGui.drawScaledCustomSizeModalRect(offsetX2, offsetY, 483 * GUI_SCALE, 322 * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                                if (ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")) != null) {
                                    ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")).renderInGUI(offsetX2, offsetY, 1.0f, par3);
                                }
                                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                                ClientEventHandler.STYLE.bindTexture("cosmetic");
                                GL11.glPushMatrix();
                                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)500.0f);
                                ModernGui.drawScaledCustomSizeModalRect(offsetX2, offsetY, 513 * GUI_SCALE, 322 * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                                GL11.glPopMatrix();
                                if (mouseX >= offsetX2 && mouseX <= offsetX2 + 25 && mouseY >= offsetY && mouseY <= offsetY + 25) {
                                    this.hoveredAction = "skinID#" + skinData.get("skin_name") + "#select";
                                }
                            }
                        }
                        if (selectedSkinId != null && selectedSkinId.equals(skinData.get("skin_name"))) {
                            ClientEventHandler.STYLE.bindTexture("cosmetic");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX2, offsetY, 543 * GUI_SCALE, 322 * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                        }
                        ++index;
                    }
                } else if (this.categoryTarget.equals("emotes")) {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 206, this.guiTop + 85, 219 * GUI_SCALE, 257 * GUI_SCALE, 244 * GUI_SCALE, 43 * GUI_SCALE, 244, 43, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    for (int i = 0; i < 6; ++i) {
                        offsetX = this.guiLeft + 214 + i * 24;
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX, this.guiTop + 90, 483 * GUI_SCALE, 421 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                        if (activeEmotes.size() > i) {
                            if (ClientProxy.SKIN_MANAGER.getSkinFromID(activeEmotes.get(i)) != null) {
                                ClientProxy.SKIN_MANAGER.getSkinFromID(activeEmotes.get(i)).renderInGUI(offsetX - 3, this.guiTop + 89, 1.0f, par3);
                            }
                            if (mouseX < offsetX + 2 || mouseX > offsetX + 18 || mouseY < this.guiTop + 90 + 2 || mouseY > this.guiTop + 90 + 18) continue;
                            this.hoveredAction = "groupID#" + activeEmotes.get(i);
                            continue;
                        }
                        ModernGui.drawScaledStringCustomFont(i + 1 + "", (float)offsetX + 10.5f, (float)this.guiTop + 96.5f, CosmeticGUI.COLOR_DARK_BLUE, 0.75f, "center", false, "georamaBold", 26);
                    }
                } else if (this.categoryTarget.equals("badges")) {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 206, this.guiTop + 85, 219 * GUI_SCALE, 257 * GUI_SCALE, 244 * GUI_SCALE, 43 * GUI_SCALE, 244, 43, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    for (int i = 0; i < 2; ++i) {
                        offsetX = this.guiLeft + 214 + i * 24;
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX, this.guiTop + 90, 483 * GUI_SCALE, 421 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                        if (activeBadges.size() > i) {
                            if (ClientProxy.SKIN_MANAGER.getSkinFromID(activeBadges.get(i)) != null) {
                                ClientProxy.SKIN_MANAGER.getSkinFromID(activeBadges.get(i)).renderInGUI(offsetX + 3, this.guiTop + 93, 0.6f, par3);
                            }
                            if (mouseX < offsetX + 2 || mouseX > offsetX + 18 || mouseY < this.guiTop + 90 + 2 || mouseY > this.guiTop + 90 + 18) continue;
                            this.hoveredAction = "groupID#" + activeBadges.get(i);
                            continue;
                        }
                        ModernGui.drawScaledStringCustomFont(i + 1 + "", (float)offsetX + 10.5f, this.guiTop + 97, CosmeticGUI.COLOR_DARK_BLUE, 0.75f, "center", false, "georamaBold", 26);
                    }
                }
                if (selectedSkinId == null) {
                    cachedSelectedSkin = categoriesData.get(this.categoryTarget).get(this.selectedGroupId).get(0);
                    selectedSkinId = cachedSelectedSkin.get("skin_name");
                    if (cachedSelectedSkin.get("preview_sound") != null) {
                        ClientProxy.playClientMusic(cachedSelectedSkin.get("preview_sound"), 1.5f);
                    } else {
                        ClientProxy.stopClientMusic();
                    }
                }
                index = 0;
                int groupIdsOffsetY = 158;
                if (this.categoryTarget.equals("hats") || this.categoryTarget.equals("chestplates") || this.categoryTarget.equals("emotes") || this.categoryTarget.equals("badges")) {
                    groupIdsOffsetY = 126;
                } else if (this.categoryTarget.equals("capes") || this.categoryTarget.equals("buddies")) {
                    groupIdsOffsetY = 94;
                }
                GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + groupIdsOffsetY - 2, 235, 59 + (158 - groupIdsOffsetY));
                for (Map.Entry<String, ArrayList<HashMap<String, String>>> entry : categoriesData.get(this.categoryTarget).entrySet()) {
                    String groupID = entry.getKey();
                    HashMap<String, String> skinData = entry.getValue().get(0);
                    int offsetX3 = this.guiLeft + 206 + index % 8 * 30;
                    Float offsetY = Float.valueOf((float)(this.guiTop + groupIdsOffsetY + index / 8 * 32) + this.getSlideGroupIds());
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    boolean isGroupIdActive = false;
                    isGroupIdActive = this.categoryTarget.equals("emotes") ? activeEmotes.contains(groupID) : (this.categoryTarget.equals("badges") ? activeBadges.contains(groupID) : this.selectedGroupId.equals(groupID));
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glEnable((int)3042);
                    ModernGui.drawScaledCustomSizeModalRect(offsetX3, offsetY.intValue(), groupIDSlotByRarityX.get(skinData.get("rarity")) * GUI_SCALE, (isGroupIdActive ? 385 : 356) * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                    if (!this.isBehindModal(offsetX3, offsetY.intValue())) {
                        if (ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")) != null) {
                            ClientProxy.SKIN_MANAGER.getSkinFromID(skinData.get("skin_name")).renderInGUI(offsetX3, offsetY.intValue(), 1.0f, par3);
                        }
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    }
                    if (skinData.get("owned").equals("0")) {
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)200.0f);
                        ModernGui.drawScaledCustomSizeModalRect(offsetX3, offsetY.intValue(), 513 * GUI_SCALE, 356 * GUI_SCALE, 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                        GL11.glPopMatrix();
                    }
                    if (skinData.containsKey("is_new") && skinData.get("is_new").equals("1")) {
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)200.0f);
                        ModernGui.drawScaledCustomSizeModalRect(offsetX3 + 15, offsetY.intValue() - 1, 709 * GUI_SCALE, 189 * GUI_SCALE, 12 * GUI_SCALE, 4 * GUI_SCALE, 12, 4, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                        GL11.glPopMatrix();
                    }
                    if (mouseY >= this.guiTop + groupIdsOffsetY && mouseY <= this.guiTop + groupIdsOffsetY + 57 + (158 - groupIdsOffsetY) && mouseX >= offsetX3 && mouseX <= offsetX3 + 25 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 25.0f) {
                        this.hoveredAction = "groupID#" + groupID;
                    }
                    ++index;
                }
                GUIUtils.endGLScissor();
                this.scrollBarGroupIds.draw(mouseX, mouseY);
            }
            this.overridePreviewSkin = selectedSkinId;
        }
        super.func_73863_a(mouseX, mouseY, par3);
        if (!this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public void func_73874_b() {
        ClientProxy.stopClientMusic();
        super.func_73874_b();
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (this.displayModal) {
            super.func_73864_a(mouseX, mouseY, mouseButton);
        }
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("return")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CosmeticGUI(this.playerTarget));
            } else if (this.hoveredAction.contains("tab#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.categoryTarget = this.hoveredAction.replaceAll("tab#", "");
                if (!categoriesData.containsKey(this.categoryTarget)) {
                    loaded = false;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryTarget, this.playerTarget)));
                }
                this.selectedGroupId = null;
                selectedSkinId = null;
                manualOffsetPlayerRotation = 0.0f;
            } else if (this.hoveredAction.contains("groupID#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedGroupId = this.hoveredAction.replaceAll("groupID#", "");
                selectedSkinId = categoriesData.get(this.categoryTarget).get(this.selectedGroupId).get(0).get("skin_name");
                cachedSelectedSkin = null;
                if (this.categoryTarget.equals("emotes")) {
                    ClientEmotesHandler.playEmote(selectedSkinId.replaceAll("emotes_", ""), true);
                }
            } else if (this.hoveredAction.contains("skinID#")) {
                if (this.hoveredAction.split("#")[2].equals("select")) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    selectedSkinId = this.hoveredAction.split("#")[1];
                    cachedSelectedSkin = null;
                } else if (this.hoveredAction.split("#")[2].equals("equip")) {
                    ClientProxy.playClientMusic("https://static.nationsglory.fr/N342363533.mp3", 1.0f);
                    boolean activeEmotesChanged = false;
                    boolean activeBadgesChanged = false;
                    if (this.categoryTarget.equals("emotes")) {
                        if (activeEmotes.contains(this.selectedGroupId)) {
                            activeEmotes.remove(this.selectedGroupId);
                            activeEmotesChanged = true;
                        } else if (activeEmotes.size() < 6 && categoriesData.get(this.categoryTarget).get(this.selectedGroupId).get(0).get("owned").equals("1")) {
                            activeEmotes.add(this.selectedGroupId);
                            activeEmotesChanged = true;
                        }
                        if (activeEmotesChanged) {
                            String emotes = "";
                            for (String emote : activeEmotes) {
                                emotes = emotes + emote + "#";
                            }
                            emotes = emotes.replaceAll("#$", "");
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, emotes, this.categoryTarget)));
                        }
                    } else if (this.categoryTarget.equals("badges")) {
                        if (activeBadges.contains(this.selectedGroupId)) {
                            activeBadges.remove(this.selectedGroupId);
                            activeBadgesChanged = true;
                        } else if (activeBadges.size() < 2 && categoriesData.get(this.categoryTarget).get(this.selectedGroupId).get(0).get("owned").equals("1")) {
                            activeBadges.add(this.selectedGroupId);
                            activeBadgesChanged = true;
                        }
                        if (activeBadgesChanged) {
                            String badges = "";
                            for (String badge : activeBadges) {
                                badges = badges + badge + "#";
                            }
                            badges = badges.replaceAll("#$", "");
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, badges, this.categoryTarget)));
                        }
                    } else {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, this.hoveredAction.split("#")[1], this.categoryTarget)));
                    }
                } else if (this.hoveredAction.split("#")[2].equals("unequip")) {
                    ClientProxy.playClientMusic("https://static.nationsglory.fr/N342364y3y.mp3", 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticResetGroupPacket(this.playerTarget, this.selectedGroupId, this.categoryTarget)));
                }
            } else if (this.hoveredAction.equals("rotatePlus")) {
                manualOffsetPlayerRotation += 45.0f;
            } else if (this.hoveredAction.equals("rotateMinus")) {
                manualOffsetPlayerRotation -= 45.0f;
            } else if (this.hoveredAction.equals("exportHat")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new HatExportPacket(selectedSkinId)));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    static {
        categoriesData = new HashMap();
        loaded = false;
        groupIDSlotByRarityX = new LinkedHashMap<String, Integer>(){
            {
                this.put("common", 543);
                this.put("rare", 573);
                this.put("epic", 603);
                this.put("limited", 633);
            }
        };
        skinIDSlotByRarityX = new LinkedHashMap<String, Integer>(){
            {
                this.put("common", 573);
                this.put("rare", 603);
                this.put("epic", 633);
                this.put("limited", 663);
            }
        };
        activeEmotes = new ArrayList<String>();
        activeBadges = new ArrayList<String>();
        targetPlayerRotation = -1.0f;
        manualOffsetPlayerRotation = 0.0f;
        lastPlayerRotation = -1.0f;
        lastPlayerAnimation = -1L;
        nextRandomAnimationTime = -1L;
        nextRandomAnimation = null;
        randomAnimations = Arrays.asList("cheer", "clap", "facepalm", "stand", "wave");
    }
}

