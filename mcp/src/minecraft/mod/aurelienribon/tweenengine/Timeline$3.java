package aurelienribon.tweenengine;

import aurelienribon.tweenengine.Timeline$Modes;

class Timeline$3
{
    static final int[] $SwitchMap$aurelienribon$tweenengine$Timeline$Modes = new int[Timeline$Modes.values().length];

    static
    {
        try
        {
            $SwitchMap$aurelienribon$tweenengine$Timeline$Modes[Timeline$Modes.SEQUENCE.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            $SwitchMap$aurelienribon$tweenengine$Timeline$Modes[Timeline$Modes.PARALLEL.ordinal()] = 2;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
