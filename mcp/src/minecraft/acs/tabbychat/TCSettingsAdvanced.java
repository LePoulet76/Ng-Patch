/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import acs.tabbychat.TCSetting;
import acs.tabbychat.TCSettingBool;
import acs.tabbychat.TCSettingSlider;
import acs.tabbychat.TCSettingTextBox;
import acs.tabbychat.TCSettingsGUI;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;

public class TCSettingsAdvanced
extends TCSettingsGUI {
    private static final int chatScrollHistoryID = 9401;
    private static final int maxLengthChannelNameID = 9402;
    private static final int multiChatDelayID = 9403;
    private static final int chatBoxWidthID = 9404;
    private static final int chatBoxFocHeightID = 9405;
    private static final int chatBoxUnfocHeightID = 9406;
    private static final int customChatBoxSizeID = 9407;
    private static final int chatFadeTicksID = 9408;
    private static final int forceUnicodeID = 9409;
    public TCSettingTextBox chatScrollHistory = new TCSettingTextBox("100", "Chat history to retain (lines)", 9401);
    protected TCSettingTextBox maxLengthChannelName = new TCSettingTextBox("10", "Channel name max. length", 9402);
    protected TCSettingTextBox multiChatDelay = new TCSettingTextBox("100", "Multi-chat send delay (ms)", 9403);
    public TCSettingBool customChatBoxSize = new TCSettingBool(false, "Custom Chatbox size (screen %)", 9407);
    public TCSettingSlider chatBoxWidth = new TCSettingSlider(Float.valueOf(50.0f), "Width", 9404, 20.0f, 100.0f);
    public TCSettingSlider chatBoxFocHeight = new TCSettingSlider(Float.valueOf(50.0f), "Focused Height", 9405, 20.0f, 100.0f);
    public TCSettingSlider chatBoxUnfocHeight = new TCSettingSlider(Float.valueOf(20.0f), "Unfocused Height", 9406, 20.0f, 100.0f);
    public TCSettingSlider chatFadeTicks = new TCSettingSlider(Float.valueOf(200.0f), "Chat fade time (ticks)", 9408, 10.0f, 2000.0f);
    public TCSettingBool forceUnicode = new TCSettingBool(false, "Force Unicode Chat Rendering", 9409);

    public TCSettingsAdvanced() {
        this.name = "Advanced Settings";
        this.bgcolor = 1719676564;
        this.chatScrollHistory.textBox.func_73804_f(3);
        this.maxLengthChannelName.textBox.func_73804_f(2);
        this.multiChatDelay.textBox.func_73804_f(4);
    }

    protected TCSettingsAdvanced(TabbyChat _tc) {
        this();
        tc = _tc;
    }

    public void func_73867_d() {
        super.func_73867_d();
        for (int i = 0; i < this.field_73887_h.size(); ++i) {
            if (!TCSetting.class.isInstance(this.field_73887_h.get(i))) continue;
            TCSetting tmp = (TCSetting)((Object)this.field_73887_h.get(i));
            if (tmp.type != "slider") continue;
            ((TCSettingSlider)tmp).handleMouseInput();
        }
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        ((Object)((Object)this)).getClass();
        int effLeft = (this.field_73880_f - 325) / 2;
        ((Object)((Object)this)).getClass();
        int absLeft = effLeft - 4;
        ((Object)((Object)this)).getClass();
        int effTop = (this.field_73881_g - 180) / 2;
        ((Object)((Object)this)).getClass();
        int absTop = effTop - 4;
        ((Object)((Object)this)).getClass();
        int effRight = (this.field_73880_f + 325) / 2;
        ((Object)((Object)this)).getClass();
        int col1x = (this.field_73880_f - 325) / 2 + 100;
        ((Object)((Object)this)).getClass();
        int col2x = (this.field_73880_f + 325) / 2 - 65;
        int buttonColor = (this.bgcolor & 0xFFFFFF) + -16777216;
        this.chatScrollHistory.labelX = col1x;
        this.chatScrollHistory.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.chatScrollHistory.description), this.rowY(1));
        this.chatScrollHistory.setButtonDims(30, 11);
        this.field_73887_h.add(this.chatScrollHistory);
        this.maxLengthChannelName.labelX = col1x;
        this.maxLengthChannelName.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.maxLengthChannelName.description), this.rowY(2));
        this.maxLengthChannelName.setButtonDims(20, 11);
        this.field_73887_h.add(this.maxLengthChannelName);
        this.multiChatDelay.labelX = col1x;
        this.multiChatDelay.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.multiChatDelay.description), this.rowY(3));
        this.multiChatDelay.setButtonDims(40, 11);
        this.field_73887_h.add(this.multiChatDelay);
        this.customChatBoxSize.setButtonLoc(col1x, this.rowY(4));
        this.customChatBoxSize.labelX = col1x + 19;
        this.customChatBoxSize.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.customChatBoxSize);
        this.chatBoxWidth.labelX = col1x + 10;
        this.chatBoxWidth.setButtonLoc(col1x + 15 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.chatBoxWidth.description), this.rowY(5));
        this.chatBoxWidth.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.chatBoxWidth);
        this.chatBoxFocHeight.labelX = col1x + 10;
        this.chatBoxFocHeight.setButtonLoc(col1x + 15 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.chatBoxFocHeight.description), this.rowY(6));
        this.chatBoxFocHeight.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.chatBoxFocHeight);
        this.chatBoxUnfocHeight.labelX = col1x + 10;
        this.chatBoxUnfocHeight.setButtonLoc(col1x + 15 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.chatBoxUnfocHeight.description), this.rowY(7));
        this.chatBoxUnfocHeight.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.chatBoxUnfocHeight);
        this.chatFadeTicks.labelX = col1x;
        this.chatFadeTicks.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.chatFadeTicks.description), this.rowY(8));
        this.chatFadeTicks.buttonOnColor = buttonColor;
        this.chatFadeTicks.units = "";
        this.field_73887_h.add(this.chatFadeTicks);
        this.forceUnicode.setButtonLoc(col1x, this.rowY(9));
        this.forceUnicode.labelX = col1x + 19;
        this.forceUnicode.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.forceUnicode);
        this.validateButtonStates();
    }

    @Override
    public void validateButtonStates() {
        if (!this.customChatBoxSize.getTempValue().booleanValue()) {
            this.chatBoxWidth.disable();
            this.chatBoxFocHeight.disable();
            this.chatBoxUnfocHeight.disable();
        } else {
            this.chatBoxWidth.enable();
            this.chatBoxFocHeight.enable();
            this.chatBoxUnfocHeight.enable();
        }
    }

    @Override
    protected void storeTempVars() {
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

    @Override
    protected void resetTempVars() {
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

    protected void importSettings() {
        this.chatScrollHistory.setValue(Integer.toString(TCSettingsAdvanced.tc.globalPrefs.retainedChats));
        this.maxLengthChannelName.setValue(Integer.toString(TCSettingsAdvanced.tc.globalPrefs.maxChannelNameLength));
        this.resetTempVars();
    }

    @Override
    protected boolean loadSettingsFile() {
        this.settingsFile = new File(tabbyChatDir, "advanced.cfg");
        boolean loaded = false;
        if (!this.settingsFile.exists()) {
            return loaded;
        }
        Properties settingsTable = new Properties();
        try {
            FileInputStream fInStream = new FileInputStream(this.settingsFile);
            settingsTable.load(fInStream);
            fInStream.close();
        }
        catch (Exception e) {
            TabbyChat.printErr("Unable to read from advanced settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
        }
        this.chatScrollHistory.setValue(settingsTable.getProperty("chatScrollHistory"));
        this.maxLengthChannelName.setValue(settingsTable.getProperty("maxLengthChannelName"));
        this.multiChatDelay.setValue(settingsTable.getProperty("multiChatDelay"));
        this.customChatBoxSize.setValue(Boolean.parseBoolean(settingsTable.getProperty("customChatBoxSize")));
        this.chatBoxWidth.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxWidth"), 20.0f, 100.0f, 50.0f));
        this.chatBoxFocHeight.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxFocHeight"), 20.0f, 100.0f, 50.0f));
        this.chatBoxUnfocHeight.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatBoxUnfocHeight"), 20.0f, 100.0f, 20.0f));
        this.chatFadeTicks.setValue(TabbyChatUtils.parseFloat(settingsTable.getProperty("chatFadeTicks"), 10.0f, 2000.0f, 200.0f));
        this.forceUnicode.setValue(Boolean.parseBoolean(settingsTable.getProperty("forceUnicode")));
        loaded = true;
        this.resetTempVars();
        return loaded;
    }

    @Override
    protected void saveSettingsFile() {
        if (!tabbyChatDir.exists()) {
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
        try {
            FileOutputStream fOutStream = new FileOutputStream(this.settingsFile);
            settingsTable.store(fOutStream, "Advanced settings");
            fOutStream.close();
        }
        catch (Exception e) {
            TabbyChat.printErr("Unable to write to advanced settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
        }
    }
}

