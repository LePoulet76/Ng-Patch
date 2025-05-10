package net.ilexiconn.nationsgui.forge.client.render.texture;

import java.awt.image.BufferedImage;
import net.ilexiconn.nationsgui.forge.client.ThreadDownloadImage;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DownloadableTexture extends AbstractTexture
{
    private final String imageUrl;
    private IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private SimpleTexture imageLocation;
    private boolean textureUploaded;
    private boolean textureUpdate;

    public DownloadableTexture(String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        this.imageUrl = par1Str;
        this.imageBuffer = par3IImageBuffer;
        this.imageLocation = par2ResourceLocation != null ? new SimpleTexture(par2ResourceLocation) : null;
    }

    public int getGlTextureId()
    {
        int i = super.getGlTextureId();

        if (this.bufferedImage != null && (!this.textureUploaded || this.textureUpdate))
        {
            GL11.glDeleteTextures(i);
            TextureUtil.uploadTextureImage(i, this.bufferedImage);
            this.textureUploaded = true;
            this.textureUpdate = false;
        }

        return i;
    }

    public BufferedImage getBufferedImage()
    {
        return this.bufferedImage;
    }

    public void setBufferedImage(BufferedImage par1BufferedImage)
    {
        this.bufferedImage = par1BufferedImage;
    }

    public void loadTexture(ResourceManager par1ResourceManager)
    {
        if (this.bufferedImage == null)
        {
            if (this.imageLocation != null)
            {
                this.imageLocation.loadTexture(par1ResourceManager);
                this.glTextureId = this.imageLocation.getGlTextureId();
            }
        }
        else
        {
            TextureUtil.uploadTextureImage(this.getGlTextureId(), this.bufferedImage);
        }

        if (this.imageThread == null)
        {
            this.imageThread = new ThreadDownloadImage(this);
            this.imageThread.setDaemon(true);
            this.imageThread.start();
        }
    }

    public void reloadTexture()
    {
        this.bufferedImage = null;
        this.textureUpdate = true;
        this.imageThread = new ThreadDownloadImage(this);
        this.imageThread.setDaemon(true);
        this.imageThread.start();
    }

    public boolean isTextureUploaded()
    {
        this.getGlTextureId();
        return this.textureUploaded;
    }

    public static String getImageUrl(DownloadableTexture downloadableTexture)
    {
        return downloadableTexture.imageUrl;
    }

    public static IImageBuffer getImageBuffer(DownloadableTexture downloadableTexture)
    {
        return downloadableTexture.imageBuffer;
    }
}
