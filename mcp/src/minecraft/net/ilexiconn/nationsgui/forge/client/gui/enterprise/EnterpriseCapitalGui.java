package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankCapitalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseCapitalGui extends ModalGui
{
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField capitalInput;

    public EnterpriseCapitalGui(GuiScreen guiFrom)
    {
        super(guiFrom);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.capitalInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 184, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.valid"));

        if (!EnterpriseGui.hasPermission("capital"))
        {
            this.validButton.enabled = false;
        }

        this.capitalInput = new GuiTextField(this.fontRenderer, this.guiLeft + 57, this.guiTop + 68, 97, 10);
        this.capitalInput.setEnableBackgroundDrawing(false);
        this.capitalInput.setMaxStringLength(7);
        this.capitalInput.setText("0");
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.capital.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.capital.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.capital.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 242, 122, 20, 512.0F, 512.0F, false);
        this.drawScaledString("\u00a7a$", this.guiLeft + 167, this.guiTop + 68, 16777215, 1.3F, true, false);
        this.capitalInput.drawTextBox();
        int tax = 0;

        if (this.isNumeric(this.capitalInput.getText()))
        {
            if (EnterpriseGui.enterpriseInfos.get("type").equals("loan"))
            {
                tax = Integer.parseInt(this.capitalInput.getText()) * 0;
            }
            else
            {
                tax = (int)((double)Integer.parseInt(this.capitalInput.getText()) * 0.1D);
            }
        }

        this.drawScaledString("Taxe: \u00a74" + tax + "$", this.guiLeft + 180, this.guiTop + 68, 0, 1.0F, false, false);
        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.validButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.capitalInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }

            if (this.validButton.enabled && this.isNumeric(this.capitalInput.getText()) && EnterpriseGui.hasPermission("capital") && mouseX > this.guiLeft + 184 && mouseX < this.guiLeft + 184 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankCapitalPacket((String)EnterpriseGui.enterpriseInfos.get("name"), Integer.valueOf(Integer.parseInt(this.capitalInput.getText())))));
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }
        }

        this.capitalInput.mouseClicked(mouseX, mouseY, mouseButton);
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
