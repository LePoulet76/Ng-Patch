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
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinBow;
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

public class ItemSkinSimple
extends AbstractItemSkin {
    private Icon icon = null;
    private final String textureName;

    public ItemSkinSimple(JSONObject object) {
        super(object);
        this.textureName = (String)object.get("textureName");
    }

    @Override
    protected void render(float partialTick) {
        if (this.icon == null) {
            return;
        }
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        ItemStack itemStack = new ItemStack(this.getItemID(), 1, 0);
        textureManager.func_110577_a(textureManager.func_130087_a(itemStack.func_94608_d()));
        Tessellator tessellator = Tessellator.field_78398_a;
        float f = this.icon.func_94209_e();
        float f1 = this.icon.func_94212_f();
        float f2 = this.icon.func_94206_g();
        float f3 = this.icon.func_94210_h();
        GL11.glEnable((int)32826);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        ItemRenderer.func_78439_a((Tessellator)tessellator, (float)f1, (float)f2, (float)f, (float)f3, (int)this.icon.func_94211_a(), (int)this.icon.func_94216_b(), (float)0.0625f);
    }

    public static void register(IconRegister iconRegister) {
        for (AbstractSkin abstractSkin : SkinType.ITEM_SIMPLE.getSkins()) {
            ItemSkinSimple skinSimple = (ItemSkinSimple)abstractSkin;
            File file = new File("assets/textures/itemskins/" + skinSimple.textureName + ".png");
            TextureMap textureMap = (TextureMap)iconRegister;
            String name = "itemskin-" + FilenameUtils.removeExtension((String)file.getName());
            TextureAtlasSprite icon = textureMap.getTextureExtry(name);
            if (icon == null) {
                LocalTextureAtlasSprite sprite = new LocalTextureAtlasSprite(name, file);
                textureMap.setTextureEntry(name, (TextureAtlasSprite)sprite);
                icon = sprite;
            }
            skinSimple.icon = icon;
        }
    }

    public static Icon getCustomIcon(Icon originalIcon, EntityPlayer player, ItemStack itemStack) {
        for (AbstractItemSkin abstractItemSkin : ItemSkinSimple.getSkinsOfItem(SkinType.ITEM_SIMPLE, itemStack.field_77993_c)) {
            if (!ClientProxy.SKIN_MANAGER.playerHasSkin(player.field_71092_bJ, abstractItemSkin)) continue;
            return ((ItemSkinSimple)abstractItemSkin).icon;
        }
        Icon bowIcon = ItemSkinBow.getCustomIcon(player, itemStack);
        if (bowIcon != null) {
            return bowIcon;
        }
        return originalIcon;
    }
}

