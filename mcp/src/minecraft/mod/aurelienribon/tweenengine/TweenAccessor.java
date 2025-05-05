package aurelienribon.tweenengine;

public interface TweenAccessor<T extends Object>
{
    int getValues(T var1, int var2, float[] var3);

    void setValues(T var1, int var2, float[] var3);
}
