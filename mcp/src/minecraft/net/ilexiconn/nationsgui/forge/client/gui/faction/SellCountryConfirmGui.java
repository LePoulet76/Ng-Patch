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
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSellCountryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

@SideOnly(value=Side.CLIENT)
public class SellCountryConfirmGui
extends ModalGui {
    private GuiButton cancelButton;
    private GuiButton confirmButton;
    private GuiTextField amountInput;

    public SellCountryConfirmGui(GuiScreen guiFrom) {
        super(guiFrom);
    }

    public void func_73876_c() {
        this.amountInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"faction.sell.modal.action.cancel"));
        this.confirmButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"faction.sell.modal.action.confirm"));
        if (!((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
            this.confirmButton.field_73742_g = false;
        }
        this.amountInput = new GuiTextField(this.field_73886_k, this.guiLeft + 56, this.guiTop + 68, 247, 10);
        this.amountInput.func_73786_a(false);
        this.amountInput.func_73804_f(8);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        super.func_73863_a(mouseX, mouseY, par3);
        this.drawScaledString(I18n.func_135053_a((String)"faction.sell.modal.action.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.sell.modal.action.text_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.sell.modal.action.text_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 62, 0, 158, 249, 20, 512.0f, 512.0f, false);
        this.amountInput.func_73795_f();
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.confirmButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.amountInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(this.guiFrom);
            }
            if (!this.amountInput.func_73781_b().isEmpty() && this.isNumeric(this.amountInput.func_73781_b()) && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSellCountryPacket((String)FactionGUI.factionInfos.get("id"), Integer.parseInt(this.amountInput.func_73781_b()))));
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)FactionGUI.factionInfos.get("name"), true)));
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI((String)FactionGUI.factionInfos.get("name")));
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

