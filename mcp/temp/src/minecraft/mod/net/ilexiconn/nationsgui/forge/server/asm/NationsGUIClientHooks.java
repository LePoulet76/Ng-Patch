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
import org.lwjgl.opengl.GLContext;

@SideOnly(Side.CLIENT)
public enum NationsGUIClientHooks {

   INSTANCE("INSTANCE", 0);
   static Minecraft mc = Minecraft.func_71410_x();
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
   // $FF: synthetic field
   private static final NationsGUIClientHooks[] $VALUES = new NationsGUIClientHooks[]{INSTANCE};


   private NationsGUIClientHooks(String var1, int var2) {}

   public void onDrawScreen() {
      if(ClientEventHandler.getInstance().snackbarGUI != null) {
         ClientEventHandler.getInstance().snackbarGUI.drawSnackbar();
      }

   }

   public static void resetOpenGL() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void drawLoadingScreen() {
      if(Minecraft.func_71410_x().func_110442_L() != null) {
         if(this.textRendererDungeons == null) {
            this.textRendererDungeons = FontManager.createClientHookFontDungeons();
            if(this.textRendererDungeons != null) {
               this.textRendererDungeons.setFontSize(20.0F);
            }
         }

         if(this.textRendererGeoramaSemiBold == null) {
            this.textRendererGeoramaSemiBold = FontManager.createClientHookFontGeorama();
            if(this.textRendererGeoramaSemiBold != null) {
               this.textRendererGeoramaSemiBold.setFontSize(15.0F);
            }
         }
      }

      if(this.time % 120.0D == 0.0D) {
         this.changeTitle();
      }

      Minecraft mc = Minecraft.func_71410_x();
      ScaledResolution scaledresolution = new ScaledResolution(mc.field_71474_y, mc.field_71443_c, mc.field_71440_d);
      GL11.glClear(16640);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glViewport(0, 0, mc.field_71443_c, mc.field_71440_d);
      GL11.glDisable(2896);
      GL11.glEnable(3553);
      GL11.glDisable(2912);
      Tessellator tessellator = Tessellator.field_78398_a;
      ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_loading");
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, scaledresolution.func_78326_a(), scaledresolution.func_78328_b(), 3840.0F, 2160.0F, false);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3553);
      mc.func_110434_K().func_110577_a(MINECRAFT_SCREEN_TEXTURE);
      int logoWidth = (int)((float)scaledresolution.func_78326_a() * 0.174F);
      int logoHeight = (int)((float)logoWidth * 0.19F);
      ModernGui.drawScaledCustomSizeModalRect(12.0F, 12.0F, (float)(709 * LOADING_SCREEN_GUI_SCALE), 0.0F, 335 * LOADING_SCREEN_GUI_SCALE, 64 * LOADING_SCREEN_GUI_SCALE, logoWidth, logoHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
      GL11.glDisable(3042);
      GL11.glDisable(3553);
      GL11.glPopMatrix();
      resetOpenGL();
      if(ModernGui.isRemoteTextureLoaded("https://apiv2.nationsglory.fr/proxy_images/screen_loading")) {
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2896);
         GL11.glEnable(3553);
         byte loadingTextX = 12;
         int loadingTextY = scaledresolution.func_78328_b() - 45;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if(this.textRendererDungeons != null) {
            this.textRendererDungeons.drawString("CHARGEMENT...", (float)loadingTextX, (float)loadingTextY, 16777215);
         }

         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2896);
         GL11.glEnable(3553);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         byte helpTextX = 12;
         int helpTextY = scaledresolution.func_78328_b() - 28;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if(this.textRendererGeoramaSemiBold != null) {
            this.textRendererGeoramaSemiBold.drawString(this.currentTitle, (float)helpTextX, (float)helpTextY, 8882056);
         }

         GL11.glPopMatrix();
         byte barX = 12;
         int barY = scaledresolution.func_78328_b() - 15;
         int barWidth = scaledresolution.func_78326_a() - 24;
         int barHeight = (int)((float)barWidth * 0.008928571F);
         double progress = Math.min(1.0D, this.time / 812.0D);
         mc.func_110434_K().func_110577_a(MINECRAFT_SCREEN_TEXTURE);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ModernGui.drawScaledCustomSizeModalRect((float)barX, (float)barY, 0.0F, (float)(228 * LOADING_SCREEN_GUI_SCALE), 1792 * LOADING_SCREEN_GUI_SCALE, 16 * LOADING_SCREEN_GUI_SCALE, barWidth, barHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)barX, (float)barY, 0.0F, (float)(260 * LOADING_SCREEN_GUI_SCALE), (int)(progress * 1792.0D * (double)LOADING_SCREEN_GUI_SCALE), 16 * LOADING_SCREEN_GUI_SCALE, (int)(progress * (double)barWidth), barHeight, (float)(1792 * LOADING_SCREEN_GUI_SCALE), (float)(276 * LOADING_SCREEN_GUI_SCALE), false);
      }

      Display.update();
      ++this.time;
   }

   private void changeTitle() {
      String newTitle = (String)sentences.toArray()[(new Random()).nextInt(sentences.size())];
      if(passed.contains(newTitle)) {
         this.changeTitle();
      } else {
         this.currentTitle = newTitle;
         passed.add(newTitle);
      }
   }

   @SideOnly(Side.CLIENT)
   public static void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3) {
      Minecraft mc = Minecraft.func_71410_x();
      List list = par1ItemStack.func_82840_a(mc.field_71439_g, mc.field_71474_y.field_82882_x);

      int color;
      for(color = 0; color < list.size(); ++color) {
         if(color == 0) {
            list.set(color, "\u00a7" + Integer.toHexString(par1ItemStack.func_77953_t().field_77937_e) + list.get(color));
         } else {
            list.set(color, EnumChatFormatting.GRAY + (String)list.get(color));
         }
      }

      color = 1347420415;
      int background = -267386864;
      if(par1ItemStack.func_77973_b() instanceof ICustomTooltip) {
         ICustomTooltip font = (ICustomTooltip)par1ItemStack.func_77973_b();
         int c = font.getTooltipColor(par1ItemStack);
         int b = font.getTooltipBackgroundColor(par1ItemStack);
         if(c != 255) {
            color = c;
         }

         if(b != 255) {
            background = b;
         }
      }

      FontRenderer var10 = par1ItemStack.func_77973_b().getFontRenderer(par1ItemStack);
      drawHoveringText(mc.field_71462_r, list, par2, par3, var10 == null?Minecraft.func_71410_x().field_71466_p:var10, color, background);
   }

   @SideOnly(Side.CLIENT)
   public static void drawHoveringText(GuiScreen guiScreen, List par1List, int par2, int par3, FontRenderer font, int color, int backgroudColor) {
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

         int var18 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var18 + k > guiScreen.field_73880_f) {
            var18 -= 28 + k;
         }

         if(j1 + k1 + 6 > guiScreen.field_73881_g) {
            j1 = guiScreen.field_73881_g - k1 - 6;
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

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var18, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         zLevel = 0.0F;
         GL11.glEnable(2896);
         GL11.glEnable(2929);
         RenderHelper.func_74519_b();
         GL11.glEnable('\u803a');
      }

   }

   @SideOnly(Side.CLIENT)
   public static void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6, float zLevel) {
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      float var11 = (float)(var6 >> 24 & 255) / 255.0F;
      float var12 = (float)(var6 >> 16 & 255) / 255.0F;
      float var13 = (float)(var6 >> 8 & 255) / 255.0F;
      float var14 = (float)(var6 & 255) / 255.0F;
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      Tessellator var15 = Tessellator.field_78398_a;
      var15.func_78382_b();
      var15.func_78369_a(var8, var9, var10, var7);
      var15.func_78377_a((double)var3, (double)var2, (double)zLevel);
      var15.func_78377_a((double)var1, (double)var2, (double)zLevel);
      var15.func_78369_a(var12, var13, var14, var11);
      var15.func_78377_a((double)var1, (double)var4, (double)zLevel);
      var15.func_78377_a((double)var3, (double)var4, (double)zLevel);
      var15.func_78381_a();
      GL11.glShadeModel(7424);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glEnable(3553);
   }

   @SideOnly(Side.CLIENT)
   public static void onResourceReload() {
      Thread t = new Thread(new NationsGUIClientHooks$1());
      t.start();
   }

   private static boolean checkResource(ResourceLocation resourceLocation) throws IOException {
      boolean clean = true;
      Resource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(resourceLocation);
      BufferedImage bufferedimage = ImageIO.read(resource.func_110527_b());

      for(int y = 0; y < bufferedimage.getHeight(); ++y) {
         for(int x = 0; x < bufferedimage.getWidth(); ++x) {
            int color = bufferedimage.getRGB(x, y);
            int alpha = color >> 24 & 255;
            if(alpha < 255) {
               System.out.println("Unauthorized Texture : " + resourceLocation.func_110623_a());
               clean = false;
               break;
            }
         }

         if(!clean) {
            break;
         }
      }

      return clean;
   }

   public static void handleSetupFog(EntityRenderer renderer, int par1, float par2) {
      EntityLivingBase entitylivingbase = mc.field_71451_h;
      boolean flag = false;
      float fogColorRed = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78518_n", "fogColorRed"})).floatValue();
      float fogColorGreen = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78519_o", "fogColorGreen"})).floatValue();
      float fogColorBlue = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78533_p", "fogColorBlue"})).floatValue();
      float farPlaneDistance = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78530_s", "farPlaneDistance"})).floatValue();
      boolean cloudFog = ((Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78500_U", "cloudFog"})).booleanValue();
      Method setFCB = ReflectionHelper.findMethod(EntityRenderer.class, renderer, new String[]{"setFogColorBuffer", "func_78469_a"}, new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});

      try {
         if(entitylivingbase instanceof EntityPlayer) {
            flag = ((EntityPlayer)entitylivingbase).field_71075_bZ.field_75098_d;
         }

         if(par1 == 999) {
            GL11.glFog(2918, (FloatBuffer)setFCB.invoke(renderer, new Object[]{Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(1.0F)}));
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, 0.0F);
            GL11.glFogf(2916, 8.0F);
            if(GLContext.getCapabilities().GL_NV_fog_distance) {
               GL11.glFogi('\u855a', '\u855b');
            }

            GL11.glFogf(2915, 0.0F);
         } else {
            GL11.glFog(2918, (FloatBuffer)setFCB.invoke(renderer, new Object[]{Float.valueOf(fogColorRed), Float.valueOf(fogColorGreen), Float.valueOf(fogColorBlue), Float.valueOf(1.0F)}));
            GL11.glNormal3f(0.0F, -1.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int e = ActiveRenderInfo.func_74584_a(mc.field_71441_e, entitylivingbase, par2);
            EntityViewRenderEvent$FogDensity event = new EntityViewRenderEvent$FogDensity(renderer, entitylivingbase, e, (double)par2, 0.1F);
            if(MinecraftForge.EVENT_BUS.post(event)) {
               GL11.glFogf(2914, event.density);
            } else {
               float f1;
               if(entitylivingbase.func_70644_a(Potion.field_76440_q)) {
                  f1 = 5.0F;
                  int d0 = entitylivingbase.func_70660_b(Potion.field_76440_q).func_76459_b();
                  if(d0 < 20) {
                     f1 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - (float)d0 / 20.0F);
                  }

                  GL11.glFogi(2917, 9729);
                  if(par1 < 0) {
                     GL11.glFogf(2915, 0.0F);
                     GL11.glFogf(2916, f1 * 0.8F);
                  } else {
                     GL11.glFogf(2915, f1 * 0.25F);
                     GL11.glFogf(2916, f1);
                  }

                  if(GLContext.getCapabilities().GL_NV_fog_distance) {
                     GL11.glFogi('\u855a', '\u855b');
                  }
               } else if(cloudFog) {
                  GL11.glFogi(2917, 2048);
                  GL11.glFogf(2914, 0.1F);
               } else if(e != Block.field_71942_A.field_71990_ca && e != Block.field_71943_B.field_71990_ca) {
                  if(e != Block.field_71944_C.field_71990_ca && e != Block.field_71938_D.field_71990_ca) {
                     f1 = farPlaneDistance;
                     if(mc.field_71441_e.field_73011_w.func_76564_j() && !flag) {
                        double d01 = (double)((entitylivingbase.func_70070_b(par2) & 15728640) >> 20) / 16.0D + (entitylivingbase.field_70137_T + (entitylivingbase.field_70163_u - entitylivingbase.field_70137_T) * (double)par2 + 4.0D) / 32.0D;
                        if(d01 < 1.0D) {
                           if(d01 < 0.0D) {
                              d01 = 0.0D;
                           }

                           d01 *= d01;
                           float f2 = 100.0F * (float)d01;
                           if(f2 < 5.0F) {
                              f2 = 5.0F;
                           }

                           if(farPlaneDistance > f2) {
                              f1 = f2;
                           }
                        }
                     }

                     GL11.glFogi(2917, 9729);
                     if(par1 < 0) {
                        GL11.glFogf(2915, 0.0F);
                        GL11.glFogf(2916, f1);
                     } else {
                        GL11.glFogf(2915, f1 * 0.75F);
                        GL11.glFogf(2916, f1);
                     }

                     if(GLContext.getCapabilities().GL_NV_fog_distance) {
                        GL11.glFogi('\u855a', '\u855b');
                     }

                     if(mc.field_71441_e.field_73011_w.func_76568_b((int)entitylivingbase.field_70165_t, (int)entitylivingbase.field_70161_v)) {
                        GL11.glFogf(2915, f1 * 0.05F);
                        GL11.glFogf(2916, Math.min(f1, 192.0F) * 0.5F);
                     }

                     MinecraftForge.EVENT_BUS.post(new EntityViewRenderEvent$RenderFogEvent(renderer, entitylivingbase, e, (double)par2, par1, f1));
                  } else {
                     GL11.glFogi(2917, 2048);
                     GL11.glFogf(2914, 2.0F);
                  }
               } else {
                  GL11.glFogi(2917, 2048);
                  if(entitylivingbase.func_70644_a(Potion.field_76427_o)) {
                     GL11.glFogf(2914, 0.05F);
                  } else {
                     GL11.glFogf(2914, 0.1F - (float)EnchantmentHelper.func_77501_a(entitylivingbase) * 0.03F);
                  }
               }
            }

            GL11.glEnable(2903);
            GL11.glColorMaterial(1028, 4608);
         }
      } catch (Exception var17) {
         var17.printStackTrace();
      }

   }

   public static void handleUpdateFog(EntityRenderer renderer, float par1) {
      try {
         WorldClient e = mc.field_71441_e;
         EntityLivingBase entitylivingbase = mc.field_71451_h;
         float f1 = 1.0F / (float)(4 - mc.field_71474_y.field_74339_e);
         f1 = 1.0F - (float)Math.pow((double)f1, 0.25D);
         Vec3 vec3 = e.func_72833_a(mc.field_71451_h, par1);
         float f2 = (float)vec3.field_72450_a;
         float f3 = (float)vec3.field_72448_b;
         float f4 = (float)vec3.field_72449_c;
         Vec3 vec31 = e.func_72948_g(par1);
         float fogColorRed = (float)vec31.field_72450_a;
         float fogColorGreen = (float)vec31.field_72448_b;
         float fogColorBlue = (float)vec31.field_72449_c;
         float fogColor1 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78539_ae", "fogColor1"})).floatValue();
         float fogColor2 = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78535_ad", "fogColor2"})).floatValue();
         boolean cloudFog = ((Boolean)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_78500_U", "cloudFog"})).booleanValue();
         float field_82831_U = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_82831_U"})).floatValue();
         float field_82832_V = ((Float)ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"field_82832_V"})).floatValue();
         float f5;
         if(mc.field_71474_y.field_74339_e < 2) {
            Vec3 f6 = MathHelper.func_76126_a(e.func_72929_e(par1)) > 0.0F?e.func_82732_R().func_72345_a(-1.0D, 0.0D, 0.0D):e.func_82732_R().func_72345_a(1.0D, 0.0D, 0.0D);
            f5 = (float)entitylivingbase.func_70676_i(par1).func_72430_b(f6);
            if(f5 < 0.0F) {
               f5 = 0.0F;
            }

            if(f5 > 0.0F) {
               float[] f7 = e.field_73011_w.func_76560_a(e.func_72826_c(par1), par1);
               if(f7 != null) {
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
         float f61 = e.func_72867_j(par1);
         float f71;
         if(f61 > 0.0F) {
            f5 = 1.0F - f61 * 0.5F;
            f71 = 1.0F - f61 * 0.4F;
            fogColorRed *= f5;
            fogColorGreen *= f5;
            fogColorBlue *= f71;
         }

         f5 = e.func_72819_i(par1);
         if(f5 > 0.0F) {
            f71 = 1.0F - f5 * 0.5F;
            fogColorRed *= f71;
            fogColorGreen *= f71;
            fogColorBlue *= f71;
         }

         int i = ActiveRenderInfo.func_74584_a(mc.field_71441_e, entitylivingbase, par1);
         float f8;
         if(cloudFog) {
            Vec3 d0 = e.func_72824_f(par1);
            fogColorRed = (float)d0.field_72450_a;
            fogColorGreen = (float)d0.field_72448_b;
            fogColorBlue = (float)d0.field_72449_c;
         } else if(i != 0 && Block.field_71973_m[i].field_72018_cp == Material.field_76244_g) {
            f8 = (float)EnchantmentHelper.func_77501_a(entitylivingbase) * 0.2F;
            fogColorRed = 0.02F + f8;
            fogColorGreen = 0.02F + f8;
            fogColorBlue = 0.2F + f8;
         } else if(i != 0 && Block.field_71973_m[i].field_72018_cp == Material.field_76256_h) {
            fogColorRed = 0.6F;
            fogColorGreen = 0.1F;
            fogColorBlue = 0.0F;
         }

         f8 = fogColor2 + (fogColor1 - fogColor2) * par1;
         fogColorRed *= f8;
         fogColorGreen *= f8;
         fogColorBlue *= f8;
         double d01 = (entitylivingbase.field_70137_T + (entitylivingbase.field_70163_u - entitylivingbase.field_70137_T) * (double)par1) * e.field_73011_w.func_76565_k();
         if(entitylivingbase.func_70644_a(Potion.field_76440_q)) {
            int f9 = entitylivingbase.func_70660_b(Potion.field_76440_q).func_76459_b();
            if(f9 < 20) {
               d01 *= (double)(1.0F - (float)f9 / 20.0F);
            } else {
               d01 = 0.0D;
            }
         }

         if(d01 < 1.0D) {
            if(d01 < 0.0D) {
               d01 = 0.0D;
            }

            d01 *= d01;
            fogColorRed = (float)((double)fogColorRed * d01);
            fogColorGreen = (float)((double)fogColorGreen * d01);
            fogColorBlue = (float)((double)fogColorBlue * d01);
         }

         float f91;
         if(field_82831_U > 0.0F) {
            f91 = field_82832_V + (field_82831_U - field_82832_V) * par1;
            fogColorRed = fogColorRed * (1.0F - f91) + fogColorRed * 0.7F * f91;
            fogColorGreen = fogColorGreen * (1.0F - f91) + fogColorGreen * 0.6F * f91;
            fogColorBlue = fogColorBlue * (1.0F - f91) + fogColorBlue * 0.6F * f91;
         }

         float f10;
         if(entitylivingbase.func_70644_a(Potion.field_76439_r)) {
            f91 = ((Float)ReflectionHelper.findMethod(EntityRenderer.class, renderer, new String[]{"getNightVisionBrightness", "func_82830_a"}, new Class[]{EntityPlayer.class, Float.TYPE}).invoke(renderer, new Object[]{mc.field_71439_g, Float.valueOf(par1)})).floatValue();
            f10 = 1.0F / fogColorRed;
            if(f10 > 1.0F / fogColorGreen) {
               f10 = 1.0F / fogColorGreen;
            }

            if(f10 > 1.0F / fogColorBlue) {
               f10 = 1.0F / fogColorBlue;
            }

            fogColorRed = fogColorRed * (1.0F - f91) + fogColorRed * f10 * f91;
            fogColorGreen = fogColorGreen * (1.0F - f91) + fogColorGreen * f10 * f91;
            fogColorBlue = fogColorBlue * (1.0F - f91) + fogColorBlue * f10 * f91;
         }

         if(mc.field_71474_y.field_74337_g) {
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
         ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[]{"fogColorRed", "field_78518_n"});
         ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[]{"fogColorGreen", "field_78519_o"});
         ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, Float.valueOf(fogColorRed), new String[]{"fogColorBlue", "field_78533_p"});
      } catch (Exception var28) {
         var28.printStackTrace();
      }

   }

   public static boolean isDone() {
      return isDone;
   }

   public static void setDone(boolean b) {
      isDone = true;
   }

   public static void sendNotificationToUploadImage(BufferedImage image) {
      if(!ClientData.lastCaptureScreenshot.isEmpty()) {
         Iterator uuid = ClientData.lastCaptureScreenshot.entrySet().iterator();

         while(uuid.hasNext()) {
            Entry comp = (Entry)uuid.next();
            if(System.currentTimeMillis() - ((Long)comp.getValue()).longValue() < 180000L) {
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
      comp1.func_74778_a("title", I18n.func_135053_a("screen.upload.title"));
      comp1.func_74778_a("content", I18n.func_135053_a("screen.upload.content"));
      comp1.func_74772_a("lifetime", 10000L);
      comp1.func_74778_a("color", NotificationManager$NColor.CYAN.name());
      comp1.func_74778_a("icon", NotificationManager$NIcon.VOTE.name());
      NBTTagCompound allow1 = new NBTTagCompound();
      NBTTagCompound deny = new NBTTagCompound();
      allow1.func_74778_a("translatedTitle", I18n.func_135053_a("screen.uploader.allow"));
      allow1.func_74778_a("id", "screen.uploader.allow");
      allow1.func_74778_a("args", uuid1);
      deny.func_74778_a("translatedTitle", I18n.func_135053_a("screen.uploader.deny"));
      deny.func_74778_a("id", "screen.uploader.deny");
      deny.func_74778_a("args", uuid1);
      NBTTagCompound list = new NBTTagCompound();
      list.func_74782_a("allow", allow1);
      list.func_74782_a("deny", deny);
      comp1.func_74782_a("actions", list);
      ClientData.notifications.add(new Notification(comp1));
   }

   public static void uploadToForum(BufferedImage image) {
      if(image != null) {
         if(cooldown < System.currentTimeMillis()) {
            Thread me = new Thread(new NationsGUIClientHooks$Uploader(image, (String)null));
            me.setDaemon(true);
            me.start();
            cooldown = System.currentTimeMillis() + 10000L;
         } else {
            EntityClientPlayerMP me1 = Minecraft.func_71410_x().field_71439_g;
            if(me1 == null) {
               return;
            }

            me1.func_71035_c(I18n.func_135052_a("screen.uploader.cooldown", new Object[]{Long.valueOf((cooldown - System.currentTimeMillis()) / 1000L)}));
         }
      }

   }

   public static void doPreChunk(WorldClient worldClient, int par1, int par2, boolean par3) {
      if(par3 && worldClient.func_72863_F().func_73154_d(par1, par2).func_76621_g()) {
         worldClient.func_72863_F().func_73158_c(par1, par2);
      }

      if(!par3) {
         worldClient.func_72909_d(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
      }

   }

   // $FF: synthetic method
   static boolean access$000(ResourceLocation x0) throws IOException {
      return checkResource(x0);
   }

   static {
      sentences.add(System.getProperty("java.lang").equals("fr")?"Au d\u00e9but, Il est conseill\u00e9 de rejoindre un pays qui t\'invite via le /f join (pays)":"At first, it is advisable to join a country that invites you via /f join (country).");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Une information ? Une question ? Retrouve le wiki sur wiki.nationsglory.fr":"Need information? Have a question? Find the wiki at wiki.nationsglory.fr");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Une question ? Contacte le Staff via TeamSpeak (ts.nationsglory.fr) ou via Discord...":"Have a question? Contact the Staff via TeamSpeak (ts.nationsglory.fr) or via Discord.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Tu es limit\u00e9 \u00e0 4 comptes par foyer (IP), si vous \u00eates plusieurs, partagez-vous ce nombre...":"You are limited to 4 accounts per household (IP). If you have more people, share this number.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Tout dispositif d\'anti-afk est interdit et sera sanctionn\u00e9...":"Any anti-AFK device is prohibited and will be sanctioned.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Bienvenue sur NationsGlory ! Si tu es nouveau, tu es prot\u00e9g\u00e9 pendant 48h de jeu !":"Welcome to NationsGlory! If you are new, you are protected for 48 hours of gameplay!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Fais toi de l\'argent gr\u00e2ce \u00e0 l\'HDV, tu peux voir le top des ventes via le /hdv top":"Make money through the Auction House, you can see the top sales via /hdv top.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Evite les soucis et cours lire le CODEX (les r\u00e8gles de NationsGlory)...":"Avoid problems and go read the CODEX (NationsGlory rules).");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Au d\u00e9but, fais toi un maximum d\'argent en allant miner (Utilise le /mine)...":"At the beginning, make as much money as possible by mining (use /mine).");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Vote pour NationsGlory et gagne des r\u00e9compenses avec le /vote !":"Vote for NationsGlory and earn rewards with /vote!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Si tu pars en vacances ou en examen, signale ton absence via le /f absence !":"If you go on vacation or have exams, report your absence via /f absence!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Toute forme d\'anti-jeu peut \u00eatre sanctionn\u00e9e, renseigne toi aupr\u00e8s du staff...":"Any form of anti-gameplay can be sanctioned, check with the staff.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Tu peux s\u00e9curiser ton compte sur le site pour \u00e9viter le piratage.":"You can secure your account on the website to prevent hacking.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Ton compte est personnel, ne le partage pas.":"Your account is personal, do not share it.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Pour te balader sur NationsGlory, utilise le /warps.":"To navigate around NationsGlory, use /warps.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Un probl\u00e8me avec ton launcher ? Essaie de r\u00e9parer ton installation via les param\u00e8tres...":"Having trouble with your launcher? Try repairing your installation via settings.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Tu peux installer diff\u00e9rents mods depuis les param\u00e8tres de ton launcher...":"You can install various mods from your launcher settings.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Rejoins le discord de NationsGlory : https://discord.gg/nationsglory":"Join the NationsGlory Discord: https://discord.gg/nationsglory.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Le savais-tu ? Il existe un serveur anglophone sur NationsGlory !":"Did you know? There is an English-speaking server on NationsGlory.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Attention, le tp-kill est une raison de guerre !":"Be careful, tp-kill is a reason for war!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Attention, tuer quelqu\'un en wilderness est une raison de guerre !":"Be careful, killing someone in the wilderness is a reason for war!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Attention, l\'arnaque est autoris\u00e9e (sauf via la boutique) mais c\'est une raison de guerre !":"Be careful, scamming is allowed (except via the shop), but it is a reason for war!");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Fais attention aux permissions que tu donnes, tout le monde n\'est pas de bonne volont\u00e9...":"Pay attention to the permissions you give; not everyone has good intentions.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"N\'oublie pas de poser le /f point chaque semaine \u00e0 l\'endroit o\u00f9 tu construis pour les notations...":"Don\'t forget to place the /f point every week at your construction site for ratings.");
      sentences.add(System.getProperty("java.lang").equals("fr")?"Pour te faire de l\'argent, r\u00e9cup\u00e8res ton ATM \u00e0 la Capitale de ton serveur !":"To make money, collect your ATM at the Capital of your server!");
      screenMap = new HashMap();
      NotificationManager.registerAction("screen.uploader.allow", new NationsGUIClientHooks$2());
      NotificationManager.registerAction("screen.uploader.deny", new NationsGUIClientHooks$3());
   }
}
