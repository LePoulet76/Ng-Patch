package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class MultiplayerConfigGUI extends ScreenChatOptions
{
    public MultiplayerConfigGUI(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        super(par1GuiScreen, par2GameSettings);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        Iterator iterator = this.buttonList.iterator();
        List idsToRemove = Arrays.asList(new Integer[] {Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(12)});

        while (iterator.hasNext())
        {
            GuiButton i = (GuiButton)iterator.next();

            if (idsToRemove.contains(Integer.valueOf(i.id)))
            {
                iterator.remove();
            }
        }

        byte i1 = 1;
        GuiButton guiButtonTimestamp = new GuiButton(4244, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), 150, 20, "Enable Timestamp");
        guiButtonTimestamp.displayString = I18n.getString("options.chat_timestamp") + ": " + I18n.getString(ClientProxy.clientConfig.enableTimestamp ? "options.on" : "options.off");
        this.buttonList.add(guiButtonTimestamp);
        i1 = 2;
        GuiButton guiButtonTag = new GuiButton(4245, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), 150, 20, "Enable Tag");
        guiButtonTag.displayString = I18n.getString("options.chat_tag") + ": " + I18n.getString(ClientProxy.clientConfig.enableTag ? "options.on" : "options.off");
        this.buttonList.add(guiButtonTag);
        i1 = 9;
        GuiButton guiButtonUnicode = new GuiButton(4243, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), 150, 20, "Enable Unicode");
        guiButtonUnicode.displayString = I18n.getString("options.chat_unicode") + ": " + I18n.getString(ClientProxy.clientConfig.enableUnicode ? "options.on" : "options.off");
        this.buttonList.add(guiButtonUnicode);
        i1 = 12;
        GuiButton guiButtonAnimation = new GuiButton(4242, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), 150, 20, I18n.getString("options.chat_animation"));
        guiButtonAnimation.displayString = I18n.getString("options.chat_animation") + ": " + I18n.getString(ClientProxy.clientConfig.enableChatAnimation ? "options.on" : "options.off");
        this.buttonList.add(guiButtonAnimation);
        i1 = 13;
        GuiButton guiButtonBackground = new GuiButton(4241, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), 150, 20, I18n.getString("options.chat_background"));
        guiButtonBackground.displayString = I18n.getString("options.chat_background") + ": " + I18n.getString(ClientProxy.clientConfig.enableChatBackground ? "options.on" : "options.off");
        this.buttonList.add(guiButtonBackground);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);

        if (par1GuiButton.id == 4242)
        {
            ClientProxy.clientConfig.enableChatAnimation = !ClientProxy.clientConfig.enableChatAnimation;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var7)
            {
                var7.printStackTrace();
            }

            par1GuiButton.displayString = I18n.getString("options.chat_animation") + ": " + I18n.getString(ClientProxy.clientConfig.enableChatAnimation ? "options.on" : "options.off");
        }
        else if (par1GuiButton.id == 4241)
        {
            ClientProxy.clientConfig.enableChatBackground = !ClientProxy.clientConfig.enableChatBackground;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var6)
            {
                var6.printStackTrace();
            }

            par1GuiButton.displayString = I18n.getString("options.chat_background") + ": " + I18n.getString(ClientProxy.clientConfig.enableChatBackground ? "options.on" : "options.off");
        }
        else if (par1GuiButton.id == 4243)
        {
            ClientProxy.clientConfig.enableUnicode = !ClientProxy.clientConfig.enableUnicode;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var5)
            {
                var5.printStackTrace();
            }

            par1GuiButton.displayString = I18n.getString("options.chat_unicode") + ": " + I18n.getString(ClientProxy.clientConfig.enableUnicode ? "options.on" : "options.off");
        }
        else if (par1GuiButton.id == 4244)
        {
            ClientProxy.clientConfig.enableTimestamp = !ClientProxy.clientConfig.enableTimestamp;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var4)
            {
                var4.printStackTrace();
            }

            par1GuiButton.displayString = I18n.getString("options.chat_timestamp") + ": " + I18n.getString(ClientProxy.clientConfig.enableTimestamp ? "options.on" : "options.off");
        }
        else if (par1GuiButton.id == 4245)
        {
            ClientProxy.clientConfig.enableTag = !ClientProxy.clientConfig.enableTag;

            try
            {
                ClientProxy.saveConfig();
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }

            par1GuiButton.displayString = I18n.getString("options.chat_tag") + ": " + I18n.getString(ClientProxy.clientConfig.enableTag ? "options.on" : "options.off");
        }
    }

    private void updateButtonText(GuiButton guiButton)
    {
        guiButton.displayString = I18n.getString("options.pictureframe") + ": " + I18n.getString(ClientProxy.clientConfig.displayPictureFrame ? "options.on" : "options.off");
    }
}
