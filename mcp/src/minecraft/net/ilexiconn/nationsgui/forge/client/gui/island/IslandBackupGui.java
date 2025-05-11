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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandDeleteBackupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandRestoreBackupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveBackupPacket;
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
public class IslandBackupGui
extends GuiScreen {
    protected int xSize = 289;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBar;
    private boolean saveStarted = false;
    private boolean restoreStarted = false;
    private String restoreStartedId = "";
    private long actionTime = 0L;
    private int actionWaitingSec = 5;
    private GuiTextField nameTextField;
    private String hoveredDelete = "";
    private String hoveredRestore = "";

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.nameTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 59, this.guiTop + 36, 92, 10);
        this.nameTextField.func_73786_a(false);
        this.nameTextField.func_73804_f(25);
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 270, this.guiTop + 116, 108);
    }

    public void func_73876_c() {
        this.nameTextField.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_backup");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<Object> tooltipToDraw = new ArrayList();
        ClientEventHandler.STYLE.bindTexture("island_main");
        for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
            GuiScreenTab type = IslandMainGui.TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = IslandMainGui.getTabIndex(IslandMainGui.TABS.get(i));
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 23, 249, 29, 30, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 0, 249, 23, 30, 512.0f, 512.0f, false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.tab." + x)));
        }
        ClientEventHandler.STYLE.bindTexture("island_backup");
        if (mouseX >= this.guiLeft + 276 && mouseX <= this.guiLeft + 276 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 276, this.guiTop - 8, 84, 254, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 276, this.guiTop - 8, 84, 264, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        this.drawScaledString(I18n.func_135053_a((String)"island.backup.save_name"), this.guiLeft + 57, this.guiTop + 22, 0xFFFFFF, 1.0f, false, false);
        this.nameTextField.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("island_backup");
        if (!this.saveStarted) {
            if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18) {
                ClientEventHandler.STYLE.bindTexture("island_backup");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 56, this.guiTop + 54, 0, 314, 98, 18, 512.0f, 512.0f, false);
                if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.cooldown_1"), I18n.func_135053_a((String)"island.global.cooldown_2"));
                } else if (((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() >= 6 && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.backup.limit_1"), I18n.func_135053_a((String)"island.backup.limit_2"), I18n.func_135053_a((String)"island.backup.limit_3"));
                }
            }
            this.drawScaledString(I18n.func_135053_a((String)"island.global.save"), this.guiLeft + 105, this.guiTop + 59, 0xFFFFFF, 1.0f, true, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 57, this.guiTop + 66, 0, 284, 96, 5, 512.0f, 512.0f, false);
            long timeSinceActionStarted = System.currentTimeMillis() - this.actionTime;
            int i = 0;
            while ((long)i <= timeSinceActionStarted / (long)(this.actionWaitingSec * 1000 / 48)) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 57 + i * 2, this.guiTop + 67, 97, 284, 2, 4, 512.0f, 512.0f, false);
                i += 2;
            }
            if (timeSinceActionStarted >= (long)(this.actionWaitingSec * 1000)) {
                this.saveStarted = false;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 60, this.guiTop + 56, 118, 253, 14, 14, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.backup.last_save"), this.guiLeft + 54, this.guiTop + 84, 0xFFFFFF, 1.0f, false, false);
        if (!IslandMainGui.islandInfos.get("lastBackup").equals("0")) {
            Date date = new Date(Long.parseLong((String)IslandMainGui.islandInfos.get("lastBackup")));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            String dateFormated = simpleDateFormat.format(date);
            this.drawScaledString(dateFormated, this.guiLeft + 181, this.guiTop + 84, 0xFFFFFF, 1.0f, false, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.backup.my_saves"), this.guiLeft + 49, this.guiTop + 102, 0, 1.0f, false, false);
        this.hoveredDelete = "";
        this.hoveredRestore = "";
        ClientEventHandler.STYLE.bindTexture("island_backup");
        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 112, 220, 116);
        for (int l = 0; l < ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size(); ++l) {
            String backupId = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(l)).split("###")[0];
            String backupName = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(l)).split("###")[1];
            String backupTime = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(l)).split("###")[2];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            String backupDate = simpleDateFormat.format(new Date(Long.parseLong(backupTime)));
            int offsetX = this.guiLeft + 50;
            Float offsetY = Float.valueOf((float)(this.guiTop + 112 + l * 21) + this.getSlide());
            ClientEventHandler.STYLE.bindTexture("island_backup");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 50, 112, 220, 21, 512.0f, 512.0f, false);
            this.drawScaledString("\u00a77" + backupName, offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("island_backup");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 136, offsetY.intValue() + 4, 68, 255, 10, 11, 512.0f, 512.0f, false);
            if (mouseX >= offsetX + 136 && mouseX <= offsetX + 136 + 10 && mouseY >= offsetY.intValue() + 4 && mouseY <= offsetY.intValue() + 4 + 11) {
                tooltipToDraw = Arrays.asList(backupDate);
            }
            if (this.restoreStarted || this.saveStarted || mouseX >= offsetX + 152 && mouseX <= offsetX + 152 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 152, offsetY.intValue() + 2, 0, 267, 32, 15, 512.0f, 512.0f, false);
                if (!this.restoreStarted && !this.saveStarted) {
                    this.hoveredDelete = backupId;
                }
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 152, offsetY.intValue() + 2, 0, 253, 32, 15, 512.0f, 512.0f, false);
            }
            ClientEventHandler.STYLE.bindTexture("island_backup");
            if (!this.restoreStarted && (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || this.saveStarted || mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15)) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 186, offsetY.intValue() + 2, 32, 267, 32, 15, 512.0f, 512.0f, false);
                if (!this.restoreStarted && !this.saveStarted && mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15) {
                    this.hoveredRestore = backupId;
                }
                if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) >= 300000L || mouseX < offsetX + 186 || mouseX > offsetX + 186 + 32 || mouseY < offsetY.intValue() + 2 || mouseY > offsetY.intValue() + 2 + 15) continue;
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.cooldown_1"), I18n.func_135053_a((String)"island.global.cooldown_2"));
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 186, offsetY.intValue() + 2, 32, 253, 32, 15, 512.0f, 512.0f, false);
            if (!this.restoreStarted || !backupId.equals(this.restoreStartedId)) continue;
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 187, offsetY.intValue() + 12, 0, 290, 30, 4, 512.0f, 512.0f, false);
            long timeSinceActionStarted = System.currentTimeMillis() - this.actionTime;
            int i = 0;
            while ((long)i <= timeSinceActionStarted / (long)(this.actionWaitingSec * 1000 / 15)) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 188 + i * 2, offsetY.intValue() + 13, 31, 290, 1, 3, 512.0f, 512.0f, false);
                ++i;
            }
            if (timeSinceActionStarted < (long)(this.actionWaitingSec * 1000)) continue;
            this.restoreStarted = false;
        }
        GUIUtils.endGLScissor();
        if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 226 && mouseY > this.guiTop + 111 && mouseY < this.guiTop + 111 + 118) {
            this.scrollBar.draw(mouseX, mouseY);
        }
        if (!IslandMainGui.isPremium) {
            IslandBackupGui.func_73734_a((int)(this.guiLeft + 40), (int)(this.guiTop + 6), (int)(this.guiLeft + 40 + 247), (int)(this.guiTop + 6 + 236), (int)-1157627904);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 155, this.guiTop + 111, 293, 57, 16, 26, 512.0f, 512.0f, false);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 155 + 9, this.guiTop + 111 + 19, 75, 331, 10, 12, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 155 && mouseX <= this.guiLeft + 155 + 16 && mouseY >= this.guiTop + 111 && mouseY <= this.guiTop + 111 + 26) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.premium_required"));
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
            for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
                GuiScreenTab type = IslandMainGui.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 276 && mouseX < this.guiLeft + 276 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (IslandMainGui.isPremium && ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() < 6 && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && !this.nameTextField.func_73781_b().isEmpty() && !this.saveStarted && !this.restoreStarted && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18) {
                this.saveStarted = true;
                this.actionTime = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSaveBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.nameTextField.func_73781_b())));
            } else if (IslandMainGui.isPremium && !this.saveStarted && !this.restoreStarted && !this.hoveredDelete.isEmpty()) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandDeleteBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredDelete)));
            } else if (IslandMainGui.isPremium && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && !this.saveStarted && !this.restoreStarted && !this.hoveredRestore.isEmpty()) {
                this.restoreStartedId = this.hoveredRestore;
                this.restoreStarted = true;
                this.actionTime = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandRestoreBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredRestore)));
            }
        }
        if (IslandMainGui.isPremium) {
            this.nameTextField.func_73793_a(mouseX, mouseY, mouseButton);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() > 5 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() - 5) * 21) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.nameTextField.func_73802_a(typedChar, keyCode);
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

