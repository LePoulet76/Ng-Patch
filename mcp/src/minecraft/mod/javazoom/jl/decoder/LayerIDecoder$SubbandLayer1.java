package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIDecoder$Subband;

class LayerIDecoder$SubbandLayer1 extends LayerIDecoder$Subband
{
    public static final float[] table_factor = new float[] {0.0F, 0.6666667F, 0.2857143F, 0.13333334F, 0.06451613F, 0.031746034F, 0.015748031F, 0.007843138F, 0.0039138943F, 0.0019550342F, 9.770396E-4F, 4.884005E-4F, 2.4417043E-4F, 1.2207776E-4F, 6.103702E-5F};
    public static final float[] table_offset = new float[] {0.0F, -0.6666667F, -0.8571429F, -0.9333334F, -0.9677419F, -0.98412704F, -0.992126F, -0.9960785F, -0.99804306F, -0.9990225F, -0.9995115F, -0.99975586F, -0.9998779F, -0.99993896F, -0.9999695F};
    protected int subbandnumber;
    protected int samplenumber;
    protected int allocation;
    protected float scalefactor;
    protected int samplelength;
    protected float sample;
    protected float factor;
    protected float offset;

    public LayerIDecoder$SubbandLayer1(int subbandnumber)
    {
        this.subbandnumber = subbandnumber;
        this.samplenumber = 0;
    }

    public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException
    {
        if ((this.allocation = stream.get_bits(4)) == 15)
        {
            throw new DecoderException(514, (Throwable)null);
        }
        else
        {
            if (crc != null)
            {
                crc.add_bits(this.allocation, 4);
            }

            if (this.allocation != 0)
            {
                this.samplelength = this.allocation + 1;
                this.factor = table_factor[this.allocation];
                this.offset = table_offset[this.allocation];
            }
        }
    }

    public void read_scalefactor(Bitstream stream, Header header)
    {
        if (this.allocation != 0)
        {
            this.scalefactor = scalefactors[stream.get_bits(6)];
        }
    }

    public boolean read_sampledata(Bitstream stream)
    {
        if (this.allocation != 0)
        {
            this.sample = (float)stream.get_bits(this.samplelength);
        }

        if (++this.samplenumber == 12)
        {
            this.samplenumber = 0;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2)
    {
        if (this.allocation != 0 && channels != 2)
        {
            float scaled_sample = (this.sample * this.factor + this.offset) * this.scalefactor;
            filter1.input_sample(scaled_sample, this.subbandnumber);
        }

        return true;
    }
}
