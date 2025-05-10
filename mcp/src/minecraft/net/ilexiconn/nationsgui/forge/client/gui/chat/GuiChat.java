package net.ilexiconn.nationsgui.forge.client.gui.chat;

import acs.tabbychat.GuiChatTC;
import java.net.URI;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiChat extends GuiScreen
{
    protected GuiTextField inputField;
    protected GuiChatTC wrapper;
    public String defaultInputFieldText = "";

    public GuiChat() {}

    public GuiChat(String par1Str)
    {
        this.defaultInputFieldText = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.wrapper = new GuiChatTC();
        this.wrapper.defaultInputFieldText = this.defaultInputFieldText;
        this.wrapper.initGui();
        this.inputField = this.wrapper.inputField;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.wrapper.updateScreen();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.wrapper.keyTyped(par1, par2);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        if (this.wrapper != null)
        {
            this.wrapper.handleMouseInput();
        }

        super.handleMouseInput();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        this.wrapper.mouseClicked(par1, par2, par3);
    }

    public void confirmClicked(boolean par1, int par2)
    {
        this.wrapper.confirmClicked(par1, par2);
    }

    private void func_73896_a(URI par1URI)
    {
        this.wrapper.func_73896_a(par1URI);
    }

    public void completePlayerName()
    {
        this.wrapper.completePlayerName();
    }

    private void func_73893_a(String par1Str, String par2Str)
    {
        this.wrapper.func_73893_a(par1Str, par2Str);
    }

    public void getSentHistory(int par1)
    {
        this.wrapper.getSentHistory(par1);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.wrapper.drawScreen(par1, par2, par3);
    }

    public void func_73894_a(String[] par1ArrayOfStr)
    {
        this.wrapper.func_73894_a(par1ArrayOfStr);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
