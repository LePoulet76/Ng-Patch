package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Default_Packet;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormTraderPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class EnterpriseContractForm_Trader_Gui extends GuiScreen
{
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField priceInput;
    private RenderItem itemRenderer = new RenderItem();
    protected int xSize = 371;
    protected int ySize = 223;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    public static HashMap<String, Object> data = new HashMap();

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.priceInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractFormTraderPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.getString("enterprise.contract.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.getString("enterprise.contract.action.valid"));
        this.priceInput = new GuiTextField(this.fontRenderer, this.guiLeft + 219, this.guiTop + 166, 58, 10);
        this.priceInput.setEnableBackgroundDrawing(false);
        this.priceInput.setMaxStringLength(7);
        this.priceInput.setText("0");
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        List tooltipToDraw = null;
        ModernGui.drawModalRectWithCustomSizedTexture((float)((int)((double)(this.guiLeft + 91) - (double)this.fontRenderer.getStringWidth(I18n.getString("enterprise.contract.title.services")) * 1.2D / 2.0D - 8.0D - 2.0D)), (float)(this.guiTop + 16), 0, 276, 16, 16, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 16777215, 1.2F, true, false);
        int index = 0;
        String[] type = ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##");
        int investLimit = type.length;

        for (int playerInvest = 0; playerInvest < investLimit; ++playerInvest)
        {
            String canInvest = type[playerInvest];
            this.drawScaledString(canInvest.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 16777215, 0.8F, false, false);
            ++index;
        }

        this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 16777215, 1.7F, true, true);
        String var11 = I18n.getString("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase());
        ClientEventHandler.STYLE.bindTexture("enterprise_main");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 280 - this.fontRenderer.getStringWidth(var11) / 2 - 7 - 4), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 280 + 7, this.guiTop + 29, 11842740, 1.0F, true, false);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("enterprise.contract.label.mymoney"), this.guiLeft + 197, this.guiTop + 70 - 9, 1644825, 1.0F, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 70, 165, 20);
            this.drawScaledString(String.format("%.0f", new Object[] {(Double)data.get("playerMoney")}) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 70 + 6, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.contract.label.invested").replace("<enterprise>", (String)EnterpriseGui.enterpriseInfos.get("name")), this.guiLeft + 197, this.guiTop + 115 - 9, 1644825, 1.0F, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 115, 165, 20);
            this.drawScaledString(String.format("%.2f", new Object[] {(Double)data.get("totalPlayerInvestment")}) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 115 + 6, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.contract.label.investment"), this.guiLeft + 197, this.guiTop + 151, 1644825, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 160, 165, 20);
            this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 16777215, 1.3F, true, false);
            this.priceInput.drawTextBox();

            if (this.isNumeric(this.priceInput.getText()))
            {
                Double var12 = Double.valueOf(((Double)data.get("totalInvestment")).doubleValue() * 0.1D);
                var12 = Double.valueOf(Math.max(50000.0D, var12.doubleValue()));
                Double var13 = (Double)data.get("totalPlayerInvestment");
                var13 = Double.valueOf(var13.doubleValue() + (double)Integer.parseInt(this.priceInput.getText()));

                if (var13.doubleValue() > var12.doubleValue())
                {
                    this.validButton.enabled = false;
                    double var14 = var12.doubleValue() - ((Double)data.get("totalPlayerInvestment")).doubleValue();

                    if (mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20)
                    {
                        tooltipToDraw = Arrays.asList(I18n.getString("enterprise.contract.trader.investment_limit").replace("<limit>", var14 + "").split("##"));
                    }
                }
                else if (data.containsKey("countContract") && ((Double)data.get("countContract")).doubleValue() >= 2.0D)
                {
                    this.validButton.enabled = false;

                    if (mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20)
                    {
                        tooltipToDraw = Arrays.asList(I18n.getString("enterprise.contract.trader.count_limit").split("##"));
                    }
                }
                else
                {
                    this.validButton.enabled = true;
                }
            }
        }

        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.validButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        if (tooltipToDraw != null)
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.priceInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
            }

            if (this.validButton.enabled && this.isNumeric(this.priceInput.getText()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                EnterpriseGui.lastContractDemand = Long.valueOf(System.currentTimeMillis());
                String content = I18n.getString("enterprise.contract.content.trader");
                content = content.replace("<amount>", this.priceInput.getText());
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Default_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), content, Integer.valueOf(Integer.parseInt(this.priceInput.getText())), Long.valueOf(-10L))));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
        }

        this.priceInput.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
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

            if (Integer.parseInt(str) <= 0)
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

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
            this.itemRenderer.zLevel = 300.0F;
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
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
