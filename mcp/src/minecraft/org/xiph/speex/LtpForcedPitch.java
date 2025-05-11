/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.Ltp;

public class LtpForcedPitch
extends Ltp {
    @Override
    public final int quant(float[] var1, float[] var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7, int var8, int var9, int var10, float var11, int var12, int var13, Bits var14, float[] var15, int var16, float[] var17, int var18) {
        if (var11 > 0.99f) {
            var11 = 0.99f;
        }
        for (int var19 = 0; var19 < var13; ++var19) {
            var7[var8 + var19] = var7[var8 + var19 - var9] * var11;
        }
        return var9;
    }

    @Override
    public final int unquant(float[] var1, int var2, int var3, float var4, int var5, float[] var6, Bits var7, int var8, int var9, float var10) {
        if (var4 > 0.99f) {
            var4 = 0.99f;
        }
        for (int var11 = 0; var11 < var5; ++var11) {
            var1[var2 + var11] = var1[var2 + var11 - var3] * var4;
        }
        var6[2] = 0.0f;
        var6[0] = 0.0f;
        var6[1] = var4;
        return var3;
    }
}

