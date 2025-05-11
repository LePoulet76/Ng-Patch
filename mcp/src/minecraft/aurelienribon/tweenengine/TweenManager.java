/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TweenManager {
    private final ArrayList<BaseTween<?>> objects = new ArrayList(20);
    private boolean isPaused = false;

    public static void setAutoRemove(BaseTween<?> object, boolean value) {
        object.isAutoRemoveEnabled = value;
    }

    public static void setAutoStart(BaseTween<?> object, boolean value) {
        object.isAutoStartEnabled = value;
    }

    public TweenManager add(BaseTween<?> object) {
        if (!this.objects.contains(object)) {
            this.objects.add(object);
        }
        if (object.isAutoStartEnabled) {
            object.start();
        }
        return this;
    }

    public boolean containsTarget(Object target) {
        int n = this.objects.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.objects.get(i);
            if (!obj.containsTarget(target)) continue;
            return true;
        }
        return false;
    }

    public boolean containsTarget(Object target, int tweenType) {
        int n = this.objects.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.objects.get(i);
            if (!obj.containsTarget(target, tweenType)) continue;
            return true;
        }
        return false;
    }

    public void killAll() {
        int n = this.objects.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.objects.get(i);
            obj.kill();
        }
    }

    public void killTarget(Object target) {
        int n = this.objects.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.objects.get(i);
            obj.killTarget(target);
        }
    }

    public void killTarget(Object target, int tweenType) {
        int n = this.objects.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = this.objects.get(i);
            obj.killTarget(target, tweenType);
        }
    }

    public void ensureCapacity(int minCapacity) {
        this.objects.ensureCapacity(minCapacity);
    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public void update(float delta) {
        block5: {
            int i;
            for (i = this.objects.size() - 1; i >= 0; --i) {
                BaseTween<?> obj = this.objects.get(i);
                if (!obj.isFinished() || !obj.isAutoRemoveEnabled) continue;
                this.objects.remove(i);
                obj.free();
            }
            if (this.isPaused) break block5;
            if (delta >= 0.0f) {
                int n = this.objects.size();
                for (i = 0; i < n; ++i) {
                    this.objects.get(i).update(delta);
                }
            } else {
                for (i = this.objects.size() - 1; i >= 0; --i) {
                    this.objects.get(i).update(delta);
                }
            }
        }
    }

    public int size() {
        return this.objects.size();
    }

    public int getRunningTweensCount() {
        return TweenManager.getTweensCount(this.objects);
    }

    public int getRunningTimelinesCount() {
        return TweenManager.getTimelinesCount(this.objects);
    }

    public List<BaseTween<?>> getObjects() {
        return Collections.unmodifiableList(this.objects);
    }

    private static int getTweensCount(List<BaseTween<?>> objs) {
        int cnt = 0;
        int n = objs.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = objs.get(i);
            if (obj instanceof Tween) {
                ++cnt;
                continue;
            }
            cnt += TweenManager.getTweensCount(((Timeline)obj).getChildren());
        }
        return cnt;
    }

    private static int getTimelinesCount(List<BaseTween<?>> objs) {
        int cnt = 0;
        int n = objs.size();
        for (int i = 0; i < n; ++i) {
            BaseTween<?> obj = objs.get(i);
            if (!(obj instanceof Timeline)) continue;
            cnt += 1 + TweenManager.getTimelinesCount(((Timeline)obj).getChildren());
        }
        return cnt;
    }
}

