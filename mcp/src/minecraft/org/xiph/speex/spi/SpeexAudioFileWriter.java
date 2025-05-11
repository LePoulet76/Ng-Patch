/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.spi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.spi.AudioFileWriter;
import org.xiph.speex.spi.SpeexEncoding;
import org.xiph.speex.spi.SpeexFileFormatType;

public class SpeexAudioFileWriter
extends AudioFileWriter {
    public static final AudioFileFormat.Type[] NO_FORMAT = new AudioFileFormat.Type[0];
    public static final AudioFileFormat.Type[] SPEEX_FORMAT = new AudioFileFormat.Type[]{SpeexFileFormatType.SPEEX};

    @Override
    public AudioFileFormat.Type[] getAudioFileTypes() {
        return SPEEX_FORMAT;
    }

    @Override
    public AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream var1) {
        return var1.getFormat().getEncoding() instanceof SpeexEncoding ? SPEEX_FORMAT : NO_FORMAT;
    }

    @Override
    public int write(AudioInputStream var1, AudioFileFormat.Type var2, OutputStream var3) throws IOException {
        AudioFileFormat.Type[] var4 = this.getAudioFileTypes(var1);
        if (var4 != null && var4.length > 0) {
            return this.write(var1, var3);
        }
        throw new IllegalArgumentException("cannot write given file type");
    }

    @Override
    public int write(AudioInputStream var1, AudioFileFormat.Type var2, File var3) throws IOException {
        AudioFileFormat.Type[] var4 = this.getAudioFileTypes(var1);
        if (var4 != null && var4.length > 0) {
            FileOutputStream var5 = new FileOutputStream(var3);
            return this.write(var1, var5);
        }
        throw new IllegalArgumentException("cannot write given file type");
    }

    private int write(AudioInputStream var1, OutputStream var2) throws IOException {
        int var5;
        byte[] var3 = new byte[2048];
        int var4 = 0;
        while ((var5 = var1.read(var3, 0, 2048)) > 0) {
            var2.write(var3, 0, var5);
            var4 += var5;
        }
        var2.flush();
        var2.close();
        return var4;
    }
}

