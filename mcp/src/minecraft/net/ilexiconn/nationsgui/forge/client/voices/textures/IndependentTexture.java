package net.ilexiconn.nationsgui.forge.client.voices.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class IndependentTexture
{
    public static final IndependentTexture TEXTURES = new IndependentTexture("gvctextures");
    public static final IndependentTexture GUI_WIZARD = new IndependentTexture("wizard_gui");
    private String texture;
    private Object resource;
    private static final ResourceLocation steve = new ResourceLocation("textures/entity/steve.png");

    public IndependentTexture(String texture)
    {
        this.texture = texture;
        this.resource = new ResourceLocation("gvc", "textures/" + texture + ".png");
    }

    public void bindTexture(Minecraft mc)
    {
        mc.getTextureManager().bindTexture((ResourceLocation)this.getTexture());
    }

    public static void bindDefaultPlayer(Minecraft mc)
    {
        mc.getTextureManager().bindTexture(steve);
    }

    public static void bindPlayer(Minecraft mc, Entity entity)
    {
        mc.getTextureManager().bindTexture(((AbstractClientPlayer)entity).getLocationSkin());
    }

    public static void bindClientPlayer(Minecraft mc)
    {
        mc.getTextureManager().bindTexture(mc.thePlayer.getLocationSkin());
    }

    public String getTexturePath()
    {
        return "textures/" + this.texture + ".png";
    }

    public Object getTexture()
    {
        return this.resource;
    }
}
