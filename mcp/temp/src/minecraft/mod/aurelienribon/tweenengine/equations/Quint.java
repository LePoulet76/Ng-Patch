package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Quint$1;
import aurelienribon.tweenengine.equations.Quint$2;
import aurelienribon.tweenengine.equations.Quint$3;

public abstract class Quint extends TweenEquation {

   public static final Quint IN = new Quint$1();
   public static final Quint OUT = new Quint$2();
   public static final Quint INOUT = new Quint$3();


}
