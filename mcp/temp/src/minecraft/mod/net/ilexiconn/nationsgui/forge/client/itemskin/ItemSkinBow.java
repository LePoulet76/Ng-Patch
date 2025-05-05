package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.File;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
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

public class ItemSkinBow extends AbstractItemSkin {

   private final String textureName;
   private final Icon[] icons = new Icon[4];


   public ItemSkinBow(JSONObject object) {
      super(object);
      this.textureName = (String)object.get("textureName");
   }

   protected void render(float partialTick) {
      if(this.icons.length != 0 && this.icons[0] != null) {
         TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
         ItemStack itemStack = new ItemStack(this.getItemID(), 1, 0);
         Icon icon = this.icons[0];
         textureManager.func_110577_a(textureManager.func_130087_a(itemStack.func_94608_d()));
         Tessellator tessellator = Tessellator.field_78398_a;
         float f = icon.func_94209_e();
         float f1 = icon.func_94212_f();
         float f2 = icon.func_94206_g();
         float f3 = icon.func_94210_h();
         GL11.glEnable('\u803a');
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
      }
   }

   public static void register(IconRegister iconRegister) {
      AbstractSkin[] var1 = SkinType.BOW.getSkins();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         AbstractSkin abstractSkin = var1[var3];
         ItemSkinBow skinBow = (ItemSkinBow)abstractSkin;
         TextureMap textureMap = (TextureMap)iconRegister;

         for(int i = 0; i < skinBow.icons.length; ++i) {
            File file = new File("assets/textures/itemskins/" + skinBow.textureName + "_" + i + ".png");
            String name = "itemskin-" + FilenameUtils.removeExtension(file.getName());
            Object icon = textureMap.getTextureExtry(name);
            if(icon == null) {
               LocalTextureAtlasSprite sprite = new LocalTextureAtlasSprite(name, file);
               textureMap.setTextureEntry(name, sprite);
               icon = sprite;
            }

            skinBow.icons[i] = (Icon)icon;
         }
      }

   }

   private static boolean canRender(AbstractItemSkin abstractItemSkin, EntityPlayer player) {
      return ClientProxy.SKIN_MANAGER.playerHasSkin(player.field_71092_bJ, (AbstractSkin)abstractItemSkin);
   }

   public static Icon getCustomIcon(EntityPlayer player, ItemStack itemStack) {
      Iterator var2 = getSkinsOfItem(SkinType.BOW, itemStack.field_77993_c).iterator();

      AbstractItemSkin abstractItemSkin;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         abstractItemSkin = (AbstractItemSkin)var2.next();
      } while(!canRender(abstractItemSkin, player));

      ItemSkinBow itemSkinBow = (ItemSkinBow)abstractItemSkin;
      if(player.func_71011_bu() != null) {
         int var8 = itemStack.func_77988_m() - player.func_71052_bv();
         if(var8 >= 18) {
            return itemSkinBow.icons[3];
         }

         if(var8 > 13) {
            return itemSkinBow.icons[2];
         }

         if(var8 > 0) {
            return itemSkinBow.icons[1];
         }
      }

      return itemSkinBow.icons[0];
   }
}
