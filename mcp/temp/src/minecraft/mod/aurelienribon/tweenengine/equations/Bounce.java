package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Bounce$1;
import aurelienribon.tweenengine.equations.Bounce$2;
import aurelienribon.tweenengine.equations.Bounce$3;

public abstract class Bounce extends TweenEquation {

   public static final Bounce IN = new Bounce$1();
   public static final Bounce OUT = new Bounce$2();
   public static final Bounce INOUT = new Bounce$3();


}
