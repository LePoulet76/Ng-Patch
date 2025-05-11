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
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Default_Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnterpriseContractForm_Default_Gui
extends GuiScreen {
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField priceInput;
    private GuiScrollBarFaction scrollBarDelay;
    protected int xSize = 371;
    protected int ySize = 223;
    private int guiLeft;
    private int guiTop;
    private boolean delayExpanded = false;
    private String selectedDelay = "";
    private String hoveredDelay = "";
    private ArrayList<String> delays = new ArrayList();
    private ArrayList<GuiTextField> linesTextField = new ArrayList();

    public void func_73876_c() {
        this.priceInput.func_73780_a();
        for (GuiTextField lineTextField : this.linesTextField) {
            lineTextField.func_73780_a();
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a((String)"enterprise.contract.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a((String)"enterprise.contract.action.valid"));
        this.scrollBarDelay = new GuiScrollBarFaction(this.guiLeft + 357, this.guiTop + 184, 90);
        this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 219, this.guiTop + 166, 58, 10);
        this.priceInput.func_73786_a(false);
        this.priceInput.func_73804_f(7);
        this.priceInput.func_73782_a("0");
        this.delays.addAll(Arrays.asList("3h", "6h", "12h", "1j", "2j", "3j", "5j", "1s", "2s", "3s", "1m"));
        this.selectedDelay = this.delays.get(0);
        for (int i = 0; i < 8; ++i) {
            GuiTextField lineTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 200, this.guiTop + 65 + i * 10, 160, 10);
            lineTextField.func_73786_a(false);
            lineTextField.func_73804_f(32);
            lineTextField.func_73797_d();
            this.linesTextField.add(lineTextField);
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.hoveredDelay = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTexture((int)((double)(this.guiLeft + 91) - (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"enterprise.contract.title.services")) * 1.2 / 2.0 - 8.0 - 2.0), this.guiTop + 16, 0, 276, 16, 16, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 0xFFFFFF, 1.2f, true, false);
        int index = 0;
        for (String serviceLine : ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##")) {
            this.drawScaledString(serviceLine.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 0xFFFFFF, 0.8f, false, false);
            ++index;
        }
        this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 0xFFFFFF, 1.7f, true, true);
        String type = I18n.func_135053_a((String)("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()));
        ClientEventHandler.STYLE.bindTexture("enterprise_main");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 280 - this.field_73886_k.func_78256_a(type) / 2 - 7 - 4, this.guiTop + 23, EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase())), this.guiLeft + 280 + 7, this.guiTop + 29, 0xB4B4B4, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.demand"), this.guiLeft + 197, this.guiTop + 53, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 62, 165, 85);
        for (GuiTextField lineTextField : this.linesTextField) {
            lineTextField.func_73795_f();
        }
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.price"), this.guiLeft + 197, this.guiTop + 151, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 197, this.guiTop + 160, 0, 234, 80, 20, 512.0f, 512.0f, false);
        this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 0xFFFFFF, 1.3f, true, false);
        this.priceInput.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.delay"), this.guiLeft + 282, this.guiTop + 151, 0x191919, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 282, this.guiTop + 160, 0, 255, 80, 20, 512.0f, 512.0f, false);
        if (this.selectedDelay != null) {
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.contract.delay." + this.selectedDelay)), this.guiLeft + 285, this.guiTop + 166, 0xFFFFFF, 1.0f, false, false);
        }
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (this.delayExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 282, this.guiTop + 179, 247, 234, 80, 99, 512.0f, 512.0f, false);
            GUIUtils.startGLScissor(this.guiLeft + 283, this.guiTop + 180, 74, 97);
            for (int i = 0; i < this.delays.size(); ++i) {
                int offsetX = this.guiLeft + 283;
                Float offsetY = Float.valueOf((float)(this.guiTop + 180 + i * 20) + this.getSlideDelay());
                ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 248, 235, 74, 20, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("enterprise.contract.delay." + this.delays.get(i))), offsetX + 2, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                if (mouseX <= offsetX || mouseX >= offsetX + 74 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                this.hoveredDelay = this.delays.get(i);
            }
            GUIUtils.endGLScissor();
            this.scrollBarDelay.draw(mouseX, mouseY);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.priceInput.func_73802_a(typedChar, keyCode);
        for (GuiTextField lineTextField : this.linesTextField) {
            lineTextField.func_73802_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    private float getSlideDelay() {
        return this.delays.size() > 5 ? (float)(-(this.delays.size() - 5) * 20) * this.scrollBarDelay.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.delayExpanded && mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
            }
            if (this.validButton.field_73742_g && !this.delayExpanded && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                String description = "";
                for (GuiTextField lineTextField : this.linesTextField) {
                    description = description + " " + lineTextField.func_73781_b();
                }
                description = description.trim();
                Long deadlineTime = System.currentTimeMillis();
                switch (this.selectedDelay) {
                    case "3h": {
                        deadlineTime = deadlineTime + 10800000L;
                        break;
                    }
                    case "6h": {
                        deadlineTime = deadlineTime + 21600000L;
                        break;
                    }
                    case "12h": {
                        deadlineTime = deadlineTime + 43200000L;
                        break;
                    }
                    case "1j": {
                        deadlineTime = deadlineTime + 86400000L;
                        break;
                    }
                    case "2j": {
                        deadlineTime = deadlineTime + 172800000L;
                        break;
                    }
                    case "3j": {
                        deadlineTime = deadlineTime + 259200000L;
                        break;
                    }
                    case "5j": {
                        deadlineTime = deadlineTime + 432000000L;
                        break;
                    }
                    case "1s": {
                        deadlineTime = deadlineTime + 604800000L;
                        break;
                    }
                    case "2s": {
                        deadlineTime = deadlineTime + 1209600000L;
                        break;
                    }
                    case "3s": {
                        deadlineTime = deadlineTime + 1814400000L;
                        break;
                    }
                    case "1m": {
                        deadlineTime = deadlineTime + 2592000000L;
                    }
                }
                EnterpriseGui.lastContractDemand = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Default_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), description, Integer.parseInt(this.priceInput.func_73781_b()), deadlineTime)));
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (mouseX > this.guiLeft + 343 && mouseX < this.guiLeft + 343 + 19 && mouseY > this.guiTop + 160 && mouseY < this.guiTop + 160 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                boolean bl = this.delayExpanded = !this.delayExpanded;
            }
            if (this.delayExpanded && this.hoveredDelay != null && !this.hoveredDelay.isEmpty()) {
                this.selectedDelay = this.hoveredDelay;
                this.hoveredDelay = "";
                this.delayExpanded = false;
            }
        }
        this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
        for (GuiTextField lineTextField : this.linesTextField) {
            lineTextField.func_73793_a(mouseX, mouseY, mouseButton);
        }
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

