/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class FrameGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    private final String url;
    private final String title;
    private final String musicUrl;
    public String hoveredAction = "";
    public long lastMusicCheck = 0L;
    protected int xSize = 463;
    protected int ySize = 261;
    private int guiLeft;
    private int guiTop;
    private int movingImageHeight = 0;
    private int movingImageWidth = 0;
    private Long startMovingImage = 0L;

    public FrameGui(String url, String title, String musicUrl) {
        this.url = url;
        this.title = title;
        this.musicUrl = musicUrl;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int crossY;
        if (!this.musicUrl.isEmpty() && System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer(this.musicUrl);
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.35f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        this.hoveredAction = "";
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.func_73734_a((int)this.guiLeft, (int)(this.guiTop + 10), (int)(this.guiLeft + this.xSize), (int)(this.guiTop + 10 + this.ySize), (int)-15263977);
        DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(this.url);
        if (downloadableTexture != null && downloadableTexture.getBufferedImage() != null) {
            this.movingImageHeight = downloadableTexture.getBufferedImage().getHeight();
            this.movingImageWidth = downloadableTexture.getBufferedImage().getWidth();
            if (this.movingImageHeight > this.ySize * GUI_SCALE) {
                int maxMovingImageY;
                int movingImageY;
                if (this.startMovingImage == 0L) {
                    this.startMovingImage = System.currentTimeMillis();
                }
                if ((movingImageY = (int)((System.currentTimeMillis() - this.startMovingImage) / 50L)) > (maxMovingImageY = this.movingImageHeight / GUI_SCALE - this.field_73881_g)) {
                    Minecraft.func_71410_x().func_71373_a(null);
                }
                movingImageY = Math.min(movingImageY, this.movingImageHeight - this.ySize);
                ModernGui.bindRemoteTexture(this.url);
                ModernGui.drawScaledCustomSizeModalRect(this.movingImageHeight > this.ySize * GUI_SCALE ? 0.0f : (float)this.guiLeft, (this.movingImageHeight > this.ySize * GUI_SCALE ? 0 : this.guiTop + 10) - movingImageY, 0 * GUI_SCALE, 0 * GUI_SCALE, this.movingImageWidth, this.movingImageHeight, this.movingImageHeight > this.ySize * GUI_SCALE ? this.field_73880_f : this.xSize, this.movingImageHeight / GUI_SCALE, this.movingImageWidth, this.movingImageHeight, true);
            } else {
                ModernGui.bindRemoteTexture(this.url);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 10, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, true);
            }
        }
        ClientEventHandler.STYLE.bindTexture("faction_global");
        int crossX = this.movingImageHeight > this.ySize * GUI_SCALE ? this.field_73880_f - 18 : this.guiLeft + 448;
        int n = crossY = this.movingImageHeight > this.ySize * GUI_SCALE ? 5 : this.guiTop + 13;
        if (mouseX > crossX && mouseX < crossX + 12 && mouseY > crossY && mouseY < crossY + 12) {
            ModernGui.drawScaledCustomSizeModalRect(crossX, crossY, 0 * GUI_SCALE, 18 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0f, 1536.0f, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(crossX, crossY, 0 * GUI_SCALE, 2 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 1536.0f, 1536.0f, false);
        }
        ModernGui.drawScaledStringCustomFont(this.title, this.guiLeft, this.guiTop - 7, 0xFFFFFF, 0.75f, "left", true, "minecraftDungeons", 21);
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }
}

