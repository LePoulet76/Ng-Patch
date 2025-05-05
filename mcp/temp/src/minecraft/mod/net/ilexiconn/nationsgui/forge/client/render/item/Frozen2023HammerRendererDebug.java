package net.ilexiconn.nationsgui.forge.client.render.item;

import fr.nationsglory.ngupgrades.client.renderer.item.GenericItemSwordGeckoRenderer;
import fr.nationsglory.ngupgrades.common.item.GenericGeckoItemSword;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class Frozen2023HammerRendererDebug extends GenericItemSwordGeckoRenderer {

   public Frozen2023HammerRendererDebug(String name, String modId) {
      super(name, modId);
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      GL11.glEnable('\u803a');
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glRotated(50.0D, 0.0D, 1.0D, 0.0D);
      if(type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
         GL11.glScalef(3.0F, 3.0F, 3.0F);
         GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(-1.3D, -0.9D, -0.1D);
      } else if(type.equals(ItemRenderType.INVENTORY)) {
         GL11.glScalef(1.4F, 1.4F, 1.4F);
         GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(45.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(0.0D, -1.1D, -0.3D);
      } else if(type.equals(ItemRenderType.EQUIPPED)) {
         GL11.glScalef(3.0F, 3.0F, 3.0F);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(45.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(-0.45D, -0.75D, -0.9D);
      } else {
         GL11.glTranslated(-0.5D, -0.3D, 0.0D);
      }

      GL11.glPushMatrix();
      this.render((GenericGeckoItemSword)item.func_77973_b(), item, Minecraft.func_71410_x().field_71428_T.field_74281_c);
      GL11.glPopMatrix();
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
