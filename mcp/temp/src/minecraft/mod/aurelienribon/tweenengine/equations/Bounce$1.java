package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Bounce;

final class Bounce$1 extends Bounce {

   public final float compute(float t) {
      return 1.0F - OUT.compute(1.0F - t);
   }

   public String toString() {
      return "Bounce.IN";
   }
}
