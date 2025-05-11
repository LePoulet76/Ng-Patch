/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NotificationActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class NotificationActionsGUI
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public int xSize = 332;
    public int ySize = 109;
    public int guiLeft;
    public int guiTop;
    public String hoveredAction;
    public List<String> tooltipToDraw = new ArrayList<String>();
    private Notification notification;

    public NotificationActionsGUI(Notification notification) {
        this.notification = notification;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = this.field_73881_g - 170;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
            if (this.hoveredAction.equals("cancel") && this.notification.getActions() != null && this.notification.getActions().func_74764_b("deny")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NotificationActionPacket(this.notification.getActions().func_74775_l("deny").func_74779_i("id"), this.notification.getActions().func_74775_l("deny").func_74779_i("args"))));
            } else if (this.hoveredAction.equals("validate") && this.notification.getActions() != null && this.notification.getActions().func_74764_b("allow")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NotificationActionPacket(this.notification.getActions().func_74775_l("allow").func_74779_i("id"), this.notification.getActions().func_74775_l("allow").func_74779_i("args"))));
            }
            this.notification.setActionDone(true);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.hoveredAction = "";
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 221 * GUI_SCALE, 686 * GUI_SCALE, 830 * GUI_SCALE, 275 * GUI_SCALE, this.xSize, this.ySize, 1920 * GUI_SCALE, 1033 * GUI_SCALE, false);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 0.5f, (float)this.guiTop + 3.5f, 126 * this.notification.getColor().ordinal() * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 50, 49, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 0.5f, (float)this.guiTop + 3.5f, 126 * (this.notification.getIcon().ordinal() % 15) * GenericOverride.GUI_SCALE, (381 + this.notification.getIcon().ordinal() / 15 * 123) * GenericOverride.GUI_SCALE, 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 50, 49, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(this.notification.getTitle().toUpperCase(), this.guiLeft + 57, this.guiTop + 20, -1314054, 0.9f, "left", true, "minecraftDungeons", 23);
        ModernGui.drawSectionStringCustomFont(this.notification.getContent(), this.guiLeft + 57, this.guiTop + 45, 15463162, 0.5f, "left", false, "georamaSemiBold", 30, 9, 500);
        ModernGui.bindTextureOverlayMain();
        if (mouseX > this.guiLeft + this.xSize - 160 && mouseX < this.guiLeft + this.xSize - 160 + 74 && mouseY > this.guiTop + this.ySize - 20 && mouseY < this.guiTop + this.ySize - 20 + 14) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + this.xSize - 160, this.guiTop + this.ySize - 20, 1734 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            this.hoveredAction = "cancel";
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().func_74775_l("deny").func_74779_i("translatedTitle"), this.guiLeft + this.xSize - 160 + 37, this.guiTop + this.ySize - 20 + 3, 0x6E76EE, 0.5f, "center", false, "georamaSemiBold", 30, 9, 500);
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + this.xSize - 160, this.guiTop + this.ySize - 20, 1734 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().func_74775_l("deny").func_74779_i("translatedTitle"), this.guiLeft + this.xSize - 160 + 37, this.guiTop + this.ySize - 20 + 3, 15463162, 0.5f, "center", false, "georamaSemiBold", 30, 9, 500);
        }
        ModernGui.bindTextureOverlayMain();
        if (mouseX > this.guiLeft + this.xSize - 80 && mouseX < this.guiLeft + this.xSize - 80 + 74 && mouseY > this.guiTop + this.ySize - 20 && mouseY < this.guiTop + this.ySize - 20 + 14) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + this.xSize - 80, this.guiTop + this.ySize - 20, 1734 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            this.hoveredAction = "validate";
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().func_74775_l("allow").func_74779_i("translatedTitle"), this.guiLeft + this.xSize - 80 + 37, this.guiTop + this.ySize - 20 + 3, 0x6E76EE, 0.5f, "center", false, "georamaSemiBold", 30, 9, 500);
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + this.xSize - 80, this.guiTop + this.ySize - 20, 1734 * GenericOverride.GUI_SCALE, 0 * GenericOverride.GUI_SCALE, 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().func_74775_l("allow").func_74779_i("translatedTitle"), this.guiLeft + this.xSize - 80 + 37, this.guiTop + this.ySize - 20 + 3, 15463162, 0.5f, "center", false, "georamaSemiBold", 30, 9, 500);
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }
}

