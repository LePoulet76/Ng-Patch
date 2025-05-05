package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngcontent.server.potion.PotionParanoia;
import java.util.ArrayList;
import java.util.Random;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerSP;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.edora.common.planet.gen.GCEdoraWorldProvider;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class HealthOverride extends Gui implements ElementOverride {

   private float healthTextureWidth;
   private float foodTextureWidth;
   private float armorTextureWidth;
   private EntityLivingBase lastEntity;
   private float lastWidth;
   private float lastPotionOffset;
   private int f = 25;
   private Long lastHealthRandom = Long.valueOf(0L);
   private Float lastFakeHealth = Float.valueOf(0.0F);
   public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(25));
   protected static final ResourceLocation icons = new ResourceLocation("textures/gui/container/inventory.png");
   private ElementType[] subTypes;


   public HealthOverride() {
      this.subTypes = new ElementType[]{ElementType.ARMOR, ElementType.EXPERIENCE, ElementType.AIR, ElementType.FOOD};
   }

   public ElementType getType() {
      return ElementType.HEALTH;
   }

   public ElementType[] getSubTypes() {
      return this.subTypes;
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      if(ClientData.versusOverlayData.isEmpty() || ((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(client.field_71439_g.field_71092_bJ)) {
         int offsetXMiddle = resolution.func_78326_a() / 2;
         EntityPlayer player = (EntityPlayer)client.field_71451_h;
         ModernGui.bindTextureOverlayMain();
         ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 37), (float)(resolution.func_78328_b() - 36), (float)(414 * GenericOverride.GUI_SCALE), (float)(63 * GenericOverride.GUI_SCALE), 185 * GenericOverride.GUI_SCALE, 19 * GenericOverride.GUI_SCALE, 74, 8, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         float playerHealth = player.func_110143_aJ();
         if(client.field_71439_g.func_70644_a(PotionParanoia.potionParanoia)) {
            if(System.currentTimeMillis() - this.lastHealthRandom.longValue() > 500L) {
               this.lastHealthRandom = Long.valueOf(System.currentTimeMillis());
               this.lastFakeHealth = Float.valueOf((new Random()).nextFloat() * 20.0F);
            }

            playerHealth = this.lastFakeHealth.floatValue();
         }

         ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{Float.valueOf(playerHealth)}), (float)(offsetXMiddle - 37) + 11.5F, (float)(resolution.func_78328_b() - 36) + 1.5F, 16000586, 0.5F, "center", true, "georamaBold", 25);
         ModernGui.bindTextureOverlayMain();
         ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 5) - georamaBold25.getStringWidth(player.field_71068_ca + "") / 2.0F / 2.0F, (float)(resolution.func_78328_b() - 36) + 1.0F, (float)(241 * GenericOverride.GUI_SCALE), (float)(122 * GenericOverride.GUI_SCALE), 8 * GenericOverride.GUI_SCALE, 8 * GenericOverride.GUI_SCALE, 6, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)offsetXMiddle - 4.85F - georamaBold25.getStringWidth(player.field_71068_ca + "") / 2.0F / 2.0F, (float)(resolution.func_78328_b() - 36) + 5.4F - 4.0F * player.field_71106_cc, (float)(251 * GenericOverride.GUI_SCALE), (129.0F - 6.0F * player.field_71106_cc) * (float)GenericOverride.GUI_SCALE, 5 * GenericOverride.GUI_SCALE, 5 * GenericOverride.GUI_SCALE, 5, 5, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         if(ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main")) {
            ModernGui.drawScaledStringCustomFont(player.field_71068_ca + "", (float)(offsetXMiddle + 4), (float)(resolution.func_78328_b() - 36) + 1.5F, 12237530, 0.5F, "center", true, "georamaBold", 25);
         } else {
            ModernGui.drawScaledStringCustomFont(player.field_71068_ca + "", (float)(offsetXMiddle + 4), (float)(resolution.func_78328_b() - 36) + 1.5F, 0, 0.5F, "center", false, "georamaBold", 25);
         }

         ModernGui.bindTextureOverlayMain();
         ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle + 37) - 11.5F - 4.0F - georamaBold25.getStringWidth(player.func_70658_aO() + "") / 2.0F / 2.0F - 1.0F, (float)(resolution.func_78328_b() - 36) + 1.8F, (float)(228 * GenericOverride.GUI_SCALE), (float)(122 * GenericOverride.GUI_SCALE), 10 * GenericOverride.GUI_SCALE, 9 * GenericOverride.GUI_SCALE, 6, 5, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         if(ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main")) {
            ModernGui.drawScaledStringCustomFont(player.func_70658_aO() + "", (float)(offsetXMiddle + 37) - 11.5F + 3.0F, (float)(resolution.func_78328_b() - 36) + 1.5F, 12237530, 0.5F, "center", true, "georamaBold", 25);
         } else {
            ModernGui.drawScaledStringCustomFont(player.func_70658_aO() + "", (float)(offsetXMiddle + 37) - 11.5F + 3.0F, (float)(resolution.func_78328_b() - 36) + 1.5F, 0, 0.5F, "center", false, "georamaBold", 25);
         }

         this.vanillaLife(client, resolution, playerHealth);
         int offsetXProgressBar = resolution.func_78326_a() / 2 + 37 + 80;
         if(client.field_71439_g.func_70090_H()) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(816 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(800 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, (int)(74.0F * (1.0F - (float)client.field_71439_g.func_70086_ai() / 300.0F)) * GenericOverride.GUI_SCALE, 5, (int)((float)((int)(74.0F * (1.0F - (float)client.field_71439_g.func_70086_ai() / 300.0F))) / 2.5F), (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar + 0.9F, (float)(resolution.func_78328_b() - 33 + 29 - 5), (float)(227 * GenericOverride.GUI_SCALE), (float)(81 * GenericOverride.GUI_SCALE), 7 * GenericOverride.GUI_SCALE, 7 * GenericOverride.GUI_SCALE, 3, 3, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            offsetXProgressBar += 10;
         }

         GCCorePlayerSP playerGC;
         float percentage;
         if(Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(864 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            playerGC = PlayerUtil.getPlayerBaseClientFromPlayer(Minecraft.func_71410_x().field_71439_g, false);
            percentage = (playerGC.temperature - -200.0F) / 500.0F;
            percentage = Math.max(0.1F, percentage);
            percentage = Math.min(1.0F, percentage);
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(800 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, (int)((1.0F - percentage) * 74.0F) * GenericOverride.GUI_SCALE, 5, (int)((1.0F - percentage) * 74.0F / 2.5F), (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont("\u00b0C", (float)offsetXProgressBar + 2.5F, (float)(resolution.func_78328_b() - 33 + 2), 16777215, 0.5F, "center", true, "georamaMedium", 16);
            ModernGui.drawScaledStringCustomFont((int)playerGC.temperature + "", (float)(offsetXProgressBar + 2), (float)(resolution.func_78328_b() - 33 - 6), 16777215, 0.5F, "center", true, "georamaMedium", 20);
            offsetXProgressBar += 10;
         }

         if(Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider) {
            playerGC = PlayerUtil.getPlayerBaseClientFromPlayer(Minecraft.func_71410_x().field_71439_g, false);
            percentage = playerGC.gasBottle;
            if(percentage > 0.0F) {
               ModernGui.bindTextureOverlayMain();
               ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(848 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
               ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar, (float)(resolution.func_78328_b() - 33), (float)(800 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, (int)((1.0F - percentage) * 74.0F) * GenericOverride.GUI_SCALE, 5, (int)((1.0F - percentage) * 74.0F / 2.5F), (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
               ModernGui.drawScaledStringCustomFont("%", (float)offsetXProgressBar + 2.5F, (float)(resolution.func_78328_b() - 33 + 2), 16777215, 0.5F, "center", true, "georamaMedium", 16);
               ModernGui.drawScaledStringCustomFont((int)playerGC.gasBottle + "", (float)(offsetXProgressBar + 2), (float)(resolution.func_78328_b() - 33 - 6), 16777215, 0.5F, "center", true, "georamaMedium", 20);
               offsetXProgressBar += 10;
            }
         }

      }
   }

   public void glScissor(int x, int y, int width, int height, Minecraft client, ScaledResolution resolution) {
      double scaleW = (double)client.field_71443_c / resolution.func_78327_c();
      double scaleH = (double)client.field_71440_d / resolution.func_78324_d();
      GL11.glScissor((int)Math.floor((double)x * scaleW), (int)Math.floor((double)client.field_71440_d - (double)(y + height) * scaleH), (int)Math.floor((double)(x + width) * scaleW) - (int)Math.floor((double)x * scaleW), (int)Math.floor((double)client.field_71440_d - (double)y * scaleH) - (int)Math.floor((double)client.field_71440_d - (double)(y + height) * scaleH));
   }

   public void drawTexturedModalRect(float x, float y, int textureX, int textureY, float width, float height) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a((double)x, (double)(y + height), (double)this.field_73735_i, (double)((float)textureX * 0.00390625F), (double)(((float)textureY + height) * 0.00390625F));
      tessellator.func_78374_a((double)(x + width), (double)(y + height), (double)this.field_73735_i, (double)(((float)textureX + width) * 0.00390625F), (double)(((float)textureY + height) * 0.00390625F));
      tessellator.func_78374_a((double)(x + width), (double)y, (double)this.field_73735_i, (double)(((float)textureX + width) * 0.00390625F), (double)((float)textureY * 0.00390625F));
      tessellator.func_78374_a((double)x, (double)y, (double)this.field_73735_i, (double)((float)textureX * 0.00390625F), (double)((float)textureY * 0.00390625F));
      tessellator.func_78381_a();
   }

   public static void drawRect(float par0, float par1, float par2, float par3, int par4) {
      float j1;
      if(par0 < par2) {
         j1 = par0;
         par0 = par2;
         par2 = j1;
      }

      if(par1 < par3) {
         j1 = par1;
         par1 = par3;
         par3 = j1;
      }

      float f = (float)(par4 >> 24 & 255) / 255.0F;
      float f1 = (float)(par4 >> 16 & 255) / 255.0F;
      float f2 = (float)(par4 >> 8 & 255) / 255.0F;
      float f3 = (float)(par4 & 255) / 255.0F;
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(f1, f2, f3, f);
      tessellator.func_78382_b();
      tessellator.func_78377_a((double)par0, (double)par3, 0.0D);
      tessellator.func_78377_a((double)par2, (double)par3, 0.0D);
      tessellator.func_78377_a((double)par2, (double)par1, 0.0D);
      tessellator.func_78377_a((double)par0, (double)par1, 0.0D);
      tessellator.func_78381_a();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   private void vanillaLife(Minecraft client, ScaledResolution resolution, float playerHealth) {
      ModernGui.bindTextureOverlayMain();
      client.func_110434_K().func_110577_a(Gui.field_110324_m);
      int updateCounter = ((Integer)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, client.field_71456_v, new String[]{"updateCounter", "i"})).intValue();
      Random rand = new Random();
      float left = (float)(resolution.func_78326_a() / 2 + 113);
      float top = (float)resolution.func_78328_b() - 35.5F;
      FoodStats stats = client.field_71439_g.func_71024_bL();
      int level = stats.func_75116_a();
      int levelLast = stats.func_75120_b();

      for(int highlight = 0; highlight < 10; ++highlight) {
         int attrMaxHealth = highlight * 2 + 1;
         float health = left - (float)(highlight * 7) - 9.0F;
         float healthLast = top;
         byte healthMax = 16;
         boolean absorb = false;
         if(client.field_71439_g.func_70644_a(Potion.field_76438_s)) {
            int var33 = healthMax + 36;
            absorb = true;
         }

         if(client.field_71439_g.func_71024_bL().func_75115_e() <= 0.0F && updateCounter % (level * 3 + 1) == 0) {
            healthLast = top + (float)(rand.nextInt(3) - 1);
         }

         ModernGui.bindTextureOverlayMain();
         ModernGui.drawScaledCustomSizeModalRect(health, healthLast, (float)(698 * GenericOverride.GUI_SCALE), (float)(65 * GenericOverride.GUI_SCALE), 14 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 7, 7, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         if(attrMaxHealth < level) {
            ModernGui.drawScaledCustomSizeModalRect(health, healthLast - 1.0F, (float)(680 * GenericOverride.GUI_SCALE), (float)(65 * GenericOverride.GUI_SCALE), 14 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 7, 7, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         }

         client.func_110434_K().func_110577_a(Gui.field_110324_m);
      }

      boolean var29 = client.field_71439_g.field_70172_ad / 3 % 2 == 1;
      if(client.field_71439_g.field_70172_ad < 10) {
         var29 = false;
      }

      AttributeInstance var30 = client.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111267_a);
      int var31 = MathHelper.func_76123_f(playerHealth);
      int var32 = MathHelper.func_76123_f(client.field_71439_g.field_70735_aL);
      float var34 = (float)var30.func_111126_e();
      float var35 = client.field_71439_g.func_110139_bj();
      int healthRows = MathHelper.func_76123_f((var34 + var35) / 2.0F / 10.0F);
      int rowHeight = Math.max(10 - (healthRows - 2), 3);
      rand.setSeed((long)(updateCounter * 312871));
      left = (float)(resolution.func_78326_a() / 2) - 114.0F;
      top = (float)resolution.func_78328_b() - 34.5F;
      int regen = -1;
      if(client.field_71439_g.func_70644_a(Potion.field_76428_l)) {
         regen = updateCounter % 25;
      }

      int TOP = 9 * (client.field_71441_e.func_72912_H().func_76093_s()?5:0);
      boolean BACKGROUND = var29?true:true;
      short positionFullHeartTexture = 226;
      if(client.field_71439_g.func_70644_a(Potion.field_76436_u)) {
         positionFullHeartTexture = 286;
      } else if(client.field_71439_g.func_70644_a(Potion.field_82731_v)) {
         positionFullHeartTexture = 271;
      }

      float absorbRemaining = var35;

      for(int i = MathHelper.func_76123_f((var34 + var35) / 2.0F) - 1; i >= 0; --i) {
         boolean b0 = var29;
         int row = MathHelper.func_76123_f((float)(i + 1) / 10.0F) - 1;
         float x = left + (float)(i % 10) * 7.5F;
         float y = top - (float)(row * rowHeight);
         if(var31 <= 4) {
            y += (float)rand.nextInt(2);
         }

         if(i == regen) {
            y -= 2.0F;
         }

         ModernGui.bindTextureOverlayMain();
         ModernGui.drawScaledCustomSizeModalRect(x, y, (float)(241 * GenericOverride.GUI_SCALE), (float)(67 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         if(absorbRemaining > 0.0F) {
            if(absorbRemaining == var35 && var35 % 2.0F == 1.0F) {
               ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0F, (float)(256 * GenericOverride.GUI_SCALE), (float)(67 * GenericOverride.GUI_SCALE), 6 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 3, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            } else {
               ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0F, (float)(256 * GenericOverride.GUI_SCALE), (float)(67 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            }

            absorbRemaining -= 2.0F;
         } else if(i * 2 + 1 < var31) {
            ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0F, (float)(positionFullHeartTexture * GenericOverride.GUI_SCALE), (float)(67 * GenericOverride.GUI_SCALE), 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         } else if(i * 2 + 1 == var31) {
            ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0F, (float)(positionFullHeartTexture * GenericOverride.GUI_SCALE), (float)(67 * GenericOverride.GUI_SCALE), 6 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 3, 6, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
         }
      }

   }

}
