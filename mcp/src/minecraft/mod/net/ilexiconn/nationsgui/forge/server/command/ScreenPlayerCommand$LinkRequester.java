package net.ilexiconn.nationsgui.forge.server.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UploadScreenPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class ScreenPlayerCommand$LinkRequester implements Runnable
{
    private EntityPlayer sender;
    private EntityPlayer target;

    public ScreenPlayerCommand$LinkRequester(EntityPlayer sender, EntityPlayer target)
    {
        this.sender = sender;
        this.target = target;
    }

    public void run()
    {
        URL url = null;

        try
        {
            url = new URL("https://apiv2.nationsglory.fr/mods/screen_api_authorize");
        }
        catch (MalformedURLException var17)
        {
            var17.printStackTrace();
        }

        String data = "";

        try
        {
            data = data + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(this.sender.username, "UTF-8");
            data = data + "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(this.target.username, "UTF-8");
        }
        catch (UnsupportedEncodingException var16)
        {
            var16.printStackTrace();
        }

        HttpURLConnection conn = null;

        try
        {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
        }
        catch (IOException var15)
        {
            var15.printStackTrace();
        }

        conn.setDoOutput(true);
        OutputStreamWriter wr = null;

        try
        {
            wr = new OutputStreamWriter(conn.getOutputStream());
        }
        catch (IOException var14)
        {
            var14.printStackTrace();
        }

        try
        {
            wr.write(data);
            wr.flush();
        }
        catch (IOException var13)
        {
            var13.printStackTrace();
        }

        InputStream is = null;

        try
        {
            if (conn.getResponseCode() == 400)
            {
                is = conn.getErrorStream();
            }
            else
            {
                try
                {
                    is = conn.getInputStream();
                }
                catch (IOException var11)
                {
                    this.sender.addChatMessage(I18n.getString("screen.uploader.http_error"));
                }
            }
        }
        catch (IOException var12)
        {
            var12.printStackTrace();
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try
        {
            JsonParser e = new JsonParser();
            String line;

            while ((line = rd.readLine()) != null)
            {
                try
                {
                    JsonObject e1 = (JsonObject)e.parse(line);

                    if (e1.get("token") != null && e1.get("location") != null)
                    {
                        this.sender.addChatMessage(Translation.get("\u00a76Lien vers la capture d\'\u00e9cran du joueur:"));
                        this.sender.addChatMessage(" \u00a7e" + e1.get("location").getAsString());
                        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new UploadScreenPacket(e1.get("token").getAsString())), (Player)this.target);
                    }
                    else
                    {
                        this.sender.addChatMessage("\u00a7cError");
                    }
                }
                catch (JsonSyntaxException var18)
                {
                    var18.printStackTrace();
                }
            }
        }
        catch (IOException var19)
        {
            var19.printStackTrace();
        }

        try
        {
            rd.close();
        }
        catch (IOException var10)
        {
            var10.printStackTrace();
        }
    }
}
