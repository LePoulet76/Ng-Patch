package javazoom.jl.decoder;

public class Decoder$Params implements Cloneable
{
    private OutputChannels outputChannels;
    private Equalizer equalizer;

    public Decoder$Params()
    {
        this.outputChannels = OutputChannels.BOTH;
        this.equalizer = new Equalizer();
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException var2)
        {
            throw new InternalError(this + ": " + var2);
        }
    }

    public void setOutputChannels(OutputChannels out)
    {
        if (out == null)
        {
            throw new NullPointerException("out");
        }
        else
        {
            this.outputChannels = out;
        }
    }

    public OutputChannels getOutputChannels()
    {
        return this.outputChannels;
    }

    public Equalizer getInitialEqualizerSettings()
    {
        return this.equalizer;
    }
}
