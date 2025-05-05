package acs.tabbychat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.ServerData;

public class TCSettingsFilters extends TCSettingsGUI
{
    protected int curFilterId;
    protected int numTempFilters;
    protected int numFilters;
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
    protected TCSettingBool inverseMatch;
    protected TCSettingBool caseSensitive;
    protected TCSettingBool highlightBool;
    protected TCSettingEnum highlightColor;
    protected TCSettingEnum highlightFormat;
    protected TCSettingBool audioNotificationBool;
    protected TCSettingEnum audioNotificationSound;
    protected TCSettingTextBox filterName;
    protected TCSettingBool sendToTabBool;
    protected TCSettingTextBox sendToTabName;
    protected TCSettingBool sendToAllTabs;
    protected TCSettingBool removeMatches;
    protected TCSettingTextBox expressionString;
    public HashMap filterMap;
    protected HashMap tempFilterMap;

    public TCSettingsFilters()
    {
        this.curFilterId = 0;
        this.numTempFilters = 0;
        this.numFilters = 0;
        this.inverseMatch = new TCSettingBool(Boolean.valueOf(false), "Inverse match", 9301);
        this.caseSensitive = new TCSettingBool(Boolean.valueOf(false), "Case Sensitive", 9302);
        this.highlightBool = new TCSettingBool(Boolean.valueOf(true), "Highlight matches", 9303);
        this.highlightColor = new TCSettingEnum(ColorCodeEnum.YELLOW, "\u00a7oColor\u00a7r", 9304);
        this.highlightFormat = new TCSettingEnum(FormatCodeEnum.BOLD, "\u00a7oFormat\u00a7r", 9305);
        this.audioNotificationBool = new TCSettingBool(Boolean.valueOf(false), "Audio notification", 9306);
        this.audioNotificationSound = new TCSettingEnum(NotificationSoundEnum.ORB, "\u00a7oSound\u00a7r", 9307);
        this.filterName = new TCSettingTextBox("Filter Name", 9310);
        this.sendToTabBool = new TCSettingBool(Boolean.valueOf(false), "Send matches to tab", 9311);
        this.sendToTabName = new TCSettingTextBox("Tab Name", 9312);
        this.sendToAllTabs = new TCSettingBool(Boolean.valueOf(false), "All tabs", 9313);
        this.removeMatches = new TCSettingBool(Boolean.valueOf(false), "Hide matches from chat", 9314);
        this.expressionString = new TCSettingTextBox("Expression", 9315);
        this.filterMap = new HashMap();
        this.tempFilterMap = new HashMap();
        this.name = "Custom Filters";
        this.bgcolor = 1713938216;
        this.filterName.setCharLimit(50);
        this.sendToTabName.setCharLimit(20);
        this.expressionString.setCharLimit(600);
    }

    protected TCSettingsFilters(TabbyChat _tc)
    {
        this();
        tc = _tc;
    }

    protected boolean removeMatches(int ind)
    {
        return ((Boolean)this.filterMap.get(Integer.toString(ind) + ".removeMatches")).booleanValue();
    }

    protected boolean audioNotificationBool(int ind)
    {
        return ((Boolean)this.filterMap.get(Integer.toString(ind) + ".audioNotificationBool")).booleanValue();
    }

    protected boolean sendToAllTabs(int ind)
    {
        return ((Boolean)this.filterMap.get(Integer.toString(ind) + ".sendToAllTabs")).booleanValue();
    }

    protected boolean sendToTabBool(int ind)
    {
        return ((Boolean)this.filterMap.get(Integer.toString(ind) + ".sendToTabBool")).booleanValue();
    }

    protected String sendToTabName(int ind)
    {
        return (String)this.filterMap.get(Integer.toString(ind) + ".sendToTabName");
    }

    protected void audioNotification(int ind)
    {
        Minecraft.getMinecraft().sndManager.playSoundFX("random.orb", 1.0F, 1.0F);
    }

    private int addNewFilter()
    {
        int nextId = this.numTempFilters++;
        String sId = Integer.toString(nextId);
        this.tempFilterMap.put(sId + ".filterName", "New" + sId);
        this.tempFilterMap.put(sId + ".inverseMatch", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".caseSensitive", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".highlightBool", Boolean.valueOf(true));
        this.tempFilterMap.put(sId + ".highlightColor", ColorCodeEnum.YELLOW);
        this.tempFilterMap.put(sId + ".highlightFormat", FormatCodeEnum.BOLD);
        this.tempFilterMap.put(sId + ".audioNotificationBool", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".audioNotificationSound", NotificationSoundEnum.ORB);
        this.tempFilterMap.put(sId + ".sendToTabBool", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".sendToTabName", "");
        this.tempFilterMap.put(sId + ".sendToAllTabs", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".removeMatches", Boolean.valueOf(false));
        this.tempFilterMap.put(sId + ".expressionString", "");
        return nextId;
    }

    private int deleteFilter()
    {
        if (this.curFilterId >= 0 && this.curFilterId <= this.numTempFilters)
        {
            String here;

            for (int i = this.curFilterId; i < this.numTempFilters - 1; ++i)
            {
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
        else
        {
            return 0;
        }
    }

    private void displayFilter(int fId)
    {
        if (this.numTempFilters != 0 && this.tempFilterMap.containsKey(Integer.toString(fId) + ".filterName"))
        {
            String sId = Integer.toString(fId);
            this.filterName.setTempValue((String)this.tempFilterMap.get(sId + ".filterName"));
            this.inverseMatch.setTempValue((Boolean)this.tempFilterMap.get(sId + ".inverseMatch"));
            this.caseSensitive.setTempValue((Boolean)this.tempFilterMap.get(sId + ".caseSensitive"));
            this.highlightBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".highlightBool"));
            this.highlightColor.setTempValue((ColorCodeEnum)this.tempFilterMap.get(sId + ".highlightColor"));
            this.highlightFormat.setTempValue((FormatCodeEnum)this.tempFilterMap.get(sId + ".highlightFormat"));
            this.audioNotificationBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".audioNotificationBool"));
            this.audioNotificationSound.setTempValue((NotificationSoundEnum)this.tempFilterMap.get(sId + ".audioNotificationSound"));
            this.sendToTabBool.setTempValue((Boolean)this.tempFilterMap.get(sId + ".sendToTabBool"));
            this.sendToTabName.setTempValue((String)this.tempFilterMap.get(sId + ".sendToTabName"));
            this.sendToAllTabs.setTempValue((Boolean)this.tempFilterMap.get(sId + ".sendToAllTabs"));
            this.removeMatches.setTempValue((Boolean)this.tempFilterMap.get(sId + ".removeMatches"));
            this.expressionString.setTempValue((String)this.tempFilterMap.get(sId + ".expressionString"));
            this.curFilterId = fId;
        }
        else
        {
            this.filterName.setTempValue("");
            this.inverseMatch.setTempValue(Boolean.valueOf(false));
            this.caseSensitive.setTempValue(Boolean.valueOf(false));
            this.highlightBool.setTempValue(Boolean.valueOf(true));
            this.highlightColor.setTempValue(ColorCodeEnum.YELLOW);
            this.highlightFormat.setTempValue(FormatCodeEnum.BOLD);
            this.audioNotificationBool.setTempValue(Boolean.valueOf(false));
            this.audioNotificationSound.setTempValue(NotificationSoundEnum.ORB);
            this.sendToTabBool.setTempValue(Boolean.valueOf(false));
            this.sendToAllTabs.setTempValue(Boolean.valueOf(false));
            this.sendToTabName.setTempValue("");
            this.removeMatches.setTempValue(Boolean.valueOf(false));
            this.expressionString.setTempValue("");
        }
    }

    private void storeTempFilter(int fId)
    {
        if (this.numTempFilters != 0)
        {
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
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton button)
    {
        this.storeTempFilter(this.curFilterId);
        super.actionPerformed(button);

        switch (button.id)
        {
            case 9308:
                if (this.numTempFilters > 1)
                {
                    this.displayFilter(this.curFilterId > 0 ? this.curFilterId - 1 : this.numTempFilters - 1);
                }

                break;

            case 9309:
                if (this.numTempFilters > 1)
                {
                    this.displayFilter(this.curFilterId < this.numTempFilters - 1 ? this.curFilterId + 1 : 0);
                }

            case 9310:
            case 9311:
            case 9312:
            case 9313:
            case 9314:
            case 9315:
            default:
                break;

            case 9316:
                this.curFilterId = this.addNewFilter();
                this.displayFilter(this.curFilterId);
                break;

            case 9317:
                if (this.numTempFilters > 0)
                {
                    this.curFilterId = this.deleteFilter();
                    this.displayFilter(this.curFilterId);
                }
        }

        this.validateButtonStates();
    }

    public void validateButtonStates()
    {
        this.inverseMatch.enabled = !this.highlightBool.getTempValue().booleanValue();
        this.caseSensitive.enabled = true;
        this.highlightBool.enabled = !this.removeMatches.getTempValue().booleanValue() && !this.inverseMatch.getTempValue().booleanValue();
        this.audioNotificationBool.enabled = !this.removeMatches.getTempValue().booleanValue();
        this.removeMatches.enabled = !this.sendToTabBool.getTempValue().booleanValue() && !this.highlightBool.getTempValue().booleanValue() && !this.audioNotificationBool.getTempValue().booleanValue();
        this.sendToTabBool.enabled = !this.removeMatches.getTempValue().booleanValue();
        this.highlightColor.enabled = this.highlightBool.getTempValue().booleanValue();
        this.highlightFormat.enabled = this.highlightBool.getTempValue().booleanValue();
        this.audioNotificationSound.enabled = this.audioNotificationBool.getTempValue().booleanValue();
        this.sendToAllTabs.enabled = this.sendToTabBool.getTempValue().booleanValue();

        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            if (TCSetting.class.isInstance(this.buttonList.get(i)))
            {
                TCSetting tmp = (TCSetting)this.buttonList.get(i);

                if (this.numTempFilters == 0)
                {
                    tmp.disable();
                }
                else if (tmp.type == "textbox")
                {
                    tmp.enable();
                }
                else if (tmp.type == "bool")
                {
                    ((TCSettingBool)tmp).setTempValue(Boolean.valueOf(((TCSettingBool)tmp).getTempValue().booleanValue() && tmp.enabled));
                }
            }
        }

        this.sendToTabName.enabled(this.sendToTabBool.getTempValue().booleanValue() && !this.sendToAllTabs.getTempValue().booleanValue());
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int par1, int par2, int par3)
    {
        if (this.audioNotificationSound.hovered(par1, par2).booleanValue())
        {
            this.audioNotificationSound.mouseClicked(par1, par2, par3);
            Minecraft.getMinecraft().sndManager.playSoundFX(((NotificationSoundEnum)this.audioNotificationSound.tempValue).file(), 1.0F, 1.0F);
        }
        else
        {
            super.mouseClicked(par1, par2, par3);
        }
    }

    protected void resetTempVars()
    {
        this.tempFilterMap.clear();
        this.curFilterId = 0;
        this.numTempFilters = 0;

        for (int i = 0; i < this.filterMap.size(); ++i)
        {
            String sId = Integer.toString(i);

            if (!this.filterMap.containsKey(sId + ".filterName"))
            {
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

    protected void storeTempVars()
    {
        this.filterMap.clear();
        this.numFilters = 0;

        for (int i = 0; i < this.tempFilterMap.size(); ++i)
        {
            String sId = Integer.toString(i);

            if (!this.tempFilterMap.containsKey(sId + ".filterName"))
            {
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

            try
            {
                this.filterMap.put(sId + ".expressionString", this.tempFilterMap.get(sId + ".expressionString"));

                if (((Boolean)this.tempFilterMap.get(sId + ".caseSensitive")).booleanValue())
                {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile((String)this.tempFilterMap.get(sId + ".expressionString")));
                }
                else
                {
                    this.filterMap.put(sId + ".expressionPattern", Pattern.compile((String)this.tempFilterMap.get(sId + ".expressionString"), 2));
                }
            }
            catch (PatternSyntaxException var4)
            {
                this.filterMap.put(sId + ".expressionString", ".*");
                this.filterMap.put(sId + ".expressionPattern", Pattern.compile(".*"));
            }

            ++this.numFilters;
        }
    }

    protected void importSettings()
    {
        String sId = "0";
        this.filterMap.put(sId + ".filterName", "Tag");
        this.filterMap.put(sId + ".inverseMatch", Boolean.valueOf(false));
        this.filterMap.put(sId + ".caseSensitive", Boolean.valueOf(false));
        this.filterMap.put(sId + ".highlightBool", Boolean.valueOf(true));
        this.filterMap.put(sId + ".highlightColor", ColorCodeEnum.DARKRED);
        this.filterMap.put(sId + ".highlightFormat", FormatCodeEnum.BOLD);
        this.filterMap.put(sId + ".audioNotificationBool", Boolean.valueOf(true));
        this.filterMap.put(sId + ".audioNotificationSound", NotificationSoundEnum.ORB);
        this.filterMap.put(sId + ".sendToTabBool", Boolean.valueOf(false));
        this.filterMap.put(sId + ".sendToTabName", "");
        this.filterMap.put(sId + ".sendToAllTabs", Boolean.valueOf(false));
        this.filterMap.put(sId + ".removeMatches", Boolean.valueOf(false));
        this.filterMap.put(sId + ".expressionString", "\u00bb.*[ ](" + Minecraft.getMinecraft().thePlayer.username + ").*");
        this.filterMap.put(sId + ".expressionPattern", Pattern.compile("\u00bb.*[ ](" + Minecraft.getMinecraft().thePlayer.username + ").*"));
        ++this.numFilters;
        this.resetTempVars();
    }

    protected boolean loadSettingsFile()
    {
        boolean loaded = false;
        this.filterMap.clear();
        this.numFilters = 0;
        ServerData server = ServerSettings.getServerData();

        if (server == null)
        {
            return loaded;
        }
        else
        {
            String sname = server.serverName;
            String ip = server.serverIP;

            if (ip.contains(":"))
            {
                ip = ip.replaceAll(":", "(") + ")";
            }

            File settingsDir = new File(tabbyChatDir, ip);
            this.settingsFile = new File(settingsDir, "filters.cfg");

            if (!this.settingsFile.exists())
            {
                return loaded;
            }
            else
            {
                Properties settingsTable = new Properties();

                try
                {
                    FileInputStream sId = new FileInputStream(this.settingsFile);
                    settingsTable.load(sId);
                    sId.close();
                    loaded = true;
                }
                catch (Exception var12)
                {
                    TabbyChat.printErr("Unable to read from filter settings file : \'" + var12.getLocalizedMessage() + "\' : " + var12.toString());
                    loaded = false;
                }

                int _ind = settingsTable.size();

                for (int i = 0; i < _ind; ++i)
                {
                    String var13 = Integer.toString(i);

                    if (!loaded || !settingsTable.containsKey(var13 + ".filterName"))
                    {
                        break;
                    }

                    this.filterMap.put(var13 + ".filterName", settingsTable.getProperty(var13 + ".filterName"));
                    this.filterMap.put(var13 + ".inverseMatch", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".inverseMatch"))));
                    this.filterMap.put(var13 + ".caseSensitive", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".caseSensitive"))));
                    this.filterMap.put(var13 + ".highlightBool", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".highlightBool"))));
                    this.filterMap.put(var13 + ".highlightColor", ColorCodeEnum.valueOf(settingsTable.getProperty(var13 + ".highlightColor")));
                    this.filterMap.put(var13 + ".highlightFormat", FormatCodeEnum.valueOf(settingsTable.getProperty(var13 + ".highlightFormat")));
                    this.filterMap.put(var13 + ".audioNotificationBool", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".audioNotificationBool"))));
                    this.filterMap.put(var13 + ".audioNotificationSound", TabbyChatUtils.parseSound(settingsTable.getProperty(var13 + ".audioNotificationSound")));
                    this.filterMap.put(var13 + ".sendToTabBool", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".sendToTabBool"))));
                    this.filterMap.put(var13 + ".sendToTabName", settingsTable.getProperty(var13 + ".sendToTabName"));
                    this.filterMap.put(var13 + ".sendToAllTabs", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".sendToAllTabs"))));
                    this.filterMap.put(var13 + ".removeMatches", Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty(var13 + ".removeMatches"))));

                    try
                    {
                        this.filterMap.put(var13 + ".expressionString", settingsTable.getProperty(var13 + ".expressionString"));

                        if (Boolean.parseBoolean(settingsTable.getProperty(var13 + ".caseSensitive")))
                        {
                            this.filterMap.put(var13 + ".expressionPattern", Pattern.compile(settingsTable.getProperty(var13 + ".expressionString")));
                        }
                        else
                        {
                            this.filterMap.put(var13 + ".expressionPattern", Pattern.compile(settingsTable.getProperty(var13 + ".expressionString"), 2));
                        }
                    }
                    catch (PatternSyntaxException var11)
                    {
                        this.filterMap.put(var13 + ".expressionString", ".*");
                        this.filterMap.put(var13 + ".expressionPattern", Pattern.compile(".*"));
                    }

                    ++this.numFilters;
                }

                this.resetTempVars();
                return loaded;
            }
        }
    }

    protected void saveSettingsFile()
    {
        ServerData server = ServerSettings.getServerData();
        String sname = server.serverName;
        String ip = server.serverIP;

        if (ip.contains(":"))
        {
            ip = ip.replaceAll(":", "(") + ")";
        }

        File settingsDir = new File(tabbyChatDir, ip);

        if (!settingsDir.exists())
        {
            settingsDir.mkdirs();
        }

        this.settingsFile = new File(settingsDir, "filters.cfg");
        Properties settingsTable = new Properties();
        Set filterKeys = this.filterMap.keySet();
        Iterator e = filterKeys.iterator();

        while (e.hasNext())
        {
            String key = (String)e.next();
            Object stg = this.filterMap.get(key);

            if (Enum.class.isInstance(stg))
            {
                settingsTable.put(key, ((Enum)stg).name());
            }
            else
            {
                settingsTable.put(key, stg.toString());
            }
        }

        try
        {
            FileOutputStream e1 = new FileOutputStream(this.settingsFile);
            settingsTable.store(e1, "Custom filters");
            e1.close();
        }
        catch (Exception var10)
        {
            TabbyChat.printErr("Unable to write to filter settings file : \'" + var10.getLocalizedMessage() + "\' : " + var10.toString());
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
        PrefsButton var13 = new PrefsButton;
        int var10004 = this.height;
        this.getClass();
        var10004 = (var10004 + 180) / 2;
        this.getClass();
        var10004 -= 14;
        this.getClass();
        var13.<init>(9316, col1x, var10004, 45, 14, "New");
        PrefsButton newButton = var13;
        var13 = new PrefsButton;
        int var10003 = col1x + 50;
        var10004 = this.height;
        this.getClass();
        var10004 = (var10004 + 180) / 2;
        this.getClass();
        var10004 -= 14;
        this.getClass();
        var13.<init>(9317, var10003, var10004, 45, 14, "Delete");
        PrefsButton delButton = var13;
        newButton.bgcolor = this.bgcolor;
        delButton.bgcolor = this.bgcolor;
        this.buttonList.add(newButton);
        this.buttonList.add(delButton);
        this.filterName.setButtonDims(100, 11);
        this.filterName.labelX = col1x;
        this.filterName.setButtonLoc(col1x + 33 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.filterName.description), this.rowY(1));
        this.buttonList.add(this.filterName);
        var13 = new PrefsButton;
        var10003 = this.filterName.xPosition - 23;
        var10004 = this.rowY(1);
        this.getClass();
        var13.<init>(9308, var10003, var10004, 20, 14, "<<");
        PrefsButton prevButton = var13;
        var13 = new PrefsButton;
        var10003 = this.filterName.xPosition + 103;
        var10004 = this.rowY(1);
        this.getClass();
        var13.<init>(9309, var10003, var10004, 20, 14, ">>");
        PrefsButton nextButton = var13;
        this.buttonList.add(prevButton);
        this.buttonList.add(nextButton);
        this.sendToTabBool.setButtonLoc(col1x, this.rowY(2));
        this.sendToTabBool.labelX = col1x + 19;
        this.sendToTabBool.buttonOnColor = buttonColor;
        this.buttonList.add(this.sendToTabBool);
        this.sendToAllTabs.setButtonLoc(col1x + 20, this.rowY(3));
        this.sendToAllTabs.labelX = col1x + 39;
        this.sendToAllTabs.buttonOnColor = buttonColor;
        this.buttonList.add(this.sendToAllTabs);
        this.sendToTabName.labelX = effRight - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.sendToTabName.description) - 55;
        this.sendToTabName.setButtonLoc(effRight - 50, this.rowY(3));
        this.sendToTabName.setButtonDims(50, 11);
        this.buttonList.add(this.sendToTabName);
        this.removeMatches.setButtonLoc(col1x, this.rowY(4));
        this.removeMatches.labelX = col1x + 19;
        this.removeMatches.buttonOnColor = buttonColor;
        this.buttonList.add(this.removeMatches);
        this.highlightBool.setButtonLoc(col1x, this.rowY(5));
        this.highlightBool.labelX = col1x + 19;
        this.highlightBool.buttonOnColor = buttonColor;
        this.buttonList.add(this.highlightBool);
        this.highlightColor.setButtonDims(70, 11);
        this.highlightColor.setButtonLoc(col1x + 15 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.highlightColor.description), this.rowY(6));
        this.highlightColor.labelX = col1x + 10;
        this.buttonList.add(this.highlightColor);
        this.highlightFormat.setButtonDims(60, 11);
        this.highlightFormat.setButtonLoc(effRight - 60, this.rowY(6));
        this.highlightFormat.labelX = this.highlightFormat.xPosition - 5 - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.highlightFormat.description);
        this.buttonList.add(this.highlightFormat);
        this.audioNotificationBool.setButtonLoc(col1x, this.rowY(7));
        this.audioNotificationBool.labelX = col1x + 19;
        this.audioNotificationBool.buttonOnColor = buttonColor;
        this.buttonList.add(this.audioNotificationBool);
        this.audioNotificationSound.setButtonDims(60, 11);
        this.audioNotificationSound.setButtonLoc(effRight - 60, this.rowY(7));
        this.audioNotificationSound.labelX = this.audioNotificationSound.xPosition - 5 - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.audioNotificationSound.description);
        this.buttonList.add(this.audioNotificationSound);
        this.inverseMatch.setButtonLoc(col1x, this.rowY(8));
        this.inverseMatch.labelX = col1x + 19;
        this.inverseMatch.buttonOnColor = buttonColor;
        this.buttonList.add(this.inverseMatch);
        this.caseSensitive.labelX = effRight - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.caseSensitive.description);
        this.caseSensitive.setButtonLoc(this.caseSensitive.labelX - 19, this.rowY(8));
        this.caseSensitive.buttonOnColor = buttonColor;
        this.buttonList.add(this.caseSensitive);
        this.expressionString.labelX = col1x;
        this.expressionString.setButtonLoc(col1x + 5 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.expressionString.description), this.rowY(9));
        this.expressionString.setButtonDims(effRight - this.expressionString.xPosition, 11);
        this.buttonList.add(this.expressionString);
        this.displayFilter(0);
        this.validateButtonStates();
    }

    protected boolean applyFilterToDirtyChat(int filterNum, String input)
    {
        if (filterNum >= this.numFilters)
        {
            return false;
        }
        else
        {
            String fNum = Integer.toString(filterNum);
            Boolean caseSensitive = (Boolean)this.filterMap.get(fNum + ".caseSensitive");
            Boolean inverseMatch = (Boolean)this.filterMap.get(fNum + ".inverseMatch");
            Boolean highlightBool = (Boolean)this.filterMap.get(fNum + ".highlightBool");
            ColorCodeEnum highlightColor = (ColorCodeEnum)this.filterMap.get(fNum + ".highlightColor");
            FormatCodeEnum highlightFormat = (FormatCodeEnum)this.filterMap.get(fNum + ".highlightFormat");
            String expressionString = new String((String)this.filterMap.get(fNum + ".expressionString"));

            if (expressionString.equals(""))
            {
                return false;
            }
            else
            {
                Pattern filter = (Pattern)this.filterMap.get(fNum + ".expressionPattern");
                Pattern pullCodes = Pattern.compile("(?i)(\\u00A7[0-9A-FK-OR])+");
                boolean _start = false;
                boolean _end = false;
                TreeMap chatCodes = new TreeMap();
                HashMap hlCodes = new HashMap();
                StringBuilder result = new StringBuilder(input);
                int _start1;
                int _end1;

                for (Matcher matchCodes = pullCodes.matcher(result.toString()); matchCodes.find(); matchCodes = pullCodes.matcher(result.toString()))
                {
                    _start1 = matchCodes.start();
                    _end1 = matchCodes.end();
                    chatCodes.put(Integer.valueOf(_start1), result.substring(_start1, _end1));
                    result.replace(_start1, _end1, "");
                }

                Matcher matchFilter = filter.matcher(result.toString());
                boolean matched = false;
                String prefix = highlightColor.toCode() + highlightFormat.toCode();
                String suffix = "\u00a7r";
                Entry ptr;

                while (matchFilter.find())
                {
                    matched = true;

                    if (!highlightBool.booleanValue())
                    {
                        break;
                    }

                    _start1 = matchFilter.start();
                    _end1 = matchFilter.end();
                    ptr = chatCodes.lowerEntry(Integer.valueOf(_end1));
                    hlCodes.put(Integer.valueOf(_start1), prefix);

                    if (ptr == null)
                    {
                        hlCodes.put(Integer.valueOf(_end1), suffix);
                    }
                    else
                    {
                        hlCodes.put(Integer.valueOf(_end1), (String)ptr.getValue());
                    }
                }

                if (highlightBool.booleanValue())
                {
                    chatCodes.putAll(hlCodes);

                    for (ptr = chatCodes.pollLastEntry(); ptr != null; ptr = chatCodes.pollLastEntry())
                    {
                        result.insert(((Integer)ptr.getKey()).intValue(), (String)ptr.getValue());
                    }

                    lastMatch = result.toString();
                }
                else
                {
                    lastMatch = input;
                }

                return !matched && inverseMatch.booleanValue() ? true : (matched && !inverseMatch.booleanValue() ? true : (matched && inverseMatch.booleanValue() ? false : (!matched && !inverseMatch.booleanValue() ? false : false)));
            }
        }
    }

    protected String getLastMatchPretty()
    {
        String tmp = new String(lastMatch);
        lastMatch = "";
        return tmp;
    }
}
