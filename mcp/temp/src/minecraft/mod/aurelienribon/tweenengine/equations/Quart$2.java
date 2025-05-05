package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Quart;

final class Quart$2 extends Quart {

   public final float compute(float t) {
      return -(--t * t * t * t - 1.0F);
   }

   public String toString() {
      return "Quart.OUT";
   }
}
