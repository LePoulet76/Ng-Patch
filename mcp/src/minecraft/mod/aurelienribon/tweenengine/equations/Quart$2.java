package aurelienribon.tweenengine.equations;

final class Quart$2 extends Quart
{
    public final float compute(float t)
    {
        return -(--t * t * t * t - 1.0F);
    }

    public String toString()
    {
        return "Quart.OUT";
    }
}
