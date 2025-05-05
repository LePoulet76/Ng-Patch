package acs.tabbychat;

import acs.tabbychat.ChannelDelimEnum;
import acs.tabbychat.ColorCodeEnum;
import acs.tabbychat.FormatCodeEnum;
import acs.tabbychat.ServerSettings;
import acs.tabbychat.TCSettingBool;
import acs.tabbychat.TCSettingEnum;
import acs.tabbychat.TCSettingTextBox;
import acs.tabbychat.TCSettingsGUI;
import acs.tabbychat.TabbyChat;
import acs.tabbychat.TabbyChatUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class TCSettingsServer extends TCSettingsGUI {

   private static final int autoChannelSearchID = 9201;
   private static final int chatChannelDelimsID = 9202;
   private static final int delimColorBoolID = 9203;
   private static final int delimColorEnumID = 9204;
   private static final int delimFormatBoolID = 9205;
   private static final int delimFormatEnumID = 9206;
   private static final int defaultChansID = 9207;
   private static final int ignoredChansID = 9208;
   protected TCSettingBool autoChannelSearch;
   protected TCSettingEnum delimiterChars;
   protected TCSettingBool delimColorBool;
   protected TCSettingEnum delimColorCode;
   protected TCSettingBool delimFormatBool;
   protected TCSettingEnum delimFormatCode;
   protected TCSettingTextBox defaultChannels;
   protected TCSettingTextBox ignoredChannels;
   protected ServerData server;
   public String serverName;
   public String serverIP;


   public TCSettingsServer() {
      this.autoChannelSearch = new TCSettingBool(Boolean.valueOf(true), "Auto-search for new channels", 9201);
      this.delimiterChars = new TCSettingEnum(ChannelDelimEnum.BRACKETS, "Chat-channel delimiters", 9202);
      this.delimColorBool = new TCSettingBool(Boolean.valueOf(false), "\u00a7oColored delimiters\u00a7r", 9203);
      this.delimColorCode = new TCSettingEnum(ColorCodeEnum.DEFAULT, "", 9204);
      this.delimFormatBool = new TCSettingBool(Boolean.valueOf(false), "\u00a7oFormatted delimiters\u00a7r", 9205);
      this.delimFormatCode = new TCSettingEnum(FormatCodeEnum.DEFAULT, "", 9206);
      this.defaultChannels = new TCSettingTextBox("Default channels", 9207);
      this.ignoredChannels = new TCSettingTextBox("Ignored channels", 9208);
      this.name = "Server Config";
      this.bgcolor = 1725355587;
      this.defaultChannels.setCharLimit(300);
      this.ignoredChannels.setCharLimit(300);
      this.updateForServer();
   }

   protected TCSettingsServer(TabbyChat _tc) {
      this();
      tc = _tc;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int var10000 = this.field_73880_f;
      this.getClass();
      int effLeft = (var10000 - 325) / 2;
      this.getClass();
      var10000 = effLeft - 4;
      var10000 = this.field_73881_g;
      this.getClass();
      int effTop = (var10000 - 180) / 2;
      this.getClass();
      var10000 = effTop - 4;
      var10000 = this.field_73880_f;
      this.getClass();
      int effRight = (var10000 + 325) / 2;
      var10000 = this.field_73880_f;
      this.getClass();
      int col1x = (var10000 - 325) / 2 + 100;
      var10000 = this.field_73880_f;
      this.getClass();
      int col2x = (var10000 + 325) / 2 - 65;
      int buttonColor = (this.bgcolor & 16777215) + -16777216;
      this.autoChannelSearch.setButtonLoc(col1x, this.rowY(1));
      this.autoChannelSearch.labelX = col1x + 19;
      this.autoChannelSearch.buttonOnColor = buttonColor;
      this.field_73887_h.add(this.autoChannelSearch);
      this.delimiterChars.labelX = col1x;
      this.delimiterChars.setButtonLoc(col1x + 20 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.delimiterChars.description), this.rowY(2));
      this.delimiterChars.setButtonDims(80, 11);
      this.field_73887_h.add(this.delimiterChars);
      this.delimColorBool.setButtonLoc(col1x + 20, this.rowY(3));
      this.delimColorBool.labelX = col1x + 39;
      this.delimColorBool.buttonOnColor = buttonColor;
      this.field_73887_h.add(this.delimColorBool);
      this.delimColorCode.setButtonLoc(effRight - 70, this.rowY(3));
      this.delimColorCode.setButtonDims(70, 11);
      this.field_73887_h.add(this.delimColorCode);
      this.delimFormatBool.setButtonLoc(col1x + 20, this.rowY(4));
      this.delimFormatBool.labelX = col1x + 39;
      this.delimFormatBool.buttonOnColor = buttonColor;
      this.field_73887_h.add(this.delimFormatBool);
      this.delimFormatCode.setButtonLoc(this.delimColorCode.field_73746_c, this.rowY(4));
      this.delimFormatCode.setButtonDims(70, 11);
      this.field_73887_h.add(this.delimFormatCode);
      this.defaultChannels.labelX = col1x;
      this.defaultChannels.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.defaultChannels.description), this.rowY(5));
      this.defaultChannels.setButtonDims(effRight - this.defaultChannels.field_73746_c, 11);
      this.field_73887_h.add(this.defaultChannels);
      this.ignoredChannels.labelX = col1x;
      this.ignoredChannels.setButtonLoc(col1x + 5 + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.ignoredChannels.description), this.rowY(6));
      this.ignoredChannels.setButtonDims(effRight - this.ignoredChannels.field_73746_c, 11);
      this.field_73887_h.add(this.ignoredChannels);
      this.validateButtonStates();
   }

   public void validateButtonStates() {
      this.delimColorBool.field_73742_g = this.autoChannelSearch.getTempValue().booleanValue();
      this.delimFormatBool.field_73742_g = this.autoChannelSearch.getTempValue().booleanValue();
      this.delimColorCode.field_73742_g = this.delimColorBool.getTempValue().booleanValue() && this.autoChannelSearch.getTempValue().booleanValue();
      this.delimFormatCode.field_73742_g = this.delimFormatBool.getTempValue().booleanValue() && this.autoChannelSearch.getTempValue().booleanValue();
      this.delimiterChars.field_73742_g = this.autoChannelSearch.getTempValue().booleanValue();
   }

   protected void storeTempVars() {
      this.autoChannelSearch.save();
      this.delimiterChars.save();
      this.delimColorBool.save();
      this.delimColorCode.save();
      this.delimFormatBool.save();
      this.delimFormatCode.save();
      this.defaultChannels.save();
      this.ignoredChannels.save();
   }

   protected void resetTempVars() {
      this.autoChannelSearch.reset();
      this.delimiterChars.reset();
      this.delimColorBool.reset();
      this.delimColorCode.reset();
      this.delimFormatBool.reset();
      this.delimFormatCode.reset();
      this.defaultChannels.reset();
      this.ignoredChannels.reset();
   }

   protected void importSettings() {
      this.autoChannelSearch.setValue(Boolean.valueOf(tc.globalPrefs.autoSearchEnabled));
      this.delimiterChars.setValue(tc.serverPrefs.chanDelims);
      this.delimColorCode.setValue(TabbyChatUtils.parseColor(tc.serverPrefs.chanDelimColor.name()));
      this.delimFormatCode.setValue(TabbyChatUtils.parseFormat(tc.serverPrefs.chanDelimFormat.name()));
      this.defaultChannels.setValue(TabbyChatUtils.join(tc.serverPrefs.defaultChans, ","));
      this.ignoredChannels.setValue(TabbyChatUtils.join(tc.serverPrefs.ignoredChans, ","));
      this.resetTempVars();
   }

   protected boolean loadSettingsFile() {
      boolean loaded = false;
      if(this.server == null) {
         return loaded;
      } else {
         String ip = this.serverIP;
         if(ip.contains(":")) {
            ip = ip.replaceAll(":", "(") + ")";
         }

         File settingsDir = new File(tabbyChatDir, ip);
         this.settingsFile = new File(settingsDir, "settings.cfg");
         if(!this.settingsFile.exists()) {
            return loaded;
         } else {
            Properties settingsTable = new Properties();

            try {
               FileInputStream e = new FileInputStream(this.settingsFile);
               settingsTable.load(e);
               e.close();
               loaded = true;
            } catch (Exception var6) {
               TabbyChat.printErr("Unable to read from server settings file : \'" + var6.getLocalizedMessage() + "\' : " + var6.toString());
               loaded = false;
            }

            this.autoChannelSearch.setValue(Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty("autoChannelSearch"))));
            this.delimiterChars.setValue(TabbyChatUtils.parseDelimiters(settingsTable.getProperty("delimiterChars")));
            this.delimColorBool.setValue(Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty("delimColorBool"))));
            this.delimColorCode.setValue(TabbyChatUtils.parseColor(settingsTable.getProperty("delimColorCode")));
            this.delimFormatBool.setValue(Boolean.valueOf(Boolean.parseBoolean(settingsTable.getProperty("delimFormatBool"))));
            this.delimFormatCode.setValue(TabbyChatUtils.parseFormat(settingsTable.getProperty("delimFormatCode")));
            this.defaultChannels.setValue(settingsTable.getProperty("defaultChannels"));
            this.ignoredChannels.setValue(settingsTable.getProperty("ignoredChannels"));
            this.resetTempVars();
            return loaded;
         }
      }
   }

   protected void saveSettingsFile() {
      String ip = this.serverIP;
      if(ip.contains(":")) {
         ip = ip.replaceAll(":", "(") + ")";
      }

      File settingsDir = new File(tabbyChatDir, ip);
      if(!settingsDir.exists()) {
         settingsDir.mkdirs();
      }

      this.settingsFile = new File(settingsDir, "settings.cfg");
      Properties settingsTable = new Properties();
      settingsTable.put("autoChannelSearch", this.autoChannelSearch.getValue().toString());
      settingsTable.put("delimiterChars", this.delimiterChars.getValue().name());
      settingsTable.put("delimColorBool", this.delimColorBool.getValue().toString());
      settingsTable.put("delimColorCode", this.delimColorCode.getValue().name());
      settingsTable.put("delimFormatBool", this.delimFormatBool.getValue().toString());
      settingsTable.put("delimFormatCode", this.delimFormatCode.getValue().name());
      settingsTable.put("defaultChannels", this.defaultChannels.getValue());
      settingsTable.put("ignoredChannels", this.ignoredChannels.getValue());

      try {
         FileOutputStream e = new FileOutputStream(this.settingsFile);
         settingsTable.store(e, "Server config");
         e.close();
      } catch (Exception var5) {
         TabbyChat.printErr("Unable to write to server config file : \'" + var5.getLocalizedMessage() + "\' : " + var5.toString());
      }

   }

   public void updateForServer() {
      if(!Minecraft.func_71410_x().func_71356_B() && ServerSettings.getServerData() != null) {
         this.server = ServerSettings.getServerData();
         this.serverName = this.server.field_78847_a;
         this.serverIP = this.server.field_78845_b;
      } else {
         this.server = null;
         this.settingsFile = null;
         this.serverName = "";
         this.serverIP = "";
      }

   }
}
