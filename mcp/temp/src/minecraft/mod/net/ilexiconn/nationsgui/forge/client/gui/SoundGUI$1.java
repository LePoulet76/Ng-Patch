package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.SliderGUI$ISliderCallback;
import net.ilexiconn.nationsgui.forge.client.gui.SoundGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;

class SoundGUI$1 implements SliderGUI$ISliderCallback {

   // $FF: synthetic field
   final SoundGUI this$0;


   SoundGUI$1(SoundGUI this$0) {
      this.this$0 = this$0;
   }

   public void call(float value) {
      NBTConfig.CONFIG.getCompound().func_74776_a("RadioVolume", value);
   }

   public float getMaxValue() {
      return 100.0F;
   }

   public float getCurrentValue() {
      return NBTConfig.CONFIG.getCompound().func_74760_g("RadioVolume");
   }
}
