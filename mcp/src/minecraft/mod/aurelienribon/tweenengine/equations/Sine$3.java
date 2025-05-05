package aurelienribon.tweenengine.equations;

final class Sine$3 extends Sine
{
    public final float compute(float t)
    {
        return -0.5F * ((float)Math.cos((double)((float)Math.PI * t)) - 1.0F);
    }

    public String toString()
    {
        return "Sine.INOUT";
    }
}
