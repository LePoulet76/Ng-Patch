package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class StatsGui extends GuiScreen
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market_stats.png");
    private GuiScreen previousGui;
    private int posX;
    private int posY;
    private RenderItem renderItem = new RenderItem();
    private ItemStack itemStack;
    public static final ResourceLocation CHART1_TEXTURE = new ResourceLocation("nationsgui", "tmp/chart1");
    public static final ResourceLocation CHART2_TEXTURE = new ResourceLocation("nationsgui", "tmp/chart2");

    public StatsGui(GuiScreen previousGui, ItemStack itemStack)
    {
        this.previousGui = previousGui;
        this.itemStack = itemStack;
        PacketCallbacks.MONEY.send(new String[0]);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 343, 256, 372.0F, 400.0F, false);
        String money = (int)ShopGUI.CURRENT_MONEY + " $";
        this.fontRenderer.drawString(money, this.posX + 312 - this.fontRenderer.getStringWidth(money), this.posY + 9, 16777215);
        this.fontRenderer.drawString(I18n.getStringParams("hdv.stats.name", new Object[] {this.itemStack.getDisplayName().replaceAll("^\\\u00a7[0-9a-z]", "")}), this.posX + 75, this.posY + 80, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.posX + 28), (float)(this.posY + 3), 0.0F);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.fontRenderer.drawStringWithShadow(I18n.getString("hdv.title"), 0, 0, 16777215);
        GL11.glPopMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        float size = 3.0F;
        GL11.glTranslatef((float)(this.posX + 37) - 8.0F * size, (float)(this.posY + 117) - 8.0F * size, 0.0F);
        GL11.glScalef(size, size, size);
        this.renderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), this.itemStack, 0, 0);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        super.drawScreen(par1, par2, par3);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double)this.mc.displayWidth, (double)this.mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(CHART1_TEXTURE);
        this.drawPriceChart(this.posX + 70, this.posY + 90, 260, 75);
        Minecraft.getMinecraft().getTextureManager().bindTexture(CHART2_TEXTURE);
        this.drawPriceChart(this.posX + 70, this.posY + 170, 260, 75);

        if (par1 >= this.posX + 7 && par1 <= this.posX + 7 + 60 && par2 >= this.posY + 75 && par2 <= this.posY + 75 + 83)
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
            NationsGUIClientHooks.drawItemStackTooltip(this.itemStack, par1, par2);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.posX = this.width / 2 - 171;
        this.posY = this.height / 2 - 126;
        this.buttonList.clear();
        this.buttonList.add(new MarketSimpleButton(0, this.posX + 6, this.posY + 35, 75, 15, I18n.getString("hdv.return")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(this.previousGui);
        }
    }

    private void drawPriceChart(int x, int y, int width, int height)
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        x *= scaledresolution.getScaleFactor();
        y *= scaledresolution.getScaleFactor();
        width *= scaledresolution.getScaleFactor();
        height *= scaledresolution.getScaleFactor();
        ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 0, width, height, (float)width, (float)height, true);
    }
}
