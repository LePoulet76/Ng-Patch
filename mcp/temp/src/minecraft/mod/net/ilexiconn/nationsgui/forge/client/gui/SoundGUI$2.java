package net.ilexiconn.nationsgui.forge.client.gui;

import fr.nationsglory.ngbrowser.NGBrowser;
import fr.nationsglory.ngbrowser.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderGUI$ISliderCallback;
import net.ilexiconn.nationsgui.forge.client.gui.SoundGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;

class SoundGUI$2 implements SliderGUI$ISliderCallback {

   // $FF: synthetic field
   final SoundGUI this$0;


   SoundGUI$2(SoundGUI this$0) {
      this.this$0 = this$0;
   }

   public void call(float value) {
      NBTConfig.CONFIG.getCompound().func_74776_a("BrowserVolume", value);
      ClientProxy clientProxy = (ClientProxy)NGBrowser.proxy;
      clientProxy.setBrowserVolume(value / 100.0F);
   }

   public float getMaxValue() {
      return 100.0F;
   }

   public float getCurrentValue() {
      return NBTConfig.CONFIG.getCompound().func_74760_g("BrowserVolume");
   }
}
