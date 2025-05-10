package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.voices.options.GuiVoiceChatOptions;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class OptionsGUI extends GuiOptions
{
    public OptionsGUI(GuiScreen parent)
    {
        super(parent, Minecraft.getMinecraft().gameSettings);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(1044, this.width / 2 + 5, this.height / 6 - 12 + 72, 150, 20, I18n.getString("options.sounds")));
        Iterator iterator = this.buttonList.iterator();
        List idsToRemove = Arrays.asList(new Integer[] {Integer.valueOf(104), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(27), Integer.valueOf(200)});

        while (iterator.hasNext())
        {
            GuiButton guiButton = (GuiButton)iterator.next();

            if (idsToRemove.contains(Integer.valueOf(guiButton.id)))
            {
                iterator.remove();
            }
        }

        this.buttonList.add(new GuiButton(1042, this.width / 2 + 2, this.height / 6 + 144 - 6, 150, 20, I18n.getString("gui.customization.title")));
        this.buttonList.add(new GuiButton(146, this.width / 2 - 155, this.height / 6 + 60, 150, 20, I18n.getString("options.voices")));
        this.buttonList.add(new GuiButton(156, this.width / 2 - 152, this.height / 6 + 162, 150, 20, I18n.getString("options.screenshots")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 194, I18n.getString("gui.done")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);

        switch (par1GuiButton.id)
        {
            case 146:
                this.mc.displayGuiScreen(new GuiVoiceChatOptions(VoiceChat.getProxyInstance(), this));
                break;

            case 156:
                this.mc.displayGuiScreen(new GuiScreenshotOptions(this));
                break;

            case 1042:
                this.mc.displayGuiScreen(new CustomizationGUI(this));
                break;

            case 1044:
                this.mc.displayGuiScreen(new SoundGUI(this));
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        NBTConfig.CONFIG.save();
    }
}
