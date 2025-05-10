package net.ilexiconn.nationsgui.forge.client.render.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;

public class GraphTexture extends AbstractTexture
{
    private InputStream inputStream;

    public GraphTexture(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public void loadTexture(ResourceManager resourcemanager)
    {
        if (this.inputStream != null)
        {
            try
            {
                BufferedImage e = ImageIO.read(this.inputStream);

                if (e != null)
                {
                    TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), e, false, false);
                }

                this.inputStream.close();
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }
        }
    }
}
