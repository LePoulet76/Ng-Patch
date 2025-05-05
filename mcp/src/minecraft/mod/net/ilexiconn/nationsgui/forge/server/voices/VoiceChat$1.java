package net.ilexiconn.nationsgui.forge.server.voices;

import cpw.mods.fml.relauncher.Side;

class VoiceChat$1
{
    static final int[] $SwitchMap$cpw$mods$fml$relauncher$Side = new int[Side.values().length];

    static
    {
        try
        {
            $SwitchMap$cpw$mods$fml$relauncher$Side[Side.CLIENT.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            $SwitchMap$cpw$mods$fml$relauncher$Side[Side.SERVER.ordinal()] = 2;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
