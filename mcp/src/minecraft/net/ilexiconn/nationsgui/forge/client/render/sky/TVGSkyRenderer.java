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

public class TVGSkyRenderer extends IRenderHandler
{
    private static final ResourceLocation textureSkybox = new ResourceLocation("nationsgui", "textures/sky/skybox.png");
    private static final ResourceLocation textureRainbow = new ResourceLocation("nationsgui", "textures/sky/rainbow.png");
    private static final ResourceLocation[] planetTextures = new ResourceLocation[] {new ResourceLocation("nationsgui", "textures/sky/planet0.png"), new ResourceLocation("nationsgui", "textures/sky/planet1.png"), new ResourceLocation("nationsgui", "textures/sky/planet2.png"), new ResourceLocation("nationsgui", "textures/sky/planet3.png"), new ResourceLocation("nationsgui", "textures/sky/planet4.png")};

    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
        boolean test = false;

        if (!test)
        {
            int glSkyList = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, new String[] {"glSkyList", "glSkyList", "G"})).intValue();
            int glSkyList2 = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, new String[] {"glSkyList2", "glSkyList2", "H"})).intValue();
            int starGLCallList = ((Integer)ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, new String[] {"starGLCallList", "starGLCallList", "F"})).intValue();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
            float f1 = (float)vec3.xCoord;
            float f2 = (float)vec3.yCoord;
            float f3 = (float)vec3.zCoord;
            float insideVoid = 0.0F;

            if (mc.thePlayer.posY <= -2.0D)
            {
                insideVoid = (float)Math.min(1.0D, -(mc.thePlayer.posY + 2.0D) / 30.0D);
            }

            f1 = Math.max(0.0F, f1 - insideVoid);
            f2 = Math.max(0.0F, f2 - insideVoid);
            f3 = Math.max(0.0F, f3 - insideVoid);
            Tessellator tessellator1 = Tessellator.instance;
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glColor3f(f1, f2, f3);
            GL11.glCallList(glSkyList);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            NewOpenGlHelper.glBlendFunc(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
            float f6;
            float f7;
            float f8;
            float celAng;
            float a;

            if (afloat != null)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GL11.glPushMatrix();
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                f6 = afloat[0];
                f7 = afloat[1];
                f8 = afloat[2];
                tessellator1.startDrawing(6);
                tessellator1.setColorRGBA_F(f6, f7, f8, afloat[3] * (1.0F - insideVoid));
                tessellator1.addVertex(0.0D, 100.0D, 0.0D);
                byte effCelAng = 16;
                tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);

                for (int lowA = 0; lowA <= effCelAng; ++lowA)
                {
                    celAng = (float)lowA * (float)Math.PI * 2.0F / (float)effCelAng;
                    a = MathHelper.sin(celAng);
                    float angles = MathHelper.cos(celAng);
                    tessellator1.addVertex((double)(a * 120.0F), (double)(angles * 120.0F), (double)(-angles * 40.0F * afloat[3]));
                }

                tessellator1.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPushMatrix();
            f6 = Math.max(0.2F, 1.0F - world.getRainStrength(partialTicks)) * (1.0F - insideVoid);
            f7 = 0.0F;
            f8 = 0.0F;
            float f9 = 0.0F;
            GL11.glTranslatef(f7, f8, f9);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            celAng = world.getCelestialAngle(partialTicks);
            float var52 = celAng;

            if ((double)celAng > 0.5D)
            {
                var52 = 0.5F - (celAng - 0.5F);
            }

            float f10 = 20.0F;
            float var53 = Math.max(0.0F, var52 - 0.3F) * f6;
            a = Math.max(0.1F, var53);
            NewOpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, a * 4.0F * (1.0F - insideVoid));
            GL11.glRotatef(90.0F, 0.5F, 0.5F, 0.0F);

            for (int var54 = 0; var54 < planetTextures.length; ++var54)
            {
                mc.renderEngine.bindTexture(planetTextures[var54]);
                this.drawObject(tessellator1, f10);

                switch (var54)
                {
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
            mc.renderEngine.bindTexture(textureSkybox);
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

            for (int effCelAng1 = 0; effCelAng1 < 3; ++effCelAng1)
            {
                float time = rotSpeed * rotSpeedMod * ((float)ClientTickHandler.tick + partialTicks);
                GL11.glRotatef(((float)ClientTickHandler.tick + partialTicks) * 0.25F * rotSpeed * rotSpeedMod, 0.0F, 1.0F, 0.0F);
                tessellator1.startDrawingQuads();

                for (int i = 0; i < var55; ++i)
                {
                    day = i;

                    if (i % 2 == 0)
                    {
                        day = i - 1;
                    }

                    float rand = (float)day * anglePer + time;
                    double angle1 = Math.cos((double)rand * Math.PI / 180.0D) * (double)f10;
                    double t = Math.sin((double)rand * Math.PI / 180.0D) * (double)f10;
                    double ang = Math.sin(fuzzPer * (double)day) * 1.0D;
                    float ut = rand * uPer;

                    if (i % 2 == 0)
                    {
                        tessellator1.addVertexWithUV(angle1, ang + (double)y0 + (double)y, t, (double)ut, 1.0D);
                        tessellator1.addVertexWithUV(angle1, ang + (double)y0, t, (double)ut, 0.0D);
                    }
                    else
                    {
                        tessellator1.addVertexWithUV(angle1, ang + (double)y0, t, (double)ut, 0.0D);
                        tessellator1.addVertexWithUV(angle1, ang + (double)y0 + (double)y, t, (double)ut, 1.0D);
                    }
                }

                tessellator1.draw();

                switch (effCelAng1)
                {
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
            mc.renderEngine.bindTexture(textureRainbow);
            f10 = 10.0F;
            float var56 = celAng;

            if (celAng > 0.25F)
            {
                var56 = 1.0F - celAng;
            }

            var56 = 0.25F - Math.min(0.25F, var56);
            long var57 = world.getWorldTime() + 1000L;
            day = (int)(var57 / 24000L);
            Random var58 = new Random((long)(day * 255));
            float var59 = var58.nextFloat() * 360.0F;
            float angle2 = var58.nextFloat() * 360.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var56 * (1.0F - insideVoid));
            GL11.glRotatef(var59, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(angle2, 0.0F, 0.0F, 1.0F);
            tessellator1.startDrawingQuads();

            for (int var60 = 0; var60 < var55; ++var60)
            {
                int j = var60;

                if (var60 % 2 == 0)
                {
                    j = var60 - 1;
                }

                float var62 = (float)j * anglePer;
                double xp = Math.cos((double)var62 * Math.PI / 180.0D) * (double)f10;
                double zp = Math.sin((double)var62 * Math.PI / 180.0D) * (double)f10;
                double yo = 0.0D;
                float ut1 = var62 * uPer;

                if (var60 % 2 == 0)
                {
                    tessellator1.addVertexWithUV(xp, yo + (double)y0 + (double)y, zp, (double)ut1, 1.0D);
                    tessellator1.addVertexWithUV(xp, yo + (double)y0, zp, (double)ut1, 0.0D);
                }
                else
                {
                    tessellator1.addVertexWithUV(xp, yo + (double)y0, zp, (double)ut1, 0.0D);
                    tessellator1.addVertexWithUV(xp, yo + (double)y0 + (double)y, zp, (double)ut1, 1.0D);
                }
            }

            tessellator1.draw();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - insideVoid);
            NewOpenGlHelper.glBlendFunc(770, 1, 1, 0);
            f6 *= Math.max(0.1F, var52 * 2.0F);
            float var61 = ((float)ClientTickHandler.tick + partialTicks + 2000.0F) * 0.005F;
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
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
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
    }

    private void drawObject(Tessellator tess, float f10)
    {
        tess.startDrawingQuads();
        tess.addVertexWithUV((double)(-f10), 100.0D, (double)(-f10), 0.0D, 0.0D);
        tess.addVertexWithUV((double)f10, 100.0D, (double)(-f10), 1.0D, 0.0D);
        tess.addVertexWithUV((double)f10, 100.0D, (double)f10, 1.0D, 1.0D);
        tess.addVertexWithUV((double)(-f10), 100.0D, (double)f10, 0.0D, 1.0D);
        tess.draw();
    }
}
