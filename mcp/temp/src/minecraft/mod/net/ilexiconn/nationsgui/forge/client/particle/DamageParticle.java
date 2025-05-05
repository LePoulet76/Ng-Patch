package net.ilexiconn.nationsgui.forge.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DamageParticle extends EntityFX {

   protected static final float SIZE = 3.0F;
   protected boolean grow = true;
   private float damage;
   private String text;
   protected boolean shouldOnTop = true;
   protected float scale = 1.0F;


   public DamageParticle(World p_i1219_1_, double p_i1219_2_, double p_i1219_4_, double p_i1219_6_, double p_i1219_8_, double p_i1219_10_, double p_i1219_12_, float damage) {
      super(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_, p_i1219_8_, p_i1219_10_, p_i1219_12_);
      this.damage = damage;
      this.text = Integer.toString((int)Math.abs(damage));
   }

   public void func_70539_a(Tessellator tessellator, float x, float y, float z, float dX, float dY, float dZ) {
      this.scale = 1.0F;
      float rotationYaw = -Minecraft.func_71410_x().field_71439_g.field_70177_z;
      float rotationPitch = Minecraft.func_71410_x().field_71439_g.field_70125_A;
      float locX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)x - field_70556_an);
      float locY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)y - field_70554_ao);
      float locZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)z - field_70555_ap);
      GL11.glPushMatrix();
      if(this.shouldOnTop) {
         GL11.glDepthFunc(519);
      } else {
         GL11.glDepthFunc(515);
      }

      GL11.glTranslatef(locX, locY, locZ);
      GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glScaled((double)this.field_70544_f * 0.008D, (double)this.field_70544_f * 0.008D, (double)this.field_70544_f * 0.008D);
      GL11.glScaled((double)this.scale, (double)this.scale, (double)this.scale);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 0.003662109F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2896);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int color = 16711680;
      if(this.damage < 0.0F) {
         color = '\uff00';
      }

      FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
      fontRenderer.func_78261_a(this.text, -MathHelper.func_76141_d((float)fontRenderer.func_78256_a(this.text) / 2.0F) + 1, -MathHelper.func_76141_d((float)fontRenderer.field_78288_b / 2.0F) + 1, color);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthFunc(515);
      GL11.glPopMatrix();
      if(this.grow) {
         this.field_70544_f *= 1.08F;
         if((double)this.field_70544_f > 9.0D) {
            this.grow = false;
         }
      } else {
         this.field_70544_f *= 0.96F;
      }

   }

   public int func_70537_b() {
      return 3;
   }
}
