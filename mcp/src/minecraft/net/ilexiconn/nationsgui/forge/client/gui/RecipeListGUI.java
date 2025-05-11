/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.Slot
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Collections;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.server.container.RecipeContainer;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RecipeListGUI
extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftbook.png");
    private GuiScrollBar scrollBar;
    private GuiTextField guiTextField;
    private int itemLines;
    private int skipedLines = 0;
    private GuiButton nextButton;
    private GuiButton previousButton;
    private DisplayButton displayButton;
    private boolean displayAll = false;
    private GuiScreen prev;
    public static boolean achievementDone = false;

    public RecipeListGUI(GuiScreen prev) {
        super((Container)new RecipeContainer());
        this.field_74194_b = 400;
        this.field_74195_c = 158;
        ((RecipeContainer)this.field_74193_d).generateItemList("", this.displayAll);
        this.itemLines = ((RecipeContainer)this.field_74193_d).displayResults(0);
        this.prev = prev;
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_craftbook", 1)));
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBar = new GuiScrollBar(this.field_73880_f / 2 - 196 - 5 + 176, this.field_73881_g / 2 - 80 + 46, 88);
        this.scrollBar.setScrollIncrement(1.0f / (float)this.itemLines);
        String currentText = "";
        if (this.guiTextField != null) {
            currentText = this.guiTextField.func_73781_b();
        }
        this.guiTextField = new GuiTextField(this.field_73886_k, this.field_73880_f / 2 - 196 - 5 + 102, this.field_73881_g / 2 - 80 + 142, 71, 12);
        this.guiTextField.func_73782_a(currentText);
        this.guiTextField.func_73786_a(false);
        this.guiTextField.func_73796_b(true);
        this.nextButton = new GuiButton(0, this.field_73880_f / 2 + 8 + 75, this.field_73881_g / 2 - 39 + 5, 12, 20, ">");
        this.previousButton = new GuiButton(1, this.field_73880_f / 2 + 8 + 75, this.field_73881_g / 2 - 39 + 52, 12, 20, "<");
        this.displayButton = new DisplayButton(2, this.field_73880_f / 2 - 196 - 5 + 84, this.field_73881_g / 2 - 80 + 139);
        this.field_73887_h.add(this.nextButton);
        this.field_73887_h.add(this.previousButton);
        this.field_73887_h.add(this.displayButton);
        this.field_73887_h.add(new CloseButtonGUI(3, this.field_73880_f / 2 + 8 + 122, this.field_73881_g / 2 - 39 + 7));
    }

    public GuiScreen getPrev() {
        return this.prev;
    }

    public void func_73876_c() {
        int skip;
        super.func_73876_c();
        if (this.itemLines > 5 && (skip = (int)((float)(this.itemLines - 5) * this.scrollBar.getSliderValue())) != this.skipedLines) {
            ((RecipeContainer)this.field_74193_d).displayResults(skip);
            this.skipedLines = skip;
        }
        this.guiTextField.func_73780_a();
        this.previousButton.field_73748_h = this.nextButton.field_73748_h = ((RecipeContainer)this.field_74193_d).hasMutipleResults();
        ((RecipeContainer)this.field_74193_d).updateItemStackAnimation();
    }

    protected void func_74191_a(Slot par1Slot, int par2, int par3, int par4) {
        if (par1Slot != null) {
            par2 = par1Slot.field_75222_d;
        }
        this.field_73882_e.field_71439_g.field_71070_bA.func_75144_a(par2, par3, par4, (EntityPlayer)this.field_73882_e.field_71439_g);
    }

    public void func_73863_a(int par1, int par2, float par3) {
        super.func_73863_a(par1, par2, par3);
        if (this.displayButton.hovered) {
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)"display.allitem")), par1, par2, this.field_73886_k);
        }
    }

    protected void func_74185_a(float f, int i, int j) {
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        this.func_73729_b(this.field_73880_f / 2 - 196 - 5, this.field_73881_g / 2 - 80, 0, 0, 196, 160);
        this.func_73729_b(this.field_73880_f / 2 + 8, this.field_73881_g / 2 - 39, 0, 160, 140, 79);
        this.scrollBar.draw(i, j);
        this.guiTextField.func_73795_f();
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"craftbook.name"), this.field_73880_f / 2 - 196 - 5 + 33, this.field_73881_g / 2 - 80 + 14, 0xFFFFFF);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        if (par1GuiButton.equals(this.previousButton)) {
            ((RecipeContainer)this.field_74193_d).previous();
        } else if (par1GuiButton.equals(this.nextButton)) {
            ((RecipeContainer)this.field_74193_d).next();
        } else if (par1GuiButton.equals((Object)this.displayButton)) {
            this.displayButton.en = !this.displayButton.en;
            this.displayAll = this.displayButton.en;
            this.skipedLines = 0;
            this.scrollBar.reset();
            ((RecipeContainer)this.field_74193_d).generateItemList(this.guiTextField.func_73781_b(), this.displayAll);
            this.itemLines = ((RecipeContainer)this.field_74193_d).displayResults(0);
            this.scrollBar.setScrollIncrement(1.0f / (float)this.itemLines);
        } else if (par1GuiButton.field_73741_f == 3) {
            this.field_73882_e.func_71373_a(this.prev);
        }
    }

    public void func_73864_a(int mouseX, int mouseY, int button) {
        this.guiTextField.func_73793_a(mouseX, mouseY, button);
        super.func_73864_a(mouseX, mouseY, button);
    }

    public void func_73869_a(char character, int key) {
        if (!this.guiTextField.func_73802_a(character, key)) {
            super.func_73869_a(character, key);
        } else {
            this.skipedLines = 0;
            this.scrollBar.reset();
            ((RecipeContainer)this.field_74193_d).generateItemList(this.guiTextField.func_73781_b(), this.displayAll);
            this.itemLines = ((RecipeContainer)this.field_74193_d).displayResults(0);
            this.scrollBar.setScrollIncrement(1.0f / (float)this.itemLines);
        }
    }

    public static class BookButton
    extends GuiButton {
        private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");

        public BookButton(int id, int posX, int posY) {
            super(id, posX, posY, 20, 18, "");
        }

        protected int func_73738_a(boolean par1) {
            return par1 ? 0 : 18;
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            if (this.field_73748_h) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
                int k = this.func_73738_a(this.field_82253_i);
                this.func_73729_b(this.field_73746_c, this.field_73743_d, 145, k, 20, 18);
                this.func_73739_b(par1Minecraft, par2, par3);
            }
        }
    }

    private class DisplayButton
    extends GuiButton {
        public boolean en;
        public boolean hovered;

        public DisplayButton(int id, int posX, int posY) {
            super(id, posX, posY, 14, 14, "");
            this.en = false;
            this.hovered = false;
        }

        protected int func_73738_a(boolean par1) {
            return par1 ? 0 : 18;
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            if (this.field_73748_h) {
                RecipeListGUI.this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.hovered = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
                int k = this.en ? 0 : 14;
                this.func_73729_b(this.field_73746_c, this.field_73743_d, 206, k, 14, 14);
                this.func_73739_b(par1Minecraft, par2, par3);
            }
        }
    }
}

