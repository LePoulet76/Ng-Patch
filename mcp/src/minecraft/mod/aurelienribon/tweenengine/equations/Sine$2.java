package aurelienribon.tweenengine.equations;

final class Sine$2 extends Sine
{
    public final float compute(float t)
    {
        return (float)Math.sin((double)(t * ((float)Math.PI / 2F)));
    }

    public String toString()
    {
        return "Sine.OUT";
    }
}
