/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.nationsmap.NationsMap
 *  fr.nationsglory.ngupgrades.common.item.GenericGeckoItemSword
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.ngupgrades.common.item.GenericGeckoItemSword;
import java.util.ArrayList;
import java.util.Collection;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class HotbarOverride
extends Gui
implements ElementOverride {
    private RenderItem itemRenderer = new RenderItem();
    public static CFontRenderer georamaSemiBold = ModernGui.getCustomFont("georamaSemiBold", 30);
    public static Long lastDisplayHotbarMessageTime = 0L;
    public static final Long defaultDelayHotbarMessage = 2500L;
    public static Long delayHotbarMessage = 2500L;
    protected static final ResourceLocation icons = new ResourceLocation("textures/gui/container/inventory.png");

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return null;
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (!ClientData.versusOverlayData.isEmpty() && !((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(client.field_71439_g.field_71092_bJ)) {
            return;
        }
        if (client.field_71451_h instanceof EntityPlayer) {
            String text;
            int offsetXMiddle = resolution.func_78326_a() / 2;
            int offsetYHotbar = resolution.func_78328_b() - 52;
            if (ClientProxy.clientConfig.enableAzimut && ClientProxy.clientConfig.azimutBottom) {
                offsetYHotbar -= 22;
            }
            EntityPlayer player = (EntityPlayer)client.field_71451_h;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)32826);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            if (!ClientData.hotbarMessage.isEmpty()) {
                ModernGui.glColorHex(-1289937611, 1.0f);
                ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(ClientData.hotbarMessage) / 2.0f / 2.0f - 5.0f, offsetYHotbar, 0.0f, georamaSemiBold.getStringWidth(ClientData.hotbarMessage) / 2.0f + 10.0f, 12.0f);
                ModernGui.drawScaledStringCustomFont(ClientData.hotbarMessage, offsetXMiddle, offsetYHotbar + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
                if (System.currentTimeMillis() - lastDisplayHotbarMessageTime > delayHotbarMessage) {
                    ClientData.hotbarMessage = "";
                    delayHotbarMessage = defaultDelayHotbarMessage;
                }
            } else if (ClientData.worldName.equalsIgnoreCase("warzone")) {
                ModernGui.glColorHex(-1288364259, 1.0f);
                text = I18n.func_135053_a((String)"overlay.warzone.hotbar").replaceAll("<key>", Keyboard.getKeyName((int)ClientKeyHandler.KEY_WARZONE_LEAVE.field_74512_d));
                ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(text) / 2.0f / 2.0f - 5.0f, offsetYHotbar, 0.0f, georamaSemiBold.getStringWidth(text) / 2.0f + 10.0f, 12.0f);
                ModernGui.drawScaledStringCustomFont(text, offsetXMiddle, offsetYHotbar + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
            } else if (player.func_70694_bm() != null && player.func_70694_bm().func_77973_b() != null && player.func_70694_bm().func_77973_b().func_77658_a().contains("halloween_2024") && GenericGeckoItemSword.playerSmashTime.containsKey(player.func_70005_c_()) && System.currentTimeMillis() < (Long)GenericGeckoItemSword.playerSmashTime.get(player.func_70005_c_()) + GenericGeckoItemSword.SMASH_COOLDOWN) {
                ModernGui.glColorHex(-1288364259, 1.0f);
                int delay = (int)(((Long)GenericGeckoItemSword.playerSmashTime.get(player.func_70005_c_()) + GenericGeckoItemSword.SMASH_COOLDOWN - System.currentTimeMillis()) / 1000L);
                String text2 = I18n.func_135053_a((String)"overlay.hammer_halloween_2024.hotbar").replaceAll("<delay>", delay + "");
                ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(text2) / 2.0f / 2.0f - 5.0f, offsetYHotbar, 0.0f, georamaSemiBold.getStringWidth(text2) / 2.0f + 10.0f, 12.0f);
                ModernGui.drawScaledStringCustomFont(text2, offsetXMiddle, offsetYHotbar + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
            } else if (ClientProxy.currentServerName.equalsIgnoreCase("hub") && ClientData.waitingServerName != null && !ClientData.waitingServerName.equalsIgnoreCase("null")) {
                ModernGui.glColorHex(-1289937611, 1.0f);
                text = I18n.func_135053_a((String)"overlay.hub.waiting.1");
                ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(text) / 2.0f / 2.0f - 5.0f, offsetYHotbar, 0.0f, georamaSemiBold.getStringWidth(text) / 2.0f + 10.0f, 12.0f);
                ModernGui.drawScaledStringCustomFont(text, offsetXMiddle, offsetYHotbar + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.hub.waiting.2"), offsetXMiddle, offsetYHotbar - 10, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
            }
            for (int slot = 0; slot < 9; ++slot) {
                int slotX = offsetXMiddle - 108 + slot * 24;
                int slotY = resolution.func_78328_b() - 24;
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect(slotX, slotY - 3, (player.field_71071_by.field_70461_c == slot ? 419 : 482) * GenericOverride.GUI_SCALE, 86 * GenericOverride.GUI_SCALE, 55 * GenericOverride.GUI_SCALE, 55 * GenericOverride.GUI_SCALE, 24, 24, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                GL11.glPushMatrix();
                RenderHelper.func_74520_c();
                GL11.glTranslatef((float)4.3f, (float)1.3f, (float)0.0f);
                this.renderItem(client, slotX, slotY, partialTicks, player, player.field_71071_by.field_70462_a[slot]);
                GL11.glPopMatrix();
                RenderHelper.func_74518_a();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
            GL11.glDisable((int)32826);
            GL11.glDisable((int)3042);
            if (ClientProxy.clientConfig.displayArmorInInfo) {
                int offsetXLeft = 5;
                int offsetYLeft = resolution.func_78328_b() / 2 - 50;
                int offsetXRight = resolution.func_78326_a() - 27;
                int offsetYRight = NationsMap.instance.miniMap.smallMapMode.yTranslation + NationsMap.instance.miniMap.smallMapMode.y + NationsMap.instance.miniMap.smallMapMode.h + (ClientProxy.clientConfig.specialEnabled ? 58 : 33) + (!ClientData.currentWarzone.isEmpty() ? 25 : 0) + (!ClientData.currentAssault.isEmpty() ? 25 : 0) + (ClientData.isCombatTagged ? 15 : 0) + 5;
                if (NationsMap.instance != null && NationsMap.instance.miniMap != null && NationsMap.instance.miniMap.smallMapMode != null) {
                    EntityClientPlayerMP entityPlayer = Minecraft.func_71410_x().field_71439_g;
                    for (int i = 0; i < 5; ++i) {
                        ItemStack itemStack = null;
                        itemStack = i == 4 ? Minecraft.func_71410_x().field_71439_g.field_71071_by.func_70448_g() : entityPlayer.func_82169_q(3 - i);
                        if (itemStack == null) continue;
                        ModernGui.bindTextureOverlayMain();
                        ModernGui.drawScaledCustomSizeModalRect(ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)offsetXLeft, ClientProxy.clientConfig.armorInfosRight ? (float)offsetYRight : (float)offsetYLeft, 23 * GenericOverride.GUI_SCALE, 247 * GenericOverride.GUI_SCALE, 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                        if (itemStack.func_77984_f()) {
                            double percentDamage = (double)(itemStack.func_77958_k() - itemStack.func_77960_j()) * 1.0 / (double)itemStack.func_77958_k() * 100.0;
                            ModernGui.drawScaledStringCustomFont(itemStack.func_77958_k() - itemStack.func_77960_j() + "", ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)(offsetXLeft + 21), (ClientProxy.clientConfig.armorInfosRight ? offsetYRight : offsetYLeft) + 5, percentDamage < 25.0 ? 16000586 : (percentDamage < 75.0 ? 16749100 : 15463162), 0.5f, ClientProxy.clientConfig.armorInfosRight ? "right" : "left", false, "minecraftDungeons", 23);
                        }
                        GL11.glPushMatrix();
                        GL11.glScalef((float)0.8f, (float)0.8f, (float)0.8f);
                        this.renderItem(client, (int)((float)((ClientProxy.clientConfig.armorInfosRight ? offsetXRight : offsetXLeft) + 4) / 0.8f), (int)((float)((ClientProxy.clientConfig.armorInfosRight ? offsetYRight : offsetYLeft) + 3) / 0.8f), partialTicks, (EntityPlayer)client.field_71439_g, itemStack);
                        GL11.glPopMatrix();
                        if (ClientProxy.clientConfig.armorInfosRight) {
                            offsetYRight += 19;
                            continue;
                        }
                        offsetYLeft += 19;
                    }
                    Collection effects = client.field_71439_g.func_70651_bq();
                    if (!effects.isEmpty()) {
                        GL11.glEnable((int)3042);
                        boolean i = false;
                        for (PotionEffect effect : effects) {
                            Potion potion = Potion.field_76425_a[effect.func_76456_a()];
                            if (potion == null) continue;
                            ModernGui.bindTextureOverlayMain();
                            ModernGui.drawScaledCustomSizeModalRect(ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)offsetXLeft, ClientProxy.clientConfig.armorInfosRight ? (float)offsetYRight : (float)offsetYLeft, 23 * GenericOverride.GUI_SCALE, 247 * GenericOverride.GUI_SCALE, 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                            client.func_110434_K().func_110577_a(icons);
                            int iconIndex = potion.func_76392_e();
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            ModernGui.drawScaledCustomSizeModalRect((float)(ClientProxy.clientConfig.armorInfosRight ? offsetXRight : offsetXLeft) + 4.5f, (ClientProxy.clientConfig.armorInfosRight ? offsetYRight : offsetYLeft) + 4, iconIndex % 8 * 18, 198 + iconIndex / 8 * 18, 18, 18, 12, 12, 256.0f, 256.0f, true);
                            ModernGui.drawScaledStringCustomFont(Potion.func_76389_a((PotionEffect)effect), ClientProxy.clientConfig.armorInfosRight ? offsetXRight : offsetXLeft + 21, (ClientProxy.clientConfig.armorInfosRight ? offsetYRight : offsetYLeft) + 5, 0xFFFFFF, 0.4f, ClientProxy.clientConfig.armorInfosRight ? "right" : "left", true, "minecraftDungeons", 25);
                            if (ClientProxy.clientConfig.armorInfosRight) {
                                offsetYRight += 19;
                                continue;
                            }
                            offsetYLeft += 19;
                        }
                    }
                }
                if (!ClientData.playerCombatArmorInfos.isEmpty() && System.currentTimeMillis() - Long.parseLong(ClientData.playerCombatArmorInfos.get("displayTime")) < 5000L) {
                    int offsetY = ClientProxy.clientConfig.armorInfosRight ? offsetYLeft : offsetYRight;
                    int offsetX = ClientProxy.clientConfig.armorInfosRight ? offsetXLeft : offsetXRight;
                    ModernGui.bindTextureOverlayMain();
                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, 23 * GenericOverride.GUI_SCALE, 247 * GenericOverride.GUI_SCALE, 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(ClientData.playerCombatArmorInfos.get("playerName"), offsetX + (ClientProxy.clientConfig.armorInfosRight ? 21 : 0), offsetY + 3, 0xFFFFFF, 0.4f, ClientProxy.clientConfig.armorInfosRight ? "left" : "right", true, "minecraftDungeons", 25);
                    ModernGui.drawScaledStringCustomFont(ClientData.playerCombatArmorInfos.get("playerCountry"), offsetX + (ClientProxy.clientConfig.armorInfosRight ? 21 : 0), offsetY + 12, 0xFFFFFF, 0.5f, ClientProxy.clientConfig.armorInfosRight ? "left" : "right", false, "georamaBold", 20);
                    if (!ClientProxy.cacheHeadPlayer.containsKey(ClientData.playerCombatArmorInfos.get("playerName"))) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)ClientData.playerCombatArmorInfos.get("playerName"));
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)ClientData.playerCombatArmorInfos.get("playerName"));
                            ClientProxy.cacheHeadPlayer.put(ClientData.playerCombatArmorInfos.get("playerName"), resourceLocation);
                        }
                        catch (Exception exception) {}
                    } else {
                        Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(ClientData.playerCombatArmorInfos.get("playerName")));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 14, offsetY + 13, 8.0f, 16.0f, 8, -8, -8, -8, 64.0f, 64.0f);
                    }
                    offsetY += 19;
                    for (String damagedItemStr : ClientData.playerCombatArmorDurability) {
                        ItemStack itemStack = new ItemStack(Integer.parseInt(damagedItemStr.split(":")[0]), 1, Integer.parseInt(damagedItemStr.split(":")[1]));
                        if (itemStack == null || itemStack.func_77973_b() == null) continue;
                        ModernGui.bindTextureOverlayMain();
                        ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, 23 * GenericOverride.GUI_SCALE, 247 * GenericOverride.GUI_SCALE, 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                        GL11.glPushMatrix();
                        GL11.glScalef((float)0.8f, (float)0.8f, (float)0.8f);
                        this.drawItemStack(itemStack, client, (int)((float)(offsetX + 4) / 0.8f), (int)((float)(offsetY + 3) / 0.8f));
                        GL11.glPopMatrix();
                        offsetY += 19;
                    }
                }
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

