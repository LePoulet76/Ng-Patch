/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.bouncycastle.util.encoders.Base64
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceNewTicketGUI;
import net.minecraft.client.Minecraft;
import org.bouncycastle.util.encoders.Base64;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenshotHelper {
    public static void saveScreenshotToBase64(final int par2, final int par3) {
        final int k = par2 * par3;
        GL11.glPixelStorei((int)3333, (int)1);
        GL11.glPixelStorei((int)3317, (int)1);
        final IntBuffer field_74293_b = BufferUtils.createIntBuffer((int)k);
        GL11.glReadPixels((int)0, (int)0, (int)par2, (int)par3, (int)32993, (int)33639, (IntBuffer)field_74293_b);
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                int[] field_74294_c = new int[k];
                field_74293_b.get(field_74294_c);
                ScreenshotHelper.func_74289_a(field_74294_c, par2, par3);
                BufferedImage bufferedimage = new BufferedImage(par2, par3, 1);
                bufferedimage.setRGB(0, 0, par2, par3, field_74294_c, 0, par2);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ImageIO.write((RenderedImage)bufferedimage, "png", byteArrayOutputStream);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] encoded = Base64.encode((byte[])byteArrayOutputStream.toByteArray());
                String base64 = new String(encoded, StandardCharsets.US_ASCII);
                if (Minecraft.func_71410_x().field_71462_r instanceof AssistanceNewTicketGUI) {
                    AssistanceNewTicketGUI assistanceNewTicketGUI = (AssistanceNewTicketGUI)Minecraft.func_71410_x().field_71462_r;
                    assistanceNewTicketGUI.base64 = base64;
                }
            }
        });
        thread.start();
    }

    private static void func_74289_a(int[] par0ArrayOfInteger, int par1, int par2) {
        int[] aint1 = new int[par1];
        int k = par2 / 2;
        for (int l = 0; l < k; ++l) {
            System.arraycopy(par0ArrayOfInteger, l * par1, aint1, 0, par1);
            System.arraycopy(par0ArrayOfInteger, (par2 - 1 - l) * par1, par0ArrayOfInteger, l * par1, par1);
            System.arraycopy(aint1, 0, par0ArrayOfInteger, (par2 - 1 - l) * par1, par1);
        }
    }
}

