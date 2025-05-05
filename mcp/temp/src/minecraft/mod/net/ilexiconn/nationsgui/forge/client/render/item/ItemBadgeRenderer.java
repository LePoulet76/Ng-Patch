package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemBadgeRenderer implements IItemRenderer {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.INVENTORY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      if(item.func_77978_p() != null) {
         String badgeType = item.func_77978_p().func_74779_i("BadgeID");
         Tessellator tessellator = Tessellator.field_78398_a;
         byte par5 = 16;
         byte par4 = 16;
         Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(badgeType));
         tessellator.func_78382_b();
         tessellator.func_78374_a(0.0D, (double)par5, 0.0D, 0.0D, 1.0D);
         tessellator.func_78374_a((double)par4, (double)par5, 0.0D, 1.0D, 1.0D);
         tessellator.func_78374_a((double)par4, 0.0D, 0.0D, 1.0D, 0.0D);
         tessellator.func_78374_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
         tessellator.func_78381_a();
      }

   }
}
