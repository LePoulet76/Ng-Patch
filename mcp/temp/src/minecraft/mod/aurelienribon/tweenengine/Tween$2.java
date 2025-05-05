package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Pool;
import aurelienribon.tweenengine.Pool$Callback;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.Tween$1;

final class Tween$2 extends Pool<Tween> {

   Tween$2(int initCapacity, Pool$Callback callback) {
      super(initCapacity, callback);
   }

   protected Tween create() {
      return new Tween((Tween$1)null);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected Object create() {
      return this.create();
   }
}
