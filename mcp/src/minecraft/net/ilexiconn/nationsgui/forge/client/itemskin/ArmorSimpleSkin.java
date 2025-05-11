/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ArmorSimpleSkin
extends AbstractSkin {
    private final HashMap<Integer, ResourceLocation[]> textureMap = new HashMap();
    private final List<Tuple<Integer, ResourceLocation[]>> renderList = new ArrayList<Tuple<Integer, ResourceLocation[]>>();
    protected ModelBiped field_82423_g;
    protected ModelBiped field_82425_h;

    public ArmorSimpleSkin(JSONObject object) {
        super(object);
        if (object.containsKey("items")) {
            for (Map.Entry o : ((JSONObject)object.get("items")).entrySet()) {
                try {
                    Map.Entry entry = o;
                    String textureName = (String)entry.getValue();
                    int itemID = Integer.parseInt((String)entry.getKey());
                    ResourceLocation[] locations = new ResourceLocation[]{this.getResourceTexture(textureName + "_layer_1"), this.getResourceTexture(textureName + "_layer_2")};
                    this.textureMap.put(itemID, locations);
                    ItemStack itemStack = new ItemStack(itemID, 1, 0);
                    if (itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemArmor)) continue;
                    ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
                    this.renderList.add(new Tuple<Integer, ResourceLocation[]>(itemArmor.field_77881_a, locations));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.field_82423_g = new ModelBiped(1.0f);
        this.field_82425_h = new ModelBiped(0.5f);
    }

    private ResourceLocation getResourceTexture(String textureName) throws IOException {
        ResourceLocation resourceLocation = new ResourceLocation("skins/armors/" + textureName);
        if (Minecraft.func_71410_x().func_110434_K().func_110581_b(resourceLocation) == null) {
            BufferedImage textureBuffer = ImageIO.read(new File("assets/textures/armorskins/" + textureName + ".png"));
            Minecraft.func_71410_x().func_110434_K().func_110579_a(resourceLocation, (TextureObject)new DynamicTexture(textureBuffer));
        }
        return resourceLocation;
    }

    @Override
    protected void render(float partialTick) {
        GL11.glTranslatef((float)-0.63f, (float)0.8f, (float)0.0f);
        for (Tuple<Integer, ResourceLocation[]> tuple : this.renderList) {
            this.renderItem((Integer)tuple.a, (ResourceLocation[])tuple.b);
        }
    }

    private void renderItem(int par2, ResourceLocation[] resourceLocation) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation[par2 == 2 ? 1 : 0]);
        ModelBiped modelbiped = par2 == 2 ? this.field_82425_h : this.field_82423_g;
        modelbiped.field_78091_s = false;
        modelbiped.field_78116_c.field_78806_j = par2 == 0;
        modelbiped.field_78114_d.field_78806_j = par2 == 0;
        modelbiped.field_78115_e.field_78806_j = par2 == 1 || par2 == 2;
        modelbiped.field_78112_f.field_78806_j = par2 == 1;
        modelbiped.field_78113_g.field_78806_j = par2 == 1;
        modelbiped.field_78123_h.field_78806_j = par2 == 2 || par2 == 3;
        modelbiped.field_78124_i.field_78806_j = par2 == 2 || par2 == 3;
        modelbiped.func_78088_a((Entity)Minecraft.func_71410_x().field_71439_g, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }

    public static String getTexture(String base, Entity entity, ItemStack itemStack) {
        if (entity instanceof EntityPlayer && itemStack.func_77973_b() != null && itemStack.func_77973_b() instanceof ItemArmor) {
            EntityPlayer player = (EntityPlayer)entity;
            for (AbstractSkin playerActiveSkin : ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(player.field_71092_bJ, SkinType.ARMOR_SIMPLE)) {
                ArmorSimpleSkin armorSimpleSkin = (ArmorSimpleSkin)playerActiveSkin;
                ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
                ResourceLocation[] resourceLocation = armorSimpleSkin.textureMap.get(itemStack.field_77993_c);
                if (resourceLocation == null) continue;
                return resourceLocation[itemArmor.field_77881_a == 2 ? 1 : 0].toString();
            }
        }
        return base;
    }
}

