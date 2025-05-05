package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FactionSelectGui$FactionButton;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui$Data;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernScrollBar;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class FactionSelectGui extends AbstractFirstConnectionGui {

   private boolean createFaction;
   private FirstConnectionChoiceGui$Data data;
   private List<FactionSelectGui$FactionButton> factionButtonList = new ArrayList();
   private ModernScrollBar modernScrollBar;
   private List<String> textList = new ArrayList();
   private String infoText;


   public FactionSelectGui(boolean createFaction, FirstConnectionChoiceGui$Data data) {
      this.createFaction = createFaction;
      this.data = data;
      if(createFaction) {
         this.infoText = "Astuce : Une fois ton pays cr\u00e9\u00e9, tu seras t\u00e9l\u00e9port\u00e9 sur ta terre promise !";
      } else {
         this.infoText = "Astuce : Tu sera automatiquement t\u00e9l\u00e9port\u00e9 au spawn de ton pays.";
      }

      this.updateText();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.factionButtonList.clear();
      List list = this.createFaction?this.data.getAvailableFactions():this.data.getOpenFactions();
      int i = 0;

      for(Iterator var3 = list.iterator(); var3.hasNext(); ++i) {
         String factionName = (String)var3.next();
         this.factionButtonList.add(new FactionSelectGui$FactionButton(this, factionName, this.field_73880_f / 2 - 89, this.field_73881_g / 2 - 26 + i * 12));
      }

      if(this.factionButtonList.size() > 6) {
         this.modernScrollBar = new ModernScrollBar((float)(this.field_73880_f / 2 + 22), (float)(this.field_73881_g / 2 - 40), 3, 95, 3);
      } else {
         this.modernScrollBar = null;
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float truc) {
      super.func_73863_a(mouseX, mouseY, truc);
      this.drawGreyRectangle(this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 40, 110, 95);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      float scale = 0.35F;
      GL11.glTranslatef((float)(this.field_73880_f / 2 + 100) - scale * 155.0F - 2.0F, (float)(this.field_73881_g / 2 + 64) - 144.0F * scale - 2.0F, 0.0F);
      GL11.glScalef(scale, scale, 1.0F);
      this.func_73729_b(0, 0, 40, 112, 155, 144);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      String text = this.createFaction?"Cr\u00e9er mon propre pays":"Rejoindre un pays";
      GL11.glTranslatef((float)(this.field_73880_f / 2 - 90 + 55) - (float)this.field_73882_e.field_71466_p.func_78256_a(text) / 2.0F * 0.75F, (float)(this.field_73881_g / 2 - 37), 0.0F);
      GL11.glScalef(0.75F, 0.75F, 0.75F);
      this.field_73882_e.field_71466_p.func_78276_b(text, 0, 0, -1);
      GL11.glPopMatrix();
      int x = this.field_73880_f / 2 - 90;
      int y = this.field_73881_g / 2 - 28;
      func_73734_a(x + 4, y, x + 106, y + 1, -1);
      int decal = 0;
      if(this.modernScrollBar != null) {
         this.modernScrollBar.draw(mouseX, mouseY);
         decal = this.getDecal();
      }

      GUIUtils.startGLScissor(this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 26, 109, 80);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, (float)(-decal), 0.0F);
      Iterator i = this.factionButtonList.iterator();

      while(i.hasNext()) {
         FactionSelectGui$FactionButton factionButton = (FactionSelectGui$FactionButton)i.next();
         factionButton.draw(mouseX, mouseY + decal);
      }

      GL11.glPopMatrix();
      GUIUtils.endGLScissor();
      int var12 = 0;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.field_73880_f / 2 + 23), (float)(this.field_73881_g / 2 - 40), 0.0F);
      GL11.glScalef(0.75F, 0.75F, 0.75F);

      for(Iterator var13 = this.textList.iterator(); var13.hasNext(); ++var12) {
         String mText = (String)var13.next();
         this.field_73882_e.field_71466_p.func_78276_b(mText, 0, var12 * 8, -1);
      }

      GL11.glPopMatrix();
   }

   private void updateText() {
      this.textList.clear();
      StringBuilder sub = new StringBuilder();
      String[] words = this.infoText.split(" ");
      String[] var3 = words;
      int var4 = words.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String word = var3[var5];
         if(sub.length() + word.length() <= 18) {
            if(!Objects.equals(words[0], word)) {
               sub.append(' ');
            }

            sub.append(word);
         } else {
            this.textList.add(sub.toString());
            sub = new StringBuilder(word);
         }
      }

      if(sub.length() > 0) {
         this.textList.add(sub.toString());
      }

   }

   private int getDecal() {
      return (int)(this.modernScrollBar.getSliderValue() * (float)(this.factionButtonList.size() - 6) * 12.0F);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mode) {
      super.func_73864_a(mouseX, mouseY, mode);
      Iterator var4 = this.factionButtonList.iterator();

      while(var4.hasNext()) {
         FactionSelectGui$FactionButton factionButton = (FactionSelectGui$FactionButton)var4.next();
         factionButton.click(mouseX, mouseY + (this.modernScrollBar != null?this.getDecal():0));
      }

   }

   // $FF: synthetic method
   static Minecraft access$000(FactionSelectGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static boolean access$100(FactionSelectGui x0) {
      return x0.createFaction;
   }

   // $FF: synthetic method
   static Minecraft access$200(FactionSelectGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$300(FactionSelectGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$400(FactionSelectGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$500(FactionSelectGui x0) {
      return x0.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft access$600(FactionSelectGui x0) {
      return x0.field_73882_e;
   }
}
