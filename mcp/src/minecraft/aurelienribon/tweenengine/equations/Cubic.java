package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Cubic$1;
import aurelienribon.tweenengine.equations.Cubic$2;
import aurelienribon.tweenengine.equations.Cubic$3;

public abstract class Cubic extends TweenEquation
{
    public static final Cubic IN = new Cubic$1();
    public static final Cubic OUT = new Cubic$2();
    public static final Cubic INOUT = new Cubic$3();
}
