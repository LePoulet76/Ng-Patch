package aurelienribon.tweenengine.equations;

final class Expo$1 extends Expo
{
    public final float compute(float t)
    {
        return t == 0.0F ? 0.0F : (float)Math.pow(2.0D, (double)(10.0F * (t - 1.0F)));
    }

    public String toString()
    {
        return "Expo.IN";
    }
}
