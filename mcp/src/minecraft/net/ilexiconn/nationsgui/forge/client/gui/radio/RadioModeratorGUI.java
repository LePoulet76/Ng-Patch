package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.ToggleGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioModeratorGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioModeratorGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioModeratorGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.GearButtonGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetSourcePacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class RadioModeratorGUI extends GuiScreen
{
    private RadioGUI parent;
    private GuiTextField sourceField;

    public RadioModeratorGUI(RadioGUI parent)
    {
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 74;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.buttonList.add(new GearButtonGUI(1, x + 192, y + 13, true));
        this.buttonList.add(new ToggleGUI(x + 189, y + 73, new RadioModeratorGUI$1(this), this.parent.getBlockEntity().needsRedstone));
        this.buttonList.add(new ToggleGUI(x + 189, y + 96, new RadioModeratorGUI$2(this), this.parent.getBlockEntity().looping));
        this.sourceField = new GuiTextField(this.fontRenderer, x + 77, y + 48, 139, 18);
        this.sourceField.setMaxStringLength(200);
        this.sourceField.setText(this.parent.getBlockEntity().source);
        this.sourceField.setCursorPositionEnd();
        this.sourceField.setFocused(true);
        this.buttonList.add(new ToggleGUI(x + 189, y + 119, new RadioModeratorGUI$3(this), this.parent.getBlockEntity().canOpen));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 74;
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(RadioGUI.TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, 233, 43);

        for (int i = 0; i < 8; ++i)
        {
            this.drawTexturedModalRect(x, y + 43 + 13 * i, 0, 243, 228, 13);
        }

        this.drawTexturedModalRect(x, y + 144, 0, 160, 235, 5);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.source"), x + 16, y + 54, 7303023);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.source"), x + 16, y + 53, 16777215);
        this.sourceField.drawTextBox();
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.redstone"), x + 16, y + 77, 7303023);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.redstone"), x + 16, y + 76, 16777215);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.loop"), x + 16, y + 100, 7303023);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.loop"), x + 16, y + 99, 16777215);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.open"), x + 16, y + 123, 7303023);
        this.fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.radio.open"), x + 16, y + 122, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (button.id == 1)
        {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.sourceField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char character, int key)
    {
        if (!this.sourceField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.sourceField.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        if (!this.parent.getBlockEntity().source.equals(this.sourceField.getText()))
        {
            if (this.sourceField.getText().isEmpty())
            {
                this.sourceField.setText("https://radio.nationsglory.fr/listen/ngradio/ngradio");
            }

            this.parent.getBlockEntity().source = this.sourceField.getText();
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetSourcePacket(this.parent.getBlockEntity())));
        }
    }

    static RadioGUI access$000(RadioModeratorGUI x0)
    {
        return x0.parent;
    }
}
