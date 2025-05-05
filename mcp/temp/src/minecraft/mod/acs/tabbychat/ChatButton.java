package acs.tabbychat;

import acs.tabbychat.ChatChannel;
import acs.tabbychat.TabbyChat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class ChatButton extends GuiButton {

   public ChatChannel channel;
   public static RenderItem itemRenderer = new RenderItem();


   public ChatButton() {
      super(9999, 0, 0, 1, 1, "");
   }

   public ChatButton(int _id, int _x, int _y, int _w, int _h, String _title) {
      super(_id, _x, _y, _w, _h, _title);
   }

   protected int width() {
      return this.field_73747_a;
   }

   protected void width(int _w) {
      this.field_73747_a = _w;
   }

   protected int height() {
      return this.field_73745_b;
   }

   protected void height(int _h) {
      this.field_73745_b = _h;
   }

   public void clear() {
      this.channel = null;
   }

   public boolean func_73736_c(Minecraft mc, int par2, int par3) {
      float scaleSetting = TabbyChat.gnc.getScaleSetting();
      int adjY = (int)((float)(mc.field_71462_r.field_73881_g - this.field_73743_d - 28) * (1.0F - scaleSetting)) + this.field_73743_d;
      int adjX = (int)((float)(this.field_73746_c - 5) * scaleSetting) + 5;
      int adjW = (int)((float)this.field_73747_a * scaleSetting);
      int adjH = (int)((float)this.field_73745_b * scaleSetting);
      return this.field_73742_g && this.field_73748_h && par2 >= adjX && par3 >= adjY && par2 < adjX + adjW && par3 < adjY + adjH;
   }

   public void func_73737_a(Minecraft mc, int cursorX, int cursorY) {
      if(this.field_73748_h) {
         FontRenderer fr = mc.field_71466_p;
         float _mult = mc.field_71474_y.field_74357_r * 0.9F + 0.1F;
         int _opacity = (int)(255.0F * _mult);
         float scaleSetting = TabbyChat.gnc.getScaleSetting();
         int adjY = (int)((float)(mc.field_71462_r.field_73881_g - this.field_73743_d - 28) * (1.0F - scaleSetting)) + this.field_73743_d;
         int adjX = (int)((float)(this.field_73746_c - 5) * scaleSetting) + 5;
         int adjW = (int)((float)this.field_73747_a * scaleSetting);
         int adjH = (int)((float)this.field_73745_b * scaleSetting);
         boolean hovered = cursorX >= adjX && cursorY >= adjY && cursorX < adjX + adjW && cursorY < adjY + adjH;
         int var7 = 10526880;
         int var8 = 0;
         if(!this.field_73742_g) {
            var7 = -6250336;
         } else if(hovered) {
            var7 = 16777120;
            var8 = 8355922;
         } else if(this.channel.active) {
            var7 = 10872804;
            var8 = 5995643;
         } else if(this.channel.unread) {
            var7 = 16711680;
            var8 = 7471104;
         }

         func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, var8 + (_opacity / 2 << 24));
         GL11.glEnable(3042);
         String label = this.field_73744_e;
         boolean hasBracket = this.field_73744_e.contains("[");
         boolean hasChevron = this.field_73744_e.contains("<");
         String customLabel = I18n.func_135053_a("chat.tab." + this.field_73744_e.toLowerCase().replace(" ", "_").replace("[", "").replace("]", "").replace("<", "").replace(">", ""));
         if(hasBracket) {
            customLabel = "[" + customLabel + "]";
         }

         if(hasChevron) {
            customLabel = "<" + customLabel + ">";
         }

         if(this.field_73744_e.contains("ALL")) {
            label = "\u00a75" + customLabel;
         } else if(this.field_73744_e.contains("ENE")) {
            label = "\u00a7c" + customLabel;
         } else if(this.field_73744_e.contains("Mon pays")) {
            label = "\u00a7a" + customLabel;
         } else if(this.field_73744_e.contains("ADMIN")) {
            label = "\u00a74" + customLabel;
         } else if(this.field_73744_e.contains("MODO")) {
            label = "\u00a72" + customLabel;
         } else if(this.field_73744_e.contains("Police")) {
            label = "\u00a71" + customLabel;
         } else if(this.field_73744_e.contains("Mafia")) {
            label = "\u00a75" + customLabel;
         } else if(this.field_73744_e.contains("Guide")) {
            label = "\u00a7d" + customLabel;
         } else if(this.field_73744_e.contains("Journal")) {
            label = "\u00a7e" + customLabel;
         } else if(this.field_73744_e.contains("Avocat")) {
            label = "\u00a73" + customLabel;
         } else if(this.field_73744_e.contains("Logs")) {
            label = "\u00a74" + customLabel;
         } else if(this.field_73744_e.contains("RP")) {
            label = "\u00a76" + customLabel;
         }

         this.func_73732_a(fr, label, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, var7 + (_opacity << 24));
         if(hovered && !this.field_73744_e.contains("Global")) {
            this.drawHoveringText(Arrays.asList(new String[]{"\u00a77Shift + Click", I18n.func_135053_a("chat.label.to_close")}), this.field_73746_c + 3, this.field_73743_d - 25, fr);
         }
      }

   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2;
         j1 = par3;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(par2 + k > Minecraft.func_71410_x().field_71462_r.field_73880_f) {
            var15 = par2 - (28 + k);
         }

         if(par3 + k1 + 6 > Minecraft.func_71410_x().field_71462_r.field_73881_g) {
            j1 = Minecraft.func_71410_x().field_71462_r.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 500.0F;
         itemRenderer.field_77023_b = 500.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
