package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionSellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PreSellStatsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SellItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SellItemGUI extends GuiScreen
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/hdv_sell.png");
    private ItemStack itemStack;
    private NumberSelector price;
    private NumberSelector quantity;
    private int posX;
    private int posY;
    private int amount;
    private int taxe = 0;
    private int pubPrice = 200;
    private GuiButton sellButton;
    private int slotID;
    private RadioButton radioButton;

    public SellItemGUI(ItemStack itemStack, int slotID)
    {
        this.itemStack = itemStack;
        this.slotID = slotID;
        ItemStack[] var3 = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            ItemStack itemStackInInventory = var3[var5];

            if (itemStackInInventory != null && itemStackInInventory.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(itemStackInInventory, itemStack))
            {
                this.amount += itemStackInInventory.stackSize;
            }
        }

        PacketCallbacks.MONEY.send(new String[0]);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.price.keyTyped(par1, par2);
        this.quantity.keyTyped(par1, par2);
        super.keyTyped(par1, par2);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int par3)
    {
        if (mouseX >= this.posX + 5 && mouseY >= this.posY + 118 && mouseX <= this.posX + 5 + 183 && mouseY <= this.posY + 118 + 19)
        {
            Minecraft.getMinecraft().displayGuiScreen(new AuctionSellItemGUI(this.itemStack, this.slotID));
        }

        this.price.mouseClicked(mouseX, mouseY, par3);
        this.quantity.mouseClicked(mouseX, mouseY, par3);
        this.radioButton.mousePressed(mouseX, mouseY, par3);
        super.mouseClicked(mouseX, mouseY, par3);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.posX = this.width / 2 - 137;
        this.posY = this.height / 2 - 59;
        String txt = this.price != null ? this.price.getText() : "0";
        this.price = new NumberSelector(this.mc, this.posX + 82, this.posY + 40, 70, " $");
        this.price.setText(txt);
        txt = this.quantity != null ? this.quantity.getText() : "0";
        this.quantity = new NumberSelector(this.mc, this.posX + 82, this.posY + 75, 70);
        this.quantity.setText(txt);
        this.quantity.setMax(this.amount);
        this.sellButton = new GuiButton(0, this.posX + 187, this.posY + 83, 70, 20, I18n.getString("hdv.sell.action"));
        this.buttonList.add(this.sellButton);
        this.buttonList.add(new StatsButton(1, this.posX + 191, this.posY + 13));
        this.radioButton = new RadioButton(this.posX + 257 - this.fontRenderer.getStringWidth(I18n.getString("hdv.ad")) - 12, this.posY + 55);
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 275, 117, 275.0F, 256.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 5), (float)(this.posY + 117), 79, 117, 183, 19, 275.0F, 256.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 5 + 23), (float)(this.posY + 117 + 2), 31, 151, 25, 15, 275.0F, 256.0F, false);
        this.fontRenderer.drawString(I18n.getString("hdv.sell.toAuctions"), this.posX + 5 + 23 + 25 + 10, this.posY + 118 + 5, 16777215);
        ModernGui.drawScaledString(this.itemStack.getDisplayName(), this.posX + 47, this.posY + 17, 16777215, 0.75F, true, false);
        this.fontRenderer.drawString(I18n.getString("hdv.sell.price"), this.posX + 82, this.posY + 30, 16777215);
        this.fontRenderer.drawString(I18n.getString("hdv.sell.quantity"), this.posX + 82, this.posY + 65, 16777215);
        this.fontRenderer.drawString(I18n.getString("hdv.ad"), this.posX + 257 - this.fontRenderer.getStringWidth(I18n.getString("hdv.ad")), this.posY + 55, 16777215);
        String t = "...";
        this.taxe = (int)((double)(this.price.getNumber() * this.quantity.getNumber()) * 0.01D);
        this.taxe = Math.max(5, this.taxe);

        if (ClientData.bonusStartTime.longValue() != 0L && System.currentTimeMillis() >= ClientData.bonusStartTime.longValue() && System.currentTimeMillis() <= ClientData.bonusEndTime.longValue())
        {
            this.taxe = 0;
        }

        if (this.radioButton.getState())
        {
            this.taxe += this.pubPrice;
        }

        t = Integer.toString(this.taxe);
        String s = I18n.getStringParams("hdv.sell.taxes", new Object[] {t});
        this.fontRenderer.drawString(s, this.posX + 257 - this.fontRenderer.getStringWidth(s), this.posY + 70, 16777215);

        if (this.price != null)
        {
            this.price.draw(par1, par2);
        }

        if (this.quantity != null)
        {
            this.quantity.draw(par1, par2);
        }

        RenderItem renderItem = new RenderItem();
        float modifier = 3.2F;
        int size = (int)(16.0F * modifier);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.posX + 47 - size / 2), (float)(this.posY + 58 - size / 2), 0.0F);
        GL11.glScalef(modifier, modifier, 1.0F);
        ItemStack item = this.itemStack.copy();
        item.stackSize = 1;
        renderItem.renderItemIntoGUI(this.fontRenderer, this.mc.getTextureManager(), item, 0, 0);
        renderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), item, 0, 0);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        this.radioButton.draw(par1, par2);
        super.drawScreen(par1, par2, par3);

        if (par1 >= this.posX + 14 && par1 <= this.posX + 14 + 65 && par2 >= this.posY + 13 && par2 <= this.posY + 13 + 91)
        {
            NationsGUIClientHooks.drawItemStackTooltip(item, par1, par2);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        int d = this.taxe;

        if (this.radioButton.getState())
        {
            d += this.pubPrice;
        }

        this.sellButton.enabled = this.taxe != -1 && (double)d <= ShopGUI.CURRENT_MONEY && this.quantity.getNumber() > 0 && this.price.getNumber() > 0;
    }

    public void setTaxe(int taxe)
    {
        this.taxe = taxe;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SellItemPacket(this.slotID, this.price.getNumber(), this.quantity.getNumber(), this.radioButton.getState())));
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par1GuiButton.id == 1)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PreSellStatsPacket(this.slotID)));
        }
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public void setPubPrice(int pubPrice)
    {
        this.pubPrice = pubPrice;
    }
}
