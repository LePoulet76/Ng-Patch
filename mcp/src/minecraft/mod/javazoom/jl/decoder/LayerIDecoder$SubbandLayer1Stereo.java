package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIDecoder$SubbandLayer1;

class LayerIDecoder$SubbandLayer1Stereo extends LayerIDecoder$SubbandLayer1
{
    protected int channel2_allocation;
    protected float channel2_scalefactor;
    protected int channel2_samplelength;
    protected float channel2_sample;
    protected float channel2_factor;
    protected float channel2_offset;

    public LayerIDecoder$SubbandLayer1Stereo(int subbandnumber)
    {
        super(subbandnumber);
    }

    public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException
    {
        this.allocation = stream.get_bits(4);
        this.channel2_allocation = stream.get_bits(4);

        if (crc != null)
        {
            crc.add_bits(this.allocation, 4);
            crc.add_bits(this.channel2_allocation, 4);
        }

        if (this.allocation != 0)
        {
            this.samplelength = this.allocation + 1;
            this.factor = table_factor[this.allocation];
            this.offset = table_offset[this.allocation];
        }

        if (this.channel2_allocation != 0)
        {
            this.channel2_samplelength = this.channel2_allocation + 1;
            this.channel2_factor = table_factor[this.channel2_allocation];
            this.channel2_offset = table_offset[this.channel2_allocation];
        }
    }

    public void read_scalefactor(Bitstream stream, Header header)
    {
        if (this.allocation != 0)
        {
            this.scalefactor = scalefactors[stream.get_bits(6)];
        }

        if (this.channel2_allocation != 0)
        {
            this.channel2_scalefactor = scalefactors[stream.get_bits(6)];
        }
    }

    public boolean read_sampledata(Bitstream stream)
    {
        boolean returnvalue = super.read_sampledata(stream);

        if (this.channel2_allocation != 0)
        {
            this.channel2_sample = (float)stream.get_bits(this.channel2_samplelength);
        }

        return returnvalue;
    }

    public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2)
    {
        super.put_next_sample(channels, filter1, filter2);

        if (this.channel2_allocation != 0 && channels != 1)
        {
            float sample2 = (this.channel2_sample * this.channel2_factor + this.channel2_offset) * this.channel2_scalefactor;

            if (channels == 0)
            {
                filter2.input_sample(sample2, this.subbandnumber);
            }
            else
            {
                filter1.input_sample(sample2, this.subbandnumber);
            }
        }

        return true;
    }
}
