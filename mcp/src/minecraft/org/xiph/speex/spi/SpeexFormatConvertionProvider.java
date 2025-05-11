/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.spi;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.spi.FormatConversionProvider;
import org.xiph.speex.spi.Pcm2SpeexAudioInputStream;
import org.xiph.speex.spi.Speex2PcmAudioInputStream;
import org.xiph.speex.spi.SpeexEncoding;

public class SpeexFormatConvertionProvider
extends FormatConversionProvider {
    public static final AudioFormat.Encoding[] NO_ENCODING = new AudioFormat.Encoding[0];
    public static final AudioFormat.Encoding[] PCM_ENCODING = new AudioFormat.Encoding[]{AudioFormat.Encoding.PCM_SIGNED};
    public static final AudioFormat.Encoding[] SPEEX_ENCODING = new AudioFormat.Encoding[]{SpeexEncoding.SPEEX};
    public static final AudioFormat.Encoding[] BOTH_ENCODINGS = new AudioFormat.Encoding[]{SpeexEncoding.SPEEX, AudioFormat.Encoding.PCM_SIGNED};
    public static final AudioFormat[] NO_FORMAT = new AudioFormat[0];

    @Override
    public AudioFormat.Encoding[] getSourceEncodings() {
        AudioFormat.Encoding[] var1 = new AudioFormat.Encoding[]{SpeexEncoding.SPEEX, AudioFormat.Encoding.PCM_SIGNED};
        return var1;
    }

    @Override
    public AudioFormat.Encoding[] getTargetEncodings() {
        AudioFormat.Encoding[] var1 = new AudioFormat.Encoding[]{SpeexEncoding.SPEEX_Q0, SpeexEncoding.SPEEX_Q1, SpeexEncoding.SPEEX_Q2, SpeexEncoding.SPEEX_Q3, SpeexEncoding.SPEEX_Q4, SpeexEncoding.SPEEX_Q5, SpeexEncoding.SPEEX_Q6, SpeexEncoding.SPEEX_Q7, SpeexEncoding.SPEEX_Q8, SpeexEncoding.SPEEX_Q9, SpeexEncoding.SPEEX_Q10, SpeexEncoding.SPEEX_VBR0, SpeexEncoding.SPEEX_VBR1, SpeexEncoding.SPEEX_VBR2, SpeexEncoding.SPEEX_VBR3, SpeexEncoding.SPEEX_VBR4, SpeexEncoding.SPEEX_VBR5, SpeexEncoding.SPEEX_VBR6, SpeexEncoding.SPEEX_VBR7, SpeexEncoding.SPEEX_VBR8, SpeexEncoding.SPEEX_VBR9, SpeexEncoding.SPEEX_VBR10, AudioFormat.Encoding.PCM_SIGNED};
        return var1;
    }

    @Override
    public AudioFormat.Encoding[] getTargetEncodings(AudioFormat var1) {
        if (var1.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
            AudioFormat.Encoding[] var2 = new AudioFormat.Encoding[]{SpeexEncoding.SPEEX_Q0, SpeexEncoding.SPEEX_Q1, SpeexEncoding.SPEEX_Q2, SpeexEncoding.SPEEX_Q3, SpeexEncoding.SPEEX_Q4, SpeexEncoding.SPEEX_Q5, SpeexEncoding.SPEEX_Q6, SpeexEncoding.SPEEX_Q7, SpeexEncoding.SPEEX_Q8, SpeexEncoding.SPEEX_Q9, SpeexEncoding.SPEEX_Q10, SpeexEncoding.SPEEX_VBR0, SpeexEncoding.SPEEX_VBR1, SpeexEncoding.SPEEX_VBR2, SpeexEncoding.SPEEX_VBR3, SpeexEncoding.SPEEX_VBR4, SpeexEncoding.SPEEX_VBR5, SpeexEncoding.SPEEX_VBR6, SpeexEncoding.SPEEX_VBR7, SpeexEncoding.SPEEX_VBR8, SpeexEncoding.SPEEX_VBR9, SpeexEncoding.SPEEX_VBR10};
            return var2;
        }
        if (var1.getEncoding() instanceof SpeexEncoding) {
            AudioFormat.Encoding[] var2 = new AudioFormat.Encoding[]{AudioFormat.Encoding.PCM_SIGNED};
            return var2;
        }
        AudioFormat.Encoding[] var2 = new AudioFormat.Encoding[]{};
        return var2;
    }

    @Override
    public AudioFormat[] getTargetFormats(AudioFormat.Encoding var1, AudioFormat var2) {
        if (var2.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && var1 instanceof SpeexEncoding) {
            if (var2.getChannels() <= 2 && var2.getChannels() > 0 && !var2.isBigEndian()) {
                AudioFormat[] var3 = new AudioFormat[]{new AudioFormat(var1, var2.getSampleRate(), -1, var2.getChannels(), -1, -1.0f, false)};
                return var3;
            }
            AudioFormat[] var3 = new AudioFormat[]{};
            return var3;
        }
        if (var2.getEncoding() instanceof SpeexEncoding && var1.equals(AudioFormat.Encoding.PCM_SIGNED)) {
            AudioFormat[] var3 = new AudioFormat[]{new AudioFormat(var2.getSampleRate(), 16, var2.getChannels(), true, false)};
            return var3;
        }
        AudioFormat[] var3 = new AudioFormat[]{};
        return var3;
    }

    @Override
    public AudioInputStream getAudioInputStream(AudioFormat.Encoding var1, AudioInputStream var2) {
        if (this.isConversionSupported(var1, var2.getFormat())) {
            AudioFormat[] var3 = this.getTargetFormats(var1, var2.getFormat());
            if (var3 != null && var3.length > 0) {
                AudioFormat var5;
                AudioFormat var4 = var2.getFormat();
                if (var4.equals(var5 = var3[0])) {
                    return var2;
                }
                if (var4.getEncoding() instanceof SpeexEncoding && var5.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
                    return new Speex2PcmAudioInputStream(var2, var5, -1L);
                }
                if (var4.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && var5.getEncoding() instanceof SpeexEncoding) {
                    return new Pcm2SpeexAudioInputStream(var2, var5, -1L);
                }
                throw new IllegalArgumentException("unable to convert " + var4.toString() + " to " + var5.toString());
            }
            throw new IllegalArgumentException("target format not found");
        }
        throw new IllegalArgumentException("conversion not supported");
    }

    @Override
    public AudioInputStream getAudioInputStream(AudioFormat var1, AudioInputStream var2) {
        if (this.isConversionSupported(var1, var2.getFormat())) {
            AudioFormat[] var3 = this.getTargetFormats(var1.getEncoding(), var2.getFormat());
            if (var3 != null && var3.length > 0) {
                AudioFormat var4 = var2.getFormat();
                if (var4.equals(var1)) {
                    return var2;
                }
                if (var4.getEncoding() instanceof SpeexEncoding && var1.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
                    return new Speex2PcmAudioInputStream(var2, var1, -1L);
                }
                if (var4.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) && var1.getEncoding() instanceof SpeexEncoding) {
                    return new Pcm2SpeexAudioInputStream(var2, var1, -1L);
                }
                throw new IllegalArgumentException("unable to convert " + var4.toString() + " to " + var1.toString());
            }
            throw new IllegalArgumentException("target format not found");
        }
        throw new IllegalArgumentException("conversion not supported");
    }
}

