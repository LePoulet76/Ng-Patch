package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class BankActionGui extends ModalGui
{
    private GuiButton depositButton;
    private GuiButton takeButton;
    private GuiTextField amountInput;
    private EntityPlayer entityPlayer;

    public BankActionGui(EntityPlayer entityPlayer, GuiScreen guiFrom)
    {
        super(guiFrom);
        this.entityPlayer = entityPlayer;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.amountInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.depositButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("faction.bank.modal.action.deposit"));
        this.takeButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.getString("faction.bank.modal.action.take"));

        if (!((Boolean)BankGUI_OLD.factionBankInfos.get("playerIsMember")).booleanValue())
        {
            this.takeButton.enabled = false;
        }

        this.amountInput = new GuiTextField(this.fontRenderer, this.guiLeft + 56, this.guiTop + 68, 247, 10);
        this.amountInput.setEnableBackgroundDrawing(false);
        this.amountInput.setMaxStringLength(8);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("faction.bank.modal.action.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("faction.bank.modal.action.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("faction.bank.modal.action.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 158, 249, 20, 512.0F, 512.0F, false);
        this.amountInput.drawTextBox();
        this.depositButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.takeButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.amountInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.amountInput.getText().isEmpty() && this.isNumeric(this.amountInput.getText()) && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGui_OLD.factionInfos.get("name"), this.amountInput.getText(), "deposit")));
                Minecraft.getMinecraft().displayGuiScreen(new BankGUI_OLD(this.entityPlayer, false));
            }

            if (!this.amountInput.getText().isEmpty() && this.isNumeric(this.amountInput.getText()) && ((Boolean)BankGUI_OLD.factionBankInfos.get("playerIsMember")).booleanValue() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGui_OLD.factionInfos.get("name"), this.amountInput.getText(), "take")));
                Minecraft.getMinecraft().displayGuiScreen(new BankGUI_OLD(this.entityPlayer, false));
            }
        }

        this.amountInput.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isNumeric(String str)
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

            if (Integer.parseInt(str) <= 0)
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
}
