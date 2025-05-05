package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIDecoder$SubbandLayer1;

class LayerIDecoder$SubbandLayer1IntensityStereo extends LayerIDecoder$SubbandLayer1
{
    protected float channel2_scalefactor;

    public LayerIDecoder$SubbandLayer1IntensityStereo(int subbandnumber)
    {
        super(subbandnumber);
    }

    public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException
    {
        super.read_allocation(stream, header, crc);
    }

    public void read_scalefactor(Bitstream stream, Header header)
    {
        if (this.allocation != 0)
        {
            this.scalefactor = scalefactors[stream.get_bits(6)];
            this.channel2_scalefactor = scalefactors[stream.get_bits(6)];
        }
    }

    public boolean read_sampledata(Bitstream stream)
    {
        return super.read_sampledata(stream);
    }

    public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2)
    {
        if (this.allocation != 0)
        {
            this.sample = this.sample * this.factor + this.offset;
            float sample2;

            if (channels == 0)
            {
                sample2 = this.sample * this.scalefactor;
                float sample21 = this.sample * this.channel2_scalefactor;
                filter1.input_sample(sample2, this.subbandnumber);
                filter2.input_sample(sample21, this.subbandnumber);
            }
            else if (channels == 1)
            {
                sample2 = this.sample * this.scalefactor;
                filter1.input_sample(sample2, this.subbandnumber);
            }
            else
            {
                sample2 = this.sample * this.channel2_scalefactor;
                filter1.input_sample(sample2, this.subbandnumber);
            }
        }

        return true;
    }
}
