package aurelienribon.tweenengine.equations;

final class Quad$3 extends Quad
{
    public final float compute(float t)
    {
        return (t *= 2.0F) < 1.0F ? 0.5F * t * t : -0.5F * (--t * (t - 2.0F) - 1.0F);
    }

    public String toString()
    {
        return "Quad.INOUT";
    }
}
