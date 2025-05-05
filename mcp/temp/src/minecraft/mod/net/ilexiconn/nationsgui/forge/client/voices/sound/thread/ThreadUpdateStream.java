package net.ilexiconn.nationsgui.forge.client.voices.sound.thread;

import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.minecraft.client.Minecraft;

public class ThreadUpdateStream implements Runnable {

   private SoundManager manager;
   Minecraft mc;


   public ThreadUpdateStream(SoundManager manager) {
      this.manager = manager;
      this.mc = Minecraft.func_71410_x();
   }

   public void run() {
      while(true) {
         if(!this.manager.streaming.isEmpty()) {
            Iterator var10 = this.manager.streaming.entrySet().iterator();

            for(int i = 0; var10.hasNext(); ++i) {
               PlayableStream var8 = (PlayableStream)((Entry)var10.next()).getValue();
               if(i < 3) {
                  if(var8.end) {
                     if(!this.mc.field_71416_A.field_77381_a.playing("" + var8.id)) {
                        this.manager.killStream(var8.id);
                     }
                  } else if(System.currentTimeMillis() - var8.lastUpdated > 340L && !this.mc.field_71416_A.field_77381_a.playing("" + var8.id)) {
                     this.manager.killStream(var8.id);
                  }

                  switch(var8.voiceMode) {
                  case 0:
                     this.mc.field_71416_A.field_77381_a.setPosition("" + var8.id, (float)this.mc.field_71439_g.field_70165_t, (float)this.mc.field_71439_g.field_70163_u, (float)this.mc.field_71439_g.field_70161_v);
                     this.mc.field_71416_A.field_77381_a.setVelocity("" + var8.id, (float)this.mc.field_71439_g.field_70165_t, (float)this.mc.field_71439_g.field_70181_x, (float)this.mc.field_71439_g.field_70179_y);
                     break;
                  case 1:
                     EntityVector vector = var8.getCustomEntityVector();
                     this.mc.field_71416_A.field_77381_a.setPosition("" + var8.id, (float)vector.x, (float)vector.y, (float)vector.z);
                     this.mc.field_71416_A.field_77381_a.setVelocity("" + var8.id, (float)vector.motX, (float)vector.motY, (float)vector.motZ);
                  }
               } else {
                  this.manager.killStream(var8.id);
               }
            }

            try {
               synchronized(this) {
                  this.wait(30L);
               }
            } catch (InterruptedException var81) {
               var81.printStackTrace();
            }
         } else {
            try {
               synchronized(this) {
                  this.wait(500L);
               }
            } catch (InterruptedException var101) {
               var101.printStackTrace();
            }
         }
      }
   }
}
