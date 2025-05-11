/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.StringUtils
 */
package acs.tabbychat;

import acs.tabbychat.ChannelDelimEnum;
import acs.tabbychat.ChatButton;
import acs.tabbychat.ChatChannel;
import acs.tabbychat.ColorCodeEnum;
import acs.tabbychat.FormatCodeEnum;
import acs.tabbychat.GlobalSettings;
import acs.tabbychat.GuiChatTC;
import acs.tabbychat.GuiNewChatTC;
import acs.tabbychat.NotificationSoundEnum;
import acs.tabbychat.ServerSettings;
import acs.tabbychat.TCSettingsAdvanced;
import acs.tabbychat.TCSettingsFilters;
import acs.tabbychat.TCSettingsGeneral;
import acs.tabbychat.TCSettingsServer;
import acs.tabbychat.TabbyChatUtils;
import acs.tabbychat.TaggableChatLine;
import acs.tabbychat.TimeStampEnum;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;

public class TabbyChat {
    protected static Minecraft mc;
    private Pattern chatChannelPatternCountry = Pattern.compile("^(Pays|Country):[ ]\\[([A-Za-z0-9_]{1,20})\\]");
    private Pattern chatChannelPatternAlly = Pattern.compile("^Ally:[ ]\\[([A-Za-z0-9_]{1,20})\\]");
    private Pattern chatChannelPatternTruce = Pattern.compile("^Truce:[ ]\\[([A-Za-z0-9_]{1,20})\\]");
    private Pattern chatChannelPatternEnemy = Pattern.compile("^Enemy:[ ]\\[([A-Za-z0-9_]{1,20})\\]");
    private Pattern chatChannelPatternAdmin = Pattern.compile("^\\[Staff\\-Admin\\]");
    private Pattern chatChannelPatternModo = Pattern.compile("^\\[Staff\\-(Modo|Mod)\\]");
    private Pattern chatChannelPatternPolice = Pattern.compile("^\\[Police\\]");
    private Pattern chatChannelPatternMafia = Pattern.compile("^\\[Mafia\\]");
    private Pattern chatChannelPatternJournaliste = Pattern.compile("^\\[Journal\\]");
    private Pattern chatChannelPatternGuide = Pattern.compile("^\\[Guide\\]");
    private Pattern chatChannelPatternAvocat = Pattern.compile("^\\[(Avocat|Lawyer)\\]");
    private Pattern chatChannelPatternAnnonce = Pattern.compile("^\\[(Annonce|Announce)\\]");
    private Pattern chatChannelPatternEvent = Pattern.compile("^\\[Event\\]");
    private Pattern chatChannelPatternRP = Pattern.compile("^\\[RP\\]");
    private Pattern chatChannelPatternLogs = Pattern.compile("^\\[Logs\\]");
    private Pattern chatChannelPatternIle = Pattern.compile("^.*\\[((Ile|Isle) [0-9]+)\\].*");
    private Pattern chatChannelPatternAll = Pattern.compile("^\\[All\\]");
    private Pattern chatChannelPatternClean = Pattern.compile("^\\[([A-Za-z0-9_]{1,10})\\]");
    private Pattern chatChannelPatternDirty = Pattern.compile("^\\[([A-Za-z0-9_]{1,10})\\]");
    private Pattern chatPMfromMePattern = Pattern.compile("^\\[(?:me)[ ]\\-\\>[ ]((?:.* )?[A-Za-z0-9_]{1,16}).*\\]");
    private Pattern chatPMtoMePattern = Pattern.compile("^\\[(?:.* )?([A-Za-z0-9_]{1,16}).*[ ]\\-\\>[ ](?:me)\\]");
    public static String version;
    protected Calendar cal = Calendar.getInstance();
    public List<TaggableChatLine> lastChat;
    public LinkedHashMap<String, ChatChannel> channelMap = new LinkedHashMap();
    public int nextID = 3600;
    public GlobalSettings globalPrefs = new GlobalSettings();
    public ServerSettings serverPrefs = new ServerSettings();
    public TCSettingsGeneral generalSettings;
    public TCSettingsServer serverSettings;
    public TCSettingsFilters filterSettings;
    public TCSettingsAdvanced advancedSettings;
    public static final GuiNewChatTC gnc;
    public static final TabbyChat instance;
    public static boolean defaultChannelsInit;

    private TabbyChat() {
        mc = Minecraft.func_71410_x();
        this.generalSettings = new TCSettingsGeneral(this);
        this.serverSettings = new TCSettingsServer(this);
        this.filterSettings = new TCSettingsFilters(this);
        this.advancedSettings = new TCSettingsAdvanced(this);
        boolean globalLoaded1 = this.generalSettings.loadSettingsFile();
        boolean globalLoaded2 = this.advancedSettings.loadSettingsFile();
        if (!globalLoaded1 && !globalLoaded2) {
            this.globalPrefs.loadSettings();
            this.generalSettings.importSettings();
            this.advancedSettings.importSettings();
        }
        if (!this.enabled()) {
            this.disable();
        } else {
            this.enable();
            this.channelMap.get((Object)"Global").active = true;
            ArrayList<TaggableChatLine> firstmsg = new ArrayList<TaggableChatLine>();
            this.lastChat = firstmsg;
            gnc.addChatLines(firstmsg);
        }
    }

    private int addToChannel(String name, ChatLine thisChat) {
        int ret = 0;
        ChatLine newChat = this.withTimeStamp(thisChat);
        ChatChannel theChan = this.channelMap.get(name);
        theChan.chatLog.add(0, newChat);
        theChan.trimLog();
        if (theChan.active) {
            ret = 1;
        }
        return ret;
    }

    private void spamCheck(String _chan, List<ChatLine> lastChat) {
        int i;
        ChatChannel theChan = this.channelMap.get(_chan);
        String oldChat = "";
        String oldChat2 = "";
        String newChat = "";
        if (theChan.chatLog.size() < lastChat.size()) {
            theChan.hasSpam = false;
            theChan.spamCount = 1;
            return;
        }
        int _size = lastChat.size();
        for (i = 0; i < _size; ++i) {
            newChat = newChat + lastChat.get(i).func_74538_a();
            oldChat = ClientProxy.clientConfig.enableTimestamp ? theChan.chatLog.get(i).func_74538_a().replaceAll("^" + ((TimeStampEnum)this.generalSettings.timeStampStyle.getValue()).regEx, "") + oldChat : theChan.chatLog.get(i).func_74538_a() + oldChat;
        }
        if (theChan.hasSpam && oldChat.length() - 4 - Integer.toString(theChan.spamCount).length() > 0) {
            oldChat = oldChat2 = oldChat.substring(0, oldChat.length() - 4 - Integer.toString(theChan.spamCount).length());
        }
        if (oldChat.equals(newChat) && !oldChat.equalsIgnoreCase("\u00a7r") && lastChat.size() != 0) {
            theChan.hasSpam = true;
            ++theChan.spamCount;
            for (i = 1; i < _size; ++i) {
                theChan.chatLog.set(i, this.withTimeStamp(lastChat.get(lastChat.size() - i - 1)));
            }
            ChatLine last = lastChat.get(lastChat.size() - 1);
            if (last instanceof TaggableChatLine) {
                TaggableChatLine taggableLast = (TaggableChatLine)last;
                theChan.chatLog.set(0, new TaggableChatLine(last.func_74540_b(), this.withTimeStamp(last.func_74538_a()) + " [" + theChan.spamCount + "x]", taggableLast.getTags(), last.func_74539_c()));
            } else {
                theChan.chatLog.set(0, new ChatLine(last.func_74540_b(), this.withTimeStamp(last.func_74538_a()) + " [" + theChan.spamCount + "x]", last.func_74539_c()));
            }
        } else {
            theChan.hasSpam = false;
            theChan.spamCount = 1;
        }
    }

    public int addToChannel(String _name, List<ChatLine> thisChat) {
        ChatChannel theChan = this.channelMap.get(_name);
        for (String ichan : Pattern.compile("[ ]?,[ ]?").split(this.serverSettings.ignoredChannels.getValue())) {
            if (ichan.length() <= 0 || !_name.equals(ichan)) continue;
            return 0;
        }
        if (theChan != null) {
            int ret = 0;
            if (!this.generalSettings.groupSpam.getValue().booleanValue()) {
                // empty if block
            }
            this.spamCheck(_name, thisChat);
            if (!theChan.hasSpam) {
                for (ChatLine cl : thisChat) {
                    ret += this.addToChannel(_name, cl);
                }
            }
            return ret;
        }
        if (this.channelMap.size() >= 20) {
            return 0;
        }
        if (this.serverSettings.autoChannelSearch.getValue().booleanValue()) {
            this.channelMap.put(_name, new ChatChannel(_name));
            int ret = 0;
            for (ChatLine cl : thisChat) {
                ret += this.addToChannel(_name, cl);
            }
            return ret;
        }
        return 0;
    }

    private List<ChatLine> withTimeStamp(List<ChatLine> _orig) {
        ArrayList<ChatLine> stamped = new ArrayList<ChatLine>();
        for (ChatLine cl : _orig) {
            stamped.add(0, this.withTimeStamp(cl));
        }
        return stamped;
    }

    private ChatLine withTimeStamp(ChatLine _orig) {
        ChatLine stamped = _orig;
        if (ClientProxy.clientConfig.enableTimestamp) {
            this.cal = Calendar.getInstance();
            if (_orig instanceof TaggableChatLine) {
                TaggableChatLine _orig_tab = (TaggableChatLine)_orig;
                stamped = new TaggableChatLine(_orig.func_74540_b(), this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig.func_74538_a(), _orig_tab.getTags(), _orig.func_74539_c());
            } else {
                stamped = new ChatLine(_orig.func_74540_b(), this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig.func_74538_a(), _orig.func_74539_c());
            }
        }
        return stamped;
    }

    private String withTimeStamp(String _orig) {
        String stamped = _orig;
        if (ClientProxy.clientConfig.enableTimestamp) {
            this.cal = Calendar.getInstance();
            stamped = this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig;
        }
        return stamped;
    }

    protected void disable() {
        this.channelMap.clear();
        this.channelMap.put("Global", new ChatChannel("Global"));
    }

    protected void enable() {
        if (!this.channelMap.containsKey("Global")) {
            this.channelMap.put("Global", new ChatChannel("Global"));
            this.channelMap.get((Object)"Global").active = true;
        }
        this.serverSettings.updateForServer();
        boolean serverLoaded1 = this.serverSettings.loadSettingsFile();
        boolean serverLoaded2 = this.filterSettings.loadSettingsFile();
        if (!serverLoaded1 && !serverLoaded2) {
            this.serverPrefs.updateForServer();
            this.serverPrefs.loadSettings();
            this.serverSettings.importSettings();
            this.filterSettings.importSettings();
        }
        this.loadPatterns();
        this.updateDefaults();
        this.updateFilters();
        if (this.generalSettings.saveChatLog.getValue().booleanValue() && this.serverSettings.server != null) {
            TabbyChatUtils.logChat("\nBEGIN CHAT LOGGING FOR " + this.serverSettings.serverName + "(" + this.serverSettings.serverIP + ") -- " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
        }
    }

    protected void loadPatterns() {
        ChannelDelimEnum delims = (ChannelDelimEnum)this.serverSettings.delimiterChars.getValue();
        String colCode = "";
        String fmtCode = "";
        if (this.serverSettings.delimColorBool.getValue().booleanValue()) {
            colCode = ((ColorCodeEnum)this.serverSettings.delimColorCode.getValue()).toCode();
        }
        if (this.serverSettings.delimFormatBool.getValue().booleanValue()) {
            fmtCode = ((FormatCodeEnum)this.serverSettings.delimFormatCode.getValue()).toCode();
        }
        String frmt = colCode + fmtCode;
        if (((ColorCodeEnum)this.serverSettings.delimColorCode.getValue()).toString().equals("White")) {
            frmt = "(" + colCode + ")?" + fmtCode;
        } else if (frmt.length() > 7) {
            frmt = "[" + frmt + "]{2}";
        }
        if (frmt.length() > 0) {
            frmt = "(?i:" + frmt + ")";
        }
        if (frmt.length() == 0) {
            frmt = "(?i:\u00a7[0-9A-FK-OR])*";
        }
        this.chatChannelPatternDirty = Pattern.compile("^(\u00a7r)?" + frmt + "\\" + delims.open() + "([A-Za-z0-9_\u00a7]+)\\" + delims.close());
        this.chatChannelPatternClean = Pattern.compile("^\\" + delims.open() + "([A-Za-z0-9_]{1," + this.advancedSettings.maxLengthChannelName.getValue() + "})\\" + delims.close());
        this.chatPMtoMePattern = Pattern.compile("^\\[(?:.* )?([A-Za-z0-9_]{1,16}).*[ ]\\-\\>[ ](?:me)\\]");
        this.chatPMfromMePattern = Pattern.compile("^\\[(?:me)[ ]\\-\\>[ ](?:.* )?([A-Za-z0-9_]{1,16}).*\\]");
    }

    protected void updateFilters() {
        if (!this.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
            return;
        }
        if (this.filterSettings.numFilters == 0) {
            return;
        }
        for (int i = 0; i < this.filterSettings.numFilters; ++i) {
            String newName = this.filterSettings.sendToTabName(i);
            if (!this.filterSettings.sendToTabBool(i) || this.filterSettings.sendToAllTabs(i) || this.channelMap.containsKey(newName)) continue;
            this.channelMap.put(newName, new ChatChannel(newName));
        }
    }

    protected void updateDefaults() {
        if (!this.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
            return;
        }
        ArrayList<String> dList = new ArrayList<String>(Arrays.asList(Pattern.compile("[ ]?,[ ]?").split(this.serverSettings.defaultChannels.getValue())));
        for (ChatChannel chan : this.channelMap.values()) {
            int ind = dList.indexOf(chan.title);
            if (ind < 0) continue;
            dList.remove(ind);
        }
        for (String defChan : dList) {
            if (defChan.length() <= 0) continue;
            this.channelMap.put(defChan, new ChatChannel(defChan));
        }
    }

    public void checkServer() {
        if (ServerSettings.getServerData() == null) {
            return;
        }
        if (!ServerSettings.getServerData().field_78845_b.equalsIgnoreCase(this.serverSettings.serverIP)) {
            this.channelMap.clear();
            if (this.enabled()) {
                this.enable();
            } else {
                this.disable();
            }
        }
    }

    public void copyTab(String toName, String fromName) {
        this.channelMap.put(toName, this.channelMap.get(fromName));
    }

    public boolean enabled() {
        return !mc.func_71356_B();
    }

    public List<String> getActive() {
        int n = this.channelMap.size();
        ArrayList<String> actives = new ArrayList<String>(n);
        for (ChatChannel chan : this.channelMap.values()) {
            if (!chan.active) continue;
            actives.add(chan.title);
        }
        return actives;
    }

    public ChatLine getChatLine(String _chan, int _line) {
        return this.channelMap.get((Object)_chan).chatLog.get(_line);
    }

    public static String getNewestVersion() {
        try {
            URLConnection conn = new URL("http://dl.dropbox.com/u/8347166/tabbychat_ver.txt").openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String newestVersion = buffer.readLine();
            buffer.close();
            return newestVersion;
        }
        catch (Throwable e) {
            TabbyChat.printErr("Unable to check for TabbyChat update.");
            return version;
        }
    }

    public void pollForUnread(Gui _gui, int _y, int _tick) {
        int tickdiff;
        int _opacity = 0;
        try {
            if (this.lastChat == null || this.lastChat.size() == 0) {
                return;
            }
            tickdiff = _tick - this.lastChat.get(0).func_74540_b();
        }
        catch (Exception e) {
            return;
        }
        if (tickdiff < 50) {
            float var6 = TabbyChat.mc.field_71474_y.field_74357_r * 0.9f + 0.1f;
            double var10 = (double)tickdiff / 50.0;
            var10 = 1.0 - var10;
            if ((var10 *= 10.0) < 0.0) {
                var10 = 0.0;
            }
            if (var10 > 1.0) {
                var10 = 1.0;
            }
            var10 *= var10;
            _opacity = (int)(255.0 * var10);
            if ((_opacity = (int)((float)_opacity * var6)) <= 3) {
                return;
            }
            this.updateButtonLocations();
            for (ChatChannel chan : this.channelMap.values()) {
                if (!chan.unread) continue;
                chan.unreadNotify(_gui, _y, _opacity);
            }
        }
    }

    public static void printErr(String err) {
        System.err.println(err);
    }

    public int processChat(List<ChatLine> theChat) {
        ArrayList<ChatLine> filteredChatLine = new ArrayList<ChatLine>(theChat.size());
        ArrayList<String> toTabs = new ArrayList<String>();
        int ret = 0;
        boolean skip = false;
        int n = theChat.size();
        StringBuilder filteredChat = new StringBuilder(n > 0 ? theChat.get(0).func_74538_a().length() * n : 0);
        for (int z = 0; z < n; ++z) {
            filteredChat.append(theChat.get(z).func_74538_a());
        }
        for (int i = 0; i < this.filterSettings.numFilters; ++i) {
            if (i == 0 && !ClientProxy.clientConfig.enableTag || !this.filterSettings.applyFilterToDirtyChat(i, filteredChat.toString())) continue;
            if (this.filterSettings.removeMatches(i)) {
                toTabs.clear();
                skip = true;
                break;
            }
            filteredChat = new StringBuilder(this.filterSettings.getLastMatchPretty());
            if (this.filterSettings.sendToTabBool(i)) {
                if (this.filterSettings.sendToAllTabs(i)) {
                    toTabs.clear();
                    for (ChatChannel chan : this.channelMap.values()) {
                        toTabs.add(chan.title);
                    }
                    skip = true;
                    continue;
                }
                String string = this.filterSettings.sendToTabName(i);
                if (!this.channelMap.containsKey(string)) {
                    this.channelMap.put(string, new ChatChannel(string));
                }
                if (!toTabs.contains(string)) {
                    toTabs.add(string);
                }
            }
            if (!this.filterSettings.audioNotificationBool(i)) continue;
            this.filterSettings.audioNotification(i);
        }
        filteredChatLine.addAll(theChat);
        for (String string : toTabs) {
            this.addToChannel(string, filteredChatLine);
        }
        for (String string : this.getActive()) {
            if (!toTabs.contains(string)) continue;
            ++ret;
        }
        String coloredChat = "";
        for (ChatLine cl : theChat) {
            coloredChat = coloredChat + cl.func_74538_a();
        }
        String string = StringUtils.func_76338_a((String)coloredChat);
        if (this.generalSettings.saveChatLog.getValue().booleanValue()) {
            TabbyChatUtils.logChat(this.withTimeStamp(string));
        }
        if (!skip) {
            String cName;
            boolean dirtyValid;
            Matcher findChannelClean = this.chatChannelPatternClean.matcher(string);
            Matcher findChannelDirty = this.chatChannelPatternDirty.matcher(coloredChat);
            Matcher findChannelAlly = this.chatChannelPatternAlly.matcher(string);
            Matcher findChannelEnemy = this.chatChannelPatternEnemy.matcher(string);
            Matcher findChannelTruce = this.chatChannelPatternTruce.matcher(string);
            Matcher findChannelCountry = this.chatChannelPatternCountry.matcher(string);
            Matcher findChannelAdmin = this.chatChannelPatternAdmin.matcher(string);
            Matcher findChannelModo = this.chatChannelPatternModo.matcher(string);
            Matcher findChannelAll = this.chatChannelPatternAll.matcher(string);
            Matcher findChannelPolice = this.chatChannelPatternPolice.matcher(string);
            Matcher findChannelMafia = this.chatChannelPatternMafia.matcher(string);
            Matcher findChannelJournaliste = this.chatChannelPatternJournaliste.matcher(string);
            Matcher findChannelGuide = this.chatChannelPatternGuide.matcher(string);
            Matcher findChannelAvocat = this.chatChannelPatternAvocat.matcher(string);
            Matcher findChannelRP = this.chatChannelPatternRP.matcher(string);
            Matcher findChannelLogs = this.chatChannelPatternLogs.matcher(string);
            Matcher findChannelAnnonce = this.chatChannelPatternAnnonce.matcher(string);
            Matcher findChannelEvent = this.chatChannelPatternEvent.matcher(string);
            Matcher findChannelIle = this.chatChannelPatternIle.matcher(string);
            Matcher findPMtoMe = this.chatPMtoMePattern.matcher(string);
            Matcher findPMfromMe = this.chatPMfromMePattern.matcher(string);
            boolean bl = dirtyValid = this.serverSettings.delimColorBool.getValue() == false && this.serverSettings.delimFormatBool.getValue() == false ? true : findChannelDirty.find();
            if (findChannelAll.find()) {
                for (ChatChannel chan : this.channelMap.values()) {
                    if (this.getActive().size() <= 0 || !this.getActive().get(0).equals(chan.title)) continue;
                    ret += this.addToChannel(chan.title, filteredChatLine);
                }
            } else if (findChannelCountry.find()) {
                ret += this.addToChannel("Mon pays", filteredChatLine);
                toTabs.add("Mon pays");
            } else if (findChannelAlly.find()) {
                ret += this.addToChannel("ALL", filteredChatLine);
                toTabs.add("ALL");
            } else if (findChannelEnemy.find()) {
                ret += this.addToChannel("ENE", filteredChatLine);
                toTabs.add("ENE");
            } else if (findChannelAdmin.find()) {
                ret += this.addToChannel("ADMIN", filteredChatLine);
                toTabs.add("ADMIN");
                ret += this.addToChannel("Global", filteredChatLine);
                toTabs.add("Global");
            } else if (findChannelModo.find()) {
                ret += this.addToChannel("MODO", filteredChatLine);
                toTabs.add("MODO");
                ret += this.addToChannel("Global", filteredChatLine);
                toTabs.add("Global");
            } else if (findChannelPolice.find()) {
                ret += this.addToChannel("Police", filteredChatLine);
                toTabs.add("Police");
            } else if (findChannelMafia.find()) {
                ret += this.addToChannel("Mafia", filteredChatLine);
                toTabs.add("Mafia");
            } else if (findChannelJournaliste.find()) {
                ret += this.addToChannel("Journal", filteredChatLine);
                toTabs.add("Journal");
            } else if (findChannelGuide.find()) {
                ret += this.addToChannel("Guide", filteredChatLine);
                toTabs.add("Guide");
                ret += this.addToChannel("Global", filteredChatLine);
                toTabs.add("Global");
            } else if (findChannelAvocat.find()) {
                ret += this.addToChannel("Avocat", filteredChatLine);
                toTabs.add("Avocat");
            } else if (findChannelAnnonce.find()) {
                ret += this.addToChannel("Annonce", filteredChatLine);
                toTabs.add("Annonce");
            } else if (findChannelRP.find()) {
                ret += this.addToChannel("RP", filteredChatLine);
                toTabs.add("RP");
            } else if (findChannelLogs.find()) {
                ret += this.addToChannel("Logs", filteredChatLine);
                toTabs.add("Logs");
            } else if (findChannelEvent.find()) {
                ret += this.addToChannel("Event", filteredChatLine);
                toTabs.add("Event");
                ret += this.addToChannel("Global", filteredChatLine);
                toTabs.add("Global");
            } else if (findChannelIle.find()) {
                cName = string.substring(findChannelIle.start(1), findChannelIle.end(1));
                ret += this.addToChannel(cName, filteredChatLine);
                toTabs.add(cName);
            } else if (findPMtoMe.find()) {
                cName = string.substring(findPMtoMe.start(1), findPMtoMe.end(1));
                ret += this.addToChannel(cName, filteredChatLine);
                toTabs.add(cName);
                if (!this.getActive().contains(cName)) {
                    Minecraft.func_71410_x().field_71416_A.func_77366_a(NotificationSoundEnum.HARP.file(), 1.0f, 1.0f);
                }
            } else if (findPMfromMe.find()) {
                cName = string.substring(findPMfromMe.start(1), findPMfromMe.end(1));
                ret += this.addToChannel(cName, filteredChatLine);
                toTabs.add(cName);
            } else {
                ret += this.addToChannel("Global", filteredChatLine);
                toTabs.add("Global");
            }
        }
        if (ret == 0) {
            for (String c : toTabs) {
                if (c == "Global" || !this.channelMap.containsKey(c)) continue;
                this.channelMap.get((Object)c).unread = true;
            }
        }
        List<String> activeTabs = this.getActive();
        if (!this.generalSettings.groupSpam.getValue().booleanValue()) {
            // empty if block
        }
        this.lastChat = activeTabs.size() > 0 ? (toTabs.contains(activeTabs.get(0)) ? TaggableChatLine.convertList(this.channelMap.get((Object)activeTabs.get((int)0)).chatLog.subList(0, Math.min(filteredChatLine.size(), this.channelMap.get((Object)activeTabs.get((int)0)).chatLog.size()))) : TaggableChatLine.convertList(this.withTimeStamp(filteredChatLine))) : TaggableChatLine.convertList(this.withTimeStamp(filteredChatLine));
        if (ret > 0) {
            if (!this.generalSettings.groupSpam.getValue().booleanValue()) {
                // empty if block
            }
            if (this.channelMap.get((Object)activeTabs.get((int)0)).hasSpam) {
                gnc.setChatLines(0, this.lastChat);
            } else {
                gnc.addChatLines(0, this.lastChat);
            }
        }
        return ret;
    }

    public void removeTab(String _name) {
        this.channelMap.remove(_name);
    }

    public void resetDisplayedChat() {
        gnc.clearChatLines();
        List<String> actives = this.getActive();
        if (actives.size() < 1) {
            return;
        }
        gnc.addChatLines(TaggableChatLine.convertList(this.channelMap.get((Object)actives.get((int)0)).chatLog));
        int n = actives.size();
        for (int i = 1; i < n; ++i) {
            gnc.mergeChatLines(this.channelMap.get((Object)actives.get((int)i)).chatLog);
        }
    }

    public void updateButtonLocations() {
        int xOff = 0;
        int yOff = 0;
        ScaledResolution sr = new ScaledResolution(TabbyChat.mc.field_71474_y, TabbyChat.mc.field_71443_c, TabbyChat.mc.field_71440_d);
        int maxlines = gnc.getHeightSetting() / 9;
        int clines = gnc.GetChatHeight() < maxlines ? gnc.GetChatHeight() : maxlines;
        int vert = sr.func_78328_b() - TabbyChat.gnc.chatHeight - 51;
        int horiz = 5;
        int n = this.channelMap.size();
        try {
            if (TabbyChatUtils.is((Gui)TabbyChat.mc.field_71456_v.func_73827_b(), "GuiNewChatWrapper")) {
                Class<?> aHudCls = Class.forName("advancedhud.ahuditem.DefaultHudItems");
                Field aHudFld = aHudCls.getField("chat");
                Object aHudObj = aHudFld.get(null);
                aHudCls = Class.forName("advancedhud.ahuditem.HudItem");
                int dVert = TabbyChat.mc.field_71462_r.field_73881_g - 22 - 108;
                xOff = aHudCls.getField("posX").getInt(aHudObj) - 3;
                yOff = aHudCls.getField("posY").getInt(aHudObj) - dVert;
                horiz += xOff;
                vert -= yOff;
                if (gnc.func_73760_d()) {
                    ((GuiChatTC)TabbyChat.mc.field_71462_r).scrollBar.setOffset(xOff, yOff);
                }
            }
        }
        catch (Throwable aHudCls) {
            // empty catch block
        }
        boolean i = false;
        for (ChatChannel chan : this.channelMap.values()) {
            chan.tab.width(TabbyChat.mc.field_71466_p.func_78256_a("<" + chan.title + ">") + 8);
            if (horiz + chan.tab.width() > TabbyChat.gnc.chatWidth - 5) {
                vert -= chan.tab.height();
                horiz = 5;
            }
            chan.setButtonLoc(horiz, vert);
            if (chan.tab == null) {
                chan.setButtonObj(new ChatButton(chan.getID(), horiz, vert, chan.tab.width(), chan.tab.height(), chan.getDisplayTitle()));
            } else {
                chan.tab.field_73741_f = chan.getID();
                chan.tab.field_73746_c = horiz;
                chan.tab.field_73743_d = vert;
                chan.tab.field_73744_e = chan.getDisplayTitle();
            }
            horiz = chan.getButtonEnd() + 1;
        }
    }

    static {
        version = "1.6.04";
        gnc = GuiNewChatTC.me;
        instance = new TabbyChat();
        defaultChannelsInit = false;
    }
}

