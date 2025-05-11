/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.minecraft.client.Minecraft;

public class ThreadDownloadImage
extends Thread {
    final DownloadableTexture downloadableTexture;

    public ThreadDownloadImage(DownloadableTexture par1ThreadDownloadImageData) {
        this.downloadableTexture = par1ThreadDownloadImageData;
    }

    @Override
    public void run() {
        HttpURLConnection httpurlconnection = null;
        try {
            httpurlconnection = (HttpURLConnection)new URL(DownloadableTexture.getImageUrl(this.downloadableTexture)).openConnection(Minecraft.func_71410_x().func_110437_J());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();
            if (httpurlconnection.getResponseCode() / 100 != 2) {
                return;
            }
            BufferedImage bufferedimage = ImageIO.read(httpurlconnection.getInputStream());
            this.downloadableTexture.setBufferedImage(bufferedimage);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
    }
}

