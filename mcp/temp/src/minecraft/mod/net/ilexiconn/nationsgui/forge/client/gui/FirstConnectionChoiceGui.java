package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FactionSelectGui;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui$Button;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui$Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class FirstConnectionChoiceGui extends AbstractFirstConnectionGui {

   private FirstConnectionChoiceGui$Data data;


   public FirstConnectionChoiceGui(String serverName) {
      try {
         this.data = (FirstConnectionChoiceGui$Data)(new Gson()).fromJson(new InputStreamReader((new URL("http://api.nationsglory.fr/factions_list.php?server=" + serverName)).openStream(), Charsets.UTF_8), FirstConnectionChoiceGui$Data.class);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public boolean canBeOpen() {
      return !FirstConnectionChoiceGui$Data.access$000(this.data).isEmpty() || !FirstConnectionChoiceGui$Data.access$100(this.data).isEmpty();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new FirstConnectionChoiceGui$Button(this, 0, this.field_73880_f / 2 - 75, this.field_73881_g / 2 - 30, "Rejoindre un pays existant"));
      FirstConnectionChoiceGui$Button button = new FirstConnectionChoiceGui$Button(this, 1, this.field_73880_f / 2 - 75, this.field_73881_g / 2 - 10, "Cr\u00e9er mon propre pays");
      button.field_73742_g = !FirstConnectionChoiceGui$Data.access$000(this.data).isEmpty();
      this.field_73887_h.add(button);
   }

   public void func_73863_a(int par1, int par2, float par3) {
      super.func_73863_a(par1, par2, par3);
      String text = "Il est temps de faire ton choix :";
      this.field_73882_e.field_71466_p.func_78276_b(text, this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 40, -1);
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      GL11.glPushMatrix();
      float scale = 0.375F;
      GL11.glTranslatef((float)(this.field_73880_f / 2) - 112.0F * scale / 2.0F, (float)(this.field_73881_g / 2 + 64) - 144.0F * scale - 2.0F, 0.0F);
      GL11.glScalef(scale, scale, 1.0F);
      this.func_73729_b(0, 0, 40, 112, 216, 144);
      GL11.glPopMatrix();
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      super.func_73875_a(par1GuiButton);
      if(par1GuiButton.field_73741_f == 0 || par1GuiButton.field_73741_f == 1) {
         this.field_73882_e.func_71373_a(new FactionSelectGui(par1GuiButton.field_73741_f == 1, this.data));
      }

   }

   // $FF: synthetic method
   static Minecraft access$200(FirstConnectionChoiceGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$300(FirstConnectionChoiceGui x0) {
      return x0.field_73882_e;
   }
}
