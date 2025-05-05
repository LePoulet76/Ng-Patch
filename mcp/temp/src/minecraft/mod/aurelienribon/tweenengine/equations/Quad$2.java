package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Quad;

final class Quad$2 extends Quad {

   public final float compute(float t) {
      return -t * (t - 2.0F);
   }

   public String toString() {
      return "Quad.OUT";
   }
}
