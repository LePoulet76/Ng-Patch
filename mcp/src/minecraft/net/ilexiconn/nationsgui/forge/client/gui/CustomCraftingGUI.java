package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI$BookButton;
import net.ilexiconn.nationsgui.forge.server.container.CustomWorkbenchContainer;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CustomCraftingGUI extends GuiContainer
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");
    public ItemStack[] selectedRecipe = new ItemStack[10];

    public CustomCraftingGUI(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        super(new CustomWorkbenchContainer(par1InventoryPlayer, par2World, par3, par4, par5));
        this.xSize = 380;
        this.ySize = 150;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new CloseButtonGUI(0, this.width / 2 + 165, this.height / 2 - 61 + 14));
        this.buttonList.add(new RecipeListGUI$BookButton(1, this.width / 2 - 133, this.height / 2 + 8));
        this.buttonList.add(new TexturedButtonGUI(2, this.width / 2 - 40, this.height / 2 - 9, 19, 19, "mail_open", 182, 72, ""));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);

        switch (par1GuiButton.id)
        {
            case 0:
                this.mc.displayGuiScreen((GuiScreen)null);
                break;

            case 1:
                this.selectedRecipe = new ItemStack[10];
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
                break;

            case 2:
                this.selectedRecipe = new ItemStack[10];
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(this.width / 2 - 144 - 5, this.height / 2 - 61, 0, 0, 144, 123);
        this.drawTexturedModalRect(this.width / 2 + 5, this.height / 2 - 61, 0, 123, 182, 128);
        int posY = this.height / 2 - 61 + 14;
        this.fontRenderer.drawString(I18n.getString("container.crafting"), this.width / 2 - 144 - 5 + 35, posY + 4, 16777215);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), this.width / 2 + 5 + 30, posY, 16777215);
        this.displayRecipe();
    }

    private void displayRecipe()
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
        itemRenderer.renderWithColor = false;
        boolean recipeDisplayed = false;

        for (int itemStack = 0; itemStack < 3; ++itemStack)
        {
            for (int x = 0; x < 3; ++x)
            {
                ItemStack itemStack1 = this.selectedRecipe[x + 3 * itemStack];

                if (itemStack1 != null)
                {
                    recipeDisplayed = true;
                    itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemStack1, this.width / 2 - 144 - 5 + 48 + x * 18, this.height / 2 - 61 + 53 + itemStack * 18);
                }
            }
        }

        ((GuiButton)this.buttonList.get(2)).drawButton = recipeDisplayed;
        ItemStack var5 = this.selectedRecipe[9];

        if (var5 != null)
        {
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var5, this.width / 2 - 144 - 5 + 111, this.height / 2 - 61 + 89);
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        itemRenderer.renderWithColor = true;
    }
}
