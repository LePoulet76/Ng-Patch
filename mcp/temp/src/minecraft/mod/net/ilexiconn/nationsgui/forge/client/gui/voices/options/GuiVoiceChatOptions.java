package net.ilexiconn.nationsgui.forge.client.gui.voices.options;

import net.ilexiconn.nationsgui.forge.client.gui.voices.GuiBoostSlider;
import net.ilexiconn.nationsgui.forge.client.gui.voices.GuiDropDownMenu;
import net.ilexiconn.nationsgui.forge.client.gui.voices.options.GuiVoiceChatOptions$GuiToggleButton;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.device.Device;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeySpeakEvent;
import net.ilexiconn.nationsgui.forge.client.voices.sound.MicrophoneTester;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiVoiceChatOptions extends GuiScreen {

   VoiceChatClient voiceChat;
   MicrophoneTester tester;
   GuiBoostSlider voiceVolume;
   GuiDropDownMenu dropDown;
   GuiButton micModeButton;
   GuiVoiceChatOptions$GuiToggleButton enableVoiceButton;
   private GuiButton encodingMode;
   private GuiButton enchantedDecoding;
   private GuiButton serverConnection;
   private GuiBoostSlider qualitySlider;
   boolean resetRequired;
   private GuiScreen parent;


   public GuiVoiceChatOptions(VoiceChatClient voiceChat, GuiScreen parent) {
      this.voiceChat = voiceChat;
      this.tester = new MicrophoneTester(voiceChat);
      this.parent = parent;
   }

   public void func_73866_w_() {
      String[] array = new String[this.voiceChat.getSettings().getDeviceHandler().getDevices().size()];

      for(int var7 = 0; var7 < this.voiceChat.getSettings().getDeviceHandler().getDevices().size(); ++var7) {
         array[var7] = ((Device)this.voiceChat.getSettings().getDeviceHandler().getDevices().get(var7)).getName();
      }

      byte var71 = 55;
      this.enableVoiceButton = new GuiVoiceChatOptions$GuiToggleButton(415, (this.field_73880_f - 304) / 2, this.field_73881_g / 2 - 105, 304, 20, VoiceChat.getProxyInstance().getSettings().isVoiceEnable());
      this.dropDown = new GuiDropDownMenu(0, this.field_73880_f / 2 - 152, this.field_73881_g / 2 - var71, 150, 20, this.voiceChat.getSettings().getInputDevice() != null?this.voiceChat.getSettings().getInputDevice().getName():"None", array);
      this.micModeButton = new GuiButton(5, this.field_73880_f / 2 - 152, this.field_73881_g / 2 + 25 - var71, 150, 20, I18n.func_135053_a("voice.mode") + " " + (this.voiceChat.getSettings().getSpeakMode() == 0?"Push To Talk":"Toggle To Talk"));
      this.voiceVolume = new GuiBoostSlider(910, this.field_73880_f / 2 + 2, this.field_73881_g / 2 - 25 - var71, "VALUE", I18n.func_135053_a("voice.volume") + " " + (this.voiceChat.getSettings().getWorldVolume() == 0.0F?"OFF":"" + (int)(this.voiceChat.getSettings().getWorldVolume() * 100.0F) + "%"), 0.0F);
      this.voiceVolume.sliderValue = this.voiceChat.getSettings().getWorldVolume();
      this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 2 - 152, this.field_73881_g / 2 - 25 - var71, 150, 20, !this.tester.recording?I18n.func_135053_a("voice.test_micro"):I18n.func_135053_a("voice.stop_micro")));
      this.field_73887_h.add(new GuiButton(3, this.field_73880_f / 2 - 75, this.field_73881_g / 2 + 100 - var71, 150, 20, I18n.func_135053_a("gui.done")));
      this.field_73887_h.add(this.micModeButton);
      this.field_73887_h.add(this.voiceVolume);
      this.field_73887_h.add(this.enableVoiceButton);
      if(this.voiceChat.getSettings().getDeviceHandler().isEmpty()) {
         GuiButton button1 = (GuiButton)this.field_73887_h.get(0);
         GuiButton button2 = (GuiButton)this.field_73887_h.get(1);
         GuiButton button3 = (GuiButton)this.field_73887_h.get(3);
         button1.field_73742_g = false;
         button2.field_73742_g = false;
         button3.field_73742_g = false;
         this.micModeButton.field_73742_g = false;
         String noDevices = I18n.func_135053_a("voice.no_input");
         this.func_73731_b(this.field_73886_k, noDevices, this.field_73880_f / 2 - this.field_73886_k.func_78256_a(noDevices) / 2, this.field_73881_g - 70, -269484032);
      }

      this.field_73887_h.add(new GuiButton(151, this.field_73880_f / 2 + 77, this.field_73881_g / 2 + 100 - var71, 75, 20, "Reset"));
      this.qualitySlider = new GuiBoostSlider(154, this.field_73880_f / 2 + 2, this.field_73881_g / 2 + 50 - var71, "VALUE", I18n.func_135053_a("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F?" 0":" " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F)), 0.0F);
      this.qualitySlider.sliderValue = this.voiceChat.getSettings().getEncodingQuality();
      this.encodingMode = new GuiButton(155, this.field_73880_f / 2 - 152, this.field_73881_g / 2 + 75 - var71, 150, 20, I18n.func_135053_a("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString());
      this.field_73887_h.add(this.enchantedDecoding = new GuiButton(156, this.field_73880_f / 2 - 152, this.field_73881_g / 2 + 50 - var71, 150, 20, I18n.func_135053_a("voice.encoding_quality") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed()?"ON":"OFF")));
      this.field_73887_h.add(this.qualitySlider);
      this.field_73887_h.add(this.encodingMode);
      this.encodingMode.field_73742_g = false;
      this.field_73887_h.add(this.dropDown);
   }

   public void func_73876_c() {
      this.voiceChat.getSettings().setWorldVolume(this.voiceVolume.sliderValue);
      this.voiceVolume.setDisplayString(I18n.func_135053_a("voice.volume") + " " + (this.voiceChat.getSettings().getWorldVolume() == 0.0F?"OFF":(int)(this.voiceChat.getSettings().getWorldVolume() * 100.0F) + "%"));
      this.voiceChat.getSettings().setEncodingQuality(this.qualitySlider.sliderValue);
      this.qualitySlider.setDisplayString(I18n.func_135053_a("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F?" 0":" " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F)));
      if(this.resetRequired) {
         this.qualitySlider.sliderValue = 0.6F;
         this.voiceChat.getSettings().setEncodingQuality(this.qualitySlider.sliderValue);
         this.qualitySlider.idValue = I18n.func_135053_a("voice.encoding_quality") + (this.voiceChat.getSettings().getEncodingQuality() == 0.0F?" 0":" " + (int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F));
         this.qualitySlider.field_73744_e = this.qualitySlider.idValue;
         this.voiceChat.getSettings().setEncodingMode(1);
         this.encodingMode.field_73744_e = I18n.func_135053_a("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString();
         this.voiceChat.getSettings().setPerceptualEnchantment(true);
         this.enchantedDecoding.field_73744_e = I18n.func_135053_a("voice.decoding") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed()?"ON":"OFF");
         this.resetRequired = false;
      }

   }

   public void func_73863_a(int x, int y, float tick) {
      this.func_73873_v_();
      if(this.voiceChat.getSettings().getDeviceHandler().isEmpty()) {
         String scale = I18n.func_135053_a("voice.no_input");
         this.func_73731_b(this.field_73886_k, scale, this.field_73880_f / 2 - this.field_73886_k.func_78256_a(scale) / 2, this.field_73881_g - 80, -269484032);
      }

      this.func_73731_b(this.field_73886_k, I18n.func_135053_a("voice.settings_title"), (this.field_73880_f - this.field_73886_k.func_78256_a(I18n.func_135053_a("voice.settings_title"))) / 2, this.field_73881_g / 2 - 130, -1);
      if(this.field_73882_e.func_71356_B()) {
         this.func_73731_b(this.field_73886_k, "Voice Chat doesn\'t work in Singleplayer mode.", this.field_73880_f / 2 - this.field_73886_k.func_78256_a("Voice Chat doesn\'t work in Singleplayer mode.") / 2, this.field_73881_g - 70, -269466299);
      }

      if((int)(this.voiceChat.getSettings().getEncodingQuality() * 10.0F) <= 2) {
         this.func_73731_b(this.field_73886_k, "Encoding Quality below 2 is not recommended.", this.field_73880_f / 2, this.field_73881_g - 50, -255);
      }

      super.func_73863_a(x, y, tick);
   }

   protected void func_73864_a(int x, int y, int b) {
      if(b == 0 && this.dropDown.getMouseOverInteger() != -1 && this.dropDown.dropDownMenu && !this.voiceChat.getSettings().getDeviceHandler().isEmpty()) {
         Device device1 = (Device)this.voiceChat.getSettings().getDeviceHandler().getDevices().get(this.dropDown.getMouseOverInteger());
         if(device1 == null) {
            return;
         }

         this.voiceChat.getSettings().setInputDevice(device1);
         this.dropDown.setDisplayString(device1.getName());
      }

      super.func_73864_a(x, y, b);
   }

   public void func_73875_a(GuiButton button) {
      if(button.field_73741_f == 0 && button instanceof GuiDropDownMenu && !this.voiceChat.getSettings().getDeviceHandler().isEmpty()) {
         ((GuiDropDownMenu)button).dropDownMenu = !((GuiDropDownMenu)button).dropDownMenu;
      }

      if(button.field_73741_f == 2) {
         if(!this.tester.recording) {
            this.tester.start();
         } else {
            this.tester.stop();
         }

         button.field_73744_e = this.tester.recording?I18n.func_135053_a("voice.stop_micro"):I18n.func_135053_a("voice.test_micro");
      }

      if(button.field_73741_f == 3) {
         this.voiceChat.getSettings().getConfiguration().save();
         this.field_73882_e.func_71373_a(this.parent);
      }

      if(button.field_73741_f == 5) {
         if(!this.dropDown.dropDownMenu) {
            this.micModeButton.field_73742_g = true;
            this.micModeButton.field_73748_h = true;
            this.voiceChat.getSettings().setSpeakMode(this.voiceChat.getSettings().getSpeakMode() == 0?1:0);
            this.micModeButton.field_73744_e = I18n.func_135053_a("voice.mode") + " " + (this.voiceChat.getSettings().getSpeakMode() == 0?"Push To Talk":"Toggle To Talk");
         } else if(this.voiceChat.getSettings().getDeviceHandler().isEmpty()) {
            this.micModeButton.field_73742_g = false;
            this.micModeButton.field_73748_h = false;
         }
      }

      if(button.field_73741_f == 151) {
         this.resetRequired = true;
      }

      if(button.field_73741_f == 155) {
         int mode = this.voiceChat.getSettings().getEncodingMode();
         if(mode < 2) {
            ++mode;
         } else {
            mode = 0;
         }

         this.voiceChat.getSettings().setEncodingMode(mode);
         this.encodingMode.field_73744_e = I18n.func_135053_a("voice.encoding_mode") + " " + this.voiceChat.getSettings().getEncodingModeString();
      }

      if(button.field_73741_f == 156) {
         this.voiceChat.getSettings().setPerceptualEnchantment(!this.voiceChat.getSettings().isPerceptualEnchantmentAllowed());
         this.enchantedDecoding.field_73744_e = I18n.func_135053_a("voice.decoding") + " " + (this.voiceChat.getSettings().isPerceptualEnchantmentAllowed()?"ON":"OFF");
      }

      if(button.field_73741_f == 415) {
         this.enableVoiceButton.isVoiceActive = !this.enableVoiceButton.isVoiceActive;
         VoiceChat.getProxyInstance().getSettings().setVoiceEnable(this.enableVoiceButton.isVoiceActive);
         if(!this.enableVoiceButton.isVoiceActive) {
            this.voiceChat.setRecorderActive(false);
            KeySpeakEvent.recorder.stop();
         }
      }

   }

   protected void func_73869_a(char t, int key) {
      if(key == 1) {
         this.voiceChat.getSettings().getConfiguration().save();
         this.field_73882_e.func_71373_a(this.parent);
         this.field_73882_e.func_71381_h();
      }

   }

   public boolean inBounds(int x, int y, int posX, int posY, int width, int height) {
      return x >= posX && y >= posY && x < posX + width && y < posY + height;
   }

   public void func_73874_b() {
      this.voiceChat.getSettings().getConfiguration().save();
      if(this.tester.recording) {
         this.tester.stop();
      }

   }
}
