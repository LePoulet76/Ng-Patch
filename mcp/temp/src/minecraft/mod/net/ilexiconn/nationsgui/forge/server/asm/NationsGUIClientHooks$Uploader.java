package net.ilexiconn.nationsgui.forge.server.asm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryAddImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotAddImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionUpdateBannerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.bouncycastle.util.encoders.Base64;

public class NationsGUIClientHooks$Uploader implements Runnable {

   private BufferedImage image;
   private String imageName;


   public NationsGUIClientHooks$Uploader(BufferedImage image, String imageName) {
      this.image = image;
      this.imageName = imageName;
   }

   public void run() {
      Minecraft mc = Minecraft.func_71410_x();
      EntityClientPlayerMP me = mc.field_71439_g;
      if(this.imageName != null) {
         int baos;
         int url;
         int sess;
         BufferedImage data;
         BufferedImage conn;
         Graphics2D wr;
         if(this.imageName.startsWith("plot")) {
            baos = this.image.getWidth();
            url = (int)((double)baos / 1.93D);
            sess = (this.image.getHeight() - url) / 2;
            data = this.image.getSubimage(0, sess, baos, Math.min(url, this.image.getHeight()));
            conn = new BufferedImage(675, 348, 1);
            wr = conn.createGraphics();
            wr.drawImage(data, 0, 0, 675, 348, (ImageObserver)null);
            wr.dispose();
            this.image = conn;
         } else if(!this.imageName.equals("gallery")) {
            baos = this.image.getWidth();
            url = (int)((double)baos / 2.53D);
            sess = (this.image.getHeight() - url) / 2;
            data = this.image.getSubimage(0, sess, baos, Math.min(url, this.image.getHeight()));
            conn = new BufferedImage(837, 330, 1);
            wr = conn.createGraphics();
            wr.drawImage(data, 0, 0, 837, 330, (ImageObserver)null);
            wr.dispose();
            this.image = conn;
         } else {
            baos = this.image.getWidth();
            url = (int)((double)baos / 1.78D);
            sess = (this.image.getHeight() - url) / 2;
            data = this.image.getSubimage(0, sess, baos, Math.min(url, this.image.getHeight()));
            conn = new BufferedImage(573, 321, 1);
            wr = conn.createGraphics();
            wr.drawImage(data, 0, 0, 573, 321, (ImageObserver)null);
            wr.dispose();
            this.image = conn;
         }
      }

      if(me != null) {
         ByteArrayOutputStream baos1 = new ByteArrayOutputStream();

         try {
            ImageIO.write(this.image, "png", baos1);
         } catch (IOException var23) {
            var23.printStackTrace();
         }

         URL url1 = null;

         try {
            url1 = new URL("https://apiv2.nationsglory.fr/mods/sharing_uploader");
         } catch (MalformedURLException var22) {
            var22.printStackTrace();
         }

         Session sess1 = Minecraft.func_71410_x().func_110432_I();
         String data1 = "";

         try {
            data1 = URLEncoder.encode("file", "UTF-8") + "=" + URLEncoder.encode(new String(Base64.encode(baos1.toByteArray()), StandardCharsets.US_ASCII), "UTF-8");
            data1 = data1 + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(sess1.func_111285_a(), "UTF-8");
            data1 = data1 + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(sess1.func_111286_b(), "UTF-8");
            data1 = data1 + "&" + URLEncoder.encode("dated", "UTF-8") + "=" + URLEncoder.encode(NBTConfig.CONFIG.getCompound().func_74767_n("DatedScreenshot") + "", "UTF-8");
         } catch (UnsupportedEncodingException var21) {
            var21.printStackTrace();
         }

         HttpURLConnection conn1 = null;

         try {
            conn1 = (HttpURLConnection)url1.openConnection();
            conn1.setRequestMethod("POST");
         } catch (IOException var20) {
            var20.printStackTrace();
         }

         conn1.setDoOutput(true);
         OutputStreamWriter wr1 = null;

         try {
            wr1 = new OutputStreamWriter(conn1.getOutputStream());
         } catch (IOException var19) {
            var19.printStackTrace();
         }

         try {
            wr1.write(data1);
            wr1.flush();
         } catch (IOException var18) {
            var18.printStackTrace();
         }

         InputStream is = null;

         try {
            if(conn1.getResponseCode() == 400) {
               is = conn1.getErrorStream();
            } else {
               try {
                  is = conn1.getInputStream();
               } catch (IOException var16) {
                  me.func_71035_c(I18n.func_135053_a("screen.uploader.http_error"));
               }
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }

         BufferedReader rd = new BufferedReader(new InputStreamReader(is));

         try {
            JsonParser e = new JsonParser();

            String line;
            while((line = rd.readLine()) != null) {
               try {
                  JsonObject e1 = (JsonObject)e.parse(line);
                  if(e1.get("error") != null) {
                     me.func_71035_c(I18n.func_135052_a("screen.uploader.error", new Object[]{I18n.func_135053_a(e1.get("error").getAsString())}));
                  } else if(e1.get("location") != null) {
                     if(this.imageName != null) {
                        if(this.imageName.startsWith("plot")) {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotAddImagePacket(Double.valueOf(Double.parseDouble(this.imageName.split("#")[1])).intValue(), e1.get("location").getAsString())));
                        } else if(!this.imageName.equals("gallery")) {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionUpdateBannerPacket(this.imageName, e1.get("location").getAsString())));
                        } else {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGalleryAddImagePacket(e1.get("location").getAsString())));
                        }
                     } else {
                        me.func_71035_c(I18n.func_135052_a("screen.uploader.uploaded", new Object[]{I18n.func_135053_a(e1.get("location").getAsString())}));
                     }
                  }
               } catch (JsonSyntaxException var15) {
                  var15.printStackTrace();
               }
            }
         } catch (IOException var24) {
            var24.printStackTrace();
         }

         try {
            rd.close();
         } catch (IOException var14) {
            var14.printStackTrace();
         }

      }
   }
}
