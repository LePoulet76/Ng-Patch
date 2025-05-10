package net.ilexiconn.nationsgui.forge.client;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.minecraft.client.Minecraft;

public class ThreadDownloadImage extends Thread
{
    final DownloadableTexture downloadableTexture;

    public ThreadDownloadImage(DownloadableTexture par1ThreadDownloadImageData)
    {
        this.downloadableTexture = par1ThreadDownloadImageData;
    }

    public void run()
    {
        HttpURLConnection httpurlconnection = null;

        try
        {
            httpurlconnection = (HttpURLConnection)(new URL(DownloadableTexture.getImageUrl(this.downloadableTexture))).openConnection(Minecraft.getMinecraft().getProxy());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            if (httpurlconnection.getResponseCode() / 100 == 2)
            {
                BufferedImage exception = ImageIO.read(httpurlconnection.getInputStream());
                this.downloadableTexture.setBufferedImage(exception);
                return;
            }
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
            return;
        }
        finally
        {
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }
    }
}
