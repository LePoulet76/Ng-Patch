package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$10;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$11;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$12;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$13;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$9;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;

public class CustomizationGUI extends GuiScreen {

   GuiScreen previous;
   private boolean displayRestartMessage = false;


   public CustomizationGUI(GuiScreen previous) {
      this.previous = previous;
   }

   public void func_73863_a(int par1, int par2, float par3) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("gui.customization.title"), this.field_73880_f / 2, 15, 16777215);
      super.func_73863_a(par1, par2, par3);
      if(this.displayRestartMessage) {
         this.func_73732_a(this.field_73886_k, I18n.func_135053_a("gui.option.restartRequired"), this.field_73880_f / 2, this.field_73881_g / 6 + 153, 16777215);
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new CustomizationGUI$1(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6, 150, 20, I18n.func_135053_a("options.displayobjectives")));
      this.field_73887_h.add(new CustomizationGUI$2(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 125, 150, 20, I18n.func_135053_a("options.azimutenable")));
      this.field_73887_h.add(new CustomizationGUI$3(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6, 150, 20, I18n.func_135053_a("options.azimutposition")));
      this.field_73887_h.add(new CustomizationGUI$4(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 - 25, 150, 20, I18n.func_135053_a("options.armorInfosRight")));
      this.field_73887_h.add(new CustomizationGUI$5(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 25, 150, 20, I18n.func_135053_a("options.damageIndicator")));
      this.field_73887_h.add(new CustomizationGUI$6(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 25, 150, 20, I18n.func_135053_a("options.displayArmorInInfo")));
      this.field_73887_h.add(new CustomizationGUI$7(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 50, 150, 20, I18n.func_135053_a("options.render3DSkins")));
      this.field_73887_h.add(new CustomizationGUI$8(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 50, 150, 20, I18n.func_135053_a("options.displayNotifications")));
      this.field_73887_h.add(new CustomizationGUI$9(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 75, 150, 20, I18n.func_135053_a("options.displayFurnitures")));
      this.field_73887_h.add(new CustomizationGUI$10(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 75, 150, 20, I18n.func_135053_a("options.customArmor")));
      this.field_73887_h.add(new CustomizationGUI$11(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 100, 150, 20, I18n.func_135053_a("options.pictureframe")));
      EnumOptions options = EnumOptions.SHOW_CAPE;
      this.field_73887_h.add(new CustomizationGUI$12(this, 1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 100, 150, 20, I18n.func_135053_a(options.func_74378_d()), options));
      this.field_73887_h.add(new CustomizationGUI$13(this, 1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 125, 150, 20, I18n.func_135053_a("options.displayEmotes")));
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 165, I18n.func_135053_a("gui.done")));
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton.field_73741_f == 0) {
         this.field_73882_e.func_71373_a(this.previous);
      }

   }

   // $FF: synthetic method
   static boolean access$002(CustomizationGUI x0, boolean x1) {
      return x0.displayRestartMessage = x1;
   }

   // $FF: synthetic method
   static Minecraft access$100(CustomizationGUI x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$200(CustomizationGUI x0) {
      return x0.field_73882_e;
   }
}
