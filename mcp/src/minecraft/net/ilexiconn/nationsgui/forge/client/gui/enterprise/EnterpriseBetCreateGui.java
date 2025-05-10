package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalLargeGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetCreatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseBetCreateGui extends ModalLargeGui
{
    private GuiButton yesButton;
    private GuiButton noButton;
    private GuiScreen guiFrom;
    private GuiTextField inputQuestion;
    private GuiTextField inputOption1;
    private GuiTextField inputOption2;
    private GuiTextField inputDuration;
    private GuiTextField inputBetMin;

    public EnterpriseBetCreateGui(GuiScreen guiFrom)
    {
        super(guiFrom);
        this.guiFrom = guiFrom;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.inputQuestion = new GuiTextField(this.fontRenderer, this.guiLeft + 55, this.guiTop + 45, 245, 10);
        this.inputQuestion.setEnableBackgroundDrawing(false);
        this.inputQuestion.setMaxStringLength(50);
        this.inputOption1 = new GuiTextField(this.fontRenderer, this.guiLeft + 55, this.guiTop + 84, 245, 10);
        this.inputOption1.setEnableBackgroundDrawing(false);
        this.inputOption1.setMaxStringLength(40);
        this.inputOption2 = new GuiTextField(this.fontRenderer, this.guiLeft + 55, this.guiTop + 123, 245, 10);
        this.inputOption2.setEnableBackgroundDrawing(false);
        this.inputOption2.setMaxStringLength(40);
        this.inputDuration = new GuiTextField(this.fontRenderer, this.guiLeft + 55, this.guiTop + 160, 116, 10);
        this.inputDuration.setEnableBackgroundDrawing(false);
        this.inputDuration.setMaxStringLength(5);
        this.inputBetMin = new GuiTextField(this.fontRenderer, this.guiLeft + 184, this.guiTop + 160, 116, 10);
        this.inputBetMin.setEnableBackgroundDrawing(false);
        this.inputBetMin.setMaxStringLength(5);
        this.yesButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 183, 118, 20, I18n.getString("faction.common.confirm"));
        this.noButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 183, 118, 20, I18n.getString("faction.common.cancel"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("enterprise.bet.create.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bet.create.question"), this.guiLeft + 54, this.guiTop + 29, 1644825, 1.0F, false, false);
        ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 38, 249, 20);
        this.drawScaledString(I18n.getString("enterprise.bet.create.option1"), this.guiLeft + 54, this.guiTop + 68, 1644825, 1.0F, false, false);
        ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 77, 249, 20);
        this.drawScaledString(I18n.getString("enterprise.bet.create.option2"), this.guiLeft + 54, this.guiTop + 106, 1644825, 1.0F, false, false);
        ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 115, 249, 20);
        this.drawScaledString(I18n.getString("enterprise.bet.create.duration"), this.guiLeft + 54, this.guiTop + 144, 1644825, 1.0F, false, false);
        ModernGui.drawNGBlackSquare(this.guiLeft + 53, this.guiTop + 153, 120, 20);
        this.drawScaledString(I18n.getString("enterprise.bet.create.betMin"), this.guiLeft + 183, this.guiTop + 144, 1644825, 1.0F, false, false);
        ModernGui.drawNGBlackSquare(this.guiLeft + 182, this.guiTop + 153, 120, 20);
        this.inputQuestion.drawTextBox();
        this.inputOption1.drawTextBox();
        this.inputOption2.drawTextBox();
        this.inputDuration.drawTextBox();
        this.inputBetMin.drawTextBox();
        this.yesButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.noButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.inputQuestion.getText().isEmpty() && !this.inputOption1.getText().isEmpty() && !this.inputOption2.getText().isEmpty() && this.isNumeric(this.inputDuration.getText()) && this.isNumeric(this.inputBetMin.getText()) && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 183 && mouseY < this.guiTop + 183 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBetCreatePacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.inputQuestion.getText(), this.inputOption1.getText(), this.inputOption2.getText(), Integer.valueOf(Integer.parseInt(this.inputDuration.getText())), Integer.valueOf(Integer.parseInt(this.inputBetMin.getText())))));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }

            if (mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 183 && mouseY < this.guiTop + 183 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(this.guiFrom);
            }

            this.inputQuestion.mouseClicked(mouseX, mouseY, mouseButton);
            this.inputOption1.mouseClicked(mouseX, mouseY, mouseButton);
            this.inputOption2.mouseClicked(mouseX, mouseY, mouseButton);
            this.inputDuration.mouseClicked(mouseX, mouseY, mouseButton);
            this.inputBetMin.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.inputQuestion.textboxKeyTyped(typedChar, keyCode);
        this.inputOption1.textboxKeyTyped(typedChar, keyCode);
        this.inputOption2.textboxKeyTyped(typedChar, keyCode);
        this.inputDuration.textboxKeyTyped(typedChar, keyCode);
        this.inputBetMin.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    public boolean isNumeric(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException var3)
        {
            return false;
        }
    }
}
