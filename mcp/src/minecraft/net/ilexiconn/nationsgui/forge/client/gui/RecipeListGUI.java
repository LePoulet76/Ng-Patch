package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Collections;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI$DisplayButton;
import net.ilexiconn.nationsgui.forge.server.container.RecipeContainer;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class RecipeListGUI extends GuiContainer
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftbook.png");
    private GuiScrollBar scrollBar;
    private GuiTextField guiTextField;
    private int itemLines;
    private int skipedLines = 0;
    private GuiButton nextButton;
    private GuiButton previousButton;
    private RecipeListGUI$DisplayButton displayButton;
    private boolean displayAll = false;
    private GuiScreen prev;
    public static boolean achievementDone = false;

    public RecipeListGUI(GuiScreen prev)
    {
        super(new RecipeContainer());
        this.xSize = 400;
        this.ySize = 158;
        ((RecipeContainer)this.inventorySlots).generateItemList("", this.displayAll);
        this.itemLines = ((RecipeContainer)this.inventorySlots).displayResults(0);
        this.prev = prev;

        if (!achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_craftbook", 1)));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollBar = new GuiScrollBar((float)(this.width / 2 - 196 - 5 + 176), (float)(this.height / 2 - 80 + 46), 88);
        this.scrollBar.setScrollIncrement(1.0F / (float)this.itemLines);
        String currentText = "";

        if (this.guiTextField != null)
        {
            currentText = this.guiTextField.getText();
        }

        this.guiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 196 - 5 + 102, this.height / 2 - 80 + 142, 71, 12);
        this.guiTextField.setText(currentText);
        this.guiTextField.setEnableBackgroundDrawing(false);
        this.guiTextField.setFocused(true);
        this.nextButton = new GuiButton(0, this.width / 2 + 8 + 75, this.height / 2 - 39 + 5, 12, 20, ">");
        this.previousButton = new GuiButton(1, this.width / 2 + 8 + 75, this.height / 2 - 39 + 52, 12, 20, "<");
        this.displayButton = new RecipeListGUI$DisplayButton(this, 2, this.width / 2 - 196 - 5 + 84, this.height / 2 - 80 + 139);
        this.buttonList.add(this.nextButton);
        this.buttonList.add(this.previousButton);
        this.buttonList.add(this.displayButton);
        this.buttonList.add(new CloseButtonGUI(3, this.width / 2 + 8 + 122, this.height / 2 - 39 + 7));
    }

    public GuiScreen getPrev()
    {
        return this.prev;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();

        if (this.itemLines > 5)
        {
            int skip = (int)((float)(this.itemLines - 5) * this.scrollBar.getSliderValue());

            if (skip != this.skipedLines)
            {
                ((RecipeContainer)this.inventorySlots).displayResults(skip);
                this.skipedLines = skip;
            }
        }

        this.guiTextField.updateCursorCounter();
        this.previousButton.drawButton = this.nextButton.drawButton = ((RecipeContainer)this.inventorySlots).hasMutipleResults();
        ((RecipeContainer)this.inventorySlots).updateItemStackAnimation();
    }

    protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
    {
        if (par1Slot != null)
        {
            par2 = par1Slot.slotNumber;
        }

        this.mc.thePlayer.openContainer.slotClick(par2, par3, par4, this.mc.thePlayer);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        if (this.displayButton.hovered)
        {
            this.drawHoveringText(Collections.singletonList(I18n.getString("display.allitem")), par1, par2, this.fontRenderer);
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(this.width / 2 - 196 - 5, this.height / 2 - 80, 0, 0, 196, 160);
        this.drawTexturedModalRect(this.width / 2 + 8, this.height / 2 - 39, 0, 160, 140, 79);
        this.scrollBar.draw(i, j);
        this.guiTextField.drawTextBox();
        this.fontRenderer.drawString(I18n.getString("craftbook.name"), this.width / 2 - 196 - 5 + 33, this.height / 2 - 80 + 14, 16777215);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);

        if (par1GuiButton.equals(this.previousButton))
        {
            ((RecipeContainer)this.inventorySlots).previous();
        }
        else if (par1GuiButton.equals(this.nextButton))
        {
            ((RecipeContainer)this.inventorySlots).next();
        }
        else if (par1GuiButton.equals(this.displayButton))
        {
            this.displayAll = this.displayButton.en = !this.displayButton.en;
            this.skipedLines = 0;
            this.scrollBar.reset();
            ((RecipeContainer)this.inventorySlots).generateItemList(this.guiTextField.getText(), this.displayAll);
            this.itemLines = ((RecipeContainer)this.inventorySlots).displayResults(0);
            this.scrollBar.setScrollIncrement(1.0F / (float)this.itemLines);
        }
        else if (par1GuiButton.id == 3)
        {
            this.mc.displayGuiScreen(this.prev);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.guiTextField.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    public void keyTyped(char character, int key)
    {
        if (!this.guiTextField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }
        else
        {
            this.skipedLines = 0;
            this.scrollBar.reset();
            ((RecipeContainer)this.inventorySlots).generateItemList(this.guiTextField.getText(), this.displayAll);
            this.itemLines = ((RecipeContainer)this.inventorySlots).displayResults(0);
            this.scrollBar.setScrollIncrement(1.0F / (float)this.itemLines);
        }
    }

    static ResourceLocation access$000()
    {
        return BACKGROUND;
    }

    static Minecraft access$100(RecipeListGUI x0)
    {
        return x0.mc;
    }
}
