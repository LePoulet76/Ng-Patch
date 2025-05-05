package net.ilexiconn.nationsgui.forge.client.voices.keybindings;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.EnumSet;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyEvent;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class KeyBindingHandler extends KeyHandler {

   KeyManager keyManager;


   public KeyBindingHandler(KeyBinding[] keyBindings, boolean[] repeating, KeyManager manager) {
      super(keyBindings, repeating);
      this.keyManager = manager;
   }

   public String getLabel() {
      return null;
   }

   public void keyDown(EnumSet types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
      for(int i = 0; i < this.keyManager.getKeyEvents().size(); ++i) {
         KeyEvent keyEvent = (KeyEvent)this.keyManager.getKeyEvents().get(i);
         if(Minecraft.func_71410_x().field_71462_r == null && keyEvent.keyBind.name.equals(kb.field_74515_c) && tickEnd) {
            keyEvent.keyDown(kb, tickEnd, isRepeat);
         }
      }

   }

   public void keyUp(EnumSet types, KeyBinding kb, boolean tickEnd) {
      for(int i = 0; i < this.keyManager.getKeyEvents().size(); ++i) {
         KeyEvent keyEvent = (KeyEvent)this.keyManager.getKeyEvents().get(i);
         if(Minecraft.func_71410_x().field_71462_r == null && keyEvent.keyBind.name.equals(kb.field_74515_c) && tickEnd) {
            keyEvent.keyUp(kb, tickEnd);
         }
      }

   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.CLIENT);
   }
}
