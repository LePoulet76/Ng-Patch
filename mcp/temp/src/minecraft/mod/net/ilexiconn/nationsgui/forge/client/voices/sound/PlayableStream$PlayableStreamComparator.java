package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;

public class PlayableStream$PlayableStreamComparator implements Comparator<PlayableStream> {

   public int compare(PlayableStream a, PlayableStream b) {
      int f = a.id < b.id?-1:(a.id > b.id?1:0);
      return f;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public int compare(Object var1, Object var2) {
      return this.compare((PlayableStream)var1, (PlayableStream)var2);
   }
}
