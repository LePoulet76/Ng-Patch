/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnterpriseCreateGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    boolean typesExpanded = false;
    private String selectedType = "";
    private String hoveredType = "";
    private GuiButton createButton;
    private GuiTextField enterpriseNameField;
    public static HashMap<String, Object> createInfos = new HashMap();
    public static String errorMessage;

    public EnterpriseCreateGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseCreateDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 296, this.guiTop + 128, 70);
        this.createButton = new GuiButton(0, this.guiLeft + 303 - 122, this.guiTop + 211, 122, 20, I18n.func_135053_a((String)"enterprise.create.create"));
        this.enterpriseNameField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 65, 247, 10);
        this.enterpriseNameField.func_73786_a(false);
        this.enterpriseNameField.func_73804_f(15);
        this.selectedType = "build";
        errorMessage = "";
    }

    public void func_73876_c() {
        this.enterpriseNameField.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.enterpriseNameField.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int i;
        this.func_73873_v_();
        List<String> tooltipToDraw = null;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_create");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.createButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.enterpriseNameField.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("enterprise_create");
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
        if (loaded) {
            if ((Double)createInfos.get("count") >= (Double)createInfos.get("canCreate")) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.disable.count").split("##"));
                }
            } else if ((Double)createInfos.get("canJoin") <= 0.0) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.max_join.count").split("##"));
                }
            } else if (((List)createInfos.get("types")).contains(this.selectedType)) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.disable.type"));
                }
            } else if (((List)createInfos.get("forbiddenTypes")).contains(this.selectedType)) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.forbidden.type"));
                }
            } else if (((List)createInfos.get("blockedByResearchTypes")).contains(this.selectedType)) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.blockedByResearchTypes.type"));
                }
            } else if (((List)createInfos.get("notAvailableTypes")).contains(this.selectedType)) {
                this.createButton.field_73742_g = false;
                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.create.not_available.type"));
                }
            } else {
                this.createButton.field_73742_g = true;
            }
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.create.title"), this.guiLeft + 50, this.guiTop + 20, 0x191919, 1.4f, false, false);
            if (errorMessage != null && !errorMessage.isEmpty()) {
                this.drawScaledString(I18n.func_135053_a((String)("enterprise.create.error." + errorMessage)), this.guiLeft + 50, this.guiTop + 36, 0x191919, 1.0f, false, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.create.name"), this.guiLeft + 51, this.guiTop + 49, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.create.type"), this.guiLeft + 51, this.guiTop + 94, 0x191919, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 106, EnterpriseGui.getTypeOffsetX(this.selectedType), 442, 16, 16, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + this.selectedType)), this.guiLeft + 53 + 16 + 2, this.guiTop + 110, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_create");
            if (this.selectedType != null && !this.selectedType.isEmpty() && EnterpriseGui.availableTypes.contains(this.selectedType)) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 66, this.guiTop + 148, EnterpriseGui.availableTypes.indexOf(this.selectedType) <= 12 ? EnterpriseGui.availableTypes.indexOf(this.selectedType) * 39 : (EnterpriseGui.availableTypes.indexOf(this.selectedType) - 13) * 39, EnterpriseGui.availableTypes.indexOf(this.selectedType) <= 12 ? 331 : 371, 39, 39, 512.0f, 512.0f, false);
                String[] descriptionWords = I18n.func_135053_a((String)("enterprise.create.type.desc." + this.selectedType)).split(" ");
                String line = "";
                int lineNumber = 0;
                for (String descWord : descriptionWords) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if ((double)this.field_73886_k.func_78256_a(stringBuilder.append(line).append(descWord).toString()) * 0.9 <= 175.0) {
                        if (!line.equals("")) {
                            line = line + " ";
                        }
                        line = line + descWord;
                        continue;
                    }
                    this.drawScaledString(line, this.guiLeft + 124, this.guiTop + 137 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                    ++lineNumber;
                    line = descWord;
                }
                this.drawScaledString(line, this.guiLeft + 124, this.guiTop + 137 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
            }
            ClientEventHandler.STYLE.bindTexture("enterprise_create");
            if (this.typesExpanded) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 123, 181, 250, 253, 80, 512.0f, 512.0f, false);
                GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 124, 245, 78);
                for (i = 0; i < EnterpriseGui.availableTypes.size(); ++i) {
                    int offsetX = this.guiLeft + 51;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 124 + i * 20) + this.getSlideTypes());
                    ClientEventHandler.STYLE.bindTexture("enterprise_create");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 182, 251, 245, 20, 512.0f, 512.0f, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue() + 1, EnterpriseGui.getTypeOffsetX(EnterpriseGui.availableTypes.get(i)), 442, 16, 16, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + EnterpriseGui.availableTypes.get(i))), offsetX + 2 + 16 + 3, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_create");
                    if (mouseX <= offsetX || mouseX >= offsetX + 245 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredType = EnterpriseGui.availableTypes.get(i);
                }
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("enterprise_create");
            String price = "\u00a77" + I18n.func_135053_a((String)"enterprise.create.fee") + ": \u00a7f" + (int)(((Double)createInfos.get("count") + 1.0) * 15000.0) + "$";
            this.drawScaledString(price, this.guiLeft + 168 - this.field_73886_k.func_78256_a(price), this.guiTop + 217, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString("\u00a7a$", this.guiLeft + 60, this.guiTop + 217, 0xFFFFFF, 1.3f, true, false);
        }
        for (i = 0; i < TABS.size(); ++i) {
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("enterprise.tab.search." + i))), mouseX, mouseY, this.field_73886_k);
        }
        if (tooltipToDraw != null) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideTypes() {
        return EnterpriseGui.availableTypes.size() > 4 ? (float)(-(EnterpriseGui.availableTypes.size() - 4) * 20) * this.scrollBar.getSliderValue() : 0.0f;
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
            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 253 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 20) {
                boolean bl = this.typesExpanded = !this.typesExpanded;
            }
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(null);
            }
            if (this.hoveredType != null && !this.hoveredType.isEmpty()) {
                this.selectedType = this.hoveredType;
                this.hoveredType = "";
                this.typesExpanded = false;
            }
            if (!this.enterpriseNameField.func_73781_b().isEmpty() && this.createButton.field_73742_g && this.selectedType != null && !this.selectedType.isEmpty() && mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                System.out.println("send create packet");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseCreatePacket(this.enterpriseNameField.func_73781_b(), this.selectedType, (int)(((Double)createInfos.get("count") + 1.0) * 15000.0))));
            }
            this.enterpriseNameField.func_73793_a(mouseX, mouseY, mouseButton);
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
                return EnterpriseCreateGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseCreateGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return EnterpriseListGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseListGui());
            }
        });
    }
}

