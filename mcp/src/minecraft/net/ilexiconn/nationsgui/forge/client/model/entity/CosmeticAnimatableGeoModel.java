/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.model.AnimatedGeoModel
 */
package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CosmeticAnimatableGeoModel
extends AnimatedGeoModel<IAnimatable> {
    private final String modelName;
    private final String textureName;

    public CosmeticAnimatableGeoModel(String modelName, String textureName) {
        this.modelName = modelName;
        this.textureName = textureName;
    }

    public ResourceLocation getModelLocation(IAnimatable cosmeticModel) {
        return new ResourceLocation("nationsgui", "geo/cosmetics/" + this.modelName + ".geo.json");
    }

    public ResourceLocation getTextureLocation(IAnimatable cosmeticModel) {
        return new ResourceLocation("nationsgui", "textures/cosmetics/" + this.textureName + ".png");
    }

    public ResourceLocation getAnimationFileLocation(IAnimatable cosmeticModel) {
        return new ResourceLocation("nationsgui", "animations/cosmetics/" + this.modelName + ".animation.json");
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getTextureName() {
        return this.textureName;
    }
}

