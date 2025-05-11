/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.modelapi.ModelAPI
 *  fr.nationsglory.modelapi.ModelOBJ
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import fr.nationsglory.modelapi.ModelAPI;
import fr.nationsglory.modelapi.ModelOBJ;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.item.ItemSkinRenderer;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ItemSkinModel
extends AbstractItemSkin {
    private final IItemRenderer renderer = new ItemSkinRenderer(this);
    private final ModelOBJ model;

    public ItemSkinModel(JSONObject object) {
        super(object);
        String textureName = (String)object.get("textureName");
        String modelName = (String)object.get("modelName");
        this.model = ModelAPI.registerModel((String)"itemskins", (String)this.getId(), (String)modelName, (String)textureName);
    }

    public Transform getTransformFirstPerson() {
        return this.getTransform("firstPerson");
    }

    public Transform getTransformEntity() {
        return this.getTransform("entity");
    }

    @Override
    protected void render(float partialTick) {
        GL11.glEnable((int)32826);
        this.getModel().render();
    }

    public ModelOBJ getModel() {
        return this.model;
    }

    public static IItemRenderer hookRenderer(IItemRenderer iItemRenderer, ItemStack itemStack) {
        ItemSkinModel playerSkin = ItemSkinModel.getPlayerSkin(itemStack);
        return playerSkin == null ? iItemRenderer : playerSkin.renderer;
    }

    public static ItemSkinModel getPlayerSkin(ItemStack itemStack) {
        if (Minecraft.func_71410_x().field_71439_g != null && Minecraft.func_71410_x().field_71439_g.field_70170_p != null && Minecraft.func_71410_x().field_71439_g.field_70170_p.field_73010_i != null) {
            for (Object obj : Minecraft.func_71410_x().field_71439_g.field_70170_p.field_73010_i) {
                for (AbstractItemSkin abstractItemSkin : ItemSkinModel.getSkinsOfItem(SkinType.ITEM_MODEL, itemStack.field_77993_c)) {
                    EntityPlayer player = (EntityPlayer)obj;
                    if (!ClientProxy.SKIN_MANAGER.playerHasSkin(player.field_71092_bJ, abstractItemSkin)) continue;
                    return (ItemSkinModel)abstractItemSkin;
                }
            }
        }
        return null;
    }

    @Override
    public void reload() {
        this.model.resetTexture();
        this.model.resetModel(true);
    }
}

