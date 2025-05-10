package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankSalaryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseSalaryGui extends ModalGui
{
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField leaderInput;
    private GuiTextField cadreInput;
    private GuiTextField employeeInput;

    public EnterpriseSalaryGui(GuiScreen guiFrom)
    {
        super(guiFrom);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.leaderInput.updateCursorCounter();
        this.cadreInput.updateCursorCounter();
        this.employeeInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.cancelButton = new GuiButton(0, this.guiLeft + 48, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 190, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.valid"));

        if (!EnterpriseGui.hasPermission("salary"))
        {
            this.validButton.enabled = false;
        }

        this.leaderInput = new GuiTextField(this.fontRenderer, this.guiLeft + 70, this.guiTop + 72, 55, 10);
        this.leaderInput.setEnableBackgroundDrawing(false);
        this.leaderInput.setMaxStringLength(5);

        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_leader"))
        {
            this.leaderInput.setText(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_leader")).intValue() + "");
        }
        else
        {
            this.leaderInput.setText("0");
        }

        this.cadreInput = new GuiTextField(this.fontRenderer, this.guiLeft + 160, this.guiTop + 72, 55, 10);
        this.cadreInput.setEnableBackgroundDrawing(false);
        this.cadreInput.setMaxStringLength(5);

        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_cadre"))
        {
            this.cadreInput.setText(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_cadre")).intValue() + "");
        }
        else
        {
            this.cadreInput.setText("0");
        }

        this.employeeInput = new GuiTextField(this.fontRenderer, this.guiLeft + 250, this.guiTop + 72, 55, 10);
        this.employeeInput.setEnableBackgroundDrawing(false);
        this.employeeInput.setMaxStringLength(5);

        if (EnterpriseBankGUI.enterpriseBankInfos.containsKey("salary_employee"))
        {
            this.employeeInput.setText(((Double)EnterpriseBankGUI.enterpriseBankInfos.get("salary_employee")).intValue() + "");
        }
        else
        {
            this.employeeInput.setText("0");
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.salary.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.salary.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.salary.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48), (float)(this.guiTop + 66), 0, 180, 80, 20, 512.0F, 512.0F, false);
        this.leaderInput.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 138), (float)(this.guiTop + 66), 0, 200, 80, 20, 512.0F, 512.0F, false);
        this.cadreInput.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 228), (float)(this.guiTop + 66), 0, 220, 80, 20, 512.0F, 512.0F, false);
        this.employeeInput.drawTextBox();
        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.validButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.leaderInput.textboxKeyTyped(typedChar, keyCode);
        this.cadreInput.textboxKeyTyped(typedChar, keyCode);
        this.employeeInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }

            if (this.validButton.enabled && this.isNumeric(this.leaderInput.getText()) && this.isNumeric(this.cadreInput.getText()) && this.isNumeric(this.employeeInput.getText()) && EnterpriseGui.hasPermission("salary") && mouseX > this.guiLeft + 190 && mouseX < this.guiLeft + 190 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                HashMap hashMapForPacket = new HashMap();
                hashMapForPacket.put("leader", Integer.valueOf(Integer.parseInt(this.leaderInput.getText())));
                hashMapForPacket.put("cadre", Integer.valueOf(Integer.parseInt(this.cadreInput.getText())));
                hashMapForPacket.put("employee", Integer.valueOf(Integer.parseInt(this.employeeInput.getText())));
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankSalaryPacket((String)EnterpriseGui.enterpriseInfos.get("name"), hashMapForPacket)));
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }
        }

        this.leaderInput.mouseClicked(mouseX, mouseY, mouseButton);
        this.cadreInput.mouseClicked(mouseX, mouseY, mouseButton);
        this.employeeInput.mouseClicked(mouseX, mouseY, mouseButton);
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

            if (Integer.parseInt(str) < 0)
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
