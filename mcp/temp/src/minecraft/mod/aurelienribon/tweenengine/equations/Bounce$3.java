package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Bounce;

final class Bounce$3 extends Bounce {

   public final float compute(float t) {
      return t < 0.5F?IN.compute(t * 2.0F) * 0.5F:OUT.compute(t * 2.0F - 1.0F) * 0.5F + 0.5F;
   }

   public String toString() {
      return "Bounce.INOUT";
   }
}
