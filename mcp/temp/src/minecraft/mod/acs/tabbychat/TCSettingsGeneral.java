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

public class TCSettingsGeneral extends TCSettingsGUI {

   protected SimpleDateFormat timeStamp;
   private static final int tabbyChatEnableID = 9101;
   private static final int saveChatLogID = 9102;
   private static final int timeStampEnableID = 9103;
   private static final int timeStampStyleID = 9104;
   private static final int groupSpamID = 9105;
   private static final int unreadFlashingID = 9106;
   public TCSettingBool tabbyChatEnable;
   protected TCSettingBool saveChatLog;
   public TCSettingBool timeStampEnable;
   public TCSettingEnum timeStampStyle;
   protected TCSettingBool groupSpam;
   public TCSettingBool unreadFlashing;


   public TCSettingsGeneral() {
      this.timeStamp = new SimpleDateFormat();
      this.tabbyChatEnable = new TCSettingBool(Boolean.valueOf(true), "TabbyChat Enabled", 9101);
      this.saveChatLog = new TCSettingBool(Boolean.valueOf(false), "Log chat to file", 9102);
      this.timeStampEnable = new TCSettingBool(Boolean.valueOf(false), "Timestamp chat", 9103);
      this.timeStampStyle = new TCSettingEnum(TimeStampEnum.MILITARY, "\u00a7oTimestamp Style\u00a7r", 9104);
      this.groupSpam = new TCSettingBool(Boolean.valueOf(false), "Consolidate spammed chat", 9105);
      this.unreadFlashing = new TCSettingBool(Boolean.valueOf(true), "Unread notification flashing", 9106);
      this.name = "General Config";
      this.bgcolor = 1715962558;
   }

   protected TCSettingsGeneral(TabbyChat _tc) {
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

   public void func_73875_a(GuiButton button) {
      super.func_73875_a(button);
      switch(button.field_73741_f) {
      case 9101:
         if(TabbyChat.instance.enabled()) {
            TabbyChat.instance.disable();
         } else {
            TabbyChat.instance.enable();
         }
      default:
         this.validateButtonStates();
      }
   }

   public void validateButtonStates() {
      this.timeStampStyle.field_73742_g = this.timeStampEnable.getTempValue().booleanValue();
   }

   protected void importSettings() {
      this.tabbyChatEnable.setValue(Boolean.valueOf(tc.globalPrefs.TCenabled));
      this.saveChatLog.setValue(Boolean.valueOf(tc.globalPrefs.saveLocalLogEnabled));
      this.timeStampEnable.setValue(Boolean.valueOf(tc.globalPrefs.timestampsEnabled));
      this.timeStampStyle.setValue(tc.globalPrefs.timestampStyle);
      this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
      this.resetTempVars();
   }

   protected boolean loadSettingsFile() {
      this.settingsFile = new File(tabbyChatDir, "general.cfg");
      boolean loaded = false;
      if(!this.settingsFile.exists()) {
         return loaded;
      } else {
         Properties settingsTable = new Properties();

         try {
            FileInputStream e = new FileInputStream(this.settingsFile);
            settingsTable.load(e);
            e.close();
         } catch (Exception var4) {
            TabbyChat.printErr("Unable to read from general settings file : \'" + var4.getLocalizedMessage() + "\' : " + var4.toString());
         }

         this.tabbyChatEnable.setValue(Boolean.valueOf(Boolean.parseBoolean((String)settingsTable.get("tabbyChatEnable"))));
         this.saveChatLog.setValue(Boolean.valueOf(Boolean.parseBoolean((String)settingsTable.get("saveChatLog"))));
         this.timeStampEnable.setValue(Boolean.valueOf(Boolean.parseBoolean((String)settingsTable.get("timeStampEnable"))));
         this.timeStampStyle.setValue(TabbyChatUtils.parseTimestamp((String)settingsTable.get("timeStampStyle")));
         this.groupSpam.setValue(Boolean.valueOf(Boolean.parseBoolean((String)settingsTable.get("groupSpam"))));
         this.unreadFlashing.setValue(Boolean.valueOf(Boolean.parseBoolean((String)settingsTable.get("unreadFlashing"))));
         loaded = true;
         this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
         this.resetTempVars();
         return loaded;
      }
   }

   protected void saveSettingsFile() {
      if(!tabbyChatDir.exists()) {
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
         FileOutputStream e = new FileOutputStream(this.settingsFile);
         settingsTable.store(e, "General settings");
         e.close();
      } catch (Exception var3) {
         TabbyChat.printErr("Unable to write to general settings file : \'" + var3.getLocalizedMessage() + "\' : " + var3.toString());
      }

   }

   protected void storeTempVars() {
      this.tabbyChatEnable.save();
      this.saveChatLog.save();
      this.timeStampEnable.save();
      this.timeStampStyle.save();
      this.groupSpam.save();
      this.unreadFlashing.save();
      this.timeStamp.applyPattern(((TimeStampEnum)this.timeStampStyle.getValue()).toCode());
   }

   protected void resetTempVars() {
      this.tabbyChatEnable.reset();
      this.saveChatLog.reset();
      this.timeStampEnable.reset();
      this.timeStampStyle.reset();
      this.groupSpam.reset();
      this.unreadFlashing.reset();
   }
}
