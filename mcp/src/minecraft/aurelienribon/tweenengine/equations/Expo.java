/*
 * Decompiled with CFR 0.152.
 */
package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Expo
extends TweenEquation {
    public static final Expo IN = new Expo(){

        @Override
        public final float compute(float t) {
            return t == 0.0f ? 0.0f : (float)Math.pow(2.0, 10.0f * (t - 1.0f));
        }

        public String toString() {
            return "Expo.IN";
        }
    };
    public static final Expo OUT = new Expo(){

        @Override
        public final float compute(float t) {
            return t == 1.0f ? 1.0f : -((float)Math.pow(2.0, -10.0f * t)) + 1.0f;
        }

        public String toString() {
            return "Expo.OUT";
        }
    };
    public static final Expo INOUT = new Expo(){

        @Override
        public final float compute(float t) {
            float f;
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == 1.0f) {
                return 1.0f;
            }
            t *= 2.0f;
            if (f < 1.0f) {
                return 0.5f * (float)Math.pow(2.0, 10.0f * (t - 1.0f));
            }
            return 0.5f * (-((float)Math.pow(2.0, -10.0f * (t -= 1.0f))) + 2.0f);
        }

        public String toString() {
            return "Expo.INOUT";
        }
    };
}

