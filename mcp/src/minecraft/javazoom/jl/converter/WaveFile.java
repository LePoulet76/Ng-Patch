package javazoom.jl.converter;

import javazoom.jl.converter.RiffFile$RiffChunkHeader;
import javazoom.jl.converter.WaveFile$WaveFormat_Chunk;

public class WaveFile extends RiffFile
{
    public static final int MAX_WAVE_CHANNELS = 2;
    private WaveFile$WaveFormat_Chunk wave_format = new WaveFile$WaveFormat_Chunk(this);
    private RiffFile$RiffChunkHeader pcm_data = new RiffFile$RiffChunkHeader(this);
    private long pcm_data_offset = 0L;
    private int num_samples = 0;

    public WaveFile()
    {
        this.pcm_data.ckID = FourCC("data");
        this.pcm_data.ckSize = 0;
        this.num_samples = 0;
    }

    public int OpenForWrite(String Filename, int SamplingRate, short BitsPerSample, short NumChannels)
    {
        if (Filename != null && (BitsPerSample == 8 || BitsPerSample == 16) && NumChannels >= 1 && NumChannels <= 2)
        {
            this.wave_format.data.Config(SamplingRate, BitsPerSample, NumChannels);
            int retcode = this.Open(Filename, 1);

            if (retcode == 0)
            {
                byte[] theWave = new byte[] {(byte)87, (byte)65, (byte)86, (byte)69};
                retcode = this.Write(theWave, 4);

                if (retcode == 0)
                {
                    retcode = this.Write(this.wave_format.header, 8);
                    retcode = this.Write(this.wave_format.data.wFormatTag, 2);
                    retcode = this.Write(this.wave_format.data.nChannels, 2);
                    retcode = this.Write(this.wave_format.data.nSamplesPerSec, 4);
                    retcode = this.Write(this.wave_format.data.nAvgBytesPerSec, 4);
                    retcode = this.Write(this.wave_format.data.nBlockAlign, 2);
                    retcode = this.Write(this.wave_format.data.nBitsPerSample, 2);

                    if (retcode == 0)
                    {
                        this.pcm_data_offset = this.CurrentFilePosition();
                        retcode = this.Write(this.pcm_data, 8);
                    }
                }
            }

            return retcode;
        }
        else
        {
            return 4;
        }
    }

    public int WriteData(short[] data, int numData)
    {
        int extraBytes = numData * 2;
        this.pcm_data.ckSize += extraBytes;
        return super.Write(data, extraBytes);
    }

    public int Close()
    {
        int rc = 0;

        if (this.fmode == 1)
        {
            rc = this.Backpatch(this.pcm_data_offset, this.pcm_data, 8);
        }

        if (rc == 0)
        {
            rc = super.Close();
        }

        return rc;
    }

    public int SamplingRate()
    {
        return this.wave_format.data.nSamplesPerSec;
    }

    public short BitsPerSample()
    {
        return this.wave_format.data.nBitsPerSample;
    }

    public short NumChannels()
    {
        return this.wave_format.data.nChannels;
    }

    public int NumSamples()
    {
        return this.num_samples;
    }

    public int OpenForWrite(String Filename, WaveFile OtherWave)
    {
        return this.OpenForWrite(Filename, OtherWave.SamplingRate(), OtherWave.BitsPerSample(), OtherWave.NumChannels());
    }

    public long CurrentFilePosition()
    {
        return super.CurrentFilePosition();
    }
}
