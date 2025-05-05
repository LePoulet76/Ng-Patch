package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI$BookButton;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.server.container.CustomWorkbenchContainer;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class CustomCraftingGUI extends GuiContainer {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");
   public ItemStack[] selectedRecipe = new ItemStack[10];


   public CustomCraftingGUI(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
      super(new CustomWorkbenchContainer(par1InventoryPlayer, par2World, par3, par4, par5));
      this.field_74194_b = 380;
      this.field_74195_c = 150;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new CloseButtonGUI(0, this.field_73880_f / 2 + 165, this.field_73881_g / 2 - 61 + 14));
      this.field_73887_h.add(new RecipeListGUI$BookButton(1, this.field_73880_f / 2 - 133, this.field_73881_g / 2 + 8));
      this.field_73887_h.add(new TexturedButtonGUI(2, this.field_73880_f / 2 - 40, this.field_73881_g / 2 - 9, 19, 19, "mail_open", 182, 72, ""));
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      super.func_73875_a(par1GuiButton);
      switch(par1GuiButton.field_73741_f) {
      case 0:
         this.field_73882_e.func_71373_a((GuiScreen)null);
         break;
      case 1:
         this.selectedRecipe = new ItemStack[10];
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
         break;
      case 2:
         this.selectedRecipe = new ItemStack[10];
      }

   }

   protected void func_74185_a(float f, int i, int j) {
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      this.func_73729_b(this.field_73880_f / 2 - 144 - 5, this.field_73881_g / 2 - 61, 0, 0, 144, 123);
      this.func_73729_b(this.field_73880_f / 2 + 5, this.field_73881_g / 2 - 61, 0, 123, 182, 128);
      int posY = this.field_73881_g / 2 - 61 + 14;
      this.field_73886_k.func_78276_b(I18n.func_135053_a("container.crafting"), this.field_73880_f / 2 - 144 - 5 + 35, posY + 4, 16777215);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("container.inventory"), this.field_73880_f / 2 + 5 + 30, posY, 16777215);
      this.displayRecipe();
   }

   private void displayRecipe() {
      GL11.glEnable(3042);
      GL11.glEnable('\u803a');
      RenderHelper.func_74520_c();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
      field_74196_a.field_77024_a = false;
      boolean recipeDisplayed = false;

      for(int itemStack = 0; itemStack < 3; ++itemStack) {
         for(int x = 0; x < 3; ++x) {
            ItemStack itemStack1 = this.selectedRecipe[x + 3 * itemStack];
            if(itemStack1 != null) {
               recipeDisplayed = true;
               field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack1, this.field_73880_f / 2 - 144 - 5 + 48 + x * 18, this.field_73881_g / 2 - 61 + 53 + itemStack * 18);
            }
         }
      }

      ((GuiButton)this.field_73887_h.get(2)).field_73748_h = recipeDisplayed;
      ItemStack var5 = this.selectedRecipe[9];
      if(var5 != null) {
         field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.func_110434_K(), var5, this.field_73880_f / 2 - 144 - 5 + 111, this.field_73881_g / 2 - 61 + 89);
      }

      RenderHelper.func_74518_a();
      GL11.glDisable('\u803a');
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      field_74196_a.field_77024_a = true;
   }

}
