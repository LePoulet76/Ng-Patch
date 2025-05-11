/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine;

import java.util.ArrayList;

abstract class Pool<T> {
    private final ArrayList<T> objects;
    private final Callback<T> callback;

    protected abstract T create();

    public Pool(int initCapacity, Callback<T> callback) {
        this.objects = new ArrayList(initCapacity);
        this.callback = callback;
    }

    public T get() {
        T obj;
        T t = obj = this.objects.isEmpty() ? this.create() : this.objects.remove(this.objects.size() - 1);
        if (this.callback != null) {
            this.callback.onUnPool(obj);
        }
        return obj;
    }

    public void free(T obj) {
        if (!this.objects.contains(obj)) {
            if (this.callback != null) {
                this.callback.onPool(obj);
            }
            this.objects.add(obj);
        }
    }

    public void clear() {
        this.objects.clear();
    }

    public int size() {
        return this.objects.size();
    }

    public void ensureCapacity(int minCapacity) {
        this.objects.ensureCapacity(minCapacity);
    }

    public static interface Callback<T> {
        public void onPool(T var1);

        public void onUnPool(T var1);
    }
}

