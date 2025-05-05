package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.equations.Back;

final class Back$3 extends Back {

   public final float compute(float t) {
      float s = this.param_s;
      return (t *= 2.0F) < 1.0F?0.5F * t * t * (((s *= 1.525F) + 1.0F) * t - s):0.5F * ((t -= 2.0F) * t * (((s *= 1.525F) + 1.0F) * t + s) + 2.0F);
   }

   public String toString() {
      return "Back.INOUT";
   }
}
