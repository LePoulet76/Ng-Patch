package net.ilexiconn.nationsgui.forge.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class SkinReseter extends ModelRenderer
{
    private ResourceLocation resource;

    public SkinReseter(ModelBase model, ResourceLocation texture)
    {
        super(model, "SkinReseter");
        this.resource = texture;
    }

    public void render(float par1)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resource);
    }
}
