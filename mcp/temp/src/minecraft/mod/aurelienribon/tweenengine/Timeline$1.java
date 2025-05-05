package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Pool$Callback;
import aurelienribon.tweenengine.Timeline;

final class Timeline$1 implements Pool$Callback<Timeline> {

   public void onPool(Timeline obj) {
      obj.reset();
   }

   public void onUnPool(Timeline obj) {
      obj.reset();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void onUnPool(Object var1) {
      this.onUnPool((Timeline)var1);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void onPool(Object var1) {
      this.onPool((Timeline)var1);
   }
}
