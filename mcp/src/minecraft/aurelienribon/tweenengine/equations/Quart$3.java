package aurelienribon.tweenengine.equations;

final class Quart$3 extends Quart
{
    public final float compute(float t)
    {
        return (t *= 2.0F) < 1.0F ? 0.5F * t * t * t * t : -0.5F * ((t -= 2.0F) * t * t * t - 2.0F);
    }

    public String toString()
    {
        return "Quart.INOUT";
    }
}
