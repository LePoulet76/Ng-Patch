package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Circ$1;
import aurelienribon.tweenengine.equations.Circ$2;
import aurelienribon.tweenengine.equations.Circ$3;

public abstract class Circ extends TweenEquation
{
    public static final Circ IN = new Circ$1();
    public static final Circ OUT = new Circ$2();
    public static final Circ INOUT = new Circ$3();
}
