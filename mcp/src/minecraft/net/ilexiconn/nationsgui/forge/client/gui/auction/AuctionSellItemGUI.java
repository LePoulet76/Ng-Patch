package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.NumberSelector;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.RadioButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionSellPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class AuctionSellItemGUI extends GuiScreen
{
    private ItemStack itemStack;
    private NumberSelector startPrice;
    private NumberSelector quantity;
    private NumberSelector time;
    private int posX;
    private int posY;
    private int amount;
    private int taxe = 0;
    private int pubPrice = 200;
    private GuiButton sellButton;
    private int slotID;
    private RadioButton radioButton;

    public AuctionSellItemGUI(ItemStack itemStack, int slotID)
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
        this.startPrice.keyTyped(par1, par2);
        this.quantity.keyTyped(par1, par2);
        this.time.keyTyped(par1, par2);
        super.keyTyped(par1, par2);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int par3)
    {
        if (mouseX >= this.posX + 5 && mouseY >= this.posY + 118 && mouseX <= this.posX + 5 + 183 && mouseY <= this.posY + 118 + 19)
        {
            Minecraft.getMinecraft().displayGuiScreen(new SellItemGUI(this.itemStack, this.slotID));
        }

        this.startPrice.mouseClicked(mouseX, mouseY, par3);
        this.quantity.mouseClicked(mouseX, mouseY, par3);
        this.time.mouseClicked(mouseX, mouseY, par3);
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
        String txt = this.startPrice != null ? this.startPrice.getText() : "0";
        this.startPrice = new NumberSelector(this.mc, this.posX + 174, this.posY + 16, 80, " $");
        this.startPrice.setText(txt);
        txt = this.quantity != null ? this.quantity.getText() : "0";
        this.quantity = new NumberSelector(this.mc, this.posX + 174, this.posY + 37, 80);
        this.quantity.setText(txt);
        this.quantity.setMax(this.amount);
        txt = this.time != null ? this.time.getText() : "0";
        this.time = new NumberSelector(this.mc, this.posX + 174, this.posY + 58, 80);
        this.time.setText(txt);
        this.time.setMax(168);
        this.sellButton = new GuiButton(0, this.posX + 173, this.posY + 83, 80, 20, I18n.getString("auctions.sell.action"));
        this.buttonList.add(this.sellButton);
        this.radioButton = new RadioButton(this.posX + 82, this.posY + 79);
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        ClientEventHandler.STYLE.bindTexture("auction_sell");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 275, 117, 275.0F, 256.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 5), (float)(this.posY + 117), 79, 117, 183, 19, 275.0F, 256.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 5 + 27), (float)(this.posY + 117 + 2), 8, 150, 15, 16, 275.0F, 256.0F, false);
        this.fontRenderer.drawString(I18n.getString("auctions.sell.toHDV"), this.posX + 5 + 23 + 25 + 10, this.posY + 118 + 5, 16777215);
        ModernGui.drawScaledString(this.itemStack.getDisplayName(), this.posX + 47, this.posY + 17, 16777215, 0.75F, true, false);
        this.fontRenderer.drawString(I18n.getString("auctions.sell.price"), this.posX + 84, this.posY + 22, 16777215);
        this.fontRenderer.drawString(I18n.getString("auctions.sell.quantity"), this.posX + 84, this.posY + 44, 16777215);
        this.fontRenderer.drawString(I18n.getString("auctions.sell.time"), this.posX + 84, this.posY + 66, 16777215);
        this.fontRenderer.drawString(I18n.getString("auctions.ad"), this.posX + 82 + 14, this.posY + 79, 16777215);
        String t = "...";
        this.taxe = 200;

        if (this.radioButton.getState())
        {
            this.taxe += this.pubPrice;
        }

        t = Integer.toString(this.taxe);
        String s = I18n.getStringParams("auctions.sell.taxes", new Object[] {t});
        this.fontRenderer.drawString(s, this.posX + 82, this.posY + 92, 16777215);

        if (this.startPrice != null)
        {
            this.startPrice.draw(par1, par2);
        }

        if (this.quantity != null)
        {
            this.quantity.draw(par1, par2);
        }

        if (this.time != null)
        {
            this.time.draw(par1, par2);
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

        this.sellButton.enabled = this.taxe != -1 && (double)d <= ShopGUI.CURRENT_MONEY && this.quantity.getNumber() > 0 && this.startPrice.getNumber() > 0 && this.time.getNumber() > 0;
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
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionSellPacket(this.slotID, this.startPrice.getNumber(), this.quantity.getNumber(), (long)(this.time.getNumber() * 3600 * 1000), this.radioButton.getState())));
            this.mc.displayGuiScreen((GuiScreen)null);
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
