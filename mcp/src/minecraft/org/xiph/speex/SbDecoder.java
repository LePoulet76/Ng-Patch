/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex;

import java.io.StreamCorruptedException;
import org.xiph.speex.Bits;
import org.xiph.speex.Codebook;
import org.xiph.speex.Decoder;
import org.xiph.speex.Filters;
import org.xiph.speex.Lsp;
import org.xiph.speex.NbDecoder;
import org.xiph.speex.SbCodec;
import org.xiph.speex.Stereo;

public class SbDecoder
extends SbCodec
implements Decoder {
    protected Decoder lowdec;
    protected Stereo stereo = new Stereo();
    protected boolean enhanced = true;
    private float[] innov2;

    @Override
    public void wbinit() {
        this.lowdec = new NbDecoder();
        ((NbDecoder)this.lowdec).nbinit();
        this.lowdec.setPerceptualEnhancement(this.enhanced);
        super.wbinit();
        this.init(160, 40, 8, 640, 0.7f);
    }

    @Override
    public void uwbinit() {
        this.lowdec = new SbDecoder();
        ((SbDecoder)this.lowdec).wbinit();
        this.lowdec.setPerceptualEnhancement(this.enhanced);
        super.uwbinit();
        this.init(320, 80, 8, 1280, 0.5f);
    }

    @Override
    public void init(int var1, int var2, int var3, int var4, float var5) {
        super.init(var1, var2, var3, var4, var5);
        this.excIdx = 0;
        this.innov2 = new float[var2];
    }

    @Override
    public int decode(Bits var1, float[] var2) throws StreamCorruptedException {
        int var3;
        int var6 = this.lowdec.decode(var1, this.x0d);
        if (var6 != 0) {
            return var6;
        }
        boolean var10 = this.lowdec.getDtx();
        if (var1 == null) {
            this.decodeLost(var2, var10);
            return 0;
        }
        int var5 = var1.peek();
        if (var5 != 0) {
            var5 = var1.unpack(1);
            this.submodeID = var1.unpack(3);
        } else {
            this.submodeID = 0;
        }
        for (var3 = 0; var3 < this.frameSize; ++var3) {
            this.excBuf[var3] = 0.0f;
        }
        if (this.submodes[this.submodeID] == null) {
            if (var10) {
                this.decodeLost(var2, true);
                return 0;
            }
            for (var3 = 0; var3 < this.frameSize; ++var3) {
                this.excBuf[var3] = 0.0f;
            }
            this.first = 1;
            Filters.iir_mem2(this.excBuf, this.excIdx, this.interp_qlpc, this.high, 0, this.frameSize, this.lpcSize, this.mem_sp);
            this.filters.fir_mem_up(this.x0d, Codebook.h0, this.y0, this.fullFrameSize, 64, this.g0_mem);
            this.filters.fir_mem_up(this.high, Codebook.h1, this.y1, this.fullFrameSize, 64, this.g1_mem);
            for (var3 = 0; var3 < this.fullFrameSize; ++var3) {
                var2[var3] = 2.0f * (this.y0[var3] - this.y1[var3]);
            }
            return 0;
        }
        float[] var7 = this.lowdec.getPiGain();
        float[] var8 = this.lowdec.getExc();
        float[] var9 = this.lowdec.getInnov();
        this.submodes[this.submodeID].lsqQuant.unquant(this.qlsp, this.lpcSize, var1);
        if (this.first != 0) {
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.old_qlsp[var3] = this.qlsp[var3];
            }
        }
        for (int var4 = 0; var4 < this.nbSubframes; ++var4) {
            float var18;
            float var17;
            float var13 = 0.0f;
            float var14 = 0.0f;
            float var15 = 0.0f;
            int var16 = this.subframeSize * var4;
            float var11 = (1.0f + (float)var4) / (float)this.nbSubframes;
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.interp_qlsp[var3] = (1.0f - var11) * this.old_qlsp[var3] + var11 * this.qlsp[var3];
            }
            Lsp.enforce_margin(this.interp_qlsp, this.lpcSize, 0.05f);
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.interp_qlsp[var3] = (float)Math.cos(this.interp_qlsp[var3]);
            }
            this.m_lsp.lsp2lpc(this.interp_qlsp, this.interp_qlpc, this.lpcSize);
            if (this.enhanced) {
                var17 = this.submodes[this.submodeID].lpc_enh_k1;
                var18 = this.submodes[this.submodeID].lpc_enh_k2;
                float var19 = var17 - var18;
                Filters.bw_lpc(var17, this.interp_qlpc, this.awk1, this.lpcSize);
                Filters.bw_lpc(var18, this.interp_qlpc, this.awk2, this.lpcSize);
                Filters.bw_lpc(var19, this.interp_qlpc, this.awk3, this.lpcSize);
            }
            var11 = 1.0f;
            this.pi_gain[var4] = 0.0f;
            for (var3 = 0; var3 <= this.lpcSize; ++var3) {
                var15 += var11 * this.interp_qlpc[var3];
                var11 = -var11;
                int n = var4;
                this.pi_gain[n] = this.pi_gain[n] + this.interp_qlpc[var3];
            }
            var14 = var7[var4];
            var14 = 1.0f / (Math.abs(var14) + 0.01f);
            var15 = 1.0f / (Math.abs(var15) + 0.01f);
            float var12 = Math.abs(0.01f + var15) / (0.01f + Math.abs(var14));
            for (var3 = var16; var3 < var16 + this.subframeSize; ++var3) {
                this.excBuf[var3] = 0.0f;
            }
            if (this.submodes[this.submodeID].innovation == null) {
                int var20 = var1.unpack(5);
                var17 = (float)Math.exp(((double)var20 - 10.0) / 8.0);
                var17 /= var12;
                for (var3 = var16; var3 < var16 + this.subframeSize; ++var3) {
                    this.excBuf[var3] = this.foldingGain * var17 * var9[var3];
                }
            } else {
                int var21 = var1.unpack(4);
                for (var3 = var16; var3 < var16 + this.subframeSize; ++var3) {
                    var13 += var8[var3] * var8[var3];
                }
                var17 = (float)Math.exp(0.27027026f * (float)var21 - 2.0f);
                var18 = var17 * (float)Math.sqrt(1.0f + var13) / var12;
                this.submodes[this.submodeID].innovation.unquant(this.excBuf, var16, this.subframeSize, var1);
                var3 = var16;
                while (var3 < var16 + this.subframeSize) {
                    int n = var3++;
                    this.excBuf[n] = this.excBuf[n] * var18;
                }
                if (this.submodes[this.submodeID].double_codebook != 0) {
                    for (var3 = 0; var3 < this.subframeSize; ++var3) {
                        this.innov2[var3] = 0.0f;
                    }
                    this.submodes[this.submodeID].innovation.unquant(this.innov2, 0, this.subframeSize, var1);
                    var3 = 0;
                    while (var3 < this.subframeSize) {
                        int n = var3++;
                        this.innov2[n] = this.innov2[n] * (var18 * 0.4f);
                    }
                    for (var3 = 0; var3 < this.subframeSize; ++var3) {
                        int n = var16 + var3;
                        this.excBuf[n] = this.excBuf[n] + this.innov2[var3];
                    }
                }
            }
            for (var3 = var16; var3 < var16 + this.subframeSize; ++var3) {
                this.high[var3] = this.excBuf[var3];
            }
            if (this.enhanced) {
                Filters.filter_mem2(this.high, var16, this.awk2, this.awk1, this.subframeSize, this.lpcSize, this.mem_sp, this.lpcSize);
                Filters.filter_mem2(this.high, var16, this.awk3, this.interp_qlpc, this.subframeSize, this.lpcSize, this.mem_sp, 0);
                continue;
            }
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.mem_sp[this.lpcSize + var3] = 0.0f;
            }
            Filters.iir_mem2(this.high, var16, this.interp_qlpc, this.high, var16, this.subframeSize, this.lpcSize, this.mem_sp);
        }
        this.filters.fir_mem_up(this.x0d, Codebook.h0, this.y0, this.fullFrameSize, 64, this.g0_mem);
        this.filters.fir_mem_up(this.high, Codebook.h1, this.y1, this.fullFrameSize, 64, this.g1_mem);
        for (var3 = 0; var3 < this.fullFrameSize; ++var3) {
            var2[var3] = 2.0f * (this.y0[var3] - this.y1[var3]);
        }
        for (var3 = 0; var3 < this.lpcSize; ++var3) {
            this.old_qlsp[var3] = this.qlsp[var3];
        }
        this.first = 0;
        return 0;
    }

    public int decodeLost(float[] var1, boolean var2) {
        int var3;
        int var4 = 0;
        if (var2) {
            var4 = this.submodeID;
            this.submodeID = 1;
        } else {
            Filters.bw_lpc(0.99f, this.interp_qlpc, this.interp_qlpc, this.lpcSize);
        }
        this.first = 1;
        this.awk1 = new float[this.lpcSize + 1];
        this.awk2 = new float[this.lpcSize + 1];
        this.awk3 = new float[this.lpcSize + 1];
        if (this.enhanced) {
            float var6;
            float var5;
            if (this.submodes[this.submodeID] != null) {
                var5 = this.submodes[this.submodeID].lpc_enh_k1;
                var6 = this.submodes[this.submodeID].lpc_enh_k2;
            } else {
                var6 = 0.7f;
                var5 = 0.7f;
            }
            float var7 = var5 - var6;
            Filters.bw_lpc(var5, this.interp_qlpc, this.awk1, this.lpcSize);
            Filters.bw_lpc(var6, this.interp_qlpc, this.awk2, this.lpcSize);
            Filters.bw_lpc(var7, this.interp_qlpc, this.awk3, this.lpcSize);
        }
        if (!var2) {
            for (int var32 = 0; var32 < this.frameSize; ++var32) {
                this.excBuf[this.excIdx + var32] = (float)((double)this.excBuf[this.excIdx + var32] * 0.9);
            }
        }
        for (var3 = 0; var3 < this.frameSize; ++var3) {
            this.high[var3] = this.excBuf[this.excIdx + var3];
        }
        if (this.enhanced) {
            Filters.filter_mem2(this.high, 0, this.awk2, this.awk1, this.high, 0, this.frameSize, this.lpcSize, this.mem_sp, this.lpcSize);
            Filters.filter_mem2(this.high, 0, this.awk3, this.interp_qlpc, this.high, 0, this.frameSize, this.lpcSize, this.mem_sp, 0);
        } else {
            for (var3 = 0; var3 < this.lpcSize; ++var3) {
                this.mem_sp[this.lpcSize + var3] = 0.0f;
            }
            Filters.iir_mem2(this.high, 0, this.interp_qlpc, this.high, 0, this.frameSize, this.lpcSize, this.mem_sp);
        }
        this.filters.fir_mem_up(this.x0d, Codebook.h0, this.y0, this.fullFrameSize, 64, this.g0_mem);
        this.filters.fir_mem_up(this.high, Codebook.h1, this.y1, this.fullFrameSize, 64, this.g1_mem);
        for (var3 = 0; var3 < this.fullFrameSize; ++var3) {
            var1[var3] = 2.0f * (this.y0[var3] - this.y1[var3]);
        }
        if (var2) {
            this.submodeID = var4;
        }
        return 0;
    }

    @Override
    public void decodeStereo(float[] var1, int var2) {
        this.stereo.decode(var1, var2);
    }

    @Override
    public void setPerceptualEnhancement(boolean var1) {
        this.enhanced = var1;
    }

    @Override
    public boolean getPerceptualEnhancement() {
        return this.enhanced;
    }
}

