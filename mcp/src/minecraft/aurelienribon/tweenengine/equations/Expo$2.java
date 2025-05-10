package aurelienribon.tweenengine.equations;

final class Expo$2 extends Expo
{
    public final float compute(float t)
    {
        return t == 1.0F ? 1.0F : -((float)Math.pow(2.0D, (double)(-10.0F * t))) + 1.0F;
    }

    public String toString()
    {
        return "Expo.OUT";
    }
}
