package net.ilexiconn.nationsgui.forge.client.gui.url;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.URLSavePacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class URLGUI extends GuiScreen
{
    public static String savedClientURL = "";
    public static Long savedClientTime = Long.valueOf(0L);
    private TileEntity blockEntity;
    private GuiTextField urlField;

    public URLGUI(TileEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 200;
        int y = this.height / 2 - 9;
        this.urlField = new GuiTextField(this.fontRenderer, x, y, 400, 18);
        this.urlField.setMaxStringLength(2000);

        if (this.blockEntity instanceof URLBlockEntity)
        {
            this.urlField.setText(((URLBlockEntity)this.blockEntity).url);
        }
        else if (this.blockEntity instanceof GenericGeckoTileEntity)
        {
            this.urlField.setText(((GenericGeckoTileEntity)this.blockEntity).url);
        }

        this.urlField.setCursorPositionEnd();
        this.urlField.setFocused(true);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.urlField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.urlField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char character, int key)
    {
        if (!this.urlField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.urlField.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        String actualUrl = "";

        if (this.blockEntity instanceof URLBlockEntity)
        {
            actualUrl = ((URLBlockEntity)this.blockEntity).url;
        }
        else if (this.blockEntity instanceof GenericGeckoTileEntity)
        {
            actualUrl = ((GenericGeckoTileEntity)this.blockEntity).url;
        }

        if (!actualUrl.equals(this.urlField.getText()))
        {
            if (this.blockEntity instanceof URLBlockEntity)
            {
                ((URLBlockEntity)this.blockEntity).url = this.urlField.getText();
            }
            else if (this.blockEntity instanceof GenericGeckoTileEntity)
            {
                ((GenericGeckoTileEntity)this.blockEntity).url = this.urlField.getText();
            }

            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new URLSavePacket(this.blockEntity)));
            savedClientURL = this.urlField.getText();
            savedClientTime = Long.valueOf(System.currentTimeMillis());
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
