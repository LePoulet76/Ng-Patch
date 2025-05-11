/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 */
package acs.tabbychat;

import acs.tabbychat.TCSettingBool;
import acs.tabbychat.TCSettingEnum;
import acs.tabbychat.TCSettingsGUI;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
import acs.tabbychat.TimeStampEnum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class TCSettingsGeneral
extends TCSettingsGUI {
    protected SimpleDateFormat timeStamp = new SimpleDateFormat();
    private static final int tabbyChatEnableID = 9101;
    private static final int saveChatLogID = 9102;
    private static final int timeStampEnableID = 9103;
    private static final int timeStampStyleID = 9104;
    private static final int groupSpamID = 9105;
    private static final int unreadFlashingID = 9106;
    public TCSettingBool tabbyChatEnable = new TCSettingBool(true, "TabbyChat Enabled", 9101);
    protected TCSettingBool saveChatLog = new TCSettingBool(false, "Log chat to file", 9102);
    public TCSettingBool timeStampEnable = new TCSettingBool(false, "Timestamp chat", 9103);
    public TCSettingEnum timeStampStyle = new TCSettingEnum(TimeStampEnum.MILITARY, "\u00a7oTimestamp Style\u00a7r", 9104);
    protected TCSettingBool groupSpam = new TCSettingBool(false, "Consolidate spammed chat", 9105);
    public TCSettingBool unreadFlashing = new TCSettingBool(true, "Unread notification flashing", 9106);

    public TCSettingsGeneral() {
        this.name = "General Config";
        this.bgcolor = 1715962558;
    }

    protected TCSettingsGeneral(TabbyChat _tc) {
        this();
        tc = _tc;
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
        this.tabbyChatEnable.setButtonLoc(col1x, this.rowY(1));
        this.tabbyChatEnable.labelX = col1x + 19;
        this.tabbyChatEnable.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.tabbyChatEnable);
        this.saveChatLog.setButtonLoc(col1x, this.rowY(2));
        this.saveChatLog.labelX = col1x + 19;
        this.saveChatLog.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.saveChatLog);
        this.timeStampEnable.setButtonLoc(col1x, this.rowY(3));
        this.timeStampEnable.labelX = col1x + 19;
        this.timeStampEnable.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.timeStampEnable);
        this.timeStampStyle.setButtonDims(80, 11);
        this.timeStampStyle.setButtonLoc(effRight - 80, this.rowY(4));
        this.timeStampStyle.labelX = this.timeStampStyle.field_73746_c - 10 - Minecraft.func_71410_x().field_71466_p.func_78256_a(this.timeStampStyle.description);
        this.field_73887_h.add(this.timeStampStyle);
        this.groupSpam.setButtonLoc(col1x, this.rowY(5));
        this.groupSpam.labelX = col1x + 19;
        this.groupSpam.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.groupSpam);
        this.unreadFlashing.setButtonLoc(col1x, this.rowY(6));
        this.unreadFlashing.labelX = col1x + 19;
        this.unreadFlashing.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.unreadFlashing);
        this.validateButtonStates();
    }

    @Override
    public void func_73875_a(GuiButton button) {
        super.func_73875_a(button);
        switch (button.field_73741_f) {
            case 9101: {
                if (TabbyChat.instance.enabled()) {
                    TabbyChat.instance.disable();
                    break;
                }
                TabbyChat.instance.enable();
            }
        }
        this.validateButtonStates();
    }

    @Override
    public void validateButtonStates() {
        this.timeStampStyle.field_73742_g = this.timeStampEnable.getTempValue();
    }

    protected void importSettings() {
        this.tabbyChatEnable.setValue(TCSettingsGeneral.tc.globalPrefs.TCenabled);
        this.saveChatLog.setValue(TCSettingsGeneral.tc.globalPrefs.saveLocalLogEnabled);
        this.timeStampEnable.setValue(TCSettingsGeneral.tc.globalPrefs.timestampsEnabled);
        this.timeStampStyle.setValue(TCSettingsGeneral.tc.globalPrefs.timestampStyle);
        this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
        this.resetTempVars();
    }

    @Override
    protected boolean loadSettingsFile() {
        this.settingsFile = new File(tabbyChatDir, "general.cfg");
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
            TabbyChat.printErr("Unable to read from general settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
        }
        this.tabbyChatEnable.setValue(Boolean.parseBoolean((String)settingsTable.get("tabbyChatEnable")));
        this.saveChatLog.setValue(Boolean.parseBoolean((String)settingsTable.get("saveChatLog")));
        this.timeStampEnable.setValue(Boolean.parseBoolean((String)settingsTable.get("timeStampEnable")));
        this.timeStampStyle.setValue(TabbyChatUtils.parseTimestamp((String)settingsTable.get("timeStampStyle")));
        this.groupSpam.setValue(Boolean.parseBoolean((String)settingsTable.get("groupSpam")));
        this.unreadFlashing.setValue(Boolean.parseBoolean((String)settingsTable.get("unreadFlashing")));
        loaded = true;
        this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
        this.resetTempVars();
        return loaded;
    }

    @Override
    protected void saveSettingsFile() {
        if (!tabbyChatDir.exists()) {
            tabbyChatDir.mkdirs();
        }
        Properties settingsTable = new Properties();
        settingsTable.put("tabbyChatEnable", this.tabbyChatEnable.getValue().toString());
        settingsTable.put("saveChatLog", this.saveChatLog.getValue().toString());
        settingsTable.put("timeStampEnable", this.timeStampEnable.getValue().toString());
        settingsTable.put("timeStampStyle", this.timeStampStyle.getValue().name());
        settingsTable.put("groupSpam", this.groupSpam.getValue().toString());
        settingsTable.put("unreadFlashing", this.unreadFlashing.getValue().toString());
        try {
            FileOutputStream fOutStream = new FileOutputStream(this.settingsFile);
            settingsTable.store(fOutStream, "General settings");
            fOutStream.close();
        }
        catch (Exception e) {
            TabbyChat.printErr("Unable to write to general settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
        }
    }

    @Override
    protected void storeTempVars() {
        this.tabbyChatEnable.save();
        this.saveChatLog.save();
        this.timeStampEnable.save();
        this.timeStampStyle.save();
        this.groupSpam.save();
        this.unreadFlashing.save();
        this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
    }

    @Override
    protected void resetTempVars() {
        this.tabbyChatEnable.reset();
        this.saveChatLog.reset();
        this.timeStampEnable.reset();
        this.timeStampStyle.reset();
        this.groupSpam.reset();
        this.unreadFlashing.reset();
    }
}

