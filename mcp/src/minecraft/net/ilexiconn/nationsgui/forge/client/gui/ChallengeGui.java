/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeAcceptPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ChallengeGui
extends GuiScreen {
    protected int xSize = 171;
    protected int ySize = 172;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    public static ArrayList<String> kits = new ArrayList();
    public static String playerAttStats = "0";
    public static String playerDefStats = "0";
    public boolean kitExpanded = false;
    public String selectedKit = "";
    public String hoveredKit = "";
    public GuiTextField betInput;
    private String playerAtt;
    private String playerDef;
    private boolean isSetup;
    private String duelInfos;

    public ChallengeGui(String playerAtt, String playerDef, boolean isSetup, String duelInfos) {
        this.playerAtt = playerAtt;
        this.playerDef = playerDef;
        this.isSetup = isSetup;
        this.duelInfos = duelInfos;
        loaded = false;
    }

    public void func_73876_c() {
        this.betInput.func_73780_a();
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChallengeDataPacket(this.playerAtt, this.playerDef)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.betInput = new GuiTextField(this.field_73886_k, this.guiLeft + 58, this.guiTop + 100, 75, 10);
        this.betInput.func_73786_a(false);
        this.betInput.func_73804_f(8);
        if (this.isSetup) {
            this.betInput.func_73782_a("0");
        } else {
            this.betInput.func_73782_a(this.duelInfos.split("##")[1]);
            this.selectedKit = this.duelInfos.split("##")[0];
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("challenge");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 128), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 128)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"challenge.title"), this.guiLeft + 14, this.guiTop + 128, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ArrayList tooltipToDraw = new ArrayList();
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 0, 261, 9, 10, 512.0f, 512.0f, false);
        } else {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 0, 251, 9, 10, 512.0f, 512.0f, false);
        }
        if (loaded) {
            ResourceLocation resourceLocation;
            this.drawScaledString(I18n.func_135053_a((String)"Duel"), this.guiLeft + 56, this.guiTop + 27, 0xAF1F1F, 1.0f, false, true);
            ClientEventHandler.STYLE.bindTexture("challenge");
            this.drawScaledString(playerAttStats + "%", this.guiLeft + 73, this.guiTop + 38, 0xFFFFFF, 0.7f, true, false);
            try {
                resourceLocation = AbstractClientPlayer.field_110314_b;
                resourceLocation = AbstractClientPlayer.func_110311_f((String)this.playerAtt);
                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)this.playerAtt);
                Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 65 + 14, this.guiTop + 45 + 14, 8.0f, 16.0f, 8, -8, -14, -14, 64.0f, 64.0f);
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 65 + 14, this.guiTop + 45 + 14, 40.0f, 16.0f, 8, -8, -14, -14, 64.0f, 64.0f);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            this.drawScaledString(playerDefStats + "%", this.guiLeft + 134, this.guiTop + 38, 0xFFFFFF, 0.7f, true, false);
            try {
                resourceLocation = AbstractClientPlayer.field_110314_b;
                resourceLocation = AbstractClientPlayer.func_110311_f((String)this.playerDef);
                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)this.playerDef);
                Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 126 + 14, this.guiTop + 45 + 14, 8.0f, 16.0f, 8, -8, -14, -14, 64.0f, 64.0f);
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 126 + 14, this.guiTop + 45 + 14, 40.0f, 16.0f, 8, -8, -14, -14, 64.0f, 64.0f);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ClientEventHandler.STYLE.bindTexture("challenge");
            if (this.isSetup) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 132, 0, 175, 94, 15, 512.0f, 512.0f, false);
                if (this.selectedKit.isEmpty() || mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 132, 0, 190, 94, 15, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"challenge.send_duel"), this.guiLeft + 102, this.guiTop + 136, 0xFFFFFF, 1.0f, true, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 132, 94, 175, 94, 15, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 132, 94, 190, 94, 15, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"challenge.accept_duel"), this.guiLeft + 102, this.guiTop + 136, 0xFFFFFF, 1.0f, true, false);
            }
            this.betInput.func_73795_f();
            this.hoveredKit = "";
            String selectedKitText = I18n.func_135053_a((String)"challenge.placeholder_kit");
            if (!this.selectedKit.isEmpty()) {
                selectedKitText = this.selectedKit;
            }
            this.drawScaledString(selectedKitText, this.guiLeft + 60, this.guiTop + 71, 0xFFFFFF, 1.0f, false, false);
            if (this.isSetup && this.kitExpanded) {
                for (int i = 0; i < kits.size(); ++i) {
                    ClientEventHandler.STYLE.bindTexture("challenge");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 82 + 14 * i, 181, 28, 94, 14, 512.0f, 512.0f, false);
                    this.drawScaledString(kits.get(i), this.guiLeft + 60, this.guiTop + 81 + 14 * i + 4, 0xFFFFFF, 0.9f, false, false);
                    if (mouseX < this.guiLeft + 55 || mouseX > this.guiLeft + 55 + 94 || mouseY < this.guiTop + 81 + 14 * i || mouseY > this.guiTop + 81 + 14 * i + 14) continue;
                    this.hoveredKit = kits.get(i);
                }
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.betInput.func_73802_a(typedChar, keyCode);
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
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (mouseX > this.guiLeft + 134 && mouseX < this.guiLeft + 134 + 15 && mouseY > this.guiTop + 67 && mouseY < this.guiTop + 67 + 14) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.kitExpanded = !this.kitExpanded;
            } else if (!this.hoveredKit.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedKit = this.hoveredKit;
                this.kitExpanded = false;
                this.hoveredKit = "";
            } else if (mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
                if (this.isSetup) {
                    if (this.betInput.func_73781_b().isEmpty()) {
                        this.betInput.func_73782_a("0");
                    }
                    if (!this.selectedKit.isEmpty() && this.isNumeric(this.betInput.func_73781_b())) {
                        this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                        Minecraft.func_71410_x().func_71373_a(null);
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChallengeRequestPacket(this.playerAtt, this.playerDef, this.selectedKit, Integer.parseInt(this.betInput.func_73781_b()))));
                    }
                } else {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a(null);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChallengeAcceptPacket(this.playerAtt, this.playerDef, this.selectedKit, Integer.parseInt(this.betInput.func_73781_b()))));
                }
            }
        }
        if (this.isSetup) {
            this.betInput.func_73793_a(mouseX, mouseY, mouseButton);
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

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) >= 0;
    }
}

