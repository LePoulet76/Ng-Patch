package acs.tabbychat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
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

public class TabbyChat
{
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
    public static String version = "1.6.04";
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
    public static final GuiNewChatTC gnc = GuiNewChatTC.me;
    public static final TabbyChat instance = new TabbyChat();
    public static boolean defaultChannelsInit = false;

    private TabbyChat()
    {
        mc = Minecraft.getMinecraft();
        this.generalSettings = new TCSettingsGeneral(this);
        this.serverSettings = new TCSettingsServer(this);
        this.filterSettings = new TCSettingsFilters(this);
        this.advancedSettings = new TCSettingsAdvanced(this);
        boolean globalLoaded1 = this.generalSettings.loadSettingsFile();
        boolean globalLoaded2 = this.advancedSettings.loadSettingsFile();

        if (!globalLoaded1 && !globalLoaded2)
        {
            this.globalPrefs.loadSettings();
            this.generalSettings.importSettings();
            this.advancedSettings.importSettings();
        }

        if (!this.enabled())
        {
            this.disable();
        }
        else
        {
            this.enable();
            ((ChatChannel)this.channelMap.get("Global")).active = true;
            ArrayList firstmsg = new ArrayList();
            this.lastChat = firstmsg;
            gnc.addChatLines(firstmsg);
        }
    }

    private int addToChannel(String name, ChatLine thisChat)
    {
        byte ret = 0;
        ChatLine newChat = this.withTimeStamp(thisChat);
        ChatChannel theChan = (ChatChannel)this.channelMap.get(name);
        theChan.chatLog.add(0, newChat);
        theChan.trimLog();

        if (theChan.active)
        {
            ret = 1;
        }

        return ret;
    }

    private void spamCheck(String _chan, List<ChatLine> lastChat)
    {
        ChatChannel theChan = (ChatChannel)this.channelMap.get(_chan);
        String oldChat = "";
        String oldChat2 = "";
        String newChat = "";

        if (theChan.chatLog.size() < lastChat.size())
        {
            theChan.hasSpam = false;
            theChan.spamCount = 1;
        }
        else
        {
            int _size = lastChat.size();
            int last;

            for (last = 0; last < _size; ++last)
            {
                newChat = newChat + ((ChatLine)lastChat.get(last)).getChatLineString();

                if (ClientProxy.clientConfig.enableTimestamp)
                {
                    oldChat = ((ChatLine)theChan.chatLog.get(last)).getChatLineString().replaceAll("^" + ((TimeStampEnum)this.generalSettings.timeStampStyle.getValue()).regEx, "") + oldChat;
                }
                else
                {
                    oldChat = ((ChatLine)theChan.chatLog.get(last)).getChatLineString() + oldChat;
                }
            }

            if (theChan.hasSpam && oldChat.length() - 4 - Integer.toString(theChan.spamCount).length() > 0)
            {
                oldChat2 = oldChat.substring(0, oldChat.length() - 4 - Integer.toString(theChan.spamCount).length());
                oldChat = oldChat2;
            }

            if (oldChat.equals(newChat) && !oldChat.equalsIgnoreCase("\u00a7r") && lastChat.size() != 0)
            {
                theChan.hasSpam = true;
                ++theChan.spamCount;

                for (last = 1; last < _size; ++last)
                {
                    theChan.chatLog.set(last, this.withTimeStamp((ChatLine)lastChat.get(lastChat.size() - last - 1)));
                }

                ChatLine var10 = (ChatLine)lastChat.get(lastChat.size() - 1);

                if (var10 instanceof TaggableChatLine)
                {
                    TaggableChatLine taggableLast = (TaggableChatLine)var10;
                    theChan.chatLog.set(0, new TaggableChatLine(var10.getUpdatedCounter(), this.withTimeStamp(var10.getChatLineString()) + " [" + theChan.spamCount + "x]", taggableLast.getTags(), var10.getChatLineID()));
                }
                else
                {
                    theChan.chatLog.set(0, new ChatLine(var10.getUpdatedCounter(), this.withTimeStamp(var10.getChatLineString()) + " [" + theChan.spamCount + "x]", var10.getChatLineID()));
                }
            }
            else
            {
                theChan.hasSpam = false;
                theChan.spamCount = 1;
            }
        }
    }

    public int addToChannel(String _name, List<ChatLine> thisChat)
    {
        ChatChannel theChan = (ChatChannel)this.channelMap.get(_name);
        String[] ret = Pattern.compile("[ ]?,[ ]?").split(this.serverSettings.ignoredChannels.getValue());
        int var5 = ret.length;

        for (int cl = 0; cl < var5; ++cl)
        {
            String ichan = ret[cl];

            if (ichan.length() > 0 && _name.equals(ichan))
            {
                return 0;
            }
        }

        int var8;
        Iterator var9;
        ChatLine var10;

        if (theChan != null)
        {
            var8 = 0;

            if (!this.generalSettings.groupSpam.getValue().booleanValue())
            {
                ;
            }

            this.spamCheck(_name, thisChat);

            if (!theChan.hasSpam)
            {
                for (var9 = thisChat.iterator(); var9.hasNext(); var8 += this.addToChannel(_name, var10))
                {
                    var10 = (ChatLine)var9.next();
                }
            }

            return var8;
        }
        else if (this.channelMap.size() >= 20)
        {
            return 0;
        }
        else if (!this.serverSettings.autoChannelSearch.getValue().booleanValue())
        {
            return 0;
        }
        else
        {
            this.channelMap.put(_name, new ChatChannel(_name));
            var8 = 0;

            for (var9 = thisChat.iterator(); var9.hasNext(); var8 += this.addToChannel(_name, var10))
            {
                var10 = (ChatLine)var9.next();
            }

            return var8;
        }
    }

    private List<ChatLine> withTimeStamp(List<ChatLine> _orig)
    {
        ArrayList stamped = new ArrayList();
        Iterator var3 = _orig.iterator();

        while (var3.hasNext())
        {
            ChatLine cl = (ChatLine)var3.next();
            stamped.add(0, this.withTimeStamp(cl));
        }

        return stamped;
    }

    private ChatLine withTimeStamp(ChatLine _orig)
    {
        Object stamped = _orig;

        if (ClientProxy.clientConfig.enableTimestamp)
        {
            this.cal = Calendar.getInstance();

            if (_orig instanceof TaggableChatLine)
            {
                TaggableChatLine _orig_tab = (TaggableChatLine)_orig;
                stamped = new TaggableChatLine(_orig.getUpdatedCounter(), this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig.getChatLineString(), _orig_tab.getTags(), _orig.getChatLineID());
            }
            else
            {
                stamped = new ChatLine(_orig.getUpdatedCounter(), this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig.getChatLineString(), _orig.getChatLineID());
            }
        }

        return (ChatLine)stamped;
    }

    private String withTimeStamp(String _orig)
    {
        String stamped = _orig;

        if (ClientProxy.clientConfig.enableTimestamp)
        {
            this.cal = Calendar.getInstance();
            stamped = this.generalSettings.timeStamp.format(this.cal.getTime()) + _orig;
        }

        return stamped;
    }

    protected void disable()
    {
        this.channelMap.clear();
        this.channelMap.put("Global", new ChatChannel("Global"));
    }

    protected void enable()
    {
        if (!this.channelMap.containsKey("Global"))
        {
            this.channelMap.put("Global", new ChatChannel("Global"));
            ((ChatChannel)this.channelMap.get("Global")).active = true;
        }

        this.serverSettings.updateForServer();
        boolean serverLoaded1 = this.serverSettings.loadSettingsFile();
        boolean serverLoaded2 = this.filterSettings.loadSettingsFile();

        if (!serverLoaded1 && !serverLoaded2)
        {
            this.serverPrefs.updateForServer();
            this.serverPrefs.loadSettings();
            this.serverSettings.importSettings();
            this.filterSettings.importSettings();
        }

        this.loadPatterns();
        this.updateDefaults();
        this.updateFilters();

        if (this.generalSettings.saveChatLog.getValue().booleanValue() && this.serverSettings.server != null)
        {
            TabbyChatUtils.logChat("\nBEGIN CHAT LOGGING FOR " + this.serverSettings.serverName + "(" + this.serverSettings.serverIP + ") -- " + (new SimpleDateFormat()).format(Calendar.getInstance().getTime()));
        }
    }

    protected void loadPatterns()
    {
        ChannelDelimEnum delims = (ChannelDelimEnum)this.serverSettings.delimiterChars.getValue();
        String colCode = "";
        String fmtCode = "";

        if (this.serverSettings.delimColorBool.getValue().booleanValue())
        {
            colCode = ((ColorCodeEnum)this.serverSettings.delimColorCode.getValue()).toCode();
        }

        if (this.serverSettings.delimFormatBool.getValue().booleanValue())
        {
            fmtCode = ((FormatCodeEnum)this.serverSettings.delimFormatCode.getValue()).toCode();
        }

        String frmt = colCode + fmtCode;

        if (((ColorCodeEnum)this.serverSettings.delimColorCode.getValue()).toString().equals("White"))
        {
            frmt = "(" + colCode + ")?" + fmtCode;
        }
        else if (frmt.length() > 7)
        {
            frmt = "[" + frmt + "]{2}";
        }

        if (frmt.length() > 0)
        {
            frmt = "(?i:" + frmt + ")";
        }

        if (frmt.length() == 0)
        {
            frmt = "(?i:\u00a7[0-9A-FK-OR])*";
        }

        this.chatChannelPatternDirty = Pattern.compile("^(\u00a7r)?" + frmt + "\\" + delims.open() + "([A-Za-z0-9_\u00a7]+)\\" + delims.close());
        this.chatChannelPatternClean = Pattern.compile("^\\" + delims.open() + "([A-Za-z0-9_]{1," + this.advancedSettings.maxLengthChannelName.getValue() + "})\\" + delims.close());
        this.chatPMtoMePattern = Pattern.compile("^\\[(?:.* )?([A-Za-z0-9_]{1,16}).*[ ]\\-\\>[ ](?:me)\\]");
        this.chatPMfromMePattern = Pattern.compile("^\\[(?:me)[ ]\\-\\>[ ](?:.* )?([A-Za-z0-9_]{1,16}).*\\]");
    }

    protected void updateFilters()
    {
        if (this.generalSettings.tabbyChatEnable.getValue().booleanValue())
        {
            if (this.filterSettings.numFilters != 0)
            {
                for (int i = 0; i < this.filterSettings.numFilters; ++i)
                {
                    String newName = this.filterSettings.sendToTabName(i);

                    if (this.filterSettings.sendToTabBool(i) && !this.filterSettings.sendToAllTabs(i) && !this.channelMap.containsKey(newName))
                    {
                        this.channelMap.put(newName, new ChatChannel(newName));
                    }
                }
            }
        }
    }

    protected void updateDefaults()
    {
        if (this.generalSettings.tabbyChatEnable.getValue().booleanValue())
        {
            ArrayList dList = new ArrayList(Arrays.asList(Pattern.compile("[ ]?,[ ]?").split(this.serverSettings.defaultChannels.getValue())));
            Iterator var3 = this.channelMap.values().iterator();

            while (var3.hasNext())
            {
                ChatChannel defChan = (ChatChannel)var3.next();
                int ind = dList.indexOf(defChan.title);

                if (ind >= 0)
                {
                    dList.remove(ind);
                }
            }

            var3 = dList.iterator();

            while (var3.hasNext())
            {
                String defChan1 = (String)var3.next();

                if (defChan1.length() > 0)
                {
                    this.channelMap.put(defChan1, new ChatChannel(defChan1));
                }
            }
        }
    }

    public void checkServer()
    {
        if (ServerSettings.getServerData() != null)
        {
            if (!ServerSettings.getServerData().serverIP.equalsIgnoreCase(this.serverSettings.serverIP))
            {
                this.channelMap.clear();

                if (this.enabled())
                {
                    this.enable();
                }
                else
                {
                    this.disable();
                }
            }
        }
    }

    public void copyTab(String toName, String fromName)
    {
        this.channelMap.put(toName, this.channelMap.get(fromName));
    }

    public boolean enabled()
    {
        return !mc.isSingleplayer();
    }

    public List<String> getActive()
    {
        int n = this.channelMap.size();
        ArrayList actives = new ArrayList(n);
        Iterator var3 = this.channelMap.values().iterator();

        while (var3.hasNext())
        {
            ChatChannel chan = (ChatChannel)var3.next();

            if (chan.active)
            {
                actives.add(chan.title);
            }
        }

        return actives;
    }

    public ChatLine getChatLine(String _chan, int _line)
    {
        return (ChatLine)((ChatChannel)this.channelMap.get(_chan)).chatLog.get(_line);
    }

    public static String getNewestVersion()
    {
        try
        {
            URLConnection e = (new URL("http://dl.dropbox.com/u/8347166/tabbychat_ver.txt")).openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(e.getInputStream()));
            String newestVersion = buffer.readLine();
            buffer.close();
            return newestVersion;
        }
        catch (Throwable var3)
        {
            printErr("Unable to check for TabbyChat update.");
            return version;
        }
    }

    public void pollForUnread(Gui _gui, int _y, int _tick)
    {
        boolean _opacity = false;
        int tickdiff;

        try
        {
            if (this.lastChat == null || this.lastChat.size() == 0)
            {
                return;
            }

            tickdiff = _tick - ((TaggableChatLine)this.lastChat.get(0)).getUpdatedCounter();
        }
        catch (Exception var11)
        {
            return;
        }

        if (tickdiff < 50)
        {
            float var6 = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            double var10 = (double)tickdiff / 50.0D;
            var10 = 1.0D - var10;
            var10 *= 10.0D;

            if (var10 < 0.0D)
            {
                var10 = 0.0D;
            }

            if (var10 > 1.0D)
            {
                var10 = 1.0D;
            }

            var10 *= var10;
            int _opacity1 = (int)(255.0D * var10);
            _opacity1 = (int)((float)_opacity1 * var6);

            if (_opacity1 <= 3)
            {
                return;
            }

            this.updateButtonLocations();
            Iterator var9 = this.channelMap.values().iterator();

            while (var9.hasNext())
            {
                ChatChannel chan = (ChatChannel)var9.next();

                if (chan.unread)
                {
                    chan.unreadNotify(_gui, _y, _opacity1);
                }
            }
        }
    }

    public static void printErr(String err)
    {
        System.err.println(err);
    }

    public int processChat(List<ChatLine> theChat)
    {
        ArrayList filteredChatLine = new ArrayList(theChat.size());
        ArrayList toTabs = new ArrayList();
        int ret = 0;
        boolean skip = false;
        int n = theChat.size();
        StringBuilder filteredChat = new StringBuilder(n > 0 ? ((ChatLine)theChat.get(0)).getChatLineString().length() * n : 0);
        int coloredChat;

        for (coloredChat = 0; coloredChat < n; ++coloredChat)
        {
            filteredChat.append(((ChatLine)theChat.get(coloredChat)).getChatLineString());
        }

        String cleanedChat;
        Iterator var37;

        for (coloredChat = 0; coloredChat < this.filterSettings.numFilters; ++coloredChat)
        {
            if ((coloredChat != 0 || ClientProxy.clientConfig.enableTag) && this.filterSettings.applyFilterToDirtyChat(coloredChat, filteredChat.toString()))
            {
                if (this.filterSettings.removeMatches(coloredChat))
                {
                    toTabs.clear();
                    skip = true;
                    break;
                }

                filteredChat = new StringBuilder(this.filterSettings.getLastMatchPretty());

                if (this.filterSettings.sendToTabBool(coloredChat))
                {
                    if (this.filterSettings.sendToAllTabs(coloredChat))
                    {
                        toTabs.clear();
                        var37 = this.channelMap.values().iterator();

                        while (var37.hasNext())
                        {
                            ChatChannel activeTabs = (ChatChannel)var37.next();
                            toTabs.add(activeTabs.title);
                        }

                        skip = true;
                        continue;
                    }

                    cleanedChat = this.filterSettings.sendToTabName(coloredChat);

                    if (!this.channelMap.containsKey(cleanedChat))
                    {
                        this.channelMap.put(cleanedChat, new ChatChannel(cleanedChat));
                    }

                    if (!toTabs.contains(cleanedChat))
                    {
                        toTabs.add(cleanedChat);
                    }
                }

                if (this.filterSettings.audioNotificationBool(coloredChat))
                {
                    this.filterSettings.audioNotification(coloredChat);
                }
            }
        }

        filteredChatLine.addAll(theChat);
        Iterator var36 = toTabs.iterator();

        while (var36.hasNext())
        {
            cleanedChat = (String)var36.next();
            this.addToChannel(cleanedChat, (List)filteredChatLine);
        }

        var36 = this.getActive().iterator();

        while (var36.hasNext())
        {
            cleanedChat = (String)var36.next();

            if (toTabs.contains(cleanedChat))
            {
                ++ret;
            }
        }

        String var38 = "";
        ChatLine var39;

        for (var37 = theChat.iterator(); var37.hasNext(); var38 = var38 + var39.getChatLineString())
        {
            var39 = (ChatLine)var37.next();
        }

        cleanedChat = StringUtils.stripControlCodes(var38);

        if (this.generalSettings.saveChatLog.getValue().booleanValue())
        {
            TabbyChatUtils.logChat(this.withTimeStamp(cleanedChat));
        }

        if (!skip)
        {
            this.chatChannelPatternClean.matcher(cleanedChat);
            Matcher c = this.chatChannelPatternDirty.matcher(var38);
            Matcher findChannelAlly = this.chatChannelPatternAlly.matcher(cleanedChat);
            Matcher findChannelEnemy = this.chatChannelPatternEnemy.matcher(cleanedChat);
            this.chatChannelPatternTruce.matcher(cleanedChat);
            Matcher findChannelCountry = this.chatChannelPatternCountry.matcher(cleanedChat);
            Matcher findChannelAdmin = this.chatChannelPatternAdmin.matcher(cleanedChat);
            Matcher findChannelModo = this.chatChannelPatternModo.matcher(cleanedChat);
            Matcher findChannelAll = this.chatChannelPatternAll.matcher(cleanedChat);
            Matcher findChannelPolice = this.chatChannelPatternPolice.matcher(cleanedChat);
            Matcher findChannelMafia = this.chatChannelPatternMafia.matcher(cleanedChat);
            Matcher findChannelJournaliste = this.chatChannelPatternJournaliste.matcher(cleanedChat);
            Matcher findChannelGuide = this.chatChannelPatternGuide.matcher(cleanedChat);
            Matcher findChannelAvocat = this.chatChannelPatternAvocat.matcher(cleanedChat);
            Matcher findChannelRP = this.chatChannelPatternRP.matcher(cleanedChat);
            Matcher findChannelLogs = this.chatChannelPatternLogs.matcher(cleanedChat);
            Matcher findChannelAnnonce = this.chatChannelPatternAnnonce.matcher(cleanedChat);
            Matcher findChannelEvent = this.chatChannelPatternEvent.matcher(cleanedChat);
            Matcher findChannelIle = this.chatChannelPatternIle.matcher(cleanedChat);
            Matcher findPMtoMe = this.chatPMtoMePattern.matcher(cleanedChat);
            Matcher findPMfromMe = this.chatPMfromMePattern.matcher(cleanedChat);

            if (!this.serverSettings.delimColorBool.getValue().booleanValue() && !this.serverSettings.delimFormatBool.getValue().booleanValue())
            {
                boolean var10000 = true;
            }
            else
            {
                c.find();
            }

            if (findChannelAll.find())
            {
                Iterator var34 = this.channelMap.values().iterator();

                while (var34.hasNext())
                {
                    ChatChannel chan = (ChatChannel)var34.next();

                    if (this.getActive().size() > 0 && ((String)this.getActive().get(0)).equals(chan.title))
                    {
                        ret += this.addToChannel(chan.title, (List)filteredChatLine);
                    }
                }
            }
            else if (findChannelCountry.find())
            {
                ret += this.addToChannel("Mon pays", (List)filteredChatLine);
                toTabs.add("Mon pays");
            }
            else if (findChannelAlly.find())
            {
                ret += this.addToChannel("ALL", (List)filteredChatLine);
                toTabs.add("ALL");
            }
            else if (findChannelEnemy.find())
            {
                ret += this.addToChannel("ENE", (List)filteredChatLine);
                toTabs.add("ENE");
            }
            else if (findChannelAdmin.find())
            {
                ret += this.addToChannel("ADMIN", (List)filteredChatLine);
                toTabs.add("ADMIN");
                ret += this.addToChannel("Global", (List)filteredChatLine);
                toTabs.add("Global");
            }
            else if (findChannelModo.find())
            {
                ret += this.addToChannel("MODO", (List)filteredChatLine);
                toTabs.add("MODO");
                ret += this.addToChannel("Global", (List)filteredChatLine);
                toTabs.add("Global");
            }
            else if (findChannelPolice.find())
            {
                ret += this.addToChannel("Police", (List)filteredChatLine);
                toTabs.add("Police");
            }
            else if (findChannelMafia.find())
            {
                ret += this.addToChannel("Mafia", (List)filteredChatLine);
                toTabs.add("Mafia");
            }
            else if (findChannelJournaliste.find())
            {
                ret += this.addToChannel("Journal", (List)filteredChatLine);
                toTabs.add("Journal");
            }
            else if (findChannelGuide.find())
            {
                ret += this.addToChannel("Guide", (List)filteredChatLine);
                toTabs.add("Guide");
                ret += this.addToChannel("Global", (List)filteredChatLine);
                toTabs.add("Global");
            }
            else if (findChannelAvocat.find())
            {
                ret += this.addToChannel("Avocat", (List)filteredChatLine);
                toTabs.add("Avocat");
            }
            else if (findChannelAnnonce.find())
            {
                ret += this.addToChannel("Annonce", (List)filteredChatLine);
                toTabs.add("Annonce");
            }
            else if (findChannelRP.find())
            {
                ret += this.addToChannel("RP", (List)filteredChatLine);
                toTabs.add("RP");
            }
            else if (findChannelLogs.find())
            {
                ret += this.addToChannel("Logs", (List)filteredChatLine);
                toTabs.add("Logs");
            }
            else if (findChannelEvent.find())
            {
                ret += this.addToChannel("Event", (List)filteredChatLine);
                toTabs.add("Event");
                ret += this.addToChannel("Global", (List)filteredChatLine);
                toTabs.add("Global");
            }
            else
            {
                String cName;

                if (findChannelIle.find())
                {
                    cName = cleanedChat.substring(findChannelIle.start(1), findChannelIle.end(1));
                    ret += this.addToChannel(cName, (List)filteredChatLine);
                    toTabs.add(cName);
                }
                else if (findPMtoMe.find())
                {
                    cName = cleanedChat.substring(findPMtoMe.start(1), findPMtoMe.end(1));
                    ret += this.addToChannel(cName, (List)filteredChatLine);
                    toTabs.add(cName);

                    if (!this.getActive().contains(cName))
                    {
                        Minecraft.getMinecraft().sndManager.playSoundFX(NotificationSoundEnum.HARP.file(), 1.0F, 1.0F);
                    }
                }
                else if (findPMfromMe.find())
                {
                    cName = cleanedChat.substring(findPMfromMe.start(1), findPMfromMe.end(1));
                    ret += this.addToChannel(cName, (List)filteredChatLine);
                    toTabs.add(cName);
                }
                else
                {
                    ret += this.addToChannel("Global", (List)filteredChatLine);
                    toTabs.add("Global");
                }
            }
        }

        if (ret == 0)
        {
            Iterator var40 = toTabs.iterator();

            while (var40.hasNext())
            {
                String var42 = (String)var40.next();

                if (var42 != "Global" && this.channelMap.containsKey(var42))
                {
                    ((ChatChannel)this.channelMap.get(var42)).unread = true;
                }
            }
        }

        List var41 = this.getActive();

        if (!this.generalSettings.groupSpam.getValue().booleanValue())
        {
            ;
        }

        if (var41.size() > 0)
        {
            if (toTabs.contains(var41.get(0)))
            {
                this.lastChat = TaggableChatLine.convertList(((ChatChannel)this.channelMap.get(var41.get(0))).chatLog.subList(0, Math.min(filteredChatLine.size(), ((ChatChannel)this.channelMap.get(var41.get(0))).chatLog.size())));
            }
            else
            {
                this.lastChat = TaggableChatLine.convertList(this.withTimeStamp((List)filteredChatLine));
            }
        }
        else
        {
            this.lastChat = TaggableChatLine.convertList(this.withTimeStamp((List)filteredChatLine));
        }

        if (ret > 0)
        {
            if (!this.generalSettings.groupSpam.getValue().booleanValue())
            {
                ;
            }

            if (((ChatChannel)this.channelMap.get(var41.get(0))).hasSpam)
            {
                gnc.setChatLines(0, this.lastChat);
            }
            else
            {
                gnc.addChatLines(0, this.lastChat);
            }
        }

        return ret;
    }

    public void removeTab(String _name)
    {
        this.channelMap.remove(_name);
    }

    public void resetDisplayedChat()
    {
        gnc.clearChatLines();
        List actives = this.getActive();

        if (actives.size() >= 1)
        {
            gnc.addChatLines(TaggableChatLine.convertList(((ChatChannel)this.channelMap.get(actives.get(0))).chatLog));
            int n = actives.size();

            for (int i = 1; i < n; ++i)
            {
                gnc.mergeChatLines(((ChatChannel)this.channelMap.get(actives.get(i))).chatLog);
            }
        }
    }

    public void updateButtonLocations()
    {
        boolean xOff = false;
        boolean yOff = false;
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int maxlines = gnc.getHeightSetting() / 9;

        if (gnc.GetChatHeight() < maxlines)
        {
            gnc.GetChatHeight();
        }

        int vert = sr.getScaledHeight() - gnc.chatHeight - 51;
        int horiz = 5;
        int n = this.channelMap.size();

        try
        {
            if (TabbyChatUtils.is(mc.ingameGUI.getChatGUI(), "GuiNewChatWrapper"))
            {
                Class i = Class.forName("advancedhud.ahuditem.DefaultHudItems");
                Field aHudFld = i.getField("chat");
                Object chan = aHudFld.get((Object)null);
                i = Class.forName("advancedhud.ahuditem.HudItem");
                int dVert = mc.currentScreen.height - 22 - 108;
                int xOff1 = i.getField("posX").getInt(chan) - 3;
                int yOff1 = i.getField("posY").getInt(chan) - dVert;
                horiz += xOff1;
                vert -= yOff1;

                if (gnc.getChatOpen())
                {
                    ((GuiChatTC)mc.currentScreen).scrollBar.setOffset(xOff1, yOff1);
                }
            }
        }
        catch (Throwable var13)
        {
            ;
        }

        boolean i1 = false;
        ChatChannel chan1;

        for (Iterator aHudFld1 = this.channelMap.values().iterator(); aHudFld1.hasNext(); horiz = chan1.getButtonEnd() + 1)
        {
            chan1 = (ChatChannel)aHudFld1.next();
            chan1.tab.width(mc.fontRenderer.getStringWidth("<" + chan1.title + ">") + 8);

            if (horiz + chan1.tab.width() > gnc.chatWidth - 5)
            {
                vert -= chan1.tab.height();
                horiz = 5;
            }

            chan1.setButtonLoc(horiz, vert);

            if (chan1.tab == null)
            {
                chan1.setButtonObj(new ChatButton(chan1.getID(), horiz, vert, chan1.tab.width(), chan1.tab.height(), chan1.getDisplayTitle()));
            }
            else
            {
                chan1.tab.id = chan1.getID();
                chan1.tab.xPosition = horiz;
                chan1.tab.yPosition = vert;
                chan1.tab.displayString = chan1.getDisplayTitle();
            }
        }
    }
}
