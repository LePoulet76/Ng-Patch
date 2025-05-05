package javazoom.jl.player.advanced;

import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.player.advanced.jlap;

public class jlap$InfoListener extends PlaybackListener {

   // $FF: synthetic field
   final jlap this$0;


   public jlap$InfoListener(jlap this$0) {
      this.this$0 = this$0;
   }

   public void playbackStarted(PlaybackEvent evt) {
      System.out.println("Play started from frame " + evt.getFrame());
   }

   public void playbackFinished(PlaybackEvent evt) {
      System.out.println("Play completed at frame " + evt.getFrame());
      System.exit(0);
   }
}
