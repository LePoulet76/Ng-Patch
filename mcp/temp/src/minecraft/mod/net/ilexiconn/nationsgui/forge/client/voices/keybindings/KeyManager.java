package net.ilexiconn.nationsgui.forge.client.voices.keybindings;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.EnumBinding;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyBindingHandler;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyEvent;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyMuteSoundEvent;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeySpeakEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyManager {

   private static KeyManager instance;
   private VoiceChatClient voiceChat;
   @SideOnly(Side.CLIENT)
   private List keyEvents = new ArrayList();
   private KeyMuteSoundEvent muteKey;


   public KeyManager(VoiceChatClient voiceChat) {
      this.voiceChat = voiceChat;
      instance = this;
   }

   @SideOnly(Side.CLIENT)
   public List getKeyEvents() {
      return this.keyEvents;
   }

   public void init() {
      this.keyEvents.add(new KeySpeakEvent(this.voiceChat, EnumBinding.SPEAK, 47, false));
      this.keyEvents.add(this.muteKey = new KeyMuteSoundEvent(EnumBinding.SOUND, 34, false));
      KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler(this.compileKeyBindings(), this.compileRepeating(), this));
   }

   private boolean[] compileRepeating() {
      boolean[] keyRepeating = new boolean[this.keyEvents.size()];

      for(int i = 0; i < keyRepeating.length; ++i) {
         KeyEvent keyEvent = (KeyEvent)this.keyEvents.get(i);
         keyRepeating[i] = keyEvent.repeating;
      }

      return keyRepeating;
   }

   private KeyBinding[] compileKeyBindings() {
      KeyBinding[] keyBinding = new KeyBinding[this.keyEvents.size()];

      for(int i = 0; i < keyBinding.length; ++i) {
         KeyEvent keyEvent = (KeyEvent)this.keyEvents.get(i);
         keyBinding[i] = new KeyBinding(keyEvent.keyBind.name, keyEvent.keyID);
      }

      return keyBinding;
   }

   public String getKeyName(EnumBinding binding) {
      for(int i = 0; i < this.keyEvents.size(); ++i) {
         KeyEvent event = (KeyEvent)this.keyEvents.get(i);
         if(event.keyBind == binding) {
            return Keyboard.getKeyName(event.keyID);
         }
      }

      return null;
   }

   public boolean isKeyMuted() {
      return this.muteKey.isMuted();
   }

   public static KeyManager getInstance() {
      return instance;
   }
}
