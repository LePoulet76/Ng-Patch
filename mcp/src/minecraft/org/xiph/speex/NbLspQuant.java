/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Bits;
import org.xiph.speex.Codebook;
import org.xiph.speex.LspQuant;

public class NbLspQuant
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
            float var6 = 1.0f / ((0.15f + var2[var5] - var2[var5 - 1]) * (0.15f + var2[var5] - var2[var5 - 1]));
            float var7 = 1.0f / ((0.15f + var2[var5 + 1] - var2[var5]) * (0.15f + var2[var5 + 1] - var2[var5]));
            var9[var5] = var6 > var7 ? var6 : var7;
        }
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = (float)((double)var2[var5] - (0.25 * (double)var5 + 0.25));
        }
        var5 = 0;
        while (var5 < var3) {
            int n = var5++;
            var2[n] = var2[n] * 256.0f;
        }
        int var8 = NbLspQuant.lsp_quant(var2, 0, Codebook.cdbk_nb, 64, var3);
        var4.pack(var8, 6);
        var5 = 0;
        while (var5 < var3) {
            int n = var5++;
            var2[n] = var2[n] * 2.0f;
        }
        var8 = NbLspQuant.lsp_weight_quant(var2, 0, var9, 0, Codebook.cdbk_nb_low1, 64, 5);
        var4.pack(var8, 6);
        var5 = 0;
        while (var5 < 5) {
            int n = var5++;
            var2[n] = var2[n] * 2.0f;
        }
        var8 = NbLspQuant.lsp_weight_quant(var2, 0, var9, 0, Codebook.cdbk_nb_low2, 64, 5);
        var4.pack(var8, 6);
        var8 = NbLspQuant.lsp_weight_quant(var2, 5, var9, 5, Codebook.cdbk_nb_high1, 64, 5);
        var4.pack(var8, 6);
        var5 = 5;
        while (var5 < 10) {
            int n = var5++;
            var2[n] = var2[n] * 2.0f;
        }
        var8 = NbLspQuant.lsp_weight_quant(var2, 5, var9, 5, Codebook.cdbk_nb_high2, 64, 5);
        var4.pack(var8, 6);
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = (float)((double)var2[var5] * 9.7656E-4);
        }
        for (var5 = 0; var5 < var3; ++var5) {
            var2[var5] = var1[var5] - var2[var5];
        }
    }

    @Override
    public final void unquant(float[] var1, int var2, Bits var3) {
        for (int var4 = 0; var4 < var2; ++var4) {
            var1[var4] = 0.25f * (float)var4 + 0.25f;
        }
        this.unpackPlus(var1, Codebook.cdbk_nb, var3, 0.0039062f, 10, 0);
        this.unpackPlus(var1, Codebook.cdbk_nb_low1, var3, 0.0019531f, 5, 0);
        this.unpackPlus(var1, Codebook.cdbk_nb_low2, var3, 9.7656E-4f, 5, 0);
        this.unpackPlus(var1, Codebook.cdbk_nb_high1, var3, 0.0019531f, 5, 5);
        this.unpackPlus(var1, Codebook.cdbk_nb_high2, var3, 9.7656E-4f, 5, 5);
    }
}

