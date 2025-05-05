package javazoom.jl.converter;

import javazoom.jl.converter.WaveFile;

class WaveFile$WaveFormat_ChunkData {

   public short wFormatTag;
   public short nChannels;
   public int nSamplesPerSec;
   public int nAvgBytesPerSec;
   public short nBlockAlign;
   public short nBitsPerSample;
   // $FF: synthetic field
   final WaveFile this$0;


   public WaveFile$WaveFormat_ChunkData(WaveFile this$0) {
      this.this$0 = this$0;
      this.wFormatTag = 0;
      this.nChannels = 0;
      this.nSamplesPerSec = 0;
      this.nAvgBytesPerSec = 0;
      this.nBlockAlign = 0;
      this.nBitsPerSample = 0;
      this.wFormatTag = 1;
      this.Config('\uac44', (short)16, (short)1);
   }

   public void Config(int NewSamplingRate, short NewBitsPerSample, short NewNumChannels) {
      this.nSamplesPerSec = NewSamplingRate;
      this.nChannels = NewNumChannels;
      this.nBitsPerSample = NewBitsPerSample;
      this.nAvgBytesPerSec = this.nChannels * this.nSamplesPerSec * this.nBitsPerSample / 8;
      this.nBlockAlign = (short)(this.nChannels * this.nBitsPerSample / 8);
   }
}
