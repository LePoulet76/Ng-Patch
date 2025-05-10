package net.ilexiconn.nationsgui.forge.client.gui.imagehologram;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ImageHologramSavePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ImageHologramGUI extends GuiScreen
{
    private ImageHologramBlockEntity blockEntity;
    private GuiTextField urlField;
    private GuiTextField imgWidth;
    private GuiTextField imgHeight;
    private GuiTextField size;

    public ImageHologramGUI(ImageHologramBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    public static boolean isNumeric(String str, boolean allowZero)
    {
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) == 0 && !allowZero)
            {
                return false;
            }
            else if (Integer.parseInt(str) < 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 70;
        int y = this.height / 2 - 9;
        this.urlField = new GuiTextField(this.fontRenderer, x, y, 140, 18);
        this.urlField.setMaxStringLength(200);
        this.urlField.setText(this.blockEntity.url);
        this.urlField.setCursorPositionEnd();
        this.urlField.setFocused(true);
        this.imgWidth = new GuiTextField(this.fontRenderer, x, y + 22, 140, 18);
        this.imgWidth.setMaxStringLength(4);
        this.imgWidth.setText(this.blockEntity.imgWidth + "");
        this.imgWidth.setCursorPositionEnd();
        this.imgWidth.setFocused(false);
        this.imgHeight = new GuiTextField(this.fontRenderer, x, y + 44, 140, 18);
        this.imgHeight.setMaxStringLength(4);
        this.imgHeight.setText(this.blockEntity.imgHeight + "");
        this.imgHeight.setCursorPositionEnd();
        this.imgHeight.setFocused(false);
        this.size = new GuiTextField(this.fontRenderer, x, y + 66, 140, 18);
        this.size.setMaxStringLength(4);
        this.size.setText(this.blockEntity.size + "");
        this.size.setCursorPositionEnd();
        this.size.setFocused(false);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ModernGui.drawScaledString("URL", this.width / 2 - 70 - this.fontRenderer.getStringWidth("URL") - 5, this.height / 2 - 9 + 4, 16777215, 1.0F, false, true);
        this.urlField.drawTextBox();
        ModernGui.drawScaledString("Image Width", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Image Width") - 5, this.height / 2 - 9 + 26, 16777215, 1.0F, false, true);
        this.imgWidth.drawTextBox();
        ModernGui.drawScaledString("Image Height", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Image Height") - 5, this.height / 2 - 9 + 48, 16777215, 1.0F, false, true);
        this.imgHeight.drawTextBox();
        ModernGui.drawScaledString("Size (%)", this.width / 2 - 70 - this.fontRenderer.getStringWidth("Size (%)") - 5, this.height / 2 - 9 + 70, 16777215, 1.0F, false, true);
        this.size.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.urlField.updateCursorCounter();
        this.imgWidth.updateCursorCounter();
        this.imgHeight.updateCursorCounter();
        this.size.updateCursorCounter();
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

        if (!this.imgWidth.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }

        if (!this.imgHeight.textboxKeyTyped(character, key))
        {
            super.keyTyped(character, key);
        }

        if (!this.size.textboxKeyTyped(character, key))
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
        this.imgWidth.mouseClicked(mouseX, mouseY, button);
        this.imgHeight.mouseClicked(mouseX, mouseY, button);
        this.size.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public ImageHologramBlockEntity getBlockEntity()
    {
        return this.blockEntity;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        if (this.urlField.getText().startsWith("https://static.nationsglory.fr/"))
        {
            this.blockEntity.url = this.urlField.getText();
        }
        else if (!this.urlField.getText().isEmpty())
        {
            Minecraft.getMinecraft().thePlayer.addChatMessage("\u00a7cThe URL must start with https://static.nationsglory.fr/");
        }

        if (!this.imgWidth.getText().isEmpty() && isNumeric(this.imgWidth.getText(), false))
        {
            this.blockEntity.imgWidth = Integer.parseInt(this.imgWidth.getText());
        }

        if (!this.imgHeight.getText().isEmpty() && isNumeric(this.imgHeight.getText(), false))
        {
            this.blockEntity.imgHeight = Integer.parseInt(this.imgHeight.getText());
        }

        if (!this.size.getText().isEmpty() && isNumeric(this.size.getText(), false))
        {
            this.blockEntity.size = Integer.parseInt(this.size.getText());
        }

        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ImageHologramSavePacket(this.blockEntity)));
    }
}
