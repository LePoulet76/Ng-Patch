/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.event.EventPriority
 *  net.minecraftforge.event.ForgeSubscribe
 */
package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.render.entity.SkinReseter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class ClearPlayerModels {
    @ForgeSubscribe(priority=EventPriority.HIGHEST)
    public void clearPlayerModel(RenderPlayerEvent.Pre event) {
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)event.renderer, (String[])new String[]{"modelBipedMain", "field_77109_a"});
        Iterator iterator = playerModel.field_78092_r.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (!(o instanceof SkinReseter)) continue;
            iterator.remove();
        }
        this.clearBodyPart(playerModel.field_78115_e);
        this.clearBodyPart(playerModel.field_78116_c);
        this.clearBodyPart(playerModel.field_78123_h);
        this.clearBodyPart(playerModel.field_78124_i);
        this.clearBodyPart(playerModel.field_78113_g);
        this.clearBodyPart(playerModel.field_78112_f);
    }

    @ForgeSubscribe(priority=EventPriority.LOWEST)
    public void applySkinReseters(RenderPlayerEvent.Pre event) throws InvocationTargetException, IllegalAccessException {
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)event.renderer, (String[])new String[]{"modelBipedMain", "field_77109_a"});
        Method m = ReflectionHelper.findMethod(RenderPlayer.class, (Object)event.renderer, (String[])new String[]{"getEntityTexture", "func_110775_a"}, (Class[])new Class[]{Entity.class});
        ResourceLocation skin = (ResourceLocation)m.invoke(event.renderer, event.entityPlayer);
        SkinReseter reseter = new SkinReseter((ModelBase)playerModel, skin);
        this.applyReseter(playerModel.field_78115_e, reseter);
        this.applyReseter(playerModel.field_78116_c, reseter);
        this.applyReseter(playerModel.field_78123_h, reseter);
        this.applyReseter(playerModel.field_78124_i, reseter);
        this.applyReseter(playerModel.field_78113_g, reseter);
        this.applyReseter(playerModel.field_78112_f, reseter);
    }

    private void applyReseter(ModelRenderer bodyPart, SkinReseter reseter) {
        if (bodyPart.field_78805_m != null && !bodyPart.field_78805_m.isEmpty()) {
            bodyPart.func_78792_a((ModelRenderer)reseter);
        }
    }

    private void clearBodyPart(ModelRenderer bodyPart) {
        if (bodyPart.field_78805_m != null) {
            bodyPart.field_78805_m.clear();
        }
    }
}

