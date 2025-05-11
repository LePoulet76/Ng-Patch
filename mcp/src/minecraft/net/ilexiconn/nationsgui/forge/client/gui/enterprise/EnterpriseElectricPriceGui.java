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
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseElectricGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalLargeGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseElectricPricePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

@SideOnly(value=Side.CLIENT)
public class EnterpriseElectricPriceGui
extends ModalLargeGui {
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField priceInput;

    public EnterpriseElectricPriceGui(GuiScreen guiFrom) {
        super(guiFrom);
    }

    public void func_73876_c() {
        this.priceInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 184, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 184, this.guiTop + 184, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.valid"));
        if (!EnterpriseGui.hasPermission("electric")) {
            this.validButton.field_73742_g = false;
        }
        this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 57, this.guiTop + 76, 97, 10);
        this.priceInput.func_73786_a(false);
        this.priceInput.func_73804_f(6);
        this.priceInput.func_73782_a(((Double)EnterpriseElectricGUI.enterpriseElectricInfos.get("price")).intValue() + "");
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        super.func_73863_a(mouseX, mouseY, par3);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.text_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.text_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.text_3"), this.guiLeft + 53, this.guiTop + 55, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal_large");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 70, 0, 242, 122, 20, 512.0f, 512.0f, false);
        this.drawScaledString("\u00a7a$", this.guiLeft + 167, this.guiTop + 76, 0xFFFFFF, 1.3f, true, false);
        this.priceInput.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.avg") + " \u00a72" + String.format("%.1f", (Double)EnterpriseElectricGUI.enterpriseElectricInfos.get("avgPrice")) + "$/GW", this.guiLeft + 180, this.guiTop + 76, 0, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.restrict"), this.guiLeft + 53, this.guiTop + 100, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal_large");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 115, (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAll") != false ? 0 : 10, 262, 10, 10, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.all"), this.guiLeft + 66, this.guiTop + 117, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal_large");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 130, (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowCountry") != false ? 0 : 10, 262, 10, 10, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.country").replace("<country>", (String)EnterpriseElectricGUI.enterpriseElectricInfos.get("associatedCountry")), this.guiLeft + 66, this.guiTop + 132, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal_large");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 145, (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAlly") != false ? 0 : 10, 262, 10, 10, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.electric.modal.price.ally").replace("<country>", (String)EnterpriseElectricGUI.enterpriseElectricInfos.get("associatedCountry")), this.guiLeft + 66, this.guiTop + 147, 0x191919, 1.0f, false, false);
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.priceInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 184 && mouseY < this.guiTop + 184 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseElectricGUI());
            }
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 115 && mouseY < this.guiTop + 115 + 10) {
                EnterpriseElectricGUI.enterpriseElectricInfos.put("allowAll", (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAll") == false);
            } else if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 130 && mouseY < this.guiTop + 130 + 10) {
                EnterpriseElectricGUI.enterpriseElectricInfos.put("allowCountry", (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowCountry") == false);
            } else if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 10 && mouseY > this.guiTop + 145 && mouseY < this.guiTop + 145 + 10) {
                EnterpriseElectricGUI.enterpriseElectricInfos.put("allowAlly", (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAlly") == false);
            }
            if (this.validButton.field_73742_g && this.isNumeric(this.priceInput.func_73781_b()) && EnterpriseGui.hasPermission("capital") && mouseX > this.guiLeft + 184 && mouseX < this.guiLeft + 184 + 118 && mouseY > this.guiTop + 184 && mouseY < this.guiTop + 184 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseElectricPricePacket((String)EnterpriseGui.enterpriseInfos.get("name"), Integer.parseInt(this.priceInput.func_73781_b()), (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAll"), (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowCountry"), (Boolean)EnterpriseElectricGUI.enterpriseElectricInfos.get("allowAlly"))));
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseElectricGUI());
            }
        }
        this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
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
        return Integer.parseInt(str) >= 0;
    }
}

