/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.client.event.RenderPlayerEvent$SetArmorModel
 *  net.minecraftforge.event.EventPriority
 *  net.minecraftforge.event.ForgeSubscribe
 */
package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ArmorSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class ArmorSkinRenderer {
    /*
     * Enabled aggressive block sorting
     */
    @ForgeSubscribe(priority=EventPriority.LOW)
    public void onPlayerRenderer(RenderPlayerEvent.Pre event) {
        if (!ClientProxy.clientConfig.render3DSkins) {
            return;
        }
        ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, (Object)event.renderer, (String[])new String[]{"modelBipedMain", "field_77109_a"});
        Minecraft minecraft = Minecraft.func_71410_x();
        if (event.entity == minecraft.field_71439_g ? Minecraft.func_71410_x().field_71474_y.field_74320_O == 0 && !(Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI) : event.entityPlayer.func_98034_c((EntityPlayer)Minecraft.func_71410_x().field_71439_g)) {
            return;
        }
        List<AbstractSkin> bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.ARMOR);
        if (bippedSkins.size() <= 0) return;
        ArmorSkin armorSkin = (ArmorSkin)bippedSkins.get(0);
        ArmorSkin modelSkin = armorSkin;
        if (modelSkin == null) {
            return;
        }
        int i = 0;
        while (true) {
            block9: {
                if (i > 3) {
                    return;
                }
                switch (i) {
                    case 3: {
                        if (ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.HAT).isEmpty()) break;
                        break block9;
                    }
                    case 2: {
                        if (!ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.CHESTPLATE).isEmpty()) break block9;
                    }
                }
                modelSkin.applyToBody(event.entityPlayer.func_82169_q(i), playerModel);
            }
            ++i;
        }
    }

    @ForgeSubscribe
    public void onArmorModel(RenderPlayerEvent.SetArmorModel event) {
        ArmorSkin modelSkin;
        List<AbstractSkin> bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.ARMOR);
        ArmorSkin armorSkin = modelSkin = bippedSkins.size() > 0 ? (ArmorSkin)bippedSkins.get(0) : null;
        if (modelSkin == null) {
            return;
        }
        if (modelSkin.isValidArmor(event.stack)) {
            event.result = 0;
        }
    }
}

