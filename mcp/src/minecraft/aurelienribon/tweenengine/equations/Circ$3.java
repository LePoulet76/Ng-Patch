package aurelienribon.tweenengine.equations;

final class Circ$3 extends Circ
{
    public final float compute(float t)
    {
        return (t *= 2.0F) < 1.0F ? -0.5F * ((float)Math.sqrt((double)(1.0F - t * t)) - 1.0F) : 0.5F * ((float)Math.sqrt((double)(1.0F - (t -= 2.0F) * t)) + 1.0F);
    }

    public String toString()
    {
        return "Circ.INOUT";
    }
}
