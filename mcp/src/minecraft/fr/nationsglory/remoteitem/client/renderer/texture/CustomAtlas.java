/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.renderer.texture.IconRegister
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.resources.Resource
 *  net.minecraft.client.resources.ResourceManager
 *  net.minecraft.util.Icon
 *  net.minecraft.util.ResourceLocation
 */
package fr.nationsglory.remoteitem.client.renderer.texture;

import com.google.common.collect.Lists;
import fr.nationsglory.remoteitem.RemoteItem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import sun.misc.BASE64Decoder;

public class CustomAtlas
extends TextureAtlasSprite {
    public CustomAtlas(String par1Str) {
        super(par1Str);
    }

    private void resetSprite() {
        this.func_110968_a(Lists.newArrayList());
        this.field_110973_g = 0;
        this.field_110983_h = 0;
    }

    public void func_130100_a(Resource par1Resource) throws IOException {
        this.resetSprite();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(RemoteItem.getRemoteConfig().get(this.func_94215_i()).getTexture());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedimage = ImageIO.read(bis);
        this.field_130224_d = bufferedimage.getHeight();
        this.field_130223_c = bufferedimage.getWidth();
        int[] aint = new int[this.field_130224_d * this.field_130223_c];
        bufferedimage.getRGB(0, 0, this.field_130223_c, this.field_130224_d, aint, 0, this.field_130223_c);
        this.field_110976_a.add(aint);
    }

    public boolean load(ResourceManager manager, ResourceLocation location) throws IOException {
        this.func_130100_a(null);
        return true;
    }

    public static Icon registerIcon(IconRegister iconRegister, String textureName) {
        TextureMap textureMap = (TextureMap)iconRegister;
        CustomAtlas customAtlas = (CustomAtlas)textureMap.getTextureExtry(textureName);
        if (customAtlas == null) {
            customAtlas = new CustomAtlas(textureName);
        }
        textureMap.setTextureEntry(textureName, (TextureAtlasSprite)customAtlas);
        return customAtlas;
    }
}

