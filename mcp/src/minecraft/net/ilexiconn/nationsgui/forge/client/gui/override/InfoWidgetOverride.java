package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.PingThread;
import net.ilexiconn.nationsgui.forge.client.util.InterpolationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class InfoWidgetOverride extends Gui implements ElementOverride
{
    private float lastWidth = 0.0F;
    public static String faction = "Wilderness";
    private RenderItem renderItem = new RenderItem();

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
        ClientEventHandler.STYLE.bindTexture("hud2");
        this.drawTexturedModalRect(resolution.getScaledWidth() - 19, resolution.getScaledHeight() - 80, 0, 0, 19, 18);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String s = GameSettings.getKeyDisplayString(ClientKeyHandler.KEY_SPECIAL_INFO.keyCode);
        fontRenderer.drawString(s, resolution.getScaledWidth() - 12 + fontRenderer.getStringWidth(s) / 2, resolution.getScaledHeight() - 54 - 20, -16777216);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("hud2");
        int offsetX = ClientProxy.clientConfig.displayArmorInInfo ? 0 : 75;

        if (ClientProxy.clientConfig.specialEnabled || this.lastWidth < 145.0F)
        {
            int xPos = (int)(this.lastWidth + (float)resolution.getScaledWidth() - 146.0F + (float)offsetX);
            int yPos = resolution.getScaledHeight() - 100;
            this.drawTexturedModalRect(xPos, yPos, 33, 0, 146, 62);
            ArrayList information = new ArrayList();
            information.add(faction);
            information.add(PingThread.ping + " ms");
            information.add(ClientEventHandler.lastClicks + " cps");
            information.add("ToggleSprint " + (ClientKeyHandler.toggleSprintEnabled ? "\u00a7aON" : "\u00a7cOFF"));
            information.add(client.debug.split(",")[0]);
            long time = (long)((float)(client.theWorld.getWorldTime() - 6000L) / 24000.0F * 86400.0F) * 1000L;
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String formattedDate = sdf.format(date);
            information.add(formattedDate);

            for (int entityPlayer = 0; entityPlayer < information.size(); ++entityPlayer)
            {
                ClientEventHandler.STYLE.bindTexture("hud2");
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                this.drawTexturedModalRect(xPos + 7, yPos + 9 + 7 * entityPlayer, 0, 56, 7, 5);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(xPos + 15), (float)(yPos + 10 + 7 * entityPlayer), 0.0F);
                GL11.glScalef(0.5F, 0.5F, 1.0F);
                this.drawString(client.fontRenderer, (String)information.get(entityPlayer), 0, 0, 16777215);
                GL11.glPopMatrix();
            }

            EntityClientPlayerMP var18 = Minecraft.getMinecraft().thePlayer;

            for (int handItem = 0; handItem < 4; ++handItem)
            {
                ItemStack itemStack = var18.getCurrentArmor(3 - handItem);

                if (itemStack != null)
                {
                    this.drawItemStack(itemStack, client, xPos + 75 + 17 * handItem, yPos + 5);
                }
            }

            ItemStack var19 = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();

            if (var19 != null)
            {
                this.drawItemStack(var19, client, xPos + 100, yPos + 30);
            }

            this.lastWidth = InterpolationUtil.interpolate(this.lastWidth, ClientProxy.clientConfig.specialEnabled ? 0.0F : 146.0F - (float)offsetX, 0.2F);
        }
    }

    private void drawItemStack(ItemStack itemStack, Minecraft client, int xPos, int yPos)
    {
        RenderHelper.enableGUIStandardItemLighting();
        this.renderItem.renderItemAndEffectIntoGUI(client.fontRenderer, Minecraft.getMinecraft().getTextureManager(), itemStack, xPos, yPos);
        this.renderItem.renderItemOverlayIntoGUI(client.fontRenderer, Minecraft.getMinecraft().getTextureManager(), itemStack, xPos, yPos);

        if (itemStack.isItemStackDamageable())
        {
            String str = Integer.toString(itemStack.getMaxDamage() - itemStack.getItemDamageForDisplay());
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)xPos + 8.0F - (float)client.fontRenderer.getStringWidth(str) / 4.0F, (float)(yPos + 17), 0.0F);
            GL11.glScalef(0.5F, 0.5F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.drawString(client.fontRenderer, str, 0, 0, 16777215);
            GL11.glPopMatrix();
        }

        RenderHelper.disableStandardItemLighting();
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
}
