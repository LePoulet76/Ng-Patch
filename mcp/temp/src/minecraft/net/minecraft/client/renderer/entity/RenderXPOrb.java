package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderXPOrb extends Render {

   private static final ResourceLocation field_110785_a = new ResourceLocation("textures/entity/experience_orb.png");


   public RenderXPOrb() {
      this.field_76989_e = 0.15F;
      this.field_76987_f = 0.75F;
   }

   public void func_77005_a(EntityXPOrb p_77005_1_, double p_77005_2_, double p_77005_4_, double p_77005_6_, float p_77005_8_, float p_77005_9_) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)p_77005_2_, (float)p_77005_4_, (float)p_77005_6_);
      this.func_110777_b(p_77005_1_);
      int var10 = p_77005_1_.func_70528_g();
      float var11 = (float)(var10 % 4 * 16 + 0) / 64.0F;
      float var12 = (float)(var10 % 4 * 16 + 16) / 64.0F;
      float var13 = (float)(var10 / 4 * 16 + 0) / 64.0F;
      float var14 = (float)(var10 / 4 * 16 + 16) / 64.0F;
      float var15 = 1.0F;
      float var16 = 0.5F;
      float var17 = 0.25F;
      int var18 = p_77005_1_.func_70070_b(p_77005_9_);
      int var19 = var18 % 65536;
      int var20 = var18 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var19 / 1.0F, (float)var20 / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var26 = 255.0F;
      float var27 = ((float)p_77005_1_.field_70533_a + p_77005_9_) / 2.0F;
      var20 = (int)((MathHelper.func_76126_a(var27 + 0.0F) + 1.0F) * 0.5F * var26);
      int var21 = (int)var26;
      int var22 = (int)((MathHelper.func_76126_a(var27 + 4.1887903F) + 1.0F) * 0.1F * var26);
      int var23 = var20 << 16 | var21 << 8 | var22;
      GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      float var24 = 0.3F;
      GL11.glScalef(var24, var24, var24);
      Tessellator var25 = Tessellator.field_78398_a;
      var25.func_78382_b();
      var25.func_78384_a(var23, 128);
      var25.func_78375_b(0.0F, 1.0F, 0.0F);
      var25.func_78374_a((double)(0.0F - var16), (double)(0.0F - var17), 0.0D, (double)var11, (double)var14);
      var25.func_78374_a((double)(var15 - var16), (double)(0.0F - var17), 0.0D, (double)var12, (double)var14);
      var25.func_78374_a((double)(var15 - var16), (double)(1.0F - var17), 0.0D, (double)var12, (double)var13);
      var25.func_78374_a((double)(0.0F - var16), (double)(1.0F - var17), 0.0D, (double)var11, (double)var13);
      var25.func_78381_a();
      GL11.glDisable(3042);
      GL11.glDisable('\u803a');
      GL11.glPopMatrix();
   }

   protected ResourceLocation func_110784_a(EntityXPOrb p_110784_1_) {
      return field_110785_a;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_110784_a((EntityXPOrb)p_110775_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_77005_a((EntityXPOrb)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
