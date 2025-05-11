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
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankBonusPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

@SideOnly(value=Side.CLIENT)
public class EnterpriseBonusGui
extends ModalGui {
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField bonusInput;
    private GuiScrollBarFaction scrollBar;
    private boolean membersExpanded = false;
    private String selectedMember = "";
    private String hoveredMember = "";
    private ArrayList<String> members = new ArrayList();

    public EnterpriseBonusGui(GuiScreen guiFrom) {
        super(guiFrom);
    }

    public void func_73876_c() {
        this.bonusInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 184, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"enterprise.bank.modal.action.valid"));
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 170, this.guiTop + 86, 90);
        if (!EnterpriseGui.hasPermission("salary")) {
            this.validButton.field_73742_g = false;
        }
        this.bonusInput = new GuiTextField(this.field_73886_k, this.guiLeft + 184, this.guiTop + 68, 97, 10);
        this.bonusInput.func_73786_a(false);
        this.bonusInput.func_73804_f(7);
        this.bonusInput.func_73782_a("0");
        if (EnterpriseGui.enterpriseInfos != null) {
            this.members.addAll((ArrayList)EnterpriseGui.enterpriseInfos.get("players_online"));
            this.members.addAll((ArrayList)EnterpriseGui.enterpriseInfos.get("players_offline"));
            if (this.members.size() > 0) {
                this.selectedMember = this.members.get(0);
            }
        }
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        super.func_73863_a(mouseX, mouseY, par3);
        this.hoveredMember = "";
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.bonus.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.bonus.text_1"), this.guiLeft + 53, this.guiTop + 30, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.modal.bonus.text_2"), this.guiLeft + 53, this.guiTop + 40, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 180, this.guiTop + 62, 0, 242, 122, 20, 512.0f, 512.0f, false);
        this.drawScaledString("\u00a7a$", this.guiLeft + 294, this.guiTop + 68, 0xFFFFFF, 1.3f, true, false);
        this.bonusInput.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 62, 0, 242, 122, 20, 512.0f, 512.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 156, this.guiTop + 62, 123, 242, 19, 20, 512.0f, 512.0f, false);
        if (this.selectedMember != null) {
            this.drawScaledString(this.selectedMember.split("#")[1], this.guiLeft + 56, this.guiTop + 68, 0xFFFFFF, 1.0f, false, false);
        }
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (this.membersExpanded) {
            ClientEventHandler.STYLE.bindTexture("faction_modal");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 53, this.guiTop + 81, 0, 264, 122, 99, 512.0f, 512.0f, false);
            GUIUtils.startGLScissor(this.guiLeft + 54, this.guiTop + 82, 116, 97);
            for (int i = 0; i < this.members.size(); ++i) {
                int offsetX = this.guiLeft + 54;
                Float offsetY = Float.valueOf((float)(this.guiTop + 82 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("faction_modal");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 1, 265, 116, 20, 512.0f, 512.0f, false);
                this.drawScaledString(this.members.get(i).split("#")[1], offsetX + 2, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_modal");
                if (mouseX <= offsetX || mouseX >= offsetX + 116 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                this.hoveredMember = this.members.get(i);
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.bonusInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    private float getSlide() {
        return this.members.size() > 5 ? (float)(-(this.members.size() - 5) * 20) * this.scrollBar.getSliderValue() : 0.0f;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.membersExpanded && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBankGUI());
            }
            if (this.validButton.field_73742_g && !this.membersExpanded && this.isNumeric(this.bonusInput.func_73781_b()) && EnterpriseGui.hasPermission("salary") && mouseX > this.guiLeft + 184 && mouseX < this.guiLeft + 184 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankBonusPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.selectedMember.replaceAll("\\d+#\\**\\s?", ""), Integer.parseInt(this.bonusInput.func_73781_b()))));
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBankGUI());
            }
            if (mouseX > this.guiLeft + 156 && mouseX < this.guiLeft + 156 + 19 && mouseY > this.guiTop + 62 && mouseY < this.guiTop + 62 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                boolean bl = this.membersExpanded = !this.membersExpanded;
            }
            if (this.membersExpanded && this.hoveredMember != null && !this.hoveredMember.isEmpty()) {
                this.selectedMember = this.hoveredMember;
                this.hoveredMember = "";
                this.membersExpanded = false;
            }
        }
        this.bonusInput.func_73793_a(mouseX, mouseY, mouseButton);
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

