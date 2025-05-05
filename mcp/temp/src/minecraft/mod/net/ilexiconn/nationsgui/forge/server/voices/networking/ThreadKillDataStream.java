package net.ilexiconn.nationsgui.forge.server.voices.networking;

import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataStream;
import net.minecraft.entity.player.EntityPlayerMP;

public class ThreadKillDataStream implements Runnable {

   DataManager dataManager;


   public ThreadKillDataStream(DataManager dataManager) {
      this.dataManager = dataManager;
   }

   public void run() {
      while(true) {
         if(!this.dataManager.streaming.isEmpty()) {
            Iterator var6 = this.dataManager.streaming.entrySet().iterator();

            while(var6.hasNext()) {
               Entry pairs = (Entry)var6.next();
               DataStream stream = (DataStream)pairs.getValue();
               if(System.currentTimeMillis() - stream.lastUpdated > 250L && System.currentTimeMillis() - stream.lastUpdated > (long)(((EntityPlayerMP)stream.player).field_71138_i * 2)) {
                  this.dataManager.killStream(stream.id);
               }
            }
         }

         try {
            synchronized(this) {
               this.wait(500L);
            }
         } catch (InterruptedException var61) {
            var61.printStackTrace();
         }
      }
   }
}
