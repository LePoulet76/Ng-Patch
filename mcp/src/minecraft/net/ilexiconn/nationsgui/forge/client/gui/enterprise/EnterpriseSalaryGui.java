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
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankSalaryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

@SideOnly(value=Side.CLIENT)
public class EnterpriseSalaryGui
extends ModalGui {
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField leaderInput;
    private GuiTextField cadreInput;
    private GuiTextField employeeInput;

    public EnterpriseSalaryGui(GuiScreen guiFrom) {
        super(guiFrom);
    }

    public void func_73876_c() {
        this.leaderInput.func_73780_a();
        this.cadreInput.func_73780_a();
        this.employeeInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.cancelButton = new GuiButton(0, this.guiLeft + 48, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 190, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.valid"));
        if (!EnterpriseGui.hasPermission("salary")) {
            this.validButton.field_73742_g = false;
        }
        this.leaderInput = new GuiTextField(this.field_73886_k, this.guiLeft + 70, this.guiTop + 72, 55, 10);
        this.leaderInput.func_73786_a(false);
        this.leaderInput.func_73804_f(5);
        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_leader")) {
            this.leaderInput.func_73782_a(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_leader")).intValue() + "");
        } else {
            this.leaderInput.func_73782_a("0");
        }
        this.cadreInput = new GuiTextField(this.field_73886_k, this.guiLeft + 160, this.guiTop + 72, 55, 10);
        this.cadreInput.func_73786_a(false);
        this.cadreInput.func_73804_f(5);
        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_cadre")) {
            this.cadreInput.func_73782_a(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_cadre")).intValue() + "");
        } else {
            this.cadreInput.func_73782_a("0");
        }
        this.employeeInput = new GuiTextField(this.field_73886_k, this.guiLeft + 250, this.guiTop + 72, 55, 10);
        this.employeeInput.func_73786_a(false);
        this.employeeInput.func_73804_f(5);
        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_employee")) {
            this.employeeInput.func_73782_a(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_employee")).intValue() + "");
        } else {
            this.employeeInput.func_73782_a("0");
        }
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        super.func_73863_a(mouseX, mouseY, par3);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.salary.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.salary.text_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.salary.text_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48, this.guiTop + 66, 0, 180, 80, 20, 512.0f, 512.0f, false);
        this.leaderInput.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 138, this.guiTop + 66, 0, 200, 80, 20, 512.0f, 512.0f, false);
        this.cadreInput.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 228, this.guiTop + 66, 0, 220, 80, 20, 512.0f, 512.0f, false);
        this.employeeInput.func_73795_f();
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.leaderInput.func_73802_a(typedChar, keyCode);
        this.cadreInput.func_73802_a(typedChar, keyCode);
        this.employeeInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBankGUI());
            }
            if (this.validButton.field_73742_g && this.isNumeric(this.leaderInput.func_73781_b()) && this.isNumeric(this.cadreInput.func_73781_b()) && this.isNumeric(this.employeeInput.func_73781_b()) && EnterpriseGui.hasPermission("salary") && mouseX > this.guiLeft + 190 && mouseX < this.guiLeft + 190 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                HashMap<String, Integer> hashMapForPacket = new HashMap<String, Integer>();
                hashMapForPacket.put("leader", Integer.parseInt(this.leaderInput.func_73781_b()));
                hashMapForPacket.put("cadre", Integer.parseInt(this.cadreInput.func_73781_b()));
                hashMapForPacket.put("employee", Integer.parseInt(this.employeeInput.func_73781_b()));
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankSalaryPacket((String)EnterpriseGui.enterpriseInfos.get("name"), hashMapForPacket)));
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBankGUI());
            }
        }
        this.leaderInput.func_73793_a(mouseX, mouseY, mouseButton);
        this.cadreInput.func_73793_a(mouseX, mouseY, mouseButton);
        this.employeeInput.func_73793_a(mouseX, mouseY, mouseButton);
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

