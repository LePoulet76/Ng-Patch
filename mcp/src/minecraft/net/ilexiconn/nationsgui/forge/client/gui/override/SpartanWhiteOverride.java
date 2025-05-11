/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.ngupgrades.common.CommonEventHandler
 *  fr.nationsglory.ngupgrades.common.item.SpartanWhiteArmorItem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.CommonEventHandler;
import fr.nationsglory.ngupgrades.common.item.SpartanWhiteArmorItem;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SpartanWhiteOverride
extends Gui
implements ElementOverride {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/default/overlay_spartan_white.png");
    public static CFontRenderer georamaSemiBold = ModernGui.getCustomFont("georamaSemiBold", 30);
    public static Long lastDisplayHotbarMessageTime = 0L;
    private RenderItem itemRenderer = new RenderItem();

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main")) {
            return;
        }
        ClientEventHandler.STYLE.bindTexture("overlay_spartan_white");
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(1.0f, 1.0f, 1.0f, 1.0f);
        int screenWidth = resolution.func_78326_a();
        int screenHeight = resolution.func_78328_b();
        tessellator.func_78374_a(0.0, (double)screenHeight, 0.0, 0.0, 1.0);
        tessellator.func_78374_a((double)screenWidth, (double)screenHeight, 0.0, 1.0, 1.0);
        tessellator.func_78374_a((double)screenWidth, 0.0, 0.0, 1.0, 0.0);
        tessellator.func_78374_a(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.func_78381_a();
        GL11.glDisable((int)3042);
        int offsetXMiddle = resolution.func_78326_a() / 2;
        ItemStack chestplate = client.field_71439_g.func_82169_q(2);
        if (CommonEventHandler.isWearingFullSpartanWhiteArmor((EntityPlayer)client.field_71439_g) && chestplate != null && chestplate.func_77973_b() instanceof SpartanWhiteArmorItem) {
            SpartanWhiteArmorItem spartanWhiteArmorItem = (SpartanWhiteArmorItem)chestplate.func_77973_b();
            ClientEventHandler.STYLE.bindTexture("overlay_main_spartan_white");
            float shieldValue = spartanWhiteArmorItem.getShieldValue(chestplate);
            float progress = shieldValue / 100.0f;
            int offsetY = resolution.func_78328_b() - 65;
            if (ClientProxy.clientConfig.enableAzimut && ClientProxy.clientConfig.azimutBottom) {
                offsetY -= 20;
            }
            ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle - 86, offsetY, 1366 * GenericOverride.GUI_SCALE, 319 * GenericOverride.GUI_SCALE, 128 * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, 64, 10, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle - 86, offsetY, 1366 * GenericOverride.GUI_SCALE, 345 * GenericOverride.GUI_SCALE, (int)(128.0f * progress) * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, (int)(128.0f * progress) / 2, 10, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle + 22, offsetY, 1499 * GenericOverride.GUI_SCALE, 319 * GenericOverride.GUI_SCALE, 128 * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, 64, 10, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle + 22) + 128.0f * (1.0f - progress) / 2.0f, offsetY, (1499.0f + 128.0f * (1.0f - progress)) * (float)GenericOverride.GUI_SCALE, 345 * GenericOverride.GUI_SCALE, (int)(128.0f * progress) * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, (int)(128.0f * progress) / 2, 10, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            if (progress == 1.0f) {
                ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle - 14, offsetY - 10, 1363 * GenericOverride.GUI_SCALE, 254 * GenericOverride.GUI_SCALE, 56 * GenericOverride.GUI_SCALE, 55 * GenericOverride.GUI_SCALE, 28, 27, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            }
        }
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

    private void renderItem(Minecraft client, int x, int y, float partialTicks, EntityPlayer player, ItemStack stack) {
        if (stack != null) {
            float animation = (float)stack.field_77992_b - partialTicks;
            if (animation > 0.0f) {
                GL11.glPushMatrix();
                float scale = 1.0f + animation / 5.0f;
                GL11.glTranslatef((float)((float)x + 8.0f), (float)((float)y + 12.0f), (float)0.0f);
                GL11.glScalef((float)(1.0f / scale), (float)((scale + 1.0f) / 2.0f), (float)1.0f);
                GL11.glTranslatef((float)(-((float)x + 8.0f)), (float)(-((float)y + 12.0f)), (float)0.0f);
            }
            this.itemRenderer.func_82406_b(client.field_71466_p, Minecraft.func_71410_x().func_110434_K(), stack, x, y);
            if (animation > 0.0f) {
                GL11.glPopMatrix();
            }
            this.itemRenderer.func_77021_b(client.field_71466_p, Minecraft.func_71410_x().func_110434_K(), stack, x, y);
        }
        RenderHelper.func_74518_a();
    }

    private void drawItemStack(ItemStack itemStack, Minecraft client, int xPos, int yPos) {
        RenderHelper.func_74520_c();
        this.itemRenderer.func_82406_b(client.field_71466_p, Minecraft.func_71410_x().func_110434_K(), itemStack, xPos, yPos);
        RenderHelper.func_74518_a();
    }
}

