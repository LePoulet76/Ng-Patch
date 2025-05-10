package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class ClearPlayerModels
{
    @ForgeSubscribe(
        priority = EventPriority.HIGHEST
    )
    public void clearPlayerModel(Pre event)
    {
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[] {"modelBipedMain", "modelBipedMain"});
        Iterator iterator = playerModel.boxList.iterator();

        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof SkinReseter)
            {
                iterator.remove();
            }
        }

        this.clearBodyPart(playerModel.bipedBody);
        this.clearBodyPart(playerModel.bipedHead);
        this.clearBodyPart(playerModel.bipedRightLeg);
        this.clearBodyPart(playerModel.bipedLeftLeg);
        this.clearBodyPart(playerModel.bipedLeftArm);
        this.clearBodyPart(playerModel.bipedRightArm);
    }

    @ForgeSubscribe(
        priority = EventPriority.LOWEST
    )
    public void applySkinReseters(Pre event) throws InvocationTargetException, IllegalAccessException
    {
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[] {"modelBipedMain", "modelBipedMain"});
        Method m = ReflectionHelper.findMethod(RenderPlayer.class, event.renderer, new String[] {"getEntityTexture", "getEntityTexture"}, new Class[] {Entity.class});
        ResourceLocation skin = (ResourceLocation)m.invoke(event.renderer, new Object[] {event.entityPlayer});
        SkinReseter reseter = new SkinReseter(playerModel, skin);
        this.applyReseter(playerModel.bipedBody, reseter);
        this.applyReseter(playerModel.bipedHead, reseter);
        this.applyReseter(playerModel.bipedRightLeg, reseter);
        this.applyReseter(playerModel.bipedLeftLeg, reseter);
        this.applyReseter(playerModel.bipedLeftArm, reseter);
        this.applyReseter(playerModel.bipedRightArm, reseter);
    }

    private void applyReseter(ModelRenderer bodyPart, SkinReseter reseter)
    {
        if (bodyPart.childModels != null && !bodyPart.childModels.isEmpty())
        {
            bodyPart.addChild(reseter);
        }
    }

    private void clearBodyPart(ModelRenderer bodyPart)
    {
        if (bodyPart.childModels != null)
        {
            bodyPart.childModels.clear();
        }
    }
}
