package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.ngupgrades.common.item.GenericGeckoItemSword;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
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
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class HotbarOverride extends Gui implements ElementOverride
{
    private RenderItem itemRenderer = new RenderItem();
    public static CFontRenderer georamaSemiBold = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(30));
    public static Long lastDisplayHotbarMessageTime = Long.valueOf(0L);
    public static final Long defaultDelayHotbarMessage = Long.valueOf(2500L);
    public static Long delayHotbarMessage = Long.valueOf(2500L);
    protected static final ResourceLocation icons = new ResourceLocation("textures/gui/container/inventory.png");

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return null;
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (ClientData.versusOverlayData.isEmpty() || ((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(client.thePlayer.username))
        {
            if (client.renderViewEntity instanceof EntityPlayer)
            {
                int offsetXMiddle = resolution.getScaledWidth() / 2;
                int offsetYHotbar = resolution.getScaledHeight() - 52;

                if (ClientProxy.clientConfig.enableAzimut && ClientProxy.clientConfig.azimutBottom)
                {
                    offsetYHotbar -= 22;
                }

                EntityPlayer player = (EntityPlayer)client.renderViewEntity;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int var19;

                if (!ClientData.hotbarMessage.isEmpty())
                {
                    ModernGui.glColorHex(-1289937611, 1.0F);
                    ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(ClientData.hotbarMessage) / 2.0F / 2.0F - 5.0F, (float)offsetYHotbar, 0.0F, georamaSemiBold.getStringWidth(ClientData.hotbarMessage) / 2.0F + 10.0F, 12.0F);
                    ModernGui.drawScaledStringCustomFont(ClientData.hotbarMessage, (float)offsetXMiddle, (float)(offsetYHotbar + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);

                    if (System.currentTimeMillis() - lastDisplayHotbarMessageTime.longValue() > delayHotbarMessage.longValue())
                    {
                        ClientData.hotbarMessage = "";
                        delayHotbarMessage = defaultDelayHotbarMessage;
                    }
                }
                else
                {
                    String offsetXLeft;

                    if (ClientData.worldName.equalsIgnoreCase("warzone"))
                    {
                        ModernGui.glColorHex(-1288364259, 1.0F);
                        offsetXLeft = I18n.getString("overlay.warzone.hotbar").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_WARZONE_LEAVE.keyCode));
                        ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(offsetXLeft) / 2.0F / 2.0F - 5.0F, (float)offsetYHotbar, 0.0F, georamaSemiBold.getStringWidth(offsetXLeft) / 2.0F + 10.0F, 12.0F);
                        ModernGui.drawScaledStringCustomFont(offsetXLeft, (float)offsetXMiddle, (float)(offsetYHotbar + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
                    }
                    else if (player.getHeldItem() != null && player.getHeldItem().getItem() != null && player.getHeldItem().getItem().getUnlocalizedName().contains("halloween_2024") && GenericGeckoItemSword.playerSmashTime.containsKey(player.getCommandSenderName()) && System.currentTimeMillis() < ((Long)GenericGeckoItemSword.playerSmashTime.get(player.getCommandSenderName())).longValue() + GenericGeckoItemSword.SMASH_COOLDOWN)
                    {
                        ModernGui.glColorHex(-1288364259, 1.0F);
                        var19 = (int)((((Long)GenericGeckoItemSword.playerSmashTime.get(player.getCommandSenderName())).longValue() + GenericGeckoItemSword.SMASH_COOLDOWN - System.currentTimeMillis()) / 1000L);
                        String offsetYLeft = I18n.getString("overlay.hammer_halloween_2024.hotbar").replaceAll("<delay>", var19 + "");
                        ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(offsetYLeft) / 2.0F / 2.0F - 5.0F, (float)offsetYHotbar, 0.0F, georamaSemiBold.getStringWidth(offsetYLeft) / 2.0F + 10.0F, 12.0F);
                        ModernGui.drawScaledStringCustomFont(offsetYLeft, (float)offsetXMiddle, (float)(offsetYHotbar + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
                    }
                    else if (ClientProxy.currentServerName.equalsIgnoreCase("hub") && ClientData.waitingServerName != null && !ClientData.waitingServerName.equalsIgnoreCase("null"))
                    {
                        ModernGui.glColorHex(-1289937611, 1.0F);
                        offsetXLeft = I18n.getString("overlay.hub.waiting.1");
                        ModernGui.drawRoundedRectangle((float)offsetXMiddle - georamaSemiBold.getStringWidth(offsetXLeft) / 2.0F / 2.0F - 5.0F, (float)offsetYHotbar, 0.0F, georamaSemiBold.getStringWidth(offsetXLeft) / 2.0F + 10.0F, 12.0F);
                        ModernGui.drawScaledStringCustomFont(offsetXLeft, (float)offsetXMiddle, (float)(offsetYHotbar + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.hub.waiting.2"), (float)offsetXMiddle, (float)(offsetYHotbar - 10), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
                    }
                }

                int offsetXRight;
                int var20;

                for (var19 = 0; var19 < 9; ++var19)
                {
                    var20 = offsetXMiddle - 108 + var19 * 24;
                    offsetXRight = resolution.getScaledHeight() - 24;
                    ModernGui.bindTextureOverlayMain();
                    ModernGui.drawScaledCustomSizeModalRect((float)var20, (float)(offsetXRight - 3), (float)((player.inventory.currentItem == var19 ? 419 : 482) * GenericOverride.GUI_SCALE), (float)(86 * GenericOverride.GUI_SCALE), 55 * GenericOverride.GUI_SCALE, 55 * GenericOverride.GUI_SCALE, 24, 24, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                    GL11.glPushMatrix();
                    RenderHelper.enableGUIStandardItemLighting();
                    GL11.glTranslatef(4.3F, 1.3F, 0.0F);
                    this.renderItem(client, var20, offsetXRight, partialTicks, player, player.inventory.mainInventory[var19]);
                    GL11.glPopMatrix();
                    RenderHelper.disableStandardItemLighting();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }

                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glDisable(GL11.GL_BLEND);

                if (ClientProxy.clientConfig.displayArmorInInfo)
                {
                    byte var21 = 5;
                    var20 = resolution.getScaledHeight() / 2 - 50;
                    offsetXRight = resolution.getScaledWidth() - 27;
                    int offsetYRight = NationsMap.instance.miniMap.smallMapMode.yTranslation + NationsMap.instance.miniMap.smallMapMode.y + NationsMap.instance.miniMap.smallMapMode.h + (ClientProxy.clientConfig.specialEnabled ? 58 : 33) + (!ClientData.currentWarzone.isEmpty() ? 25 : 0) + (!ClientData.currentAssault.isEmpty() ? 25 : 0) + (ClientData.isCombatTagged ? 15 : 0) + 5;
                    int offsetX;

                    if (NationsMap.instance != null && NationsMap.instance.miniMap != null && NationsMap.instance.miniMap.smallMapMode != null)
                    {
                        EntityClientPlayerMP offsetY = Minecraft.getMinecraft().thePlayer;

                        for (offsetX = 0; offsetX < 5; ++offsetX)
                        {
                            ItemStack resourceLocation = null;

                            if (offsetX == 4)
                            {
                                resourceLocation = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
                            }
                            else
                            {
                                resourceLocation = offsetY.getCurrentArmor(3 - offsetX);
                            }

                            if (resourceLocation != null)
                            {
                                ModernGui.bindTextureOverlayMain();
                                ModernGui.drawScaledCustomSizeModalRect(ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)var21, ClientProxy.clientConfig.armorInfosRight ? (float)offsetYRight : (float)var20, (float)(23 * GenericOverride.GUI_SCALE), (float)(247 * GenericOverride.GUI_SCALE), 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);

                                if (resourceLocation.isItemStackDamageable())
                                {
                                    double damagedItemStr = (double)(resourceLocation.getMaxDamage() - resourceLocation.getItemDamage()) * 1.0D / (double)resourceLocation.getMaxDamage() * 100.0D;
                                    ModernGui.drawScaledStringCustomFont(resourceLocation.getMaxDamage() - resourceLocation.getItemDamage() + "", ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)(var21 + 21), (float)((ClientProxy.clientConfig.armorInfosRight ? offsetYRight : var20) + 5), damagedItemStr < 25.0D ? 16000586 : (damagedItemStr < 75.0D ? 16749100 : 15463162), 0.5F, ClientProxy.clientConfig.armorInfosRight ? "right" : "left", false, "minecraftDungeons", 23);
                                }

                                GL11.glPushMatrix();
                                GL11.glScalef(0.8F, 0.8F, 0.8F);
                                this.renderItem(client, (int)((float)((ClientProxy.clientConfig.armorInfosRight ? offsetXRight : var21) + 4) / 0.8F), (int)((float)((ClientProxy.clientConfig.armorInfosRight ? offsetYRight : var20) + 3) / 0.8F), partialTicks, client.thePlayer, resourceLocation);
                                GL11.glPopMatrix();

                                if (ClientProxy.clientConfig.armorInfosRight)
                                {
                                    offsetYRight += 19;
                                }
                                else
                                {
                                    var20 += 19;
                                }
                            }
                        }

                        Collection var23 = client.thePlayer.getActivePotionEffects();

                        if (!var23.isEmpty())
                        {
                            GL11.glEnable(GL11.GL_BLEND);
                            boolean var24 = false;
                            Iterator var25 = var23.iterator();

                            while (var25.hasNext())
                            {
                                PotionEffect itemStack = (PotionEffect)var25.next();
                                Potion potion = Potion.potionTypes[itemStack.getPotionID()];

                                if (potion != null)
                                {
                                    ModernGui.bindTextureOverlayMain();
                                    ModernGui.drawScaledCustomSizeModalRect(ClientProxy.clientConfig.armorInfosRight ? (float)offsetXRight : (float)var21, ClientProxy.clientConfig.armorInfosRight ? (float)offsetYRight : (float)var20, (float)(23 * GenericOverride.GUI_SCALE), (float)(247 * GenericOverride.GUI_SCALE), 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                                    client.getTextureManager().bindTexture(icons);
                                    int iconIndex = potion.getStatusIconIndex();
                                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                                    ModernGui.drawScaledCustomSizeModalRect((float)(ClientProxy.clientConfig.armorInfosRight ? offsetXRight : var21) + 4.5F, (float)((ClientProxy.clientConfig.armorInfosRight ? offsetYRight : var20) + 4), (float)(iconIndex % 8 * 18), (float)(198 + iconIndex / 8 * 18), 18, 18, 12, 12, 256.0F, 256.0F, true);
                                    ModernGui.drawScaledStringCustomFont(Potion.getDurationString(itemStack), (float)(ClientProxy.clientConfig.armorInfosRight ? offsetXRight : var21 + 21), (float)((ClientProxy.clientConfig.armorInfosRight ? offsetYRight : var20) + 5), 16777215, 0.4F, ClientProxy.clientConfig.armorInfosRight ? "right" : "left", true, "minecraftDungeons", 25);

                                    if (ClientProxy.clientConfig.armorInfosRight)
                                    {
                                        offsetYRight += 19;
                                    }
                                    else
                                    {
                                        var20 += 19;
                                    }
                                }
                            }
                        }
                    }

                    if (!ClientData.playerCombatArmorInfos.isEmpty() && System.currentTimeMillis() - Long.parseLong((String)ClientData.playerCombatArmorInfos.get("displayTime")) < 5000L)
                    {
                        int var22 = ClientProxy.clientConfig.armorInfosRight ? var20 : offsetYRight;
                        offsetX = ClientProxy.clientConfig.armorInfosRight ? var21 : offsetXRight;
                        ModernGui.bindTextureOverlayMain();
                        ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)var22, (float)(23 * GenericOverride.GUI_SCALE), (float)(247 * GenericOverride.GUI_SCALE), 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont((String)ClientData.playerCombatArmorInfos.get("playerName"), (float)(offsetX + (ClientProxy.clientConfig.armorInfosRight ? 21 : 0)), (float)(var22 + 3), 16777215, 0.4F, ClientProxy.clientConfig.armorInfosRight ? "left" : "right", true, "minecraftDungeons", 25);
                        ModernGui.drawScaledStringCustomFont((String)ClientData.playerCombatArmorInfos.get("playerCountry"), (float)(offsetX + (ClientProxy.clientConfig.armorInfosRight ? 21 : 0)), (float)(var22 + 12), 16777215, 0.5F, ClientProxy.clientConfig.armorInfosRight ? "left" : "right", false, "georamaBold", 20);

                        if (!ClientProxy.cacheHeadPlayer.containsKey(ClientData.playerCombatArmorInfos.get("playerName")))
                        {
                            try
                            {
                                ResourceLocation var26 = AbstractClientPlayer.locationStevePng;
                                var26 = AbstractClientPlayer.getLocationSkin((String)ClientData.playerCombatArmorInfos.get("playerName"));
                                AbstractClientPlayer.getDownloadImageSkin(var26, (String)ClientData.playerCombatArmorInfos.get("playerName"));
                                ClientProxy.cacheHeadPlayer.put(ClientData.playerCombatArmorInfos.get("playerName"), var26);
                            }
                            catch (Exception var18)
                            {
                                ;
                            }
                        }
                        else
                        {
                            Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(ClientData.playerCombatArmorInfos.get("playerName")));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 14, var22 + 13, 8.0F, 16.0F, 8, -8, -8, -8, 64.0F, 64.0F);
                        }

                        var22 += 19;
                        Iterator var28 = ClientData.playerCombatArmorDurability.iterator();

                        while (var28.hasNext())
                        {
                            String var27 = (String)var28.next();
                            ItemStack var29 = new ItemStack(Integer.parseInt(var27.split(":")[0]), 1, Integer.parseInt(var27.split(":")[1]));

                            if (var29 != null && var29.getItem() != null)
                            {
                                ModernGui.bindTextureOverlayMain();
                                ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)var22, (float)(23 * GenericOverride.GUI_SCALE), (float)(247 * GenericOverride.GUI_SCALE), 40 * GenericOverride.GUI_SCALE, 39 * GenericOverride.GUI_SCALE, 20, 19, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                                GL11.glPushMatrix();
                                GL11.glScalef(0.8F, 0.8F, 0.8F);
                                this.drawItemStack(var29, client, (int)((float)(offsetX + 4) / 0.8F), (int)((float)(var22 + 3) / 0.8F));
                                GL11.glPopMatrix();
                                var22 += 19;
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawTexturedModalRect(float x, float y, int textureX, int textureY, float width, float height)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, (double)((float)textureX * 0.00390625F), (double)(((float)textureY + height) * 0.00390625F));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)(((float)textureX + width) * 0.00390625F), (double)(((float)textureY + height) * 0.00390625F));
        tessellator.addVertexWithUV((double)(x + width), (double)y, (double)this.zLevel, (double)(((float)textureX + width) * 0.00390625F), (double)((float)textureY * 0.00390625F));
        tessellator.addVertexWithUV((double)x, (double)y, (double)this.zLevel, (double)((float)textureX * 0.00390625F), (double)((float)textureY * 0.00390625F));
        tessellator.draw();
    }

    private void renderItem(Minecraft client, int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
    {
        if (stack != null)
        {
            float animation = (float)stack.animationsToGo - partialTicks;

            if (animation > 0.0F)
            {
                GL11.glPushMatrix();
                float scale = 1.0F + animation / 5.0F;
                GL11.glTranslatef((float)x + 8.0F, (float)y + 12.0F, 0.0F);
                GL11.glScalef(1.0F / scale, (scale + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef(-((float)x + 8.0F), -((float)y + 12.0F), 0.0F);
            }

            this.itemRenderer.renderItemAndEffectIntoGUI(client.fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);

            if (animation > 0.0F)
            {
                GL11.glPopMatrix();
            }

            this.itemRenderer.renderItemOverlayIntoGUI(client.fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        }

        RenderHelper.disableStandardItemLighting();
    }

    private void drawItemStack(ItemStack itemStack, Minecraft client, int xPos, int yPos)
    {
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRenderer.renderItemAndEffectIntoGUI(client.fontRenderer, Minecraft.getMinecraft().getTextureManager(), itemStack, xPos, yPos);
        RenderHelper.disableStandardItemLighting();
    }
}
