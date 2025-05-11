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
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.ConfirmDeleteGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandFlagGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveSettingsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandSettingsGui
extends GuiScreen {
    protected int xSize = 291;
    protected int ySize = 247;
    private int guiLeft;
    private int guiTop;
    private boolean isPrivate = false;
    private GuiTextField nameTextField;
    private GuiTextField descriptionTextField;
    private GuiTextField passwordTextField;
    private GuiTextField welcomeTextField;
    private RenderItem itemRenderer = new RenderItem();
    private DynamicTexture imageTexture;
    private String cachedImage = "";
    public boolean canSave = true;
    public boolean expandOpenType = false;
    public ArrayList<String> openTypeList = new ArrayList();
    public String hoveredOpenType = "";
    public String selectedOpenType = "";

    public IslandSettingsGui() {
        this.isPrivate = Boolean.parseBoolean((String)IslandMainGui.islandInfos.get("isPrivate"));
        this.selectedOpenType = IslandMainGui.islandInfos.get("password").equals("true") ? "password" : (IslandMainGui.islandInfos.get("askBeforeJoin").equals("true") ? "ask" : "open");
        this.openTypeList.addAll(Arrays.asList("open", "password"));
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.nameTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 30, 218, 10);
        this.nameTextField.func_73786_a(false);
        this.nameTextField.func_73804_f(25);
        this.nameTextField.func_73782_a((String)IslandMainGui.islandInfos.get("name"));
        this.nameTextField.func_73797_d();
        this.descriptionTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 57, 218, 10);
        this.descriptionTextField.func_73786_a(false);
        this.descriptionTextField.func_73804_f(50);
        this.descriptionTextField.func_73782_a((String)IslandMainGui.islandInfos.get("description"));
        this.descriptionTextField.func_73797_d();
        this.passwordTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 168, this.guiTop + 139, 103, 10);
        this.passwordTextField.func_73786_a(false);
        this.passwordTextField.func_73804_f(20);
        this.passwordTextField.func_73782_a("********");
        this.welcomeTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 166, 218, 10);
        this.welcomeTextField.func_73786_a(false);
        this.welcomeTextField.func_73804_f(100);
        this.welcomeTextField.func_73782_a((String)IslandMainGui.islandInfos.get("welcome"));
        this.welcomeTextField.func_73797_d();
    }

    public void func_73876_c() {
        this.nameTextField.func_73780_a();
        this.descriptionTextField.func_73780_a();
        this.passwordTextField.func_73780_a();
        this.welcomeTextField.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        List<Object> tooltipToDraw = new ArrayList();
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("island_main");
        for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
            GuiScreenTab type = IslandMainGui.TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = IslandMainGui.getTabIndex(IslandMainGui.TABS.get(i));
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 23, 249, 29, 30, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 0, 249, 23, 30, 512.0f, 512.0f, false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.tab." + x)));
        }
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 61, 305, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 61, 295, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.name"), this.guiLeft + 48, this.guiTop + 16, 0, 1.0f, false, false);
        this.nameTextField.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.description"), this.guiLeft + 48, this.guiTop + 44, 0, 1.0f, false, false);
        this.descriptionTextField.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.welcome"), this.guiLeft + 48, this.guiTop + 153, 0, 1.0f, false, false);
        this.welcomeTextField.func_73795_f();
        if (!(this.imageTexture != null && ((String)IslandMainGui.islandInfos.get("image")).equals(this.cachedImage) || ((String)IslandMainGui.islandInfos.get("image")).isEmpty())) {
            BufferedImage image = IslandSettingsGui.decodeToImage((String)IslandMainGui.islandInfos.get("image"));
            this.imageTexture = new DynamicTexture(image);
            this.cachedImage = (String)IslandMainGui.islandInfos.get("image");
        }
        if (this.imageTexture != null) {
            GL11.glBindTexture((int)3553, (int)this.imageTexture.func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 64, this.guiTop + 84, 0.0f, 0.0f, 102, 102, 30, 30, 102.0f, 102.0f, false);
            ClientEventHandler.STYLE.bindTexture("island_settings");
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 63, this.guiTop + 83, 292, 41, 32, 32, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3008);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.edit_flag"), this.guiLeft + 186, this.guiTop + 95, 0xFFFFFF, 1.0f, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (this.isPrivate) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 52, this.guiTop + 137, 50, 295, 10, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.private"), this.guiLeft + 64, this.guiTop + 138, 0xFFFFFF, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (!IslandMainGui.isPremium) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 101, this.guiTop + 131, 292, 90, 10, 12, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 101 && mouseX <= this.guiLeft + 101 + 10 && mouseY >= this.guiTop + 131 && mouseY <= this.guiTop + 131 + 12) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.premium_required"));
            }
        }
        int openTypeTextureY = 294;
        if (this.selectedOpenType.equals("ask")) {
            openTypeTextureY = 306;
        } else if (this.selectedOpenType.equals("password")) {
            openTypeTextureY = 320;
        }
        ClientEventHandler.STYLE.bindTexture("island_settings");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 115, this.guiTop + 136, 73, openTypeTextureY, 20, 11, 512.0f, 512.0f, false);
        if (this.expandOpenType) {
            for (int i = 0; i < this.openTypeList.size(); ++i) {
                openTypeTextureY = 294;
                if (this.openTypeList.get(i).equals("ask")) {
                    openTypeTextureY = 306;
                } else if (this.openTypeList.get(i).equals("password")) {
                    openTypeTextureY = 320;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 112, this.guiTop + 149 + 15 * i, 0, 295, 49, 16, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 115, this.guiTop + 151 + 15 * i, 73, openTypeTextureY, 20, 11, 512.0f, 512.0f, false);
                if (mouseX < this.guiLeft + 112 || mouseX > this.guiLeft + 112 + 49 || mouseY < this.guiTop + 149 + 15 * i || mouseY > this.guiTop + 149 + 15 * i + 11) continue;
                this.hoveredOpenType = this.openTypeList.get(i);
            }
        }
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (this.selectedOpenType.equals("password")) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 163, this.guiTop + 134, 0, 275, 113, 16, 512.0f, 512.0f, false);
            this.passwordTextField.func_73795_f();
        }
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (IslandMainGui.islandInfos.get("isCreator").equals("false") || mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48, this.guiTop + 214, 0, 256, 113, 18, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.settings.delete"), this.guiLeft + 104, this.guiTop + 219, 0xFFFFFF, 1.0f, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (!this.canSave || this.nameTextField.func_73781_b().isEmpty() || this.descriptionTextField.func_73781_b().isEmpty() || this.selectedOpenType.equals("password") && this.passwordTextField.func_73781_b().isEmpty() || mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 163, this.guiTop + 214, 113, 256, 113, 18, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.global.save"), this.guiLeft + 219, this.guiTop + 219, 0xFFFFFF, 1.0f, true, false);
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.nameTextField.func_73802_a(typedChar, keyCode);
        this.descriptionTextField.func_73802_a(typedChar, keyCode);
        this.passwordTextField.func_73802_a(typedChar, keyCode);
        this.welcomeTextField.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
                GuiScreenTab type = IslandMainGui.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
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
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (IslandMainGui.isPremium && mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 10 && mouseY >= this.guiTop + 137 && mouseY <= this.guiTop + 137 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.isPrivate = !this.isPrivate;
                this.canSave = true;
            } else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 49 && mouseY >= this.guiTop + 134 && mouseY <= this.guiTop + 134 + 15) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.expandOpenType = !this.expandOpenType;
                this.canSave = true;
            } else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 49 && mouseY >= this.guiTop + 149 && mouseY <= this.guiTop + 149 + 46 && !this.hoveredOpenType.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedOpenType = this.hoveredOpenType;
                this.expandOpenType = false;
                this.canSave = true;
                if (this.selectedOpenType.equals("password")) {
                    this.passwordTextField.func_73782_a("");
                }
            } else if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 134 && mouseY <= this.guiTop + 134 + 16 && this.passwordTextField.func_73781_b().matches("\\*+")) {
                this.passwordTextField.func_73782_a("");
                this.canSave = true;
            } else if (mouseX >= this.guiLeft + 47 && mouseX <= this.guiLeft + 47 + 228 && mouseY >= this.guiTop + 25 && mouseY <= this.guiTop + 25 + 44) {
                this.canSave = true;
            } else if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                if (!(!this.canSave || this.nameTextField.func_73781_b().isEmpty() || this.descriptionTextField.func_73781_b().isEmpty() || this.selectedOpenType.equals("password") && this.passwordTextField.func_73781_b().isEmpty())) {
                    this.canSave = false;
                    HashMap<String, String> settings = new HashMap<String, String>();
                    settings.put("name", this.nameTextField.func_73781_b());
                    settings.put("description", this.descriptionTextField.func_73781_b());
                    settings.put("welcome", this.welcomeTextField.func_73781_b());
                    settings.put("isPrivate", IslandMainGui.isPremium ? this.isPrivate + "" : "false");
                    settings.put("openType", this.selectedOpenType);
                    settings.put("password", this.passwordTextField.func_73781_b());
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSaveSettingsPacket((String)IslandMainGui.islandInfos.get("id"), settings)));
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                }
            } else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 150 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandFlagGui());
            } else if (IslandMainGui.islandInfos.get("isCreator").equals("true") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ConfirmDeleteGui(this));
            }
        }
        this.nameTextField.func_73793_a(mouseX, mouseY, mouseButton);
        this.descriptionTextField.func_73793_a(mouseX, mouseY, mouseButton);
        this.passwordTextField.func_73793_a(mouseX, mouseY, mouseButton);
        this.welcomeTextField.func_73793_a(mouseX, mouseY, mouseButton);
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

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}

