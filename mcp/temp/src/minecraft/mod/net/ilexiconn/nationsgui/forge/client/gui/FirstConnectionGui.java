package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui$Button;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui$Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class FirstConnectionGui extends AbstractFirstConnectionGui {

   private List<FirstConnectionGui$Screen> screenList = null;
   private List<String> textList = new ArrayList();
   private int currentScreen = 0;
   private String serverName;


   public FirstConnectionGui(String serverName) {
      this.serverName = serverName;

      try {
         Type e = (new FirstConnectionGui$1(this)).getType();
         this.screenList = (List)(new Gson()).fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/welcome.json")).openStream(), Charsets.UTF_8), e);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      this.updateText();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new FirstConnectionGui$Button(this, this.field_73880_f / 2 + (FirstConnectionGui$Screen.access$000((FirstConnectionGui$Screen)this.screenList.get(this.currentScreen)).equals("girl")?35:75), this.field_73881_g / 2 + 15));
   }

   private int getCharacterLimit(int line) {
      return FirstConnectionGui$Screen.access$000((FirstConnectionGui$Screen)this.screenList.get(this.currentScreen)).equals("girl")?(line <= 1?180:(line >= 6?117:147)):180;
   }

   private void updateText() {
      this.textList.clear();
      StringBuilder sub = new StringBuilder();
      List lines = FirstConnectionGui$Screen.access$100((FirstConnectionGui$Screen)this.screenList.get(this.currentScreen));
      int i = 0;
      Iterator var4 = lines.iterator();

      while(var4.hasNext()) {
         String line = (String)var4.next();
         String[] words = line.split(" ");
         String[] var7 = words;
         int var8 = words.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String word = var7[var9];
            String temp = (!Objects.equals(words[0], word)?" ":"") + word;
            if(Minecraft.func_71410_x().field_71466_p.func_78256_a(sub.toString()) + Minecraft.func_71410_x().field_71466_p.func_78256_a(temp) <= this.getCharacterLimit(i)) {
               sub.append(temp);
            } else {
               this.textList.add(sub.toString());
               sub = new StringBuilder(word);
               ++i;
            }
         }

         if(!sub.toString().equals("")) {
            this.textList.add(sub.toString());
         }

         if(lines.size() > 1 && !Objects.equals(lines.get(lines.size() - 1), line)) {
            sub = new StringBuilder();
            this.textList.add("");
            ++i;
         }
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      super.func_73863_a(par1, par2, par3);
      this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
      String currentImage = FirstConnectionGui$Screen.access$000((FirstConnectionGui$Screen)this.screenList.get(this.currentScreen));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(currentImage.equals("girl")) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(this.field_73880_f / 2 + 100 - 42 - 2), (float)(this.field_73881_g / 2 + 128 - 144 + 5), 0.0F);
         GL11.glScalef(0.5F, 0.5F, 1.0F);
         this.func_73729_b(0, 0, 40, 112, 85, 144);
         GL11.glPopMatrix();
      } else if(currentImage.equals("warrior")) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(3008);
         GL11.glShadeModel(7425);
         GL11.glPushMatrix();
         float i = 0.6F;
         GL11.glTranslatef((float)(this.field_73880_f / 2) - 194.0F * i / 2.0F, (float)(this.field_73881_g / 2 + 64) - 109.0F * i - 2.0F, 0.0F);
         GL11.glScalef(i, i, 1.0F);
         this.func_73729_b(0, 0, 62, 0, 194, 109);
         GL11.glPopMatrix();
         GL11.glShadeModel(7424);
         GL11.glEnable(3008);
         GL11.glDisable(3042);
      }

      int var8 = 0;

      for(Iterator var6 = this.textList.iterator(); var6.hasNext(); ++var8) {
         String text = (String)var6.next();
         this.field_73882_e.field_71466_p.func_78276_b(text, this.field_73880_f / 2 - 90, this.field_73881_g / 2 + var8 * 9 - 35, -1);
      }

   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      super.func_73875_a(par1GuiButton);
      if(par1GuiButton.field_73741_f == 0) {
         if(this.currentScreen + 1 < this.screenList.size()) {
            ++this.currentScreen;
         } else {
            this.field_73882_e.func_71373_a((GuiScreen)null);
         }

         this.updateText();
         this.field_73887_h.clear();
         this.func_73866_w_();
      }

   }

   // $FF: synthetic method
   static Minecraft access$200(FirstConnectionGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static FontRenderer access$300(FirstConnectionGui x0) {
      return x0.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer access$400(FirstConnectionGui x0) {
      return x0.field_73886_k;
   }
}
