package javazoom.jl.converter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.converter.Converter$PrintWriterProgressListener;
import javazoom.jl.converter.Converter$ProgressListener;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Decoder$Params;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.Obuffer;

public class Converter
{

    public synchronized void convert(String sourceName, String destName) throws JavaLayerException
    {
        this.convert(sourceName, destName, (Converter$ProgressListener)null, (Decoder$Params)null);
    }

    public synchronized void convert(String sourceName, String destName, Converter$ProgressListener progressListener) throws JavaLayerException
    {
        this.convert(sourceName, destName, progressListener, (Decoder$Params)null);
    }

    public void convert(String sourceName, String destName, Converter$ProgressListener progressListener, Decoder$Params decoderParams) throws JavaLayerException
    {
        if (destName.length() == 0)
        {
            destName = null;
        }

        try
        {
            InputStream ioe = this.openInput(sourceName);
            this.convert(ioe, destName, progressListener, decoderParams);
            ioe.close();
        }
        catch (IOException var6)
        {
            throw new JavaLayerException(var6.getLocalizedMessage(), var6);
        }
    }

    public synchronized void convert(InputStream sourceStream, String destName, Converter$ProgressListener progressListener, Decoder$Params decoderParams) throws JavaLayerException
    {
        if (progressListener == null)
        {
            progressListener = Converter$PrintWriterProgressListener.newStdOut(0);
        }

        try
        {
            if (!(sourceStream instanceof BufferedInputStream))
            {
                sourceStream = new BufferedInputStream((InputStream)sourceStream);
            }

            int ex = -1;

            if (((InputStream)sourceStream).markSupported())
            {
                ((InputStream)sourceStream).mark(-1);
                ex = this.countFrames((InputStream)sourceStream);
                ((InputStream)sourceStream).reset();
            }

            ((Converter$ProgressListener)progressListener).converterUpdate(1, ex, 0);
            WaveFileObuffer output = null;
            Decoder decoder = new Decoder(decoderParams);
            Bitstream stream = new Bitstream((InputStream)sourceStream);

            if (ex == -1)
            {
                ex = Integer.MAX_VALUE;
            }

            int frame = 0;
            long startTime = System.currentTimeMillis();

            try
            {
                for (; frame < ex; ++frame)
                {
                    try
                    {
                        Header time = stream.readFrame();

                        if (time == null)
                        {
                            break;
                        }

                        ((Converter$ProgressListener)progressListener).readFrame(frame, time);

                        if (output == null)
                        {
                            int var23 = time.mode() == 3 ? 1 : 2;
                            int freq = time.frequency();
                            output = new WaveFileObuffer(var23, freq, destName);
                            decoder.setOutputBuffer(output);
                        }

                        Obuffer var24 = decoder.decodeFrame(time, stream);

                        if (var24 != output)
                        {
                            throw new InternalError("Output buffers are different.");
                        }

                        ((Converter$ProgressListener)progressListener).decodedFrame(frame, time, output);
                        stream.closeFrame();
                    }
                    catch (Exception var19)
                    {
                        boolean stop = !((Converter$ProgressListener)progressListener).converterException(var19);

                        if (stop)
                        {
                            throw new JavaLayerException(var19.getLocalizedMessage(), var19);
                        }
                    }
                }
            }
            finally
            {
                if (output != null)
                {
                    output.close();
                }
            }

            int var22 = (int)(System.currentTimeMillis() - startTime);
            ((Converter$ProgressListener)progressListener).converterUpdate(2, var22, frame);
        }
        catch (IOException var21)
        {
            throw new JavaLayerException(var21.getLocalizedMessage(), var21);
        }
    }

    protected int countFrames(InputStream in)
    {
        return -1;
    }

    protected InputStream openInput(String fileName) throws IOException
    {
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        BufferedInputStream bufIn = new BufferedInputStream(fileIn);
        return bufIn;
    }
}
