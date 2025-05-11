/*
 * Decompiled with CFR 0.152.
 */
package org.xiph.speex.spi;

import javax.sound.sampled.AudioFileFormat;

public class SpeexFileFormatType
extends AudioFileFormat.Type {
    public static final AudioFileFormat.Type SPEEX = new SpeexFileFormatType("SPEEX", "spx");

    public SpeexFileFormatType(String var1, String var2) {
        super(var1, var2);
    }
}

