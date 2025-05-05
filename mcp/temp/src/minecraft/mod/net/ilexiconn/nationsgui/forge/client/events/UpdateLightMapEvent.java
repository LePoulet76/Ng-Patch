package net.ilexiconn.nationsgui.forge.client.events;

import net.minecraftforge.event.Event;

public class UpdateLightMapEvent extends Event {

   public float r;
   public float g;
   public float b;
   public final float i;


   public UpdateLightMapEvent(float r, float g, float b, float i) {
      this.r = r;
      this.g = g;
      this.b = b;
      this.i = i;
   }
}
