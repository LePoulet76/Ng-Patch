package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Sine;

final class Sine$1 extends Sine {

   public final float compute(float t) {
      return (float)(-Math.cos((double)(t * 1.5707964F))) + 1.0F;
   }

   public String toString() {
      return "Sine.IN";
   }
}
