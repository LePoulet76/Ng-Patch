package javazoom.jl.player.advanced;

public class jlap$InfoListener extends PlaybackListener
{
    final jlap this$0;

    public jlap$InfoListener(jlap this$0)
    {
        this.this$0 = this$0;
    }

    public void playbackStarted(PlaybackEvent evt)
    {
        System.out.println("Play started from frame " + evt.getFrame());
    }

    public void playbackFinished(PlaybackEvent evt)
    {
        System.out.println("Play completed at frame " + evt.getFrame());
        System.exit(0);
    }
}
