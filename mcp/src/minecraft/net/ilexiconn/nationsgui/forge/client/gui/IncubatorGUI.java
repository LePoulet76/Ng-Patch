package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.block.entity.IncubatorBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.IncubatorContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class IncubatorGUI extends GuiContainer
{
    private static final ResourceLocation INCUBATOR_GUI = new ResourceLocation("nationsgui", "textures/gui/incubator.png");
    private IncubatorBlockEntity blockEntity;

    public IncubatorGUI(IncubatorContainer container)
    {
        super(container);
        this.blockEntity = container.getBlockEntity();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String name = this.blockEntity.isInvNameLocalized() ? this.blockEntity.getInvName() : I18n.getString(this.blockEntity.getInvName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(INCUBATOR_GUI);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (this.blockEntity.isActive())
        {
            this.drawTexturedModalRect(x + 135, y + 22, 176, 0, 14, 14);
            this.drawTexturedModalRect(x + 52, y + 64, 176, 14, this.blockEntity.getProgress(), 5);
        }
    }
}
