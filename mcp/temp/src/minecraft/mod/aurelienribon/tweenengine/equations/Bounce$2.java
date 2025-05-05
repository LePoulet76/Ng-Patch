package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Bounce;

final class Bounce$2 extends Bounce {

   public final float compute(float t) {
      return (double)t < 0.36363636363636365D?7.5625F * t * t:((double)t < 0.7272727272727273D?7.5625F * (t -= 0.54545456F) * t + 0.75F:((double)t < 0.9090909090909091D?7.5625F * (t -= 0.8181818F) * t + 0.9375F:7.5625F * (t -= 0.95454544F) * t + 0.984375F));
   }

   public String toString() {
      return "Bounce.OUT";
   }
}
