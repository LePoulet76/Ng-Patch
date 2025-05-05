package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Pool$Callback;
import aurelienribon.tweenengine.Tween;

final class Tween$1 implements Pool$Callback<Tween> {

   public void onPool(Tween obj) {
      obj.reset();
   }

   public void onUnPool(Tween obj) {
      obj.reset();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void onUnPool(Object var1) {
      this.onUnPool((Tween)var1);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void onPool(Object var1) {
      this.onPool((Tween)var1);
   }
}
