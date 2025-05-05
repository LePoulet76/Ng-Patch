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

public class SoundStreamer implements Runnable
{
    protected String source;
    protected Bitstream bitstream;
    protected Decoder decoder;
    public AudioDevice audio;
    protected float volume = 1.0F;
    protected boolean looping = false;
    protected long softCloseTime = 0L;

    public SoundStreamer(String source)
    {
        try
        {
            this.source = source;
            this.audio = FactoryRegistry.systemRegistry().createAudioDevice();
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            URL e = new URL(this.source);
            HttpURLConnection httpConn = (HttpURLConnection)e.openConnection();
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            this.bitstream = new Bitstream(httpConn.getInputStream());
            this.decoder = new Decoder();
            this.audio.open(this.decoder);
            this.play(Integer.MAX_VALUE);
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
        }
    }

    public void play(int frames) throws JavaLayerException
    {
        ClientProxy.STREAMER_LIST.add(this);
        int f = frames;

        while (f-- > 0)
        {
            if (this.softCloseTime != 0L)
            {
                if (System.currentTimeMillis() - this.softCloseTime >= 1000L)
                {
                    this.close();
                    break;
                }

                this.setVolume(this.getVolume() * (1.0F - (float)(System.currentTimeMillis() - this.softCloseTime) / 1000.0F));
            }

            if (!this.decodeFrame())
            {
                break;
            }
        }

        if (this.audio != null)
        {
            this.audio.flush();

            synchronized (this)
            {
                this.close();
            }

            if (this.isLooping())
            {
                (new Thread(this)).run();
            }
        }
    }

    public synchronized void close()
    {
        ClientProxy.STREAMER_LIST.remove(this);

        if (this.audio != null)
        {
            this.audio.close();

            try
            {
                if (this.isLooping())
                {
                    this.audio = FactoryRegistry.systemRegistry().createAudioDevice();
                }
                else
                {
                    this.audio = null;
                }

                if (this.bitstream != null)
                {
                    this.bitstream.close();
                }
            }
            catch (Exception var2)
            {
                var2.printStackTrace();
            }
        }
    }

    public synchronized void forceClose()
    {
        ClientProxy.STREAMER_LIST.remove(this);

        if (this.audio != null)
        {
            this.audio.close();
            this.audio = null;

            try
            {
                if (this.bitstream != null)
                {
                    this.bitstream.close();
                }
            }
            catch (BitstreamException var2)
            {
                var2.printStackTrace();
            }
        }
    }

    public synchronized void softClose()
    {
        this.softCloseTime = System.currentTimeMillis();
    }

    protected boolean decodeFrame() throws JavaLayerException
    {
        try
        {
            if (this.audio == null)
            {
                return false;
            }
            else
            {
                Header e = this.bitstream.readFrame();

                if (e == null)
                {
                    return false;
                }
                else
                {
                    SampleBuffer sampleBuffer = (SampleBuffer)this.decoder.decodeFrame(e, this.bitstream);

                    synchronized (this)
                    {
                        if (this.audio != null)
                        {
                            short[] samples = sampleBuffer.getBuffer();

                            for (int sample = 0; sample < samples.length; ++sample)
                            {
                                samples[sample] = (short)((int)((float)samples[sample] * this.volume));
                            }

                            this.audio.write(samples, 0, sampleBuffer.getBufferLength());
                        }
                    }

                    this.bitstream.closeFrame();
                    return true;
                }
            }
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
            return false;
        }
    }

    public float getVolume()
    {
        return this.volume;
    }

    public void setVolume(float volume)
    {
        this.volume = volume;
    }

    public boolean isLooping()
    {
        return this.looping;
    }

    public void setLooping(boolean looping)
    {
        this.looping = looping;
    }

    public String getSource()
    {
        return this.source;
    }

    public boolean isPlaying()
    {
        return this.audio != null && this.audio.isOpen();
    }
}
