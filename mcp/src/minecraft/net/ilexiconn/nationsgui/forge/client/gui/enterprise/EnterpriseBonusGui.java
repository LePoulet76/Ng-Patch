package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankBonusPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseBonusGui extends ModalGui
{
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField bonusInput;
    private GuiScrollBarFaction scrollBar;
    private boolean membersExpanded = false;
    private String selectedMember = "";
    private String hoveredMember = "";
    private ArrayList<String> members = new ArrayList();

    public EnterpriseBonusGui(GuiScreen guiFrom)
    {
        super(guiFrom);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.bonusInput.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 184, this.guiTop + 95, 118, 20, I18n.getString("enterprise.bank.modal.action.valid"));
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 170), (float)(this.guiTop + 86), 90);

        if (!EnterpriseGui.hasPermission("salary"))
        {
            this.validButton.enabled = false;
        }

        this.bonusInput = new GuiTextField(this.fontRenderer, this.guiLeft + 184, this.guiTop + 68, 97, 10);
        this.bonusInput.setEnableBackgroundDrawing(false);
        this.bonusInput.setMaxStringLength(7);
        this.bonusInput.setText("0");

        if (EnterpriseGui.enterpriseInfos != null)
        {
            this.members.addAll((ArrayList)EnterpriseGui.enterpriseInfos.get("players_online"));
            this.members.addAll((ArrayList)EnterpriseGui.enterpriseInfos.get("players_offline"));

            if (this.members.size() > 0)
            {
                this.selectedMember = (String)this.members.get(0);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.hoveredMember = "";
        this.drawScaledString(I18n.getString("enterprise.bank.modal.bonus.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.bonus.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("enterprise.bank.modal.bonus.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 180), (float)(this.guiTop + 62), 0, 242, 122, 20, 512.0F, 512.0F, false);
        this.drawScaledString("\u00a7a$", this.guiLeft + 294, this.guiTop + 68, 16777215, 1.3F, true, false);
        this.bonusInput.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 242, 122, 20, 512.0F, 512.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 156), (float)(this.guiTop + 62), 123, 242, 19, 20, 512.0F, 512.0F, false);

        if (this.selectedMember != null)
        {
            this.drawScaledString(this.selectedMember.split("#")[1], this.guiLeft + 56, this.guiTop + 68, 16777215, 1.0F, false, false);
        }

        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.validButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        if (this.membersExpanded)
        {
            ClientEventHandler.STYLE.bindTexture("faction_modal");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 81), 0, 264, 122, 99, 512.0F, 512.0F, false);
            GUIUtils.startGLScissor(this.guiLeft + 54, this.guiTop + 82, 116, 97);

            for (int i = 0; i < this.members.size(); ++i)
            {
                int offsetX = this.guiLeft + 54;
                Float offsetY = Float.valueOf((float)(this.guiTop + 82 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("faction_modal");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 1, 265, 116, 20, 512.0F, 512.0F, false);
                this.drawScaledString(((String)this.members.get(i)).split("#")[1], offsetX + 2, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_modal");

                if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0F)
                {
                    this.hoveredMember = (String)this.members.get(i);
                }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.bonusInput.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    private float getSlide()
    {
        return this.members.size() > 5 ? (float)(-(this.members.size() - 5) * 20) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.membersExpanded && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }

            if (this.validButton.enabled && !this.membersExpanded && this.isNumeric(this.bonusInput.getText()) && EnterpriseGui.hasPermission("salary") && mouseX > this.guiLeft + 184 && mouseX < this.guiLeft + 184 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankBonusPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.selectedMember.replaceAll("\\d+#\\**\\s?", ""), Integer.valueOf(Integer.parseInt(this.bonusInput.getText())))));
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBankGUI());
            }

            if (mouseX > this.guiLeft + 156 && mouseX < this.guiLeft + 156 + 19 && mouseY > this.guiTop + 62 && mouseY < this.guiTop + 62 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.membersExpanded = !this.membersExpanded;
            }

            if (this.membersExpanded && this.hoveredMember != null && !this.hoveredMember.isEmpty())
            {
                this.selectedMember = this.hoveredMember;
                this.hoveredMember = "";
                this.membersExpanded = false;
            }
        }

        this.bonusInput.mouseClicked(mouseX, mouseY, mouseButton);
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
