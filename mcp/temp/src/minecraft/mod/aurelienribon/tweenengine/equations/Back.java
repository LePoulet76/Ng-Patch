package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Back$1;
import aurelienribon.tweenengine.equations.Back$2;
import aurelienribon.tweenengine.equations.Back$3;

public abstract class Back extends TweenEquation {

   public static final Back IN = new Back$1();
   public static final Back OUT = new Back$2();
   public static final Back INOUT = new Back$3();
   protected float param_s = 1.70158F;


   public Back s(float s) {
      this.param_s = s;
      return this;
   }

}
