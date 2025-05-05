package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class AssistanceButton extends GuiButton {

   private int uMin = 0;
   private int uMax = 157;
   private int v = 256;


   public AssistanceButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
      super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
   }

   public AssistanceButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
      super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
   }

   protected int func_73738_a(boolean par1) {
      byte b0 = 0;
      if(!this.field_73742_g) {
         b0 = 2;
      } else if(par1) {
         b0 = 1;
      }

      return b0;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         FontRenderer fontrenderer = par1Minecraft.field_71466_p;
         par1Minecraft.func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         int k = this.func_73738_a(this.field_82253_i);
         this.func_73729_b(this.field_73746_c, this.field_73743_d, this.uMin, this.v + k * 20, this.field_73747_a / 2, this.field_73745_b);
         this.func_73729_b(this.field_73746_c + this.field_73747_a / 2, this.field_73743_d, this.uMax - this.field_73747_a / 2, this.v + k * 20, this.field_73747_a / 2, this.field_73745_b);
         this.func_73739_b(par1Minecraft, par2, par3);
         int l = 14737632;
         if(!this.field_73742_g) {
            l = -6250336;
         } else if(this.field_82253_i) {
            l = 16777120;
         }

         this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, l);
      }

   }

   public void func_73729_b(int posX, int posY, int u, int v, int width, int height) {
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

   public void setUVMap(int uMin, int uMax, int v) {
      this.uMin = uMin;
      this.uMax = uMax;
      this.v = v;
   }
}
