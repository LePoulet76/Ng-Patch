package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Pool$Callback;
import aurelienribon.tweenengine.Timeline$1;

final class Timeline$2 extends Pool<Timeline>
{
    Timeline$2(int initCapacity, Pool$Callback callback)
    {
        super(initCapacity, callback);
    }

    protected Timeline create()
    {
        return new Timeline((Timeline$1)null);
    }

    protected Object create()
    {
        return this.create();
    }
}
