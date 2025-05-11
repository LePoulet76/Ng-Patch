/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 */
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
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UploadScreenPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;

public class ScreenPlayerCommand
extends CommandBase {
    public String func_71517_b() {
        return "screen";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "\u00a7c/screen <player>";
    }

    public void func_71515_b(ICommandSender icommandsender, String[] args) {
        if (icommandsender instanceof EntityPlayer) {
            EntityPlayer sender = (EntityPlayer)icommandsender;
            EntityPlayer player = sender.func_130014_f_().func_72924_a(args[0]);
            if (args.length > 0 && player != null) {
                Thread t = new Thread(new LinkRequester(sender, player));
                t.setDaemon(true);
                t.start();
            }
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? ScreenPlayerCommand.func_71530_a((String[])par2ArrayOfStr, (String[])MinecraftServer.func_71276_C().func_71213_z()) : null;
    }

    public int compareTo(Object o) {
        return 0;
    }

    public static class LinkRequester
    implements Runnable {
        private EntityPlayer sender;
        private EntityPlayer target;

        public LinkRequester(EntityPlayer sender, EntityPlayer target) {
            this.sender = sender;
            this.target = target;
        }

        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL("https://apiv2.nationsglory.fr/mods/screen_api_authorize");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = "";
            try {
                data = data + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(this.sender.field_71092_bJ, "UTF-8");
                data = data + "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(this.target.field_71092_bJ, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            conn.setDoOutput(true);
            OutputStreamWriter wr = null;
            try {
                wr = new OutputStreamWriter(conn.getOutputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wr.write(data);
                wr.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = null;
            try {
                if (conn.getResponseCode() == 400) {
                    is = conn.getErrorStream();
                } else {
                    try {
                        is = conn.getInputStream();
                    }
                    catch (IOException e1) {
                        this.sender.func_71035_c(I18n.func_135053_a((String)"screen.uploader.http_error"));
                    }
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                String line;
                JsonParser parser = new JsonParser();
                while ((line = rd.readLine()) != null) {
                    try {
                        JsonObject json = (JsonObject)parser.parse(line);
                        if (json.get("token") != null && json.get("location") != null) {
                            this.sender.func_71035_c(Translation.get("\u00a76Lien vers la capture d'\u00e9cran du joueur:"));
                            this.sender.func_71035_c(" \u00a7e" + json.get("location").getAsString());
                            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new UploadScreenPacket(json.get("token").getAsString())), (Player)((Player)this.target));
                            continue;
                        }
                        this.sender.func_71035_c("\u00a7cError");
                    }
                    catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                rd.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

