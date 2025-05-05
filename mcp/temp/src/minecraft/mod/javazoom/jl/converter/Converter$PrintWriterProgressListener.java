package javazoom.jl.converter;

import java.io.PrintWriter;
import javazoom.jl.converter.Converter$ProgressListener;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.Obuffer;

public class Converter$PrintWriterProgressListener implements Converter$ProgressListener {

   public static final int NO_DETAIL = 0;
   public static final int EXPERT_DETAIL = 1;
   public static final int VERBOSE_DETAIL = 2;
   public static final int DEBUG_DETAIL = 7;
   public static final int MAX_DETAIL = 10;
   private PrintWriter pw;
   private int detailLevel;


   public static Converter$PrintWriterProgressListener newStdOut(int detail) {
      return new Converter$PrintWriterProgressListener(new PrintWriter(System.out, true), detail);
   }

   public Converter$PrintWriterProgressListener(PrintWriter writer, int detailLevel) {
      this.pw = writer;
      this.detailLevel = detailLevel;
   }

   public boolean isDetail(int detail) {
      return this.detailLevel >= detail;
   }

   public void converterUpdate(int updateID, int param1, int param2) {
      if(this.isDetail(2)) {
         switch(updateID) {
         case 2:
            if(param2 == 0) {
               param2 = 1;
            }

            this.pw.println();
            this.pw.println("Converted " + param2 + " frames in " + param1 + " ms (" + param1 / param2 + " ms per frame.)");
         }
      }

   }

   public void parsedFrame(int frameNo, Header header) {
      String headerString;
      if(frameNo == 0 && this.isDetail(2)) {
         headerString = header.toString();
         this.pw.println("File is a " + headerString);
      } else if(this.isDetail(10)) {
         headerString = header.toString();
         this.pw.println("Prased frame " + frameNo + ": " + headerString);
      }

   }

   public void readFrame(int frameNo, Header header) {
      String headerString;
      if(frameNo == 0 && this.isDetail(2)) {
         headerString = header.toString();
         this.pw.println("File is a " + headerString);
      } else if(this.isDetail(10)) {
         headerString = header.toString();
         this.pw.println("Read frame " + frameNo + ": " + headerString);
      }

   }

   public void decodedFrame(int frameNo, Header header, Obuffer o) {
      if(this.isDetail(10)) {
         String headerString = header.toString();
         this.pw.println("Decoded frame " + frameNo + ": " + headerString);
         this.pw.println("Output: " + o);
      } else if(this.isDetail(2)) {
         if(frameNo == 0) {
            this.pw.print("Converting.");
            this.pw.flush();
         }

         if(frameNo % 10 == 0) {
            this.pw.print('.');
            this.pw.flush();
         }
      }

   }

   public boolean converterException(Throwable t) {
      if(this.detailLevel > 0) {
         t.printStackTrace(this.pw);
         this.pw.flush();
      }

      return false;
   }
}
