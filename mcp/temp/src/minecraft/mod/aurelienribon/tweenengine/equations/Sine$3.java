package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Sine;

final class Sine$3 extends Sine {

   public final float compute(float t) {
      return -0.5F * ((float)Math.cos((double)(3.1415927F * t)) - 1.0F);
   }

   public String toString() {
      return "Sine.INOUT";
   }
}
