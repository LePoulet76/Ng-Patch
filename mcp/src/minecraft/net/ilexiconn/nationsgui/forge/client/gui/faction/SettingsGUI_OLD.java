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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DisbandConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FlagGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SellCountryConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSellCountryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class SettingsGUI_OLD
extends TabbedFactionGUI_OLD {
    private GuiScrollBarFaction scrollBarLogs;
    private GuiTextField inputDescription;
    private boolean checkboxOpen = (Boolean)FactionGui_OLD.factionInfos.get("isOpen");
    private DynamicTexture flagTexture;
    private GuiButton disbandButton;
    private GuiButton sellButton;
    private boolean saved = false;

    public SettingsGUI_OLD(EntityPlayer player) {
        super(player);
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

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.inputDescription = new GuiTextField(this.field_73886_k, this.guiLeft + 133, this.guiTop + 95, 246, 10);
        this.inputDescription.func_73786_a(false);
        this.inputDescription.func_73804_f(250);
        this.inputDescription.func_73782_a((String)FactionGui_OLD.factionInfos.get("description"));
        this.inputDescription.func_73797_d();
        this.disbandButton = new TexturedCenteredButtonGUI(1, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.func_135053_a((String)"faction.settings.disband_button"));
        this.sellButton = new TexturedCenteredButtonGUI(2, this.guiLeft + 10, this.guiTop + 195, 100, 20, "faction_btn", 0, 0, (Boolean)FactionGui_OLD.factionInfos.get("isForSale") != false ? I18n.func_135053_a((String)"faction.settings.sell_off_button") : I18n.func_135053_a((String)"faction.settings.sell_button"));
        this.scrollBarLogs = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 149, 63);
        if (!((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue()) {
            this.disbandButton.field_73742_g = false;
            this.sellButton.field_73742_g = false;
        }
        if (!((Boolean)FactionGui_OLD.factionInfos.get("canBeSale")).booleanValue()) {
            this.sellButton.field_73742_g = false;
        }
    }

    public void func_73876_c() {
        this.inputDescription.func_73780_a();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClientEventHandler.STYLE.bindTexture("faction_settings");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue()) {
            this.disbandButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            this.sellButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
        if (FactionGui_OLD.factionInfos.get("flagImage") != null && !((String)FactionGui_OLD.factionInfos.get("flagImage")).isEmpty()) {
            BufferedImage image = SettingsGUI_OLD.decodeToImage((String)FactionGui_OLD.factionInfos.get("flagImage"));
            this.flagTexture = new DynamicTexture(image);
            if (this.flagTexture != null) {
                GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 156, this.guiTop + 47, 0.0f, 0.0f, 156, 78, 35, 20, 156.0f, 78.0f, false);
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.edit_flag"), this.guiLeft + 292, this.guiTop + 52, 0xFFFFFF, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.description"), this.guiLeft + 131, this.guiTop + 80, 0x191919, 0.9f, false, false);
        this.inputDescription.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("faction_settings");
        if (!this.checkboxOpen) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 113, 159, 250, 10, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 113, 169, 250, 10, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.open_text"), this.guiLeft + 145, this.guiTop + 114, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.logs"), this.guiLeft + 131, this.guiTop + 136, 0x191919, 0.9f, false, false);
        ArrayList logs = (ArrayList)FactionGui_OLD.factionInfos.get("logs");
        String tooltipToDraw = "";
        GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 146, 246, 70);
        for (int j = 0; j < logs.size(); ++j) {
            int offsetX = this.guiLeft + 131;
            Float offsetY = Float.valueOf((float)(this.guiTop + 146 + j * 20) + this.getSlide());
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 146, 246, 18, 512.0f, 512.0f, false);
            this.drawScaledString(((String)logs.get(j)).split("##")[0] + " " + I18n.func_135053_a((String)("faction.settings.logs." + ((String)logs.get(j)).split("##")[1])).replace("#target#", ((String)logs.get(j)).split("##")[2]), offsetX + 6, offsetY.intValue() + 6, 0xB4B4B4, 0.85f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 232, offsetY.intValue() + 3, 148, 250, 10, 11, 512.0f, 512.0f, false);
            if (mouseX <= offsetX + 232 || mouseX >= offsetX + 232 + 10 || !((float)mouseY > offsetY.floatValue() + 3.0f) || !((float)mouseY < offsetY.floatValue() + 3.0f + 11.0f)) continue;
            tooltipToDraw = ((String)logs.get(j)).split("##")[((String)logs.get(j)).split("##").length - 1];
        }
        GUIUtils.endGLScissor();
        if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 145 && mouseY < this.guiTop + 145 + 72) {
            this.scrollBarLogs.draw(mouseX, mouseY);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.settings.save_button"), this.guiLeft + 334, this.guiTop + 225, 0xFFFFFF, 1.0f, true, false);
        if (!((Boolean)FactionGui_OLD.factionInfos.get("canBeSale")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 195 && mouseY <= this.guiTop + 195 + 20) {
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.settings.cant_sell_1"), I18n.func_135053_a((String)"faction.settings.cant_sell_2"), I18n.func_135053_a((String)"faction.settings.cant_sell_3")), mouseX, mouseY, this.field_73886_k);
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawTooltip(tooltipToDraw, mouseX, mouseY);
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 217 && mouseX <= this.guiLeft + 217 + 150 && mouseY >= this.guiTop + 47 && mouseY <= this.guiTop + 47 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FlagGui(this.player, (String)FactionGui_OLD.factionInfos.get("name")));
            }
            if (!this.saved && mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 221 + 15) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.saved = true;
            }
            if (((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new DisbandConfirmGui(this));
            }
            if (((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 195 && mouseY <= this.guiTop + 195 + 20) {
                if (((Boolean)FactionGui_OLD.factionInfos.get("isForSale")).booleanValue()) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSellCountryPacket((String)FactionGui_OLD.factionInfos.get("id"), -1)));
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)FactionGui_OLD.factionInfos.get("name"), true)));
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGui_OLD((String)FactionGui_OLD.factionInfos.get("name")));
                } else if (((Boolean)FactionGui_OLD.factionInfos.get("canBeSale")).booleanValue()) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new SellCountryConfirmGui(this));
                }
            }
            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 10 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 10) {
                this.checkboxOpen = !this.checkboxOpen;
                FactionGui_OLD.factionInfos.put("isOpen", this.checkboxOpen);
            }
        }
        this.inputDescription.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return ((ArrayList)FactionGui_OLD.factionInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)FactionGui_OLD.factionInfos.get("logs")).size() - 3) * 18) * this.scrollBarLogs.getSliderValue() : 0.0f;
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
        this.inputDescription.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }
}

