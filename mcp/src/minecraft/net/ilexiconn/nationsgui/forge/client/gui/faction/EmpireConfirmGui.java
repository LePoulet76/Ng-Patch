package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPassEmpirePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EmpireConfirmGui extends ModalGui
{
    private GuiButton yesButton;
    private GuiButton noButton;
    private GuiScreen guiFrom;

    public EmpireConfirmGui(GuiScreen guiFrom)
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
        this.yesButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, ((String)FactionGUI.factionInfos.get("name")).contains("Empire") ? I18n.getString("faction.home.button.empire_down") : I18n.getString("faction.home.button.empire_up"));
        this.noButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.getString("faction.common.cancel"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("faction.modal.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);

        if (((String)FactionGUI.factionInfos.get("name")).contains("Empire"))
        {
            this.drawScaledString(I18n.getString("faction.empire_down.text_1"), this.guiLeft + 53, this.guiTop + 35, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_down.text_2"), this.guiLeft + 53, this.guiTop + 45, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_down.text_3"), this.guiLeft + 53, this.guiTop + 60, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_down.text_4"), this.guiLeft + 53, this.guiTop + 70, 1644825, 1.0F, false, false);
        }
        else
        {
            this.drawScaledString(I18n.getString("faction.empire_up.text_1"), this.guiLeft + 53, this.guiTop + 35, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_up.text_2"), this.guiLeft + 53, this.guiTop + 45, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_up.text_3"), this.guiLeft + 53, this.guiTop + 60, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.empire_up.text_4"), this.guiLeft + 53, this.guiTop + 70, 1644825, 1.0F, false, false);
        }

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
            if (((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue() && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPassEmpirePacket()));
                Minecraft.getMinecraft().displayGuiScreen(new FactionGUI((String)FactionGUI.factionInfos.get("name")));
            }

            if (mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(this.guiFrom);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
