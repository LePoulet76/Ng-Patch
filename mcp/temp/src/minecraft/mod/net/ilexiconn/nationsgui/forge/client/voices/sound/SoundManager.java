package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.client.voices.sound.Datalet;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream$PlayableStreamComparator;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundPreProcessor;
import net.ilexiconn.nationsgui.forge.client.voices.sound.thread.ThreadQueue;
import net.ilexiconn.nationsgui.forge.client.voices.sound.thread.ThreadUpdateStream;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;

public class SoundManager {

   public static final int MAX_VOCAL_PLAYERS = 3;
   public static AudioFormat universalAudioFormat = new AudioFormat(Encoding.PCM_SIGNED, 16000.0F, 16, 1, 2, 16000.0F, false);
   public ConcurrentLinkedQueue<Datalet> queue = new ConcurrentLinkedQueue();
   public ConcurrentHashMap<Integer, PlayableStream> streaming = new ConcurrentHashMap();
   private final SoundPreProcessor soundPreProcessor;
   public ConcurrentHashMap<Integer, EntityVector> entityData = new ConcurrentHashMap();
   private Minecraft mc;
   private Thread threadUpdate;
   private ThreadQueue queueThread;
   private VoiceChatClient voiceChatClient;


   public SoundManager(Minecraft mc, VoiceChatClient voiceChatClient) {
      this.mc = mc;
      this.voiceChatClient = voiceChatClient;
      this.soundPreProcessor = new SoundPreProcessor(voiceChatClient, mc);
   }

   public void init() {
      (new Thread(this.queueThread = new ThreadQueue(this), "Client Stream Queue")).start();
      this.threadUpdate = new Thread(new ThreadUpdateStream(this), "Client Stream Updater");
      this.threadUpdate.start();
   }

   public void addQueue(byte[] decoded_data, int global, int id) {
      this.queue.offer(new Datalet(global, id, decoded_data));
      this.queueThread.notifyQueue();
   }

   public boolean newDatalet(Datalet let) {
      if(this.streaming.containsKey(Integer.valueOf(let.id))) {
         PlayableStream stream = (PlayableStream)this.streaming.get(Integer.valueOf(let.id));
         return stream.voiceMode != let.global;
      } else {
         return true;
      }
   }

   public void createStream(Datalet data) {
      if(this.streaming.containsKey(Integer.valueOf(data.id))) {
         this.replaceStream(data);
      } else {
         String identifier = this.generateSource(data.id);
         if(data.global == 1) {
            Entity entity = this.mc.field_71441_e.func_73045_a(data.id);
            EntityVector vector = (EntityVector)this.entityData.get(Integer.valueOf(data.id));
            if(entity != null) {
               this.mc.field_71416_A.field_77381_a.rawDataStream(universalAudioFormat, false, identifier, (float)entity.field_70165_t, (float)entity.field_70163_u, (float)entity.field_70161_v, 2, (float)this.voiceChatClient.getSettings().getSoundDistance());
            } else if(vector != null) {
               this.mc.field_71416_A.field_77381_a.rawDataStream(universalAudioFormat, false, identifier, (float)vector.x, (float)vector.y, (float)vector.z, 2, (float)this.voiceChatClient.getSettings().getSoundDistance());
            } else {
               this.mc.field_71416_A.field_77381_a.rawDataStream(universalAudioFormat, false, identifier, 0.0F, 0.0F, 0.0F, 0, 0.0F);
            }
         } else {
            this.mc.field_71416_A.field_77381_a.rawDataStream(universalAudioFormat, false, identifier, 0.0F, 0.0F, 0.0F, 0, 0.0F);
         }

         this.mc.field_71416_A.field_77381_a.setPitch(identifier, 1.0F);
         this.mc.field_71416_A.field_77381_a.setVolume(identifier, this.voiceChatClient.getSettings().getWorldVolume());
         this.addStreamSafe(new PlayableStream(this, data.id, data.global));
         this.giveStream(data);
      }

   }

   public void replaceStream(Datalet data) {
      PlayableStream stream = (PlayableStream)this.streaming.get(Integer.valueOf(data.id));
      stream.voiceMode = data.global;
      this.mc.field_71416_A.field_77381_a.setDistOrRoll(this.generateSource(stream.id), (float)this.voiceChatClient.getSettings().getSoundDistance());
   }

   public void giveStream(Datalet data) {
      PlayableStream stream = this.getStreamByID(data.id);
      if(!KeyManager.getInstance().isKeyMuted() && stream != null && (!stream.isEntityPlayer() || stream.isEntityPlayer() && !VoiceChat.getProxyInstance().getSettings().isPlayerMuted(((AbstractClientPlayer)stream.entity).field_71092_bJ))) {
         String identifier = this.generateSource(data.id);
         stream.update((int)(System.currentTimeMillis() - stream.lastUpdated));
         stream.buffer.push(data.data);
         stream.buffer.updateJitter(stream.getJitterRate());
         if(stream.buffer.isReady() || stream.end) {
            this.mc.field_71416_A.field_77381_a.flush(identifier);
            this.mc.field_71416_A.field_77381_a.feedRawAudioData(identifier, stream.buffer.get());
            stream.buffer.clearBuffer(stream.getJitterRate());
         }

         stream.lastUpdated = System.currentTimeMillis();
      }

   }

   public void giveEnd(int id) {
      PlayableStream stream = (PlayableStream)this.streaming.get(Integer.valueOf(id));
      if(stream != null) {
         stream.end = true;
      }

   }

   private void addStreamSafe(PlayableStream stream) {
      if(this.streaming.size() < 3) {
         if(!this.streaming.containsKey(Integer.valueOf(stream.id))) {
            this.streaming.put(Integer.valueOf(stream.id), stream);
            Thread var7 = this.threadUpdate;
            Thread var8 = this.threadUpdate;
            synchronized(this.threadUpdate) {
               this.threadUpdate.notify();
            }
         }

         Entity var71 = this.mc.field_71441_e.func_73045_a(stream.id);
         if(var71 != null) {
            stream.setEntity(var71);
         }

         if(!this.containsGuiStream(stream.id)) {
            ArrayList var81 = new ArrayList(VoiceChatClient.activeStreams);
            var81.add(stream);
            Collections.sort(var81, new PlayableStream$PlayableStreamComparator());
            VoiceChatClient.activeStreams.removeAll(VoiceChatClient.activeStreams);
            VoiceChatClient.activeStreams.addAll(var81);
         }
      }

   }

   private String generateSource(int let) {
      return "" + let;
   }

   public void killStream(int id) {
      PlayableStream stream = this.getGuiStreamByID(id);
      if(stream != null) {
         ArrayList streams = new ArrayList(VoiceChatClient.activeStreams);
         streams.remove(stream);
         Collections.sort(streams, new PlayableStream$PlayableStreamComparator());
         VoiceChatClient.activeStreams.removeAll(VoiceChatClient.activeStreams);
         VoiceChatClient.activeStreams.addAll(streams);
         VoiceChatClient.activeStreams.remove(stream);
         Collections.sort(VoiceChatClient.activeStreams, new PlayableStream$PlayableStreamComparator());
         this.streaming.remove(Integer.valueOf(id));
      }

   }

   public PlayableStream getStreamByID(int id) {
      return (PlayableStream)this.streaming.get(Integer.valueOf(id));
   }

   public PlayableStream getGuiStreamByID(int id) {
      for(int i = 0; i < VoiceChatClient.activeStreams.size(); ++i) {
         PlayableStream stream = (PlayableStream)VoiceChatClient.activeStreams.get(i);
         if(stream.id == id) {
            return stream;
         }
      }

      return null;
   }

   public boolean containsGuiStream(int id) {
      PlayableStream currentStream = (PlayableStream)this.streaming.get(Integer.valueOf(id));

      for(int i = 0; i < VoiceChatClient.activeStreams.size(); ++i) {
         PlayableStream stream = (PlayableStream)VoiceChatClient.activeStreams.get(i);
         String currentName = currentStream.getEntityName();
         String otherName = stream.getEntityName();
         if(stream.getEntityName() != null && currentStream.getEntityName() != null && currentName.equals(otherName)) {
            return true;
         }

         if(stream.id == id) {
            return true;
         }
      }

      return false;
   }

   public SoundPreProcessor getSoundPreProcessor() {
      return this.soundPreProcessor;
   }

   public static AudioFormat getUniversalAudioFormat() {
      return universalAudioFormat;
   }

   public void setPosition(String string, float posX, float posY, float posZ) {
      this.mc.field_71416_A.field_77381_a.setPosition(string, posX, posY, posZ);
   }

   public void alertEnd(int id) {
      this.queue.offer(new Datalet(id, 0, (byte[])null));
      this.queueThread.notifyQueue();
   }

   public void reset() {
      this.streaming.clear();
      VoiceChatClient.activeStreams.clear();
      this.entityData.clear();
   }

}
