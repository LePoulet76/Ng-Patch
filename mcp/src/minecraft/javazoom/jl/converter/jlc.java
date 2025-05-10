package javazoom.jl.converter;

import java.io.PrintWriter;
import javazoom.jl.converter.Converter$PrintWriterProgressListener;
import javazoom.jl.converter.jlc$jlcArgs;
import javazoom.jl.decoder.JavaLayerException;

public class jlc
{
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        int argc = args.length + 1;
        String[] argv = new String[argc];
        argv[0] = "jlc";

        for (int ma = 0; ma < args.length; ++ma)
        {
            argv[ma + 1] = args[ma];
        }

        jlc$jlcArgs var11 = new jlc$jlcArgs();

        if (!var11.processArgs(argv))
        {
            System.exit(1);
        }

        Converter conv = new Converter();
        int detail = var11.verbose_mode ? var11.verbose_level : 0;
        Converter$PrintWriterProgressListener listener = new Converter$PrintWriterProgressListener(new PrintWriter(System.out, true), detail);

        try
        {
            conv.convert(var11.filename, var11.output_filename, listener);
        }
        catch (JavaLayerException var10)
        {
            System.err.println("Convertion failure: " + var10);
        }

        System.exit(0);
    }
}
