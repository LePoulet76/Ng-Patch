package net.ilexiconn.nationsgui.forge.client.gui.trade;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade$VoidButton;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeUpdateMoneyPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiTrade extends GuiContainer implements ITrade
{
    private static ResourceLocation resource = new ResourceLocation("nationsgui", "textures/gui/trade/troc.png");
    public Map items = new HashMap();
    public EntityPlayer trader = null;
    private ContainerTrade container;
    private GuiButton accept;
    private GuiTextField moneyField;
    private int moneyUpdater;
    public int moneyTrader;
    public boolean traderIsReady = false;
    public boolean hasEnoughMoney = true;
    private boolean imReady = false;
    public long lastInteraction = 0L;
    public static long stopCooldown;

    public GuiTrade(Container container)
    {
        super(container);
        this.container = (ContainerTrade)container;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketCallbacks.MONEY.send(new String[0]);
        this.xSize = 401;
        this.ySize = 216;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonList.clear();
        this.buttonList.add(this.accept = new GuiButton(0, this.guiLeft + 16, this.guiTop + 95, 35, 20, "Ok"));
        this.buttonList.add(new GuiTrade$VoidButton(this, 1, this.guiLeft + 181, this.guiTop + 17, 9, 10));
        this.moneyField = new GuiTextField(this.fontRenderer, this.guiLeft + 113, this.guiTop + 102, 58, 20);
        this.moneyField.setMaxStringLength(8);
        this.moneyField.setFocused(false);
        this.moneyField.setText("0");
        this.moneyField.setEnableBackgroundDrawing(false);

        if (this.trader != null)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.getText()), this.trader.username, true)));
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float partialsTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        GL11.glPushMatrix();
        GUIUtils.startGLScissor(this.guiLeft + 16, this.guiTop + 56, 35, 35);
        GuiInventory.func_110423_a(this.guiLeft + 35, this.guiTop + 125, 35, (float)((-mouseX + this.guiLeft + 32) / 4), (float)((-mouseY + this.guiTop + 32) / 4), this.mc.thePlayer);
        GUIUtils.endGLScissor();

        if (this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING)
        {
            GUIUtils.startGLScissor(this.guiLeft + 16, this.guiTop + 146, 35, 35);
            GuiInventory.func_110423_a(this.guiLeft + 35, this.guiTop + 215, 35, (float)((-mouseX + this.guiLeft + 32) / 4), (float)((-mouseY + this.guiTop + 32) / 4), this.trader);
            GUIUtils.endGLScissor();
        }

        GL11.glPopMatrix();
        this.mc.getTextureManager().bindTexture(resource);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (this.imReady)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57), (float)(this.guiTop + 56), 0, 218, 4, 59, 512.0F, 512.0F, false);
        }

        if (this.traderIsReady)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57), (float)(this.guiTop + 146), 0, 218, 4, 59, 512.0F, 512.0F, false);
        }

        this.drawString(this.fontRenderer, (int)ShopGUI.CURRENT_MONEY + "$", this.guiLeft + 378 - this.fontRenderer.getStringWidth((int)ShopGUI.CURRENT_MONEY + "$"), this.guiTop + 63, -1);
        this.drawString(this.fontRenderer, "Mon Inventaire", this.guiLeft + 249, this.guiTop + 63, -1);
        this.drawString(this.fontRenderer, String.valueOf(this.moneyTrader), this.guiLeft + 113, this.guiTop + 193, -1);

        if (this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING)
        {
            this.drawString(this.fontRenderer, "Echange", this.guiLeft + 33, this.guiTop + 12, -1);
            this.drawString(this.fontRenderer, "Avec " + this.trader.username, this.guiLeft + 33, this.guiTop + 23, 11842740);
            ItemStack hover = null;

            for (int k = 0; k < 14; ++k)
            {
                ItemStack item = (ItemStack)this.items.get(Integer.valueOf(k));

                if (item != null)
                {
                    int x = this.guiLeft + 66 + k % 7 * 18;
                    int y = this.guiTop + 147 + k / 7 * 18;
                    this.drawItemStack(item, x, y, (String)null);

                    if (this.isPointInRegion(x - this.guiLeft, y - this.guiTop, 16, 16, mouseX, mouseY))
                    {
                        hover = item;
                    }
                }
            }

            if (hover != null)
            {
                this.drawItemStackTooltip(hover, mouseX, mouseY);
            }
        }

        if (!this.accept.enabled && mouseX >= this.guiLeft + 16 && mouseX <= this.guiLeft + 16 + 35 && mouseY >= this.guiTop + 95 && mouseY <= this.guiTop + 95 + 20 && !this.hasEnoughMoney)
        {
            this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("trade.not_enough_money")}), mouseX, mouseY, this.fontRenderer);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        this.lastInteraction = System.currentTimeMillis();

        if (this.moneyField.textboxKeyTyped(par1, par2))
        {
            if (Character.isDigit(par1) || par2 == 14 || par2 == 54 || par2 == 42 || par2 == 205 || par2 == 203 || par2 == 211)
            {
                if (Character.isDigit(par1) && this.moneyField.getText().equals("0"))
                {
                    this.moneyField.setText("");
                }

                if (this.moneyField.getText().isEmpty() || this.moneyField.getText().matches("0*"))
                {
                    this.moneyField.setText("0");
                }
            }

            if (this.moneyField.getText() != null && !this.moneyField.getText().isEmpty() && this.isNumeric(this.moneyField.getText()) && this.trader != null)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.getText()), this.trader.username, true)));
            }
            else if (this.trader != null)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(0, this.trader.username, true)));
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.moneyField.mouseClicked(par1, par2, par3);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        this.moneyField.updateCursorCounter();

        if (this.moneyUpdater > 10)
        {
            PacketCallbacks.MONEY.send(new String[0]);

            if (this.isNumeric(this.moneyField.getText()) && this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING && this.container.state != EnumTradeState.TRADER_ACCEPTED)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.getText()), this.trader.username, false)));
            }

            this.moneyUpdater = 0;
        }

        ++this.moneyUpdater;
    }

    public boolean isNumeric(String str)
    {
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) < 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.moneyField.drawTextBox();
        this.accept.enabled = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton btn)
    {
        switch (btn.id)
        {
            case 0:
                if (!this.isNumeric(this.moneyField.getText()))
                {
                    this.moneyField.setText("0");
                    return;
                }

                if (this.accept.enabled && System.currentTimeMillis() - this.lastInteraction > 1000L && this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING)
                {
                    TradeManager.sendData(EnumPacketServer.TRADE_COMPLETE, Integer.parseInt(this.moneyField.getText()));
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                }

                break;

            case 1:
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        PacketCallbacks.MONEY.send(new String[0]);
        TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);
    }

    public EntityPlayer getTrader()
    {
        return this.trader;
    }

    public static void addCooldown(long timeMillis)
    {
        stopCooldown = System.currentTimeMillis() + timeMillis;
    }

    public void updateState(EnumTradeState state)
    {
        this.container.state = state;
        this.imReady = state != EnumTradeState.STARTED && state != EnumTradeState.TRADER_ACCEPTED;
        this.accept.enabled = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
        this.traderIsReady = state == EnumTradeState.TRADER_ACCEPTED;
    }

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = null;

        if (par1ItemStack != null)
        {
            font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        }

        if (font == null)
        {
            font = this.fontRenderer;
        }

        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
        itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3, par4Str);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
