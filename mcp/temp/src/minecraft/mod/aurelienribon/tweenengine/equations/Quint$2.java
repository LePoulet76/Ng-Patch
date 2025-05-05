package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Quint;

final class Quint$2 extends Quint {

   public final float compute(float t) {
      return --t * t * t * t * t + 1.0F;
   }

   public String toString() {
      return "Quint.OUT";
   }
}
