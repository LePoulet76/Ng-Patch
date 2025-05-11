/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class InterpolationUtil {
    private static long lastUpdate = System.currentTimeMillis();

    public static void updateLast() {
        lastUpdate = System.currentTimeMillis();
    }

    public static float updateValue(float current, float target) {
        return InterpolationUtil.updateValue(current, target, 0.5f);
    }

    public static float updateValue(float current, float target, float factor) {
        float times = (float)(System.currentTimeMillis() - lastUpdate) / 16.666666f;
        float off = target - current;
        off = off > 0.01f || off < -0.01f ? off * (float)Math.pow(factor, times) : 0.0f;
        return target - off;
    }

    public static float interpolate(float prev, float current, float partialTicks) {
        return prev + partialTicks * (current - prev);
    }
}

