package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIIDecoder$SubbandLayer2;
import javazoom.jl.decoder.LayerIIDecoder$SubbandLayer2IntensityStereo;
import javazoom.jl.decoder.LayerIIDecoder$SubbandLayer2Stereo;

class LayerIIDecoder extends LayerIDecoder implements FrameDecoder
{
    protected void createSubbands()
    {
        int i;

        if (this.mode == 3)
        {
            for (i = 0; i < this.num_subbands; ++i)
            {
                this.subbands[i] = new LayerIIDecoder$SubbandLayer2(i);
            }
        }
        else if (this.mode == 1)
        {
            for (i = 0; i < this.header.intensity_stereo_bound(); ++i)
            {
                this.subbands[i] = new LayerIIDecoder$SubbandLayer2Stereo(i);
            }

            while (i < this.num_subbands)
            {
                this.subbands[i] = new LayerIIDecoder$SubbandLayer2IntensityStereo(i);
                ++i;
            }
        }
        else
        {
            for (i = 0; i < this.num_subbands; ++i)
            {
                this.subbands[i] = new LayerIIDecoder$SubbandLayer2Stereo(i);
            }
        }
    }

    protected void readScaleFactorSelection()
    {
        for (int i = 0; i < this.num_subbands; ++i)
        {
            ((LayerIIDecoder$SubbandLayer2)this.subbands[i]).read_scalefactor_selection(this.stream, this.crc);
        }
    }
}
