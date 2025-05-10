package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.AdvancedGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractAssistanceGUI extends AdvancedGui
{
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("nationsgui", "textures/gui/assistance.png");
    protected static final int GUI_WIDTH = 373;
    protected static final int GUI_HEIGHT = 227;
    public static boolean canBeAdmin = false;
    protected int guiTop;
    protected int guiLeft;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = this.width / 2 - 186;
        this.guiTop = this.height / 2 - 113;

        if (canBeAdmin)
        {
            StaffButton staffButton = new StaffButton(this.guiLeft + 336, this.guiTop + 12);

            if (ClientProxy.playersInAdminMode.containsKey(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
            {
                staffButton.setActive(((Boolean)ClientProxy.playersInAdminMode.get(Minecraft.getMinecraft().thePlayer.getCommandSenderName())).booleanValue());
            }

            this.addComponent(staffButton);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 373, 227);
        ModernGui.drawScaledString(I18n.getString("nationsgui.assistance.title"), this.guiLeft + 40, this.guiTop + 13, 16777215, 1.5F, false, true);
        this.drawGui(mouseX, mouseY, partialTick);
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    protected abstract void drawGui(int var1, int var2, float var3);

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int posX, int posY, int u, int v, int width, int height)
    {
        float f = 0.001953125F;
        float f1 = 0.001953125F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)posX, (double)(posY + height), (double)this.zLevel, (double)((float)u * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(posX + width), (double)(posY + height), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(posX + width), (double)posY, (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)v * f1));
        tessellator.addVertexWithUV((double)posX, (double)posY, (double)this.zLevel, (double)((float)u * f), (double)((float)v * f1));
        tessellator.draw();
    }
}
