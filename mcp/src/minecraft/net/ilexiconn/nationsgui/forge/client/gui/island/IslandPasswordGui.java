package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandPasswordPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class IslandPasswordGui extends ModalGui
{
    private GuiButton cancelButton;
    private GuiButton confirmButton;
    private GuiTextField passwordInput;
    private EntityPlayer entityPlayer;
    private String islandId;
    private String passwordTyped = "";
    private String passwordValue = "";
    private String serverNumber = "";
    public static boolean hasError = false;

    public IslandPasswordGui(EntityPlayer entityPlayer, GuiScreen guiFrom, String islandId, String passwordValue, String serverNumber)
    {
        super(guiFrom);
        this.entityPlayer = entityPlayer;
        this.islandId = islandId;
        this.passwordValue = passwordValue;
        this.serverNumber = serverNumber;
        hasError = false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.passwordInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("island.password.cancel"));
        this.confirmButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.getString("island.password.confirm"));
        this.passwordInput = new GuiTextField(this.fontRenderer, this.guiLeft + 56, this.guiTop + 68, 247, 10);
        this.passwordInput.setEnableBackgroundDrawing(false);
        this.passwordInput.setMaxStringLength(20);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("island.password.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);

        if (hasError)
        {
            this.drawScaledString(I18n.getString("island.password.error"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        }
        else
        {
            this.drawScaledString(I18n.getString("island.password.description_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("island.password.description_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        }

        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 158, 249, 20, 512.0F, 512.0F, false);
        this.passwordInput.drawTextBox();
        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.confirmButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.passwordInput.setText(this.passwordTyped);
        this.passwordInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
        this.passwordTyped = this.passwordInput.getText();
        this.passwordInput.setText(this.passwordInput.getText().replaceAll(".", "*"));
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen(this.guiFrom);
        }

        if (!this.passwordInput.getText().isEmpty() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            hasError = false;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandPasswordPacket(this.islandId, this.passwordTyped, this.passwordValue, this.serverNumber)));
        }

        this.passwordInput.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
