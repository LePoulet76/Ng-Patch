/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class FlansGunSkin
extends AbstractItemSkin {
    private final ResourceLocation resourceLocation;

    public FlansGunSkin(JSONObject object) {
        super(object);
        String textureName = (String)object.get("textureName");
        this.resourceLocation = new ResourceLocation("nationsgui", "skins/flansmod_gun/" + textureName);
        try {
            if (Minecraft.func_71410_x().func_110434_K().func_110581_b(this.resourceLocation) == null) {
                BufferedImage textureBuffer = ImageIO.read(new File("assets/textures/itemskins/" + textureName + ".png"));
                Minecraft.func_71410_x().func_110434_K().func_110579_a(this.resourceLocation, (TextureObject)new DynamicTexture(textureBuffer));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void render(float partialTick) {
        float f1 = 1.0f;
        GL11.glColor3f((float)f1, (float)f1, (float)f1);
        ItemStack itemstack = new ItemStack(this.getItemID(), 1, 0);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        RenderManager.field_78727_a.field_78721_f.func_78443_a((EntityLivingBase)Minecraft.func_71410_x().field_71439_g, itemstack, 0);
        if (itemstack.func_77973_b().func_77623_v()) {
            for (int x = 1; x < itemstack.func_77973_b().getRenderPasses(itemstack.func_77960_j()); ++x) {
                RenderManager.field_78727_a.field_78721_f.func_78443_a((EntityLivingBase)Minecraft.func_71410_x().field_71439_g, itemstack, x);
            }
        }
    }

    public static ResourceLocation getGunSkinFromPlayer(EntityPlayer entityPlayer, int itemID) {
        for (AbstractSkin abstractSkin : FlansGunSkin.getSkinsOfItem(SkinType.FLANSMOD_GUN, itemID)) {
            FlansGunSkin gunSkin = (FlansGunSkin)abstractSkin;
            if (!ClientProxy.SKIN_MANAGER.playerHasSkin(entityPlayer.field_71092_bJ, gunSkin)) continue;
            return gunSkin.resourceLocation;
        }
        return null;
    }
}

