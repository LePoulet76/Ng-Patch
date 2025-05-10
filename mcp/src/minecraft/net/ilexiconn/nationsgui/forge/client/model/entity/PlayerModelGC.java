package net.ilexiconn.nationsgui.forge.client.model.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import micdoodle8.mods.galacticraft.core.client.model.GCCoreModelPlayer;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.HandSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.entity.SkinReseter;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerModelGC extends GCCoreModelPlayer
{
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;

    public PlayerModelGC(float scale)
    {
        super(scale);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedLeftArm = new ModelRenderer(this, 32, 48);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.setTextureSize(64, 32);
        this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.3F);
        this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
        this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
        this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedEars = new ModelRenderer(this, 24, 0);
        this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks)
    {
        super.render(entity, limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks);
        GL11.glPushMatrix();

        if (this.isChild)
        {
            float scale = 2.0F;
            GL11.glScaled((double)(1.0F / scale), (double)(1.0F / scale), (double)(1.0F / scale));
            GL11.glTranslatef(0.0F, 24.0F * partialTicks, 0.0F);
            this.bipedLeftLegwear.render(partialTicks);
            this.bipedRightLegwear.render(partialTicks);
            this.bipedLeftArmwear.render(partialTicks);
            this.bipedRightArmwear.render(partialTicks);
            this.bipedBodyWear.render(partialTicks);
        }
        else
        {
            if (entity.isSneaking())
            {
                GL11.glTranslatef(0.0F, 0.2F, 0.0F);
            }

            this.bipedLeftLegwear.render(partialTicks);
            this.bipedRightLegwear.render(partialTicks);
            this.bipedLeftArmwear.render(partialTicks);
            this.bipedRightArmwear.render(partialTicks);
            this.bipedBodyWear.render(partialTicks);
        }

        GL11.glPopMatrix();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks, entity);

        try
        {
            Class cl = Class.forName("fr.nationsglory.ngupgrades.client.ClientHooks");
            Method method = cl.getDeclaredMethod("setRotationAngles", new Class[] {ModelBiped.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Entity.class});
            method.invoke((Object)null, new Object[] {this, Float.valueOf(limbSwing), Float.valueOf(limbSwingAmount), Float.valueOf(rotation), Float.valueOf(rotationYaw), Float.valueOf(rotationPitch), Float.valueOf(partialTicks), entity});
        }
        catch (InvocationTargetException var10)
        {
            ;
        }

        this.copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        this.copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        this.copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        this.copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        this.copyModelAngles(this.bipedBody, this.bipedBodyWear);
        this.copyModelAngles(this.bipedHead, this.bipedHeadwear);
    }

    public void copyModelAngles(ModelRenderer source, ModelRenderer destination)
    {
        destination.rotateAngleX = source.rotateAngleX;
        destination.rotateAngleY = source.rotateAngleY;
        destination.rotateAngleZ = source.rotateAngleZ;
        destination.rotationPointX = source.rotationPointX;
        destination.rotationPointY = source.rotationPointY;
        destination.rotationPointZ = source.rotationPointZ;
    }

    public static void renderFirstPersonArm(RenderPlayer playerRenderer, ModelBiped modelBiped, EntityPlayer entityPlayer)
    {
        PlayerModelGC playerModel = (PlayerModelGC)modelBiped;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        playerModel.onGround = 0.0F;
        playerModel.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, entityPlayer);

        if (playerModel.bipedRightArm.childModels != null)
        {
            playerModel.bipedRightArm.childModels.clear();
        }

        AbstractSkin abstractSkin = ClientProxy.SKIN_MANAGER.getUniquePlayerActiveSkins(entityPlayer.username, new SkinType[] {SkinType.HANDS});

        if (abstractSkin != null && entityPlayer.getHeldItem() == null && abstractSkin instanceof HandSkin)
        {
            HandSkin handSkin = (HandSkin)abstractSkin;
            handSkin.applyToBody(handSkin.getId(), playerModel);
            Method m = ReflectionHelper.findMethod(RenderPlayer.class, playerRenderer, new String[] {"getEntityTexture", "getEntityTexture"}, new Class[] {Entity.class});

            try
            {
                ResourceLocation e = (ResourceLocation)m.invoke(playerRenderer, new Object[] {entityPlayer});
                SkinReseter reseter = new SkinReseter(playerModel, e);

                if (playerModel.bipedRightArm != null)
                {
                    playerModel.bipedRightArm.addChild(reseter);
                }
            }
            catch (InvocationTargetException var9)
            {
                throw new RuntimeException(var9);
            }
        }

        playerModel.bipedRightArm.render(0.0625F);
        GL11.glDisable(GL11.GL_CULL_FACE);
        playerModel.bipedRightArmwear.render(0.0625F);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
