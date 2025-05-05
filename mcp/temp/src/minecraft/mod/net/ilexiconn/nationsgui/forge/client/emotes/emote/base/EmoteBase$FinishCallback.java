package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase$1;

class EmoteBase$FinishCallback implements TweenCallback {

   // $FF: synthetic field
   final EmoteBase this$0;


   private EmoteBase$FinishCallback(EmoteBase var1) {
      this.this$0 = var1;
   }

   public void onEvent(int type, BaseTween<?> source) {
      if(type == 8) {
         EmoteBase.access$102(this.this$0, true);
      }

   }

   // $FF: synthetic method
   EmoteBase$FinishCallback(EmoteBase x0, EmoteBase$1 x1) {
      this(x0);
   }
}
