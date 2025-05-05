package net.ilexiconn.nationsgui.forge.client.voices.device;

import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;

public class Device {

   private TargetDataLine line;
   private Info info;


   public Device(TargetDataLine line, Info info) {
      this.line = line;
      this.info = info;
   }

   public void setDevice(Device device) {
      this.line = device.line;
      this.info = device.info;
   }

   public String getName() {
      return this.info.getName();
   }

   public String getIdentifer() {
      return this.info.getName();
   }

   public String getVendor() {
      return this.info.getVendor();
   }

   public String getVersion() {
      return this.info.getVersion();
   }

   public String getDescription() {
      return this.info.getDescription();
   }

   public TargetDataLine getLine() {
      return this.line;
   }

   public Info getInfo() {
      return this.info;
   }
}
