/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.ResourceManager
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.server.json.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(value=Side.CLIENT)
public class JSONTexture
extends TextureAtlasSprite {
    private File parent = new File(".", "nationsgui");
    private File imageFile;

    public JSONTexture(String hash) {
        super(hash);
        hash = hash.substring(hash.indexOf(":") + 1);
        this.imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);
    }

    public boolean load(ResourceManager manager, ResourceLocation location) throws IOException {
        this.field_110976_a.clear();
        BufferedImage image = ImageIO.read(this.imageFile);
        this.field_130224_d = image.getHeight();
        this.field_130223_c = image.getWidth();
        int[] data = new int[this.field_130224_d * this.field_130223_c];
        image.getRGB(0, 0, this.field_130223_c, this.field_130224_d, data, 0, this.field_130223_c);
        this.field_110976_a.add(data);
        return true;
    }
}

