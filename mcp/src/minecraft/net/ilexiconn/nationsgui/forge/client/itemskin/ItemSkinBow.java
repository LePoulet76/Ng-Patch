package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.File;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.texture.LocalTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemSkinBow extends AbstractItemSkin
{
    private final String textureName;
    private final Icon[] icons = new Icon[4];

    public ItemSkinBow(JSONObject object)
    {
        super(object);
        this.textureName = (String)object.get("textureName");
    }

    protected void render(float partialTick)
    {
        if (this.icons.length != 0 && this.icons[0] != null)
        {
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ItemStack itemStack = new ItemStack(this.getItemID(), 1, 0);
            Icon icon = this.icons[0];
            textureManager.bindTexture(textureManager.getResourceLocation(itemStack.getItemSpriteNumber()));
            Tessellator tessellator = Tessellator.instance;
            float f = icon.getMinU();
            float f1 = icon.getMaxU();
            float f2 = icon.getMinV();
            float f3 = icon.getMaxV();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        }
    }

    public static void register(IconRegister iconRegister)
    {
        AbstractSkin[] var1 = SkinType.BOW.getSkins();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            AbstractSkin abstractSkin = var1[var3];
            ItemSkinBow skinBow = (ItemSkinBow)abstractSkin;
            TextureMap textureMap = (TextureMap)iconRegister;

            for (int i = 0; i < skinBow.icons.length; ++i)
            {
                File file = new File("assets/textures/itemskins/" + skinBow.textureName + "_" + i + ".png");
                String name = "itemskin-" + FilenameUtils.removeExtension(file.getName());
                Object icon = textureMap.getTextureExtry(name);

                if (icon == null)
                {
                    LocalTextureAtlasSprite sprite = new LocalTextureAtlasSprite(name, file);
                    textureMap.setTextureEntry(name, sprite);
                    icon = sprite;
                }

                skinBow.icons[i] = (Icon)icon;
            }
        }
    }

    private static boolean canRender(AbstractItemSkin abstractItemSkin, EntityPlayer player)
    {
        return ClientProxy.SKIN_MANAGER.playerHasSkin(player.username, (AbstractSkin)abstractItemSkin);
    }

    public static Icon getCustomIcon(EntityPlayer player, ItemStack itemStack)
    {
        Iterator var2 = getSkinsOfItem(SkinType.BOW, itemStack.itemID).iterator();
        AbstractItemSkin abstractItemSkin;

        do
        {
            if (!var2.hasNext())
            {
                return null;
            }

            abstractItemSkin = (AbstractItemSkin)var2.next();
        }
        while (!canRender(abstractItemSkin, player));

        ItemSkinBow itemSkinBow = (ItemSkinBow)abstractItemSkin;

        if (player.getItemInUse() != null)
        {
            int var8 = itemStack.getMaxItemUseDuration() - player.getItemInUseCount();

            if (var8 >= 18)
            {
                return itemSkinBow.icons[3];
            }

            if (var8 > 13)
            {
                return itemSkinBow.icons[2];
            }

            if (var8 > 0)
            {
                return itemSkinBow.icons[1];
            }
        }

        return itemSkinBow.icons[0];
    }
}
