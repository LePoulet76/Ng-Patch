/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchActionsGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WildernessGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateCheckCountryDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class CreateGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    private boolean regenZone = false;
    private boolean announce = false;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public MinimapRenderer minimapRenderer = new MinimapRenderer(8, 8);
    boolean expanded = false;
    public static boolean playerHasCountry = false;
    public static ArrayList<HashMap<String, String>> countries = new ArrayList();
    public static HashMap<String, String> selectedCountry = new HashMap();
    private HashMap<String, String> hoveredCountry = new HashMap();
    public static boolean mapLoaded;
    public static String mapLoadedFor;
    private GuiButton createButton;
    private GuiButton redirectButton;
    private GuiButton leaveButton;

    public CreateGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionCreateDataPacket()));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionCreateCheckCountryDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 296, this.guiTop + 69, 150);
        this.createButton = new GuiButton(0, this.guiLeft + 186, this.guiTop + 201, 122, 20, I18n.func_135053_a((String)"faction.create.create"));
        this.redirectButton = new GuiButton(1, this.guiLeft + 97, this.guiTop + 130, 160, 20, I18n.func_135053_a((String)"faction.create.redirect"));
        this.leaveButton = new GuiButton(2, this.guiLeft + 97, this.guiTop + 140, 160, 20, I18n.func_135053_a((String)"faction.create.leave"));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int i;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_create");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (countries != null && countries.size() > 0) {
            this.createButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            if (!(NationsGUIClientHooks.whitelistedStaff != null && NationsGUIClientHooks.whitelistedStaff.contains(Minecraft.func_71410_x().field_71439_g.getDisplayName()) || System.currentTimeMillis() - ClientProxy.clientConfig.currentServerTime >= 10800000L)) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 122 && mouseY >= this.guiTop + 201 && mouseY <= this.guiTop + 201 + 20) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.create.cooldown")), mouseX, mouseY, this.field_73886_k);
                }
            }
        }
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
        this.drawScaledString(I18n.func_135053_a((String)"faction.create.title"), this.guiLeft + 50, this.guiTop + 20, 0x191919, 1.4f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.create.map"), this.guiLeft + 51, this.guiTop + 83, 0x191919, 0.8f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.create.location"), this.guiLeft + 187, this.guiTop + 83, 0x191919, 0.8f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_create");
        if (playerHasCountry) {
            Gui.func_73734_a((int)(this.guiLeft + 45), (int)(this.guiTop + 30), (int)(this.guiLeft + 45 + 266), (int)(this.guiTop + 30 + 209), (int)-1513240);
            this.drawScaledString(I18n.func_135053_a((String)"faction.create.hascountry_1"), this.guiLeft + 177, this.guiTop + 100, 328965, 1.1f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.create.hascountry_2"), this.guiLeft + 177, this.guiTop + 110, 328965, 1.1f, true, false);
            this.leaveButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        } else if (countries != null && countries.size() > 0) {
            if (selectedCountry != null && !selectedCountry.isEmpty()) {
                this.drawScaledString(selectedCountry.get("name"), this.guiLeft + 56, this.guiTop + 52, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(selectedCountry.get("x"), this.guiLeft + 213, this.guiTop + 98, 0xFFFFFF, 1.0f, true, false);
                this.drawScaledString(selectedCountry.get("z"), this.guiLeft + 276, this.guiTop + 98, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture("faction_create");
                if (this.announce) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 186, this.guiTop + 155, 159, 250, 10, 10, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 186, this.guiTop + 155, 169, 250, 10, 10, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.create.announce"), this.guiLeft + 200, this.guiTop + 157, 0x191919, 0.9f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_create");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 300, this.guiTop + 155, 148, 250, 10, 11, 512.0f, 512.0f, false);
                if (!mapLoaded || mapLoadedFor != null && !mapLoadedFor.equals(selectedCountry.get("name"))) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(selectedCountry.get("x")), Integer.parseInt(selectedCountry.get("z")), 8, 8)));
                    mapLoaded = true;
                    mapLoadedFor = selectedCountry.get("name");
                }
                GL11.glDisable((int)2929);
                this.minimapRenderer.renderMap(this.guiLeft + 51, this.guiTop + 92, mouseX, mouseY, true);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("faction_create");
                GL11.glEnable((int)3042);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3008);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 91, 0, 344, 129, 129, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3008);
            }
            if (this.expanded && countries.size() > 0) {
                ClientEventHandler.STYLE.bindTexture("faction_create");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 64, 241, 344, 253, 160, 512.0f, 512.0f, false);
                this.hoveredCountry = new HashMap();
                GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 65, 253, 160);
                if (countries.size() > 0) {
                    for (i = 0; i < countries.size(); ++i) {
                        int offsetX = this.guiLeft + 51;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 65 + i * 20) + this.getSlide());
                        ClientEventHandler.STYLE.bindTexture("faction_create");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 242, 345, 245, 20, 512.0f, 512.0f, false);
                        this.drawScaledString(countries.get(i).get("name"), offsetX + 5, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                        if (mouseX < offsetX || mouseX > offsetX + 245 || !((float)mouseY >= offsetY.floatValue()) || !((float)mouseY <= offsetY.floatValue() + 20.0f)) continue;
                        this.hoveredCountry = countries.get(i);
                    }
                }
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
            }
        } else {
            Gui.func_73734_a((int)(this.guiLeft + 45), (int)(this.guiTop + 30), (int)(this.guiLeft + 45 + 266), (int)(this.guiTop + 30 + 209), (int)-1513240);
            this.drawScaledString(I18n.func_135053_a((String)"faction.create.unavailable"), this.guiLeft + 177, this.guiTop + 100, 328965, 1.1f, true, false);
            this.redirectButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        }
        for (i = 0; i < SearchGui.TABS.size(); ++i) {
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab.search." + i))), mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlide() {
        return countries.size() > 8 ? (float)(-(countries.size() - 8) * 20) * this.scrollBar.getSliderValue() : 0.0f;
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
            if (mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 302 && mouseY >= this.guiTop + 46 && mouseY <= this.guiTop + 64) {
                this.expanded = !this.expanded;
            }
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(null);
            }
            if (this.hoveredCountry != null && !this.hoveredCountry.isEmpty()) {
                selectedCountry = this.hoveredCountry;
                this.hoveredCountry = new HashMap();
                this.expanded = false;
            }
            if (mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 10 && mouseY >= this.guiTop + 155 && mouseY <= this.guiTop + 155 + 10) {
                boolean bl = this.announce = !this.announce;
            }
            if (countries != null && countries.size() == 0 && mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 160 && mouseY >= this.guiTop + 130 && mouseY <= this.guiTop + 130 + 20) {
                this.field_73882_e.func_71373_a((GuiScreen)new SearchGui());
            }
            if ((NationsGUIClientHooks.whitelistedStaff != null && NationsGUIClientHooks.whitelistedStaff.contains(Minecraft.func_71410_x().field_71439_g.getDisplayName()) || System.currentTimeMillis() - ClientProxy.clientConfig.currentServerTime > 10800000L) && this.createButton.field_73742_g && selectedCountry != null && !selectedCountry.isEmpty() && mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 122 && mouseY >= this.guiTop + 201 && mouseY <= this.guiTop + 201 + 20) {
                List<String> artarcticaCountries;
                if (System.currentTimeMillis() < 1648382400000L && (artarcticaCountries = Arrays.asList("ArchipelCrozet", "TerreSiple", "TerreSpaatz", "TerreMill", "TerreGrant", "TerreVega", "TerreThor", "TerreLow", "TerrePowell", "TerreAdelie", "TerreBurke", "TerreSnow", "TerreBooth", "TerreSmith", "IleBouvet", "TerreRoss", "TerreSigny", "TerreLiard", "TerreMasson")).contains(selectedCountry.get("name"))) {
                    Minecraft.func_71410_x().func_71373_a(null);
                    return;
                }
                ClientProxy.clientConfig.currentServerTime = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionCreatePacket(selectedCountry.get("name"), this.announce, this.regenZone)));
                Minecraft.func_71410_x().func_71373_a(null);
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (playerHasCountry && mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 160 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LeaveConfirmGui(this));
                playerHasCountry = false;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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

