package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Elastic$1;
import aurelienribon.tweenengine.equations.Elastic$2;
import aurelienribon.tweenengine.equations.Elastic$3;

public abstract class Elastic extends TweenEquation {

   private static final float PI = 3.1415927F;
   public static final Elastic IN = new Elastic$1();
   public static final Elastic OUT = new Elastic$2();
   public static final Elastic INOUT = new Elastic$3();
   protected float param_a;
   protected float param_p;
   protected boolean setA = false;
   protected boolean setP = false;


   public Elastic a(float a) {
      this.param_a = a;
      this.setA = true;
      return this;
   }

   public Elastic p(float p) {
      this.param_p = p;
      this.setP = true;
      return this;
   }

}
