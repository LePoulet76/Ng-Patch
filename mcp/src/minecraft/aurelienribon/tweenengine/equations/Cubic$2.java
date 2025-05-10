package aurelienribon.tweenengine.equations;

final class Cubic$2 extends Cubic
{
    public final float compute(float t)
    {
        return --t * t * t + 1.0F;
    }

    public String toString()
    {
        return "Cubic.OUT";
    }
}
