/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.server.container.CustomWorkbenchContainer;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class CustomCraftingGUI
extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");
    public ItemStack[] selectedRecipe = new ItemStack[10];

    public CustomCraftingGUI(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
        super((Container)new CustomWorkbenchContainer(par1InventoryPlayer, par2World, par3, par4, par5));
        this.field_74194_b = 380;
        this.field_74195_c = 150;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new CloseButtonGUI(0, this.field_73880_f / 2 + 165, this.field_73881_g / 2 - 61 + 14));
        this.field_73887_h.add(new RecipeListGUI.BookButton(1, this.field_73880_f / 2 - 133, this.field_73881_g / 2 + 8));
        this.field_73887_h.add(new TexturedButtonGUI(2, this.field_73880_f / 2 - 40, this.field_73881_g / 2 - 9, 19, 19, "mail_open", 182, 72, ""));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        switch (par1GuiButton.field_73741_f) {
            case 0: {
                this.field_73882_e.func_71373_a(null);
                break;
            }
            case 1: {
                this.selectedRecipe = new ItemStack[10];
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
                break;
            }
            case 2: {
                this.selectedRecipe = new ItemStack[10];
            }
        }
    }

    protected void func_74185_a(float f, int i, int j) {
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        this.func_73729_b(this.field_73880_f / 2 - 144 - 5, this.field_73881_g / 2 - 61, 0, 0, 144, 123);
        this.func_73729_b(this.field_73880_f / 2 + 5, this.field_73881_g / 2 - 61, 0, 123, 182, 128);
        int posY = this.field_73881_g / 2 - 61 + 14;
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"container.crafting"), this.field_73880_f / 2 - 144 - 5 + 35, posY + 4, 0xFFFFFF);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"container.inventory"), this.field_73880_f / 2 + 5 + 30, posY, 0xFFFFFF);
        this.displayRecipe();
    }

    private void displayRecipe() {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)32826);
        RenderHelper.func_74520_c();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.6f);
        CustomCraftingGUI.field_74196_a.field_77024_a = false;
        boolean recipeDisplayed = false;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                ItemStack itemStack = this.selectedRecipe[x + 3 * y];
                if (itemStack == null) continue;
                recipeDisplayed = true;
                field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 144 - 5 + 48 + x * 18, this.field_73881_g / 2 - 61 + 53 + y * 18);
            }
        }
        ((GuiButton)this.field_73887_h.get((int)2)).field_73748_h = recipeDisplayed;
        ItemStack itemStack = this.selectedRecipe[9];
        if (itemStack != null) {
            field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, this.field_73880_f / 2 - 144 - 5 + 111, this.field_73881_g / 2 - 61 + 89);
        }
        RenderHelper.func_74518_a();
        GL11.glDisable((int)32826);
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        CustomCraftingGUI.field_74196_a.field_77024_a = true;
    }
}

