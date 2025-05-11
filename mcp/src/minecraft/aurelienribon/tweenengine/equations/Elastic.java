/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Elastic
extends TweenEquation {
    private static final float PI = (float)Math.PI;
    public static final Elastic IN = new Elastic(){

        @Override
        public final float compute(float t) {
            float s;
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == 1.0f) {
                return 1.0f;
            }
            if (!this.setP) {
                p = 0.3f;
            }
            if (!this.setA || a < 1.0f) {
                a = 1.0f;
                s = p / 4.0f;
            } else {
                s = p / ((float)Math.PI * 2) * (float)Math.asin(1.0f / a);
            }
            return -(a * (float)Math.pow(2.0, 10.0f * (t -= 1.0f)) * (float)Math.sin((t - s) * ((float)Math.PI * 2) / p));
        }

        public String toString() {
            return "Elastic.IN";
        }
    };
    public static final Elastic OUT = new Elastic(){

        @Override
        public final float compute(float t) {
            float s;
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == 1.0f) {
                return 1.0f;
            }
            if (!this.setP) {
                p = 0.3f;
            }
            if (!this.setA || a < 1.0f) {
                a = 1.0f;
                s = p / 4.0f;
            } else {
                s = p / ((float)Math.PI * 2) * (float)Math.asin(1.0f / a);
            }
            return a * (float)Math.pow(2.0, -10.0f * t) * (float)Math.sin((t - s) * ((float)Math.PI * 2) / p) + 1.0f;
        }

        public String toString() {
            return "Elastic.OUT";
        }
    };
    public static final Elastic INOUT = new Elastic(){

        @Override
        public final float compute(float t) {
            float s;
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            if ((t *= 2.0f) == 2.0f) {
                return 1.0f;
            }
            if (!this.setP) {
                p = 0.45000002f;
            }
            if (!this.setA || a < 1.0f) {
                a = 1.0f;
                s = p / 4.0f;
            } else {
                s = p / ((float)Math.PI * 2) * (float)Math.asin(1.0f / a);
            }
            if (t < 1.0f) {
                return -0.5f * (a * (float)Math.pow(2.0, 10.0f * (t -= 1.0f)) * (float)Math.sin((t - s) * ((float)Math.PI * 2) / p));
            }
            return a * (float)Math.pow(2.0, -10.0f * (t -= 1.0f)) * (float)Math.sin((t - s) * ((float)Math.PI * 2) / p) * 0.5f + 1.0f;
        }

        public String toString() {
            return "Elastic.INOUT";
        }
    };
    protected float param_a;
    protected float param_p;
    protected boolean setA = false;
    protected boolean setP = false;

    public Elastic a(float a) {
        this.param_a = a;
        this.setA = true;
        return this;
    }

    public Elastic p(float p) {
        this.param_p = p;
        this.setP = true;
        return this;
    }
}

