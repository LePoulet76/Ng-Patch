/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

public class VQ {
    public static final int index(float var0, float[] var1, int var2) {
        float var4 = 0.0f;
        int var5 = 0;
        for (int var3 = 0; var3 < var2; ++var3) {
            float var6 = var0 - var1[var3];
            var6 *= var6;
            if (var3 != 0 && !(var6 < var4)) continue;
            var4 = var6;
            var5 = var3;
        }
        return var5;
    }

    public static final int index(float[] var0, float[] var1, int var2, int var3) {
        int var6 = 0;
        float var7 = 0.0f;
        int var8 = 0;
        for (int var4 = 0; var4 < var3; ++var4) {
            float var9 = 0.0f;
            for (int var5 = 0; var5 < var2; ++var5) {
                float var10 = var0[var5] - var1[var6++];
                var9 += var10 * var10;
            }
            if (var4 != 0 && !(var9 < var7)) continue;
            var7 = var9;
            var8 = var4;
        }
        return var8;
    }

    public static final void nbest(float[] var0, int var1, float[] var2, int var3, int var4, float[] var5, int var6, int[] var7, float[] var8) {
        int var12 = 0;
        int var13 = 0;
        for (int var9 = 0; var9 < var4; ++var9) {
            float var14 = 0.5f * var5[var9];
            for (int var10 = 0; var10 < var3; ++var10) {
                var14 -= var0[var1 + var10] * var2[var12++];
            }
            if (var9 >= var6 && !(var14 < var8[var6 - 1])) continue;
            for (int var11 = var6 - 1; var11 >= 1 && (var11 > var13 || var14 < var8[var11 - 1]); --var11) {
                var8[var11] = var8[var11 - 1];
                var7[var11] = var7[var11 - 1];
            }
            var8[var11] = var14;
            var7[var11] = var9;
            ++var13;
        }
    }

    public static final void nbest_sign(float[] var0, int var1, float[] var2, int var3, int var4, float[] var5, int var6, int[] var7, float[] var8) {
        int var12 = 0;
        int var14 = 0;
        for (int var9 = 0; var9 < var4; ++var9) {
            int var11;
            boolean var13;
            float var15 = 0.0f;
            for (int var10 = 0; var10 < var3; ++var10) {
                var15 -= var0[var1 + var10] * var2[var12++];
            }
            if (var15 > 0.0f) {
                var13 = true;
                var15 = -var15;
            } else {
                var13 = false;
            }
            var15 = (float)((double)var15 + 0.5 * (double)var5[var9]);
            if (var9 >= var6 && !(var15 < var8[var6 - 1])) continue;
            for (var11 = var6 - 1; var11 >= 1 && (var11 > var14 || var15 < var8[var11 - 1]); --var11) {
                var8[var11] = var8[var11 - 1];
                var7[var11] = var7[var11 - 1];
            }
            var8[var11] = var15;
            var7[var11] = var9;
            ++var14;
            if (!var13) continue;
            int n = var11;
            var7[n] = var7[n] + var4;
        }
    }
}

