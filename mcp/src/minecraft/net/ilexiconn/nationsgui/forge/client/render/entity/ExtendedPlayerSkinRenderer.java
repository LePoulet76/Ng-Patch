/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.client.event.RenderPlayerEvent$SetArmorModel
 *  net.minecraftforge.event.ForgeSubscribe
 */
package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BodyPartSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

public abstract class ExtendedPlayerSkinRenderer {
    private final SkinType skinType;
    private final int slotIndex;
    private boolean renderDone = false;

    public ExtendedPlayerSkinRenderer(SkinType skinType, int slotIndex) {
        this.skinType = skinType;
        this.slotIndex = slotIndex;
    }

    @ForgeSubscribe
    public void onPlayerRenderer(RenderPlayerEvent.Pre event) {
        BodyPartSkin modelSkin;
        this.renderDone = false;
        if (!ClientProxy.clientConfig.render3DSkins) {
            return;
        }
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)event.renderer, (String[])new String[]{"modelBipedMain", "field_77109_a"});
        ModelRenderer bodyPart = this.getBodyPart(playerModel);
        Minecraft minecraft = Minecraft.func_71410_x();
        if (event.entity == minecraft.field_71439_g ? Minecraft.func_71410_x().field_71474_y.field_74320_O == 0 && !(Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI) : event.entityPlayer.func_98034_c((EntityPlayer)Minecraft.func_71410_x().field_71439_g)) {
            return;
        }
        List<AbstractSkin> bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, this.skinType);
        BodyPartSkin bodyPartSkin = modelSkin = bippedSkins.size() > 0 ? (BodyPartSkin)bippedSkins.get(0) : null;
        if (modelSkin == null) {
            return;
        }
        CosmeticModel model = modelSkin.getModel();
        if (model.isReady()) {
            model.updateModel(modelSkin.getTransform("entity"));
            bodyPart.func_78792_a(model.getModel());
            this.renderDone = true;
        }
    }

    @ForgeSubscribe
    public void onArmorModel(RenderPlayerEvent.SetArmorModel event) {
        if (event.slot == this.slotIndex && this.renderDone) {
            event.result = 0;
        }
    }

    protected abstract ModelRenderer getBodyPart(ModelBiped var1);
}

