package acs.tabbychat;

import acs.tabbychat.PrefsButton;
import acs.tabbychat.TCSetting;
import acs.tabbychat.TCSettingTextBox;
import acs.tabbychat.TabbyChat;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class TCSettingsGUI extends GuiScreen {

   protected static TabbyChat tc;
   protected static Minecraft mc;
   protected final int margin;
   protected final int line_height;
   public final int displayWidth;
   public final int displayHeight;
   protected int lastOpened;
   protected String name;
   protected int bgcolor;
   protected int id;
   protected static List<TCSettingsGUI> ScreenList = new ArrayList();
   public static File tabbyChatDir = new File("config/ngchat");
   protected File settingsFile;
   private static final int saveButton = 8901;
   private static final int cancelButton = 8902;


   public TCSettingsGUI() {
      this.margin = 4;
      this.line_height = 14;
      this.displayWidth = 325;
      this.displayHeight = 180;
      this.lastOpened = 0;
      this.name = "";
      this.bgcolor = 1722148836;
      this.id = 9000;
      mc = Minecraft.func_71410_x();
      ScreenList.add(this);
   }

   protected TCSettingsGUI(TabbyChat _tc) {
      this();
      tc = _tc;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
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
      this.lastOpened = mc.field_71456_v.func_73834_c();
      var10000 = this.field_73880_f;
      this.getClass();
      int effRight = (var10000 + 325) / 2;
      byte bW = 40;
      this.getClass();
      byte bH = 14;
      PrefsButton var11 = new PrefsButton;
      int var10003 = effRight - bW;
      int var10004 = this.field_73881_g;
      this.getClass();
      var11.<init>(8901, var10003, (var10004 + 180) / 2 - bH, bW, bH, "Save");
      PrefsButton savePrefs = var11;
      this.field_73887_h.add(savePrefs);
      var11 = new PrefsButton;
      var10003 = effRight - 2 * bW - 2;
      var10004 = this.field_73881_g;
      this.getClass();
      var11.<init>(8902, var10003, (var10004 + 180) / 2 - bH, bW, bH, "Cancel");
      PrefsButton cancelPrefs = var11;
      this.field_73887_h.add(cancelPrefs);

      for(int i = 0; i < ScreenList.size(); ++i) {
         ((TCSettingsGUI)ScreenList.get(i)).id = 9000 + i;
         if(ScreenList.get(i) != this) {
            this.field_73887_h.add(new PrefsButton(((TCSettingsGUI)ScreenList.get(i)).id, effLeft, effTop + 30 * i, 45, 20, mc.field_71466_p.func_78269_a(((TCSettingsGUI)ScreenList.get(i)).name, 35) + "..."));
            ((PrefsButton)this.field_73887_h.get(this.field_73887_h.size() - 1)).bgcolor = 0;
         }
      }

   }

   public void func_73864_a(int par1, int par2, int par3) {
      for(int i = 0; i < this.field_73887_h.size(); ++i) {
         if(TCSetting.class.isInstance(this.field_73887_h.get(i))) {
            TCSetting tmp = (TCSetting)this.field_73887_h.get(i);
            if(tmp.type == "textbox" || tmp.type == "enum" || tmp.type == "slider") {
               tmp.mouseClicked(par1, par2, par3);
            }
         }
      }

      super.func_73864_a(par1, par2, par3);
   }

   protected void func_73869_a(char par1, int par2) {
      for(int i = 0; i < this.field_73887_h.size(); ++i) {
         if(TCSetting.class.isInstance(this.field_73887_h.get(i))) {
            TCSetting tmp = (TCSetting)this.field_73887_h.get(i);
            if(tmp.type == "textbox") {
               ((TCSettingTextBox)tmp).keyTyped(par1, par2);
            }
         }
      }

      super.func_73869_a(par1, par2);
   }

   public void func_73875_a(GuiButton button) {
      if(TCSetting.class.isInstance(button) && ((TCSetting)button).type != "textbox") {
         ((TCSetting)button).actionPerformed();
      } else {
         Iterator i;
         TCSettingsGUI screen;
         if(button.field_73741_f == 8901) {
            i = ScreenList.iterator();

            while(i.hasNext()) {
               screen = (TCSettingsGUI)i.next();
               screen.storeTempVars();
               screen.saveSettingsFile();
            }

            tc.updateDefaults();
            tc.loadPatterns();
            tc.updateFilters();
            mc.func_71373_a((GuiScreen)null);
            if(tc.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
               tc.resetDisplayedChat();
            }
         } else if(button.field_73741_f == 8902) {
            i = ScreenList.iterator();

            while(i.hasNext()) {
               screen = (TCSettingsGUI)i.next();
               screen.resetTempVars();
            }

            mc.func_71373_a((GuiScreen)null);
            if(tc.generalSettings.tabbyChatEnable.getValue().booleanValue()) {
               tc.resetDisplayedChat();
            }
         } else {
            for(int var4 = 0; var4 < ScreenList.size(); ++var4) {
               if(button.field_73741_f == ((TCSettingsGUI)ScreenList.get(var4)).id) {
                  mc.func_71373_a((GuiScreen)ScreenList.get(var4));
               }
            }
         }
      }

      this.validateButtonStates();
   }

   public void validateButtonStates() {}

   protected boolean loadSettingsFile() {
      return false;
   }

   protected void saveSettingsFile() {}

   protected void storeTempVars() {}

   protected void resetTempVars() {}

   protected int rowY(int rowNum) {
      int var10000 = this.field_73881_g;
      this.getClass();
      var10000 = (var10000 - 180) / 2;
      int var10001 = rowNum - 1;
      this.getClass();
      this.getClass();
      return var10000 + var10001 * (14 + 4);
   }

   public void func_73863_a(int x, int y, float f) {
      boolean unicodeStore = mc.field_71466_p.func_82883_a();
      if(tc.generalSettings.tabbyChatEnable.getValue().booleanValue() && tc.advancedSettings.forceUnicode.getValue().booleanValue()) {
         mc.field_71466_p.func_78264_a(true);
      }

      this.getClass();
      int iMargin = (14 - mc.field_71466_p.field_78288_b) / 2;
      int var10000 = this.field_73880_f;
      this.getClass();
      int effLeft = (var10000 - 325) / 2;
      this.getClass();
      int absLeft = effLeft - 4;
      var10000 = this.field_73881_g;
      this.getClass();
      int effTop = (var10000 - 180) / 2;
      this.getClass();
      int absTop = effTop - 4;
      this.getClass();
      int var10002 = absLeft + 325;
      this.getClass();
      var10002 += 2 * 4;
      this.getClass();
      int var10003 = absTop + 180;
      this.getClass();
      func_73734_a(absLeft, absTop, var10002, var10003 + 2 * 4, -2013265920);

      int i;
      for(i = 0; i < ScreenList.size(); ++i) {
         if(ScreenList.get(i) == this) {
            int delta = mc.field_71456_v.func_73834_c() - this.lastOpened;
            var10000 = mc.field_71466_p.func_78256_a(((TCSettingsGUI)ScreenList.get(i)).name);
            this.getClass();
            int tabDist = var10000 + 2 * 4 - 40;
            int curWidth;
            if(delta <= 5) {
               curWidth = 45 + delta * tabDist / 5;
            } else {
               curWidth = tabDist + 45;
            }

            func_73734_a(absLeft, effTop + 30 * i, absLeft + curWidth, effTop + 30 * i + 20, ((TCSettingsGUI)ScreenList.get(i)).bgcolor);
            func_73734_a(absLeft + 45, absTop, absLeft + 46, effTop + 30 * i, 1728053247);
            var10000 = absLeft + 45;
            int var10001 = effTop + 30 * i + 20;
            var10002 = absLeft + 46;
            this.getClass();
            func_73734_a(var10000, var10001, var10002, absTop + 180, 1728053247);
            this.func_73731_b(mc.field_71466_p, mc.field_71466_p.func_78269_a(((TCSettingsGUI)ScreenList.get(i)).name, curWidth), effLeft, effTop + 6 + 30 * i, 16777215);
         } else {
            func_73734_a(absLeft, effTop + 30 * i, absLeft + 45, effTop + 30 * i + 20, ((TCSettingsGUI)ScreenList.get(i)).bgcolor);
         }
      }

      for(i = 0; i < this.field_73887_h.size(); ++i) {
         ((GuiButton)this.field_73887_h.get(i)).func_73737_a(mc, x, y);
      }

      mc.field_71466_p.func_78264_a(unicodeStore);
   }

}
