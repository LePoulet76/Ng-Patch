/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.spi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;
import org.xiph.speex.OggCrc;
import org.xiph.speex.spi.SpeexEncoding;
import org.xiph.speex.spi.SpeexFileFormatType;

public class SpeexAudioFileReader
extends AudioFileReader {
    public static final int OGG_HEADERSIZE = 27;
    public static final int SPEEX_HEADERSIZE = 80;
    public static final int SEGOFFSET = 26;
    public static final String OGGID = "OggS";
    public static final String SPEEXID = "Speex   ";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AudioFileFormat getAudioFileFormat(File var1) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat var3;
        try (FileInputStream var2 = null;){
            var2 = new FileInputStream(var1);
            var3 = this.getAudioFileFormat(var2, (int)var1.length());
        }
        return var3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AudioFileFormat getAudioFileFormat(URL var1) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat var3;
        try (InputStream var2 = var1.openStream();){
            var3 = this.getAudioFileFormat(var2);
        }
        return var3;
    }

    @Override
    public AudioFileFormat getAudioFileFormat(InputStream var1) throws UnsupportedAudioFileException, IOException {
        return this.getAudioFileFormat(var1, -1);
    }

    protected AudioFileFormat getAudioFileFormat(InputStream var1, int var2) throws UnsupportedAudioFileException, IOException {
        return this.getAudioFileFormat(var1, null, var2);
    }

    protected AudioFileFormat getAudioFileFormat(InputStream var1, ByteArrayOutputStream var2, int var3) throws UnsupportedAudioFileException, IOException {
        AudioFormat var4;
        try {
            boolean var17;
            if (var1.markSupported()) {
                var1.mark(675);
            }
            boolean var5 = true;
            boolean var6 = false;
            boolean var7 = false;
            int var8 = -1;
            float var9 = -1.0f;
            byte[] var10 = new byte[128];
            boolean var11 = false;
            boolean var12 = false;
            DataInputStream var13 = new DataInputStream(var1);
            if (var2 == null) {
                var2 = new ByteArrayOutputStream(128);
            }
            var13.readFully(var10, 0, 27);
            var2.write(var10, 0, 27);
            int var14 = SpeexAudioFileReader.readInt(var10, 22);
            var10[22] = 0;
            var10[23] = 0;
            var10[24] = 0;
            var10[25] = 0;
            int var15 = OggCrc.checksum(0, var10, 0, 27);
            if (!OGGID.equals(new String(var10, 0, 4))) {
                throw new UnsupportedAudioFileException("missing ogg id!");
            }
            int var23 = var10[26] & 0xFF;
            if (var23 > 1) {
                throw new UnsupportedAudioFileException("Corrupt Speex Header: more than 1 segments");
            }
            var13.readFully(var10, 27, var23);
            var2.write(var10, 27, var23);
            var15 = OggCrc.checksum(var15, var10, 27, var23);
            int var24 = var10[27] & 0xFF;
            if (var24 != 80) {
                throw new UnsupportedAudioFileException("Corrupt Speex Header: size=" + var24);
            }
            var13.readFully(var10, 28, var24);
            var2.write(var10, 28, var24);
            var15 = OggCrc.checksum(var15, var10, 28, var24);
            if (!SPEEXID.equals(new String(var10, 28, 8))) {
                throw new UnsupportedAudioFileException("Corrupt Speex Header: missing Speex ID");
            }
            int var20 = SpeexAudioFileReader.readInt(var10, 68);
            int var21 = SpeexAudioFileReader.readInt(var10, 64);
            int var22 = SpeexAudioFileReader.readInt(var10, 76);
            int var16 = SpeexAudioFileReader.readInt(var10, 92);
            boolean bl = var17 = SpeexAudioFileReader.readInt(var10, 88) == 1;
            if (var15 != var14) {
                throw new IOException("Ogg CheckSums do not match");
            }
            if (!var17) {
                // empty if block
            }
            if (var20 >= 0 && var20 <= 2 && var16 > 0) {
                var9 = (float)var21 / ((var20 == 0 ? 160.0f : (var20 == 1 ? 320.0f : 640.0f)) * (float)var16);
            }
            var4 = new AudioFormat(SpeexEncoding.SPEEX, var21, -1, var22, var8, var9, false);
        }
        catch (UnsupportedAudioFileException var18) {
            if (var1.markSupported()) {
                var1.reset();
            }
            throw var18;
        }
        catch (IOException var19) {
            if (var1.markSupported()) {
                var1.reset();
            }
            throw new UnsupportedAudioFileException(var19.getMessage());
        }
        return new AudioFileFormat(SpeexFileFormatType.SPEEX, var4, -1);
    }

    @Override
    public AudioInputStream getAudioInputStream(File var1) throws UnsupportedAudioFileException, IOException {
        FileInputStream var2 = new FileInputStream(var1);
        try {
            return this.getAudioInputStream(var2, (int)var1.length());
        }
        catch (UnsupportedAudioFileException var4) {
            var2.close();
            throw var4;
        }
        catch (IOException var5) {
            var2.close();
            throw var5;
        }
    }

    @Override
    public AudioInputStream getAudioInputStream(URL var1) throws UnsupportedAudioFileException, IOException {
        InputStream var2 = var1.openStream();
        try {
            return this.getAudioInputStream(var2);
        }
        catch (UnsupportedAudioFileException var4) {
            var2.close();
            throw var4;
        }
        catch (IOException var5) {
            var2.close();
            throw var5;
        }
    }

    @Override
    public AudioInputStream getAudioInputStream(InputStream var1) throws UnsupportedAudioFileException, IOException {
        return this.getAudioInputStream(var1, -1);
    }

    protected AudioInputStream getAudioInputStream(InputStream var1, int var2) throws UnsupportedAudioFileException, IOException {
        ByteArrayOutputStream var3 = new ByteArrayOutputStream(128);
        AudioFileFormat var4 = this.getAudioFileFormat(var1, var3, var2);
        ByteArrayInputStream var5 = new ByteArrayInputStream(var3.toByteArray());
        SequenceInputStream var6 = new SequenceInputStream(var5, var1);
        return new AudioInputStream(var6, var4.getFormat(), var4.getFrameLength());
    }

    private static int readInt(byte[] var0, int var1) {
        return var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8 | (var0[var1 + 2] & 0xFF) << 16 | var0[var1 + 3] << 24;
    }
}

