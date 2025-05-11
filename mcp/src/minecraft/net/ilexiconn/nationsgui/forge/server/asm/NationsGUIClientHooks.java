/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.resources.Resource
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Session
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  org.bouncycastle.util.encoders.Base64
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 */
package net.ilexiconn.nationsgui.forge.server.asm;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.event.EntityViewRenderEvent;
import net.ilexiconn.nationsgui.forge.server.item.ICustomTooltip;
import net.ilexiconn.nationsgui.forge.server.notifications.INotificationActionHandler;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryAddImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotAddImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionUpdateBannerPacket;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import org.bouncycastle.util.encoders.Base64;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

@SideOnly(value=Side.CLIENT)
public enum NationsGUIClientHooks {
    INSTANCE;

    static Minecraft mc;
    static boolean isDone;
    public static List<String> whitelistedStaff;
    static List<String> sentences;
    static List<String> passed;
    public static final ResourceLocation MINECRAFT_SCREEN_TEXTURE;
    public static final ResourceLocation LOADING_SCREEN_OVERLAY_TEXTURE;
    public static int LOADING_SCREEN_GUI_SCALE;
    public double time;
    private String currentTitle;
    private CFontRenderer textRendererDungeons;
    private CFontRenderer textRendererGeoramaSemiBold;
    public static Map<String, BufferedImage> screenMap;
    private static long cooldown;

    public void onDrawScreen() {
        if (ClientEventHandler.getInstance().snackbarGUI != null) {
            ClientEventHandler.getInstance().snackbarGUI.drawSnackbar();
        }
    }

    public static void resetOpenGL() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void drawLoadingScreen() {
        if (Minecraft.func_71410_x().func_110442_L() != null) {
            if (this.textRendererDungeons == null) {
                this.textRendererDungeons = FontManager.createClientHookFontDungeons();
                if (this.textRendererDungeons != null) {
                    this.textRendererDungeons.setFontSize(20.0f);
                }
            }
            if (this.textRendererGeoramaSemiBold == null) {
                this.textRendererGeoramaSemiBold = FontManager.createClientHookFontGeorama();
                if (this.textRendererGeoramaSemiBold != null) {
                    this.textRendererGeoramaSemiBold.setFontSize(15.0f);
                }
            }
        }
        if (this.time % 120.0 == 0.0) {
            this.changeTitle();
        }
        Minecraft mc = Minecraft.func_71410_x();
        ScaledResolution scaledresolution = new ScaledResolution(mc.field_71474_y, mc.field_71443_c, mc.field_71440_d);
        GL11.glClear((int)16640);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)scaledresolution.func_78327_c(), (double)scaledresolution.func_78324_d(), (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glViewport((int)0, (int)0, (int)mc.field_71443_c, (int)mc.field_71440_d);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2912);
        Tessellator tessellator = Tessellator.field_78398_a;
        ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_loading");
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 3840, 2160, scaledresolution.func_78326_a(), scaledresolution.func_78328_b(), 3840.0f, 2160.0f, false);
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3553);
        mc.func_110434_K().func_110577_a(MINECRAFT_SCREEN_TEXTURE);
        int logoWidth = (int)((float)scaledresolution.func_78326_a() * 0.174f);
        int logoHeight = (int)((float)logoWidth * 0.19f);
        ModernGui.drawScaledCustomSizeModalRect(12.0f, 12.0f, 709 * LOADING_SCREEN_GUI_SCALE, 0.0f, 335 * LOADING_SCREEN_GUI_SCALE, 64 * LOADING_SCREEN_GUI_SCALE, logoWidth, logoHeight, 1792 * LOADING_SCREEN_GUI_SCALE, 276 * LOADING_SCREEN_GUI_SCALE, false);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glPopMatrix();
        NationsGUIClientHooks.resetOpenGL();
        if (ModernGui.isRemoteTextureLoaded("https://apiv2.nationsglory.fr/proxy_images/screen_loading")) {
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3553);
            int loadingTextX = 12;
            int loadingTextY = scaledresolution.func_78328_b() - 45;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (this.textRendererDungeons != null) {
                this.textRendererDungeons.drawString("CHARGEMENT...", loadingTextX, loadingTextY, 0xFFFFFF);
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int helpTextX = 12;
            int helpTextY = scaledresolution.func_78328_b() - 28;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (this.textRendererGeoramaSemiBold != null) {
                this.textRendererGeoramaSemiBold.drawString(this.currentTitle, helpTextX, helpTextY, 0x878788);
            }
            GL11.glPopMatrix();
            int barX = 12;
            int barY = scaledresolution.func_78328_b() - 15;
            int barWidth = scaledresolution.func_78326_a() - 24;
            int barHeight = (int)((float)barWidth * 0.008928571f);
            double progress = Math.min(1.0, this.time / 812.0);
            mc.func_110434_K().func_110577_a(MINECRAFT_SCREEN_TEXTURE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ModernGui.drawScaledCustomSizeModalRect(barX, barY, 0.0f, 228 * LOADING_SCREEN_GUI_SCALE, 1792 * LOADING_SCREEN_GUI_SCALE, 16 * LOADING_SCREEN_GUI_SCALE, barWidth, barHeight, 1792 * LOADING_SCREEN_GUI_SCALE, 276 * LOADING_SCREEN_GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(barX, barY, 0.0f, 260 * LOADING_SCREEN_GUI_SCALE, (int)(progress * 1792.0 * (double)LOADING_SCREEN_GUI_SCALE), 16 * LOADING_SCREEN_GUI_SCALE, (int)(progress * (double)barWidth), barHeight, 1792 * LOADING_SCREEN_GUI_SCALE, 276 * LOADING_SCREEN_GUI_SCALE, false);
        }
        Display.update();
        this.time += 1.0;
    }

    private void changeTitle() {
        String newTitle = (String)sentences.toArray()[new Random().nextInt(sentences.size())];
        if (passed.contains(newTitle)) {
            this.changeTitle();
            return;
        }
        this.currentTitle = newTitle;
        passed.add(newTitle);
    }

    @SideOnly(value=Side.CLIENT)
    public static void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3) {
        Minecraft mc = Minecraft.func_71410_x();
        List list = par1ItemStack.func_82840_a((EntityPlayer)mc.field_71439_g, mc.field_71474_y.field_82882_x);
        for (int k = 0; k < list.size(); ++k) {
            if (k == 0) {
                list.set(k, "\u00a7" + Integer.toHexString(par1ItemStack.func_77953_t().field_77937_e) + list.get(k));
                continue;
            }
            list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
        }
        int color = 0x505000FF;
        int background = -267386864;
        if (par1ItemStack.func_77973_b() instanceof ICustomTooltip) {
            ICustomTooltip customTooltip = (ICustomTooltip)par1ItemStack.func_77973_b();
            int c = customTooltip.getTooltipColor(par1ItemStack);
            int b = customTooltip.getTooltipBackgroundColor(par1ItemStack);
            if (c != 255) {
                color = c;
            }
            if (b != 255) {
                background = b;
            }
        }
        FontRenderer font = par1ItemStack.func_77973_b().getFontRenderer(par1ItemStack);
        NationsGUIClientHooks.drawHoveringText(mc.field_71462_r, list, par2, par3, font == null ? Minecraft.func_71410_x().field_71466_p : font, color, background);
    }

    @SideOnly(value=Side.CLIENT)
    public static void drawHoveringText(GuiScreen guiScreen, List par1List, int par2, int par3, FontRenderer font, int color, int backgroudColor) {
        if (!par1List.isEmpty()) {
            int j1;
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                j1 = font.func_78256_a(s);
                if (j1 <= k) continue;
                k = j1;
            }
            int i1 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > guiScreen.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > guiScreen.field_73881_g) {
                j1 = guiScreen.field_73881_g - k1 - 6;
            }
            float zLevel = 300.0f;
            zLevel = 300.0f;
            int l1 = backgroudColor;
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1, zLevel);
            int i2 = color;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2, zLevel);
            NationsGUIClientHooks.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2, zLevel);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            zLevel = 0.0f;
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            RenderHelper.func_74519_b();
            GL11.glEnable((int)32826);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public static void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6, float zLevel) {
        float var7 = (float)(var5 >> 24 & 0xFF) / 255.0f;
        float var8 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var9 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var10 = (float)(var5 & 0xFF) / 255.0f;
        float var11 = (float)(var6 >> 24 & 0xFF) / 255.0f;
        float var12 = (float)(var6 >> 16 & 0xFF) / 255.0f;
        float var13 = (float)(var6 >> 8 & 0xFF) / 255.0f;
        float var14 = (float)(var6 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Tessellator var15 = Tessellator.field_78398_a;
        var15.func_78382_b();
        var15.func_78369_a(var8, var9, var10, var7);
        var15.func_78377_a((double)var3, (double)var2, (double)zLevel);
        var15.func_78377_a((double)var1, (double)var2, (double)zLevel);
        var15.func_78369_a(var12, var13, var14, var11);
        var15.func_78377_a((double)var1, (double)var4, (double)zLevel);
        var15.func_78377_a((double)var3, (double)var4, (double)zLevel);
        var15.func_78381_a();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    @SideOnly(value=Side.CLIENT)
    public static void onResourceReload() {
        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                block5: {
                    try {
                        URL url = new URL("https://apiv2.nationsglory.fr/mods/staff_members");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<String>>(){}.getType();
                        whitelistedStaff = Arrays.asList("MisterSand", "iBalix");
                        try {
                            whitelistedStaff = (List)new Gson().fromJson((Reader)new InputStreamReader(url.openStream()), listType);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        String[] blocks = new String[]{"stone", "dirt", "grass_top"};
                        ClientData.textureClean = true;
                        if (whitelistedStaff.contains(Minecraft.func_71410_x().func_110432_I().func_111285_a())) break block5;
                        for (String block : blocks) {
                            if (NationsGUIClientHooks.checkResource(new ResourceLocation("textures/blocks/" + block + ".png"))) continue;
                            ClientData.textureClean = false;
                            break;
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    private static boolean checkResource(ResourceLocation resourceLocation) throws IOException {
        boolean clean = true;
        Resource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(resourceLocation);
        BufferedImage bufferedimage = ImageIO.read(resource.func_110527_b());
        for (int y = 0; y < bufferedimage.getHeight(); ++y) {
            for (int x = 0; x < bufferedimage.getWidth(); ++x) {
                int color = bufferedimage.getRGB(x, y);
                int alpha = color >> 24 & 0xFF;
                if (alpha >= 255) continue;
                System.out.println("Unauthorized Texture : " + resourceLocation.func_110623_a());
                clean = false;
                break;
            }
            if (!clean) break;
        }
        return clean;
    }

    public static void handleSetupFog(EntityRenderer renderer, int par1, float par2) {
        EntityLivingBase entitylivingbase = NationsGUIClientHooks.mc.field_71451_h;
        boolean flag = false;
        float fogColorRed = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78518_n", "fogColorRed"})).floatValue();
        float fogColorGreen = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78519_o", "fogColorGreen"})).floatValue();
        float fogColorBlue = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78533_p", "fogColorBlue"})).floatValue();
        float farPlaneDistance = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78530_s", "farPlaneDistance"})).floatValue();
        boolean cloudFog = (Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78500_U", "cloudFog"});
        Method setFCB = ReflectionHelper.findMethod(EntityRenderer.class, (Object)renderer, (String[])new String[]{"setFogColorBuffer", "func_78469_a"}, (Class[])new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
        try {
            if (entitylivingbase instanceof EntityPlayer) {
                flag = ((EntityPlayer)entitylivingbase).field_71075_bZ.field_75098_d;
            }
            if (par1 == 999) {
                GL11.glFog((int)2918, (FloatBuffer)((FloatBuffer)setFCB.invoke(renderer, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f))));
                GL11.glFogi((int)2917, (int)9729);
                GL11.glFogf((int)2915, (float)0.0f);
                GL11.glFogf((int)2916, (float)8.0f);
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    GL11.glFogi((int)34138, (int)34139);
                }
                GL11.glFogf((int)2915, (float)0.0f);
            } else {
                GL11.glFog((int)2918, (FloatBuffer)((FloatBuffer)setFCB.invoke(renderer, Float.valueOf(fogColorRed), Float.valueOf(fogColorGreen), Float.valueOf(fogColorBlue), Float.valueOf(1.0f))));
                GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int block = ActiveRenderInfo.func_74584_a((World)NationsGUIClientHooks.mc.field_71441_e, (EntityLivingBase)entitylivingbase, (float)par2);
                EntityViewRenderEvent.FogDensity event = new EntityViewRenderEvent.FogDensity(renderer, entitylivingbase, block, par2, 0.1f);
                if (MinecraftForge.EVENT_BUS.post((Event)event)) {
                    GL11.glFogf((int)2914, (float)event.density);
                } else if (entitylivingbase.func_70644_a(Potion.field_76440_q)) {
                    float f1 = 5.0f;
                    int j = entitylivingbase.func_70660_b(Potion.field_76440_q).func_76459_b();
                    if (j < 20) {
                        f1 = 5.0f + (farPlaneDistance - 5.0f) * (1.0f - (float)j / 20.0f);
                    }
                    GL11.glFogi((int)2917, (int)9729);
                    if (par1 < 0) {
                        GL11.glFogf((int)2915, (float)0.0f);
                        GL11.glFogf((int)2916, (float)(f1 * 0.8f));
                    } else {
                        GL11.glFogf((int)2915, (float)(f1 * 0.25f));
                        GL11.glFogf((int)2916, (float)f1);
                    }
                    if (GLContext.getCapabilities().GL_NV_fog_distance) {
                        GL11.glFogi((int)34138, (int)34139);
                    }
                } else if (cloudFog) {
                    GL11.glFogi((int)2917, (int)2048);
                    GL11.glFogf((int)2914, (float)0.1f);
                } else if (block == Block.field_71942_A.field_71990_ca || block == Block.field_71943_B.field_71990_ca) {
                    GL11.glFogi((int)2917, (int)2048);
                    if (entitylivingbase.func_70644_a(Potion.field_76427_o)) {
                        GL11.glFogf((int)2914, (float)0.05f);
                    } else {
                        GL11.glFogf((int)2914, (float)(0.1f - (float)EnchantmentHelper.func_77501_a((EntityLivingBase)entitylivingbase) * 0.03f));
                    }
                } else if (block == Block.field_71944_C.field_71990_ca || block == Block.field_71938_D.field_71990_ca) {
                    GL11.glFogi((int)2917, (int)2048);
                    GL11.glFogf((int)2914, (float)2.0f);
                } else {
                    double d0;
                    float f1 = farPlaneDistance;
                    if (NationsGUIClientHooks.mc.field_71441_e.field_73011_w.func_76564_j() && !flag && (d0 = (double)((entitylivingbase.func_70070_b(par2) & 0xF00000) >> 20) / 16.0 + (entitylivingbase.field_70137_T + (entitylivingbase.field_70163_u - entitylivingbase.field_70137_T) * (double)par2 + 4.0) / 32.0) < 1.0) {
                        float f2;
                        if (d0 < 0.0) {
                            d0 = 0.0;
                        }
                        if ((f2 = 100.0f * (float)(d0 *= d0)) < 5.0f) {
                            f2 = 5.0f;
                        }
                        if (f1 > f2) {
                            f1 = f2;
                        }
                    }
                    GL11.glFogi((int)2917, (int)9729);
                    if (par1 < 0) {
                        GL11.glFogf((int)2915, (float)0.0f);
                        GL11.glFogf((int)2916, (float)f1);
                    } else {
                        GL11.glFogf((int)2915, (float)(f1 * 0.75f));
                        GL11.glFogf((int)2916, (float)f1);
                    }
                    if (GLContext.getCapabilities().GL_NV_fog_distance) {
                        GL11.glFogi((int)34138, (int)34139);
                    }
                    if (NationsGUIClientHooks.mc.field_71441_e.field_73011_w.func_76568_b((int)entitylivingbase.field_70165_t, (int)entitylivingbase.field_70161_v)) {
                        GL11.glFogf((int)2915, (float)(f1 * 0.05f));
                        GL11.glFogf((int)2916, (float)(Math.min(f1, 192.0f) * 0.5f));
                    }
                    MinecraftForge.EVENT_BUS.post((Event)new EntityViewRenderEvent.RenderFogEvent(renderer, entitylivingbase, block, par2, par1, f1));
                }
                GL11.glEnable((int)2903);
                GL11.glColorMaterial((int)1028, (int)4608);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleUpdateFog(EntityRenderer renderer, float par1) {
        try {
            float f10;
            float f8;
            float f5;
            WorldClient worldclient = NationsGUIClientHooks.mc.field_71441_e;
            EntityLivingBase entitylivingbase = NationsGUIClientHooks.mc.field_71451_h;
            float f1 = 1.0f / (float)(4 - NationsGUIClientHooks.mc.field_71474_y.field_74339_e);
            f1 = 1.0f - (float)Math.pow(f1, 0.25);
            Vec3 vec3 = worldclient.func_72833_a((Entity)NationsGUIClientHooks.mc.field_71451_h, par1);
            float f2 = (float)vec3.field_72450_a;
            float f3 = (float)vec3.field_72448_b;
            float f4 = (float)vec3.field_72449_c;
            Vec3 vec31 = worldclient.func_72948_g(par1);
            float fogColorRed = (float)vec31.field_72450_a;
            float fogColorGreen = (float)vec31.field_72448_b;
            float fogColorBlue = (float)vec31.field_72449_c;
            float fogColor1 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78539_ae", "fogColor1"})).floatValue();
            float fogColor2 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78535_ad", "fogColor2"})).floatValue();
            boolean cloudFog = (Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_78500_U", "cloudFog"});
            float field_82831_U = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_82831_U"})).floatValue();
            float field_82832_V = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, (Object)renderer, (String[])new String[]{"field_82832_V"})).floatValue();
            if (NationsGUIClientHooks.mc.field_71474_y.field_74339_e < 2) {
                float[] afloat;
                Vec3 vec32 = MathHelper.func_76126_a((float)worldclient.func_72929_e(par1)) > 0.0f ? worldclient.func_82732_R().func_72345_a(-1.0, 0.0, 0.0) : worldclient.func_82732_R().func_72345_a(1.0, 0.0, 0.0);
                f5 = (float)entitylivingbase.func_70676_i(par1).func_72430_b(vec32);
                if (f5 < 0.0f) {
                    f5 = 0.0f;
                }
                if (f5 > 0.0f && (afloat = worldclient.field_73011_w.func_76560_a(worldclient.func_72826_c(par1), par1)) != null) {
                    fogColorRed = fogColorRed * (1.0f - (f5 *= afloat[3])) + afloat[0] * f5;
                    fogColorGreen = fogColorGreen * (1.0f - f5) + afloat[1] * f5;
                    fogColorBlue = fogColorBlue * (1.0f - f5) + afloat[2] * f5;
                }
            }
            fogColorRed += (f2 - fogColorRed) * f1;
            fogColorGreen += (f3 - fogColorGreen) * f1;
            fogColorBlue += (f4 - fogColorBlue) * f1;
            float f6 = worldclient.func_72867_j(par1);
            if (f6 > 0.0f) {
                f5 = 1.0f - f6 * 0.5f;
                float f7 = 1.0f - f6 * 0.4f;
                fogColorRed *= f5;
                fogColorGreen *= f5;
                fogColorBlue *= f7;
            }
            if ((f5 = worldclient.func_72819_i(par1)) > 0.0f) {
                float f7 = 1.0f - f5 * 0.5f;
                fogColorRed *= f7;
                fogColorGreen *= f7;
                fogColorBlue *= f7;
            }
            int i = ActiveRenderInfo.func_74584_a((World)NationsGUIClientHooks.mc.field_71441_e, (EntityLivingBase)entitylivingbase, (float)par1);
            if (cloudFog) {
                Vec3 vec33 = worldclient.func_72824_f(par1);
                fogColorRed = (float)vec33.field_72450_a;
                fogColorGreen = (float)vec33.field_72448_b;
                fogColorBlue = (float)vec33.field_72449_c;
            } else if (i != 0 && Block.field_71973_m[i].field_72018_cp == Material.field_76244_g) {
                f8 = (float)EnchantmentHelper.func_77501_a((EntityLivingBase)entitylivingbase) * 0.2f;
                fogColorRed = 0.02f + f8;
                fogColorGreen = 0.02f + f8;
                fogColorBlue = 0.2f + f8;
            } else if (i != 0 && Block.field_71973_m[i].field_72018_cp == Material.field_76256_h) {
                fogColorRed = 0.6f;
                fogColorGreen = 0.1f;
                fogColorBlue = 0.0f;
            }
            f8 = fogColor2 + (fogColor1 - fogColor2) * par1;
            fogColorRed *= f8;
            fogColorGreen *= f8;
            fogColorBlue *= f8;
            double d0 = (entitylivingbase.field_70137_T + (entitylivingbase.field_70163_u - entitylivingbase.field_70137_T) * (double)par1) * worldclient.field_73011_w.func_76565_k();
            if (entitylivingbase.func_70644_a(Potion.field_76440_q)) {
                int j = entitylivingbase.func_70660_b(Potion.field_76440_q).func_76459_b();
                d0 = j < 20 ? (d0 *= (double)(1.0f - (float)j / 20.0f)) : 0.0;
            }
            if (d0 < 1.0) {
                if (d0 < 0.0) {
                    d0 = 0.0;
                }
                d0 *= d0;
                fogColorRed = (float)((double)fogColorRed * d0);
                fogColorGreen = (float)((double)fogColorGreen * d0);
                fogColorBlue = (float)((double)fogColorBlue * d0);
            }
            if (field_82831_U > 0.0f) {
                float f9 = field_82832_V + (field_82831_U - field_82832_V) * par1;
                fogColorRed = fogColorRed * (1.0f - f9) + fogColorRed * 0.7f * f9;
                fogColorGreen = fogColorGreen * (1.0f - f9) + fogColorGreen * 0.6f * f9;
                fogColorBlue = fogColorBlue * (1.0f - f9) + fogColorBlue * 0.6f * f9;
            }
            if (entitylivingbase.func_70644_a(Potion.field_76439_r)) {
                float f9 = ((Float)ReflectionHelper.findMethod(EntityRenderer.class, (Object)renderer, (String[])new String[]{"getNightVisionBrightness", "func_82830_a"}, (Class[])new Class[]{EntityPlayer.class, Float.TYPE}).invoke(renderer, NationsGUIClientHooks.mc.field_71439_g, Float.valueOf(par1))).floatValue();
                f10 = 1.0f / fogColorRed;
                if (f10 > 1.0f / fogColorGreen) {
                    f10 = 1.0f / fogColorGreen;
                }
                if (f10 > 1.0f / fogColorBlue) {
                    f10 = 1.0f / fogColorBlue;
                }
                fogColorRed = fogColorRed * (1.0f - f9) + fogColorRed * f10 * f9;
                fogColorGreen = fogColorGreen * (1.0f - f9) + fogColorGreen * f10 * f9;
                fogColorBlue = fogColorBlue * (1.0f - f9) + fogColorBlue * f10 * f9;
            }
            if (NationsGUIClientHooks.mc.field_71474_y.field_74337_g) {
                float f9 = (fogColorRed * 30.0f + fogColorGreen * 59.0f + fogColorBlue * 11.0f) / 100.0f;
                f10 = (fogColorRed * 30.0f + fogColorGreen * 70.0f) / 100.0f;
                float f11 = (fogColorRed * 30.0f + fogColorBlue * 70.0f) / 100.0f;
                fogColorRed = f9;
                fogColorGreen = f10;
                fogColorBlue = f11;
            }
            EntityViewRenderEvent.FogColors event = new EntityViewRenderEvent.FogColors(renderer, entitylivingbase, i, par1, fogColorRed, fogColorGreen, fogColorBlue);
            MinecraftForge.EVENT_BUS.post((Event)event);
            fogColorRed = event.red;
            fogColorBlue = event.blue;
            fogColorGreen = event.green;
            GL11.glClearColor((float)fogColorRed, (float)fogColorGreen, (float)fogColorBlue, (float)0.0f);
            ReflectionHelper.setPrivateValue(EntityRenderer.class, (Object)renderer, (Object)Float.valueOf(fogColorRed), (String[])new String[]{"fogColorRed", "field_78518_n"});
            ReflectionHelper.setPrivateValue(EntityRenderer.class, (Object)renderer, (Object)Float.valueOf(fogColorRed), (String[])new String[]{"fogColorGreen", "field_78519_o"});
            ReflectionHelper.setPrivateValue(EntityRenderer.class, (Object)renderer, (Object)Float.valueOf(fogColorRed), (String[])new String[]{"fogColorBlue", "field_78533_p"});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDone() {
        return isDone;
    }

    public static void setDone(boolean b) {
        isDone = true;
    }

    public static void sendNotificationToUploadImage(BufferedImage image) {
        if (!ClientData.lastCaptureScreenshot.isEmpty()) {
            for (Map.Entry<String, Long> pair : ClientData.lastCaptureScreenshot.entrySet()) {
                if (System.currentTimeMillis() - pair.getValue() >= 180000L) continue;
                Thread uploader = new Thread(new Uploader(image, pair.getKey()));
                uploader.setDaemon(true);
                uploader.start();
                ClientData.lastCaptureScreenshot.clear();
            }
        }
        String uuid = UUID.randomUUID().toString();
        screenMap.put(uuid, image);
        NBTTagCompound comp = new NBTTagCompound();
        comp.func_74778_a("title", I18n.func_135053_a((String)"screen.upload.title"));
        comp.func_74778_a("content", I18n.func_135053_a((String)"screen.upload.content"));
        comp.func_74772_a("lifetime", 10000L);
        comp.func_74778_a("color", NotificationManager.NColor.CYAN.name());
        comp.func_74778_a("icon", NotificationManager.NIcon.VOTE.name());
        NBTTagCompound allow = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        allow.func_74778_a("translatedTitle", I18n.func_135053_a((String)"screen.uploader.allow"));
        allow.func_74778_a("id", "screen.uploader.allow");
        allow.func_74778_a("args", uuid);
        deny.func_74778_a("translatedTitle", I18n.func_135053_a((String)"screen.uploader.deny"));
        deny.func_74778_a("id", "screen.uploader.deny");
        deny.func_74778_a("args", uuid);
        NBTTagCompound list = new NBTTagCompound();
        list.func_74782_a("allow", (NBTBase)allow);
        list.func_74782_a("deny", (NBTBase)deny);
        comp.func_74782_a("actions", (NBTBase)list);
        ClientData.notifications.add(new Notification(comp));
    }

    public static void uploadToForum(BufferedImage image) {
        if (image != null) {
            if (cooldown < System.currentTimeMillis()) {
                Thread uploader = new Thread(new Uploader(image, null));
                uploader.setDaemon(true);
                uploader.start();
                cooldown = System.currentTimeMillis() + 10000L;
            } else {
                EntityClientPlayerMP me = Minecraft.func_71410_x().field_71439_g;
                if (me == null) {
                    return;
                }
                me.func_71035_c(I18n.func_135052_a((String)"screen.uploader.cooldown", (Object[])new Object[]{(cooldown - System.currentTimeMillis()) / 1000L}));
            }
        }
    }

    public static void doPreChunk(WorldClient worldClient, int par1, int par2, boolean par3) {
        if (par3 && worldClient.func_72863_F().func_73154_d(par1, par2).func_76621_g()) {
            worldClient.func_72863_F().func_73158_c(par1, par2);
        }
        if (!par3) {
            worldClient.func_72909_d(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
        }
    }

    static {
        mc = Minecraft.func_71410_x();
        whitelistedStaff = new ArrayList<String>();
        sentences = new ArrayList<String>();
        passed = new ArrayList<String>();
        MINECRAFT_SCREEN_TEXTURE = new ResourceLocation("textures/gui/minecraft_screen.png");
        LOADING_SCREEN_OVERLAY_TEXTURE = new ResourceLocation("textures/gui/loading_screen_overlay.png");
        LOADING_SCREEN_GUI_SCALE = 2;
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Au d\u00e9but, Il est conseill\u00e9 de rejoindre un pays qui t'invite via le /f join (pays)" : "At first, it is advisable to join a country that invites you via /f join (country).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Une information ? Une question ? Retrouve le wiki sur wiki.nationsglory.fr" : "Need information? Have a question? Find the wiki at wiki.nationsglory.fr");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Une question ? Contacte le Staff via TeamSpeak (ts.nationsglory.fr) ou via Discord..." : "Have a question? Contact the Staff via TeamSpeak (ts.nationsglory.fr) or via Discord.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu es limit\u00e9 \u00e0 4 comptes par foyer (IP), si vous \u00eates plusieurs, partagez-vous ce nombre..." : "You are limited to 4 accounts per household (IP). If you have more people, share this number.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tout dispositif d'anti-afk est interdit et sera sanctionn\u00e9..." : "Any anti-AFK device is prohibited and will be sanctioned.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Bienvenue sur NationsGlory ! Si tu es nouveau, tu es prot\u00e9g\u00e9 pendant 48h de jeu !" : "Welcome to NationsGlory! If you are new, you are protected for 48 hours of gameplay!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Fais toi de l'argent gr\u00e2ce \u00e0 l'HDV, tu peux voir le top des ventes via le /hdv top" : "Make money through the Auction House, you can see the top sales via /hdv top.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Evite les soucis et cours lire le CODEX (les r\u00e8gles de NationsGlory)..." : "Avoid problems and go read the CODEX (NationsGlory rules).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Au d\u00e9but, fais toi un maximum d'argent en allant miner (Utilise le /mine)..." : "At the beginning, make as much money as possible by mining (use /mine).");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Vote pour NationsGlory et gagne des r\u00e9compenses avec le /vote !" : "Vote for NationsGlory and earn rewards with /vote!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Si tu pars en vacances ou en examen, signale ton absence via le /f absence !" : "If you go on vacation or have exams, report your absence via /f absence!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Toute forme d'anti-jeu peut \u00eatre sanctionn\u00e9e, renseigne toi aupr\u00e8s du staff..." : "Any form of anti-gameplay can be sanctioned, check with the staff.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu peux s\u00e9curiser ton compte sur le site pour \u00e9viter le piratage." : "You can secure your account on the website to prevent hacking.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Ton compte est personnel, ne le partage pas." : "Your account is personal, do not share it.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Pour te balader sur NationsGlory, utilise le /warps." : "To navigate around NationsGlory, use /warps.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Un probl\u00e8me avec ton launcher ? Essaie de r\u00e9parer ton installation via les param\u00e8tres..." : "Having trouble with your launcher? Try repairing your installation via settings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Tu peux installer diff\u00e9rents mods depuis les param\u00e8tres de ton launcher..." : "You can install various mods from your launcher settings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Rejoins le discord de NationsGlory : https://discord.gg/nationsglory" : "Join the NationsGlory Discord: https://discord.gg/nationsglory.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Le savais-tu ? Il existe un serveur anglophone sur NationsGlory !" : "Did you know? There is an English-speaking server on NationsGlory.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, le tp-kill est une raison de guerre !" : "Be careful, tp-kill is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, tuer quelqu'un en wilderness est une raison de guerre !" : "Be careful, killing someone in the wilderness is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Attention, l'arnaque est autoris\u00e9e (sauf via la boutique) mais c'est une raison de guerre !" : "Be careful, scamming is allowed (except via the shop), but it is a reason for war!");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Fais attention aux permissions que tu donnes, tout le monde n'est pas de bonne volont\u00e9..." : "Pay attention to the permissions you give; not everyone has good intentions.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "N'oublie pas de poser le /f point chaque semaine \u00e0 l'endroit o\u00f9 tu construis pour les notations..." : "Don't forget to place the /f point every week at your construction site for ratings.");
        sentences.add(System.getProperty("java.lang").equals("fr") ? "Pour te faire de l'argent, r\u00e9cup\u00e8res ton ATM \u00e0 la Capitale de ton serveur !" : "To make money, collect your ATM at the Capital of your server!");
        screenMap = new HashMap<String, BufferedImage>();
        NotificationManager.registerAction("screen.uploader.allow", new INotificationActionHandler(){

            @Override
            public void handleAction(EntityPlayer entityPlayer, NBTTagCompound data) {
                NationsGUIClientHooks.uploadToForum(screenMap.remove(data.func_74779_i("screenId")));
            }
        });
        NotificationManager.registerAction("screen.uploader.deny", new INotificationActionHandler(){

            @Override
            public void handleAction(EntityPlayer entityPlayer, NBTTagCompound data) {
                screenMap.remove(data.func_74779_i("screenId"));
            }
        });
    }

    public static class Uploader
    implements Runnable {
        private BufferedImage image;
        private String imageName;

        public Uploader(BufferedImage image, String imageName) {
            this.image = image;
            this.imageName = imageName;
        }

        @Override
        public void run() {
            Minecraft mc = Minecraft.func_71410_x();
            EntityClientPlayerMP me = mc.field_71439_g;
            if (this.imageName != null) {
                Graphics2D graphics2D;
                BufferedImage resizedImage;
                BufferedImage croppedImage;
                int offsetHeight;
                int targetHeight;
                int initialWidth;
                if (this.imageName.startsWith("plot")) {
                    initialWidth = this.image.getWidth();
                    targetHeight = (int)((double)initialWidth / 1.93);
                    offsetHeight = (this.image.getHeight() - targetHeight) / 2;
                    croppedImage = this.image.getSubimage(0, offsetHeight, initialWidth, Math.min(targetHeight, this.image.getHeight()));
                    resizedImage = new BufferedImage(675, 348, 1);
                    graphics2D = resizedImage.createGraphics();
                    graphics2D.drawImage(croppedImage, 0, 0, 675, 348, null);
                    graphics2D.dispose();
                    this.image = resizedImage;
                } else if (!this.imageName.equals("gallery")) {
                    initialWidth = this.image.getWidth();
                    targetHeight = (int)((double)initialWidth / 2.53);
                    offsetHeight = (this.image.getHeight() - targetHeight) / 2;
                    croppedImage = this.image.getSubimage(0, offsetHeight, initialWidth, Math.min(targetHeight, this.image.getHeight()));
                    resizedImage = new BufferedImage(837, 330, 1);
                    graphics2D = resizedImage.createGraphics();
                    graphics2D.drawImage(croppedImage, 0, 0, 837, 330, null);
                    graphics2D.dispose();
                    this.image = resizedImage;
                } else {
                    initialWidth = this.image.getWidth();
                    targetHeight = (int)((double)initialWidth / 1.78);
                    offsetHeight = (this.image.getHeight() - targetHeight) / 2;
                    croppedImage = this.image.getSubimage(0, offsetHeight, initialWidth, Math.min(targetHeight, this.image.getHeight()));
                    resizedImage = new BufferedImage(573, 321, 1);
                    graphics2D = resizedImage.createGraphics();
                    graphics2D.drawImage(croppedImage, 0, 0, 573, 321, null);
                    graphics2D.dispose();
                    this.image = resizedImage;
                }
            }
            if (me == null) {
                return;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write((RenderedImage)this.image, "png", baos);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            URL url = null;
            try {
                url = new URL("https://apiv2.nationsglory.fr/mods/sharing_uploader");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Session sess = Minecraft.func_71410_x().func_110432_I();
            String data = "";
            try {
                data = URLEncoder.encode("file", "UTF-8") + "=" + URLEncoder.encode(new String(Base64.encode((byte[])baos.toByteArray()), StandardCharsets.US_ASCII), "UTF-8");
                data = data + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(sess.func_111285_a(), "UTF-8");
                data = data + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(sess.func_111286_b(), "UTF-8");
                data = data + "&" + URLEncoder.encode("dated", "UTF-8") + "=" + URLEncoder.encode(NBTConfig.CONFIG.getCompound().func_74767_n("DatedScreenshot") + "", "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            conn.setDoOutput(true);
            OutputStreamWriter wr = null;
            try {
                wr = new OutputStreamWriter(conn.getOutputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wr.write(data);
                wr.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = null;
            try {
                if (conn.getResponseCode() == 400) {
                    is = conn.getErrorStream();
                } else {
                    try {
                        is = conn.getInputStream();
                    }
                    catch (IOException e1) {
                        me.func_71035_c(I18n.func_135053_a((String)"screen.uploader.http_error"));
                    }
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                String line;
                JsonParser parser = new JsonParser();
                while ((line = rd.readLine()) != null) {
                    try {
                        JsonObject json = (JsonObject)parser.parse(line);
                        if (json.get("error") != null) {
                            me.func_71035_c(I18n.func_135052_a((String)"screen.uploader.error", (Object[])new Object[]{I18n.func_135053_a((String)json.get("error").getAsString())}));
                            continue;
                        }
                        if (json.get("location") == null) continue;
                        if (this.imageName != null) {
                            if (this.imageName.startsWith("plot")) {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotAddImagePacket(Double.valueOf(Double.parseDouble(this.imageName.split("#")[1])).intValue(), json.get("location").getAsString())));
                                continue;
                            }
                            if (!this.imageName.equals("gallery")) {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionUpdateBannerPacket(this.imageName, json.get("location").getAsString())));
                                continue;
                            }
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGalleryAddImagePacket(json.get("location").getAsString())));
                            continue;
                        }
                        me.func_71035_c(I18n.func_135052_a((String)"screen.uploader.uploaded", (Object[])new Object[]{I18n.func_135053_a((String)json.get("location").getAsString())}));
                    }
                    catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                rd.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

