package net.ilexiconn.nationsgui.forge.client.texture;

import java.io.File;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.resources.LocalResource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

public class LocalTextureAtlasSprite extends TextureAtlasSprite
{
    private final File file;

    public LocalTextureAtlasSprite(String par1Str, File file)
    {
        super(par1Str);
        this.file = file;
    }

    public boolean load(ResourceManager manager, ResourceLocation location) throws IOException
    {
        this.loadSprite(new LocalResource(this.file));
        return true;
    }
}
