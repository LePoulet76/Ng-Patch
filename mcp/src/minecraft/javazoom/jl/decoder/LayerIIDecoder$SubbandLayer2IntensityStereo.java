package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIIDecoder$SubbandLayer2;

class LayerIIDecoder$SubbandLayer2IntensityStereo extends LayerIIDecoder$SubbandLayer2
{
    protected int channel2_scfsi;
    protected float channel2_scalefactor1;
    protected float channel2_scalefactor2;
    protected float channel2_scalefactor3;

    public LayerIIDecoder$SubbandLayer2IntensityStereo(int subbandnumber)
    {
        super(subbandnumber);
    }

    public void read_allocation(Bitstream stream, Header header, Crc16 crc)
    {
        super.read_allocation(stream, header, crc);
    }

    public void read_scalefactor_selection(Bitstream stream, Crc16 crc)
    {
        if (this.allocation != 0)
        {
            this.scfsi = stream.get_bits(2);
            this.channel2_scfsi = stream.get_bits(2);

            if (crc != null)
            {
                crc.add_bits(this.scfsi, 2);
                crc.add_bits(this.channel2_scfsi, 2);
            }
        }
    }

    public void read_scalefactor(Bitstream stream, Header header)
    {
        if (this.allocation != 0)
        {
            super.read_scalefactor(stream, header);

            switch (this.channel2_scfsi)
            {
                case 0:
                    this.channel2_scalefactor1 = scalefactors[stream.get_bits(6)];
                    this.channel2_scalefactor2 = scalefactors[stream.get_bits(6)];
                    this.channel2_scalefactor3 = scalefactors[stream.get_bits(6)];
                    break;

                case 1:
                    this.channel2_scalefactor1 = this.channel2_scalefactor2 = scalefactors[stream.get_bits(6)];
                    this.channel2_scalefactor3 = scalefactors[stream.get_bits(6)];
                    break;

                case 2:
                    this.channel2_scalefactor1 = this.channel2_scalefactor2 = this.channel2_scalefactor3 = scalefactors[stream.get_bits(6)];
                    break;

                case 3:
                    this.channel2_scalefactor1 = scalefactors[stream.get_bits(6)];
                    this.channel2_scalefactor2 = this.channel2_scalefactor3 = scalefactors[stream.get_bits(6)];
            }
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
            float sample = this.samples[this.samplenumber];

            if (this.groupingtable[0] == null)
            {
                sample = (sample + this.d[0]) * this.c[0];
            }

            if (channels == 0)
            {
                float sample2 = sample;

                if (this.groupnumber <= 4)
                {
                    sample *= this.scalefactor1;
                    sample2 *= this.channel2_scalefactor1;
                }
                else if (this.groupnumber <= 8)
                {
                    sample *= this.scalefactor2;
                    sample2 *= this.channel2_scalefactor2;
                }
                else
                {
                    sample *= this.scalefactor3;
                    sample2 *= this.channel2_scalefactor3;
                }

                filter1.input_sample(sample, this.subbandnumber);
                filter2.input_sample(sample2, this.subbandnumber);
            }
            else if (channels == 1)
            {
                if (this.groupnumber <= 4)
                {
                    sample *= this.scalefactor1;
                }
                else if (this.groupnumber <= 8)
                {
                    sample *= this.scalefactor2;
                }
                else
                {
                    sample *= this.scalefactor3;
                }

                filter1.input_sample(sample, this.subbandnumber);
            }
            else
            {
                if (this.groupnumber <= 4)
                {
                    sample *= this.channel2_scalefactor1;
                }
                else if (this.groupnumber <= 8)
                {
                    sample *= this.channel2_scalefactor2;
                }
                else
                {
                    sample *= this.channel2_scalefactor3;
                }

                filter1.input_sample(sample, this.subbandnumber);
            }
        }

        return ++this.samplenumber == 3;
    }
}
