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
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandCreateCooldownPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTPPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandCreateGui
extends GuiScreen {
    protected int xSize = 289;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private String nameText = "";
    private String descriptionText = "";
    boolean sizeExpanded = false;
    List<String> sizes = new ArrayList<String>();
    private String selectedSize = "";
    private String hoveredSize = "";
    private String selectedBiome = "";
    private String hoveredBiome = "";
    List<String> biomes = new ArrayList<String>();
    private int biomeOffsetXMax = 1;
    public int biomeOffsetNumber = 0;
    public int biomeOffsetX = 0;
    private boolean isPrivate = false;
    private GuiTextField nameTextField;
    private GuiTextField descriptionTextField;
    private boolean creationStarted = false;
    private long creationTime = 0L;
    private int creationWaitingSec = 7;
    public static int creationIslandId = -1;
    public static long lastPlayerCreation = 0L;
    public static boolean loaded = false;
    public boolean isPremium = false;
    public String serverNumber = "1";

    public IslandCreateGui(boolean isPremium, String serverNumber) {
        this.isPremium = isPremium;
        this.serverNumber = serverNumber;
        this.sizes.addAll(Arrays.asList("25", "50", "100", "250", "500"));
        this.selectedSize = "25";
        this.biomes.addAll(Arrays.asList("plaine", "marais", "desert", "neige", "jungle"));
        this.selectedBiome = "plaine";
        this.biomeOffsetXMax = this.biomes.size() - 4;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandCreateCooldownPacket()));
    }

    public void func_73876_c() {
        this.nameTextField.func_73780_a();
        this.descriptionTextField.func_73780_a();
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.nameTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 30, 218, 10);
        this.nameTextField.func_73786_a(false);
        this.nameTextField.func_73804_f(25);
        this.descriptionTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 62, 218, 10);
        this.descriptionTextField.func_73786_a(false);
        this.descriptionTextField.func_73804_f(50);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int offsetY;
        int offsetX;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_create");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<Object> tooltipToDraw = new ArrayList();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 115), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 115)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"island.create.title"), this.guiLeft + 14, this.guiTop + 115, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_create");
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 239, 266, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 239, 256, 9, 10, 512.0f, 512.0f, false);
        }
        ClientEventHandler.STYLE.bindTexture("island_create");
        this.drawScaledString(I18n.func_135053_a((String)"island.create.name"), this.guiLeft + 50, this.guiTop + 15, 0, 1.0f, false, false);
        this.nameTextField.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"island.create.description"), this.guiLeft + 50, this.guiTop + 47, 0, 1.0f, false, false);
        this.descriptionTextField.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"island.create.biome"), this.guiLeft + 50, this.guiTop + 114, 0, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_create");
        int biomeOffsetXTarget = 51 * this.biomeOffsetNumber;
        if (this.biomeOffsetX < biomeOffsetXTarget) {
            ++this.biomeOffsetX;
        } else if (this.biomeOffsetX > biomeOffsetXTarget) {
            --this.biomeOffsetX;
        }
        this.hoveredBiome = "";
        GUIUtils.startGLScissor(this.guiLeft + 60, this.guiTop + 124, 203, 50);
        for (int i = 0; i < this.biomes.size(); ++i) {
            offsetX = this.guiLeft + 60 - this.biomeOffsetX + 51 * i;
            offsetY = this.guiTop + 124;
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0 + 51 * i, 279, 50, 50, 512.0f, 512.0f, false);
            if (mouseX >= offsetX && mouseX <= offsetX + 50 && mouseY >= offsetY && mouseY <= offsetY + 50) {
                this.hoveredBiome = this.biomes.get(i);
            }
            if (!this.biomes.get(i).equals(this.selectedBiome)) continue;
            ClientEventHandler.STYLE.bindTexture("island_create");
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 331, 50, 50, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3008);
        }
        GUIUtils.endGLScissor();
        ClientEventHandler.STYLE.bindTexture("island_create");
        if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 10 && mouseY >= this.guiTop + 124 && mouseY <= this.guiTop + 124 + 50) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 124, 51, 331, 10, 50, 512.0f, 512.0f, false);
        } else if (mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 264 + 10 && mouseY >= this.guiTop + 124 && mouseY <= this.guiTop + 124 + 50) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 264, this.guiTop + 124, 63, 331, 10, 50, 512.0f, 512.0f, false);
        }
        ClientEventHandler.STYLE.bindTexture("island_list");
        if (!(loaded && System.currentTimeMillis() - lastPlayerCreation >= 3600000L || this.creationStarted || mouseX < this.guiLeft + 171 || mouseX > this.guiLeft + 171 + 103 || mouseY < this.guiTop + 195 || mouseY > this.guiTop + 195 + 27)) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 171, this.guiTop + 195, 153, 319, 103, 27, 512.0f, 512.0f, false);
            if (!loaded || System.currentTimeMillis() - lastPlayerCreation < 3600000L) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.main.create.cooldown"));
            }
        }
        ClientEventHandler.STYLE.bindTexture("island_create");
        if (this.creationStarted) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 172, this.guiTop + 216, 0, 249, 101, 5, 512.0f, 512.0f, false);
            long timeSinceCreationStarted = System.currentTimeMillis() - this.creationTime;
            int creationDelay = this.creationWaitingSec;
            int i = 0;
            while ((long)i <= timeSinceCreationStarted / (long)(creationDelay * 1000 / 48)) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 174 + i * 2, this.guiTop + 217, 102, 249, 2, 4, 512.0f, 512.0f, false);
                i += 2;
            }
            if (timeSinceCreationStarted >= (long)(creationDelay * 1000)) {
                Minecraft.func_71410_x().func_71373_a(null);
                if (creationIslandId != -1) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTPPacket(creationIslandId + "", this.serverNumber)));
                }
            }
            Float loadPercent = Float.valueOf((float)timeSinceCreationStarted * 1.0f / (float)(creationDelay * 1000) * 100.0f);
            this.drawScaledString(loadPercent.intValue() + "%", this.guiLeft + 228, this.guiTop + 204, 0xFFFFFF, 1.0f, true, false);
        } else {
            this.drawScaledString(I18n.func_135053_a((String)"island.create.create_button"), this.guiLeft + 228, this.guiTop + 204, 0xFFFFFF, 1.0f, true, false);
        }
        ClientEventHandler.STYLE.bindTexture("island_create");
        if (this.isPrivate) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 204, 0, 256, 10, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 204, 0, 268, 10, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.create.private"), this.guiLeft + 64, this.guiTop + 204, 0, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_create");
        if (!this.isPremium) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 44, this.guiTop + 196, 75, 331, 10, 12, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 44 && mouseX <= this.guiLeft + 44 + 10 && mouseY >= this.guiTop + 196 && mouseY <= this.guiTop + 196 + 12) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.premium_required"));
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.create.size"), this.guiLeft + 50, this.guiTop + 80, 0, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_create");
        this.drawScaledString(this.selectedSize + "x" + this.selectedSize, this.guiLeft + 53, this.guiTop + 95, 0xFFFFFF, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_create");
        this.hoveredSize = "";
        if (this.sizeExpanded && this.sizes.size() > 0) {
            for (int i = 0; i < this.sizes.size(); ++i) {
                offsetX = this.guiLeft + 49;
                offsetY = this.guiTop + 107 + i * 16;
                ClientEventHandler.STYLE.bindTexture("island_create");
                if (mouseX >= offsetX && mouseX <= offsetX + 225 && mouseY >= offsetY && mouseY <= offsetY + 16) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, offsetY, 0, 384, 225, 16, 512.0f, 512.0f, false);
                    this.hoveredSize = this.sizes.get(i);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, offsetY, 11, 257, 225, 16, 512.0f, 512.0f, false);
                }
                if (i > 2 && !this.isPremium) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 260, offsetY + 2, 75, 331, 10, 12, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 260 && mouseX <= this.guiLeft + 260 + 10 && mouseY >= offsetY + 2 && mouseY <= offsetY + 2 + 12) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.premium_required"));
                    }
                }
                this.drawScaledString(this.sizes.get(i) + "x" + this.sizes.get(i), offsetX + 6, offsetY + 4, 0xFFFFFF, 1.0f, false, false);
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 225 && mouseY > this.guiTop + 90 && mouseY < this.guiTop + 90 + 17) {
                this.sizeExpanded = !this.sizeExpanded;
            } else if (!this.hoveredSize.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 225 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 100) {
                if (this.isPremium || this.sizes.indexOf(this.hoveredSize) <= 2) {
                    this.selectedSize = this.hoveredSize;
                    this.sizeExpanded = false;
                }
            } else if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.biomeOffsetNumber = this.biomeOffsetNumber - 1 > 0 ? this.biomeOffsetNumber - 1 : 0;
            } else if (mouseX > this.guiLeft + 264 && mouseX < this.guiLeft + 264 + 10 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.biomeOffsetNumber = this.biomeOffsetNumber + 1 < this.biomeOffsetXMax ? this.biomeOffsetNumber + 1 : this.biomeOffsetXMax;
            } else if (!this.hoveredBiome.isEmpty() && mouseX > this.guiLeft + 60 && mouseX < this.guiLeft + 60 + 203 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedBiome = this.hoveredBiome;
            } else if (this.isPremium && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 204 && mouseY < this.guiTop + 204 + 10) {
                this.isPrivate = !this.isPrivate;
            } else if (!(mouseX < this.guiLeft + 171 || mouseX > this.guiLeft + 171 + 103 || mouseY < this.guiTop + 195 || mouseY > this.guiTop + 195 + 27 || !loaded || System.currentTimeMillis() - lastPlayerCreation <= 3600000L || this.creationStarted || this.nameText.isEmpty() || this.descriptionText.isEmpty() || this.selectedSize.isEmpty() || this.selectedBiome.isEmpty())) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandCreatePacket(this.nameText, this.descriptionText, this.selectedSize, this.selectedBiome, this.isPrivate)));
                this.creationStarted = true;
                this.creationTime = System.currentTimeMillis();
            }
        }
        this.nameTextField.func_73793_a(mouseX, mouseY, mouseButton);
        this.descriptionTextField.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.nameTextField.func_73802_a(typedChar, keyCode)) {
            this.nameText = this.nameTextField.func_73781_b();
        } else if (this.descriptionTextField.func_73802_a(typedChar, keyCode)) {
            this.descriptionText = this.descriptionTextField.func_73781_b();
        }
        super.func_73869_a(typedChar, keyCode);
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

