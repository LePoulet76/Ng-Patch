package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.CommonEventHandler;
import fr.nationsglory.ngupgrades.common.item.SpartanWhiteArmorItem;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SpartanWhiteOverride extends Gui implements ElementOverride
{
    protected static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/default/overlay_spartan_white.png");
    public static CFontRenderer georamaSemiBold = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(30));
    public static Long lastDisplayHotbarMessageTime = Long.valueOf(0L);
    private RenderItem itemRenderer = new RenderItem();

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (!ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main"))
        {
            ClientEventHandler.STYLE.bindTexture("overlay_spartan_white");
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
            int screenWidth = resolution.getScaledWidth();
            int screenHeight = resolution.getScaledHeight();
            tessellator.addVertexWithUV(0.0D, (double)screenHeight, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV((double)screenWidth, (double)screenHeight, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV((double)screenWidth, 0.0D, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            GL11.glDisable(GL11.GL_BLEND);
            int offsetXMiddle = resolution.getScaledWidth() / 2;
            ItemStack chestplate = client.thePlayer.getCurrentArmor(2);

            if (CommonEventHandler.isWearingFullSpartanWhiteArmor(client.thePlayer) && chestplate != null && chestplate.getItem() instanceof SpartanWhiteArmorItem)
            {
                SpartanWhiteArmorItem spartanWhiteArmorItem = (SpartanWhiteArmorItem)chestplate.getItem();
                ClientEventHandler.STYLE.bindTexture("overlay_main_spartan_white");
                float shieldValue = spartanWhiteArmorItem.getShieldValue(chestplate);
                float progress = shieldValue / 100.0F;
                int offsetY = resolution.getScaledHeight() - 65;

                if (ClientProxy.clientConfig.enableAzimut && ClientProxy.clientConfig.azimutBottom)
                {
                    offsetY -= 20;
                }

                ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 86), (float)offsetY, (float)(1366 * GenericOverride.GUI_SCALE), (float)(319 * GenericOverride.GUI_SCALE), 128 * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, 64, 10, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 86), (float)offsetY, (float)(1366 * GenericOverride.GUI_SCALE), (float)(345 * GenericOverride.GUI_SCALE), (int)(128.0F * progress) * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, (int)(128.0F * progress) / 2, 10, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle + 22), (float)offsetY, (float)(1499 * GenericOverride.GUI_SCALE), (float)(319 * GenericOverride.GUI_SCALE), 128 * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, 64, 10, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle + 22) + 128.0F * (1.0F - progress) / 2.0F, (float)offsetY, (1499.0F + 128.0F * (1.0F - progress)) * (float)GenericOverride.GUI_SCALE, (float)(345 * GenericOverride.GUI_SCALE), (int)(128.0F * progress) * GenericOverride.GUI_SCALE, 21 * GenericOverride.GUI_SCALE, (int)(128.0F * progress) / 2, 10, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);

                if (progress == 1.0F)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 14), (float)(offsetY - 10), (float)(1363 * GenericOverride.GUI_SCALE), (float)(254 * GenericOverride.GUI_SCALE), 56 * GenericOverride.GUI_SCALE, 55 * GenericOverride.GUI_SCALE, 28, 27, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
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
