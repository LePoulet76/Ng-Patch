package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Cubic;

final class Cubic$2 extends Cubic {

   public final float compute(float t) {
      return --t * t * t + 1.0F;
   }

   public String toString() {
      return "Cubic.OUT";
   }
}
