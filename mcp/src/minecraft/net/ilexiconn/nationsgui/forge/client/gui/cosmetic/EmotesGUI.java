/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmotesGUI
extends GuiScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "emotes/gui/cosmetic.png");
    private int guiLeft = 0;
    private int guiTop = 0;
    private int xSize = 256;
    private int ySize = 210;
    private List<String> ownedEmotes;
    private List<String> currentEmotes;
    private String selectedEmote;
    private String hoveredEmote;
    private int selectedSlot = -1;
    private GuiButton removeButton;
    public static boolean loaded = false;

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.selectedSlot = -1;
        this.guiLeft = this.field_73880_f / 2 - 128;
        this.guiTop = this.field_73881_g / 2 - 105;
        this.ownedEmotes = NGPlayerData.get((EntityPlayer)this.field_73882_e.field_71439_g).getEmotes();
        this.currentEmotes = NGPlayerData.get((EntityPlayer)this.field_73882_e.field_71439_g).getCurrentEmotes();
        this.selectedEmote = this.ownedEmotes.get(0);
        this.removeButton = new TexturedButtonGUI(0, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 0, 0, "Retirer");
        this.field_73887_h.add(this.removeButton);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialsTicks) {
        int y;
        int x;
        GuiScreenTab type;
        int i;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.bindTexture(TEXTURE);
        this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        for (i = 0; i <= 3; ++i) {
            type = CosmeticGUI_OLD.TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i % 4;
            y = i / 4;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft - 23, this.guiTop + 50 + i * 31, 125, 210, 29, 30);
                this.func_73729_b(this.guiLeft - 23 + 3, this.guiTop + 50 + i * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft - 20, this.guiTop + 50 + i * 31, 102, 210, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft - 20 + 3, this.guiTop + 50 + i * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        for (i = 4; i < CosmeticGUI_OLD.TABS.size(); ++i) {
            type = CosmeticGUI_OLD.TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i % 4;
            y = i / 4;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft + 250, this.guiTop + 50 + (i - 4) * 31, 51, 210, 29, 30);
                this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (i - 4) * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft + 252, this.guiTop + 50 + (i - 4) * 31, 80, 210, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (i - 4) * 31 + 5, 154 + x * 20, 210 + y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        EmotesGUI.drawScaledString("Emotes", this.guiLeft + 20, this.guiTop + 15, 0xFFFFFF, 2.0f, false);
        if (this.selectedSlot == -1 || this.currentEmotes.get(this.selectedSlot).isEmpty()) {
            if (this.field_73887_h.contains(this.removeButton)) {
                this.field_73887_h.remove(this.removeButton);
            }
        } else if (!this.field_73887_h.contains(this.removeButton)) {
            this.field_73887_h.add(this.removeButton);
        }
        this.hoveredEmote = null;
        int offsetX = 0;
        int offsetY = 0;
        int i2 = 0;
        for (String emote : this.ownedEmotes) {
            int j = i2 % 7;
            int k = i2 / 7;
            offsetX = this.guiLeft + 11 + j * 18;
            offsetY = this.guiTop + 53 + k * 18;
            this.drawIcon(emote, offsetX, offsetY, -1);
            if (this.selectedEmote != null && this.selectedEmote.equals(emote)) {
                this.bindTexture(TEXTURE);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.func_73729_b(offsetX - 4, offsetY - 4, 9, 210, 24, 24);
                GL11.glDisable((int)3042);
            }
            if (mouseX >= offsetX && mouseX <= offsetX + 16 && mouseY >= offsetY && mouseY <= offsetY + 16) {
                this.hoveredEmote = emote;
                tooltipToDraw = I18n.func_135053_a((String)("emotes." + this.hoveredEmote + ".name"));
            }
            ++i2;
        }
        int c = 0;
        for (String emote : this.currentEmotes) {
            switch (c) {
                case 0: {
                    this.drawIcon(emote, this.guiLeft + 180, this.guiTop + 76, 0);
                    break;
                }
                case 1: {
                    this.drawIcon(emote, this.guiLeft + 204, this.guiTop + 76, 1);
                    break;
                }
                case 2: {
                    this.drawIcon(emote, this.guiLeft + 166, this.guiTop + 98, 2);
                    break;
                }
                case 3: {
                    this.drawIcon(emote, this.guiLeft + 218, this.guiTop + 98, 3);
                    break;
                }
                case 4: {
                    this.drawIcon(emote, this.guiLeft + 180, this.guiTop + 120, 4);
                    break;
                }
                case 5: {
                    this.drawIcon(emote, this.guiLeft + 204, this.guiTop + 120, 5);
                }
            }
            if (this.selectedSlot != -1 && this.selectedSlot == c && this.currentEmotes.get(this.selectedSlot) != null && !this.currentEmotes.get(this.selectedSlot).isEmpty()) {
                this.bindTexture(TEXTURE);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int w = this.guiLeft;
                int h = this.guiTop;
                switch (c) {
                    case 0: {
                        w += 180;
                        h += 76;
                        break;
                    }
                    case 1: {
                        w += 204;
                        h += 76;
                        break;
                    }
                    case 2: {
                        w += 166;
                        h += 98;
                        break;
                    }
                    case 3: {
                        w += 218;
                        h += 98;
                        break;
                    }
                    case 4: {
                        w += 180;
                        h += 120;
                        break;
                    }
                    case 5: {
                        w += 204;
                        h += 120;
                    }
                }
                this.func_73729_b(w - 4, h - 4, 9, 210, 24, 24);
                GL11.glDisable((int)3042);
            }
            ++c;
        }
        if (this.selectedSlot != -1 && this.currentEmotes.get(this.selectedSlot) != null && !this.currentEmotes.get(this.selectedSlot).isEmpty()) {
            EmotesGUI.drawScaledString(I18n.func_135053_a((String)("emotes." + this.currentEmotes.get(this.selectedSlot) + ".name")), this.guiLeft + 23, this.guiTop + 180, 0xFFFFFF, 1.2f, false);
        }
        super.func_73863_a(mouseX, mouseY, partialsTicks);
        if (mouseX >= this.guiLeft + 165 && mouseX <= this.guiLeft + 165 + 74 && mouseY >= this.guiTop + 175 && mouseY <= this.guiTop + 175 + 19 && this.selectedSlot != -1 && this.currentEmotes.get(this.selectedSlot) != "") {
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"emotes.gui.label.remove")), mouseX, mouseY, this.field_73886_k);
        }
        for (i2 = 0; i2 <= 3; ++i2) {
            if (mouseX < this.guiLeft - (((Object)((Object)this)).getClass() == InventoryGUI.TABS.get(i2).getClassReferent() ? 23 : 20) || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 50 + i2 * 31 || mouseY > this.guiTop + 85 + i2 * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.cosmetic.tab." + i2))), mouseX, mouseY, this.field_73886_k);
        }
        for (i2 = 4; i2 < CosmeticGUI_OLD.TABS.size(); ++i2) {
            if (mouseX < this.guiLeft + 252 || mouseX > this.guiLeft + 278 || mouseY < this.guiTop + 50 + (i2 - 4) * 31 || mouseY > this.guiTop + 85 + (i2 - 4) * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.cosmetic.tab." + i2))), mouseX, mouseY, this.field_73886_k);
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(Arrays.asList(tooltipToDraw), mouseX, mouseY, this.field_73886_k);
        }
    }

    private void drawIcon(String emote, int offsetX, int offsetY, int id) {
        ResourceLocation rl = new ResourceLocation("nationsgui", "emotes/icons/" + emote + ".png");
        if (this.iconExist(rl)) {
            this.bindTexture(rl);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, 16, 16, 16.0f, 16.0f, false);
        } else {
            this.bindTexture(TEXTURE);
            this.func_73729_b(offsetX, offsetY, 11, 53, 16, 16);
            if (id > -1 && !this.currentEmotes.get(id).isEmpty()) {
                this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/custom.png"));
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, 16, 16, 16.0f, 16.0f, false);
            }
            if (id == -1) {
                this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/custom.png"));
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, 16, 16, 16.0f, 16.0f, false);
            }
        }
    }

    public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer) {
        if (!text.isEmpty()) {
            GL11.glDisable((int)32826);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int width = 0;
            for (String line : text) {
                int lineWidth = fontRenderer.func_78256_a(line);
                width = Math.max(width, lineWidth);
            }
            int posX = mouseX + 12;
            int posY = mouseY - 12;
            int offsetY = 8;
            if (text.size() > 1) {
                offsetY += 2 + (text.size() - 1) * 10;
            }
            if (posX + width > this.field_73880_f) {
                posX -= 28 + width;
            }
            if (posY + offsetY + 6 > this.field_73881_g) {
                posY = this.field_73881_g - offsetY - 6;
            }
            this.field_73735_i = 300.0f;
            int color1 = -267386864;
            this.func_73733_a(posX - 3, posY - 4, posX + width + 3, posY - 3, color1, color1);
            this.func_73733_a(posX - 3, posY + offsetY + 3, posX + width + 3, posY + offsetY + 4, color1, color1);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX - 4, posY - 3, posX - 3, posY + offsetY + 3, color1, color1);
            this.func_73733_a(posX + width + 3, posY - 3, posX + width + 4, posY + offsetY + 3, color1, color1);
            int color2 = 0x505000FF;
            int color3 = (color2 & 0xFEFEFE) >> 1 | color2 & 0xFF000000;
            this.func_73733_a(posX - 3, posY - 3 + 1, posX - 3 + 1, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX + width + 2, posY - 3 + 1, posX + width + 3, posY + offsetY + 3 - 1, color2, color3);
            this.func_73733_a(posX - 3, posY - 3, posX + width + 3, posY - 3 + 1, color2, color2);
            this.func_73733_a(posX - 3, posY + offsetY + 2, posX + width + 3, posY + offsetY + 3, color3, color3);
            for (int i = 0; i < text.size(); ++i) {
                String line = text.get(i);
                if (i == 0) {
                    fontRenderer.func_78261_a(line, posX, posY, -1);
                    posY += 2;
                } else {
                    fontRenderer.func_78261_a(EnumChatFormatting.GOLD + line, posX + width - fontRenderer.func_78256_a(line), posY, 0xFFFFFF);
                }
                posY += 10;
            }
            this.field_73735_i = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            GuiScreenTab type;
            int i;
            for (i = 0; i <= 3; ++i) {
                type = CosmeticGUI_OLD.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 50 + i * 31 || mouseY > this.guiTop + 85 + i * 31) continue;
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
            for (i = 4; i < CosmeticGUI_OLD.TABS.size(); ++i) {
                type = CosmeticGUI_OLD.TABS.get(i);
                if (mouseX < this.guiLeft + 252 || mouseX > this.guiLeft + 278 || mouseY < this.guiTop + 50 + (i - 4) * 31 || mouseY > this.guiTop + 85 + (i - 4) * 31) continue;
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
            if (mouseX >= this.guiLeft + 236 && mouseX <= this.guiLeft + 236 + 9 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 10) {
                this.field_73882_e.func_71373_a(null);
                this.field_73882_e.func_71381_h();
                this.field_73882_e.field_71416_A.func_82461_f();
            }
            if (this.hoveredEmote != null) {
                this.selectedEmote = this.hoveredEmote;
                this.selectedSlot = -1;
            }
            if (mouseX >= this.guiLeft + 154 && mouseX <= this.guiLeft + 154 + 92 && mouseY >= this.guiTop + 52 && mouseY <= this.guiTop + 52 + 107) {
                this.selectedSlot = -1;
            }
            int slot = -1;
            slot = this.mouseClickedInCurentSlots(mouseX, mouseY);
            if (slot != -1) {
                this.selectedSlot = slot;
                if (this.selectedEmote != null) {
                    if (!this.currentEmotes.contains(this.selectedEmote)) {
                        this.currentEmotes.set(this.selectedSlot, this.selectedEmote);
                    }
                    this.selectedEmote = null;
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73875_a(GuiButton button) {
        switch (button.field_73741_f) {
            case 0: {
                if (this.selectedSlot == -1) break;
                this.currentEmotes.set(this.selectedSlot, "");
                this.selectedSlot = -1;
            }
        }
    }

    private int mouseClickedInCurentSlots(int mouseX, int mouseY) {
        int slotLenght = 16;
        int[][] slots = new int[][]{{180, 76}, {204, 76}, {166, 98}, {218, 98}, {180, 120}, {204, 120}};
        for (int i = 0; i < slots.length; ++i) {
            int[] slot = slots[i];
            if (mouseX < this.guiLeft + slot[0] || mouseX > this.guiLeft + slot[0] + slotLenght || mouseY < this.guiTop + slot[1] || mouseY > this.guiTop + slot[1] + slotLenght) continue;
            return i;
        }
        return -1;
    }

    public void func_73874_b() {
        if (this.currentEmotes.size() < 7) {
            NGPlayerData.get((EntityPlayer)this.field_73882_e.field_71439_g).setCurrentEmotes(this.currentEmotes);
            System.out.println("GuiClosed + sending current emotes");
        }
    }

    private void bindTexture(ResourceLocation texture) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(texture);
    }

    private boolean iconExist(ResourceLocation rl) {
        return EmotesGUI.class.getResourceAsStream("/assets/" + rl.func_110624_b() + "/" + rl.func_110623_a()) != null;
    }

    public static void drawScaledString(String text, int x, int y, int color, float scale, boolean centered) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0f;
        }
        Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
    }
}

