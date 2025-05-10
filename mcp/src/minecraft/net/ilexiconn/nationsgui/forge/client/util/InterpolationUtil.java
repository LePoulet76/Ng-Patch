package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class InterpolationUtil
{
    private static long lastUpdate = System.currentTimeMillis();

    public static void updateLast()
    {
        lastUpdate = System.currentTimeMillis();
    }

    public static float updateValue(float current, float target)
    {
        return updateValue(current, target, 0.5F);
    }

    public static float updateValue(float current, float target, float factor)
    {
        float times = (float)(System.currentTimeMillis() - lastUpdate) / 16.666666F;
        float off = (off = target - current) <= 0.01F && off >= -0.01F ? 0.0F : off * (float)Math.pow((double)factor, (double)times);
        return target - off;
    }

    public static float interpolate(float prev, float current, float partialTicks)
    {
        return prev + partialTicks * (current - prev);
    }
}
