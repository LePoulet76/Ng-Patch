package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$10;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$11;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$12;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$13;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI$9;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;

public class CustomizationGUI extends GuiScreen
{
    GuiScreen previous;
    private boolean displayRestartMessage = false;

    public CustomizationGUI(GuiScreen previous)
    {
        this.previous = previous;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.getString("gui.customization.title"), this.width / 2, 15, 16777215);
        super.drawScreen(par1, par2, par3);

        if (this.displayRestartMessage)
        {
            this.drawCenteredString(this.fontRenderer, I18n.getString("gui.option.restartRequired"), this.width / 2, this.height / 6 + 153, 16777215);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new CustomizationGUI$1(this, 1, this.width / 2 - 152, this.height / 6, 150, 20, I18n.getString("options.displayobjectives")));
        this.buttonList.add(new CustomizationGUI$2(this, 1, this.width / 2 + 2, this.height / 6 + 125, 150, 20, I18n.getString("options.azimutenable")));
        this.buttonList.add(new CustomizationGUI$3(this, 1, this.width / 2 + 2, this.height / 6, 150, 20, I18n.getString("options.azimutposition")));
        this.buttonList.add(new CustomizationGUI$4(this, 1, this.width / 2 + 2, this.height / 6 - 25, 150, 20, I18n.getString("options.armorInfosRight")));
        this.buttonList.add(new CustomizationGUI$5(this, 1, this.width / 2 - 152, this.height / 6 + 25, 150, 20, I18n.getString("options.damageIndicator")));
        this.buttonList.add(new CustomizationGUI$6(this, 1, this.width / 2 + 2, this.height / 6 + 25, 150, 20, I18n.getString("options.displayArmorInInfo")));
        this.buttonList.add(new CustomizationGUI$7(this, 1, this.width / 2 - 152, this.height / 6 + 50, 150, 20, I18n.getString("options.render3DSkins")));
        this.buttonList.add(new CustomizationGUI$8(this, 1, this.width / 2 + 2, this.height / 6 + 50, 150, 20, I18n.getString("options.displayNotifications")));
        this.buttonList.add(new CustomizationGUI$9(this, 1, this.width / 2 - 152, this.height / 6 + 75, 150, 20, I18n.getString("options.displayFurnitures")));
        this.buttonList.add(new CustomizationGUI$10(this, 1, this.width / 2 + 2, this.height / 6 + 75, 150, 20, I18n.getString("options.customArmor")));
        this.buttonList.add(new CustomizationGUI$11(this, 1, this.width / 2 - 152, this.height / 6 + 100, 150, 20, I18n.getString("options.pictureframe")));
        EnumOptions options = EnumOptions.SHOW_CAPE;
        this.buttonList.add(new CustomizationGUI$12(this, 1, this.width / 2 + 2, this.height / 6 + 100, 150, 20, I18n.getString(options.getEnumString()), options));
        this.buttonList.add(new CustomizationGUI$13(this, 1, this.width / 2 - 152, this.height / 6 + 125, 150, 20, I18n.getString("options.displayEmotes")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 165, I18n.getString("gui.done")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(this.previous);
        }
    }

    static boolean access$002(CustomizationGUI x0, boolean x1)
    {
        return x0.displayRestartMessage = x1;
    }

    static Minecraft access$100(CustomizationGUI x0)
    {
        return x0.mc;
    }

    static Minecraft access$200(CustomizationGUI x0)
    {
        return x0.mc;
    }
}
