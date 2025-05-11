/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.Codebook;
import org.xiph.speex.LspQuant;

public class HighLspQuant
extends LspQuant {
    @Override
    public final void quant(float[] var1, float[] var2, int var3, Bits var4) {
        int var5;
        float[] var9 = new float[20];
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = var1[var5];
        }
        var9[0] = 1.0f / (var2[1] - var2[0]);
        var9[var3 - 1] = 1.0f / (var2[var3 - 1] - var2[var3 - 2]);
        for (var5 = 1; var5 < var3 - 1; ++var5) {
            float var6 = 1.0f / (var2[var5] - var2[var5 - 1]);
            float var7 = 1.0f / (var2[var5 + 1] - var2[var5]);
            var9[var5] = var6 > var7 ? var6 : var7;
        }
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = (float)((double)var2[var5] - (0.3125 * (double)var5 + 0.75));
        }
        var5 = 0;
        while (var5 < var3) {
            int n = var5++;
            var2[n] = var2[n] * 256.0f;
        }
        int var8 = HighLspQuant.lsp_quant(var2, 0, Codebook.high_lsp_cdbk, 64, var3);
        var4.pack(var8, 6);
        var5 = 0;
        while (var5 < var3) {
            int n = var5++;
            var2[n] = var2[n] * 2.0f;
        }
        var8 = HighLspQuant.lsp_weight_quant(var2, 0, var9, 0, Codebook.high_lsp_cdbk2, 64, var3);
        var4.pack(var8, 6);
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = (float)((double)var2[var5] * 0.0019531);
        }
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = var1[var5] - var2[var5];
        }
    }

    @Override
    public final void unquant(float[] var1, int var2, Bits var3) {
        for (int var4 = 0; var4 < var2; ++var4) {
            var1[var4] = 0.3125f * (float)var4 + 0.75f;
        }
        this.unpackPlus(var1, Codebook.high_lsp_cdbk, var3, 0.0039062f, var2, 0);
        this.unpackPlus(var1, Codebook.high_lsp_cdbk2, var3, 0.0019531f, var2, 0);
    }
}

