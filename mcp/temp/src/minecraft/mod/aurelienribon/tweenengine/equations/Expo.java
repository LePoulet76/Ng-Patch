package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Expo$1;
import aurelienribon.tweenengine.equations.Expo$2;
import aurelienribon.tweenengine.equations.Expo$3;

public abstract class Expo extends TweenEquation {

   public static final Expo IN = new Expo$1();
   public static final Expo OUT = new Expo$2();
   public static final Expo INOUT = new Expo$3();


}
