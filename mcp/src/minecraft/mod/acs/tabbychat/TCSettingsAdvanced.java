package acs.tabbychat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;

public class TCSettingsAdvanced extends TCSettingsGUI
{
    private static final int chatScrollHistoryID = 9401;
    private static final int maxLengthChannelNameID = 9402;
    private static final int multiChatDelayID = 9403;
    private static final int chatBoxWidthID = 9404;
    private static final int chatBoxFocHeightID = 9405;
    private static final int chatBoxUnfocHeightID = 9406;
    private static final int customChatBoxSizeID = 9407;
    private static final int chatFadeTicksID = 9408;
    private static final int forceUnicodeID = 9409;
    public TCSettingTextBox chatScrollHistory;
    protected TCSettingTextBox maxLengthChannelName;
    protected TCSettingTextBox multiChatDelay;
    public TCSettingBool customChatBoxSize;
    public TCSettingSlider chatBoxWidth;
    public TCSettingSlider chatBoxFocHeight;
    public TCSettingSlider chatBoxUnfocHeight;
    public TCSettingSlider chatFadeTicks;
    public TCSettingBool forceUnicode;

    public TCSettingsAdvanced()
    {
        this.chatScrollHistory = new TCSettingTextBox("100", "Chat history to retain (lines)", 9401);
        this.maxLengthChannelName = new TCSettingTextBox("10", "Channel name max. length", 9402);
        this.multiChatDelay = new TCSettingTextBox("100", "Multi-chat send delay (ms)", 9403);
        this.customChatBoxSize = new TCSettingBool(Boolean.valueOf(false), "Custom Chatbox size (screen %)", 9407);
        this.chatBoxWidth = new TCSettingSlider(Float.valueOf(50.0F), "Width", 9404, 20.0F, 100.0F);
        this.chatBoxFocHeight = new TCSettingSlider(Float.valueOf(50.0F), "Focused Height", 9405, 20.0F, 100.0F);
        this.chatBoxUnfocHeight = new TCSettingSlider(Float.valueOf(20.0F), "Unfocused Height", 9406, 20.0F, 100.0F);
        this.chatFadeTicks = new TCSettingSlider(Float.valueOf(200.0F), "Chat fade time (ticks)", 9408, 10.0F, 2000.0F);
        this.forceUnicode = new TCSettingBool(Boolean.valueOf(false), "Force Unicode Chat Rendering", 9409);
        this.name = "Advanced Settings";
        this.bgcolor = 1719676564;
        this.chatScrollHistory.textBox.setMaxStringLength(3);
        this.maxLengthChannelName.textBox.setMaxStringLength(2);
        this.multiChatDelay.textBox.setMaxStringLength(4);
    }

    protected TCSettingsAdvanced(TabbyChat _tc)
    {
        this();
        tc = _tc;
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();

        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            if (TCSetting.class.isInstance(this.buttonList.get(i)))
            {
                TCSetting tmp = (TCSetting)this.buttonList.get(i);

                if (tmp.type == "slider")
                {
                    ((TCSettingSlider)tmp).handleMouseInput();
                }
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        int var10000 = this.width;
        this.getClass();
        int effLeft = (var10000 - 325) / 2;
        this.getClass();
        var10000 = effLeft - 4;
        var10000 = this.height;
        this.getClass();
        int effTop = (var10000 - 180) / 2;
        this.getClass();
        var10000 = effTop - 4;
        var10000 = this.width;
        this.getClass();
        int effRight = (var10000 + 325) / 2;
        var10000 = this.width;
        this.getClass();
        int col1x = (var10000 - 325) / 2 + 100;
        var10000 = this.width;
        this.getClass();
        int col2x = (var10000 + 325) / 2 - 65;
        int buttonColor = (this.bgcolor & 16777215) + -16777216;
        this.chatScrollHistory.labelX = col1x;
        this.chatScrollHistory.setButtonLoc(col1x + 5 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.chatScrollHistory.description), this.rowY(1));
        this.chatScrollHistory.setButtonDims(30, 11);
        this.buttonList.add(this.chatScrollHistory);
        this.maxLengthChannelName.labelX = col1x;
        this.maxLengthChannelName.setButtonLoc(col1x + 5 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.maxLengthChannelName.description), this.rowY(2));
        this.maxLengthChannelName.setButtonDims(20, 11);
        this.buttonList.add(this.maxLengthChannelName);
        this.multiChatDelay.labelX = col1x;
        this.multiChatDelay.setButtonLoc(col1x + 5 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.multiChatDelay.description), this.rowY(3));
        this.multiChatDelay.setButtonDims(40, 11);
        this.buttonList.add(this.multiChatDelay);
        this.customChatBoxSize.setButtonLoc(col1x, this.rowY(4));
        this.customChatBoxSize.labelX = col1x + 19;
        this.customChatBoxSize.buttonOnColor = buttonColor;
        this.buttonList.add(this.customChatBoxSize);
        this.chatBoxWidth.labelX = col1x + 10;
        this.chatBoxWidth.setButtonLoc(col1x + 15 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.chatBoxWidth.description), this.rowY(5));
        this.chatBoxWidth.buttonOnColor = buttonColor;
        this.buttonList.add(this.chatBoxWidth);
        this.chatBoxFocHeight.labelX = col1x + 10;
        this.chatBoxFocHeight.setButtonLoc(col1x + 15 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.chatBoxFocHeight.description), this.rowY(6));
        this.chatBoxFocHeight.buttonOnColor = buttonColor;
        this.buttonList.add(this.chatBoxFocHeight);
        this.chatBoxUnfocHeight.labelX = col1x + 10;
        this.chatBoxUnfocHeight.setButtonLoc(col1x + 15 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.chatBoxUnfocHeight.description), this.rowY(7));
        this.chatBoxUnfocHeight.buttonOnColor = buttonColor;
        this.buttonList.add(this.chatBoxUnfocHeight);
        this.chatFadeTicks.labelX = col1x;
        this.chatFadeTicks.setButtonLoc(col1x + 5 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.chatFadeTicks.description), this.rowY(8));
        this.chatFadeTicks.buttonOnColor = buttonColor;
        this.chatFadeTicks.units = "";
        this.buttonList.add(this.chatFadeTicks);
        this.forceUnicode.setButtonLoc(col1x, this.rowY(9));
        this.forceUnicode.labelX = col1x + 19;
        this.forceUnicode.buttonOnColor = buttonColor;
        this.buttonList.add(this.forceUnicode);
        this.validateButtonStates();
    }

    public void validateButtonStates()
    {
        if (!this.customChatBoxSize.getTempValue().booleanValue())
        {
            this.chatBoxWidth.disable();
            this.chatBoxFocHeight.disable();
            this.chatBoxUnfocHeight.disable();
        }
        else
        {
            this.chatBoxWidth.enable();
            this.chatBoxFocHeight.enable();
            this.chatBoxUnfocHeight.enable();
        }
    }

    protected void storeTempVars()
    {
        this.chatScrollHistory.save();
        this.maxLengthChannelName.save();
        this.multiChatDelay.save();
        this.customChatBoxSize.save();
        this.chatBoxWidth.save();
        this.chatBoxFocHeight.save();
        this.chatBoxUnfocHeight.save();
        this.chatFadeTicks.save();
        this.forceUnicode.save();
    }

    protected void resetTempVars()
    {
        this.chatScrollHistory.reset();
        this.maxLengthChannelName.reset();
        this.multiChatDelay.reset();
        this.customChatBoxSize.reset();
        this.chatBoxWidth.reset();
        this.chatBoxFocHeight.reset();
        this.chatBoxUnfocHeight.reset();
        this.chatFadeTicks.reset();
        this.forceUnicode.reset();
    }

    protected void importSettings()
    {
        this.chatScrollHistory.setValue(Integer.toString(tc.globalPrefs.retainedChats));
        this.maxLengthChannelName.setValue(Integer.toString(tc.globalPrefs.maxChannelNameLength));
        this.resetTempVars();
    }

    protected boolean loadSettingsFile()
    {
        this.settingsFile = new File(tabbyChatDir, "advanced.cfg");
        boolean loaded = false;

        if (!this.settingsFile.exists())
        {
            return loaded;
        }
        else
        {
            Properties settingsTable = new Properties();

            try
            {
                FileInputStream e = new FileInputStream(this.settingsFile);
                settingsTable.load(e);
                e.close();
            }
            catch (Exception var4)
            {
                TabbyChat.printErr("Unable to read from advanced settings file : \'" + var4.getLocalizedMessage() + "\' : " + var4.toString());
            }

            this.chatScrollHistory.setValue(settingsTable.getProperty("chatScrollHistory"));
            this.maxLengthChannelName.setValue(settingsTable.getProperty("maxLengthChannelName"));
            this.multiChatDelay.setValue(settingsTable.getProperty("multiChatDelay"));
            this.customChatBoxSize.setValue(Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty("customChatBoxSize"))));
            this.chatBoxWidth.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxWidth"), 20.0F, 100.0F, 50.0F));
            this.chatBoxFocHeight.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxFocHeight"), 20.0F, 100.0F, 50.0F));
            this.chatBoxUnfocHeight.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxUnfocHeight"), 20.0F, 100.0F, 20.0F));
            this.chatFadeTicks.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatFadeTicks"), 10.0F, 2000.0F, 200.0F));
            this.forceUnicode.setValue(Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty("forceUnicode"))));
            loaded = true;
            this.resetTempVars();
            return loaded;
        }
    }

    protected void saveSettingsFile()
    {
        if (!tabbyChatDir.exists())
        {
            tabbyChatDir.mkdirs();
        }

        Properties settingsTable = new Properties();
        settingsTable.put("chatScrollHistory", this.chatScrollHistory.getValue());
        settingsTable.put("maxLengthChannelName", this.maxLengthChannelName.getValue());
        settingsTable.put("multiChatDelay", this.multiChatDelay.getValue());
        settingsTable.put("customChatBoxSize", this.customChatBoxSize.getValue().toString());
        settingsTable.put("chatBoxWidth", this.chatBoxWidth.getValue().toString());
        settingsTable.put("chatBoxFocHeight", this.chatBoxFocHeight.getValue().toString());
        settingsTable.put("chatBoxUnfocHeight", this.chatBoxUnfocHeight.getValue().toString());
        settingsTable.put("chatFadeTicks", this.chatFadeTicks.getValue().toString());
        settingsTable.put("forceUnicode", this.forceUnicode.getValue().toString());

        try
        {
            FileOutputStream e = new FileOutputStream(this.settingsFile);
            settingsTable.store(e, "Advanced settings");
            e.close();
        }
        catch (Exception var3)
        {
            TabbyChat.printErr("Unable to write to advanced settings file : \'" + var3.getLocalizedMessage() + "\' : " + var3.toString());
        }
    }
}
