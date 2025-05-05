package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

class GuiDebugSkin$DebugTextField extends GuiTextField {

   private final int x;
   private final int y;
   private final FontRenderer renderer;
   private final String name;


   public GuiDebugSkin$DebugTextField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_, String name) {
      super(p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
      this.x = p_i1032_2_;
      this.y = p_i1032_3_;
      this.renderer = p_i1032_1_;
      this.name = name;
   }

   public void func_73795_f() {
      super.func_73795_f();
      this.renderer.func_78261_a(this.name, this.x, this.y - 10, 16777215);
   }
}
