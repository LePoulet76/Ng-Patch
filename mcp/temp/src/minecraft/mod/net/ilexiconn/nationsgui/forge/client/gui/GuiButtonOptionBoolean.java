package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.IButtonOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public abstract class GuiButtonOptionBoolean extends GuiButton implements IButtonOption<Boolean> {

   private String text;


   public GuiButtonOptionBoolean(int id, int posX, int posY, int width, int height, String text) {
      super(id, posX, posY, width, height, "");
      this.text = text;
      this.updateText();
   }

   protected void updateText() {
      this.field_73744_e = this.text + " : " + (((Boolean)this.getData()).booleanValue()?this.getEnabledStateText():this.getDisabledStateText());
   }

   protected String getEnabledStateText() {
      return I18n.func_135053_a("options.on");
   }

   protected String getDisabledStateText() {
      return I18n.func_135053_a("options.off");
   }

   public boolean func_73736_c(Minecraft par1Minecraft, int par2, int par3) {
      if(super.func_73736_c(par1Minecraft, par2, par3)) {
         this.setData(Boolean.valueOf(!((Boolean)this.getData()).booleanValue()));
         this.updateText();
         return true;
      } else {
         return false;
      }
   }
}
