package aurelienribon.tweenengine.equations;

final class Elastic$3 extends Elastic
{
    public final float compute(float t)
    {
        float a = this.param_a;
        float p = this.param_p;

        if (t == 0.0F)
        {
            return 0.0F;
        }
        else if ((t *= 2.0F) == 2.0F)
        {
            return 1.0F;
        }
        else
        {
            if (!this.setP)
            {
                p = 0.45000002F;
            }

            float s;

            if (this.setA && a >= 1.0F)
            {
                s = p / ((float)Math.PI * 2F) * (float)Math.asin((double)(1.0F / a));
            }
            else
            {
                a = 1.0F;
                s = p / 4.0F;
            }

            return t < 1.0F ? -0.5F * a * (float)Math.pow(2.0D, (double)(10.0F * --t)) * (float)Math.sin((double)((t - s) * ((float)Math.PI * 2F) / p)) : a * (float)Math.pow(2.0D, (double)(-10.0F * --t)) * (float)Math.sin((double)((t - s) * ((float)Math.PI * 2F) / p)) * 0.5F + 1.0F;
        }
    }

    public String toString()
    {
        return "Elastic.INOUT";
    }
}
