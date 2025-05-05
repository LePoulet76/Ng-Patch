package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Quart$1;
import aurelienribon.tweenengine.equations.Quart$2;
import aurelienribon.tweenengine.equations.Quart$3;

public abstract class Quart extends TweenEquation
{
    public static final Quart IN = new Quart$1();
    public static final Quart OUT = new Quart$2();
    public static final Quart INOUT = new Quart$3();
}
