/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.IImageBuffer
 */
package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.IImageBuffer;

@SideOnly(value=Side.CLIENT)
public class CapeImageBuffer
implements IImageBuffer {
    public BufferedImage func_78432_a(BufferedImage unprocessed) {
        if (unprocessed == null) {
            return null;
        }
        BufferedImage result = new BufferedImage(64, 32, 2);
        Graphics graphics = result.getGraphics();
        graphics.drawImage(unprocessed, 0, 0, null);
        graphics.dispose();
        return result;
    }
}

