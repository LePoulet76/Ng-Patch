/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnemyGui
extends GuiScreen {
    private String attFaction;
    private String defFaction;
    protected int xSize = 217;
    protected int ySize = 210;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    boolean reasonExpanded = false;
    private String selectedReason = "";
    private String hoveredReason = "";
    private List<String> availableReasons = Arrays.asList("kill_wilderness", "provocation", "territorial_expansion", "treason", "intrusion", "scam", "tpkill", "independence", "colony_refusal", "colony_protection", "buffer_country", "under_power", "reprisal", "500mmr", "follow_war", "empire", "war_revenge", "bombing_asssistance", "treaty", "colony_steal");
    private List<String> reasonsAuto = Arrays.asList("kill_wilderness", "tpkill", "independence", "colony_refusal", "colony_protection", "under_power", "reprisal", "500mmr", "empire", "war_revenge", "colony_steal");

    public EnemyGui(String attFaction, String defFaction) {
        this.attFaction = attFaction;
        this.defFaction = defFaction;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 202, this.guiTop + 117, 71);
        this.selectedReason = "kill_wilderness";
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        BufferedImage image;
        this.hoveredReason = "";
        this.func_73873_v_();
        Object tooltipToDraw = null;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_enemy");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 190), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 190)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.title"), this.guiLeft + 12, this.guiTop + 190, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_enemy");
        if (mouseX >= this.guiLeft + 206 && mouseX <= this.guiLeft + 206 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 206, this.guiTop - 6, 63, 223, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 206, this.guiTop - 6, 63, 213, 9, 10, 512.0f, 512.0f, false);
        }
        if (!ClientProxy.flagsTexture.containsKey(this.attFaction)) {
            if (!ClientProxy.base64FlagsByFactionName.containsKey(this.attFaction)) {
                ClientProxy.base64FlagsByFactionName.put(this.attFaction, "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(this.attFaction)));
            } else if (ClientProxy.base64FlagsByFactionName.containsKey(this.attFaction) && !ClientProxy.base64FlagsByFactionName.get(this.attFaction).isEmpty()) {
                image = ClientProxy.decodeToImage(ClientProxy.base64FlagsByFactionName.get(this.attFaction));
                ClientProxy.flagsTexture.put(this.attFaction, new DynamicTexture(image));
            }
        }
        if (!ClientProxy.flagsTexture.containsKey(this.defFaction)) {
            if (!ClientProxy.base64FlagsByFactionName.containsKey(this.defFaction)) {
                ClientProxy.base64FlagsByFactionName.put(this.defFaction, "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(this.defFaction)));
            } else if (ClientProxy.base64FlagsByFactionName.containsKey(this.defFaction) && !ClientProxy.base64FlagsByFactionName.get(this.defFaction).isEmpty()) {
                image = ClientProxy.decodeToImage(ClientProxy.base64FlagsByFactionName.get(this.defFaction));
                ClientProxy.flagsTexture.put(this.defFaction, new DynamicTexture(image));
            }
        }
        if (ClientProxy.flagsTexture.containsKey(this.attFaction)) {
            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.attFaction).func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 63, this.guiTop + 34, 0.0f, 0.0f, 156, 78, 27, 15, 156.0f, 78.0f, false);
        }
        if (ClientProxy.flagsTexture.containsKey(this.defFaction)) {
            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.defFaction).func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 162, this.guiTop + 34, 0.0f, 0.0f, 156, 78, 27, 15, 156.0f, 78.0f, false);
        }
        if (this.attFaction.contains("Empire")) {
            this.drawScaledString("Empire", this.guiLeft + 76, this.guiTop + 53, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString(this.attFaction.replace("Empire", ""), this.guiLeft + 76, this.guiTop + 63, 0xFFFFFF, 1.0f, true, false);
        } else {
            this.drawScaledString(this.attFaction, this.guiLeft + 76, this.guiTop + 53, 0xFFFFFF, 1.0f, true, false);
        }
        if (this.defFaction.contains("Empire")) {
            this.drawScaledString("\u00a7cEmpire", this.guiLeft + 175, this.guiTop + 53, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString("\u00a7c" + this.defFaction.replace("Empire", ""), this.guiLeft + 175, this.guiTop + 63, 0xFFFFFF, 1.0f, true, false);
        } else {
            this.drawScaledString("\u00a7c" + this.defFaction, this.guiLeft + 175, this.guiTop + 53, 0xFFFFFF, 1.0f, true, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.reason"), this.guiLeft + 45, this.guiTop + 86, 0, 1.0f, false, false);
        if (!this.selectedReason.isEmpty()) {
            String reason = I18n.func_135053_a((String)("faction.enemy.reason." + this.selectedReason));
            if (reason.length() > 25) {
                reason = reason.substring(0, 25) + "..";
            }
            this.drawScaledString(reason, this.guiLeft + 48, this.guiTop + 102, 0xFFFFFF, 1.0f, false, false);
        }
        if (!this.selectedReason.isEmpty()) {
            String[] descriptionWords = I18n.func_135053_a((String)("faction.enemy.reason.desc." + this.selectedReason)).split(" ");
            String line = "";
            int lineNumber = 0;
            for (String descWord : descriptionWords) {
                StringBuilder stringBuilder = new StringBuilder();
                if ((double)this.field_73886_k.func_78256_a(stringBuilder.append(line).append(descWord).toString()) * 0.9 <= 152.0) {
                    if (!line.equals("")) {
                        line = line + " ";
                    }
                    line = line + descWord;
                    continue;
                }
                this.drawScaledString(line, this.guiLeft + 49, this.guiTop + 120 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                ++lineNumber;
                line = descWord;
            }
            this.drawScaledString(line, this.guiLeft + 49, this.guiTop + 120 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
            if (this.reasonsAuto.contains(this.selectedReason)) {
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.reason.auto"), this.guiLeft + 49, this.guiTop + 170, 0xFFFFFF, 0.9f, false, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.reason.forum"), this.guiLeft + 49, this.guiTop + 170, 0xFFFFFF, 0.9f, false, false);
            }
        }
        if (!this.reasonExpanded && mouseX >= this.guiLeft + 45 && mouseX <= this.guiLeft + 45 + 162 && mouseY >= this.guiTop + 182 && mouseY <= this.guiTop + 182 + 15) {
            ClientEventHandler.STYLE.bindTexture("faction_enemy");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 45, this.guiTop + 182, 0, 249, 162, 15, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.send_request"), this.guiLeft + 126, this.guiTop + 185, 0xFFFFFF, 1.0f, true, false);
        if (this.reasonExpanded) {
            ClientEventHandler.STYLE.bindTexture("faction_enemy");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 45, this.guiTop + 114, 0, 285, 162, 77, 512.0f, 512.0f, false);
            GUIUtils.startGLScissor(this.guiLeft + 46, this.guiTop + 115, 156, 75);
            for (int i = 0; i < this.availableReasons.size(); ++i) {
                int offsetX = this.guiLeft + 46;
                Float offsetY = Float.valueOf((float)(this.guiTop + 115 + i * 19) + this.getSlideReasons());
                ClientEventHandler.STYLE.bindTexture("faction_enemy");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 1, 286, 156, 19, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.reason." + this.availableReasons.get(i))), this.guiLeft + 48, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                if (mouseX < offsetX || mouseX > offsetX + 156 || !((float)mouseY >= offsetY.floatValue()) || !((float)mouseY <= offsetY.floatValue() + 19.0f)) continue;
                this.hoveredReason = this.availableReasons.get(i);
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideReasons() {
        return this.availableReasons.size() > 4 ? (float)(-(this.availableReasons.size() - 4) * 19) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 45 && mouseX <= this.guiLeft + 45 + 162 && mouseY >= this.guiTop + 95 && mouseY <= this.guiTop + 95 + 20) {
                boolean bl = this.reasonExpanded = !this.reasonExpanded;
            }
            if (mouseX > this.guiLeft + 206 && mouseX < this.guiLeft + 206 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(null);
            }
            if (this.hoveredReason != null && !this.hoveredReason.isEmpty()) {
                this.selectedReason = this.hoveredReason;
                this.hoveredReason = "";
                this.reasonExpanded = false;
            }
            if (!this.reasonExpanded && !this.selectedReason.isEmpty() && mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 162 && mouseY > this.guiTop + 182 && mouseY < this.guiTop + 182 + 15) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyCreatePacket(this.attFaction, this.defFaction, this.selectedReason)));
                this.field_73882_e.func_71373_a((GuiScreen)new WarRequestGUI(-1, null));
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

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }
}

