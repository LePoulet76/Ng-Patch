package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.SoundGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.SoundGUI$2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;

public class SoundGUI extends GuiScreen
{
    private GuiScreen previous;
    private static final EnumOptions[] relevantOptions = new EnumOptions[] {EnumOptions.MUSIC, EnumOptions.SOUND};

    public SoundGUI(GuiScreen previous)
    {
        this.previous = previous;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.getString("options.sounds"), this.width / 2, 15, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 100)
        {
            this.mc.displayGuiScreen(this.previous);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new SliderGUI(-1, this.width / 2 - 155, this.height / 6 + 12, "Radio", new SoundGUI$1(this)));
        this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 50, I18n.getString("gui.done")));
        EnumOptions[] aenumoptions = relevantOptions;
        int j = aenumoptions.length;
        int i = 0;

        for (int k = 0; k < j; ++k)
        {
            EnumOptions enumoptions = aenumoptions[k];

            if (enumoptions.getEnumFloat())
            {
                this.buttonList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), enumoptions, this.mc.gameSettings.getKeyBinding(enumoptions), this.mc.gameSettings.getOptionFloatValue(enumoptions)));
            }
            else
            {
                GuiSmallButton guismallbutton = new GuiSmallButton(enumoptions.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), enumoptions, this.mc.gameSettings.getKeyBinding(enumoptions));

                if (enumoptions == EnumOptions.DIFFICULTY && this.mc.theWorld != null && this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                {
                    guismallbutton.enabled = false;
                    guismallbutton.displayString = I18n.getString("options.difficulty") + ": " + I18n.getString("options.difficulty.hardcore");
                }

                this.buttonList.add(guismallbutton);
            }

            ++i;
        }

        this.buttonList.add(new SliderGUI(-1, this.width / 2 + 5, this.height / 6 - 12 + 24, "Browser Volume", new SoundGUI$2(this)));
    }
}
