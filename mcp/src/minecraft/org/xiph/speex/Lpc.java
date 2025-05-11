/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class Lpc {
    public static float wld(float[] var0, float[] var1, float[] var2, int var3) {
        float var7 = var1[0];
        if (var1[0] == 0.0f) {
            for (int var4 = 0; var4 < var3; ++var4) {
                var2[var4] = 0.0f;
            }
            return 0.0f;
        }
        for (int var4 = 0; var4 < var3; ++var4) {
            int var5;
            float var6 = -var1[var4 + 1];
            for (var5 = 0; var5 < var4; ++var5) {
                var6 -= var0[var5] * var1[var4 - var5];
            }
            var2[var4] = var6 /= var7;
            var0[var4] = var6;
            for (var5 = 0; var5 < var4 / 2; ++var5) {
                float var8 = var0[var5];
                int n = var5;
                var0[n] = var0[n] + var6 * var0[var4 - 1 - var5];
                int n2 = var4 - 1 - var5;
                var0[n2] = var0[n2] + var6 * var8;
            }
            if (var4 % 2 != 0) {
                int n = var5;
                var0[n] = var0[n] + var0[var5] * var6;
            }
            var7 = (float)((double)var7 * (1.0 - (double)(var6 * var6)));
        }
        return var7;
    }

    public static void autocorr(float[] var0, float[] var1, int var2, int var3) {
        while (var2-- > 0) {
            float var4 = 0.0f;
            for (int var5 = var2; var5 < var3; ++var5) {
                var4 += var0[var5] * var0[var5 - var2];
            }
            var1[var2] = var4;
        }
    }
}

