/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(value=Side.CLIENT)
public class OpenDiscordConfirmGui
extends ModalGui {
    private GuiButton yesButton;
    private GuiButton noButton;
    private GuiScreen guiFrom;

    public OpenDiscordConfirmGui(GuiScreen guiFrom) {
        super(guiFrom);
        this.guiFrom = guiFrom;
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.yesButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"faction.common.confirm"));
        this.noButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.func_135053_a((String)"faction.common.cancel"));
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float par3) {
        super.func_73863_a(mouseX, mouseY, par3);
        this.drawScaledString(I18n.func_135053_a((String)"faction.modal.title"), this.guiLeft + 53, this.guiTop + 16, 0x191919, 1.3f, false, false);
        int index = 0;
        for (String line : I18n.func_135053_a((String)"faction.discord.confirmation.text").split("##")) {
            this.drawScaledString(line, this.guiLeft + 53, this.guiTop + 35 + index * 15, 0x191919, 0.95f, false, false);
            ++index;
        }
        this.yesButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.noButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
                try {
                    Class<?> desktop = Class.forName("java.awt.Desktop");
                    Object theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    desktop.getMethod("browse", URI.class).invoke(theDesktop, URI.create((String)FactionGUI.factionInfos.get("discord")));
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(this.guiFrom);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
}

