/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.debug;

import net.ilexiconn.nationsgui.forge.client.voices.debug.MovingAverage;

public class Statistics {
    MovingAverage decodedAverage = new MovingAverage(8);
    MovingAverage encodedAverage = new MovingAverage(8);
    int encodedSum;
    int decodedSum;

    public int getDecodedDataReceived() {
        return this.decodedSum;
    }

    public int getDecodedAverageDataReceived() {
        return this.decodedAverage.getAverage().intValue();
    }

    public int getEncodedDataReceived() {
        return this.encodedSum;
    }

    public int getEncodedAverageDataReceived() {
        return this.encodedAverage.getAverage().intValue();
    }

    public void addDecodedSamples(int size) {
        this.decodedSum += size;
        this.decodedAverage.add(size);
    }

    public void addEncodedSamples(int size) {
        this.encodedSum += size;
        this.encodedAverage.add(size);
    }
}

