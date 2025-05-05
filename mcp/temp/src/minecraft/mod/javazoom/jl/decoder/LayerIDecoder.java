package javazoom.jl.decoder;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Crc16;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.FrameDecoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.LayerIDecoder$Subband;
import javazoom.jl.decoder.LayerIDecoder$SubbandLayer1;
import javazoom.jl.decoder.LayerIDecoder$SubbandLayer1IntensityStereo;
import javazoom.jl.decoder.LayerIDecoder$SubbandLayer1Stereo;
import javazoom.jl.decoder.Obuffer;
import javazoom.jl.decoder.SynthesisFilter;

class LayerIDecoder implements FrameDecoder {

   protected Bitstream stream;
   protected Header header;
   protected SynthesisFilter filter1;
   protected SynthesisFilter filter2;
   protected Obuffer buffer;
   protected int which_channels;
   protected int mode;
   protected int num_subbands;
   protected LayerIDecoder$Subband[] subbands;
   protected Crc16 crc = null;


   public LayerIDecoder() {
      this.crc = new Crc16();
   }

   public void create(Bitstream stream0, Header header0, SynthesisFilter filtera, SynthesisFilter filterb, Obuffer buffer0, int which_ch0) {
      this.stream = stream0;
      this.header = header0;
      this.filter1 = filtera;
      this.filter2 = filterb;
      this.buffer = buffer0;
      this.which_channels = which_ch0;
   }

   public void decodeFrame() throws DecoderException {
      this.num_subbands = this.header.number_of_subbands();
      this.subbands = new LayerIDecoder$Subband[32];
      this.mode = this.header.mode();
      this.createSubbands();
      this.readAllocation();
      this.readScaleFactorSelection();
      if(this.crc != null || this.header.checksum_ok()) {
         this.readScaleFactors();
         this.readSampleData();
      }

   }

   protected void createSubbands() {
      int i;
      if(this.mode == 3) {
         for(i = 0; i < this.num_subbands; ++i) {
            this.subbands[i] = new LayerIDecoder$SubbandLayer1(i);
         }
      } else if(this.mode == 1) {
         for(i = 0; i < this.header.intensity_stereo_bound(); ++i) {
            this.subbands[i] = new LayerIDecoder$SubbandLayer1Stereo(i);
         }

         while(i < this.num_subbands) {
            this.subbands[i] = new LayerIDecoder$SubbandLayer1IntensityStereo(i);
            ++i;
         }
      } else {
         for(i = 0; i < this.num_subbands; ++i) {
            this.subbands[i] = new LayerIDecoder$SubbandLayer1Stereo(i);
         }
      }

   }

   protected void readAllocation() throws DecoderException {
      for(int i = 0; i < this.num_subbands; ++i) {
         this.subbands[i].read_allocation(this.stream, this.header, this.crc);
      }

   }

   protected void readScaleFactorSelection() {}

   protected void readScaleFactors() {
      for(int i = 0; i < this.num_subbands; ++i) {
         this.subbands[i].read_scalefactor(this.stream, this.header);
      }

   }

   protected void readSampleData() {
      boolean read_ready = false;
      boolean write_ready = false;
      int mode = this.header.mode();

      do {
         int i;
         for(i = 0; i < this.num_subbands; ++i) {
            read_ready = this.subbands[i].read_sampledata(this.stream);
         }

         do {
            for(i = 0; i < this.num_subbands; ++i) {
               write_ready = this.subbands[i].put_next_sample(this.which_channels, this.filter1, this.filter2);
            }

            this.filter1.calculate_pcm_samples(this.buffer);
            if(this.which_channels == 0 && mode != 3) {
               this.filter2.calculate_pcm_samples(this.buffer);
            }
         } while(!write_ready);
      } while(!read_ready);

   }
}
