package net.ilexiconn.nationsgui.forge.client.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceNewTicketGUI;
import net.minecraft.client.Minecraft;
import org.bouncycastle.util.encoders.Base64;

final class ScreenshotHelper$1 implements Runnable
{
    final int val$k;

    final IntBuffer val$field_74293_b;

    final int val$par2;

    final int val$par3;

    ScreenshotHelper$1(int var1, IntBuffer var2, int var3, int var4)
    {
        this.val$k = var1;
        this.val$field_74293_b = var2;
        this.val$par2 = var3;
        this.val$par3 = var4;
    }

    public void run()
    {
        int[] field_74294_c = new int[this.val$k];
        this.val$field_74293_b.get(field_74294_c);
        ScreenshotHelper.access$000(field_74294_c, this.val$par2, this.val$par3);
        BufferedImage bufferedimage = new BufferedImage(this.val$par2, this.val$par3, 1);
        bufferedimage.setRGB(0, 0, this.val$par2, this.val$par3, field_74294_c, 0, this.val$par2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(bufferedimage, "png", byteArrayOutputStream);
        }
        catch (IOException var7)
        {
            var7.printStackTrace();
        }

        byte[] encoded = Base64.encode(byteArrayOutputStream.toByteArray());
        String base64 = new String(encoded, StandardCharsets.US_ASCII);

        if (Minecraft.getMinecraft().currentScreen instanceof AssistanceNewTicketGUI)
        {
            AssistanceNewTicketGUI assistanceNewTicketGUI = (AssistanceNewTicketGUI)Minecraft.getMinecraft().currentScreen;
            assistanceNewTicketGUI.base64 = base64;
        }
    }
}
