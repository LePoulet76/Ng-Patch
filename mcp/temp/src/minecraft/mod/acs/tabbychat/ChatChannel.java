package acs.tabbychat;

import acs.tabbychat.ChatButton;
import acs.tabbychat.TabbyChat;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class ChatChannel {

   private static int nextID = 3600;
   public String title;
   public ChatButton tab;
   public ArrayList<ChatLine> chatLog;
   protected int chanID;
   public boolean unread;
   public boolean active;
   protected boolean hasFilter;
   protected boolean hasSpam;
   protected int spamCount;


   public ChatChannel() {
      this.chanID = nextID + 1;
      this.unread = false;
      this.active = false;
      this.hasFilter = false;
      this.hasSpam = false;
      this.spamCount = 1;
      this.chanID = nextID++;
      this.chatLog = new ArrayList(100);
   }

   public ChatChannel(int _x, int _y, int _w, int _h, String _title) {
      this();
      this.tab = new ChatButton(this.chanID, _x, _y, _w, _h, _title);
      this.title = _title;
      this.tab.channel = this;
   }

   public ChatChannel(String _title) {
      this(3, 3, Minecraft.func_71410_x().field_71466_p.func_78256_a("<" + _title + ">") + 8, 14, _title);
   }

   public boolean doesButtonEqual(GuiButton btnObj) {
      return this.tab.field_73741_f == btnObj.field_73741_f;
   }

   public int getButtonEnd() {
      return this.tab.field_73746_c + this.tab.width();
   }

   public int getID() {
      return this.chanID;
   }

   public String getDisplayTitle() {
      String label = this.title;
      if(Arrays.asList(new String[]{"Mon pays", "MODO", "Journal", "Avocat"}).contains(this.title)) {
         label = I18n.func_135053_a("chat.tab." + this.title.toLowerCase().replace(" ", "_"));
      }

      return this.active?"[" + label + "]":(this.unread?"<" + label + ">":label);
   }

   public void setButtonObj(ChatButton btnObj) {
      this.tab = btnObj;
      this.tab.channel = this;
   }

   public String toString() {
      return this.getDisplayTitle();
   }

   public void clear() {
      this.chatLog.clear();
      this.tab.clear();
      this.tab = null;
   }

   public void setButtonLoc(int _x, int _y) {
      this.tab.field_73746_c = _x;
      this.tab.field_73743_d = _y;
   }

   public void trimLog() {
      if(TabbyChat.instance != null && this.chatLog.size() >= Integer.parseInt(TabbyChat.instance.advancedSettings.chatScrollHistory.getValue()) + 5) {
         this.chatLog.subList(this.chatLog.size() - 11, this.chatLog.size() - 1).clear();
      }

   }

   public void unreadNotify(Gui _gui, int _y, int _opacity) {
      float scaleSetting = TabbyChat.gnc.getScaleSetting();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 20.0F, 0.0F);
      GL11.glScalef(scaleSetting, scaleSetting, 1.0F);
      TabbyChat var10000 = TabbyChat.instance;
      TabbyChat.mc.field_71456_v.func_73827_b();
      GuiNewChat.func_73734_a(this.tab.field_73746_c, -this.tab.height() + _y, this.tab.field_73746_c + this.tab.width(), _y, 7471104 + (_opacity / 2 << 24));
      GL11.glEnable(3042);
      var10000 = TabbyChat.instance;
      GuiNewChat var5 = TabbyChat.mc.field_71456_v.func_73827_b();
      TabbyChat var10001 = TabbyChat.instance;
      var5.func_73732_a(TabbyChat.mc.field_71466_p, this.getDisplayTitle(), this.tab.field_73746_c + this.tab.width() / 2, -(this.tab.height() + 8) / 2 + _y, 16711680 + (_opacity << 24));
      GL11.glPopMatrix();
   }

}
