/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MoonConfirmDonationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class ToTheMoonGUI
extends GuiScreen {
    private ToTheMoonGuiButton btn;
    private boolean isButtonActive;
    private double goal;
    private double actualMoney;
    private int guiLeft;
    private int guiTop;
    public static int xSize = 289;
    public static int ySize = 172;
    public boolean helpOpened = false;
    public int helpSectionOffsetX = 0;
    private List<String> donators;

    public ToTheMoonGUI(boolean goalAchieved, double goal, double actualMoney, List<String> donators) {
        this.isButtonActive = goalAchieved;
        this.goal = goal;
        this.actualMoney = actualMoney;
        this.donators = donators;
    }

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - xSize) / 2;
        this.guiTop = (this.field_73881_g - ySize) / 2;
        this.btn = new ToTheMoonGuiButton(0, (this.field_73880_f - 289) / 2 + 174, (this.field_73881_g - 172) / 2 + 135, I18n.func_135053_a((String)(this.isButtonActive ? "moon.contribute" : "moon.achieved")), this.isButtonActive);
        this.field_73887_h.add(this.btn);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int x = (this.field_73880_f - xSize) / 2;
        int y = (this.field_73881_g - ySize) / 2;
        ClientEventHandler.STYLE.bindTexture("to_the_moon");
        this.helpSectionOffsetX = !this.helpOpened ? Math.max(this.helpSectionOffsetX - 2, 0) : Math.min(this.helpSectionOffsetX + 2, 88);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 289 + this.helpSectionOffsetX, this.guiTop + 110, 106, 175, 23, 45, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 296 + this.helpSectionOffsetX), (float)(this.guiTop + 147), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 296 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 147)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"moon.label.leader"), this.guiLeft + 296 + this.helpSectionOffsetX, this.guiTop + 147, 0, 1.0f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("to_the_moon");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 289 - 88 + this.helpSectionOffsetX, this.guiTop + 8, 424, 0, 88, 156, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"moon.label.leader.title"), this.guiLeft + 286 - 88 + 44 + this.helpSectionOffsetX, this.guiTop + 17, 0, 1.0f, true, false);
        if (this.donators != null && !this.donators.isEmpty()) {
            for (int i = 0; i < Math.min(this.donators.size(), 10); ++i) {
                int position = i + 1;
                this.drawScaledString(position + ". " + this.donators.get(i).split("#")[0], this.guiLeft + 294 - 88 + this.helpSectionOffsetX, this.guiTop + 30 + i * 13, 0, 0.8f, false, false);
                this.drawScaledString("\u00a72" + this.donators.get(i).split("#")[1] + "$", this.guiLeft + 294 - 88 + this.helpSectionOffsetX + this.field_73886_k.func_78256_a(position + ". ") - 2, this.guiTop + 37 + i * 13, 0, 0.5f, false, false);
            }
        }
        ClientEventHandler.STYLE.bindTexture("to_the_moon");
        ModernGui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, xSize, ySize, 512.0f, 512.0f, false);
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((this.field_73880_f - xSize) / 2 + 17), (float)((this.field_73881_g - ySize) / 2 + 145), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-((this.field_73880_f - xSize) / 2 + 17)), (float)(-((this.field_73881_g - ySize) / 2 + 145)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"moon.title"), (this.field_73880_f - xSize) / 2 + 17, (this.field_73881_g - ySize) / 2 + 145, 0xFFFFFF, 1.2f, false, false);
        GL11.glPopMatrix();
        int percent = (int)(this.actualMoney / this.goal * 100.0);
        Double progress = this.actualMoney / this.goal;
        Double barHeight = progress * 115.0;
        int barHeightInt = barHeight.intValue();
        int offsetHeight = 115 - barHeightInt;
        this.drawScaledString(percent + "%", (this.field_73880_f - xSize) / 2 + 147, (this.field_73881_g - ySize) / 2 + 141, 0xFFFFFF, 0.8f, true, false);
        this.func_73732_a(this.field_73886_k, Math.round(this.goal) + "$", (this.field_73880_f - xSize) / 2 + 94, (this.field_73881_g - ySize) / 2 + 22, 0xFFFFFF);
        ClientEventHandler.STYLE.bindTexture("to_the_moon");
        ModernGui.drawModalRectWithCustomSizedTexture((this.field_73880_f - xSize) / 2 + 139, (this.field_73881_g - ySize) / 2 + 22 + (117 - percent), 0, 212 + (117 - percent), 13, 117 - percent, 512.0f, 512.0f, false);
        String[] descriptionWords = I18n.func_135053_a((String)"moon.desc").split(" ");
        String line = "";
        int lineNumber = 0;
        for (String descWord : descriptionWords) {
            if (this.field_73886_k.func_78256_a(line + descWord) <= 100) {
                if (!line.equals("")) {
                    line = line + " ";
                }
                line = line + descWord;
                continue;
            }
            this.drawScaledString(line, (this.field_73880_f - xSize) / 2 + 170, (this.field_73881_g - ySize) / 2 + 28 + lineNumber * 10, 0xFFFFFF, 0.8f, false, true);
            ++lineNumber;
            line = descWord;
        }
        this.drawScaledString(line, (this.field_73880_f - xSize) / 2 + 170, (this.field_73881_g - ySize) / 2 + 28 + lineNumber * 10, 0xFFFFFF, 0.8f, false, true);
        if (mouseX >= (this.field_73880_f - xSize) / 2 + 233 && mouseX <= (this.field_73880_f - xSize) / 2 + 243 && mouseY >= (this.field_73881_g - ySize) / 2 + 115 && mouseY <= (this.field_73881_g - ySize) / 2 + 125) {
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"moon.info").split("##")), mouseX, mouseY, this.field_73886_k);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.helpOpened && mouseX > this.guiLeft + 289 && mouseX < this.guiLeft + 289 + 23 && mouseY > this.guiTop + 110 && mouseY < this.guiTop + 110 + 45) {
                this.helpOpened = true;
            } else if (this.helpOpened && mouseX > this.guiLeft + 289 + 88 && mouseX < this.guiLeft + 289 + 88 + 23 && mouseY > this.guiTop + 110 && mouseY < this.guiTop + 110 + 45) {
                this.helpOpened = false;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73875_a(GuiButton btn) {
        switch (btn.field_73741_f) {
            case 0: {
                if (!this.isButtonActive) break;
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ToTheMoonModalGui(this));
            }
        }
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
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public static class ToTheMoonModalGui
    extends ModalGui {
        private GuiButton confirmButton;
        private GuiButton cancelButton;
        private GuiTextField amountInput;

        public ToTheMoonModalGui(GuiScreen guiFrom) {
            super(guiFrom);
        }

        public void func_73876_c() {
            this.amountInput.func_73780_a();
        }

        @Override
        public void func_73866_w_() {
            super.func_73866_w_();
            this.confirmButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"moon.modal.confirm"));
            this.cancelButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"moon.modal.cancel"));
            this.amountInput = new GuiTextField(this.field_73886_k, this.guiLeft + 56, this.guiTop + 68, 247, 10);
            this.amountInput.func_73786_a(false);
            this.amountInput.func_73804_f(7);
        }

        @Override
        public void func_73863_a(int mouseX, int mouseY, float par3) {
            super.func_73863_a(mouseX, mouseY, par3);
            this.drawScaledString(I18n.func_135053_a((String)"moon.modal.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"moon.modal.desc_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"moon.modal.desc_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_modal");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 62, 0, 158, 249, 20, 512.0f, 512.0f, false);
            this.amountInput.func_73795_f();
            this.confirmButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        }

        protected void func_73869_a(char typedChar, int keyCode) {
            this.amountInput.func_73802_a(typedChar, keyCode);
            super.func_73869_a(typedChar, keyCode);
        }

        @Override
        protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
            if (mouseButton == 0) {
                if (!this.amountInput.func_73781_b().isEmpty() && this.isNumeric(this.amountInput.func_73781_b()) && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MoonConfirmDonationPacket(Double.parseDouble(this.amountInput.func_73781_b()))));
                    Minecraft.func_71410_x().func_71373_a(null);
                }
                if (mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a(null);
                }
            }
            this.amountInput.func_73793_a(mouseX, mouseY, mouseButton);
            super.func_73864_a(mouseX, mouseY, mouseButton);
        }

        public boolean isNumeric(String str) {
            if (str == null || str.length() == 0) {
                return false;
            }
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) continue;
                return false;
            }
            return Integer.parseInt(str) > 0;
        }
    }

    public static class ToTheMoonGuiButton
    extends GuiButton {
        private boolean isActive;

        public ToTheMoonGuiButton(int par1, int par2, int par3, String par4Str, boolean isActive) {
            super(par1, par2, par3, par4Str);
            this.isActive = isActive;
            this.field_73747_a = 94;
            this.field_73745_b = 15;
        }

        public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
            ClientEventHandler.STYLE.bindTexture("to_the_moon");
            if (this.isActive) {
                if (mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 0, 190, this.field_73747_a, this.field_73745_b, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 0, 175, this.field_73747_a, this.field_73745_b, 512.0f, 512.0f, false);
                }
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 0, 190, this.field_73747_a, this.field_73745_b, 512.0f, 512.0f, false);
            }
            this.func_73732_a(mc.field_71466_p, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + 4, 0xFFFFFF);
        }
    }
}

