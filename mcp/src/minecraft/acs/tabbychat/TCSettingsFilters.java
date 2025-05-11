/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.multiplayer.ServerData
 */
package acs.tabbychat;

import acs.tabbychat.ColorCodeEnum;
import acs.tabbychat.FormatCodeEnum;
import acs.tabbychat.NotificationSoundEnum;
import acs.tabbychat.PrefsButton;
import acs.tabbychat.ServerSettings;
import acs.tabbychat.TCSetting;
import acs.tabbychat.TCSettingBool;
import acs.tabbychat.TCSettingEnum;
import acs.tabbychat.TCSettingTextBox;
import acs.tabbychat.TCSettingsGUI;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.ServerData;

public class TCSettingsFilters
extends TCSettingsGUI {
    protected int curFilterId = 0;
    protected int numTempFilters = 0;
    protected int numFilters = 0;
    private static String lastMatch = "";
    private static final int inverseMatchID = 9301;
    private static final int caseSenseID = 9302;
    private static final int highlightBoolID = 9303;
    private static final int highlightColorID = 9304;
    private static final int highlightFormatID = 9305;
    private static final int audioNotificationBoolID = 9306;
    private static final int audioNotificationEnumID = 9307;
    private static final int prevButtonID = 9308;
    private static final int nextButtonID = 9309;
    private static final int filterNameID = 9310;
    private static final int sendToTabBoolID = 9311;
    private static final int sendToTabNameID = 9312;
    private static final int sendToAllTabsID = 9313;
    private static final int removeMatchesID = 9314;
    private static final int expressionID = 9315;
    private static final int addNewID = 9316;
    private static final int delID = 9317;
    protected TCSettingBool inverseMatch = new TCSettingBool(false, "Inverse match", 9301);
    protected TCSettingBool caseSensitive = new TCSettingBool(false, "Case Sensitive", 9302);
    protected TCSettingBool highlightBool = new TCSettingBool(true, "Highlight matches", 9303);
    protected TCSettingEnum highlightColor = new TCSettingEnum(ColorCodeEnum.YELLOW, "\u00a7oColor\u00a7r", 9304);
    protected TCSettingEnum highlightFormat = new TCSettingEnum(FormatCodeEnum.BOLD, "\u00a7oFormat\u00a7r", 9305);
    protected TCSettingBool audioNotificationBool = new TCSettingBool(false, "Audio notification", 9306);
    protected TCSettingEnum audioNotificationSound = new TCSettingEnum(NotificationSoundEnum.ORB, "\u00a7oSound\u00a7r", 9307);
    protected TCSettingTextBox filterName = new TCSettingTextBox("Filter Name", 9310);
    protected TCSettingBool sendToTabBool = new TCSettingBool(false, "Send matches to tab", 9311);
    protected TCSettingTextBox sendToTabName = new TCSettingTextBox("Tab Name", 9312);
    protected TCSettingBool sendToAllTabs = new TCSettingBool(false, "All tabs", 9313);
    protected TCSettingBool removeMatches = new TCSettingBool(false, "Hide matches from chat", 9314);
    protected TCSettingTextBox expressionString = new TCSettingTextBox("Expression", 9315);
    public HashMap filterMap = new HashMap();
    protected HashMap tempFilterMap = new HashMap();

    public TCSettingsFilters() {
        this.name = "Custom Filters";
        this.bgcolor = 1713938216;
        this.filterName.setCharLimit(50);
        this.sendToTabName.setCharLimit(20);
        this.expressionString.setCharLimit(600);
    }

    protected TCSettingsFilters(TabbyChat _tc) {
        this();
        tc = _tc;
    }

    protected boolean removeMatches(int ind) {
        return (Boolean)this.filterMap.get(Integer.toString(ind) + ".removeMatches");
    }

    protected boolean audioNotificationBool(int ind) {
        return (Boolean)this.filterMap.get(Integer.toString(ind) + ".audioNotificationBool");
    }

    protected boolean sendToAllTabs(int ind) {
        return (Boolean)this.filterMap.get(Integer.toString(ind) + ".sendToAllTabs");
    }

    protected boolean sendToTabBool(int ind) {
        return (Boolean)this.filterMap.get(Integer.toString(ind) + ".sendToTabBool");
    }

    protected String sendToTabName(int ind) {
        return (String)this.filterMap.get(Integer.toString(ind) + ".sendToTabName");
    }

    protected void audioNotification(int ind) {
        Minecraft.func_71410_x().field_71416_A.func_77366_a("random.orb", 1.0f, 1.0f);
    }

    private int addNewFilter() {
        int nextId = this.numTempFilters++;
        String sId = Integer.toString(nextId);
        this.tempFilterMap.put(sId + ".filterName", "New" + sId);
        this.tempFilterMap.put(sId + ".inverseMatch", false);
        this.tempFilterMap.put(sId + ".caseSensitive", false);
        this.tempFilterMap.put(sId + ".highlightBool", true);
        this.tempFilterMap.put(sId + ".highlightColor", ColorCodeEnum.YELLOW);
        this.tempFilterMap.put(sId + ".highlightFormat", FormatCodeEnum.BOLD);
        this.tempFilterMap.put(sId + ".audioNotificationBool", false);
        this.tempFilterMap.put(sId + ".audioNotificationSound", NotificationSoundEnum.ORB);
        this.tempFilterMap.put(sId + ".sendToTabBool", false);
        this.tempFilterMap.put(sId + ".sendToTabName", "");
        this.tempFilterMap.put(sId + ".sendToAllTabs", false);
        this.tempFilterMap.put(sId + ".removeMatches", false);
        this.tempFilterMap.put(sId + ".expressionString", "");
        return nextId;
    }

    private int deleteFilter() {
        String here;
        if (this.curFilterId < 0 || this.curFilterId > this.numTempFilters) {
            return 0;
        }
        for (int i = this.curFilterId; i < this.numTempFilters - 1; ++i) {
            here = Integer.toString(i);
            String next = Integer.toString(i + 1);
            this.tempFilterMap.put(here + ".filterName", this.tempFilterMap.get(next + ".filterName"));
            this.tempFilterMap.put(here + ".inverseMatch", this.tempFilterMap.get(next + ".inverseMatch"));
            this.tempFilterMap.put(here + ".caseSensitive", this.tempFilterMap.get(next + ".caseSensitive"));
            this.tempFilterMap.put(here + ".highlightBool", this.tempFilterMap.get(next + ".highlightBool"));
            this.tempFilterMap.put(here + ".highlightColor", this.tempFilterMap.get(next + ".highlightColor"));
            this.tempFilterMap.put(here + ".highlightFormat", this.tempFilterMap.get(next + ".highlightFormat"));
            this.tempFilterMap.put(here + ".audioNotificationBool", this.tempFilterMap.get(next + ".audioNotificationBool"));
            this.tempFilterMap.put(here + ".audioNotificationSound", this.tempFilterMap.get(next + ".audioNotificationSound"));
            this.tempFilterMap.put(here + ".sendToTabBool", this.tempFilterMap.get(next + ".sendToTabBool"));
            this.tempFilterMap.put(here + ".sendToTabName", this.tempFilterMap.get(next + ".sendToTabName"));
            this.tempFilterMap.put(here + ".sendToAllTabs", this.tempFilterMap.get(next + ".sendToAllTabs"));
            this.tempFilterMap.put(here + ".removeMatches", this.tempFilterMap.get(next + ".removeMatches"));
            this.tempFilterMap.put(here + ".expressionString", this.tempFilterMap.get(next + ".expressionString"));
        }
        here = Integer.toString(this.numTempFilters - 1);
        this.tempFilterMap.remove(here + ".filterName");
        this.tempFilterMap.remove(here + ".inverseMatch");
        this.tempFilterMap.remove(here + ".caseSensitive");
        this.tempFilterMap.remove(here + ".highlightBool");
        this.tempFilterMap.remove(here + ".highlightColor");
        this.tempFilterMap.remove(here + ".highlightFormat");
        this.tempFilterMap.remove(here + ".audioNotificationBool");
        this.tempFilterMap.remove(here + ".audioNotificationSound");
        this.tempFilterMap.remove(here + ".sendToTabBool");
        this.tempFilterMap.remove(here + ".sendToTabName");
        this.tempFilterMap.remove(here + ".sendToAllTabs");
        this.tempFilterMap.remove(here + ".removeMatches");
        this.tempFilterMap.remove(here + ".expressionString");
        --this.numTempFilters;
        return this.curFilterId > 0 ? this.curFilterId - 1 : 0;
    }

    private void displayFilter(int fId) {
        if (this.numTempFilters == 0 || !this.tempFilterMap.containsKey(Integer.toString(fId) + ".filterName")) {
            this.filterName.setTempValue("");
            this.inverseMatch.setTempValue(false);
            this.caseSensitive.setTempValue(false);
            this.highlightBool.setTempValue(true);
            this.highlightColor.setTempValue(ColorCodeEnum.YELLOW);
            this.highlightFormat.setTempValue(FormatCodeEnum.BOLD);
            this.audioNotificationBool.setTempValue(false);
            this.audioNotificationSound.setTempValue(NotificationSoundEnum.ORB);
            this.sendToTabBool.setTempValue(false);
            this.sendToAllTabs.setTempValue(false);
            this.sendToTabName.setTempValue("");
            this.removeMatches.setTempValue(false);
            this.expressionString.setTempValue("");
        } else {
            String sId = Integer.toString(fId);
            this.filterName.setTempValue((String)this.tempFilterMap.get(sId + ".filterName"));
            this.inverseMatch.setTempValue((Boolean)this.tempFilterMap.get(sId + ".inverseMatch"));
            this.caseSensitive.setTempValue((Boolean)this.tempFilterMap.get(sId + ".caseSensitive"));
            this.highlightBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".highlightBool"));
            this.highlightColor.setTempValue((ColorCodeEnum)((Object)this.tempFilterMap.get(sId + ".highlightColor")));
            this.highlightFormat.setTempValue((FormatCodeEnum)((Object)this.tempFilterMap.get(sId + ".highlightFormat")));
            this.audioNotificationBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".audioNotificationBool"));
            this.audioNotificationSound.setTempValue((NotificationSoundEnum)((Object)this.tempFilterMap.get(sId + ".audioNotificationSound")));
            this.sendToTabBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".sendToTabBool"));
            this.sendToTabName.setTempValue((String)this.tempFilterMap.get(sId + ".sendToTabName"));
            this.sendToAllTabs.setTempValue((Boolean)this.tempFilterMap.get(sId + ".sendToAllTabs"));
            this.removeMatches.setTempValue((Boolean)this.tempFilterMap.get(sId + ".removeMatches"));
            this.expressionString.setTempValue((String)this.tempFilterMap.get(sId + ".expressionString"));
            this.curFilterId = fId;
        }
    }

    private void storeTempFilter(int fId) {
        if (this.numTempFilters == 0) {
            return;
        }
        String sId = Integer.toString(fId);
        this.tempFilterMap.put(sId + ".filterName", this.filterName.getTempValue());
        this.tempFilterMap.put(sId + ".inverseMatch", this.inverseMatch.getTempValue());
        this.tempFilterMap.put(sId + ".caseSensitive", this.caseSensitive.getTempValue());
        this.tempFilterMap.put(sId + ".highlightBool", this.highlightBool.getTempValue());
        this.tempFilterMap.put(sId + ".highlightColor", this.highlightColor.getTempValue());
        this.tempFilterMap.put(sId + ".highlightFormat", this.highlightFormat.getTempValue());
        this.tempFilterMap.put(sId + ".audioNotificationBool", this.audioNotificationBool.getTempValue());
        this.tempFilterMap.put(sId + ".audioNotificationSound", this.audioNotificationSound.getTempValue());
        this.tempFilterMap.put(sId + ".sendToTabBool", this.sendToTabBool.getTempValue());
        this.tempFilterMap.put(sId + ".sendToTabName", this.sendToTabName.getTempValue());
        this.tempFilterMap.put(sId + ".sendToAllTabs", this.sendToAllTabs.getTempValue());
        this.tempFilterMap.put(sId + ".removeMatches", this.removeMatches.getTempValue());
        this.tempFilterMap.put(sId + ".expressionString", this.expressionString.getTempValue());
    }

    @Override
    public void func_73875_a(GuiButton button) {
        this.storeTempFilter(this.curFilterId);
        super.func_73875_a(button);
        switch (button.field_73741_f) {
            case 9316: {
                this.curFilterId = this.addNewFilter();
                this.displayFilter(this.curFilterId);
                break;
            }
            case 9317: {
                if (this.numTempFilters <= 0) break;
                this.curFilterId = this.deleteFilter();
                this.displayFilter(this.curFilterId);
                break;
            }
            case 9308: {
                if (this.numTempFilters <= 1) break;
                this.displayFilter(this.curFilterId > 0 ? this.curFilterId - 1 : this.numTempFilters - 1);
                break;
            }
            case 9309: {
                if (this.numTempFilters <= 1) break;
                this.displayFilter(this.curFilterId < this.numTempFilters - 1 ? this.curFilterId + 1 : 0);
            }
        }
        this.validateButtonStates();
    }

    @Override
    public void validateButtonStates() {
        this.inverseMatch.field_73742_g = this.highlightBool.getTempValue() == false;
        this.caseSensitive.field_73742_g = true;
        this.highlightBool.field_73742_g = this.removeMatches.getTempValue() == false && this.inverseMatch.getTempValue() == false;
        this.audioNotificationBool.field_73742_g = this.removeMatches.getTempValue() == false;
        this.removeMatches.field_73742_g = this.sendToTabBool.getTempValue() == false && this.highlightBool.getTempValue() == false && this.audioNotificationBool.getTempValue() == false;
        this.sendToTabBool.field_73742_g = this.removeMatches.getTempValue() == false;
        this.highlightColor.field_73742_g = this.highlightBool.getTempValue();
        this.highlightFormat.field_73742_g = this.highlightBool.getTempValue();
        this.audioNotificationSound.field_73742_g = this.audioNotificationBool.getTempValue();
        this.sendToAllTabs.field_73742_g = this.sendToTabBool.getTempValue();
        for (int i = 0; i < this.field_73887_h.size(); ++i) {
            if (!TCSetting.class.isInstance(this.field_73887_h.get(i))) continue;
            TCSetting tmp = (TCSetting)((Object)this.field_73887_h.get(i));
            if (this.numTempFilters == 0) {
                tmp.disable();
                continue;
            }
            if (tmp.type == "textbox") {
                tmp.enable();
                continue;
            }
            if (tmp.type != "bool") continue;
            ((TCSettingBool)tmp).setTempValue(((TCSettingBool)tmp).getTempValue() != false && tmp.field_73742_g);
        }
        this.sendToTabName.enabled(this.sendToTabBool.getTempValue() != false && this.sendToAllTabs.getTempValue() == false);
    }

    @Override
    public void func_73864_a(int par1, int par2, int par3) {
        if (this.audioNotificationSound.hovered(par1, par2).booleanValue()) {
            this.audioNotificationSound.mouseClicked(par1, par2, par3);
            Minecraft.func_71410_x().field_71416_A.func_77366_a(((NotificationSoundEnum)this.audioNotificationSound.tempValue).file(), 1.0f, 1.0f);
        } else {
            super.func_73864_a(par1, par2, par3);
        }
    }

    @Override
    protected void resetTempVars() {
        this.tempFilterMap.clear();
        this.curFilterId = 0;
        this.numTempFilters = 0;
        for (int i = 0; i < this.filterMap.size(); ++i) {
            String sId = Integer.toString(i);
            if (!this.filterMap.containsKey(sId + ".filterName")) {
                return;
            }
            this.tempFilterMap.put(sId + ".filterName", this.filterMap.get(sId + ".filterName"));
            this.tempFilterMap.put(sId + ".inverseMatch", this.filterMap.get(sId + ".inverseMatch"));
            this.tempFilterMap.put(sId + ".caseSensitive", this.filterMap.get(sId + ".caseSensitive"));
            this.tempFilterMap.put(sId + ".highlightBool", this.filterMap.get(sId + ".highlightBool"));
            this.tempFilterMap.put(sId + ".highlightColor", this.filterMap.get(sId + ".highlightColor"));
            this.tempFilterMap.put(sId + ".highlightFormat", this.filterMap.get(sId + ".highlightFormat"));
            this.tempFilterMap.put(sId + ".audioNotificationBool", this.filterMap.get(sId + ".audioNotificationBool"));
            this.tempFilterMap.put(sId + ".audioNotificationSound", this.filterMap.get(sId + ".audioNotificationSound"));
            this.tempFilterMap.put(sId + ".sendToTabBool", this.filterMap.get(sId + ".sendToTabBool"));
            this.tempFilterMap.put(sId + ".sendToTabName", this.filterMap.get(sId + ".sendToTabName"));
            this.tempFilterMap.put(sId + ".sendToAllTabs", this.filterMap.get(sId + ".sendToAllTabs"));
            this.tempFilterMap.put(sId + ".removeMatches", this.filterMap.get(sId + ".removeMatches"));
            this.tempFilterMap.put(sId + ".expressionString", this.filterMap.get(sId + ".expressionString"));
            ++this.numTempFilters;
        }
    }

    @Override
    protected void storeTempVars() {
        this.filterMap.clear();
        this.numFilters = 0;
        for (int i = 0; i < this.tempFilterMap.size(); ++i) {
            String sId = Integer.toString(i);
            if (!this.tempFilterMap.containsKey(sId + ".filterName")) {
                return;
            }
            this.filterMap.put(sId + ".filterName", this.tempFilterMap.get(sId + ".filterName"));
            this.filterMap.put(sId + ".inverseMatch", this.tempFilterMap.get(sId + ".inverseMatch"));
            this.filterMap.put(sId + ".caseSensitive", this.tempFilterMap.get(sId + ".caseSensitive"));
            this.filterMap.put(sId + ".highlightBool", this.tempFilterMap.get(sId + ".highlightBool"));
            this.filterMap.put(sId + ".highlightColor", this.tempFilterMap.get(sId + ".highlightColor"));
            this.filterMap.put(sId + ".highlightFormat", this.tempFilterMap.get(sId + ".highlightFormat"));
            this.filterMap.put(sId + ".audioNotificationBool", this.tempFilterMap.get(sId + ".audioNotificationBool"));
            this.filterMap.put(sId + ".audioNotificationSound", this.tempFilterMap.get(sId + ".audioNotificationSound"));
            this.filterMap.put(sId + ".sendToTabBool", this.tempFilterMap.get(sId + ".sendToTabBool"));
            this.filterMap.put(sId + ".sendToTabName", this.tempFilterMap.get(sId + ".sendToTabName"));
            this.filterMap.put(sId + ".sendToAllTabs", this.tempFilterMap.get(sId + ".sendToAllTabs"));
            this.filterMap.put(sId + ".removeMatches", this.tempFilterMap.get(sId + ".removeMatches"));
            try {
                this.filterMap.put(sId + ".expressionString", this.tempFilterMap.get(sId + ".expressionString"));
                if (((Boolean)this.tempFilterMap.get(sId + ".caseSensitive")).booleanValue()) {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile((String)this.tempFilterMap.get(sId + ".expressionString")));
                } else {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile((String)this.tempFilterMap.get(sId + ".expressionString"), 2));
                }
            }
            catch (PatternSyntaxException e) {
                this.filterMap.put(sId + ".expressionString", ".*");
                this.filterMap.put(sId + ".expressionPattern", Pattern.compile(".*"));
            }
            ++this.numFilters;
        }
    }

    protected void importSettings() {
        String sId = "0";
        this.filterMap.put(sId + ".filterName", "Tag");
        this.filterMap.put(sId + ".inverseMatch", false);
        this.filterMap.put(sId + ".caseSensitive", false);
        this.filterMap.put(sId + ".highlightBool", true);
        this.filterMap.put(sId + ".highlightColor", ColorCodeEnum.DARKRED);
        this.filterMap.put(sId + ".highlightFormat", FormatCodeEnum.BOLD);
        this.filterMap.put(sId + ".audioNotificationBool", true);
        this.filterMap.put(sId + ".audioNotificationSound", NotificationSoundEnum.ORB);
        this.filterMap.put(sId + ".sendToTabBool", false);
        this.filterMap.put(sId + ".sendToTabName", "");
        this.filterMap.put(sId + ".sendToAllTabs", false);
        this.filterMap.put(sId + ".removeMatches", false);
        this.filterMap.put(sId + ".expressionString", "\u00bb.*[ ](" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + ").*");
        this.filterMap.put(sId + ".expressionPattern", Pattern.compile("\u00bb.*[ ](" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + ").*"));
        ++this.numFilters;
        this.resetTempVars();
    }

    @Override
    protected boolean loadSettingsFile() {
        boolean loaded = false;
        this.filterMap.clear();
        this.numFilters = 0;
        ServerData server = ServerSettings.getServerData();
        if (server == null) {
            return loaded;
        }
        String sname = server.field_78847_a;
        String ip = server.field_78845_b;
        if (ip.contains(":")) {
            ip = ip.replaceAll(":", "(") + ")";
        }
        File settingsDir = new File(tabbyChatDir, ip);
        this.settingsFile = new File(settingsDir, "filters.cfg");
        if (!this.settingsFile.exists()) {
            return loaded;
        }
        Properties settingsTable = new Properties();
        try {
            FileInputStream fInStream = new FileInputStream(this.settingsFile);
            settingsTable.load(fInStream);
            fInStream.close();
            loaded = true;
        }
        catch (Exception e) {
            TabbyChat.printErr("Unable to read from filter settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
            loaded = false;
        }
        int _ind = settingsTable.size();
        for (int i = 0; i < _ind; ++i) {
            String sId = Integer.toString(i);
            if (!loaded || !settingsTable.containsKey(sId + ".filterName")) break;
            this.filterMap.put(sId + ".filterName", settingsTable.getProperty(sId + ".filterName"));
            this.filterMap.put(sId + ".inverseMatch", Boolean.parseBoolean(settingsTable.getProperty(sId + ".inverseMatch")));
            this.filterMap.put(sId + ".caseSensitive", Boolean.parseBoolean(settingsTable.getProperty(sId + ".caseSensitive")));
            this.filterMap.put(sId + ".highlightBool", Boolean.parseBoolean(settingsTable.getProperty(sId + ".highlightBool")));
            this.filterMap.put(sId + ".highlightColor", ColorCodeEnum.valueOf(settingsTable.getProperty(sId + ".highlightColor")));
            this.filterMap.put(sId + ".highlightFormat", FormatCodeEnum.valueOf(settingsTable.getProperty(sId + ".highlightFormat")));
            this.filterMap.put(sId + ".audioNotificationBool", Boolean.parseBoolean(settingsTable.getProperty(sId + ".audioNotificationBool")));
            this.filterMap.put(sId + ".audioNotificationSound", TabbyChatUtils.parseSound(settingsTable.getProperty(sId + ".audioNotificationSound")));
            this.filterMap.put(sId + ".sendToTabBool", Boolean.parseBoolean(settingsTable.getProperty(sId + ".sendToTabBool")));
            this.filterMap.put(sId + ".sendToTabName", settingsTable.getProperty(sId + ".sendToTabName"));
            this.filterMap.put(sId + ".sendToAllTabs", Boolean.parseBoolean(settingsTable.getProperty(sId + ".sendToAllTabs")));
            this.filterMap.put(sId + ".removeMatches", Boolean.parseBoolean(settingsTable.getProperty(sId + ".removeMatches")));
            try {
                this.filterMap.put(sId + ".expressionString", settingsTable.getProperty(sId + ".expressionString"));
                if (Boolean.parseBoolean(settingsTable.getProperty(sId + ".caseSensitive"))) {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile(settingsTable.getProperty(sId + ".expressionString")));
                } else {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile(settingsTable.getProperty(sId + ".expressionString"), 2));
                }
            }
            catch (PatternSyntaxException e) {
                this.filterMap.put(sId + ".expressionString", ".*");
                this.filterMap.put(sId + ".expressionPattern", Pattern.compile(".*"));
            }
            ++this.numFilters;
        }
        this.resetTempVars();
        return loaded;
    }

    @Override
    protected void saveSettingsFile() {
        File settingsDir;
        ServerData server = ServerSettings.getServerData();
        String sname = server.field_78847_a;
        String ip = server.field_78845_b;
        if (ip.contains(":")) {
            ip = ip.replaceAll(":", "(") + ")";
        }
        if (!(settingsDir = new File(tabbyChatDir, ip)).exists()) {
            settingsDir.mkdirs();
        }
        this.settingsFile = new File(settingsDir, "filters.cfg");
        Properties settingsTable = new Properties();
        Set filterKeys = this.filterMap.keySet();
        for (String key : filterKeys) {
            Object stg = this.filterMap.get(key);
            if (Enum.class.isInstance(stg)) {
                settingsTable.put(key, ((Enum)stg).name());
                continue;
            }
            settingsTable.put(key, stg.toString());
        }
        try {
            FileOutputStream fOutStream = new FileOutputStream(this.settingsFile);
            settingsTable.store(fOutStream, "Custom filters");
            fOutStream.close();
        }
        catch (Exception e) {
            TabbyChat.printErr("Unable to write to filter settings file : '" + e.getLocalizedMessage() + "' : " + e.toString());
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
        ((Object)((Object)this)).getClass();
        int n = (this.field_73881_g + 180) / 2;
        ((Object)((Object)this)).getClass();
        int n2 = n - 14;
        ((Object)((Object)this)).getClass();
        PrefsButton newButton = new PrefsButton(9316, col1x, n2, 45, 14, "New");
        ((Object)((Object)this)).getClass();
        int n3 = (this.field_73881_g + 180) / 2;
        ((Object)((Object)this)).getClass();
        int n4 = n3 - 14;
        ((Object)((Object)this)).getClass();
        PrefsButton delButton = new PrefsButton(9317, col1x + 50, n4, 45, 14, "Delete");
        newButton.bgcolor = this.bgcolor;
        delButton.bgcolor = this.bgcolor;
        this.field_73887_h.add(newButton);
        this.field_73887_h.add(delButton);
        this.filterName.setButtonDims(100, 11);
        this.filterName.labelX = col1x;
        this.filterName.setButtonLoc(col1x + 33 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.filterName.description), this.rowY(1));
        this.field_73887_h.add(this.filterName);
        int n5 = this.filterName.field_73746_c - 23;
        int n6 = this.rowY(1);
        ((Object)((Object)this)).getClass();
        PrefsButton prevButton = new PrefsButton(9308, n5, n6, 20, 14, "<<");
        int n7 = this.filterName.field_73746_c + 103;
        int n8 = this.rowY(1);
        ((Object)((Object)this)).getClass();
        PrefsButton nextButton = new PrefsButton(9309, n7, n8, 20, 14, ">>");
        this.field_73887_h.add(prevButton);
        this.field_73887_h.add(nextButton);
        this.sendToTabBool.setButtonLoc(col1x, this.rowY(2));
        this.sendToTabBool.labelX = col1x + 19;
        this.sendToTabBool.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.sendToTabBool);
        this.sendToAllTabs.setButtonLoc(col1x + 20, this.rowY(3));
        this.sendToAllTabs.labelX = col1x + 39;
        this.sendToAllTabs.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.sendToAllTabs);
        this.sendToTabName.labelX = effRight - Minecraft.func_71410_x().field_71466_p.func_78256_a(this.sendToTabName.description) - 55;
        this.sendToTabName.setButtonLoc(effRight - 50, this.rowY(3));
        this.sendToTabName.setButtonDims(50, 11);
        this.field_73887_h.add(this.sendToTabName);
        this.removeMatches.setButtonLoc(col1x, this.rowY(4));
        this.removeMatches.labelX = col1x + 19;
        this.removeMatches.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.removeMatches);
        this.highlightBool.setButtonLoc(col1x, this.rowY(5));
        this.highlightBool.labelX = col1x + 19;
        this.highlightBool.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.highlightBool);
        this.highlightColor.setButtonDims(70, 11);
        this.highlightColor.setButtonLoc(col1x + 15 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.highlightColor.description), this.rowY(6));
        this.highlightColor.labelX = col1x + 10;
        this.field_73887_h.add(this.highlightColor);
        this.highlightFormat.setButtonDims(60, 11);
        this.highlightFormat.setButtonLoc(effRight - 60, this.rowY(6));
        this.highlightFormat.labelX = this.highlightFormat.field_73746_c - 5 - Minecraft.func_71410_x().field_71466_p.func_78256_a(this.highlightFormat.description);
        this.field_73887_h.add(this.highlightFormat);
        this.audioNotificationBool.setButtonLoc(col1x, this.rowY(7));
        this.audioNotificationBool.labelX = col1x + 19;
        this.audioNotificationBool.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.audioNotificationBool);
        this.audioNotificationSound.setButtonDims(60, 11);
        this.audioNotificationSound.setButtonLoc(effRight - 60, this.rowY(7));
        this.audioNotificationSound.labelX = this.audioNotificationSound.field_73746_c - 5 - Minecraft.func_71410_x().field_71466_p.func_78256_a(this.audioNotificationSound.description);
        this.field_73887_h.add(this.audioNotificationSound);
        this.inverseMatch.setButtonLoc(col1x, this.rowY(8));
        this.inverseMatch.labelX = col1x + 19;
        this.inverseMatch.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.inverseMatch);
        this.caseSensitive.labelX = effRight - Minecraft.func_71410_x().field_71466_p.func_78256_a(this.caseSensitive.description);
        this.caseSensitive.setButtonLoc(this.caseSensitive.labelX - 19, this.rowY(8));
        this.caseSensitive.buttonOnColor = buttonColor;
        this.field_73887_h.add(this.caseSensitive);
        this.expressionString.labelX = col1x;
        this.expressionString.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.expressionString.description), this.rowY(9));
        this.expressionString.setButtonDims(effRight - this.expressionString.field_73746_c, 11);
        this.field_73887_h.add(this.expressionString);
        this.displayFilter(0);
        this.validateButtonStates();
    }

    protected boolean applyFilterToDirtyChat(int filterNum, String input) {
        if (filterNum >= this.numFilters) {
            return false;
        }
        String fNum = Integer.toString(filterNum);
        Boolean caseSensitive = (Boolean)this.filterMap.get(fNum + ".caseSensitive");
        Boolean inverseMatch = (Boolean)this.filterMap.get(fNum + ".inverseMatch");
        Boolean highlightBool = (Boolean)this.filterMap.get(fNum + ".highlightBool");
        ColorCodeEnum highlightColor = (ColorCodeEnum)((Object)this.filterMap.get(fNum + ".highlightColor"));
        FormatCodeEnum highlightFormat = (FormatCodeEnum)((Object)this.filterMap.get(fNum + ".highlightFormat"));
        String expressionString = new String((String)this.filterMap.get(fNum + ".expressionString"));
        if (expressionString.equals("")) {
            return false;
        }
        Pattern filter = (Pattern)this.filterMap.get(fNum + ".expressionPattern");
        Pattern pullCodes = Pattern.compile("(?i)(\\u00A7[0-9A-FK-OR])+");
        int _start = 0;
        int _end = 0;
        TreeMap<Integer, String> chatCodes = new TreeMap<Integer, String>();
        HashMap<Integer, String> hlCodes = new HashMap<Integer, String>();
        StringBuilder result = new StringBuilder(input);
        Matcher matchCodes = pullCodes.matcher(result.toString());
        while (matchCodes.find()) {
            _start = matchCodes.start();
            _end = matchCodes.end();
            chatCodes.put(_start, result.substring(_start, _end));
            result.replace(_start, _end, "");
            matchCodes = pullCodes.matcher(result.toString());
        }
        Matcher matchFilter = filter.matcher(result.toString());
        boolean matched = false;
        String prefix = highlightColor.toCode() + highlightFormat.toCode();
        String suffix = "\u00a7r";
        while (matchFilter.find()) {
            matched = true;
            if (!highlightBool.booleanValue()) break;
            _start = matchFilter.start();
            _end = matchFilter.end();
            Map.Entry newSuffix = chatCodes.lowerEntry(_end);
            hlCodes.put(_start, prefix);
            if (newSuffix == null) {
                hlCodes.put(_end, suffix);
                continue;
            }
            hlCodes.put(_end, (String)newSuffix.getValue());
        }
        if (highlightBool.booleanValue()) {
            chatCodes.putAll(hlCodes);
            Map.Entry ptr = chatCodes.pollLastEntry();
            while (ptr != null) {
                result.insert((int)((Integer)ptr.getKey()), (String)ptr.getValue());
                ptr = chatCodes.pollLastEntry();
            }
            lastMatch = result.toString();
        } else {
            lastMatch = input;
        }
        if (!matched && inverseMatch.booleanValue()) {
            return true;
        }
        if (matched && !inverseMatch.booleanValue()) {
            return true;
        }
        if (matched && inverseMatch.booleanValue()) {
            return false;
        }
        if (!matched && !inverseMatch.booleanValue()) {
            return false;
        }
        return false;
    }

    protected String getLastMatchPretty() {
        String tmp = new String(lastMatch);
        lastMatch = "";
        return tmp;
    }
}

