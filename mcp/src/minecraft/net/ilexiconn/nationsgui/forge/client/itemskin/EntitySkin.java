/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class EntitySkin
extends AbstractSkin {
    private final String className;
    private final ResourceLocation resourceLocation;
    private final Constructor<? extends Entity> constructor;
    private Entity entity = null;
    private int dataWatcher = -1;
    private boolean rideable = false;

    public EntitySkin(JSONObject object) {
        super(object);
        this.className = (String)object.get("entityClass");
        try {
            Class<?> entityClass = Class.forName(this.className);
            this.constructor = entityClass.getConstructor(World.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String textureName = (String)object.get("textureName");
        this.resourceLocation = new ResourceLocation("nationsgui", "skins/entity_ride/" + textureName);
        if (object.containsKey("dataWatcherID")) {
            this.dataWatcher = Integer.parseInt((String)object.get("dataWatcherID"));
        }
        if (object.containsKey("rideable")) {
            this.rideable = Boolean.parseBoolean((String)object.get("rideable"));
        }
        try {
            if (Minecraft.func_71410_x().func_110434_K().func_110581_b(this.resourceLocation) == null) {
                BufferedImage textureBuffer = ImageIO.read(new File("assets/textures/entities/" + textureName + ".png"));
                Minecraft.func_71410_x().func_110434_K().func_110579_a(this.resourceLocation, (TextureObject)new DynamicTexture(textureBuffer));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float partialTick) {
        try {
            if (this.entity == null) {
                this.entity = this.constructor.newInstance(Minecraft.func_71410_x().field_71441_e);
            }
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glEnable((int)2903);
            RenderManager.field_78727_a.func_78719_a(this.entity, 0.0, 0.0, 0.0, 0.0f, partialTick);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)32826);
            OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77476_b);
            GL11.glDisable((int)3553);
            OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77478_a);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getClassName() {
        return this.className;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public boolean entityIsValid(Entity entity) {
        return this.className.equals(entity.getClass().getName());
    }

    public static ResourceLocation hookEntityTexture(ResourceLocation resourceLocation, Entity entity) {
        for (AbstractSkin skin : SkinType.ENTITY.getSkins()) {
            EntitySkin entitySkin = (EntitySkin)skin;
            if (!entitySkin.entityIsValid(entity)) continue;
            String username = "";
            if (entitySkin.dataWatcher != -1) {
                if (entity.func_70096_w() != null) {
                    username = entity.func_70096_w().func_75681_e(entitySkin.dataWatcher);
                }
            } else if (entitySkin.rideable && entity.field_70153_n instanceof EntityPlayer) {
                username = ((EntityPlayer)entity.field_70153_n).field_71092_bJ;
            }
            if (!ClientProxy.SKIN_MANAGER.playerHasSkin(username, entitySkin)) continue;
            return entitySkin.resourceLocation;
        }
        return resourceLocation;
    }
}

