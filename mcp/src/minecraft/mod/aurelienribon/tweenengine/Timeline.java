package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Pool$Callback;
import aurelienribon.tweenengine.Timeline$1;
import aurelienribon.tweenengine.Timeline$2;
import aurelienribon.tweenengine.Timeline$3;
import aurelienribon.tweenengine.Timeline$Modes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Timeline extends BaseTween<Timeline>
{
    private static final Pool$Callback<Timeline> poolCallback = new Timeline$1();
    static final Pool<Timeline> pool = new Timeline$2(10, poolCallback);
    private final List < BaseTween<? >> children;
    private Timeline current;
    private Timeline parent;
    private Timeline$Modes mode;
    private boolean isBuilt;

    public static int getPoolSize()
    {
        return pool.size();
    }

    public static void ensurePoolCapacity(int minCapacity)
    {
        pool.ensureCapacity(minCapacity);
    }

    public static Timeline createSequence()
    {
        Timeline tl = (Timeline)pool.get();
        tl.setup(Timeline$Modes.SEQUENCE);
        return tl;
    }

    public static Timeline createParallel()
    {
        Timeline tl = (Timeline)pool.get();
        tl.setup(Timeline$Modes.PARALLEL);
        return tl;
    }

    private Timeline()
    {
        this.children = new ArrayList(10);
        this.reset();
    }

    protected void reset()
    {
        super.reset();
        this.children.clear();
        this.current = this.parent = null;
        this.isBuilt = false;
    }

    private void setup(Timeline$Modes mode)
    {
        this.mode = mode;
        this.current = this;
    }

    public Timeline push(Tween tween)
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else
        {
            this.current.children.add(tween);
            return this;
        }
    }

    public Timeline push(Timeline timeline)
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else if (timeline.current != timeline)
        {
            throw new RuntimeException("You forgot to call a few \'end()\' statements in your pushed timeline");
        }
        else
        {
            timeline.parent = this.current;
            this.current.children.add(timeline);
            return this;
        }
    }

    public Timeline pushPause(float time)
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else
        {
            this.current.children.add(Tween.mark().delay(time));
            return this;
        }
    }

    public Timeline beginSequence()
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else
        {
            Timeline tl = (Timeline)pool.get();
            tl.parent = this.current;
            tl.mode = Timeline$Modes.SEQUENCE;
            this.current.children.add(tl);
            this.current = tl;
            return this;
        }
    }

    public Timeline beginParallel()
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else
        {
            Timeline tl = (Timeline)pool.get();
            tl.parent = this.current;
            tl.mode = Timeline$Modes.PARALLEL;
            this.current.children.add(tl);
            this.current = tl;
            return this;
        }
    }

    public Timeline end()
    {
        if (this.isBuilt)
        {
            throw new RuntimeException("You can\'t push anything to a timeline once it is started");
        }
        else if (this.current == this)
        {
            throw new RuntimeException("Nothing to end...");
        }
        else
        {
            this.current = this.current.parent;
            return this;
        }
    }

    public List < BaseTween<? >> getChildren()
    {
        return this.isBuilt ? Collections.unmodifiableList(this.current.children) : this.current.children;
    }

    public Timeline build()
    {
        if (this.isBuilt)
        {
            return this;
        }
        else
        {
            this.duration = 0.0F;

            for (int i = 0; i < this.children.size(); ++i)
            {
                BaseTween obj = (BaseTween)this.children.get(i);

                if (obj.getRepeatCount() < 0)
                {
                    throw new RuntimeException("You can\'t push an object with infinite repetitions in a timeline");
                }

                obj.build();

                switch (Timeline$3.$SwitchMap$aurelienribon$tweenengine$Timeline$Modes[this.mode.ordinal()])
                {
                    case 1:
                        float tDelay = this.duration;
                        this.duration += obj.getFullDuration();
                        obj.delay += tDelay;
                        break;

                    case 2:
                        this.duration = Math.max(this.duration, obj.getFullDuration());
                }
            }

            this.isBuilt = true;
            return this;
        }
    }

    public Timeline start()
    {
        super.start();

        for (int i = 0; i < this.children.size(); ++i)
        {
            BaseTween obj = (BaseTween)this.children.get(i);
            obj.start();
        }

        return this;
    }

    public void free()
    {
        for (int i = this.children.size() - 1; i >= 0; --i)
        {
            BaseTween obj = (BaseTween)this.children.remove(i);
            obj.free();
        }

        pool.free(this);
    }

    protected void updateOverride(int step, int lastStep, boolean isIterationStep, float delta)
    {
        int i;
        int n;
        float var8;

        if (!isIterationStep && step > lastStep)
        {
            assert delta >= 0.0F;
            var8 = this.isReverse(lastStep) ? -delta - 1.0F : delta + 1.0F;
            i = 0;

            for (n = this.children.size(); i < n; ++i)
            {
                ((BaseTween)this.children.get(i)).update(var8);
            }
        }
        else if (!isIterationStep && step < lastStep)
        {
            assert delta <= 0.0F;
            var8 = this.isReverse(lastStep) ? -delta - 1.0F : delta + 1.0F;

            for (i = this.children.size() - 1; i >= 0; --i)
            {
                ((BaseTween)this.children.get(i)).update(var8);
            }
        }
        else
        {
            assert isIterationStep;
            int dt;

            if (step > lastStep)
            {
                if (this.isReverse(step))
                {
                    this.forceEndValues();
                    dt = 0;

                    for (i = this.children.size(); dt < i; ++dt)
                    {
                        ((BaseTween)this.children.get(dt)).update(delta);
                    }
                }
                else
                {
                    this.forceStartValues();
                    dt = 0;

                    for (i = this.children.size(); dt < i; ++dt)
                    {
                        ((BaseTween)this.children.get(dt)).update(delta);
                    }
                }
            }
            else if (step < lastStep)
            {
                if (this.isReverse(step))
                {
                    this.forceStartValues();

                    for (dt = this.children.size() - 1; dt >= 0; --dt)
                    {
                        ((BaseTween)this.children.get(dt)).update(delta);
                    }
                }
                else
                {
                    this.forceEndValues();

                    for (dt = this.children.size() - 1; dt >= 0; --dt)
                    {
                        ((BaseTween)this.children.get(dt)).update(delta);
                    }
                }
            }
            else
            {
                var8 = this.isReverse(step) ? -delta : delta;

                if (delta >= 0.0F)
                {
                    i = 0;

                    for (n = this.children.size(); i < n; ++i)
                    {
                        ((BaseTween)this.children.get(i)).update(var8);
                    }
                }
                else
                {
                    for (i = this.children.size() - 1; i >= 0; --i)
                    {
                        ((BaseTween)this.children.get(i)).update(var8);
                    }
                }
            }
        }
    }

    protected void forceStartValues()
    {
        for (int i = this.children.size() - 1; i >= 0; --i)
        {
            BaseTween obj = (BaseTween)this.children.get(i);
            obj.forceToStart();
        }
    }

    protected void forceEndValues()
    {
        int i = 0;

        for (int n = this.children.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.children.get(i);
            obj.forceToEnd(this.duration);
        }
    }

    protected boolean containsTarget(Object target)
    {
        int i = 0;

        for (int n = this.children.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.children.get(i);

            if (obj.containsTarget(target))
            {
                return true;
            }
        }

        return false;
    }

    protected boolean containsTarget(Object target, int tweenType)
    {
        int i = 0;

        for (int n = this.children.size(); i < n; ++i)
        {
            BaseTween obj = (BaseTween)this.children.get(i);

            if (obj.containsTarget(target, tweenType))
            {
                return true;
            }
        }

        return false;
    }

    public Object start()
    {
        return this.start();
    }

    public Object build()
    {
        return this.build();
    }

    Timeline(Timeline$1 x0)
    {
        this();
    }
}
