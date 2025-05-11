/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  fr.nationsglory.ngcontent.client.render.NewOpenGlHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.IRenderHandler
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class TVGSkyRenderer
extends IRenderHandler {
    private static final ResourceLocation textureSkybox = new ResourceLocation("nationsgui", "textures/sky/skybox.png");
    private static final ResourceLocation textureRainbow = new ResourceLocation("nationsgui", "textures/sky/rainbow.png");
    private static final ResourceLocation[] planetTextures = new ResourceLocation[]{new ResourceLocation("nationsgui", "textures/sky/planet0.png"), new ResourceLocation("nationsgui", "textures/sky/planet1.png"), new ResourceLocation("nationsgui", "textures/sky/planet2.png"), new ResourceLocation("nationsgui", "textures/sky/planet3.png"), new ResourceLocation("nationsgui", "textures/sky/planet4.png")};

    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        float celAng;
        float f8;
        float f7;
        float f6;
        boolean test = false;
        if (test) {
            return;
        }
        int glSkyList = (Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, (Object)mc.field_71438_f, (String[])new String[]{"glSkyList", "field_72771_w", "G"});
        int glSkyList2 = (Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, (Object)mc.field_71438_f, (String[])new String[]{"glSkyList2", "field_72781_x", "H"});
        int starGLCallList = (Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, (Object)mc.field_71438_f, (String[])new String[]{"starGLCallList", "field_72772_v", "F"});
        GL11.glDisable((int)3553);
        Vec3 vec3 = world.func_72833_a((Entity)mc.field_71451_h, partialTicks);
        float f1 = (float)vec3.field_72450_a;
        float f2 = (float)vec3.field_72448_b;
        float f3 = (float)vec3.field_72449_c;
        float insideVoid = 0.0f;
        if (mc.field_71439_g.field_70163_u <= -2.0) {
            insideVoid = (float)Math.min(1.0, -(mc.field_71439_g.field_70163_u + 2.0) / 30.0);
        }
        f1 = Math.max(0.0f, f1 - insideVoid);
        f2 = Math.max(0.0f, f2 - insideVoid);
        f3 = Math.max(0.0f, f3 - insideVoid);
        Tessellator tessellator1 = Tessellator.field_78398_a;
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2912);
        GL11.glColor3f((float)f1, (float)f2, (float)f3);
        GL11.glCallList((int)glSkyList);
        GL11.glDisable((int)2912);
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        NewOpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        RenderHelper.func_74518_a();
        float[] afloat = world.field_73011_w.func_76560_a(world.func_72826_c(partialTicks), partialTicks);
        if (afloat != null) {
            GL11.glDisable((int)3553);
            GL11.glShadeModel((int)7425);
            GL11.glPushMatrix();
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(MathHelper.func_76126_a((float)world.func_72929_e(partialTicks)) < 0.0f ? 180.0f : 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            f6 = afloat[0];
            f7 = afloat[1];
            f8 = afloat[2];
            tessellator1.func_78371_b(6);
            tessellator1.func_78369_a(f6, f7, f8, afloat[3] * (1.0f - insideVoid));
            tessellator1.func_78377_a(0.0, 100.0, 0.0);
            int b0 = 16;
            tessellator1.func_78369_a(afloat[0], afloat[1], afloat[2], 0.0f);
            for (int j = 0; j <= b0; ++j) {
                float f11 = (float)j * (float)Math.PI * 2.0f / (float)b0;
                float f12 = MathHelper.func_76126_a((float)f11);
                float f13 = MathHelper.func_76134_b((float)f11);
                tessellator1.func_78377_a((double)(f12 * 120.0f), (double)(f13 * 120.0f), (double)(-f13 * 40.0f * afloat[3]));
            }
            tessellator1.func_78381_a();
            GL11.glPopMatrix();
            GL11.glShadeModel((int)7424);
        }
        GL11.glEnable((int)3553);
        GL11.glPushMatrix();
        f6 = Math.max(0.2f, 1.0f - world.func_72867_j(partialTicks)) * (1.0f - insideVoid);
        f7 = 0.0f;
        f8 = 0.0f;
        float f9 = 0.0f;
        GL11.glTranslatef((float)f7, (float)f8, (float)f9);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float effCelAng = celAng = world.func_72826_c(partialTicks);
        if ((double)celAng > 0.5) {
            effCelAng = 0.5f - (celAng - 0.5f);
        }
        float f10 = 20.0f;
        float lowA = Math.max(0.0f, effCelAng - 0.3f) * f6;
        float a = Math.max(0.1f, lowA);
        NewOpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        GL11.glPushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(a * 4.0f * (1.0f - insideVoid)));
        GL11.glRotatef((float)90.0f, (float)0.5f, (float)0.5f, (float)0.0f);
        block12: for (int p = 0; p < planetTextures.length; ++p) {
            mc.field_71446_o.func_110577_a(planetTextures[p]);
            this.drawObject(tessellator1, f10);
            switch (p) {
                case 0: {
                    GL11.glRotatef((float)70.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    f10 = 12.0f;
                    continue block12;
                }
                case 1: {
                    GL11.glRotatef((float)120.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    f10 = 15.0f;
                    continue block12;
                }
                case 2: {
                    GL11.glRotatef((float)80.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                    f10 = 25.0f;
                    continue block12;
                }
                case 3: {
                    GL11.glRotatef((float)100.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    f10 = 10.0f;
                    continue block12;
                }
                case 4: {
                    GL11.glRotatef((float)-60.0f, (float)1.0f, (float)0.0f, (float)0.5f);
                    f10 = 40.0f;
                }
            }
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        mc.field_71446_o.func_110577_a(textureSkybox);
        f10 = 20.0f;
        a = lowA;
        GL11.glPushMatrix();
        NewOpenGlHelper.glBlendFunc((int)770, (int)1, (int)1, (int)0);
        GL11.glTranslatef((float)0.0f, (float)-1.0f, (float)0.0f);
        GL11.glRotatef((float)220.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)a);
        int angles = 90;
        float s = 3.0f;
        float m = 1.0f;
        float y = 2.0f;
        float y0 = 0.0f;
        float uPer = 0.0027777778f;
        float anglePer = 360.0f / (float)angles;
        double fuzzPer = Math.PI * 10 / (double)angles;
        float rotSpeed = 1.0f;
        float rotSpeedMod = 0.4f;
        block13: for (int p = 0; p < 3; ++p) {
            float baseAngle = rotSpeed * rotSpeedMod * ((float)ClientTickHandler.tick + partialTicks);
            GL11.glRotatef((float)(((float)ClientTickHandler.tick + partialTicks) * 0.25f * rotSpeed * rotSpeedMod), (float)0.0f, (float)1.0f, (float)0.0f);
            tessellator1.func_78382_b();
            for (int i = 0; i < angles; ++i) {
                int j = i;
                if (i % 2 == 0) {
                    --j;
                }
                float ang = (float)j * anglePer + baseAngle;
                double xp = Math.cos((double)ang * Math.PI / 180.0) * (double)f10;
                double zp = Math.sin((double)ang * Math.PI / 180.0) * (double)f10;
                double yo = Math.sin(fuzzPer * (double)j) * 1.0;
                float ut = ang * uPer;
                if (i % 2 == 0) {
                    tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut, 1.0);
                    tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut, 0.0);
                    continue;
                }
                tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut, 0.0);
                tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut, 1.0);
            }
            tessellator1.func_78381_a();
            switch (p) {
                case 0: {
                    GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glColor4f((float)1.0f, (float)0.4f, (float)0.4f, (float)a);
                    fuzzPer = 43.982297150257104 / (double)angles;
                    rotSpeed = 0.2f;
                    continue block13;
                }
                case 1: {
                    GL11.glRotatef((float)50.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glColor4f((float)0.4f, (float)1.0f, (float)0.7f, (float)a);
                    fuzzPer = Math.PI * 6 / (double)angles;
                    rotSpeed = 2.0f;
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        NewOpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        mc.field_71446_o.func_110577_a(textureRainbow);
        f10 = 10.0f;
        float effCelAng1 = celAng;
        if (effCelAng1 > 0.25f) {
            effCelAng1 = 1.0f - effCelAng1;
        }
        effCelAng1 = 0.25f - Math.min(0.25f, effCelAng1);
        long time = world.func_72820_D() + 1000L;
        int day = (int)(time / 24000L);
        Random rand = new Random(day * 255);
        float angle1 = rand.nextFloat() * 360.0f;
        float angle2 = rand.nextFloat() * 360.0f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(effCelAng1 * (1.0f - insideVoid)));
        GL11.glRotatef((float)angle1, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)angle2, (float)0.0f, (float)0.0f, (float)1.0f);
        tessellator1.func_78382_b();
        for (int i = 0; i < angles; ++i) {
            int j = i;
            if (i % 2 == 0) {
                --j;
            }
            float ang = (float)j * anglePer;
            double xp = Math.cos((double)ang * Math.PI / 180.0) * (double)f10;
            double zp = Math.sin((double)ang * Math.PI / 180.0) * (double)f10;
            double yo = 0.0;
            float ut = ang * uPer;
            if (i % 2 == 0) {
                tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut, 1.0);
                tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut, 0.0);
                continue;
            }
            tessellator1.func_78374_a(xp, yo + (double)y0, zp, (double)ut, 0.0);
            tessellator1.func_78374_a(xp, yo + (double)y0 + (double)y, zp, (double)ut, 1.0);
        }
        tessellator1.func_78381_a();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(1.0f - insideVoid));
        NewOpenGlHelper.glBlendFunc((int)770, (int)1, (int)1, (int)0);
        f6 *= Math.max(0.1f, effCelAng * 2.0f);
        float t = ((float)ClientTickHandler.tick + partialTicks + 2000.0f) * 0.005f;
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glPushMatrix();
        GL11.glRotatef((float)(t * 3.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)f6);
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)t, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glColor4f((float)0.5f, (float)1.0f, (float)1.0f, (float)f6);
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)(t * 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)0.75f, (float)0.75f, (float)f6);
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)(t * 3.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(0.25f * f6));
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)t, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glColor4f((float)0.5f, (float)1.0f, (float)1.0f, (float)(0.25f * f6));
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)(t * 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)0.75f, (float)0.75f, (float)(0.25f * f6));
        GL11.glCallList((int)starGLCallList);
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)2912);
        GL11.glPopMatrix();
        GL11.glDepthMask((boolean)true);
    }

    private void drawObject(Tessellator tess, float f10) {
        tess.func_78382_b();
        tess.func_78374_a((double)(-f10), 100.0, (double)(-f10), 0.0, 0.0);
        tess.func_78374_a((double)f10, 100.0, (double)(-f10), 1.0, 0.0);
        tess.func_78374_a((double)f10, 100.0, (double)f10, 1.0, 1.0);
        tess.func_78374_a((double)(-f10), 100.0, (double)f10, 0.0, 1.0);
        tess.func_78381_a();
    }
}

