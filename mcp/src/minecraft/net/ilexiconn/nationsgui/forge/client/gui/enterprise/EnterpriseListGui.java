/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseListDataPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnterpriseListGui
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarEnterprises;
    private GuiScrollBarFaction scrollBarTypes;
    public static ArrayList<HashMap<String, String>> enterprises = new ArrayList();
    private HashMap<String, String> hoveredEnterprise = new HashMap();
    private String hoveredType;
    private String selectedType = "all";
    private boolean typesExpanded;
    private boolean open_filter = false;
    private String searchText = "";
    private GuiTextField enterpriseSearch;

    public EnterpriseListGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        enterprises.clear();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseListDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarEnterprises = new GuiScrollBarFaction(this.guiLeft + 296, this.guiTop + 64, 150);
        this.scrollBarTypes = new GuiScrollBarFaction(this.guiLeft + 155, this.guiTop + 45, 90);
        this.enterpriseSearch = new GuiTextField(this.field_73886_k, this.guiLeft + 208, this.guiTop + 28, 93, 10);
        this.enterpriseSearch.func_73786_a(false);
        this.enterpriseSearch.func_73804_f(40);
    }

    public void func_73876_c() {
        this.enterpriseSearch.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int i;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_list");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_create");
        for (i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
            GuiScreenTab type = EnterpriseCreateGui.TABS.get(i);
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
        this.enterpriseSearch.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("enterprise_list");
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
        }
        if (!this.selectedType.equalsIgnoreCase("all")) {
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 23, EnterpriseGui.getTypeOffsetX(this.selectedType), 442, 16, 16, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + this.selectedType)), this.guiLeft + 53 + 16 + 2, this.guiTop + 27, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_list");
        } else {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.type.all"), this.guiLeft + 53, this.guiTop + 27, 0xFFFFFF, 1.0f, false, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.list.name"), this.guiLeft + 51 + 5, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.list.type"), this.guiLeft + 51 + 100, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.list.satisfaction"), this.guiLeft + 51 + 155, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.list.online_players"), this.guiLeft + 51 + 210, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.list.open_filter"), this.guiLeft + 64, this.guiTop + 227, 0x191919, 0.9f, false, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_list");
        if (this.open_filter) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 225, 159, 250, 10, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 225, 169, 250, 10, 10, 512.0f, 512.0f, false);
        }
        if (loaded && enterprises.size() > 0) {
            this.hoveredEnterprise = new HashMap();
            int filterOffsetY = 0;
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);
            for (int i2 = 0; i2 < enterprises.size(); ++i2) {
                if ((!this.open_filter || this.open_filter && enterprises.get(i2).get("open").equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && enterprises.get(i2).get("name").toLowerCase().contains(this.searchText.toLowerCase())) && (this.selectedType.equals("all") || enterprises.get(i2).get("type").equals(this.selectedType))) {
                    int offsetX = this.guiLeft + 51;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 60 + (i2 - filterOffsetY) * 20) + this.getSlideEnterprises());
                    ClientEventHandler.STYLE.bindTexture("enterprise_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 51, 60, 245, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(enterprises.get(i2).get("name"), offsetX + 5, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 100, offsetY.intValue() + 1, EnterpriseGui.getTypeOffsetX(enterprises.get(i2).get("type")), 442, 16, 16, 512.0f, 512.0f, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_list");
                    this.drawScaledString(enterprises.get(i2).get("satisfaction"), offsetX + 155, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(enterprises.get(i2).get("online_players") + "/" + enterprises.get(i2).get("max_players"), offsetX + 210, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    if (this.typesExpanded || mouseX <= this.guiLeft + 51 || mouseX >= this.guiLeft + 51 + 245 || mouseY <= this.guiTop + 60 || mouseY >= this.guiTop + 60 + 158 || mouseX <= offsetX || mouseX >= offsetX + 245 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredEnterprise = enterprises.get(i2);
                    continue;
                }
                ++filterOffsetY;
            }
            GUIUtils.endGLScissor();
            if (!this.typesExpanded) {
                this.scrollBarEnterprises.draw(mouseX, mouseY);
            }
        }
        this.hoveredType = null;
        if (this.typesExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_list");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 40, 180, 250, 110, 99, 512.0f, 512.0f, false);
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 41, 104, 97);
            for (i = 0; i < EnterpriseGui.availableTypes.size(); ++i) {
                int offsetX = this.guiLeft + 51;
                Float offsetY = Float.valueOf((float)(this.guiTop + 41 + i * 20) + this.getSlideTypes());
                ClientEventHandler.STYLE.bindTexture("enterprise_list");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 181, 251, 104, 20, 512.0f, 512.0f, false);
                if (!EnterpriseGui.availableTypes.get(i).equalsIgnoreCase("all")) {
                    ClientEventHandler.STYLE.bindTexture("enterprise_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue() + 1, EnterpriseGui.getTypeOffsetX(EnterpriseGui.availableTypes.get(i)), 442, 16, 16, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + EnterpriseGui.availableTypes.get(i))), offsetX + 2 + 16 + 2, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_list");
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.type.all"), offsetX + 2, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                }
                if (mouseX <= offsetX || mouseX >= offsetX + 104 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                this.hoveredType = EnterpriseGui.availableTypes.get(i);
            }
            GUIUtils.endGLScissor();
            this.scrollBarTypes.draw(mouseX, mouseY);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        for (i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("enterprise.tab.search." + i))), mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideEnterprises() {
        int counter = 0;
        for (int i = 0; i < enterprises.size(); ++i) {
            if (this.open_filter && (!this.open_filter || !enterprises.get(i).get("open").equals("true")) || !this.searchText.isEmpty() && (this.searchText.isEmpty() || !enterprises.get(i).get("name").toLowerCase().contains(this.searchText.toLowerCase()))) continue;
            ++counter;
        }
        return counter > 8 ? (float)(-(counter - 8) * 20) * this.scrollBarEnterprises.getSliderValue() : 0.0f;
    }

    private float getSlideTypes() {
        return EnterpriseGui.availableTypes.size() > 5 ? (float)(-(EnterpriseGui.availableTypes.size() - 5) * 20) * this.scrollBarTypes.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
                GuiScreenTab type = EnterpriseCreateGui.TABS.get(i);
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
            if (this.hoveredEnterprise != null && !this.hoveredEnterprise.isEmpty()) {
                this.field_73882_e.func_71373_a((GuiScreen)new EnterpriseGui(this.hoveredEnterprise.get("name")));
            }
            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 10 && mouseY >= this.guiTop + 225 && mouseY <= this.guiTop + 225 + 10) {
                boolean bl = this.open_filter = !this.open_filter;
            }
            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 110 && mouseY >= this.guiTop + 21 && mouseY <= this.guiTop + 21 + 20) {
                boolean bl = this.typesExpanded = !this.typesExpanded;
            }
            if (this.hoveredType != null && !this.hoveredType.isEmpty()) {
                this.selectedType = this.hoveredType;
                this.hoveredType = null;
                this.typesExpanded = false;
                this.scrollBarEnterprises.setSliderValue(0.0f);
            }
        }
        this.enterpriseSearch.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.enterpriseSearch.func_73802_a(typedChar, keyCode)) {
            this.searchText = this.enterpriseSearch.func_73781_b();
            this.scrollBarEnterprises.setSliderValue(0.0f);
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
}

