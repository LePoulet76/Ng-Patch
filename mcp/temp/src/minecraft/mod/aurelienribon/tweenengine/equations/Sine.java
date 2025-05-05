package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Sine$1;
import aurelienribon.tweenengine.equations.Sine$2;
import aurelienribon.tweenengine.equations.Sine$3;

public abstract class Sine extends TweenEquation {

   private static final float PI = 3.1415927F;
   public static final Sine IN = new Sine$1();
   public static final Sine OUT = new Sine$2();
   public static final Sine INOUT = new Sine$3();


}
