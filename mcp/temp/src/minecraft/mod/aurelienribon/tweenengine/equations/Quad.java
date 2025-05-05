package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Quad$1;
import aurelienribon.tweenengine.equations.Quad$2;
import aurelienribon.tweenengine.equations.Quad$3;

public abstract class Quad extends TweenEquation {

   public static final Quad IN = new Quad$1();
   public static final Quad OUT = new Quad$2();
   public static final Quad INOUT = new Quad$3();


}
