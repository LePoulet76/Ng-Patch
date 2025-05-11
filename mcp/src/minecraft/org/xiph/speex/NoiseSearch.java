/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.CbSearch;
import org.xiph.speex.Filters;

public class NoiseSearch
extends CbSearch {
    @Override
    public final void quant(float[] var1, float[] var2, float[] var3, float[] var4, int var5, int var6, float[] var7, int var8, float[] var9, Bits var10, int var11) {
        int var12;
        float[] var13 = new float[var6];
        Filters.residue_percep_zero(var1, 0, var2, var3, var4, var13, var6, var5);
        for (var12 = 0; var12 < var6; ++var12) {
            int n = var8 + var12;
            var7[n] = var7[n] + var13[var12];
        }
        for (var12 = 0; var12 < var6; ++var12) {
            var1[var12] = 0.0f;
        }
    }

    @Override
    public final void unquant(float[] var1, int var2, int var3, Bits var4) {
        for (int var5 = 0; var5 < var3; ++var5) {
            int n = var2 + var5;
            var1[n] = var1[n] + (float)(3.0 * (Math.random() - 0.5));
        }
    }
}

