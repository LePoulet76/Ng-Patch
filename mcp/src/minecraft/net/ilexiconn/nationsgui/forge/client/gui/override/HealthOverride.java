/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ObfuscationReflectionHelper
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.ngcontent.server.potion.PotionParanoia
 *  micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerSP
 *  micdoodle8.mods.galacticraft.core.util.PlayerUtil
 *  micdoodle8.mods.galacticraft.edora.common.planet.gen.GCEdoraWorldProvider
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiIngame
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.AttributeInstance
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.FoodStats
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class HealthOverride
extends Gui
implements ElementOverride {
    private float healthTextureWidth;
    private float foodTextureWidth;
    private float armorTextureWidth;
    private EntityLivingBase lastEntity;
    private float lastWidth;
    private float lastPotionOffset;
    private int f = 25;
    private Long lastHealthRandom = 0L;
    private Float lastFakeHealth = Float.valueOf(0.0f);
    public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", 25);
    protected static final ResourceLocation icons = new ResourceLocation("textures/gui/container/inventory.png");
    private RenderGameOverlayEvent.ElementType[] subTypes = new RenderGameOverlayEvent.ElementType[]{RenderGameOverlayEvent.ElementType.ARMOR, RenderGameOverlayEvent.ElementType.EXPERIENCE, RenderGameOverlayEvent.ElementType.AIR, RenderGameOverlayEvent.ElementType.FOOD};

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HEALTH;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return this.subTypes;
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        float percentage;
        GCCorePlayerSP playerGC;
        if (!ClientData.versusOverlayData.isEmpty() && !((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(client.field_71439_g.field_71092_bJ)) {
            return;
        }
        int offsetXMiddle = resolution.func_78326_a() / 2;
        EntityPlayer player = (EntityPlayer)client.field_71451_h;
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle - 37, resolution.func_78328_b() - 36, 414 * GenericOverride.GUI_SCALE, 63 * GenericOverride.GUI_SCALE, 185 * GenericOverride.GUI_SCALE, 19 * GenericOverride.GUI_SCALE, 74, 8, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        float playerHealth = player.func_110143_aJ();
        if (client.field_71439_g.func_70644_a((Potion)PotionParanoia.potionParanoia)) {
            if (System.currentTimeMillis() - this.lastHealthRandom > 500L) {
                this.lastHealthRandom = System.currentTimeMillis();
                this.lastFakeHealth = Float.valueOf(new Random().nextFloat() * 20.0f);
            }
            playerHealth = this.lastFakeHealth.floatValue();
        }
        ModernGui.drawScaledStringCustomFont(String.format("%.0f", Float.valueOf(playerHealth)), (float)(offsetXMiddle - 37) + 11.5f, (float)(resolution.func_78328_b() - 36) + 1.5f, 16000586, 0.5f, "center", true, "georamaBold", 25);
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 5) - georamaBold25.getStringWidth(player.field_71068_ca + "") / 2.0f / 2.0f, (float)(resolution.func_78328_b() - 36) + 1.0f, 241 * GenericOverride.GUI_SCALE, 122 * GenericOverride.GUI_SCALE, 8 * GenericOverride.GUI_SCALE, 8 * GenericOverride.GUI_SCALE, 6, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ModernGui.drawScaledCustomSizeModalRect((float)offsetXMiddle - 4.85f - georamaBold25.getStringWidth(player.field_71068_ca + "") / 2.0f / 2.0f, (float)(resolution.func_78328_b() - 36) + 5.4f - 4.0f * player.field_71106_cc, 251 * GenericOverride.GUI_SCALE, (129.0f - 6.0f * player.field_71106_cc) * (float)GenericOverride.GUI_SCALE, 5 * GenericOverride.GUI_SCALE, 5 * GenericOverride.GUI_SCALE, 5, 5, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        if (ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main")) {
            ModernGui.drawScaledStringCustomFont(player.field_71068_ca + "", offsetXMiddle + 4, (float)(resolution.func_78328_b() - 36) + 1.5f, 0xBABADA, 0.5f, "center", true, "georamaBold", 25);
        } else {
            ModernGui.drawScaledStringCustomFont(player.field_71068_ca + "", offsetXMiddle + 4, (float)(resolution.func_78328_b() - 36) + 1.5f, 0, 0.5f, "center", false, "georamaBold", 25);
        }
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle + 37) - 11.5f - 4.0f - georamaBold25.getStringWidth(player.func_70658_aO() + "") / 2.0f / 2.0f - 1.0f, (float)(resolution.func_78328_b() - 36) + 1.8f, 228 * GenericOverride.GUI_SCALE, 122 * GenericOverride.GUI_SCALE, 10 * GenericOverride.GUI_SCALE, 9 * GenericOverride.GUI_SCALE, 6, 5, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        if (ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main")) {
            ModernGui.drawScaledStringCustomFont(player.func_70658_aO() + "", (float)(offsetXMiddle + 37) - 11.5f + 3.0f, (float)(resolution.func_78328_b() - 36) + 1.5f, 0xBABADA, 0.5f, "center", true, "georamaBold", 25);
        } else {
            ModernGui.drawScaledStringCustomFont(player.func_70658_aO() + "", (float)(offsetXMiddle + 37) - 11.5f + 3.0f, (float)(resolution.func_78328_b() - 36) + 1.5f, 0, 0.5f, "center", false, "georamaBold", 25);
        }
        this.vanillaLife(client, resolution, playerHealth);
        int offsetXProgressBar = resolution.func_78326_a() / 2 + 37 + 80;
        if (client.field_71439_g.func_70090_H()) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 816 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 800 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, (int)(74.0f * (1.0f - (float)client.field_71439_g.func_70086_ai() / 300.0f)) * GenericOverride.GUI_SCALE, 5, (int)((float)((int)(74.0f * (1.0f - (float)client.field_71439_g.func_70086_ai() / 300.0f))) / 2.5f), 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect((float)offsetXProgressBar + 0.9f, resolution.func_78328_b() - 33 + 29 - 5, 227 * GenericOverride.GUI_SCALE, 81 * GenericOverride.GUI_SCALE, 7 * GenericOverride.GUI_SCALE, 7 * GenericOverride.GUI_SCALE, 3, 3, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            offsetXProgressBar += 10;
        }
        if (Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 864 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            playerGC = PlayerUtil.getPlayerBaseClientFromPlayer((EntityPlayer)Minecraft.func_71410_x().field_71439_g, (boolean)false);
            percentage = (playerGC.temperature - -200.0f) / 500.0f;
            percentage = Math.max(0.1f, percentage);
            percentage = Math.min(1.0f, percentage);
            ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 800 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, (int)((1.0f - percentage) * 74.0f) * GenericOverride.GUI_SCALE, 5, (int)((1.0f - percentage) * 74.0f / 2.5f), 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont("\u00b0C", (float)offsetXProgressBar + 2.5f, resolution.func_78328_b() - 33 + 2, 0xFFFFFF, 0.5f, "center", true, "georamaMedium", 16);
            ModernGui.drawScaledStringCustomFont((int)playerGC.temperature + "", offsetXProgressBar + 2, resolution.func_78328_b() - 33 - 6, 0xFFFFFF, 0.5f, "center", true, "georamaMedium", 20);
            offsetXProgressBar += 10;
        }
        if (Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider) {
            playerGC = PlayerUtil.getPlayerBaseClientFromPlayer((EntityPlayer)Minecraft.func_71410_x().field_71439_g, (boolean)false);
            percentage = playerGC.gasBottle;
            if (percentage > 0.0f) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 848 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 74 * GenericOverride.GUI_SCALE, 5, 29, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(offsetXProgressBar, resolution.func_78328_b() - 33, 800 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, (int)((1.0f - percentage) * 74.0f) * GenericOverride.GUI_SCALE, 5, (int)((1.0f - percentage) * 74.0f / 2.5f), 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont("%", (float)offsetXProgressBar + 2.5f, resolution.func_78328_b() - 33 + 2, 0xFFFFFF, 0.5f, "center", true, "georamaMedium", 16);
                ModernGui.drawScaledStringCustomFont((int)playerGC.gasBottle + "", offsetXProgressBar + 2, resolution.func_78328_b() - 33 - 6, 0xFFFFFF, 0.5f, "center", true, "georamaMedium", 20);
                offsetXProgressBar += 10;
            }
        }
    }

    public void glScissor(int x, int y, int width, int height, Minecraft client, ScaledResolution resolution) {
        double scaleW = (double)client.field_71443_c / resolution.func_78327_c();
        double scaleH = (double)client.field_71440_d / resolution.func_78324_d();
        GL11.glScissor((int)((int)Math.floor((double)x * scaleW)), (int)((int)Math.floor((double)client.field_71440_d - (double)(y + height) * scaleH)), (int)((int)Math.floor((double)(x + width) * scaleW) - (int)Math.floor((double)x * scaleW)), (int)((int)Math.floor((double)client.field_71440_d - (double)y * scaleH) - (int)Math.floor((double)client.field_71440_d - (double)(y + height) * scaleH)));
    }

    public void drawTexturedModalRect(float x, float y, int textureX, int textureY, float width, float height) {
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)x, (double)(y + height), (double)this.field_73735_i, (double)((float)textureX * 0.00390625f), (double)(((float)textureY + height) * 0.00390625f));
        tessellator.func_78374_a((double)(x + width), (double)(y + height), (double)this.field_73735_i, (double)(((float)textureX + width) * 0.00390625f), (double)(((float)textureY + height) * 0.00390625f));
        tessellator.func_78374_a((double)(x + width), (double)y, (double)this.field_73735_i, (double)(((float)textureX + width) * 0.00390625f), (double)((float)textureY * 0.00390625f));
        tessellator.func_78374_a((double)x, (double)y, (double)this.field_73735_i, (double)((float)textureX * 0.00390625f), (double)((float)textureY * 0.00390625f));
        tessellator.func_78381_a();
    }

    public static void drawRect(float par0, float par1, float par2, float par3, int par4) {
        float j1;
        if (par0 < par2) {
            j1 = par0;
            par0 = par2;
            par2 = j1;
        }
        if (par1 < par3) {
            j1 = par1;
            par1 = par3;
            par3 = j1;
        }
        float f = (float)(par4 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(par4 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(par4 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(par4 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.field_78398_a;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        tessellator.func_78382_b();
        tessellator.func_78377_a((double)par0, (double)par3, 0.0);
        tessellator.func_78377_a((double)par2, (double)par3, 0.0);
        tessellator.func_78377_a((double)par2, (double)par1, 0.0);
        tessellator.func_78377_a((double)par0, (double)par1, 0.0);
        tessellator.func_78381_a();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    private void vanillaLife(Minecraft client, ScaledResolution resolution, float playerHealth) {
        boolean highlight;
        ModernGui.bindTextureOverlayMain();
        client.func_110434_K().func_110577_a(Gui.field_110324_m);
        int updateCounter = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, (Object)client.field_71456_v, (String[])new String[]{"updateCounter", "i"});
        Random rand = new Random();
        float left = resolution.func_78326_a() / 2 + 113;
        float top = (float)resolution.func_78328_b() - 35.5f;
        FoodStats stats = client.field_71439_g.func_71024_bL();
        int level = stats.func_75116_a();
        int levelLast = stats.func_75120_b();
        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            float x = left - (float)(i * 7) - 9.0f;
            float y = top;
            int icon = 16;
            int backgound = 0;
            if (client.field_71439_g.func_70644_a(Potion.field_76438_s)) {
                icon += 36;
                backgound = 13;
            }
            if (client.field_71439_g.func_71024_bL().func_75115_e() <= 0.0f && updateCounter % (level * 3 + 1) == 0) {
                y = top + (float)(rand.nextInt(3) - 1);
            }
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(x, y, 698 * GenericOverride.GUI_SCALE, 65 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 7, 7, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            if (idx < level) {
                ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0f, 680 * GenericOverride.GUI_SCALE, 65 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 14 * GenericOverride.GUI_SCALE, 7, 7, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            }
            client.func_110434_K().func_110577_a(Gui.field_110324_m);
        }
        boolean bl = highlight = client.field_71439_g.field_70172_ad / 3 % 2 == 1;
        if (client.field_71439_g.field_70172_ad < 10) {
            highlight = false;
        }
        AttributeInstance attrMaxHealth = client.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111267_a);
        int health = MathHelper.func_76123_f((float)playerHealth);
        int healthLast = MathHelper.func_76123_f((float)client.field_71439_g.field_70735_aL);
        float healthMax = (float)attrMaxHealth.func_111126_e();
        float absorb = client.field_71439_g.func_110139_bj();
        int healthRows = MathHelper.func_76123_f((float)((healthMax + absorb) / 2.0f / 10.0f));
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        rand.setSeed(updateCounter * 312871);
        left = (float)(resolution.func_78326_a() / 2) - 114.0f;
        top = (float)resolution.func_78328_b() - 34.5f;
        int regen = -1;
        if (client.field_71439_g.func_70644_a(Potion.field_76428_l)) {
            regen = updateCounter % 25;
        }
        int TOP = 9 * (client.field_71441_e.func_72912_H().func_76093_s() ? 5 : 0);
        int BACKGROUND = highlight ? 25 : 16;
        int positionFullHeartTexture = 226;
        if (client.field_71439_g.func_70644_a(Potion.field_76436_u)) {
            positionFullHeartTexture = 286;
        } else if (client.field_71439_g.func_70644_a(Potion.field_82731_v)) {
            positionFullHeartTexture = 271;
        }
        float absorbRemaining = absorb;
        for (int i = MathHelper.func_76123_f((float)((healthMax + absorb) / 2.0f)) - 1; i >= 0; --i) {
            boolean b0 = highlight;
            int row = MathHelper.func_76123_f((float)((float)(i + 1) / 10.0f)) - 1;
            float x = left + (float)(i % 10) * 7.5f;
            float y = top - (float)(row * rowHeight);
            if (health <= 4) {
                y += (float)rand.nextInt(2);
            }
            if (i == regen) {
                y -= 2.0f;
            }
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(x, y, 241 * GenericOverride.GUI_SCALE, 67 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            if (absorbRemaining > 0.0f) {
                if (absorbRemaining == absorb && absorb % 2.0f == 1.0f) {
                    ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0f, 256 * GenericOverride.GUI_SCALE, 67 * GenericOverride.GUI_SCALE, 6 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 3, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0f, 256 * GenericOverride.GUI_SCALE, 67 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                }
                absorbRemaining -= 2.0f;
                continue;
            }
            if (i * 2 + 1 < health) {
                ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0f, positionFullHeartTexture * GenericOverride.GUI_SCALE, 67 * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 7, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                continue;
            }
            if (i * 2 + 1 != health) continue;
            ModernGui.drawScaledCustomSizeModalRect(x, y - 1.0f, positionFullHeartTexture * GenericOverride.GUI_SCALE, 67 * GenericOverride.GUI_SCALE, 6 * GenericOverride.GUI_SCALE, 11 * GenericOverride.GUI_SCALE, 3, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        }
    }
}

