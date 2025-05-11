/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionResearchGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.GalleryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public abstract class TabbedFactionGUI
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    public static int GUI_SCALE = 3;
    protected int xSize = 463;
    protected int ySize = 235;
    protected int guiLeft;
    protected int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static List<String> tooltipToDraw = new ArrayList<String>();
    protected String hoveredAction = "";
    public static HashMap<String, Integer> tabIconsPositionX = new HashMap<String, Integer>(){
        {
            this.put("FactionGUI", 0);
            this.put("MembersGUI", 19);
            this.put("WarGUI", 38);
            this.put("BankGUI", 57);
            this.put("DiplomatieGUI", 76);
            this.put("FactionStatsGUI", 95);
            this.put("FactionSkillsGUI", 114);
            this.put("SettingsGUI", 133);
            this.put("GalleryGUI", 152);
            this.put("FactionResearchGUI", 171);
            this.put("FactionPlotsGUI", 190);
        }
    };
    public static HashMap<String, Integer> tabIconsY = new HashMap<String, Integer>(){
        {
            this.put("neutral", 110);
            this.put("enemy", 91);
            this.put("colony", 72);
            this.put("ally", 53);
            this.put("hover", 34);
        }
    };

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public static void initTabs() {
        TABS.clear();
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI((String)FactionGUI.factionInfos.get("name")));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return BankGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new BankGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return MembersGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new MembersGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionSkillsGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionSkillsGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return WarGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return DiplomatieGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new DiplomatieGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return GalleryGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new GalleryGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionStatsGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionStatsGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionResearchGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionResearchGUI());
            }
        });
    }

    public abstract void drawScreen(int var1, int var2);

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (mouseX < this.guiLeft + 11 || mouseX > this.guiLeft + 11 + 19 || mouseY < this.guiTop + 1 + i * 19 || mouseY > this.guiTop + 1 + i * 19 + 19) continue;
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
            if (mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        GuiScreenTab type;
        int i;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_global");
        if (mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 445, this.guiTop + 8, 0 * GUI_SCALE, 18 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0f, 1536.0f, false);
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 445, this.guiTop + 8, 0 * GUI_SCALE, 2 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0f, 1536.0f, false);
        }
        if (FactionGUI.factionInfos != null && (this instanceof FactionGUI && !FactionGUI.displayLevels || this instanceof MembersGUI || this instanceof FactionSkillsGUI || this instanceof WarGUI || this instanceof FactionStatsGUI || this instanceof DiplomatieGUI) && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
            ClientEventHandler.STYLE.bindTexture("faction_global");
            if (mouseX >= this.guiLeft + 430 && mouseX <= this.guiLeft + 430 + 9 && mouseY >= this.guiTop + 8 && mouseY <= this.guiTop + 8 + 8) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 430, this.guiTop + 8, 30 * GUI_SCALE, 18 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (FactionGUI.getResearchLevel("general") >= 11) {
                    this.hoveredAction = "edit_photo";
                } else {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"gui.faction.not_enough_research").split("##"));
                }
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 430, this.guiTop + 8, 30 * GUI_SCALE, 2 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
        }
        ClientEventHandler.STYLE.bindTexture("faction_global");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop, 0 * GUI_SCALE, 280 * GUI_SCALE, 19 * GUI_SCALE, 209 * GUI_SCALE, 19, 209, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        for (i = 0; i < TABS.size(); ++i) {
            type = TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = i % 9;
            int y = i / 9;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + i * 19, 399 * GUI_SCALE, 64 * GUI_SCALE + i * 19 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + 1 + i * 19, tabIconsPositionX.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, (FactionGUI.factionInfos != null ? tabIconsY.get(FactionGUI.factionInfos.get("actualRelation")) : 129) * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                continue;
            }
            if (mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 19 && mouseY < this.guiTop + 1 + i * 19 + 19) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + 1 + i * 19, tabIconsPositionX.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, 34 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                continue;
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + 1 + i * 19, tabIconsPositionX.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, 129 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        }
        for (i = 0; i < TABS.size(); ++i) {
            type = TABS.get(i);
            if (mouseX <= this.guiLeft + 11 || mouseX >= this.guiLeft + 11 + 19 || mouseY <= this.guiTop + 1 + i * 19 || mouseY >= this.guiTop + 1 + i * 19 + 19) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
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
}

