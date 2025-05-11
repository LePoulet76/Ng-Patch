/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseDisbandConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseFlagGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseSaveSettingsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EnterpriseSettingsGUI
extends TabbedEnterpriseGUI {
    private GuiScrollBarFaction scrollBarLogs;
    private GuiTextField inputDescription;
    private boolean checkboxOpen;
    private DynamicTexture flagTexture;
    private GuiButton disbandButton;
    private boolean saved = false;
    private boolean servicesEdit = false;
    private ArrayList<GuiTextField> linesTextField = new ArrayList();

    public EnterpriseSettingsGUI() {
        this.checkboxOpen = (Boolean)EnterpriseGui.enterpriseInfos.get("isOpen");
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.inputDescription = new GuiTextField(this.field_73886_k, this.guiLeft + 133, this.guiTop + 95, 246, 10);
        this.inputDescription.func_73786_a(false);
        this.inputDescription.func_73804_f(250);
        this.inputDescription.func_73782_a((String)EnterpriseGui.enterpriseInfos.get("description"));
        this.inputDescription.func_73797_d();
        List<String> services = Arrays.asList(((String)EnterpriseGui.enterpriseInfos.get("services")).split("##"));
        for (int i = 0; i < 18; ++i) {
            GuiTextField lineTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 131, this.guiTop + 37 + i * 10, 246, 10);
            lineTextField.func_73786_a(false);
            lineTextField.func_73804_f(41);
            if (i < services.size()) {
                lineTextField.func_73782_a(services.get(i));
            }
            lineTextField.func_73797_d();
            this.linesTextField.add(lineTextField);
        }
        this.disbandButton = new TexturedCenteredButtonGUI(1, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.func_135053_a((String)"enterprise.settings.close_button"));
        this.scrollBarLogs = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 149, 63);
        if (!EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader")) {
            this.disbandButton.field_73742_g = false;
        }
    }

    public void func_73876_c() {
        if (!this.servicesEdit) {
            this.inputDescription.func_73780_a();
        } else {
            for (GuiTextField lineTextField : this.linesTextField) {
                lineTextField.func_73780_a();
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClientEventHandler.STYLE.bindTexture("enterprise_settings");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader")) {
            this.disbandButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        }
        String tooltipToDraw = "";
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
        if (!this.servicesEdit) {
            if (this.flagTexture == null && EnterpriseGui.enterpriseInfos.get("flagImage") != null && !((String)EnterpriseGui.enterpriseInfos.get("flagImage")).isEmpty()) {
                BufferedImage image = EnterpriseSettingsGUI.decodeToImage((String)EnterpriseGui.enterpriseInfos.get("flagImage"));
                this.flagTexture = new DynamicTexture(image);
            }
            if (this.flagTexture != null) {
                GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 156, this.guiTop + 40, 0.0f, 0.0f, 150, 150, 32, 32, 150.0f, 150.0f, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.settings.edit_flag"), this.guiLeft + 292, this.guiTop + 52, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.settings.description"), this.guiLeft + 131, this.guiTop + 80, 0x191919, 0.9f, false, false);
            this.inputDescription.func_73795_f();
            ClientEventHandler.STYLE.bindTexture("enterprise_settings");
            if (this.checkboxOpen) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 113, 159, 250, 10, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 113, 169, 250, 10, 10, 512.0f, 512.0f, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.settings.open_text"), this.guiLeft + 145, this.guiTop + 114, 0x191919, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.settings.logs"), this.guiLeft + 131, this.guiTop + 136, 0x191919, 0.9f, false, false);
            ArrayList logs = (ArrayList)EnterpriseGui.enterpriseInfos.get("logs");
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 146, 246, 70);
            for (int j = 0; j < logs.size(); ++j) {
                int offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 146 + j * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("enterprise_settings");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 146, 246, 18, 512.0f, 512.0f, false);
                this.drawScaledString(((String)logs.get(j)).split("##")[2] + " " + I18n.func_135053_a((String)("enterprise.settings.logs." + ((String)logs.get(j)).split("##")[1])).replace("#target#", ((String)logs.get(j)).split("##")[3]), offsetX + 6, offsetY.intValue() + 6, 0xB4B4B4, 0.85f, false, false);
                ClientEventHandler.STYLE.bindTexture("enterprise_settings");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 232, offsetY.intValue() + 3, 148, 250, 10, 11, 512.0f, 512.0f, false);
                if (mouseX <= offsetX + 232 || mouseX >= offsetX + 232 + 10 || !((float)mouseY > offsetY.floatValue() + 3.0f) || !((float)mouseY < offsetY.floatValue() + 3.0f + 11.0f)) continue;
                tooltipToDraw = ((String)logs.get(j)).split("##")[0];
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 145 && mouseY < this.guiTop + 145 + 72) {
                this.scrollBarLogs.draw(mouseX, mouseY);
            }
        } else {
            ClientEventHandler.STYLE.bindTexture("enterprise_settings");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 36, 223, 250, 254, 181, 512.0f, 512.0f, false);
            for (GuiTextField lineTextField : this.linesTextField) {
                lineTextField.func_73795_f();
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.settings.services_button"), this.guiLeft + 184, this.guiTop + 225, 0xFFFFFF, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.save_button"), this.guiLeft + 334, this.guiTop + 225, 0xFFFFFF, 1.0f, true, false);
        if (!tooltipToDraw.isEmpty()) {
            this.drawTooltip(tooltipToDraw, mouseX, mouseY);
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.servicesEdit && mouseX >= this.guiLeft + 217 && mouseX <= this.guiLeft + 217 + 150 && mouseY >= this.guiTop + 47 && mouseY <= this.guiTop + 47 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseFlagGui((String)EnterpriseGui.enterpriseInfos.get("name")));
            }
            if (!this.saved && mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 221 + 15) {
                String services = "";
                for (GuiTextField lineTextField : this.linesTextField) {
                    services = services + "##" + lineTextField.func_73781_b();
                }
                services = services.replaceAll("^##", "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseSaveSettingsDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.inputDescription.func_73781_b(), this.checkboxOpen, services)));
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.saved = true;
            }
            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 100 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 221 + 15) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                boolean bl = this.servicesEdit = !this.servicesEdit;
            }
            if (EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseDisbandConfirmGui(this));
            }
            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 10 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 10) {
                this.checkboxOpen = !this.checkboxOpen;
                EnterpriseGui.enterpriseInfos.put("isOpen", this.checkboxOpen);
            }
        }
        if (!this.servicesEdit) {
            this.inputDescription.func_73793_a(mouseX, mouseY, mouseButton);
        } else {
            for (GuiTextField lineTextField : this.linesTextField) {
                lineTextField.func_73793_a(mouseX, mouseY, mouseButton);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return ((ArrayList)EnterpriseGui.enterpriseInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)EnterpriseGui.enterpriseInfos.get("logs")).size() - 3) * 18) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String time, int mouseX, int mouseY) {
        int mouseXGui = mouseX;
        int mouseYGui = mouseY;
        String date = "\u00a77";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = diff / 3600000L;
            if (hours == 0L) {
                minutes = diff / 60000L;
                if (minutes == 0L) {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds");
                } else {
                    date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes");
                }
            } else {
                date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
            }
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
        }
        this.drawHoveringText(Arrays.asList(date), mouseXGui, mouseYGui, this.field_73886_k);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (!this.servicesEdit) {
            this.inputDescription.func_73802_a(typedChar, keyCode);
        } else {
            for (GuiTextField lineTextField : this.linesTextField) {
                lineTextField.func_73802_a(typedChar, keyCode);
            }
        }
        super.func_73869_a(typedChar, keyCode);
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

