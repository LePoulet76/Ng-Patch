package aurelienribon.tweenengine.equations;

final class Sine$1 extends Sine
{
    public final float compute(float t)
    {
        return (float)(-Math.cos((double)(t * ((float)Math.PI / 2F)))) + 1.0F;
    }

    public String toString()
    {
        return "Sine.IN";
    }
}
