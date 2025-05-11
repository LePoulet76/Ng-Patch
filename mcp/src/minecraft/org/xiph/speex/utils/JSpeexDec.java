/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.utils;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import org.xiph.speex.AudioFileWriter;
import org.xiph.speex.NbEncoder;
import org.xiph.speex.OggCrc;
import org.xiph.speex.PcmWaveWriter;
import org.xiph.speex.RawWriter;
import org.xiph.speex.SbEncoder;
import org.xiph.speex.SpeexDecoder;

public class JSpeexDec {
    public static final String VERSION = "Java Speex Command Line Decoder v0.9.7 ($Revision: 1.4 $)";
    public static final String COPYRIGHT = "Copyright (C) 2002-2004 Wimba S.A.";
    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARN = 2;
    public static final int ERROR = 3;
    protected int printlevel = 1;
    public static final int FILE_FORMAT_RAW = 0;
    public static final int FILE_FORMAT_OGG = 1;
    public static final int FILE_FORMAT_WAVE = 2;
    protected int srcFormat = 1;
    protected int destFormat = 2;
    protected static Random random = new Random();
    protected SpeexDecoder speexDecoder;
    protected boolean enhanced = true;
    private int mode = 0;
    private int quality = 8;
    private int nframes = 1;
    private int sampleRate = -1;
    private float vbr_quality = -1.0f;
    private boolean vbr = false;
    private int channels = 1;
    private int loss = 0;
    protected String srcFile;
    protected String destFile;

    public static void main(String[] var0) throws IOException {
        JSpeexDec var1 = new JSpeexDec();
        if (var1.parseArgs(var0)) {
            var1.decode();
        }
    }

    public boolean parseArgs(String[] var1) {
        if (var1.length < 2) {
            if (var1.length == 1 && (var1[0].equals("-v") || var1[0].equals("--version"))) {
                JSpeexDec.version();
                return false;
            }
            JSpeexDec.usage();
            return false;
        }
        this.srcFile = var1[var1.length - 2];
        this.destFile = var1[var1.length - 1];
        this.srcFormat = this.srcFile.toLowerCase().endsWith(".spx") ? 1 : (this.srcFile.toLowerCase().endsWith(".wav") ? 2 : 0);
        this.destFormat = this.destFile.toLowerCase().endsWith(".wav") ? 2 : 0;
        for (int var2 = 0; var2 < var1.length - 2; ++var2) {
            if (!var1[var2].equalsIgnoreCase("-h") && !var1[var2].equalsIgnoreCase("--help")) {
                if (!var1[var2].equalsIgnoreCase("-v") && !var1[var2].equalsIgnoreCase("--version")) {
                    if (var1[var2].equalsIgnoreCase("--verbose")) {
                        this.printlevel = 0;
                        continue;
                    }
                    if (var1[var2].equalsIgnoreCase("--quiet")) {
                        this.printlevel = 2;
                        continue;
                    }
                    if (var1[var2].equalsIgnoreCase("--enh")) {
                        this.enhanced = true;
                        continue;
                    }
                    if (var1[var2].equalsIgnoreCase("--no-enh")) {
                        this.enhanced = false;
                        continue;
                    }
                    if (var1[var2].equalsIgnoreCase("--packet-loss")) {
                        try {
                            this.loss = Integer.parseInt(var1[++var2]);
                            continue;
                        }
                        catch (NumberFormatException var6) {
                            JSpeexDec.usage();
                            return false;
                        }
                    }
                    if (!(var1[var2].equalsIgnoreCase("-n") || var1[var2].equalsIgnoreCase("-nb") || var1[var2].equalsIgnoreCase("--narrowband"))) {
                        if (!(var1[var2].equalsIgnoreCase("-w") || var1[var2].equalsIgnoreCase("-wb") || var1[var2].equalsIgnoreCase("--wideband"))) {
                            if (!(var1[var2].equalsIgnoreCase("-u") || var1[var2].equalsIgnoreCase("-uwb") || var1[var2].equalsIgnoreCase("--ultra-wideband"))) {
                                if (!var1[var2].equalsIgnoreCase("-q") && !var1[var2].equalsIgnoreCase("--quality")) {
                                    if (var1[var2].equalsIgnoreCase("--nframes")) {
                                        try {
                                            this.nframes = Integer.parseInt(var1[++var2]);
                                            continue;
                                        }
                                        catch (NumberFormatException var4) {
                                            JSpeexDec.usage();
                                            return false;
                                        }
                                    }
                                    if (var1[var2].equalsIgnoreCase("--vbr")) {
                                        this.vbr = true;
                                        continue;
                                    }
                                    if (!var1[var2].equalsIgnoreCase("--stereo")) {
                                        JSpeexDec.usage();
                                        return false;
                                    }
                                    this.channels = 2;
                                    continue;
                                }
                                try {
                                    this.vbr_quality = Float.parseFloat(var1[++var2]);
                                    this.quality = (int)this.vbr_quality;
                                    continue;
                                }
                                catch (NumberFormatException var5) {
                                    JSpeexDec.usage();
                                    return false;
                                }
                            }
                            this.mode = 2;
                            continue;
                        }
                        this.mode = 1;
                        continue;
                    }
                    this.mode = 0;
                    continue;
                }
                JSpeexDec.version();
                return false;
            }
            JSpeexDec.usage();
            return false;
        }
        if (this.sampleRate < 0) {
            switch (this.mode) {
                case 0: {
                    this.sampleRate = 8000;
                    break;
                }
                case 1: {
                    this.sampleRate = 16000;
                    break;
                }
                case 2: {
                    this.sampleRate = 32000;
                    break;
                }
                default: {
                    this.sampleRate = 8000;
                }
            }
        }
        return true;
    }

    public static void usage() {
        JSpeexDec.version();
        System.out.println("Usage: JSpeexDec [options] input_file output_file");
        System.out.println("Where:");
        System.out.println("  input_file can be:");
        System.out.println("    filename.spx  an Ogg Speex file");
        System.out.println("    filename.wav  a Wave Speex file (beta!!!)");
        System.out.println("    filename.*    a raw Speex file");
        System.out.println("  output_file can be:");
        System.out.println("    filename.wav  a PCM wav file");
        System.out.println("    filename.*    a raw PCM file (any extension other than .wav)");
        System.out.println("Options: -h, --help     This help");
        System.out.println("         -v, --version    Version information");
        System.out.println("         --verbose        Print detailed information");
        System.out.println("         --quiet          Print minimal information");
        System.out.println("         --enh            Enable perceptual enhancement (default)");
        System.out.println("         --no-enh         Disable perceptual enhancement");
        System.out.println("         --packet-loss n  Simulate n % random packet loss");
        System.out.println("         if the input file is raw Speex (not Ogg Speex)");
        System.out.println("         -n, -nb          Narrowband (8kHz)");
        System.out.println("         -w, -wb          Wideband (16kHz)");
        System.out.println("         -u, -uwb         Ultra-Wideband (32kHz)");
        System.out.println("         --quality n      Encoding quality (0-10) default 8");
        System.out.println("         --nframes n      Number of frames per Ogg packet, default 1");
        System.out.println("         --vbr            Enable varible bit-rate (VBR)");
        System.out.println("         --stereo         Consider input as stereo");
        System.out.println("More information is available from: http://jspeex.sourceforge.net/");
        System.out.println("This code is a Java port of the Speex codec: http://www.speex.org/");
    }

    public static void version() {
        System.out.println(VERSION);
        System.out.println("using Java Speex Decoder v0.9.7 ($Revision: 1.4 $)");
        System.out.println(COPYRIGHT);
    }

    public void decode() throws IOException {
        this.decode(new File(this.srcFile), new File(this.destFile));
    }

    public void decode(File var1, File var2) throws IOException {
        byte[] var3 = new byte[2048];
        byte[] var4 = new byte[65536];
        byte[] var5 = new byte[176400];
        boolean var15 = false;
        boolean var16 = false;
        int var17 = 0;
        boolean var18 = false;
        int var19 = 0;
        if (this.printlevel <= 1) {
            JSpeexDec.version();
        }
        if (this.printlevel <= 0) {
            System.out.println("");
        }
        if (this.printlevel <= 0) {
            System.out.println("Input File: " + var1);
        }
        this.speexDecoder = new SpeexDecoder();
        DataInputStream var20 = new DataInputStream(new FileInputStream(var1));
        AudioFileWriter var21 = null;
        try {
            while (true) {
                int var29;
                int var24;
                if (this.srcFormat != 1) {
                    if (var19 == 0) {
                        if (this.srcFormat == 2) {
                            var20.readFully(var3, 0, 12);
                            if (!"RIFF".equals(new String(var3, 0, 4)) && !"WAVE".equals(new String(var3, 8, 4))) {
                                System.err.println("Not a WAVE file");
                                return;
                            }
                            var20.readFully(var3, 0, 8);
                            String var30 = new String(var3, 0, 4);
                            int var25 = JSpeexDec.readInt(var3, 4);
                            while (!var30.equals("data")) {
                                var20.readFully(var3, 0, var25);
                                if (var30.equals("fmt ")) {
                                    if (JSpeexDec.readShort(var3, 0) != -24311) {
                                        System.err.println("Not a Wave Speex file");
                                        return;
                                    }
                                    this.channels = JSpeexDec.readShort(var3, 2);
                                    this.sampleRate = JSpeexDec.readInt(var3, 4);
                                    var17 = JSpeexDec.readShort(var3, 12);
                                    if (JSpeexDec.readShort(var3, 16) < 82) {
                                        System.err.println("Possibly corrupt Speex Wave file.");
                                        return;
                                    }
                                    this.readSpeexHeader(var3, 20, 80);
                                    if (this.printlevel <= 0) {
                                        System.out.println("File Format: Wave Speex");
                                        System.out.println("Sample Rate: " + this.sampleRate);
                                        System.out.println("Channels: " + this.channels);
                                        System.out.println("Encoder mode: " + (this.mode == 0 ? "Narrowband" : (this.mode == 1 ? "Wideband" : "UltraWideband")));
                                        System.out.println("Frames per packet: " + this.nframes);
                                    }
                                }
                                var20.readFully(var3, 0, 8);
                                var30 = new String(var3, 0, 4);
                                var25 = JSpeexDec.readInt(var3, 4);
                            }
                            if (this.printlevel <= 0) {
                                System.out.println("Data size: " + var25);
                            }
                        } else {
                            if (this.printlevel <= 0) {
                                System.out.println("File Format: Raw Speex");
                                System.out.println("Sample Rate: " + this.sampleRate);
                                System.out.println("Channels: " + this.channels);
                                System.out.println("Encoder mode: " + (this.mode == 0 ? "Narrowband" : (this.mode == 1 ? "Wideband" : "UltraWideband")));
                                System.out.println("Frames per packet: " + this.nframes);
                            }
                            this.speexDecoder.init(this.mode, this.sampleRate, this.channels, this.enhanced);
                            if (!this.vbr) {
                                switch (this.mode) {
                                    case 0: {
                                        var17 = NbEncoder.NB_FRAME_SIZE[NbEncoder.NB_QUALITY_MAP[this.quality]];
                                        break;
                                    }
                                    case 1: {
                                        var17 = SbEncoder.NB_FRAME_SIZE[SbEncoder.NB_QUALITY_MAP[this.quality]];
                                        var17 += SbEncoder.SB_FRAME_SIZE[SbEncoder.WB_QUALITY_MAP[this.quality]];
                                        break;
                                    }
                                    case 2: {
                                        var17 = SbEncoder.NB_FRAME_SIZE[SbEncoder.NB_QUALITY_MAP[this.quality]];
                                        var17 += SbEncoder.SB_FRAME_SIZE[SbEncoder.WB_QUALITY_MAP[this.quality]];
                                        var17 += SbEncoder.SB_FRAME_SIZE[SbEncoder.UWB_QUALITY_MAP[this.quality]];
                                        break;
                                    }
                                    default: {
                                        throw new IOException("Illegal mode encoundered.");
                                    }
                                }
                                var17 = var17 + 7 >> 3;
                            } else {
                                var17 = 0;
                            }
                        }
                        if (this.destFormat == 2) {
                            var21 = new PcmWaveWriter(this.sampleRate, this.channels);
                            if (this.printlevel <= 0) {
                                System.out.println("");
                                System.out.println("Output File: " + var2);
                                System.out.println("File Format: PCM Wave");
                                System.out.println("Perceptual Enhancement: " + this.enhanced);
                            }
                        } else {
                            var21 = new RawWriter();
                            if (this.printlevel <= 0) {
                                System.out.println("");
                                System.out.println("Output File: " + var2);
                                System.out.println("File Format: Raw Audio");
                                System.out.println("Perceptual Enhancement: " + this.enhanced);
                            }
                        }
                        ((AudioFileWriter)var21).open(var2);
                        ((AudioFileWriter)var21).writeHeader(null);
                        ++var19;
                        continue;
                    }
                    var20.readFully(var4, 0, var17);
                    if (this.loss > 0 && random.nextInt(100) < this.loss) {
                        this.speexDecoder.processData(null, 0, var17);
                        for (var24 = 1; var24 < this.nframes; ++var24) {
                            this.speexDecoder.processData(true);
                        }
                    } else {
                        this.speexDecoder.processData(var4, 0, var17);
                        for (var24 = 1; var24 < this.nframes; ++var24) {
                            this.speexDecoder.processData(false);
                        }
                    }
                    if ((var29 = this.speexDecoder.getProcessedData(var5, 0)) > 0) {
                        ((AudioFileWriter)var21).writePacket(var5, 0, var29);
                    }
                    ++var19;
                    continue;
                }
                var20.readFully(var3, 0, 27);
                int var22 = JSpeexDec.readInt(var3, 22);
                var3[22] = 0;
                var3[23] = 0;
                var3[24] = 0;
                var3[25] = 0;
                int var23 = OggCrc.checksum(0, var3, 0, 27);
                if (!"OggS".equals(new String(var3, 0, 4))) {
                    System.err.println("missing ogg id!");
                    return;
                }
                int var27 = var3[26] & 0xFF;
                var20.readFully(var3, 27, var27);
                var23 = OggCrc.checksum(var23, var3, 27, var27);
                for (int var28 = 0; var28 < var27; ++var28) {
                    var17 = var3[27 + var28] & 0xFF;
                    if (var17 == 255) {
                        System.err.println("sorry, don't handle 255 sizes!");
                        return;
                    }
                    var20.readFully(var4, 0, var17);
                    var23 = OggCrc.checksum(var23, var4, 0, var17);
                    if (var19 == 0) {
                        if (this.readSpeexHeader(var4, 0, var17)) {
                            if (this.printlevel <= 0) {
                                System.out.println("File Format: Ogg Speex");
                                System.out.println("Sample Rate: " + this.sampleRate);
                                System.out.println("Channels: " + this.channels);
                                System.out.println("Encoder mode: " + (this.mode == 0 ? "Narrowband" : (this.mode == 1 ? "Wideband" : "UltraWideband")));
                                System.out.println("Frames per packet: " + this.nframes);
                            }
                            if (this.destFormat == 2) {
                                var21 = new PcmWaveWriter(this.speexDecoder.getSampleRate(), this.speexDecoder.getChannels());
                                if (this.printlevel <= 0) {
                                    System.out.println("");
                                    System.out.println("Output File: " + var2);
                                    System.out.println("File Format: PCM Wave");
                                    System.out.println("Perceptual Enhancement: " + this.enhanced);
                                }
                            } else {
                                var21 = new RawWriter();
                                if (this.printlevel <= 0) {
                                    System.out.println("");
                                    System.out.println("Output File: " + var2);
                                    System.out.println("File Format: Raw Audio");
                                    System.out.println("Perceptual Enhancement: " + this.enhanced);
                                }
                            }
                            ((AudioFileWriter)var21).open(var2);
                            ((AudioFileWriter)var21).writeHeader(null);
                            ++var19;
                            continue;
                        }
                        var19 = 0;
                        continue;
                    }
                    if (var19 == 1) {
                        ++var19;
                        continue;
                    }
                    if (this.loss > 0 && random.nextInt(100) < this.loss) {
                        this.speexDecoder.processData(null, 0, var17);
                        for (var24 = 1; var24 < this.nframes; ++var24) {
                            this.speexDecoder.processData(true);
                        }
                    } else {
                        this.speexDecoder.processData(var4, 0, var17);
                        for (var24 = 1; var24 < this.nframes; ++var24) {
                            this.speexDecoder.processData(false);
                        }
                    }
                    if ((var29 = this.speexDecoder.getProcessedData(var5, 0)) > 0) {
                        ((AudioFileWriter)var21).writePacket(var5, 0, var29);
                    }
                    ++var19;
                }
                if (var23 != var22) break;
            }
            throw new IOException("Ogg CheckSums do not match");
        }
        catch (EOFException var26) {
            ((AudioFileWriter)var21).close();
            return;
        }
    }

    private boolean readSpeexHeader(byte[] var1, int var2, int var3) {
        if (var3 != 80) {
            System.out.println("Oooops");
            return false;
        }
        if (!"Speex   ".equals(new String(var1, var2, 8))) {
            return false;
        }
        this.mode = var1[40 + var2] & 0xFF;
        this.sampleRate = JSpeexDec.readInt(var1, var2 + 36);
        this.channels = JSpeexDec.readInt(var1, var2 + 48);
        this.nframes = JSpeexDec.readInt(var1, var2 + 64);
        return this.speexDecoder.init(this.mode, this.sampleRate, this.channels, this.enhanced);
    }

    protected static int readInt(byte[] var0, int var1) {
        return var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8 | (var0[var1 + 2] & 0xFF) << 16 | var0[var1 + 3] << 24;
    }

    protected static int readShort(byte[] var0, int var1) {
        return var0[var1] & 0xFF | var0[var1 + 1] << 8;
    }
}

