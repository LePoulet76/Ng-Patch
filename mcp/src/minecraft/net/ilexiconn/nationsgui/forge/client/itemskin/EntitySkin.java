package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class EntitySkin extends AbstractSkin
{
    private final String className;
    private final ResourceLocation resourceLocation;
    private final Constructor <? extends Entity > constructor;
    private Entity entity = null;
    private int dataWatcher = -1;
    private boolean rideable = false;

    public EntitySkin(JSONObject object)
    {
        super(object);
        this.className = (String)object.get("entityClass");

        try
        {
            Class textureName = Class.forName(this.className);
            this.constructor = textureName.getConstructor(new Class[] {World.class});
        }
        catch (Exception var5)
        {
            throw new RuntimeException(var5);
        }

        String textureName1 = (String)object.get("textureName");
        this.resourceLocation = new ResourceLocation("nationsgui", "skins/entity_ride/" + textureName1);

        if (object.containsKey("dataWatcherID"))
        {
            this.dataWatcher = Integer.parseInt((String)object.get("dataWatcherID"));
        }

        if (object.containsKey("rideable"))
        {
            this.rideable = Boolean.parseBoolean((String)object.get("rideable"));
        }

        try
        {
            if (Minecraft.getMinecraft().getTextureManager().getTexture(this.resourceLocation) == null)
            {
                BufferedImage e = ImageIO.read(new File("assets/textures/entities/" + textureName1 + ".png"));
                Minecraft.getMinecraft().getTextureManager().loadTexture(this.resourceLocation, new DynamicTexture(e));
            }
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }
    }

    public void render(float partialTick)
    {
        try
        {
            if (this.entity == null)
            {
                this.entity = (Entity)this.constructor.newInstance(new Object[] {Minecraft.getMinecraft().theWorld});
            }

            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            RenderManager.instance.renderEntityWithPosYaw(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTick);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }
        catch (InvocationTargetException var3)
        {
            var3.printStackTrace();
        }
    }

    public String getClassName()
    {
        return this.className;
    }

    public ResourceLocation getResourceLocation()
    {
        return this.resourceLocation;
    }

    public boolean entityIsValid(Entity entity)
    {
        return this.className.equals(entity.getClass().getName());
    }

    public static ResourceLocation hookEntityTexture(ResourceLocation resourceLocation, Entity entity)
    {
        AbstractSkin[] var2 = SkinType.ENTITY.getSkins();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            AbstractSkin skin = var2[var4];
            EntitySkin entitySkin = (EntitySkin)skin;

            if (entitySkin.entityIsValid(entity))
            {
                String username = "";

                if (entitySkin.dataWatcher != -1)
                {
                    if (entity.getDataWatcher() != null)
                    {
                        username = entity.getDataWatcher().getWatchableObjectString(entitySkin.dataWatcher);
                    }
                }
                else if (entitySkin.rideable && entity.riddenByEntity instanceof EntityPlayer)
                {
                    username = ((EntityPlayer)entity.riddenByEntity).username;
                }

                if (ClientProxy.SKIN_MANAGER.playerHasSkin(username, (AbstractSkin)entitySkin))
                {
                    return entitySkin.resourceLocation;
                }
            }
        }

        return resourceLocation;
    }
}
