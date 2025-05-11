/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.texture.IconRegister
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.Icon
 *  org.apache.commons.io.FilenameUtils
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.File;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.texture.LocalTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ItemSkinBow
extends AbstractItemSkin {
    private final String textureName;
    private final Icon[] icons = new Icon[4];

    public ItemSkinBow(JSONObject object) {
        super(object);
        this.textureName = (String)object.get("textureName");
    }

    @Override
    protected void render(float partialTick) {
        if (this.icons.length == 0 || this.icons[0] == null) {
            return;
        }
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        ItemStack itemStack = new ItemStack(this.getItemID(), 1, 0);
        Icon icon = this.icons[0];
        textureManager.func_110577_a(textureManager.func_130087_a(itemStack.func_94608_d()));
        Tessellator tessellator = Tessellator.field_78398_a;
        float f = icon.func_94209_e();
        float f1 = icon.func_94212_f();
        float f2 = icon.func_94206_g();
        float f3 = icon.func_94210_h();
        GL11.glEnable((int)32826);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        ItemRenderer.func_78439_a((Tessellator)tessellator, (float)f1, (float)f2, (float)f, (float)f3, (int)icon.func_94211_a(), (int)icon.func_94216_b(), (float)0.0625f);
    }

    public static void register(IconRegister iconRegister) {
        for (AbstractSkin abstractSkin : SkinType.BOW.getSkins()) {
            ItemSkinBow skinBow = (ItemSkinBow)abstractSkin;
            TextureMap textureMap = (TextureMap)iconRegister;
            for (int i = 0; i < skinBow.icons.length; ++i) {
                File file = new File("assets/textures/itemskins/" + skinBow.textureName + "_" + i + ".png");
                String name = "itemskin-" + FilenameUtils.removeExtension((String)file.getName());
                TextureAtlasSprite icon = textureMap.getTextureExtry(name);
                if (icon == null) {
                    LocalTextureAtlasSprite sprite = new LocalTextureAtlasSprite(name, file);
                    textureMap.setTextureEntry(name, (TextureAtlasSprite)sprite);
                    icon = sprite;
                }
                skinBow.icons[i] = icon;
            }
        }
    }

    private static boolean canRender(AbstractItemSkin abstractItemSkin, EntityPlayer player) {
        return ClientProxy.SKIN_MANAGER.playerHasSkin(player.field_71092_bJ, abstractItemSkin);
    }

    public static Icon getCustomIcon(EntityPlayer player, ItemStack itemStack) {
        for (AbstractItemSkin abstractItemSkin : ItemSkinBow.getSkinsOfItem(SkinType.BOW, itemStack.field_77993_c)) {
            if (!ItemSkinBow.canRender(abstractItemSkin, player)) continue;
            ItemSkinBow itemSkinBow = (ItemSkinBow)abstractItemSkin;
            if (player.func_71011_bu() != null) {
                int var8 = itemStack.func_77988_m() - player.func_71052_bv();
                if (var8 >= 18) {
                    return itemSkinBow.icons[3];
                }
                if (var8 > 13) {
                    return itemSkinBow.icons[2];
                }
                if (var8 > 0) {
                    return itemSkinBow.icons[1];
                }
            }
            return itemSkinBow.icons[0];
        }
        return null;
    }
}

