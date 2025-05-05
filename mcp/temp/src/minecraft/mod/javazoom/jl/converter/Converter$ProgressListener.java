package javazoom.jl.converter;

import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.Obuffer;

public interface Converter$ProgressListener {

   int UPDATE_FRAME_COUNT = 1;
   int UPDATE_CONVERT_COMPLETE = 2;


   void converterUpdate(int var1, int var2, int var3);

   void parsedFrame(int var1, Header var2);

   void readFrame(int var1, Header var2);

   void decodedFrame(int var1, Header var2, Obuffer var3);

   boolean converterException(Throwable var1);
}
