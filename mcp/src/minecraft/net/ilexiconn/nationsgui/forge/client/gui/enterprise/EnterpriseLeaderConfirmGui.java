package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ModalGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class EnterpriseLeaderConfirmGui extends ModalGui
{
    private GuiButton yesButton;
    private GuiButton noButton;
    private GuiScreen guiFrom;
    private String targetPlayer;
    private String enterpriseName;

    public EnterpriseLeaderConfirmGui(GuiScreen guiFrom, String targetPlayer, String enterpriseName)
    {
        super(guiFrom);
        this.guiFrom = guiFrom;
        this.targetPlayer = targetPlayer;
        this.enterpriseName = enterpriseName;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.yesButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("faction.common.confirm"));
        this.noButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, I18n.getString("faction.common.cancel"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        this.drawScaledString(I18n.getString("faction.modal.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);
        this.drawScaledString(I18n.getString("enterprise.leader.text_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("enterprise.leader.text_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
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
            if (EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader") && mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(this.targetPlayer, this.enterpriseName, "enterprise_leader")));
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
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
