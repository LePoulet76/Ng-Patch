package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.minecraft.client.gui.Gui;

public class ScrollerSeparator extends Gui implements GuiScrollerElement {

   private int width;


   public void init(ComponentContainer container) {}

   public void draw(int mouseX, int mouseY, float partialTicks) {
      func_73734_a(0, 4, this.width, 5, -16777216);
   }

   public void onClick(int mouseX, int mouseY, int clickType) {}

   public void update() {}

   public void keyTyped(char c, int key) {}

   public boolean isPriorityClick() {
      return false;
   }

   public void init(GuiScroller scroller) {
      this.width = scroller.getWorkWidth();
   }

   public int getHeight() {
      return 9;
   }
}
