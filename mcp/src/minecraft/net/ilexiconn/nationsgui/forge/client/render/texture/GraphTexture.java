/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.resources.ResourceManager
 */
package net.ilexiconn.nationsgui.forge.client.render.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;

public class GraphTexture
extends AbstractTexture {
    private InputStream inputStream;

    public GraphTexture(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void func_110551_a(ResourceManager resourcemanager) {
        if (this.inputStream != null) {
            try {
                BufferedImage bufferedimage = ImageIO.read(this.inputStream);
                if (bufferedimage != null) {
                    TextureUtil.func_110989_a((int)this.func_110552_b(), (BufferedImage)bufferedimage, (boolean)false, (boolean)false);
                }
                this.inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

