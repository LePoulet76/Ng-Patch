package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Circ;

final class Circ$2 extends Circ {

   public final float compute(float t) {
      return (float)Math.sqrt((double)(1.0F - --t * t));
   }

   public String toString() {
      return "Circ.OUT";
   }
}
