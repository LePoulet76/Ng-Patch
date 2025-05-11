/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Pool;
import aurelienribon.tweenengine.Tween;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Timeline
extends BaseTween<Timeline> {
    private static final Pool.Callback<Timeline> poolCallback = new Pool.Callback<Timeline>(){

        @Override
        public void onPool(Timeline obj) {
            obj.reset();
        }

        @Override
        public void onUnPool(Timeline obj) {
            obj.reset();
        }
    };
    static final Pool<Timeline> pool = new Pool<Timeline>(10, poolCallback){

        @Override
        protected Timeline create() {
            return new Timeline();
        }
    };
    private final List<BaseTween<?>> children = new ArrayList(10);
    private Timeline current;
    private Timeline parent;
    private Modes mode;
    private boolean isBuilt;

    public static int getPoolSize() {
        return pool.size();
    }

    public static void ensurePoolCapacity(int minCapacity) {
        pool.ensureCapacity(minCapacity);
    }

    public static Timeline createSequence() {
        Timeline tl = pool.get();
        tl.setup(Modes.SEQUENCE);
        return tl;
    }

    public static Timeline createParallel() {
        Timeline tl = pool.get();
        tl.setup(Modes.PARALLEL);
        return tl;
    }

    private Timeline() {
        this.reset();
    }

    @Override
    protected void reset() {
        super.reset();
        this.children.clear();
        this.parent = null;
        this.current = null;
        this.isBuilt = false;
    }

    private void setup(Modes mode) {
        this.mode = mode;
        this.current = this;
    }

    public Timeline push(Tween tween) {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        this.current.children.add(tween);
        return this;
    }

    public Timeline push(Timeline timeline) {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        if (timeline.current != timeline) {
            throw new RuntimeException("You forgot to call a few 'end()' statements in your pushed timeline");
        }
        timeline.parent = this.current;
        this.current.children.add(timeline);
        return this;
    }

    public Timeline pushPause(float time) {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        this.current.children.add((BaseTween<?>)Tween.mark().delay(time));
        return this;
    }

    public Timeline beginSequence() {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        Timeline tl = pool.get();
        tl.parent = this.current;
        tl.mode = Modes.SEQUENCE;
        this.current.children.add(tl);
        this.current = tl;
        return this;
    }

    public Timeline beginParallel() {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        Timeline tl = pool.get();
        tl.parent = this.current;
        tl.mode = Modes.PARALLEL;
        this.current.children.add(tl);
        this.current = tl;
        return this;
    }

    public Timeline end() {
        if (this.isBuilt) {
            throw new RuntimeException("You can't push anything to a timeline once it is started");
        }
        if (this.current == this) {
            throw new RuntimeException("Nothing to end...");
        }
        this.current = this.current.parent;
        return this;
    }

    public List<BaseTween<?>> getChildren() {
        if (this.isBuilt) {
            return Collections.unmodifiableList(this.current.children);
        }
        return this.current.children;
    }

    @Override
    public Timeline build() {
        if (this.isBuilt) {
            return this;
        }
        this.duration = 0.0f;
        block4: for (int i = 0; i < this.children.size(); ++i) {
            BaseTween<?> obj = this.children.get(i);
            if (obj.getRepeatCount() < 0) {
                throw new RuntimeException("You can't push an object with infinite repetitions in a timeline");
            }
            obj.build();
            switch (this.mode) {
                case SEQUENCE: {
                    float tDelay = this.duration;
                    this.duration += obj.getFullDuration();
                    obj.delay += tDelay;
                    continue block4;
                }
                case PARALLEL: {
                    this.duration = Math.max(this.duration, obj.getFullDuration());
                }
            }
        }
        this.isBuilt = true;
        return this;
    }

    @Override
    public Timeline start() {
        super.start();
        for (int i = 0; i < this.children.size(); ++i) {
            BaseTween<?> obj = this.children.get(i);
            obj.start();
        }
        return this;
    }

    @Override
    public void free() {
        for (int i = this.children.size() - 1; i >= 0; --i) {
            BaseTween<?> obj = this.children.remove(i);
            obj.free();
        }
        pool.free(this);
    }

    @Override
    protected void updateOverride(int step, int lastStep, boolean isIterationStep, float delta) {
        if (!isIterationStep && step > lastStep) {
            assert (delta >= 0.0f);
            float dt = this.isReverse(lastStep) ? -delta - 1.0f : delta + 1.0f;
            int n = this.children.size();
            for (int i = 0; i < n; ++i) {
                this.children.get(i).update(dt);
            }
            return;
        }
        if (!isIterationStep && step < lastStep) {
            assert (delta <= 0.0f);
            float dt = this.isReverse(lastStep) ? -delta - 1.0f : delta + 1.0f;
            for (int i = this.children.size() - 1; i >= 0; --i) {
                this.children.get(i).update(dt);
            }
            return;
        }
        assert (isIterationStep);
        if (step > lastStep) {
            if (this.isReverse(step)) {
                this.forceEndValues();
                int n = this.children.size();
                for (int i = 0; i < n; ++i) {
                    this.children.get(i).update(delta);
                }
            } else {
                this.forceStartValues();
                int n = this.children.size();
                for (int i = 0; i < n; ++i) {
                    this.children.get(i).update(delta);
                }
            }
        } else if (step < lastStep) {
            if (this.isReverse(step)) {
                this.forceStartValues();
                for (int i = this.children.size() - 1; i >= 0; --i) {
                    this.children.get(i).update(delta);
                }
            } else {
                this.forceEndValues();
                for (int i = this.children.size() - 1; i >= 0; --i) {
                    this.children.get(i).update(delta);
                }
            }
        } else {
            float dt;
            float f = dt = this.isReverse(step) ? -delta : delta;
            if (delta >= 0.0f) {
                int n = this.children.size();
                for (int i = 0; i < n; ++i) {
                    this.children.get(i).update(dt);
                }
            } else {
                for (int i = this.children.size() - 1; i >= 0; --i) {
                    this.children.get(i).update(dt);
                }
            }
        }
    }

    @Override
    protected void forceStartValues() {
        for (int i = this.children.size() - 1; i >= 0; --i) {
            BaseTween<?> obj = this.children.get(i);
            obj.forceToStart();
        }
    }

    @Override
    protected void forceEndValues() {
        int n = this.children.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.children.get(i);
            obj.forceToEnd(this.duration);
        }
    }

    @Override
    protected boolean containsTarget(Object target) {
        int n = this.children.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.children.get(i);
            if (!obj.containsTarget(target)) continue;
            return true;
        }
        return false;
    }

    @Override
    protected boolean containsTarget(Object target, int tweenType) {
        int n = this.children.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.children.get(i);
            if (!obj.containsTarget(target, tweenType)) continue;
            return true;
        }
        return false;
    }

    private static enum Modes {
        SEQUENCE,
        PARALLEL;

    }
}

