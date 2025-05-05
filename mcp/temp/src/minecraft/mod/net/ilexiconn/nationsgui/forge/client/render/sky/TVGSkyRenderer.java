package net.ilexiconn.nationsgui.forge.client.render.sky;

import cpw.mods.fml.relauncher.ReflectionHelper;
import fr.nationsglory.ngcontent.client.render.NewOpenGlHelper;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class TVGSkyRenderer extends IRenderHandler {

   private static final ResourceLocation textureSkybox = new ResourceLocation("nationsgui", "textures/sky/skybox.png");
   private static final ResourceLocation textureRainbow = new ResourceLocation("nationsgui", "textures/sky/rainbow.png");
   private static final ResourceLocation[] planetTextures = new ResourceLocation[]{new ResourceLocation("nationsgui", "textures/sky/planet0.png"), new ResourceLocation("nationsgui", "textures/sky/planet1.png"), new ResourceLocation("nationsgui", "textures/sky/planet2.png"), new ResourceLocation("nationsgui", "textures/sky/planet3.png"), new ResourceLocation("nationsgui", "textures/sky/planet4.png")};


   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      boolean test = false;
      if(!test) {
         int glSkyList = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.field_71438_f, new String[]{"glSkyList", "field_72771_w", "G"})).intValue();
         int glSkyList2 = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.field_71438_f, new String[]{"glSkyList2", "field_72781_x", "H"})).intValue();
         int starGLCallList = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.field_71438_f, new String[]{"starGLCallList", "field_72772_v", "F"})).intValue();
         GL11.glDisable(3553);
         Vec3 vec3 = world.func_72833_a(mc.field_71451_h, partialTicks);
         float f1 = (float)vec3.field_72450_a;
         float f2 = (float)vec3.field_72448_b;
         float f3 = (float)vec3.field_72449_c;
         float insideVoid = 0.0F;
         if(mc.field_71439_g.field_70163_u <= -2.0D) {
            insideVoid = (float)Math.min(1.0D, -(mc.field_71439_g.field_70163_u + 2.0D) / 30.0D);
         }

         f1 = Math.max(0.0F, f1 - insideVoid);
         f2 = Math.max(0.0F, f2 - insideVoid);
         f3 = Math.max(0.0F, f3 - insideVoid);
         Tessellator tessellator1 = Tessellator.field_78398_a;
         GL11.glDepthMask(false);
         GL11.glEnable(2912);
         GL11.glColor3f(f1, f2, f3);
         GL11.glCallList(glSkyList);
         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         NewOpenGlHelper.glBlendFunc(770, 771, 1, 0);
         RenderHelper.func_74518_a();
         float[] afloat = world.field_73011_w.func_76560_a(world.func_72826_c(partialTicks), partialTicks);
         float f6;
         float f7;
         float f8;
         float celAng;
         float a;
         if(afloat != null) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.func_76126_a(world.func_72929_e(partialTicks)) < 0.0F?180.0F:0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            f6 = afloat[0];
            f7 = afloat[1];
            f8 = afloat[2];
            tessellator1.func_78371_b(6);
            tessellator1.func_78369_a(f6, f7, f8, afloat[3] * (1.0F - insideVoid));
            tessellator1.func_78377_a(0.0D, 100.0D, 0.0D);
            byte effCelAng = 16;
            tessellator1.func_78369_a(afloat[0], afloat[1], afloat[2], 0.0F);

            for(int lowA = 0; lowA <= effCelAng; ++lowA) {
               celAng = (float)lowA * 3.1415927F * 2.0F / (float)effCelAng;
               a = MathHelper.func_76126_a(celAng);
               float angles = MathHelper.func_76134_b(celAng);
               tessellator1.func_78377_a((double)(a * 120.0F), (double)(angles * 120.0F), (double)(-angles * 40.0F * afloat[3]));
            }

            tessellator1.func_78381_a();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
         }

         GL11.glEnable(3553);
         GL11.glPushMatrix();
         f6 = Math.max(0.2F, 1.0F - world.func_72867_j(partialTicks)) * (1.0F - insideVoid);
         f7 = 0.0F;
         f8 = 0.0F;
         float f9 = 0.0F;
         GL11.glTranslatef(f7, f8, f9);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         celAng = world.func_72826_c(partialTicks);
         float var52 = celAng;
         if((double)celAng > 0.5D) {
            var52 = 0.5F - (celAng - 0.5F);
         }

         float f10 = 20.0F;
         float var53 = Math.max(0.0F, var52 - 0.3F) * f6;
         a = Math.max(0.1F, var53);
         NewOpenGlHelper.glBlendFunc(770, 771, 1, 0);
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, a * 4.0F * (1.0F - insideVoid));
         GL11.glRotatef(90.0F, 0.5F, 0.5F, 0.0F);

         for(int var54 = 0; var54 < planetTextures.length; ++var54) {
            mc.field_71446_o.func_110577_a(planetTextures[var54]);
            this.drawObject(tessellator1, f10);
            switch(var54) {
            case 0:
               GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
               f10 = 12.0F;
               break;
            case 1:
               GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
               f10 = 15.0F;
               break;
            case 2:
               GL11.glRotatef(80.0F, 1.0F, 0.0F, 1.0F);
               f10 = 25.0F;
               break;
            case 3:
               GL11.glRotatef(100.0F, 0.0F, 0.0F, 1.0F);
               f10 = 10.0F;
               break;
            case 4:
               GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.5F);
               f10 = 40.0F;
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
         mc.field_71446_o.func_110577_a(textureSkybox);
         f10 = 20.0F;
         a = var53;
         GL11.glPushMatrix();
         NewOpenGlHelper.glBlendFunc(770, 1, 1, 0);
         GL11.glTranslatef(0.0F, -1.0F, 0.0F);
         GL11.glRotatef(220.0F, 1.0F, 0.0F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var53);
         byte var55 = 90;
         float s = 3.0F;
         float m = 1.0F;
         float y = 2.0F;
         float y0 = 0.0F;
         float uPer = 0.0027777778F;
         float anglePer = 360.0F / (float)var55;
         double fuzzPer = 31.41592653589793D / (double)var55;
         float rotSpeed = 1.0F;
         float rotSpeedMod = 0.4F;

         int day;
         for(int effCelAng1 = 0; effCelAng1 < 3; ++effCelAng1) {
            float time = rotSpeed * rotSpeedMod * ((float)ClientTickHandler.tick + partialTicks);
            GL11.glRotatef(((float)ClientTickHandler.tick + partialTicks) * 0.25F * rotSpeed * rotSpeedMod, 0.0F, 1.0F, 0.0F);
            tessellator1.func_78382_b();

            for(int i = 0; i < var55; ++i) {
               day = i;
               if(i % 2 == 0) {
                  day = i - 1;
               }

               float rand = (float)day * anglePer + time;
               double angle1 = Math.cos((double)rand * 3.141592653589793D / 180.0D) * (double)f10;
               double t = Math.sin((double)rand * 3.141592653589793D / 180.0D) * (double)f10;
               double ang = Math.sin(fuzzPer * (double)day) * 1.0D;
               float ut = rand * uPer;
               if(i % 2 == 0) {
                  tessellator1.func_78374_a(angle1, ang + (double)y0 + (double)y, t, (double)ut, 1.0D);
                  tessellator1.func_78374_a(angle1, ang + (double)y0, t, (double)ut, 0.0D);
               } else {
                  tessellator1.func_78374_a(angle1, ang + (double)y0, t, (double)ut, 0.0D);
                  tessellator1.func_78374_a(angle1, ang + (double)y0 + (double)y, t, (double)ut, 1.0D);
               }
            }

            tessellator1.func_78381_a();
            switch(effCelAng1) {
            case 0:
               GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
               GL11.glColor4f(1.0F, 0.4F, 0.4F, a);
               fuzzPer = 43.982297150257104D / (double)var55;
               rotSpeed = 0.2F;
               break;
            case 1:
               GL11.glRotatef(50.0F, 1.0F, 0.0F, 0.0F);
               GL11.glColor4f(0.4F, 1.0F, 0.7F, a);
               fuzzPer = 18.84955592153876D / (double)var55;
               rotSpeed = 2.0F;
            }
         }

         GL11.glPopMatrix();
         GL11.glPushMatrix();
         NewOpenGlHelper.glBlendFunc(770, 771, 1, 0);
         mc.field_71446_o.func_110577_a(textureRainbow);
         f10 = 10.0F;
         float var56 = celAng;
         if(celAng > 0.25F) {
            var56 = 1.0F - celAng;
         }

         var56 = 0.25F - Math.min(0.25F, var56);
         long var57 = world.func_72820_D() + 1000L;
         day = (int)(var57 / 24000L);
         Random var58 = new Random((long)(day * 255));
         float var59 = var58.nextFloat() * 360.0F;
         float angle2 = var58.nextFloat() * 360.0F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var56 * (1.0F - insideVoid));
         GL11.glRotatef(var59, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(angle2, 0.0F, 0.0F, 1.0F);
         tessellator1.func_78382_b();

         for(int var60 = 0; var60 < var55; ++var60) {
            int j = var60;
            if(var60 % 2 == 0) {
               j = var60 - 1;
            }

            float var62 = (float)j * anglePer;
            double xp = Math.cos((double)var62 * 3.141592653589793D / 180.0D) * (double)f10;
            double zp = Math.sin((double)var62 * 3.141592653589793D / 180.0D) * (double)f10;
            double yo = 0.0D;
            float ut1 = var62 * uPer;
            if(var60 % 2 == 0) {
               tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut1, 1.0D);
               tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut1, 0.0D);
            } else {
               tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut1, 0.0D);
               tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut1, 1.0D);
            }
         }

         tessellator1.func_78381_a();
         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - insideVoid);
         NewOpenGlHelper.glBlendFunc(770, 1, 1, 0);
         f6 *= Math.max(0.1F, var52 * 2.0F);
         float var61 = ((float)ClientTickHandler.tick + partialTicks + 2000.0F) * 0.005F;
         GL11.glPushMatrix();
         GL11.glDisable(3553);
         GL11.glPushMatrix();
         GL11.glRotatef(var61 * 3.0F, 0.0F, 1.0F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(var61, 0.0F, 1.0F, 0.0F);
         GL11.glColor4f(0.5F, 1.0F, 1.0F, f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(var61 * 2.0F, 0.0F, 1.0F, 0.0F);
         GL11.glColor4f(1.0F, 0.75F, 0.75F, f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(var61 * 3.0F, 0.0F, 0.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F * f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(var61, 0.0F, 0.0F, 1.0F);
         GL11.glColor4f(0.5F, 1.0F, 1.0F, 0.25F * f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(var61 * 2.0F, 0.0F, 0.0F, 1.0F);
         GL11.glColor4f(1.0F, 0.75F, 0.75F, 0.25F * f6);
         GL11.glCallList(starGLCallList);
         GL11.glPopMatrix();
         GL11.glEnable(3553);
         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(3008);
         GL11.glEnable(2912);
         GL11.glPopMatrix();
         GL11.glDepthMask(true);
      }
   }

   private void drawObject(Tessellator tess, float f10) {
      tess.func_78382_b();
      tess.func_78374_a((double)(-f10), 100.0D, (double)(-f10), 0.0D, 0.0D);
      tess.func_78374_a((double)f10, 100.0D, (double)(-f10), 1.0D, 0.0D);
      tess.func_78374_a((double)f10, 100.0D, (double)f10, 1.0D, 1.0D);
      tess.func_78374_a((double)(-f10), 100.0D, (double)f10, 0.0D, 1.0D);
      tess.func_78381_a();
   }

}
