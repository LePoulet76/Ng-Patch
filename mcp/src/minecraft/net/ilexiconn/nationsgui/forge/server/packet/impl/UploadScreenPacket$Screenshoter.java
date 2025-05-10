package net.ilexiconn.nationsgui.forge.server.packet.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.bouncycastle.util.encoders.Base64;

public class UploadScreenPacket$Screenshoter implements Runnable
{
    private String token;
    private int[] array;

    public UploadScreenPacket$Screenshoter(String token, int[] array)
    {
        this.token = token;
        this.array = array;
    }

    public void run()
    {
        Minecraft mc = Minecraft.getMinecraft();
        int[] aint1 = new int[mc.displayWidth];
        int k = mc.displayHeight / 2;

        for (int image = 0; image < k; ++image)
        {
            System.arraycopy(this.array, image * mc.displayWidth, aint1, 0, mc.displayWidth);
            System.arraycopy(this.array, (mc.displayHeight - 1 - image) * mc.displayWidth, this.array, image * mc.displayWidth, mc.displayWidth);
            System.arraycopy(aint1, 0, this.array, (mc.displayHeight - 1 - image) * mc.displayWidth, mc.displayWidth);
        }

        BufferedImage var18 = new BufferedImage(mc.displayWidth, mc.displayHeight, 1);
        var18.setRGB(0, 0, mc.displayWidth, mc.displayHeight, this.array, 0, mc.displayWidth);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(var18, "png", baos);
        }
        catch (IOException var17)
        {
            var17.printStackTrace();
        }

        URL url = null;

        try
        {
            url = new URL("https://apiv2.nationsglory.fr/mods/screen_api_uploader");
        }
        catch (MalformedURLException var16)
        {
            var16.printStackTrace();
        }

        Session sess = Minecraft.getMinecraft().getSession();
        String data = "";

        try
        {
            data = URLEncoder.encode("file", "UTF-8") + "=" + URLEncoder.encode(new String(Base64.encode(baos.toByteArray()), StandardCharsets.US_ASCII), "UTF-8");
            data = data + "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(sess.getUsername(), "UTF-8");
            data = data + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(this.token, "UTF-8");
        }
        catch (UnsupportedEncodingException var15)
        {
            var15.printStackTrace();
        }

        HttpURLConnection conn = null;

        try
        {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
        }
        catch (IOException var14)
        {
            var14.printStackTrace();
        }

        conn.setDoOutput(true);
        OutputStreamWriter wr = null;

        try
        {
            wr = new OutputStreamWriter(conn.getOutputStream());
        }
        catch (IOException var13)
        {
            var13.printStackTrace();
        }

        try
        {
            wr.write(data);
            wr.flush();
            BufferedReader e = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            e.readLine();
            e.close();
            wr.close();
        }
        catch (IOException var12)
        {
            var12.printStackTrace();
        }
    }
}
