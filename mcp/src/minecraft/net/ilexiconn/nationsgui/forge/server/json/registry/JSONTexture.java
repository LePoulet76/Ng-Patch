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

@SideOnly(Side.CLIENT)
public class JSONTexture extends TextureAtlasSprite
{
    private File parent = new File(".", "nationsgui");
    private File imageFile;

    public JSONTexture(String hash)
    {
        super(hash);
        hash = hash.substring(hash.indexOf(":") + 1);
        this.imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);
    }

    public boolean load(ResourceManager manager, ResourceLocation location) throws IOException
    {
        this.framesTextureData.clear();
        BufferedImage image = ImageIO.read(this.imageFile);
        this.height = image.getHeight();
        this.width = image.getWidth();
        int[] data = new int[this.height * this.width];
        image.getRGB(0, 0, this.width, this.height, data, 0, this.width);
        this.framesTextureData.add(data);
        return true;
    }
}
