package aurelienribon.tweenengine.equations;

final class Circ$1 extends Circ
{
    public final float compute(float t)
    {
        return (float)(-Math.sqrt((double)(1.0F - t * t))) - 1.0F;
    }

    public String toString()
    {
        return "Circ.IN";
    }
}
