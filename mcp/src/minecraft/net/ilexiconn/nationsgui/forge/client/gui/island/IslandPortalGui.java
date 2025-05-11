/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandPortalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class IslandPortalGui
extends GuiScreen {
    public int posX;
    public int posY;
    public int posZ;
    protected int xSize = 319;
    protected int ySize = 128;
    public int guiLeft;
    public int guiTop;
    public boolean isIsland = false;
    private GuiButton cancelButton;
    private GuiButton confirmButton;
    private GuiTextField codeInput;

    public IslandPortalGui(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void func_73876_c() {
        if (!this.isIsland) {
            this.codeInput.func_73780_a();
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.codeInput = new GuiTextField(this.field_73886_k, this.guiLeft + 56, this.guiTop + 68, 247, 10);
        this.codeInput.func_73786_a(false);
        this.codeInput.func_73804_f(20);
        if (ClientProxy.serverType.equalsIgnoreCase("build")) {
            this.isIsland = true;
            this.codeInput.func_73782_a(System.currentTimeMillis() + "");
        }
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"island.portal.cancel"));
        this.confirmButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, this.isIsland ? I18n.func_135053_a((String)"island.portal.copy") : I18n.func_135053_a((String)"island.portal.confirm"));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        super.func_73863_a(mouseX, mouseY, par3);
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 143, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 133, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.portal.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        if (ClientProxy.serverType.equalsIgnoreCase("ng")) {
            this.drawScaledString(I18n.func_135053_a((String)"island.portal.ng.description_1"), this.guiLeft + 53, this.guiTop + 25, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"island.portal.ng.description_2"), this.guiLeft + 53, this.guiTop + 35, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"island.portal.ng.description_3"), this.guiLeft + 53, this.guiTop + 45, 0x191919, 1.0f, false, false);
        } else {
            this.drawScaledString(I18n.func_135053_a((String)"island.portal.build.description_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"island.portal.build.description_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
        }
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 62, 0, 158, 249, 20, 512.0f, 512.0f, false);
        this.codeInput.func_73795_f();
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.confirmButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (!this.isIsland) {
            this.codeInput.func_73802_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
        }
        if (!this.codeInput.func_73781_b().isEmpty() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandPortalPacket(this.codeInput.func_73781_b(), this.posX, this.posY, this.posZ)));
            if (this.isIsland) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                StringSelection strSel = new StringSelection(this.codeInput.func_73781_b());
                clipboard.setContents(strSel, null);
            }
            Minecraft.func_71410_x().func_71373_a(null);
        }
        if (!this.isIsland) {
            this.codeInput.func_73793_a(mouseX, mouseY, mouseButton);
        }
        if (mouseButton == 0 && mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
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
}

