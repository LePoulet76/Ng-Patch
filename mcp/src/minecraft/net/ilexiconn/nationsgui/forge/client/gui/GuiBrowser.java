package net.ilexiconn.nationsgui.forge.client.gui;

import fr.nationsglory.ngbrowser.client.gui.IBrowserGuiScreen;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.gui.GuiScreen;

public class GuiBrowser extends GuiScreen implements IBrowserGuiScreen
{
    private fr.nationsglory.ngbrowser.client.gui.GuiBrowser browser;
    private final String url;
    private String hoveredAction = "";

    public GuiBrowser(String url)
    {
        this.url = url;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        if (this.browser == null)
        {
            this.browser = new fr.nationsglory.ngbrowser.client.gui.GuiBrowser(this.url, 0, 0, 0, 0);
        }

        this.browser.setSize(this.width, this.height);
        this.browser.setVolume(1.0F);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.hoveredAction = "";
        this.browser.draw();
        ClientEventHandler.STYLE.bindTexture("overlay_main");
        boolean hoveringClose = mouseX >= this.width - 25 && mouseX < this.width - 25 + 14 && mouseY >= 10 && mouseY < 24;
        ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 25), 10.0F, (float)(1658 * GenericOverride.GUI_SCALE), (float)((hoveringClose ? 215 : 153) * GenericOverride.GUI_SCALE), 52 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 14, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);

        if (hoveringClose)
        {
            this.hoveredAction = "close";
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.browser.update();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        this.browser.handleMouseInput();
    }

    /**
     * Handles keyboard input.
     */
    public void handleKeyboardInput()
    {
        super.handleKeyboardInput();
        this.browser.handleKeyboardInput();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        this.browser.close();
        super.onGuiClosed();
    }

    public fr.nationsglory.ngbrowser.client.gui.GuiBrowser getBrowser()
    {
        return this.browser;
    }

    public void closeRequestedByBrowser()
    {
        this.mc.displayGuiScreen((GuiScreen)null);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int x, int y, int button)
    {
        if (button == 0 && this.hoveredAction.equals("close"))
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }

        super.mouseClicked(x, y, button);
    }
}
