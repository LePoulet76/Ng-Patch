/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerStatsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class StatsGUI
extends GuiScreen {
    protected int xSize = 299;
    protected int ySize = 217;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, String> statsInfos = new HashMap();
    private String targetName;

    public StatsGUI(String targetName) {
        this.targetName = targetName;
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerStatsDataPacket(this.targetName)));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        ClientEventHandler.STYLE.bindTexture("player_stats");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 260 && mouseX <= this.guiLeft + 260 + 9 && mouseY >= this.guiTop - 4 && mouseY <= this.guiTop - 4 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 260, this.guiTop - 4, 0, 229, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 260, this.guiTop - 4, 0, 219, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.stats.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 0x191919, 1.2f, false, false);
        ClientEventHandler.STYLE.bindTexture("player_stats");
        if (loaded && statsInfos != null && statsInfos.size() > 0) {
            int i = 0;
            for (Map.Entry<String, String> pair : statsInfos.entrySet()) {
                if (pair == null) continue;
                int offsetY = this.guiTop + 37 + 16 * i;
                int offsetX = this.guiLeft + 56;
                this.drawScaledString(I18n.func_135053_a((String)("faction.stats.type." + pair.getKey().toString().toLowerCase())), offsetX, offsetY, 0xB4B4B4, 1.0f, false, false);
                if (pair.getValue() != null) {
                    long diff;
                    String date;
                    String value = pair.getValue().toString();
                    if (pair.getKey().toString().equals("PLAYTIME")) {
                        date = "";
                        diff = Long.parseLong(pair.getValue().toString()) * 1000L;
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
                        value = date;
                    } else if (pair.getKey().toString().equals("PLAYTIME_INTERSERV")) {
                        date = "";
                        diff = Long.parseLong(pair.getValue().toString()) * 1000L;
                        value = diff < 18000000L ? "< 5h" : (diff < 36000000L ? "5-10h" : (diff < 54000000L ? "10-15h" : (diff < 90000000L ? "15-25h" : (diff < 180000000L ? "25-50h" : (diff < 360000000L ? "50-100h" : (diff < 691200000L ? "4-8" + I18n.func_135053_a((String)"faction.common.days.short") : (diff < 1728000000L ? "8-20" + I18n.func_135053_a((String)"faction.common.days.short") : (diff < 3456000000L ? "20-40" + I18n.func_135053_a((String)"faction.common.days.short") : (diff < 5184000000L ? "40-60" + I18n.func_135053_a((String)"faction.common.days.short") : (diff < 10368000000L ? "60-120" + I18n.func_135053_a((String)"faction.common.days.short") : (diff < 17280000000L ? "120-200" + I18n.func_135053_a((String)"faction.common.days.short") : "> 200" + I18n.func_135053_a((String)"faction.common.days.short"))))))))))));
                    }
                    this.drawScaledString(value, offsetX + 193 - this.field_73886_k.func_78256_a(value), offsetY, 0xFFFFFF, 1.0f, false, false);
                }
                ++i;
            }
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 9 && mouseY > this.guiTop - 4 && mouseY < this.guiTop - 4 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.targetName, ""));
        }
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

