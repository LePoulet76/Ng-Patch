package javazoom.jl.decoder;

import javazoom.jl.decoder.LayerIIIDecoder$temporaire;

class LayerIIIDecoder$III_side_info_t
{
    public int main_data_begin = 0;
    public int private_bits = 0;
    public LayerIIIDecoder$temporaire[] ch = new LayerIIIDecoder$temporaire[2];

    public LayerIIIDecoder$III_side_info_t()
    {
        this.ch[0] = new LayerIIIDecoder$temporaire();
        this.ch[1] = new LayerIIIDecoder$temporaire();
    }
}
