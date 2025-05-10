package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIIDecoder$SubbandLayer2;

class LayerIIDecoder$SubbandLayer2Stereo extends LayerIIDecoder$SubbandLayer2
{
    protected int channel2_allocation;
    protected int channel2_scfsi;
    protected float channel2_scalefactor1;
    protected float channel2_scalefactor2;
    protected float channel2_scalefactor3;
    protected int[] channel2_codelength = new int[] {0};
    protected float[] channel2_factor = new float[] {0.0F};
    protected float[] channel2_samples = new float[3];
    protected float[] channel2_c = new float[] {0.0F};
    protected float[] channel2_d = new float[] {0.0F};

    public LayerIIDecoder$SubbandLayer2Stereo(int subbandnumber)
    {
        super(subbandnumber);
    }

    public void read_allocation(Bitstream stream, Header header, Crc16 crc)
    {
        int length = this.get_allocationlength(header);
        this.allocation = stream.get_bits(length);
        this.channel2_allocation = stream.get_bits(length);

        if (crc != null)
        {
            crc.add_bits(this.allocation, length);
            crc.add_bits(this.channel2_allocation, length);
        }
    }

    public void read_scalefactor_selection(Bitstream stream, Crc16 crc)
    {
        if (this.allocation != 0)
        {
            this.scfsi = stream.get_bits(2);

            if (crc != null)
            {
                crc.add_bits(this.scfsi, 2);
            }
        }

        if (this.channel2_allocation != 0)
        {
            this.channel2_scfsi = stream.get_bits(2);

            if (crc != null)
            {
                crc.add_bits(this.channel2_scfsi, 2);
            }
        }
    }

    public void read_scalefactor(Bitstream stream, Header header)
    {
        super.read_scalefactor(stream, header);

        if (this.channel2_allocation != 0)
        {
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

            this.prepare_sample_reading(header, this.channel2_allocation, 1, this.channel2_factor, this.channel2_codelength, this.channel2_c, this.channel2_d);
        }
    }

    public boolean read_sampledata(Bitstream stream)
    {
        boolean returnvalue = super.read_sampledata(stream);

        if (this.channel2_allocation != 0)
        {
            if (this.groupingtable[1] != null)
            {
                int samplecode = stream.get_bits(this.channel2_codelength[0]);
                samplecode += samplecode << 1;
                float[] target = this.channel2_samples;
                float[] source = this.groupingtable[1];
                byte tmp = 0;
                target[tmp] = source[samplecode];
                int temp = samplecode + 1;
                int var8 = tmp + 1;
                target[var8] = source[temp];
                ++temp;
                ++var8;
                target[var8] = source[temp];
            }
            else
            {
                this.channel2_samples[0] = (float)((double)((float)stream.get_bits(this.channel2_codelength[0]) * this.channel2_factor[0]) - 1.0D);
                this.channel2_samples[1] = (float)((double)((float)stream.get_bits(this.channel2_codelength[0]) * this.channel2_factor[0]) - 1.0D);
                this.channel2_samples[2] = (float)((double)((float)stream.get_bits(this.channel2_codelength[0]) * this.channel2_factor[0]) - 1.0D);
            }
        }

        return returnvalue;
    }

    public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2)
    {
        boolean returnvalue = super.put_next_sample(channels, filter1, filter2);

        if (this.channel2_allocation != 0 && channels != 1)
        {
            float sample = this.channel2_samples[this.samplenumber - 1];

            if (this.groupingtable[1] == null)
            {
                sample = (sample + this.channel2_d[0]) * this.channel2_c[0];
            }

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

            if (channels == 0)
            {
                filter2.input_sample(sample, this.subbandnumber);
            }
            else
            {
                filter1.input_sample(sample, this.subbandnumber);
            }
        }

        return returnvalue;
    }
}
