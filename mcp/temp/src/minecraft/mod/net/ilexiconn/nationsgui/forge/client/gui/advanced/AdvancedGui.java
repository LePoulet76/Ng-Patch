package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.minecraft.client.gui.GuiScreen;

public abstract class AdvancedGui extends GuiScreen implements ComponentContainer {

   private final List<GuiComponent> components = new ArrayList();


   public void func_73866_w_() {
      super.func_73866_w_();
      this.components.clear();
   }

   protected void addComponent(GuiComponent guiComponent) {
      if(!this.components.contains(guiComponent)) {
         this.components.add(guiComponent);
      }

      guiComponent.init(this);
   }

   public void func_73863_a(int par1, int par2, float par3) {
      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         GuiComponent component = (GuiComponent)var4.next();
         component.draw(par1, par2, par3);
      }

      super.func_73863_a(par1, par2, par3);
   }

   protected void func_73869_a(char par1, int par2) {
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         GuiComponent component = (GuiComponent)var3.next();
         component.keyTyped(par1, par2);
      }

      super.func_73869_a(par1, par2);
   }

   protected void func_73864_a(int par1, int par2, int par3) {
      boolean clickSkipped = false;
      Iterator var5 = this.components.iterator();

      GuiComponent component;
      while(var5.hasNext()) {
         component = (GuiComponent)var5.next();
         if(component.isPriorityClick()) {
            component.onClick(par1, par2, par3);
            clickSkipped = true;
         }
      }

      if(!clickSkipped) {
         var5 = this.components.iterator();

         while(var5.hasNext()) {
            component = (GuiComponent)var5.next();
            component.onClick(par1, par2, par3);
         }
      }

      super.func_73864_a(par1, par2, par3);
   }

   public void func_73876_c() {
      Iterator var1 = this.components.iterator();

      while(var1.hasNext()) {
         GuiComponent component = (GuiComponent)var1.next();
         component.update();
      }

      super.func_73876_c();
   }
}
