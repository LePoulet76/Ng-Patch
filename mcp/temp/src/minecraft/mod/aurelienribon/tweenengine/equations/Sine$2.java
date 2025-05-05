package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Sine;

final class Sine$2 extends Sine {

   public final float compute(float t) {
      return (float)Math.sin((double)(t * 1.5707964F));
   }

   public String toString() {
      return "Sine.OUT";
   }
}
