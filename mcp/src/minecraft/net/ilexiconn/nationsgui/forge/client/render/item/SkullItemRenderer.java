/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.model.block.SkullModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SkullItemRenderer
implements IItemRenderer {
    public static final ResourceLocation STEVE_TEXTURE = new ResourceLocation("textures/entity/steve.png");
    public static final ResourceLocation SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    public static final ResourceLocation WITHER_SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    public static final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation("textures/entity/zombie/zombie.png");
    public static final ResourceLocation CREEPER_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper.png");
    public static final SkullModel MODEL_SKULL = new SkullModel();
    public static final SkullModel MODEL_SKULL_LARGE = new SkullModel(64, 64);

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case EQUIPPED_FIRST_PERSON: {
                this.renderSkull(item, 0.5f, 0.0f, 0.5f, 2.0f, 0.0f);
                break;
            }
            case EQUIPPED: {
                this.renderSkull(item, 0.5f, 0.0f, 0.5f, 2.0f, -90.0f);
                break;
            }
            case INVENTORY: {
                this.renderSkull(item, 0.0f, -0.5f, 0.0f, 1.8f, 90.0f);
                break;
            }
            default: {
                this.renderSkull(item, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
            }
        }
    }

    public void renderSkull(ItemStack itemStack, float x, float y, float z, float scale, float rotation) {
        NBTTagCompound tagCompound = itemStack.func_77978_p();
        String skullOwner = null;
        if (tagCompound != null && tagCompound.func_74764_b("SkullOwner")) {
            skullOwner = tagCompound.func_74779_i("SkullOwner");
        }
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        GL11.glEnable((int)32826);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glRotatef((float)rotation, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glEnable((int)3008);
        switch (itemStack.func_77960_j()) {
            case 1: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(WITHER_SKELETON_TEXTURE);
                break;
            }
            case 2: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(ZOMBIE_TEXTURE);
                MODEL_SKULL_LARGE.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                GL11.glPopMatrix();
                return;
            }
            case 3: {
                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                try {
                    if (skullOwner != null && skullOwner.length() > 0) {
                        resourceLocation = AbstractClientPlayer.func_110305_h((String)skullOwner);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)skullOwner);
                    }
                }
                catch (Exception e) {
                    resourceLocation = AbstractClientPlayer.field_110314_b;
                }
                Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                MODEL_SKULL_LARGE.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                GL11.glPopMatrix();
                return;
            }
            case 4: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(CREEPER_TEXTURE);
                break;
            }
            default: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(SKELETON_TEXTURE);
            }
        }
        MODEL_SKULL.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}

