/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.wiki;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class WikiGUI
extends GuiScreen {
    public static final ResourceLocation SCROLLBAR_CURSOR = new ResourceLocation("nationsgui", "textures/gui/multi/scrollbar_cursor.png");
    public static String selectedMainCategory = "";
    public static String selectedCategory = "";
    public static String defaultCategory = "";
    public static String hoveredCategory = "";
    public static String hoveredMainCategory = "";
    public static String hoveredAction = "";
    public static int selectedMainCategoryFullHeight = 0;
    private RenderItem itemRenderer = new RenderItem();
    private GuiTextField inputSearch;
    private GuiScrollBarGeneric scrollBar;
    public static HashMap<String, Integer> categoryScrollOffset = new HashMap();
    private GuiScreen guiFrom;

    public WikiGUI(GuiScreen guiFrom) {
        this.guiFrom = guiFrom;
        selectedMainCategoryFullHeight = 0;
    }

    public void func_73876_c() {
        super.func_73876_c();
        this.inputSearch.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.inputSearch.func_73802_a(typedChar, keyCode);
        if (keyCode == 1) {
            this.field_73882_e.func_71373_a(this.guiFrom);
            if (this.guiFrom == null) {
                this.field_73882_e.func_71381_h();
            }
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBar = new GuiScrollBarGeneric(3780.0f, 440.0f, 1500, SCROLLBAR_CURSOR, 24, 179);
        int windowHeight = this.field_73880_f * 9 / 16;
        this.inputSearch = new GuiTextField(this.field_73886_k, (int)(1650.0f / (3840.0f / (float)this.field_73880_f)), (int)(880.0f / (2160.0f / (float)windowHeight)), (int)(530.0f / (3840.0f / (float)this.field_73880_f)), 20);
        this.inputSearch.func_73786_a(false);
        this.inputSearch.func_73804_f(40);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        ArrayList toolTipLines = new ArrayList();
        hoveredCategory = "";
        hoveredMainCategory = "";
        hoveredAction = "";
        GL11.glDisable((int)2884);
        GL11.glPushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int windowWidth = this.field_73880_f;
        int windowHeight = this.field_73880_f * 9 / 16;
        int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = screenWidth * 9 / 16;
        int mouseXScaled = (int)((float)mouseX * (3840.0f / (float)this.field_73880_f));
        int mouseYScaled = (int)((float)mouseY * (2160.0f / (float)windowHeight));
        Gui.func_73734_a((int)0, (int)0, (int)((int)(950.0f * ((float)this.field_73880_f / 3840.0f))), (int)this.field_73881_g, (int)-13749417);
        Gui.func_73734_a((int)((int)(950.0f * ((float)this.field_73880_f / 3840.0f))), (int)0, (int)this.field_73880_f, (int)this.field_73881_g, (int)-15197637);
        GL11.glScaled((double)((float)this.field_73880_f / 3840.0f), (double)((float)windowHeight / 2160.0f), (double)1.0);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"wiki.title"), 82.0f, 50.0f, 0xEEEFFC, 4.0f, "left", false, "georamaSemiBold", 50);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"wiki.description"), 82.0f, 185.0f, 0xEEEFFC, 4.0f, "left", false, "georamaRegular", 18, 40, 200);
        ClientProxy.loadResource("textures/gui/wiki/search.png");
        ModernGui.drawModalRectWithCustomSizedTexture(80.0f, 300.0f, 0, 0, 794, 92, 794.0f, 92.0f, true);
        Gui.func_73734_a((int)56, (int)450, (int)894, (int)452, (int)-12170642);
        int initialCategoriesOffset = 520;
        int offsetCategories = 0;
        for (HashMap mainCategory : (ArrayList)ClientProxy.wiki.clone()) {
            if (mouseXScaled >= 200 && mouseXScaled <= 800 && mouseYScaled >= initialCategoriesOffset + offsetCategories && mouseYScaled <= initialCategoriesOffset + offsetCategories + 60) {
                hoveredMainCategory = (String)mainCategory.get("name");
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientProxy.loadResource("textures/gui/wiki/icons/" + mainCategory.get("name") + (selectedMainCategory.equals(mainCategory.get("name")) ? "_purple" : (hoveredMainCategory.equals(mainCategory.get("name")) ? "_white" : "_gray")) + ".png");
            ModernGui.drawModalRectWithCustomSizedTexture(120.0f, initialCategoriesOffset + offsetCategories, 0, 0, 60, 63, 60.0f, 63.0f, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("wiki.text." + mainCategory.get("name") + ".title")), 200.0f, initialCategoriesOffset + offsetCategories, selectedMainCategory.equals(mainCategory.get("name")) ? 0x6E76EE : (hoveredMainCategory.equals(mainCategory.get("name")) ? 0xFFFFFF : 0xC4C4C4), 4.0f, "left", false, "georamaSemiBold", 30);
            if (mainCategory.containsKey("children") && (selectedMainCategory.isEmpty() || selectedMainCategory.equals(mainCategory.get("name")))) {
                offsetCategories += 10;
                for (HashMap childCategory : (ArrayList)((ArrayList)mainCategory.get("children")).clone()) {
                    if (selectedCategory.isEmpty()) {
                        selectedCategory = (String)childCategory.get("name");
                        defaultCategory = (String)childCategory.get("name");
                        selectedMainCategory = (String)mainCategory.get("name");
                    }
                    if (mouseXScaled >= 230 && mouseXScaled <= 800 && mouseYScaled >= initialCategoriesOffset + 10 + (offsetCategories += 70) && mouseYScaled <= initialCategoriesOffset + 10 + offsetCategories + 40) {
                        hoveredCategory = (String)childCategory.get("name");
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("wiki.text." + childCategory.get("name") + ".title")), 230.0f, initialCategoriesOffset + 10 + offsetCategories, selectedCategory.equals((String)childCategory.get("name")) ? 0xFFFFFF : (hoveredCategory.equals((String)childCategory.get("name")) ? 0x6E76EE : 0xC4C4C4), 3.0f, "left", false, selectedCategory.equals((String)childCategory.get("name")) ? "georamaBold" : "georamaRegular", 30);
                }
                Gui.func_73734_a((int)108, (int)(initialCategoriesOffset + offsetCategories + 70 + 30), (int)848, (int)(initialCategoriesOffset + offsetCategories + 70 + 30 + 2), (int)-15197637);
                offsetCategories += 140;
                continue;
            }
            offsetCategories += 100;
        }
        if (!selectedMainCategory.isEmpty()) {
            int offsetContent = 0;
            float currentSliderValue = this.scrollBar.getSliderValue() * (float)selectedMainCategoryFullHeight;
            for (HashMap mainCategory : (ArrayList)ClientProxy.wiki.clone()) {
                if (!((String)mainCategory.get("name")).equals(selectedMainCategory) || !mainCategory.containsKey("children")) continue;
                String tempSelectedCategory = "";
                GUIUtils.startGLScissor((int)(950.0f * ((float)this.field_73880_f / 3840.0f)), (int)(0.0f * ((float)this.field_73881_g / 2160.0f)), (int)(2890.0f * ((float)this.field_73880_f / 3840.0f)), (int)(2160.0f * ((float)this.field_73881_g - 0.0f * ((float)windowHeight / 2160.0f) / 2160.0f)));
                Float offsetY = Float.valueOf((float)(0 + offsetContent) + this.getSlide());
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientProxy.loadResource("textures/gui/wiki/images/" + mainCategory.get("name") + "/top.png");
                ModernGui.drawModalRectWithCustomSizedTexture(950.0f, offsetY.floatValue(), 0, 0, 2890, 396, 2890.0f, 396.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("wiki.text." + mainCategory.get("name") + ".title")).toUpperCase(), 1110.0f, offsetY.intValue() + 110, 0xFFFFFF, 4.0f, "left", false, "georamaSemiBold", 35);
                if (mainCategory.containsKey("tags")) {
                    int tagIndex = 0;
                    for (String tag : (List)mainCategory.get("tags")) {
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        ClientProxy.loadResource("textures/gui/wiki/tag.png");
                        ModernGui.drawModalRectWithCustomSizedTexture(1110 + tagIndex * 205, offsetY.intValue() + 190, 0, 0, 186, 44, 186.0f, 44.0f, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("wiki.tag." + tag)).toUpperCase(), 1110 + tagIndex * 205 + 93, offsetY.intValue() + 198, 0xFFFFFF, 2.0f, "center", false, "georamaMedium", 30);
                        ++tagIndex;
                    }
                }
                ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("wiki.text." + mainCategory.get("name") + ".description")), 1110.0f, offsetY.intValue() + 260, 0xC4C4C4, 2.0f, "left", false, "georamaMedium", 33, 20, 600);
                offsetContent += 396;
                for (HashMap childCategory : (ArrayList)((ArrayList)mainCategory.get("children")).clone()) {
                    offsetY = Float.valueOf((float)(0 + offsetContent) + this.getSlide());
                    if (!categoryScrollOffset.containsKey(mainCategory.get("name") + "#" + childCategory.get("name"))) {
                        categoryScrollOffset.put(mainCategory.get("name") + "#" + childCategory.get("name"), offsetContent);
                    }
                    if (tempSelectedCategory.isEmpty() && offsetY.floatValue() > (float)(-((Long)childCategory.get("height") / 2L)) && offsetY.floatValue() < 1800.0f || (long)selectedMainCategoryFullHeight == (long)offsetContent + (Long)childCategory.get("height") && this.scrollBar.getSliderValue() == 1.0f) {
                        tempSelectedCategory = (String)childCategory.get("name");
                    }
                    Gui.func_73734_a((int)950, (int)offsetY.intValue(), (int)3840, (int)(offsetY.intValue() + ((Long)childCategory.get("height")).intValue()), (int)(childCategory.containsKey("color") ? (int)Long.parseLong(((String)childCategory.get("color")).replace("0x", ""), 16) : -15197637));
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (childCategory.containsKey("texts")) {
                        for (Object textObj : (JSONArray)childCategory.get("texts")) {
                            String textTrad;
                            if (!(textObj instanceof JSONObject)) continue;
                            JSONObject text = (JSONObject)textObj;
                            if (((String)text.get("name")).equals("title")) {
                                textTrad = I18n.func_135053_a((String)("wiki.text." + childCategory.get("name") + ".title"));
                                if (textTrad.equals("wiki.text." + childCategory.get("name") + ".title")) {
                                    textTrad = (String)childCategory.get("name");
                                }
                                ModernGui.drawScaledStringCustomFont(textTrad, 950 + ((Long)text.get("posX")).intValue(), offsetY.intValue() + ((Long)text.get("posY")).intValue(), 0xFFFFFF, 4.0f, (String)text.get("align"), false, text.containsKey("font") ? (String)text.get("font") : "georamaSemiBold", 32);
                                continue;
                            }
                            textTrad = I18n.func_135053_a((String)("wiki.text." + childCategory.get("name") + "." + text.get("name")));
                            if (textTrad.equals("wiki.text." + childCategory.get("name") + "." + text.get("name"))) {
                                textTrad = (String)text.get("name");
                            }
                            ModernGui.drawSectionStringCustomFont(textTrad, 950 + ((Long)text.get("posX")).intValue(), offsetY.intValue() + ((Long)text.get("posY")).intValue(), text.containsKey("color") ? (int)Long.parseLong(((String)text.get("color")).replace("0x", ""), 16) : -3881788, text.containsKey("scale") ? ((Double)text.get("scale")).floatValue() : 3.0f, (String)text.get("align"), false, text.containsKey("font") ? (String)text.get("font") : "georamaRegular", text.containsKey("size") ? ((Long)text.get("size")).intValue() : 30, 60, text.containsKey("width") ? ((Long)text.get("width")).intValue() : 500);
                        }
                    }
                    if (childCategory.containsKey("images")) {
                        for (Object imgObj : (JSONArray)childCategory.get("images")) {
                            if (!(imgObj instanceof JSONObject)) continue;
                            JSONObject img = (JSONObject)imgObj;
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            if (img.containsKey("name")) {
                                ClientProxy.loadResource("textures/gui/wiki/images/" + mainCategory.get("name") + "/" + img.get("name") + ".png");
                            } else if (img.containsKey("url")) {
                                ModernGui.bindRemoteTexture((String)img.get("url"));
                            }
                            ModernGui.drawModalRectWithCustomSizedTexture(950 + ((Long)img.get("posX")).intValue(), offsetY.intValue() + ((Long)img.get("posY")).intValue(), 0, 0, ((Long)img.get("width")).intValue(), ((Long)img.get("height")).intValue(), ((Long)img.get("width")).intValue(), ((Long)img.get("height")).intValue(), false);
                        }
                    }
                    offsetContent = (int)((long)offsetContent + (Long)childCategory.get("height"));
                }
                String string = selectedCategory = !tempSelectedCategory.isEmpty() ? tempSelectedCategory : defaultCategory;
                if (selectedMainCategoryFullHeight == 0) {
                    selectedMainCategoryFullHeight = offsetContent;
                }
                GUIUtils.endGLScissor();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientProxy.loadResource("textures/gui/wiki/scrollbar.png");
                ModernGui.drawModalRectWithCustomSizedTexture(3780.0f, 440.0f, 0, 0, 24, 1500, 24.0f, 1500.0f, false);
                this.scrollBar.draw(mouseXScaled, mouseYScaled);
            }
        }
        if (mouseXScaled >= 3770 && mouseXScaled <= 3802 && mouseYScaled >= 50 && mouseYScaled <= 82) {
            hoveredAction = "close";
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientProxy.loadResource("textures/gui/generic/cross_" + (hoveredAction.equals("close") ? "white" : "gray") + ".png");
        ModernGui.drawModalRectWithCustomSizedTexture(3770.0f, 50.0f, 0, 0, 32, 32, 32.0f, 32.0f, true);
        if (!toolTipLines.isEmpty()) {
            this.drawHoveringText(toolTipLines, mouseXScaled, mouseYScaled, this.field_73886_k);
        }
        GL11.glPopMatrix();
    }

    public void openURL(String url) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private float getSlide() {
        float test = (float)this.field_73881_g * (2160.0f / (float)this.field_73881_g);
        return (float)selectedMainCategoryFullHeight > test ? -((float)(selectedMainCategoryFullHeight + 100) - test) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public void scrollToCategory(String category) {
        if (categoryScrollOffset.containsKey(selectedMainCategory + "#" + category)) {
            int scrollOffset = categoryScrollOffset.get(selectedMainCategory + "#" + category);
            int totalScrollHeight = selectedMainCategoryFullHeight + 100 - (int)((float)this.field_73881_g * (2160.0f / (float)this.field_73881_g));
            float scrollValue = Math.min(1.0f, (float)scrollOffset * 1.0f / (float)Math.max(1, totalScrollHeight));
            this.scrollBar.setSliderValue(scrollValue);
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        int windowWidth = this.field_73880_f;
        int windowHeight = this.field_73880_f * 9 / 16;
        int mouseXScaled = (int)((float)par1 * (3840.0f / (float)this.field_73880_f));
        int mouseYScaled = (int)((float)par2 * (2160.0f / (float)windowHeight));
        if (!hoveredMainCategory.isEmpty()) {
            selectedMainCategory = hoveredMainCategory;
            selectedMainCategoryFullHeight = 0;
            selectedCategory = "";
            this.scrollBar.setSliderValue(0.0f);
        } else if (!hoveredCategory.isEmpty()) {
            selectedCategory = hoveredCategory;
            this.scrollToCategory(hoveredCategory);
        } else if (!hoveredAction.isEmpty() && hoveredAction.equals("close")) {
            Minecraft.func_71410_x().func_71373_a(this.guiFrom);
        }
        this.inputSearch.func_73793_a(par1, par2, par3);
        super.func_73864_a(par1, par2, par3);
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
}

