/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TVGStreamersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TVGStreamersSetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class TVGStreamersGui
extends GuiScreen {
    protected int xSize = 463;
    protected int ySize = 232;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = true;
    private RenderItem itemRenderer = new RenderItem();
    public static Integer playerPosition = 0;
    public static Long playerTime = 0L;
    public static Integer totalCagnotte = 0;
    public static String hoveredStreamer = "";
    public static String selectedStreamer = "";
    public static String playerStreamer = "";
    public static HashMap<String, String> streamerData = new HashMap();
    public static boolean hoverPageSwitch = false;
    public static final HashMap<String, ResourceLocation> streamersLogo = new HashMap();
    public static final List<String> streamersName = Arrays.asList("2old4Stream", "17Blazx", "Akytio", "Alvaena", "Anthox", "Areliann", "Bichard", "Billiechou", "Chipsette", "DiscoverID", "Emmashtream", "French Hardware", "Guillaume", "Hanny017tv", "Hiuuugs", "iMeelk", "JulietteArz", "Karoviper", "KawaVanille", "Kinstaar", "Kitty_R6", "Krolay", "Krysthal", "KyriaTV", "Lemed", "Lilith", "Lriaa", "Lucyd", "LyeGaia", "Nensha", "Packam", "Pandaman", "Phyziik", "Pollynette", "PoppieLala", "Prophecy", "Raww", "Remx", "Seroths", "Sheppard", "Shynouh", "Skartiz", "Sludeina", "SoDlire", "SpaceKaeru", "Thomacky", "Teuf", "Toonz", "test");
    public Integer page = 1;
    public static ResourceLocation unknowLogo = new ResourceLocation("nationsgui", "textures/tvg_streamers/inconnu.png");

    public TVGStreamersGui() {
        selectedStreamer = "";
        loaded = false;
        streamerData = new HashMap();
        for (String streamerName : streamersName) {
            streamersLogo.put(streamerName, new ResourceLocation("nationsgui", "textures/tvg_streamers/" + streamerName.replaceAll(" ", "_").toLowerCase() + ".png"));
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TVGStreamersDataPacket("")));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        hoverPageSwitch = false;
        hoveredStreamer = "";
        List<Object> tooltipToDraw = new ArrayList();
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 435 && mouseX <= this.guiLeft + 435 + 18 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 18) {
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 435, this.guiTop + 13, 353, 245, 18, 18, 512.0f, 512.0f, false);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.title"), this.guiLeft + 14, this.guiTop + 12, 0xFFFFFF, 1.0f, "left", false, "georamaExtraBold", 22);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.subtitle"), this.guiLeft + 14, this.guiTop + 24, 13513553, 1.0f, "left", false, "georamaMedium", 18);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 337, this.guiTop + 17, 453, 245, 11, 11, 512.0f, 512.0f, false);
        ModernGui.drawScaledStringCustomFont(totalCagnotte + " eur", this.guiLeft + 425, this.guiTop + 18, 0xFFFFFF, 1.0f, "right", false, "georamaMedium", 22);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        List<String> streamers = streamersName.subList((this.page - 1) * 28, Math.min((this.page - 1) * 28 + 28, streamersName.size() - 1));
        for (int i = 0; i < streamers.size(); ++i) {
            int j = i % 7;
            int k = i / 7;
            int offsetX = this.guiLeft + 24 + j * 42;
            int offsetY = this.guiTop + 50 + k * 40;
            this.field_73882_e.func_110434_K().func_110577_a(streamersLogo.get(streamers.get(i)));
            ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, 0.0f, 0.0f, 192, 192, 33, 33, 192.0f, 192.0f, false);
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            if (mouseX >= offsetX && mouseX <= offsetX + 33 && mouseY >= offsetY && mouseY <= offsetY + 33 || streamers.get(i).equals(selectedStreamer)) {
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(offsetX - 1, offsetY - 1, 3, 353, 35, 35, 512.0f, 512.0f, false);
                if (mouseX >= offsetX && mouseX <= offsetX + 33 && mouseY >= offsetY && mouseY <= offsetY + 33) {
                    hoveredStreamer = streamers.get(i);
                    tooltipToDraw = Arrays.asList(streamers.get(i));
                }
            } else {
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(offsetX - 1, offsetY - 1, 39, 353, 35, 35, 512.0f, 512.0f, false);
            }
            if (!selectedStreamer.equalsIgnoreCase(streamers.get(i))) continue;
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(offsetX, offsetY, 431, 313, 33, 33, 512.0f, 512.0f, false);
        }
        if (mouseX >= this.guiLeft + 7 && mouseX <= this.guiLeft + 18 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 135) {
            hoverPageSwitch = true;
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 7, this.guiTop + 117, 389, 243, 11, 18, 512.0f, 512.0f, false);
        } else if (mouseX >= this.guiLeft + 317 && mouseX <= this.guiLeft + 328 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 135) {
            hoverPageSwitch = true;
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 317, this.guiTop + 117, 404, 243, 11, 18, 512.0f, 512.0f, false);
        }
        if (!selectedStreamer.isEmpty() && !selectedStreamer.equals(playerStreamer)) {
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 24, this.guiTop + 208, 176, 276, 287, 18, 512.0f, 512.0f, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 170 - 7 + 2) + ModernGui.getCustomFont("georamaSemiBold", 20).getStringWidth(I18n.func_135053_a((String)"tvg.valid")) / 2.0f, this.guiTop + 212, 428, 245, 14, 11, 512.0f, 512.0f, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.valid"), this.guiLeft + 170 - 7, this.guiTop + 213, 0xFFFFFF, 1.0f, "center", false, "georamaSemiBold", 20);
        } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.select"), this.guiLeft + 170, this.guiTop + 213, 0xFFFFFF, 1.0f, "center", false, "georamaSemiBold", 20);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.stats"), this.guiLeft + 339, this.guiTop + 58, 0xFFFFFF, 1.0f, "left", false, "georamaSemiBold", 20);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 339, this.guiTop + 75, 3, 323, 108, 28, 512.0f, 512.0f, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.bestTime"), this.guiLeft + 344, this.guiTop + 86, 13513553, 1.0f, "left", false, "georamaMedium", 15);
        String bestTime = "00:00:00";
        if (playerTime != 0L) {
            long min = playerTime / 1000L / 60L;
            long sec = playerTime / 1000L % 60L;
            long millis = playerTime - min * 60000L - sec * 1000L;
            if (millis > 100L) {
                millis /= 10L;
            }
            bestTime = (min < 10L ? "0" + min : Long.valueOf(min)) + ":" + (sec < 10L ? "0" + sec : Long.valueOf(sec)) + ":" + (millis < 10L ? "0" + millis : Long.valueOf(millis));
        }
        ModernGui.drawScaledStringCustomFont(bestTime, this.guiLeft + 410, this.guiTop + 86, 0xFFFFFF, 1.0f, "left", false, "georamaMedium", 15);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 339, this.guiTop + 108, 3, 323, 108, 28, 512.0f, 512.0f, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.position"), this.guiLeft + 344, this.guiTop + 119, 13513553, 1.0f, "left", false, "georamaMedium", 15);
        String position = playerPosition != 0 ? "#" + playerPosition : "NC";
        ModernGui.drawScaledStringCustomFont(position, this.guiLeft + 410, this.guiTop + 119, 0xFFFFFF, 1.0f, "left", false, "georamaMedium", 15);
        if (loaded) {
            this.field_73882_e.func_110434_K().func_110577_a(!selectedStreamer.isEmpty() ? streamersLogo.get(selectedStreamer) : unknowLogo);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop + 141, 0.0f, 0.0f, 192, 192, 79, 79, 192.0f, 192.0f, false);
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + 339, this.guiTop + 141, 3, 238, 108, 79, 512.0f, 512.0f, false);
            if (!selectedStreamer.isEmpty()) {
                ModernGui.drawScaledStringCustomFont(selectedStreamer, this.guiLeft + 344, this.guiTop + 148, 0xFFFFFF, 1.0f, "left", false, "georamaMedium", 15);
            } else {
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.unknow1"), this.guiLeft + 344, this.guiTop + 148, 0xFFFFFF, 1.0f, "left", false, "georamaMedium", 15);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"tvg.unknow2"), this.guiLeft + 344, this.guiTop + 155, 0xFFFFFF, 1.0f, "left", false, "georamaMedium", 15);
            }
            ModernGui.drawScaledStringCustomFont(streamerData.containsKey("position") ? "#" + streamerData.get("position") : "NC", this.guiLeft + 344, this.guiTop + 190, 0xFFFFFF, 1.0f, "left", false, "georamaRegular", 15);
            String cagnotte = streamerData.containsKey("cagnotte") ? streamerData.get("cagnotte") : "0";
            ModernGui.drawScaledStringCustomFont(cagnotte + " eur", this.guiLeft + 344, this.guiTop + 200, 0xFFFFFF, 1.0f, "left", false, "georamaRegular", 15);
            bestTime = "00:00:00";
            if (streamerData.containsKey("time")) {
                long min = Long.parseLong(streamerData.get("time")) / 1000L / 60L;
                long sec = Long.parseLong(streamerData.get("time")) / 1000L % 60L;
                long millis = Long.parseLong(streamerData.get("time")) - min * 60000L - sec * 1000L;
                if (millis > 100L) {
                    millis /= 10L;
                }
                bestTime = (min < 10L ? "0" + min : Long.valueOf(min)) + ":" + (sec < 10L ? "0" + sec : Long.valueOf(sec)) + ":" + (millis < 10L ? "0" + millis : Long.valueOf(millis));
            }
            ModernGui.drawScaledStringCustomFont(bestTime, this.guiLeft + 344, this.guiTop + 210, 0xFFFFFF, 1.0f, "left", false, "georamaRegular", 15);
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 435 && mouseX < this.guiLeft + 435 + 18 && mouseY > this.guiTop + 13 && mouseY < this.guiTop + 13 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (mouseX > this.guiLeft + 24 && mouseX < this.guiLeft + 24 + 287 && mouseY > this.guiTop + 208 && mouseY < this.guiTop + 208 + 18) {
                if (!selectedStreamer.isEmpty()) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TVGStreamersSetPacket(selectedStreamer)));
                    playerStreamer = selectedStreamer;
                }
            } else if (!hoveredStreamer.isEmpty()) {
                selectedStreamer = hoveredStreamer;
                hoveredStreamer = "";
                streamerData = new HashMap();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TVGStreamersDataPacket(selectedStreamer)));
            } else if (hoverPageSwitch) {
                hoverPageSwitch = false;
                this.page = this.page == 1 ? 2 : 1;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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

