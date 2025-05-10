package javazoom.jl.converter;

import javazoom.jl.converter.RiffFile$RiffChunkHeader;
import javazoom.jl.converter.WaveFile$WaveFormat_ChunkData;

class WaveFile$WaveFormat_Chunk
{
    public RiffFile$RiffChunkHeader header;
    public WaveFile$WaveFormat_ChunkData data;

    final WaveFile this$0;

    public WaveFile$WaveFormat_Chunk(WaveFile this$0)
    {
        this.this$0 = this$0;
        this.header = new RiffFile$RiffChunkHeader(this$0);
        this.data = new WaveFile$WaveFormat_ChunkData(this$0);
        this.header.ckID = RiffFile.FourCC("fmt ");
        this.header.ckSize = 16;
    }

    public int VerifyValidity()
    {
        boolean ret = this.header.ckID == RiffFile.FourCC("fmt ") && (this.data.nChannels == 1 || this.data.nChannels == 2) && this.data.nAvgBytesPerSec == this.data.nChannels * this.data.nSamplesPerSec * this.data.nBitsPerSample / 8 && this.data.nBlockAlign == this.data.nChannels * this.data.nBitsPerSample / 8;
        return ret ? 1 : 0;
    }
}
