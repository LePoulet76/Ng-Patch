package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class OpenDiscordConfirmGui extends ModalGui
{
    private GuiButton yesButton;
    private GuiButton noButton;
    private GuiScreen guiFrom;

    public OpenDiscordConfirmGui(GuiScreen guiFrom)
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
        int index = 0;
        String[] var5 = I18n.getString("faction.discord.confirmation.text").split("##");
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            String line = var5[var7];
            this.drawScaledString(line, this.guiLeft + 53, this.guiTop + 35 + index * 15, 1644825, 0.95F, false, false);
            ++index;
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
            if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);

                try
                {
                    Class t = Class.forName("java.awt.Desktop");
                    Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    t.getMethod("browse", new Class[] {URI.class}).invoke(theDesktop, new Object[] {URI.create((String)FactionGUI.factionInfos.get("discord"))});
                }
                catch (Throwable var6)
                {
                    var6.printStackTrace();
                }
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
