package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

public abstract class AbstractAssistanceComponent extends Gui implements GuiComponent {

   protected ComponentContainer container;


   public void func_73729_b(int posX, int posY, int u, int v, int width, int height) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
      float f = 0.001953125F;
      float f1 = 0.001953125F;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a((double)posX, (double)(posY + height), (double)this.field_73735_i, (double)((float)u * f), (double)((float)(v + height) * f1));
      tessellator.func_78374_a((double)(posX + width), (double)(posY + height), (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
      tessellator.func_78374_a((double)(posX + width), (double)posY, (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)v * f1));
      tessellator.func_78374_a((double)posX, (double)posY, (double)this.field_73735_i, (double)((float)u * f), (double)((float)v * f1));
      tessellator.func_78381_a();
   }

   public boolean isPriorityClick() {
      return false;
   }

   public void init(ComponentContainer container) {
      this.container = container;
   }
}
