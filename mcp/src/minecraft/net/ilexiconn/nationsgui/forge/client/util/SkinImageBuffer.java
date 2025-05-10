package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import net.minecraft.client.renderer.IImageBuffer;

@SideOnly(Side.CLIENT)
public class SkinImageBuffer implements IImageBuffer
{
    public BufferedImage parseUserSkin(BufferedImage unprocessed)
    {
        if (unprocessed == null)
        {
            return null;
        }
        else
        {
            BufferedImage result = new BufferedImage(64, 64, 2);
            Graphics graphics = result.getGraphics();
            graphics.drawImage(unprocessed, 0, 0, (ImageObserver)null);

            if (unprocessed.getHeight() == 32)
            {
                graphics.drawImage(result, 24, 48, 20, 52, 4, 16, 8, 20, (ImageObserver)null);
                graphics.drawImage(result, 28, 48, 24, 52, 8, 16, 12, 20, (ImageObserver)null);
                graphics.drawImage(result, 20, 52, 16, 64, 8, 20, 12, 32, (ImageObserver)null);
                graphics.drawImage(result, 24, 52, 20, 64, 4, 20, 8, 32, (ImageObserver)null);
                graphics.drawImage(result, 28, 52, 24, 64, 0, 20, 4, 32, (ImageObserver)null);
                graphics.drawImage(result, 32, 52, 28, 64, 12, 20, 16, 32, (ImageObserver)null);
                graphics.drawImage(result, 40, 48, 36, 52, 44, 16, 48, 20, (ImageObserver)null);
                graphics.drawImage(result, 44, 48, 40, 52, 48, 16, 52, 20, (ImageObserver)null);
                graphics.drawImage(result, 36, 52, 32, 64, 48, 20, 52, 32, (ImageObserver)null);
                graphics.drawImage(result, 40, 52, 36, 64, 44, 20, 48, 32, (ImageObserver)null);
                graphics.drawImage(result, 44, 52, 40, 64, 40, 20, 44, 32, (ImageObserver)null);
                graphics.drawImage(result, 48, 52, 44, 64, 52, 20, 56, 32, (ImageObserver)null);
            }

            graphics.dispose();
            int[] imageData = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();
            this.setAreaOpaque(imageData, 0, 0, 32, 16);
            this.setAreaTransparent(imageData, 32, 0, 64, 32);
            this.setAreaOpaque(imageData, 0, 16, 64, 32);
            this.setAreaTransparent(imageData, 0, 32, 16, 48);
            this.setAreaTransparent(imageData, 16, 32, 40, 48);
            this.setAreaTransparent(imageData, 40, 32, 56, 48);
            this.setAreaTransparent(imageData, 0, 48, 16, 64);
            this.setAreaOpaque(imageData, 16, 48, 48, 64);
            this.setAreaTransparent(imageData, 48, 48, 64, 64);
            return result;
        }
    }

    private void setAreaTransparent(int[] imageData, int x, int y, int width, int height)
    {
        if (!this.hasTransparency(imageData, x, y, width, height))
        {
            for (int i = x; i < width; ++i)
            {
                for (int j = y; j < height; ++j)
                {
                    imageData[i + j * 64] &= 16777215;
                }
            }
        }
    }

    private void setAreaOpaque(int[] imageData, int x, int y, int width, int height)
    {
        for (int i = x; i < width; ++i)
        {
            for (int j = y; j < height; ++j)
            {
                imageData[i + j * 64] |= -16777216;
            }
        }
    }

    private boolean hasTransparency(int[] imageData, int x, int y, int width, int height)
    {
        for (int i = x; i < width; ++i)
        {
            for (int j = y; j < height; ++j)
            {
                int k = imageData[i + j * 64];

                if ((k >> 24 & 255) < 128)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
