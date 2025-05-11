/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.utils;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.xiph.speex.AudioFileWriter;
import org.xiph.speex.OggSpeexWriter;
import org.xiph.speex.PcmWaveWriter;
import org.xiph.speex.RawWriter;
import org.xiph.speex.SpeexEncoder;

public class JSpeexEnc {
    public static final String VERSION = "Java Speex Command Line Encoder v0.9.7 ($Revision: 1.5 $)";
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
    protected int mode = -1;
    protected int quality = 8;
    protected int complexity = 3;
    protected int nframes = 1;
    protected int bitrate = -1;
    protected int sampleRate = -1;
    protected int channels = 1;
    protected float vbr_quality = -1.0f;
    protected boolean vbr = false;
    protected boolean vad = false;
    protected boolean dtx = false;
    protected String srcFile;
    protected String destFile;

    public static void main(String[] var0) throws IOException {
        JSpeexEnc var1 = new JSpeexEnc();
        if (var1.parseArgs(var0)) {
            var1.encode();
        }
    }

    public boolean parseArgs(String[] var1) {
        if (var1.length < 2) {
            if (var1.length == 1 && (var1[0].equalsIgnoreCase("-v") || var1[0].equalsIgnoreCase("--version"))) {
                JSpeexEnc.version();
                return false;
            }
            JSpeexEnc.usage();
            return false;
        }
        this.srcFile = var1[var1.length - 2];
        this.destFile = var1[var1.length - 1];
        this.srcFormat = this.srcFile.toLowerCase().endsWith(".wav") ? 2 : 0;
        this.destFormat = this.destFile.toLowerCase().endsWith(".spx") ? 1 : (this.destFile.toLowerCase().endsWith(".wav") ? 2 : 0);
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
                    if (!(var1[var2].equalsIgnoreCase("-n") || var1[var2].equalsIgnoreCase("-nb") || var1[var2].equalsIgnoreCase("--narrowband"))) {
                        if (!(var1[var2].equalsIgnoreCase("-w") || var1[var2].equalsIgnoreCase("-wb") || var1[var2].equalsIgnoreCase("--wideband"))) {
                            if (!(var1[var2].equalsIgnoreCase("-u") || var1[var2].equalsIgnoreCase("-uwb") || var1[var2].equalsIgnoreCase("--ultra-wideband"))) {
                                if (!var1[var2].equalsIgnoreCase("-q") && !var1[var2].equalsIgnoreCase("--quality")) {
                                    if (var1[var2].equalsIgnoreCase("--complexity")) {
                                        try {
                                            this.complexity = Integer.parseInt(var1[++var2]);
                                            continue;
                                        }
                                        catch (NumberFormatException var6) {
                                            JSpeexEnc.usage();
                                            return false;
                                        }
                                    }
                                    if (var1[var2].equalsIgnoreCase("--nframes")) {
                                        try {
                                            this.nframes = Integer.parseInt(var1[++var2]);
                                            continue;
                                        }
                                        catch (NumberFormatException var5) {
                                            JSpeexEnc.usage();
                                            return false;
                                        }
                                    }
                                    if (var1[var2].equalsIgnoreCase("--vbr")) {
                                        this.vbr = true;
                                        continue;
                                    }
                                    if (var1[var2].equalsIgnoreCase("--vad")) {
                                        this.vad = true;
                                        continue;
                                    }
                                    if (var1[var2].equalsIgnoreCase("--dtx")) {
                                        this.dtx = true;
                                        continue;
                                    }
                                    if (var1[var2].equalsIgnoreCase("--rate")) {
                                        try {
                                            this.sampleRate = Integer.parseInt(var1[++var2]);
                                            continue;
                                        }
                                        catch (NumberFormatException var4) {
                                            JSpeexEnc.usage();
                                            return false;
                                        }
                                    }
                                    if (!var1[var2].equalsIgnoreCase("--stereo")) {
                                        JSpeexEnc.usage();
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
                                catch (NumberFormatException var7) {
                                    JSpeexEnc.usage();
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
                JSpeexEnc.version();
                return false;
            }
            JSpeexEnc.usage();
            return false;
        }
        return true;
    }

    public static void usage() {
        JSpeexEnc.version();
        System.out.println("");
        System.out.println("Usage: JSpeexEnc [options] input_file output_file");
        System.out.println("Where:");
        System.out.println("  input_file can be:");
        System.out.println("    filename.wav  a PCM wav file");
        System.out.println("    filename.*    a raw PCM file (any extension other than .wav)");
        System.out.println("  output_file can be:");
        System.out.println("    filename.spx  an Ogg Speex file");
        System.out.println("    filename.wav  a Wave Speex file (beta!!!)");
        System.out.println("    filename.*    a raw Speex file");
        System.out.println("Options: -h, --help     This help");
        System.out.println("         -v, --version  Version information");
        System.out.println("         --verbose      Print detailed information");
        System.out.println("         --quiet        Print minimal information");
        System.out.println("         -n, -nb        Consider input as Narrowband (8kHz)");
        System.out.println("         -w, -wb        Consider input as Wideband (16kHz)");
        System.out.println("         -u, -uwb       Consider input as Ultra-Wideband (32kHz)");
        System.out.println("         --quality n    Encoding quality (0-10) default 8");
        System.out.println("         --complexity n Encoding complexity (0-10) default 3");
        System.out.println("         --nframes n    Number of frames per Ogg packet, default 1");
        System.out.println("         --vbr          Enable varible bit-rate (VBR)");
        System.out.println("         --vad          Enable voice activity detection (VAD)");
        System.out.println("         --dtx          Enable file based discontinuous transmission (DTX)");
        System.out.println("         if the input file is raw PCM (not a Wave file)");
        System.out.println("         --rate n       Sampling rate for raw input");
        System.out.println("         --stereo       Consider input as stereo");
        System.out.println("More information is available from: http://jspeex.sourceforge.net/");
        System.out.println("This code is a Java port of the Speex codec: http://www.speex.org/");
    }

    public static void version() {
        System.out.println(VERSION);
        System.out.println("using Java Speex Encoder v0.9.7 ($Revision: 1.6 $)");
        System.out.println(COPYRIGHT);
    }

    public void encode() throws IOException {
        this.encode(new File(this.srcFile), new File(this.destFile));
    }

    public void encode(File var1, File var2) throws IOException {
        AudioFileWriter var17;
        byte[] var3 = new byte[2560];
        if (this.printlevel <= 1) {
            JSpeexEnc.version();
        }
        if (this.printlevel <= 0) {
            System.out.println("");
        }
        if (this.printlevel <= 0) {
            System.out.println("Input File: " + var1);
        }
        DataInputStream var10 = new DataInputStream(new FileInputStream(var1));
        if (this.srcFormat == 2) {
            var10.readFully(var3, 0, 12);
            if (!"RIFF".equals(new String(var3, 0, 4)) && !"WAVE".equals(new String(var3, 8, 4))) {
                System.err.println("Not a WAVE file");
                return;
            }
            var10.readFully(var3, 0, 8);
            String var11 = new String(var3, 0, 4);
            int var12 = JSpeexEnc.readInt(var3, 4);
            while (!var11.equals("data")) {
                var10.readFully(var3, 0, var12);
                if (var11.equals("fmt ")) {
                    if (JSpeexEnc.readShort(var3, 0) != 1) {
                        System.err.println("Not a PCM file");
                        return;
                    }
                    this.channels = JSpeexEnc.readShort(var3, 2);
                    this.sampleRate = JSpeexEnc.readInt(var3, 4);
                    if (JSpeexEnc.readShort(var3, 14) != 16) {
                        System.err.println("Not a 16 bit file " + JSpeexEnc.readShort(var3, 18));
                        return;
                    }
                    if (this.printlevel <= 0) {
                        System.out.println("File Format: PCM wave");
                        System.out.println("Sample Rate: " + this.sampleRate);
                        System.out.println("Channels: " + this.channels);
                    }
                }
                var10.readFully(var3, 0, 8);
                var11 = new String(var3, 0, 4);
                var12 = JSpeexEnc.readInt(var3, 4);
            }
            if (this.printlevel <= 0) {
                System.out.println("Data size: " + var12);
            }
        } else {
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
            if (this.printlevel <= 0) {
                System.out.println("File format: Raw audio");
                System.out.println("Sample rate: " + this.sampleRate);
                System.out.println("Channels: " + this.channels);
                System.out.println("Data size: " + var1.length());
            }
        }
        if (this.mode < 0) {
            if (this.sampleRate < 100) {
                this.sampleRate *= 1000;
            }
            this.mode = this.sampleRate < 12000 ? 0 : (this.sampleRate < 24000 ? 1 : 2);
        }
        SpeexEncoder var16 = new SpeexEncoder();
        var16.init(this.mode, this.quality, this.sampleRate, this.channels);
        if (this.complexity > 0) {
            var16.getEncoder().setComplexity(this.complexity);
        }
        if (this.bitrate > 0) {
            var16.getEncoder().setBitRate(this.bitrate);
        }
        if (this.vbr) {
            var16.getEncoder().setVbr(this.vbr);
            if (this.vbr_quality > 0.0f) {
                var16.getEncoder().setVbrQuality(this.vbr_quality);
            }
        }
        if (this.vad) {
            var16.getEncoder().setVad(this.vad);
        }
        if (this.dtx) {
            var16.getEncoder().setDtx(this.dtx);
        }
        if (this.printlevel <= 0) {
            System.out.println("");
            System.out.println("Output File: " + var2);
            System.out.println("File format: Ogg Speex");
            System.out.println("Encoder mode: " + (this.mode == 0 ? "Narrowband" : (this.mode == 1 ? "Wideband" : "UltraWideband")));
            System.out.println("Quality: " + (this.vbr ? this.vbr_quality : (float)this.quality));
            System.out.println("Complexity: " + this.complexity);
            System.out.println("Frames per packet: " + this.nframes);
            System.out.println("Varible bitrate: " + this.vbr);
            System.out.println("Voice activity detection: " + this.vad);
            System.out.println("Discontinouous Transmission: " + this.dtx);
        }
        if (this.destFormat == 1) {
            var17 = new OggSpeexWriter(this.mode, this.sampleRate, this.channels, this.nframes, this.vbr);
        } else if (this.destFormat == 2) {
            this.nframes = PcmWaveWriter.WAVE_FRAME_SIZES[this.mode - 1][this.channels - 1][this.quality];
            var17 = new PcmWaveWriter(this.mode, this.quality, this.sampleRate, this.channels, this.nframes, this.vbr);
        } else {
            var17 = new RawWriter();
        }
        ((AudioFileWriter)var17).open(var2);
        ((AudioFileWriter)var17).writeHeader("Encoded with: Java Speex Command Line Encoder v0.9.7 ($Revision: 1.5 $)");
        int var13 = 2 * this.channels * var16.getFrameSize();
        try {
            while (true) {
                int var14;
                var10.readFully(var3, 0, this.nframes * var13);
                for (var14 = 0; var14 < this.nframes; ++var14) {
                    var16.processData(var3, var14 * var13, var13);
                }
                var14 = var16.getProcessedData(var3, 0);
                if (var14 <= 0) continue;
                ((AudioFileWriter)var17).writePacket(var3, 0, var14);
            }
        }
        catch (EOFException var15) {
            ((AudioFileWriter)var17).close();
            var10.close();
            return;
        }
    }

    protected static int readInt(byte[] var0, int var1) {
        return var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8 | (var0[var1 + 2] & 0xFF) << 16 | var0[var1 + 3] << 24;
    }

    protected static int readShort(byte[] var0, int var1) {
        return var0[var1] & 0xFF | var0[var1 + 1] << 8;
    }
}

