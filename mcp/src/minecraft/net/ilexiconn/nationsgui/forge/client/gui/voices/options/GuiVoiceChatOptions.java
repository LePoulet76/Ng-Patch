package net.ilexiconn.nationsgui.forge.client.gui.voices.options;

import net.ilexiconn.nationsgui.forge.client.gui.voices.GuiBoostSlider;
import net.ilexiconn.nationsgui.forge.client.gui.voices.GuiDropDownMenu;
import net.ilexiconn.nationsgui.forge.client.gui.voices.options.GuiVoiceChatOptions$GuiToggleButton;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.device.Device;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeySpeakEvent;
import net.ilexiconn.nationsgui.forge.client.voices.sound.MicrophoneTester;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiVoiceChatOptions extends GuiScreen
{
    VoiceChatClient voiceChat;
    MicrophoneTester tester;
    GuiBoostSlider voiceVolume;
    GuiDropDownMenu dropDown;
    GuiButton micModeButton;
    GuiVoiceChatOptions$GuiToggleButton enableVoiceButton;
    private GuiButton encodingMode;
    private GuiButton enchantedDecoding;
    private GuiButton serverConnection;
    private GuiBoostSlider qualitySlider;
    boolean resetRequired;
    private GuiScreen parent;

    public GuiVoiceChatOptions(VoiceChatClient voiceChat, GuiScreen parent)
    {
        this.voiceChat = voiceChat;
        this.tester = new MicrophoneTester(voiceChat);
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        String[] array = new String[this.voiceChat.getSettings().getDeviceHandler().getDevices().size()];

        for (int var7 = 0; var7 < this.voiceChat.getSettings().getDeviceHandler().getDevices().size(); ++var7)
        {
            array[var7] = ((Device)this.voiceChat.getSettings().getDeviceHandler().getDevices().get(var7)).getName();
        }

        byte var71 = 55;
        this.enableVoiceButton = new GuiVoiceChatOptions$GuiToggleButton(415, (this.width - 304) / 2, this.height / 2 - 105, 304, 20, VoiceChat.getProxyInstance().getSettings().isVoiceEnable());
        this.dropDown = new GuiDropDownMenu(0, this.width / 2 - 152, this.height / 2 - var71, 150, 20, this.voiceChat.getSettings().getInputDevice() != null ? this.voiceChat.getSettings().getInputDevice().getName() : "None", array);
        this.micModeButton = new GuiButton(5, this.width / 2 - 152, this.height / 2 + 25 - var71, 150, 20, I18n.getString("voice.mode") + " " + (this.voiceChat.getSettings().getSpeakMode() == 0 ? "Push To Talk" : "Toggle To Talk"));
        this.voiceVolume = new GuiBoostSlider(910, this.width / 2 + 2, this.height / 2 - 25 - var71, "VALUE", I18n.getString("voice.volume") + " " + (this.voiceChat.getSettings().getWorldVolume() == 0.0F ? "OFF" : "" + (int)(this.voiceChat.getSettings().getWorldVolume() * 100.0F) + "%"), 0.0F);
        this.voiceVolume.sliderValue = this.voiceChat.getSettings().getWorldVolume();
        this.buttonList.add(new GuiButton(2, this.width / 2 - 152, this.height / 2 - 25 - var71, 150, 20, !this.tester.recording ? I18n.getString("voice.test_micro") : I18n.getString("voice.stop_micro")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 100 - var71, 150, 20, I18n.getString("gui.done")));
        this.buttonList.add(this.micModeButton);
        this.buttonList.add(this.voiceVolume);
        this.buttonList.add(this.enableVoiceButton);

        if (this.voiceChat.getSettings().getDeviceHandler().isEmpty())
        {
            GuiButton button1 = (GuiButton)this.buttonList.get(0);
            GuiButton button2 = (GuiButton)this.buttonList.get(1);
            GuiButton button3 = (GuiButton)this.buttonList.get(3);
            button1.enabled = false;
            button2.enabled = false;
            button3.enabled = false;
            this.micModeButton.enabled = false;
            String noDevices = I18n.getString("voice.no_input");
            this.drawString(this.fontRenderer, noDevices, this.width / 2 - this.fontRenderer.getStringWidth(noDevices) / 2, this.height - 70, -269484032);
        }

        this.buttonList.add(new GuiButton(151, this.width / 2 + 77, this.height / 2 + 100 - var71, 75, 20, "Reset"));
        this.qualitySlider = new GuiBoostSlider(154, this.width / 2 + 2, this.height / 2 + 50 - var71, "VALUE", I18n.getString("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F ? " 0" : " " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F)), 0.0F);
        this.qualitySlider.sliderValue = this.voiceChat.getSettings().getEncodingQuality();
        this.encodingMode = new GuiButton(155, this.width / 2 - 152, this.height / 2 + 75 - var71, 150, 20, I18n.getString("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString());
        this.buttonList.add(this.enchantedDecoding = new GuiButton(156, this.width / 2 - 152, this.height / 2 + 50 - var71, 150, 20, I18n.getString("voice.encoding_quality") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed() ? "ON" : "OFF")));
        this.buttonList.add(this.qualitySlider);
        this.buttonList.add(this.encodingMode);
        this.encodingMode.enabled = false;
        this.buttonList.add(this.dropDown);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.voiceChat.getSettings().setWorldVolume(this.voiceVolume.sliderValue);
        this.voiceVolume.setDisplayString(I18n.getString("voice.volume") + " " + (this.voiceChat.getSettings().getWorldVolume() == 0.0F ? "OFF" : (int)(this.voiceChat.getSettings().getWorldVolume() * 100.0F) + "%"));
        this.voiceChat.getSettings().setEncodingQuality(this.qualitySlider.sliderValue);
        this.qualitySlider.setDisplayString(I18n.getString("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F ? " 0" : " " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F)));

        if (this.resetRequired)
        {
            this.qualitySlider.sliderValue = 0.6F;
            this.voiceChat.getSettings().setEncodingQuality(this.qualitySlider.sliderValue);
            this.qualitySlider.idValue = I18n.getString("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F ? " 0" : " " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F));
            this.qualitySlider.displayString = this.qualitySlider.idValue;
            this.voiceChat.getSettings().setEncodingMode(1);
            this.encodingMode.displayString = I18n.getString("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString();
            this.voiceChat.getSettings().setPerceptualEnchantment(true);
            this.enchantedDecoding.displayString = I18n.getString("voice.decoding") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed() ? "ON" : "OFF");
            this.resetRequired = false;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int x, int y, float tick)
    {
        this.drawDefaultBackground();

        if (this.voiceChat.getSettings().getDeviceHandler().isEmpty())
        {
            String scale = I18n.getString("voice.no_input");
            this.drawString(this.fontRenderer, scale, this.width / 2 - this.fontRenderer.getStringWidth(scale) / 2, this.height - 80, -269484032);
        }

        this.drawString(this.fontRenderer, I18n.getString("voice.settings_title"), (this.width - this.fontRenderer.getStringWidth(I18n.getString("voice.settings_title"))) / 2, this.height / 2 - 130, -1);

        if (this.mc.isSingleplayer())
        {
            this.drawString(this.fontRenderer, "Voice Chat doesn\'t work in Singleplayer mode.", this.width / 2 - this.fontRenderer.getStringWidth("Voice Chat doesn\'t work in Singleplayer mode.") / 2, this.height - 70, -269466299);
        }

        if ((int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F) <= 2)
        {
            this.drawString(this.fontRenderer, "Encoding Quality below 2 is not recommended.", this.width / 2, this.height - 50, -255);
        }

        super.drawScreen(x, y, tick);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int x, int y, int b)
    {
        if (b == 0 && this.dropDown.getMouseOverInteger() != -1 && this.dropDown.dropDownMenu && !this.voiceChat.getSettings().getDeviceHandler().isEmpty())
        {
            Device device1 = (Device)this.voiceChat.getSettings().getDeviceHandler().getDevices().get(this.dropDown.getMouseOverInteger());

            if (device1 == null)
            {
                return;
            }

            this.voiceChat.getSettings().setInputDevice(device1);
            this.dropDown.setDisplayString(device1.getName());
        }

        super.mouseClicked(x, y, b);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton button)
    {
        if (button.id == 0 && button instanceof GuiDropDownMenu && !this.voiceChat.getSettings().getDeviceHandler().isEmpty())
        {
            ((GuiDropDownMenu)button).dropDownMenu = !((GuiDropDownMenu)button).dropDownMenu;
        }

        if (button.id == 2)
        {
            if (!this.tester.recording)
            {
                this.tester.start();
            }
            else
            {
                this.tester.stop();
            }

            button.displayString = this.tester.recording ? I18n.getString("voice.stop_micro") : I18n.getString("voice.test_micro");
        }

        if (button.id == 3)
        {
            this.voiceChat.getSettings().getConfiguration().save();
            this.mc.displayGuiScreen(this.parent);
        }

        if (button.id == 5)
        {
            if (!this.dropDown.dropDownMenu)
            {
                this.micModeButton.enabled = true;
                this.micModeButton.drawButton = true;
                this.voiceChat.getSettings().setSpeakMode(this.voiceChat.getSettings().getSpeakMode() == 0 ? 1 : 0);
                this.micModeButton.displayString = I18n.getString("voice.mode") + " " + (this.voiceChat.getSettings().getSpeakMode() == 0 ? "Push To Talk" : "Toggle To Talk");
            }
            else if (this.voiceChat.getSettings().getDeviceHandler().isEmpty())
            {
                this.micModeButton.enabled = false;
                this.micModeButton.drawButton = false;
            }
        }

        if (button.id == 151)
        {
            this.resetRequired = true;
        }

        if (button.id == 155)
        {
            int mode = this.voiceChat.getSettings().getEncodingMode();

            if (mode < 2)
            {
                ++mode;
            }
            else
            {
                mode = 0;
            }

            this.voiceChat.getSettings().setEncodingMode(mode);
            this.encodingMode.displayString = I18n.getString("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString();
        }

        if (button.id == 156)
        {
            this.voiceChat.getSettings().setPerceptualEnchantment(!this.voiceChat.getSettings().isPerceptualEnchantmentAllowed());
            this.enchantedDecoding.displayString = I18n.getString("voice.decoding") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed() ? "ON" : "OFF");
        }

        if (button.id == 415)
        {
            this.enableVoiceButton.isVoiceActive = !this.enableVoiceButton.isVoiceActive;
            VoiceChat.getProxyInstance().getSettings().setVoiceEnable(this.enableVoiceButton.isVoiceActive);

            if (!this.enableVoiceButton.isVoiceActive)
            {
                this.voiceChat.setRecorderActive(false);
                KeySpeakEvent.recorder.stop();
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char t, int key)
    {
        if (key == 1)
        {
            this.voiceChat.getSettings().getConfiguration().save();
            this.mc.displayGuiScreen(this.parent);
            this.mc.setIngameFocus();
        }
    }

    public boolean inBounds(int x, int y, int posX, int posY, int width, int height)
    {
        return x >= posX && y >= posY && x < posX + width && y < posY + height;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        this.voiceChat.getSettings().getConfiguration().save();

        if (this.tester.recording)
        {
            this.tester.stop();
        }
    }
}
