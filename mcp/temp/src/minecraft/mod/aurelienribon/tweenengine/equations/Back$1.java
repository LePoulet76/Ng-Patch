package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Back;

final class Back$1 extends Back {

   public final float compute(float t) {
      float s = this.param_s;
      return t * t * ((s + 1.0F) * t - s);
   }

   public String toString() {
      return "Back.IN";
   }
}
