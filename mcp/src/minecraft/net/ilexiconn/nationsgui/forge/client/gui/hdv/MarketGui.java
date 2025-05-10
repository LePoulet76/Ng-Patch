package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$5;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$6;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui$7;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BuyMarketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ItemStackMarketStatsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoveSalePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class MarketGui extends GuiScreen
{
    public static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
    private List<MarketSale> marketSales = new ArrayList();
    private List<MarketSale> filteredMarket = new ArrayList();
    private int posX;
    private int posY;
    private RenderItem renderItem = new RenderItem();
    private GuiScrollBar guiScrollBar;
    private MarketSale selected = null;
    private GuiButton guiButton;
    private NumberSelector numberSelector;
    private StatsButton statsButton;
    public static int currentPage = 1;
    private List<MarketSale> currentSale = new ArrayList();
    public static GuiTextField search;
    public static boolean mySalesOnly = false;
    private ChoiseSelectorGui<Comparator<MarketSale>> filterSelector;
    private static Map<String, Comparator<MarketSale>> filters = new LinkedHashMap();
    private static long lastBuy = 0L;
    public static int totalResults = 0;
    public static boolean canRemoveAll = false;
    public static boolean achievementDone = false;

    public MarketGui()
    {
        search = null;
        currentPage = 1;
        mySalesOnly = false;

        if (!achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_hdv", 1)));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        PacketCallbacks.MONEY.send(new String[0]);
        this.posX = this.width / 2 - 171;
        this.posY = this.height / 2 - 128;
        this.buttonList.clear();
        ChangePageButton b1 = new ChangePageButton(0, this.posX + 270, this.posY + 235, false);
        b1.enabled = currentPage > 1;
        this.buttonList.add(b1);
        ChangePageButton b2 = new ChangePageButton(1, this.posX + 322, this.posY + 235, true);
        b2.enabled = currentPage < totalResults / 10 + 1;
        this.buttonList.add(b2);
        this.buttonList.add(new MarketSimpleButton(2, this.posX + 230, this.posY + 35, 100, 16, I18n.getString(mySalesOnly ? "hdv.othersales" : "hdv.mysales")));
        Entry s = null;

        if (this.filterSelector != null)
        {
            s = this.filterSelector.saveSelection();
        }

        this.filterSelector = new ChoiseSelectorGui(this.posX + 127, this.posY + 35, 75, filters);

        if (s != null)
        {
            this.filterSelector.restoreSelection(s);
        }

        if (search == null)
        {
            search = new GuiTextField(this.fontRenderer, this.posX + 27, this.posY + 39, 90, 16);
            search.setEnableBackgroundDrawing(false);
        }

        float save = 0.0F;

        if (this.guiScrollBar != null)
        {
            save = this.guiScrollBar.getSliderValue();
        }

        this.guiScrollBar = new MarketGui$7(this, (float)(this.posX + 326), (float)(this.posY + 79), 149);
        this.guiScrollBar.setSliderValue(save);
        this.guiButton = null;
        this.numberSelector = null;

        if (this.filteredMarket != null)
        {
            int i = 0;

            for (Iterator var6 = this.currentSale.iterator(); var6.hasNext(); ++i)
            {
                MarketSale marketSale = (MarketSale)var6.next();
                int y = i * 20;

                if (marketSale.equals(this.selected) && marketSale.getItemStack() != null)
                {
                    if (marketSale.getPseudo().equals(Minecraft.getMinecraft().thePlayer.username))
                    {
                        this.guiButton = null;
                        this.numberSelector = null;
                    }
                    else if (marketSale.getExpiry() > System.currentTimeMillis())
                    {
                        this.guiButton = new GuiButton(1, this.posX + 6 + 65, this.posY + 79 + y + 60, 80, 20, I18n.getString("hdv.buy"));
                        this.numberSelector = new NumberSelector(this.mc, this.posX + 6 + 150, this.posY + 79 + y + 60, 80);
                        this.numberSelector.setText("1");
                        int a = 0;
                        ItemStack[] qt = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
                        int var11 = qt.length;

                        for (int var12 = 0; var12 < var11; ++var12)
                        {
                            ItemStack itemStack = qt[var12];

                            if (itemStack != null && itemStack.isItemEqual(marketSale.getItemStack()) && ItemStack.areItemStackTagsEqual(itemStack, marketSale.getItemStack()))
                            {
                                a += itemStack.getMaxStackSize() - itemStack.stackSize;
                            }
                            else if (itemStack == null)
                            {
                                a += marketSale.getItemStack().getMaxStackSize();
                            }
                        }

                        int var14 = marketSale.getQuantity() - marketSale.getSold();
                        this.numberSelector.setMax(a >= var14 ? var14 : a);
                    }

                    this.statsButton = new StatsButton(3, this.posX + 252, this.posY + y + 149);
                }
            }
        }

        super.initGui();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.numberSelector != null)
        {
            this.numberSelector.update();
        }

        if (this.selected != null && !this.selected.getPseudo().equals(Minecraft.getMinecraft().thePlayer.username) && this.guiButton != null)
        {
            long buyDiff = (System.currentTimeMillis() - lastBuy) / 1000L;
            this.guiButton.enabled = this.selected.getExpiry() > System.currentTimeMillis() && this.numberSelector.getNumber() > 0 && ShopGUI.CURRENT_MONEY >= (double)(this.selected.getPrice() * this.numberSelector.getNumber()) && this.numberSelector.getMax() > 0 && buyDiff >= 3L;

            if (buyDiff < 3L)
            {
                this.guiButton.displayString = 3L - buyDiff + " s";
            }
            else
            {
                this.guiButton.displayString = I18n.getString("hdv.buy");
            }
        }

        if (search != null)
        {
            search.updateCursorCounter();
        }

        super.updateScreen();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 343, 256, 372.0F, 400.0F, false);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 217), (float)(this.posY + 259), 244, 373, 125, 26, 372.0F, 400.0F, false);
        ClientEventHandler.STYLE.bindTexture("auction_house");
        int textWidth = (int)((double)this.fontRenderer.getStringWidth(I18n.getString("auctions.title")) * 1.2D);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.posX + 217 + 5), (float)(this.posY + 265), 6, 5, 25, 15, 372.0F, 400.0F, false);
        ModernGui.drawScaledString(I18n.getString("auctions.title"), this.posX + 288, this.posY + 269, 16777215, 1.2F, true, true);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        if (search != null)
        {
            search.drawTextBox();
        }

        String page = currentPage + "";
        this.drawCenteredString(this.fontRenderer, page, this.posX + 269 + 33, this.posY + 238, 16777215);
        String money = (int)ShopGUI.CURRENT_MONEY + " $";
        this.fontRenderer.drawString(money, this.posX + 312 - this.fontRenderer.getStringWidth(money), this.posY + 10, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.posX + 28), (float)(this.posY + 3), 0.0F);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.fontRenderer.drawStringWithShadow(I18n.getString("hdv.title"), 0, 0, 16777215);
        GL11.glPopMatrix();
        int offset;
        int i;
        byte add;
        Iterator var11;
        MarketSale marketSale;
        ItemStack itemStack;
        int y;
        int var20;

        if (this.marketSales != null && this.filteredMarket != null && !this.filteredMarket.isEmpty())
        {
            GUIUtils.startGLScissor(this.posX + 6, this.posY + 79, 317, 149);
            GL11.glPushMatrix();
            var20 = this.selected == null ? 0 : 67;
            offset = this.currentSale.size() <= 7 && (this.selected == null || this.currentSale.size() <= 4) ? 0 : (int)((float)((this.currentSale.size() - 7) * 20 + var20) * -this.guiScrollBar.getSliderValue());
            GL11.glTranslatef((float)(this.posX + 6), (float)(this.posY + 79 + offset), 0.0F);
            i = 0;
            add = 0;

            for (var11 = this.currentSale.iterator(); var11.hasNext(); ++i)
            {
                marketSale = (MarketSale)var11.next();
                itemStack = marketSale.getItemStack();

                if (itemStack != null)
                {
                    itemStack.stackSize = 1;
                    y = i * 20 + add;
                    int price = marketSale.getPrice();
                    String priceString;

                    if (marketSale.getExpiry() > System.currentTimeMillis())
                    {
                        priceString = (price < 100000 ? Integer.valueOf(price) : String.format("%.0f", new Object[] {Double.valueOf((double)price / 1000.0D)}) + "k") + "\u00a72$";
                    }
                    else if (marketSale.getSuperexpiry() > System.currentTimeMillis())
                    {
                        priceString = I18n.getString("hdv.expired");
                    }
                    else
                    {
                        priceString = I18n.getString("hdv.superexpired");
                    }

                    this.mc.getTextureManager().bindTexture(BACKGROUND);

                    if (!marketSale.equals(this.selected))
                    {
                        if (par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset && this.cursorOnMarket(par1, par2))
                        {
                            GL11.glColor3f(0.7F, 0.7F, 0.7F);
                        }

                        ModernGui.drawModalRectWithCustomSizedTexture(0.0F, (float)y, 1, 262, 317, 18, 372.0F, 400.0F, false);
                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        this.fontRenderer.drawString(itemStack.getDisplayName().replaceAll("^\\\u00a7[0-9a-z]", ""), 22, y + 5, -6381922);
                        this.fontRenderer.drawString(priceString, 293 - this.fontRenderer.getStringWidth(priceString) / 2, y + 5, -1);
                        this.drawDeleteButton(marketSale, y, par1, par2);
                        RenderHelper.enableGUIStandardItemLighting();
                        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                        this.renderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemStack, 1, y + 1);
                        this.renderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemStack, 1, y + 1, "\u00a77" + Integer.toString(marketSale.getQuantity() - marketSale.getSold()));
                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                        RenderHelper.disableStandardItemLighting();
                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    }
                    else
                    {
                        add = 67;
                        ModernGui.drawModalRectWithCustomSizedTexture(0.0F, (float)y, 1, 283, 317, 85, 372.0F, 400.0F, false);
                        this.fontRenderer.drawString(priceString, 293 - this.fontRenderer.getStringWidth(priceString) / 2, y + 5, -1);
                        String itemName = itemStack.getDisplayName().replaceAll("^\\\u00a7[0-9a-z]", "");

                        if (itemName.length() > (itemName.contains("\u00a7l") ? 15 : 25))
                        {
                            itemName = itemName.substring(0, itemName.contains("\u00a7l") ? 14 : 24) + "...";
                        }

                        this.fontRenderer.drawString("\u00a7l" + itemName, 65, y + 5, -6381922);
                        this.fontRenderer.drawString(I18n.getStringParams("hdv.seller", new Object[] {marketSale.getPseudo()}), 65, y + 20, -1);
                        String size;

                        if (this.numberSelector != null)
                        {
                            size = I18n.getStringParams("hdv.totalprice", new Object[] {Integer.valueOf(marketSale.getPrice() * this.numberSelector.getNumber())});
                            this.fontRenderer.drawString(size, 65, y + 50, -1);
                        }

                        String output;

                        if (marketSale.getExpiry() > System.currentTimeMillis())
                        {
                            size = I18n.getString("hdv.expiry.time");
                            this.fontRenderer.drawString(size, 314 - this.fontRenderer.getStringWidth(size), y + 40, -1);
                            output = this.getCountdownString(marketSale.getExpiry());
                            this.fontRenderer.drawString(output, 314 - this.fontRenderer.getStringWidth(output), y + 50, -1);
                        }
                        else if (marketSale.getPseudo().equals(this.mc.thePlayer.username) && marketSale.getSuperexpiry() > System.currentTimeMillis())
                        {
                            size = I18n.getString("hdv.superexpiry.time");
                            this.fontRenderer.drawString(size, 314 - this.fontRenderer.getStringWidth(size), y + 40, -1);
                            output = this.getCountdownString(marketSale.getSuperexpiry());
                            this.fontRenderer.drawString(output, 314 - this.fontRenderer.getStringWidth(output), y + 50, -1);
                        }

                        this.drawDeleteButton(marketSale, y, par1, par2);
                        RenderHelper.enableGUIStandardItemLighting();
                        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                        GL11.glPushMatrix();
                        float var21 = 3.0F;
                        GL11.glTranslatef(30.0F - 8.0F * var21 + 1.0F, (float)(y + 42) - 8.0F * var21, 0.0F);
                        GL11.glScalef(var21, var21, var21);
                        this.renderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemStack, 0, 0);
                        this.renderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemStack, 0, 0, "\u00a77" + Integer.toString(marketSale.getQuantity() - marketSale.getSold()));
                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                        RenderHelper.disableStandardItemLighting();
                    }
                }
            }

            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, (float)offset, 0.0F);

            if (this.guiButton != null)
            {
                this.guiButton.drawButton(this.mc, par1, par2 - offset);
            }

            if (this.numberSelector != null)
            {
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                this.numberSelector.draw(par1, par2 - offset);
            }

            if (this.statsButton != null)
            {
                this.statsButton.drawButton(this.mc, par1, par2 - offset);
            }

            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
            GUIUtils.endGLScissor();
        }
        else
        {
            String a = I18n.getString("hdv.unavailable");
            this.drawCenteredString(this.fontRenderer, a, this.posX + 170, this.posY + 75, 16777215);
        }

        if (this.guiScrollBar != null)
        {
            this.guiScrollBar.draw(par1, par2);
        }

        if (this.filterSelector != null)
        {
            this.filterSelector.draw(par1, par2);
        }

        if (this.marketSales != null && this.filteredMarket != null && !this.filteredMarket.isEmpty() && par1 >= this.posX + 9 && par1 <= this.posX + 9 + 317 && par2 >= this.posY + 79 && par2 <= this.posY + 79 + 149)
        {
            var20 = this.selected == null ? 0 : 67;
            offset = this.currentSale.size() <= 7 && (this.selected == null || this.currentSale.size() <= 4) ? 0 : (int)((float)((this.currentSale.size() - 7) * 20 + var20) * -this.guiScrollBar.getSliderValue());
            i = 0;
            add = 0;

            for (var11 = this.currentSale.iterator(); var11.hasNext(); ++i)
            {
                marketSale = (MarketSale)var11.next();
                itemStack = marketSale.getItemStack();

                if (itemStack != null)
                {
                    itemStack.stackSize = 1;
                    y = i * 20 + add;

                    if (this.selected != null && this.selected.equals(marketSale))
                    {
                        add = 67;
                    }

                    if (par1 >= this.posX + 7 && par2 >= this.posY + 79 + offset + y + 1)
                    {
                        if (marketSale.equals(this.selected))
                        {
                            if (par1 > this.posX + 7 + 60 || par2 > this.posY + 79 + offset + y + 84)
                            {
                                continue;
                            }
                        }
                        else if (par1 > this.posX + 7 + 16 || par2 > this.posY + 79 + offset + y + 17)
                        {
                            continue;
                        }

                        NationsGUIClientHooks.drawItemStackTooltip(itemStack, par1, par2);
                        GL11.glDisable(GL11.GL_LIGHTING);
                    }
                }
            }
        }

        super.drawScreen(par1, par2, par3);
    }

    private String getCountdownString(long time)
    {
        int SECONDS_IN_A_DAY = 86400;
        long diff = time - System.currentTimeMillis();
        long diffSec = diff / 1000L;
        long secondsDay = diffSec % (long)SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60L;
        long minutes = secondsDay / 60L % 60L;
        long hours = diffSec / 3600L;
        String output = "";

        if (hours > 0L)
        {
            output = output + hours + "h ";
        }

        if (minutes > 0L)
        {
            output = output + minutes + "m ";
        }

        output = output + seconds + "s";
        return output;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (search.textboxKeyTyped(par1, par2))
        {
            this.resetSelection();
            currentPage = 1;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
        }

        if (this.numberSelector != null)
        {
            this.numberSelector.keyTyped(par1, par2);
        }

        super.keyTyped(par1, par2);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        search.mouseClicked(par1, par2, par3);
        byte add = 0;
        int i = 0;
        int a = this.selected == null ? 0 : 67;
        int offset = this.currentSale.size() <= 7 && (this.selected == null || this.currentSale.size() <= 4) ? 0 : (int)((float)((this.currentSale.size() - 7) * 20 + a) * -this.guiScrollBar.getSliderValue());

        if (this.statsButton != null && this.statsButton.mousePressed(this.mc, par1, par2 - offset))
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ItemStackMarketStatsPacket(this.selected.getUuid())));
        }

        if (this.numberSelector != null)
        {
            this.numberSelector.mouseClicked(par1, par2 - offset, par3);
        }

        if (this.filterSelector != null && this.filterSelector.mousePressed(par1, par2))
        {
            this.resetSelection();
        }

        if (this.guiButton != null && this.guiButton.mousePressed(this.mc, par1, par2 - offset))
        {
            if (!this.selected.getPseudo().equals(Minecraft.getMinecraft().thePlayer.username) && !canRemoveAll)
            {
                if (this.numberSelector != null && par1 > this.posX + 3 && par1 < this.posX + 336 && par2 > this.posY + 79 && par2 < this.posY + 228)
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new BuyMarketPacket(this.selected.getUuid(), this.numberSelector.getNumber())));
                    PacketCallbacks.MONEY.send(new String[0]);

                    if (this.numberSelector.getNumber() == this.selected.getQuantity() - this.selected.getSold())
                    {
                        this.removeSale(this.selected);
                        this.resetSelection();
                    }

                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                    lastBuy = System.currentTimeMillis();
                }
            }
            else if (this.canRemove(this.selected))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoveSalePacket(this.selected.getUuid(), false)));
                this.removeSale(this.selected);
                this.resetSelection();
            }
            else
            {
                this.mc.displayGuiScreen(new RemoveConfirmGui(this, this.selected));
            }
        }

        if (this.cursorOnMarket(par1, par2))
        {
            for (Iterator var8 = this.currentSale.iterator(); var8.hasNext(); ++i)
            {
                MarketSale marketSale = (MarketSale)var8.next();
                int y = i * 20 + add;

                if (marketSale.equals(this.selected))
                {
                    add = 67;
                }

                if (par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset)
                {
                    if ((marketSale.getPseudo().equals(Minecraft.getMinecraft().thePlayer.username) || canRemoveAll) && par1 >= this.posX + 6 + 242 && par1 <= this.posX + 6 + 242 + 19 && par2 >= this.posY + 79 + y + offset + 1 && par2 <= this.posY + 79 + y + offset + 20)
                    {
                        if (this.canRemove(marketSale))
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoveSalePacket(marketSale.getUuid(), false)));

                            if (marketSale == this.selected)
                            {
                                this.resetSelection();
                            }

                            System.out.println("remove3");
                            this.removeSale(marketSale);
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                        }
                        else
                        {
                            this.mc.displayGuiScreen(new RemoveConfirmGui(this, marketSale));
                        }
                    }
                    else if (this.selected == null || !this.selected.equals(marketSale))
                    {
                        this.selected = marketSale;
                        this.initGui();
                    }

                    break;
                }
            }
        }

        if (par1 > this.posX + 217 && par1 < this.posX + 217 + 125 && par2 > this.posY + 259 && par2 < this.posY + 259 + 26)
        {
            this.mc.displayGuiScreen(new AuctionGui());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket("", "", 0, false)));
        }

        super.mouseClicked(par1, par2, par3);
    }

    public void setMarketSales(List<MarketSale> marketSales)
    {
        this.marketSales = marketSales;
        this.currentSale = this.getCurrentList();
    }

    public List<MarketSale> getMarketSales()
    {
        return this.marketSales;
    }

    public void resetSelection()
    {
        this.selected = null;
        this.guiButton = null;
        this.numberSelector = null;
        this.statsButton = null;
    }

    public MarketSale getSelected()
    {
        return this.selected;
    }

    private void drawDeleteButton(MarketSale marketSale, int y, int mouseX, int mouseY)
    {
        if ((marketSale.getPseudo().equals(Minecraft.getMinecraft().thePlayer.username) || canRemoveAll) && marketSale.getSuperexpiry() > System.currentTimeMillis())
        {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND);
            ModernGui.drawModalRectWithCustomSizedTexture(242.0F, (float)(y + 1), 16, 373, 19, 19, 372.0F, 400.0F, false);
        }
    }

    private boolean canRemove(MarketSale marketSale)
    {
        if (marketSale.getSuperexpiry() < System.currentTimeMillis())
        {
            return false;
        }
        else
        {
            int withdrawAmount = marketSale.getQuantity() - marketSale.getSold();

            if (withdrawAmount <= 0)
            {
                return true;
            }
            else
            {
                ItemStack[] var3 = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
                int var4 = var3.length;

                for (int var5 = 0; var5 < var4; ++var5)
                {
                    ItemStack itemStack = var3[var5];

                    if (itemStack != null && itemStack.isItemEqual(marketSale.getItemStack()) && ItemStack.areItemStackTagsEqual(itemStack, marketSale.getItemStack()))
                    {
                        int o = itemStack.getMaxStackSize() - itemStack.stackSize;
                        withdrawAmount -= o;

                        if (withdrawAmount <= 0)
                        {
                            return true;
                        }
                    }
                    else if (itemStack == null)
                    {
                        if (withdrawAmount <= marketSale.getItemStack().getMaxStackSize())
                        {
                            return true;
                        }

                        withdrawAmount -= marketSale.getItemStack().getMaxStackSize();
                    }
                }

                return false;
            }
        }
    }

    public void updateList()
    {
        this.currentSale = this.getCurrentList();
    }

    private List<MarketSale> getCurrentList()
    {
        this.filteredMarket = new ArrayList();

        if (this.marketSales != null)
        {
            Iterator var1 = this.marketSales.iterator();

            while (var1.hasNext())
            {
                MarketSale marketSale = (MarketSale)var1.next();

                if (marketSale.getSuperexpiry() > System.currentTimeMillis())
                {
                    if (marketSale.getPseudo().equals(this.mc.thePlayer.username) && mySalesOnly)
                    {
                        this.filteredMarket.add(marketSale);
                    }
                    else if (!marketSale.getPseudo().equals(this.mc.thePlayer.username) && !mySalesOnly)
                    {
                        this.filteredMarket.add(marketSale);
                    }
                }
            }
        }

        return (List)(this.filteredMarket.size() > 0 ? this.filteredMarket.subList(10 * (currentPage - 1), this.filteredMarket.size() / 10 + 1 == currentPage ? this.filteredMarket.size() : 10 * currentPage) : new ArrayList());
    }

    private List<MarketSale> searchFilter(List<MarketSale> target)
    {
        Object result = new ArrayList();

        if (search != null && !search.getText().equals(""))
        {
            Iterator var3 = target.iterator();

            while (var3.hasNext())
            {
                MarketSale marketSale = (MarketSale)var3.next();

                if (search.getText().startsWith("@"))
                {
                    String id1 = search.getText().substring(1, search.getText().length());

                    if (StringUtils.containsIgnoreCase(marketSale.getPseudo(), id1))
                    {
                        ((List)result).add(marketSale);
                    }
                }
                else
                {
                    int id = -1;

                    try
                    {
                        id = Integer.parseInt(search.getText());
                    }
                    catch (NumberFormatException var7)
                    {
                        ;
                    }

                    if (marketSale != null && marketSale.getItemStack() != null && (StringUtils.containsIgnoreCase(marketSale.getItemStack().getDisplayName(), search.getText()) || id != -1 && id == marketSale.getItemStack().getItem().itemID))
                    {
                        ((List)result).add(marketSale);
                    }
                }
            }
        }
        else
        {
            result = target;
        }

        return (List)result;
    }

    private boolean cursorOnMarket(int mouseX, int mouseY)
    {
        return mouseX >= this.posX + 6 && mouseX <= this.posX + 6 + 317 && mouseY >= this.posY + 79 && mouseY <= this.posY + 79 + 149;
    }

    public void removeSale(MarketSale marketSale)
    {
        if (marketSale.equals(this.selected))
        {
            this.resetSelection();
        }

        this.currentSale.remove(marketSale);
        this.filteredMarket.remove(marketSale);
        this.marketSales.remove(marketSale);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 0:
                if (currentPage - 1 >= 1)
                {
                    --currentPage;
                    this.resetSelection();
                    this.guiScrollBar.setSliderValue(0.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                }

                break;

            case 1:
                if (currentPage + 1 <= this.filteredMarket.size() / 10 + 1)
                {
                    ++currentPage;
                    this.resetSelection();
                    this.guiScrollBar.setSliderValue(0.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                }

                break;

            case 2:
                mySalesOnly = !mySalesOnly;
                currentPage = 1;
                this.resetSelection();
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.getText(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
        }
    }

    static Minecraft access$000(MarketGui x0)
    {
        return x0.mc;
    }

    static
    {
        filters.put(I18n.getString("hdv.filter.price.increasing"), new MarketGui$1());
        filters.put(I18n.getString("hdv.filter.price.declining"), new MarketGui$2());
        filters.put(I18n.getString("hdv.filter.quantity.increasing"), new MarketGui$3());
        filters.put(I18n.getString("hdv.filter.quantity.declining"), new MarketGui$4());
        filters.put(I18n.getString("hdv.filter.date.increasing"), new MarketGui$5());
        filters.put(I18n.getString("hdv.filter.date.declining"), new MarketGui$6());
    }
}
