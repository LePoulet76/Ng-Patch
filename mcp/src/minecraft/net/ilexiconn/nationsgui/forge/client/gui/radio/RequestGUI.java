package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class RequestGUI extends GuiScreen
{
    private RadioGUI parent;
    private GuiTextField fieldRequest;
    private GuiButton buttonSend;

    public RequestGUI(RadioGUI parent)
    {
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 50;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.buttonList.add(this.buttonSend = new GuiButton(1, x + 16, y + 72, StatCollector.translateToLocal("nationsgui.radio.send")));
        this.buttonSend.enabled = false;
        this.fieldRequest = new GuiTextField(this.fontRenderer, x + 77, y + 48, 139, 18);
        this.fieldRequest.setMaxStringLength(200);
        this.fieldRequest.setCursorPositionEnd();
        this.fieldRequest.setFocused(true);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 50;
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(RadioGUI.TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, 233, 43);

        for (int i = 0; i < 4; ++i)
        {
            this.drawTexturedModalRect(x, y + 43 + 13 * i, 0, 243, 228, 13);
        }

        this.drawTexturedModalRect(x, y + 95, 0, 160, 235, 5);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.request"), x + 16, y + 54, 7303023);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.request"), x + 16, y + 53, 16777215);
        this.fieldRequest.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parent);
        }
        else if (button.id == 1)
        {
            this.buttonSend.enabled = false;
            this.fieldRequest.setEnabled(false);
            PacketCallbacks.REQUEST_SONG.send(new String[] {this.fieldRequest.getText()});
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.fieldRequest.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char character, int key)
    {
        if (!this.fieldRequest.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }
        else
        {
            this.buttonSend.enabled = !this.fieldRequest.getText().isEmpty();
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.fieldRequest.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public static void handleReturn(boolean success)
    {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;

        if (screen instanceof RequestGUI)
        {
            RequestGUI request = (RequestGUI)screen;

            if (success)
            {
                request.mc.displayGuiScreen(request.parent);
            }
            else
            {
                request.buttonSend.enabled = true;
                request.fieldRequest.setEnabled(true);
            }
        }
    }
}
