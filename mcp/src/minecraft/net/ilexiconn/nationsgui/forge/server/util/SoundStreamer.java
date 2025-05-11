/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.util;

import java.net.HttpURLConnection;
import java.net.URL;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;

public class SoundStreamer
implements Runnable {
    protected String source;
    protected Bitstream bitstream;
    protected Decoder decoder;
    public AudioDevice audio;
    protected float volume = 1.0f;
    protected boolean looping = false;
    protected long softCloseTime = 0L;

    public SoundStreamer(String source) {
        try {
            this.source = source;
            this.audio = FactoryRegistry.systemRegistry().createAudioDevice();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.source);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            this.bitstream = new Bitstream(httpConn.getInputStream());
            this.decoder = new Decoder();
            this.audio.open(this.decoder);
            this.play(Integer.MAX_VALUE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void play(int frames) throws JavaLayerException {
        ClientProxy.STREAMER_LIST.add(this);
        int f = frames;
        while (f-- > 0) {
            if (this.softCloseTime != 0L) {
                if (System.currentTimeMillis() - this.softCloseTime >= 1000L) {
                    this.close();
                    break;
                }
                this.setVolume(this.getVolume() * (1.0f - (float)(System.currentTimeMillis() - this.softCloseTime) / 1000.0f));
            }
            if (this.decodeFrame()) continue;
        }
        if (this.audio != null) {
            this.audio.flush();
            SoundStreamer soundStreamer = this;
            synchronized (soundStreamer) {
                this.close();
            }
            if (this.isLooping()) {
                new Thread(this).run();
            }
        }
    }

    public synchronized void close() {
        ClientProxy.STREAMER_LIST.remove(this);
        if (this.audio != null) {
            this.audio.close();
            try {
                this.audio = this.isLooping() ? FactoryRegistry.systemRegistry().createAudioDevice() : null;
                if (this.bitstream != null) {
                    this.bitstream.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void forceClose() {
        ClientProxy.STREAMER_LIST.remove(this);
        if (this.audio != null) {
            this.audio.close();
            this.audio = null;
            try {
                if (this.bitstream != null) {
                    this.bitstream.close();
                }
            }
            catch (BitstreamException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void softClose() {
        this.softCloseTime = System.currentTimeMillis();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean decodeFrame() throws JavaLayerException {
        try {
            if (this.audio == null) {
                return false;
            }
            Header header = this.bitstream.readFrame();
            if (header == null) {
                return false;
            }
            SampleBuffer sampleBuffer = (SampleBuffer)this.decoder.decodeFrame(header, this.bitstream);
            SoundStreamer soundStreamer = this;
            synchronized (soundStreamer) {
                if (this.audio != null) {
                    short[] samples = sampleBuffer.getBuffer();
                    for (int sample = 0; sample < samples.length; ++sample) {
                        samples[sample] = (short)((float)samples[sample] * this.volume);
                    }
                    this.audio.write(samples, 0, sampleBuffer.getBufferLength());
                }
            }
            this.bitstream.closeFrame();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isLooping() {
        return this.looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public String getSource() {
        return this.source;
    }

    public boolean isPlaying() {
        return this.audio != null && this.audio.isOpen();
    }
}

