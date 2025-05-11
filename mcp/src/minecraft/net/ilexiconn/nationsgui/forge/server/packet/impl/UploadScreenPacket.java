/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.Session
 *  org.bouncycastle.util.encoders.Base64
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;
import org.bouncycastle.util.encoders.Base64;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class UploadScreenPacket
implements IPacket,
IClientPacket {
    private static IntBuffer buffer;
    private static int[] array;
    private String allocatorId;

    public UploadScreenPacket(String allocatorId) {
        this.allocatorId = allocatorId;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.allocatorId = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.allocatorId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        try {
            Minecraft mc = Minecraft.func_71410_x();
            int k = mc.field_71443_c * mc.field_71440_d;
            if (buffer == null || buffer.capacity() < k) {
                buffer = BufferUtils.createIntBuffer((int)k);
                array = new int[k];
            }
            GL11.glPixelStorei((int)3333, (int)1);
            GL11.glPixelStorei((int)3317, (int)1);
            buffer.clear();
            GL11.glReadPixels((int)0, (int)0, (int)mc.field_71443_c, (int)mc.field_71440_d, (int)32993, (int)33639, (IntBuffer)buffer);
            buffer.get(array);
            Thread screenshot = new Thread(new Screenshoter(this.allocatorId, array));
            screenshot.setDaemon(true);
            screenshot.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static class Screenshoter
    implements Runnable {
        private String token;
        private int[] array;

        public Screenshoter(String token, int[] array) {
            this.token = token;
            this.array = array;
        }

        @Override
        public void run() {
            Minecraft mc = Minecraft.func_71410_x();
            int[] aint1 = new int[mc.field_71443_c];
            int k = mc.field_71440_d / 2;
            for (int l = 0; l < k; ++l) {
                System.arraycopy(this.array, l * mc.field_71443_c, aint1, 0, mc.field_71443_c);
                System.arraycopy(this.array, (mc.field_71440_d - 1 - l) * mc.field_71443_c, this.array, l * mc.field_71443_c, mc.field_71443_c);
                System.arraycopy(aint1, 0, this.array, (mc.field_71440_d - 1 - l) * mc.field_71443_c, mc.field_71443_c);
            }
            BufferedImage image = new BufferedImage(mc.field_71443_c, mc.field_71440_d, 1);
            image.setRGB(0, 0, mc.field_71443_c, mc.field_71440_d, this.array, 0, mc.field_71443_c);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write((RenderedImage)image, "png", baos);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            URL url = null;
            try {
                url = new URL("https://apiv2.nationsglory.fr/mods/screen_api_uploader");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Session sess = Minecraft.func_71410_x().func_110432_I();
            String data = "";
            try {
                data = URLEncoder.encode("file", "UTF-8") + "=" + URLEncoder.encode(new String(Base64.encode((byte[])baos.toByteArray()), StandardCharsets.US_ASCII), "UTF-8");
                data = data + "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(sess.func_111285_a(), "UTF-8");
                data = data + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(this.token, "UTF-8");
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                reader.readLine();
                reader.close();
                wr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

