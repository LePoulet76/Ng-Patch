package javazoom.jl.player.advanced;

import javazoom.jl.player.advanced.AdvancedPlayer;

final class jlap$1 extends Thread {

   // $FF: synthetic field
   final AdvancedPlayer val$player;
   // $FF: synthetic field
   final int val$start;
   // $FF: synthetic field
   final int val$end;


   jlap$1(AdvancedPlayer var1, int var2, int var3) {
      this.val$player = var1;
      this.val$start = var2;
      this.val$end = var3;
   }

   public void run() {
      try {
         this.val$player.play(this.val$start, this.val$end);
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }
}
