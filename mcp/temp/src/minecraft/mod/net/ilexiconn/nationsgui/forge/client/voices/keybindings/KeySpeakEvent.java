package net.ilexiconn.nationsgui.forge.client.voices.keybindings;

import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.EnumBinding;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyEvent;
import net.ilexiconn.nationsgui.forge.client.voices.sound.Recorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

public class KeySpeakEvent extends KeyEvent {

   public static Recorder recorder;
   private VoiceChatClient voiceChat;


   public KeySpeakEvent(VoiceChatClient voiceChat, EnumBinding keyBind, int keyID, boolean repeating) {
      super(keyBind, keyID, repeating);
      this.voiceChat = voiceChat;
      recorder = new Recorder(voiceChat);
   }

   public void keyDown(KeyBinding kb, boolean tickEnd, boolean isRepeat) {
      GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
      if((screen == null || screen instanceof GuiContainer) && tickEnd) {
         boolean flag = this.voiceChat.getSettings().getSpeakMode() == 1?!this.voiceChat.isRecorderActive():true;
         if(!flag || this.voiceChat.getSettings().isVoiceEnable()) {
            recorder.set(flag);
            this.voiceChat.setRecorderActive(flag);
         }
      }

   }

   public void keyUp(KeyBinding kb, boolean tickEnd) {
      if(tickEnd && this.voiceChat.getSettings().getSpeakMode() == 0) {
         this.voiceChat.setRecorderActive(false);
         recorder.stop();
      }

   }
}
