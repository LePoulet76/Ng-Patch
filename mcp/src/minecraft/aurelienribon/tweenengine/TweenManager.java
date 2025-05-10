package aurelienribon.tweenengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TweenManager
{
    private final ArrayList < BaseTween<? >> objects = new ArrayList(20);
    private boolean isPaused = false;

    public static void setAutoRemove(BaseTween<?> object, boolean value)
    {
        object.isAutoRemoveEnabled = value;
    }

    public static void setAutoStart(BaseTween<?> object, boolean value)
    {
        object.isAutoStartEnabled = value;
    }

    public TweenManager add(BaseTween<?> object)
    {
        if (!this.objects.contains(object))
        {
            this.objects.add(object);
        }

        if (object.isAutoStartEnabled)
        {
            object.start();
        }

        return this;
    }

    public boolean containsTarget(Object target)
    {
        int i = 0;

        for (int n = this.objects.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.objects.get(i);

            if (obj.containsTarget(target))
            {
                return true;
            }
        }

        return false;
    }

    public boolean containsTarget(Object target, int tweenType)
    {
        int i = 0;

        for (int n = this.objects.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.objects.get(i);

            if (obj.containsTarget(target, tweenType))
            {
                return true;
            }
        }

        return false;
    }

    public void killAll()
    {
        int i = 0;

        for (int n = this.objects.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.objects.get(i);
            obj.kill();
        }
    }

    public void killTarget(Object target)
    {
        int i = 0;

        for (int n = this.objects.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.objects.get(i);
            obj.killTarget(target);
        }
    }

    public void killTarget(Object target, int tweenType)
    {
        int i = 0;

        for (int n = this.objects.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.objects.get(i);
            obj.killTarget(target, tweenType);
        }
    }

    public void ensureCapacity(int minCapacity)
    {
        this.objects.ensureCapacity(minCapacity);
    }

    public void pause()
    {
        this.isPaused = true;
    }

    public void resume()
    {
        this.isPaused = false;
    }

    public void update(float delta)
    {
        int i;

        for (i = this.objects.size() - 1; i >= 0; --i)
        {
            BaseTween n = (BaseTween)this.objects.get(i);

            if (n.isFinished() && n.isAutoRemoveEnabled)
            {
                this.objects.remove(i);
                n.free();
            }
        }

        if (!this.isPaused)
        {
            if (delta >= 0.0F)
            {
                i = 0;

                for (int var4 = this.objects.size(); i < var4; ++i)
                {
                    ((BaseTween)this.objects.get(i)).update(delta);
                }
            }
            else
            {
                for (i = this.objects.size() - 1; i >= 0; --i)
                {
                    ((BaseTween)this.objects.get(i)).update(delta);
                }
            }
        }
    }

    public int size()
    {
        return this.objects.size();
    }

    public int getRunningTweensCount()
    {
        return getTweensCount(this.objects);
    }

    public int getRunningTimelinesCount()
    {
        return getTimelinesCount(this.objects);
    }

    public List < BaseTween<? >> getObjects()
    {
        return Collections.unmodifiableList(this.objects);
    }

    private static int getTweensCount(List < BaseTween<? >> objs)
    {
        int cnt = 0;
        int i = 0;

        for (int n = objs.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)objs.get(i);

            if (obj instanceof Tween)
            {
                ++cnt;
            }
            else
            {
                cnt += getTweensCount(((Timeline)obj).getChildren());
            }
        }

        return cnt;
    }

    private static int getTimelinesCount(List < BaseTween<? >> objs)
    {
        int cnt = 0;
        int i = 0;

        for (int n = objs.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)objs.get(i);

            if (obj instanceof Timeline)
            {
                cnt += 1 + getTimelinesCount(((Timeline)obj).getChildren());
            }
        }

        return cnt;
    }
}
