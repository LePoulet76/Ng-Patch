/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandSettingsGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveFlagPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Encoder;

@SideOnly(value=Side.CLIENT)
public class IslandFlagGui
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 179;
    private int guiLeft;
    private int guiTop;
    private static Map<String, DynamicTexture> images = Maps.newHashMap();
    private HashMap<Integer, Integer> pixels = new HashMap();
    private ArrayList<Integer> colorList = new ArrayList();
    private int pixelHoveredId = -1;
    private int pixelHoveredColorId = 0;
    private boolean mouseDrawing = true;
    private int colorHovered = 0;
    private int colorSelected = 0;
    private GuiButton saveButton;

    public IslandFlagGui() {
        if (((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).size() > 0) {
            for (int i = 0; i < ((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).size(); ++i) {
                this.pixels.put(i, ((Double)((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).get(i)).intValue());
            }
        } else {
            for (int i = 0; i < 289; ++i) {
                this.pixels.put(i, -1);
            }
        }
        this.colorList.addAll(Arrays.asList(-793344, -145910, -815329, -1351392, -1891550, -3931010, -9553525, -12235111, -13930319, -16345413, -16740772, -7488731, -1, -16448251, -10471149, -5789785, -6274803, -3761043));
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.saveButton = new GuiButton(0, this.guiLeft + 244, this.guiTop + 142, 54, 20, I18n.func_135053_a((String)"faction.flag.valid"));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int i;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_flag");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        super.func_73863_a(mouseX, mouseY, par3);
        ClientEventHandler.STYLE.bindTexture("island_flag");
        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 192, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 304, this.guiTop - 6, 0, 182, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        int x = 0;
        int y = 0;
        for (i = 0; i < this.colorList.size(); ++i) {
            int colodId = this.colorList.get(i);
            if (mouseX > this.guiLeft + 244 + x * 18 && mouseX < this.guiLeft + 244 + x * 18 + 18 && mouseY > this.guiTop + 28 + y * 18 && mouseY < this.guiTop + 28 + y * 18 + 18) {
                this.colorHovered = colodId;
            }
            if (colodId == this.colorSelected) {
                ClientEventHandler.STYLE.bindTexture("island_flag");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 + x * 18 - 3, this.guiTop + 28 + y * 18 - 3, 22, 182, 24, 24, 512.0f, 512.0f, false);
            }
            x = x < 2 ? x + 1 : 0;
            y = x == 0 ? y + 1 : y;
        }
        x = 0;
        y = 0;
        i = 0;
        if (this.pixels.size() > 0) {
            for (Map.Entry<Integer, Integer> pair : this.pixels.entrySet()) {
                int colorId = pair.getValue();
                if (mouseX >= this.guiLeft + 84 + x * 6 && mouseX <= this.guiLeft + 84 + x * 6 + 6 && mouseY >= this.guiTop + 32 + y * 6 && mouseY <= this.guiTop + 32 + y * 6 + 6) {
                    this.pixelHoveredId = i;
                }
                if (this.mouseDrawing && colorId != this.colorSelected && this.pixelHoveredId == i) {
                    this.pixels.put(i, this.colorSelected);
                    colorId = this.colorSelected;
                }
                Gui.func_73734_a((int)(this.guiLeft + 84 + x * 6), (int)(this.guiTop + 32 + y * 6), (int)(this.guiLeft + 84 + x * 6 + 6), (int)(this.guiTop + 32 + y * 6 + 6), (int)colorId);
                x = x < 16 ? x + 1 : 0;
                y = x == 0 ? y + 1 : y;
                ++i;
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.flag.reset"), this.guiLeft + 73, this.guiTop + 144, 0x191919, 1.0f, false, false);
        this.saveButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void func_73879_b(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.mouseDrawing = false;
        }
        super.func_73879_b(mouseX, mouseY, mouseButton);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandSettingsGui());
            }
            if (this.colorHovered != 0 && mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 28 && mouseY < this.guiTop + 28 + 108) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
            }
            if (mouseX > this.guiLeft + 84 && mouseX < this.guiLeft + 84 + 102 && mouseY > this.guiTop + 32 && mouseY < this.guiTop + 32 + 102) {
                this.mouseDrawing = true;
            }
            if (mouseX > this.guiLeft + 54 && mouseX < this.guiLeft + 54 + 15 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 15) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                for (int i = 0; i < 289; ++i) {
                    this.pixels.put(i, -1);
                }
            }
            if (mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 142 && mouseY < this.guiTop + 142 + 18) {
                ArrayList<Integer> imagePixels = new ArrayList<Integer>();
                BufferedImage image = new BufferedImage(102, 102, 2);
                Graphics2D graphics2D = image.createGraphics();
                int x = 0;
                int y = 0;
                for (Map.Entry<Integer, Integer> pair : this.pixels.entrySet()) {
                    imagePixels.add(pair.getValue());
                    graphics2D.setPaint(new Color(pair.getValue()));
                    graphics2D.fillRect(x * 6, y * 6, 6, 6);
                    x = x < 16 ? x + 1 : 0;
                    y = x == 0 ? y + 1 : y;
                }
                graphics2D.dispose();
                String base64Img = IslandFlagGui.encodeToString(image, "png").replace("\r\n", "");
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                HashMap<String, Object> dataToPacket = new HashMap<String, Object>();
                dataToPacket.put("image", base64Img);
                dataToPacket.put("imagePixels", imagePixels);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSaveFlagPacket((String)IslandMainGui.islandInfos.get("id"), dataToPacket)));
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandSettingsGui());
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

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static void bindTexture(String name) {
        GL11.glBindTexture((int)3553, (int)images.get(name).func_110552_b());
    }

    public boolean func_73868_f() {
        return false;
    }
}

