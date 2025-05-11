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
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchActionsGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WildernessGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SearchGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBar;
    public static ArrayList<HashMap<String, String>> countries = new ArrayList();
    private HashMap<String, String> hoveredCountry = new HashMap();
    private boolean open_filter = false;
    private String searchText = "";
    private GuiTextField countrySearch;

    public SearchGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionListDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 296, this.guiTop + 64, 150);
        this.countrySearch = new GuiTextField(this.field_73886_k, this.guiLeft + 208, this.guiTop + 28, 93, 10);
        this.countrySearch.func_73786_a(false);
        this.countrySearch.func_73804_f(40);
    }

    public void func_73876_c() {
        this.countrySearch.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int i;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_search");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.countrySearch.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("faction_search");
        for (i = 0; i < TABS.size(); ++i) {
            GuiScreenTab type = TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = i % 5;
            int y = i / 5;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 23, 251, 29, 30, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 298 + y * 20, 20, 20, 512.0f, 512.0f, false);
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 0, 251, 23, 30, 512.0f, 512.0f, false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 298 + y * 20, 20, 20, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
        }
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.title"), this.guiLeft + 50, this.guiTop + 20, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.country"), this.guiLeft + 51 + 5, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.age"), this.guiLeft + 51 + 100, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.level"), this.guiLeft + 51 + 155, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.online_players"), this.guiLeft + 51 + 210, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.search.open_filter"), this.guiLeft + 64, this.guiTop + 227, 0x191919, 0.9f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_search");
        if (this.open_filter) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 225, 159, 250, 10, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 225, 169, 250, 10, 10, 512.0f, 512.0f, false);
        }
        if (loaded && countries.size() > 0) {
            this.hoveredCountry = new HashMap();
            int filterOffsetY = 0;
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);
            for (int i2 = 0; i2 < countries.size(); ++i2) {
                if ((!this.open_filter || this.open_filter && countries.get(i2).get("open").equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && countries.get(i2).get("name").toLowerCase().contains(this.searchText.toLowerCase()))) {
                    int offsetX = this.guiLeft + 51;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 60 + (i2 - filterOffsetY) * 20) + this.getSlide());
                    ClientEventHandler.STYLE.bindTexture("faction_search");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 51, 60, 245, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(countries.get(i2).get("name"), offsetX + 5, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(countries.get(i2).get("age") + I18n.func_135053_a((String)"faction.common.days.short"), offsetX + 100, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(countries.get(i2).get("level"), offsetX + 155, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(countries.get(i2).get("online_players") + "/" + countries.get(i2).get("max_players"), offsetX + 210, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX <= this.guiLeft + 51 || mouseX >= this.guiLeft + 51 + 245 || mouseY <= this.guiTop + 60 || mouseY >= this.guiTop + 60 + 158 || mouseX <= offsetX || mouseX >= offsetX + 245 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredCountry = countries.get(i2);
                    continue;
                }
                ++filterOffsetY;
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
        for (i = 0; i < TABS.size(); ++i) {
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab.search." + i))), mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlide() {
        int counter = 0;
        for (int i = 0; i < countries.size(); ++i) {
            if (this.open_filter && (!this.open_filter || !countries.get(i).get("open").equals("true")) || !this.searchText.isEmpty() && (this.searchText.isEmpty() || !countries.get(i).get("name").toLowerCase().contains(this.searchText.toLowerCase()))) continue;
            ++counter;
        }
        return counter > 8 ? (float)(-(counter - 8) * 20) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 50 + i * 31) continue;
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
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(null);
            }
            if (this.hoveredCountry != null && !this.hoveredCountry.isEmpty()) {
                this.field_73882_e.func_71373_a((GuiScreen)new FactionGui_OLD(this.hoveredCountry.get("name")));
            }
            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 10 && mouseY >= this.guiTop + 225 && mouseY <= this.guiTop + 225 + 10) {
                this.open_filter = !this.open_filter;
            }
        }
        this.countrySearch.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.countrySearch.func_73802_a(typedChar, keyCode)) {
            this.searchText = this.countrySearch.func_73781_b();
        }
        super.func_73869_a(typedChar, keyCode);
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

    static {
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return CreateGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CreateGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return SearchGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SearchGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return SearchActionsGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SearchActionsGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return WildernessGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WildernessGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return SearchSellCountryGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SearchSellCountryGui());
            }
        });
    }
}

