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
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSavePermsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandPermsGui
extends GuiScreen {
    protected int xSize = 260;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBar;
    public boolean helpOpened = false;
    public int helpSectionOffsetX = 0;
    public String hoveredPerm = "";
    public String hoveredRole = "";
    public boolean hoveredStatus = false;
    public static HashMap<String, HashMap<String, Boolean>> editedPerms = new HashMap();

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 243, this.guiTop + 37, 164);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_perms");
        List<Object> tooltipToDraw = new ArrayList();
        this.helpSectionOffsetX = !this.helpOpened ? Math.max(this.helpSectionOffsetX - 1, 0) : Math.min(this.helpSectionOffsetX + 1, 107);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 260 + this.helpSectionOffsetX, this.guiTop + 171, 0, 271, 23, 45, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 268 + this.helpSectionOffsetX), (float)(this.guiTop + 209), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 268 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 209)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"island.list.help.title"), this.guiLeft + 268 + this.helpSectionOffsetX, this.guiTop + 209, 0, 1.0f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_perms");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 260 - 107 + this.helpSectionOffsetX, this.guiTop + 8, 405, 0, 107, 232, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.title"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 20, 0, 1.3f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_1"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 50, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_2"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 60, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_3"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 70, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_4"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 80, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_5"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 95, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_6"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 105, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_7"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 115, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_8"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 125, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.help.text_9"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 135, 0, 1.0f, true, false);
        ClientEventHandler.STYLE.bindTexture("island_perms");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
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
        ClientEventHandler.STYLE.bindTexture("island_perms");
        if (mouseX >= this.guiLeft + 248 && mouseX <= this.guiLeft + 248 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 248, this.guiTop - 8, 139, 259, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 248, this.guiTop - 8, 139, 249, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.role.member").toUpperCase(), this.guiLeft + 159, this.guiTop + 25, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.perms.role.visitor").toUpperCase(), this.guiLeft + 215, this.guiTop + 25, 0, 1.0f, true, false);
        this.hoveredPerm = "";
        this.hoveredRole = "";
        GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 35, 194, 168);
        for (int l = 0; l < ((ArrayList)IslandMainGui.islandInfos.get("permissions")).size(); ++l) {
            String perm = (String)((ArrayList)IslandMainGui.islandInfos.get("permissions")).get(l);
            int offsetX = this.guiLeft + 49;
            Float offsetY = Float.valueOf((float)(this.guiTop + 35 + l * 23) + this.getSlide());
            boolean trueForMembers = false;
            boolean trueForVisitors = false;
            ClientEventHandler.STYLE.bindTexture("island_perms");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 49, 35, 194, 23, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("island.perms.label." + perm)), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("island_perms");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 67, offsetY.intValue() + 6, 119, 249, 10, 11, 512.0f, 512.0f, false);
            if (mouseX >= offsetX + 67 && mouseX <= offsetX + 67 + 10 && mouseY >= offsetY.intValue() + 6 && mouseY <= offsetY.intValue() + 6 + 11) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.perms.tooltip." + perm)));
            }
            ClientEventHandler.STYLE.bindTexture("island_perms");
            if (editedPerms.containsKey("member") && editedPerms.get("member").containsKey(perm) && editedPerms.get("member").get(perm).booleanValue() || (!editedPerms.containsKey("member") || !editedPerms.get("member").containsKey(perm)) && IslandMainGui.membersPerms.get(perm).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 102, offsetY.intValue() + 3, 164, 251, 15, 15, 512.0f, 512.0f, false);
                trueForMembers = true;
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 102, offsetY.intValue() + 3, 180, 251, 14, 15, 512.0f, 512.0f, false);
            }
            if (editedPerms.containsKey("visitor") && editedPerms.get("visitor").containsKey(perm) && editedPerms.get("visitor").get(perm).booleanValue() || (!editedPerms.containsKey("visitor") || !editedPerms.get("visitor").containsKey(perm)) && IslandMainGui.visitorsPerms.get(perm).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 158, offsetY.intValue() + 3, 164, 251, 15, 15, 512.0f, 512.0f, false);
                trueForVisitors = true;
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 158, offsetY.intValue() + 3, 180, 251, 14, 15, 512.0f, 512.0f, false);
            }
            if (mouseX >= offsetX + 102 && mouseX <= offsetX + 102 + 15 && (float)mouseY >= offsetY.floatValue() + 3.0f && (float)mouseY <= offsetY.floatValue() + 3.0f + 15.0f) {
                this.hoveredRole = "member";
                this.hoveredPerm = perm;
                this.hoveredStatus = trueForMembers;
                continue;
            }
            if (mouseX < offsetX + 158 || mouseX > offsetX + 158 + 15 || !((float)mouseY >= offsetY.floatValue() + 3.0f) || !((float)mouseY <= offsetY.floatValue() + 3.0f + 15.0f)) continue;
            this.hoveredRole = "visitor";
            this.hoveredPerm = perm;
            this.hoveredStatus = trueForVisitors;
        }
        GUIUtils.endGLScissor();
        this.scrollBar.draw(mouseX, mouseY);
        ClientEventHandler.STYLE.bindTexture("island_perms");
        if (editedPerms.isEmpty() || mouseX >= this.guiLeft + 135 && mouseX <= this.guiLeft + 135 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 135, this.guiTop + 214, 0, 249, 113, 18, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.global.save"), this.guiLeft + 191, this.guiTop + 219, 0xFFFFFF, 1.0f, true, false);
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
            if (mouseX > this.guiLeft + 248 && mouseX < this.guiLeft + 248 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (!this.helpOpened && mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
                this.helpOpened = true;
            } else if (this.helpOpened && mouseX > this.guiLeft + 260 + 107 && mouseX < this.guiLeft + 260 + 107 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
                this.helpOpened = false;
            } else if (!this.hoveredRole.isEmpty() && !this.hoveredPerm.isEmpty()) {
                HashMap<String, Boolean> editedPermForRole = new HashMap<String, Boolean>();
                if (editedPerms.containsKey(this.hoveredRole)) {
                    editedPermForRole = editedPerms.get(this.hoveredRole);
                }
                editedPermForRole.put(this.hoveredPerm, !this.hoveredStatus);
                editedPerms.put(this.hoveredRole, editedPermForRole);
            } else if (mouseX >= this.guiLeft + 135 && mouseX <= this.guiLeft + 135 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18 && !editedPerms.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSavePermsPacket((String)IslandMainGui.islandInfos.get("id"), editedPerms)));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return ((ArrayList)IslandMainGui.islandInfos.get("permissions")).size() > 7 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("permissions")).size() - 7) * 23) * this.scrollBar.getSliderValue() : 0.0f;
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

