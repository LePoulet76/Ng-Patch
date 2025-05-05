package net.ilexiconn.nationsgui.forge.server.asm;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks$1;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks$2;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks$3;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks$Uploader;
import net.ilexiconn.nationsgui.forge.server.event.EntityViewRenderEvent$FogColors;
import net.ilexiconn.nationsgui.forge.server.event.EntityViewRenderEvent$FogDensity;
import net.ilexiconn.nationsgui.forge.server.event.EntityViewRenderEvent$RenderFogEvent;
import net.ilexiconn.nationsgui.forge.server.item.ICustomTooltip;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NColor;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NIcon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Resource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;

@SideOnly(Side.CLIENT)
public enum NationsGUIClientHooks
{
    INSTANCE;
    static Minecraft mc = Minecraft.getMinecraft();
    static boolean isDone;
    public static List<String> whitelistedStaff = new ArrayList();
    static List<String> sentences = new ArrayList();
    static List<String> passed = new ArrayList();
    public static final ResourceLocation MINECRAFT_SCREEN_TEXTURE = new ResourceLocation("textures/gui/minecraft_screen.png");
    public static final ResourceLocation LOADING_SCREEN_OVERLAY_TEXTURE = new ResourceLocation("textures/gui/loading_screen_overlay.png");
    public static int LOADING_SCREEN_GUI_SCALE = 2;
    public double time;
    private String currentTitle;
    private CFontRenderer textRendererDungeons;
    private CFontRenderer textRendererGeoramaSemiBold;
    public static Map<String, BufferedImage> screenMap;
    private static long cooldown;

    public void onDrawScreen()
    {
        if (ClientEventHandler.getInstance().snackbarGUI != null)
        {
            ClientEventHandler.getInstance().snackbarGUI.drawSnackbar();
        }
    }

    public static void resetOpenGL()
    {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawLoadingScreen()
    {
        if (Minecraft.getMinecraft().getResourceManager() != null)
        {
            if (this.textRendererDungeons == null)
            {
                this.textRendererDungeons = FontManager.createClientHookFontDungeons();

                if (this.textRendererDungeons != null)
                {
                    this.textRendererDungeons.setFontSize(20.0F);
                }
            }

            if (this.textRendererGeoramaSemiBold == null)
            {
                this.textRendererGeoramaSemiBold = FontManager.createClientHookFontGeorama();

                if (this.textRendererGeoramaSemiBold != null)
                {
                    this.textRendererGeoramaSemiBold.setFontSize(15.0F);
                }
            }
        }

        if (this.time % 120.0D == 0.0D)
        {
            this.changeTitle();
        }

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator tessellator = Tessellator.instance;
        ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_loading");
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 3840.0F, 2160.0F, false);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        mc.getTextureManager().bindTexture(MINECRAFT_SCREEN_TEXTURE);
        int logoWidth = (int)((float)scaledresolution.getScaledWidth() * 0.174F);
        int logoHeight = (int)((float)logoWidth * 0.19F);
        ModernGui.drawScaledCustomSizeModalRect(12.0F, 12.0F, (float)(709 * LOADING_SCREEN_GUI_SCALE), 0.0F, 335 * LOADING_SCREEN_GUI_SCALE, 64 * LOADING_SCREEN_GUI_SCALE, logoWidth, logoHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        resetOpenGL();

        if (ModernGui.isRemoteTextureLoaded("https://apiv2.nationsglory.fr/proxy_images/screen_loading"))
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            byte loadingTextX = 12;
            int loadingTextY = scaledresolution.getScaledHeight() - 45;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (this.textRendererDungeons != null)
            {
                this.textRendererDungeons.drawString("CHARGEMENT...", (float)loadingTextX, (float)loadingTextY, 16777215);
            }

            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            byte helpTextX = 12;
            int helpTextY = scaledresolution.getScaledHeight() - 28;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (this.textRendererGeoramaSemiBold != null)
            {
                this.textRendererGeoramaSemiBold.drawString(this.currentTitle, (float)helpTextX, (float)helpTextY, 8882056);
            }

            GL11.glPopMatrix();
            byte barX = 12;
            int barY = scaledresolution.getScaledHeight() - 15;
            int barWidth = scaledresolution.getScaledWidth() - 24;
            int barHeight = (int)((float)barWidth * 0.008928571F);
            double progress = Math.min(1.0D, this.time / 812.0D);
            mc.getTextureManager().bindTexture(MINECRAFT_SCREEN_TEXTURE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ModernGui.drawScaledCustomSizeModalRect((float)barX, (float)barY, 0.0F, (float)(228 * LOADING_SCREEN_GUI_SCALE), 1792 * LOADING_SCREEN_GUI_SCALE, 16 * LOADING_SCREEN_GUI_SCALE, barWidth, barHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)barX, (float)barY, 0.0F, (float)(260 * LOADING_SCREEN_GUI_SCALE), (int)(progress * 1792.0D * (double)LOADING_SCREEN_GUI_SCALE), 16 * LOADING_SCREEN_GUI_SCALE, (int)(progress * (double)barWidth), barHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
        }

        Display.update();
        ++this.time;
    }

    private void changeTitle()
    {
        String newTitle = (String)sentences.toArray()[(new Random()).nextInt(sentences.size())];

        if (passed.contains(newTitle))
        {
            this.changeTitle();
        }
        else
        {
            this.currentTitle = newTitle;
            passed.add(newTitle);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3)
    {
        Minecraft mc = Minecraft.getMinecraft();
        List list = par1ItemStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
        int color;

        for (color = 0; color < list.size(); ++color)
        {
            if (color == 0)
            {
                list.set(color, "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + list.get(color));
            }
            else
            {
                list.set(color, EnumChatFormatting.GRAY + (String)list.get(color));
            }
        }

        color = 1347420415;
        int background = -267386864;

        if (par1ItemStack.getItem() instanceof ICustomTooltip)
        {
            ICustomTooltip font = (ICustomTooltip)par1ItemStack.getItem();
            int c = font.getTooltipColor(par1ItemStack);
            int b = font.getTooltipBackgroundColor(par1ItemStack);

            if (c != 255)
            {
                color = c;
            }

            if (b != 255)
            {
                background = b;
            }
        }

        FontRenderer var10 = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        drawHoveringText(mc.currentScreen, list, par2, par3, var10 == null ? Minecraft.getMinecraft().fontRenderer : var10, color, background);
    }

    @SideOnly(Side.CLIENT)
    public static void drawHoveringText(GuiScreen guiScreen, List par1List, int par2, int par3, FontRenderer font, int color, int backgroudColor)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var18 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var18 + k > guiScreen.width)
            {
                var18 -= 28 + k;
            }

            if (j1 + k1 + 6 > guiScreen.height)
            {
                j1 = guiScreen.height - k1 - 6;
            }

            float zLevel = 300.0F;
            zLevel = 300.0F;
            drawGradientRect(var18 - 3, j1 - 4, var18 + k + 3, j1 - 3, backgroudColor, backgroudColor, zLevel);
            drawGradientRect(var18 - 3, j1 + k1 + 3, var18 + k + 3, j1 + k1 + 4, backgroudColor, backgroudColor, zLevel);
            drawGradientRect(var18 - 3, j1 - 3, var18 + k + 3, j1 + k1 + 3, backgroudColor, backgroudColor, zLevel);
            drawGradientRect(var18 - 4, j1 - 3, var18 - 3, j1 + k1 + 3, backgroudColor, backgroudColor, zLevel);
            drawGradientRect(var18 + k + 3, j1 - 3, var18 + k + 4, j1 + k1 + 3, backgroudColor, backgroudColor, zLevel);
            int j2 = (color & 16711422) >> 1 | color & -16777216;
            drawGradientRect(var18 - 3, j1 - 3 + 1, var18 - 3 + 1, j1 + k1 + 3 - 1, color, j2, zLevel);
            drawGradientRect(var18 + k + 2, j1 - 3 + 1, var18 + k + 3, j1 + k1 + 3 - 1, color, j2, zLevel);
            drawGradientRect(var18 - 3, j1 - 3, var18 + k + 3, j1 - 3 + 1, color, color, zLevel);
            drawGradientRect(var18 - 3, j1 + k1 + 2, var18 + k + 3, j1 + k1 + 3, j2, j2, zLevel);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var18, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6, float zLevel)
    {
        float var7 = (float)(var5 >> 24 & 255) / 255.0F;
        float var8 = (float)(var5 >> 16 & 255) / 255.0F;
        float var9 = (float)(var5 >> 8 & 255) / 255.0F;
        float var10 = (float)(var5 & 255) / 255.0F;
        float var11 = (float)(var6 >> 24 & 255) / 255.0F;
        float var12 = (float)(var6 >> 16 & 255) / 255.0F;
        float var13 = (float)(var6 >> 8 & 255) / 255.0F;
        float var14 = (float)(var6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex((double)var3, (double)var2, (double)zLevel);
        var15.addVertex((double)var1, (double)var2, (double)zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex((double)var1, (double)var4, (double)zLevel);
        var15.addVertex((double)var3, (double)var4, (double)zLevel);
        var15.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @SideOnly(Side.CLIENT)
    public static void onResourceReload()
    {
        Thread t = new Thread(new NationsGUIClientHooks$1());
        t.start();
    }

    private static boolean checkResource(ResourceLocation resourceLocation) throws IOException {
        boolean clean = true;
        Resource resource = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation);
        BufferedImage bufferedimage = ImageIO.read(resource.getInputStream());

        for (int y = 0; y < bufferedimage.getHeight(); ++y)
        {
            for (int x = 0; x < bufferedimage.getWidth(); ++x)
            {
                int color = bufferedimage.getRGB(x, y);
                int alpha = color >> 24 & 255;

                if (alpha < 255)
                {
                    System.out.println("Unauthorized Texture : " + resourceLocation.getResourcePath());
                    clean = false;
                    break;
                }
            }

            if (!clean)
            {
                break;
            }
        }

        return clean;
    }

    public static void handleSetupFog(EntityRenderer renderer, int par1, float par2)
    {
        EntityLivingBase entitylivingbase = mc.renderViewEntity;
        boolean flag = false;
        float fogColorRed = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"fogColorRed", "fogColorRed"})).floatValue();
        float fogColorGreen = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"fogColorGreen", "fogColorGreen"})).floatValue();
        float fogColorBlue = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"fogColorBlue", "fogColorBlue"})).floatValue();
        float farPlaneDistance = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"farPlaneDistance", "farPlaneDistance"})).floatValue();
        boolean cloudFog = ((Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"cloudFog", "cloudFog"})).booleanValue();
        Method setFCB = ReflectionHelper.findMethod(EntityRenderer.class, renderer, new String[] {"setFogColorBuffer", "setFogColorBuffer"}, new Class[] {Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});

        try
        {
            if (entitylivingbase instanceof EntityPlayer)
            {
                flag = ((EntityPlayer)entitylivingbase).capabilities.isCreativeMode;
            }

            if (par1 == 999)
            {
                GL11.glFog(GL11.GL_FOG_COLOR, (FloatBuffer)setFCB.invoke(renderer, new Object[] {Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(1.0F)}));
                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
                GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                GL11.glFogf(GL11.GL_FOG_END, 8.0F);

                if (GLContext.getCapabilities().GL_NV_fog_distance)
                {
                    GL11.glFogi(34138, 34139);
                }

                GL11.glFogf(GL11.GL_FOG_START, 0.0F);
            }
            else
            {
                GL11.glFog(GL11.GL_FOG_COLOR, (FloatBuffer)setFCB.invoke(renderer, new Object[] {Float.valueOf(fogColorRed), Float.valueOf(fogColorGreen), Float.valueOf(fogColorBlue), Float.valueOf(1.0F)}));
                GL11.glNormal3f(0.0F, -1.0F, 0.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int e = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, entitylivingbase, par2);
                EntityViewRenderEvent$FogDensity event = new EntityViewRenderEvent$FogDensity(renderer, entitylivingbase, e, (double)par2, 0.1F);

                if (MinecraftForge.EVENT_BUS.post(event))
                {
                    GL11.glFogf(GL11.GL_FOG_DENSITY, event.density);
                }
                else
                {
                    float f1;

                    if (entitylivingbase.isPotionActive(Potion.blindness))
                    {
                        f1 = 5.0F;
                        int d0 = entitylivingbase.getActivePotionEffect(Potion.blindness).getDuration();

                        if (d0 < 20)
                        {
                            f1 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - (float)d0 / 20.0F);
                        }

                        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

                        if (par1 < 0)
                        {
                            GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                            GL11.glFogf(GL11.GL_FOG_END, f1 * 0.8F);
                        }
                        else
                        {
                            GL11.glFogf(GL11.GL_FOG_START, f1 * 0.25F);
                            GL11.glFogf(GL11.GL_FOG_END, f1);
                        }

                        if (GLContext.getCapabilities().GL_NV_fog_distance)
                        {
                            GL11.glFogi(34138, 34139);
                        }
                    }
                    else if (cloudFog)
                    {
                        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                        GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
                    }
                    else if (e != Block.waterMoving.blockID && e != Block.waterStill.blockID)
                    {
                        if (e != Block.lavaMoving.blockID && e != Block.lavaStill.blockID)
                        {
                            f1 = farPlaneDistance;

                            if (mc.theWorld.provider.getWorldHasVoidParticles() && !flag)
                            {
                                double d01 = (double)((entitylivingbase.getBrightnessForRender(par2) & 15728640) >> 20) / 16.0D + (entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)par2 + 4.0D) / 32.0D;

                                if (d01 < 1.0D)
                                {
                                    if (d01 < 0.0D)
                                    {
                                        d01 = 0.0D;
                                    }

                                    d01 *= d01;
                                    float f2 = 100.0F * (float)d01;

                                    if (f2 < 5.0F)
                                    {
                                        f2 = 5.0F;
                                    }

                                    if (farPlaneDistance > f2)
                                    {
                                        f1 = f2;
                                    }
                                }
                            }

                            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

                            if (par1 < 0)
                            {
                                GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                                GL11.glFogf(GL11.GL_FOG_END, f1);
                            }
                            else
                            {
                                GL11.glFogf(GL11.GL_FOG_START, f1 * 0.75F);
                                GL11.glFogf(GL11.GL_FOG_END, f1);
                            }

                            if (GLContext.getCapabilities().GL_NV_fog_distance)
                            {
                                GL11.glFogi(34138, 34139);
                            }

                            if (mc.theWorld.provider.doesXZShowFog((int)entitylivingbase.posX, (int)entitylivingbase.posZ))
                            {
                                GL11.glFogf(GL11.GL_FOG_START, f1 * 0.05F);
                                GL11.glFogf(GL11.GL_FOG_END, Math.min(f1, 192.0F) * 0.5F);
                            }

                            MinecraftForge.EVENT_BUS.post(new EntityViewRenderEvent$RenderFogEvent(renderer, entitylivingbase, e, (double)par2, par1, f1));
                        }
                        else
                        {
                            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                            GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
                        }
                    }
                    else
                    {
                        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);

                        if (entitylivingbase.isPotionActive(Potion.waterBreathing))
                        {
                            GL11.glFogf(GL11.GL_FOG_DENSITY, 0.05F);
                        }
                        else
                        {
                            GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F - (float)EnchantmentHelper.getRespiration(entitylivingbase) * 0.03F);
                        }
                    }
                }

                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
            }
        }
        catch (Exception var17)
        {
            var17.printStackTrace();
        }
    }

    public static void handleUpdateFog(EntityRenderer renderer, float par1)
    {
        try
        {
            WorldClient e = mc.theWorld;
            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            float f1 = 1.0F / (float)(4 - mc.gameSettings.renderDistance);
            f1 = 1.0F - (float)Math.pow((double)f1, 0.25D);
            Vec3 vec3 = e.getSkyColor(mc.renderViewEntity, par1);
            float f2 = (float)vec3.xCoord;
            float f3 = (float)vec3.yCoord;
            float f4 = (float)vec3.zCoord;
            Vec3 vec31 = e.getFogColor(par1);
            float fogColorRed = (float)vec31.xCoord;
            float fogColorGreen = (float)vec31.yCoord;
            float fogColorBlue = (float)vec31.zCoord;
            float fogColor1 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"fogColor1", "fogColor1"})).floatValue();
            float fogColor2 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"fogColor2", "fogColor2"})).floatValue();
            boolean cloudFog = ((Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"cloudFog", "cloudFog"})).booleanValue();
            float field_82831_U = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"field_82831_U"})).floatValue();
            float field_82832_V = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[] {"field_82832_V"})).floatValue();
            float f5;

            if (mc.gameSettings.renderDistance < 2)
            {
                Vec3 f6 = MathHelper.sin(e.getCelestialAngleRadians(par1)) > 0.0F ? e.getWorldVec3Pool().getVecFromPool(-1.0D, 0.0D, 0.0D) : e.getWorldVec3Pool().getVecFromPool(1.0D, 0.0D, 0.0D);
                f5 = (float)entitylivingbase.getLook(par1).dotProduct(f6);

                if (f5 < 0.0F)
                {
                    f5 = 0.0F;
                }

                if (f5 > 0.0F)
                {
                    float[] f7 = e.provider.calcSunriseSunsetColors(e.getCelestialAngle(par1), par1);

                    if (f7 != null)
                    {
                        f5 *= f7[3];
                        fogColorRed = fogColorRed * (1.0F - f5) + f7[0] * f5;
                        fogColorGreen = fogColorGreen * (1.0F - f5) + f7[1] * f5;
                        fogColorBlue = fogColorBlue * (1.0F - f5) + f7[2] * f5;
                    }
                }
            }

            fogColorRed += (f2 - fogColorRed) * f1;
            fogColorGreen += (f3 - fogColorGreen) * f1;
            fogColorBlue += (f4 - fogColorBlue) * f1;
            float f61 = e.getRainStrength(par1);
            float f71;

            if (f61 > 0.0F)
            {
                f5 = 1.0F - f61 * 0.5F;
                f71 = 1.0F - f61 * 0.4F;
                fogColorRed *= f5;
                fogColorGreen *= f5;
                fogColorBlue *= f71;
            }

            f5 = e.getWeightedThunderStrength(par1);

            if (f5 > 0.0F)
            {
                f71 = 1.0F - f5 * 0.5F;
                fogColorRed *= f71;
                fogColorGreen *= f71;
                fogColorBlue *= f71;
            }

            int i = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, entitylivingbase, par1);
            float f8;

            if (cloudFog)
            {
                Vec3 d0 = e.getCloudColour(par1);
                fogColorRed = (float)d0.xCoord;
                fogColorGreen = (float)d0.yCoord;
                fogColorBlue = (float)d0.zCoord;
            }
            else if (i != 0 && Block.blocksList[i].blockMaterial == Material.water)
            {
                f8 = (float)EnchantmentHelper.getRespiration(entitylivingbase) * 0.2F;
                fogColorRed = 0.02F + f8;
                fogColorGreen = 0.02F + f8;
                fogColorBlue = 0.2F + f8;
            }
            else if (i != 0 && Block.blocksList[i].blockMaterial == Material.lava)
            {
                fogColorRed = 0.6F;
                fogColorGreen = 0.1F;
                fogColorBlue = 0.0F;
            }

            f8 = fogColor2 + (fogColor1 - fogColor2) * par1;
            fogColorRed *= f8;
            fogColorGreen *= f8;
            fogColorBlue *= f8;
            double d01 = (entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)par1) * e.provider.getVoidFogYFactor();

            if (entitylivingbase.isPotionActive(Potion.blindness))
            {
                int f9 = entitylivingbase.getActivePotionEffect(Potion.blindness).getDuration();

                if (f9 < 20)
                {
                    d01 *= (double)(1.0F - (float)f9 / 20.0F);
                }
                else
                {
                    d01 = 0.0D;
                }
            }

            if (d01 < 1.0D)
            {
                if (d01 < 0.0D)
                {
                    d01 = 0.0D;
                }

                d01 *= d01;
                fogColorRed = (float)((double)fogColorRed * d01);
                fogColorGreen = (float)((double)fogColorGreen * d01);
                fogColorBlue = (float)((double)fogColorBlue * d01);
            }

            float f91;

            if (field_82831_U > 0.0F)
            {
                f91 = field_82832_V + (field_82831_U - field_82832_V) * par1;
                fogColorRed = fogColorRed * (1.0F - f91) + fogColorRed * 0.7F * f91;
                fogColorGreen = fogColorGreen * (1.0F - f91) + fogColorGreen * 0.6F * f91;
                fogColorBlue = fogColorBlue * (1.0F - f91) + fogColorBlue * 0.6F * f91;
            }

            float f10;

            if (entitylivingbase.isPotionActive(Potion.nightVision))
            {
                f91 = ((Float)ReflectionHelper.findMethod(EntityRenderer.class, renderer, new String[] {"getNightVisionBrightness", "getNightVisionBrightness"}, new Class[] {EntityPlayer.class, Float.TYPE}).invoke(renderer, new Object[] {mc.thePlayer, Float.valueOf(par1)})).floatValue();
                f10 = 1.0F / fogColorRed;

                if (f10 > 1.0F / fogColorGreen)
                {
                    f10 = 1.0F / fogColorGreen;
                }

                if (f10 > 1.0F / fogColorBlue)
                {
                    f10 = 1.0F / fogColorBlue;
                }

                fogColorRed = fogColorRed * (1.0F - f91) + fogColorRed * f10 * f91;
                fogColorGreen = fogColorGreen * (1.0F - f91) + fogColorGreen * f10 * f91;
                fogColorBlue = fogColorBlue * (1.0F - f91) + fogColorBlue * f10 * f91;
            }

            if (mc.gameSettings.anaglyph)
            {
                f91 = (fogColorRed * 30.0F + fogColorGreen * 59.0F + fogColorBlue * 11.0F) / 100.0F;
                f10 = (fogColorRed * 30.0F + fogColorGreen * 70.0F) / 100.0F;
                float event = (fogColorRed * 30.0F + fogColorBlue * 70.0F) / 100.0F;
                fogColorRed = f91;
                fogColorGreen = f10;
                fogColorBlue = event;
            }

            EntityViewRenderEvent$FogColors event1 = new EntityViewRenderEvent$FogColors(renderer, entitylivingbase, i, (double)par1, fogColorRed, fogColorGreen, fogColorBlue);
            MinecraftForge.EVENT_BUS.post(event1);
            fogColorRed = event1.red;
            fogColorBlue = event1.blue;
            fogColorGreen = event1.green;
            GL11.glClearColor(fogColorRed, fogColorGreen, fogColorBlue, 0.0F);
            ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[] {"fogColorRed", "fogColorRed"});
            ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[] {"fogColorGreen", "fogColorGreen"});
            ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[] {"fogColorBlue", "fogColorBlue"});
        }
        catch (Exception var28)
        {
            var28.printStackTrace();
        }
    }

    public static boolean isDone()
    {
        return isDone;
    }

    public static void setDone(boolean b)
    {
        isDone = true;
    }

    public static void sendNotificationToUploadImage(BufferedImage image)
    {
        if (!ClientData.lastCaptureScreenshot.isEmpty())
        {
            Iterator uuid = ClientData.lastCaptureScreenshot.entrySet().iterator();

            while (uuid.hasNext())
            {
                Entry comp = (Entry)uuid.next();

                if (System.currentTimeMillis() - ((Long)comp.getValue()).longValue() < 180000L)
                {
                    Thread allow = new Thread(new NationsGUIClientHooks$Uploader(image, (String)comp.getKey()));
                    allow.setDaemon(true);
                    allow.start();
                    ClientData.lastCaptureScreenshot.clear();
                }
            }
        }

        String uuid1 = UUID.randomUUID().toString();
        screenMap.put(uuid1, image);
        NBTTagCompound comp1 = new NBTTagCompound();
        comp1.setString("title", I18n.getString("screen.upload.title"));
        comp1.setString("content", I18n.getString("screen.upload.content"));
        comp1.setLong("lifetime", 10000L);
        comp1.setString("color", NotificationManager$NColor.CYAN.name());
        comp1.setString("icon", NotificationManager$NIcon.VOTE.name());
        NBTTagCompound allow1 = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        allow1.setString("translatedTitle", I18n.getString("screen.uploader.allow"));
        allow1.setString("id", "screen.uploader.allow");
        allow1.setString("args", uuid1);
        deny.setString("translatedTitle", I18n.getString("screen.uploader.deny"));
        deny.setString("id", "screen.uploader.deny");
        deny.setString("args", uuid1);
        NBTTagCompound list = new NBTTagCompound();
        list.setTag("allow", allow1);
        list.setTag("deny", deny);
        comp1.setTag("actions", list);
        ClientData.notifications.add(new Notification(comp1));
    }

    public static void uploadToForum(BufferedImage image)
    {
        if (image != null)
        {
            if (cooldown < System.currentTimeMillis())
            {
                Thread me = new Thread(new NationsGUIClientHooks$Uploader(image, (String)null));
                me.setDaemon(true);
                me.start();
                cooldown = System.currentTimeMillis() + 10000L;
            }
            else
            {
                EntityClientPlayerMP me1 = Minecraft.getMinecraft().thePlayer;

                if (me1 == null)
                {
                    return;
                }

                me1.addChatMessage(I18n.getStringParams("screen.uploader.cooldown", new Object[] {Long.valueOf((cooldown - System.currentTimeMillis()) / 1000L)}));
            }
        }
    }

    public static void doPreChunk(WorldClient worldClient, int par1, int par2, boolean par3)
    {
        if (par3 && worldClient.getChunkProvider().provideChunk(par1, par2).isEmpty())
        {
            worldClient.getChunkProvider().loadChunk(par1, par2);
        }

        if (!par3)
        {
            worldClient.markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
        }
    }

    static boolean access$000(ResourceLocation x0) throws IOException {
        return checkResource(x0);
    }

    static {
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Au d\u00e9but, Il est conseill\u00e9 de rejoindre un pays qui t\'invite via le /f join (pays)" : "At first, it is advisable to join a country that invites you via /f join (country).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Une information ? Une question ? Retrouve le wiki sur wiki.nationsglory.fr" : "Need information? Have a question? Find the wiki at wiki.nationsglory.fr");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Une question ? Contacte le Staff via TeamSpeak (ts.nationsglory.fr) ou via Discord..." : "Have a question? Contact the Staff via TeamSpeak (ts.nationsglory.fr) or via Discord.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu es limit\u00e9 \u00e0 4 comptes par foyer (IP), si vous \u00eates plusieurs, partagez-vous ce nombre..." : "You are limited to 4 accounts per household (IP). If you have more people, share this number.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tout dispositif d\'anti-afk est interdit et sera sanctionn\u00e9..." : "Any anti-AFK device is prohibited and will be sanctioned.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Bienvenue sur NationsGlory ! Si tu es nouveau, tu es prot\u00e9g\u00e9 pendant 48h de jeu !" : "Welcome to NationsGlory! If you are new, you are protected for 48 hours of gameplay!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Fais toi de l\'argent gr\u00e2ce \u00e0 l\'HDV, tu peux voir le top des ventes via le /hdv top" : "Make money through the Auction House, you can see the top sales via /hdv top.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Evite les soucis et cours lire le CODEX (les r\u00e8gles de NationsGlory)..." : "Avoid problems and go read the CODEX (NationsGlory rules).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Au d\u00e9but, fais toi un maximum d\'argent en allant miner (Utilise le /mine)..." : "At the beginning, make as much money as possible by mining (use /mine).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Vote pour NationsGlory et gagne des r\u00e9compenses avec le /vote !" : "Vote for NationsGlory and earn rewards with /vote!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Si tu pars en vacances ou en examen, signale ton absence via le /f absence !" : "If you go on vacation or have exams, report your absence via /f absence!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Toute forme d\'anti-jeu peut \u00eatre sanctionn\u00e9e, renseigne toi aupr\u00e8s du staff..." : "Any form of anti-gameplay can be sanctioned, check with the staff.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu peux s\u00e9curiser ton compte sur le site pour \u00e9viter le piratage." : "You can secure your account on the website to prevent hacking.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Ton compte est personnel, ne le partage pas." : "Your account is personal, do not share it.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Pour te balader sur NationsGlory, utilise le /warps." : "To navigate around NationsGlory, use /warps.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Un probl\u00e8me avec ton launcher ? Essaie de r\u00e9parer ton installation via les param\u00e8tres..." : "Having trouble with your launcher? Try repairing your installation via settings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu peux installer diff\u00e9rents mods depuis les param\u00e8tres de ton launcher..." : "You can install various mods from your launcher settings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Rejoins le discord de NationsGlory : https://discord.gg/nationsglory" : "Join the NationsGlory Discord: https://discord.gg/nationsglory.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Le savais-tu ? Il existe un serveur anglophone sur NationsGlory !" : "Did you know? There is an English-speaking server on NationsGlory.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, le tp-kill est une raison de guerre !" : "Be careful, tp-kill is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, tuer quelqu\'un en wilderness est une raison de guerre !" : "Be careful, killing someone in the wilderness is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, l\'arnaque est autoris\u00e9e (sauf via la boutique) mais c\'est une raison de guerre !" : "Be careful, scamming is allowed (except via the shop), but it is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Fais attention aux permissions que tu donnes, tout le monde n\'est pas de bonne volont\u00e9..." : "Pay attention to the permissions you give; not everyone has good intentions.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "N\'oublie pas de poser le /f point chaque semaine \u00e0 l\'endroit o\u00f9 tu construis pour les notations..." : "Don\'t forget to place the /f point every week at your construction site for ratings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Pour te faire de l\'argent, r\u00e9cup\u00e8res ton ATM \u00e0 la Capitale de ton serveur !" : "To make money, collect your ATM at the Capital of your server!");
        screenMap = new HashMap();
        NotificationManager.registerAction("screen.uploader.allow", new NationsGUIClientHooks$2());
        NotificationManager.registerAction("screen.uploader.deny", new NationsGUIClientHooks$3());
    }
}
