package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Expo;

final class Expo$3 extends Expo {

   public final float compute(float t) {
      return t == 0.0F?0.0F:(t == 1.0F?1.0F:((t *= 2.0F) < 1.0F?0.5F * (float)Math.pow(2.0D, (double)(10.0F * (t - 1.0F))):0.5F * (-((float)Math.pow(2.0D, (double)(-10.0F * --t))) + 2.0F)));
   }

   public String toString() {
      return "Expo.INOUT";
   }
}
