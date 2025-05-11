/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ActionsListGui
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 234;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public String hoveredCountry;
    public static ArrayList<HashMap<String, String>> actions = new ArrayList();

    public ActionsListGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionActionsListDataPacket((String)FactionGUI.factionInfos.get("name"))));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 296, this.guiTop + 64, 150);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_actions_list");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 247, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 237, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.title") + " " + FactionGUI.factionInfos.get("name"), this.guiLeft + 50, this.guiTop + 20, 0x191919, 1.0f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.country"), this.guiLeft + 51 + 5, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.countActions_1"), this.guiLeft + 51 + 80, this.guiTop + 40, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.countActions_2"), this.guiLeft + 51 + 80, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.dividendes_1"), this.guiLeft + 51 + 130, this.guiTop + 40, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.dividendes_2"), this.guiLeft + 51 + 130, this.guiTop + 50, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.value_1"), this.guiLeft + 51 + 195, this.guiTop + 40, 0x191919, 0.9f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.actions_list.value_2"), this.guiLeft + 51 + 195, this.guiTop + 50, 0x191919, 0.9f, false, false);
        if (loaded && actions.size() > 0) {
            this.hoveredCountry = "";
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);
            for (int i = 0; i < actions.size(); ++i) {
                int offsetX = this.guiLeft + 51;
                Float offsetY = Float.valueOf((float)(this.guiTop + 60 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("faction_actions_list");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 51, 60, 245, 20, 512.0f, 512.0f, false);
                this.drawScaledString(actions.get(i).get("name").length() < 14 ? actions.get(i).get("name") : actions.get(i).get("name").substring(0, 13) + "..", offsetX + 5, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(actions.get(i).get("countActions"), offsetX + 80, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(actions.get(i).get("dividendes"), offsetX + 130, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(actions.get(i).get("value") + "$", offsetX + 195, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                if (mouseX <= this.guiLeft + 51 || mouseX >= this.guiLeft + 51 + 245 || mouseY <= this.guiTop + 60 || mouseY >= this.guiTop + 60 + 158 || mouseX <= offsetX || mouseX >= offsetX + 245 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                this.hoveredCountry = actions.get(i).get("name");
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlide() {
        return actions.size() > 8 ? (float)(-(actions.size() - 8) * 20) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(null);
            }
            if (this.hoveredCountry != null && !this.hoveredCountry.isEmpty()) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.hoveredCountry, true)));
                FactionGUI.loaded = false;
                this.field_73882_e.func_71373_a((GuiScreen)new BankGUI());
            }
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

    public boolean func_73868_f() {
        return false;
    }
}

