package net.ilexiconn.nationsgui.forge.client.gui.gecko;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GeckoEntityDialogSavePacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GeckoEntityGUI extends GuiScreen
{
    private GenericGeckoEntity geckoEntity;
    private GuiTextField dialogInteractionField;
    private GuiTextField dialogWalkByField;
    private GuiTextField radiusWalkByField;

    public GeckoEntityGUI(GenericGeckoEntity geckoEntity)
    {
        this.geckoEntity = geckoEntity;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 70;
        int y = this.height / 2 - 9;
        this.dialogInteractionField = new GuiTextField(this.fontRenderer, x, y, 140, 18);
        this.dialogInteractionField.setMaxStringLength(200);
        this.dialogInteractionField.setText(this.geckoEntity.getDialogInteraction());
        this.dialogInteractionField.setCursorPositionEnd();
        this.dialogInteractionField.setFocused(true);
        this.dialogWalkByField = new GuiTextField(this.fontRenderer, x, y + 22, 140, 18);
        this.dialogWalkByField.setMaxStringLength(200);
        this.dialogWalkByField.setText(this.geckoEntity.getDialogWalkBy());
        this.dialogWalkByField.setCursorPositionEnd();
        this.dialogWalkByField.setFocused(false);
        this.radiusWalkByField = new GuiTextField(this.fontRenderer, x, y + 44, 140, 18);
        this.radiusWalkByField.setMaxStringLength(2);
        this.radiusWalkByField.setText(this.geckoEntity.getRadiusWalkBy() + "");
        this.radiusWalkByField.setCursorPositionEnd();
        this.radiusWalkByField.setFocused(false);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ModernGui.drawScaledString("Dialog interaction", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Dialog interaction") - 5, this.height / 2 - 9 + 4, 16777215, 1.0F, false, true);
        this.dialogInteractionField.drawTextBox();
        ModernGui.drawScaledString("Dialog walk by", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Dialog walk by") - 5, this.height / 2 - 9 + 4 + 22, 16777215, 1.0F, false, true);
        this.dialogWalkByField.drawTextBox();
        ModernGui.drawScaledString("Radius walk by", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Radius walk by") - 5, this.height / 2 - 9 + 4 + 44, 16777215, 1.0F, false, true);
        this.radiusWalkByField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.dialogInteractionField.updateCursorCounter();
        this.dialogWalkByField.updateCursorCounter();
        this.radiusWalkByField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char character, int key)
    {
        if (!this.dialogInteractionField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }

        if (!this.dialogWalkByField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }

        if (!this.radiusWalkByField.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.dialogInteractionField.mouseClicked(mouseX, mouseY, button);
        this.dialogWalkByField.mouseClicked(mouseX, mouseY, button);
        this.radiusWalkByField.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        if (!this.geckoEntity.getDialogInteraction().equals(this.dialogInteractionField.getText()) || !this.geckoEntity.getDialogWalkBy().equals(this.dialogWalkByField.getText()) || !this.geckoEntity.getDialogWalkBy().equals(this.radiusWalkByField.getText()))
        {
            this.geckoEntity.dialogInteraction = this.dialogInteractionField.getText();
            this.geckoEntity.dialogWalkBy = this.dialogWalkByField.getText();
            this.geckoEntity.radiusWalkBy = Integer.parseInt(this.radiusWalkByField.getText());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GeckoEntityDialogSavePacket(this.geckoEntity)));
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
