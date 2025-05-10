package fr.nationsglory.remoteitem.client.renderer.texture;

import com.google.common.collect.Lists;
import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.common.data.ItemData;
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

public class CustomAtlas extends TextureAtlasSprite
{
    public CustomAtlas(String par1Str)
    {
        super(par1Str);
    }

    private void resetSprite()
    {
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
    }

    public void loadSprite(Resource par1Resource) throws IOException
    {
        this.resetSprite();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(((ItemData)RemoteItem.getRemoteConfig().get(this.getIconName())).getTexture());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedimage = ImageIO.read(bis);
        this.height = bufferedimage.getHeight();
        this.width = bufferedimage.getWidth();
        int[] aint = new int[this.height * this.width];
        bufferedimage.getRGB(0, 0, this.width, this.height, aint, 0, this.width);
        this.framesTextureData.add(aint);
    }

    public boolean load(ResourceManager manager, ResourceLocation location) throws IOException
    {
        this.loadSprite((Resource)null);
        return true;
    }

    public static Icon registerIcon(IconRegister iconRegister, String textureName)
    {
        TextureMap textureMap = (TextureMap)iconRegister;
        CustomAtlas customAtlas = (CustomAtlas)textureMap.getTextureExtry(textureName);

        if (customAtlas == null)
        {
            customAtlas = new CustomAtlas(textureName);
        }

        textureMap.setTextureEntry(textureName, customAtlas);
        return customAtlas;
    }
}
