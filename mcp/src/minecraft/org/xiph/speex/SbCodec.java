/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import org.xiph.speex.Codebook;
import org.xiph.speex.HighLspQuant;
import org.xiph.speex.NbCodec;
import org.xiph.speex.SplitShapeSearch;
import org.xiph.speex.SubMode;

public class SbCodec
extends NbCodec {
    public static final int[] SB_FRAME_SIZE = new int[]{4, 36, 112, 192, 352, -1, -1, -1};
    public static final int SB_SUBMODES = 8;
    public static final int SB_SUBMODE_BITS = 3;
    public static final int QMF_ORDER = 64;
    protected int fullFrameSize;
    protected float foldingGain;
    protected float[] high;
    protected float[] y0;
    protected float[] y1;
    protected float[] x0d;
    protected float[] g0_mem;
    protected float[] g1_mem;

    public void wbinit() {
        this.submodes = SbCodec.buildWbSubModes();
        this.submodeID = 3;
    }

    public void uwbinit() {
        this.submodes = SbCodec.buildUwbSubModes();
        this.submodeID = 1;
    }

    protected void init(int var1, int var2, int var3, int var4, float var5) {
        super.init(var1, var2, var3, var4);
        this.fullFrameSize = 2 * var1;
        this.foldingGain = var5;
        this.lag_factor = 0.002f;
        this.high = new float[this.fullFrameSize];
        this.y0 = new float[this.fullFrameSize];
        this.y1 = new float[this.fullFrameSize];
        this.x0d = new float[var1];
        this.g0_mem = new float[64];
        this.g1_mem = new float[64];
    }

    protected static SubMode[] buildWbSubModes() {
        HighLspQuant var0 = new HighLspQuant();
        SplitShapeSearch var1 = new SplitShapeSearch(40, 10, 4, Codebook.hexc_10_32_table, 5, 0);
        SplitShapeSearch var2 = new SplitShapeSearch(40, 8, 5, Codebook.hexc_table, 7, 1);
        SubMode[] var3 = new SubMode[8];
        var3[1] = new SubMode(0, 0, 1, 0, var0, null, null, 0.75f, 0.75f, -1.0f, 36);
        var3[2] = new SubMode(0, 0, 1, 0, var0, null, var1, 0.85f, 0.6f, -1.0f, 112);
        var3[3] = new SubMode(0, 0, 1, 0, var0, null, var2, 0.75f, 0.7f, -1.0f, 192);
        var3[4] = new SubMode(0, 0, 1, 1, var0, null, var2, 0.75f, 0.75f, -1.0f, 352);
        return var3;
    }

    protected static SubMode[] buildUwbSubModes() {
        HighLspQuant var0 = new HighLspQuant();
        SubMode[] var1 = new SubMode[8];
        var1[1] = new SubMode(0, 0, 1, 0, var0, null, null, 0.75f, 0.75f, -1.0f, 2);
        return var1;
    }

    @Override
    public int getFrameSize() {
        return this.fullFrameSize;
    }

    @Override
    public boolean getDtx() {
        return this.dtx_enabled != 0;
    }

    @Override
    public float[] getExc() {
        float[] var2 = new float[this.fullFrameSize];
        for (int var1 = 0; var1 < this.frameSize; ++var1) {
            var2[2 * var1] = 2.0f * this.excBuf[this.excIdx + var1];
        }
        return var2;
    }

    @Override
    public float[] getInnov() {
        return this.getExc();
    }
}

