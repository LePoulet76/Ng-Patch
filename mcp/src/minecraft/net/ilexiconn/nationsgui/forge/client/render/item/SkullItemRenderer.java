package net.ilexiconn.nationsgui.forge.client.render.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.model.block.SkullModel;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer$1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class SkullItemRenderer implements IItemRenderer
{
    public static final ResourceLocation STEVE_TEXTURE = new ResourceLocation("textures/entity/steve.png");
    public static final ResourceLocation SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    public static final ResourceLocation WITHER_SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    public static final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation("textures/entity/zombie/zombie.png");
    public static final ResourceLocation CREEPER_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper.png");
    public static final SkullModel MODEL_SKULL = new SkullModel();
    public static final SkullModel MODEL_SKULL_LARGE = new SkullModel(64, 64);

    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        switch (SkullItemRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
        {
            case 1:
                this.renderSkull(item, 0.5F, 0.0F, 0.5F, 2.0F, 0.0F);
                break;

            case 2:
                this.renderSkull(item, 0.5F, 0.0F, 0.5F, 2.0F, -90.0F);
                break;

            case 3:
                this.renderSkull(item, 0.0F, -0.5F, 0.0F, 1.8F, 90.0F);
                break;

            default:
                this.renderSkull(item, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
        }
    }

    public void renderSkull(ItemStack itemStack, float x, float y, float z, float scale, float rotation)
    {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        String skullOwner = null;

        if (tagCompound != null && tagCompound.hasKey("SkullOwner"))
        {
            skullOwner = tagCompound.getString("SkullOwner");
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        switch (itemStack.getItemDamage())
        {
            case 1:
                Minecraft.getMinecraft().renderEngine.bindTexture(WITHER_SKELETON_TEXTURE);
                break;

            case 2:
                Minecraft.getMinecraft().renderEngine.bindTexture(ZOMBIE_TEXTURE);
                MODEL_SKULL_LARGE.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GL11.glPopMatrix();
                return;

            case 3:
                ResourceLocation resourceLocation = AbstractClientPlayer.locationStevePng;

                try
                {
                    if (skullOwner != null && skullOwner.length() > 0)
                    {
                        resourceLocation = AbstractClientPlayer.getLocationSkull(skullOwner);
                        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, skullOwner);
                    }
                }
                catch (Exception var11)
                {
                    resourceLocation = AbstractClientPlayer.locationStevePng;
                }

                Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                MODEL_SKULL_LARGE.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GL11.glPopMatrix();
                return;

            case 4:
                Minecraft.getMinecraft().renderEngine.bindTexture(CREEPER_TEXTURE);
                break;

            default:
                Minecraft.getMinecraft().renderEngine.bindTexture(SKELETON_TEXTURE);
        }

        MODEL_SKULL.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
